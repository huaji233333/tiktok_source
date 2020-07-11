package com.facebook.yoga;

public enum YogaNodeType {
  DEFAULT(0),
  TEXT(1);
  
  private final int mIntValue;
  
  static {
    $VALUES = new YogaNodeType[] { DEFAULT, TEXT };
  }
  
  YogaNodeType(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaNodeType fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt == 1)
        return TEXT; 
      StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
      stringBuilder.append(paramInt);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return DEFAULT;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaNodeType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */