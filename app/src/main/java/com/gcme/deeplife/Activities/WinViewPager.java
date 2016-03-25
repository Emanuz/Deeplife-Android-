package com.gcme.deeplife.Activities;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by rog on 11/8/2015.
 */
public class WinViewPager extends ViewPager {

    private boolean swipeable;
    public WinViewPager(Context context) {
        super(context);
    }
    public WinViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.swipeable = true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.swipeable) {
            return super.onTouchEvent(event);
        }
        return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.swipeable) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }
    public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }
}
