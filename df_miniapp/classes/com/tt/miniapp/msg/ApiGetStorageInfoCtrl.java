package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetStorageInfoCtrl extends b {
  public ApiGetStorageInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    long l1 = ToolUtils.byte2Kb(Storage.getStorageSize(), true);
    long l2 = ToolUtils.byte2Kb(Storage.getLimitSize(), true);
    JSONArray jSONArray = Storage.getKeys();
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("currentSize", l1);
      jSONObject.put("limitSize", l2);
      jSONObject.put("keys", jSONArray);
      callbackOk(jSONObject);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "ApiGetStorageInfoCtrl", jSONException.getStackTrace());
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "getStorageInfo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetStorageInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */