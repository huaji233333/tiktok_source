package com.tt.miniapp.debug;

import android.content.Context;
import android.content.Intent;
import d.f.a.b;
import d.f.b.l;
import d.f.b.m;
import java.util.Map;

public final class DebugInfoUtil {
  public static final DebugInfoUtil INSTANCE = new DebugInfoUtil();
  
  private static String sInfo;
  
  public static final Intent appendDebugInfo(Context paramContext, Intent paramIntent) {
    l.b(paramContext, "context");
    l.b(paramIntent, "intent");
    paramIntent.putExtra("bdp_debug_info", INSTANCE.generateDebugInfo(paramContext));
    return paramIntent;
  }
  
  private final String generateDebugInfo(Context paramContext) {
    // Byte code:
    //   0: getstatic com/tt/miniapp/debug/DebugInfoUtil.sInfo : Ljava/lang/String;
    //   3: astore_3
    //   4: aload_3
    //   5: ifnull -> 24
    //   8: aload_3
    //   9: ifnull -> 14
    //   12: aload_3
    //   13: areturn
    //   14: new d/u
    //   17: dup
    //   18: ldc 'null cannot be cast to non-null type kotlin.String'
    //   20: invokespecial <init> : (Ljava/lang/String;)V
    //   23: athrow
    //   24: new java/util/LinkedHashMap
    //   27: dup
    //   28: invokespecial <init> : ()V
    //   31: checkcast java/util/Map
    //   34: astore #5
    //   36: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   39: astore_3
    //   40: aload_3
    //   41: ldc 'AppbrandApplicationImpl.getInst()'
    //   43: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   46: aload_3
    //   47: invokevirtual getMiniAppContext : ()Lcom/tt/miniapp/base/MiniAppContextWrapper;
    //   50: astore #6
    //   52: aload #6
    //   54: ldc 'AppbrandApplicationImpl.getInst().miniAppContext'
    //   56: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   59: aload #6
    //   61: invokevirtual getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   64: astore_3
    //   65: aload_3
    //   66: ifnull -> 626
    //   69: aload_3
    //   70: getfield appId : Ljava/lang/String;
    //   73: astore #4
    //   75: aload #4
    //   77: astore_3
    //   78: aload #4
    //   80: ifnonnull -> 86
    //   83: goto -> 626
    //   86: aload #5
    //   88: ldc 'appId'
    //   90: aload_3
    //   91: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   96: pop
    //   97: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   100: astore_3
    //   101: aload_3
    //   102: ldc 'AppbrandContext.getInst()'
    //   104: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   107: aload_3
    //   108: invokevirtual getInitParams : ()Lcom/tt/miniapphost/entity/InitParamsEntity;
    //   111: astore_3
    //   112: invokestatic a : ()Ljava/lang/String;
    //   115: astore #4
    //   117: aload #4
    //   119: ldc 'NetRequestUtil.getDeviceId()'
    //   121: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   124: aload #5
    //   126: ldc 'deviceId'
    //   128: aload #4
    //   130: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   135: pop
    //   136: aload_3
    //   137: ldc 'initParams'
    //   139: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   142: aload_3
    //   143: invokevirtual getAppId : ()Ljava/lang/String;
    //   146: astore #4
    //   148: aload #4
    //   150: ldc 'initParams.appId'
    //   152: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   155: aload #5
    //   157: ldc 'aid'
    //   159: aload #4
    //   161: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   166: pop
    //   167: aload_3
    //   168: invokevirtual getChannel : ()Ljava/lang/String;
    //   171: astore #4
    //   173: aload #4
    //   175: ldc 'initParams.channel'
    //   177: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   180: aload #5
    //   182: ldc 'channel'
    //   184: aload #4
    //   186: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   191: pop
    //   192: aload_3
    //   193: invokevirtual getOsVersion : ()Ljava/lang/String;
    //   196: astore #4
    //   198: aload #4
    //   200: ldc 'initParams.osVersion'
    //   202: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   205: aload #5
    //   207: ldc 'osVersion'
    //   209: aload #4
    //   211: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   216: pop
    //   217: aload_3
    //   218: invokevirtual getVersionCode : ()Ljava/lang/String;
    //   221: astore_3
    //   222: aload_3
    //   223: ldc 'initParams.versionCode'
    //   225: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   228: aload #5
    //   230: ldc 'hostVersionCode'
    //   232: aload_3
    //   233: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   238: pop
    //   239: aload #6
    //   241: invokevirtual getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   244: ifnull -> 367
    //   247: aload #6
    //   249: invokevirtual getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   252: astore_3
    //   253: aload_3
    //   254: ifnonnull -> 260
    //   257: invokestatic a : ()V
    //   260: aload_3
    //   261: ldc 'miniAppContext.appInfo!!'
    //   263: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   266: aload_3
    //   267: invokevirtual isGame : ()Z
    //   270: ifne -> 367
    //   273: aload #6
    //   275: ldc com/bytedance/sandboxapp/protocol/service/m/a
    //   277: invokevirtual getService : (Ljava/lang/Class;)Lcom/bytedance/sandboxapp/b/b;
    //   280: astore_3
    //   281: aload_3
    //   282: ldc 'miniAppContext.getServic…iceInterface::class.java)'
    //   284: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   287: aload_3
    //   288: checkcast com/bytedance/sandboxapp/protocol/service/m/a
    //   291: astore_3
    //   292: aload_3
    //   293: invokeinterface useWebVideo : ()Z
    //   298: ifne -> 633
    //   301: iconst_1
    //   302: istore_2
    //   303: goto -> 306
    //   306: aload #5
    //   308: ldc 'isNativeVideoView'
    //   310: iload_2
    //   311: invokestatic valueOf : (Z)Ljava/lang/String;
    //   314: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   319: pop
    //   320: aload_3
    //   321: invokeinterface useWebLivePlayer : ()Z
    //   326: ifne -> 638
    //   329: iconst_1
    //   330: istore_2
    //   331: goto -> 334
    //   334: aload #5
    //   336: ldc 'isNativeLivePlayer'
    //   338: iload_2
    //   339: invokestatic valueOf : (Z)Ljava/lang/String;
    //   342: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   347: pop
    //   348: aload #5
    //   350: ldc 'isRenderInBrowser'
    //   352: aload_3
    //   353: invokeinterface isRenderInBrowser : ()Z
    //   358: invokestatic valueOf : (Z)Ljava/lang/String;
    //   361: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   366: pop
    //   367: getstatic com/tt/miniapphost/render/export/TTWebSdkWrapper.INSTANCE : Lcom/tt/miniapphost/render/export/TTWebSdkWrapper;
    //   370: invokevirtual isTTWebView : ()Z
    //   373: istore_2
    //   374: aload #5
    //   376: ldc 'isTTWebView'
    //   378: iload_2
    //   379: invokestatic valueOf : (Z)Ljava/lang/String;
    //   382: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   387: pop
    //   388: iload_2
    //   389: ifeq -> 398
    //   392: aload_0
    //   393: aload #5
    //   395: invokespecial getTTWebViewInfo : (Ljava/util/Map;)V
    //   398: invokestatic getInst : ()Lcom/tt/miniapp/manager/basebundle/BaseBundleManager;
    //   401: aload_1
    //   402: invokevirtual getSdkCurrentVersionStr : (Landroid/content/Context;)Ljava/lang/String;
    //   405: astore_1
    //   406: aload_1
    //   407: ldc 'BaseBundleManager.getIns…urrentVersionStr(context)'
    //   409: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   412: aload #5
    //   414: ldc 'baseBundleVersion'
    //   416: aload_1
    //   417: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   422: pop
    //   423: invokestatic debug : ()Z
    //   426: ifeq -> 567
    //   429: aload #5
    //   431: ldc 'iHostVersion'
    //   433: ldc '3.3.9'
    //   435: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   440: pop
    //   441: aload #5
    //   443: ldc 'littleAppVersion'
    //   445: ldc '3.7.4-tiktok'
    //   447: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   452: pop
    //   453: aload #5
    //   455: ldc 'littleGameVersion'
    //   457: ldc '3.7.4'
    //   459: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   464: pop
    //   465: aload #5
    //   467: ldc 'heliumVersion'
    //   469: ldc '3.7.1-douyin'
    //   471: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   476: pop
    //   477: aload #5
    //   479: ldc 'heliumRTCVersion'
    //   481: ldc '3.4.0'
    //   483: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   488: pop
    //   489: aload #5
    //   491: ldc 'jsBindingVersion'
    //   493: ldc '3.4.0'
    //   495: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   500: pop
    //   501: aload #5
    //   503: ldc 'baseDownloadVersion'
    //   505: ldc '2.44.0'
    //   507: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   512: pop
    //   513: aload #5
    //   515: ldc 'adVersion'
    //   517: ldc '2.48.0'
    //   519: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   524: pop
    //   525: aload #5
    //   527: ldc 'i18NVersion'
    //   529: ldc '3.6.0'
    //   531: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   536: pop
    //   537: goto -> 567
    //   540: astore_1
    //   541: ldc 'DebugInfoUtil'
    //   543: iconst_1
    //   544: anewarray java/lang/Object
    //   547: dup
    //   548: iconst_0
    //   549: aload_1
    //   550: aastore
    //   551: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   554: ldc 'DebugInfoUtil'
    //   556: iconst_1
    //   557: anewarray java/lang/Object
    //   560: dup
    //   561: iconst_0
    //   562: aload_1
    //   563: aastore
    //   564: invokestatic outputError : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   567: aload #5
    //   569: invokeinterface keySet : ()Ljava/util/Set;
    //   574: checkcast java/lang/Iterable
    //   577: ldc_w '\\n'
    //   580: checkcast java/lang/CharSequence
    //   583: aconst_null
    //   584: aconst_null
    //   585: iconst_0
    //   586: aconst_null
    //   587: new com/tt/miniapp/debug/DebugInfoUtil$generateDebugInfo$1
    //   590: dup
    //   591: aload #5
    //   593: invokespecial <init> : (Ljava/util/Map;)V
    //   596: checkcast d/f/a/b
    //   599: bipush #30
    //   601: aconst_null
    //   602: invokestatic a : (Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Ld/f/a/b;ILjava/lang/Object;)Ljava/lang/String;
    //   605: astore_1
    //   606: aload_1
    //   607: putstatic com/tt/miniapp/debug/DebugInfoUtil.sInfo : Ljava/lang/String;
    //   610: aload_1
    //   611: ifnull -> 616
    //   614: aload_1
    //   615: areturn
    //   616: new d/u
    //   619: dup
    //   620: ldc 'null cannot be cast to non-null type kotlin.String'
    //   622: invokespecial <init> : (Ljava/lang/String;)V
    //   625: athrow
    //   626: ldc_w ''
    //   629: astore_3
    //   630: goto -> 86
    //   633: iconst_0
    //   634: istore_2
    //   635: goto -> 306
    //   638: iconst_0
    //   639: istore_2
    //   640: goto -> 334
    // Exception table:
    //   from	to	target	type
    //   36	65	540	finally
    //   69	75	540	finally
    //   86	253	540	finally
    //   257	260	540	finally
    //   260	301	540	finally
    //   306	329	540	finally
    //   334	367	540	finally
    //   367	388	540	finally
    //   392	398	540	finally
    //   398	537	540	finally
  }
  
  private final void getTTWebViewInfo(Map<String, String> paramMap) {
    // Byte code:
    //   0: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   3: astore_2
    //   4: aload_2
    //   5: ldc 'AppbrandApplicationImpl.getInst()'
    //   7: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   10: aload_2
    //   11: invokevirtual getWebViewManager : ()Lcom/tt/miniapp/WebViewManager;
    //   14: astore_2
    //   15: aload_2
    //   16: ifnull -> 160
    //   19: aload_2
    //   20: invokevirtual getCurrentIRender : ()Lcom/tt/miniapp/WebViewManager$IRender;
    //   23: astore_2
    //   24: aload_2
    //   25: ifnull -> 160
    //   28: aload_2
    //   29: invokeinterface getWebView : ()Landroid/webkit/WebView;
    //   34: astore_2
    //   35: aload_2
    //   36: ifnull -> 160
    //   39: aload_2
    //   40: invokevirtual getSettings : ()Landroid/webkit/WebSettings;
    //   43: astore_2
    //   44: goto -> 47
    //   47: aload_2
    //   48: ifnull -> 138
    //   51: aload_2
    //   52: invokevirtual getUserAgentString : ()Ljava/lang/String;
    //   55: astore_2
    //   56: new d/m/l
    //   59: dup
    //   60: ldc_w 'TTWebView/([0-9]*)'
    //   63: invokespecial <init> : (Ljava/lang/String;)V
    //   66: astore_3
    //   67: aload_2
    //   68: ldc_w 'userAgentString'
    //   71: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   74: aload_3
    //   75: aload_2
    //   76: checkcast java/lang/CharSequence
    //   79: iconst_0
    //   80: iconst_2
    //   81: aconst_null
    //   82: invokestatic findAll$default : (Ld/m/l;Ljava/lang/CharSequence;IILjava/lang/Object;)Ld/l/g;
    //   85: invokestatic f : (Ld/l/g;)Ljava/util/List;
    //   88: astore_2
    //   89: aload_2
    //   90: checkcast java/util/Collection
    //   93: invokeinterface isEmpty : ()Z
    //   98: iconst_1
    //   99: ixor
    //   100: ifeq -> 159
    //   103: aload_1
    //   104: ldc_w 'ttWebViewVersion'
    //   107: aload_2
    //   108: iconst_0
    //   109: invokeinterface get : (I)Ljava/lang/Object;
    //   114: checkcast d/m/j
    //   117: invokeinterface d : ()Ljava/util/List;
    //   122: iconst_1
    //   123: invokeinterface get : (I)Ljava/lang/Object;
    //   128: checkcast java/lang/String
    //   131: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   136: pop
    //   137: return
    //   138: return
    //   139: astore_1
    //   140: ldc 'DebugInfoUtil'
    //   142: iconst_2
    //   143: anewarray java/lang/Object
    //   146: dup
    //   147: iconst_0
    //   148: ldc_w 'parse ttWebView version error:'
    //   151: aastore
    //   152: dup
    //   153: iconst_1
    //   154: aload_1
    //   155: aastore
    //   156: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   159: return
    //   160: aconst_null
    //   161: astore_2
    //   162: goto -> 47
    // Exception table:
    //   from	to	target	type
    //   0	15	139	finally
    //   19	24	139	finally
    //   28	35	139	finally
    //   39	44	139	finally
    //   51	137	139	finally
  }
  
  static final class DebugInfoUtil$generateDebugInfo$1 extends m implements b<String, String> {
    DebugInfoUtil$generateDebugInfo$1(Map param1Map) {
      super(1);
    }
    
    public final String invoke(String param1String) {
      l.b(param1String, "it");
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1String);
      stringBuilder.append(": ");
      stringBuilder.append((String)this.$infoMap.get(param1String));
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\DebugInfoUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */