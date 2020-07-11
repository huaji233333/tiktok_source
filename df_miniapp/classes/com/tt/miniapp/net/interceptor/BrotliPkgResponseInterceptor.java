package com.tt.miniapp.net.interceptor;

import com.tt.miniapp.dec.BrotliInputStream;
import com.tt.miniapphost.AppBrandLogger;
import g.q;
import g.z;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.af;
import okhttp3.internal.c.e;
import okhttp3.internal.c.h;
import okhttp3.u;

public class BrotliPkgResponseInterceptor implements u {
  public ae intercept(u.a parama) throws IOException {
    ac ac = parama.a();
    ac.a a2 = ac.a();
    a2.b("Accept-Encoding");
    a2.b("Accept-Encoding", "identity");
    ae ae = parama.a(a2.c());
    ae.a a1 = ae.b().a(ac);
    String str = ae.b("Content-Type");
    if (e.b(ae)) {
      AppBrandLogger.i("tma_BrPkgResponseInterceptor", new Object[] { "use brotli to unCompress pkg" });
      BrotliInputStream brotliInputStream = new BrotliInputStream(ae.g.source().f());
      brotliInputStream.setEager(true);
      z z = q.a((InputStream)brotliInputStream);
      a1.a(ae.f.d().b("Content-Encoding").b("Content-Length").a());
      a1.a((af)new h(str, -1L, q.a(z)));
    } 
    return a1.a();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\interceptor\BrotliPkgResponseInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */