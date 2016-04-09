package deeplife.gcme.com.deeplife.Helps;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by BENGEOS on 2/19/16.
 */
public class HelpViewPager extends ViewPager {
    private boolean isSwipeable;
    public HelpViewPager(Context context) {
        super(context);
    }
    public HelpViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isSwipeable = true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.isSwipeable) {
            return super.onTouchEvent(event);
        }
        return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.isSwipeable) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }
    public void setSwipeable(boolean swipeable) {

        this.isSwipeable = swipeable;
    }
}
