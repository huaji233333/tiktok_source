package com.facebook.react.modules.core;

import com.facebook.common.e.a;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.devsupport.StackTraceHelper;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.devsupport.interfaces.StackFrame;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.util.JSStackTrace;
import com.facebook.react.util.RNVersionUtils;
import org.json.JSONObject;

@ReactModule(name = "ExceptionsManager")
public class ExceptionsManagerModule extends BaseJavaModule {
  private final DevSupportManager mDevSupportManager;
  
  public ExceptionsManagerModule(DevSupportManager paramDevSupportManager) {
    this.mDevSupportManager = paramDevSupportManager;
  }
  
  private void showOrThrowError(String paramString, ReadableArray paramReadableArray, int paramInt) {
    StackFrame[] arrayOfStackFrame = StackTraceHelper.convertJsStackTrace(paramReadableArray);
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("Version", RNVersionUtils.getVersion());
      jSONObject.put("JSErrorTitle", paramString);
      jSONObject.put("JSErrorStack", StackTraceHelper.formatStackTrace(arrayOfStackFrame, false));
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    ReactBridge.uploadJSException(jSONObject);
    if (this.mDevSupportManager.getDevSupportEnabled())
      this.mDevSupportManager.showNewJSError(paramString, paramReadableArray, paramInt); 
  }
  
  @ReactMethod
  public void dismissRedbox() {
    if (this.mDevSupportManager.getDevSupportEnabled())
      this.mDevSupportManager.hideRedboxDialog(); 
  }
  
  public String getName() {
    return "ExceptionsManager";
  }
  
  @ReactMethod
  public void reportFatalException(String paramString, ReadableArray paramReadableArray, int paramInt) {
    showOrThrowError(paramString, paramReadableArray, paramInt);
  }
  
  @ReactMethod
  public void reportSoftException(String paramString, ReadableArray paramReadableArray, int paramInt) {
    if (this.mDevSupportManager.getDevSupportEnabled()) {
      this.mDevSupportManager.showNewJSError(paramString, paramReadableArray, paramInt);
      return;
    } 
    a.c("ReactNative", JSStackTrace.format(paramString, paramReadableArray));
  }
  
  @ReactMethod
  public void updateExceptionMessage(String paramString, ReadableArray paramReadableArray, int paramInt) {
    if (this.mDevSupportManager.getDevSupportEnabled())
      this.mDevSupportManager.updateJSError(paramString, paramReadableArray, paramInt); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\core\ExceptionsManagerModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */