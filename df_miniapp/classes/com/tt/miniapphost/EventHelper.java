package com.tt.miniapphost;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tt.miniapp.event.Event;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.option.q.d;
import com.tt.option.q.g;
import com.tt.option.q.i;
import com.tt.option.q.j;
import java.io.File;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class EventHelper {
  private static String sProcessFlag;
  
  private static void addExtraEventParams(AppInfoEntity paramAppInfoEntity, JSONObject paramJSONObject) throws JSONException {
    if (paramAppInfoEntity != null && paramJSONObject != null) {
      String str = paramAppInfoEntity.extra;
      if (TextUtils.isEmpty(str))
        return; 
      JSONObject jSONObject = (new JSONObject(str)).optJSONObject("event_extra");
      if (jSONObject != null)
        try {
          Iterator<String> iterator = jSONObject.keys();
          while (iterator.hasNext()) {
            String str1 = iterator.next();
            paramJSONObject.put(str1, jSONObject.get(str1));
          } 
          return;
        } catch (JSONException jSONException) {
          AppBrandLogger.stacktrace(5, "tma_EventHelper", jSONException.getStackTrace());
        }  
    } 
  }
  
  public static void copyBasicCommonParams(JSONObject paramJSONObject1, JSONObject paramJSONObject2) {
    if (paramJSONObject1 != null) {
      if (paramJSONObject2 == null)
        return; 
      try {
        paramJSONObject2.put("lib_version", paramJSONObject1.opt("lib_version"));
        paramJSONObject2.put("miniapp_sdk_version", paramJSONObject1.opt("miniapp_sdk_version"));
        paramJSONObject2.put("js_engine_version", paramJSONObject1.opt("js_engine_version"));
        paramJSONObject2.put("dora_version", paramJSONObject1.opt("dora_version"));
        paramJSONObject2.put("_param_for_special", paramJSONObject1.opt("_param_for_special"));
        paramJSONObject2.put("mp_id", paramJSONObject1.opt("mp_id"));
        paramJSONObject2.put("mp_gid", paramJSONObject1.opt("mp_gid"));
        paramJSONObject2.put("mp_name", paramJSONObject1.opt("mp_name"));
        paramJSONObject2.put("launch_from", paramJSONObject1.opt("launch_from"));
        paramJSONObject2.put("scene", paramJSONObject1.opt("scene"));
        paramJSONObject2.put("sub_scene", paramJSONObject1.opt("sub_scene"));
        paramJSONObject2.put("bdp_log", paramJSONObject1.opt("bdp_log"));
        paramJSONObject2.put("location", paramJSONObject1.opt("location"));
        paramJSONObject2.put("biz_location", paramJSONObject1.opt("biz_location"));
        paramJSONObject2.put("tech_type", paramJSONObject1.opt("tech_type"));
        return;
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("tma_EventHelper", "fetchCommonParams exp!", (Throwable)jSONException);
      } 
    } 
  }
  
  public static JSONObject getCommonParams(JSONObject paramJSONObject, AppInfoEntity paramAppInfoEntity) {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull -> 15
    //   4: new org/json/JSONObject
    //   7: dup
    //   8: invokespecial <init> : ()V
    //   11: astore_3
    //   12: goto -> 27
    //   15: new org/json/JSONObject
    //   18: dup
    //   19: aload_0
    //   20: invokevirtual toString : ()Ljava/lang/String;
    //   23: invokespecial <init> : (Ljava/lang/String;)V
    //   26: astore_3
    //   27: aload_3
    //   28: ldc 'lib_version'
    //   30: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   33: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   36: invokestatic getJsSdkVersion : (Landroid/content/Context;)Ljava/lang/String;
    //   39: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   42: pop
    //   43: aload_3
    //   44: ldc 'dora_version'
    //   46: invokestatic getDoraVersion : ()Ljava/lang/String;
    //   49: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   52: pop
    //   53: aload_3
    //   54: ldc 'miniapp_sdk_version'
    //   56: invokestatic getFullAppSdkVersion : ()Ljava/lang/String;
    //   59: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   62: pop
    //   63: aload_3
    //   64: ldc 'miniapp_process'
    //   66: invokestatic getProcess : ()Ljava/lang/String;
    //   69: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   72: pop
    //   73: aload_3
    //   74: ldc 'miniapp_sdk_version_code'
    //   76: invokestatic getMiniAppSdkVersionCode : ()I
    //   79: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   82: pop
    //   83: aload_3
    //   84: ldc 'report_timestamp'
    //   86: invokestatic currentTimeMillis : ()J
    //   89: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   92: pop
    //   93: aload_3
    //   94: ldc 'unique_id'
    //   96: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   99: invokevirtual getUniqueId : ()Ljava/lang/String;
    //   102: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   105: pop
    //   106: aload_3
    //   107: ldc 'launch_id'
    //   109: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   112: invokevirtual getLaunchId : ()Ljava/lang/String;
    //   115: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   118: pop
    //   119: aload_3
    //   120: ldc 'is_tt_webview'
    //   122: getstatic com/tt/miniapphost/render/export/TTWebSdkWrapper.INSTANCE : Lcom/tt/miniapphost/render/export/TTWebSdkWrapper;
    //   125: invokevirtual isTTWebView : ()Z
    //   128: invokevirtual put : (Ljava/lang/String;Z)Lorg/json/JSONObject;
    //   131: pop
    //   132: aload_1
    //   133: astore_0
    //   134: aload_1
    //   135: ifnonnull -> 155
    //   138: aload_1
    //   139: astore_0
    //   140: invokestatic isMiniappProcess : ()Z
    //   143: ifeq -> 155
    //   146: invokestatic getInst : ()Lcom/tt/miniapphost/IAppbrandApplication;
    //   149: invokeinterface getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   154: astore_0
    //   155: aload_0
    //   156: astore_1
    //   157: aload_0
    //   158: ifnonnull -> 169
    //   161: new com/tt/miniapphost/entity/AppInfoEntity
    //   164: dup
    //   165: invokespecial <init> : ()V
    //   168: astore_1
    //   169: aload_3
    //   170: ldc '_param_for_special'
    //   172: aload_1
    //   173: invokestatic getTypeStr : (Lcom/tt/miniapphost/entity/AppInfoEntity;)Ljava/lang/String;
    //   176: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   179: pop
    //   180: aload_3
    //   181: ldc 'mp_id'
    //   183: aload_1
    //   184: getfield appId : Ljava/lang/String;
    //   187: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   190: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   193: pop
    //   194: aload_3
    //   195: ldc 'mp_version'
    //   197: aload_1
    //   198: getfield version : Ljava/lang/String;
    //   201: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   204: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   207: pop
    //   208: aload_3
    //   209: ldc 'mp_gid'
    //   211: aload_1
    //   212: getfield ttId : Ljava/lang/String;
    //   215: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   218: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   221: pop
    //   222: aload_3
    //   223: ldc 'mp_name'
    //   225: aload_1
    //   226: getfield appName : Ljava/lang/String;
    //   229: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   232: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   235: pop
    //   236: aload_3
    //   237: ldc 'launch_from'
    //   239: aload_1
    //   240: getfield launchFrom : Ljava/lang/String;
    //   243: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   246: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   249: pop
    //   250: aload_3
    //   251: ldc 'scene'
    //   253: aload_1
    //   254: getfield scene : Ljava/lang/String;
    //   257: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   260: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   263: pop
    //   264: aload_3
    //   265: ldc 'sub_scene'
    //   267: aload_1
    //   268: getfield subScene : Ljava/lang/String;
    //   271: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   274: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   277: pop
    //   278: aload_3
    //   279: ldc 'bdp_log'
    //   281: aload_1
    //   282: getfield bdpLog : Ljava/lang/String;
    //   285: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   288: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   291: pop
    //   292: aload_3
    //   293: ldc 'location'
    //   295: aload_1
    //   296: getfield location : Ljava/lang/String;
    //   299: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   302: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   305: pop
    //   306: aload_3
    //   307: ldc 'biz_location'
    //   309: aload_1
    //   310: getfield bizLocation : Ljava/lang/String;
    //   313: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   316: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   319: pop
    //   320: aload_3
    //   321: ldc 'session_id'
    //   323: invokestatic getSessionId : ()Ljava/lang/String;
    //   326: invokestatic null2Empty : (Ljava/lang/String;)Ljava/lang/String;
    //   329: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   332: pop
    //   333: aload_3
    //   334: ldc 'tech_type'
    //   336: aload_1
    //   337: getfield type : I
    //   340: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   343: pop
    //   344: aload_1
    //   345: invokevirtual isAdSite : ()Z
    //   348: ifeq -> 374
    //   351: invokestatic getInstance : ()Lcom/tt/miniapp/adsite/AdSiteManager;
    //   354: invokevirtual isAdSiteBrowser : ()Z
    //   357: ifeq -> 403
    //   360: iconst_1
    //   361: istore_2
    //   362: goto -> 365
    //   365: aload_3
    //   366: ldc_w 'ad_site_version'
    //   369: iload_2
    //   370: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   373: pop
    //   374: aload_1
    //   375: aload_3
    //   376: invokestatic addExtraEventParams : (Lcom/tt/miniapphost/entity/AppInfoEntity;Lorg/json/JSONObject;)V
    //   379: aload_3
    //   380: areturn
    //   381: astore_0
    //   382: new org/json/JSONObject
    //   385: dup
    //   386: invokespecial <init> : ()V
    //   389: astore_1
    //   390: bipush #6
    //   392: ldc 'tma_EventHelper'
    //   394: aload_0
    //   395: invokevirtual getStackTrace : ()[Ljava/lang/StackTraceElement;
    //   398: invokestatic stacktrace : (ILjava/lang/String;[Ljava/lang/StackTraceElement;)V
    //   401: aload_1
    //   402: areturn
    //   403: iconst_0
    //   404: istore_2
    //   405: goto -> 365
    // Exception table:
    //   from	to	target	type
    //   4	12	381	org/json/JSONException
    //   15	27	381	org/json/JSONException
    //   27	132	381	org/json/JSONException
    //   140	155	381	org/json/JSONException
    //   161	169	381	org/json/JSONException
    //   169	360	381	org/json/JSONException
    //   365	374	381	org/json/JSONException
    //   374	379	381	org/json/JSONException
  }
  
  private static String getProcess() {
    // Byte code:
    //   0: getstatic com/tt/miniapphost/EventHelper.sProcessFlag : Ljava/lang/String;
    //   3: ifnonnull -> 92
    //   6: ldc 'tma_EventHelper'
    //   8: monitorenter
    //   9: getstatic com/tt/miniapphost/EventHelper.sProcessFlag : Ljava/lang/String;
    //   12: ifnonnull -> 80
    //   15: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   18: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   21: invokestatic getCurProcessName : (Landroid/content/Context;)Ljava/lang/String;
    //   24: astore_1
    //   25: aload_1
    //   26: astore_0
    //   27: aload_1
    //   28: ifnonnull -> 35
    //   31: ldc_w ''
    //   34: astore_0
    //   35: new java/lang/StringBuilder
    //   38: dup
    //   39: invokespecial <init> : ()V
    //   42: astore_1
    //   43: aload_1
    //   44: aload_0
    //   45: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: pop
    //   49: aload_1
    //   50: ldc_w '('
    //   53: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: pop
    //   57: aload_1
    //   58: invokestatic myPid : ()I
    //   61: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   64: pop
    //   65: aload_1
    //   66: ldc_w ')'
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: pop
    //   73: aload_1
    //   74: invokevirtual toString : ()Ljava/lang/String;
    //   77: putstatic com/tt/miniapphost/EventHelper.sProcessFlag : Ljava/lang/String;
    //   80: ldc 'tma_EventHelper'
    //   82: monitorexit
    //   83: goto -> 92
    //   86: astore_0
    //   87: ldc 'tma_EventHelper'
    //   89: monitorexit
    //   90: aload_0
    //   91: athrow
    //   92: getstatic com/tt/miniapphost/EventHelper.sProcessFlag : Ljava/lang/String;
    //   95: areturn
    // Exception table:
    //   from	to	target	type
    //   9	25	86	finally
    //   35	80	86	finally
    //   80	83	86	finally
    //   87	90	86	finally
  }
  
  private static String getTypeStr(AppInfoEntity paramAppInfoEntity) {
    return (paramAppInfoEntity != null && 2 == paramAppInfoEntity.type) ? "micro_game" : "micro_app";
  }
  
  public static void mpInitResult(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean, long paramLong, String paramString5, String paramString6) {
    JSONObject jSONObject = getCommonParams(new JSONObject(), null);
    try {
      if (!TextUtils.isEmpty(paramString1))
        jSONObject.put("mp_id", paramString1); 
      if (!TextUtils.isEmpty(paramString1))
        jSONObject.put("launch_from", paramString2); 
      if (!TextUtils.isEmpty(paramString1))
        jSONObject.put("scene", paramString3); 
      if (!TextUtils.isEmpty(paramString1))
        jSONObject.put("sub_scene", paramString4); 
      if (!TextUtils.isEmpty(paramString1)) {
        if (paramBoolean) {
          paramString1 = "micro_game";
        } else {
          paramString1 = "micro_app";
        } 
        jSONObject.put("_param_for_special", paramString1);
      } 
      jSONObject.put("duration", paramLong);
      jSONObject.put("result_type", paramString5);
      jSONObject.put("error_msg", paramString6);
    } catch (JSONException jSONException) {}
    HostProcessBridge.logEvent("mp_init_result", jSONObject);
  }
  
  public static void mpLibResult(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong) {
    Event.Builder builder = Event.builder(paramString1).kv("lib_version", paramString2).kv("latest_version", paramString3).kv("result_type", paramString4).kv("_param_for_special", "micro_app");
    if (paramLong >= 0L)
      builder.kv("duration", Long.valueOf(paramLong)); 
    if (!TextUtils.isEmpty(paramString5))
      builder.kv("error_msg", paramString5); 
    builder.flush();
  }
  
  public static void mpSdkRequestResult(i parami, j paramj, long paramLong) {
    Event.Builder builder = Event.builder("mp_sdk_request_result");
    try {
      boolean bool1;
      builder.kv("url", parami.f());
      builder.kv("duration", Long.valueOf(paramLong));
      boolean bool = d.a((Context)AppbrandContext.getInst().getApplicationContext());
      boolean bool2 = true;
      if (bool) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      builder.kv("is_net_availbale", Integer.valueOf(bool1));
      builder.kv("net_type", d.b((Context)AppbrandContext.getInst().getApplicationContext()));
      if (paramj != null) {
        builder.kv("net_code", Integer.valueOf(paramj.b));
        builder.kv("net_message", paramj.c);
        builder.kv("data", paramj.a());
        builder.kv("err_stack", Log.getStackTraceString(paramj.f));
        if (paramj instanceof g) {
          File file = ((g)paramj).a;
          if (file != null && file.exists()) {
            bool1 = bool2;
          } else {
            bool1 = false;
          } 
          builder.kv("download_file_result", Integer.valueOf(bool1));
        } 
        if (parami.n != null) {
          Iterator<String> iterator = parami.n.keys();
          while (iterator.hasNext()) {
            String str = iterator.next();
            builder.kv(str, parami.n.get(str));
          } 
        } 
        if (paramj.g != null) {
          Iterator<String> iterator = paramj.g.keys();
          while (iterator.hasNext()) {
            String str = iterator.next();
            builder.kv(str, paramj.g.get(str));
          } 
        } 
      } 
    } catch (JSONException jSONException) {}
    builder.flush();
  }
  
  private static String null2Empty(String paramString) {
    String str = paramString;
    if (TextUtils.isEmpty(paramString))
      str = ""; 
    return str;
  }
  
  public static void sendLogV1(String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2, JSONObject paramJSONObject) {
    HostProcessBridge.sendLogV1(paramString1, paramString2, paramString3, paramLong1, paramLong2, paramJSONObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\EventHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */