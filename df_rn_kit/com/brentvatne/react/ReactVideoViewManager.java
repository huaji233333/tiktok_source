package com.brentvatne.react;

import android.view.View;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.yqritc.scalablevideoview.b;
import java.util.Map;

public class ReactVideoViewManager extends SimpleViewManager<b> {
  protected b createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new b(paramThemedReactContext);
  }
  
  public Map getExportedCustomDirectEventTypeConstants() {
    MapBuilder.Builder builder = MapBuilder.builder();
    for (b.a a : b.a.values())
      builder.put(a.toString(), MapBuilder.of("registrationName", a.toString())); 
    return builder.build();
  }
  
  public Map getExportedViewConstants() {
    return MapBuilder.of("ScaleNone", Integer.toString(b.LEFT_TOP.ordinal()), "ScaleToFill", Integer.toString(b.FIT_XY.ordinal()), "ScaleAspectFit", Integer.toString(b.FIT_CENTER.ordinal()), "ScaleAspectFill", Integer.toString(b.CENTER_CROP.ordinal()));
  }
  
  public String getName() {
    return "RCTVideo";
  }
  
  public void onDropViewInstance(b paramb) {
    super.onDropViewInstance((View)paramb);
    if (paramb.d != null)
      paramb.d.hide(); 
    if (paramb.m != null) {
      paramb.i = false;
      paramb.a();
    } 
    if (paramb.h)
      paramb.setFullscreen(false); 
  }
  
  @ReactProp(defaultBoolean = false, name = "controls")
  public void setControls(b paramb, boolean paramBoolean) {
    paramb.setControls(paramBoolean);
  }
  
  @ReactProp(defaultBoolean = false, name = "fullscreen")
  public void setFullscreen(b paramb, boolean paramBoolean) {
    paramb.setFullscreen(paramBoolean);
  }
  
  @ReactProp(defaultBoolean = false, name = "muted")
  public void setMuted(b paramb, boolean paramBoolean) {
    paramb.setMutedModifier(paramBoolean);
  }
  
  @ReactProp(defaultBoolean = false, name = "paused")
  public void setPaused(b paramb, boolean paramBoolean) {
    paramb.setPausedModifier(paramBoolean);
  }
  
  @ReactProp(defaultBoolean = false, name = "playInBackground")
  public void setPlayInBackground(b paramb, boolean paramBoolean) {
    paramb.setPlayInBackground(paramBoolean);
  }
  
  @ReactProp(defaultFloat = 250.0F, name = "progressUpdateInterval")
  public void setProgressUpdateInterval(b paramb, float paramFloat) {
    paramb.setProgressUpdateInterval(paramFloat);
  }
  
  @ReactProp(name = "rate")
  public void setRate(b paramb, float paramFloat) {
    paramb.setRateModifier(paramFloat);
  }
  
  @ReactProp(defaultBoolean = false, name = "repeat")
  public void setRepeat(b paramb, boolean paramBoolean) {
    paramb.setRepeatModifier(paramBoolean);
  }
  
  @ReactProp(name = "resizeMode")
  public void setResizeMode(b paramb, String paramString) {
    paramb.setResizeModeModifier(b.values()[Integer.parseInt(paramString)]);
  }
  
  @ReactProp(name = "seek")
  public void setSeek(b paramb, float paramFloat) {
    paramb.seekTo(Math.round(paramFloat * 1000.0F));
  }
  
  @ReactProp(name = "src")
  public void setSrc(b paramb, ReadableMap paramReadableMap) {
    int i = paramReadableMap.getInt("mainVer");
    int j = paramReadableMap.getInt("patchVer");
    if (i < 0)
      i = 0; 
    if (j < 0)
      j = 0; 
    if (i > 0) {
      paramb.a(paramReadableMap.getString("uri"), paramReadableMap.getString("type"), paramReadableMap.getBoolean("isNetwork"), paramReadableMap.getBoolean("isAsset"), paramReadableMap.getMap("requestHeaders"), i, j);
      return;
    } 
    paramb.a(paramReadableMap.getString("uri"), paramReadableMap.getString("type"), paramReadableMap.getBoolean("isNetwork"), paramReadableMap.getBoolean("isAsset"), paramReadableMap.getMap("requestHeaders"));
  }
  
  @ReactProp(name = "stereoPan")
  public void setStereoPan(b paramb, float paramFloat) {
    paramb.setStereoPan(paramFloat);
  }
  
  @ReactProp(defaultFloat = 1.0F, name = "volume")
  public void setVolume(b paramb, float paramFloat) {
    paramb.setVolumeModifier(paramFloat);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\brentvatne\react\ReactVideoViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */