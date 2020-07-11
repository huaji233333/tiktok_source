package com.facebook.react.devsupport;

import com.facebook.react.bridge.DefaultNativeModuleCallExceptionHandler;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.devsupport.interfaces.DevOptionHandler;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.devsupport.interfaces.ErrorCustomizer;
import com.facebook.react.devsupport.interfaces.PackagerStatusCallback;
import com.facebook.react.devsupport.interfaces.StackFrame;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import com.facebook.react.util.RNVersionUtils;
import java.io.File;
import org.json.JSONObject;

public class DisabledDevSupportManager implements DevSupportManager {
  private final DefaultNativeModuleCallExceptionHandler mDefaultNativeModuleCallExceptionHandler = new DefaultNativeModuleCallExceptionHandler();
  
  public void addCustomDevOption(String paramString, DevOptionHandler paramDevOptionHandler) {}
  
  public void deleteJSBundleFile() {}
  
  public File downloadBundleResourceFromUrlSync(String paramString, File paramFile) {
    return null;
  }
  
  public DeveloperSettings getDevSettings() {
    return null;
  }
  
  public boolean getDevSupportEnabled() {
    return false;
  }
  
  public String getDownloadedJSBundleFile() {
    return null;
  }
  
  public String getJSBundleURLForRemoteDebugging() {
    return null;
  }
  
  public StackFrame[] getLastErrorStack() {
    return null;
  }
  
  public String getLastErrorTitle() {
    return null;
  }
  
  public String getSourceMapUrl() {
    return null;
  }
  
  public String getSourceUrl() {
    return null;
  }
  
  public void handleException(Exception paramException) {
    if (!(paramException instanceof JSException)) {
      StringBuilder stringBuilder = new StringBuilder(paramException.getMessage());
      for (Throwable throwable = paramException.getCause(); throwable != null; throwable = throwable.getCause()) {
        stringBuilder.append("\n\n");
        stringBuilder.append(throwable.getMessage());
      } 
      String str = stringBuilder.toString();
      StackFrame[] arrayOfStackFrame = StackTraceHelper.convertJavaStackTrace(paramException);
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("Version", RNVersionUtils.getVersion());
        jSONObject.put("NativeErrorTitle", str);
        jSONObject.put("NativeErrorStack", StackTraceHelper.formatStackTrace(arrayOfStackFrame, false));
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
      ReactBridge.uploadJSException(jSONObject);
    } 
    this.mDefaultNativeModuleCallExceptionHandler.handleException(paramException);
  }
  
  public void handleReloadJS() {}
  
  public boolean hasUpToDateJSBundleInCache() {
    return false;
  }
  
  public void hideRedboxDialog() {}
  
  public void isPackagerRunning(PackagerStatusCallback paramPackagerStatusCallback) {}
  
  public void onNewReactContextCreated(ReactContext paramReactContext) {}
  
  public void onReactInstanceDestroyed(ReactContext paramReactContext) {}
  
  public void registerErrorCustomizer(ErrorCustomizer paramErrorCustomizer) {}
  
  public void reloadJSFromServer(String paramString) {}
  
  public void reloadSettings() {}
  
  public void setDevSupportEnabled(boolean paramBoolean) {}
  
  public void showDevOptionsDialog() {}
  
  public void showNewJSError(String paramString, ReadableArray paramReadableArray, int paramInt) {}
  
  public void showNewJavaError(String paramString, Throwable paramThrowable) {}
  
  public void startInspector() {}
  
  public void stopInspector() {}
  
  public void updateJSError(String paramString, ReadableArray paramReadableArray, int paramInt) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\DisabledDevSupportManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */