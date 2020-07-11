package com.tt.miniapp.msg.storage.internal;

import android.text.TextUtils;
import android.util.Log;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.storage.InternalStorageHelper;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiOperateInternalStorageSyncCtrl extends SyncMsgCtrl {
  public ApiOperateInternalStorageSyncCtrl(String paramString) {
    super(paramString);
  }
  
  private String makeGetValResult(String paramString1, String paramString2) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    if (paramString1 == null || paramString2 == null) {
      jSONObject.put("data", "");
      jSONObject.put("dataType", "String");
      jSONObject.put("errMsg", buildErrorMsg("getStorageSync", "fail"));
      return String.valueOf(jSONObject);
    } 
    jSONObject.put("errMsg", buildErrorMsg("getStorageSync", "ok"));
    jSONObject.put("data", paramString1);
    jSONObject.put("dataType", paramString2);
    return String.valueOf(jSONObject);
  }
  
  public String act() {
    try {
      String str1;
      JSONObject jSONObject = new JSONObject(this.mParams);
      String str2 = jSONObject.optString("type");
      boolean bool = "get".equalsIgnoreCase(str2);
      if (bool)
        return TextUtils.isEmpty(str1) ? makeMsgByExtraInfo(false, "error params.key") : makeGetValResult(InternalStorageHelper.getStorage().getValue(str1), InternalStorageHelper.getStorage().getDataType(str1)); 
      return makeMsgByExtraInfo(false, "unknown params.type");
    } finally {
      Exception exception = null;
      AppBrandLogger.e("ApiInternalStorage", new Object[] { exception.getMessage(), Log.getStackTraceString(exception) });
      StringBuilder stringBuilder = new StringBuilder("exp: ");
      stringBuilder.append(exception.getMessage());
    } 
  }
  
  public String getName() {
    return "operateInternalStorageSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\storage\internal\ApiOperateInternalStorageSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */