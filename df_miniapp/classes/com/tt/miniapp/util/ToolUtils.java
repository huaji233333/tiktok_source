package com.tt.miniapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import g.i;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

public class ToolUtils {
  public static String sCustomUA;
  
  public static String sSystemUA;
  
  private static byte[] base64StrToBytes(String paramString) {
    return i.decodeBase64(paramString).toByteArray();
  }
  
  public static long byte2Kb(long paramLong, boolean paramBoolean) {
    double d = paramLong;
    Double.isNaN(d);
    d /= 1024.0D;
    return paramBoolean ? (long)Math.ceil(d) : (long)d;
  }
  
  private static String bytesToBase64Str(byte[] paramArrayOfbyte) {
    return i.of(paramArrayOfbyte).base64();
  }
  
  private static String bytesToHexStr(byte[] paramArrayOfbyte) {
    return i.of(paramArrayOfbyte).hex();
  }
  
  public static void clearWebView(WebView paramWebView) {
    ViewParent viewParent = paramWebView.getParent();
    if (viewParent instanceof ViewGroup) {
      ((ViewGroup)viewParent).removeView((View)paramWebView);
      try {
        return;
      } finally {
        paramWebView = null;
      } 
    } 
  }
  
  public static Bitmap decodeByteArray(byte[] paramArrayOfbyte) {
    try {
      return BitmapFactory.decodeByteArray(paramArrayOfbyte, 0, paramArrayOfbyte.length);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ToolUtils", exception.getStackTrace());
      return null;
    } 
  }
  
  public static String decodeByteArrayToString(byte[] paramArrayOfbyte, String paramString) {
    byte b = -1;
    try {
      int i = paramString.hashCode();
      if (i != -1396204209) {
        if (i == 103195 && paramString.equals("hex"))
          b = 1; 
      } else if (paramString.equals("base64")) {
        b = 0;
      } 
      return (b != 0) ? ((b != 1) ? new String(paramArrayOfbyte, getCharSet(paramString)) : bytesToHexStr(paramArrayOfbyte)) : bytesToBase64Str(paramArrayOfbyte);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ToolUtils", exception.getStackTrace());
      return null;
    } 
  }
  
  public static long getByteBufferSize(ByteBuffer paramByteBuffer) {
    return (paramByteBuffer == null) ? 0L : (paramByteBuffer.limit() - paramByteBuffer.position());
  }
  
  private static byte[] getBytes(String paramString1, String paramString2) throws UnsupportedEncodingException {
    if (TextUtils.isEmpty(paramString1))
      return null; 
    byte b = -1;
    int i = paramString2.hashCode();
    if (i != -1396204209) {
      if (i == 103195 && paramString2.equals("hex"))
        b = 1; 
    } else if (paramString2.equals("base64")) {
      b = 0;
    } 
    return (b != 0) ? ((b != 1) ? paramString1.getBytes(getCharSet(paramString2)) : hexStrToBytes(paramString1)) : base64StrToBytes(paramString1);
  }
  
  private static String getCharSet(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return paramString; 
    byte b = -1;
    switch (paramString.hashCode()) {
      case 111113226:
        if (paramString.equals("ucs-2"))
          b = 1; 
        break;
      case 3584301:
        if (paramString.equals("ucs2"))
          b = 0; 
        break;
      case -119555963:
        if (paramString.equals("utf16le"))
          b = 2; 
        break;
      case -1109877331:
        if (paramString.equals("latin1"))
          b = 4; 
        break;
      case -1388966911:
        if (paramString.equals("binary"))
          b = 3; 
        break;
    } 
    return (b != 0 && b != 1 && b != 2) ? ((b != 3 && b != 4) ? paramString : "latin1") : "UTF-16LE";
  }
  
  public static String getCustomUA() {
    HostDependManager hostDependManager = HostDependManager.getInst();
    String str3 = "";
    String str2 = (String)hostDependManager.getHostData(2002, "");
    if (!TextUtils.isEmpty(str2))
      return str2; 
    if (!TextUtils.isEmpty(sCustomUA))
      return sCustomUA; 
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null) {
      str3 = initParamsEntity.getUaName();
      str1 = initParamsEntity.getPluginVersion();
    } else {
      str1 = "";
    } 
    String str5 = BaseBundleManager.getInst().getSdkCurrentVersionStr((Context)AppbrandContext.getInst().getApplicationContext());
    String str4 = str5;
    if (TextUtils.isEmpty(str5))
      str4 = "1.0.0"; 
    StringBuilder stringBuilder = new StringBuilder(getSystemUA());
    if (!TextUtils.isEmpty(str3)) {
      stringBuilder.append(" ");
      stringBuilder.append(str3);
      stringBuilder.append("/");
    } 
    stringBuilder.append(DevicesUtil.getVersion((Context)AppbrandContext.getInst().getApplicationContext()));
    stringBuilder.append(" ToutiaoMicroApp/");
    stringBuilder.append(str4);
    if (!TextUtils.isEmpty(str1)) {
      stringBuilder.append(" PluginVersion/");
      stringBuilder.append(str1);
    } 
    String str1 = stringBuilder.toString();
    sCustomUA = str1;
    return str1;
  }
  
  public static String getSystemUA() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/util/ToolUtils.sSystemUA : Ljava/lang/String;
    //   3: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   6: ifne -> 13
    //   9: getstatic com/tt/miniapp/util/ToolUtils.sSystemUA : Ljava/lang/String;
    //   12: areturn
    //   13: getstatic android/os/Build$VERSION.SDK_INT : I
    //   16: bipush #17
    //   18: if_icmplt -> 34
    //   21: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   24: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   27: invokestatic getDefaultUserAgent : (Landroid/content/Context;)Ljava/lang/String;
    //   30: astore_3
    //   31: goto -> 40
    //   34: ldc 'http.agent'
    //   36: invokestatic getProperty : (Ljava/lang/String;)Ljava/lang/String;
    //   39: astore_3
    //   40: new java/lang/StringBuffer
    //   43: dup
    //   44: invokespecial <init> : ()V
    //   47: astore #4
    //   49: aload_3
    //   50: invokevirtual length : ()I
    //   53: istore_2
    //   54: iconst_0
    //   55: istore_1
    //   56: iload_1
    //   57: iload_2
    //   58: if_icmpge -> 122
    //   61: aload_3
    //   62: iload_1
    //   63: invokevirtual charAt : (I)C
    //   66: istore_0
    //   67: iload_0
    //   68: bipush #31
    //   70: if_icmple -> 92
    //   73: iload_0
    //   74: bipush #127
    //   76: if_icmplt -> 82
    //   79: goto -> 92
    //   82: aload #4
    //   84: iload_0
    //   85: invokevirtual append : (C)Ljava/lang/StringBuffer;
    //   88: pop
    //   89: goto -> 115
    //   92: aload #4
    //   94: ldc_w '\u%04x'
    //   97: iconst_1
    //   98: anewarray java/lang/Object
    //   101: dup
    //   102: iconst_0
    //   103: iload_0
    //   104: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   107: aastore
    //   108: invokestatic a : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   111: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   114: pop
    //   115: iload_1
    //   116: iconst_1
    //   117: iadd
    //   118: istore_1
    //   119: goto -> 56
    //   122: aload #4
    //   124: invokevirtual toString : ()Ljava/lang/String;
    //   127: astore_3
    //   128: aload_3
    //   129: putstatic com/tt/miniapp/util/ToolUtils.sSystemUA : Ljava/lang/String;
    //   132: aload_3
    //   133: areturn
    //   134: astore_3
    //   135: goto -> 34
    // Exception table:
    //   from	to	target	type
    //   21	31	134	java/lang/Exception
  }
  
  private static byte[] hexStrToBytes(String paramString) {
    return i.decodeHex(paramString).toByteArray();
  }
  
  private static boolean isAnchorCase() {
    String str = AppbrandApplication.getInst().getSchema();
    return (HostDependManager.getInst().getAnchorConfig(str) != null);
  }
  
  public static boolean isAppInstalled(Context paramContext, String paramString) {
    try {
      PackageInfo packageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 0);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException = null;
    } 
    if (nameNotFoundException == null) {
      AppBrandLogger.d("ToolUtils", new Object[] { "没有安装" });
      return false;
    } 
    AppBrandLogger.d("ToolUtils", new Object[] { "已经安装" });
    return true;
  }
  
  public static boolean isInstalledApp(Context paramContext, Intent paramIntent) {
    if (paramIntent == null)
      return false; 
    PackageManager packageManager = paramContext.getPackageManager();
    if (packageManager == null)
      return false; 
    List list = packageManager.queryIntentActivities(paramIntent, 65536);
    return (list != null && list.size() > 0);
  }
  
  public static void onActivityExit(Activity paramActivity, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull -> 5
    //   4: return
    //   5: aload_0
    //   6: instanceof com/tt/miniapphost/MiniappHostBase
    //   9: ifeq -> 461
    //   12: aload_0
    //   13: checkcast com/tt/miniapphost/MiniappHostBase
    //   16: astore #4
    //   18: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   21: invokevirtual getMiniAppLaunchConfig : ()Lcom/tt/miniapp/launch/MiniAppLaunchConfig;
    //   24: invokevirtual isLaunchWithFloatStyle : ()Z
    //   27: ifeq -> 44
    //   30: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   33: aload_0
    //   34: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   37: invokevirtual getSchema : ()Ljava/lang/String;
    //   40: iconst_1
    //   41: invokevirtual dismissLiveWindowView : (Landroid/app/Activity;Ljava/lang/String;Z)V
    //   44: invokestatic dismissAllFavoriteGuide : ()V
    //   47: iload_1
    //   48: bipush #10
    //   50: if_icmpne -> 58
    //   53: iconst_1
    //   54: istore_3
    //   55: goto -> 60
    //   58: iconst_0
    //   59: istore_3
    //   60: invokestatic isAnchorCase : ()Z
    //   63: ifeq -> 105
    //   66: iload_1
    //   67: iconst_2
    //   68: if_icmpeq -> 86
    //   71: iload_1
    //   72: bipush #9
    //   74: if_icmpeq -> 86
    //   77: iload_1
    //   78: bipush #10
    //   80: if_icmpeq -> 86
    //   83: goto -> 105
    //   86: aload #4
    //   88: invokevirtual getActivityProxy : ()Lcom/tt/miniapphost/IActivityProxy;
    //   91: astore #5
    //   93: aload #5
    //   95: ifnull -> 105
    //   98: aload #5
    //   100: invokeinterface hideAnchorButton : ()V
    //   105: iload_1
    //   106: istore_2
    //   107: aload_0
    //   108: invokevirtual getClass : ()Ljava/lang/Class;
    //   111: invokestatic isInHostStack : (Ljava/lang/Class;)Z
    //   114: ifeq -> 127
    //   117: iload_1
    //   118: istore_2
    //   119: iload_1
    //   120: bipush #12
    //   122: if_icmpeq -> 127
    //   125: iconst_1
    //   126: istore_2
    //   127: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   130: invokevirtual getMiniAppLifeCycleInstance : ()Lcom/tt/c/a;
    //   133: ifnull -> 173
    //   136: new java/lang/StringBuilder
    //   139: dup
    //   140: ldc_w 'onActivityExit:'
    //   143: invokespecial <init> : (Ljava/lang/String;)V
    //   146: astore #5
    //   148: aload #5
    //   150: iload_2
    //   151: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   154: pop
    //   155: ldc_w 'tma_MiniAppLifeCycle'
    //   158: iconst_1
    //   159: anewarray java/lang/Object
    //   162: dup
    //   163: iconst_0
    //   164: aload #5
    //   166: invokevirtual toString : ()Ljava/lang/String;
    //   169: aastore
    //   170: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   173: iload_2
    //   174: bipush #12
    //   176: if_icmpeq -> 192
    //   179: aload #4
    //   181: invokevirtual isTriggeredHomeOrRecentApp : ()Z
    //   184: ifeq -> 192
    //   187: aload_0
    //   188: iload_3
    //   189: invokestatic moveHostActivityTaskToFront : (Landroid/app/Activity;Z)V
    //   192: ldc 'ToolUtils'
    //   194: iconst_2
    //   195: anewarray java/lang/Object
    //   198: dup
    //   199: iconst_0
    //   200: ldc_w 'moveTaskToBack exitType:'
    //   203: aastore
    //   204: dup
    //   205: iconst_1
    //   206: iload_2
    //   207: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   210: aastore
    //   211: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   214: iload_2
    //   215: iconst_2
    //   216: if_icmpeq -> 355
    //   219: iload_2
    //   220: bipush #9
    //   222: if_icmpeq -> 355
    //   225: iload_2
    //   226: bipush #10
    //   228: if_icmpeq -> 355
    //   231: iload_2
    //   232: bipush #12
    //   234: if_icmpeq -> 295
    //   237: iload_2
    //   238: bipush #13
    //   240: if_icmpeq -> 269
    //   243: aload_0
    //   244: invokevirtual finish : ()V
    //   247: ldc 'ToolUtils'
    //   249: iconst_2
    //   250: anewarray java/lang/Object
    //   253: dup
    //   254: iconst_0
    //   255: ldc_w 'Activity finish activity:'
    //   258: aastore
    //   259: dup
    //   260: iconst_1
    //   261: aload_0
    //   262: aastore
    //   263: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   266: goto -> 380
    //   269: aload_0
    //   270: invokevirtual finishAndRemoveTask : ()V
    //   273: ldc 'ToolUtils'
    //   275: iconst_2
    //   276: anewarray java/lang/Object
    //   279: dup
    //   280: iconst_0
    //   281: ldc_w 'Activity finishAndRemoveTask activity:'
    //   284: aastore
    //   285: dup
    //   286: iconst_1
    //   287: aload_0
    //   288: aastore
    //   289: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   292: goto -> 380
    //   295: aload #4
    //   297: invokevirtual isInHostStack : ()Z
    //   300: ifeq -> 329
    //   303: aload_0
    //   304: invokevirtual finish : ()V
    //   307: ldc 'ToolUtils'
    //   309: iconst_2
    //   310: anewarray java/lang/Object
    //   313: dup
    //   314: iconst_0
    //   315: ldc_w 'Host Stack Activity finish activity:'
    //   318: aastore
    //   319: dup
    //   320: iconst_1
    //   321: aload_0
    //   322: aastore
    //   323: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   326: goto -> 380
    //   329: aload_0
    //   330: invokevirtual finishAndRemoveTask : ()V
    //   333: ldc 'ToolUtils'
    //   335: iconst_2
    //   336: anewarray java/lang/Object
    //   339: dup
    //   340: iconst_0
    //   341: ldc_w 'Activity finishAndRemoveTask activity:'
    //   344: aastore
    //   345: dup
    //   346: iconst_1
    //   347: aload_0
    //   348: aastore
    //   349: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   352: goto -> 380
    //   355: aload_0
    //   356: iconst_1
    //   357: invokevirtual moveTaskToBack : (Z)Z
    //   360: pop
    //   361: ldc 'ToolUtils'
    //   363: iconst_2
    //   364: anewarray java/lang/Object
    //   367: dup
    //   368: iconst_0
    //   369: ldc_w 'moveTaskToBack activity:'
    //   372: aastore
    //   373: dup
    //   374: iconst_1
    //   375: aload_0
    //   376: aastore
    //   377: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   380: iload_3
    //   381: ifeq -> 401
    //   384: aload_0
    //   385: invokestatic changeToSilentHideActivityAnimation : (Landroid/app/Activity;)V
    //   388: aload_0
    //   389: invokevirtual getWindow : ()Landroid/view/Window;
    //   392: sipush #8192
    //   395: invokevirtual addFlags : (I)V
    //   398: goto -> 417
    //   401: aload #4
    //   403: invokevirtual getActivityProxy : ()Lcom/tt/miniapphost/IActivityProxy;
    //   406: astore_0
    //   407: aload_0
    //   408: ifnull -> 417
    //   411: aload_0
    //   412: invokeinterface overrideActivityExitAnimation : ()V
    //   417: iload_2
    //   418: bipush #12
    //   420: if_icmpeq -> 429
    //   423: aconst_null
    //   424: iconst_0
    //   425: invokestatic backApp : (Ljava/lang/String;Z)Z
    //   428: pop
    //   429: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   432: invokevirtual getForeBackgroundManager : ()Lcom/tt/miniapp/manager/ForeBackgroundManager;
    //   435: iconst_1
    //   436: invokevirtual setGoingBackground : (Z)V
    //   439: return
    //   440: astore_0
    //   441: ldc 'ToolUtils'
    //   443: iconst_2
    //   444: anewarray java/lang/Object
    //   447: dup
    //   448: iconst_0
    //   449: ldc_w 'onActivityExit'
    //   452: aastore
    //   453: dup
    //   454: iconst_1
    //   455: aload_0
    //   456: aastore
    //   457: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   460: return
    //   461: aload_0
    //   462: invokevirtual isFinishing : ()Z
    //   465: ifne -> 476
    //   468: aload_0
    //   469: invokevirtual finish : ()V
    //   472: aload_0
    //   473: invokestatic changeToSilentHideActivityAnimation : (Landroid/app/Activity;)V
    //   476: return
    // Exception table:
    //   from	to	target	type
    //   355	361	440	java/lang/Exception
  }
  
  public static byte[] readBytes(String paramString) {
    try {
      FileInputStream fileInputStream = new FileInputStream(paramString);
      byte[] arrayOfByte = new byte[fileInputStream.available()];
      fileInputStream.read(arrayOfByte);
      fileInputStream.close();
      return arrayOfByte;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ToolUtils", exception.getStackTrace());
      return null;
    } 
  }
  
  public static String readString(String paramString1, String paramString2) {
    byte[] arrayOfByte = readBytes(paramString1);
    byte b = -1;
    try {
      int i = paramString2.hashCode();
      if (i != -1396204209) {
        if (i == 103195 && paramString2.equals("hex"))
          b = 1; 
      } else if (paramString2.equals("base64")) {
        b = 0;
      } 
      return (b != 0) ? ((b != 1) ? new String(arrayOfByte, getCharSet(paramString2)) : bytesToHexStr(arrayOfByte)) : bytesToBase64Str(arrayOfByte);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ToolUtils", exception.getStackTrace());
      return null;
    } 
  }
  
  public static String toAndroidStyleColor(String paramString) {
    if (paramString == null)
      return null; 
    int i = paramString.length();
    if (i == 7)
      return paramString; 
    String str = paramString;
    if (i == 4) {
      str = paramString;
      if (paramString.charAt(0) == '#') {
        StringBuilder stringBuilder = new StringBuilder("#");
        stringBuilder.append(paramString.charAt(1));
        stringBuilder.append(paramString.charAt(1));
        stringBuilder.append(paramString.charAt(2));
        stringBuilder.append(paramString.charAt(2));
        stringBuilder.append(paramString.charAt(3));
        stringBuilder.append(paramString.charAt(3));
        str = stringBuilder.toString();
      } 
    } 
    return str;
  }
  
  public static boolean writeByteBufferToFile(String paramString, ByteBuffer paramByteBuffer) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    File file = new File(paramString);
    try {
      if (!file.exists())
        file.createNewFile(); 
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      fileOutputStream.getChannel().write(paramByteBuffer);
      fileOutputStream.close();
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e("ToolUtils", new Object[] { exception });
      return false;
    } 
  }
  
  public static void writeBytes(String paramString, byte[] paramArrayOfbyte) throws IOException {
    if (paramArrayOfbyte != null) {
      if (paramArrayOfbyte.length == 0)
        return; 
      FileOutputStream fileOutputStream = new FileOutputStream(paramString);
      DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
      dataOutputStream.write(paramArrayOfbyte, 0, paramArrayOfbyte.length);
      dataOutputStream.close();
      fileOutputStream.close();
    } 
  }
  
  public static boolean writeStringToFile(String paramString1, String paramString2, String paramString3) throws Exception {
    if (TextUtils.isEmpty(paramString1))
      return false; 
    File file = new File(paramString1);
    if (!file.exists())
      file.createNewFile(); 
    writeBytes(paramString1, getBytes(paramString2, paramString3));
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\ToolUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */