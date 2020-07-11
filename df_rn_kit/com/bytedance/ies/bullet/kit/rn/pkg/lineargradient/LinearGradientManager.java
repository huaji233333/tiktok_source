package com.bytedance.ies.bullet.kit.rn.pkg.lineargradient;

import android.content.Context;
import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class LinearGradientManager extends SimpleViewManager<b> {
  protected b createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new b((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "BVLinearGradient";
  }
  
  @ReactProp(name = "borderRadii")
  public void setBorderRadii(b paramb, ReadableArray paramReadableArray) {
    paramb.setBorderRadii(paramReadableArray);
  }
  
  @ReactProp(name = "colors")
  public void setColors(b paramb, ReadableArray paramReadableArray) {
    paramb.setColors(paramReadableArray);
  }
  
  @ReactProp(name = "endPoint")
  public void setEndPosition(b paramb, ReadableArray paramReadableArray) {
    paramb.setEndPosition(paramReadableArray);
  }
  
  @ReactProp(name = "locations")
  public void setLocations(b paramb, ReadableArray paramReadableArray) {
    if (paramReadableArray != null)
      paramb.setLocations(paramReadableArray); 
  }
  
  @ReactProp(name = "startPoint")
  public void setStartPosition(b paramb, ReadableArray paramReadableArray) {
    paramb.setStartPosition(paramReadableArray);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\lineargradient\LinearGradientManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */