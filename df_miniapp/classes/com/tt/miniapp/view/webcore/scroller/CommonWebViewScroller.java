package com.tt.miniapp.view.webcore.scroller;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

public class CommonWebViewScroller extends WebViewScroller {
  public CommonWebViewScroller(Context paramContext) {
    super(paramContext);
  }
  
  protected int handleComputeOffset(View paramView, Rect paramRect) {
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
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\scroller\CommonWebViewScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */