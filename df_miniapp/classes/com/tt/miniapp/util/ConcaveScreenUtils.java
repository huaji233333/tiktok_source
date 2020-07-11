package com.tt.miniapp.util;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tt.miniapphost.util.UIUtils;

public class ConcaveScreenUtils {
  private static int sHWConcaveScreenHeight = -1;
  
  private static int sIsHwConcaveScreen = -1;
  
  private static int sIsOppoConcaveScreen = -1;
  
  private static int sIsVivoConcaveScreen = -1;
  
  public static int getHWConcaveScreenHeight(Context paramContext) {
    int i = sHWConcaveScreenHeight;
    if (i != -1)
      return i; 
    int j = getNotchSize(paramContext)[1];
    i = j;
    if (j <= 0)
      i = (int)UIUtils.dip2Px(paramContext, 28.0F); 
    sHWConcaveScreenHeight = i;
    return i;
  }
  
  public static int getHWConcaveScreenWidht(Context paramContext) {
    return getNotchSize(paramContext)[0];
  }
  
  private static int[] getNotchSize(Context paramContext) {
    // Byte code:
    //   0: iconst_2
    //   1: newarray int
    //   3: astore_1
    //   4: aload_1
    //   5: dup
    //   6: iconst_0
    //   7: iconst_0
    //   8: iastore
    //   9: dup
    //   10: iconst_1
    //   11: iconst_0
    //   12: iastore
    //   13: pop
    //   14: aload_0
    //   15: ifnonnull -> 20
    //   18: aload_1
    //   19: areturn
    //   20: aload_0
    //   21: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   24: ldc 'com.huawei.android.util.HwNotchSizeUtil'
    //   26: invokevirtual loadClass : (Ljava/lang/String;)Ljava/lang/Class;
    //   29: astore_0
    //   30: aload_0
    //   31: ldc 'getNotchSize'
    //   33: iconst_0
    //   34: anewarray java/lang/Class
    //   37: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   40: aload_0
    //   41: iconst_0
    //   42: anewarray java/lang/Object
    //   45: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   48: checkcast [I
    //   51: checkcast [I
    //   54: astore_0
    //   55: aload_0
    //   56: areturn
    //   57: astore_0
    //   58: aload_1
    //   59: areturn
    // Exception table:
    //   from	to	target	type
    //   20	55	57	java/lang/Exception
    //   20	55	57	finally
  }
  
  private static int getRealHeight(Context paramContext) {
    if (paramContext == null)
      return 0; 
    if (Build.VERSION.SDK_INT >= 17) {
      DisplayMetrics displayMetrics = new DisplayMetrics();
      ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
      return displayMetrics.heightPixels;
    } 
    return UIUtils.getScreenHeight(paramContext);
  }
  
  public static boolean isHWConcaveScreen(Context paramContext) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static boolean isOVConcaveScreen(Context paramContext) {
    return (isVivoConcaveScreen() || isOppoConcaveScreen(paramContext));
  }
  
  public static boolean isOVConcaveScreen(Context paramContext, boolean paramBoolean) {
    return (isVivoConcaveScreen() || isOppoConcaveScreen(paramContext, paramBoolean));
  }
  
  public static boolean isOppoConcaveScreen(Context paramContext) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static boolean isOppoConcaveScreen(Context paramContext, boolean paramBoolean) {
    // Byte code:
    //   0: ldc 'oppo'
    //   2: getstatic android/os/Build.BRAND : Ljava/lang/String;
    //   5: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   8: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   11: ifeq -> 31
    //   14: aload_0
    //   15: ifnull -> 31
    //   18: aload_0
    //   19: invokevirtual getPackageManager : ()Landroid/content/pm/PackageManager;
    //   22: ldc 'com.oppo.feature.screen.heteromorphism'
    //   24: invokevirtual hasSystemFeature : (Ljava/lang/String;)Z
    //   27: istore_2
    //   28: goto -> 33
    //   31: iconst_0
    //   32: istore_2
    //   33: iload_2
    //   34: ifeq -> 65
    //   37: getstatic android/os/Build$VERSION.SDK_INT : I
    //   40: bipush #17
    //   42: if_icmplt -> 63
    //   45: iload_1
    //   46: ifeq -> 63
    //   49: aload_0
    //   50: invokestatic getRealHeight : (Landroid/content/Context;)I
    //   53: sipush #2280
    //   56: if_icmplt -> 61
    //   59: iconst_1
    //   60: ireturn
    //   61: iconst_0
    //   62: ireturn
    //   63: iload_2
    //   64: ireturn
    //   65: iconst_0
    //   66: ireturn
    //   67: astore_3
    //   68: goto -> 31
    // Exception table:
    //   from	to	target	type
    //   0	14	67	finally
    //   18	28	67	finally
  }
  
  public static boolean isVivoAndHWConcaveScreen(Context paramContext) {
    return (isVivoConcaveScreen() || isHWConcaveScreen(paramContext));
  }
  
  public static boolean isVivoConcaveScreen() {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static void preload(Context paramContext) {
    isVivoConcaveScreen();
    isOppoConcaveScreen(paramContext);
    isHWConcaveScreen(paramContext);
    getHWConcaveScreenHeight(paramContext);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\ConcaveScreenUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */