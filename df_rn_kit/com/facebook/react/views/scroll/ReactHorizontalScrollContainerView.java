package com.facebook.react.views.scroll;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class ReactHorizontalScrollContainerView extends ViewGroup {
  private int mLayoutDirection;
  
  public ReactHorizontalScrollContainerView(Context paramContext) {}
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mLayoutDirection == 1) {
      setLeft(0);
      setRight(paramInt3 - paramInt1 + 0);
      paramInt1 = computeHorizontalScrollRange();
      paramInt2 = getScrollX();
      HorizontalScrollView horizontalScrollView = (HorizontalScrollView)getParent();
      horizontalScrollView.scrollTo(paramInt1 - paramInt2, horizontalScrollView.getScrollY());
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\ReactHorizontalScrollContainerView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */