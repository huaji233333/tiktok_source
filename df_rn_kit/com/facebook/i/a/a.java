package com.facebook.i.a;

public final class a {
  public static <T> T a(T paramT) {
    return paramT;
  }
  
  public static <T> T a(T paramT, String paramString) {
    if (paramT != null)
      return paramT; 
    throw new AssertionError(paramString);
  }
  
  public static void a(boolean paramBoolean) {
    if (paramBoolean)
      return; 
    throw new AssertionError();
  }
  
  public static void a(boolean paramBoolean, String paramString) {
    if (paramBoolean)
      return; 
    throw new AssertionError(paramString);
  }
  
  public static <T> T b(T paramT) {
    if (paramT != null)
      return paramT; 
    throw new AssertionError();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\i\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */