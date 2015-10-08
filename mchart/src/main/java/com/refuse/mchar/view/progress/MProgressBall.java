package com.refuse.mchar.view.progress;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.refuse.mchar.R;
import com.refuse.mchar.utills.DisplayUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jonas on 2015/10/2.
 */
public class MProgressBall extends View {

    /**
     * 进度的颜色
     */
    private int progressColor;
    private static final int MODE_WAVE = 0;
    private static final int MODE_NORMAL = 1;
    private int progressMode;
    /**
     * 进度球的背景颜色
     */
    private int ballgroundColor;
    /**
     * onDraw中使用的进度 动画使用的进度
     */
//    private float progressCurrent;
    /**
     * 设置的 进度
     */
    private float pCurrent;
    private float progressMax = 100;
    private Paint mBackPaint;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    /**
     * 长宽中较短一个 也是球的直径
     */
    private int mJust;
    private PointF mCenter;
    private RectF mProgressArc;
    private DecimalFormat numFormat;
    private Rect bounds = new Rect();
    private Paint mTextPaint;
    private int textColor;
    /**
     * 进度球的半径
     */
    private int radius;
    private float mTextSize = DisplayUtils.sp2px(getContext(), 20);
    public long ANIDURATION = 5000;
    private TimeInterpolator Interpolator = new DecelerateInterpolator();
    public ValueAnimator mAnimator = new ValueAnimator();
    private float textCurrent;
    private List<Integer> mTierColor = new ArrayList<>();

    /**
     * 超过100%的多少层
     */
    private int mTier;
    private float mCenx;
    private float mCeny;
    /**
     * 用来剪切 圆画布的路劲
     */
    private Path backPath;
    /**
     * 画波纹需要的 路劲
     */
    private Path wPath;
    /**
     * 波浪振幅
     */
    private float range = 20;
    /**
     * 存放原始的 波浪振幅
     */
    private float rangeTemp = 20;
    /**
     * 波纹 移动产生波浪效果 需要的变量
     */
    private Random mRandom;
    /**
     * 波纹 移动产生波浪效果 需要的变量
     * 移动的距离
     */
    private float waveMove = 0;
    /**
     * 存放
     */
    private float waveMoveTemp = 0;
    /**
     * 波纹 移动产生波浪效果 需要的变量
     * 移动的速度
     */
    private double waveSpeed = 5;
    /**
     * 关闭 波纹的 滚动波浪效果
     */
    private boolean stopWaving = false;

    {
        backPath = new Path();
        wPath = new Path();
        mRandom = new Random();
    }

    public MProgressBall(Context context){
        this(context, null);
    }

    public MProgressBall(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public MProgressBall(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        if(attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MProgressBall, defStyleAttr, 0);
            progressColor = typedArray.getColor(R.styleable.MProgressBall_progressColor, Color.RED);
            ballgroundColor = typedArray.getColor(R.styleable.MProgressBall_backgroundColor, Color.WHITE);
            textColor = typedArray.getColor(R.styleable.MProgressBall_balltextColor, Color.BLACK);
            progressMax = typedArray.getFloat(R.styleable.MProgressBall_progressMax, 100);
            pCurrent = typedArray.getFloat(R.styleable.MProgressBall_progressCurrent, 0);
            //获取字体大小
            mTextSize = typedArray.getDimensionPixelSize(R.styleable.MProgressBall_balltextSize, 40);
            //            mTextSize = typedArray.getDimension(R.styleable.MProgressBall_balltextSize, 30);

            typedArray.recycle();
        }
        init();
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();

    }

    private void init(){

        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //"##.##0"小数位0不能放#后面    "0##.##"整数位0不能放#前面
        numFormat = new DecimalFormat("#0.##%");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mJust = mWidth>mHeight ? mHeight : mWidth;
        radius = radius == 0 ? mJust/2 : radius;
        mCenx = mWidth/2f;
        mCeny = mHeight/2f;
        mBackPaint.setColor(ballgroundColor);
        mPaint.setColor(progressColor);
        mTextPaint.setColor(textColor);
        mCenter = new PointF(mWidth/2, mHeight/2);
        mProgressArc = new RectF(mCenter.x-mJust/2, mCenter.y-mJust/2, mCenter.x+mJust/2, mCenter.y+mJust/2);
        backPath.addCircle(mCenx, mCeny, radius, Path.Direction.CCW);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MProgressBall(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //画背景
        //        canvas.drawCircle(mCenter.x, mCenter.y, radius, mBackPaint);
        canvas.clipPath(backPath);//剪切出圆形画布 画在外面的部分不显示
        canvas.drawColor(ballgroundColor);

        mTier = (int)textCurrent/( (int)progressMax );//当前画的是第mTier+1层
        mTier = textCurrent == progressMax*mTier ? mTier>0 ? mTier-1 : 0 : mTier;
        for(int i = 0; i<mTier; i++) {
            Paint tierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            tierPaint.setColor(mTierColor.get(i));
            mBackPaint.setColor(mTierColor.get(i));
            //            tierPaint.setAlpha(255-10*mTier);
            canvas.drawArc(mProgressArc, 0, 360, false, tierPaint);
        }
        float progressCurrent = textCurrent-( textCurrent == progressMax*mTier ? 0 : mTier )*progressMax;//进度条进度 解决超过100%部分

        if(progressMode == 0) {
            drawWaveProgress(canvas, progressCurrent);
        }else {
            //画进度
            drawProgress(canvas,progressCurrent);
        }

        String currMsg = numFormat.format(textCurrent/progressMax);
        mTextPaint.getTextBounds(currMsg, 0, currMsg.length(), bounds);
        mTextPaint.setTextSize(mTextSize);
        canvas.drawText(currMsg, mCenter.x-bounds.width()/2, mCenter.y+bounds.height()/2, mTextPaint);

    }

    private void drawWaveProgress(Canvas canvas, float progressCurrent){

        float progress = mJust/progressMax*progressCurrent;

        //mCeny+wRadius-cProgress 为当前进度 所在的y轴坐标
        float currentY = mCeny+radius-progressCurrent/progressMax*radius*2;
        wPath.reset();
        wPath.moveTo(0, currentY);//起点

        //波浪进度 的path
        for(int i = (int)( mCenx-radius ); i<=mCenx+radius; i++) {
            //            range = mRandom.nextInt(30)+10; //打开 会出现较大的锯齿 有点像声波
            //画cos曲线  cos曲线往左或者往右一直移动出现波浪波动效果
            wPath.lineTo(i, currentY+range*(float)Math.cos(( i+waveMove )*( 480f/( mCenx+radius ) )*Math.PI/180f));
        }
        wPath.lineTo(mWidth, mHeight);//右下角
        wPath.lineTo(0, mHeight);//左下角  之后会闭合到起点
        if(mTierColor.size()>0 && mTierColor.size()>mTier) {
            mPaint.setColor(mTierColor.get(mTier));
        }
        //画波浪进度
        canvas.drawPath(wPath, mPaint);

        //中间线条
        canvas.drawLine(0, currentY, mWidth, currentY, mBackPaint);
        if(!stopWaving) {
            waveMove += waveSpeed+mRandom.nextInt(10);
            //当最后一层进度满100% 时波纹慢慢消失
            if(progressCurrent == progressMax && mTier == (int)(pCurrent/progressMax)-1) {
                //注意--range和 range-- 的区别
                //range = range>0?--range:0;
                if(range>0) {
                    range = range-0.08f;
                    postInvalidate();
                }
            }else {
                postInvalidate();
                range = rangeTemp;
            }
        }
    }

    /**
     * 画扇形进度
     *
     * @param canvas
     *         画笔
     * @param progressCurrent
     */
    private void drawProgress(Canvas canvas, float progressCurrent){
        //        mTier = (int)textCurrent/( (int)progressMax );//当前画的是第mTier+1层
        //        mTier = textCurrent == progressMax*mTier ? mTier>0 ? mTier-1 : 0 : mTier;
        //        for(int i = 0; i<mTier; i++) {
        //            Paint tierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //            tierPaint.setColor(mTierColor.get(i));
        //            //            tierPaint.setAlpha(255-10*mTier);
        //            canvas.drawArc(mProgressArc, 0, 360, false, tierPaint);
        //        }
        //        progressCurrent = textCurrent-( textCurrent == progressMax*mTier ? 0 : mTier )*progressMax;//进度条进度 解决超过100%部分

        float progress = mJust/progressMax*progressCurrent;
        //        角度=弧度/PI * 180     弧度＝(角度/180) *PI
        double acos = Math.acos(( radius-progress )/radius)/Math.PI*180;
        float start = (float)( 90-acos );
        float sweep = (float)( acos*2 );
        if(mTierColor.size()>0 && mTierColor.size()>mTier) {
            mPaint.setColor(mTierColor.get(mTier));
        }
        canvas.drawArc(mProgressArc, start, sweep, false, mPaint);

    }

    /**
     * 获取随机的颜色
     *
     * @return 随机的颜色
     */
    private int getRanColor(){
        Random random = new Random();
        return 0xff000000|random.nextInt(0x00ffffff);
    }

    /**
     * 设置 进度条的颜色
     *
     * @param progressColor
     *         进度条的颜色
     */
    public void setProgressColor(int progressColor){
        this.progressColor = progressColor;
        mBackPaint.setColor(progressColor);
        //        postInvalidate();
    }


    public float getProgressCurrent(){
        return pCurrent;
    }

    /**
     * 设置当前进度  使当前有效 需要刷新
     *
     * @param progressCurrent
     *         当前进度
     */
    public void setProgressCurrent(float progressCurrent){
        pCurrent = progressCurrent;
        tierColor(progressCurrent);
    }

    /**
     * 设置当前进度  有动画
     *
     * @param progressCurrent2
     *         当前进度  有动画
     */
    public void setProgressCurrentAni(float progressCurrent2){
        pCurrent = progressCurrent2;
        tierColor(progressCurrent2);
        animateShow();
        //        mAnimator = ValueAnimator.ofFloat(0, progressCurrent2)
        //                .setDuration((long)( ANIDURATION*progressCurrent2/progressMax ));
        //        mAnimator.cancel();
        //        mAnimator.setFloatValues(0, progressCurrent2);
        //        mAnimator.setDuration((long)( ANIDURATION*pCurrent/progressMax ));
        //        mAnimator.setInterpolator(Interpolator);
        //        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        //            @Override
        //            public void onAnimationUpdate(ValueAnimator animation){
        //                progressCurrent = (float)animation.getAnimatedValue();
        //
        //                postInvalidate();
        //            }
        //        });
        //        mAnimator.start();
    }

    /**
     * 需要的话 分配好各层的颜色
     *
     * @param progressCurrent2
     *         设置进度 并会根据需要配置随机颜色
     */
    private void tierColor(float progressCurrent2){
        mTier = (int)progressCurrent2/( (int)progressMax );
        mTier = progressCurrent2 == progressMax*mTier ? mTier>0 ? mTier-1 : 0 : mTier;//需要mtier+1层
        mTierColor.clear();//有add的地方就要注意clear
        if(mTier>0) {
            for(int i = 0; i<=mTier; i++) {
                if(i == 0) {
                    mTierColor.add(progressColor);
                }else {
                    mTierColor.add(getRanColor());
                }
            }
        }
    }

    public void animateShow(){
        mAnimator.cancel();
        mAnimator.setFloatValues(0, pCurrent);
        mAnimator.setDuration((long)( ANIDURATION*pCurrent/progressMax ));
        mAnimator.setInterpolator(Interpolator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                textCurrent = (float)animation.getAnimatedValue();//文字进度
                textCurrent = Math.abs(textCurrent-pCurrent)<1 ? pCurrent : textCurrent;
                //                mTier = (int)textCurrent/( (int)progressMax );
                //                progressCurrent = textCurrent-( textCurrent == progressMax*mTier ? 0 : mTier )*progressMax;//进度条进度 解决超过100%部分
                postInvalidate();
            }
        });
        mAnimator.start();
    }

    /**
     * 设置 总进度
     *
     * @param progressMax
     *         总进度
     */
    public void setProgressMax(float progressMax){
        this.progressMax = progressMax;
    }

    /**
     * 设置进度球的颜色
     *
     * @param ballgroundColor
     *         进度球的颜色
     */
    public void setBallgroundColor(int ballgroundColor){
        this.ballgroundColor = ballgroundColor;
        mBackPaint.setColor(ballgroundColor);
        //        postInvalidate();
    }

    public void setTextSize(float textSize){
        mTextSize = DisplayUtils.sp2px(getContext(), textSize);
    }

    /**
     * 修改进度的格式器
     *
     * @param numFormat
     *         进度的格式器
     */
    public void setNumFormat(DecimalFormat numFormat){
        this.numFormat = numFormat;
    }

    /**
     * 设置动画执行的加速器
     *
     * @param interpolator
     *         加速器
     */
    public void setInterpolator(TimeInterpolator interpolator){
        Interpolator = interpolator;
    }

    public int getProgressMode(){
        return progressMode;
    }

    /**
     * 设置 进度球的 进度模式 1，普通模式
     * 0，波纹模式
     *
     * @param progressMode
     */
    public void setProgressMode(int progressMode){
        this.progressMode = progressMode;

    }
}

