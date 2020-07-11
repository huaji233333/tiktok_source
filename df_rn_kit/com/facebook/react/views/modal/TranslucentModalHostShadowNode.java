package com.facebook.react.views.modal;

import android.util.DisplayMetrics;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;

public class TranslucentModalHostShadowNode extends LayoutShadowNode {
  public void addChildAt(ReactShadowNodeImpl paramReactShadowNodeImpl, int paramInt) {
    super.addChildAt(paramReactShadowNodeImpl, paramInt);
    DisplayMetrics displayMetrics = DisplayMetricsHolder.getWindowDisplayMetrics();
    paramReactShadowNodeImpl.setStyleWidth(displayMetrics.widthPixels);
    paramReactShadowNodeImpl.setStyleHeight(displayMetrics.heightPixels);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\modal\TranslucentModalHostShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */