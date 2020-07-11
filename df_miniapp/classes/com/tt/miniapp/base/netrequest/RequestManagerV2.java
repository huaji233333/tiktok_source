package com.tt.miniapp.base.netrequest;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.bytedance.sandboxapp.protocol.service.d.a;
import com.bytedance.sandboxapp.protocol.service.request.entity.HttpRequest;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.RequestInceptUtil;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.manager.PreTTRequestManager;
import com.tt.miniapp.net.NetBus;
import com.tt.miniapp.process.ServiceBindManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.StringUtils;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.option.q.b;
import com.tt.option.q.d;
import com.tt.option.q.h;
import com.tt.option.q.i;
import com.tt.option.q.j;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.ac;
import okhttp3.ad;
import okhttp3.e;
import okhttp3.w;
import org.json.JSONObject;

public class RequestManagerV2 {
  public static ConcurrentHashMap<String, Long> requestedDomains = new ConcurrentHashMap<String, Long>();
  
  private Boolean isInnerApp = null;
  
  public SparseArray<b> requests = new SparseArray();
  
  private Vector<Integer> taskIds = new Vector<Integer>();
  
  private RequestManagerV2() {}
  
  private void addToRequests(int paramInt, i parami) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield requests : Landroid/util/SparseArray;
    //   6: iload_1
    //   7: aload_2
    //   8: invokevirtual put : (ILjava/lang/Object;)V
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: astore_2
    //   15: aload_0
    //   16: monitorexit
    //   17: aload_2
    //   18: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	14	finally
  }
  
  private String getDataString(HashMap<String, String> paramHashMap) throws UnsupportedEncodingException {
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<Map.Entry> iterator = paramHashMap.entrySet().iterator();
    boolean bool = true;
    while (iterator.hasNext()) {
      Map.Entry entry = iterator.next();
      if (bool) {
        bool = false;
      } else {
        stringBuilder.append("&");
      } 
      stringBuilder.append(URLEncoder.encode((String)entry.getKey(), "UTF-8"));
      stringBuilder.append("=");
      stringBuilder.append(URLEncoder.encode((String)entry.getValue(), "UTF-8"));
    } 
    return stringBuilder.toString();
  }
  
  public static RequestManagerV2 getInst() {
    return Holder.requestManager;
  }
  
  private String getMpId() {
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    return (appInfoEntity != null) ? ((appInfoEntity.appId == null) ? "" : appInfoEntity.appId) : "";
  }
  
  private long getRequestTimeout() {
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    return (appConfig != null) ? (appConfig.getNetworkTimeout()).request : 60000L;
  }
  
  private boolean isInnerApp() {
    if (this.isInnerApp == null) {
      Application application = AppbrandContext.getInst().getApplicationContext();
      Settings settings = Settings.TT_TMA_PROXY_LIST;
      boolean bool2 = false;
      List list = SettingsDAO.getListString((Context)application, new Enum[] { (Enum)settings, (Enum)Settings.TmaProxyList.APP_LIST });
      String str = getMpId();
      boolean bool1 = bool2;
      if (!TextUtils.isEmpty(str)) {
        bool1 = bool2;
        if (list.contains(str))
          bool1 = true; 
      } 
      this.isInnerApp = Boolean.valueOf(bool1);
    } 
    return this.isInnerApp.booleanValue();
  }
  
  private e makeCall(boolean paramBoolean, ac.a parama, long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic com/tt/miniapp/net/NetBus.okHttpClient : Lokhttp3/y;
    //   5: invokevirtual c : ()Lokhttp3/y$a;
    //   8: lload_3
    //   9: getstatic java/util/concurrent/TimeUnit.MILLISECONDS : Ljava/util/concurrent/TimeUnit;
    //   12: invokevirtual a : (JLjava/util/concurrent/TimeUnit;)Lokhttp3/y$a;
    //   15: lload_3
    //   16: getstatic java/util/concurrent/TimeUnit.MILLISECONDS : Ljava/util/concurrent/TimeUnit;
    //   19: invokevirtual c : (JLjava/util/concurrent/TimeUnit;)Lokhttp3/y$a;
    //   22: lload_3
    //   23: getstatic java/util/concurrent/TimeUnit.MILLISECONDS : Ljava/util/concurrent/TimeUnit;
    //   26: invokevirtual b : (JLjava/util/concurrent/TimeUnit;)Lokhttp3/y$a;
    //   29: astore #5
    //   31: iload_1
    //   32: ifeq -> 44
    //   35: aload #5
    //   37: invokestatic getInstance : ()Lcom/tt/miniapp/net/httpdns/TTHttpDns;
    //   40: invokevirtual a : (Lokhttp3/o;)Lokhttp3/y$a;
    //   43: pop
    //   44: aload #5
    //   46: invokevirtual a : ()Lokhttp3/y;
    //   49: aload_2
    //   50: invokevirtual c : ()Lokhttp3/ac;
    //   53: invokevirtual a : (Lokhttp3/ac;)Lokhttp3/e;
    //   56: astore_2
    //   57: aload_0
    //   58: monitorexit
    //   59: aload_2
    //   60: areturn
    //   61: astore_2
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_2
    //   65: athrow
    // Exception table:
    //   from	to	target	type
    //   2	31	61	finally
    //   35	44	61	finally
    //   44	57	61	finally
  }
  
  private j requestWithOkHttp(i parami, boolean paramBoolean) {
    j j = new j();
    try {
      String str1 = parami.f();
      ac.a a = new ac.a();
      a.a(str1);
      String str2 = parami.c;
      boolean bool = str2.equals("GET");
      int k = 0;
      if (!bool) {
        str1 = parami.e();
        if (str1 != null) {
          w w = w.a(str1);
        } else {
          str1 = null;
        } 
        byte[] arrayOfByte2 = parami.i;
        byte[] arrayOfByte1 = arrayOfByte2;
        if (arrayOfByte2 == null)
          arrayOfByte1 = new byte[0]; 
        a.a(str2, ad.create((w)str1, arrayOfByte1));
      } 
      for (Map.Entry entry : parami.f.entrySet()) {
        String str3 = (String)entry.getKey();
        String str4 = (String)entry.getValue();
        a.b(str3, str4);
        if (str3.equalsIgnoreCase("cookie") && !TextUtils.isEmpty(str4))
          NetBus.requestCookie.set(str4); 
      } 
      e e = makeCall(paramBoolean, a, parami.l);
    } finally {
      parami = null;
      j.f = (Throwable)parami;
    } 
    NetBus.requestCookie.remove();
    return j;
  }
  
  private Map<String, String> setupHeaders(String paramString, JSONObject paramJSONObject, boolean paramBoolean1, boolean paramBoolean2) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    List<Header> list = parseHeader(paramJSONObject);
    boolean bool1 = NetBus.isWhiteUrl(paramString);
    boolean bool = false;
    if (paramBoolean2 || (bool1 && ProcessUtil.isMiniappProcess() && (AppbrandApplication.getInst().getAppInfo()).innertype == 1)) {
      paramString = HostProcessBridge.getLoginCookie();
      AppBrandLogger.d("RequestManagerV2", new Object[] { "cookie ", paramString });
    } else {
      paramString = null;
    } 
    for (Header header : list) {
      if (!header.key.equalsIgnoreCase("User-Agent") && !header.key.equalsIgnoreCase("Referer")) {
        if (bool1 && header.key.equalsIgnoreCase("Cookie")) {
          String str = header.value;
          if (TextUtils.isEmpty(str)) {
            if (!TextUtils.isEmpty(paramString))
              hashMap.put(header.key, paramString); 
          } else {
            if (!TextUtils.isEmpty(paramString)) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(str);
              stringBuilder.append("; ");
              stringBuilder.append(paramString);
              str = stringBuilder.toString();
            } 
            if (!TextUtils.isEmpty(str))
              hashMap.put(header.key, str); 
          } 
          bool = true;
          continue;
        } 
        hashMap.put(header.key, header.value);
      } 
    } 
    if (!bool && bool1 && !TextUtils.isEmpty(paramString))
      hashMap.put("Cookie", paramString); 
    hashMap.put("User-Agent", ToolUtils.getCustomUA());
    hashMap.put("referer", RequestInceptUtil.getRequestReferer());
    if (paramBoolean1) {
      a a = (a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class);
      if (a != null)
        hashMap.putAll(a.getRequestHeader()); 
    } 
    return (Map)hashMap;
  }
  
  public void addRequest(final HttpRequest.RequestTask requestTask, final HttpRequest.a callback) {
    this.taskIds.add(Integer.valueOf(requestTask.a));
    final TimeMeter beginRequestTime = TimeMeter.newAndStart();
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            // Byte code:
            //   0: ldc 'RequestManagerV2'
            //   2: iconst_2
            //   3: anewarray java/lang/Object
            //   6: dup
            //   7: iconst_0
            //   8: ldc 'request:'
            //   10: aastore
            //   11: dup
            //   12: iconst_1
            //   13: aload_0
            //   14: getfield val$requestTask : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestTask;
            //   17: aastore
            //   18: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
            //   21: aload_0
            //   22: getfield val$requestTask : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestTask;
            //   25: getfield b : Ljava/lang/String;
            //   28: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
            //   31: invokevirtual getHost : ()Ljava/lang/String;
            //   34: astore #4
            //   36: getstatic com/tt/miniapp/base/netrequest/RequestManagerV2.requestedDomains : Ljava/util/concurrent/ConcurrentHashMap;
            //   39: aload #4
            //   41: invokevirtual containsKey : (Ljava/lang/Object;)Z
            //   44: istore_1
            //   45: iload_1
            //   46: iconst_1
            //   47: ixor
            //   48: istore_1
            //   49: iload_1
            //   50: ifeq -> 66
            //   53: getstatic com/tt/miniapp/base/netrequest/RequestManagerV2.requestedDomains : Ljava/util/concurrent/ConcurrentHashMap;
            //   56: aload #4
            //   58: lconst_0
            //   59: invokestatic valueOf : (J)Ljava/lang/Long;
            //   62: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
            //   65: pop
            //   66: aload_0
            //   67: getfield this$0 : Lcom/tt/miniapp/base/netrequest/RequestManagerV2;
            //   70: aload_0
            //   71: getfield val$requestType : Ljava/lang/String;
            //   74: aload_0
            //   75: getfield val$requestTask : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestTask;
            //   78: invokevirtual requestSync : (Ljava/lang/String;Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestTask;)Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestResult;
            //   81: astore #4
            //   83: aload_0
            //   84: getfield val$beginRequestTime : Lcom/tt/miniapphost/util/TimeMeter;
            //   87: invokevirtual stop : ()J
            //   90: lstore_2
            //   91: aload_0
            //   92: getfield this$0 : Lcom/tt/miniapp/base/netrequest/RequestManagerV2;
            //   95: getfield requests : Landroid/util/SparseArray;
            //   98: aload_0
            //   99: getfield val$requestTask : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestTask;
            //   102: getfield a : I
            //   105: invokevirtual get : (I)Ljava/lang/Object;
            //   108: checkcast com/tt/option/q/b
            //   111: astore #5
            //   113: aload #5
            //   115: ifnull -> 157
            //   118: aload #5
            //   120: invokeinterface b : ()Z
            //   125: ifeq -> 157
            //   128: aload_0
            //   129: getfield val$callback : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$a;
            //   132: aload_0
            //   133: getfield val$requestTask : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestTask;
            //   136: invokeinterface onRequestAbort : (Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestTask;)V
            //   141: aload #4
            //   143: iconst_0
            //   144: putfield b : Z
            //   147: aload #4
            //   149: ldc 'abort'
            //   151: putfield h : Ljava/lang/String;
            //   154: goto -> 168
            //   157: aload_0
            //   158: getfield val$callback : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$a;
            //   161: aload #4
            //   163: invokeinterface onRequestFinish : (Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestResult;)V
            //   168: goto -> 251
            //   171: astore #4
            //   173: goto -> 180
            //   176: astore #4
            //   178: iconst_0
            //   179: istore_1
            //   180: ldc 'RequestManagerV2'
            //   182: iconst_2
            //   183: anewarray java/lang/Object
            //   186: dup
            //   187: iconst_0
            //   188: ldc 'addRequest'
            //   190: aastore
            //   191: dup
            //   192: iconst_1
            //   193: aload #4
            //   195: aastore
            //   196: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
            //   199: aload_0
            //   200: getfield val$beginRequestTime : Lcom/tt/miniapphost/util/TimeMeter;
            //   203: invokevirtual stop : ()J
            //   206: lstore_2
            //   207: new com/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestResult
            //   210: dup
            //   211: aload_0
            //   212: getfield val$requestTask : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestTask;
            //   215: getfield a : I
            //   218: invokespecial <init> : (I)V
            //   221: astore #5
            //   223: aload #5
            //   225: iconst_0
            //   226: putfield b : Z
            //   229: aload #5
            //   231: aload #4
            //   233: putfield i : Ljava/lang/Throwable;
            //   236: aload_0
            //   237: getfield val$callback : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$a;
            //   240: aload #5
            //   242: invokeinterface onRequestFinish : (Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestResult;)V
            //   247: aload #5
            //   249: astore #4
            //   251: aload_0
            //   252: getfield this$0 : Lcom/tt/miniapp/base/netrequest/RequestManagerV2;
            //   255: aload #4
            //   257: getfield b : Z
            //   260: iload_1
            //   261: aload_0
            //   262: getfield val$requestType : Ljava/lang/String;
            //   265: lload_2
            //   266: aload_0
            //   267: getfield val$requestTask : Lcom/bytedance/sandboxapp/protocol/service/request/entity/HttpRequest$RequestTask;
            //   270: getfield b : Ljava/lang/String;
            //   273: aload #4
            //   275: getfield h : Ljava/lang/String;
            //   278: aload #4
            //   280: getfield i : Ljava/lang/Throwable;
            //   283: invokevirtual reportTTRequestResult : (ZZLjava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
            //   286: return
            // Exception table:
            //   from	to	target	type
            //   0	45	176	finally
            //   53	66	171	finally
            //   66	113	171	finally
            //   118	154	171	finally
            //   157	168	171	finally
          }
        },  Schedulers.longIO());
  }
  
  public HttpRequest.RequestResult convertToRequestResult(j paramj, int paramInt, String paramString) throws Exception {
    HttpRequest.RequestResult requestResult;
    JSONObject jSONObject = new JSONObject();
    ArrayList<h> arrayList = paramj.b();
    if (arrayList != null) {
      if (arrayList.isEmpty() && paramj.b >= 400)
        arrayList.add(new h("code", String.valueOf(paramj.b))); 
      for (h h : arrayList) {
        String str1 = h.a;
        String str2 = h.b;
        if (jSONObject.has(str1)) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(jSONObject.optString(str1));
          stringBuilder.append(",");
          stringBuilder.append(str2);
          jSONObject.put(str1, stringBuilder.toString());
          continue;
        } 
        jSONObject.put(str1, str2);
      } 
    } 
    if (paramj.f != null) {
      if (paramj.b >= 400) {
        requestResult = new HttpRequest.RequestResult(paramInt, true, paramj.b, paramj.a(), jSONObject, paramString);
      } else {
        requestResult = new HttpRequest.RequestResult(paramInt, false, paramj.b, "", jSONObject, (String)requestResult);
      } 
      requestResult.h = paramj.c;
      return requestResult;
    } 
    if (paramj.e != null) {
      byte[] arrayOfByte = paramj.e;
    } else if (paramj.d != null) {
      byte[] arrayOfByte = paramj.d.getBytes();
    } else {
      arrayList = null;
    } 
    if (arrayList != null) {
      if (TextUtils.equals((CharSequence)requestResult, "arraybuffer"))
        return new HttpRequest.RequestResult(paramInt, true, paramj.b, (byte[])arrayList, jSONObject, (String)requestResult); 
      String str = StringUtils.newString((byte[])arrayList);
      return new HttpRequest.RequestResult(paramInt, true, paramj.b, str, jSONObject, (String)requestResult);
    } 
    return new HttpRequest.RequestResult(paramInt, true, paramj.b, "", jSONObject, (String)requestResult);
  }
  
  public j doRequest(i parami, String paramString) {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual hashCode : ()I
    //   4: istore_3
    //   5: iload_3
    //   6: ldc_w 110693149
    //   9: if_icmpeq -> 37
    //   12: iload_3
    //   13: ldc_w 1242648481
    //   16: if_icmpeq -> 22
    //   19: goto -> 52
    //   22: aload_2
    //   23: ldc_w 'httpdns'
    //   26: invokevirtual equals : (Ljava/lang/Object;)Z
    //   29: ifeq -> 52
    //   32: iconst_0
    //   33: istore_3
    //   34: goto -> 54
    //   37: aload_2
    //   38: ldc_w 'ttnet'
    //   41: invokevirtual equals : (Ljava/lang/Object;)Z
    //   44: ifeq -> 52
    //   47: iconst_1
    //   48: istore_3
    //   49: goto -> 54
    //   52: iconst_m1
    //   53: istore_3
    //   54: iload_3
    //   55: ifeq -> 78
    //   58: iload_3
    //   59: iconst_1
    //   60: if_icmpeq -> 70
    //   63: aload_0
    //   64: aload_1
    //   65: iconst_0
    //   66: invokespecial requestWithOkHttp : (Lcom/tt/option/q/i;Z)Lcom/tt/option/q/j;
    //   69: areturn
    //   70: invokestatic getInst : ()Lcom/tt/miniapp/manager/NetManager;
    //   73: aload_1
    //   74: invokevirtual requestRaw : (Lcom/tt/option/q/i;)Lcom/tt/option/q/j;
    //   77: areturn
    //   78: aload_0
    //   79: aload_1
    //   80: iconst_1
    //   81: invokespecial requestWithOkHttp : (Lcom/tt/option/q/i;Z)Lcom/tt/option/q/j;
    //   84: areturn
  }
  
  List<Header> parseHeader(JSONObject paramJSONObject) {
    ArrayList<Header> arrayList = new ArrayList();
    if (paramJSONObject != null) {
      Iterator<String> iterator = paramJSONObject.keys();
      if (iterator != null)
        while (iterator.hasNext()) {
          String str = iterator.next();
          arrayList.add(new Header(str, paramJSONObject.optString(str)));
        }  
    } 
    return arrayList;
  }
  
  public void removeRequest(Integer paramInteger) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield requests : Landroid/util/SparseArray;
    //   6: aload_1
    //   7: invokevirtual intValue : ()I
    //   10: invokevirtual get : (I)Ljava/lang/Object;
    //   13: ifnull -> 35
    //   16: aload_0
    //   17: getfield requests : Landroid/util/SparseArray;
    //   20: aload_1
    //   21: invokevirtual intValue : ()I
    //   24: invokevirtual get : (I)Ljava/lang/Object;
    //   27: checkcast com/tt/option/q/b
    //   30: invokeinterface a : ()V
    //   35: aload_0
    //   36: getfield taskIds : Ljava/util/Vector;
    //   39: aload_1
    //   40: invokevirtual remove : (Ljava/lang/Object;)Z
    //   43: pop
    //   44: ldc_w 'RequestManagerV2'
    //   47: iconst_1
    //   48: anewarray java/lang/Object
    //   51: dup
    //   52: iconst_0
    //   53: aload_0
    //   54: getfield requests : Landroid/util/SparseArray;
    //   57: aload_1
    //   58: invokevirtual intValue : ()I
    //   61: invokevirtual get : (I)Ljava/lang/Object;
    //   64: aastore
    //   65: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   68: aload_0
    //   69: monitorexit
    //   70: return
    //   71: astore_1
    //   72: aload_0
    //   73: monitorexit
    //   74: aload_1
    //   75: athrow
    // Exception table:
    //   from	to	target	type
    //   2	35	71	finally
    //   35	68	71	finally
  }
  
  public void reportTTRequestResult(boolean paramBoolean1, boolean paramBoolean2, String paramString1, long paramLong, String paramString2, String paramString3, Throwable paramThrowable) {
    String str1;
    if (Math.random() > 0.05D && !paramBoolean2)
      return; 
    boolean bool = TextUtils.isEmpty(paramString2);
    String str2 = "";
    if (!bool) {
      str1 = paramString2;
      if (paramString2.contains("?"))
        str1 = paramString2.substring(0, paramString2.indexOf("?")); 
    } else {
      str1 = "";
    } 
    JSONObject jSONObject = new JSONObject();
    try {
      boolean bool1;
      jSONObject.put("error_msg", paramString3);
      paramString2 = str2;
      if (paramThrowable != null)
        paramString2 = Log.getStackTraceString(paramThrowable); 
      jSONObject.put("error_stack", paramString2);
      jSONObject.put("url_path", str1);
      jSONObject.put("request_type", paramString1);
      jSONObject.put("first_domain_req", paramBoolean2);
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      if (appInfoEntity != null && appInfoEntity.appId != null && appInfoEntity.version != null) {
        jSONObject.put("app_id", appInfoEntity.appId);
        jSONObject.put("app_version", appInfoEntity.version);
      } 
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("duration", paramLong);
      if (!paramBoolean1) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      jSONObject.put("net_type", d.b((Context)AppbrandContext.getInst().getApplicationContext()));
      jSONObject.put("net_available", d.a((Context)AppbrandContext.getInst().getApplicationContext()));
      return;
    } finally {
      paramString1 = null;
      AppBrandLogger.e("RequestManagerV2", new Object[] { paramString1 });
    } 
  }
  
  public HttpRequest.RequestResult requestSync(String paramString, HttpRequest.RequestTask paramRequestTask) throws Exception {
    String str1;
    j j;
    boolean bool1 = paramRequestTask.l;
    boolean bool2 = ProcessUtil.isMiniappProcess();
    String str2 = paramString;
    if (bool1) {
      if (bool2 && !HostDependManager.getInst().supportRequestCommonParamsInChildProcess()) {
        ServiceBindManager.getInstance().bindHostService();
        HttpRequest.RequestResult requestResult2 = InnerHostProcessBridge.httpRequestWithCommonParam(paramRequestTask);
        HttpRequest.RequestResult requestResult1 = requestResult2;
        if (requestResult2 == null) {
          ServiceBindManager.getInstance().bindHostService();
          requestResult1 = new HttpRequest.RequestResult(paramRequestTask.a);
          requestResult1.h = "附加通用参数的内部网络请求失败";
        } 
        return requestResult1;
      } 
      str2 = "ttnet";
    } 
    int k = paramRequestTask.a;
    paramString = paramRequestTask.b;
    String str3 = paramString;
    if (bool2) {
      str3 = paramString;
      if (isInnerApp())
        str3 = Uri.parse(paramRequestTask.b).buildUpon().appendQueryParameter("device_id", d.a()).appendQueryParameter("aid", AppbrandContext.getInst().getInitParams().getAppId()).build().toString(); 
    } 
    String str5 = paramRequestTask.c;
    String str4 = paramRequestTask.h;
    JSONObject jSONObject2 = paramRequestTask.g;
    JSONObject jSONObject1 = jSONObject2;
    if ((DebugManager.getInst()).mRemoteDebugEnable) {
      jSONObject1 = jSONObject2;
      if (!paramRequestTask.i) {
        jSONObject1 = jSONObject2;
        if (jSONObject2 == null)
          jSONObject1 = new JSONObject(); 
        jSONObject1.put("remoteDebug", "request");
      } 
    } 
    Map<String, String> map = setupHeaders(str3, jSONObject1, paramRequestTask.k, bool1);
    i i1 = new i(str3, str5, bool1);
    jSONObject1 = null;
    for (Map.Entry<String, String> entry : map.entrySet()) {
      String str = (String)entry.getKey();
      str3 = (String)entry.getValue();
      i1.a((String)entry.getKey(), (String)entry.getValue());
      if (str.equalsIgnoreCase("content-type"))
        str1 = str3; 
    } 
    long l = 60000L;
    if (bool2)
      l = getRequestTimeout(); 
    i1.k = l;
    i1.l = l;
    i1.j = l;
    if (str1 != null)
      i1.d = str1; 
    int i = str5.hashCode();
    boolean bool = true;
    switch (i) {
      default:
        i = -1;
        break;
      case 2012838315:
        if (str5.equals("DELETE")) {
          i = 3;
          break;
        } 
      case 1669334218:
        if (str5.equals("CONNECT")) {
          i = 5;
          break;
        } 
      case 80083237:
        if (str5.equals("TRACE")) {
          i = 4;
          break;
        } 
      case 2461856:
        if (str5.equals("POST")) {
          i = 1;
          break;
        } 
      case 79599:
        if (str5.equals("PUT")) {
          i = 2;
          break;
        } 
      case -531492226:
        if (str5.equals("OPTIONS")) {
          i = 0;
          break;
        } 
    } 
    if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
      byte[] arrayOfByte;
      if (paramRequestTask.f != null) {
        arrayOfByte = paramRequestTask.f;
      } else if (paramRequestTask.e == null) {
        arrayOfByte = new byte[0];
      } else {
        arrayOfByte = paramRequestTask.e.getBytes();
      } 
      i1.i = arrayOfByte;
      i1.c = str5;
    } 
    addToRequests(k, i1);
    if (paramRequestTask.d) {
      j = PreTTRequestManager.getFromCacheIfMatched(i1);
      if (j != null) {
        i = bool;
      } else {
        i = 0;
        j = doRequest(i1, str2);
      } 
    } else {
      i = -1;
      j = doRequest(i1, str2);
    } 
    HttpRequest.RequestResult requestResult = convertToRequestResult(j, k, str4);
    requestResult.j = i;
    requestResult.i = j.f;
    return requestResult;
  }
  
  public static class Header {
    String key;
    
    String value;
    
    public Header(String param1String1, String param1String2) {
      this.key = param1String1;
      this.value = param1String2;
    }
    
    public String getName() {
      return this.key;
    }
    
    public String getValue() {
      return this.value;
    }
  }
  
  static class Holder {
    static RequestManagerV2 requestManager = new RequestManagerV2();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\netrequest\RequestManagerV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */