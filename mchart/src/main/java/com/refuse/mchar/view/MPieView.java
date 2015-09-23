package com.refuse.mchar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.refuse.mchar.utills.DisplayUtils;

import java.util.List;

/**
 * Created by jiangzuyun on 2015/8/31.
 */
public class MPieView extends View {

    private Paint mPieP;
    private int diameter;
    private int mHeight;
    private int mWidth;
    private int mSecond;
    private int mThird;
    private int pading = DisplayUtils.dip2px(getContext(), 4.5f);
    private RectF mRectf;
    private float interval = 1;
    private List<Apiece> piedata;
    private boolean usePie = true;
    private float progress = 1;
    private int backColor;

    public MPieView(Context context){
        this(context, null);
    }

    public MPieView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }


    public MPieView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth()-getPaddingRight()-getPaddingLeft();
        mHeight = getHeight()-getPaddingBottom()-getPaddingTop();
        diameter = mWidth>mHeight ? mHeight : mWidth;
        diameter -= pading;
        mSecond = diameter/2-DisplayUtils.dip2px(getContext(), 4.5f);
        mThird = mSecond-DisplayUtils.dip2px(getContext(), 19f);
    }

    private void init(Context context, AttributeSet attrs, int defStyle){
        mPieP = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override protected void onFinishInflate(){
        super.onFinishInflate();
    }

    @Override protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(usePie) {
            mPieP.setColor(backColor == 0 ? Color.parseColor("#ebebeb") : backColor);
            mPieP.setStyle(Paint.Style.STROKE);
            mPieP.setStrokeWidth(DisplayUtils.dip2px(getContext(), 4.5f));
            canvas.drawCircle(mWidth/2, mHeight/2, diameter/2, mPieP);
            mPieP.reset();
            mPieP.setColor(Color.WHITE);
            canvas.drawCircle(mWidth/2, mHeight/2, mSecond, mPieP);
            mPieP.reset();
            mPieP.setAntiAlias(true);
            mRectf = mRectf != null ? mRectf : new RectF(mWidth/2-diameter/2+pading/2, mHeight/2-diameter/2+pading/2, mWidth/2+diameter/2-pading/2, mHeight/2+diameter/2-pading/2);
            if(piedata != null && piedata.size()>0) {
                for(Apiece pie : piedata) {
                    mPieP.setColor(pie.getEcolor());
                    canvas.drawArc(mRectf, pie.getStartAngle()*progress, pie
                            .getSweepAngle()*progress, true, mPieP);
                }
            }

            mPieP.reset();
            mPieP.setAntiAlias(true);
            mPieP.setColor(Color.WHITE);
            canvas.drawCircle(mWidth/2, mHeight/2, mThird, mPieP);
        }else {
            mPieP.setColor(backColor == 0 ? Color.parseColor("#ebebeb") : backColor);
            canvas.drawRect(0, 0, mWidth, mHeight, mPieP);
            if(piedata != null && piedata.size()>0) {
                for(Apiece pie : piedata) {
                    mPieP.setColor(pie.getEcolor());
                    canvas.drawRect(pie.getStartAngle()/360f*mWidth*progress, 0, ( pie
                            .getSweepAngle()+pie
                            .getStartAngle() )/360f*mWidth*progress, mHeight, mPieP);
                }
            }
        }

    }

    public void AnalyticData(List<Apiece> piedata){
        float totalData = 0;
        for(Apiece pie : piedata) {
            totalData += pie.getNum();
        }
        float totalAngle = 360-interval*piedata.size();
        float startAngle = interval;
        for(Apiece pie : piedata) {
            float sweepAngle = pie.getNum()/totalData*totalAngle;
            pie.setStartAngle(startAngle);
            pie.setSweepAngle(sweepAngle);
            startAngle += sweepAngle+interval;
        }
        this.piedata = piedata;
        postInvalidate();
    }

    public static class Apiece {
        private float startAngle;
        private float sweepAngle;
        private int ecolor;
        private float num;

        public Apiece(int ecolor, float num){
            this.ecolor = ecolor;
            this.num = num;
        }

        public Apiece(float startAngle, float sweepAngle, int ecolor, float num){
            this.startAngle = startAngle;
            this.sweepAngle = sweepAngle;
            this.ecolor = ecolor;
            this.num = num;
        }

        public float getStartAngle(){
            return startAngle;
        }

        public void setStartAngle(float startAngle){
            this.startAngle = startAngle;
        }

        public float getSweepAngle(){
            return sweepAngle;
        }

        public void setSweepAngle(float sweepAngle){
            this.sweepAngle = sweepAngle;
        }

        public int getEcolor(){
            return ecolor;
        }

        public void setEcolor(int ecolor){
            this.ecolor = ecolor;
        }

        public float getNum(){
            return num;
        }

        public void setNum(float num){
            this.num = num;
        }
    }

    public boolean isUsePie(){
        return usePie;
    }

    public void setUsePie(boolean usePie){
        this.usePie = usePie;
        postInvalidate();
    }

    public float getProgress(){
        return progress;
    }

    public void setProgress(float progress){
        this.progress = progress;
    }
}
