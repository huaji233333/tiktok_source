package com.tt.miniapp.component.nativeview.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.tt.miniapphost.util.CoordinateTransformUtil;

public class MapUtil {
  private static Bitmap.Config getConfig(Bitmap paramBitmap) {
    Bitmap.Config config2 = paramBitmap.getConfig();
    Bitmap.Config config1 = config2;
    if (config2 == null)
      config1 = Bitmap.Config.ARGB_8888; 
    return config1;
  }
  
  public static boolean isValidLat(double paramDouble) {
    return CoordinateTransformUtil.isValidLatLng(paramDouble, 0.0D);
  }
  
  public static boolean isValidLatLng(double paramDouble1, double paramDouble2) {
    return CoordinateTransformUtil.isValidLatLng(paramDouble1, paramDouble2);
  }
  
  public static boolean isValidLng(double paramDouble) {
    return CoordinateTransformUtil.isValidLatLng(0.0D, paramDouble);
  }
  
  public static Bitmap parseMiniAppImagePath(String paramString, Bitmap paramBitmap) {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic isNetworkUrl : (Ljava/lang/String;)Z
    //   4: istore_3
    //   5: aconst_null
    //   6: astore #5
    //   8: iload_3
    //   9: ifeq -> 64
    //   12: aload_0
    //   13: invokestatic readImage : (Ljava/lang/String;)[B
    //   16: astore_0
    //   17: goto -> 36
    //   20: astore_0
    //   21: ldc 'tma_MapUtil'
    //   23: iconst_1
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_0
    //   30: aastore
    //   31: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   34: aconst_null
    //   35: astore_0
    //   36: aload #5
    //   38: astore #4
    //   40: aload_0
    //   41: ifnull -> 128
    //   44: aload #5
    //   46: astore #4
    //   48: aload_0
    //   49: arraylength
    //   50: ifle -> 128
    //   53: aload_0
    //   54: iconst_0
    //   55: aload_0
    //   56: arraylength
    //   57: invokestatic decodeByteArray : ([BII)Landroid/graphics/Bitmap;
    //   60: astore_0
    //   61: goto -> 125
    //   64: aload_0
    //   65: invokestatic isTTFileDir : (Ljava/lang/String;)Z
    //   68: ifeq -> 93
    //   71: invokestatic inst : ()Lcom/tt/miniapp/storage/filestorge/FileManager;
    //   74: aload_0
    //   75: invokevirtual getRealFilePath : (Ljava/lang/String;)Ljava/lang/String;
    //   78: invokestatic decodeFile : (Ljava/lang/String;)Landroid/graphics/Bitmap;
    //   81: astore_0
    //   82: aload #5
    //   84: astore #4
    //   86: aload_0
    //   87: ifnull -> 128
    //   90: goto -> 125
    //   93: aload #5
    //   95: astore #4
    //   97: aload_0
    //   98: invokestatic loadByteFromStream : (Ljava/lang/String;)[B
    //   101: ifnull -> 128
    //   104: aload_0
    //   105: invokestatic loadByteFromStream : (Ljava/lang/String;)[B
    //   108: astore_0
    //   109: aload_0
    //   110: iconst_0
    //   111: aload_0
    //   112: arraylength
    //   113: invokestatic decodeByteArray : ([BII)Landroid/graphics/Bitmap;
    //   116: astore_0
    //   117: aload #5
    //   119: astore #4
    //   121: aload_0
    //   122: ifnull -> 128
    //   125: aload_0
    //   126: astore #4
    //   128: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   131: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   134: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   137: invokevirtual getDisplayMetrics : ()Landroid/util/DisplayMetrics;
    //   140: getfield scaledDensity : F
    //   143: fstore_2
    //   144: aload #4
    //   146: ifnull -> 157
    //   149: aload #4
    //   151: fload_2
    //   152: iconst_1
    //   153: invokestatic resizeBitmapByScale : (Landroid/graphics/Bitmap;FZ)Landroid/graphics/Bitmap;
    //   156: areturn
    //   157: aload_1
    //   158: areturn
    // Exception table:
    //   from	to	target	type
    //   12	17	20	java/lang/Exception
  }
  
  public static Bitmap resizeBitmapByScale(Bitmap paramBitmap, float paramFloat, boolean paramBoolean) {
    int i = Math.round(paramBitmap.getWidth() * paramFloat);
    int j = Math.round(paramBitmap.getHeight() * paramFloat);
    if (i == paramBitmap.getWidth() && j == paramBitmap.getHeight())
      return paramBitmap; 
    Bitmap bitmap = Bitmap.createBitmap(i, j, getConfig(paramBitmap));
    Canvas canvas = new Canvas(bitmap);
    canvas.scale(paramFloat, paramFloat);
    canvas.drawBitmap(paramBitmap, 0.0F, 0.0F, new Paint(6));
    if (paramBoolean)
      paramBitmap.recycle(); 
    return bitmap;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\map\MapUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */