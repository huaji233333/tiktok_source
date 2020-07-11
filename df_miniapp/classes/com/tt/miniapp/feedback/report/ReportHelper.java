package com.tt.miniapp.feedback.report;

import android.content.Context;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.JsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReportHelper {
  private static ReportConfig sConfig;
  
  static String getInfringementImg() {
    if (sConfig == null)
      init(); 
    return sConfig.infringementTodoTipImg;
  }
  
  static int getInfringementType() {
    if (sConfig == null)
      init(); 
    return sConfig.infringementType;
  }
  
  static String getPlagiarizeImg() {
    if (sConfig == null)
      init(); 
    return sConfig.plagiarizeOriginalLinkImg;
  }
  
  static int getPlagiarizeType() {
    if (sConfig == null)
      init(); 
    return sConfig.plagiarizeType;
  }
  
  public static long getReportItemId() {
    if (sConfig == null)
      init(); 
    return sConfig.id;
  }
  
  public static int getTitleResId() {
    return isReportOpen() ? 2097741912 : 2097741911;
  }
  
  public static void init() {
    sConfig = new ReportConfig(SettingsDAO.getJSONObject((Context)AppbrandContext.getInst().getApplicationContext(), new Enum[] { (Enum)Settings.BDP_FEEDBACK_REPORT }));
  }
  
  public static boolean isReportOpen() {
    if (sConfig == null)
      init(); 
    return sConfig.isOpen;
  }
  
  public static void monitor(int paramInt, String paramString1, String paramString2) {
    AppBrandMonitor.statusRate("mp_image_load_error", paramInt, (new JsonBuilder()).put("img_url", paramString1).put("errMsg", paramString2).build());
  }
  
  public static void tryInsertReportItem(JSONArray paramJSONArray) {
    // Byte code:
    //   0: invokestatic isReportOpen : ()Z
    //   3: ifeq -> 135
    //   6: aload_0
    //   7: ifnull -> 135
    //   10: aload_0
    //   11: invokevirtual length : ()I
    //   14: ifgt -> 18
    //   17: return
    //   18: new com/tt/miniapphost/util/JsonBuilder
    //   21: dup
    //   22: aload_0
    //   23: iconst_0
    //   24: invokevirtual optJSONObject : (I)Lorg/json/JSONObject;
    //   27: invokevirtual toString : ()Ljava/lang/String;
    //   30: invokespecial <init> : (Ljava/lang/String;)V
    //   33: ldc 'id'
    //   35: getstatic com/tt/miniapp/feedback/report/ReportHelper.sConfig : Lcom/tt/miniapp/feedback/report/ReportHelper$ReportConfig;
    //   38: getfield id : J
    //   41: invokestatic valueOf : (J)Ljava/lang/Long;
    //   44: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lcom/tt/miniapphost/util/JsonBuilder;
    //   47: ldc 'name'
    //   49: getstatic com/tt/miniapp/feedback/report/ReportHelper.sConfig : Lcom/tt/miniapp/feedback/report/ReportHelper$ReportConfig;
    //   52: getfield name : Ljava/lang/String;
    //   55: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lcom/tt/miniapphost/util/JsonBuilder;
    //   58: invokevirtual build : ()Lorg/json/JSONObject;
    //   61: astore_3
    //   62: getstatic com/tt/miniapp/feedback/report/ReportHelper.sConfig : Lcom/tt/miniapp/feedback/report/ReportHelper$ReportConfig;
    //   65: getfield pos : I
    //   68: istore_2
    //   69: iload_2
    //   70: iflt -> 83
    //   73: iload_2
    //   74: istore_1
    //   75: iload_2
    //   76: aload_0
    //   77: invokevirtual length : ()I
    //   80: if_icmplt -> 88
    //   83: aload_0
    //   84: invokevirtual length : ()I
    //   87: istore_1
    //   88: aload_0
    //   89: invokevirtual length : ()I
    //   92: istore_2
    //   93: iload_2
    //   94: iload_1
    //   95: if_icmple -> 118
    //   98: aload_0
    //   99: iload_2
    //   100: aload_0
    //   101: iload_2
    //   102: iconst_1
    //   103: isub
    //   104: invokevirtual get : (I)Ljava/lang/Object;
    //   107: invokevirtual put : (ILjava/lang/Object;)Lorg/json/JSONArray;
    //   110: pop
    //   111: iload_2
    //   112: iconst_1
    //   113: isub
    //   114: istore_2
    //   115: goto -> 93
    //   118: aload_0
    //   119: iload_1
    //   120: aload_3
    //   121: invokevirtual put : (ILjava/lang/Object;)Lorg/json/JSONArray;
    //   124: pop
    //   125: return
    //   126: astore_0
    //   127: ldc 'ReportHelper'
    //   129: ldc 'insert report json exp'
    //   131: aload_0
    //   132: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   135: return
    // Exception table:
    //   from	to	target	type
    //   88	93	126	org/json/JSONException
    //   98	111	126	org/json/JSONException
    //   118	125	126	org/json/JSONException
  }
  
  static class ReportConfig {
    long id = -1L;
    
    String infringementTodoTipImg = CharacterUtils.empty();
    
    int infringementType = 314;
    
    boolean isOpen;
    
    String name = CharacterUtils.empty();
    
    String plagiarizeOriginalLinkImg = CharacterUtils.empty();
    
    int plagiarizeType = 321;
    
    int pos = -1;
    
    ReportConfig(JSONObject param1JSONObject) {
      if (param1JSONObject == null)
        return; 
      boolean bool = false;
      if (param1JSONObject.optInt("state", 0) != 0)
        bool = true; 
      this.isOpen = bool;
      this.name = param1JSONObject.optString("name", this.name);
      this.pos = param1JSONObject.optInt("pos", this.pos);
      this.id = param1JSONObject.optLong("id", this.id);
      this.plagiarizeType = param1JSONObject.optInt("plagiarize_type", this.plagiarizeType);
      this.infringementType = param1JSONObject.optInt("infringement_type", this.infringementType);
      this.plagiarizeOriginalLinkImg = param1JSONObject.optString("plagiarize_original_link_img", this.plagiarizeOriginalLinkImg);
      this.infringementTodoTipImg = param1JSONObject.optString("infringement_todo_tip_img", this.infringementTodoTipImg);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\report\ReportHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */