package com.facebook.react.common;

import java.nio.charset.Charset;

public class StandardCharsets {
  public static final Charset UTF_16;
  
  public static final Charset UTF_16BE;
  
  public static final Charset UTF_16LE;
  
  public static final Charset UTF_8 = Charset.forName("UTF-8");
  
  static {
    UTF_16 = Charset.forName("UTF-16");
    UTF_16BE = Charset.forName("UTF-16BE");
    UTF_16LE = Charset.forName("UTF-16LE");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\StandardCharsets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */