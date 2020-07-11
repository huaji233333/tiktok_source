package com.tt.miniapp.msg.sync;

import android.text.TextUtils;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.option.v.c;
import org.json.JSONObject;

public class ApiCanLaunchAppSyncCtrl extends SyncMsgCtrl {
  public ApiCanLaunchAppSyncCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errMsg", buildErrorMsg("canLaunchAppSync", "ok"));
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
      if (appInfoEntity != null && appConfig != null && appConfig.getLaunchAppConfig() != null && c.a().b() && appInfoEntity.isCanLaunchApp() && !TextUtils.isEmpty((appConfig.getLaunchAppConfig()).packageName) && !TextUtils.isEmpty((appConfig.getLaunchAppConfig()).appName)) {
        boolean bool1 = true;
        jSONObject.put("canLaunch", bool1);
        return jSONObject.toString();
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("SyncMsgCtrl", new Object[] { exception });
      return makeFailMsg(exception);
    } 
    boolean bool = false;
    exception.put("canLaunch", bool);
    return exception.toString();
  }
  
  public String getName() {
    return "canLaunchAppSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\ApiCanLaunchAppSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */