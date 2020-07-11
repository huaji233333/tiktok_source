package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.option.e.e;

public class ApiGetTimingSettingCtrl extends b {
  public ApiGetTimingSettingCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    // Byte code:
    //   0: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandConstant$OpenApi;
    //   3: pop
    //   4: ldc 'getHostSettings'
    //   6: invokestatic isDataHandlerExist : (Ljava/lang/String;)Z
    //   9: istore_2
    //   10: bipush #30
    //   12: istore_1
    //   13: ldc 'https://i.snssdk.com/api/apps/report_duration'
    //   15: astore_3
    //   16: iload_2
    //   17: ifeq -> 148
    //   20: invokestatic getHostSettings : ()Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;
    //   23: astore #4
    //   25: aload #4
    //   27: ifnull -> 148
    //   30: aload #4
    //   32: ldc 'jsonData'
    //   34: invokevirtual getJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   37: ldc 'js_timing_settings'
    //   39: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   42: astore #4
    //   44: aload #4
    //   46: ifnull -> 148
    //   49: aload #4
    //   51: ldc 'switch'
    //   53: iconst_0
    //   54: invokevirtual optBoolean : (Ljava/lang/String;Z)Z
    //   57: istore_2
    //   58: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandConstant$OpenApi;
    //   61: pop
    //   62: aload #4
    //   64: ldc 'url'
    //   66: ldc 'https://i.snssdk.com/api/apps/report_duration'
    //   68: invokevirtual optString : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   71: astore_3
    //   72: aload #4
    //   74: ldc 'interval'
    //   76: bipush #30
    //   78: invokevirtual optInt : (Ljava/lang/String;I)I
    //   81: istore_1
    //   82: goto -> 85
    //   85: new org/json/JSONObject
    //   88: dup
    //   89: invokespecial <init> : ()V
    //   92: astore #4
    //   94: aload #4
    //   96: ldc 'switch'
    //   98: iload_2
    //   99: invokevirtual put : (Ljava/lang/String;Z)Lorg/json/JSONObject;
    //   102: pop
    //   103: aload #4
    //   105: ldc 'url'
    //   107: aload_3
    //   108: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   111: pop
    //   112: aload #4
    //   114: ldc 'interval'
    //   116: iload_1
    //   117: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   120: pop
    //   121: aload_0
    //   122: aload #4
    //   124: invokevirtual callbackOk : (Lorg/json/JSONObject;)V
    //   127: return
    //   128: astore_3
    //   129: ldc 'ApiGetTimingSettingCtrl'
    //   131: iconst_1
    //   132: anewarray java/lang/Object
    //   135: dup
    //   136: iconst_0
    //   137: aload_3
    //   138: aastore
    //   139: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   142: aload_0
    //   143: aload_3
    //   144: invokevirtual callbackFail : (Ljava/lang/Throwable;)V
    //   147: return
    //   148: iconst_0
    //   149: istore_2
    //   150: goto -> 85
    // Exception table:
    //   from	to	target	type
    //   4	10	128	java/lang/Exception
    //   20	25	128	java/lang/Exception
    //   30	44	128	java/lang/Exception
    //   49	82	128	java/lang/Exception
    //   85	127	128	java/lang/Exception
  }
  
  public String getActionName() {
    return "getTimingSettings";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetTimingSettingCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */