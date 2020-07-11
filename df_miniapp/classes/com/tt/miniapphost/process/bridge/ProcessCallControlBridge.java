package com.tt.miniapphost.process.bridge;

import com.tt.miniapp.process.interceptor.ISyncCallInterceptor;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.core.IProcessCallControl;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.data.CrossProcessInformation;

public class ProcessCallControlBridge {
  private static IProcessCallControl sProcessCallControl;
  
  public static void callHostProcessAsync(String paramString, CrossProcessDataEntity paramCrossProcessDataEntity, IpcCallback paramIpcCallback) {
    if (!checkValid())
      return; 
    sProcessCallControl.callProcessAsync(new CrossProcessCallEntity("hostProcess", paramString, paramCrossProcessDataEntity), paramIpcCallback);
  }
  
  public static CrossProcessDataEntity callHostProcessSync(String paramString, CrossProcessDataEntity paramCrossProcessDataEntity) {
    return !checkValid() ? null : sProcessCallControl.callProcessSync(new CrossProcessCallEntity("hostProcess", paramString, paramCrossProcessDataEntity));
  }
  
  public static void callMiniAppProcessAsync(String paramString1, String paramString2, CrossProcessDataEntity paramCrossProcessDataEntity, IpcCallback paramIpcCallback) {
    if (!checkValid())
      return; 
    sProcessCallControl.callProcessAsync(new CrossProcessCallEntity(paramString1, paramString2, paramCrossProcessDataEntity), paramIpcCallback);
  }
  
  private static boolean checkValid() {
    // Byte code:
    //   0: getstatic com/tt/miniapphost/process/bridge/ProcessCallControlBridge.sProcessCallControl : Lcom/tt/miniapphost/process/core/IProcessCallControl;
    //   3: ifnonnull -> 37
    //   6: ldc com/tt/miniapphost/process/bridge/ProcessCallControlBridge
    //   8: monitorenter
    //   9: getstatic com/tt/miniapphost/process/bridge/ProcessCallControlBridge.sProcessCallControl : Lcom/tt/miniapphost/process/core/IProcessCallControl;
    //   12: ifnonnull -> 25
    //   15: new com/tt/miniapp/process/core/ProcessCallControl
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putstatic com/tt/miniapphost/process/bridge/ProcessCallControlBridge.sProcessCallControl : Lcom/tt/miniapphost/process/core/IProcessCallControl;
    //   25: ldc com/tt/miniapphost/process/bridge/ProcessCallControlBridge
    //   27: monitorexit
    //   28: goto -> 37
    //   31: astore_0
    //   32: ldc com/tt/miniapphost/process/bridge/ProcessCallControlBridge
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    //   37: iconst_1
    //   38: ireturn
    // Exception table:
    //   from	to	target	type
    //   9	25	31	finally
    //   25	28	31	finally
    //   32	35	31	finally
  }
  
  public static void handleAsyncCall(CrossProcessCallEntity paramCrossProcessCallEntity, int paramInt) {
    if (!checkValid())
      return; 
    sProcessCallControl.handleAsyncCall(paramCrossProcessCallEntity, paramInt);
  }
  
  public static CrossProcessDataEntity handleSyncCall(CrossProcessCallEntity paramCrossProcessCallEntity) {
    return !checkValid() ? null : sProcessCallControl.handleSyncCall(paramCrossProcessCallEntity);
  }
  
  public static void ipcCallback(CrossProcessInformation.CallerProcess paramCallerProcess, CrossProcessDataEntity paramCrossProcessDataEntity, boolean paramBoolean) {
    if (!checkValid())
      return; 
    sProcessCallControl.callback(paramCallerProcess, paramCrossProcessDataEntity, paramBoolean);
  }
  
  public static void setSyncInterceptor(ISyncCallInterceptor paramISyncCallInterceptor) {
    if (!checkValid())
      return; 
    sProcessCallControl.setSyncInterceptor(paramISyncCallInterceptor);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\bridge\ProcessCallControlBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */