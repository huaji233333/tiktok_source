package com.tt.miniapp.manager.basebundle.handler;

import android.content.Context;
import android.content.SharedPreferences;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.manager.basebundle.BaseBundleDAO;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public class UpdateSettingsHandler extends BaseBundleHandler {
  private void saveJsSdkInfo(Context paramContext, JSONObject paramJSONObject, BundleHandlerParam paramBundleHandlerParam) {
    if (paramJSONObject == null)
      return; 
    try {
      SharedPreferences.Editor editor = BaseBundleDAO.getJsSdkSP(paramContext).edit();
      if (paramJSONObject.has("sdkVersion"))
        editor.putString("sdk_version", paramJSONObject.optString("sdkVersion")).apply(); 
      if (paramJSONObject.has("sdkUpdateVersion"))
        editor.putString("sdk_update_version", paramJSONObject.optString("sdkUpdateVersion")); 
      if (paramJSONObject.has("latestSDKUrl"))
        editor.putString("latest_sdk_url", paramJSONObject.optString("latestSDKUrl")).apply(); 
      paramBundleHandlerParam.baseBundleEvent.appendLog(paramJSONObject.toString());
      return;
    } catch (Exception exception) {
      paramBundleHandlerParam.isLastTaskSuccess = false;
      AppBrandLogger.e("UpdateSettingsHandler", new Object[] { exception });
      long l = paramBundleHandlerParam.timeMeter.getMillisAfterStart();
      InnerEventHelper.mpLibResult("mp_lib_request_result", "0", "0", "fail", exception.getMessage(), l);
      return;
    } 
  }
  
  public BundleHandlerParam handle(Context paramContext, BundleHandlerParam paramBundleHandlerParam) {
    saveJsSdkInfo(paramContext, SettingsDAO.getJSONObject(paramContext, new Enum[] { (Enum)Settings.TMA_SDK_CONFIG }), paramBundleHandlerParam);
    return paramBundleHandlerParam;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\handler\UpdateSettingsHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */