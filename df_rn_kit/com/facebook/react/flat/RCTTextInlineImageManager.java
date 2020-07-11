package com.facebook.react.flat;

import android.view.View;
import com.facebook.react.uimanager.ReactShadowNode;

public final class RCTTextInlineImageManager extends VirtualViewManager<RCTTextInlineImage> {
  public final RCTTextInlineImage createShadowNodeInstance() {
    return new RCTTextInlineImage();
  }
  
  public final String getName() {
    return "RCTTextInlineImage";
  }
  
  public final Class<RCTTextInlineImage> getShadowNodeClass() {
    return RCTTextInlineImage.class;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTTextInlineImageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */