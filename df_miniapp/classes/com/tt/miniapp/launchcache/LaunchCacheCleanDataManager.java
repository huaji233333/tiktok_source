package com.tt.miniapp.launchcache;

import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Action;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import d.b.a;
import d.f.b.l;
import d.n;
import java.util.Comparator;
import java.util.Iterator;

public final class LaunchCacheCleanDataManager {
  public static final LaunchCacheCleanDataManager INSTANCE = new LaunchCacheCleanDataManager();
  
  private static int sMaxMiniAppNumber;
  
  private static int sMaxPreloadAppNumber;
  
  private final int getMaxPreloadMiniAppCount() {
    if (sMaxPreloadAppNumber == 0) {
      AppbrandContext appbrandContext = AppbrandContext.getInst();
      l.a(appbrandContext, "AppbrandContext.getInst()");
      InitParamsEntity initParamsEntity = appbrandContext.getInitParams();
      if (initParamsEntity != null) {
        String str = initParamsEntity.getHostStr(100000, null);
        if (!TextUtils.isEmpty(str))
          try {
            Integer integer = Integer.valueOf(str);
            l.a(integer, "Integer.valueOf(maxPreloadMiniAppNumber)");
            sMaxPreloadAppNumber = integer.intValue();
          } catch (Exception exception) {
            AppBrandLogger.e("LaunchCacheManager", new Object[] { "getMaxPreloadMiniAppNumber", exception });
          }  
      } 
      if (sMaxPreloadAppNumber == 0) {
        AppbrandContext appbrandContext1 = AppbrandContext.getInst();
        l.a(appbrandContext1, "AppbrandContext.getInst()");
        sMaxPreloadAppNumber = SettingsDAO.getInt((Context)appbrandContext1.getApplicationContext(), 20, new Enum[] { (Enum)Settings.BDP_TTPKG_CONFIG, (Enum)Settings.BdpTtPkgConfig.PRELOAD_PKG_LIMIT });
      } 
    } 
    return sMaxPreloadAppNumber;
  }
  
  public final void cleanMiniAppCache(Context paramContext, String paramString) {
    l.b(paramContext, "context");
    l.b(paramString, "appId");
    null = LaunchCacheDAO.INSTANCE.getCacheAppIdDir(paramContext, paramString);
    LaunchCacheDAO.LockObject lockObject = null.lock();
    if (lockObject == null)
      return; 
    try {
      null.clearLocked();
      return;
    } finally {
      lockObject.unlock();
    } 
  }
  
  public final long clearUnused(Context paramContext) {
    l.b(paramContext, "context");
    Iterator<LaunchCacheDAO.CacheAppIdDir> iterator = LaunchCacheDAO.INSTANCE.listCacheAppIdDirs(paramContext).iterator();
    long l = 0L;
    while (iterator.hasNext()) {
      LaunchCacheDAO.CacheAppIdDir cacheAppIdDir = iterator.next();
      LaunchCacheDAO.LockObject lockObject = cacheAppIdDir.lock();
      if (lockObject == null)
        continue; 
      try {
        for (LaunchCacheDAO.CacheVersionDir cacheVersionDir : cacheAppIdDir.listCacheVersionDirs()) {
          if (cacheVersionDir.getRequestType() != RequestType.normal) {
            l += cacheVersionDir.size();
            cacheVersionDir.clearLocked();
          } 
        } 
      } finally {
        lockObject.unlock();
      } 
    } 
    return l;
  }
  
  public final int getMaxMiniAppCount() {
    if (sMaxMiniAppNumber == 0) {
      AppbrandContext appbrandContext = AppbrandContext.getInst();
      l.a(appbrandContext, "AppbrandContext.getInst()");
      sMaxMiniAppNumber = SettingsDAO.getInt((Context)appbrandContext.getApplicationContext(), 20, new Enum[] { (Enum)Settings.BDP_TTPKG_CONFIG, (Enum)Settings.BdpTtPkgConfig.NORMAL_LAUNCH_PKG_LIMIT });
    } 
    return sMaxMiniAppNumber;
  }
  
  public final void manageCacheForLaunchSuccess(Context paramContext, String paramString, long paramLong) {
    l.b(paramContext, "context");
    l.b(paramString, "appId");
    null = LaunchCacheDAO.INSTANCE.getCacheAppIdDir(paramContext, paramString);
    LaunchCacheDAO.LockObject lockObject = null.lock();
    if (lockObject == null)
      return; 
    try {
      for (LaunchCacheDAO.CacheVersionDir cacheVersionDir : null.listCacheVersionDirs()) {
        if (cacheVersionDir.getVersionCode() < paramLong && (cacheVersionDir.getRequestType() == RequestType.normal || cacheVersionDir.getRequestType() == RequestType.async || cacheVersionDir.getRequestType() == RequestType.silence))
          cacheVersionDir.clearLocked(); 
      } 
      return;
    } finally {
      lockObject.unlock();
    } 
  }
  
  public final void manageCacheForNormalLaunch(Context paramContext, String paramString) {
    l.b(paramContext, "context");
    l.b(paramString, "appId");
    LaunchCacheDAO.INSTANCE.increaseNormalLaunchCounter(paramContext, paramString);
    ThreadUtil.runOnWorkThread(new LaunchCacheCleanDataManager$manageCacheForNormalLaunch$1(paramContext), ThreadPools.backGround());
  }
  
  public final void manageCacheForPreDownloadSuccess(Context paramContext, String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'context'
    //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 'appId'
    //   9: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   12: getstatic com/tt/miniapp/launchcache/LaunchCacheDAO.INSTANCE : Lcom/tt/miniapp/launchcache/LaunchCacheDAO;
    //   15: aload_1
    //   16: aload_2
    //   17: invokevirtual increasePreDownloadCounter : (Landroid/content/Context;Ljava/lang/String;)V
    //   20: getstatic com/tt/miniapp/launchcache/LaunchCacheDAO.INSTANCE : Lcom/tt/miniapp/launchcache/LaunchCacheDAO;
    //   23: aload_1
    //   24: invokevirtual listCacheAppIdDirs : (Landroid/content/Context;)Ljava/util/List;
    //   27: checkcast java/lang/Iterable
    //   30: astore #6
    //   32: new java/util/ArrayList
    //   35: dup
    //   36: invokespecial <init> : ()V
    //   39: checkcast java/util/Collection
    //   42: astore_2
    //   43: aload #6
    //   45: invokeinterface iterator : ()Ljava/util/Iterator;
    //   50: astore #6
    //   52: aload #6
    //   54: invokeinterface hasNext : ()Z
    //   59: istore #5
    //   61: iconst_1
    //   62: istore #4
    //   64: iload #5
    //   66: ifeq -> 200
    //   69: aload #6
    //   71: invokeinterface next : ()Ljava/lang/Object;
    //   76: astore #7
    //   78: aload #7
    //   80: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheAppIdDir
    //   83: invokevirtual listCacheVersionDirs : ()Ljava/util/List;
    //   86: checkcast java/lang/Iterable
    //   89: astore #8
    //   91: aload #8
    //   93: instanceof java/util/Collection
    //   96: ifeq -> 112
    //   99: aload #8
    //   101: checkcast java/util/Collection
    //   104: invokeinterface isEmpty : ()Z
    //   109: ifne -> 182
    //   112: aload #8
    //   114: invokeinterface iterator : ()Ljava/util/Iterator;
    //   119: astore #8
    //   121: aload #8
    //   123: invokeinterface hasNext : ()Z
    //   128: ifeq -> 182
    //   131: aload #8
    //   133: invokeinterface next : ()Ljava/lang/Object;
    //   138: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheVersionDir
    //   141: astore #9
    //   143: aload #9
    //   145: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
    //   148: getstatic com/tt/miniapp/launchcache/RequestType.preload : Lcom/tt/miniapp/launchcache/RequestType;
    //   151: if_acmpne -> 170
    //   154: aload #9
    //   156: invokevirtual getPkgFile : ()Ljava/io/File;
    //   159: invokevirtual exists : ()Z
    //   162: ifeq -> 170
    //   165: iconst_1
    //   166: istore_3
    //   167: goto -> 172
    //   170: iconst_0
    //   171: istore_3
    //   172: iload_3
    //   173: ifeq -> 121
    //   176: iload #4
    //   178: istore_3
    //   179: goto -> 184
    //   182: iconst_0
    //   183: istore_3
    //   184: iload_3
    //   185: ifeq -> 52
    //   188: aload_2
    //   189: aload #7
    //   191: invokeinterface add : (Ljava/lang/Object;)Z
    //   196: pop
    //   197: goto -> 52
    //   200: aload_2
    //   201: checkcast java/util/List
    //   204: astore_2
    //   205: aload_0
    //   206: invokespecial getMaxPreloadMiniAppCount : ()I
    //   209: istore #4
    //   211: aload_2
    //   212: invokeinterface size : ()I
    //   217: iload #4
    //   219: if_icmple -> 484
    //   222: aload_2
    //   223: checkcast java/lang/Iterable
    //   226: astore #6
    //   228: new java/util/ArrayList
    //   231: dup
    //   232: aload #6
    //   234: bipush #10
    //   236: invokestatic a : (Ljava/lang/Iterable;I)I
    //   239: invokespecial <init> : (I)V
    //   242: checkcast java/util/Collection
    //   245: astore_2
    //   246: aload #6
    //   248: invokeinterface iterator : ()Ljava/util/Iterator;
    //   253: astore #6
    //   255: aload #6
    //   257: invokeinterface hasNext : ()Z
    //   262: ifeq -> 304
    //   265: aload #6
    //   267: invokeinterface next : ()Ljava/lang/Object;
    //   272: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheAppIdDir
    //   275: astore #7
    //   277: aload_2
    //   278: new d/n
    //   281: dup
    //   282: aload #7
    //   284: invokevirtual getLocalPreDownloadCounter : ()J
    //   287: invokestatic valueOf : (J)Ljava/lang/Long;
    //   290: aload #7
    //   292: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   295: invokeinterface add : (Ljava/lang/Object;)Z
    //   300: pop
    //   301: goto -> 255
    //   304: aload_2
    //   305: checkcast java/util/List
    //   308: checkcast java/lang/Iterable
    //   311: new com/tt/miniapp/launchcache/LaunchCacheCleanDataManager$manageCacheForPreDownloadSuccess$$inlined$sortedByDescending$1
    //   314: dup
    //   315: invokespecial <init> : ()V
    //   318: checkcast java/util/Comparator
    //   321: invokestatic a : (Ljava/lang/Iterable;Ljava/util/Comparator;)Ljava/util/List;
    //   324: astore #6
    //   326: aload #6
    //   328: invokeinterface size : ()I
    //   333: iconst_1
    //   334: isub
    //   335: istore_3
    //   336: iload_3
    //   337: iload #4
    //   339: if_icmplt -> 484
    //   342: aload #6
    //   344: iload_3
    //   345: invokeinterface get : (I)Ljava/lang/Object;
    //   350: checkcast d/n
    //   353: invokevirtual getSecond : ()Ljava/lang/Object;
    //   356: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheAppIdDir
    //   359: astore #7
    //   361: aload #7
    //   363: invokevirtual lock : ()Lcom/tt/miniapp/launchcache/LaunchCacheDAO$LockObject;
    //   366: astore_2
    //   367: aload_2
    //   368: ifnonnull -> 374
    //   371: goto -> 464
    //   374: aload_1
    //   375: aload #7
    //   377: invokevirtual getAppId : ()Ljava/lang/String;
    //   380: invokestatic isAppProcessExist : (Landroid/content/Context;Ljava/lang/String;)Z
    //   383: istore #5
    //   385: iload #5
    //   387: ifeq -> 397
    //   390: aload_2
    //   391: invokevirtual unlock : ()V
    //   394: goto -> 464
    //   397: aload #7
    //   399: invokevirtual listCacheVersionDirs : ()Ljava/util/List;
    //   402: checkcast java/lang/Iterable
    //   405: invokeinterface iterator : ()Ljava/util/Iterator;
    //   410: astore #7
    //   412: aload #7
    //   414: invokeinterface hasNext : ()Z
    //   419: ifeq -> 390
    //   422: aload #7
    //   424: invokeinterface next : ()Ljava/lang/Object;
    //   429: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheVersionDir
    //   432: astore #8
    //   434: aload #8
    //   436: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
    //   439: getstatic com/tt/miniapp/launchcache/RequestType.preload : Lcom/tt/miniapp/launchcache/RequestType;
    //   442: if_acmpne -> 412
    //   445: aload #8
    //   447: invokevirtual getPkgFile : ()Ljava/io/File;
    //   450: invokevirtual exists : ()Z
    //   453: ifeq -> 412
    //   456: aload #8
    //   458: invokevirtual clearLocked : ()V
    //   461: goto -> 412
    //   464: iload_3
    //   465: iload #4
    //   467: if_icmpeq -> 484
    //   470: iload_3
    //   471: iconst_1
    //   472: isub
    //   473: istore_3
    //   474: goto -> 342
    //   477: astore_1
    //   478: aload_2
    //   479: invokevirtual unlock : ()V
    //   482: aload_1
    //   483: athrow
    //   484: return
    // Exception table:
    //   from	to	target	type
    //   374	385	477	finally
    //   397	412	477	finally
    //   412	461	477	finally
  }
  
  public final void manageCacheForSdkLaunch(Context paramContext) {
    l.b(paramContext, "context");
    if (AppProcessManager.isMiniAppProcessExist(paramContext)) {
      AppBrandLogger.e("LaunchCacheManager", new Object[] { "manageLaunchCacheForSdkLaunch fail: miniAppProcessExist" });
      return;
    } 
    AppBrandLogger.i("LaunchCacheManager", new Object[] { "manageLaunchCacheForSdkLaunch start" });
    ThreadUtil.runOnWorkThread(new LaunchCacheCleanDataManager$manageCacheForSdkLaunch$1(paramContext), ThreadPools.backGround());
  }
  
  static final class LaunchCacheCleanDataManager$manageCacheForNormalLaunch$1 implements Action {
    LaunchCacheCleanDataManager$manageCacheForNormalLaunch$1(Context param1Context) {}
    
    public final void act() {
      // Byte code:
      //   0: getstatic com/tt/miniapp/launchcache/LaunchCacheDAO.INSTANCE : Lcom/tt/miniapp/launchcache/LaunchCacheDAO;
      //   3: aload_0
      //   4: getfield $context : Landroid/content/Context;
      //   7: invokevirtual listCacheAppIdDirs : (Landroid/content/Context;)Ljava/util/List;
      //   10: checkcast java/lang/Iterable
      //   13: astore #5
      //   15: new java/util/ArrayList
      //   18: dup
      //   19: invokespecial <init> : ()V
      //   22: checkcast java/util/Collection
      //   25: astore #4
      //   27: aload #5
      //   29: invokeinterface iterator : ()Ljava/util/Iterator;
      //   34: astore #5
      //   36: aload #5
      //   38: invokeinterface hasNext : ()Z
      //   43: istore_3
      //   44: iconst_1
      //   45: istore_2
      //   46: iload_3
      //   47: ifeq -> 203
      //   50: aload #5
      //   52: invokeinterface next : ()Ljava/lang/Object;
      //   57: astore #6
      //   59: aload #6
      //   61: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheAppIdDir
      //   64: invokevirtual listCacheVersionDirs : ()Ljava/util/List;
      //   67: checkcast java/lang/Iterable
      //   70: astore #7
      //   72: aload #7
      //   74: instanceof java/util/Collection
      //   77: ifeq -> 93
      //   80: aload #7
      //   82: checkcast java/util/Collection
      //   85: invokeinterface isEmpty : ()Z
      //   90: ifne -> 184
      //   93: aload #7
      //   95: invokeinterface iterator : ()Ljava/util/Iterator;
      //   100: astore #7
      //   102: aload #7
      //   104: invokeinterface hasNext : ()Z
      //   109: ifeq -> 184
      //   112: aload #7
      //   114: invokeinterface next : ()Ljava/lang/Object;
      //   119: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheVersionDir
      //   122: astore #8
      //   124: aload #8
      //   126: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
      //   129: getstatic com/tt/miniapp/launchcache/RequestType.normal : Lcom/tt/miniapp/launchcache/RequestType;
      //   132: if_acmpeq -> 157
      //   135: aload #8
      //   137: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
      //   140: getstatic com/tt/miniapp/launchcache/RequestType.async : Lcom/tt/miniapp/launchcache/RequestType;
      //   143: if_acmpeq -> 157
      //   146: aload #8
      //   148: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
      //   151: getstatic com/tt/miniapp/launchcache/RequestType.silence : Lcom/tt/miniapp/launchcache/RequestType;
      //   154: if_acmpne -> 173
      //   157: aload #8
      //   159: invokevirtual getPkgFile : ()Ljava/io/File;
      //   162: invokevirtual exists : ()Z
      //   165: ifeq -> 173
      //   168: iconst_1
      //   169: istore_1
      //   170: goto -> 175
      //   173: iconst_0
      //   174: istore_1
      //   175: iload_1
      //   176: ifeq -> 102
      //   179: iload_2
      //   180: istore_1
      //   181: goto -> 186
      //   184: iconst_0
      //   185: istore_1
      //   186: iload_1
      //   187: ifeq -> 36
      //   190: aload #4
      //   192: aload #6
      //   194: invokeinterface add : (Ljava/lang/Object;)Z
      //   199: pop
      //   200: goto -> 36
      //   203: aload #4
      //   205: checkcast java/util/List
      //   208: astore #4
      //   210: getstatic com/tt/miniapp/launchcache/LaunchCacheCleanDataManager.INSTANCE : Lcom/tt/miniapp/launchcache/LaunchCacheCleanDataManager;
      //   213: invokevirtual getMaxMiniAppCount : ()I
      //   216: istore_2
      //   217: aload #4
      //   219: invokeinterface size : ()I
      //   224: iload_2
      //   225: if_icmple -> 521
      //   228: aload #4
      //   230: checkcast java/lang/Iterable
      //   233: astore #5
      //   235: new java/util/ArrayList
      //   238: dup
      //   239: aload #5
      //   241: bipush #10
      //   243: invokestatic a : (Ljava/lang/Iterable;I)I
      //   246: invokespecial <init> : (I)V
      //   249: checkcast java/util/Collection
      //   252: astore #4
      //   254: aload #5
      //   256: invokeinterface iterator : ()Ljava/util/Iterator;
      //   261: astore #5
      //   263: aload #5
      //   265: invokeinterface hasNext : ()Z
      //   270: ifeq -> 313
      //   273: aload #5
      //   275: invokeinterface next : ()Ljava/lang/Object;
      //   280: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheAppIdDir
      //   283: astore #6
      //   285: aload #4
      //   287: new d/n
      //   290: dup
      //   291: aload #6
      //   293: invokevirtual getLocalLaunchCounter : ()J
      //   296: invokestatic valueOf : (J)Ljava/lang/Long;
      //   299: aload #6
      //   301: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
      //   304: invokeinterface add : (Ljava/lang/Object;)Z
      //   309: pop
      //   310: goto -> 263
      //   313: aload #4
      //   315: checkcast java/util/List
      //   318: checkcast java/lang/Iterable
      //   321: new com/tt/miniapp/launchcache/LaunchCacheCleanDataManager$manageCacheForNormalLaunch$1$$special$$inlined$sortedByDescending$1
      //   324: dup
      //   325: invokespecial <init> : ()V
      //   328: checkcast java/util/Comparator
      //   331: invokestatic a : (Ljava/lang/Iterable;Ljava/util/Comparator;)Ljava/util/List;
      //   334: astore #5
      //   336: aload #5
      //   338: invokeinterface size : ()I
      //   343: iconst_1
      //   344: isub
      //   345: istore_1
      //   346: iload_1
      //   347: iload_2
      //   348: if_icmplt -> 521
      //   351: aload #5
      //   353: iload_1
      //   354: invokeinterface get : (I)Ljava/lang/Object;
      //   359: checkcast d/n
      //   362: invokevirtual getSecond : ()Ljava/lang/Object;
      //   365: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheAppIdDir
      //   368: astore #6
      //   370: aload #6
      //   372: invokevirtual lock : ()Lcom/tt/miniapp/launchcache/LaunchCacheDAO$LockObject;
      //   375: astore #4
      //   377: aload #4
      //   379: ifnonnull -> 385
      //   382: goto -> 499
      //   385: aload_0
      //   386: getfield $context : Landroid/content/Context;
      //   389: aload #6
      //   391: invokevirtual getAppId : ()Ljava/lang/String;
      //   394: invokestatic isAppProcessExist : (Landroid/content/Context;Ljava/lang/String;)Z
      //   397: istore_3
      //   398: iload_3
      //   399: ifeq -> 410
      //   402: aload #4
      //   404: invokevirtual unlock : ()V
      //   407: goto -> 499
      //   410: aload #6
      //   412: invokevirtual listCacheVersionDirs : ()Ljava/util/List;
      //   415: checkcast java/lang/Iterable
      //   418: invokeinterface iterator : ()Ljava/util/Iterator;
      //   423: astore #6
      //   425: aload #6
      //   427: invokeinterface hasNext : ()Z
      //   432: ifeq -> 402
      //   435: aload #6
      //   437: invokeinterface next : ()Ljava/lang/Object;
      //   442: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheVersionDir
      //   445: astore #7
      //   447: aload #7
      //   449: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
      //   452: getstatic com/tt/miniapp/launchcache/RequestType.normal : Lcom/tt/miniapp/launchcache/RequestType;
      //   455: if_acmpeq -> 480
      //   458: aload #7
      //   460: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
      //   463: getstatic com/tt/miniapp/launchcache/RequestType.async : Lcom/tt/miniapp/launchcache/RequestType;
      //   466: if_acmpeq -> 480
      //   469: aload #7
      //   471: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
      //   474: getstatic com/tt/miniapp/launchcache/RequestType.silence : Lcom/tt/miniapp/launchcache/RequestType;
      //   477: if_acmpne -> 425
      //   480: aload #7
      //   482: invokevirtual getPkgFile : ()Ljava/io/File;
      //   485: invokevirtual exists : ()Z
      //   488: ifeq -> 425
      //   491: aload #7
      //   493: invokevirtual clearLocked : ()V
      //   496: goto -> 425
      //   499: iload_1
      //   500: iload_2
      //   501: if_icmpeq -> 521
      //   504: iload_1
      //   505: iconst_1
      //   506: isub
      //   507: istore_1
      //   508: goto -> 351
      //   511: astore #5
      //   513: aload #4
      //   515: invokevirtual unlock : ()V
      //   518: aload #5
      //   520: athrow
      //   521: return
      // Exception table:
      //   from	to	target	type
      //   385	398	511	finally
      //   410	425	511	finally
      //   425	480	511	finally
      //   480	496	511	finally
    }
    
    public static final class LaunchCacheCleanDataManager$manageCacheForNormalLaunch$1$$special$$inlined$sortedByDescending$1<T> implements Comparator<T> {
      public final int compare(T param1T1, T param1T2) {
        return a.a(Long.valueOf(((Number)((n)param1T2).getFirst()).longValue()), Long.valueOf(((Number)((n)param1T1).getFirst()).longValue()));
      }
    }
  }
  
  public static final class LaunchCacheCleanDataManager$manageCacheForNormalLaunch$1$$special$$inlined$sortedByDescending$1<T> implements Comparator<T> {
    public final int compare(T param1T1, T param1T2) {
      return a.a(Long.valueOf(((Number)((n)param1T2).getFirst()).longValue()), Long.valueOf(((Number)((n)param1T1).getFirst()).longValue()));
    }
  }
  
  public static final class LaunchCacheCleanDataManager$manageCacheForPreDownloadSuccess$$inlined$sortedByDescending$1<T> implements Comparator<T> {
    public final int compare(T param1T1, T param1T2) {
      return a.a(Long.valueOf(((Number)((n)param1T2).getFirst()).longValue()), Long.valueOf(((Number)((n)param1T1).getFirst()).longValue()));
    }
  }
  
  static final class LaunchCacheCleanDataManager$manageCacheForSdkLaunch$1 implements Action {
    LaunchCacheCleanDataManager$manageCacheForSdkLaunch$1(Context param1Context) {}
    
    public final void act() {
      Iterator<LaunchCacheDAO.CacheAppIdDir> iterator = LaunchCacheDAO.INSTANCE.listCacheAppIdDirs(this.$context).iterator();
      label50: while (true) {
        if (iterator.hasNext()) {
          LaunchCacheDAO.CacheAppIdDir cacheAppIdDir = iterator.next();
          LaunchCacheDAO.LockObject lockObject = cacheAppIdDir.lock();
          if (lockObject == null)
            continue; 
          try {
            boolean bool = AppProcessManager.isAppProcessExist(this.$context, cacheAppIdDir.getAppId());
            if (bool)
              continue; 
            LaunchCacheDAO.CacheVersionDir cacheVersionDir = null;
            Iterator<LaunchCacheDAO.CacheVersionDir> iterator1 = cacheAppIdDir.listCacheVersionDirs().iterator();
            while (true) {
              if (iterator1.hasNext()) {
                LaunchCacheDAO.CacheVersionDir cacheVersionDir1 = iterator1.next();
                RequestType requestType1 = cacheVersionDir1.getRequestType();
                RequestType requestType2 = RequestType.silence;
                boolean bool1 = true;
                if (requestType1 == requestType2) {
                  if (!cacheVersionDir1.getMetaFile().exists() || cacheVersionDir1.getCurrentStatusFlag() == null) {
                    cacheVersionDir1.clearLocked();
                  } else if (!cacheVersionDir1.getPkgFile().exists() || !cacheVersionDir1.checkStatusFlag(StatusFlagType.Verified)) {
                    long l = cacheVersionDir1.getUpdateTime();
                    l = System.currentTimeMillis() - l;
                    if (l < 0L || l > SilenceUpdateManager.INSTANCE.getSilenceUpdateInterval(this.$context))
                      cacheVersionDir1.clearLocked(); 
                  } else {
                    continue;
                  } 
                } else if (!cacheVersionDir1.getMetaFile().exists() || !cacheVersionDir1.getPkgFile().exists()) {
                  cacheVersionDir1.clearLocked();
                } else if (!cacheVersionDir1.checkStatusFlag(StatusFlagType.Verified)) {
                  cacheVersionDir1.clearLocked();
                } else {
                  continue;
                } 
                bool1 = false;
                continue;
              } 
              lockObject.unlock();
              continue label50;
              if (SYNTHETIC_LOCAL_VARIABLE_1 != null) {
                if (cacheVersionDir != null) {
                  if (cacheAppIdDir.getVersionCode() < cacheVersionDir.getVersionCode()) {
                    cacheAppIdDir.clearLocked();
                    continue;
                  } 
                  cacheVersionDir.clearLocked();
                } 
                LaunchCacheDAO.CacheAppIdDir cacheAppIdDir1 = cacheAppIdDir;
              } 
            } 
          } finally {
            lockObject.unlock();
          } 
          break;
        } 
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\LaunchCacheCleanDataManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */