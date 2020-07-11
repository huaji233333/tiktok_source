package com.yqritc.scalablevideoview;

public enum a {
  CENTER, CENTER_BOTTOM, CENTER_TOP, LEFT_BOTTOM, LEFT_CENTER, LEFT_TOP, RIGHT_BOTTOM, RIGHT_CENTER, RIGHT_TOP;
  
  static {
    LEFT_CENTER = new a("LEFT_CENTER", 1);
    LEFT_BOTTOM = new a("LEFT_BOTTOM", 2);
    CENTER_TOP = new a("CENTER_TOP", 3);
    CENTER = new a("CENTER", 4);
    CENTER_BOTTOM = new a("CENTER_BOTTOM", 5);
    RIGHT_TOP = new a("RIGHT_TOP", 6);
    RIGHT_CENTER = new a("RIGHT_CENTER", 7);
    RIGHT_BOTTOM = new a("RIGHT_BOTTOM", 8);
    a = new a[] { LEFT_TOP, LEFT_CENTER, LEFT_BOTTOM, CENTER_TOP, CENTER, CENTER_BOTTOM, RIGHT_TOP, RIGHT_CENTER, RIGHT_BOTTOM };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\yqritc\scalablevideoview\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */