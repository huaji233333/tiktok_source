package com.facebook.react.touch;

import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;

public class JSResponderHandler implements OnInterceptTouchEventListener {
  private volatile int mCurrentJSResponder = -1;
  
  private ViewParent mViewParentBlockingNativeResponder;
  
  private void maybeUnblockNativeResponder() {
    ViewParent viewParent = this.mViewParentBlockingNativeResponder;
    if (viewParent != null) {
      viewParent.requestDisallowInterceptTouchEvent(false);
      this.mViewParentBlockingNativeResponder = null;
    } 
  }
  
  public void clearJSResponder() {
    this.mCurrentJSResponder = -1;
    maybeUnblockNativeResponder();
  }
  
  public boolean onInterceptTouchEvent(ViewGroup paramViewGroup, MotionEvent paramMotionEvent) {
    int i = this.mCurrentJSResponder;
    return (i != -1 && paramMotionEvent.getAction() != 1 && paramViewGroup.getId() == i);
  }
  
  public void setJSResponder(int paramInt, ViewParent paramViewParent) {
    this.mCurrentJSResponder = paramInt;
    maybeUnblockNativeResponder();
    if (paramViewParent != null) {
      paramViewParent.requestDisallowInterceptTouchEvent(true);
      this.mViewParentBlockingNativeResponder = paramViewParent;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\touch\JSResponderHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */