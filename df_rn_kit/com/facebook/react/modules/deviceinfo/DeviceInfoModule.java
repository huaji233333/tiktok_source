package com.facebook.react.modules.deviceinfo;

import android.content.Context;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import java.util.HashMap;
import java.util.Map;

@ReactModule(name = "DeviceInfo")
public class DeviceInfoModule extends BaseJavaModule implements LifecycleEventListener {
  private float mFontScale;
  
  private ReactApplicationContext mReactApplicationContext;
  
  public DeviceInfoModule(Context paramContext) {
    DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(paramContext);
    this.mFontScale = (paramContext.getResources().getConfiguration()).fontScale;
  }
  
  public DeviceInfoModule(ReactApplicationContext paramReactApplicationContext) {
    this((Context)paramReactApplicationContext);
    this.mReactApplicationContext = paramReactApplicationContext;
    this.mReactApplicationContext.addLifecycleEventListener(this);
  }
  
  public void emitUpdateDimensionsEvent() {
    ReactApplicationContext reactApplicationContext = this.mReactApplicationContext;
    if (reactApplicationContext == null)
      return; 
    ((DeviceEventManagerModule.RCTDeviceEventEmitter)reactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("didUpdateDimensions", DisplayMetricsHolder.getDisplayMetricsMap(this.mFontScale));
  }
  
  public Map<String, Object> getConstants() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("Dimensions", DisplayMetricsHolder.getDisplayMetricsMap(this.mFontScale));
    return (Map)hashMap;
  }
  
  public String getName() {
    return "DeviceInfo";
  }
  
  public void onHostDestroy() {}
  
  public void onHostPause() {}
  
  public void onHostResume() {
    ReactApplicationContext reactApplicationContext = this.mReactApplicationContext;
    if (reactApplicationContext == null)
      return; 
    float f = (reactApplicationContext.getResources().getConfiguration()).fontScale;
    if (this.mFontScale != f) {
      this.mFontScale = f;
      emitUpdateDimensionsEvent();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\deviceinfo\DeviceInfoModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */