package com.tt.miniapp.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import com.tt.miniapphost.host.HostDependManager;

public class KVUtil {
  public static SharedPreferences getSharedPreferences(Context paramContext, String paramString) {
    if (paramContext == null)
      return new NullPointerSafeSP(); 
    HostDependManager hostDependManager = HostDependManager.getInst();
    StringBuilder stringBuilder = new StringBuilder("com.tt.miniapp.shared_prefs_prefix_");
    stringBuilder.append(paramString);
    return new MigrationSharedPreferences(hostDependManager.getSharedPreferences(paramContext, stringBuilder.toString()), MMKVUtil.getSPByName(paramContext, paramString));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\mmkv\KVUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */