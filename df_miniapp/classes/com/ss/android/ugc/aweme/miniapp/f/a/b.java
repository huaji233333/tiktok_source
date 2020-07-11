package com.ss.android.ugc.aweme.miniapp.f.a;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import com.tt.miniapphost.util.DebugUtil;

public final class b implements IAsyncHostDataHandler {
  public b(Context paramContext) {}
  
  public final void action(CrossProcessDataEntity paramCrossProcessDataEntity, AsyncIpcHandler paramAsyncIpcHandler) {
    if (paramCrossProcessDataEntity == null) {
      DebugUtil.outputError("HostActionAsyncHandler", new Object[] { "callData == null" });
      return;
    } 
    String str = paramCrossProcessDataEntity.getString("hostActionType");
    paramCrossProcessDataEntity.getCrossProcessDataEntity("hostActionData");
    if (TextUtils.isEmpty(str))
      DebugUtil.outputError("HostActionAsyncHandler", new Object[] { "TextUtils.isEmpty(hostCallType)" }); 
  }
  
  public final String getType() {
    return "hostActionAsync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */