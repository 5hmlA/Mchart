package com.refuse.mchar.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.refuse.mchar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 2015/9/7.
 */
public class BIChanceView extends RelativeLayout {
    private Context context;
    private MPieView pieView;
    private TextView progress;
    private TextView lose;
    private TextView win;
    private TextView cardTitle;

    public BIChanceView(Context context) {
        this(context, null);
    }

    public BIChanceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BIChanceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.chanceview, this, true);
        pieView = (MPieView) findViewById(R.id.mpieview);
        win = (TextView) findViewById(R.id.bi_customer_chance_win);
        lose = (TextView) findViewById(R.id.bi_customer_chance_lose);
        progress = (TextView) findViewById(R.id.bi_customer_chance_pro);
        cardTitle = (TextView) findViewById(R.id.cardtitle);
        this.context = context;
    }

    public void setData(int... data) {
        if (data.length<3){
            return;
        }
        cardTitle.setText(getTag()+"");
        List<MPieView.Apiece> piedata = new ArrayList<MPieView.Apiece>();
        win.setText(data[0]+"");
        lose.setText(data[1]+"");
        progress.setText(data[2] + "");
        MPieView.Apiece pie = new MPieView.Apiece(Color.parseColor("#9FD255"), data[0]);
        MPieView.Apiece pie2 = new MPieView.Apiece(Color.parseColor("#C6E893"), data[1]);
        MPieView.Apiece pie3 = new MPieView.Apiece(Color.parseColor("#EDF5E2"), data[2]);
        piedata.add(pie);
        piedata.add(pie2);
        piedata.add(pie3);
        pieView.AnalyticData(piedata);
//        postInvalidate();
    }

    public MPieView getMpieView(){
        return pieView;
    }

}
