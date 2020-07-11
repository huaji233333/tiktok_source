package com.tt.miniapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SizeDetectFrameLayout extends FrameLayout {
  private boolean mIsFirstSizeChange = true;
  
  private IWindowSizeChangeListener windowSizeListener;
  
  public SizeDetectFrameLayout(Context paramContext) {
    super(paramContext);
  }
  
  public SizeDetectFrameLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public SizeDetectFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public SizeDetectFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mIsFirstSizeChange) {
      this.mIsFirstSizeChange = false;
      return;
    } 
    IWindowSizeChangeListener iWindowSizeChangeListener = this.windowSizeListener;
    if (iWindowSizeChangeListener != null)
      iWindowSizeChangeListener.onWindowSizeChange(paramInt1, paramInt2); 
  }
  
  public void setWindowSizeListener(IWindowSizeChangeListener paramIWindowSizeChangeListener) {
    this.windowSizeListener = paramIWindowSizeChangeListener;
  }
  
  public static interface IWindowSizeChangeListener {
    void onWindowSizeChange(int param1Int1, int param1Int2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\SizeDetectFrameLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */