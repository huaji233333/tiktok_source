package com.bytedance.sandboxapp.b.a.a;

import d.f.b.l;

public final class b implements a {
  public static a a;
  
  public static final b b = new b();
  
  private static boolean c;
  
  public static void a(boolean paramBoolean) {
    c = paramBoolean;
  }
  
  public final boolean isDebugMode() {
    return c;
  }
  
  public final void logOrThrow(String paramString, Object... paramVarArgs) {
    l.b(paramVarArgs, "messages");
    a a1 = a;
    if (a1 != null)
      a1.logOrThrow(paramString, new Object[] { com.bytedance.sandboxapp.b.a.b.b.a(paramVarArgs) }); 
  }
  
  public final void logOrToast(String paramString, Object... paramVarArgs) {
    l.b(paramVarArgs, "messages");
    a a1 = a;
    if (a1 != null)
      a1.logOrToast(paramString, new Object[] { com.bytedance.sandboxapp.b.a.b.b.a(paramVarArgs) }); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\b\a\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */