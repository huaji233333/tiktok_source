package com.facebook.yoga;

public final class b {
  public static long a(float paramFloat1, float paramFloat2) {
    int i = Float.floatToRawIntBits(paramFloat1);
    int j = Float.floatToRawIntBits(paramFloat2);
    long l = i;
    return j | l << 32L;
  }
  
  public static long a(int paramInt1, int paramInt2) {
    return a(paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */