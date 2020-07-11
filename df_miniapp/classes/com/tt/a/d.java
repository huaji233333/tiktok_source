package com.tt.a;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import com.bytedance.d.a.b.a;
import com.bytedance.d.a.b.b;
import com.bytedance.d.a.c.d.a;
import d.f.b.l;

public final class d implements a {
  public static final d a = new d();
  
  private static final Handler b = new Handler(Looper.getMainLooper());
  
  public final b a() {
    return new b("BDThread");
  }
  
  public final void a(Application paramApplication) {
    l.b(paramApplication, "app");
    l.b(paramApplication, "app");
    a.a.a((a)this, paramApplication);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */