package com.tt.miniapp.view.webcore.scroller;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import com.tt.miniapp.component.nativeview.InputComponent;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapphost.util.UIUtils;

public abstract class WebViewScroller {
  protected Context mContext;
  
  protected int mCurrentKeyboardHeight;
  
  protected int mLastKeyboardHeight;
  
  protected WebViewScrollerListener mListener;
  
  protected int mTotalOffset;
  
  public WebViewScroller(Context paramContext) {
    this.mContext = paramContext;
  }
  
  public int computeAndReset() {
    int i = this.mTotalOffset;
    this.mTotalOffset = 0;
    return -i;
  }
  
  public int computeOffset(View paramView, int paramInt, Rect paramRect) {
    this.mCurrentKeyboardHeight = paramInt;
    paramInt = handleComputeOffset(paramView, paramRect);
    this.mLastKeyboardHeight = this.mCurrentKeyboardHeight;
    return paramInt;
  }
  
  public int computeOffsetWhenKeyboardShow(View paramView, Rect paramRect, int paramInt) {
    int j = this.mTotalOffset;
    int i = 0;
    if (j == 0)
      return 0; 
    if (paramInt == 0) {
      paramInt = this.mLastKeyboardHeight;
      i = this.mCurrentKeyboardHeight;
    } else if (paramInt == 1) {
      paramInt = computeRangeToBottom(paramView, paramRect.bottom - paramRect.top);
      i = this.mCurrentKeyboardHeight;
    } else {
      this.mTotalOffset += i;
      return i;
    } 
    i = paramInt - i;
    this.mTotalOffset += i;
    return i;
  }
  
  int computeRangeToBottom(View paramView, int paramInt) {
    if (paramView == null)
      return 0; 
    AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)paramView.getLayoutParams();
    int[] arrayOfInt = new int[2];
    paramView.getLocationInWindow(arrayOfInt);
    int k = arrayOfInt[1];
    int j = layoutParams.height;
    int i = j;
    if (j <= 0)
      i = paramView.getMeasuredHeight(); 
    if (paramView instanceof InputComponent)
      i = ((InputComponent)paramView).getInputHeight(); 
    j = paramInt - layoutParams.y;
    if (j >= 0) {
      paramInt = j;
      if (j > i) {
        paramInt = i;
        return UIUtils.getDeviceHeight(this.mContext) - k - paramInt;
      } 
      return UIUtils.getDeviceHeight(this.mContext) - k - paramInt;
    } 
    paramInt = i;
    return UIUtils.getDeviceHeight(this.mContext) - k - paramInt;
  }
  
  protected abstract int handleComputeOffset(View paramView, Rect paramRect);
  
  void notifyKeyboardHide() {
    WebViewScrollerListener webViewScrollerListener = this.mListener;
    if (webViewScrollerListener != null)
      webViewScrollerListener.onKeyboardHide(); 
  }
  
  void notifyUpdateBottom() {
    WebViewScrollerListener webViewScrollerListener = this.mListener;
    if (webViewScrollerListener != null)
      webViewScrollerListener.onUpdateBottom(); 
  }
  
  public void setOnOffsetChangedListener(WebViewScrollerListener paramWebViewScrollerListener) {
    this.mListener = paramWebViewScrollerListener;
  }
  
  public void updateOffset(int paramInt) {
    this.mTotalOffset += paramInt;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\scroller\WebViewScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */