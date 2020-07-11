package com.tt.miniapp.websocket.mgr;

import android.text.TextUtils;
import com.tt.frontendapiinterface.g;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.business.frontendapihandle.handler.net.ApiOperateSocketTaskParam;
import com.tt.miniapp.msg.bean.NativeBufferItem;
import com.tt.miniapp.websocket.common.IWebSocketTask;
import com.tt.miniapp.websocket.common.WsStatusListener;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class SocketManagerV2 extends AbsSocketManager {
  private SocketManagerV2() {}
  
  public static SocketManagerV2 getInst() {
    return Holder.sInstance;
  }
  
  public static void sendMsg2Js(ApiOperateSocketTaskParam.OutputParam paramOutputParam) {
    j j = AppbrandApplication.getInst().getJsBridge();
    if (j == null) {
      AppBrandLogger.w("_Socket_MgrV2", new Object[] { "onSocketTaskStateChange", "resp failed: null bridge", paramOutputParam });
      return;
    } 
    paramOutputParam.socketType = getInst().getSocketType(paramOutputParam.socketTaskId);
    paramOutputParam.protocolType = getInst().getTransportProtocol(paramOutputParam.socketTaskId);
    AppBrandLogger.d("_Socket_MgrV2", new Object[] { "onSocketTaskStateChange", "resp:", paramOutputParam.toString() });
    j.sendMsgToJsCore2("onSocketTaskStateChange", (g)paramOutputParam);
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
    static SocketManagerV2 sInstance = new SocketManagerV2();
  }
  
  static class WsListener extends WsStatusListener {
    private int mWebSocketId;
    
    public WsListener(int param1Int) {
      this.mWebSocketId = param1Int;
    }
    
    private void removeSocketTask() {
      SocketManagerV2.getInst().removeSocketTask(this.mWebSocketId);
    }
    
    public void onClosed(int param1Int, String param1String) {
      ApiOperateSocketTaskParam.OutputParam outputParam = new ApiOperateSocketTaskParam.OutputParam();
      outputParam.socketTaskId = this.mWebSocketId;
      outputParam.errMsg = AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok");
      outputParam.state = "close";
      SocketManagerV2.sendMsg2Js(outputParam);
      removeSocketTask();
    }
    
    public void onClosing(int param1Int, String param1String) {}
    
    public void onFailure(Throwable param1Throwable) {
      ApiOperateSocketTaskParam.OutputParam outputParam = new ApiOperateSocketTaskParam.OutputParam();
      outputParam.socketTaskId = this.mWebSocketId;
      outputParam.errMsg = AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok");
      outputParam.state = "close";
      SocketManagerV2.sendMsg2Js(outputParam);
      removeSocketTask();
    }
    
    public void onMessage(String param1String) {
      ApiOperateSocketTaskParam.OutputParam outputParam = new ApiOperateSocketTaskParam.OutputParam();
      outputParam.socketTaskId = this.mWebSocketId;
      outputParam.errMsg = AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok");
      outputParam.state = "message";
      outputParam.data = param1String;
      SocketManagerV2.sendMsg2Js(outputParam);
    }
    
    public void onMessage(byte[] param1ArrayOfbyte) {
      if (param1ArrayOfbyte != null) {
        ApiOperateSocketTaskParam.OutputParam outputParam = new ApiOperateSocketTaskParam.OutputParam();
        outputParam.socketTaskId = this.mWebSocketId;
        outputParam.errMsg = AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok");
        outputParam.state = "message";
        NativeBufferItem nativeBufferItem = new NativeBufferItem("data", ByteBuffer.wrap(param1ArrayOfbyte));
        ArrayList<NativeBufferItem> arrayList = new ArrayList();
        arrayList.add(nativeBufferItem);
        outputParam.__nativeBuffers__ = arrayList;
        SocketManagerV2.sendMsg2Js(outputParam);
      } 
    }
    
    public void onOpen(String param1String) {
      ApiOperateSocketTaskParam.OutputParam outputParam = new ApiOperateSocketTaskParam.OutputParam();
      outputParam.socketTaskId = this.mWebSocketId;
      outputParam.errMsg = AbsSocketManager.buildErrorMsg("onSocketTaskStateChange", "ok");
      outputParam.state = "open";
      if (!TextUtils.isEmpty(param1String))
        outputParam.header = param1String; 
      SocketManagerV2.sendMsg2Js(outputParam);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\mgr\SocketManagerV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */