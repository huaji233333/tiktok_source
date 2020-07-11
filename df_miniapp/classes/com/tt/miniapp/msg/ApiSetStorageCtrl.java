package com.tt.miniapp.msg;

import android.text.TextUtils;
import com.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiSetStorageCtrl extends b {
  public ApiSetStorageCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      boolean bool;
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str1 = jSONObject.optString("key");
      if (TextUtils.isEmpty(str1)) {
        callbackIllegalParam("key");
        return;
      } 
      String str2 = jSONObject.optString("data");
      String str3 = jSONObject.optString("dataType");
      AppBrandLogger.d("tma_ApiSetStorageCtrl", new Object[] { "key ", str1, " \n value", str2, " \n dataType", str3 });
      if ((DebugManager.getInst()).mRemoteDebugEnable) {
        String str = Storage.getValue(str1);
        bool = Storage.setValue(str1, str2, str3);
        DebugManager.getInst().getRemoteDebugManager().setDOMStorageItem(0, bool, str1, str, str2);
      } else {
        bool = Storage.setValue(str1, str2, str3);
      } 
      if (bool) {
        callbackOk();
        return;
      } 
      callbackFail(a.a("set storage fail", new Object[0]));
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "tma_ApiSetStorageCtrl", jSONException.getStackTrace());
      callbackFail((Throwable)jSONException);
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_ApiSetStorageCtrl", iOException.getStackTrace());
      callbackFail(iOException.getMessage());
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_ApiSetStorageCtrl", exception.getStackTrace());
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "setStorage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiSetStorageCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */