package com.tt.option.x;

import android.content.Context;
import android.content.SharedPreferences;
import com.tt.miniapp.impl.HostOptionKVStorageDependImpl;
import com.tt.option.a;

public final class a extends a<b> implements b {
  public final SharedPreferences getSharedPreferences(Context paramContext, String paramString) {
    return inject() ? ((b)this.defaultOptionDepend).getSharedPreferences(paramContext, paramString) : null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\x\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */