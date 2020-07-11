package com.ss.android.ugc.aweme.miniapp.utils;

import com.ss.android.ugc.aweme.miniapp_api.model.net.c;
import com.ss.android.ugc.aweme.miniapp_api.model.net.d;
import com.tt.option.q.i;
import com.tt.option.q.j;
import com.tt.option.w.h;
import java.util.Map;

public final class b {
  public static com.ss.android.ugc.aweme.miniapp_api.model.b.c a(h paramh) {
    com.ss.android.ugc.aweme.miniapp_api.model.b.c c = new com.ss.android.ugc.aweme.miniapp_api.model.b.c();
    c.a = paramh.channel;
    c.l = paramh.desc;
    c.g = paramh.token;
    c.f = paramh.entryPath;
    c.e = paramh.extra;
    c.j = paramh.schema;
    c.c = paramh.imageUrl;
    c.d = paramh.queryString;
    c.h = paramh.miniImageUrl;
    c.k = paramh.shareType;
    c.b = paramh.title;
    c.i = paramh.ugUrl;
    com.ss.android.ugc.aweme.miniapp_api.model.b.a a = new com.ss.android.ugc.aweme.miniapp_api.model.b.a();
    a.a = paramh.appInfo.appId;
    a.c = paramh.appInfo.appName;
    a.b = paramh.appInfo.icon;
    a.e = paramh.appInfo.query;
    a.d = paramh.appInfo.startPage;
    a.f = paramh.appInfo.type;
    c.m = a;
    return c;
  }
  
  public static c a(i parami) {
    c c = new c();
    c.l = parami.k;
    c.k = parami.l;
    c.h = parami.f;
    c.e = parami.c;
    c.f = parami.e;
    c.g = parami.e();
    c.d = parami.i;
    parami.p = new i.a(c) {
        public final void doCancel() {
          c c1 = this.a;
          if (c1.p != null)
            c1.p.a(); 
        }
      };
    for (Map.Entry entry : parami.h.entrySet()) {
      String str = (String)entry.getKey();
      i.b b2 = (i.b)entry.getValue();
      c.b b1 = new c.b(b2.a, b2.b);
      c.j.put(str, b1);
    } 
    c.i = parami.c();
    c.m = parami.l;
    c.c = parami.f();
    c.n = parami.m;
    c.o = parami.o;
    return c;
  }
  
  public static j a(d paramd) {
    j j = new j();
    j.e = paramd.a();
    j.b = paramd.b;
    j.d = paramd.d;
    j.c = paramd.c;
    j.f = paramd.g;
    return j;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniap\\utils\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */