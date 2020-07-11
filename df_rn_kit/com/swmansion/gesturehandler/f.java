package com.swmansion.gesturehandler;

import android.view.MotionEvent;

public final class f {
  public static float a(MotionEvent paramMotionEvent, boolean paramBoolean) {
    byte b;
    float f1 = paramMotionEvent.getRawX() - paramMotionEvent.getX();
    if (paramMotionEvent.getActionMasked() == 6) {
      b = paramMotionEvent.getActionIndex();
    } else {
      b = -1;
    } 
    if (paramBoolean) {
      float f2 = 0.0F;
      int n = paramMotionEvent.getPointerCount();
      int k = 0;
      int m = 0;
      while (k < n) {
        int i1 = m;
        float f3 = f2;
        if (k != b) {
          f3 = f2 + paramMotionEvent.getX(k) + f1;
          i1 = m + 1;
        } 
        k++;
        m = i1;
        f2 = f3;
      } 
      return f2 / m;
    } 
    int j = paramMotionEvent.getPointerCount() - 1;
    int i = j;
    if (j == b)
      i = j - 1; 
    return paramMotionEvent.getX(i) + f1;
  }
  
  public static float b(MotionEvent paramMotionEvent, boolean paramBoolean) {
    byte b;
    float f1 = paramMotionEvent.getRawY() - paramMotionEvent.getY();
    if (paramMotionEvent.getActionMasked() == 6) {
      b = paramMotionEvent.getActionIndex();
    } else {
      b = -1;
    } 
    if (paramBoolean) {
      float f2 = 0.0F;
      int n = paramMotionEvent.getPointerCount();
      int k = 0;
      int m = 0;
      while (k < n) {
        int i1 = m;
        float f3 = f2;
        if (k != b) {
          f3 = f2 + paramMotionEvent.getY(k) + f1;
          i1 = m + 1;
        } 
        k++;
        m = i1;
        f2 = f3;
      } 
      return f2 / m;
    } 
    int j = paramMotionEvent.getPointerCount() - 1;
    int i = j;
    if (j == b)
      i = j - 1; 
    return paramMotionEvent.getY(i) + f1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */