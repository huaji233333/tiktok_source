package com.tt.miniapp.websocket.mgr;

import android.text.TextUtils;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.websocket.common.IWebSocketTask;
import com.tt.miniapp.websocket.common.WsStatusListener;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.util.JsonBuilder;
import g.i;
import org.json.JSONArray;

public class SocketManager extends AbsSocketManager {
  private SocketManager() {}
  
  public static SocketManager getInst() {
    return Holder.sInstance;
  }
  
  public static void sendMsg2Js(JsonBuilder paramJsonBuilder, int paramInt) {
    j j = AppbrandApplication.getInst().getJsBridge();
    if (j == null) {
      AppBrandLogger.w("_Socket_MgrV1", new Object[] { "onSocketTaskStateChange", "resp failed: null bridge", paramJsonBuilder });
      return;
    } 
    paramJsonBuilder.put("socketType", getInst().getSocketType(paramInt));
    paramJsonBuilder.put("protocolType", getInst().getTransportProtocol(paramInt));
    AppBrandLogger.d("_Socket_MgrV1", new Object[] { "onSocketTaskStateChange", "resp:", paramJsonBuilder });
    j.sendMsgToJsCore("onSocketTaskStateChange", paramJsonBuilder.build().toString());
  }
  
  public boolean closeSocket(int paramInt1, int paramInt2, String paramString) {
    IWebSocketTask iWebSocketTask = getSocketTask(paramInt1);
    if (iWebSocketTask != null)
      iWebSocketTask.stopConnect(paramInt2, paramString); 
    return true;
  }
  
  protected WsStatusListener createWsStatusListener(int paramInt) {
    return new WsListener(paramInt);
  }
  
  public static class Holder {
    static SocketManager sInstance = new SocketManager();
  }
  
  static class WsListener extends WsStatusListener {
    private int mWebSocketId;
    
    public WsListener(int param1Int) {
      this.mWebSocketId = param1Int;
    }
    
    private void removeSocketTask() {
      SocketManager.getInst().removeSocketTask(this.mWebSocketId);
    }
    
    public void onClosed(int param1Int, String param1String) {
      JsonBuilder jsonBuilder = new JsonBuilder();
      jsonBuilder.put("socketTaskId", Integer.valueOf(this.mWebSocketId));
      jsonBuilder.put("errMsg", AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok"));
      jsonBuilder.put("state", "close");
      SocketManager.sendMsg2Js(jsonBuilder, this.mWebSocketId);
      removeSocketTask();
    }
    
    public void onClosing(int param1Int, String param1String) {}
    
    public void onFailure(Throwable param1Throwable) {
      JsonBuilder jsonBuilder = new JsonBuilder();
      jsonBuilder.put("socketTaskId", Integer.valueOf(this.mWebSocketId));
      jsonBuilder.put("errMsg", AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok"));
      jsonBuilder.put("state", "close");
      SocketManager.sendMsg2Js(jsonBuilder, this.mWebSocketId);
      removeSocketTask();
    }
    
    public void onMessage(String param1String) {
      JsonBuilder jsonBuilder = new JsonBuilder();
      jsonBuilder.put("socketTaskId", Integer.valueOf(this.mWebSocketId));
      jsonBuilder.put("errMsg", AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok"));
      jsonBuilder.put("state", "message");
      jsonBuilder.put("data", param1String);
      SocketManager.sendMsg2Js(jsonBuilder, this.mWebSocketId);
    }
    
    public void onMessage(byte[] param1ArrayOfbyte) {
      if (param1ArrayOfbyte != null) {
        String str = i.of(param1ArrayOfbyte).base64();
        JsonBuilder jsonBuilder1 = new JsonBuilder();
        jsonBuilder1.put("socketTaskId", Integer.valueOf(this.mWebSocketId));
        jsonBuilder1.put("errMsg", AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok"));
        jsonBuilder1.put("state", "message");
        JsonBuilder jsonBuilder2 = new JsonBuilder();
        jsonBuilder2.put("key", "data");
        jsonBuilder2.put("base64", str);
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(jsonBuilder2.build());
        jsonBuilder1.put("__nativeBuffers__", jSONArray);
        SocketManager.sendMsg2Js(jsonBuilder1, this.mWebSocketId);
      } 
    }
    
    public void onOpen(String param1String) {
      JsonBuilder jsonBuilder = new JsonBuilder();
      jsonBuilder.put("socketTaskId", Integer.valueOf(this.mWebSocketId));
      jsonBuilder.put("errMsg", AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok"));
      jsonBuilder.put("state", "open");
      if (!TextUtils.isEmpty(param1String))
        jsonBuilder.put("header", param1String); 
      SocketManager.sendMsg2Js(jsonBuilder, this.mWebSocketId);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\mgr\SocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */