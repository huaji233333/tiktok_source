package com.swmansion.gesturehandler.react;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.views.view.ReactViewGroup;

public final class h extends ReactViewGroup {
  g a;
  
  public h(Context paramContext) {
    super(paramContext);
  }
  
  public final boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    return ((g)a.b(this.a)).a(paramMotionEvent) ? true : super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public final void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.a == null)
      this.a = new g((ReactContext)getContext(), (ViewGroup)this); 
  }
  
  public final void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    ((g)a.b(this.a)).a(paramBoolean);
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */