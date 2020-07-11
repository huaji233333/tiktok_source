package com.tt.miniapp.msg;

import com.a;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetStorageCtrl extends b {
  public ApiGetStorageCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      String str1 = (new JSONObject(this.mArgs)).optString("key");
      String str2 = Storage.getValue(str1);
      String str3 = Storage.getType(str1);
      AppBrandLogger.d("tma_ApiGetStorageCtrl", new Object[] { "key ", str1, " \n value", str2, " \n dataType", str3 });
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      if (str2 == null) {
        hashMap.put("data", "");
        hashMap.put("dataType", "String");
        callbackFail(a.a("data not found, key == %s", new Object[] { str1 }), a.a(hashMap));
        return;
      } 
      hashMap.put("data", str2);
      hashMap.put("dataType", str3);
      callbackOk(a.a(hashMap));
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "tma_ApiGetStorageCtrl", jSONException.getStackTrace());
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "getStorage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetStorageCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */