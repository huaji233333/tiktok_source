package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapphost.AppbrandContext;

public class RestartMiniAppMenuItem extends MenuItemAdapter {
  private Activity mActivity;
  
  private MenuItemView mItemView;
  
  public RestartMiniAppMenuItem(final Activity activity) {
    this.mActivity = activity;
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(activity.getDrawable(2097479738));
    this.mItemView.setLabel(makeLabel());
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Event.builder("mp_restart_miniapp").flush();
            InnerHostProcessBridge.restartApp((AppbrandApplicationImpl.getInst().getAppInfo()).appId, AppbrandApplicationImpl.getInst().getSchema());
            MenuDialog.inst(activity).dismiss();
          }
        });
  }
  
  private String makeLabel() {
    return AppbrandContext.getInst().isGame() ? this.mActivity.getString(2097742011) : this.mActivity.getString(2097742012);
  }
  
  public final String getId() {
    return "restart_mini_app";
  }
  
  public final MenuItemView getView() {
    return this.mItemView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\RestartMiniAppMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */