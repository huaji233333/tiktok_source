package com.tt.option.e;

import android.text.TextUtils;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.i;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.util.JsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class j implements k {
  public String mArgs;
  
  public int mCallBackId;
  
  protected i mIBaseRender;
  
  public j(i parami, String paramString, int paramInt) {
    this.mArgs = paramString;
    this.mCallBackId = paramInt;
    this.mIBaseRender = parami;
  }
  
  public abstract String act();
  
  public String buildErrorMsg(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(":");
    stringBuilder.append(paramString2);
    return stringBuilder.toString();
  }
  
  public JSONObject buildErrorMsg(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errMsg", buildErrorMsg(getApiName(), paramString));
      return jSONObject;
    } catch (JSONException jSONException) {
      return jSONObject;
    } 
  }
  
  public void callbackAppUnSupportFeature() {
    callbackFail("feature is not supported in app");
  }
  
  public void callbackAppUnSupportFeature(int paramInt) {
    callbackFail(paramInt, "feature is not supported in app");
  }
  
  public void callbackFail(int paramInt, String paramString) {
    invokeHandler(makeFailMsg((new JsonBuilder()).put("data", (new JsonBuilder()).put("errCode", Integer.valueOf(paramInt)).put("errMsg", paramString).build()).build()));
  }
  
  public void callbackFail(String paramString) {
    invokeHandler(makeFailMsg(paramString));
  }
  
  public void callbackFail(Throwable paramThrowable) {
    invokeHandler(makeFailMsg(paramThrowable));
  }
  
  public void callbackOk() {
    invokeHandler(makeOkMsg());
  }
  
  public boolean canOverride() {
    return true;
  }
  
  public abstract String getApiName();
  
  public void invokeHandler(String paramString) {
    if (this.mIBaseRender != null)
      AppbrandApplication.getInst().invokeHandler(this.mIBaseRender.getWebViewId(), this.mCallBackId, paramString); 
  }
  
  public String makeFailMsg(String paramString) {
    return ApiCallResult.a.b(getApiName()).d(paramString).a().toString();
  }
  
  public String makeFailMsg(Throwable paramThrowable) {
    return ApiCallResult.a.b(getApiName()).a(paramThrowable).a().toString();
  }
  
  public String makeFailMsg(JSONObject paramJSONObject) {
    return ApiCallResult.a.b(getApiName()).a(paramJSONObject).a().toString();
  }
  
  public String makeMsg(boolean paramBoolean, JSONObject paramJSONObject, String paramString, Throwable paramThrowable) {
    if (paramBoolean) {
      ApiCallResult.a a1 = ApiCallResult.a.a(getApiName());
      if (!TextUtils.isEmpty(paramString)) {
        a1.d(paramString);
      } else if (paramThrowable != null) {
        a1.a(paramThrowable);
      } 
      a1.a(paramJSONObject);
      return a1.a().toString();
    } 
    ApiCallResult.a a = ApiCallResult.a.b(getApiName());
    if (!TextUtils.isEmpty(paramString)) {
      a.d(paramString);
    } else if (paramThrowable != null) {
      a.a(paramThrowable);
    } 
    a.a(paramJSONObject);
    return a.a().toString();
  }
  
  public String makeOkMsg() {
    return ApiCallResult.a.a(getApiName()).a().toString();
  }
  
  public String makeOkMsg(JSONObject paramJSONObject) {
    return ApiCallResult.a.a(getApiName()).a(paramJSONObject).a().toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\e\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */