package com.tt.miniapp.titlemenu.item;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlemenu.MenuDialog;
import com.tt.miniapp.titlemenu.view.MenuItemView;
import com.tt.miniapp.toast.ToastManager;
import com.tt.miniapp.util.ClipboardManagerUtil;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.AppbrandContext;

public class TimelineGraphMenuItem extends MenuItemAdapter {
  MenuItemView mItemView;
  
  public TimelineGraphMenuItem(final Activity activity) {
    final MpTimeLineReporter timeLineReporter = (MpTimeLineReporter)AppbrandApplicationImpl.getInst().getService(MpTimeLineReporter.class);
    this.mItemView = new MenuItemView((Context)activity);
    this.mItemView.setLabel("生成时序图");
    this.mItemView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            timeLineReporter.reportTimelineGraph(new MpTimeLineReporter.Callback<String>() {
                  public void onError(final String errMsg) {
                    ThreadUtil.runOnUIThread(new Runnable() {
                          public void run() {
                            ToastManager.Toast.makeText(activity, errMsg, 0L, "fail").show();
                          }
                        });
                  }
                  
                  public void onSuccess(String param2String) {
                    ClipboardManagerUtil.set(param2String, (Context)activity);
                    ThreadUtil.runOnUIThread(new Runnable() {
                          public void run() {
                            ToastManager.Toast.makeText(activity, "已拷贝至剪贴板", 0L, "success").show();
                          }
                        });
                  }
                });
            MenuDialog.inst(activity).dismiss();
          }
        });
    showTimeLine();
  }
  
  private void showTimeLine() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    Settings settings = Settings.TT_TIMELINE_SWITCH;
    byte b = 0;
    boolean bool = SettingsDAO.getBoolean((Context)application, false, new Enum[] { (Enum)settings, (Enum)Settings.TimeLineSwitch.SWITCH });
    MenuItemView menuItemView = this.mItemView;
    if (!bool)
      b = 8; 
    menuItemView.setVisibility(b);
  }
  
  public String getId() {
    return "timeline_graph";
  }
  
  public MenuItemView getView() {
    return this.mItemView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\item\TimelineGraphMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */