package com.tt.miniapp.msg;

import android.text.TextUtils;
import com.bytedance.sandboxapp.d.a;
import com.bytedance.sandboxapp.protocol.service.request.a;
import com.bytedance.sandboxapp.protocol.service.request.entity.HttpRequest;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiErrorLogCtrl extends b {
  public ApiErrorLogCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void invoke(String paramString, HttpRequest.a parama) throws Exception {
    JSONObject jSONObject1 = new JSONObject(paramString);
    String str2 = jSONObject1.optString("url");
    String str1 = jSONObject1.optString("method", "GET");
    paramString = str1;
    if (TextUtils.isEmpty(str1))
      paramString = "POST"; 
    str1 = jSONObject1.optString("data");
    JSONObject jSONObject2 = jSONObject1.optJSONObject("header");
    String str3 = jSONObject1.optString("responseType", "text");
    byte[] arrayOfByte = a.a(jSONObject1.optJSONArray("__nativeBuffers__"), false);
    HttpRequest.RequestTask requestTask = HttpRequest.RequestTask.a.a.a(str2, paramString).b(str1).a(arrayOfByte).a(jSONObject2).a(str3).a(true).a();
    AppBrandLogger.d("tma_ApiErrorLogCtrl", new Object[] { "request:", requestTask });
    ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).addHttpRequest(requestTask, parama);
  }
  
  public void act() {
    try {
      invoke(this.mArgs, new HttpRequest.a() {
            public void onRequestAbort(HttpRequest.RequestTask param1RequestTask) {
              ApiErrorLogCtrl.this.callbackOk();
            }
            
            public void onRequestFinish(HttpRequest.RequestResult param1RequestResult) {
              ApiErrorLogCtrl.this.callbackOk();
            }
          });
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiErrorLogCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "sentryReport";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiErrorLogCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */