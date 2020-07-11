package com.facebook.yoga;

public enum YogaDimension {
  HEIGHT,
  WIDTH(0);
  
  private final int mIntValue;
  
  static {
    HEIGHT = new YogaDimension("HEIGHT", 1, 1);
    $VALUES = new YogaDimension[] { WIDTH, HEIGHT };
  }
  
  YogaDimension(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaDimension fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt == 1)
        return HEIGHT; 
      StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
      stringBuilder.append(paramInt);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return WIDTH;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaDimension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */