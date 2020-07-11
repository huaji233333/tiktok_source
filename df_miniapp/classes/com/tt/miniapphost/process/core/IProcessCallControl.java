package com.tt.miniapphost.process.core;

import com.tt.miniapp.process.interceptor.ISyncCallInterceptor;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessCallEntity;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.data.CrossProcessInformation;

public interface IProcessCallControl {
  void callProcessAsync(CrossProcessCallEntity paramCrossProcessCallEntity, IpcCallback paramIpcCallback);
  
  CrossProcessDataEntity callProcessSync(CrossProcessCallEntity paramCrossProcessCallEntity);
  
  void callback(CrossProcessInformation.CallerProcess paramCallerProcess, CrossProcessDataEntity paramCrossProcessDataEntity, boolean paramBoolean);
  
  void handleAsyncCall(CrossProcessCallEntity paramCrossProcessCallEntity, int paramInt);
  
  CrossProcessDataEntity handleSyncCall(CrossProcessCallEntity paramCrossProcessCallEntity);
  
  void setSyncInterceptor(ISyncCallInterceptor paramISyncCallInterceptor);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\core\IProcessCallControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */