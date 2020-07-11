package com.facebook.react.uimanager;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;

public class ReactClippingViewGroupHelper {
  private static final Rect sHelperRect = new Rect();
  
  public static void calculateClippingRect(View paramView, Rect paramRect) {
    ViewParent viewParent = paramView.getParent();
    if (viewParent == null) {
      paramRect.setEmpty();
      return;
    } 
    if (viewParent instanceof ReactClippingViewGroup) {
      ReactClippingViewGroup reactClippingViewGroup = (ReactClippingViewGroup)viewParent;
      if (reactClippingViewGroup.getRemoveClippedSubviews()) {
        reactClippingViewGroup.getClippingRect(sHelperRect);
        if (!sHelperRect.intersect(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom())) {
          paramRect.setEmpty();
          return;
        } 
        sHelperRect.offset(-paramView.getLeft(), -paramView.getTop());
        sHelperRect.offset(paramView.getScrollX(), paramView.getScrollY());
        paramRect.set(sHelperRect);
        return;
      } 
    } 
    paramView.getDrawingRect(paramRect);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ReactClippingViewGroupHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */