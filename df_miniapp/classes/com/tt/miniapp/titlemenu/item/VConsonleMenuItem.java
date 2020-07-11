package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.adsite.AdSiteManager;
import com.tt.miniapp.debug.SwitchManager;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.MenuHelper;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.ProcessUtil;

public class VConsonleMenuItem extends MenuItemAdapter {
  private final Activity mActivity;
  
  private MenuItemView mItemView;
  
  public SwitchManager mSwitchManager = (SwitchManager)AppbrandApplicationImpl.getInst().getService(SwitchManager.class);
  
  public VConsonleMenuItem(final Activity activity) {
    this.mActivity = activity;
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(activity.getDrawable(2097479744));
    this.mItemView.setLabel(makeLabel((Context)activity));
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (VConsonleMenuItem.this.mSwitchManager.isVConsoleSwitchOn()) {
              MenuHelper.mpMenuClickEvent("mp_debug_close_click");
              VConsonleMenuItem.this.mSwitchManager.setVConsoleSwitchOn((Context)activity, false);
            } else {
              MenuHelper.mpMenuClickEvent("mp_debug_open_click");
              VConsonleMenuItem.this.mSwitchManager.setVConsoleSwitchOn((Context)activity, true);
            } 
            if (AppProcessManager.isHostProcessExist((Context)AppbrandContext.getInst().getApplicationContext())) {
              InnerHostProcessBridge.restartApp((AppbrandApplicationImpl.getInst().getAppInfo()).appId, AppbrandApplicationImpl.getInst().getSchema());
              MenuDialog.inst(activity).dismiss();
              return;
            } 
            ProcessUtil.killCurrentMiniAppProcess((Context)activity);
          }
        });
    if (AppbrandApplicationImpl.getInst().getAppInfo().isLocalTest() && !AdSiteManager.getInstance().isAdSiteBrowser()) {
      this.mItemView.setVisibility(0);
      return;
    } 
    this.mItemView.setVisibility(8);
  }
  
  private String makeLabel(Context paramContext) {
    return this.mSwitchManager.isVConsoleSwitchOn() ? paramContext.getString(2097741875) : paramContext.getString(2097741962);
  }
  
  public final String getId() {
    return "v_consonle";
  }
  
  public MenuItemView getView() {
    return this.mItemView;
  }
  
  public void onMenuShow() {
    this.mItemView.setLabel(makeLabel((Context)this.mActivity));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\VConsonleMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */