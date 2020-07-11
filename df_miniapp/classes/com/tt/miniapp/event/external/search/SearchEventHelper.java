package com.tt.miniapp.event.external.search;

import android.net.Uri;
import android.text.TextUtils;
import com.tt.miniapp.event.Event;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.TimeMeter;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchEventHelper {
  private static volatile JSONObject sGdExtJson;
  
  private static volatile long sLoadFinishedTime;
  
  private static volatile long sLoadTime;
  
  private static volatile boolean sNeedReportStayPage;
  
  private static JSONObject checkIsNeedReport() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/event/external/search/SearchEventHelper.sGdExtJson : Lorg/json/JSONObject;
    //   3: ifnonnull -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: ldc 'SearchEventHelper'
    //   10: monitorenter
    //   11: getstatic com/tt/miniapp/event/external/search/SearchEventHelper.sGdExtJson : Lorg/json/JSONObject;
    //   14: ifnonnull -> 22
    //   17: ldc 'SearchEventHelper'
    //   19: monitorexit
    //   20: aconst_null
    //   21: areturn
    //   22: getstatic com/tt/miniapp/event/external/search/SearchEventHelper.sGdExtJson : Lorg/json/JSONObject;
    //   25: astore_0
    //   26: ldc 'SearchEventHelper'
    //   28: monitorexit
    //   29: aload_0
    //   30: areturn
    //   31: astore_0
    //   32: ldc 'SearchEventHelper'
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    // Exception table:
    //   from	to	target	type
    //   11	20	31	finally
    //   22	29	31	finally
    //   32	35	31	finally
  }
  
  public static String failReasonClient(String paramString) {
    return "SDK ERROR";
  }
  
  public static void init(String paramString1, String paramString2) {
    StringBuilder stringBuilder2;
    StringBuilder stringBuilder3 = new StringBuilder("init: ");
    stringBuilder3.append(paramString1);
    stringBuilder3.append(", ");
    stringBuilder3.append(paramString2);
    AppBrandLogger.d("SearchEventHelper", new Object[] { stringBuilder3.toString() });
    setGdExtJson(null);
    if (!"search_result".equals(paramString1) && !"search_aladdin".equals(paramString1) && !"toutiao_search".equals(paramString1) && !"byte_search".equals(paramString1) && !"search".equals(paramString1)) {
      stringBuilder2 = new StringBuilder("init need not: ");
      stringBuilder2.append(paramString1);
      AppBrandLogger.d("SearchEventHelper", new Object[] { stringBuilder2.toString() });
      return;
    } 
    if (TextUtils.isEmpty(stringBuilder2)) {
      AppBrandLogger.d("SearchEventHelper", new Object[] { "init need not: empty Schema" });
      return;
    } 
    paramString1 = Uri.parse((String)stringBuilder2).getQueryParameter("gd_ext_json");
    if (TextUtils.isEmpty(paramString1)) {
      AppBrandLogger.d("SearchEventHelper", new Object[] { "init need not: no gd_ext_json" });
      return;
    } 
    try {
      setGdExtJson(new JSONObject(paramString1));
      sNeedReportStayPage = false;
      AppBrandLogger.d("SearchEventHelper", new Object[] { "init need report" });
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("SearchEventHelper", "init exp: build json", (Throwable)jSONException);
    } 
    StringBuilder stringBuilder1 = new StringBuilder("init finished: ");
    stringBuilder1.append(sGdExtJson);
    AppBrandLogger.d("SearchEventHelper", new Object[] { stringBuilder1.toString() });
  }
  
  public static void loadDetailEvent(boolean paramBoolean1, boolean paramBoolean2, long paramLong, int paramInt, String paramString) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.useAs(TypeTransformer.java:868)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:668)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static void onLoadStart() {
    AppBrandLogger.d("SearchEventHelper", new Object[] { "onLoadStart: " });
    loadDetailEvent(true, false, 0L, 2, "TIMEOUT ERROR");
  }
  
  private static void onLoadSucceed(long paramLong) {
    sLoadTime = paramLong;
    sLoadFinishedTime = TimeMeter.currentMillis();
    sNeedReportStayPage = true;
    stayPageEvent(true);
  }
  
  public static void onRelaunched() {
    AppBrandLogger.d("SearchEventHelper", new Object[] { "onRelaunched: " });
    loadDetailEvent(false, true, 0L, 1, CharacterUtils.empty());
  }
  
  private static void setGdExtJson(JSONObject paramJSONObject) {
    // Byte code:
    //   0: ldc 'SearchEventHelper'
    //   2: monitorenter
    //   3: aload_0
    //   4: putstatic com/tt/miniapp/event/external/search/SearchEventHelper.sGdExtJson : Lorg/json/JSONObject;
    //   7: ldc 'SearchEventHelper'
    //   9: monitorexit
    //   10: return
    //   11: astore_0
    //   12: ldc 'SearchEventHelper'
    //   14: monitorexit
    //   15: aload_0
    //   16: athrow
    // Exception table:
    //   from	to	target	type
    //   3	10	11	finally
    //   12	15	11	finally
  }
  
  public static void stayPageEvent(boolean paramBoolean) {
    long l;
    StringBuilder stringBuilder;
    JSONObject jSONObject = checkIsNeedReport();
    if (jSONObject == null || !sNeedReportStayPage) {
      stringBuilder = new StringBuilder("stayPageEvent: need not: isInner=");
      stringBuilder.append(paramBoolean);
      AppBrandLogger.d("SearchEventHelper", new Object[] { stringBuilder.toString() });
      return;
    } 
    if (!paramBoolean) {
      sNeedReportStayPage = false;
      setGdExtJson(null);
    } 
    if (paramBoolean) {
      l = 0L;
    } else {
      l = TimeMeter.nowDiff(sLoadFinishedTime);
    } 
    Event.Builder builder = Event.builder("stay_page").addKVJsonObject((JSONObject)stringBuilder).kv("group_from", Integer.valueOf(2)).kv("load_time", Long.valueOf(sLoadTime)).kv("read_time", Long.valueOf(l)).kv("stay_time", Long.valueOf(sLoadTime + l));
    if (paramBoolean) {
      builder.kv("__inner_handled", Boolean.valueOf(true));
    } else {
      AppBrandLogger.d("SearchEventHelper", new Object[] { "stayPageEvent: reported" });
    } 
    builder.flush();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\external\search\SearchEventHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */