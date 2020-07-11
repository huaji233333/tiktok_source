package com.tt.miniapp.msg;

import android.app.Activity;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.WindowManager;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.option.e.e;
import java.util.HashMap;
import org.json.JSONObject;

public class ApiScreenCtrl extends b {
  private String functionName;
  
  public ApiScreenCtrl(String paramString1, String paramString2, int paramInt, e parame) {
    super(paramString2, paramInt, parame);
    this.functionName = paramString1;
  }
  
  public void act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      return; 
    AppBrandLogger.i("tma_ApiScreenCtrl", new Object[] { this.functionName, "=", this.mArgs });
    if (TextUtils.equals(this.functionName, "getScreenBrightness"))
      try {
        int i = Settings.System.getInt(miniappHostBase.getContentResolver(), "screen_brightness");
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        double d = i;
        Double.isNaN(d);
        hashMap.put("brightness", Double.valueOf(d / 255.0D));
        callbackOk(a.a(hashMap));
        return;
      } catch (android.provider.Settings.SettingNotFoundException settingNotFoundException) {
        callbackFail((Throwable)settingNotFoundException);
        return;
      }  
    if (TextUtils.equals(this.functionName, "setKeepScreenOn"))
      try {
        final boolean keepScreenOn = (new JSONObject(this.mArgs)).optBoolean("keepScreenOn", false);
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                if (keepScreenOn) {
                  activity.getWindow().addFlags(128);
                  return;
                } 
                activity.getWindow().clearFlags(128);
              }
            });
        callbackOk();
        return;
      } catch (Exception exception) {
        callbackFail(exception);
        return;
      }  
    if (TextUtils.equals(this.functionName, "setScreenBrightness"))
      try {
        final WindowManager.LayoutParams params = exception.getWindow().getAttributes();
        layoutParams.screenBrightness = (float)(new JSONObject(this.mArgs)).optDouble("value");
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                activity.getWindow().setAttributes(params);
              }
            });
        callbackOk();
        return;
      } catch (Exception exception1) {
        callbackFail(exception1);
      }  
  }
  
  public String getActionName() {
    return this.functionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiScreenCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */