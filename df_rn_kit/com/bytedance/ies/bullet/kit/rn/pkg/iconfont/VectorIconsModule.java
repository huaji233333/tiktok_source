package com.bytedance.ies.bullet.kit.rn.pkg.iconfont;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.views.text.ReactFontManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class VectorIconsModule extends ReactContextBaseJavaModule {
  VectorIconsModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  @ReactMethod
  public void getImageForFont(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Callback paramCallback) {
    ReactApplicationContext reactApplicationContext = getReactApplicationContext();
    File file1 = reactApplicationContext.getCacheDir();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(file1.getAbsolutePath());
    stringBuilder2.append("/");
    String str4 = stringBuilder2.toString();
    float f = (reactApplicationContext.getResources().getDisplayMetrics()).density;
    StringBuilder stringBuilder3 = new StringBuilder("@");
    int i = (int)f;
    if (f == i) {
      str3 = Integer.toString(i);
    } else {
      str3 = Float.toString(f);
    } 
    stringBuilder3.append(str3);
    stringBuilder3.append("x");
    String str3 = stringBuilder3.toString();
    i = Math.round(paramInteger1.intValue() * f);
    stringBuilder3 = new StringBuilder();
    stringBuilder3.append(paramString1);
    stringBuilder3.append(":");
    stringBuilder3.append(paramString2);
    stringBuilder3.append(":");
    stringBuilder3.append(paramInteger2);
    String str5 = Integer.toString(stringBuilder3.toString().hashCode(), 32);
    StringBuilder stringBuilder4 = new StringBuilder();
    stringBuilder4.append(str4);
    stringBuilder4.append(str5);
    stringBuilder4.append("_");
    stringBuilder4.append(paramInteger1);
    stringBuilder4.append(str3);
    stringBuilder4.append(".png");
    String str2 = stringBuilder4.toString();
    StringBuilder stringBuilder1 = new StringBuilder("file://");
    stringBuilder1.append(str2);
    str5 = stringBuilder1.toString();
    File file2 = new File(str2);
    boolean bool = file2.exists();
    stringBuilder1 = null;
    str4 = null;
    str2 = null;
    if (bool) {
      paramCallback.invoke(new Object[] { null, str5 });
      return;
    } 
    Typeface typeface = ReactFontManager.getInstance().getTypeface(paramString1, 0, reactApplicationContext.getAssets());
    Paint paint = new Paint();
    paint.setTypeface(typeface);
    paint.setColor(paramInteger2.intValue());
    paint.setTextSize(i);
    paint.setAntiAlias(true);
    Rect rect = new Rect();
    paint.getTextBounds(paramString2, 0, paramString2.length(), rect);
    Bitmap bitmap = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888);
    (new Canvas(bitmap)).drawText(paramString2, -rect.left, -rect.top, paint);
    String str1 = str2;
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(file2);
      try {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return;
      } catch (FileNotFoundException null) {
      
      } catch (IOException null) {
      
      } finally {
        String str;
        str2 = null;
        FileOutputStream fileOutputStream1 = fileOutputStream;
      } 
    } catch (FileNotFoundException null) {
      paramString2 = str4;
    } catch (IOException iOException) {
      StringBuilder stringBuilder6 = stringBuilder1;
      StringBuilder stringBuilder5 = stringBuilder6;
      paramCallback.invoke(new Object[] { iOException.getMessage() });
      if (stringBuilder6 != null)
        try {
          stringBuilder6.close();
          return;
        } catch (IOException iOException1) {
          iOException1.printStackTrace();
          return;
        }  
    } finally {}
    str1 = paramString2;
    paramCallback.invoke(new Object[] { iOException.getMessage() });
    if (paramString2 != null)
      try {
        paramString2.close();
        return;
      } catch (IOException iOException1) {
        iOException1.printStackTrace();
      }  
  }
  
  public String getName() {
    return "RNVectorIconsModule";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\iconfont\VectorIconsModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */