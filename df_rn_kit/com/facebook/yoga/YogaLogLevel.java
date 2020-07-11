package com.facebook.yoga;

public enum YogaLogLevel {
  DEBUG,
  ERROR(0),
  FATAL(0),
  INFO(0),
  VERBOSE(0),
  WARN(1);
  
  private final int mIntValue;
  
  static {
    INFO = new YogaLogLevel("INFO", 2, 2);
    DEBUG = new YogaLogLevel("DEBUG", 3, 3);
    VERBOSE = new YogaLogLevel("VERBOSE", 4, 4);
    FATAL = new YogaLogLevel("FATAL", 5, 5);
    $VALUES = new YogaLogLevel[] { ERROR, WARN, INFO, DEBUG, VERBOSE, FATAL };
  }
  
  YogaLogLevel(int paramInt1) {
    this.mIntValue = paramInt1;
  }
  
  public static YogaLogLevel fromInt(int paramInt) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt != 3) {
            if (paramInt != 4) {
              if (paramInt == 5)
                return FATAL; 
              StringBuilder stringBuilder = new StringBuilder("Unknown enum value: ");
              stringBuilder.append(paramInt);
              throw new IllegalArgumentException(stringBuilder.toString());
            } 
            return VERBOSE;
          } 
          return DEBUG;
        } 
        return INFO;
      } 
      return WARN;
    } 
    return ERROR;
  }
  
  public final int intValue() {
    return this.mIntValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\yoga\YogaLogLevel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */