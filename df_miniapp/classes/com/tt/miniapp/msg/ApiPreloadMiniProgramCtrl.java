package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.option.e.e;

public class ApiPreloadMiniProgramCtrl extends b {
  public ApiPreloadMiniProgramCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    AppBrandLogger.d("tma_ApiPreloadMiniappCtrl", new Object[] { this.mArgs });
    HostProcessBridge.preloadMiniApp(this.mArgs, new IpcCallback() {
          public void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            if (param1CrossProcessDataEntity != null) {
              if (param1CrossProcessDataEntity.getBoolean("preload_app_result")) {
                ApiPreloadMiniProgramCtrl.this.callbackOk();
                return;
              } 
              String str = param1CrossProcessDataEntity.getString("preload_app_failed_message");
              ApiPreloadMiniProgramCtrl.this.callbackFail(str);
              return;
            } 
            ApiPreloadMiniProgramCtrl.this.callbackFail("ipc fail");
          }
          
          public void onIpcConnectError() {
            ApiPreloadMiniProgramCtrl.this.callbackFail("ipc fail");
          }
        });
  }
  
  public String getActionName() {
    return "preloadMiniProgram";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiPreloadMiniProgramCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */