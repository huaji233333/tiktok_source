package com.tt.miniapp.shortcut.handler;

import android.content.Context;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.shortcut.ShortcutResult;
import com.tt.miniapphost.AppBrandLogger;
import java.util.ArrayList;

public class PermissionHandler extends AbsHandler {
  PermissionHandler(ShortcutRequest paramShortcutRequest) {
    super(paramShortcutRequest);
  }
  
  protected ShortcutResult handleRequest() {
    if (PermissionsManager.getInstance().hasPermission((Context)this.mAct, "com.android.launcher.permission.INSTALL_SHORTCUT")) {
      AppBrandLogger.d("PermissionHandler", new Object[] { "has shortcut permission create directly" });
      return getNextHandler().handleRequest();
    } 
    AppBrandLogger.d("PermissionHandler", new Object[] { "no shortcut permission request" });
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add("com.android.launcher.permission.INSTALL_SHORTCUT");
    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this.mAct, arrayList.<String>toArray(new String[0]), new PermissionsResultAction() {
          public void onDenied(String param1String) {
            AppBrandLogger.d("PermissionHandler", new Object[] { "denied shortcut permission" });
            PermissionHandler.this.mRequest.callback(new ShortcutResult(ShortcutResult.Result.FAIL, "permission_denied"));
          }
          
          public void onGranted() {
            AppBrandLogger.d("PermissionHandler", new Object[] { "granted shortcut permission" });
            PermissionHandler.this.mRequest.callback(PermissionHandler.this.getNextHandler().handleRequest());
          }
        });
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\handler\PermissionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */