package com.facebook.react.uimanager.layoutanimation;

enum AnimatedPropertyType {
  OPACITY("opacity"),
  SCALE_XY("scaleXY");
  
  private final String mName;
  
  static {
    $VALUES = new AnimatedPropertyType[] { OPACITY, SCALE_XY };
  }
  
  AnimatedPropertyType(String paramString1) {
    this.mName = paramString1;
  }
  
  public static AnimatedPropertyType fromString(String paramString) {
    for (AnimatedPropertyType animatedPropertyType : values()) {
      if (animatedPropertyType.toString().equalsIgnoreCase(paramString))
        return animatedPropertyType; 
    } 
    StringBuilder stringBuilder = new StringBuilder("Unsupported animated property : ");
    stringBuilder.append(paramString);
    IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
    throw illegalArgumentException;
  }
  
  public final String toString() {
    return this.mName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\AnimatedPropertyType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */