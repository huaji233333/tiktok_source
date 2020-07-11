package com.swmansion.gesturehandler;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;

public class k extends b<k> {
  public ScaleGestureDetector a;
  
  public double b;
  
  public double u;
  
  public float v;
  
  public float w;
  
  private ScaleGestureDetector.OnScaleGestureListener x = new ScaleGestureDetector.OnScaleGestureListener(this) {
      public final boolean onScale(ScaleGestureDetector param1ScaleGestureDetector) {
        double d1 = this.a.b;
        k k1 = this.a;
        double d2 = k1.b;
        double d3 = param1ScaleGestureDetector.getScaleFactor();
        Double.isNaN(d3);
        k1.b = d2 * d3;
        long l = param1ScaleGestureDetector.getTimeDelta();
        if (l > 0L) {
          k1 = this.a;
          d2 = k1.b;
          d3 = l;
          Double.isNaN(d3);
          k1.u = (d2 - d1) / d3;
        } 
        if (Math.abs(this.a.v - param1ScaleGestureDetector.getCurrentSpan()) >= this.a.w && this.a.g == 2)
          this.a.e(); 
        return true;
      }
      
      public final boolean onScaleBegin(ScaleGestureDetector param1ScaleGestureDetector) {
        this.a.v = param1ScaleGestureDetector.getCurrentSpan();
        return true;
      }
      
      public final void onScaleEnd(ScaleGestureDetector param1ScaleGestureDetector) {}
    };
  
  public k() {
    a(false);
  }
  
  protected final void a(MotionEvent paramMotionEvent) {
    if (this.g == 0) {
      Context context = this.f.getContext();
      this.u = 0.0D;
      this.b = 1.0D;
      this.a = new ScaleGestureDetector(context, this.x);
      this.w = ViewConfiguration.get(context).getScaledTouchSlop();
      f();
    } 
    ScaleGestureDetector scaleGestureDetector = this.a;
    if (scaleGestureDetector != null)
      scaleGestureDetector.onTouchEvent(paramMotionEvent); 
    int j = paramMotionEvent.getPointerCount();
    int i = j;
    if (paramMotionEvent.getActionMasked() == 6)
      i = j - 1; 
    if (this.g == 4 && i < 2) {
      g();
      return;
    } 
    if (paramMotionEvent.getActionMasked() == 1)
      d(); 
  }
  
  protected final void b() {
    this.a = null;
    this.u = 0.0D;
    this.b = 1.0D;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */