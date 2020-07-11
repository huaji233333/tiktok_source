package com.ss.android.ugc.aweme.miniapp.l;

import com.ss.android.ugc.aweme.miniapp_api.a.q;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.q.k;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public final class a {
  public static final class a implements k {
    private final q a;
    
    public a(q param1q) {
      this.a = param1q;
    }
    
    public final void a(Map<String, String> param1Map1, Map<String, String> param1Map2, List<String> param1List, boolean param1Boolean1, boolean param1Boolean2) {
      this.a.a(param1Map1, param1Map2, param1List, false, false);
    }
    
    public final boolean a() {
      return this.a.a();
    }
    
    public final boolean a(byte[] param1ArrayOfbyte, int param1Int) {
      q q1;
      try {
        q1 = this.a;
        byte b = 2;
        if (param1Int != 1) {
          if (param1Int != 2)
            b = 0; 
          return q1.a(param1ArrayOfbyte, b);
        } 
      } catch (Exception exception) {
        AppBrandLogger.e("TmaWsClientImpl", new Object[] { exception });
        return false;
      } 
      boolean bool = true;
      return q1.a((byte[])exception, bool);
    }
    
    public final void b() {
      this.a.b();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\l\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */