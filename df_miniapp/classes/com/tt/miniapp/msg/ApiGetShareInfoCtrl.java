package com.tt.miniapp.msg;

import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.option.e.e;
import com.tt.option.q.i;
import com.tt.option.q.j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetShareInfoCtrl extends b {
  public ApiGetShareInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private String getHostId() {
    String str;
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null) {
      str = initParamsEntity.getAppId();
    } else {
      str = "";
    } 
    if (TextUtils.isEmpty(str)) {
      AppBrandLogger.e("ApiGetShareInfoCtrl", new Object[] { "host id is empty" });
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("errMsg", "host id is empty");
      } catch (JSONException jSONException) {
        AppBrandLogger.e("ApiGetShareInfoCtrl", new Object[] { "getHostId", jSONException });
      } 
      AppBrandMonitor.statusRate("mp_start_error", 2003, jSONObject);
    } 
    return str;
  }
  
  private i getOpenGidRequest(String paramString, String[] paramArrayOfString) {
    i i = new i(AppbrandConstant.OpenApi.getInst().getSHARE_QUERY_OPEN_GID(), "POST");
    if (!TextUtils.isEmpty(paramString))
      i.a("share_ticket", paramString); 
    if (paramArrayOfString != null && paramArrayOfString.length > 0)
      try {
        i.a("share_tickets", new JSONArray(paramArrayOfString));
      } catch (JSONException jSONException) {
        AppBrandLogger.e("ApiGetShareInfoCtrl", new Object[] { "getOpenGidRequest", jSONException });
      }  
    i.a("host_id", Integer.valueOf(Integer.parseInt(getHostId())));
    i.a("app_id", (AppbrandApplication.getInst().getAppInfo()).appId);
    paramString = InnerHostProcessBridge.getPlatformSession((AppbrandApplication.getInst().getAppInfo()).appId);
    if (!TextUtils.isEmpty(paramString))
      i.a("session", paramString); 
    return i;
  }
  
  private void queryOpenGid(String paramString, String[] paramArrayOfString) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            try {
              j j1 = HostDependManager.getInst().doPostBody(tmaRequest);
              String str = j1.a();
              if (TextUtils.isEmpty(str))
                return; 
              JSONObject jSONObject = new JSONObject(str);
              int j = jSONObject.optInt("err_no", -1);
              jSONObject = jSONObject.optJSONObject("data");
              return;
            } finally {
              Exception exception = null;
              ApiGetShareInfoCtrl.this.callbackFail(exception);
            } 
          }
        }Schedulers.longIO());
  }
  
  public void act() {
    try {
      String[] arrayOfString;
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str = jSONObject.optString("shareTicket");
      JSONArray jSONArray = jSONObject.optJSONArray("shareTickets");
      jSONObject = null;
      if (jSONArray != null) {
        String[] arrayOfString1 = new String[jSONArray.length()];
        int i = 0;
        while (true) {
          arrayOfString = arrayOfString1;
          if (i < jSONArray.length()) {
            arrayOfString1[i] = jSONArray.optString(i);
            i++;
            continue;
          } 
          break;
        } 
      } 
      queryOpenGid(str, arrayOfString);
      return;
    } catch (JSONException jSONException) {
      callbackFail(a.a(this.mArgs));
      return;
    } 
  }
  
  public String getActionName() {
    return "getShareInfo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetShareInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */