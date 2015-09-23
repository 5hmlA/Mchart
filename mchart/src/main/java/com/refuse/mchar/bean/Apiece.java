package com.refuse.mchar.bean;

import java.util.Random;

/**
 * Created by jiangzuyun on 2015/8/31.
 *
 * 把每个扇形封装成一个对象 内部封装 改扇形的 描述 大小 颜色 起始角度 扫描角度
 */
public class Apiece {
    /**
     * 内容
     */
    private String describe;
    /**
     * 数量
     */
    private Float num;
    /**
     * 颜色
     */
    private int pieColor = Integer.MAX_VALUE;
    /**
     * 扇形的起始角度
     */
    private float startAngle;
    /**
     * 扇形的角度
     */
    private float sweepAngle;

    public Apiece(String describe, Float num, int pieColor, float startAngle, float sweepAngle) {
        this.describe = describe;
        this.num = num;
        this.pieColor = pieColor;
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
    }

    public Apiece(String describe, Float num, int pieColor) {
        super();
        this.describe = describe;
        this.num = num;
        this.pieColor = pieColor;
    }

    public Apiece(int pieColor, Float num) {
        this.pieColor = pieColor;
        this.num = num;
    }

    /**
     * 颜色随机
     *
     * @param num
     */
    public Apiece(Float num) {
        this.pieColor = getRanColor();
        this.num = num;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Float getNum() {
        return num;
    }

    public void setNum(Float num) {
        this.num = num;
    }

    public int getPieColor() {
        return pieColor;
    }

    public void setPieColor(int pieColor) {
        this.pieColor = pieColor;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }
    private int getRanColor() {
        Random random = new Random();
        return 0xff000000 | random.nextInt(0x00ffffff);
    }
}
