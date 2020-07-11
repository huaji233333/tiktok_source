package com.facebook.react.modules.jsruntask;

import android.content.Context;
import android.content.res.AssetManager;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.CatalystInstanceImpl;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import java.io.File;
import java.lang.reflect.Method;

@ReactModule(name = "JSRunTask")
public final class JSRunTaskModule extends ReactContextBaseJavaModule {
  public JSRunTaskModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private boolean loadScriptFromAsset(Context paramContext, CatalystInstance paramCatalystInstance, String paramString) {
    try {
      Method method = CatalystInstanceImpl.class.getDeclaredMethod("loadScriptFromAssets", new Class[] { AssetManager.class, String.class, boolean.class });
      method.setAccessible(true);
      method.invoke(paramCatalystInstance, new Object[] { paramContext.getAssets(), paramString, Boolean.valueOf(false) });
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  private boolean loadScriptFromFile(CatalystInstance paramCatalystInstance, String paramString) {
    try {
      Method method = CatalystInstanceImpl.class.getDeclaredMethod("loadScriptFromFile", new Class[] { String.class, String.class, boolean.class });
      method.setAccessible(true);
      method.invoke(paramCatalystInstance, new Object[] { paramString, paramString, Boolean.valueOf(false) });
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  public final String getName() {
    return "JSRunTask";
  }
  
  @ReactMethod
  public final void runJs(String paramString) {
    if (paramString != null) {
      CatalystInstance catalystInstance = getReactApplicationContext().getCatalystInstance();
      if (catalystInstance != null)
        catalystInstance.loadJavaScript(catalystInstance.getSourceURL(), paramString, false); 
    } 
  }
  
  @ReactMethod
  public final void runJsFromAssets(String paramString) {
    if (paramString != null && paramString.startsWith("assets"))
      loadScriptFromAsset(getReactApplicationContext().getApplicationContext(), getReactApplicationContext().getCatalystInstance(), paramString); 
  }
  
  @ReactMethod
  public final void runJsFromFile(String paramString) {
    if (paramString != null)
      try {
        if (paramString.startsWith("/")) {
          if ((new File(paramString)).exists()) {
            loadScriptFromFile(getReactApplicationContext().getCatalystInstance(), paramString);
            return;
          } 
        } else {
          String str = getReactApplicationContext().getCatalystInstance().getMainJsBundlePath();
          if (str != null) {
            String str1 = paramString;
            if (paramString.startsWith("."))
              str1 = paramString.substring(1); 
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str1);
            if ((new File(stringBuilder.toString())).exists()) {
              CatalystInstance catalystInstance = getReactApplicationContext().getCatalystInstance();
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append(str);
              stringBuilder1.append(str1);
              loadScriptFromFile(catalystInstance, stringBuilder1.toString());
              return;
            } 
          } 
        } 
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\jsruntask\JSRunTaskModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */