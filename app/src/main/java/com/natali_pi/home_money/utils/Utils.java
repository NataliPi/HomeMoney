package com.natali_pi.home_money.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Konstantyn Zakharchenko on 15.04.2018.
 */

public class Utils {
    public static int getThisMonth(){
        return Integer.parseInt(new SimpleDateFormat("yyyyMM").format(new Date(System.currentTimeMillis())));
    }

    public static int[] getTodayKeys() {
        int[] keys = new int[3];
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");

        keys[0] = Integer.parseInt(year.format(new Date()));
        keys[1] = Integer.parseInt(month.format(new Date())) - 1;
        keys[2] = Integer.parseInt(day.format(new Date()));
        return keys;
    }
    public static int[] getPosition(View view) {
        if (view != null) {
            int[] pos = new int[2];
            view.getLocationOnScreen(pos);
            return pos;
        } else {
            return new int[]{0,0};
        }
    }

    public static int nameToCollor(String name) {
        int r = 0,g = 0,b = 0;
        for (int i = 0; i < name.length(); i++) {
            int character = ((byte)name.charAt(i) % 32)*8;
            if (i%3 == 0){
                r = (r+character) / 2;
            } else if (i%3 == 1){
                g = (g+character) / 2;
            }else {
                b = (b+character) / 2;
            }
        }
        return Color.argb(255, r,g,b);
    }
}
