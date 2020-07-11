package com.facebook.react.uimanager;

public enum PointerEvents {
  AUTO, BOX_NONE, BOX_ONLY, NONE;
  
  static {
    BOX_NONE = new PointerEvents("BOX_NONE", 1);
    BOX_ONLY = new PointerEvents("BOX_ONLY", 2);
    AUTO = new PointerEvents("AUTO", 3);
    $VALUES = new PointerEvents[] { NONE, BOX_NONE, BOX_ONLY, AUTO };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\PointerEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */