package com.tt.miniapp.msg.shortcut;

import android.app.Activity;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.shortcut.ShortcutEntity;
import com.tt.miniapp.shortcut.ShortcutEventReporter;
import com.tt.miniapp.shortcut.ShortcutService;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.option.e.e;

public class ApiAddShortcutCtrl extends b {
  public ApiAddShortcutCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    ShortcutEventReporter.reportClick("api");
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null && TextUtils.isEmpty(initParamsEntity.getShortcutClassName())) {
      AppBrandLogger.e("ApiAddShortcutCtrl", new Object[] { "shortcut launch activty not config" });
      callbackFail("feature is not supported in app");
      ShortcutEventReporter.reportResult("no", "feature is not supported in app");
      return;
    } 
    ShortcutService shortcutService = (ShortcutService)AppbrandApplicationImpl.getInst().getService(ShortcutService.class);
    if (shortcutService == null) {
      AppBrandLogger.e("ApiAddShortcutCtrl", new Object[] { "shortcut service null" });
      callbackFail("shortcut manager is null");
      ShortcutEventReporter.reportResult("no", "shortcut manager is null");
      return;
    } 
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity == null) {
      AppBrandLogger.e("ApiAddShortcutCtrl", new Object[] { "appInfo null" });
      callbackFail("appInfo is null");
      ShortcutEventReporter.reportResult("no", "appInfo is null");
      return;
    } 
    ShortcutEntity shortcutEntity = (new ShortcutEntity.Builder()).setAppId(appInfoEntity.appId).setAppType(appInfoEntity.type).setIcon(appInfoEntity.icon).setLabel(appInfoEntity.appName).build();
    shortcutService.tryToAddShortcut((Activity)AppbrandContext.getInst().getCurrentActivity(), shortcutEntity);
    callbackOk();
  }
  
  public String getActionName() {
    return "addShortcut";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\shortcut\ApiAddShortcutCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */