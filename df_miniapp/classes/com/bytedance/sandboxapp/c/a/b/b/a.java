package com.bytedance.sandboxapp.c.a.b.b;

import android.text.TextUtils;
import com.bytedance.sandboxapp.a.a.c.b;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.c.b;
import com.bytedance.sandboxapp.protocol.service.c.c;
import d.f.b.l;

public final class a extends b {
  public a(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void handleApi(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    l.b(parama, "apiInvokeInfo");
    com.bytedance.sandboxapp.protocol.service.c.a a1 = (com.bytedance.sandboxapp.protocol.service.c.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.c.a.class);
    if (a1 == null || !a1.hasAwemeDepend() || !a1.canCheckFollowAwemeState()) {
      a();
      return;
    } 
    a1.getAwemeUidFromSuffixMeta(new a(this, a1));
  }
  
  public static final class a implements b {
    a(a param1a, com.bytedance.sandboxapp.protocol.service.c.a param1a1) {}
    
    public final void a(String param1String) {
      l.b(param1String, "msg");
      this.a.a(param1String);
    }
    
    public final void a(String param1String1, String param1String2) {
      l.b(param1String1, "uid");
      l.b(param1String2, "secUid");
      if (TextUtils.isEmpty(param1String1) && TextUtils.isEmpty(param1String2)) {
        this.a.c();
        return;
      } 
      if (!this.b.hasLogin()) {
        this.a.b();
        return;
      } 
      this.b.checkFollowAwemeState(param1String1, param1String2, new a(this));
    }
    
    public static final class a implements c {
      a(a.a param2a) {}
      
      public final void onFailure(int param2Int, String param2String) {
        if (param2Int == -2) {
          this.b.a.a();
          return;
        } 
        a a1 = this.b.a;
        String str = param2String;
        if (param2String == null)
          str = "unknown"; 
        a1.a(str);
      }
      
      public final void onFollowAwemeResult(Boolean param2Boolean) {
        this.b.a.callbackOk(b.a.a().a(param2Boolean).b());
      }
    }
  }
  
  public static final class a implements c {
    a(a.a param1a) {}
    
    public final void onFailure(int param1Int, String param1String) {
      if (param1Int == -2) {
        this.b.a.a();
        return;
      } 
      a a1 = this.b.a;
      String str = param1String;
      if (param1String == null)
        str = "unknown"; 
      a1.a(str);
    }
    
    public final void onFollowAwemeResult(Boolean param1Boolean) {
      this.b.a.callbackOk(b.a.a().a(param1Boolean).b());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */