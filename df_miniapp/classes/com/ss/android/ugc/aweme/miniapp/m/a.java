package com.ss.android.ugc.aweme.miniapp.m;

import android.app.Activity;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import org.json.JSONObject;

public final class a extends NativeModule {
  public a(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public final String getName() {
    return "DMTshowToast";
  }
  
  public final String invoke(String paramString, NativeModule.NativeModuleCallback paramNativeModuleCallback) throws Exception {
    JSONObject jSONObject = new JSONObject(paramString);
    int i = jSONObject.optInt("duration");
    String str = jSONObject.optString("title");
    AppbrandContext.mainHandler.post(new Runnable(this, str, i, paramNativeModuleCallback) {
          public final void run() {
            Activity activity = this.d.getCurrentActivity();
            if (activity != null) {
              MiniAppService.inst().getPopToastDepend().a(activity, this.a, this.b, 17);
              NativeModule.NativeModuleCallback nativeModuleCallback = this.c;
              if (nativeModuleCallback != null)
                nativeModuleCallback.onNativeModuleCall("ok"); 
            } 
          }
        });
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\m\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */