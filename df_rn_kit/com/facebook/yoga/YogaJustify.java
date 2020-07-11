package com.facebook.yoga;

public enum YogaJustify {
  CENTER,
  FLEX_END,
  FLEX_START(0),
  SPACE_AROUND(0),
  SPACE_BETWEEN(0),
  SPACE_EVENLY(0);
  
  private final int mIntValue;
  
  static {
    CENTER = new YogaJustify("CENTER", 1, 1);
    FLEX_END = new YogaJustify("FLEX_END", 2, 2);
    SPACE_BETWEEN = new YogaJustify("SPACE_BETWEEN", 3, 3);
    SPACE_AROUND = new YogaJustify("SPACE_AROUND", 4, 4);
    SPACE_EVENLY = new YogaJustify("SPACE_EVENLY", 5, 5);
    $VALUES = new YogaJustify[] { FLEX_START, CENTER, FLEX_END, SPACE_BETWEEN, SPACE_AROUND, SPACE_EVENLY };
  }
  
  YogaJustify(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaJustify fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt != 3) {
            if (paramInt != 4) {
              if (paramInt == 5)
                return SPACE_EVENLY; 
              StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
              stringBuilder.append(paramInt);
              throw new IllegalArgumentException(stringBuilder.toString());
            } 
            return SPACE_AROUND;
          } 
          return SPACE_BETWEEN;
        } 
        return FLEX_END;
      } 
      return CENTER;
    } 
    return FLEX_START;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaJustify.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */