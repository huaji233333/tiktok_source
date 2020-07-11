package com.facebook.react.flat;

import android.view.View;
import com.facebook.react.uimanager.ReactShadowNode;

public final class RCTRawTextManager extends VirtualViewManager<RCTRawText> {
  public final RCTRawText createShadowNodeInstance() {
    return new RCTRawText();
  }
  
  public final String getName() {
    return "RCTRawText";
  }
  
  public final Class<RCTRawText> getShadowNodeClass() {
    return RCTRawText.class;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTRawTextManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */