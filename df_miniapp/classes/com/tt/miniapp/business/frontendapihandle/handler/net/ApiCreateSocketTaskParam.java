package com.tt.miniapp.business.frontendapihandle.handler.net;

import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiCreateSocketTaskParam {
  public static class InputParam implements f {
    public boolean __skipDomainCheck__;
    
    public JSONObject header;
    
    public String method;
    
    public JSONArray protocols;
    
    public String socketType;
    
    public String url;
    
    public InputParam(String param1String1, String param1String2, String param1String3, JSONObject param1JSONObject, JSONArray param1JSONArray, boolean param1Boolean) {
      this.socketType = param1String1;
      this.url = param1String2;
      this.method = param1String3;
      this.header = param1JSONObject;
      this.protocols = param1JSONArray;
      this.__skipDomainCheck__ = param1Boolean;
    }
  }
  
  public static class OutputParam implements g {
    public String errMsg;
    
    public int socketTaskId;
    
    public String socketType;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\net\ApiCreateSocketTaskParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */