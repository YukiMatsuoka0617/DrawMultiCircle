package com.example.drawmulticircle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements View.OnKeyListener {
    Button button;
    LayoutInflater inflater;
    View layout;

    EditText editTextName, editTextPrice, editTextNum;
    static ListView listView;
    private InputMethodManager inputMethodManager;

    static SimpleAdapter adapter;
    static ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

    static SQLiteDatabase db;
    SQLite sQLite;

    int spinnerPosition;

    TextView textViewUnit;

    MyFragmentPagerAdapter myFragmentPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButton();

        listView = findViewById(R.id.listView);
        adapter = new SimpleAdapter(this,
                list,
                R.layout.listview_item,
                new String[]{"name", "type", "price", "num", "sum"},
                new int[]{R.id.name, R.id.type, R.id.value, R.id.num, R.id.sum});
        inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(myFragmentPagerAdapter);

        if (sQLite == null) {
            sQLite = new SQLite(getApplicationContext());
        }

        if (db == null) {
            db = sQLite.getWritableDatabase();
        }

    }

    void setButton(){
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    void makeSpinner(){
        String[] spinnerItems = {
                getString(R.string.japan_stock),
                getString(R.string.america_stock),
                getString(R.string.investment_trust),
                getString(R.string.commodities)
        };
        textViewUnit = layout.findViewById(R.id.text_unit);
        Spinner spinner = layout.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPosition = i;
                if (i == 1) {
                    textViewUnit.setText(getString(R.string.dollar));
                } else {
                    textViewUnit.setText(getString(R.string.yen));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    void makeEditText(){
        editTextName = layout.findViewById(R.id.editText);
        editTextName.setHint(getString(R.string.corporation_name));
        editTextName.setOnKeyListener(this);

        editTextPrice = layout.findViewById(R.id.edit_price);
        editTextPrice.setHint(getString(R.string.stock_price));
        editTextPrice.setOnKeyListener(this);

        editTextNum = layout.findViewById(R.id.edit_num);
        editTextNum.setHint(getString(R.string.stock_num));
        editTextNum.setOnKeyListener(this);
    }

    void showDialog(){
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.layout));

        makeSpinner();

        makeEditText();

        new AlertDialog.Builder(this)
                .setTitle("会社名と株価の追加")
                .setView(layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = editTextName.getText().toString();

                        int stockPrice = Integer.parseInt(editTextPrice.getText().toString());

                        int stockNum = Integer.parseInt(editTextNum.getText().toString());

                        if (name.length() != 0 && stockPrice != 0 && stockNum != 0) {
                            setListView(name, stockPrice, stockNum, stockPrice * stockNum,
                                    spinnerPosition);
                            Fragment0.drawCircleView.addValue(stockPrice * stockNum);
                            saveDate(db, name, stockPrice, stockNum, stockPrice * stockNum,
                                    spinnerPosition);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
            inputMethodManager.hideSoftInputFromWindow(editTextName.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
            return true;
        }
        return false;
    }

    void saveDate(SQLiteDatabase db, String comName, int price, int num, int sum, int type) {
        ContentValues values = new ContentValues();
        values.put("comName", comName);
        values.put("price", price);
        values.put("num", num);
        values.put("sum", sum);
        values.put("type", type);
        db.insert("stockDb", null, values);
    }

    public static void readDate() {
        Log.d("test", "readDate");
        Cursor cursor = db.query(
                "stockDb",
                new String[]{"comName", "price", "num", "sum", "type"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            setListView(cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4));
            Fragment0.drawCircleView.addValue(cursor.getInt(3));
            cursor.moveToNext();
        }
        cursor.close();
    }

    public static void setListView(String comName, int price, int num, int sum, int type) {
        Map data = new HashMap();
        data.put("name", comName);
        data.put("price", price);
        data.put("num", num);
        data.put("sum", sum);
        switch (type){
            case 0:
                data.put("type", "日本株");
                break;
            case 1:
                data.put("type", "アメリカ株");
                break;
            case 2:
                data.put("type", "投資信託");
                break;
            case 3:
                data.put("type", "コモディティ");
                break;
        }

        list.add(data);
        listView.setAdapter(adapter);
    }
}