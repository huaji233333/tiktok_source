package com.tt.miniapp.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.host.HostDependManager;
import org.json.JSONObject;

public class ToastImpl extends NativeModule {
  public ToastImpl(AppbrandContext paramAppbrandContext) {
    super(paramAppbrandContext);
  }
  
  public String getName() {
    return "showToast";
  }
  
  public String invoke(final String title, final NativeModule.NativeModuleCallback nativeModuleCallback) throws Exception {
    JSONObject jSONObject = new JSONObject(title);
    final long duration = jSONObject.optLong("duration");
    title = jSONObject.optString("title");
    final String icon = jSONObject.optString("icon");
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            Application application;
            Activity activity = ToastImpl.this.getCurrentActivity();
            if (activity == null)
              application = AppbrandContext.getInst().getApplicationContext(); 
            long l = duration;
            if (l <= 0L)
              l = 1500L; 
            HostDependManager.getInst().showToast((Context)application, null, title, l, icon);
            NativeModule.NativeModuleCallback nativeModuleCallback = nativeModuleCallback;
            if (nativeModuleCallback != null)
              nativeModuleCallback.onNativeModuleCall("ok"); 
          }
        });
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\toast\ToastImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */