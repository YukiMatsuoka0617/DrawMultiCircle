package com.example.drawmulticircle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawCircleView drawCircleView;
    Button button;
    LayoutInflater inflater;
    View layout;
    NumberPicker numPicker0, numPicker1, numPicker2, numPicker3,numPicker4,numPicker5;
    int[] num = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        drawCircleView = findViewById(R.id.drawCircleView);
    }

    @Override
    public void onClick(View view) {
        NumPickerDialog();
    }

    void NumPickerDialog(){
        inflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.layout));

        numPicker0 = layout.findViewById(R.id.numPicker0);
        numPicker1 = layout.findViewById(R.id.numPicker1);
        numPicker2 = layout.findViewById(R.id.numPicker2);
        numPicker3 = layout.findViewById(R.id.numPicker3);
        numPicker4 = layout.findViewById(R.id.numPicker4);
        numPicker5 = layout.findViewById(R.id.numPicker5);

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

        new AlertDialog.Builder(this)
                .setTitle("title")
                .setView(layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // OK button pressed
                        num[0] = numPicker0.getValue();
                        num[1] = numPicker1.getValue();
                        num[2] = numPicker2.getValue();
                        num[3] = numPicker3.getValue();
                        num[4] = numPicker4.getValue();
                        num[5] = numPicker5.getValue();

                        int result = num[0]*10000 + num[1]*1000 + num[2]*100 + num[3]*10 + num[4];
                        drawCircleView.addValue(result);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}