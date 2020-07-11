package com.facebook.react.flat;

import android.view.View;
import com.facebook.react.uimanager.ReactShadowNode;

public final class RCTVirtualTextManager extends VirtualViewManager<RCTVirtualText> {
  public final RCTVirtualText createShadowNodeInstance() {
    return new RCTVirtualText();
  }
  
  public final String getName() {
    return "RCTVirtualText";
  }
  
  public final Class<RCTVirtualText> getShadowNodeClass() {
    return RCTVirtualText.class;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTVirtualTextManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */