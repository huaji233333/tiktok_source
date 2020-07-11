package com.tt.miniapp.share;

import android.content.Context;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.option.w.h;
import java.util.List;

public class AutoShareInterceptor {
  public static long mLastClickTime;
  
  private static boolean canAutoShare() {
    List list = SettingsDAO.getListString((Context)AppbrandContext.getInst().getApplicationContext(), new Enum[] { (Enum)Settings.BDP_CLOSE_AUTO_SHARE, (Enum)Settings.BdpCloseAutoShare.APPID_WHITELIST });
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    return (appInfoEntity == null) ? false : list.contains(appInfoEntity.appId);
  }
  
  public static boolean intercept(h paramh) {
    return (AppbrandContext.getInst().isGame() && isScreenRecord(paramh) && isAutoShare() && !canAutoShare());
  }
  
  private static boolean isAutoShare() {
    int i = SettingsDAO.getInt((Context)AppbrandContext.getInst().getApplicationContext(), 800, new Enum[] { (Enum)Settings.BDP_CLOSE_AUTO_SHARE, (Enum)Settings.BdpCloseAutoShare.MAX_CLICK_INTERVAL });
    return (System.currentTimeMillis() - mLastClickTime > i);
  }
  
  private static boolean isScreenRecord(h paramh) {
    return (paramh == null) ? false : (("video".equals(paramh.channel) && paramh.isExtraContainVideoPath));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\share\AutoShareInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */