package com.facebook.react.uimanager.layoutanimation;

enum InterpolatorType {
  EASE_IN,
  EASE_IN_EASE_OUT,
  EASE_OUT,
  LINEAR("linear"),
  SPRING("linear");
  
  private final String mName;
  
  static {
    EASE_IN = new InterpolatorType("EASE_IN", 1, "easeIn");
    EASE_OUT = new InterpolatorType("EASE_OUT", 2, "easeOut");
    EASE_IN_EASE_OUT = new InterpolatorType("EASE_IN_EASE_OUT", 3, "easeInEaseOut");
    SPRING = new InterpolatorType("SPRING", 4, "spring");
    $VALUES = new InterpolatorType[] { LINEAR, EASE_IN, EASE_OUT, EASE_IN_EASE_OUT, SPRING };
  }
  
  InterpolatorType(String paramString1) {
    this.mName = paramString1;
  }
  
  public static InterpolatorType fromString(String paramString) {
    for (InterpolatorType interpolatorType : values()) {
      if (interpolatorType.toString().equalsIgnoreCase(paramString))
        return interpolatorType; 
    } 
    StringBuilder stringBuilder = new StringBuilder("Unsupported interpolation type : ");
    stringBuilder.append(paramString);
    IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
    throw illegalArgumentException;
  }
  
  public final String toString() {
    return this.mName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\InterpolatorType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */