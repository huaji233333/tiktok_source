package com.tt.miniapp.msg.bean;

import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import java.util.List;

public class ApiReadFileParam {
  public static class InputParam implements f {
    public String encoding;
    
    public String filePath;
    
    public InputParam(String param1String1, String param1String2) {
      this.filePath = param1String1;
      this.encoding = param1String2;
    }
  }
  
  public static class OutputParam implements g {
    public List<NativeBufferItem> __nativeBuffers__;
    
    public String data;
    
    public String errMsg;
    
    public OutputParam() {}
    
    public OutputParam(String param1String1, String param1String2, List<NativeBufferItem> param1List) {
      this.errMsg = param1String1;
      this.data = param1String2;
      this.__nativeBuffers__ = param1List;
    }
    
    public static class NativeBufferItem {
      public String key;
      
      public byte[] value;
      
      public NativeBufferItem(String param2String, byte[] param2ArrayOfbyte) {
        this.key = param2String;
        this.value = param2ArrayOfbyte;
      }
    }
  }
  
  public static class NativeBufferItem {
    public String key;
    
    public byte[] value;
    
    public NativeBufferItem(String param1String, byte[] param1ArrayOfbyte) {
      this.key = param1String;
      this.value = param1ArrayOfbyte;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\bean\ApiReadFileParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */