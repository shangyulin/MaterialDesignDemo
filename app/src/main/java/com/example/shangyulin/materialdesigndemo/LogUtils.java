package com.example.shangyulin.materialdesigndemo;

import android.util.Log;

/**
 * Created by shangyulin on 2018/4/3.
 */

public class LogUtils {

    public static final int VERBOSE = 1;

    public static final int DEBUG = 2;

    public static final int INFO = 3;

    public static final int WARNING = 4;

    public static final int ERROR = 5;

    public static final int NOTHING = 6;

    public static int level = VERBOSE;

    public static void setLevel(int i){
        level = i;
    }

    public static void v(String tag, String msg){
        if (level <= VERBOSE){
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if (level <= DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if (level <= INFO){
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if (level <= WARNING){
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if (level <= ERROR){
            Log.e(tag, msg);
        }
    }
}
