package com.swmansion.gesturehandler;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class h extends b<h> {
  public boolean a;
  
  public boolean b;
  
  public h() {
    a(true);
  }
  
  private static boolean a(View paramView, MotionEvent paramMotionEvent) {
    return (paramView instanceof ViewGroup && ((ViewGroup)paramView).onInterceptTouchEvent(paramMotionEvent));
  }
  
  protected final void a() {
    long l = SystemClock.uptimeMillis();
    MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
    motionEvent.setAction(3);
    this.f.onTouchEvent(motionEvent);
  }
  
  protected final void a(MotionEvent paramMotionEvent) {
    View view = this.f;
    int i = this.g;
    if (paramMotionEvent.getActionMasked() == 1) {
      view.onTouchEvent(paramMotionEvent);
      if ((i == 0 || i == 2) && view.isPressed())
        e(); 
      g();
      return;
    } 
    if (i == 0 || i == 2) {
      if (this.a) {
        a(view, paramMotionEvent);
        view.onTouchEvent(paramMotionEvent);
        e();
        return;
      } 
      if (a(view, paramMotionEvent)) {
        view.onTouchEvent(paramMotionEvent);
        e();
        return;
      } 
      if (i != 2)
        f(); 
      return;
    } 
    if (i == 4) {
      view.onTouchEvent(paramMotionEvent);
      return;
    } 
  }
  
  public final boolean b(b paramb) {
    return super.b(paramb);
  }
  
  public final boolean d(b paramb) {
    if (paramb instanceof h) {
      h h1 = (h)paramb;
      if (h1.g == 4 && h1.b)
        return false; 
    } 
    int i = this.b ^ true;
    int j = this.g;
    int k = paramb.g;
    return (j == 4 && k == 4 && i != 0) ? false : ((j == 4 && i != 0));
  }
  
  public final boolean e(b paramb) {
    return !this.b;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */