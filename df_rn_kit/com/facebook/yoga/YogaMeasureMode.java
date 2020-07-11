package com.facebook.yoga;

public enum YogaMeasureMode {
  AT_MOST,
  EXACTLY,
  UNDEFINED(0);
  
  private final int mIntValue;
  
  static {
    EXACTLY = new YogaMeasureMode("EXACTLY", 1, 1);
    AT_MOST = new YogaMeasureMode("AT_MOST", 2, 2);
    $VALUES = new YogaMeasureMode[] { UNDEFINED, EXACTLY, AT_MOST };
  }
  
  YogaMeasureMode(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaMeasureMode fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt == 2)
          return AT_MOST; 
        StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
        stringBuilder.append(paramInt);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      return EXACTLY;
    } 
    return UNDEFINED;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaMeasureMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */