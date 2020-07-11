package com.facebook.yoga;

public enum YogaPositionType {
  ABSOLUTE,
  RELATIVE(0);
  
  private final int mIntValue;
  
  static {
    ABSOLUTE = new YogaPositionType("ABSOLUTE", 1, 1);
    $VALUES = new YogaPositionType[] { RELATIVE, ABSOLUTE };
  }
  
  YogaPositionType(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaPositionType fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt == 1)
        return ABSOLUTE; 
      StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
      stringBuilder.append(paramInt);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return RELATIVE;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaPositionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */