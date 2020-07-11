package com.tt.miniapp.msg.sync;

import android.text.TextUtils;
import android.util.Log;
import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import java.util.HashMap;
import org.json.JSONObject;

public abstract class SyncMsgCtrlV2 {
  protected f mParams;
  
  public SyncMsgCtrlV2(f paramf) {
    this.mParams = paramf;
  }
  
  public abstract g act();
  
  protected String buildErrorMsg(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(":");
    stringBuilder.append(paramString2);
    return stringBuilder.toString();
  }
  
  public abstract String getName();
  
  public String makeExceptionErrResult(Throwable paramThrowable) {
    return makeMsgByResult(false, null, "exception", paramThrowable);
  }
  
  public String makeMsgByExtraInfo(boolean paramBoolean, String paramString) {
    return makeMsgByResult(paramBoolean, null, paramString, null);
  }
  
  public String makeMsgByResult(boolean paramBoolean, HashMap<String, Object> paramHashMap, String paramString, Throwable paramThrowable) {
    try {
      JSONObject jSONObject = new JSONObject();
      boolean bool = TextUtils.isEmpty(paramString);
      String str = "ok";
      if (bool) {
        paramString = getName();
        if (!paramBoolean)
          str = "fail"; 
        jSONObject.put("errMsg", buildErrorMsg(paramString, str));
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        String str1 = getName();
        if (!paramBoolean)
          str = "fail"; 
        stringBuilder.append(buildErrorMsg(str1, str));
        stringBuilder.append(" ");
        stringBuilder.append(paramString);
        jSONObject.put("errMsg", stringBuilder.toString());
      } 
      if (!paramBoolean && paramThrowable != null)
        jSONObject.put("errStack", Log.getStackTraceString(paramThrowable)); 
      if (paramHashMap != null && paramHashMap.size() > 0)
        for (String str1 : paramHashMap.keySet())
          jSONObject.put(str1, paramHashMap.get(str1));  
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "SyncMsgCtrlV2", exception.getStackTrace());
      return CharacterUtils.empty();
    } 
  }
  
  public String makeMsgWithExtraInfo(boolean paramBoolean, String paramString) {
    String str1;
    StringBuilder stringBuilder = new StringBuilder();
    String str2 = getName();
    if (paramBoolean) {
      str1 = "ok";
    } else {
      str1 = "fail";
    } 
    stringBuilder.append(buildErrorMsg(str2, str1));
    stringBuilder.append(" ");
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\SyncMsgCtrlV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */