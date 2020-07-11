package com.bytedance.sandboxapp.c.a.b.d;

import android.net.Uri;
import android.text.TextUtils;
import com.bytedance.sandboxapp.a.a.c.n;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.h.c;
import d.f.b.l;

public final class a extends n {
  public a(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void a(n.a parama, com.bytedance.sandboxapp.protocol.service.api.entity.a parama1) {
    Boolean bool;
    l.b(parama, "paramParser");
    l.b(parama1, "apiInvokeInfo");
    boolean bool2 = TextUtils.equals(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, "openInnerSchema");
    String str1 = parama.b;
    l.a(str1, "paramParser.schema");
    com.bytedance.sandboxapp.protocol.service.l.a a1 = (com.bytedance.sandboxapp.protocol.service.l.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.l.a.class);
    if (!bool2) {
      String str = parama.b;
      l.a(str, "paramParser.schema");
      if (!a1.isSafeDomain("schema_host", str)) {
        a(str1);
        return;
      } 
    } 
    c c = (c)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(c.class);
    Uri uri = Uri.parse(str1);
    l.a(uri, "uri");
    String str2 = uri.getHost();
    CharSequence charSequence1 = "microapp";
    CharSequence charSequence2 = str2;
    boolean bool3 = TextUtils.equals(charSequence1, charSequence2);
    boolean bool1 = TextUtils.equals("microgame", charSequence2);
    if (bool3 || bool1) {
      Integer integer;
      if (bool2 || a1.isSafeDomain("appids", str1)) {
        bool = parama.c;
        l.a(bool, "paramParser.killCurrentProcess");
        bool2 = bool.booleanValue();
        bool = parama.d;
        l.a(bool, "paramParser.forceColdBoot");
        bool3 = bool.booleanValue();
        integer = parama.e;
        l.a(integer, "paramParser.toolbarStyle");
        c.openMiniApp(new c.b(str1, bool1, bool2, bool3, integer.intValue()));
        callbackOk();
        return;
      } 
      a(((n.a)integer).b);
      return;
    } 
    c.openSchema(new c.c(uri, bool.a().toString()), new a(this, str1));
  }
  
  public static final class a implements c.d {
    a(a param1a, String param1String) {}
    
    public final void a() {
      this.a.callbackOk();
    }
    
    public final void a(String param1String) {
      l.b(param1String, "failReason");
      this.a.a(param1String, this.b);
    }
    
    public final void b() {
      this.a.callbackFeatureNotSupport();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */