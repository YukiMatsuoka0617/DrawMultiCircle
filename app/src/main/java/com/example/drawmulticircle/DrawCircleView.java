package com.example.drawmulticircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DrawCircleView extends View {
    Paint paint1;
    Paint paint2;
    Paint paint3;
    Paint paint4;
    float StrokeWidth = 300.0f;
    int centerX;
    int centerY;
    int rangeX = 300;
    int rangeY = 300;
    float sweepAngle = 90;
    float startAngle = 0;
    RectF rect;

    public DrawCircleView(Context context) {
        super(context);
    }

    public DrawCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        startAngle = startAngle - 90;
        rect = new RectF();

        paint1 = new Paint();
        paint1.setColor(Color.argb(128, 0, 0, 255));
        paint1.setStrokeWidth(StrokeWidth);
        paint1.setStyle(Paint.Style.STROKE);

        paint2 = new Paint();
        paint2.setColor(Color.argb(128, 0, 255, 0));
        paint2.setStrokeWidth(StrokeWidth);
        paint2.setStyle(Paint.Style.STROKE);

        paint3 = new Paint();
        paint3.setColor(Color.argb(128, 255, 0, 0));
        paint3.setStrokeWidth(StrokeWidth);
        paint3.setStyle(Paint.Style.STROKE);

        paint4 = new Paint();
        paint4.setColor(Color.argb(128, 255, 0, 255));
        paint4.setStrokeWidth(StrokeWidth);
        paint4.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        rect.set(centerX - rangeX, centerY - rangeY,
                centerX + rangeX, centerY + rangeY);

        canvas.drawArc(rect, startAngle, sweepAngle, false, paint1);
        startAngle = 90 - 90;
        canvas.drawArc(rect, startAngle, sweepAngle, false, paint2);
        startAngle = 180 - 90;
        canvas.drawArc(rect, startAngle, sweepAngle, false, paint3);
        startAngle = 270 - 90;
        canvas.drawArc(rect, startAngle, sweepAngle, false, paint4);
    }

    public void showCanvas(){
        invalidate();
    }
}
