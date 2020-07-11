package com.facebook.react.flat;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.views.modal.ReactModalHostManager;

public class RCTModalHostManager extends ReactModalHostManager {
  public LayoutShadowNode createShadowNodeInstance() {
    return new FlatReactModalShadowNode();
  }
  
  public Class<? extends LayoutShadowNode> getShadowNodeClass() {
    return (Class)FlatReactModalShadowNode.class;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTModalHostManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */