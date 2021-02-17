package com.example.drawmulticircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class DrawCircleView extends View {
    float StrokeWidth = 300.0f;
    int centerX;
    int centerY;
    int rangeX = 300;
    int rangeY = 300;
    RectF rect;

    ArrayList<int[]> colorList;
    ArrayList<Integer> values = new ArrayList<>();
    ArrayList<Float> sweepAngles = new ArrayList<>();
    ArrayList<Float> startAngles = new ArrayList<>();
    float sumValue;
    float sweepAngle = -90;

    public DrawCircleView(Context context) {
        super(context);
    }

    public DrawCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new RectF();

        colorList = ColorList.getColorList();

        values.add(1000);
        values.add(750);
        values.add(500);
        values.add(250);
        values.add(250);
        values.add(125);
        values.add(125);

        for (int i = 0; i < values.size(); i++) {
            sumValue += values.get(i);
        }

        for (int i = 0; i < values.size(); i++) {
            sweepAngles.add((values.get(i) / sumValue) * 360);
        }

        for (int i = -1; i < values.size() - 1; i++) {
            if (i != -1) {
                sweepAngle += (double) sweepAngles.get(i);
            }
            startAngles.add(sweepAngle);
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2 - 400;
        rect.set(centerX - rangeX, centerY - rangeY,
                centerX + rangeX, centerY + rangeY);

        for (int i = 0; i < values.size(); i++) {
            canvas.drawArc(rect, startAngles.get(i), sweepAngles.get(i), false,
                    setPaint(colorList.get(i)));
        }
    }

    public void showCanvas() {
        invalidate();
    }

    private Paint setPaint(int[] colorList) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(colorList[0], colorList[1], colorList[2], colorList[3]));
        paint.setStrokeWidth(StrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }
}
