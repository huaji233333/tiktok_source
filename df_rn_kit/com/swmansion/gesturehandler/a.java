package com.swmansion.gesturehandler;

import android.os.Handler;
import android.view.MotionEvent;

public class a extends b<a> {
  private final Runnable A = new Runnable(this) {
      public final void run() {
        this.a.d();
      }
    };
  
  public int a = 1;
  
  public int b = 1;
  
  private long u = 800L;
  
  private long v = 160L;
  
  private float w;
  
  private float x;
  
  private Handler y;
  
  private int z;
  
  private boolean c(MotionEvent paramMotionEvent) {
    if (this.z == this.b && (((this.a & 0x1) != 0 && paramMotionEvent.getRawX() - this.w > (float)this.v) || ((this.a & 0x2) != 0 && this.w - paramMotionEvent.getRawX() > (float)this.v) || ((this.a & 0x4) != 0 && this.x - paramMotionEvent.getRawY() > (float)this.v) || ((this.a & 0x8) != 0 && paramMotionEvent.getRawY() - this.x > (float)this.v))) {
      this.y.removeCallbacksAndMessages(null);
      e();
      g();
      return true;
    } 
    return false;
  }
  
  protected final void a() {
    Handler handler = this.y;
    if (handler != null)
      handler.removeCallbacksAndMessages(null); 
  }
  
  protected final void a(MotionEvent paramMotionEvent) {
    int i = this.g;
    if (i == 0) {
      this.w = paramMotionEvent.getRawX();
      this.x = paramMotionEvent.getRawY();
      f();
      this.z = 1;
      Handler handler = this.y;
      if (handler == null) {
        this.y = new Handler();
      } else {
        handler.removeCallbacksAndMessages(null);
      } 
      this.y.postDelayed(this.A, this.u);
    } 
    if (i == 2) {
      c(paramMotionEvent);
      if (paramMotionEvent.getPointerCount() > this.z)
        this.z = paramMotionEvent.getPointerCount(); 
      if (paramMotionEvent.getActionMasked() == 1 && !c(paramMotionEvent))
        d(); 
    } 
  }
  
  protected final void b() {
    Handler handler = this.y;
    if (handler != null)
      handler.removeCallbacksAndMessages(null); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */