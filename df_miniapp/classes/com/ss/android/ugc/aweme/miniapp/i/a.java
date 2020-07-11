package com.ss.android.ugc.aweme.miniapp.i;

import android.app.Activity;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.callback.IpcCallbackManagerProxy;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import org.json.JSONObject;

public final class a extends NativeModule {
  int a;
  
  boolean b;
  
  public a(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public final String getName() {
    return "logclient";
  }
  
  public final <T> String invoke(String paramString, NativeModule.NativeModuleCallback<T> paramNativeModuleCallback) throws Exception {
    Activity activity = getCurrentActivity();
    if (activity != null) {
      MiniAppService.inst().getBaseLibDepend().a(activity);
      this.b = true;
      HostProcessBridge.hostActionSync("login_state", CrossProcessDataEntity.Builder.create().put("login_state_value", "1").build());
      try {
        this.a = (new JSONObject(paramString)).optInt("hostCallId");
      } catch (Exception exception) {}
    } 
    return "";
  }
  
  public final void onStart() {
    super.onStart();
    if (AppBrandLogger.debug())
      AppBrandLogger.d("LoginClient", new Object[] { "onStart" }); 
    if (this.b) {
      this.b = false;
      HostProcessBridge.hostActionSync("login_state", CrossProcessDataEntity.Builder.create().put("login_state_value", "0").build());
      IpcCallbackManagerProxy.getInstance().handleIpcCallBack(this.a, CrossProcessDataEntity.Builder.create().build());
      this.a = 0;
    } 
  }
  
  public final void onStop() {
    super.onStop();
    if (AppBrandLogger.debug())
      AppBrandLogger.d("LoginClient", new Object[] { "onStop" }); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\i\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */