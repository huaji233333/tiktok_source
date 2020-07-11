package com.facebook.react.uimanager;

import android.view.View;

public abstract class SimpleViewManager<T extends View> extends BaseViewManager<T, LayoutShadowNode> {
  public LayoutShadowNode createShadowNodeInstance() {
    return new LayoutShadowNode();
  }
  
  public Class<LayoutShadowNode> getShadowNodeClass() {
    return LayoutShadowNode.class;
  }
  
  public void updateExtraData(T paramT, Object paramObject) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\SimpleViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */