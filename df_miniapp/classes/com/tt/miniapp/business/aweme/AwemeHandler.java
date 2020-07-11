package com.tt.miniapp.business.aweme;

import com.bytedance.sandboxapp.protocol.service.c.c;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import d.f.b.g;
import d.f.b.l;

public final class AwemeHandler implements IAsyncHostDataHandler {
  public static final Companion Companion = new Companion(null);
  
  public static AsyncIpcHandler asyncIpcHandler;
  
  public final void action(CrossProcessDataEntity paramCrossProcessDataEntity, AsyncIpcHandler paramAsyncIpcHandler) {
    l.b(paramAsyncIpcHandler, "asyncIpcHandler");
    if (paramCrossProcessDataEntity == null) {
      onFailure(paramAsyncIpcHandler, -1, "callData is null");
      return;
    } 
    int i = paramCrossProcessDataEntity.getInt("aweme_action");
    String str2 = paramCrossProcessDataEntity.getString("aweme_uid");
    String str1 = paramCrossProcessDataEntity.getString("aweme_sec_uid");
    if (i != 1) {
      if (i != 2) {
        onFailure(paramAsyncIpcHandler, -1, "unknown action");
        return;
      } 
      asyncIpcHandler = paramAsyncIpcHandler;
      return;
    } 
    HostDependManager.getInst().checkFollowAwemeState(str2, str1, new AwemeHandler$action$1(paramAsyncIpcHandler));
  }
  
  public final String getType() {
    return "awemeHandler";
  }
  
  public final void onFailure(AsyncIpcHandler paramAsyncIpcHandler, int paramInt, String paramString) {
    if (paramAsyncIpcHandler != null)
      paramAsyncIpcHandler.callback(CrossProcessDataEntity.Builder.create().put("aweme_result", Integer.valueOf(-1)).put("aweme_error_code", Integer.valueOf(paramInt)).put("aweme_error_msg", paramString).build(), true); 
  }
  
  public static final class Companion {
    private Companion() {}
    
    public final void notifyFollowAwemeState(AsyncIpcHandler param1AsyncIpcHandler, Boolean param1Boolean) {
      if (param1AsyncIpcHandler != null)
        param1AsyncIpcHandler.callback(CrossProcessDataEntity.Builder.create().put("aweme_result", Integer.valueOf(0)).put("aweme_has_followed", param1Boolean).build(), true); 
    }
    
    public final void notifyFollowAwemeState(boolean param1Boolean) {
      notifyFollowAwemeState(AwemeHandler.asyncIpcHandler, Boolean.valueOf(param1Boolean));
      AwemeHandler.asyncIpcHandler = null;
    }
  }
  
  public static final class AwemeHandler$action$1 implements c {
    AwemeHandler$action$1(AsyncIpcHandler param1AsyncIpcHandler) {}
    
    public final void onFailure(int param1Int, String param1String) {
      AwemeHandler.this.onFailure(this.$asyncIpcHandler, param1Int, param1String);
    }
    
    public final void onFollowAwemeResult(Boolean param1Boolean) {
      AwemeHandler.Companion.notifyFollowAwemeState(this.$asyncIpcHandler, param1Boolean);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\aweme\AwemeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */