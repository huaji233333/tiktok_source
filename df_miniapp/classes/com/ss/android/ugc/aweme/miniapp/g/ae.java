package com.ss.android.ugc.aweme.miniapp.g;

import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;

public final class ae implements IAsyncHostDataHandler {
  public final void action(CrossProcessDataEntity paramCrossProcessDataEntity, AsyncIpcHandler paramAsyncIpcHandler) {
    long l1 = System.currentTimeMillis() / 1000L;
    long l2 = System.currentTimeMillis() / 1000L;
    MiniAppService.inst().getBaseLibDepend().a(l1 - 10800L, l2);
    if (paramAsyncIpcHandler != null)
      paramAsyncIpcHandler.callback(null); 
  }
  
  public final String getType() {
    return "uploadAlog";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\ae.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */