package com.tt.miniapphost.process.callback;

import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public interface IIpcCallbackManager {
  void handleIpcCallBack(int paramInt, CrossProcessDataEntity paramCrossProcessDataEntity);
  
  void onCallProcessDead(String paramString);
  
  void registerIpcCallback(IpcCallback paramIpcCallback);
  
  void unregisterIpcCallback(int paramInt);
  
  void unregisterIpcCallback(IpcCallback paramIpcCallback);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\callback\IIpcCallbackManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */