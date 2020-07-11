package com.facebook.react.flat;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import com.facebook.i.a.a;
import java.util.HashMap;

final class TypefaceCache {
  private static final String[] EXTENSIONS;
  
  private static final String[] FILE_EXTENSIONS;
  
  private static final HashMap<String, Typeface[]> FONTFAMILY_CACHE = (HashMap)new HashMap<String, Typeface>();
  
  private static final HashMap<Typeface, Typeface[]> TYPEFACE_CACHE = (HashMap)new HashMap<Typeface, Typeface>();
  
  private static AssetManager sAssetManager;
  
  static {
    EXTENSIONS = new String[] { "", "_bold", "_italic", "_bold_italic" };
    FILE_EXTENSIONS = new String[] { ".ttf", ".otf" };
    sAssetManager = null;
  }
  
  private static Typeface createTypeface(String paramString, int paramInt) {
    String str = EXTENSIONS[paramInt];
    StringBuilder stringBuilder = new StringBuilder(32);
    stringBuilder.append("fonts/");
    stringBuilder.append(paramString);
    stringBuilder.append(str);
    int j = stringBuilder.length();
    String[] arrayOfString = FILE_EXTENSIONS;
    int k = arrayOfString.length;
    int i = 0;
    while (true) {
      if (i < k) {
        stringBuilder.append(arrayOfString[i]);
        String str1 = stringBuilder.toString();
        try {
          return Typeface.createFromAsset(sAssetManager, str1);
        } catch (RuntimeException runtimeException) {
          stringBuilder.setLength(j);
          i++;
          continue;
        } 
      } 
      return (Typeface)a.a(Typeface.create(paramString, paramInt));
    } 
  }
  
  public static Typeface getTypeface(Typeface paramTypeface, int paramInt) {
    Typeface[] arrayOfTypeface1;
    if (paramTypeface == null)
      return Typeface.defaultFromStyle(paramInt); 
    Typeface[] arrayOfTypeface2 = TYPEFACE_CACHE.get(paramTypeface);
    if (arrayOfTypeface2 == null) {
      arrayOfTypeface1 = new Typeface[4];
      arrayOfTypeface1[paramTypeface.getStyle()] = paramTypeface;
    } else {
      arrayOfTypeface1 = arrayOfTypeface2;
      if (arrayOfTypeface2[paramInt] != null)
        return arrayOfTypeface2[paramInt]; 
    } 
    paramTypeface = Typeface.create(paramTypeface, paramInt);
    arrayOfTypeface1[paramInt] = paramTypeface;
    TYPEFACE_CACHE.put(paramTypeface, arrayOfTypeface1);
    return paramTypeface;
  }
  
  public static Typeface getTypeface(String paramString, int paramInt) {
    Typeface[] arrayOfTypeface1;
    Typeface[] arrayOfTypeface2 = FONTFAMILY_CACHE.get(paramString);
    if (arrayOfTypeface2 == null) {
      arrayOfTypeface1 = new Typeface[4];
      FONTFAMILY_CACHE.put(paramString, arrayOfTypeface1);
    } else {
      arrayOfTypeface1 = arrayOfTypeface2;
      if (arrayOfTypeface2[paramInt] != null)
        return arrayOfTypeface2[paramInt]; 
    } 
    Typeface typeface = createTypeface(paramString, paramInt);
    arrayOfTypeface1[paramInt] = typeface;
    TYPEFACE_CACHE.put(typeface, arrayOfTypeface1);
    return typeface;
  }
  
  public static void setAssetManager(AssetManager paramAssetManager) {
    sAssetManager = paramAssetManager;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\TypefaceCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */