package com.facebook.yoga;

public enum YogaFlexDirection {
  COLUMN(0),
  COLUMN_REVERSE(1),
  ROW(2),
  ROW_REVERSE(3);
  
  private final int mIntValue;
  
  static {
    $VALUES = new YogaFlexDirection[] { COLUMN, COLUMN_REVERSE, ROW, ROW_REVERSE };
  }
  
  YogaFlexDirection(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaFlexDirection fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt == 3)
            return ROW_REVERSE; 
          StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
          stringBuilder.append(paramInt);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        return ROW;
      } 
      return COLUMN_REVERSE;
    } 
    return COLUMN;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaFlexDirection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */