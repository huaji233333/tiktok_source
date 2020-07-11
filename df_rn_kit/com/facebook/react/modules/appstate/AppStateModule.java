package com.facebook.react.modules.appstate;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = "AppState")
public class AppStateModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
  private String mAppState = "uninitialized";
  
  public AppStateModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private WritableMap createAppStateEventMap() {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putString("app_state", this.mAppState);
    return writableMap;
  }
  
  private void sendAppStateChangeEvent() {
    ((DeviceEventManagerModule.RCTDeviceEventEmitter)getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("appStateDidChange", createAppStateEventMap());
  }
  
  @ReactMethod
  public void getCurrentAppState(Callback paramCallback1, Callback paramCallback2) {
    paramCallback1.invoke(new Object[] { createAppStateEventMap() });
  }
  
  public String getName() {
    return "AppState";
  }
  
  public void initialize() {
    getReactApplicationContext().addLifecycleEventListener(this);
  }
  
  public void onHostDestroy() {}
  
  public void onHostPause() {
    this.mAppState = "background";
    sendAppStateChangeEvent();
  }
  
  public void onHostResume() {
    this.mAppState = "active";
    sendAppStateChangeEvent();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\appstate\AppStateModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */