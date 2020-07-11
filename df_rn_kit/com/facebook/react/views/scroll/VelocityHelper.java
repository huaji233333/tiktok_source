package com.facebook.react.views.scroll;

import android.view.MotionEvent;
import android.view.VelocityTracker;

public class VelocityHelper {
  private VelocityTracker mVelocityTracker;
  
  private float mXVelocity;
  
  private float mYVelocity;
  
  public void calculateVelocity(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction() & 0xFF;
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
    this.mVelocityTracker.addMovement(paramMotionEvent);
    if (i != 1 && i != 3)
      return; 
    this.mVelocityTracker.computeCurrentVelocity(1);
    this.mXVelocity = this.mVelocityTracker.getXVelocity();
    this.mYVelocity = this.mVelocityTracker.getYVelocity();
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  public float getXVelocity() {
    return this.mXVelocity;
  }
  
  public float getYVelocity() {
    return this.mYVelocity;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\VelocityHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */