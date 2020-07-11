package com.swmansion.gesturehandler;

import android.view.MotionEvent;

public class n extends b<n> {
  public m a;
  
  public double b;
  
  public double u;
  
  private m.a v = new m.a(this) {
      public final boolean a(m param1m) {
        double d = this.a.b;
        n n1 = this.a;
        n1.b += param1m.d;
        long l = param1m.a - param1m.b;
        if (l > 0L) {
          n n2 = this.a;
          double d1 = n2.b;
          double d2 = l;
          Double.isNaN(d2);
          n2.u = (d1 - d) / d2;
        } 
        if (Math.abs(this.a.b) >= 0.08726646259971647D && this.a.g == 2)
          this.a.e(); 
        return true;
      }
      
      public final void b(m param1m) {
        this.a.g();
      }
    };
  
  public n() {
    a(false);
  }
  
  protected final void a(MotionEvent paramMotionEvent) {
    int i = this.g;
    if (i == 0) {
      this.u = 0.0D;
      this.b = 0.0D;
      this.a = new m(this.v);
      f();
    } 
    m m1 = this.a;
    if (m1 != null) {
      m.a a1;
      int j = paramMotionEvent.getActionMasked();
      if (j != 0) {
        if (j != 1) {
          if (j != 2) {
            if (j != 5) {
              if (j == 6 && m1.g) {
                j = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
                if (j == m1.h[0] || j == m1.h[1])
                  m1.a(); 
              } 
            } else if (!m1.g) {
              m1.h[1] = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
              m1.g = true;
              m1.b = paramMotionEvent.getEventTime();
              m1.c = Double.NaN;
              m1.a(paramMotionEvent);
              a1 = m1.i;
            } 
          } else if (((m)a1).g) {
            a1.a(paramMotionEvent);
            if (((m)a1).i != null)
              ((m)a1).i.a((m)a1); 
          } 
        } else {
          a1.a();
        } 
      } else {
        ((m)a1).g = false;
        ((m)a1).h[0] = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
        ((m)a1).h[1] = -1;
      } 
    } 
    if (paramMotionEvent.getActionMasked() == 1) {
      if (i == 4) {
        g();
        return;
      } 
      d();
    } 
  }
  
  protected final void b() {
    this.a = null;
    this.u = 0.0D;
    this.b = 0.0D;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */