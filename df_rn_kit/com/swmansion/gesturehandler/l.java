package com.swmansion.gesturehandler;

public enum l {
  AUTO, BOX_NONE, BOX_ONLY, NONE;
  
  static {
    BOX_NONE = new l("BOX_NONE", 1);
    BOX_ONLY = new l("BOX_ONLY", 2);
    AUTO = new l("AUTO", 3);
    a = new l[] { NONE, BOX_NONE, BOX_ONLY, AUTO };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */