package com.ss.android.ugc.aweme.miniapp.f.a;

import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import org.json.JSONObject;

public final class c implements ISyncHostDataHandler {
  private a a = new a();
  
  public static void a(String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2, JSONObject paramJSONObject) {
    HostProcessBridge.hostActionSync("actionVILog", CrossProcessDataEntity.Builder.create().put("category", paramString1).put("logEventName", paramString2).put("labelName", paramString3).put("logVIValue", Long.valueOf(paramLong1)).put("logVIExtValue", Long.valueOf(0L)).put("logEventData", paramJSONObject).build());
  }
  
  public final CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity) {
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: aload_1
    //   3: ifnonnull -> 22
    //   6: ldc 'HostActionSyncHandler'
    //   8: iconst_1
    //   9: anewarray java/lang/Object
    //   12: dup
    //   13: iconst_0
    //   14: ldc 'callData == null'
    //   16: aastore
    //   17: invokestatic outputError : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   20: aconst_null
    //   21: areturn
    //   22: aload_1
    //   23: ldc 'hostActionType'
    //   25: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   28: astore #6
    //   30: aload_1
    //   31: ldc 'hostActionData'
    //   33: invokevirtual getCrossProcessDataEntity : (Ljava/lang/String;)Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;
    //   36: astore_1
    //   37: aload #6
    //   39: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   42: ifeq -> 61
    //   45: ldc 'HostActionSyncHandler'
    //   47: iconst_1
    //   48: anewarray java/lang/Object
    //   51: dup
    //   52: iconst_0
    //   53: ldc 'TextUtils.isEmpty(hostCallType)'
    //   55: aastore
    //   56: invokestatic outputError : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   59: aconst_null
    //   60: areturn
    //   61: iconst_m1
    //   62: istore_2
    //   63: aload #6
    //   65: invokevirtual hashCode : ()I
    //   68: lookupswitch default -> 136, -1801325650 -> 230, 41118167 -> 215, 337629329 -> 199, 384954139 -> 184, 1270009270 -> 169, 1574122267 -> 154, 1657545787 -> 139
    //   136: goto -> 242
    //   139: aload #6
    //   141: ldc 'login_state'
    //   143: invokevirtual equals : (Ljava/lang/Object;)Z
    //   146: ifeq -> 242
    //   149: iconst_5
    //   150: istore_2
    //   151: goto -> 242
    //   154: aload #6
    //   156: ldc 'actionVILog'
    //   158: invokevirtual equals : (Ljava/lang/Object;)Z
    //   161: ifeq -> 242
    //   164: iconst_0
    //   165: istore_2
    //   166: goto -> 242
    //   169: aload #6
    //   171: ldc 'exciting_video_open_url'
    //   173: invokevirtual equals : (Ljava/lang/Object;)Z
    //   176: ifeq -> 242
    //   179: iconst_1
    //   180: istore_2
    //   181: goto -> 242
    //   184: aload #6
    //   186: ldc 'live_ad_web_url'
    //   188: invokevirtual equals : (Ljava/lang/Object;)Z
    //   191: ifeq -> 242
    //   194: iconst_3
    //   195: istore_2
    //   196: goto -> 242
    //   199: aload #6
    //   201: ldc 'micro_app_lifecycle'
    //   203: invokevirtual equals : (Ljava/lang/Object;)Z
    //   206: ifeq -> 242
    //   209: bipush #6
    //   211: istore_2
    //   212: goto -> 242
    //   215: aload #6
    //   217: ldc 'share_info_params'
    //   219: invokevirtual equals : (Ljava/lang/Object;)Z
    //   222: ifeq -> 242
    //   225: iconst_4
    //   226: istore_2
    //   227: goto -> 242
    //   230: aload #6
    //   232: ldc 'mini_app_ad_web_url'
    //   234: invokevirtual equals : (Ljava/lang/Object;)Z
    //   237: ifeq -> 242
    //   240: iconst_2
    //   241: istore_2
    //   242: iload_2
    //   243: tableswitch default -> 284, 0 -> 913, 1 -> 475, 2 -> 402, 3 -> 374, 4 -> 354, 5 -> 308, 6 -> 286
    //   284: aconst_null
    //   285: areturn
    //   286: invokestatic b : ()Lcom/ss/android/ugc/aweme/miniapp_api/services/c;
    //   289: getfield d : Lcom/ss/android/ugc/aweme/miniapp_api/b/b/a;
    //   292: ifnull -> 918
    //   295: invokestatic b : ()Lcom/ss/android/ugc/aweme/miniapp_api/services/c;
    //   298: getfield d : Lcom/ss/android/ugc/aweme/miniapp_api/b/b/a;
    //   301: invokeinterface a : ()V
    //   306: aconst_null
    //   307: areturn
    //   308: aload_1
    //   309: ldc 'login_state_value'
    //   311: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   314: astore_1
    //   315: getstatic com/ss/android/ugc/aweme/miniapp/b.b : Lcom/ss/android/ugc/aweme/miniapp/b$a;
    //   318: astore #6
    //   320: aload_1
    //   321: ldc '1'
    //   323: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   326: ifeq -> 337
    //   329: aload #6
    //   331: iconst_1
    //   332: putfield a : Z
    //   335: aconst_null
    //   336: areturn
    //   337: aload_1
    //   338: ldc '0'
    //   340: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   343: ifeq -> 918
    //   346: aload #6
    //   348: iconst_0
    //   349: putfield a : Z
    //   352: aconst_null
    //   353: areturn
    //   354: invokestatic a : ()Lcom/ss/android/ugc/aweme/miniapp_api/c;
    //   357: getfield c : Ljava/lang/String;
    //   360: astore_1
    //   361: invokestatic create : ()Lcom/tt/miniapphost/process/data/CrossProcessDataEntity$Builder;
    //   364: ldc 'share_info_value'
    //   366: aload_1
    //   367: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lcom/tt/miniapphost/process/data/CrossProcessDataEntity$Builder;
    //   370: invokevirtual build : ()Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;
    //   373: areturn
    //   374: aload_1
    //   375: ldc 'hostActionData'
    //   377: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   380: astore_1
    //   381: invokestatic inst : ()Lcom/ss/android/ugc/aweme/miniapp/MiniAppService;
    //   384: invokevirtual getRouterDepend : ()Lcom/ss/android/ugc/aweme/miniapp_api/a/l;
    //   387: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   390: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   393: aload_1
    //   394: invokeinterface b : (Landroid/content/Context;Ljava/lang/String;)Z
    //   399: pop
    //   400: aconst_null
    //   401: areturn
    //   402: aload_1
    //   403: ldc 'hostActionData'
    //   405: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   408: astore_1
    //   409: new org/json/JSONObject
    //   412: dup
    //   413: aload_1
    //   414: invokespecial <init> : (Ljava/lang/String;)V
    //   417: astore #7
    //   419: aload #7
    //   421: ldc 'web_url'
    //   423: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   426: astore_1
    //   427: aload #7
    //   429: ldc 'web_title'
    //   431: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   434: astore #6
    //   436: aload #7
    //   438: ldc 'target_class'
    //   440: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   443: astore #7
    //   445: aload #7
    //   447: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   450: ifne -> 461
    //   453: invokestatic a : ()Lcom/ss/android/ugc/aweme/miniapp_api/c;
    //   456: aload #7
    //   458: putfield d : Ljava/lang/String;
    //   461: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   464: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   467: aload_1
    //   468: aload #6
    //   470: invokestatic a : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    //   473: aconst_null
    //   474: areturn
    //   475: aload_1
    //   476: ldc 'hostActionData'
    //   478: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   481: astore_1
    //   482: new org/json/JSONObject
    //   485: dup
    //   486: aload_1
    //   487: invokespecial <init> : (Ljava/lang/String;)V
    //   490: astore #12
    //   492: aload #12
    //   494: ldc 'open_url'
    //   496: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   499: astore #6
    //   501: aload #12
    //   503: ldc 'web_url'
    //   505: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   508: astore #7
    //   510: aload #12
    //   512: ldc 'web_title'
    //   514: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   517: astore #8
    //   519: aload #12
    //   521: ldc 'log_extra'
    //   523: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   526: astore #9
    //   528: aload #12
    //   530: ldc 'creative_id'
    //   532: invokevirtual optLong : (Ljava/lang/String;)J
    //   535: lstore #4
    //   537: aload #12
    //   539: ldc 'target_class'
    //   541: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   544: astore_1
    //   545: aload #12
    //   547: ldc 'download_url'
    //   549: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   552: astore #10
    //   554: aload #12
    //   556: ldc 'package_name'
    //   558: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   561: astore #11
    //   563: aload #12
    //   565: ldc 'app_name'
    //   567: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   570: astore #12
    //   572: aload_1
    //   573: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   576: ifne -> 586
    //   579: invokestatic a : ()Lcom/ss/android/ugc/aweme/miniapp_api/c;
    //   582: aload_1
    //   583: putfield d : Ljava/lang/String;
    //   586: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   589: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   592: astore #13
    //   594: aload #13
    //   596: ifnull -> 918
    //   599: aload #6
    //   601: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   604: ifeq -> 612
    //   607: iload_3
    //   608: istore_2
    //   609: goto -> 868
    //   612: aload #6
    //   614: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   617: astore_1
    //   618: aload_1
    //   619: invokevirtual getScheme : ()Ljava/lang/String;
    //   622: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   625: ifeq -> 633
    //   628: iload_3
    //   629: istore_2
    //   630: goto -> 868
    //   633: aload_1
    //   634: invokevirtual getScheme : ()Ljava/lang/String;
    //   637: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   640: astore #14
    //   642: aload #14
    //   644: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   647: ifeq -> 655
    //   650: iload_3
    //   651: istore_2
    //   652: goto -> 868
    //   655: invokestatic inst : ()Lcom/ss/android/ugc/aweme/miniapp/MiniAppService;
    //   658: invokevirtual getRouterDepend : ()Lcom/ss/android/ugc/aweme/miniapp_api/a/l;
    //   661: aload #13
    //   663: aload #6
    //   665: aload #14
    //   667: invokeinterface a : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Z
    //   672: ifeq -> 678
    //   675: goto -> 923
    //   678: new android/content/Intent
    //   681: dup
    //   682: ldc 'android.intent.action.VIEW'
    //   684: invokespecial <init> : (Ljava/lang/String;)V
    //   687: astore #14
    //   689: aload #14
    //   691: aload_1
    //   692: invokevirtual setData : (Landroid/net/Uri;)Landroid/content/Intent;
    //   695: pop
    //   696: aload #13
    //   698: invokevirtual getPackageManager : ()Landroid/content/pm/PackageManager;
    //   701: aload #14
    //   703: ldc 65536
    //   705: invokevirtual queryIntentActivities : (Landroid/content/Intent;I)Ljava/util/List;
    //   708: astore_1
    //   709: aload_1
    //   710: ifnull -> 928
    //   713: aload_1
    //   714: invokeinterface size : ()I
    //   719: ifle -> 928
    //   722: iconst_1
    //   723: istore_2
    //   724: goto -> 930
    //   727: aload #6
    //   729: astore_1
    //   730: aload #6
    //   732: ldc_w '__back_url__'
    //   735: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   738: ifeq -> 788
    //   741: aload #6
    //   743: ldc_w '__back_url__'
    //   746: getstatic com/ss/android/ugc/aweme/miniapp/a/a.b : Ljava/lang/String;
    //   749: invokestatic encode : (Ljava/lang/String;)Ljava/lang/String;
    //   752: invokevirtual replace : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   755: astore_1
    //   756: aload #14
    //   758: aload_1
    //   759: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
    //   762: invokevirtual setData : (Landroid/net/Uri;)Landroid/content/Intent;
    //   765: pop
    //   766: invokestatic inst : ()Lcom/ss/android/ugc/aweme/miniapp/MiniAppService;
    //   769: invokevirtual getBaseLibDepend : ()Lcom/ss/android/ugc/aweme/miniapp_api/a/c;
    //   772: new com/ss/android/ugc/aweme/miniapp/a/a$1
    //   775: dup
    //   776: lload #4
    //   778: aload #9
    //   780: invokespecial <init> : (JLjava/lang/String;)V
    //   783: invokeinterface b : (Ljava/lang/Runnable;)V
    //   788: aload #14
    //   790: ldc 'open_url'
    //   792: aload_1
    //   793: invokevirtual putExtra : (Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   796: pop
    //   797: aload #13
    //   799: aload #14
    //   801: invokevirtual startActivity : (Landroid/content/Intent;)V
    //   804: iconst_1
    //   805: istore_2
    //   806: goto -> 936
    //   809: aload #13
    //   811: ldc_w 'open_url_app'
    //   814: lload #4
    //   816: aload #9
    //   818: invokestatic a : (Landroid/content/Context;Ljava/lang/String;JLjava/lang/String;)V
    //   821: new com/ss/android/ugc/aweme/miniapp/a/a$2
    //   824: dup
    //   825: aload #13
    //   827: lload #4
    //   829: aload #9
    //   831: invokespecial <init> : (Landroid/content/Context;JLjava/lang/String;)V
    //   834: astore_1
    //   835: invokestatic inst : ()Lcom/ss/android/ugc/aweme/miniapp/MiniAppService;
    //   838: invokevirtual getBaseLibDepend : ()Lcom/ss/android/ugc/aweme/miniapp_api/a/c;
    //   841: ldc2_w 5000
    //   844: new com/ss/android/ugc/aweme/miniapp/a/a$3
    //   847: dup
    //   848: invokespecial <init> : ()V
    //   851: new com/ss/android/ugc/aweme/miniapp/a/a$4
    //   854: dup
    //   855: aload_1
    //   856: invokespecial <init> : (Lcom/ss/android/ugc/aweme/miniapp/a/a$a;)V
    //   859: iconst_0
    //   860: invokeinterface a : (JLcom/ss/android/ugc/aweme/miniapp_api/b/a/a;Lcom/ss/android/ugc/aweme/miniapp_api/b/a/b;Z)V
    //   865: goto -> 923
    //   868: iload_2
    //   869: ifne -> 918
    //   872: invokestatic inst : ()Lcom/ss/android/ugc/aweme/miniapp/MiniAppService;
    //   875: invokevirtual getRouterDepend : ()Lcom/ss/android/ugc/aweme/miniapp_api/a/l;
    //   878: aload #13
    //   880: aload #7
    //   882: aload #8
    //   884: aload #9
    //   886: lload #4
    //   888: aload #10
    //   890: aload #11
    //   892: aload #12
    //   894: invokeinterface a : (Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   899: aload #13
    //   901: ldc_w 'open_url_h5'
    //   904: lload #4
    //   906: aload #9
    //   908: invokestatic a : (Landroid/content/Context;Ljava/lang/String;JLjava/lang/String;)V
    //   911: aconst_null
    //   912: areturn
    //   913: aload_1
    //   914: invokestatic a : (Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;)Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;
    //   917: areturn
    //   918: aconst_null
    //   919: areturn
    //   920: astore_1
    //   921: aconst_null
    //   922: areturn
    //   923: iconst_1
    //   924: istore_2
    //   925: goto -> 868
    //   928: iconst_0
    //   929: istore_2
    //   930: iload_2
    //   931: ifne -> 727
    //   934: iconst_0
    //   935: istore_2
    //   936: iload_2
    //   937: ifne -> 809
    //   940: iload_3
    //   941: istore_2
    //   942: goto -> 868
    // Exception table:
    //   from	to	target	type
    //   409	461	920	org/json/JSONException
    //   461	473	920	org/json/JSONException
    //   482	586	920	org/json/JSONException
    //   586	594	920	org/json/JSONException
    //   599	607	920	org/json/JSONException
    //   612	628	920	org/json/JSONException
    //   633	650	920	org/json/JSONException
    //   655	675	920	org/json/JSONException
    //   678	709	920	org/json/JSONException
    //   713	722	920	org/json/JSONException
    //   730	788	920	org/json/JSONException
    //   788	804	920	org/json/JSONException
    //   809	865	920	org/json/JSONException
    //   872	911	920	org/json/JSONException
  }
  
  public final String getType() {
    return "hostActionSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */