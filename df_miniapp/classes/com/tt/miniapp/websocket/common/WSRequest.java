package com.tt.miniapp.websocket.common;

import android.text.TextUtils;
import com.tt.miniapp.business.frontendapihandle.handler.net.ApiCreateSocketTaskParam;
import com.tt.miniapp.msg.ApiParamParser;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONArray;
import org.json.JSONObject;

public class WSRequest {
  public boolean __skipDomainCheck__;
  
  public JSONObject data;
  
  public JSONObject header;
  
  public String method;
  
  public JSONArray protocols;
  
  public String socketType;
  
  public String url;
  
  public static WSRequest parse(ApiCreateSocketTaskParam.InputParam paramInputParam) {
    if (paramInputParam == null)
      return null; 
    WSRequest wSRequest = new WSRequest();
    wSRequest.url = paramInputParam.url;
    wSRequest.method = paramInputParam.method;
    if (ApiParamParser.isEmptyString(wSRequest.method))
      wSRequest.method = "GET"; 
    wSRequest.header = paramInputParam.header;
    wSRequest.protocols = paramInputParam.protocols;
    wSRequest.__skipDomainCheck__ = paramInputParam.__skipDomainCheck__;
    wSRequest.socketType = paramInputParam.socketType;
    return wSRequest;
  }
  
  public static WSRequest parse(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      WSRequest wSRequest = new WSRequest();
      wSRequest.url = jSONObject.optString("url");
      wSRequest.data = jSONObject.optJSONObject("data");
      wSRequest.method = jSONObject.optString("method");
      if (TextUtils.isEmpty(wSRequest.method))
        wSRequest.method = "GET"; 
      wSRequest.header = jSONObject.optJSONObject("header");
      wSRequest.protocols = jSONObject.optJSONArray("protocols");
      wSRequest.__skipDomainCheck__ = jSONObject.optBoolean("__skipDomainCheck__");
      wSRequest.socketType = jSONObject.optString("socketType", "tradition");
      return wSRequest;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_WSRequest", exception.getStackTrace());
      return null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\common\WSRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */