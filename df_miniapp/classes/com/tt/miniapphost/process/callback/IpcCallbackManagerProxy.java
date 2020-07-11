package com.tt.miniapphost.process.callback;

import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public class IpcCallbackManagerProxy implements IIpcCallbackManager {
  private IIpcCallbackManager mIpcCallbackManager;
  
  private IpcCallbackManagerProxy() {}
  
  private boolean checkValid() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mIpcCallbackManager : Lcom/tt/miniapphost/process/callback/IIpcCallbackManager;
    //   4: ifnonnull -> 40
    //   7: ldc com/tt/miniapphost/process/callback/IpcCallbackManagerProxy
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield mIpcCallbackManager : Lcom/tt/miniapphost/process/callback/IIpcCallbackManager;
    //   14: ifnonnull -> 28
    //   17: aload_0
    //   18: new com/tt/miniapp/process/callback/IpcCallbackManagerImpl
    //   21: dup
    //   22: invokespecial <init> : ()V
    //   25: putfield mIpcCallbackManager : Lcom/tt/miniapphost/process/callback/IIpcCallbackManager;
    //   28: ldc com/tt/miniapphost/process/callback/IpcCallbackManagerProxy
    //   30: monitorexit
    //   31: goto -> 40
    //   34: astore_1
    //   35: ldc com/tt/miniapphost/process/callback/IpcCallbackManagerProxy
    //   37: monitorexit
    //   38: aload_1
    //   39: athrow
    //   40: iconst_1
    //   41: ireturn
    // Exception table:
    //   from	to	target	type
    //   10	28	34	finally
    //   28	31	34	finally
    //   35	38	34	finally
  }
  
  public static IpcCallbackManagerProxy getInstance() {
    return Holder.sInstance;
  }
  
  public void handleIpcCallBack(int paramInt, CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (!checkValid())
      return; 
    this.mIpcCallbackManager.handleIpcCallBack(paramInt, paramCrossProcessDataEntity);
  }
  
  public void onCallProcessDead(String paramString) {
    if (!checkValid())
      return; 
    this.mIpcCallbackManager.onCallProcessDead(paramString);
  }
  
  public void registerIpcCallback(IpcCallback paramIpcCallback) {
    if (!checkValid())
      return; 
    this.mIpcCallbackManager.registerIpcCallback(paramIpcCallback);
  }
  
  public void unregisterIpcCallback(int paramInt) {
    if (!checkValid())
      return; 
    this.mIpcCallbackManager.unregisterIpcCallback(paramInt);
  }
  
  public void unregisterIpcCallback(IpcCallback paramIpcCallback) {
    if (!checkValid())
      return; 
    this.mIpcCallbackManager.unregisterIpcCallback(paramIpcCallback);
  }
  
  static class Holder {
    public static IpcCallbackManagerProxy sInstance = new IpcCallbackManagerProxy();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\callback\IpcCallbackManagerProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */