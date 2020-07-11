package com.facebook.react.views.drawer;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.events.NativeGestureUtil;

class ReactDrawerLayout extends DrawerLayout {
  private int mDrawerPosition = 8388611;
  
  private int mDrawerWidth = -1;
  
  public ReactDrawerLayout(ReactContext paramReactContext) {
    super((Context)paramReactContext);
  }
  
  void closeDrawer() {
    closeDrawer(this.mDrawerPosition);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    try {
      if (super.onInterceptTouchEvent(paramMotionEvent)) {
        NativeGestureUtil.notifyNativeGestureStarted((View)this, paramMotionEvent);
        return true;
      } 
    } catch (IllegalArgumentException illegalArgumentException) {}
    return false;
  }
  
  void openDrawer() {
    openDrawer(this.mDrawerPosition);
  }
  
  void setDrawerPosition(int paramInt) {
    this.mDrawerPosition = paramInt;
    setDrawerProperties();
  }
  
  void setDrawerProperties() {
    if (getChildCount() == 2) {
      View view = getChildAt(1);
      DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams)view.getLayoutParams();
      layoutParams.a = this.mDrawerPosition;
      layoutParams.width = this.mDrawerWidth;
      view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      view.setClickable(true);
    } 
  }
  
  void setDrawerWidth(int paramInt) {
    this.mDrawerWidth = paramInt;
    setDrawerProperties();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\drawer\ReactDrawerLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */