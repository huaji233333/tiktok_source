package com.tt.miniapp.websocket.mgr;

import android.content.Context;
import android.util.SparseArray;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.manager.ForeBackgroundManager;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.websocket.common.IWebSocketTask;
import com.tt.miniapp.websocket.common.WSRequest;
import com.tt.miniapp.websocket.common.WsStatusListener;
import com.tt.miniapp.websocket.task.SocketTaskFactory;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.ApiErrorInfoEntity;
import com.tt.miniapphost.util.CharacterUtils;
import g.i;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbsSocketManager {
  private AtomicInteger mSocketId = new AtomicInteger(0);
  
  final SparseArray<IWebSocketTask> mWebSockets = new SparseArray();
  
  AbsSocketManager() {
    AppbrandApplicationImpl.getInst().getForeBackgroundManager().registerForeBackgroundListener((ForeBackgroundManager.ForeBackgroundListener)new ForeBackgroundManager.DefaultForeBackgroundListener() {
          public void onBackgroundOverLimitTime() {
            ThreadUtil.runOnWorkThread(new Action() {
                  public void act() {
                    AbsSocketManager.this.closeAllSocket();
                  }
                },  Schedulers.shortIO());
          }
        });
  }
  
  protected static String buildErrorMsg(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(":");
    stringBuilder.append(paramString2);
    return stringBuilder.toString();
  }
  
  private int createSocketId() {
    return this.mSocketId.incrementAndGet();
  }
  
  public void closeAllSocket() {
    int i = 0;
    AppBrandLogger.d("_Socket_AbsMgr", new Object[] { "closeAllSocket" });
    synchronized (this.mWebSockets) {
      SparseArray sparseArray = this.mWebSockets.clone();
      int j = sparseArray.size();
      while (i < j) {
        closeSocket(sparseArray.keyAt(i), 1001, "app in background");
        i++;
      } 
      return;
    } 
  }
  
  public abstract boolean closeSocket(int paramInt1, int paramInt2, String paramString);
  
  public final int createTask(WSRequest paramWSRequest) {
    null = SocketTaskFactory.createWsTask((Context)AppbrandContext.getInst().getApplicationContext(), paramWSRequest);
    if (null == null)
      return -1; 
    int i = createSocketId();
    null.setStatusListener(createWsStatusListener(i));
    null.startConnect();
    synchronized (this.mWebSockets) {
      this.mWebSockets.put(i, null);
      return i;
    } 
  }
  
  protected abstract WsStatusListener createWsStatusListener(int paramInt);
  
  IWebSocketTask getSocketTask(int paramInt) {
    synchronized (this.mWebSockets) {
      return (IWebSocketTask)this.mWebSockets.get(paramInt);
    } 
  }
  
  public String getSocketType(int paramInt) {
    IWebSocketTask iWebSocketTask = getSocketTask(paramInt);
    return (iWebSocketTask == null) ? CharacterUtils.empty() : iWebSocketTask.getSocketType();
  }
  
  String getTransportProtocol(int paramInt) {
    IWebSocketTask iWebSocketTask = getSocketTask(paramInt);
    return (iWebSocketTask == null) ? CharacterUtils.empty() : iWebSocketTask.getTransportProtocol();
  }
  
  void removeSocketTask(int paramInt) {
    synchronized (this.mWebSockets) {
      this.mWebSockets.remove(paramInt);
      return;
    } 
  }
  
  public boolean sendArrayBuffer(int paramInt, i parami, ApiErrorInfoEntity paramApiErrorInfoEntity) {
    if (parami == null) {
      paramApiErrorInfoEntity.append("data is null");
      return false;
    } 
    IWebSocketTask iWebSocketTask = getSocketTask(paramInt);
    if (iWebSocketTask != null) {
      if (iWebSocketTask.isWsConnected())
        return iWebSocketTask.sendMessage(parami); 
      paramApiErrorInfoEntity.append("webSocket no open");
      return false;
    } 
    paramApiErrorInfoEntity.append("socket no create socketId == ").append(Integer.valueOf(paramInt));
    return false;
  }
  
  public boolean sendText(int paramInt, String paramString, ApiErrorInfoEntity paramApiErrorInfoEntity) {
    IWebSocketTask iWebSocketTask = getSocketTask(paramInt);
    if (iWebSocketTask != null) {
      if (iWebSocketTask.isWsConnected())
        return iWebSocketTask.sendMessage(paramString); 
      paramApiErrorInfoEntity.append("webSocket no open");
    } else {
      paramApiErrorInfoEntity.append("socket no create socketId == ").append(Integer.valueOf(paramInt));
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\mgr\AbsSocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */