package com.facebook.yoga;

public enum YogaUnit {
  AUTO,
  PERCENT,
  POINT,
  UNDEFINED(0);
  
  private final int mIntValue;
  
  static {
    POINT = new YogaUnit("POINT", 1, 1);
    PERCENT = new YogaUnit("PERCENT", 2, 2);
    AUTO = new YogaUnit("AUTO", 3, 3);
    $VALUES = new YogaUnit[] { UNDEFINED, POINT, PERCENT, AUTO };
  }
  
  YogaUnit(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaUnit fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt == 3)
            return AUTO; 
          StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
          stringBuilder.append(paramInt);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return PERCENT;
      } 
      return POINT;
    } 
    return UNDEFINED;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaUnit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */