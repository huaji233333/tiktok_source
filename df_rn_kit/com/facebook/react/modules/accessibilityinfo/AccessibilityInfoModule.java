package com.facebook.react.modules.accessibilityinfo;

import android.os.Build;
import android.view.accessibility.AccessibilityManager;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = "AccessibilityInfo")
public class AccessibilityInfoModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
  private AccessibilityManager mAccessibilityManager;
  
  private boolean mEnabled;
  
  private ReactTouchExplorationStateChangeListener mTouchExplorationStateChangeListener;
  
  public AccessibilityInfoModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
    this.mAccessibilityManager = (AccessibilityManager)paramReactApplicationContext.getApplicationContext().getSystemService("accessibility");
    this.mEnabled = this.mAccessibilityManager.isTouchExplorationEnabled();
    if (Build.VERSION.SDK_INT >= 19)
      this.mTouchExplorationStateChangeListener = new ReactTouchExplorationStateChangeListener(); 
  }
  
  public String getName() {
    return "AccessibilityInfo";
  }
  
  public void initialize() {
    getReactApplicationContext().addLifecycleEventListener(this);
    updateAndSendChangeEvent(this.mAccessibilityManager.isTouchExplorationEnabled());
  }
  
  @ReactMethod
  public void isTouchExplorationEnabled(Callback paramCallback) {
    paramCallback.invoke(new Object[] { Boolean.valueOf(this.mEnabled) });
  }
  
  public void onCatalystInstanceDestroy() {
    super.onCatalystInstanceDestroy();
    getReactApplicationContext().removeLifecycleEventListener(this);
  }
  
  public void onHostDestroy() {}
  
  public void onHostPause() {
    if (Build.VERSION.SDK_INT >= 19)
      this.mAccessibilityManager.removeTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener); 
  }
  
  public void onHostResume() {
    if (Build.VERSION.SDK_INT >= 19)
      this.mAccessibilityManager.addTouchExplorationStateChangeListener(this.mTouchExplorationStateChangeListener); 
    updateAndSendChangeEvent(this.mAccessibilityManager.isTouchExplorationEnabled());
  }
  
  public void updateAndSendChangeEvent(boolean paramBoolean) {
    if (this.mEnabled != paramBoolean) {
      this.mEnabled = paramBoolean;
      ((DeviceEventManagerModule.RCTDeviceEventEmitter)getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("touchExplorationDidChange", Boolean.valueOf(this.mEnabled));
    } 
  }
  
  class ReactTouchExplorationStateChangeListener implements AccessibilityManager.TouchExplorationStateChangeListener {
    private ReactTouchExplorationStateChangeListener() {}
    
    public void onTouchExplorationStateChanged(boolean param1Boolean) {
      AccessibilityInfoModule.this.updateAndSendChangeEvent(param1Boolean);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\accessibilityinfo\AccessibilityInfoModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */