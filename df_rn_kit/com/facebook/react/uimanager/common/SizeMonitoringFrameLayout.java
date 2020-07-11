package com.facebook.react.uimanager.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SizeMonitoringFrameLayout extends FrameLayout {
  private OnSizeChangedListener mOnSizeChangedListener;
  
  public SizeMonitoringFrameLayout(Context paramContext) {
    super(paramContext);
  }
  
  public SizeMonitoringFrameLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public SizeMonitoringFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    OnSizeChangedListener onSizeChangedListener = this.mOnSizeChangedListener;
    if (onSizeChangedListener != null)
      onSizeChangedListener.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4); 
  }
  
  public void setOnSizeChangedListener(OnSizeChangedListener paramOnSizeChangedListener) {
    this.mOnSizeChangedListener = paramOnSizeChangedListener;
  }
  
  public static interface OnSizeChangedListener {
    void onSizeChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\common\SizeMonitoringFrameLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */