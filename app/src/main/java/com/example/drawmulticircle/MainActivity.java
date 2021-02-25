package com.example.drawmulticircle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    DrawCircleView drawCircleView;
    Button button;
    LayoutInflater inflater;
    View layout;
    NumberPicker numPicker0, numPicker1, numPicker2, numPicker3, numPicker4,
            numPicker5, numPicker6, numPicker7, numPicker8, numPicker9;
    NumberPicker[] numberPickers = new NumberPicker[]{numPicker0, numPicker1, numPicker2, numPicker3, numPicker4,
            numPicker5, numPicker6, numPicker7, numPicker8, numPicker9};
    int[] numberPickersId = new int[]{R.id.numPicker0,R.id.numPicker1,R.id.numPicker2,R.id.numPicker3,R.id.numPicker4,
            R.id.numPicker5,R.id.numPicker6,R.id.numPicker7,R.id.numPicker8,R.id.numPicker9};

    EditText editText;
    ListView listView;
    private InputMethodManager inputMethodManager;
    SimpleAdapter adapter;
    ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

    SQLiteDatabase db;
    SQLite sQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        drawCircleView = findViewById(R.id.drawCircleView);
        listView = findViewById(R.id.listView);
        adapter = new SimpleAdapter(this,
                list,
                R.layout.listview_item,
                new String[]{"name","price","num","sum"},
                new int[]{R.id.name, R.id.value,R.id.num, R.id.sum});
        inputMethodManager =  (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);

        if(sQLite == null){
            sQLite = new SQLite(getApplicationContext());
        }

        if(db == null){
            db = sQLite.getWritableDatabase();
        }

        readDate();

    }

    @Override
    public void onClick(View view) {
        NumPickerDialog();
    }

    void NumPickerDialog(){
        inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.layout));

        editText = layout.findViewById(R.id.editText);
        editText.setHint("会社名");
        editText.setOnKeyListener(this);

        for (int i = 0; i < numberPickers.length; i++) {
            numberPickers[i] = layout.findViewById(numberPickersId[i]);
            numberPickers[i].setMinValue(0);
            numberPickers[i].setMaxValue(9);
        }

        new AlertDialog.Builder(this)
                .setTitle("会社名と株価の追加")
                .setView(layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int stockPrice = numberPickers[0].getValue()*10000 +
                                numberPickers[1].getValue()*1000 +
                                numberPickers[2].getValue()*100 +
                                numberPickers[3].getValue()*10 +
                                numberPickers[4].getValue();
                        String name = editText.getText().toString();

                        int stockNum = numberPickers[6].getValue()*1000 +
                                numberPickers[7].getValue()*100 +
                                numberPickers[8].getValue()*10 +
                                numberPickers[9].getValue();

                        if (name.length() != 0 && stockPrice != 0 && stockNum != 0) {
                            setListView(name,stockPrice,stockNum,stockPrice * stockNum);
                            drawCircleView.addValue(stockPrice * stockNum);
                            saveDate(db, name, stockPrice, stockNum, stockPrice * stockNum);
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)){
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            return true;
        }
        return false;
    }

    void saveDate(SQLiteDatabase db, String comName, int price, int num , int sum){
        ContentValues values = new ContentValues();
        values.put("comName", comName);
        values.put("price", price);
        values.put("num", num);
        values.put("sum", sum);
        db.insert("stockDb", null, values);
    }

    void readDate(){
        Cursor cursor = db.query(
                "stockDb",
                new String[] { "comName", "price", "num", "sum" },
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
                    cursor.getInt(3));
            drawCircleView.addValue(cursor.getInt(3));
            cursor.moveToNext();
        }
        cursor.close();
    }

    void setListView(String comName,int price ,int num, int sum){
        Map data = new HashMap();
        data.put("name",comName);
        data.put("price", price);
        data.put("num", num);
        data.put("sum", sum);
        list.add(data);
        listView.setAdapter(adapter);
    }
}