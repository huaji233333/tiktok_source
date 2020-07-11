package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.shortcut.ShortcutEntity;
import com.tt.miniapp.shortcut.ShortcutEventReporter;
import com.tt.miniapp.shortcut.ShortcutService;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;

public class ShortcutMenuItem extends MenuItemAdapter {
  private MenuItemView mItemView;
  
  public ShortcutMenuItem(final Activity activity) {
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(activity.getDrawable(2097479742));
    this.mItemView.setLabel(activity.getString(2097741850));
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MenuDialog.inst(activity).dismiss();
            ShortcutEventReporter.reportClick("user");
            AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
            AppInfoEntity appInfoEntity = appbrandApplicationImpl.getAppInfo();
            if (appInfoEntity == null) {
              AppBrandLogger.i("ShortcutMenuItem", new Object[] { "shortcut fail appinfo is null" });
              ShortcutEventReporter.reportResult("no", "appInfo is null");
              return;
            } 
            ShortcutEntity shortcutEntity = (new ShortcutEntity.Builder()).setAppId(appInfoEntity.appId).setIcon(appInfoEntity.icon).setLabel(appInfoEntity.appName).setAppType(appInfoEntity.type).build();
            ((ShortcutService)appbrandApplicationImpl.getService(ShortcutService.class)).tryToAddShortcut(activity, shortcutEntity);
          }
        });
    if (!TextUtils.isEmpty(AppbrandContext.getInst().getInitParams().getShortcutClassName())) {
      this.mItemView.setVisibility(0);
      return;
    } 
    this.mItemView.setVisibility(8);
  }
  
  public final String getId() {
    return "generate_shortcut";
  }
  
  public MenuItemView getView() {
    return this.mItemView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\ShortcutMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */