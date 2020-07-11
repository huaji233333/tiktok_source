package com.facebook.react.views.swiperefresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.NativeGestureUtil;

public class ReactSwipeRefreshLayout extends SwipeRefreshLayout {
  private boolean mDidLayout;
  
  private boolean mIntercepted;
  
  private float mPrevTouchX;
  
  private float mProgressViewOffset;
  
  private boolean mRefreshing;
  
  private int mTouchSlop;
  
  public ReactSwipeRefreshLayout(ReactContext paramReactContext) {
    super((Context)paramReactContext);
    this.mTouchSlop = ViewConfiguration.get((Context)paramReactContext).getScaledTouchSlop();
  }
  
  private boolean shouldInterceptTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    if (i != 0) {
      if (i != 2)
        return true; 
      float f = Math.abs(paramMotionEvent.getX() - this.mPrevTouchX);
      if (this.mIntercepted || f > this.mTouchSlop) {
        this.mIntercepted = true;
        return false;
      } 
    } else {
      this.mPrevTouchX = paramMotionEvent.getX();
      this.mIntercepted = false;
    } 
    return true;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    if (shouldInterceptTouchEvent(paramMotionEvent) && super.onInterceptTouchEvent(paramMotionEvent)) {
      NativeGestureUtil.notifyNativeGestureStarted((View)this, paramMotionEvent);
      return true;
    } 
    return false;
  }
  
  public void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (!this.mDidLayout) {
      this.mDidLayout = true;
      setProgressViewOffset(this.mProgressViewOffset);
      setRefreshing(this.mRefreshing);
    } 
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    if (getParent() != null)
      getParent().requestDisallowInterceptTouchEvent(paramBoolean); 
  }
  
  public void setProgressViewOffset(float paramFloat) {
    this.mProgressViewOffset = paramFloat;
    if (this.mDidLayout) {
      int i = getProgressCircleDiameter();
      setProgressViewOffset(false, Math.round(PixelUtil.toPixelFromDIP(paramFloat)) - i, Math.round(PixelUtil.toPixelFromDIP(paramFloat + 64.0F) - i));
    } 
  }
  
  public void setRefreshing(boolean paramBoolean) {
    this.mRefreshing = paramBoolean;
    if (this.mDidLayout)
      super.setRefreshing(paramBoolean); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\swiperefresh\ReactSwipeRefreshLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */