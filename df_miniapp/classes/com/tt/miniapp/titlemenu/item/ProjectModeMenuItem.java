package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.tt.miniapp.settings.ProjectSettingsActivity;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.MenuHelper;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapp.util.ChannelUtil;
import com.tt.miniapphost.util.UIUtils;

public class ProjectModeMenuItem extends MenuItemAdapter {
  private MenuItemView mItemView;
  
  public ProjectModeMenuItem(final Activity activity) {
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(activity.getDrawable(2097479736));
    this.mItemView.setLabel(activity.getString(2097741883));
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MenuHelper.mpMenuClickEvent("mp_engineering_mode_click");
            Activity activity = activity;
            activity.startActivity(new Intent(ProjectSettingsActivity.genIntent((Context)activity)));
            activity.overridePendingTransition(UIUtils.getSlideInAnimation(), 2131034242);
            MenuDialog.inst(activity).dismiss();
          }
        });
    if (ChannelUtil.isLocalTest()) {
      this.mItemView.setVisibility(0);
      return;
    } 
    this.mItemView.setVisibility(8);
  }
  
  public final String getId() {
    return "project_mode";
  }
  
  public MenuItemView getView() {
    return this.mItemView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\ProjectModeMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */