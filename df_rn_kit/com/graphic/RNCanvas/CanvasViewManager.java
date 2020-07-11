package com.graphic.RNCanvas;

import android.content.Context;
import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import java.util.HashMap;
import java.util.Map;

public class CanvasViewManager extends SimpleViewManager {
  private static final HashMap<String, CanvasTextureView> canvasViews = new HashMap<String, CanvasTextureView>();
  
  public static CanvasTextureView getCanvasView(String paramString) {
    return canvasViews.get(paramString);
  }
  
  public static void removeCanvasView(String paramString) {
    canvasViews.remove(paramString);
  }
  
  private static void setCanvasView(String paramString, CanvasTextureView paramCanvasTextureView) {
    canvasViews.put(paramString, paramCanvasTextureView);
  }
  
  public CanvasTextureView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new CanvasTextureView((Context)paramThemedReactContext);
  }
  
  public Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of("onReady", MapBuilder.of("registrationName", "onReady"));
  }
  
  public String getName() {
    return "CanvasView";
  }
  
  @ReactProp(name = "actions")
  public void setActions(CanvasTextureView paramCanvasTextureView, ReadableArray paramReadableArray) {
    if (paramReadableArray.size() == 0)
      return; 
    paramCanvasTextureView.setActions(CanvasConvert.convertActions(paramReadableArray));
    paramCanvasTextureView.drawOutput();
  }
  
  @ReactProp(customType = "Color", name = "backgroundColor")
  public void setBackgroundColor(CanvasTextureView paramCanvasTextureView, Integer paramInteger) {
    paramCanvasTextureView.setBackgroundColor(paramInteger);
  }
  
  @ReactProp(name = "nativeID")
  public void setNativeID(CanvasTextureView paramCanvasTextureView, String paramString) {
    if (getCanvasView(paramString) == null)
      setCanvasView(paramString, paramCanvasTextureView); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */