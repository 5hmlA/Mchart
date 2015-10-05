package com.refuse.mchart_master;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.refuse.mchar.view.progress.MProgressBall;
import com.refuse.mchar.view.progress.MProgressWidthMsg;

/**
 * Created by Jonas on 2015/10/2.
 */
public class ProgressActuvity extends Activity {

    private MProgressBall mBall;
    private MProgressWidthMsg mPm;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        mBall = (MProgressBall)findViewById(R.id.progress);
        mBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                mBall.mAnimator.cancel();
            }
        });
        mPm = (MProgressWidthMsg)findViewById(R.id.progress_msg);
        mPm.setCurrentPercent(89.99f);
//        mPm.setCurrent(89.99f);
        mBall.setProgressCurrentAni(400);
//        mBall.animateShow();
    }
    public void showAni(View v){
        mBall.animateShow();
        mPm.animateShow();
    }
    public void lianji(View v){
        mBall.setProgressCurrent(940);
        mBall.postInvalidate();
        mPm.setCurrentAni(189.99f);
    }
}
