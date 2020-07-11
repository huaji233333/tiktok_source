package com.facebook.react.views.modal;

import android.content.Context;
import android.view.View;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;

@ReactModule(name = "RCTModalHostView")
public class TranslucentModalHostManager extends ReactModalHostManager {
  public LayoutShadowNode createShadowNodeInstance() {
    return new TranslucentModalHostShadowNode();
  }
  
  protected ReactModalHostView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new TranslucentModalHostView((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "RCTModalHostView";
  }
  
  public Class<? extends LayoutShadowNode> getShadowNodeClass() {
    return (Class)TranslucentModalHostShadowNode.class;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\modal\TranslucentModalHostManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */