package com.facebook.yoga;

public enum YogaWrap {
  NO_WRAP(0),
  WRAP(1),
  WRAP_REVERSE(2);
  
  private final int mIntValue;
  
  static {
    $VALUES = new YogaWrap[] { NO_WRAP, WRAP, WRAP_REVERSE };
  }
  
  YogaWrap(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaWrap fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt == 2)
          return WRAP_REVERSE; 
        StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
        stringBuilder.append(paramInt);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      return WRAP;
    } 
    return NO_WRAP;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaWrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */