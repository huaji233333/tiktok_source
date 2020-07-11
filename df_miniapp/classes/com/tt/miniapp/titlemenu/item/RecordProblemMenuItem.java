package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.feedback.FeedbackLogHandler;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.MenuHelper;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.HostProcessBridge;

public class RecordProblemMenuItem extends MenuItemAdapter {
  private MenuItemView mItemView;
  
  public RecordProblemMenuItem(final Activity activity) {
    boolean bool;
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setIcon(makeIcon((Context)activity));
    this.mItemView.setLabel(makeLabel((Context)activity));
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (FeedbackLogHandler.getInstance() != null) {
              if (RecordProblemMenuItem.this.isFeedbackSwitchOn()) {
                MenuHelper.mpMenuClickEvent("mp_record_issues_finish_click");
              } else {
                MenuHelper.mpMenuClickEvent("mp_record_issues_start_click");
              } 
              FeedbackLogHandler.getInstance().setSwitchOn(RecordProblemMenuItem.this.isFeedbackSwitchOn() ^ true);
            } 
            MenuDialog.inst(activity).dismiss();
          }
        });
    if (AppbrandApplicationImpl.getInst().getAppInfo().isLocalTest() && HostProcessBridge.isDataHandlerExist("uploadFeedback")) {
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
  
  private Drawable makeIcon(Context paramContext) {
    return isFeedbackSwitchOn() ? paramContext.getDrawable(2097479732) : paramContext.getDrawable(2097479743);
  }
  
  private String makeLabel(Context paramContext) {
    return isFeedbackSwitchOn() ? paramContext.getString(2097741887) : paramContext.getString(2097741991);
  }
  
  public final String getId() {
    return "record_problem";
  }
  
  public MenuItemView getView() {
    return this.mItemView;
  }
  
  public boolean isFeedbackSwitchOn() {
    return (FeedbackLogHandler.getInstance() != null && FeedbackLogHandler.getInstance().getSwitch());
  }
  
  public void onMenuShow() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    this.mItemView.setIcon(makeIcon((Context)application));
    this.mItemView.setLabel(makeLabel((Context)application));
    super.onMenuShow();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\RecordProblemMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */