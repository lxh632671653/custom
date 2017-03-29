package com.lxh.custom.view.luckRoller;

import android.graphics.Bitmap;

/**
 * Created by lxh on 2017/3/29.
 * QQ-632671653
 */

public class LuckyBean {

    private int img;
    private String name;
    private int Color;
    private Bitmap bitmap;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
