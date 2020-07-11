package com.tt.miniapp.net.interceptor;

import com.tt.miniapp.dec.BrotliInputStream;
import g.n;
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

public class EncodeResponseInterceptor implements u {
  public ae intercept(u.a parama) throws IOException {
    z z;
    ac ac = parama.a();
    ac.a a2 = ac.a();
    a2.b("Accept-Encoding", "br, gzip");
    ae ae = parama.a(a2.c());
    ae.a a1 = ae.b().a(ac);
    String str2 = ae.b("Content-Encoding");
    String str1 = ae.b("Content-Type");
    if ("br".equalsIgnoreCase(str2) && e.b(ae)) {
      z = q.a((InputStream)new BrotliInputStream(ae.g.source().f()));
      a1.a(ae.f.d().b("Content-Encoding").b("Content-Length").a());
      a1.a((af)new h(str1, -1L, q.a(z)));
    } else if ("gzip".equalsIgnoreCase((String)z) && e.b(ae)) {
      n n = new n((z)ae.g.source());
      a1.a(ae.f.d().b("Content-Encoding").b("Content-Length").a());
      a1.a((af)new h(str1, -1L, q.a((z)n)));
    } 
    return a1.a();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\interceptor\EncodeResponseInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */