package com.tt.miniapphost.process.handler;

import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public interface ISyncHostDataHandler {
  CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity);
  
  String getType();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\handler\ISyncHostDataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */