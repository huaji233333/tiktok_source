package com.facebook.react.modules.core;

import android.net.Uri;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "DeviceEventManager")
public class DeviceEventManagerModule extends ReactContextBaseJavaModule {
  private final Runnable mInvokeDefaultBackPressRunnable = new Runnable() {
      public void run() {
        UiThreadUtil.assertOnUiThread();
        backBtnHandler.invokeDefaultOnBackPressed();
      }
    };
  
  public DeviceEventManagerModule(ReactApplicationContext paramReactApplicationContext, final DefaultHardwareBackBtnHandler backBtnHandler) {
    super(paramReactApplicationContext);
  }
  
  public void emitHardwareBackPressed() {
    ((RCTDeviceEventEmitter)getReactApplicationContext().getJSModule(RCTDeviceEventEmitter.class)).emit("hardwareBackPress", null);
  }
  
  public void emitNewIntentReceived(Uri paramUri) {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putString("url", paramUri.toString());
    ((RCTDeviceEventEmitter)getReactApplicationContext().getJSModule(RCTDeviceEventEmitter.class)).emit("url", writableMap);
  }
  
  public String getName() {
    return "DeviceEventManager";
  }
  
  @ReactMethod
  public void invokeDefaultBackPressHandler() {
    getReactApplicationContext().runOnUiQueueThread(this.mInvokeDefaultBackPressRunnable);
  }
  
  public static interface RCTDeviceEventEmitter extends JavaScriptModule {
    void emit(String param1String, Object param1Object);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\core\DeviceEventManagerModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */