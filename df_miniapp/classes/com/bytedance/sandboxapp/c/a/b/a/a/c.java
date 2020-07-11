package com.bytedance.sandboxapp.c.a.b.a.a;

import android.text.TextUtils;
import com.bytedance.sandboxapp.a.a.c.l;
import com.bytedance.sandboxapp.a.a.d.a;
import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.a.a.a;
import com.bytedance.sandboxapp.protocol.service.a.a.a.b;
import com.bytedance.sandboxapp.protocol.service.a.a.a.d;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import org.json.JSONObject;

public final class c extends l {
  public c(b paramb, a parama) {
    super(paramb, parama);
  }
  
  public final void a(l.a parama, a parama1) {
    d d;
    a a1 = (a)((a)this).context.getService(a.class);
    if (a1 == null || !a1.isSupportDxppManager()) {
      a();
      return;
    } 
    JSONObject jSONObject = (parama1.a()).a;
    parama1 = null;
    parama = null;
    if (jSONObject == null) {
      a a2 = parama1;
    } else {
      b b;
      d d1 = new d();
      d1.a = jSONObject;
      d1.b = jSONObject.optString("type");
      d1.c = jSONObject.optString("schema");
      jSONObject = jSONObject.optJSONObject("data");
      if (jSONObject != null) {
        b = new b();
        b.a = jSONObject.optLong("cid");
        b.b = jSONObject.optString("app_name");
        b.c = jSONObject.optString("package_name");
        b.d = jSONObject.optString("source_avatar");
        b.e = jSONObject.optString("download_url");
        b.f = jSONObject.optString("open_url");
        b.g = jSONObject.optString("quick_app_url");
        b.h = jSONObject.optString("web_url");
        b.i = jSONObject.optString("web_title");
        b.j = jSONObject.optInt("auto_open");
        b.k = jSONObject.optInt("download_mode");
        b.l = jSONObject.optString("log_extra");
      } 
      d1.d = b;
      d = d1;
    } 
    if (TextUtils.isEmpty(d.c)) {
      b();
      return;
    } 
    a1.openAdLandPageLinks(d, new com.bytedance.sandboxapp.protocol.service.a.a.a.c(this, d) {
          public final void a() {
            this.b.a(this.a.c);
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\a\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */