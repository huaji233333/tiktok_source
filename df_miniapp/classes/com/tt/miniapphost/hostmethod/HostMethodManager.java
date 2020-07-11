package com.tt.miniapphost.hostmethod;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class HostMethodManager {
  private Map<String, IHostMethod> mRegisterMethod = new HashMap<String, IHostMethod>();
  
  private HostMethodManager() {}
  
  public static HostMethodManager getInstance() {
    return SingletonHolder.INSTANCE;
  }
  
  public void invokeJavaMethod(Activity paramActivity, String paramString, JSONObject paramJSONObject, ResponseCallBack paramResponseCallBack) {
    try {
      IHostMethod iHostMethod = this.mRegisterMethod.get(paramString);
      if (iHostMethod != null)
        iHostMethod.call(paramActivity, paramJSONObject, paramResponseCallBack); 
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(5, "HostMethodManager", exception.getStackTrace());
      return;
    } 
  }
  
  public String invokeJavaMethodSync(Activity paramActivity, String paramString, JSONObject paramJSONObject) {
    try {
      IHostMethod iHostMethod = this.mRegisterMethod.get(paramString);
      if (iHostMethod != null)
        return iHostMethod.callSync(paramActivity, paramJSONObject); 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(5, "HostMethodManager", exception.getStackTrace());
    } 
    return null;
  }
  
  public boolean invokeOnActivityResult(String paramString, int paramInt1, int paramInt2, Intent paramIntent) {
    try {
      IHostMethod iHostMethod = this.mRegisterMethod.get(paramString);
      if (iHostMethod != null)
        return iHostMethod.onActivityResult(paramInt1, paramInt2, paramIntent); 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(5, "HostMethodManager", exception.getStackTrace());
    } 
    return false;
  }
  
  public void registerHostMethod(String paramString, IHostMethod paramIHostMethod) {
    if (!TextUtils.isEmpty(paramString)) {
      if (paramIHostMethod == null)
        return; 
      this.mRegisterMethod.put(paramString, paramIHostMethod);
    } 
  }
  
  public boolean shouldHandleActivityResult(String paramString, JSONObject paramJSONObject) {
    try {
      IHostMethod iHostMethod = this.mRegisterMethod.get(paramString);
      if (iHostMethod != null)
        return iHostMethod.shouldHandleActivityResult(paramJSONObject); 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(5, "HostMethodManager", exception.getStackTrace());
    } 
    return false;
  }
  
  public void unregisterAllHostMethod() {
    Map<String, IHostMethod> map = this.mRegisterMethod;
    if (map != null)
      map.clear(); 
  }
  
  public void unregisterHostMethod(String paramString, IHostMethod paramIHostMethod) {
    if (!TextUtils.isEmpty(paramString)) {
      if (paramIHostMethod == null)
        return; 
      if (this.mRegisterMethod.containsKey(paramString))
        this.mRegisterMethod.remove(paramString); 
    } 
  }
  
  public static interface ResponseCallBack {
    void callResponse(String param1String);
  }
  
  static class SingletonHolder {
    public static final HostMethodManager INSTANCE = new HostMethodManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\hostmethod\HostMethodManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */