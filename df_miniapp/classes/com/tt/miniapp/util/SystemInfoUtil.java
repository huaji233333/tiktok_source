package com.tt.miniapp.util;

import android.app.Application;
import android.content.Context;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

public class SystemInfoUtil {
  private static WeakReference<IActivityRootView> mWeakActivityInfo;
  
  public static JSONObject constructSafeArea(Context paramContext, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: invokestatic currentTimeMillis : ()J
    //   3: lstore #9
    //   5: ldc 'tma_SystemInfoUtil'
    //   7: iconst_1
    //   8: anewarray java/lang/Object
    //   11: dup
    //   12: iconst_0
    //   13: ldc 'start ==== '
    //   15: aastore
    //   16: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   19: new org/json/JSONObject
    //   22: dup
    //   23: invokespecial <init> : ()V
    //   26: astore #12
    //   28: getstatic com/tt/miniapp/util/SystemInfoUtil.mWeakActivityInfo : Ljava/lang/ref/WeakReference;
    //   31: ifnull -> 369
    //   34: getstatic com/tt/miniapp/util/SystemInfoUtil.mWeakActivityInfo : Ljava/lang/ref/WeakReference;
    //   37: invokevirtual get : ()Ljava/lang/Object;
    //   40: ifnull -> 369
    //   43: getstatic com/tt/miniapp/util/SystemInfoUtil.mWeakActivityInfo : Ljava/lang/ref/WeakReference;
    //   46: invokevirtual get : ()Ljava/lang/Object;
    //   49: checkcast com/tt/miniapp/util/SystemInfoUtil$IActivityRootView
    //   52: invokeinterface getActivityRootView : ()Landroid/view/View;
    //   57: ifnull -> 369
    //   60: getstatic android/os/Build$VERSION.SDK_INT : I
    //   63: bipush #28
    //   65: if_icmplt -> 369
    //   68: getstatic com/tt/miniapp/util/SystemInfoUtil.mWeakActivityInfo : Ljava/lang/ref/WeakReference;
    //   71: invokevirtual get : ()Ljava/lang/Object;
    //   74: checkcast com/tt/miniapp/util/SystemInfoUtil$IActivityRootView
    //   77: invokeinterface getActivityRootView : ()Landroid/view/View;
    //   82: invokevirtual getRootWindowInsets : ()Landroid/view/WindowInsets;
    //   85: astore #11
    //   87: aload #11
    //   89: ifnull -> 369
    //   92: aload #11
    //   94: invokevirtual getDisplayCutout : ()Landroid/view/DisplayCutout;
    //   97: astore #11
    //   99: goto -> 102
    //   102: aload #11
    //   104: ifnull -> 217
    //   107: getstatic android/os/Build$VERSION.SDK_INT : I
    //   110: bipush #28
    //   112: if_icmplt -> 217
    //   115: aload #11
    //   117: invokevirtual getSafeInsetLeft : ()I
    //   120: i2f
    //   121: aload_0
    //   122: invokestatic getPixelRadio : (Landroid/content/Context;)F
    //   125: fdiv
    //   126: f2d
    //   127: invokestatic ceil : (D)D
    //   130: d2i
    //   131: istore #4
    //   133: iload_1
    //   134: aload #11
    //   136: invokevirtual getSafeInsetRight : ()I
    //   139: i2f
    //   140: aload_0
    //   141: invokestatic getPixelRadio : (Landroid/content/Context;)F
    //   144: fdiv
    //   145: f2d
    //   146: invokestatic ceil : (D)D
    //   149: d2i
    //   150: isub
    //   151: istore #5
    //   153: aload #11
    //   155: invokevirtual getSafeInsetTop : ()I
    //   158: i2f
    //   159: aload_0
    //   160: invokestatic getPixelRadio : (Landroid/content/Context;)F
    //   163: fdiv
    //   164: f2d
    //   165: invokestatic ceil : (D)D
    //   168: d2i
    //   169: istore #7
    //   171: iload_2
    //   172: aload #11
    //   174: invokevirtual getSafeInsetBottom : ()I
    //   177: i2f
    //   178: aload_0
    //   179: invokestatic getPixelRadio : (Landroid/content/Context;)F
    //   182: fdiv
    //   183: f2d
    //   184: invokestatic ceil : (D)D
    //   187: d2i
    //   188: isub
    //   189: istore #6
    //   191: iload #6
    //   193: iload #7
    //   195: isub
    //   196: istore #8
    //   198: iload #5
    //   200: iload #4
    //   202: isub
    //   203: istore_3
    //   204: iload #4
    //   206: istore_1
    //   207: iload #7
    //   209: istore_2
    //   210: iload #8
    //   212: istore #4
    //   214: goto -> 251
    //   217: aload_0
    //   218: aload_0
    //   219: invokestatic getStatusBarHeight : (Landroid/content/Context;)I
    //   222: i2f
    //   223: invokestatic px2dip : (Landroid/content/Context;F)I
    //   226: istore #8
    //   228: iload_2
    //   229: iload #8
    //   231: isub
    //   232: istore #4
    //   234: iload_1
    //   235: istore_3
    //   236: iconst_0
    //   237: istore #7
    //   239: iload_2
    //   240: istore #6
    //   242: iload_1
    //   243: istore #5
    //   245: iload #8
    //   247: istore_2
    //   248: iload #7
    //   250: istore_1
    //   251: aload #12
    //   253: ldc 'left'
    //   255: iload_1
    //   256: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   259: pop
    //   260: aload #12
    //   262: ldc 'right'
    //   264: iload #5
    //   266: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   269: pop
    //   270: aload #12
    //   272: ldc 'top'
    //   274: iload_2
    //   275: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   278: pop
    //   279: aload #12
    //   281: ldc 'bottom'
    //   283: iload #6
    //   285: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   288: pop
    //   289: aload #12
    //   291: ldc 'width'
    //   293: iload_3
    //   294: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   297: pop
    //   298: aload #12
    //   300: ldc 'height'
    //   302: iload #4
    //   304: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   307: pop
    //   308: new java/lang/StringBuilder
    //   311: dup
    //   312: ldc 'getSafeArea cost '
    //   314: invokespecial <init> : (Ljava/lang/String;)V
    //   317: astore_0
    //   318: aload_0
    //   319: invokestatic currentTimeMillis : ()J
    //   322: lload #9
    //   324: lsub
    //   325: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   328: pop
    //   329: aload_0
    //   330: ldc ' mill ,safeArea = '
    //   332: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   335: pop
    //   336: aload_0
    //   337: aload #12
    //   339: invokevirtual toString : ()Ljava/lang/String;
    //   342: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   345: pop
    //   346: ldc 'tma_SystemInfoUtil'
    //   348: iconst_1
    //   349: anewarray java/lang/Object
    //   352: dup
    //   353: iconst_0
    //   354: aload_0
    //   355: invokevirtual toString : ()Ljava/lang/String;
    //   358: aastore
    //   359: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   362: aload #12
    //   364: areturn
    //   365: astore_0
    //   366: goto -> 308
    //   369: aconst_null
    //   370: astore #11
    //   372: goto -> 102
    // Exception table:
    //   from	to	target	type
    //   28	87	365	java/lang/Exception
    //   92	99	365	java/lang/Exception
    //   107	191	365	java/lang/Exception
    //   217	228	365	java/lang/Exception
    //   251	308	365	java/lang/Exception
  }
  
  public static int[] getScreenWHPx() {
    int i;
    int j;
    Application application = AppbrandContext.getInst().getApplicationContext();
    WeakReference<IActivityRootView> weakReference = mWeakActivityInfo;
    if (weakReference != null && weakReference.get() != null && ((IActivityRootView)mWeakActivityInfo.get()).getActivityRootView() != null) {
      View view = ((IActivityRootView)mWeakActivityInfo.get()).getActivityRootView();
      if (view.getWidth() != 0 && view.getHeight() != 0) {
        i = view.getWidth();
        double d1 = view.getHeight();
        Double.isNaN(d1);
        double d2 = i;
        Double.isNaN(d2);
        double d3 = view.getWidth();
        Double.isNaN(d3);
        j = (int)Math.ceil(d1 * 1.0D * d2 / d3);
      } else {
        i = DevicesUtil.getScreenWidth((Context)application);
        double d1 = i;
        Double.isNaN(d1);
        double d2 = DevicesUtil.getScreenHight((Context)application);
        Double.isNaN(d2);
        double d3 = DevicesUtil.getScreenWidth((Context)application);
        Double.isNaN(d3);
        d1 = Math.ceil(d1 * 1.0D * d2 / d3);
        j = (int)d1;
      } 
    } else {
      i = DevicesUtil.getScreenWidth((Context)application);
      double d1 = i;
      Double.isNaN(d1);
      double d2 = DevicesUtil.getScreenHight((Context)application);
      Double.isNaN(d2);
      double d3 = DevicesUtil.getScreenWidth((Context)application);
      Double.isNaN(d3);
      d1 = Math.ceil(d1 * 1.0D * d2 / d3);
      j = (int)d1;
    } 
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = i;
    arrayOfInt[1] = j;
    validateWH(arrayOfInt);
    return arrayOfInt;
  }
  
  public static JSONObject getSystemInfo(Context paramContext, boolean paramBoolean) throws Exception {
    // Byte code:
    //   0: getstatic com/tt/miniapp/util/SystemInfoUtil.mWeakActivityInfo : Ljava/lang/ref/WeakReference;
    //   3: astore #6
    //   5: aload #6
    //   7: ifnull -> 132
    //   10: aload #6
    //   12: invokevirtual get : ()Ljava/lang/Object;
    //   15: ifnull -> 132
    //   18: getstatic com/tt/miniapp/util/SystemInfoUtil.mWeakActivityInfo : Ljava/lang/ref/WeakReference;
    //   21: invokevirtual get : ()Ljava/lang/Object;
    //   24: checkcast com/tt/miniapp/util/SystemInfoUtil$IActivityRootView
    //   27: invokeinterface getActivityRootView : ()Landroid/view/View;
    //   32: ifnull -> 132
    //   35: getstatic com/tt/miniapp/util/SystemInfoUtil.mWeakActivityInfo : Ljava/lang/ref/WeakReference;
    //   38: invokevirtual get : ()Ljava/lang/Object;
    //   41: checkcast com/tt/miniapp/util/SystemInfoUtil$IActivityRootView
    //   44: invokeinterface getActivityRootView : ()Landroid/view/View;
    //   49: astore #6
    //   51: aload #6
    //   53: invokevirtual getWidth : ()I
    //   56: ifeq -> 101
    //   59: aload #6
    //   61: invokevirtual getHeight : ()I
    //   64: ifeq -> 101
    //   67: aload #6
    //   69: invokevirtual getWidth : ()I
    //   72: i2f
    //   73: aload_0
    //   74: invokestatic getPixelRadio : (Landroid/content/Context;)F
    //   77: fdiv
    //   78: f2d
    //   79: invokestatic ceil : (D)D
    //   82: d2i
    //   83: istore_3
    //   84: aload #6
    //   86: invokevirtual getHeight : ()I
    //   89: iload_3
    //   90: imul
    //   91: aload #6
    //   93: invokevirtual getWidth : ()I
    //   96: idiv
    //   97: istore_2
    //   98: goto -> 160
    //   101: aload_0
    //   102: invokestatic getScreenWidth : (Landroid/content/Context;)I
    //   105: i2f
    //   106: aload_0
    //   107: invokestatic getPixelRadio : (Landroid/content/Context;)F
    //   110: fdiv
    //   111: f2d
    //   112: invokestatic ceil : (D)D
    //   115: d2i
    //   116: istore_3
    //   117: aload_0
    //   118: invokestatic getScreenHight : (Landroid/content/Context;)I
    //   121: iload_3
    //   122: imul
    //   123: aload_0
    //   124: invokestatic getScreenWidth : (Landroid/content/Context;)I
    //   127: idiv
    //   128: istore_2
    //   129: goto -> 160
    //   132: aload_0
    //   133: invokestatic getScreenWidth : (Landroid/content/Context;)I
    //   136: i2f
    //   137: aload_0
    //   138: invokestatic getPixelRadio : (Landroid/content/Context;)F
    //   141: fdiv
    //   142: f2d
    //   143: invokestatic ceil : (D)D
    //   146: d2i
    //   147: istore_3
    //   148: aload_0
    //   149: invokestatic getScreenHight : (Landroid/content/Context;)I
    //   152: iload_3
    //   153: imul
    //   154: aload_0
    //   155: invokestatic getScreenWidth : (Landroid/content/Context;)I
    //   158: idiv
    //   159: istore_2
    //   160: iload_1
    //   161: ifne -> 282
    //   164: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   167: invokevirtual getCurrentPagePath : ()Ljava/lang/String;
    //   170: astore #7
    //   172: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   175: invokevirtual getAppConfig : ()Lcom/tt/miniapp/AppConfig;
    //   178: astore #8
    //   180: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   183: invokevirtual getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   186: astore #9
    //   188: aload #7
    //   190: astore #6
    //   192: aload #7
    //   194: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   197: ifeq -> 216
    //   200: aload #7
    //   202: astore #6
    //   204: aload #9
    //   206: ifnull -> 216
    //   209: aload #9
    //   211: getfield startPage : Ljava/lang/String;
    //   214: astore #6
    //   216: aload #6
    //   218: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   221: ifne -> 282
    //   224: aload #6
    //   226: aload #8
    //   228: invokestatic isTabPage : (Ljava/lang/String;Lcom/tt/miniapp/AppConfig;)Z
    //   231: ifeq -> 243
    //   234: iload_2
    //   235: bipush #50
    //   237: isub
    //   238: istore #4
    //   240: goto -> 246
    //   243: iload_2
    //   244: istore #4
    //   246: iload #4
    //   248: istore #5
    //   250: ldc 'custom'
    //   252: invokestatic getNavigationStyle : ()Ljava/lang/String;
    //   255: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   258: ifne -> 285
    //   261: iload #4
    //   263: i2f
    //   264: aload_0
    //   265: iconst_0
    //   266: invokestatic getTitleBarHeight : (Landroid/content/Context;Z)I
    //   269: i2f
    //   270: aload_0
    //   271: invokestatic getPixelRadio : (Landroid/content/Context;)F
    //   274: fdiv
    //   275: fsub
    //   276: f2i
    //   277: istore #5
    //   279: goto -> 285
    //   282: iload_2
    //   283: istore #5
    //   285: new org/json/JSONObject
    //   288: dup
    //   289: invokespecial <init> : ()V
    //   292: astore #8
    //   294: aload #8
    //   296: ldc 'brand'
    //   298: invokestatic getBrand : ()Ljava/lang/String;
    //   301: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   304: pop
    //   305: aload #8
    //   307: ldc 'model'
    //   309: invokestatic getModel : ()Ljava/lang/String;
    //   312: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   315: pop
    //   316: aload #8
    //   318: ldc 'pixelRatio'
    //   320: aload_0
    //   321: invokestatic getPixelRadio : (Landroid/content/Context;)F
    //   324: f2d
    //   325: invokevirtual put : (Ljava/lang/String;D)Lorg/json/JSONObject;
    //   328: pop
    //   329: iconst_2
    //   330: newarray int
    //   332: astore #6
    //   334: aload #6
    //   336: iconst_0
    //   337: iload_3
    //   338: iastore
    //   339: aload #6
    //   341: iconst_1
    //   342: iload_2
    //   343: iastore
    //   344: aload #6
    //   346: invokestatic validateWH : ([I)V
    //   349: aload #8
    //   351: ldc 'screenWidth'
    //   353: aload #6
    //   355: iconst_0
    //   356: iaload
    //   357: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   360: pop
    //   361: aload #8
    //   363: ldc 'screenHeight'
    //   365: aload #6
    //   367: iconst_1
    //   368: iaload
    //   369: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   372: pop
    //   373: iconst_2
    //   374: newarray int
    //   376: astore #6
    //   378: aload #6
    //   380: iconst_0
    //   381: iload_3
    //   382: iastore
    //   383: aload #6
    //   385: iconst_1
    //   386: iload #5
    //   388: iastore
    //   389: aload #6
    //   391: invokestatic validateWH : ([I)V
    //   394: aload #8
    //   396: ldc 'windowWidth'
    //   398: aload #6
    //   400: iconst_0
    //   401: iaload
    //   402: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   405: pop
    //   406: aload #8
    //   408: ldc_w 'windowHeight'
    //   411: aload #6
    //   413: iconst_1
    //   414: iaload
    //   415: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   418: pop
    //   419: aload #8
    //   421: ldc_w 'statusBarHeight'
    //   424: aload_0
    //   425: aload_0
    //   426: invokestatic getStatusBarHeight : (Landroid/content/Context;)I
    //   429: i2f
    //   430: invokestatic px2dip : (Landroid/content/Context;F)I
    //   433: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   436: pop
    //   437: aload #8
    //   439: ldc_w 'fontSizeSetting'
    //   442: aload_0
    //   443: invokestatic getFontSize : (Landroid/content/Context;)F
    //   446: f2d
    //   447: invokevirtual put : (Ljava/lang/String;D)Lorg/json/JSONObject;
    //   450: pop
    //   451: aload #8
    //   453: ldc_w 'version'
    //   456: aload_0
    //   457: invokestatic getVersion : (Landroid/content/Context;)Ljava/lang/String;
    //   460: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   463: pop
    //   464: aload #8
    //   466: ldc_w 'nativeSDKVersion'
    //   469: invokestatic getMiniAppSdkVersion : ()Ljava/lang/String;
    //   472: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   475: pop
    //   476: aload #8
    //   478: ldc_w 'battery'
    //   481: aload_0
    //   482: invokestatic getCurrentBattery : (Landroid/content/Context;)I
    //   485: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   488: pop
    //   489: aload #8
    //   491: ldc_w 'wifiSignal'
    //   494: aload_0
    //   495: invokestatic getWifiSignal : (Landroid/content/Context;)I
    //   498: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   501: pop
    //   502: aload #8
    //   504: ldc_w 'safeArea'
    //   507: aload_0
    //   508: iload_3
    //   509: iload #5
    //   511: invokestatic constructSafeArea : (Landroid/content/Context;II)Lorg/json/JSONObject;
    //   514: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   517: pop
    //   518: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   521: invokevirtual isReturnDeviceIdInSystemInfo : ()Z
    //   524: ifeq -> 539
    //   527: aload #8
    //   529: ldc_w 'deviceId'
    //   532: invokestatic a : ()Ljava/lang/String;
    //   535: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   538: pop
    //   539: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   542: invokevirtual getInitParams : ()Lcom/tt/miniapphost/entity/InitParamsEntity;
    //   545: astore #6
    //   547: aload #6
    //   549: ifnull -> 566
    //   552: aload #8
    //   554: ldc_w 'appName'
    //   557: aload #6
    //   559: invokevirtual getAppName : ()Ljava/lang/String;
    //   562: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   565: pop
    //   566: invokestatic getSystem : ()Ljava/lang/String;
    //   569: astore #6
    //   571: aload #6
    //   573: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   576: ifne -> 657
    //   579: aload #6
    //   581: invokevirtual trim : ()Ljava/lang/String;
    //   584: astore #7
    //   586: aload #7
    //   588: astore #6
    //   590: aload #7
    //   592: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   595: ldc_w 'android'
    //   598: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   601: ifne -> 646
    //   604: new java/lang/StringBuilder
    //   607: dup
    //   608: invokespecial <init> : ()V
    //   611: astore #6
    //   613: aload #6
    //   615: invokestatic getPlatform : ()Ljava/lang/String;
    //   618: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   621: pop
    //   622: aload #6
    //   624: ldc_w ' '
    //   627: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   630: pop
    //   631: aload #6
    //   633: aload #7
    //   635: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   638: pop
    //   639: aload #6
    //   641: invokevirtual toString : ()Ljava/lang/String;
    //   644: astore #6
    //   646: aload #8
    //   648: ldc_w 'system'
    //   651: aload #6
    //   653: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   656: pop
    //   657: aload #8
    //   659: ldc_w 'platform'
    //   662: invokestatic getPlatform : ()Ljava/lang/String;
    //   665: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   668: pop
    //   669: invokestatic getInst : ()Lcom/tt/miniapphost/language/LocaleManager;
    //   672: invokevirtual getCurrentLocale : ()Ljava/util/Locale;
    //   675: astore #6
    //   677: aload #6
    //   679: ifnull -> 760
    //   682: aload #6
    //   684: invokevirtual getLanguage : ()Ljava/lang/String;
    //   687: astore #7
    //   689: aload #6
    //   691: invokevirtual getCountry : ()Ljava/lang/String;
    //   694: astore #9
    //   696: aload #7
    //   698: astore #6
    //   700: aload #9
    //   702: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   705: ifne -> 749
    //   708: new java/lang/StringBuilder
    //   711: dup
    //   712: invokespecial <init> : ()V
    //   715: astore #6
    //   717: aload #6
    //   719: aload #7
    //   721: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   724: pop
    //   725: aload #6
    //   727: ldc_w '_'
    //   730: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   733: pop
    //   734: aload #6
    //   736: aload #9
    //   738: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   741: pop
    //   742: aload #6
    //   744: invokevirtual toString : ()Ljava/lang/String;
    //   747: astore #6
    //   749: aload #8
    //   751: ldc_w 'language'
    //   754: aload #6
    //   756: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   759: pop
    //   760: invokestatic getInst : ()Lcom/tt/miniapphost/IAppbrandApplication;
    //   763: invokeinterface getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   768: astore #6
    //   770: invokestatic getInst : ()Lcom/tt/miniapp/manager/basebundle/BaseBundleManager;
    //   773: aload_0
    //   774: invokevirtual getSdkCurrentVersionStr : (Landroid/content/Context;)Ljava/lang/String;
    //   777: astore #7
    //   779: invokestatic getInst : ()Lcom/tt/miniapp/manager/basebundle/BaseBundleManager;
    //   782: aload_0
    //   783: invokevirtual getSdkCurrentVersionStr : (Landroid/content/Context;)Ljava/lang/String;
    //   786: astore_0
    //   787: aload #6
    //   789: ifnull -> 822
    //   792: aload #6
    //   794: getfield type : I
    //   797: iconst_2
    //   798: if_icmpne -> 822
    //   801: aload #8
    //   803: ldc_w 'fontSizeSetting'
    //   806: bipush #12
    //   808: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   811: pop
    //   812: aload #8
    //   814: ldc_w 'benchmarkLevel'
    //   817: iconst_1
    //   818: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   821: pop
    //   822: aload_0
    //   823: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   826: ifne -> 839
    //   829: aload #8
    //   831: ldc_w 'SDKUpdateVersion'
    //   834: aload_0
    //   835: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   838: pop
    //   839: aload #7
    //   841: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   844: ifne -> 858
    //   847: aload #8
    //   849: ldc_w 'SDKVersion'
    //   852: aload #7
    //   854: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   857: pop
    //   858: ldc_w 'AABBCCDD'
    //   861: iconst_1
    //   862: anewarray java/lang/Object
    //   865: dup
    //   866: iconst_0
    //   867: aload #8
    //   869: invokevirtual toString : ()Ljava/lang/String;
    //   872: aastore
    //   873: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   876: aload #8
    //   878: areturn
  }
  
  public static void setActivityRootViewCallBack(IActivityRootView paramIActivityRootView) {
    mWeakActivityInfo = new WeakReference<IActivityRootView>(paramIActivityRootView);
  }
  
  private static void validateWH(int[] paramArrayOfint) {
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity != null) {
      if (!appInfoEntity.isGame())
        return; 
      if ((appInfoEntity.isLandScape && paramArrayOfint[0] < paramArrayOfint[1]) || (!appInfoEntity.isLandScape && paramArrayOfint[0] > paramArrayOfint[1])) {
        int i = paramArrayOfint[0];
        paramArrayOfint[0] = paramArrayOfint[1];
        paramArrayOfint[1] = i;
      } 
    } 
  }
  
  public static interface IActivityRootView {
    View getActivityRootView();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\SystemInfoUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */