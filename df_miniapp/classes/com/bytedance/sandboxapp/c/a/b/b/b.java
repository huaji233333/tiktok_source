package com.bytedance.sandboxapp.c.a.b.b;

import android.app.Activity;
import android.text.TextUtils;
import com.bytedance.sandboxapp.a.a.c.m;
import com.bytedance.sandboxapp.protocol.service.c.c;
import d.f.b.l;

public final class b extends m {
  public b(com.bytedance.sandboxapp.c.a.b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void handleApi(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    l.b(parama, "apiInvokeInfo");
    com.bytedance.sandboxapp.protocol.service.c.a a1 = (com.bytedance.sandboxapp.protocol.service.c.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.c.a.class);
    if (a1 == null || !a1.hasAwemeDepend()) {
      a();
      return;
    } 
    a1.getAwemeUidFromSuffixMeta(new a(this, a1));
  }
  
  public static final class a implements com.bytedance.sandboxapp.protocol.service.c.b {
    a(b param1b, com.bytedance.sandboxapp.protocol.service.c.a param1a) {}
    
    public final void a(String param1String) {
      l.b(param1String, "msg");
      this.a.a(param1String);
    }
    
    public final void a(String param1String1, String param1String2) {
      l.b(param1String1, "uid");
      l.b(param1String2, "secUid");
      if (TextUtils.isEmpty(param1String1) && TextUtils.isEmpty(param1String2)) {
        this.a.d();
        return;
      } 
      if (!this.b.hasLogin()) {
        this.a.b();
        return;
      } 
      Activity activity = ((com.bytedance.sandboxapp.c.a.a.a)this.a).context.getCurrentActivity();
      if (activity == null) {
        this.a.c();
        return;
      } 
      boolean bool = this.b.canCheckFollowAwemeState();
      this.b.openAwemeUserProfile(activity, param1String1, param1String2, bool, new a(this));
    }
    
    public static final class a implements c {
      a(b.a param2a) {}
      
      public final void onFailure(int param2Int, String param2String) {
        if (param2Int == -2) {
          this.b.a.a();
          return;
        } 
        b b = this.b.a;
        String str = param2String;
        if (param2String == null)
          str = "unknown"; 
        b.a(str);
      }
      
      public final void onFollowAwemeResult(Boolean param2Boolean) {
        if (param2Boolean != null) {
          this.b.a.callbackOk(m.a.a().a(param2Boolean).b());
          return;
        } 
        this.b.a.callbackOk();
      }
    }
  }
  
  public static final class a implements c {
    a(b.a param1a) {}
    
    public final void onFailure(int param1Int, String param1String) {
      if (param1Int == -2) {
        this.b.a.a();
        return;
      } 
      b b = this.b.a;
      String str = param1String;
      if (param1String == null)
        str = "unknown"; 
      b.a(str);
    }
    
    public final void onFollowAwemeResult(Boolean param1Boolean) {
      if (param1Boolean != null) {
        this.b.a.callbackOk(m.a.a().a(param1Boolean).b());
        return;
      } 
      this.b.a.callbackOk();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */