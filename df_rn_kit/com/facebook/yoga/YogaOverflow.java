package com.facebook.yoga;

public enum YogaOverflow {
  HIDDEN,
  SCROLL,
  VISIBLE(0);
  
  private final int mIntValue;
  
  static {
    HIDDEN = new YogaOverflow("HIDDEN", 1, 1);
    SCROLL = new YogaOverflow("SCROLL", 2, 2);
    $VALUES = new YogaOverflow[] { VISIBLE, HIDDEN, SCROLL };
  }
  
  YogaOverflow(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaOverflow fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt == 2)
          return SCROLL; 
        StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
        stringBuilder.append(paramInt);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      return HIDDEN;
    } 
    return VISIBLE;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaOverflow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */