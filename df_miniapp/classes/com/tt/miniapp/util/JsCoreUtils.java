package com.tt.miniapp.util;

import android.content.Context;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.debug.SwitchManager;
import com.tt.miniapp.jsbridge.JsRuntimeManager;
import com.tt.miniapp.navigate2.Nav2Util;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsCoreUtils {
  public static void sendAppEnterBackground() {
    try {
      JSONObject jSONObject = new JSONObject();
      j j = AppbrandApplication.getInst().getJsBridge();
      if (j != null) {
        j.sendMsgToJsCore("onAppEnterBackground", jSONObject.toString());
        StringBuilder stringBuilder = new StringBuilder("sendAppEnterBackground");
        stringBuilder.append(jSONObject.toString());
        AppBrandLogger.d("tma_JsCoreUtils", new Object[] { stringBuilder.toString() });
      } 
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_JsCoreUtils", exception.getStackTrace());
      return;
    } 
  }
  
  public static void sendAppEnterForeground() {
    try {
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      if (appInfoEntity == null)
        return; 
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("shareTicket", appInfoEntity.shareTicket);
      jSONObject.put("scene", appInfoEntity.scene);
      jSONObject.put("subScene", appInfoEntity.subScene);
      jSONObject.put("refererInfo", Nav2Util.getReferer(appInfoEntity));
      j j = AppbrandApplication.getInst().getJsBridge();
      if (j != null) {
        j.sendMsgToJsCore("onAppEnterForeground", jSONObject.toString());
        StringBuilder stringBuilder = new StringBuilder("sendAppEnterForeground");
        stringBuilder.append(jSONObject.toString());
        AppBrandLogger.d("tma_JsCoreUtils", new Object[] { stringBuilder.toString() });
      } 
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_JsCoreUtils", exception.getStackTrace());
      return;
    } 
  }
  
  public static void sendAppLaunch() {
    // Byte code:
    //   0: new org/json/JSONObject
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_3
    //   8: invokestatic getInst : ()Lcom/tt/miniapphost/IAppbrandApplication;
    //   11: invokeinterface getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   16: astore_1
    //   17: aload_1
    //   18: ifnull -> 497
    //   21: aload_3
    //   22: ldc 'scene'
    //   24: aload_1
    //   25: getfield scene : Ljava/lang/String;
    //   28: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   31: pop
    //   32: aload_3
    //   33: ldc 'subScene'
    //   35: aload_1
    //   36: getfield subScene : Ljava/lang/String;
    //   39: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   42: pop
    //   43: aload_3
    //   44: ldc 'shareTicket'
    //   46: aload_1
    //   47: getfield shareTicket : Ljava/lang/String;
    //   50: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   53: pop
    //   54: aload_3
    //   55: ldc 'group_id'
    //   57: invokestatic getIns : ()Lcom/tt/miniapp/game/DiversionTool;
    //   60: invokevirtual getGroupId : ()Ljava/lang/String;
    //   63: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   66: pop
    //   67: aload_3
    //   68: ldc 'refererInfo'
    //   70: aload_1
    //   71: invokestatic getReferer : (Lcom/tt/miniapphost/entity/AppInfoEntity;)Lorg/json/JSONObject;
    //   74: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   77: pop
    //   78: aload_1
    //   79: invokevirtual isGame : ()Z
    //   82: istore_0
    //   83: iload_0
    //   84: ifeq -> 101
    //   87: aload_3
    //   88: ldc 'query'
    //   90: aload_1
    //   91: getfield query : Ljava/lang/String;
    //   94: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   97: pop
    //   98: goto -> 228
    //   101: aload_1
    //   102: getfield oriStartPage : Ljava/lang/String;
    //   105: astore_2
    //   106: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   109: invokevirtual getAppConfig : ()Lcom/tt/miniapp/AppConfig;
    //   112: astore #4
    //   114: aload_2
    //   115: astore_1
    //   116: aload #4
    //   118: ifnull -> 143
    //   121: aload_2
    //   122: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   125: ifne -> 137
    //   128: aload_2
    //   129: astore_1
    //   130: aload_2
    //   131: invokestatic isPageValidate : (Ljava/lang/String;)Z
    //   134: ifne -> 143
    //   137: aload #4
    //   139: getfield mEntryPath : Ljava/lang/String;
    //   142: astore_1
    //   143: aload_1
    //   144: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   147: istore_0
    //   148: iload_0
    //   149: ifne -> 210
    //   152: aload_1
    //   153: ldc '\?'
    //   155: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   158: astore_1
    //   159: aload_1
    //   160: arraylength
    //   161: iconst_1
    //   162: if_icmple -> 188
    //   165: aload_3
    //   166: ldc 'path'
    //   168: aload_1
    //   169: iconst_0
    //   170: aaload
    //   171: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   174: pop
    //   175: aload_3
    //   176: ldc 'query'
    //   178: aload_1
    //   179: iconst_1
    //   180: aaload
    //   181: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   184: pop
    //   185: goto -> 228
    //   188: aload_3
    //   189: ldc 'path'
    //   191: aload_1
    //   192: iconst_0
    //   193: aaload
    //   194: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   197: pop
    //   198: aload_3
    //   199: ldc 'query'
    //   201: ldc ''
    //   203: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   206: pop
    //   207: goto -> 228
    //   210: aload_3
    //   211: ldc 'path'
    //   213: ldc ''
    //   215: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   218: pop
    //   219: aload_3
    //   220: ldc 'query'
    //   222: ldc ''
    //   224: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   227: pop
    //   228: invokestatic getInst : ()Lcom/tt/miniapphost/IAppbrandApplication;
    //   231: invokeinterface getJsBridge : ()Lcom/tt/frontendapiinterface/j;
    //   236: astore_1
    //   237: aload_1
    //   238: ifnull -> 288
    //   241: aload_1
    //   242: ldc 'onAppLaunch'
    //   244: aload_3
    //   245: invokevirtual toString : ()Ljava/lang/String;
    //   248: invokeinterface sendMsgToJsCore : (Ljava/lang/String;Ljava/lang/String;)V
    //   253: new java/lang/StringBuilder
    //   256: dup
    //   257: ldc 'sendAppLaunch'
    //   259: invokespecial <init> : (Ljava/lang/String;)V
    //   262: astore_1
    //   263: aload_1
    //   264: aload_3
    //   265: invokevirtual toString : ()Ljava/lang/String;
    //   268: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   271: pop
    //   272: ldc 'tma_JsCoreUtils'
    //   274: iconst_1
    //   275: anewarray java/lang/Object
    //   278: dup
    //   279: iconst_0
    //   280: aload_1
    //   281: invokevirtual toString : ()Ljava/lang/String;
    //   284: aastore
    //   285: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   288: return
    //   289: astore_1
    //   290: ldc 'tma_JsCoreUtils'
    //   292: iconst_2
    //   293: anewarray java/lang/Object
    //   296: dup
    //   297: iconst_0
    //   298: ldc 'onAppLaunch fail'
    //   300: aastore
    //   301: dup
    //   302: iconst_1
    //   303: aload_1
    //   304: aastore
    //   305: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   308: return
    //   309: astore_1
    //   310: goto -> 413
    //   313: astore_1
    //   314: ldc 'tma_JsCoreUtils'
    //   316: iconst_2
    //   317: anewarray java/lang/Object
    //   320: dup
    //   321: iconst_0
    //   322: ldc 'onAppLaunch params error'
    //   324: aastore
    //   325: dup
    //   326: iconst_1
    //   327: aload_1
    //   328: aastore
    //   329: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   332: invokestatic getInst : ()Lcom/tt/miniapphost/IAppbrandApplication;
    //   335: invokeinterface getJsBridge : ()Lcom/tt/frontendapiinterface/j;
    //   340: astore_1
    //   341: aload_1
    //   342: ifnull -> 392
    //   345: aload_1
    //   346: ldc 'onAppLaunch'
    //   348: aload_3
    //   349: invokevirtual toString : ()Ljava/lang/String;
    //   352: invokeinterface sendMsgToJsCore : (Ljava/lang/String;Ljava/lang/String;)V
    //   357: new java/lang/StringBuilder
    //   360: dup
    //   361: ldc 'sendAppLaunch'
    //   363: invokespecial <init> : (Ljava/lang/String;)V
    //   366: astore_1
    //   367: aload_1
    //   368: aload_3
    //   369: invokevirtual toString : ()Ljava/lang/String;
    //   372: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   375: pop
    //   376: ldc 'tma_JsCoreUtils'
    //   378: iconst_1
    //   379: anewarray java/lang/Object
    //   382: dup
    //   383: iconst_0
    //   384: aload_1
    //   385: invokevirtual toString : ()Ljava/lang/String;
    //   388: aastore
    //   389: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   392: return
    //   393: astore_1
    //   394: ldc 'tma_JsCoreUtils'
    //   396: iconst_2
    //   397: anewarray java/lang/Object
    //   400: dup
    //   401: iconst_0
    //   402: ldc 'onAppLaunch fail'
    //   404: aastore
    //   405: dup
    //   406: iconst_1
    //   407: aload_1
    //   408: aastore
    //   409: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   412: return
    //   413: invokestatic getInst : ()Lcom/tt/miniapphost/IAppbrandApplication;
    //   416: invokeinterface getJsBridge : ()Lcom/tt/frontendapiinterface/j;
    //   421: astore_2
    //   422: aload_2
    //   423: ifnull -> 495
    //   426: aload_2
    //   427: ldc 'onAppLaunch'
    //   429: aload_3
    //   430: invokevirtual toString : ()Ljava/lang/String;
    //   433: invokeinterface sendMsgToJsCore : (Ljava/lang/String;Ljava/lang/String;)V
    //   438: new java/lang/StringBuilder
    //   441: dup
    //   442: ldc 'sendAppLaunch'
    //   444: invokespecial <init> : (Ljava/lang/String;)V
    //   447: astore_2
    //   448: aload_2
    //   449: aload_3
    //   450: invokevirtual toString : ()Ljava/lang/String;
    //   453: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   456: pop
    //   457: ldc 'tma_JsCoreUtils'
    //   459: iconst_1
    //   460: anewarray java/lang/Object
    //   463: dup
    //   464: iconst_0
    //   465: aload_2
    //   466: invokevirtual toString : ()Ljava/lang/String;
    //   469: aastore
    //   470: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   473: goto -> 495
    //   476: astore_2
    //   477: ldc 'tma_JsCoreUtils'
    //   479: iconst_2
    //   480: anewarray java/lang/Object
    //   483: dup
    //   484: iconst_0
    //   485: ldc 'onAppLaunch fail'
    //   487: aastore
    //   488: dup
    //   489: iconst_1
    //   490: aload_2
    //   491: aastore
    //   492: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   495: aload_1
    //   496: athrow
    //   497: return
    // Exception table:
    //   from	to	target	type
    //   21	83	313	java/lang/Exception
    //   21	83	309	finally
    //   87	98	313	java/lang/Exception
    //   87	98	309	finally
    //   101	114	313	java/lang/Exception
    //   101	114	309	finally
    //   121	128	313	java/lang/Exception
    //   121	128	309	finally
    //   130	137	313	java/lang/Exception
    //   130	137	309	finally
    //   137	143	313	java/lang/Exception
    //   137	143	309	finally
    //   143	148	313	java/lang/Exception
    //   143	148	309	finally
    //   152	185	313	java/lang/Exception
    //   152	185	309	finally
    //   188	207	313	java/lang/Exception
    //   188	207	309	finally
    //   210	228	313	java/lang/Exception
    //   210	228	309	finally
    //   228	237	289	java/lang/Exception
    //   241	288	289	java/lang/Exception
    //   314	332	309	finally
    //   332	341	393	java/lang/Exception
    //   345	392	393	java/lang/Exception
    //   413	422	476	java/lang/Exception
    //   426	473	476	java/lang/Exception
  }
  
  public static void sendAppOnPressBackButton(String paramString, int paramInt) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("type", paramString);
      jSONObject.put("id", String.valueOf(paramInt));
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_JsCoreUtils", new Object[] { "onBackPressed", jSONException });
    } 
    AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onPressedBackButton", jSONObject.toString(), paramInt);
  }
  
  public static void sendAppRoute(int paramInt, String paramString1, String paramString2, String paramString3) {
    TimeLogger.getInstance().logTimeDuration(new String[] { "SendOnAppRoute", paramString3, paramString1 });
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("webviewId", paramInt);
      jSONObject.put("path", paramString1);
      jSONObject.put("query", paramString2);
      jSONObject.put("openType", paramString3);
      if (AppbrandApplication.getInst().getAppInfo() != null) {
        jSONObject.put("scene", (AppbrandApplication.getInst().getAppInfo()).scene);
        jSONObject.put("subScene", (AppbrandApplication.getInst().getAppInfo()).subScene);
        jSONObject.put("shareTicket", (AppbrandApplication.getInst().getAppInfo()).shareTicket);
      } 
      AppBrandLogger.d("tma_JsCoreUtils", new Object[] { "sendAppRoute", jSONObject.toString() });
      ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEvent("sendAppRoute");
      AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onAppRoute", jSONObject.toString(), paramInt, true);
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_JsCoreUtils", exception.getStackTrace());
      return;
    } 
  }
  
  public static void sendGeneralConfig(JSONObject paramJSONObject) {
    j j = AppbrandApplication.getInst().getJsBridge();
    if (j != null)
      j.sendMsgToJsCore("onPushGeneralConfig", paramJSONObject.toString()); 
  }
  
  public static void sendOnBeforeExit(boolean paramBoolean) {
    j j = AppbrandApplication.getInst().getJsBridge();
    if (j != null) {
      char c;
      JSONObject jSONObject = new JSONObject();
      if (paramBoolean) {
        c = 'Ϫ';
      } else {
        c = 'ϩ';
      } 
      try {
        String str;
        jSONObject.put("from", c);
        if (paramBoolean) {
          str = "backPress";
        } else {
          str = "tag";
        } 
        jSONObject.put("fromTag", str);
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("tma_JsCoreUtils", "", (Throwable)jSONException);
      } 
      j.sendMsgToJsCore("onBeforeExitMiniProgram", jSONObject.toString());
    } 
  }
  
  public static void sendOnWindowReSize(Context paramContext) {
    if (paramContext == null)
      return; 
    WebViewManager.IRender iRender = AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender();
    if (iRender != null) {
      int j = iRender.getRenderWidth();
      int i = iRender.getRenderHeight();
      j = (int)Math.ceil((j / DevicesUtil.getPixelRadio(paramContext)));
      i = (int)Math.ceil((i / DevicesUtil.getPixelRadio(paramContext)));
      StringBuilder stringBuilder = new StringBuilder("onWindowSizeChange->width:");
      stringBuilder.append(j);
      stringBuilder.append(" height:");
      stringBuilder.append(i);
      AppBrandLogger.d("tma_JsCoreUtils", new Object[] { stringBuilder.toString() });
      try {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("windowWidth", j);
        jSONObject.put("windowHeight", i);
        AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onWindowResize", jSONObject.toString());
        return;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "tma_JsCoreUtils", exception.getStackTrace());
      } 
    } 
  }
  
  public static void sendVConsole(j paramj, JSONArray paramJSONArray) {
    try {
      SwitchManager switchManager = (SwitchManager)AppbrandApplicationImpl.getInst().getService(SwitchManager.class);
      if (switchManager == null || !switchManager.isVConsoleSwitchOn()) {
        AppBrandLogger.e("tma_JsCoreUtils", new Object[] { switchManager, Boolean.valueOf(switchManager.isVConsoleSwitchOn()) });
        return;
      } 
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("logs", paramJSONArray);
      if (paramj != null) {
        paramj.sendMsgToJsCore("console", jSONObject.toString());
        StringBuilder stringBuilder = new StringBuilder("sendConsole");
        stringBuilder.append(jSONObject.toString());
        AppBrandLogger.d("tma_JsCoreUtils", new Object[] { stringBuilder.toString() });
        return;
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_JsCoreUtils", exception.getStackTrace());
      return;
    } 
  }
  
  public static void sendVConsole(final JSONArray logs) {
    final JsRuntimeManager jsRuntimeManager = (JsRuntimeManager)AppbrandApplicationImpl.getInst().getService(JsRuntimeManager.class);
    j j = jsRuntimeManager.getJsBridge();
    if (j != null) {
      sendVConsole(j, logs);
      return;
    } 
    jsRuntimeManager.addJsRuntimeReadyListener(new JsRuntimeManager.JsRuntimeReadyListener() {
          public final void onJsRuntimeReady() {
            JsCoreUtils.sendVConsole(jsRuntimeManager.getJsBridge(), logs);
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\JsCoreUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */