package com.tt.miniapp.business.frontendapihandle.base;

import android.text.TextUtils;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.a;
import java.util.HashMap;
import org.json.JSONObject;

public abstract class ApiCallbackHandler {
  private void callbackFail(String paramString, JSONObject paramJSONObject, int paramInt) {
    callbackApiHandleResult(ApiCallResult.a.a(getApiName(), paramString, paramInt).a(paramJSONObject).a());
  }
  
  protected abstract void callbackApiHandleResult(ApiCallResult paramApiCallResult);
  
  public final void callbackCancel() {
    callbackApiHandleResult(ApiCallResult.a.c(getApiName()).a());
  }
  
  @Deprecated
  public final void callbackCancel(String paramString, JSONObject paramJSONObject) {
    callbackApiHandleResult(ApiCallResult.a.c(getApiName()).d(paramString).a(paramJSONObject).a());
  }
  
  public final void callbackFail(String paramString) {
    callbackFail(paramString, null, 0);
  }
  
  public final void callbackFail(String paramString, int paramInt) {
    callbackFail(paramString, null, paramInt);
  }
  
  @Deprecated
  public final void callbackFail(String paramString, Throwable paramThrowable) {
    String str = paramString;
    if (TextUtils.isEmpty(paramString)) {
      str = paramString;
      if (paramThrowable != null)
        str = a.a(paramThrowable); 
    } 
    callbackFail(str, null, 0);
  }
  
  @Deprecated
  public final void callbackFail(String paramString, JSONObject paramJSONObject) {
    callbackFail(paramString, paramJSONObject, 0);
  }
  
  public final void callbackFail(Throwable paramThrowable) {
    callbackFail(a.a(paramThrowable), null, 0);
  }
  
  public final void callbackOk() {
    callbackOk(null, null);
  }
  
  public final void callbackOk(String paramString) {
    callbackOk(paramString, null);
  }
  
  public final void callbackOk(String paramString, JSONObject paramJSONObject) {
    callbackApiHandleResult((new ApiCallResult.a(getApiName(), "ok")).a(paramJSONObject).d(paramString).a());
  }
  
  @Deprecated
  public final void callbackOk(HashMap<String, Object> paramHashMap) {
    callbackOk(null, a.a(paramHashMap));
  }
  
  public final void callbackOk(JSONObject paramJSONObject) {
    callbackOk(null, paramJSONObject);
  }
  
  protected abstract String getApiName();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\base\ApiCallbackHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */