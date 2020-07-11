package com.tt.miniapp.msg.bean;

import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import java.nio.ByteBuffer;

public class ApiWriteFileParam {
  public static class InputParam implements f {
    public ByteBuffer byteBuffer;
    
    public String data;
    
    public String encoding;
    
    public String filePath;
    
    public InputParam(String param1String1, String param1String2, String param1String3, ByteBuffer param1ByteBuffer) {
      this.filePath = param1String1;
      this.encoding = param1String2;
      this.data = param1String3;
      this.byteBuffer = param1ByteBuffer;
    }
  }
  
  public static class OutPutParam implements g {
    public String errMsg;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\bean\ApiWriteFileParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */