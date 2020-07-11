package com.tt.miniapp.base.netrequest;

import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.request.a;
import com.bytedance.sandboxapp.protocol.service.request.entity.HttpRequest;
import com.bytedance.sandboxapp.protocol.service.request.entity.a;
import com.bytedance.sandboxapp.protocol.service.request.entity.b;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.q.i;

public class NetRequestService implements a {
  private final MiniAppContext mContext;
  
  private final NetManager mNetManger;
  
  public NetRequestService(MiniAppContext paramMiniAppContext) {
    this.mContext = paramMiniAppContext;
    this.mNetManger = NetManager.getInst();
  }
  
  private i convertToTmaRequest(HttpRequest.RequestTask paramRequestTask) {
    i i = new i(paramRequestTask.b, paramRequestTask.c, paramRequestTask.i);
    i.a(paramRequestTask.j);
    return i;
  }
  
  public void addDownloadRequest(a.d paramd, a.a parama) {
    FileLoadManager.getInst().addDownloadRequest(paramd, parama);
  }
  
  public void addHttpRequest(HttpRequest.RequestTask paramRequestTask, HttpRequest.a parama) {
    RequestManagerV2.getInst().addRequest(paramRequestTask, parama);
  }
  
  public void addUploadRequest(b.d paramd, b.a parama) {
    FileLoadManager.getInst().addUploadTask(paramd, parama);
  }
  
  public MiniAppContext getContext() {
    return this.mContext;
  }
  
  public void onDestroy() {}
  
  public void operateDownloadRequest(int paramInt, String paramString) {
    byte b;
    if (paramString.hashCode() == 92611376 && paramString.equals("abort")) {
      b = 0;
    } else {
      b = -1;
    } 
    if (b != 0)
      return; 
    FileLoadManager.getInst().cancelDownloadTask(paramInt);
  }
  
  public void operateHttpRequest(int paramInt, String paramString) {
    byte b;
    if (paramString.hashCode() == 92611376 && paramString.equals("abort")) {
      b = 0;
    } else {
      b = -1;
    } 
    if (b != 0)
      return; 
    RequestManagerV2.getInst().removeRequest(Integer.valueOf(paramInt));
  }
  
  public void operateUploadRequest(int paramInt, String paramString) {
    byte b;
    if (paramString.hashCode() == 92611376 && paramString.equals("abort")) {
      b = 0;
    } else {
      b = -1;
    } 
    if (b != 0)
      return; 
    FileLoadManager.getInst().cancelUploadTask(paramInt);
  }
  
  public HttpRequest.RequestResult syncHttpRequest(HttpRequest.RequestTask paramRequestTask) {
    try {
      return RequestManagerV2.getInst().convertToRequestResult(this.mNetManger.request(convertToTmaRequest(paramRequestTask)), 0, null);
    } finally {
      Exception exception = null;
      AppBrandLogger.e("NetRequestService", new Object[] { "syncSdkRequest error", exception });
      HttpRequest.RequestResult requestResult = new HttpRequest.RequestResult(paramRequestTask.a);
      requestResult.b = false;
      requestResult.i = exception;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\netrequest\NetRequestService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */