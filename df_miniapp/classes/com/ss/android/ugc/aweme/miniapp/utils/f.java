package com.ss.android.ugc.aweme.miniapp.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;
import com.ss.android.ugc.aweme.app.f.d;
import com.ss.android.ugc.aweme.common.MobClick;
import com.ss.android.ugc.aweme.common.g;
import com.ss.android.ugc.aweme.framework.a.a;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp.b;
import com.ss.android.ugc.aweme.miniapp_api.a.g;
import com.ss.android.ugc.aweme.miniapp_api.a.n;
import com.ss.android.ugc.aweme.miniapp_api.c;
import com.ss.android.ugc.aweme.miniapp_api.d;
import com.ss.android.ugc.aweme.miniapp_api.model.b.a;
import com.ss.android.ugc.aweme.miniapp_api.model.b.b;
import com.ss.android.ugc.aweme.miniapp_api.model.e;
import com.ss.android.ugc.aweme.miniapp_api.model.g;
import com.ss.android.ugc.aweme.utils.v;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.AppbrandSupport;
import com.tt.miniapphost.appbase.listener.MiniAppPreloadListCheckListener;
import com.tt.miniapphost.entity.DisableStateEntity;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import com.tt.miniapphost.entity.PreLoadAppEntity;
import com.tt.miniapphost.host.HostDependManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public final class f {
  private static int a(int paramInt) {
    int i = paramInt;
    if (paramInt == 3)
      i = 1; 
    return i;
  }
  
  public static void a() {
    n n = MiniAppService.inst().getSettingsDepend();
    if (n != null && !n.b())
      return; 
    v.b(new Runnable() {
          public final void run() {
            AppbrandSupport.inst().preloadEmptyProcess();
          }
        });
  }
  
  public static void a(Context paramContext, String paramString1, String paramString2, boolean paramBoolean, String paramString3, String paramString4) {
    if (!TextUtils.isEmpty(paramString3)) {
      g g = (g)(new com.google.gson.f()).a(paramString4, g.class);
      if (TextUtils.equals(g.getChannel(), "awe_friend")) {
        (c.a()).a = g;
        (c.a()).c = paramString3;
      } 
    } 
    paramString1 = d.a(paramString1, paramString2, paramBoolean, "");
    if (TextUtils.isEmpty(paramString1))
      return; 
    a(paramContext, paramString1, (new b.a()).b("chat").a("share_chat").c("024001").a());
  }
  
  public static void a(String paramString) {
    if (!d.d(paramString))
      return; 
    String str = d.a(paramString);
    byte b = 1;
    if (d.e(paramString))
      b = 2; 
    a(str, b);
  }
  
  private static void a(String paramString, int paramInt) {
    if (!MiniAppService.inst().getSettingsDepend().a()) {
      AppBrandLogger.d("MiniAppUtils", new Object[] { "not enable preload" });
      return;
    } 
    if (TextUtils.isEmpty(paramString))
      return; 
    PreLoadAppEntity preLoadAppEntity = new PreLoadAppEntity();
    preLoadAppEntity.setAppid(paramString);
    preLoadAppEntity.setApptype(paramInt);
    ArrayList<PreLoadAppEntity> arrayList = new ArrayList();
    arrayList.add(preLoadAppEntity);
    AppbrandSupport.inst().preloadMiniApp(arrayList, Collections.emptyList());
  }
  
  public static void a(String paramString, int paramInt, Map<String, String> paramMap) {
    n n = MiniAppService.inst().getSettingsDepend();
    if (n != null && n.a()) {
      if (TextUtils.isEmpty(paramString))
        return; 
      paramInt = a(paramInt);
      PreLoadAppEntity preLoadAppEntity = new PreLoadAppEntity();
      preLoadAppEntity.setAppid(paramString);
      preLoadAppEntity.setApptype(paramInt);
      ArrayList<PreLoadAppEntity> arrayList = new ArrayList();
      arrayList.add(preLoadAppEntity);
      AppbrandSupport.inst().preloadMiniApp(arrayList, null, new MiniAppPreloadListCheckListener() {
            public final void onPreloadMiniAppListInvalid(String param1String) {}
          });
    } 
  }
  
  public static void a(String paramString1, String paramString2, String paramString3) {
    d d = d.a().a("mp_id", d.a(paramString1));
    if (d.c(paramString1)) {
      paramString1 = "micro_app";
    } else {
      paramString1 = "micro_game";
    } 
    g.a(paramString3, (d.a("_param_for_special", paramString1).a("enter_from", paramString2)).a);
  }
  
  public static boolean a(Context paramContext, e parame, b paramb) {
    JSONObject jSONObject;
    if (parame != null && parame.getType() == 3) {
      if (Build.VERSION.SDK_INT < 21 || !d.d(parame.getSchema())) {
        if (MiniAppService.inst().getRouterDepend() != null && !TextUtils.isEmpty(parame.getWebUrl()))
          try {
            jSONObject = new JSONObject();
            jSONObject.put("web_url", parame.getWebUrl());
            MiniAppService.inst().getRouterDepend().b(paramContext, jSONObject.toString());
          } catch (Exception exception) {} 
        return false;
      } 
      return a((Context)exception, d.a(parame), (b)jSONObject);
    } 
    return a((Context)exception, d.a(parame), (b)jSONObject);
  }
  
  public static boolean a(Context paramContext, String paramString) {
    if (!d.b(paramString))
      return false; 
    DisableStateEntity disableStateEntity = AppbrandConstants.getBundleManager().checkMiniAppDisableState(0);
    if (disableStateEntity != null) {
      if (paramContext == null || TextUtils.isEmpty(paramString) || !HostDependManager.getInst().handleAppbrandDisablePage(paramContext, paramString))
        HostDependManager.getInst().jumpToWebView(paramContext, disableStateEntity.getHintUrl(), "", true); 
      return false;
    } 
    return true;
  }
  
  public static boolean a(Context paramContext, String paramString, b paramb) {
    HashMap<Object, Object> hashMap;
    g g;
    String str1;
    String str2;
    String str3;
    com.ss.android.ugc.aweme.dfbase.c.f.a(paramContext);
    if (!b.c) {
      g.a(Toast.makeText(paramContext, paramContext.getString(2097742062), 0));
      return false;
    } 
    String str4 = "";
    if (paramb != null) {
      str1 = paramb.b;
    } else {
      str1 = "";
    } 
    if (paramb != null) {
      str2 = paramb.c;
    } else {
      str2 = "";
    } 
    if (paramb != null) {
      str3 = paramb.a;
    } else {
      str3 = "";
    } 
    if (paramb != null)
      str4 = paramb.f; 
    MicroSchemaEntity microSchemaEntity = MicroSchemaEntity.parseFromSchema(paramString);
    if (microSchemaEntity == null) {
      hashMap = new HashMap<Object, Object>();
      hashMap.put("uri", paramString);
      g = MiniAppService.inst().getMonitorDepend();
      if (g != null)
        g.a("mini_app_parse_fail", new JSONObject(hashMap)); 
      return false;
    } 
    if (a((String)g, paramb))
      microSchemaEntity.addCustomField("from", paramb.e); 
    if (!TextUtils.isEmpty(str2))
      microSchemaEntity.setScene(str2); 
    if (!TextUtils.isEmpty(str1))
      microSchemaEntity.addBdpLogField("launch_from", str1); 
    if (!TextUtils.isEmpty(str3))
      microSchemaEntity.addBdpLogField("location", str3); 
    if (!TextUtils.isEmpty(str4))
      microSchemaEntity.addBdpLogField("group_id", str4); 
    boolean bool = a((Context)hashMap, microSchemaEntity.toSchema());
    if (hashMap != null && bool)
      b((Context)hashMap, (String)g); 
    return bool;
  }
  
  private static boolean a(String paramString, b paramb) {
    a a = b(paramString, paramb);
    JSONObject jSONObject = new JSONObject();
    if (a != null) {
      try {
        jSONObject.put("log_extra", a.e);
        jSONObject.put("is_ad_event", "1");
      } catch (JSONException jSONException) {
        a.a((Exception)jSONException);
      } 
      if (TextUtils.equals(paramb.e, "open_url")) {
        g.onEvent(MobClick.obtain().setEventName("embeded_ad").setValue(a.b).setJsonObject(jSONObject).setLabelName("open_url_microapp"));
      } else {
        g.onEvent(MobClick.obtain().setEventName("embeded_ad").setValue(a.b).setJsonObject(jSONObject).setLabelName("micro_app_app"));
      } 
      return true;
    } 
    return false;
  }
  
  private static a b(String paramString, b paramb) {
    // Byte code:
    //   0: aload_0
    //   1: astore_3
    //   2: aload_0
    //   3: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   6: astore #5
    //   8: aload_0
    //   9: astore_3
    //   10: aload_0
    //   11: ldc_w 'UTF-8'
    //   14: invokestatic decode : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   17: astore #4
    //   19: aload #4
    //   21: astore_3
    //   22: aload #4
    //   24: invokestatic c : (Ljava/lang/String;)Z
    //   27: istore_2
    //   28: iload_2
    //   29: ifeq -> 90
    //   32: aload #4
    //   34: astore_3
    //   35: aload #5
    //   37: ldc_w 'start_page'
    //   40: invokevirtual getQueryParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   43: astore_0
    //   44: aload #4
    //   46: astore_3
    //   47: new java/lang/StringBuilder
    //   50: dup
    //   51: ldc_w 'start_page://'
    //   54: invokespecial <init> : (Ljava/lang/String;)V
    //   57: astore #5
    //   59: aload #4
    //   61: astore_3
    //   62: aload #5
    //   64: aload_0
    //   65: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: pop
    //   69: aload #4
    //   71: astore_3
    //   72: aload #5
    //   74: invokevirtual toString : ()Ljava/lang/String;
    //   77: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   80: ldc_w 'ad_params'
    //   83: invokevirtual getQueryParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   86: astore_0
    //   87: goto -> 209
    //   90: aload #4
    //   92: astore_3
    //   93: aload #4
    //   95: invokestatic e : (Ljava/lang/String;)Z
    //   98: ifeq -> 158
    //   101: aload #4
    //   103: astore_3
    //   104: aload #5
    //   106: ldc_w 'query'
    //   109: invokevirtual getQueryParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   112: astore_0
    //   113: aload #4
    //   115: astore_3
    //   116: aload_0
    //   117: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   120: ifne -> 158
    //   123: aload #4
    //   125: astore_3
    //   126: new org/json/JSONObject
    //   129: dup
    //   130: aload_0
    //   131: invokespecial <init> : (Ljava/lang/String;)V
    //   134: ldc_w 'ad_params'
    //   137: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   140: astore_0
    //   141: aload_0
    //   142: ldc_w 'UTF-8'
    //   145: invokestatic decode : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   148: astore_3
    //   149: aload_3
    //   150: astore_0
    //   151: goto -> 209
    //   154: astore_3
    //   155: goto -> 173
    //   158: aconst_null
    //   159: astore_0
    //   160: goto -> 209
    //   163: astore #5
    //   165: aconst_null
    //   166: astore_0
    //   167: aload_3
    //   168: astore #4
    //   170: aload #5
    //   172: astore_3
    //   173: new java/lang/StringBuilder
    //   176: dup
    //   177: invokespecial <init> : ()V
    //   180: astore #5
    //   182: aload #5
    //   184: aload_3
    //   185: invokevirtual getMessage : ()Ljava/lang/String;
    //   188: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: pop
    //   192: aload #5
    //   194: ldc_w 'schema is'
    //   197: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: pop
    //   201: aload #5
    //   203: aload #4
    //   205: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: pop
    //   209: aload_0
    //   210: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   213: ifeq -> 218
    //   216: aconst_null
    //   217: areturn
    //   218: new org/json/JSONObject
    //   221: dup
    //   222: aload_0
    //   223: invokespecial <init> : (Ljava/lang/String;)V
    //   226: astore #5
    //   228: aload #5
    //   230: ldc 'web_url'
    //   232: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   235: astore_0
    //   236: aload #5
    //   238: ldc_w 'web_title'
    //   241: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   244: astore_3
    //   245: aload #5
    //   247: ldc_w 'cid'
    //   250: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   253: astore #4
    //   255: aload #5
    //   257: ldc_w 'log_extra'
    //   260: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   263: astore #5
    //   265: aload_1
    //   266: getfield e : Ljava/lang/String;
    //   269: astore #6
    //   271: new com/ss/android/ugc/aweme/miniapp_api/model/b/a$a
    //   274: dup
    //   275: invokespecial <init> : ()V
    //   278: astore_1
    //   279: aload_1
    //   280: aload #4
    //   282: putfield b : Ljava/lang/String;
    //   285: aload_1
    //   286: aload_0
    //   287: putfield a : Ljava/lang/String;
    //   290: aload_1
    //   291: aload #6
    //   293: putfield d : Ljava/lang/String;
    //   296: aload_1
    //   297: aload #5
    //   299: putfield e : Ljava/lang/String;
    //   302: aload_1
    //   303: aload_3
    //   304: putfield c : Ljava/lang/String;
    //   307: new com/ss/android/ugc/aweme/miniapp_api/model/b/a
    //   310: dup
    //   311: invokespecial <init> : ()V
    //   314: astore_0
    //   315: aload_0
    //   316: aload_1
    //   317: getfield b : Ljava/lang/String;
    //   320: putfield b : Ljava/lang/String;
    //   323: aload_0
    //   324: aload_1
    //   325: getfield d : Ljava/lang/String;
    //   328: putfield d : Ljava/lang/String;
    //   331: aload_0
    //   332: aload_1
    //   333: getfield a : Ljava/lang/String;
    //   336: putfield a : Ljava/lang/String;
    //   339: aload_0
    //   340: aload_1
    //   341: getfield c : Ljava/lang/String;
    //   344: putfield c : Ljava/lang/String;
    //   347: aload_0
    //   348: aload_1
    //   349: getfield e : Ljava/lang/String;
    //   352: putfield e : Ljava/lang/String;
    //   355: aload_0
    //   356: areturn
    //   357: aconst_null
    //   358: areturn
    //   359: astore_0
    //   360: goto -> 357
    // Exception table:
    //   from	to	target	type
    //   2	8	163	java/lang/Exception
    //   10	19	163	java/lang/Exception
    //   22	28	163	java/lang/Exception
    //   35	44	163	java/lang/Exception
    //   47	59	163	java/lang/Exception
    //   62	69	163	java/lang/Exception
    //   72	87	163	java/lang/Exception
    //   93	101	163	java/lang/Exception
    //   104	113	163	java/lang/Exception
    //   116	123	163	java/lang/Exception
    //   126	141	163	java/lang/Exception
    //   141	149	154	java/lang/Exception
    //   218	355	359	org/json/JSONException
  }
  
  private static boolean b(Context paramContext, String paramString) {
    AppbrandSupport.inst().openAppbrand(paramString);
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniap\\utils\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */