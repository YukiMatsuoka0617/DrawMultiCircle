package com.example.drawmulticircle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DrawCircleView drawCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawCircleView = findViewById(R.id.drawCircleView);
        drawCircleView.showCanvas();
    }
}