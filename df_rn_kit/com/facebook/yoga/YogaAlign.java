package com.facebook.yoga;

public enum YogaAlign {
  AUTO(0),
  BASELINE(0),
  CENTER(0),
  FLEX_END(0),
  FLEX_START(1),
  SPACE_AROUND(1),
  SPACE_BETWEEN(1),
  STRETCH(1);
  
  private final int mIntValue;
  
  static {
    CENTER = new YogaAlign("CENTER", 2, 2);
    FLEX_END = new YogaAlign("FLEX_END", 3, 3);
    STRETCH = new YogaAlign("STRETCH", 4, 4);
    BASELINE = new YogaAlign("BASELINE", 5, 5);
    SPACE_BETWEEN = new YogaAlign("SPACE_BETWEEN", 6, 6);
    SPACE_AROUND = new YogaAlign("SPACE_AROUND", 7, 7);
    $VALUES = new YogaAlign[] { AUTO, FLEX_START, CENTER, FLEX_END, STRETCH, BASELINE, SPACE_BETWEEN, SPACE_AROUND };
  }
  
  YogaAlign(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaAlign fromInt(int paramInt) {
    StringBuilder stringBuilder;
    switch (paramInt) {
      default:
        stringBuilder = new StringBuilder("Unknown enum value: ");
        stringBuilder.append(paramInt);
        throw new IllegalArgumentException(stringBuilder.toString());
      case 7:
        return SPACE_AROUND;
      case 6:
        return SPACE_BETWEEN;
      case 5:
        return BASELINE;
      case 4:
        return STRETCH;
      case 3:
        return FLEX_END;
      case 2:
        return CENTER;
      case 1:
        return FLEX_START;
      case 0:
        break;
    } 
    return AUTO;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaAlign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */