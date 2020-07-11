package com.tt.miniapp.business.frontendapihandle.handler.net;

import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import com.tt.miniapp.msg.bean.NativeBufferItem;
import java.util.List;

public class ApiOperateSocketTaskParam {
  public static class InputParam implements f {
    public List<NativeBufferItem> __nativeBuffers__;
    
    public int code;
    
    public String data;
    
    public String operationType;
    
    public String reason;
    
    public int socketTaskId;
    
    public InputParam(String param1String1, int param1Int1, String param1String2, List<NativeBufferItem> param1List, int param1Int2, String param1String3) {
      this.operationType = param1String1;
      this.socketTaskId = param1Int1;
      this.data = param1String2;
      this.__nativeBuffers__ = param1List;
      this.code = param1Int2;
      this.reason = param1String3;
    }
  }
  
  public static class OutputParam implements g {
    public List<NativeBufferItem> __nativeBuffers__;
    
    public String data;
    
    public String errMsg;
    
    public String errStack;
    
    public String header;
    
    public String protocolType;
    
    public int socketTaskId;
    
    public String socketType;
    
    public String state;
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("OutputParam{errMsg='");
      stringBuilder.append(this.errMsg);
      stringBuilder.append('\'');
      stringBuilder.append(", errStack='");
      stringBuilder.append(this.errStack);
      stringBuilder.append('\'');
      stringBuilder.append(", socketTaskId=");
      stringBuilder.append(this.socketTaskId);
      stringBuilder.append(", socketType='");
      stringBuilder.append(this.socketType);
      stringBuilder.append('\'');
      stringBuilder.append(", protocolType='");
      stringBuilder.append(this.protocolType);
      stringBuilder.append('\'');
      stringBuilder.append(", state='");
      stringBuilder.append(this.state);
      stringBuilder.append('\'');
      stringBuilder.append(", header='");
      String str = this.header;
      if (str != null) {
        str = str.replaceAll("\n", "\\n");
      } else {
        str = "";
      } 
      stringBuilder.append(str);
      stringBuilder.append('\'');
      stringBuilder.append(", data='");
      stringBuilder.append(this.data);
      stringBuilder.append('\'');
      stringBuilder.append(", __nativeBuffers__=");
      stringBuilder.append(this.__nativeBuffers__);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\net\ApiOperateSocketTaskParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */