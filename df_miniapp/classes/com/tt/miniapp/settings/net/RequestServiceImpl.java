package com.tt.miniapp.settings.net;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.ParamManager;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.q.i;
import org.json.JSONObject;

public class RequestServiceImpl implements RequestService {
  public SettingsResponse request() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null)
      return null; 
    String str2 = SettingsDAO.getCtxInfo((Context)application, "");
    StringBuilder stringBuilder = new StringBuilder();
    if (HostDependManager.getInst().isEnableI18nNetRequest()) {
      str1 = "https://developer-sg.byteoversea.com/service/settings/v3/";
    } else {
      str1 = "https://ib.snssdk.com/service/settings/v3/";
    } 
    stringBuilder.append(str1);
    stringBuilder.append("?caller_name=iron_man");
    stringBuilder.append("&tma_version=");
    stringBuilder.append(ParamManager.getMiniAppSdkVersion());
    if (AppbrandContext.getInst().getInitParams() != null) {
      if (!TextUtils.isEmpty(AppbrandContext.getInst().getInitParams().getPluginVersion())) {
        stringBuilder.append("&plugin_version=");
        stringBuilder.append(AppbrandContext.getInst().getInitParams().getPluginVersion());
      } 
      stringBuilder.append("&app_id=");
      stringBuilder.append(AppbrandContext.getInst().getInitParams().getAppId());
      stringBuilder.append("&app_name=");
      stringBuilder.append(AppbrandContext.getInst().getInitParams().getAppName());
      stringBuilder.append("&version_code=");
      stringBuilder.append(AppbrandContext.getInst().getInitParams().getVersionCode());
      stringBuilder.append("&device_platform=");
      stringBuilder.append(AppbrandContext.getInst().getInitParams().getDevicePlatform());
      stringBuilder.append("&device_type=");
      stringBuilder.append(Build.MODEL);
      stringBuilder.append("&device_brand=");
      stringBuilder.append(Build.BRAND);
      stringBuilder.append("&device_id=");
      stringBuilder.append(AppbrandContext.getInst().getInitParams().getDeviceId());
      if (!TextUtils.isEmpty(str2)) {
        stringBuilder.append("&ctx_infos=");
        stringBuilder.append(str2);
      } 
    } 
    String str1 = stringBuilder.toString();
    str1 = NetManager.getInst().request(new i(str1, "GET")).a();
    AppBrandLogger.d("tma_RequestServiceImpl", new Object[] { str1 });
    try {
      JSONObject jSONObject = new JSONObject(str1);
      boolean bool = TextUtils.equals(jSONObject.optString("message"), "success");
      SettingsResponse settingsResponse = new SettingsResponse();
      settingsResponse.success = bool;
      settingsResponse.settingsData = new SettingsData(jSONObject, null);
      return settingsResponse;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_RequestServiceImpl", new Object[] { Integer.valueOf(6), exception });
      return null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\net\RequestServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */