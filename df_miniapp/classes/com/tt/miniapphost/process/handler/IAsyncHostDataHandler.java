package com.tt.miniapphost.process.handler;

import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;

public interface IAsyncHostDataHandler {
  void action(CrossProcessDataEntity paramCrossProcessDataEntity, AsyncIpcHandler paramAsyncIpcHandler);
  
  String getType();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\handler\IAsyncHostDataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */