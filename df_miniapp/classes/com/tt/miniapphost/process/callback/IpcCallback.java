package com.tt.miniapphost.process.callback;

import com.tt.miniapphost.IDCreator;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public abstract class IpcCallback {
  private String mCallProcessIdentify;
  
  private final int mCallbackId = IDCreator.create();
  
  public IpcCallback() {}
  
  public IpcCallback(String paramString) {
    this.mCallProcessIdentify = paramString;
  }
  
  public void finishListenIpcCallback() {
    IpcCallbackManagerProxy.getInstance().unregisterIpcCallback(this.mCallbackId);
  }
  
  public String getCallProcessIdentify() {
    return this.mCallProcessIdentify;
  }
  
  public int getCallbackId() {
    return this.mCallbackId;
  }
  
  public abstract void onIpcCallback(CrossProcessDataEntity paramCrossProcessDataEntity);
  
  public void onIpcConnectError() {}
  
  public void setCallProcessIdentify(String paramString) {
    this.mCallProcessIdentify = paramString;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\callback\IpcCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */