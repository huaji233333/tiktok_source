package com.facebook.yoga;

public enum YogaExperimentalFeature {
  WEB_FLEX_BASIS(0);
  
  private final int mIntValue;
  
  static {
    $VALUES = new YogaExperimentalFeature[] { WEB_FLEX_BASIS };
  }
  
  YogaExperimentalFeature(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaExperimentalFeature fromInt(int paramInt) {
    if (paramInt == 0)
      return WEB_FLEX_BASIS; 
    StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
    stringBuilder.append(paramInt);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaExperimentalFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */