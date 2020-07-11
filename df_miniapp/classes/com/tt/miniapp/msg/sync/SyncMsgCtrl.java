package com.tt.miniapp.msg.sync;

import android.text.TextUtils;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import java.util.HashMap;
import org.json.JSONObject;

public abstract class SyncMsgCtrl {
  protected String mParams;
  
  public SyncMsgCtrl(String paramString) {
    this.mParams = paramString;
  }
  
  public abstract String act();
  
  protected String buildErrorMsg(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(":");
    stringBuilder.append(paramString2);
    return stringBuilder.toString();
  }
  
  public boolean canOverride() {
    return true;
  }
  
  public abstract String getName();
  
  @Deprecated
  public String makeExceptionErrResult(Throwable paramThrowable) {
    return makeFailMsg(paramThrowable);
  }
  
  public String makeFailMsg(String paramString) {
    return ApiCallResult.a.b(getName()).d(paramString).a().toString();
  }
  
  public String makeFailMsg(String paramString, JSONObject paramJSONObject) {
    return ApiCallResult.a.b(getName()).a(paramJSONObject).d(paramString).a().toString();
  }
  
  public String makeFailMsg(Throwable paramThrowable) {
    return ApiCallResult.a.b(getName()).a(paramThrowable).a().toString();
  }
  
  public String makeMsg(boolean paramBoolean, JSONObject paramJSONObject, String paramString, Throwable paramThrowable) {
    if (paramBoolean) {
      ApiCallResult.a a1 = ApiCallResult.a.a(getName());
      if (!TextUtils.isEmpty(paramString)) {
        a1.d(paramString);
      } else if (paramThrowable != null) {
        a1.a(paramThrowable);
      } 
      a1.a(paramJSONObject);
      return a1.a().toString();
    } 
    ApiCallResult.a a = ApiCallResult.a.b(getName());
    if (!TextUtils.isEmpty(paramString)) {
      a.d(paramString);
    } else if (paramThrowable != null) {
      a.a(paramThrowable);
    } 
    a.a(paramJSONObject);
    return a.a().toString();
  }
  
  @Deprecated
  public String makeMsgByExtraInfo(boolean paramBoolean, String paramString) {
    return makeMsgByResult(paramBoolean, null, paramString, null);
  }
  
  @Deprecated
  public String makeMsgByResult(boolean paramBoolean, HashMap<String, Object> paramHashMap, String paramString, Throwable paramThrowable) {
    try {
      JSONObject jSONObject = new JSONObject();
      if (paramHashMap != null && paramHashMap.size() > 0)
        for (String str : paramHashMap.keySet())
          jSONObject.put(str, paramHashMap.get(str));  
      return makeMsg(paramBoolean, jSONObject, paramString, paramThrowable);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "SyncMsgCtrl", exception.getStackTrace());
      return CharacterUtils.empty();
    } 
  }
  
  public String makeOkMsg() {
    return ApiCallResult.a.a(getName()).a().toString();
  }
  
  public String makeOkMsg(JSONObject paramJSONObject) {
    return ApiCallResult.a.a(getName()).a(paramJSONObject).a().toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\SyncMsgCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */