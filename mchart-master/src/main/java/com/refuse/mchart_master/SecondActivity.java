package com.refuse.mchart_master;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.refuse.mchar.bean.Apiece;
import com.refuse.mchar.view.ManiPie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 2015/9/21.
 */
public class SecondActivity extends Activity {

    private ManiPie pie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        pie = (ManiPie) findViewById(R.id.mpie);
        List<Apiece> piedata = new ArrayList<Apiece>();
        Apiece pie1 = new Apiece(Color.parseColor("#9FD255"), 60f);
        Apiece pie2 = new Apiece(Color.parseColor("#C6E893"), 98f);
        Apiece pie3 = new Apiece(Color.parseColor("#EDF5E2"), 88f);
        Apiece pie4 = new Apiece(Color.parseColor("#EDF5E2"), 32f);
        piedata.add(pie1);
        piedata.add(pie2);
        piedata.add(pie3);
        piedata.add(pie4);
        pie.AnalyticData(piedata);
//        pie.setPiedata3(new Float[]{50f,50f,50f});
//        pie.setPieShowAnimation(ManiPie.PieShowAnimation.FILLOUTING);
//        ObjectAnimator oa = ObjectAnimator.ofFloat(pie, "showProgress", 0, 1);
//        oa.setDuration(1000);
//        oa.start();
    }

    public void refresh(View view) {
        pie.extraDataInit();
        pie.invalidate();
    }

    int i = 30;

    public void rotateAble(View view) {
        pie.setDegrees(i = i + 40);
        pie.invalidate();
    }

    public void showAni(View view) {
        pie.aniShowPie();
        ObjectAnimator oa = ObjectAnimator.ofFloat(pie, "showProgress", 0, 1);
        oa.setDuration(1000);
        oa.start();
        pie.setDegrees(60);
    }
}
