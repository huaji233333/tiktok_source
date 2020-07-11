package com.facebook.react.flat;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.views.textinput.ReactTextInputManager;

public class RCTTextInputManager extends ReactTextInputManager {
  public RCTTextInput createShadowNodeInstance() {
    return new RCTTextInput();
  }
  
  public Class<RCTTextInput> getShadowNodeClass() {
    return RCTTextInput.class;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTTextInputManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */