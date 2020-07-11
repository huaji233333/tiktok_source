package com.facebook.react.modules.network;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.StandardCharsets;
import com.facebook.react.common.network.OkHttpCallUtil;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import okhttp3.ad;
import okhttp3.ae;
import okhttp3.af;
import okhttp3.e;
import okhttp3.f;
import okhttp3.m;
import okhttp3.s;
import okhttp3.u;
import okhttp3.v;
import okhttp3.w;
import okhttp3.x;
import okhttp3.y;

@ReactModule(name = "Networking")
public final class NetworkingModule extends ReactContextBaseJavaModule {
  public final y mClient;
  
  private final ForwardingCookieHandler mCookieHandler;
  
  private final CookieJarContainer mCookieJarContainer;
  
  private final String mDefaultUserAgent;
  
  private final List<RequestBodyHandler> mRequestBodyHandlers = new ArrayList<RequestBodyHandler>();
  
  private final Set<Integer> mRequestIds;
  
  public final List<ResponseHandler> mResponseHandlers = new ArrayList<ResponseHandler>();
  
  public boolean mShuttingDown;
  
  private final List<UriHandler> mUriHandlers = new ArrayList<UriHandler>();
  
  public NetworkingModule(ReactApplicationContext paramReactApplicationContext) {
    this(paramReactApplicationContext, null, OkHttpClientProvider.createClient(), null);
  }
  
  public NetworkingModule(ReactApplicationContext paramReactApplicationContext, String paramString) {
    this(paramReactApplicationContext, paramString, OkHttpClientProvider.createClient(), null);
  }
  
  NetworkingModule(ReactApplicationContext paramReactApplicationContext, String paramString, y paramy) {
    this(paramReactApplicationContext, paramString, paramy, null);
  }
  
  NetworkingModule(ReactApplicationContext paramReactApplicationContext, String paramString, y paramy, List<NetworkInterceptorCreator> paramList) {
    super(paramReactApplicationContext);
    y y1 = paramy;
    if (paramList != null) {
      y.a a = paramy.c();
      Iterator<NetworkInterceptorCreator> iterator = paramList.iterator();
      while (iterator.hasNext())
        a.b(((NetworkInterceptorCreator)iterator.next()).create()); 
      y1 = a.a();
    } 
    this.mClient = y1;
    this.mCookieHandler = new ForwardingCookieHandler((ReactContext)paramReactApplicationContext);
    this.mCookieJarContainer = (CookieJarContainer)this.mClient.k;
    this.mShuttingDown = false;
    this.mDefaultUserAgent = paramString;
    this.mRequestIds = new HashSet<Integer>();
  }
  
  public NetworkingModule(ReactApplicationContext paramReactApplicationContext, List<NetworkInterceptorCreator> paramList) {
    this(paramReactApplicationContext, null, OkHttpClientProvider.createClient(), paramList);
  }
  
  private void addRequest(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mRequestIds : Ljava/util/Set;
    //   6: iload_1
    //   7: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   10: invokeinterface add : (Ljava/lang/Object;)Z
    //   15: pop
    //   16: aload_0
    //   17: monitorexit
    //   18: return
    //   19: astore_2
    //   20: aload_0
    //   21: monitorexit
    //   22: aload_2
    //   23: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	19	finally
  }
  
  private void cancelAllRequests() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mRequestIds : Ljava/util/Set;
    //   6: invokeinterface iterator : ()Ljava/util/Iterator;
    //   11: astore_1
    //   12: aload_1
    //   13: invokeinterface hasNext : ()Z
    //   18: ifeq -> 40
    //   21: aload_0
    //   22: aload_1
    //   23: invokeinterface next : ()Ljava/lang/Object;
    //   28: checkcast java/lang/Integer
    //   31: invokevirtual intValue : ()I
    //   34: invokespecial cancelRequest : (I)V
    //   37: goto -> 12
    //   40: aload_0
    //   41: getfield mRequestIds : Ljava/util/Set;
    //   44: invokeinterface clear : ()V
    //   49: aload_0
    //   50: monitorexit
    //   51: return
    //   52: astore_1
    //   53: aload_0
    //   54: monitorexit
    //   55: goto -> 60
    //   58: aload_1
    //   59: athrow
    //   60: goto -> 58
    // Exception table:
    //   from	to	target	type
    //   2	12	52	finally
    //   12	37	52	finally
    //   40	49	52	finally
  }
  
  private void cancelRequest(final int requestId) {
    (new GuardedAsyncTask<Void, Void>((ReactContext)getReactApplicationContext()) {
        protected void doInBackgroundGuarded(Void... param1VarArgs) {
          OkHttpCallUtil.cancelTag(NetworkingModule.this.mClient, Integer.valueOf(requestId));
        }
      }).execute((Object[])new Void[0]);
  }
  
  private x.a constructMultipartBody(ReadableArray paramReadableArray, String paramString, int paramInt) {
    DeviceEventManagerModule.RCTDeviceEventEmitter rCTDeviceEventEmitter = getEventEmitter();
    x.a a = new x.a();
    a.a(w.a(paramString));
    int j = paramReadableArray.size();
    int i;
    for (i = 0; i < j; i++) {
      ReadableMap readableMap = paramReadableArray.getMap(i);
      s s = extractHeaders(readableMap.getArray("headers"), null);
      if (s == null) {
        ResponseUtil.onRequestError(rCTDeviceEventEmitter, paramInt, "Missing or invalid header format for FormData part.", null);
        return null;
      } 
      String str = s.a("content-type");
      if (str != null) {
        w w = w.a(str);
        s = s.d().b("content-type").a();
      } else {
        str = null;
      } 
      if (readableMap.hasKey("string")) {
        a.a(s, ad.create((w)str, readableMap.getString("string")));
      } else if (readableMap.hasKey("uri")) {
        if (str == null) {
          ResponseUtil.onRequestError(rCTDeviceEventEmitter, paramInt, "Binary FormData part needs a content-type header.", null);
          return null;
        } 
        String str1 = readableMap.getString("uri");
        InputStream inputStream = RequestBodyUtil.getFileInputStream((Context)getReactApplicationContext(), str1);
        if (inputStream == null) {
          StringBuilder stringBuilder = new StringBuilder("Could not retrieve file for uri ");
          stringBuilder.append(str1);
          ResponseUtil.onRequestError(rCTDeviceEventEmitter, paramInt, stringBuilder.toString(), null);
          return null;
        } 
        a.a(s, RequestBodyUtil.create((w)str, inputStream));
      } else {
        ResponseUtil.onRequestError(rCTDeviceEventEmitter, paramInt, "Unrecognized FormData part.", null);
      } 
    } 
    return a;
  }
  
  private s extractHeaders(ReadableArray paramReadableArray, ReadableMap paramReadableMap) {
    if (paramReadableArray == null)
      return null; 
    s.a a = new s.a();
    int j = paramReadableArray.size();
    boolean bool = false;
    int i = 0;
    while (i < j) {
      ReadableArray readableArray = paramReadableArray.getArray(i);
      if (readableArray != null) {
        if (readableArray.size() != 2)
          return null; 
        String str1 = readableArray.getString(0);
        String str2 = readableArray.getString(1);
        if (str1 != null) {
          if (str2 == null)
            return null; 
          a.a(str1, str2);
          i++;
          continue;
        } 
      } 
      return null;
    } 
    if (a.c("user-agent") == null) {
      String str = this.mDefaultUserAgent;
      if (str != null)
        a.a("user-agent", str); 
    } 
    i = bool;
    if (paramReadableMap != null) {
      i = bool;
      if (paramReadableMap.hasKey("string"))
        i = 1; 
    } 
    if (i == 0)
      a.b("content-encoding"); 
    return a.a();
  }
  
  private DeviceEventManagerModule.RCTDeviceEventEmitter getEventEmitter() {
    return (DeviceEventManagerModule.RCTDeviceEventEmitter)getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
  }
  
  public static boolean shouldDispatch(long paramLong1, long paramLong2) {
    return (paramLong2 + 100000000L < paramLong1);
  }
  
  public static WritableMap translateHeaders(s params) {
    WritableMap writableMap = Arguments.createMap();
    for (int i = 0; i < params.a(); i++) {
      String str = params.a(i);
      if (writableMap.hasKey(str)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(writableMap.getString(str));
        stringBuilder.append(", ");
        stringBuilder.append(params.b(i));
        writableMap.putString(str, stringBuilder.toString());
      } else {
        writableMap.putString(str, params.b(i));
      } 
    } 
    return writableMap;
  }
  
  private ad wrapRequestBodyWithProgressEmitter(ad paramad, final DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter, final int requestId) {
    return (paramad == null) ? null : RequestBodyUtil.createProgressRequest(paramad, new ProgressListener() {
          long last = System.nanoTime();
          
          public void onProgress(long param1Long1, long param1Long2, boolean param1Boolean) {
            long l = System.nanoTime();
            if (param1Boolean || NetworkingModule.shouldDispatch(l, this.last)) {
              ResponseUtil.onDataSend(eventEmitter, requestId, param1Long1, param1Long2);
              this.last = l;
            } 
          }
        });
  }
  
  @ReactMethod
  public final void abortRequest(int paramInt) {
    cancelRequest(paramInt);
    removeRequest(paramInt);
  }
  
  public final void addRequestBodyHandler(RequestBodyHandler paramRequestBodyHandler) {
    this.mRequestBodyHandlers.add(paramRequestBodyHandler);
  }
  
  public final void addResponseHandler(ResponseHandler paramResponseHandler) {
    this.mResponseHandlers.add(paramResponseHandler);
  }
  
  public final void addUriHandler(UriHandler paramUriHandler) {
    this.mUriHandlers.add(paramUriHandler);
  }
  
  @ReactMethod
  public final void clearCookies(Callback paramCallback) {
    this.mCookieHandler.clearCookies(paramCallback);
  }
  
  public final String getName() {
    return "Networking";
  }
  
  public final void initialize() {
    this.mCookieJarContainer.setCookieJar((m)new v(this.mCookieHandler));
  }
  
  public final void onCatalystInstanceDestroy() {
    this.mShuttingDown = true;
    cancelAllRequests();
    this.mCookieHandler.destroy();
    this.mCookieJarContainer.removeCookieJar();
    this.mRequestBodyHandlers.clear();
    this.mResponseHandlers.clear();
    this.mUriHandlers.clear();
  }
  
  public final void readWithProgress(DeviceEventManagerModule.RCTDeviceEventEmitter paramRCTDeviceEventEmitter, int paramInt, af paramaf) throws IOException {
    long l2;
    Charset charset;
    long l1 = -1L;
    try {
      ProgressResponseBody progressResponseBody = (ProgressResponseBody)paramaf;
      l2 = progressResponseBody.totalBytesRead();
      try {
        long l = progressResponseBody.contentLength();
        l1 = l;
      } catch (ClassCastException classCastException) {}
    } catch (ClassCastException classCastException) {
      l2 = -1L;
    } 
    if (paramaf.contentType() == null) {
      charset = StandardCharsets.UTF_8;
    } else {
      charset = paramaf.contentType().a(StandardCharsets.UTF_8);
    } 
    ProgressiveStringDecoder progressiveStringDecoder = new ProgressiveStringDecoder(charset);
    InputStream inputStream = paramaf.byteStream();
    try {
      byte[] arrayOfByte = new byte[8192];
      while (true) {
        int i = inputStream.read(arrayOfByte);
        if (i != -1) {
          ResponseUtil.onIncrementalDataReceived(paramRCTDeviceEventEmitter, paramInt, progressiveStringDecoder.decodeNext(arrayOfByte, i), l2, l1);
          continue;
        } 
        return;
      } 
    } finally {
      inputStream.close();
    } 
  }
  
  public final void removeRequest(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mRequestIds : Ljava/util/Set;
    //   6: iload_1
    //   7: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   10: invokeinterface remove : (Ljava/lang/Object;)Z
    //   15: pop
    //   16: aload_0
    //   17: monitorexit
    //   18: return
    //   19: astore_2
    //   20: aload_0
    //   21: monitorexit
    //   22: aload_2
    //   23: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	19	finally
  }
  
  public final void removeRequestBodyHandler(RequestBodyHandler paramRequestBodyHandler) {
    this.mRequestBodyHandlers.remove(paramRequestBodyHandler);
  }
  
  public final void removeResponseHandler(ResponseHandler paramResponseHandler) {
    this.mResponseHandlers.remove(paramResponseHandler);
  }
  
  public final void removeUriHandler(UriHandler paramUriHandler) {
    this.mUriHandlers.remove(paramUriHandler);
  }
  
  @ReactMethod
  public final void sendRequest(String paramString1, String paramString2, int paramInt1, ReadableArray paramReadableArray, ReadableMap paramReadableMap, String paramString3, boolean paramBoolean1, int paramInt2, boolean paramBoolean2) {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial getEventEmitter : ()Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;
    //   4: astore #10
    //   6: aload_2
    //   7: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   10: astore #11
    //   12: aload_0
    //   13: getfield mUriHandlers : Ljava/util/List;
    //   16: invokeinterface iterator : ()Ljava/util/Iterator;
    //   21: astore #12
    //   23: aload #12
    //   25: invokeinterface hasNext : ()Z
    //   30: ifeq -> 81
    //   33: aload #12
    //   35: invokeinterface next : ()Ljava/lang/Object;
    //   40: checkcast com/facebook/react/modules/network/NetworkingModule$UriHandler
    //   43: astore #13
    //   45: aload #13
    //   47: aload #11
    //   49: aload #6
    //   51: invokeinterface supports : (Landroid/net/Uri;Ljava/lang/String;)Z
    //   56: ifeq -> 23
    //   59: aload #10
    //   61: iload_3
    //   62: aload #13
    //   64: aload #11
    //   66: invokeinterface fetch : (Landroid/net/Uri;)Lcom/facebook/react/bridge/WritableMap;
    //   71: invokestatic onDataReceived : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;ILcom/facebook/react/bridge/WritableMap;)V
    //   74: aload #10
    //   76: iload_3
    //   77: invokestatic onRequestSuccess : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;I)V
    //   80: return
    //   81: new okhttp3/ac$a
    //   84: dup
    //   85: invokespecial <init> : ()V
    //   88: aload_2
    //   89: invokevirtual a : (Ljava/lang/String;)Lokhttp3/ac$a;
    //   92: astore #11
    //   94: iload_3
    //   95: ifeq -> 108
    //   98: aload #11
    //   100: iload_3
    //   101: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   104: invokevirtual a : (Ljava/lang/Object;)Lokhttp3/ac$a;
    //   107: pop
    //   108: aload_0
    //   109: getfield mClient : Lokhttp3/y;
    //   112: invokevirtual c : ()Lokhttp3/y$a;
    //   115: astore_2
    //   116: iload #9
    //   118: ifne -> 129
    //   121: aload_2
    //   122: getstatic okhttp3/m.a : Lokhttp3/m;
    //   125: invokevirtual a : (Lokhttp3/m;)Lokhttp3/y$a;
    //   128: pop
    //   129: iload #7
    //   131: ifeq -> 152
    //   134: aload_2
    //   135: new com/facebook/react/modules/network/NetworkingModule$1
    //   138: dup
    //   139: aload_0
    //   140: aload #6
    //   142: aload #10
    //   144: iload_3
    //   145: invokespecial <init> : (Lcom/facebook/react/modules/network/NetworkingModule;Ljava/lang/String;Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;I)V
    //   148: invokevirtual b : (Lokhttp3/u;)Lokhttp3/y$a;
    //   151: pop
    //   152: iload #8
    //   154: aload_0
    //   155: getfield mClient : Lokhttp3/y;
    //   158: getfield z : I
    //   161: if_icmpeq -> 175
    //   164: aload_2
    //   165: iload #8
    //   167: i2l
    //   168: getstatic java/util/concurrent/TimeUnit.MILLISECONDS : Ljava/util/concurrent/TimeUnit;
    //   171: invokevirtual b : (JLjava/util/concurrent/TimeUnit;)Lokhttp3/y$a;
    //   174: pop
    //   175: aload_2
    //   176: invokevirtual a : ()Lokhttp3/y;
    //   179: astore #12
    //   181: aload_0
    //   182: aload #4
    //   184: aload #5
    //   186: invokespecial extractHeaders : (Lcom/facebook/react/bridge/ReadableArray;Lcom/facebook/react/bridge/ReadableMap;)Lokhttp3/s;
    //   189: astore_2
    //   190: aload_2
    //   191: ifnonnull -> 205
    //   194: aload #10
    //   196: iload_3
    //   197: ldc_w 'Unrecognized headers format'
    //   200: aconst_null
    //   201: invokestatic onRequestError : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;ILjava/lang/String;Ljava/io/IOException;)V
    //   204: return
    //   205: aload_2
    //   206: ldc 'content-type'
    //   208: invokevirtual a : (Ljava/lang/String;)Ljava/lang/String;
    //   211: astore #4
    //   213: aload_2
    //   214: ldc_w 'content-encoding'
    //   217: invokevirtual a : (Ljava/lang/String;)Ljava/lang/String;
    //   220: astore #13
    //   222: aload #11
    //   224: aload_2
    //   225: invokevirtual a : (Lokhttp3/s;)Lokhttp3/ac$a;
    //   228: pop
    //   229: aload #5
    //   231: ifnull -> 280
    //   234: aload_0
    //   235: getfield mRequestBodyHandlers : Ljava/util/List;
    //   238: invokeinterface iterator : ()Ljava/util/Iterator;
    //   243: astore #14
    //   245: aload #14
    //   247: invokeinterface hasNext : ()Z
    //   252: ifeq -> 280
    //   255: aload #14
    //   257: invokeinterface next : ()Ljava/lang/Object;
    //   262: checkcast com/facebook/react/modules/network/NetworkingModule$RequestBodyHandler
    //   265: astore_2
    //   266: aload_2
    //   267: aload #5
    //   269: invokeinterface supports : (Lcom/facebook/react/bridge/ReadableMap;)Z
    //   274: ifeq -> 245
    //   277: goto -> 282
    //   280: aconst_null
    //   281: astore_2
    //   282: aload #5
    //   284: ifnull -> 641
    //   287: aload_1
    //   288: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   291: ldc_w 'get'
    //   294: invokevirtual equals : (Ljava/lang/Object;)Z
    //   297: ifne -> 641
    //   300: aload_1
    //   301: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   304: ldc_w 'head'
    //   307: invokevirtual equals : (Ljava/lang/Object;)Z
    //   310: ifeq -> 316
    //   313: goto -> 641
    //   316: aload_2
    //   317: ifnull -> 334
    //   320: aload_2
    //   321: aload #5
    //   323: aload #4
    //   325: invokeinterface toRequestBody : (Lcom/facebook/react/bridge/ReadableMap;Ljava/lang/String;)Lokhttp3/ad;
    //   330: astore_2
    //   331: goto -> 646
    //   334: aload #5
    //   336: ldc 'string'
    //   338: invokeinterface hasKey : (Ljava/lang/String;)Z
    //   343: ifeq -> 424
    //   346: aload #4
    //   348: ifnonnull -> 362
    //   351: aload #10
    //   353: iload_3
    //   354: ldc_w 'Payload is set but no content-type header specified'
    //   357: aconst_null
    //   358: invokestatic onRequestError : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;ILjava/lang/String;Ljava/io/IOException;)V
    //   361: return
    //   362: aload #5
    //   364: ldc 'string'
    //   366: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
    //   371: astore_2
    //   372: aload #4
    //   374: invokestatic a : (Ljava/lang/String;)Lokhttp3/w;
    //   377: astore #4
    //   379: aload #13
    //   381: invokestatic isGzipEncoding : (Ljava/lang/String;)Z
    //   384: ifeq -> 414
    //   387: aload #4
    //   389: aload_2
    //   390: invokestatic createGzip : (Lokhttp3/w;Ljava/lang/String;)Lokhttp3/ad;
    //   393: astore #4
    //   395: aload #4
    //   397: astore_2
    //   398: aload #4
    //   400: ifnonnull -> 646
    //   403: aload #10
    //   405: iload_3
    //   406: ldc_w 'Failed to gzip request body'
    //   409: aconst_null
    //   410: invokestatic onRequestError : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;ILjava/lang/String;Ljava/io/IOException;)V
    //   413: return
    //   414: aload #4
    //   416: aload_2
    //   417: invokestatic create : (Lokhttp3/w;Ljava/lang/String;)Lokhttp3/ad;
    //   420: astore_2
    //   421: goto -> 646
    //   424: aload #5
    //   426: ldc_w 'base64'
    //   429: invokeinterface hasKey : (Ljava/lang/String;)Z
    //   434: ifeq -> 480
    //   437: aload #4
    //   439: ifnonnull -> 453
    //   442: aload #10
    //   444: iload_3
    //   445: ldc_w 'Payload is set but no content-type header specified'
    //   448: aconst_null
    //   449: invokestatic onRequestError : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;ILjava/lang/String;Ljava/io/IOException;)V
    //   452: return
    //   453: aload #5
    //   455: ldc_w 'base64'
    //   458: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
    //   463: astore_2
    //   464: aload #4
    //   466: invokestatic a : (Ljava/lang/String;)Lokhttp3/w;
    //   469: aload_2
    //   470: invokestatic decodeBase64 : (Ljava/lang/String;)Lg/i;
    //   473: invokestatic create : (Lokhttp3/w;Lg/i;)Lokhttp3/ad;
    //   476: astore_2
    //   477: goto -> 646
    //   480: aload #5
    //   482: ldc_w 'uri'
    //   485: invokeinterface hasKey : (Ljava/lang/String;)Z
    //   490: ifeq -> 578
    //   493: aload #4
    //   495: ifnonnull -> 509
    //   498: aload #10
    //   500: iload_3
    //   501: ldc_w 'Payload is set but no content-type header specified'
    //   504: aconst_null
    //   505: invokestatic onRequestError : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;ILjava/lang/String;Ljava/io/IOException;)V
    //   508: return
    //   509: aload #5
    //   511: ldc_w 'uri'
    //   514: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
    //   519: astore_2
    //   520: aload_0
    //   521: invokevirtual getReactApplicationContext : ()Lcom/facebook/react/bridge/ReactApplicationContext;
    //   524: aload_2
    //   525: invokestatic getFileInputStream : (Landroid/content/Context;Ljava/lang/String;)Ljava/io/InputStream;
    //   528: astore #5
    //   530: aload #5
    //   532: ifnonnull -> 564
    //   535: new java/lang/StringBuilder
    //   538: dup
    //   539: ldc_w 'Could not retrieve file for uri '
    //   542: invokespecial <init> : (Ljava/lang/String;)V
    //   545: astore_1
    //   546: aload_1
    //   547: aload_2
    //   548: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   551: pop
    //   552: aload #10
    //   554: iload_3
    //   555: aload_1
    //   556: invokevirtual toString : ()Ljava/lang/String;
    //   559: aconst_null
    //   560: invokestatic onRequestError : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;ILjava/lang/String;Ljava/io/IOException;)V
    //   563: return
    //   564: aload #4
    //   566: invokestatic a : (Ljava/lang/String;)Lokhttp3/w;
    //   569: aload #5
    //   571: invokestatic create : (Lokhttp3/w;Ljava/io/InputStream;)Lokhttp3/ad;
    //   574: astore_2
    //   575: goto -> 646
    //   578: aload #5
    //   580: ldc_w 'formData'
    //   583: invokeinterface hasKey : (Ljava/lang/String;)Z
    //   588: ifeq -> 633
    //   591: aload #4
    //   593: astore_2
    //   594: aload #4
    //   596: ifnonnull -> 603
    //   599: ldc_w 'multipart/form-data'
    //   602: astore_2
    //   603: aload_0
    //   604: aload #5
    //   606: ldc_w 'formData'
    //   609: invokeinterface getArray : (Ljava/lang/String;)Lcom/facebook/react/bridge/ReadableArray;
    //   614: aload_2
    //   615: iload_3
    //   616: invokespecial constructMultipartBody : (Lcom/facebook/react/bridge/ReadableArray;Ljava/lang/String;I)Lokhttp3/x$a;
    //   619: astore_2
    //   620: aload_2
    //   621: ifnonnull -> 625
    //   624: return
    //   625: aload_2
    //   626: invokevirtual a : ()Lokhttp3/x;
    //   629: astore_2
    //   630: goto -> 646
    //   633: aload_1
    //   634: invokestatic getEmptyBody : (Ljava/lang/String;)Lokhttp3/ad;
    //   637: astore_2
    //   638: goto -> 646
    //   641: aload_1
    //   642: invokestatic getEmptyBody : (Ljava/lang/String;)Lokhttp3/ad;
    //   645: astore_2
    //   646: aload #11
    //   648: aload_1
    //   649: aload_0
    //   650: aload_2
    //   651: aload #10
    //   653: iload_3
    //   654: invokespecial wrapRequestBodyWithProgressEmitter : (Lokhttp3/ad;Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;I)Lokhttp3/ad;
    //   657: invokevirtual a : (Ljava/lang/String;Lokhttp3/ad;)Lokhttp3/ac$a;
    //   660: pop
    //   661: aload_0
    //   662: iload_3
    //   663: invokespecial addRequest : (I)V
    //   666: aload #12
    //   668: aload #11
    //   670: invokevirtual c : ()Lokhttp3/ac;
    //   673: invokevirtual a : (Lokhttp3/ac;)Lokhttp3/e;
    //   676: new com/facebook/react/modules/network/NetworkingModule$2
    //   679: dup
    //   680: aload_0
    //   681: iload_3
    //   682: aload #10
    //   684: aload #6
    //   686: iload #7
    //   688: invokespecial <init> : (Lcom/facebook/react/modules/network/NetworkingModule;ILcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;Ljava/lang/String;Z)V
    //   691: invokeinterface a : (Lokhttp3/f;)V
    //   696: return
    //   697: astore_1
    //   698: aload #10
    //   700: iload_3
    //   701: aload_1
    //   702: invokevirtual getMessage : ()Ljava/lang/String;
    //   705: aconst_null
    //   706: invokestatic onRequestError : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;ILjava/lang/String;Ljava/io/IOException;)V
    //   709: return
    //   710: astore_1
    //   711: aload #10
    //   713: iload_3
    //   714: aload_1
    //   715: invokevirtual getMessage : ()Ljava/lang/String;
    //   718: aload_1
    //   719: invokestatic onRequestError : (Lcom/facebook/react/modules/core/DeviceEventManagerModule$RCTDeviceEventEmitter;ILjava/lang/String;Ljava/io/IOException;)V
    //   722: return
    // Exception table:
    //   from	to	target	type
    //   6	23	710	java/io/IOException
    //   23	80	710	java/io/IOException
    //   81	94	697	java/lang/Exception
  }
  
  public static interface RequestBodyHandler {
    boolean supports(ReadableMap param1ReadableMap);
    
    ad toRequestBody(ReadableMap param1ReadableMap, String param1String);
  }
  
  public static interface ResponseHandler {
    boolean supports(String param1String);
    
    WritableMap toResponseData(af param1af) throws IOException;
  }
  
  public static interface UriHandler {
    WritableMap fetch(Uri param1Uri) throws IOException;
    
    boolean supports(Uri param1Uri, String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\NetworkingModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */