package com.bytedance.ies.bullet.kit.rn.internal.wrapper;

import android.content.Context;
import com.bytedance.ies.bullet.kit.rn.core.c;
import com.facebook.react.bridge.ReactContext;
import d.f.b.l;

public final class a extends c {
  private final ReactContext b;
  
  public a(ReactContext paramReactContext) {
    super(context);
    this.b = paramReactContext;
  }
  
  public final Object getSystemService(String paramString) {
    l.b(paramString, "name");
    Object object = this.b.getSystemService(paramString);
    l.a(object, "real.getSystemService(name)");
    return object;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\internal\wrapper\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */