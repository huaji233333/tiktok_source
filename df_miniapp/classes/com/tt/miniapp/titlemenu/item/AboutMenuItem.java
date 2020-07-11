package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.tt.miniapp.about.AboutActivity;
import com.tt.miniapp.debug.DebugInfoUtil;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.MenuHelper;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;

public class AboutMenuItem extends MenuItemAdapter {
  private MenuItemView mItemView;
  
  public AboutMenuItem(final Activity activity) {
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(activity.getDrawable(2097479729));
    this.mItemView.setLabel(activity.getString(2097741832));
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MenuHelper.mpMenuClickEvent("mp_about_btn_click");
            MenuDialog.inst(activity).dismiss();
            if (!HostDependManager.getInst().startAboutActivity(activity)) {
              Intent intent = new Intent((Context)activity, AboutActivity.class);
              if (AppbrandApplication.getInst().getAppInfo() != null)
                intent.putExtra("appid", (AppbrandApplication.getInst().getAppInfo()).appId); 
              DebugInfoUtil.appendDebugInfo((Context)activity, intent);
              activity.startActivity(intent);
              activity.overridePendingTransition(UIUtils.getSlideInAnimation(), 2131034242);
            } 
          }
        });
  }
  
  public final String getId() {
    return "about";
  }
  
  public final MenuItemView getView() {
    return this.mItemView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\AboutMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */