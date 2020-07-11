package com.tt.miniapp.msg.sync;

import com.tt.miniapp.storage.Storage;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetStorageInfoSyncCtrl extends SyncMsgCtrl {
  public GetStorageInfoSyncCtrl(String paramString) {
    super(paramString);
  }
  
  private String buildMsg() {
    long l1 = ToolUtils.byte2Kb(Storage.getStorageSize(), true);
    long l2 = ToolUtils.byte2Kb(Storage.getLimitSize(), true);
    JSONArray jSONArray = Storage.getKeys();
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("currentSize", l1);
      jSONObject.put("limitSize", l2);
      jSONObject.put("keys", jSONArray);
      return makeOkMsg(jSONObject);
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "SyncMsgCtrl", jSONException.getStackTrace());
      return makeFailMsg((Throwable)jSONException);
    } 
  }
  
  public String act() {
    return buildMsg();
  }
  
  public String getName() {
    return "getStorageInfoSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\GetStorageInfoSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */