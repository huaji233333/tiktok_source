package com.swmansion.gesturehandler;

import android.view.MotionEvent;

public final class m {
  long a;
  
  long b;
  
  double c;
  
  public double d;
  
  public float e;
  
  public float f;
  
  boolean g;
  
  int[] h = new int[2];
  
  a i;
  
  public m(a parama) {
    this.i = parama;
  }
  
  void a() {
    if (this.g) {
      this.g = false;
      a a1 = this.i;
      if (a1 != null)
        a1.b(this); 
    } 
  }
  
  void a(MotionEvent paramMotionEvent) {
    this.b = this.a;
    this.a = paramMotionEvent.getEventTime();
    int i = paramMotionEvent.findPointerIndex(this.h[0]);
    int j = paramMotionEvent.findPointerIndex(this.h[1]);
    float f1 = paramMotionEvent.getX(i);
    float f2 = paramMotionEvent.getY(i);
    float f3 = paramMotionEvent.getX(j);
    float f4 = paramMotionEvent.getY(j);
    this.e = (f1 + f3) * 0.5F;
    this.f = (f2 + f4) * 0.5F;
    double d = -Math.atan2((f4 - f2), (f3 - f1));
    if (Double.isNaN(this.c)) {
      this.d = 0.0D;
    } else {
      this.d = this.c - d;
    } 
    this.c = d;
    d = this.d;
    if (d > Math.PI) {
      this.d = d - Math.PI;
    } else if (d < -3.141592653589793D) {
      this.d = d + Math.PI;
    } 
    d = this.d;
    if (d > 1.5707963267948966D) {
      this.d = d - Math.PI;
      return;
    } 
    if (d < -1.5707963267948966D)
      this.d = d + Math.PI; 
  }
  
  public static interface a {
    boolean a(m param1m);
    
    void b(m param1m);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */