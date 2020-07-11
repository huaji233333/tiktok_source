package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.debug.SwitchManager;
import com.tt.miniapp.monitor.MiniAppPerformanceDialog;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.MenuHelper;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapphost.AppbrandContext;

public class SeeProfileMenuItem extends MenuItemAdapter {
  public Activity mActivity;
  
  private MenuItemView mItemView;
  
  public SeeProfileMenuItem(Activity paramActivity) {
    boolean bool;
    this.mActivity = paramActivity;
    this.mItemView = new MenuItemView((Context)paramActivity);
    this.mItemView.setIcon(paramActivity.getDrawable(2097479739));
    if (AppbrandApplicationImpl.getInst().getAppInfo().isLocalTest() && !AppbrandContext.getInst().isGame()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      this.mItemView.setVisibility(0);
      return;
    } 
    this.mItemView.setVisibility(8);
  }
  
  public final String getId() {
    return "see_profile";
  }
  
  public MenuItemView getView() {
    return this.mItemView;
  }
  
  public void onMenuShow() {
    super.onMenuShow();
    final boolean isSwitchOn = ((SwitchManager)AppbrandApplicationImpl.getInst().getService(SwitchManager.class)).isPerformanceSwitchOn();
    if (bool) {
      this.mItemView.setLabel(this.mActivity.getString(2097741876));
    } else {
      this.mItemView.setLabel(this.mActivity.getString(2097742018));
    } 
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            MenuHelper.mpMenuClickEvent("mp_performance_data_show_click");
            if (!isSwitchOn) {
              MiniAppPerformanceDialog.showPerformanceDialog((Context)SeeProfileMenuItem.this.mActivity, new MiniAppPerformanceDialog.IDismissCallback() {
                    public void onDismiss() {
                      MenuHelper.mpMenuClickEvent("mp_performance_data_close_click");
                    }
                  });
            } else {
              MiniAppPerformanceDialog.dismissDialog();
            } 
            MenuDialog.inst(SeeProfileMenuItem.this.mActivity).dismiss();
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\SeeProfileMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */