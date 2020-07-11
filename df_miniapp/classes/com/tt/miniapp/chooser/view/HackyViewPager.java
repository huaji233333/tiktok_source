package com.tt.miniapp.chooser.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HackyViewPager extends ViewPager {
  public HackyViewPager(Context paramContext) {
    super(paramContext);
  }
  
  public HackyViewPager(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    try {
      return super.onInterceptTouchEvent(paramMotionEvent);
    } catch (IllegalArgumentException illegalArgumentException) {
      return false;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\chooser\view\HackyViewPager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */