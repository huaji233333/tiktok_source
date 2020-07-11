package com.facebook.react.views.image;

import com.facebook.drawee.e.q;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;

public class ImageResizeMode {
  public static q.b defaultValue() {
    return q.b.g;
  }
  
  public static q.b toScaleType(String paramString) {
    if ("contain".equals(paramString))
      return q.b.c; 
    if ("cover".equals(paramString))
      return q.b.g; 
    if ("stretch".equals(paramString))
      return q.b.a; 
    if ("center".equals(paramString))
      return q.b.f; 
    if (paramString == null)
      return defaultValue(); 
    StringBuilder stringBuilder = new StringBuilder("Invalid resize mode: '");
    stringBuilder.append(paramString);
    stringBuilder.append("'");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\image\ImageResizeMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */