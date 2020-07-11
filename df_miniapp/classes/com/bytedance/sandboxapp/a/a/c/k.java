package com.bytedance.sandboxapp.a.a.c;

import com.a;
import com.bytedance.sandboxapp.a.a.d.a;
import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.c.a.a.c;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;

public abstract class k extends c {
  public k(b paramb, a parama) {
    super(paramb, parama);
  }
  
  public final void a(String paramString) {
    callbackData(ApiCallbackData.a.a(((a)this).apiName, a.a("login fail %s", new Object[] { paramString }), 0).a());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */