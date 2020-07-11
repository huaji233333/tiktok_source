package com.facebook.yoga;

public enum YogaDirection {
  INHERIT(0),
  LTR(1),
  RTL(2);
  
  private final int mIntValue;
  
  static {
    $VALUES = new YogaDirection[] { INHERIT, LTR, RTL };
  }
  
  YogaDirection(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaDirection fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt == 2)
          return RTL; 
        StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
        stringBuilder.append(paramInt);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      return LTR;
    } 
    return INHERIT;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaDirection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */