package com.example.drawmulticircle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    EditText editText;
    ListView listView;
    private InputMethodManager inputMethodManager;
    SimpleAdapter adapter;
    ArrayList<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

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
                new String[]{"name","value","num","sum"},
                new int[]{R.id.name, R.id.value,R.id.num, R.id.sum});
        inputMethodManager =  (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        Map data = new HashMap();
        data.put("name", "Corporation");
        data.put("value", "Price");
        data.put("num", "Num");
        data.put("sum", "Sum");
        list.add(data);
        listView.setAdapter(adapter);

        //debug
        drawCircleView.addValue(10000);
        drawCircleView.addValue(10000);
        drawCircleView.addValue(10000);

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

        numPicker0 = layout.findViewById(R.id.numPicker0);
        numPicker1 = layout.findViewById(R.id.numPicker1);
        numPicker2 = layout.findViewById(R.id.numPicker2);
        numPicker3 = layout.findViewById(R.id.numPicker3);
        numPicker4 = layout.findViewById(R.id.numPicker4);
        numPicker5 = layout.findViewById(R.id.numPicker5);
        numPicker6 = layout.findViewById(R.id.numPicker6);
        numPicker7 = layout.findViewById(R.id.numPicker7);
        numPicker8 = layout.findViewById(R.id.numPicker8);
        numPicker9 = layout.findViewById(R.id.numPicker9);

        numPicker0.setMaxValue(9);
        numPicker0.setMinValue(0);

        numPicker1.setMaxValue(9);
        numPicker1.setMinValue(0);

        numPicker2.setMaxValue(9);
        numPicker2.setMinValue(0);

        numPicker3.setMaxValue(9);
        numPicker3.setMinValue(0);

        numPicker4.setMaxValue(9);
        numPicker4.setMinValue(0);

        numPicker5.setMaxValue(9);
        numPicker5.setMinValue(0);

        numPicker6.setMaxValue(9);
        numPicker6.setMinValue(0);

        numPicker7.setMaxValue(9);
        numPicker7.setMinValue(0);

        numPicker8.setMaxValue(9);
        numPicker8.setMinValue(0);

        numPicker9.setMaxValue(9);
        numPicker9.setMinValue(0);

        new AlertDialog.Builder(this)
                .setTitle("会社名と株価の追加")
                .setView(layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int stockPrice = numPicker0.getValue()*10000 +
                                numPicker1.getValue()*1000 +
                                numPicker2.getValue()*100 +
                                numPicker3.getValue()*10 +
                                numPicker4.getValue();
                        String name = editText.getText().toString();

                        int stockNum = numPicker6.getValue()*1000 +
                                numPicker7.getValue()*100 +
                                numPicker8.getValue()*10 +
                                numPicker9.getValue();

                        if (name.length() != 0 && stockPrice != 0 && stockNum != 0) {
                            Map data = new HashMap();
                            data.put("name", name);
                            data.put("value", stockPrice);
                            data.put("num", stockNum);
                            data.put("sum", stockPrice * stockNum);
                            list.add(data);
                            listView.setAdapter(adapter);
                            drawCircleView.addValue(stockPrice * stockNum);
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
}