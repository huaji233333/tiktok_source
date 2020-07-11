package com.tt.miniapp.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SwipeLoadMoreFooterLayout extends FrameLayout implements SwipeLoadMoreTrigger, SwipeTrigger {
  public SwipeLoadMoreFooterLayout(Context paramContext) {
    this(paramContext, null);
  }
  
  public SwipeLoadMoreFooterLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SwipeLoadMoreFooterLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void onFingerMove(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {}
  
  public void onFingerRelease() {}
  
  public void onLoadMore() {}
  
  public void onPageRefresh() {}
  
  public void onRefreshComplete() {}
  
  public void onReset() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\refresh\SwipeLoadMoreFooterLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */