package com.facebook.yoga;

public enum YogaPrintOptions {
  CHILDREN,
  LAYOUT(1),
  STYLE(2);
  
  private final int mIntValue;
  
  static {
    CHILDREN = new YogaPrintOptions("CHILDREN", 2, 4);
    $VALUES = new YogaPrintOptions[] { LAYOUT, STYLE, CHILDREN };
  }
  
  YogaPrintOptions(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaPrintOptions fromInt(int paramInt) {
    if (paramInt != 1) {
      if (paramInt != 2) {
        if (paramInt == 4)
          return CHILDREN; 
        StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
        stringBuilder.append(paramInt);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      return STYLE;
    } 
    return LAYOUT;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaPrintOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */