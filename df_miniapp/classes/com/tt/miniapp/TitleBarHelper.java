package com.tt.miniapp;

import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;

public class TitleBarHelper {
  public static boolean isBackButtonStyle() {
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    return (appInfoEntity != null && appInfoEntity.toolbarStyle == 1 && AppbrandContext.getInst().isGame());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\TitleBarHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */