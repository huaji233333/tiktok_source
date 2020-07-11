package com.bytedance.sandboxapp.c.a.b.e;

import com.bytedance.sandboxapp.a.a.c.j;
import com.bytedance.sandboxapp.a.a.d.a;
import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.bytedance.sandboxapp.protocol.service.m.a;
import d.f.b.l;

public final class c extends j {
  public c(b paramb, a parama) {
    super(paramb, parama);
  }
  
  public final ApiCallbackData a(a parama) {
    l.b(parama, "apiInvokeInfo");
    a a1 = (a)((a)this).context.getService(a.class);
    return a(j.a.a().a(Boolean.valueOf(a1.useWebVideo())).c(Boolean.valueOf(a1.useWebLivePlayer())).b(Boolean.valueOf(a1.isRenderInBrowser())).b());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\e\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */