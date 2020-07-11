package com.ss.android.ugc.aweme.miniapp.f;

import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;

public final class d implements ISyncHostDataHandler {
  public final CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity) {
    String str = MiniAppService.inst().getBaseLibDepend().g();
    return CrossProcessDataEntity.Builder.create().put("loginCookie", str).build();
  }
  
  public final String getType() {
    return "getLoginCookie";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */