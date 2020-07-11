package com.tt.miniapp.msg.bean;

import java.nio.ByteBuffer;

public class NativeBufferItem {
  public String key;
  
  public ByteBuffer value;
  
  public NativeBufferItem(String paramString, ByteBuffer paramByteBuffer) {
    this.key = paramString;
    this.value = paramByteBuffer;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\bean\NativeBufferItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */