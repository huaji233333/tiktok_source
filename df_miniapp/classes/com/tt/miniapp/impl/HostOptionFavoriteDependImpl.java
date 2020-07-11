package com.tt.miniapp.impl;

import android.app.Application;
import android.content.Context;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.g.b;
import com.tt.option.g.c;

public class HostOptionFavoriteDependImpl implements c {
  public void firstFavoriteAction() {
    String str;
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (AppbrandContext.getInst().isGame()) {
      str = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)application)).h;
    } else {
      str = (HostDependManager.getInst().getHostCustomFavoriteEntity((Context)application)).g;
    } 
    HostDependManager.getInst().showToast((Context)application, null, str, 0L, "success");
  }
  
  public b getHostCustomFavoriteEntity(Context paramContext) {
    return (new b.a(paramContext)).a();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostOptionFavoriteDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */