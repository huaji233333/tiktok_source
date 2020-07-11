package com.facebook.react.views.text;

import android.view.View;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManager;

@ReactModule(name = "RCTRawText")
public class ReactRawTextManager extends ViewManager<View, ReactRawTextShadowNode> {
  public ReactRawTextShadowNode createShadowNodeInstance() {
    return new ReactRawTextShadowNode();
  }
  
  public ReactTextView createViewInstance(ThemedReactContext paramThemedReactContext) {
    throw new IllegalStateException("Attempt to create a native view for RCTRawText");
  }
  
  public String getName() {
    return "RCTRawText";
  }
  
  public Class<ReactRawTextShadowNode> getShadowNodeClass() {
    return ReactRawTextShadowNode.class;
  }
  
  public void updateExtraData(View paramView, Object paramObject) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactRawTextManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */