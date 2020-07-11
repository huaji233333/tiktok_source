package com.tt.miniapp.feedback.report;

import android.text.TextUtils;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.feedback.entrance.vo.FeedbackParam;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.q.i;
import java.util.List;
import org.json.JSONObject;

public class ReportNetHelper {
  private static volatile boolean sIsRequestingOption;
  
  private static volatile ReportCallback sOptionCb;
  
  private static volatile JSONObject sReportOptionCache;
  
  private static void callback(final ReportCallback cb, final JSONObject response) {
    ThreadUtil.runOnUIThread(new Runnable() {
          public final void run() {
            cb.onResponse(response);
          }
        });
  }
  
  public static void clearOptionCache() {
    sReportOptionCache = null;
    sOptionCb = null;
  }
  
  private static String getReportFrom(String paramString) {
    return "feed".equalsIgnoreCase(paramString) ? "feed" : "common";
  }
  
  static JSONObject getReportOptionCache() {
    return sReportOptionCache;
  }
  
  private static int getSource(boolean paramBoolean, String paramString) {
    return paramBoolean ? ("feed".equalsIgnoreCase(paramString) ? 218 : 217) : ("feed".equalsIgnoreCase(paramString) ? 220 : 219);
  }
  
  static void requestReportOption(FeedbackParam paramFeedbackParam, ReportCallback paramReportCallback) {
    // Byte code:
    //   0: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sReportOptionCache : Lorg/json/JSONObject;
    //   3: ifnull -> 38
    //   6: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sReportOptionCache : Lorg/json/JSONObject;
    //   12: ifnull -> 26
    //   15: aload_1
    //   16: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sReportOptionCache : Lorg/json/JSONObject;
    //   19: invokestatic callback : (Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;Lorg/json/JSONObject;)V
    //   22: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   24: monitorexit
    //   25: return
    //   26: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   28: monitorexit
    //   29: goto -> 38
    //   32: astore_0
    //   33: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   35: monitorexit
    //   36: aload_0
    //   37: athrow
    //   38: aload_1
    //   39: ifnull -> 61
    //   42: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   44: monitorenter
    //   45: aload_1
    //   46: putstatic com/tt/miniapp/feedback/report/ReportNetHelper.sOptionCb : Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;
    //   49: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   51: monitorexit
    //   52: goto -> 61
    //   55: astore_0
    //   56: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   58: monitorexit
    //   59: aload_0
    //   60: athrow
    //   61: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sIsRequestingOption : Z
    //   64: ifeq -> 68
    //   67: return
    //   68: iconst_1
    //   69: putstatic com/tt/miniapp/feedback/report/ReportNetHelper.sIsRequestingOption : Z
    //   72: new java/lang/StringBuilder
    //   75: dup
    //   76: invokespecial <init> : ()V
    //   79: astore_1
    //   80: aload_1
    //   81: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandConstant$SnssdkAPI;
    //   84: invokevirtual getReportOption : ()Ljava/lang/String;
    //   87: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: pop
    //   91: aload_1
    //   92: aload_0
    //   93: aload_0
    //   94: invokevirtual getFeedbackAppkey : ()Ljava/lang/String;
    //   97: aload_0
    //   98: invokevirtual getFeedbackAid : ()Ljava/lang/String;
    //   101: aload_0
    //   102: invokevirtual getFeedbackAppName : ()Ljava/lang/String;
    //   105: invokevirtual toParamString : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   108: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: pop
    //   112: invokestatic getInst : ()Lcom/tt/miniapphost/language/LocaleManager;
    //   115: invokevirtual getCurrentLocale : ()Ljava/util/Locale;
    //   118: astore_2
    //   119: aload_2
    //   120: ifnull -> 141
    //   123: aload_2
    //   124: invokevirtual getLanguage : ()Ljava/lang/String;
    //   127: astore_2
    //   128: aload_1
    //   129: ldc '&lang='
    //   131: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: pop
    //   135: aload_1
    //   136: aload_2
    //   137: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: pop
    //   141: aload_1
    //   142: ldc '&source='
    //   144: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: pop
    //   148: aload_1
    //   149: aload_0
    //   150: invokevirtual isGame : ()Z
    //   153: aload_0
    //   154: invokevirtual getLaunchFrom : ()Ljava/lang/String;
    //   157: invokestatic getSource : (ZLjava/lang/String;)I
    //   160: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   163: pop
    //   164: invokestatic getInst : ()Lcom/tt/miniapp/manager/NetManager;
    //   167: new com/tt/option/q/i
    //   170: dup
    //   171: aload_1
    //   172: invokevirtual toString : ()Ljava/lang/String;
    //   175: ldc 'GET'
    //   177: iconst_0
    //   178: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Z)V
    //   181: invokevirtual request : (Lcom/tt/option/q/i;)Lcom/tt/option/q/j;
    //   184: invokevirtual a : ()Ljava/lang/String;
    //   187: astore_0
    //   188: iconst_0
    //   189: putstatic com/tt/miniapp/feedback/report/ReportNetHelper.sIsRequestingOption : Z
    //   192: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   194: monitorenter
    //   195: new com/tt/miniapphost/util/JsonBuilder
    //   198: dup
    //   199: aload_0
    //   200: invokespecial <init> : (Ljava/lang/String;)V
    //   203: invokevirtual build : ()Lorg/json/JSONObject;
    //   206: putstatic com/tt/miniapp/feedback/report/ReportNetHelper.sReportOptionCache : Lorg/json/JSONObject;
    //   209: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sOptionCb : Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;
    //   212: ifnull -> 228
    //   215: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sOptionCb : Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;
    //   218: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sReportOptionCache : Lorg/json/JSONObject;
    //   221: invokestatic callback : (Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;Lorg/json/JSONObject;)V
    //   224: aconst_null
    //   225: putstatic com/tt/miniapp/feedback/report/ReportNetHelper.sOptionCb : Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;
    //   228: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   230: monitorexit
    //   231: return
    //   232: astore_0
    //   233: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   235: monitorexit
    //   236: aload_0
    //   237: athrow
    //   238: astore_0
    //   239: iconst_0
    //   240: putstatic com/tt/miniapp/feedback/report/ReportNetHelper.sIsRequestingOption : Z
    //   243: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   245: monitorenter
    //   246: new com/tt/miniapphost/util/JsonBuilder
    //   249: dup
    //   250: aconst_null
    //   251: invokespecial <init> : (Ljava/lang/String;)V
    //   254: invokevirtual build : ()Lorg/json/JSONObject;
    //   257: putstatic com/tt/miniapp/feedback/report/ReportNetHelper.sReportOptionCache : Lorg/json/JSONObject;
    //   260: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sOptionCb : Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;
    //   263: ifnull -> 279
    //   266: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sOptionCb : Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;
    //   269: getstatic com/tt/miniapp/feedback/report/ReportNetHelper.sReportOptionCache : Lorg/json/JSONObject;
    //   272: invokestatic callback : (Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;Lorg/json/JSONObject;)V
    //   275: aconst_null
    //   276: putstatic com/tt/miniapp/feedback/report/ReportNetHelper.sOptionCb : Lcom/tt/miniapp/feedback/report/ReportNetHelper$ReportCallback;
    //   279: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   281: monitorexit
    //   282: aload_0
    //   283: athrow
    //   284: astore_0
    //   285: ldc com/tt/miniapp/feedback/report/ReportNetHelper
    //   287: monitorexit
    //   288: aload_0
    //   289: athrow
    // Exception table:
    //   from	to	target	type
    //   9	25	32	finally
    //   26	29	32	finally
    //   33	36	32	finally
    //   45	52	55	finally
    //   56	59	55	finally
    //   72	119	238	finally
    //   123	141	238	finally
    //   141	188	238	finally
    //   195	228	232	finally
    //   228	231	232	finally
    //   233	236	232	finally
    //   246	279	284	finally
    //   279	282	284	finally
    //   285	288	284	finally
  }
  
  static void submitReport(FeedbackParam paramFeedbackParam, String paramString1, ReportOptionAdapter.ItemVO paramItemVO, String paramString2, String paramString3, List<String> paramList, ReportCallback paramReportCallback) {
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(AppbrandConstant.SnssdkAPI.getInst().getReportContent());
    String str2 = paramFeedbackParam.getHostAppKey();
    String str3 = paramFeedbackParam.getHostAid();
    String str4 = paramFeedbackParam.getHostAppName();
    String str5 = CharacterUtils.splitCharByPoints(paramFeedbackParam.getHostAppVersion());
    String str6 = DevicesUtil.getSystem();
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append(DevicesUtil.getBrand());
    stringBuilder3.append("+");
    stringBuilder3.append(DevicesUtil.getModel());
    stringBuilder2.append(paramFeedbackParam.toParamString(str2, str3, str4, str5, str6, stringBuilder3.toString(), paramFeedbackParam.getHostAppUpdateVersion()));
    i i = new i(stringBuilder2.toString(), "POST", false);
    String str1 = HostProcessBridge.getLoginCookie();
    if (!TextUtils.isEmpty(str1))
      i.a("Cookie", str1); 
    JsonBuilder jsonBuilder2 = (new JsonBuilder()).put("mp_id", paramFeedbackParam.getAid()).put("mp_name", paramFeedbackParam.getAppName()).put("mp_type", Integer.valueOf(paramFeedbackParam.getType())).put("mp_path", paramFeedbackParam.getPath()).put("mp_query", paramFeedbackParam.getQuery()).put("feedback_title", paramItemVO.text);
    if (paramFeedbackParam.getVersionType() == null) {
      str1 = "current";
    } else {
      str1 = paramFeedbackParam.getVersionType();
    } 
    JsonBuilder jsonBuilder1 = jsonBuilder2.put("mp_version_type", str1);
    if (!TextUtils.isEmpty(paramString2))
      jsonBuilder1.put("origin_article_uri", paramString2); 
    if (!TextUtils.isEmpty(paramString1))
      jsonBuilder1.put("openId", paramString1); 
    JSONObject jSONObject = jsonBuilder1.build();
    StringBuilder stringBuilder1 = new StringBuilder();
    if (paramList != null && paramList.size() > 0) {
      int j;
      for (j = 0; j < paramList.size(); j++) {
        if (j != 0)
          stringBuilder1.append(","); 
        stringBuilder1.append(paramList.get(j));
      } 
    } 
    i.a("group_id", paramFeedbackParam.getTtId());
    i.a("report_from", getReportFrom(paramFeedbackParam.getLaunchFrom()));
    i.a("report_types", Integer.valueOf(paramItemVO.type));
    i.a("description", paramString3);
    i.a("source", Integer.valueOf(getSource(paramFeedbackParam.isGame(), paramFeedbackParam.getLaunchFrom())));
    i.a("evidence_urls", stringBuilder1.toString());
    i.a("app_key", paramFeedbackParam.getHostAppKey());
    i.a("extra", jSONObject);
    try {
      callback(paramReportCallback, (new JsonBuilder(HostDependManager.getInst().doPostUrlEncoded(i).a())).build());
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("ReportNetHelper", new Object[] { exception });
      callback(paramReportCallback, (new JsonBuilder()).build());
      return;
    } 
  }
  
  public static interface ReportCallback {
    void onResponse(JSONObject param1JSONObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\report\ReportNetHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */