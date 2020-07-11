package com.tt.miniapp.msg.sync;

import com.a;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public class GetStorageSyncCtrl extends SyncMsgCtrl {
  public GetStorageSyncCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      null = (new JSONObject(this.mParams)).optString("key");
      String str1 = Storage.getValue(null);
      String str2 = Storage.getType(null);
      JSONObject jSONObject = new JSONObject();
      if (str1 == null) {
        jSONObject.put("data", "");
        jSONObject.put("dataType", "String");
        return makeFailMsg(a.a("data not found, key == %s", new Object[] { null }), jSONObject);
      } 
      jSONObject.put("data", str1);
      jSONObject.put("dataType", str2);
      return makeOkMsg(jSONObject);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_GetStorageSyncCtrl", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "getStorageSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\GetStorageSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */