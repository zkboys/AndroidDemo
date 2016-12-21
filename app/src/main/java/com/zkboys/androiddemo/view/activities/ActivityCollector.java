package com.zkboys.androiddemo.view.activities;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 同意管理系统中的Activity
 */
public class ActivityCollector {
    public static List<AppCompatActivity> activities = new ArrayList<>();

    public static void addActivity(AppCompatActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity) {
        activities.remove(activity);
    }

    /**
     * 任何位置调用此方法,都可以关闭系统
     */
    public static void finishAll() {
        for (AppCompatActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
