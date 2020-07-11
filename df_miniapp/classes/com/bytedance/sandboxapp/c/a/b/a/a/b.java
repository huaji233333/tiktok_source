package com.bytedance.sandboxapp.c.a.b.a.a;

import android.app.Activity;
import com.bytedance.sandboxapp.a.a.c.f;
import com.bytedance.sandboxapp.a.a.d.a;
import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.protocol.service.a.a.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.AppbrandContext;
import java.util.HashSet;

public final class b extends f {
  public b(com.bytedance.sandboxapp.c.a.b paramb, a parama) {
    super(paramb, parama);
  }
  
  public final void a(f.a parama, a parama1) {
    a a1 = (a)((a)this).context.getService(a.class);
    if (a1 == null || !a1.isSupportDxppManager()) {
      a();
      return;
    } 
    com.bytedance.sandboxapp.protocol.service.a.a.a.b b2 = new com.bytedance.sandboxapp.protocol.service.a.a.a.b();
    b2.a = parama.b.longValue();
    b2.b = parama.c;
    b2.c = parama.d;
    b2.d = parama.e;
    b2.e = parama.f;
    b2.f = parama.g;
    b2.g = parama.h;
    b2.h = parama.i;
    b2.i = parama.j;
    Integer integer = parama.k;
    byte b1 = 0;
    if (integer != null) {
      i = parama.k.intValue();
    } else {
      i = 0;
    } 
    b2.j = i;
    int i = b1;
    if (parama.l != null)
      i = parama.l.intValue(); 
    b2.k = i;
    if (parama.m != null) {
      String str = parama.m.toString();
    } else {
      parama = null;
    } 
    b2.l = (String)parama;
    if (PermissionsManager.getInstance().hasPermission(((a)this).context.getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
      a1.dxppAd(b2);
      callbackOk(null);
      return;
    } 
    HashSet<String> hashSet = new HashSet();
    hashSet.add("android.permission.WRITE_EXTERNAL_STORAGE");
    PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity)AppbrandContext.getInst().getCurrentActivity(), hashSet, new PermissionsResultAction(this, a1, b2) {
          public final void onDenied(String param1String) {
            this.c.b();
          }
          
          public final void onGranted() {
            this.a.dxppAd(this.b);
            this.c.callbackOk(null);
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\a\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */