package com.tt.miniapp.msg.download;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetDxppTaskStatus extends SyncMsgCtrl implements TmaDownloadParams {
  public ApiGetDxppTaskStatus(String paramString) {
    super(paramString);
  }
  
  public String act() {
    if (!HostDependManager.getInst().supportDxpp())
      return makeFailMsg("feature is not supported in app"); 
    try {
      JSONObject jSONObject2 = new JSONObject(this.mParams);
      String str1 = jSONObject2.optString("app_name");
      String str2 = jSONObject2.optString("pkg_name");
      String str3 = jSONObject2.optString("download_url");
      if (TextUtils.isEmpty(str1))
        return makeFailMsg(a.b("app_name")); 
      if (TextUtils.isEmpty(str2))
        return makeFailMsg(a.b("pkg_name")); 
      if (TextUtils.isEmpty(str3))
        return makeFailMsg(a.b("download_url")); 
      JSONObject jSONObject1 = HostDependManager.getInst().getDxppTaskStatus(str1, str2, str3, true);
      return (jSONObject1 == null) ? makeFailMsg("feature is not supported in app") : makeOkMsg(jSONObject1);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_ApiGetDxppTaskStatus", new Object[] { jSONException });
      return makeFailMsg((Throwable)jSONException);
    } 
  }
  
  public String getName() {
    return "getDxppTaskStatusSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\download\ApiGetDxppTaskStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */