package com.tone.myviwe;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhaotong on 2017/6/16.
 */

public class MyView extends View {

    public static final String TAG = "MyView";
    private Paint paint;
    private float arc=0;
    private static final int MIN_WIDTH = 300;
    private float startAngle=0f;

    public MyView(Context context) {
        super(context);
        init(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);


        int width;
        if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
            width = Math.min(widthSpecSize, heightSpecSize);
        } else {
            width = Math.min(widthSpecSize, heightSpecSize);
            width = Math.min(width, MIN_WIDTH);
        }
        setMeasuredDimension(width, width);
    }

    void initPaint(){
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        Shader shader = new LinearGradient(0f,0f,getWidth()*arc/360,getWidth()*arc/360,Color.parseColor("#FFF700C2"),Color.parseColor("#FFFFD900"),Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }

    void init(Context context){
        initPaint();
        ValueAnimator arcAnmimator=ValueAnimator.ofFloat(60,360,60);
        arcAnmimator.setInterpolator(new DecelerateInterpolator());
        arcAnmimator.setDuration(6000);
        arcAnmimator.setRepeatCount(-1);
        arcAnmimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arc= (float) animation.getAnimatedValue();
                Log.d(TAG, "onAnimationUpdate: "+arc);
                postInvalidate();
            }
        });
        arcAnmimator.start();

        ValueAnimator degress=ValueAnimator.ofFloat(0,360);
        degress.setInterpolator(new LinearInterpolator());
        degress.setDuration(2000);
        degress.setRepeatCount(-1);
        degress.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startAngle= (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        degress.start();

        ValueAnimator va=ValueAnimator.ofInt(0,36);
        va.setInterpolator(new LinearInterpolator());
        va.setDuration(200);
        va.setRepeatCount(-1);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int a= (int) animation.getAnimatedValue();
            }
        });

    }

    RectF getRectF(){
        int padding=getWidth()/10;
        return new RectF(padding,padding,getWidth()-padding,getWidth()-padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initPaint();
        canvas.drawArc(getRectF(),startAngle,arc,false,paint);
        drawStroke(canvas);
    }

    private void drawStroke(Canvas canvas){
        for (int i=0;i<36;i++){
//            canvas.rotate(10,getWidth()/2,getWidth()/2);
            canvas.drawArc(getRectF(),i*10,5,false,paint);
        }
    }

}
