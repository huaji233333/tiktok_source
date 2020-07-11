package com.ss.android.ugc.aweme.miniapp.g;

import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp.l.a;
import com.ss.android.ugc.aweme.miniapp.utils.b;
import com.ss.android.ugc.aweme.miniapp_api.a.h;
import com.ss.android.ugc.aweme.miniapp_api.a.q;
import com.ss.android.ugc.aweme.miniapp_api.b.d;
import com.ss.android.ugc.aweme.miniapp_api.model.net.a;
import com.ss.android.ugc.aweme.miniapp_api.model.net.b;
import com.ss.android.ugc.aweme.miniapp_api.model.net.c;
import com.ss.android.ugc.aweme.miniapp_api.model.net.d;
import com.tt.option.q.a;
import com.tt.option.q.c;
import com.tt.option.q.f;
import com.tt.option.q.g;
import com.tt.option.q.i;
import com.tt.option.q.j;
import com.tt.option.q.k;
import java.util.Map;

public class s extends a {
  public k createWsClient(k.a parama) {
    q q = MiniAppService.inst().getNetWorkDepend().a((q.a)new Object(parama));
    return (k)((q == null) ? null : new a.a(q));
  }
  
  public j doGet(i parami) throws Exception {
    return b.a(MiniAppService.inst().getNetWorkDepend().a(b.a(parami)));
  }
  
  public j doPostBody(i parami) throws Exception {
    return b.a(MiniAppService.inst().getNetWorkDepend().b(b.a(parami)));
  }
  
  public j doRequest(i parami) throws Exception {
    return b.a(MiniAppService.inst().getNetWorkDepend().d(b.a(parami)));
  }
  
  public g downloadFile(f paramf, c.a parama) {
    h h = MiniAppService.inst().getNetWorkDepend();
    a a1 = new a();
    a1.a = paramf.a;
    a1.b = paramf.b;
    ((c)a1).l = ((i)paramf).k;
    ((c)a1).k = ((i)paramf).l;
    ((c)a1).h = ((i)paramf).f;
    for (Map.Entry entry : ((i)paramf).h.entrySet()) {
      String str = (String)entry.getKey();
      i.b b2 = (i.b)entry.getValue();
      c.b b1 = new c.b(b2.a, b2.b);
      ((c)a1).j.put(str, b1);
    } 
    ((c)a1).i = paramf.c();
    ((c)a1).m = ((i)paramf).l;
    ((c)a1).c = paramf.f();
    ((c)a1).n = ((i)paramf).m;
    b b = h.a(a1, new d(this, parama) {
          public final void a(int param1Int, long param1Long1, long param1Long2) {
            this.a.updateProgress(param1Int, -1L, -1L);
          }
        });
    g g = new g();
    g.a = b.a;
    ((j)g).b = ((d)b).b;
    ((j)g).d = ((d)b).d;
    ((j)g).c = ((d)b).c;
    ((j)g).f = ((d)b).g;
    return g;
  }
  
  public j postMultiPart(i parami) throws Exception {
    return b.a(MiniAppService.inst().getNetWorkDepend().c(b.a(parami)));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\s.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */