package com.facebook.react.flat;

import android.view.View;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

abstract class VirtualViewManager<C extends FlatShadowNode> extends ViewManager<View, C> {
  public View createViewInstance(ThemedReactContext paramThemedReactContext) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getName());
    stringBuilder.append(" doesn't map to a View");
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public void updateExtraData(View paramView, Object paramObject) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\VirtualViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */