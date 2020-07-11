package com.facebook.react.uimanager.events;

import android.view.MotionEvent;
import android.view.View;
import com.facebook.react.uimanager.RootViewUtil;

public class NativeGestureUtil {
  public static void notifyNativeGestureStarted(View paramView, MotionEvent paramMotionEvent) {
    RootViewUtil.getRootView(paramView).onChildStartedNativeGesture(paramMotionEvent);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\events\NativeGestureUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */