package com.tt.miniapp.websocket.common;

import g.i;

public interface IWebSocketTask {
  String getSocketType();
  
  String getTransportProtocol();
  
  boolean isWsConnected();
  
  boolean sendMessage(i parami);
  
  boolean sendMessage(String paramString);
  
  void setStatusListener(WsStatusListener paramWsStatusListener);
  
  void startConnect();
  
  boolean stopConnect(int paramInt, String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\common\IWebSocketTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */