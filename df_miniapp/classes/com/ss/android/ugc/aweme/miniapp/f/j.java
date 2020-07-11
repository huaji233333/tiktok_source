package com.ss.android.ugc.aweme.miniapp.f;

import android.content.Context;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public class j {
  public static final String a = j.class.getSimpleName();
  
  public static String a(Context paramContext) {
    paramContext.getApplicationContext();
    try {
      return HostProcessBridge.hostActionSync("share_info_params", CrossProcessDataEntity.Builder.create().build()).getString("share_info_value");
    } catch (Exception exception) {
      AppBrandLogger.e(a, new Object[] { "getShareParams", exception });
      return "";
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */