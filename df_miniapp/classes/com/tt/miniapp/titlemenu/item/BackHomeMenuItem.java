package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.adsite.AdSiteManager;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.MenuHelper;
import com.tt.miniapp.titlemenu.view.MenuItemView;

public class BackHomeMenuItem extends MenuItemAdapter {
  private MenuItemView mItemView;
  
  public BackHomeMenuItem(final Activity activity) {
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(activity.getDrawable(2097479730));
    this.mItemView.setLabel(activity.getString(2097741863));
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            BackHomeMenuItem.this.backHome(activity);
          }
        });
    if (AdSiteManager.getInstance().isAdSiteBrowser()) {
      this.mItemView.setVisibility(8);
      return;
    } 
    this.mItemView.setVisibility(0);
  }
  
  public void backHome(Activity paramActivity) {
    MenuHelper.mpMenuClickEvent("mp_home_btn_click");
    MenuDialog.inst(paramActivity).dismiss();
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      if (TextUtils.isEmpty(appConfig.mEntryPath))
        return; 
      ((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).reLaunchByUrl(appConfig.mEntryPath);
    } 
  }
  
  public final String getId() {
    return "back_home";
  }
  
  public final MenuItemView getView() {
    return this.mItemView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\BackHomeMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */