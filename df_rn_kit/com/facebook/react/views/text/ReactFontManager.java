package com.facebook.react.views.text;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Map;

public class ReactFontManager {
  private static final String[] EXTENSIONS = new String[] { "", "_bold", "_italic", "_bold_italic" };
  
  private static final String[] FILE_EXTENSIONS = new String[] { ".ttf", ".otf" };
  
  private static ReactFontManager sReactFontManagerInstance;
  
  private Map<String, FontFamily> mFontCache = new HashMap<String, FontFamily>();
  
  private static Typeface createTypeface(String paramString, int paramInt, AssetManager paramAssetManager) {
    String str = EXTENSIONS[paramInt];
    String[] arrayOfString = FILE_EXTENSIONS;
    int j = arrayOfString.length;
    int i = 0;
    while (true) {
      if (i < j) {
        String str1 = arrayOfString[i];
        StringBuilder stringBuilder = new StringBuilder("fonts/");
        stringBuilder.append(paramString);
        stringBuilder.append(str);
        stringBuilder.append(str1);
        str1 = stringBuilder.toString();
        try {
          return Typeface.createFromAsset(paramAssetManager, str1);
        } catch (RuntimeException runtimeException) {
          i++;
          continue;
        } 
      } 
      return Typeface.create(paramString, paramInt);
    } 
  }
  
  public static ReactFontManager getInstance() {
    if (sReactFontManagerInstance == null)
      sReactFontManagerInstance = new ReactFontManager(); 
    return sReactFontManagerInstance;
  }
  
  public Typeface getTypeface(String paramString, int paramInt, AssetManager paramAssetManager) {
    FontFamily fontFamily2 = this.mFontCache.get(paramString);
    FontFamily fontFamily1 = fontFamily2;
    if (fontFamily2 == null) {
      fontFamily1 = new FontFamily();
      this.mFontCache.put(paramString, fontFamily1);
    } 
    Typeface typeface2 = fontFamily1.getTypeface(paramInt);
    Typeface typeface1 = typeface2;
    if (typeface2 == null) {
      Typeface typeface = createTypeface(paramString, paramInt, paramAssetManager);
      typeface1 = typeface;
      if (typeface != null) {
        fontFamily1.setTypeface(paramInt, typeface);
        typeface1 = typeface;
      } 
    } 
    return typeface1;
  }
  
  public void setTypeface(String paramString, int paramInt, Typeface paramTypeface) {
    if (paramTypeface != null) {
      FontFamily fontFamily2 = this.mFontCache.get(paramString);
      FontFamily fontFamily1 = fontFamily2;
      if (fontFamily2 == null) {
        fontFamily1 = new FontFamily();
        this.mFontCache.put(paramString, fontFamily1);
      } 
      fontFamily1.setTypeface(paramInt, paramTypeface);
    } 
  }
  
  static class FontFamily {
    private SparseArray<Typeface> mTypefaceSparseArray = new SparseArray(4);
    
    private FontFamily() {}
    
    public Typeface getTypeface(int param1Int) {
      return (Typeface)this.mTypefaceSparseArray.get(param1Int);
    }
    
    public void setTypeface(int param1Int, Typeface param1Typeface) {
      this.mTypefaceSparseArray.put(param1Int, param1Typeface);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactFontManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */