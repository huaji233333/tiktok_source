package com.tt.miniapp.net;

import android.content.Context;
import com.tt.miniapp.debug.network.NetworkDebugInterceptor;
import com.tt.miniapp.net.franmontiel.persistentcookiejar.cache.CookieCache;
import com.tt.miniapp.net.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.tt.miniapp.net.franmontiel.persistentcookiejar.persistence.CookiePersistor;
import com.tt.miniapp.net.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.tt.miniapp.net.interceptor.EncodeResponseInterceptor;
import com.tt.miniapp.net.interceptor.PkgResponseInterceptor;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.aa;
import okhttp3.l;
import okhttp3.m;
import okhttp3.n;
import okhttp3.t;
import okhttp3.u;
import okhttp3.y;

public class NetBus {
  private static y.a builder = new y.a();
  
  public static final List<String> mWhiteUrls;
  
  public static y okHttpClient;
  
  public static ThreadLocal<String> requestCookie;
  
  private static volatile n sDispatcher = null;
  
  public static y sOkHttpStreamDownloadClient;
  
  public static final List<String> webViewWhiteUrls;
  
  static {
    okHttpClient = builder.a(60000L, TimeUnit.MILLISECONDS).c(60000L, TimeUnit.MILLISECONDS).b(60000L, TimeUnit.MILLISECONDS).a((u)new EncodeResponseInterceptor()).a(Collections.singletonList(aa.HTTP_1_1)).a(new WebViewCookieHandler((Context)AppbrandContext.getInst().getApplicationContext())).b((u)new NetworkDebugInterceptor()).a(getOKHttpDispatcher()).a();
    sOkHttpStreamDownloadClient = (new y.a()).a(8000L, TimeUnit.MILLISECONDS).c(8000L, TimeUnit.MILLISECONDS).b(8000L, TimeUnit.MILLISECONDS).a((u)new PkgResponseInterceptor()).a(Collections.singletonList(aa.HTTP_1_1)).a(getOKHttpDispatcher()).a();
    mWhiteUrls = Arrays.asList(new String[] { "sgsnssdk.com", "snssdk.com", "toutiao.com", "wukong.com", "baohuaxia.com", "bytedance.net" });
    webViewWhiteUrls = Arrays.asList(new String[] { "ee.bytedance.net" });
    requestCookie = new ThreadLocal<String>();
  }
  
  public static n getOKHttpDispatcher() {
    // Byte code:
    //   0: ldc com/tt/miniapp/net/NetBus
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/net/NetBus.sDispatcher : Lokhttp3/n;
    //   6: ifnonnull -> 56
    //   9: new okhttp3/n
    //   12: dup
    //   13: new java/util/concurrent/ThreadPoolExecutor
    //   16: dup
    //   17: iconst_0
    //   18: ldc 2147483647
    //   20: ldc2_w 60
    //   23: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   26: new java/util/concurrent/SynchronousQueue
    //   29: dup
    //   30: invokespecial <init> : ()V
    //   33: ldc 'TmaOkHttp Dispatcher'
    //   35: iconst_0
    //   36: invokestatic a : (Ljava/lang/String;Z)Ljava/util/concurrent/ThreadFactory;
    //   39: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;)V
    //   42: invokespecial <init> : (Ljava/util/concurrent/ExecutorService;)V
    //   45: astore_0
    //   46: aload_0
    //   47: putstatic com/tt/miniapp/net/NetBus.sDispatcher : Lokhttp3/n;
    //   50: aload_0
    //   51: bipush #10
    //   53: invokevirtual a : (I)V
    //   56: getstatic com/tt/miniapp/net/NetBus.sDispatcher : Lokhttp3/n;
    //   59: astore_0
    //   60: ldc com/tt/miniapp/net/NetBus
    //   62: monitorexit
    //   63: aload_0
    //   64: areturn
    //   65: astore_0
    //   66: ldc com/tt/miniapp/net/NetBus
    //   68: monitorexit
    //   69: aload_0
    //   70: athrow
    // Exception table:
    //   from	to	target	type
    //   3	56	65	finally
    //   56	60	65	finally
  }
  
  public static boolean isWhiteUrl(String paramString) {
    Iterator<String> iterator = mWhiteUrls.iterator();
    while (iterator.hasNext()) {
      if (paramString.contains(iterator.next()))
        return true; 
    } 
    return false;
  }
  
  static class WebViewCookieHandler implements m {
    private CookieCache cache = (CookieCache)new SetCookieCache();
    
    private boolean isLoaded;
    
    private CookiePersistor persistor;
    
    public WebViewCookieHandler(Context param1Context) {
      if (param1Context != null)
        this.persistor = (CookiePersistor)new SharedPrefsCookiePersistor(param1Context); 
      ensureLoaded();
    }
    
    private void ensureLoaded() {
      if (this.isLoaded)
        return; 
      CookiePersistor cookiePersistor = this.persistor;
      if (cookiePersistor != null) {
        List list = cookiePersistor.loadAll();
        if (list != null) {
          if (list.size() <= 0)
            return; 
          this.isLoaded = true;
          this.cache.addAll(list);
        } 
      } 
    }
    
    private List<l> filterPersistentCookies(List<l> param1List) {
      ArrayList<l> arrayList = new ArrayList();
      for (l l : param1List) {
        if (l.h)
          arrayList.add(l); 
      } 
      return arrayList;
    }
    
    private static boolean isCookieExpired(l param1l) {
      return (param1l.c < System.currentTimeMillis());
    }
    
    private boolean isSystemWhiteUrl(String param1String) {
      return isWhiteUrl(param1String, (List<String>)HostDependManager.getInst().getHostData(2001, NetBus.webViewWhiteUrls));
    }
    
    private boolean isWhiteUrl(String param1String, List<String> param1List) {
      if (param1List != null) {
        Iterator<String> iterator = param1List.iterator();
        while (iterator.hasNext()) {
          if (param1String.contains(iterator.next()))
            return true; 
        } 
      } 
      return false;
    }
    
    public List<l> loadForRequest(t param1t) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: ldc 'tma_NetBus'
      //   4: iconst_2
      //   5: anewarray java/lang/Object
      //   8: dup
      //   9: iconst_0
      //   10: ldc 'loadForRequest '
      //   12: aastore
      //   13: dup
      //   14: iconst_1
      //   15: aload_1
      //   16: aastore
      //   17: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   20: aload_1
      //   21: invokevirtual toString : ()Ljava/lang/String;
      //   24: astore #7
      //   26: aload_0
      //   27: aload #7
      //   29: invokespecial isSystemWhiteUrl : (Ljava/lang/String;)Z
      //   32: istore #5
      //   34: aload_0
      //   35: aload #7
      //   37: getstatic com/tt/miniapp/net/NetBus.mWhiteUrls : Ljava/util/List;
      //   40: invokespecial isWhiteUrl : (Ljava/lang/String;Ljava/util/List;)Z
      //   43: istore #6
      //   45: new java/util/ArrayList
      //   48: dup
      //   49: invokespecial <init> : ()V
      //   52: astore #8
      //   54: new java/util/ArrayList
      //   57: dup
      //   58: invokespecial <init> : ()V
      //   61: astore #7
      //   63: aload_0
      //   64: invokespecial ensureLoaded : ()V
      //   67: aload_0
      //   68: getfield cache : Lcom/tt/miniapp/net/franmontiel/persistentcookiejar/cache/CookieCache;
      //   71: invokeinterface iterator : ()Ljava/util/Iterator;
      //   76: astore #9
      //   78: aload #9
      //   80: invokeinterface hasNext : ()Z
      //   85: ifeq -> 314
      //   88: aload #9
      //   90: invokeinterface next : ()Ljava/lang/Object;
      //   95: checkcast okhttp3/l
      //   98: astore #10
      //   100: aload #10
      //   102: invokestatic isCookieExpired : (Lokhttp3/l;)Z
      //   105: ifeq -> 128
      //   108: aload #8
      //   110: aload #10
      //   112: invokeinterface add : (Ljava/lang/Object;)Z
      //   117: pop
      //   118: aload #9
      //   120: invokeinterface remove : ()V
      //   125: goto -> 78
      //   128: aload #10
      //   130: getfield i : Z
      //   133: ifeq -> 153
      //   136: aload_1
      //   137: getfield d : Ljava/lang/String;
      //   140: aload #10
      //   142: getfield d : Ljava/lang/String;
      //   145: invokevirtual equals : (Ljava/lang/Object;)Z
      //   148: istore #4
      //   150: goto -> 436
      //   153: aload_1
      //   154: getfield d : Ljava/lang/String;
      //   157: aload #10
      //   159: getfield d : Ljava/lang/String;
      //   162: invokestatic a : (Ljava/lang/String;Ljava/lang/String;)Z
      //   165: istore #4
      //   167: goto -> 436
      //   170: aload #10
      //   172: getfield e : Ljava/lang/String;
      //   175: astore #11
      //   177: aload_1
      //   178: invokevirtual f : ()Ljava/lang/String;
      //   181: astore #12
      //   183: aload #12
      //   185: aload #11
      //   187: invokevirtual equals : (Ljava/lang/Object;)Z
      //   190: ifeq -> 196
      //   193: goto -> 446
      //   196: aload #12
      //   198: aload #11
      //   200: invokevirtual startsWith : (Ljava/lang/String;)Z
      //   203: ifeq -> 451
      //   206: aload #11
      //   208: ldc '/'
      //   210: invokevirtual endsWith : (Ljava/lang/String;)Z
      //   213: ifeq -> 219
      //   216: goto -> 446
      //   219: aload #12
      //   221: aload #11
      //   223: invokevirtual length : ()I
      //   226: invokevirtual charAt : (I)C
      //   229: bipush #47
      //   231: if_icmpne -> 451
      //   234: goto -> 446
      //   237: aload #10
      //   239: getfield f : Z
      //   242: ifeq -> 460
      //   245: aload_1
      //   246: invokevirtual c : ()Z
      //   249: ifne -> 460
      //   252: goto -> 441
      //   255: ldc 'tma_NetBus'
      //   257: iconst_2
      //   258: anewarray java/lang/Object
      //   261: dup
      //   262: iconst_0
      //   263: ldc 'loadForRequest currentCookie '
      //   265: aastore
      //   266: dup
      //   267: iconst_1
      //   268: aload #10
      //   270: aastore
      //   271: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   274: ldc 'tma_NetBus'
      //   276: iconst_2
      //   277: anewarray java/lang/Object
      //   280: dup
      //   281: iconst_0
      //   282: ldc 'loadForRequest getCookie '
      //   284: aastore
      //   285: dup
      //   286: iconst_1
      //   287: invokestatic getInstance : ()Landroid/webkit/CookieManager;
      //   290: aload_1
      //   291: invokevirtual toString : ()Ljava/lang/String;
      //   294: invokevirtual getCookie : (Ljava/lang/String;)Ljava/lang/String;
      //   297: aastore
      //   298: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   301: aload #7
      //   303: aload #10
      //   305: invokeinterface add : (Ljava/lang/Object;)Z
      //   310: pop
      //   311: goto -> 78
      //   314: aload_0
      //   315: getfield persistor : Lcom/tt/miniapp/net/franmontiel/persistentcookiejar/persistence/CookiePersistor;
      //   318: ifnull -> 332
      //   321: aload_0
      //   322: getfield persistor : Lcom/tt/miniapp/net/franmontiel/persistentcookiejar/persistence/CookiePersistor;
      //   325: aload #8
      //   327: invokeinterface removeAll : (Ljava/util/Collection;)V
      //   332: iload #6
      //   334: ifeq -> 401
      //   337: getstatic com/tt/miniapp/net/NetBus.requestCookie : Ljava/lang/ThreadLocal;
      //   340: invokevirtual get : ()Ljava/lang/Object;
      //   343: ifnull -> 401
      //   346: getstatic com/tt/miniapp/net/NetBus.requestCookie : Ljava/lang/ThreadLocal;
      //   349: invokevirtual get : ()Ljava/lang/Object;
      //   352: checkcast java/lang/String
      //   355: ldc ';'
      //   357: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
      //   360: astore #8
      //   362: aload #8
      //   364: arraylength
      //   365: istore_3
      //   366: iconst_0
      //   367: istore_2
      //   368: iload_2
      //   369: iload_3
      //   370: if_icmpge -> 401
      //   373: aload_1
      //   374: aload #8
      //   376: iload_2
      //   377: aaload
      //   378: invokestatic a : (Lokhttp3/t;Ljava/lang/String;)Lokhttp3/l;
      //   381: astore #9
      //   383: aload #9
      //   385: ifnull -> 479
      //   388: aload #7
      //   390: aload #9
      //   392: invokeinterface add : (Ljava/lang/Object;)Z
      //   397: pop
      //   398: goto -> 479
      //   401: ldc 'tma_NetBus'
      //   403: iconst_2
      //   404: anewarray java/lang/Object
      //   407: dup
      //   408: iconst_0
      //   409: ldc 'loadForRequest validCookies'
      //   411: aastore
      //   412: dup
      //   413: iconst_1
      //   414: aload #7
      //   416: aastore
      //   417: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   420: aload_0
      //   421: monitorexit
      //   422: aload #7
      //   424: areturn
      //   425: astore_1
      //   426: aload_0
      //   427: monitorexit
      //   428: goto -> 433
      //   431: aload_1
      //   432: athrow
      //   433: goto -> 431
      //   436: iload #4
      //   438: ifne -> 170
      //   441: iconst_0
      //   442: istore_2
      //   443: goto -> 462
      //   446: iconst_1
      //   447: istore_2
      //   448: goto -> 453
      //   451: iconst_0
      //   452: istore_2
      //   453: iload_2
      //   454: ifne -> 237
      //   457: goto -> 441
      //   460: iconst_1
      //   461: istore_2
      //   462: iload_2
      //   463: ifeq -> 78
      //   466: iload #6
      //   468: ifne -> 255
      //   471: iload #5
      //   473: ifeq -> 78
      //   476: goto -> 255
      //   479: iload_2
      //   480: iconst_1
      //   481: iadd
      //   482: istore_2
      //   483: goto -> 368
      // Exception table:
      //   from	to	target	type
      //   2	78	425	finally
      //   78	125	425	finally
      //   128	150	425	finally
      //   153	167	425	finally
      //   170	193	425	finally
      //   196	216	425	finally
      //   219	234	425	finally
      //   237	252	425	finally
      //   255	311	425	finally
      //   314	332	425	finally
      //   337	366	425	finally
      //   373	383	425	finally
      //   388	398	425	finally
      //   401	420	425	finally
    }
    
    public void saveFromResponse(t param1t, List<l> param1List) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: ldc 'tma_NetBus'
      //   4: iconst_2
      //   5: anewarray java/lang/Object
      //   8: dup
      //   9: iconst_0
      //   10: ldc 'saveFromResponse '
      //   12: aastore
      //   13: dup
      //   14: iconst_1
      //   15: aload_1
      //   16: aastore
      //   17: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   20: aload_0
      //   21: aload_1
      //   22: invokevirtual toString : ()Ljava/lang/String;
      //   25: invokespecial isSystemWhiteUrl : (Ljava/lang/String;)Z
      //   28: istore_3
      //   29: aload_0
      //   30: aload_1
      //   31: invokevirtual toString : ()Ljava/lang/String;
      //   34: getstatic com/tt/miniapp/net/NetBus.mWhiteUrls : Ljava/util/List;
      //   37: invokespecial isWhiteUrl : (Ljava/lang/String;Ljava/util/List;)Z
      //   40: istore #4
      //   42: aload_0
      //   43: getfield cache : Lcom/tt/miniapp/net/franmontiel/persistentcookiejar/cache/CookieCache;
      //   46: aload_2
      //   47: invokeinterface addAll : (Ljava/util/Collection;)V
      //   52: iload_3
      //   53: ifne -> 61
      //   56: iload #4
      //   58: ifeq -> 137
      //   61: aload_0
      //   62: getfield persistor : Lcom/tt/miniapp/net/franmontiel/persistentcookiejar/persistence/CookiePersistor;
      //   65: ifnull -> 82
      //   68: aload_0
      //   69: getfield persistor : Lcom/tt/miniapp/net/franmontiel/persistentcookiejar/persistence/CookiePersistor;
      //   72: aload_0
      //   73: aload_2
      //   74: invokespecial filterPersistentCookies : (Ljava/util/List;)Ljava/util/List;
      //   77: invokeinterface saveAll : (Ljava/util/Collection;)V
      //   82: invokestatic getInstance : ()Landroid/webkit/CookieManager;
      //   85: astore #5
      //   87: aload_2
      //   88: invokeinterface iterator : ()Ljava/util/Iterator;
      //   93: astore_2
      //   94: aload_2
      //   95: invokeinterface hasNext : ()Z
      //   100: ifeq -> 131
      //   103: aload_2
      //   104: invokeinterface next : ()Ljava/lang/Object;
      //   109: checkcast okhttp3/l
      //   112: astore #6
      //   114: aload #5
      //   116: aload_1
      //   117: invokevirtual toString : ()Ljava/lang/String;
      //   120: aload #6
      //   122: invokevirtual toString : ()Ljava/lang/String;
      //   125: invokevirtual setCookie : (Ljava/lang/String;Ljava/lang/String;)V
      //   128: goto -> 94
      //   131: invokestatic getInstance : ()Landroid/webkit/CookieManager;
      //   134: invokevirtual flush : ()V
      //   137: aload_0
      //   138: monitorexit
      //   139: return
      //   140: astore_1
      //   141: aload_0
      //   142: monitorexit
      //   143: goto -> 148
      //   146: aload_1
      //   147: athrow
      //   148: goto -> 146
      // Exception table:
      //   from	to	target	type
      //   2	52	140	finally
      //   61	82	140	finally
      //   82	94	140	finally
      //   94	128	140	finally
      //   131	137	140	finally
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\NetBus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */