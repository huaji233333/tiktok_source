package com.tt.miniapp.websocket.common;

public abstract class WsStatusListener {
  public void onClosed(int paramInt, String paramString) {}
  
  public void onClosing(int paramInt, String paramString) {}
  
  public void onFailure(Throwable paramThrowable) {}
  
  public void onMessage(String paramString) {}
  
  public void onMessage(byte[] paramArrayOfbyte) {}
  
  public void onOpen(String paramString) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\common\WsStatusListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */