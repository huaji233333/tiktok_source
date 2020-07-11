package com.tt.miniapp.view.webcore.scroller;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import com.tt.miniapphost.AppBrandLogger;

public class VivoConcaveWebViewScroller extends WebViewScroller {
  public VivoConcaveWebViewScroller(Context paramContext) {
    super(paramContext);
  }
  
  private int offset(View paramView, Rect paramRect) {
    notifyUpdateBottom();
    int j = this.mTotalOffset;
    int i = 0;
    if (j != 0)
      return computeOffsetWhenKeyboardShow(paramView, paramRect, 0); 
    j = computeRangeToBottom(paramView, paramRect.bottom - paramRect.top);
    if (j < this.mCurrentKeyboardHeight) {
      i = j - this.mCurrentKeyboardHeight;
      this.mTotalOffset += i;
    } 
    return i;
  }
  
  protected int handleComputeOffset(View paramView, Rect paramRect) {
    if (this.mCurrentKeyboardHeight == 120) {
      this.mCurrentKeyboardHeight += 720;
      AppBrandLogger.d("VivoConcaveWebViewScroller", new Object[] { "rectHeight", Integer.valueOf(paramRect.bottom - paramRect.top) });
      if (paramRect.bottom != 2208) {
        notifyKeyboardHide();
      } else {
        return offset(paramView, paramRect);
      } 
    } 
    return offset(paramView, paramRect);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\scroller\VivoConcaveWebViewScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */