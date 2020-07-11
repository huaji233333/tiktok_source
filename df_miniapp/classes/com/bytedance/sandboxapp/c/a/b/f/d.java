package com.bytedance.sandboxapp.c.a.b.f;

import com.bytedance.sandboxapp.a.a.c.o;
import com.bytedance.sandboxapp.a.a.d.a;
import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.bytedance.sandboxapp.protocol.service.request.a;
import d.f.b.l;

public final class d extends o {
  public d(b paramb, a parama) {
    super(paramb, parama);
  }
  
  public final void a(o.a parama, a parama1) {
    l.b(parama, "paramParser");
    l.b(parama1, "apiInvokeInfo");
    a a1 = (a)((a)this).context.getService(a.class);
    Integer integer = parama.b;
    l.a(integer, "paramParser.downloadTaskId");
    int i = integer.intValue();
    String str = parama.c;
    l.a(str, "paramParser.operationType");
    a1.operateDownloadRequest(i, str);
    callbackOk();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\f\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */