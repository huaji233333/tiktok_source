package com.tt.miniapphost.process.helper;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.data.CrossProcessInformation;

public class AsyncIpcHandler {
  private final CrossProcessInformation.CallerProcess mCallerProcess;
  
  public AsyncIpcHandler(CrossProcessInformation.CallerProcess paramCallerProcess) {
    this.mCallerProcess = paramCallerProcess;
  }
  
  public void callback(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity != null) {
      StringBuilder stringBuilder = new StringBuilder("host process callback to miniapp process with data ");
      stringBuilder.append(paramCrossProcessDataEntity.toString());
      AppBrandLogger.d("AsyncIpcHandler", new Object[] { stringBuilder.toString() });
    } 
    callback(paramCrossProcessDataEntity, false);
  }
  
  public void callback(CrossProcessDataEntity paramCrossProcessDataEntity, boolean paramBoolean) {
    CrossProcessInformation.CallerProcess callerProcess = this.mCallerProcess;
    if (callerProcess == null) {
      AppBrandLogger.e("AsyncIpcHandler", new Object[] { "mCallerProcess == null", paramCrossProcessDataEntity });
      return;
    } 
    ProcessCallControlBridge.ipcCallback(callerProcess, paramCrossProcessDataEntity, paramBoolean);
  }
  
  public CrossProcessInformation.CallerProcess getCallerProcess() {
    return this.mCallerProcess;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\helper\AsyncIpcHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */