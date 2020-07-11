package com.tt.option.v;

import android.content.Context;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.util.List;

public final class c {
  private static boolean a;
  
  private List<String> b;
  
  private List<String> c;
  
  private c() {
    if (AppbrandContext.getInst().getApplicationContext() != null) {
      this.b = SettingsDAO.getListString((Context)AppbrandContext.getInst().getApplicationContext(), new Enum[] { (Enum)Settings.BDP_LAUNCH_APP_SCENE_LIST, (Enum)Settings.BdpLaunchSceneList.WHITE_LIST });
      this.c = SettingsDAO.getListString((Context)AppbrandContext.getInst().getApplicationContext(), new Enum[] { (Enum)Settings.BDP_LAUNCH_APP_SCENE_LIST, (Enum)Settings.BdpLaunchSceneList.GRAY_LIST });
    } 
  }
  
  public static c a() {
    return a.a;
  }
  
  public final boolean b() {
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity != null)
      if (this.b.contains(appInfoEntity.scene)) {
        a = true;
      } else if (!this.c.contains(appInfoEntity.scene)) {
        a = false;
      }  
    return a;
  }
  
  static final class a {
    public static c a = new c();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\v\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */