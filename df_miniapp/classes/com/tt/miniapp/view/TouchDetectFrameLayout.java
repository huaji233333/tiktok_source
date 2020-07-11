package com.tt.miniapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.tt.miniapp.share.AutoShareInterceptor;
import com.tt.miniapphost.AppBrandLogger;

public class TouchDetectFrameLayout extends FrameLayout {
  public TouchDetectFrameLayout(Context paramContext) {
    this(paramContext, null);
  }
  
  public TouchDetectFrameLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public TouchDetectFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    if (paramMotionEvent.getAction() == 1) {
      AppBrandLogger.d("TouchDetectFrameLayout", new Object[] { "click event" });
      AutoShareInterceptor.mLastClickTime = System.currentTimeMillis();
    } 
    return super.dispatchTouchEvent(paramMotionEvent);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\TouchDetectFrameLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */