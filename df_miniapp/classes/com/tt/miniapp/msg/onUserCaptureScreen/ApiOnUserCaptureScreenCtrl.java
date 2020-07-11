package com.tt.miniapp.msg.onUserCaptureScreen;

import android.app.Activity;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.option.e.e;
import java.util.HashSet;

public class ApiOnUserCaptureScreenCtrl extends b {
  public ApiOnUserCaptureScreenCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail(a.c("activity"));
      return;
    } 
    HashSet<String> hashSet = new HashSet();
    hashSet.add("android.permission.READ_EXTERNAL_STORAGE");
    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity)miniappHostBase, hashSet, new PermissionsResultAction() {
          public void onDenied(String param1String) {
            AppBrandLogger.d("ApiOnUserCaptureScreenCtrl", new Object[] { "requestPermissionsIfNecessaryForResult user denied" });
            ApiOnUserCaptureScreenCtrl.this.callbackFail("system auth deny");
          }
          
          public void onGranted() {
            try {
              OnUserCaptureScreenManager.getInstance().registerOnUserCaptureScreen();
              ApiOnUserCaptureScreenCtrl.this.callbackOk();
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("ApiOnUserCaptureScreenCtrl", new Object[] { "act", exception });
              ApiOnUserCaptureScreenCtrl.this.callbackFail(exception);
              return;
            } 
          }
        });
  }
  
  public String getActionName() {
    return "onUserCaptureScreen";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\onUserCaptureScreen\ApiOnUserCaptureScreenCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */