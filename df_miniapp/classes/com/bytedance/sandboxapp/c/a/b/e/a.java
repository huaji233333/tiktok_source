package com.bytedance.sandboxapp.c.a.b.e;

import com.bytedance.sandboxapp.a.a.c.g;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import d.f.b.l;

public final class a extends g {
  public a(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final ApiCallbackData a(g.b paramb, com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    l.b(paramb, "paramParser");
    l.b(parama, "apiInvokeInfo");
    com.bytedance.sandboxapp.protocol.service.b.a a1 = (com.bytedance.sandboxapp.protocol.service.b.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.b.a.class);
    Boolean bool = paramb.b;
    l.a(bool, "paramParser.needSession");
    if (bool.booleanValue()) {
      String str = a1.getPlatformSession();
    } else {
      bool = null;
    } 
    return a(g.a.a().a(a1.getSchema()).b(a1.getAppId()).c((String)bool).a(a1.getApiWhiteList()).b(a1.getApiBlackList()).a(Integer.valueOf(a1.getPkgType())).b());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\e\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */