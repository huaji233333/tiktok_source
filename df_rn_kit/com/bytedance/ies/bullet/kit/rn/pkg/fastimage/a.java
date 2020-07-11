package com.bytedance.ies.bullet.kit.rn.pkg.fastimage;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.facebook.imagepipeline.common.c;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.NoSuchKeyException;
import com.facebook.react.bridge.ReadableMap;
import java.util.HashMap;
import java.util.Map;

final class a {
  static final Map<String, a> a;
  
  static final Map<String, c> b;
  
  private static final Drawable c = (Drawable)new ColorDrawable(0);
  
  private static final Map<String, ImageView.ScaleType> d;
  
  static {
    a = new HashMap<String, a>() {
      
      };
    b = new HashMap<String, c>() {
      
      };
    d = new HashMap<String, ImageView.ScaleType>() {
      
      };
  }
  
  static <T> T a(String paramString1, String paramString2, Map<String, T> paramMap, ReadableMap paramReadableMap) {
    String str2 = null;
    String str1 = str2;
    if (paramReadableMap != null)
      try {
        str1 = paramReadableMap.getString(paramString1);
      } catch (NoSuchKeyException noSuchKeyException) {
        str1 = str2;
      }  
    if (str1 != null)
      paramString2 = str1; 
    paramMap = (Map<String, T>)paramMap.get(paramString2);
    if (paramMap != null)
      return (T)paramMap; 
    StringBuilder stringBuilder = new StringBuilder("FastImage, invalid ");
    stringBuilder.append(paramString1);
    stringBuilder.append(" : ");
    stringBuilder.append(paramString2);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public enum a {
    CACHE_ONLY, IMMUTABLE, WEB;
    
    static {
    
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\fastimage\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */