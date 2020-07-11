package com.facebook.react.views.scroll;

import android.os.SystemClock;

public class OnScrollDispatchHelper {
  private long mLastScrollEventTimeMs = -11L;
  
  private int mPrevX = Integer.MIN_VALUE;
  
  private int mPrevY = Integer.MIN_VALUE;
  
  private float mXFlingVelocity;
  
  private float mYFlingVelocity;
  
  public float getXFlingVelocity() {
    return this.mXFlingVelocity;
  }
  
  public float getYFlingVelocity() {
    return this.mYFlingVelocity;
  }
  
  public boolean onScrollChanged(int paramInt1, int paramInt2) {
    boolean bool;
    long l1 = SystemClock.uptimeMillis();
    if (l1 - this.mLastScrollEventTimeMs > 10L || this.mPrevX != paramInt1 || this.mPrevY != paramInt2) {
      bool = true;
    } else {
      bool = false;
    } 
    long l2 = this.mLastScrollEventTimeMs;
    if (l1 - l2 != 0L) {
      this.mXFlingVelocity = (paramInt1 - this.mPrevX) / (float)(l1 - l2);
      this.mYFlingVelocity = (paramInt2 - this.mPrevY) / (float)(l1 - l2);
    } 
    this.mLastScrollEventTimeMs = l1;
    this.mPrevX = paramInt1;
    this.mPrevY = paramInt2;
    return bool;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\OnScrollDispatchHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */