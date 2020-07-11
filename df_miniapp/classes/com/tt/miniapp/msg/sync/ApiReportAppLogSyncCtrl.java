package com.tt.miniapp.msg.sync;

import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiReportAppLogSyncCtrl extends SyncMsgCtrl {
  public ApiReportAppLogSyncCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    boolean bool = AppbrandApplicationImpl.getInst().getAppInfo().isGame();
    String str2 = (AppbrandApplicationImpl.getInst().getAppInfo()).appId;
    StringBuilder stringBuilder = new StringBuilder();
    if (bool) {
      str1 = "MicroGame_";
    } else {
      str1 = "MicroApp_";
    } 
    stringBuilder.append(str1);
    stringBuilder.append(str2);
    String str1 = stringBuilder.toString();
    try {
      JSONObject jSONObject = new JSONObject(this.mParams);
      str2 = jSONObject.optString("type");
      String str = jSONObject.optString("value");
      if (TextUtils.equals("debug", str2)) {
        AppBrandLogger.d(str1, new Object[] { str });
      } else if (TextUtils.equals("info", str2)) {
        AppBrandLogger.i(str1, new Object[] { str });
      } else if (TextUtils.equals("warn", str2)) {
        AppBrandLogger.w(str1, new Object[] { str });
      } else if (TextUtils.equals("error", str2)) {
        AppBrandLogger.e(str1, new Object[] { str });
      } 
    } catch (JSONException jSONException) {}
    return makeOkMsg();
  }
  
  public String getName() {
    return "reportAppLog";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\ApiReportAppLogSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */