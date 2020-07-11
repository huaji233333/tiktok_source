package com.tt.miniapp.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.ss.android.ugc.aweme.keva.d;
import com.tt.option.x.b;

public class HostOptionKVStorageDependImpl implements b {
  public SharedPreferences getSharedPreferences(Context paramContext, String paramString) {
    return d.a(paramContext, paramString, 4);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostOptionKVStorageDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */