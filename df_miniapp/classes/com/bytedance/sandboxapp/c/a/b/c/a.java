package com.bytedance.sandboxapp.c.a.b.c;

import com.bytedance.sandboxapp.a.a.c.h;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import d.f.b.l;
import org.json.JSONObject;

public final class a extends h {
  public a(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final ApiCallbackData a(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    l.b(parama, "apiInvokeInfo");
    JSONObject jSONObject2 = ((com.bytedance.sandboxapp.protocol.service.b.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.b.a.class)).getExtConfigInfoJson();
    JSONObject jSONObject1 = jSONObject2;
    if (jSONObject2 == null)
      jSONObject1 = new JSONObject(); 
    return a(h.a.a().a(jSONObject1).b());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */