package com.tt.miniapp.net.interceptor;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppbrandContext;
import g.n;
import g.q;
import g.z;
import java.io.IOException;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.af;
import okhttp3.internal.c.e;
import okhttp3.internal.c.h;
import okhttp3.u;
import org.json.JSONObject;

public class PkgResponseInterceptor implements u {
  private static boolean isGetResponseFromOffsetWithGzip(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    JSONObject jSONObject = SettingsDAO.getJSONObject((Context)AppbrandContext.getInst().getApplicationContext(), new Enum[] { (Enum)Settings.BDP_TTPKG_CONFIG, (Enum)Settings.BdpTtPkgConfig.HOSTS_ADD_GZIP });
    return (jSONObject == null) ? false : jSONObject.optBoolean(paramString, false);
  }
  
  public ae intercept(u.a parama) throws IOException {
    ac ac = parama.a();
    ac.a a1 = ac.a();
    if (ac.a("Range") != null) {
      boolean bool1;
      if (isGetResponseFromOffsetWithGzip(ac.a.d)) {
        bool1 = true;
        a1.b("Accept-Encoding", "gzip");
      } else {
        a1.b("Accept-Encoding", "identity");
        bool1 = false;
      } 
      ae ae = parama.a(a1.c());
      ae.a a2 = ae.b().a(ac);
      String str2 = ae.b("Content-Encoding");
      String str1 = ae.b("Content-Type");
      if (bool1 && "gzip".equalsIgnoreCase(str2) && e.b(ae)) {
        n n = new n((z)ae.g.source());
        a2.a(ae.f.d().b("Content-Encoding").b("Content-Length").a());
        a2.a((af)new h(str1, -1L, q.a((z)n)));
      } 
      return a2.a();
    } 
    boolean bool = false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\interceptor\PkgResponseInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */