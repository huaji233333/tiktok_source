package com.swmansion.gesturehandler.react;

import android.os.Bundle;
import android.view.MotionEvent;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;

public final class a extends ReactRootView {
  ReactInstanceManager a;
  
  g b;
  
  public final boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    g g1 = this.b;
    return (g1 != null && g1.a(paramMotionEvent)) ? true : super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public final void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    g g1 = this.b;
    if (g1 != null)
      g1.a(paramBoolean); 
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
  
  public final void startReactApplication(ReactInstanceManager paramReactInstanceManager, String paramString, Bundle paramBundle) {
    super.startReactApplication(paramReactInstanceManager, paramString, paramBundle);
    this.a = paramReactInstanceManager;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */