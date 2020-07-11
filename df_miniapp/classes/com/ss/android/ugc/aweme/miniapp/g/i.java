package com.ss.android.ugc.aweme.miniapp.g;

import android.content.Context;
import android.content.SharedPreferences;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.a.c;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.x.b;

public class i implements b {
  public SharedPreferences getSharedPreferences(Context paramContext, String paramString) {
    AppBrandLogger.i("HostOptionKVStorageDependImpl", new Object[] { "Use KEVA as KV Storage" });
    c c = MiniAppService.inst().getBaseLibDepend();
    return (c != null) ? c.c(paramContext, paramString) : null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */