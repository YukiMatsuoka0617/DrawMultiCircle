package com.example.drawmulticircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class DrawCircleView extends View {
    float StrokeWidth = 100.0f;
    int centerX;
    int centerY;
    int rangeX = 500;
    int rangeY = 500;
    RectF rect;

    private ArrayList<int[]> colorList;
    private ArrayList<Integer> values = new ArrayList<>();
    private ArrayList<Float> sweepAngles = new ArrayList<>();
    private ArrayList<Float> startAngles = new ArrayList<>();
    float sumValue = 0;
    float sweepAngle = -90;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;

    public DrawCircleView(Context context) {
        super(context);
    }

    public DrawCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new RectF();

        colorList = ColorList.getColorList();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        rect.set(centerX - rangeX, centerY - rangeY,
                centerX + rangeX, centerY + rangeY);

        for (int i = 0; i < values.size(); i++) {
            sumValue += values.get(i);
        }
        Log.d("test", "sumValue:" + sumValue);

        for (int i = 0; i < values.size(); i++) {
            sweepAngles.add((values.get(i) / sumValue) * 360);
        }
        Log.d("test", "sweepAngles:" + sweepAngles);

        for (int i = -1; i < values.size() - 1; i++) {
            if (i != -1) {
                sweepAngle += (double) sweepAngles.get(i);
            }
            startAngles.add(sweepAngle);
        }
        Log.d("test", "startAngles:" + startAngles);

        drawCircleEdge(canvas);

        for (int i = 0; i < values.size(); i++) {
            canvas.drawArc(rect, startAngles.get(i), sweepAngles.get(i), false,
                    setPaint(colorList.get(i)));
        }
        drawAllText(canvas);
    }

    private void drawAllText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 0, 0, 0));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        drawText(canvas, paint, "合計", 75, 150);
        drawText(canvas, paint, String.valueOf(sumValue), 150, 0);
    }

    private void drawText(Canvas canvas, Paint paint, String text, int textSize, int diffY) {
        paint.setTextSize(textSize);
        float textWidth = paint.measureText(text);
        canvas.drawText(text, centerX - (textWidth / 2), centerY - diffY, paint);
    }

    private Paint setPaint(int[] colorList) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(colorList[0], colorList[1], colorList[2], colorList[3]));
        paint.setStrokeWidth(StrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    private void drawCircleEdge(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 0, 0, 0));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        RectF baseRect = new RectF();
        baseRect.set(centerX - 550, centerY - 550,
                centerX + 550, centerY + 550);
        canvas.drawArc(baseRect, -90, 360, false, paint);
        baseRect.set(centerX - 450, centerY - 450,
                centerX + 450, centerY + 450);
        canvas.drawArc(baseRect, -90, 360, false, paint);
    }

    public void addValue(int value) {
        values.add(value);
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
                sumValue = 0;
                sweepAngles.clear();
                sweepAngle = -90;
                startAngles.clear();
            }
        };
        handler.post(runnable);
    }

}
