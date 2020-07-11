package com.tt.miniapp.entity;

import android.net.Uri;
import android.text.TextUtils;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.bridge.InnerMiniAppProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandSupport;
import com.tt.miniapphost.entity.AppRunningEntity;
import java.util.LinkedHashMap;

public class AppJumpListManager {
  private static LinkedHashMap<String, AppRunningEntity> applist = new LinkedHashMap<String, AppRunningEntity>();
  
  private static volatile String currentApp;
  
  private static volatile String needKillAppId;
  
  private static void addAppToList(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2) {
    if (TextUtils.isEmpty(paramString1)) {
      AppBrandLogger.e("AppJumpListManager", new Object[] { "addAppToList() called with: appId isEmpty" });
      return;
    } 
    AppBrandLogger.d("AppJumpListManager", new Object[] { "addAppToList() called with: appId = [", paramString1, "], preAppId = [", paramString2, "]" });
    AppRunningEntity appRunningEntity = new AppRunningEntity();
    appRunningEntity.setAppId(paramString1);
    appRunningEntity.setPreAppId(paramString2);
    appRunningEntity.setGame(paramBoolean1);
    appRunningEntity.setSpecial(paramBoolean2);
    applist.put(paramString1, appRunningEntity);
    currentApp = paramString1;
  }
  
  private static void addToList(String paramString1, String paramString2, boolean paramBoolean) {
    // Byte code:
    //   0: ldc com/tt/miniapp/entity/AppJumpListManager
    //   2: monitorenter
    //   3: aload_0
    //   4: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   7: ifeq -> 28
    //   10: ldc 'AppJumpListManager'
    //   12: iconst_1
    //   13: anewarray java/lang/Object
    //   16: dup
    //   17: iconst_0
    //   18: ldc 'addToList() called with: appId isEmpty'
    //   20: aastore
    //   21: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   24: ldc com/tt/miniapp/entity/AppJumpListManager
    //   26: monitorexit
    //   27: return
    //   28: ldc 'AppJumpListManager'
    //   30: bipush #6
    //   32: anewarray java/lang/Object
    //   35: dup
    //   36: iconst_0
    //   37: ldc 'addToList() called with: appId = ['
    //   39: aastore
    //   40: dup
    //   41: iconst_1
    //   42: aload_0
    //   43: aastore
    //   44: dup
    //   45: iconst_2
    //   46: ldc '], preAppId = ['
    //   48: aastore
    //   49: dup
    //   50: iconst_3
    //   51: aload_1
    //   52: aastore
    //   53: dup
    //   54: iconst_4
    //   55: ldc '] , applist:'
    //   57: aastore
    //   58: dup
    //   59: iconst_5
    //   60: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   63: aastore
    //   64: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   67: aload_0
    //   68: invokestatic removeApp : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/AppRunningEntity;
    //   71: astore #5
    //   73: aload #5
    //   75: ifnull -> 121
    //   78: aload #5
    //   80: aload_1
    //   81: invokevirtual setPreAppId : (Ljava/lang/String;)V
    //   84: ldc 'AppJumpListManager'
    //   86: iconst_2
    //   87: anewarray java/lang/Object
    //   90: dup
    //   91: iconst_0
    //   92: ldc 'addToList() updateEntity entity:'
    //   94: aastore
    //   95: dup
    //   96: iconst_1
    //   97: aload #5
    //   99: aastore
    //   100: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   103: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   106: aload_0
    //   107: aload #5
    //   109: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   112: pop
    //   113: aload_0
    //   114: putstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   117: ldc com/tt/miniapp/entity/AppJumpListManager
    //   119: monitorexit
    //   120: return
    //   121: iload_2
    //   122: ifeq -> 237
    //   125: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   128: invokevirtual keySet : ()Ljava/util/Set;
    //   131: invokeinterface iterator : ()Ljava/util/Iterator;
    //   136: astore #6
    //   138: aconst_null
    //   139: astore #5
    //   141: iconst_0
    //   142: istore_3
    //   143: aload #6
    //   145: invokeinterface hasNext : ()Z
    //   150: ifeq -> 217
    //   153: aload #6
    //   155: invokeinterface next : ()Ljava/lang/Object;
    //   160: checkcast java/lang/String
    //   163: astore #7
    //   165: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   168: aload #7
    //   170: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   173: checkcast com/tt/miniapphost/entity/AppRunningEntity
    //   176: invokevirtual isGame : ()Z
    //   179: ifeq -> 143
    //   182: iload_3
    //   183: iconst_1
    //   184: iadd
    //   185: istore #4
    //   187: iload #4
    //   189: istore_3
    //   190: aload #5
    //   192: ifnonnull -> 143
    //   195: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   198: aload #7
    //   200: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   203: checkcast com/tt/miniapphost/entity/AppRunningEntity
    //   206: invokevirtual getAppId : ()Ljava/lang/String;
    //   209: astore #5
    //   211: iload #4
    //   213: istore_3
    //   214: goto -> 143
    //   217: iload_3
    //   218: iconst_2
    //   219: if_icmplt -> 237
    //   222: new com/tt/miniapp/entity/AppJumpListManager$1
    //   225: dup
    //   226: aload #5
    //   228: invokespecial <init> : (Ljava/lang/String;)V
    //   231: ldc2_w 300
    //   234: invokestatic runOnUIThread : (Ljava/lang/Runnable;J)V
    //   237: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   240: invokevirtual size : ()I
    //   243: iconst_4
    //   244: if_icmplt -> 263
    //   247: new com/tt/miniapp/entity/AppJumpListManager$2
    //   250: dup
    //   251: invokestatic getNeedKillAppId : ()Ljava/lang/String;
    //   254: invokespecial <init> : (Ljava/lang/String;)V
    //   257: ldc2_w 300
    //   260: invokestatic runOnUIThread : (Ljava/lang/Runnable;J)V
    //   263: aload_0
    //   264: aload_1
    //   265: iload_2
    //   266: iconst_0
    //   267: invokestatic addAppToList : (Ljava/lang/String;Ljava/lang/String;ZZ)V
    //   270: ldc com/tt/miniapp/entity/AppJumpListManager
    //   272: monitorexit
    //   273: return
    //   274: astore_0
    //   275: ldc com/tt/miniapp/entity/AppJumpListManager
    //   277: monitorexit
    //   278: goto -> 283
    //   281: aload_0
    //   282: athrow
    //   283: goto -> 281
    // Exception table:
    //   from	to	target	type
    //   3	24	274	finally
    //   28	73	274	finally
    //   78	117	274	finally
    //   125	138	274	finally
    //   143	182	274	finally
    //   195	211	274	finally
    //   222	237	274	finally
    //   237	263	274	finally
    //   263	270	274	finally
  }
  
  public static boolean backToApp(String paramString1, String paramString2, boolean paramBoolean) {
    // Byte code:
    //   0: ldc com/tt/miniapp/entity/AppJumpListManager
    //   2: monitorenter
    //   3: ldc 'AppJumpListManager'
    //   5: bipush #6
    //   7: anewarray java/lang/Object
    //   10: dup
    //   11: iconst_0
    //   12: ldc 'backToApp backAppId:'
    //   14: aastore
    //   15: dup
    //   16: iconst_1
    //   17: aload_0
    //   18: aastore
    //   19: dup
    //   20: iconst_2
    //   21: ldc 'refererInfo:'
    //   23: aastore
    //   24: dup
    //   25: iconst_3
    //   26: aload_1
    //   27: aastore
    //   28: dup
    //   29: iconst_4
    //   30: ldc 'isApiCall:'
    //   32: aastore
    //   33: dup
    //   34: iconst_5
    //   35: iload_2
    //   36: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   39: aastore
    //   40: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   43: ldc ''
    //   45: astore #4
    //   47: aload_0
    //   48: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   51: ifeq -> 75
    //   54: ldc 'AppJumpListManager'
    //   56: iconst_1
    //   57: anewarray java/lang/Object
    //   60: dup
    //   61: iconst_0
    //   62: ldc 'TextUtils.isEmpty(backAppId)'
    //   64: aastore
    //   65: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   68: getstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   71: astore_3
    //   72: goto -> 106
    //   75: aload_0
    //   76: astore_3
    //   77: aload_0
    //   78: getstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   81: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   84: ifne -> 106
    //   87: ldc 'AppJumpListManager'
    //   89: iconst_1
    //   90: anewarray java/lang/Object
    //   93: dup
    //   94: iconst_0
    //   95: ldc 'backToApp !TextUtils.equals(backAppId, currentApp)'
    //   97: aastore
    //   98: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   101: ldc com/tt/miniapp/entity/AppJumpListManager
    //   103: monitorexit
    //   104: iconst_0
    //   105: ireturn
    //   106: aload_3
    //   107: invokestatic removeApp : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/AppRunningEntity;
    //   110: astore #6
    //   112: ldc 'AppJumpListManager'
    //   114: iconst_2
    //   115: anewarray java/lang/Object
    //   118: dup
    //   119: iconst_0
    //   120: ldc 'backToApp backAppEntity:'
    //   122: aastore
    //   123: dup
    //   124: iconst_1
    //   125: aload #6
    //   127: aastore
    //   128: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   131: aconst_null
    //   132: astore #5
    //   134: aload #4
    //   136: astore_0
    //   137: aload #6
    //   139: ifnull -> 431
    //   142: aload #6
    //   144: invokevirtual getPreAppId : ()Ljava/lang/String;
    //   147: astore_3
    //   148: aload_3
    //   149: astore_0
    //   150: aload_3
    //   151: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   154: ifne -> 431
    //   157: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   160: aload_3
    //   161: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   164: checkcast com/tt/miniapphost/entity/AppRunningEntity
    //   167: astore #4
    //   169: aload_3
    //   170: astore_0
    //   171: aload #4
    //   173: astore_3
    //   174: goto -> 177
    //   177: aload_0
    //   178: putstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   181: iload_2
    //   182: ifeq -> 343
    //   185: aload_3
    //   186: ifnonnull -> 208
    //   189: ldc 'AppJumpListManager'
    //   191: iconst_1
    //   192: anewarray java/lang/Object
    //   195: dup
    //   196: iconst_0
    //   197: ldc 'newTopEntity == null'
    //   199: aastore
    //   200: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   203: ldc com/tt/miniapp/entity/AppJumpListManager
    //   205: monitorexit
    //   206: iconst_0
    //   207: ireturn
    //   208: new java/util/HashMap
    //   211: dup
    //   212: invokespecial <init> : ()V
    //   215: astore #6
    //   217: aload #6
    //   219: ldc 'launch_from'
    //   221: ldc 'back_mp'
    //   223: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   228: pop
    //   229: aload_1
    //   230: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   233: istore_2
    //   234: aload #5
    //   236: astore #4
    //   238: iload_2
    //   239: ifne -> 267
    //   242: aload_1
    //   243: invokestatic getMapFromJson : (Ljava/lang/String;)Ljava/util/Map;
    //   246: astore #4
    //   248: goto -> 267
    //   251: astore_1
    //   252: bipush #6
    //   254: ldc 'AppJumpListManager'
    //   256: aload_1
    //   257: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   260: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   263: aload #5
    //   265: astore #4
    //   267: new com/tt/miniapphost/entity/MicroSchemaEntity$Builder
    //   270: dup
    //   271: invokespecial <init> : ()V
    //   274: aload_0
    //   275: invokevirtual appId : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   278: aload #4
    //   280: invokevirtual refererInfo : (Ljava/util/Map;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   283: aload #6
    //   285: invokevirtual bdpLog : (Ljava/util/Map;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   288: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   291: ldc 'back_mp'
    //   293: invokevirtual getScene : (Ljava/lang/String;)Ljava/lang/String;
    //   296: invokevirtual scene : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   299: astore_1
    //   300: aload_3
    //   301: invokevirtual isGame : ()Z
    //   304: ifeq -> 314
    //   307: getstatic com/tt/miniapphost/entity/MicroSchemaEntity$Host.MICROGAME : Lcom/tt/miniapphost/entity/MicroSchemaEntity$Host;
    //   310: astore_0
    //   311: goto -> 318
    //   314: getstatic com/tt/miniapphost/entity/MicroSchemaEntity$Host.MICROAPP : Lcom/tt/miniapphost/entity/MicroSchemaEntity$Host;
    //   317: astore_0
    //   318: aload_1
    //   319: aload_0
    //   320: invokevirtual host : (Lcom/tt/miniapphost/entity/MicroSchemaEntity$Host;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   323: invokevirtual build : ()Lcom/tt/miniapphost/entity/MicroSchemaEntity;
    //   326: astore_0
    //   327: invokestatic inst : ()Lcom/tt/miniapphost/AppbrandSupport;
    //   330: aload_0
    //   331: invokevirtual toSchema : ()Ljava/lang/String;
    //   334: invokevirtual openAppbrand : (Ljava/lang/String;)Z
    //   337: pop
    //   338: ldc com/tt/miniapp/entity/AppJumpListManager
    //   340: monitorexit
    //   341: iconst_1
    //   342: ireturn
    //   343: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   346: invokevirtual isEmpty : ()Z
    //   349: istore_2
    //   350: iload_2
    //   351: ifeq -> 366
    //   354: ldc com/tt/miniapp/entity/AppJumpListManager
    //   356: monitorexit
    //   357: aload #6
    //   359: ifnull -> 364
    //   362: iconst_1
    //   363: ireturn
    //   364: iconst_0
    //   365: ireturn
    //   366: ldc 'AppJumpListManager'
    //   368: iconst_2
    //   369: anewarray java/lang/Object
    //   372: dup
    //   373: iconst_0
    //   374: ldc_w 'backToApp newTopAppId:'
    //   377: aastore
    //   378: dup
    //   379: iconst_1
    //   380: aload_0
    //   381: aastore
    //   382: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   385: aload_3
    //   386: ifnull -> 420
    //   389: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   392: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   395: invokestatic isMiniAppStackAtTop : (Landroid/content/Context;)Z
    //   398: ifne -> 420
    //   401: ldc 'AppJumpListManager'
    //   403: iconst_1
    //   404: anewarray java/lang/Object
    //   407: dup
    //   408: iconst_0
    //   409: ldc_w 'backToApp tryMoveMiniAppActivityTaskToFront'
    //   412: aastore
    //   413: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   416: aload_0
    //   417: invokestatic tryMoveMiniAppActivityTaskToFront : (Ljava/lang/String;)V
    //   420: ldc com/tt/miniapp/entity/AppJumpListManager
    //   422: monitorexit
    //   423: iconst_1
    //   424: ireturn
    //   425: astore_0
    //   426: ldc com/tt/miniapp/entity/AppJumpListManager
    //   428: monitorexit
    //   429: aload_0
    //   430: athrow
    //   431: aconst_null
    //   432: astore_3
    //   433: goto -> 177
    // Exception table:
    //   from	to	target	type
    //   3	43	425	finally
    //   47	72	425	finally
    //   77	101	425	finally
    //   106	131	425	finally
    //   142	148	425	finally
    //   150	169	425	finally
    //   177	181	425	finally
    //   189	203	425	finally
    //   208	234	425	finally
    //   242	248	251	java/lang/Exception
    //   242	248	425	finally
    //   252	263	425	finally
    //   267	311	425	finally
    //   314	318	425	finally
    //   318	338	425	finally
    //   343	350	425	finally
    //   366	385	425	finally
    //   389	420	425	finally
  }
  
  public static void clearAppList() {
    // Byte code:
    //   0: ldc 'AppJumpListManager'
    //   2: iconst_1
    //   3: anewarray java/lang/Object
    //   6: dup
    //   7: iconst_0
    //   8: ldc_w 'clearAppList'
    //   11: aastore
    //   12: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   15: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   18: invokevirtual size : ()I
    //   21: ifle -> 172
    //   24: ldc com/tt/miniapp/entity/AppJumpListManager
    //   26: monitorenter
    //   27: new java/util/LinkedHashMap
    //   30: dup
    //   31: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   34: invokespecial <init> : (Ljava/util/Map;)V
    //   37: astore_2
    //   38: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   41: invokevirtual clear : ()V
    //   44: ldc com/tt/miniapp/entity/AppJumpListManager
    //   46: monitorexit
    //   47: ldc ''
    //   49: putstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   52: aload_2
    //   53: invokevirtual keySet : ()Ljava/util/Set;
    //   56: invokeinterface iterator : ()Ljava/util/Iterator;
    //   61: astore_3
    //   62: ldc ''
    //   64: astore_0
    //   65: aload_3
    //   66: invokeinterface hasNext : ()Z
    //   71: ifeq -> 161
    //   74: aload_3
    //   75: invokeinterface next : ()Ljava/lang/Object;
    //   80: checkcast java/lang/String
    //   83: astore_1
    //   84: aload_1
    //   85: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   88: ifne -> 65
    //   91: aload_2
    //   92: aload_1
    //   93: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   96: checkcast com/tt/miniapphost/entity/AppRunningEntity
    //   99: astore #4
    //   101: aload #4
    //   103: ifnull -> 65
    //   106: aload_1
    //   107: invokestatic getProcessInfoByAppId : (Ljava/lang/String;)Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   110: astore #5
    //   112: aload #5
    //   114: ifnull -> 65
    //   117: aload #5
    //   119: invokevirtual isLaunchActivityInHostStack : ()Z
    //   122: ifeq -> 65
    //   125: ldc com/tt/miniapp/entity/AppJumpListManager
    //   127: monitorenter
    //   128: aload_1
    //   129: aload_0
    //   130: aload #4
    //   132: invokevirtual isGame : ()Z
    //   135: aload #4
    //   137: invokevirtual isSpecial : ()Z
    //   140: invokestatic addAppToList : (Ljava/lang/String;Ljava/lang/String;ZZ)V
    //   143: ldc com/tt/miniapp/entity/AppJumpListManager
    //   145: monitorexit
    //   146: aload_1
    //   147: invokestatic notifyUpdateSnapshot : (Ljava/lang/String;)V
    //   150: aload_1
    //   151: astore_0
    //   152: goto -> 65
    //   155: astore_0
    //   156: ldc com/tt/miniapp/entity/AppJumpListManager
    //   158: monitorexit
    //   159: aload_0
    //   160: athrow
    //   161: aload_2
    //   162: invokevirtual clear : ()V
    //   165: return
    //   166: astore_0
    //   167: ldc com/tt/miniapp/entity/AppJumpListManager
    //   169: monitorexit
    //   170: aload_0
    //   171: athrow
    //   172: return
    // Exception table:
    //   from	to	target	type
    //   27	47	166	finally
    //   128	146	155	finally
    //   156	159	155	finally
    //   167	170	166	finally
  }
  
  public static String getNeedKillAppId() {
    // Byte code:
    //   0: new android/util/SparseArray
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #5
    //   9: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   12: invokevirtual keySet : ()Ljava/util/Set;
    //   15: invokeinterface iterator : ()Ljava/util/Iterator;
    //   20: astore #6
    //   22: iconst_m1
    //   23: istore_1
    //   24: iconst_0
    //   25: istore_0
    //   26: aload #6
    //   28: invokeinterface hasNext : ()Z
    //   33: istore #4
    //   35: iconst_1
    //   36: istore_2
    //   37: iload #4
    //   39: ifeq -> 141
    //   42: aload #6
    //   44: invokeinterface next : ()Ljava/lang/Object;
    //   49: checkcast java/lang/String
    //   52: astore #7
    //   54: iload_0
    //   55: iconst_3
    //   56: if_icmpge -> 26
    //   59: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   62: aload #7
    //   64: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   67: checkcast com/tt/miniapphost/entity/AppRunningEntity
    //   70: astore #7
    //   72: aload #7
    //   74: invokevirtual isSpecial : ()Z
    //   77: ifeq -> 83
    //   80: bipush #10
    //   82: istore_2
    //   83: aload #7
    //   85: invokevirtual isGame : ()Z
    //   88: ifeq -> 98
    //   91: iload_2
    //   92: iconst_5
    //   93: imul
    //   94: istore_2
    //   95: goto -> 103
    //   98: iload_2
    //   99: bipush #10
    //   101: imul
    //   102: istore_2
    //   103: iload_2
    //   104: iload_0
    //   105: iadd
    //   106: istore_3
    //   107: iload_1
    //   108: iconst_m1
    //   109: if_icmpeq -> 119
    //   112: iload_1
    //   113: istore_2
    //   114: iload_1
    //   115: iload_3
    //   116: if_icmple -> 121
    //   119: iload_3
    //   120: istore_2
    //   121: aload #5
    //   123: iload_3
    //   124: aload #7
    //   126: invokevirtual getAppId : ()Ljava/lang/String;
    //   129: invokevirtual put : (ILjava/lang/Object;)V
    //   132: iload_0
    //   133: iconst_1
    //   134: iadd
    //   135: istore_0
    //   136: iload_2
    //   137: istore_1
    //   138: goto -> 26
    //   141: aload #5
    //   143: iload_1
    //   144: invokevirtual get : (I)Ljava/lang/Object;
    //   147: checkcast java/lang/String
    //   150: astore #5
    //   152: ldc 'AppJumpListManager'
    //   154: iconst_2
    //   155: anewarray java/lang/Object
    //   158: dup
    //   159: iconst_0
    //   160: ldc_w 'getNeedKillAppId: '
    //   163: aastore
    //   164: dup
    //   165: iconst_1
    //   166: aload #5
    //   168: aastore
    //   169: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   172: aload #5
    //   174: areturn
  }
  
  public static String getPreAppId(String paramString) {
    // Byte code:
    //   0: ldc com/tt/miniapp/entity/AppJumpListManager
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   6: invokevirtual isEmpty : ()Z
    //   9: ifne -> 52
    //   12: aload_0
    //   13: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   16: ifeq -> 22
    //   19: goto -> 52
    //   22: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   25: aload_0
    //   26: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   29: checkcast com/tt/miniapphost/entity/AppRunningEntity
    //   32: astore_0
    //   33: aload_0
    //   34: ifnonnull -> 42
    //   37: ldc com/tt/miniapp/entity/AppJumpListManager
    //   39: monitorexit
    //   40: aconst_null
    //   41: areturn
    //   42: aload_0
    //   43: invokevirtual getPreAppId : ()Ljava/lang/String;
    //   46: astore_0
    //   47: ldc com/tt/miniapp/entity/AppJumpListManager
    //   49: monitorexit
    //   50: aload_0
    //   51: areturn
    //   52: ldc com/tt/miniapp/entity/AppJumpListManager
    //   54: monitorexit
    //   55: aconst_null
    //   56: areturn
    //   57: astore_0
    //   58: ldc com/tt/miniapp/entity/AppJumpListManager
    //   60: monitorexit
    //   61: aload_0
    //   62: athrow
    // Exception table:
    //   from	to	target	type
    //   3	19	57	finally
    //   22	33	57	finally
    //   42	47	57	finally
  }
  
  public static boolean isInAppJumpList(String paramString) {
    return applist.keySet().contains(paramString);
  }
  
  public static void jumpToApp(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, String paramString5, String paramString6, int paramInt, String paramString7) {
    // Byte code:
    //   0: ldc 'AppJumpListManager'
    //   2: bipush #15
    //   4: anewarray java/lang/Object
    //   7: dup
    //   8: iconst_0
    //   9: ldc_w 'jumpToApp() called with: appId = ['
    //   12: aastore
    //   13: dup
    //   14: iconst_1
    //   15: aload_0
    //   16: aastore
    //   17: dup
    //   18: iconst_2
    //   19: ldc_w '], startPage = ['
    //   22: aastore
    //   23: dup
    //   24: iconst_3
    //   25: aload_1
    //   26: aastore
    //   27: dup
    //   28: iconst_4
    //   29: ldc_w '], query = ['
    //   32: aastore
    //   33: dup
    //   34: iconst_5
    //   35: aload_2
    //   36: aastore
    //   37: dup
    //   38: bipush #6
    //   40: ldc_w '], refererInfo = ['
    //   43: aastore
    //   44: dup
    //   45: bipush #7
    //   47: aload_3
    //   48: aastore
    //   49: dup
    //   50: bipush #8
    //   52: ldc_w '], isGame = ['
    //   55: aastore
    //   56: dup
    //   57: bipush #9
    //   59: iload #4
    //   61: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   64: aastore
    //   65: dup
    //   66: bipush #10
    //   68: ldc_w '], versionType = ['
    //   71: aastore
    //   72: dup
    //   73: bipush #11
    //   75: aload #5
    //   77: aastore
    //   78: dup
    //   79: bipush #12
    //   81: ldc '], preAppId = ['
    //   83: aastore
    //   84: dup
    //   85: bipush #13
    //   87: aload #6
    //   89: aastore
    //   90: dup
    //   91: bipush #14
    //   93: ldc ']'
    //   95: aastore
    //   96: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   99: aload_0
    //   100: aload #6
    //   102: iload #4
    //   104: invokestatic addToList : (Ljava/lang/String;Ljava/lang/String;Z)V
    //   107: new java/util/HashMap
    //   110: dup
    //   111: invokespecial <init> : ()V
    //   114: astore #6
    //   116: aload #6
    //   118: ldc 'launch_from'
    //   120: ldc_w 'in_mp'
    //   123: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   128: pop
    //   129: aload_3
    //   130: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   133: ifne -> 156
    //   136: aload_3
    //   137: invokestatic getMapFromJson : (Ljava/lang/String;)Ljava/util/Map;
    //   140: astore_3
    //   141: goto -> 158
    //   144: astore_3
    //   145: bipush #6
    //   147: ldc 'AppJumpListManager'
    //   149: aload_3
    //   150: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   153: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   156: aconst_null
    //   157: astore_3
    //   158: iload #4
    //   160: ifeq -> 196
    //   163: aload_2
    //   164: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   167: ifne -> 343
    //   170: aload_2
    //   171: invokestatic decode : (Ljava/lang/String;)Ljava/lang/String;
    //   174: invokestatic getMapFromJson : (Ljava/lang/String;)Ljava/util/Map;
    //   177: astore_2
    //   178: goto -> 345
    //   181: astore_1
    //   182: bipush #6
    //   184: ldc 'AppJumpListManager'
    //   186: aload_1
    //   187: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   190: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   193: goto -> 343
    //   196: aload_1
    //   197: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   200: ifne -> 343
    //   203: aload_1
    //   204: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   207: astore_2
    //   208: aload_1
    //   209: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   212: ifne -> 319
    //   215: aload_1
    //   216: ldc_w '\?'
    //   219: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   222: astore_1
    //   223: aload_1
    //   224: arraylength
    //   225: iconst_1
    //   226: if_icmple -> 312
    //   229: aload_1
    //   230: iconst_0
    //   231: aaload
    //   232: astore_1
    //   233: aload_2
    //   234: invokevirtual getQueryParameterNames : ()Ljava/util/Set;
    //   237: astore #10
    //   239: new org/json/JSONObject
    //   242: dup
    //   243: invokespecial <init> : ()V
    //   246: astore #9
    //   248: aload #10
    //   250: invokeinterface iterator : ()Ljava/util/Iterator;
    //   255: astore #10
    //   257: aload #10
    //   259: invokeinterface hasNext : ()Z
    //   264: ifeq -> 296
    //   267: aload #10
    //   269: invokeinterface next : ()Ljava/lang/Object;
    //   274: checkcast java/lang/String
    //   277: astore #11
    //   279: aload #9
    //   281: aload #11
    //   283: aload_2
    //   284: aload #11
    //   286: invokevirtual getQueryParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   289: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   292: pop
    //   293: goto -> 257
    //   296: aload #9
    //   298: invokevirtual toString : ()Ljava/lang/String;
    //   301: invokestatic getMapFromJson : (Ljava/lang/String;)Ljava/util/Map;
    //   304: astore_2
    //   305: goto -> 347
    //   308: astore_2
    //   309: goto -> 327
    //   312: aload_1
    //   313: iconst_0
    //   314: aaload
    //   315: astore_1
    //   316: goto -> 338
    //   319: aconst_null
    //   320: astore_1
    //   321: goto -> 338
    //   324: astore_2
    //   325: aconst_null
    //   326: astore_1
    //   327: bipush #6
    //   329: ldc 'AppJumpListManager'
    //   331: aload_2
    //   332: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   335: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   338: aconst_null
    //   339: astore_2
    //   340: goto -> 347
    //   343: aconst_null
    //   344: astore_2
    //   345: aconst_null
    //   346: astore_1
    //   347: new java/util/HashMap
    //   350: dup
    //   351: invokespecial <init> : ()V
    //   354: astore #9
    //   356: aload #8
    //   358: ifnull -> 374
    //   361: aload #9
    //   363: ldc_w 'origin_entrance'
    //   366: aload #8
    //   368: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   373: pop
    //   374: new com/tt/miniapphost/entity/MicroSchemaEntity$Builder
    //   377: dup
    //   378: invokespecial <init> : ()V
    //   381: aload_0
    //   382: invokevirtual appId : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   385: aload #6
    //   387: invokevirtual bdpLog : (Ljava/util/Map;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   390: aload_1
    //   391: invokevirtual path : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   394: aload_2
    //   395: invokevirtual query : (Ljava/util/Map;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   398: aload #5
    //   400: invokestatic fromString : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$VersionType;
    //   403: invokevirtual versionType : (Lcom/tt/miniapphost/entity/MicroSchemaEntity$VersionType;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   406: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   409: ldc_w 'in_mp'
    //   412: invokevirtual getScene : (Ljava/lang/String;)Ljava/lang/String;
    //   415: invokevirtual scene : (Ljava/lang/String;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   418: aload_3
    //   419: invokevirtual refererInfo : (Ljava/util/Map;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   422: astore_1
    //   423: iload #4
    //   425: ifeq -> 435
    //   428: getstatic com/tt/miniapphost/entity/MicroSchemaEntity$Host.MICROGAME : Lcom/tt/miniapphost/entity/MicroSchemaEntity$Host;
    //   431: astore_0
    //   432: goto -> 439
    //   435: getstatic com/tt/miniapphost/entity/MicroSchemaEntity$Host.MICROAPP : Lcom/tt/miniapphost/entity/MicroSchemaEntity$Host;
    //   438: astore_0
    //   439: aload_1
    //   440: aload_0
    //   441: invokevirtual host : (Lcom/tt/miniapphost/entity/MicroSchemaEntity$Host;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   444: aload #9
    //   446: invokevirtual customFields : (Ljava/util/Map;)Lcom/tt/miniapphost/entity/MicroSchemaEntity$Builder;
    //   449: invokevirtual build : ()Lcom/tt/miniapphost/entity/MicroSchemaEntity;
    //   452: invokevirtual toSchema : ()Ljava/lang/String;
    //   455: astore_0
    //   456: ldc 'AppJumpListManager'
    //   458: iconst_2
    //   459: anewarray java/lang/Object
    //   462: dup
    //   463: iconst_0
    //   464: ldc_w 'jumpToApp schema:'
    //   467: aastore
    //   468: dup
    //   469: iconst_1
    //   470: aload_0
    //   471: aastore
    //   472: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   475: invokestatic inst : ()Lcom/tt/miniapphost/AppbrandSupport;
    //   478: aload_0
    //   479: invokevirtual openAppbrand : (Ljava/lang/String;)Z
    //   482: pop
    //   483: return
    // Exception table:
    //   from	to	target	type
    //   136	141	144	java/lang/Exception
    //   170	178	181	java/lang/Exception
    //   203	229	324	java/lang/Exception
    //   233	257	308	java/lang/Exception
    //   257	293	308	java/lang/Exception
    //   296	305	308	java/lang/Exception
  }
  
  public static void jumpToApp(String paramString1, String paramString2, boolean paramBoolean) {
    AppBrandLogger.d("AppJumpListManager", new Object[] { "jumpToApp() called with: schema = [", paramString1, "], preAppId = [", paramString2, "]" });
    String str = Uri.parse(paramString1).getQueryParameter("app_id");
    if (Uri.parse(paramString1).getBooleanQueryParameter("killCurrentProcess", false))
      needKillAppId = paramString2; 
    addToList(str, paramString2, paramBoolean);
    AppbrandSupport.inst().openAppbrand(paramString1);
  }
  
  public static void killPreAppIfNeed() {
    if (!TextUtils.isEmpty(needKillAppId)) {
      AppProcessManager.killProcess(needKillAppId);
      needKillAppId = null;
    } 
  }
  
  private static void notifyUpdateSnapshot(String paramString) {
    AppBrandLogger.d("AppJumpListManager", new Object[] { "notifyUpdateSnapshot appId:", paramString });
    AppProcessManager.ProcessInfo processInfo = AppProcessManager.getProcessInfoByAppId(paramString);
    if (processInfo != null)
      InnerMiniAppProcessBridge.notifyUpdateSnapshotEvent(processInfo.mProcessIdentity); 
  }
  
  public static AppRunningEntity removeApp(String paramString) {
    // Byte code:
    //   0: ldc com/tt/miniapp/entity/AppJumpListManager
    //   2: monitorenter
    //   3: ldc 'AppJumpListManager'
    //   5: iconst_5
    //   6: anewarray java/lang/Object
    //   9: dup
    //   10: iconst_0
    //   11: ldc_w 'removeApp() called with: appId = ['
    //   14: aastore
    //   15: dup
    //   16: iconst_1
    //   17: aload_0
    //   18: aastore
    //   19: dup
    //   20: iconst_2
    //   21: ldc ']'
    //   23: aastore
    //   24: dup
    //   25: iconst_3
    //   26: ldc_w 'applist:'
    //   29: aastore
    //   30: dup
    //   31: iconst_4
    //   32: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   35: aastore
    //   36: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   39: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   42: aload_0
    //   43: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   46: checkcast com/tt/miniapphost/entity/AppRunningEntity
    //   49: astore_1
    //   50: aload_1
    //   51: ifnull -> 154
    //   54: getstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   57: aload_0
    //   58: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   61: ifeq -> 74
    //   64: aload_1
    //   65: invokevirtual getPreAppId : ()Ljava/lang/String;
    //   68: putstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   71: goto -> 149
    //   74: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   77: invokevirtual keySet : ()Ljava/util/Set;
    //   80: invokeinterface iterator : ()Ljava/util/Iterator;
    //   85: astore_2
    //   86: aload_2
    //   87: invokeinterface hasNext : ()Z
    //   92: ifeq -> 149
    //   95: aload_2
    //   96: invokeinterface next : ()Ljava/lang/Object;
    //   101: checkcast java/lang/String
    //   104: astore_3
    //   105: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   108: aload_3
    //   109: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   112: checkcast com/tt/miniapphost/entity/AppRunningEntity
    //   115: astore_3
    //   116: aload_3
    //   117: ifnull -> 86
    //   120: aload_0
    //   121: aload_3
    //   122: invokevirtual getPreAppId : ()Ljava/lang/String;
    //   125: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   128: ifeq -> 86
    //   131: aload_3
    //   132: aload_1
    //   133: invokevirtual getPreAppId : ()Ljava/lang/String;
    //   136: invokevirtual setPreAppId : (Ljava/lang/String;)V
    //   139: aload_3
    //   140: invokevirtual getAppId : ()Ljava/lang/String;
    //   143: invokestatic notifyUpdateSnapshot : (Ljava/lang/String;)V
    //   146: goto -> 86
    //   149: ldc com/tt/miniapp/entity/AppJumpListManager
    //   151: monitorexit
    //   152: aload_1
    //   153: areturn
    //   154: ldc com/tt/miniapp/entity/AppJumpListManager
    //   156: monitorexit
    //   157: aconst_null
    //   158: areturn
    //   159: astore_0
    //   160: ldc com/tt/miniapp/entity/AppJumpListManager
    //   162: monitorexit
    //   163: goto -> 168
    //   166: aload_0
    //   167: athrow
    //   168: goto -> 166
    // Exception table:
    //   from	to	target	type
    //   3	50	159	finally
    //   54	71	159	finally
    //   74	86	159	finally
    //   86	116	159	finally
    //   120	146	159	finally
  }
  
  public static void updateJumpList(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    // Byte code:
    //   0: ldc com/tt/miniapp/entity/AppJumpListManager
    //   2: monitorenter
    //   3: ldc 'AppJumpListManager'
    //   5: bipush #7
    //   7: anewarray java/lang/Object
    //   10: dup
    //   11: iconst_0
    //   12: ldc_w 'updateJumpList() called with: appId = ['
    //   15: aastore
    //   16: dup
    //   17: iconst_1
    //   18: aload_0
    //   19: aastore
    //   20: dup
    //   21: iconst_2
    //   22: ldc_w '],  isGame = ['
    //   25: aastore
    //   26: dup
    //   27: iconst_3
    //   28: iload_1
    //   29: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   32: aastore
    //   33: dup
    //   34: iconst_4
    //   35: ldc_w ']  isSpecial = ['
    //   38: aastore
    //   39: dup
    //   40: iconst_5
    //   41: iload_2
    //   42: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   45: aastore
    //   46: dup
    //   47: bipush #6
    //   49: ldc ']'
    //   51: aastore
    //   52: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   55: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   58: aload_0
    //   59: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   62: ifne -> 153
    //   65: ldc 'AppJumpListManager'
    //   67: bipush #6
    //   69: anewarray java/lang/Object
    //   72: dup
    //   73: iconst_0
    //   74: ldc_w '!applist.containsKey(appId) appId:'
    //   77: aastore
    //   78: dup
    //   79: iconst_1
    //   80: aload_0
    //   81: aastore
    //   82: dup
    //   83: iconst_2
    //   84: ldc_w 'currentApp:'
    //   87: aastore
    //   88: dup
    //   89: iconst_3
    //   90: getstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   93: aastore
    //   94: dup
    //   95: iconst_4
    //   96: ldc_w 'applist:'
    //   99: aastore
    //   100: dup
    //   101: iconst_5
    //   102: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   105: aastore
    //   106: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   109: getstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   112: astore_3
    //   113: aload_0
    //   114: getstatic com/tt/miniapp/entity/AppJumpListManager.currentApp : Ljava/lang/String;
    //   117: iload_1
    //   118: invokestatic addToList : (Ljava/lang/String;Ljava/lang/String;Z)V
    //   121: aload_3
    //   122: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   125: ifne -> 153
    //   128: ldc 'AppJumpListManager'
    //   130: iconst_2
    //   131: anewarray java/lang/Object
    //   134: dup
    //   135: iconst_0
    //   136: ldc_w 'applist invalid'
    //   139: aastore
    //   140: dup
    //   141: iconst_1
    //   142: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   145: aastore
    //   146: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   149: aload_0
    //   150: invokestatic notifyUpdateSnapshot : (Ljava/lang/String;)V
    //   153: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   156: invokevirtual keySet : ()Ljava/util/Set;
    //   159: invokeinterface iterator : ()Ljava/util/Iterator;
    //   164: astore_3
    //   165: aload_3
    //   166: invokeinterface hasNext : ()Z
    //   171: ifeq -> 223
    //   174: aload_3
    //   175: invokeinterface next : ()Ljava/lang/Object;
    //   180: checkcast java/lang/String
    //   183: astore #4
    //   185: aload_0
    //   186: aload #4
    //   188: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   191: ifeq -> 165
    //   194: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   197: aload #4
    //   199: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   202: ifnull -> 165
    //   205: getstatic com/tt/miniapp/entity/AppJumpListManager.applist : Ljava/util/LinkedHashMap;
    //   208: aload #4
    //   210: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   213: checkcast com/tt/miniapphost/entity/AppRunningEntity
    //   216: iload_2
    //   217: invokevirtual setSpecial : (Z)V
    //   220: goto -> 165
    //   223: ldc com/tt/miniapp/entity/AppJumpListManager
    //   225: monitorexit
    //   226: return
    //   227: astore_0
    //   228: ldc com/tt/miniapp/entity/AppJumpListManager
    //   230: monitorexit
    //   231: goto -> 236
    //   234: aload_0
    //   235: athrow
    //   236: goto -> 234
    // Exception table:
    //   from	to	target	type
    //   3	153	227	finally
    //   153	165	227	finally
    //   165	220	227	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\entity\AppJumpListManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */