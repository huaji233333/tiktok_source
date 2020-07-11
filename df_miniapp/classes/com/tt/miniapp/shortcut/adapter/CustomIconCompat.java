package com.tt.miniapp.shortcut.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppBrandLogger;

public class CustomIconCompat {
  private int mInt1;
  
  private int mInt2;
  
  private Object mObj1;
  
  private final int mType;
  
  private CustomIconCompat(int paramInt) {
    this.mType = paramInt;
  }
  
  static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap paramBitmap) {
    int i = (int)(Math.min(paramBitmap.getWidth(), paramBitmap.getHeight()) * 0.6666667F);
    Bitmap bitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint(3);
    float f1 = i;
    float f2 = 0.5F * f1;
    float f3 = 0.9166667F * f2;
    float f4 = 0.010416667F * f1;
    paint.setColor(0);
    paint.setShadowLayer(f4, 0.0F, f1 * 0.020833334F, 1023410176);
    canvas.drawCircle(f2, f2, f3, paint);
    paint.setShadowLayer(f4, 0.0F, 0.0F, 503316480);
    canvas.drawCircle(f2, f2, f3, paint);
    paint.clearShadowLayer();
    paint.setColor(-16777216);
    Shader.TileMode tileMode = Shader.TileMode.CLAMP;
    BitmapShader bitmapShader = new BitmapShader(paramBitmap, tileMode, tileMode);
    Matrix matrix = new Matrix();
    matrix.setTranslate((-(paramBitmap.getWidth() - i) / 2), (-(paramBitmap.getHeight() - i) / 2));
    bitmapShader.setLocalMatrix(matrix);
    paint.setShader((Shader)bitmapShader);
    canvas.drawCircle(f2, f2, f3, paint);
    canvas.setBitmap(null);
    return bitmap;
  }
  
  public static CustomIconCompat createWithAdaptiveBitmap(Bitmap paramBitmap) {
    if (paramBitmap != null) {
      CustomIconCompat customIconCompat = new CustomIconCompat(5);
      customIconCompat.mObj1 = paramBitmap;
      return customIconCompat;
    } 
    throw new IllegalArgumentException("Bitmap must not be null.");
  }
  
  public static CustomIconCompat createWithBitmap(Bitmap paramBitmap) {
    if (paramBitmap != null) {
      CustomIconCompat customIconCompat = new CustomIconCompat(1);
      customIconCompat.mObj1 = paramBitmap;
      return customIconCompat;
    } 
    throw new IllegalArgumentException("Bitmap must not be null.");
  }
  
  public static CustomIconCompat createWithContentUri(Uri paramUri) {
    if (paramUri != null)
      return createWithContentUri(paramUri.toString()); 
    throw new IllegalArgumentException("Uri must not be null.");
  }
  
  public static CustomIconCompat createWithContentUri(String paramString) {
    if (paramString != null) {
      CustomIconCompat customIconCompat = new CustomIconCompat(4);
      customIconCompat.mObj1 = paramString;
      return customIconCompat;
    } 
    throw new IllegalArgumentException("Uri must not be null.");
  }
  
  public static CustomIconCompat createWithData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    if (paramArrayOfbyte != null) {
      CustomIconCompat customIconCompat = new CustomIconCompat(3);
      customIconCompat.mObj1 = paramArrayOfbyte;
      customIconCompat.mInt1 = paramInt1;
      customIconCompat.mInt2 = paramInt2;
      return customIconCompat;
    } 
    throw new IllegalArgumentException("Data must not be null.");
  }
  
  public static CustomIconCompat createWithResource(Context paramContext, int paramInt) {
    if (paramContext != null) {
      CustomIconCompat customIconCompat = new CustomIconCompat(2);
      customIconCompat.mInt1 = paramInt;
      customIconCompat.mObj1 = paramContext;
      return customIconCompat;
    } 
    throw new IllegalArgumentException("Context must not be null.");
  }
  
  public static CustomIconCompat createWithUrl(String paramString) {
    if (!TextUtils.isEmpty(paramString)) {
      try {
        byte[] arrayOfByte = NetUtil.readImage(paramString);
        if (arrayOfByte != null && arrayOfByte.length > 0)
          return createWithBitmap(BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length)); 
      } catch (Exception exception) {
        AppBrandLogger.e("CustomIconCompat", new Object[] { exception });
      } 
      return null;
    } 
    throw new IllegalArgumentException("url must not be empty.");
  }
  
  public void addToShortcutIntent(Intent paramIntent) {
    int i = this.mType;
    if (i != 1) {
      if (i != 2) {
        if (i == 5) {
          paramIntent.putExtra("android.intent.extra.shortcut.ICON", (Parcelable)createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1));
          return;
        } 
        throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
      } 
      paramIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", (Parcelable)Intent.ShortcutIconResource.fromContext((Context)this.mObj1, this.mInt1));
      return;
    } 
    paramIntent.putExtra("android.intent.extra.shortcut.ICON", (Parcelable)this.mObj1);
  }
  
  public Icon toIcon() {
    int i = this.mType;
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4) {
            if (i == 5)
              return (Build.VERSION.SDK_INT >= 26) ? Icon.createWithAdaptiveBitmap((Bitmap)this.mObj1) : Icon.createWithBitmap(createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1)); 
            throw new IllegalArgumentException("Unknown type");
          } 
          return Icon.createWithContentUri((String)this.mObj1);
        } 
        return Icon.createWithData((byte[])this.mObj1, this.mInt1, this.mInt2);
      } 
      return Icon.createWithResource((Context)this.mObj1, this.mInt1);
    } 
    return Icon.createWithBitmap((Bitmap)this.mObj1);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\adapter\CustomIconCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */