package com.facebook.react.views.text;

import android.view.View;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;

@ReactModule(name = "RCTVirtualText")
public class ReactVirtualTextViewManager extends BaseViewManager<View, ReactVirtualTextShadowNode> {
  public ReactVirtualTextShadowNode createShadowNodeInstance() {
    return new ReactVirtualTextShadowNode();
  }
  
  public View createViewInstance(ThemedReactContext paramThemedReactContext) {
    throw new IllegalStateException("Attempt to create a native view for RCTVirtualText");
  }
  
  public String getName() {
    return "RCTVirtualText";
  }
  
  public Class<ReactVirtualTextShadowNode> getShadowNodeClass() {
    return ReactVirtualTextShadowNode.class;
  }
  
  public void updateExtraData(View paramView, Object paramObject) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactVirtualTextViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */