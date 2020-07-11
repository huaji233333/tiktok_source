package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.tt.miniapp.permission.PermissionSettingActivity;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.MenuHelper;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;

public class SettingsMenuItem extends MenuItemAdapter {
  private View.OnClickListener mClickListener;
  
  private MenuItemView mItemView;
  
  public SettingsMenuItem(final Activity activity) {
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(activity.getDrawable(2097479740));
    this.mItemView.setLabel(activity.getString(2097742025));
    MenuItemView menuItemView = this.mItemView;
    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View param1View) {
          MenuHelper.mpMenuClickEvent("mp_settings_btn_click");
          MenuDialog.inst(activity).dismiss();
          Activity activity = activity;
          activity.startActivityForResult(PermissionSettingActivity.genIntent((Context)activity), 5);
          activity.overridePendingTransition(UIUtils.getSlideInAnimation(), 2131034242);
        }
      };
    this.mClickListener = onClickListener;
    menuItemView.setOnClickListener(onClickListener);
    if (HostDependManager.getInst().needSettingTitleMenuItem()) {
      getView().setVisibility(0);
      return;
    } 
    getView().setVisibility(8);
  }
  
  public View.OnClickListener getClickListener() {
    return this.mClickListener;
  }
  
  public final String getId() {
    return "settings";
  }
  
  public final MenuItemView getView() {
    return this.mItemView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\SettingsMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */