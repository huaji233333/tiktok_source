package com.tt.miniapp.shortcut.handler;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import com.tt.miniapp.shortcut.ShortcutEntity;
import com.tt.miniapp.shortcut.ShortcutEventReporter;
import com.tt.miniapp.shortcut.ShortcutResult;
import com.tt.miniapp.shortcut.adapter.CustomIconCompat;
import com.tt.miniapp.shortcut.adapter.CustomShortcutInfoCompat;
import com.tt.miniapp.shortcut.adapter.CustomShortcutManagerCompat;
import com.tt.miniapp.toast.ToastManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;

public class AddHandler extends AbsHandler {
  AddHandler(ShortcutRequest paramShortcutRequest) {
    super(paramShortcutRequest);
  }
  
  private boolean add(ShortcutEntity paramShortcutEntity) {
    CustomShortcutInfoCompat customShortcutInfoCompat = buildShortcutInfo((Context)this.mAct, paramShortcutEntity);
    Intent intent = new Intent();
    StringBuilder stringBuilder = new StringBuilder("com.tt.appbrand.shorcut.");
    stringBuilder.append(paramShortcutEntity.getAppId());
    intent.setAction(stringBuilder.toString());
    intent.putExtra("key_request_id", this.mRequest.id);
    PendingIntent pendingIntent = PendingIntent.getBroadcast((Context)this.mAct, 1000, intent, 134217728);
    boolean bool = CustomShortcutManagerCompat.requestPinShortcut((Context)this.mAct, customShortcutInfoCompat, pendingIntent.getIntentSender());
    if (!bool)
      ShortcutEventReporter.reportResult("no", "permission_denied"); 
    AppBrandLogger.d("AddHandler", new Object[] { "addShortCut result", Boolean.valueOf(bool) });
    return bool;
  }
  
  private CustomShortcutInfoCompat buildShortcutInfo(Context paramContext, ShortcutEntity paramShortcutEntity) {
    Intent intent = new Intent();
    paramShortcutEntity.wrapIntent(intent);
    intent.setAction("android.intent.action.VIEW");
    String str = AppbrandContext.getInst().getInitParams().getShortcutClassName();
    try {
      intent.setClass(paramContext, Class.forName(str));
    } catch (ClassNotFoundException classNotFoundException) {
      AppBrandLogger.e("AddHandler", new Object[] { "shortcut launch class not found:", classNotFoundException });
    } 
    PersistableBundle persistableBundle = new PersistableBundle();
    persistableBundle.putString("key_shortcut_id", paramShortcutEntity.getShortcutMd5());
    return (new CustomShortcutInfoCompat.Builder(paramContext, paramShortcutEntity.getAppId())).setIcon(CustomIconCompat.createWithUrl(paramShortcutEntity.getIcon())).setShortLabel(paramShortcutEntity.getLabel()).setExtra(persistableBundle).setIntent(intent).build();
  }
  
  private boolean update(ShortcutEntity paramShortcutEntity) {
    String str = AppbrandContext.getInst().getInitParams().getShortcutClassName();
    return CustomShortcutManagerCompat.updateShortcut((Context)this.mAct, buildShortcutInfo((Context)this.mAct, paramShortcutEntity), str);
  }
  
  protected ShortcutResult handleRequest() {
    if (!this.mRequest.updateShortcut) {
      AppBrandLogger.i("AddHandler", new Object[] { "update shortcut not exist" });
      if (!add(this.mRequest.entity))
        this.mRequest.callback(new ShortcutResult(ShortcutResult.Result.FAIL, "permission_denied")); 
      return getNextHandler().handleRequest();
    } 
    AppBrandLogger.i("AddHandler", new Object[] { "update shortcut need update" });
    ToastManager.showShortToast((Context)this.mAct, this.mAct.getString(2097741851));
    boolean bool = update(this.mRequest.entity);
    StringBuilder stringBuilder = new StringBuilder("update shortcut result:");
    stringBuilder.append(bool);
    AppBrandLogger.i("AddHandler", new Object[] { stringBuilder.toString() });
    ShortcutEventReporter.reportResult("yes", "shortcut is exist but need update");
    return new ShortcutResult(ShortcutResult.Result.SUCCESS, "shortcut is exist but need update");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\handler\AddHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */