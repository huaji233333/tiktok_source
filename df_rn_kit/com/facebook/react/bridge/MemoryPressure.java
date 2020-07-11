package com.facebook.react.bridge;

public enum MemoryPressure {
  CRITICAL, MODERATE, UI_HIDDEN;
  
  static {
    MODERATE = new MemoryPressure("MODERATE", 1);
    CRITICAL = new MemoryPressure("CRITICAL", 2);
    $VALUES = new MemoryPressure[] { UI_HIDDEN, MODERATE, CRITICAL };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\MemoryPressure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */