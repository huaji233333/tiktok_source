package com.swmansion.gesturehandler.react;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.swmansion.gesturehandler.b;
import com.swmansion.gesturehandler.d;

public final class g {
  public final ReactRootView a;
  
  public boolean b;
  
  private final ReactContext c;
  
  private final d d;
  
  private final b e;
  
  private boolean f;
  
  public g(ReactContext paramReactContext, ViewGroup paramViewGroup) {
    UiThreadUtil.assertOnUiThread();
    int i = paramViewGroup.getId();
    if (i > 0) {
      RNGestureHandlerModule rNGestureHandlerModule = (RNGestureHandlerModule)paramReactContext.getNativeModule(RNGestureHandlerModule.class);
      f f = rNGestureHandlerModule.getRegistry();
      this.a = a(paramViewGroup);
      this.c = paramReactContext;
      this.d = new d(paramViewGroup, f, new j());
      this.d.f = 0.1F;
      this.e = new a();
      b b1 = this.e;
      b1.e = -i;
      f.a(b1);
      f.a(this.e.e, i);
      rNGestureHandlerModule.registerRootHelper(this);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Expect view tag to be set for ");
    stringBuilder.append(paramViewGroup);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  private static ReactRootView a(ViewGroup paramViewGroup) {
    ViewParent viewParent;
    UiThreadUtil.assertOnUiThread();
    ViewGroup viewGroup = paramViewGroup;
    while (viewGroup != null && !(viewGroup instanceof ReactRootView))
      viewParent = viewGroup.getParent(); 
    if (viewParent != null)
      return (ReactRootView)viewParent; 
    StringBuilder stringBuilder = new StringBuilder("View ");
    stringBuilder.append(paramViewGroup);
    stringBuilder.append(" has not been mounted under ReactRootView");
    IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
    throw illegalStateException;
  }
  
  public final void a() {
    RNGestureHandlerModule rNGestureHandlerModule = (RNGestureHandlerModule)this.c.getNativeModule(RNGestureHandlerModule.class);
    rNGestureHandlerModule.getRegistry().b(this.e.e);
    rNGestureHandlerModule.unregisterRootHelper(this);
  }
  
  public final void a(boolean paramBoolean) {
    if (this.d != null && !this.f)
      b(); 
  }
  
  public final boolean a(MotionEvent paramMotionEvent) {
    this.f = true;
    this.d.a(paramMotionEvent);
    this.f = false;
    return this.b;
  }
  
  public final void b() {
    b b1 = this.e;
    if (b1 != null && b1.g == 2) {
      this.e.e();
      this.e.g();
    } 
  }
  
  final class a extends b {
    private a(g this$0) {}
    
    public final void a() {
      this.a.b = true;
      long l = SystemClock.uptimeMillis();
      MotionEvent motionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
      motionEvent.setAction(3);
      this.a.a.onChildStartedNativeGesture(motionEvent);
    }
    
    public final void a(MotionEvent param1MotionEvent) {
      if (this.g == 0) {
        f();
        this.a.b = false;
      } 
      if (param1MotionEvent.getActionMasked() == 1)
        g(); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */