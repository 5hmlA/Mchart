package com.refuse.mchar.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.refuse.mchar.R;
import com.refuse.mchar.utills.DisplayUtils;

/**
 * Created by jiangzuyun on 2015/9/2.
 */
public class MProgressWidthMsg extends View {

    private int mColor;
    private Paint mPaint;
    private int mTextToRec;
    private int mHeight;
    private int mWidth;
    private String mUnit = "M";
    private int textColor = Color.BLACK;
    private float Max = 100;
    private float current = 100;
    private int mRecColor = Color.RED;
    private float recRound;

    //    private
    public MProgressWidthMsg(Context context) {
        this( context, null );
    }

    public MProgressWidthMsg(Context context, AttributeSet attrs) {
        this( context, attrs, 0 );
    }

    public MProgressWidthMsg(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        init( context, attrs, defStyle );
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes( attrs, R.styleable.MProgressWidthMsg, defStyle, 0 );
            mRecColor = typedArray.getColor( R.styleable.MProgressWidthMsg_recColor, Color.RED );
            textColor = typedArray.getColor( R.styleable.MProgressWidthMsg_textColor, Color.BLACK );
            mUnit = typedArray.getString( R.styleable.MProgressWidthMsg_unit );
            recRound = typedArray.getDimension( R.styleable.MProgressWidthMsg_recRound, 5 );
            current = typedArray.getFloat( R.styleable.MProgressWidthMsg_currprogress, 100 );
            Max = typedArray.getFloat( R.styleable.MProgressWidthMsg_maxprogress, 100 );

            typedArray.recycle();
        }
        mUnit = TextUtils.isEmpty( mUnit ) ? "" : mUnit;
        mPaint = new Paint( Paint.ANTI_ALIAS_FLAG );
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged( w, h, oldw, oldh );
        mWidth = getWidth();
        mHeight = getHeight();
        mTextToRec = DisplayUtils.dip2px(getContext(), 2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw( canvas );
        //        mPaint.setColor(Color.GREEN);
        //        RectF mrect2 = new RectF(0, 0, mWidth, mHeight);
        //        canvas.drawRect(mrect2,mPaint);

        mPaint.setColor( textColor );
        mPaint.setTextSize( mHeight );
        Rect bounds = new Rect();
        String msg = current + "";
        msg = ( msg.endsWith( ".0" ) ? msg.substring( 0, msg.length() - 2 ) : msg ) + mUnit;
        mPaint.getTextBounds( msg, 0, msg.length(), bounds );
        float textWidth = bounds.width();
        float textHeight = bounds.height();

//        float recLenth = mWidth - textWidth - mTextToRec - 4;

        float recLenth = mWidth -mPaint.measureText(( Float.toString(Max).endsWith(".0") ? msg.substring( 0, Float.toString(Max).length() - 2 ) : Float.toString(Max) ) + mUnit) - mTextToRec - 4;

        canvas.drawText( msg, current/Max*recLenth + mTextToRec, mHeight/2 + textHeight/2, mPaint );

        RectF mrect = new RectF( 0, 0, current/Max*recLenth, mHeight );
        mPaint.setColor( mRecColor );
        canvas.drawRoundRect( mrect, recRound, recRound, mPaint );

        //        canvas.drawLine(0,mHeight/2,mWidth,mHeight/2,mPaint);

    }

    public float getMax(){
        return Max;
    }

    public void setMax(float max){
        Max = max;
    }

    public float getCurrent(){
        return current;
    }

    public void setCurrent(float current){
        this.current = current;
    }
}
