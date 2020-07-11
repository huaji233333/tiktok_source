package com.facebook.yoga;

public enum YogaDisplay {
  FLEX(0),
  NONE(1);
  
  private final int mIntValue;
  
  static {
    $VALUES = new YogaDisplay[] { FLEX, NONE };
  }
  
  YogaDisplay(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaDisplay fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt == 1)
        return NONE; 
      StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
      stringBuilder.append(paramInt);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return FLEX;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaDisplay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */