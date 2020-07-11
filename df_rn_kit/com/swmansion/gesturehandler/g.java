package com.swmansion.gesturehandler;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;

public class g extends b<g> {
  private static float u = 10.0F;
  
  public long a = 500L;
  
  public float b;
  
  private float v;
  
  private float w;
  
  private Handler x;
  
  public g(Context paramContext) {
    a(true);
    this.b = u * (paramContext.getResources().getDisplayMetrics()).density;
  }
  
  protected final void a(MotionEvent paramMotionEvent) {
    Handler handler;
    if (this.g == 0) {
      f();
      this.v = paramMotionEvent.getRawX();
      this.w = paramMotionEvent.getRawY();
      this.x = new Handler();
      this.x.postDelayed(new Runnable(this) {
            public final void run() {
              this.a.e();
            }
          },  this.a);
    } 
    if (paramMotionEvent.getActionMasked() == 1) {
      handler = this.x;
      if (handler != null) {
        handler.removeCallbacksAndMessages(null);
        this.x = null;
      } 
      if (this.g == 4) {
        g();
        return;
      } 
      d();
      return;
    } 
    float f1 = handler.getRawX() - this.v;
    float f2 = handler.getRawY() - this.w;
    if (f1 * f1 + f2 * f2 > this.b) {
      if (this.g == 4) {
        c();
        return;
      } 
      d();
    } 
  }
  
  protected final void b(int paramInt1, int paramInt2) {
    Handler handler = this.x;
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
      this.x = null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */