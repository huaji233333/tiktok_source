package com.facebook.react.devsupport.interfaces;

import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import java.io.File;

public interface DevSupportManager extends NativeModuleCallExceptionHandler {
  void addCustomDevOption(String paramString, DevOptionHandler paramDevOptionHandler);
  
  void deleteJSBundleFile();
  
  File downloadBundleResourceFromUrlSync(String paramString, File paramFile);
  
  DeveloperSettings getDevSettings();
  
  boolean getDevSupportEnabled();
  
  String getDownloadedJSBundleFile();
  
  String getJSBundleURLForRemoteDebugging();
  
  StackFrame[] getLastErrorStack();
  
  String getLastErrorTitle();
  
  String getSourceMapUrl();
  
  String getSourceUrl();
  
  void handleReloadJS();
  
  boolean hasUpToDateJSBundleInCache();
  
  void hideRedboxDialog();
  
  void isPackagerRunning(PackagerStatusCallback paramPackagerStatusCallback);
  
  void onNewReactContextCreated(ReactContext paramReactContext);
  
  void onReactInstanceDestroyed(ReactContext paramReactContext);
  
  void registerErrorCustomizer(ErrorCustomizer paramErrorCustomizer);
  
  void reloadJSFromServer(String paramString);
  
  void reloadSettings();
  
  void setDevSupportEnabled(boolean paramBoolean);
  
  void showDevOptionsDialog();
  
  void showNewJSError(String paramString, ReadableArray paramReadableArray, int paramInt);
  
  void showNewJavaError(String paramString, Throwable paramThrowable);
  
  void startInspector();
  
  void stopInspector();
  
  void updateJSError(String paramString, ReadableArray paramReadableArray, int paramInt);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\interfaces\DevSupportManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */