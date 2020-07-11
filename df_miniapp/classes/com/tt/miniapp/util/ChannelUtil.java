package com.tt.miniapp.util;

import android.text.TextUtils;
import com.tt.miniapphost.AppbrandContext;

public class ChannelUtil {
  public static boolean isBetaCp() {
    return (AppbrandContext.getInst().getInitParams() != null) ? TextUtils.equals(AppbrandContext.getInst().getInitParams().getChannel(), "feature_beta_tmg_cp") : false;
  }
  
  public static boolean isLocalTest() {
    return (AppbrandContext.getInst().getInitParams() != null) ? TextUtils.equals(AppbrandContext.getInst().getInitParams().getChannel(), "local_test") : false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\ChannelUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */