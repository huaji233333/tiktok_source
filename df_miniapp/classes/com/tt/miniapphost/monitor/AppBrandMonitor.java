package com.tt.miniapphost.monitor;

import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.EventHelper;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.p.e;
import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;

public class AppBrandMonitor {
  private static String TAG = "AppBrandMonitor";
  
  private static LinkedList<ReportItem> mCacheReport = new LinkedList<ReportItem>();
  
  private static String mHostAppId;
  
  private static String mHostUpdateVersion;
  
  private static boolean sIsReportByProcessFramework;
  
  private static volatile e sdkmonitor;
  
  public static void _init(Context paramContext) {
    // Byte code:
    //   0: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   3: invokevirtual getInitParams : ()Lcom/tt/miniapphost/entity/InitParamsEntity;
    //   6: astore_1
    //   7: new org/json/JSONObject
    //   10: dup
    //   11: invokespecial <init> : ()V
    //   14: astore_2
    //   15: aload_2
    //   16: ldc 'device_id'
    //   18: invokestatic a : ()Ljava/lang/String;
    //   21: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   24: pop
    //   25: aload_2
    //   26: ldc 'channel'
    //   28: aload_1
    //   29: invokevirtual getChannel : ()Ljava/lang/String;
    //   32: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   35: pop
    //   36: aload_2
    //   37: ldc 'host_aid'
    //   39: aload_1
    //   40: invokevirtual getAppId : ()Ljava/lang/String;
    //   43: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   46: pop
    //   47: aload_2
    //   48: ldc 'app_version'
    //   50: ldc '3.7.4-tiktok'
    //   52: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   55: pop
    //   56: aload_2
    //   57: ldc 'update_version_code'
    //   59: iconst_1
    //   60: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   63: pop
    //   64: goto -> 85
    //   67: astore_3
    //   68: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.TAG : Ljava/lang/String;
    //   71: iconst_1
    //   72: anewarray java/lang/Object
    //   75: dup
    //   76: iconst_0
    //   77: aload_3
    //   78: invokevirtual getMessage : ()Ljava/lang/String;
    //   81: aastore
    //   82: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   85: aload_1
    //   86: invokevirtual getAppId : ()Ljava/lang/String;
    //   89: putstatic com/tt/miniapphost/monitor/AppBrandMonitor.mHostAppId : Ljava/lang/String;
    //   92: aload_1
    //   93: invokevirtual getUpdateVersionCode : ()Ljava/lang/String;
    //   96: putstatic com/tt/miniapphost/monitor/AppBrandMonitor.mHostUpdateVersion : Ljava/lang/String;
    //   99: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   101: monitorenter
    //   102: aload_0
    //   103: ifnull -> 119
    //   106: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   109: aload_0
    //   110: ldc '2033'
    //   112: aload_2
    //   113: invokevirtual createSDKMonitorInstance : (Landroid/content/Context;Ljava/lang/String;Lorg/json/JSONObject;)Lcom/tt/option/p/e;
    //   116: putstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   119: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   122: ifnonnull -> 136
    //   125: iconst_1
    //   126: putstatic com/tt/miniapphost/monitor/AppBrandMonitor.sIsReportByProcessFramework : Z
    //   129: aload_0
    //   130: invokestatic getHostMonitor : (Landroid/content/Context;)Lcom/tt/option/p/e;
    //   133: putstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   136: invokestatic reportCacheItems : ()V
    //   139: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   141: monitorexit
    //   142: return
    //   143: astore_0
    //   144: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   146: monitorexit
    //   147: aload_0
    //   148: athrow
    // Exception table:
    //   from	to	target	type
    //   15	64	67	org/json/JSONException
    //   106	119	143	finally
    //   119	136	143	finally
    //   136	142	143	finally
    //   144	147	143	finally
  }
  
  public static void duration(String paramString, JSONObject paramJSONObject1, JSONObject paramJSONObject2) {
    // Byte code:
    //   0: invokestatic isMonitorReady : ()Z
    //   3: ifeq -> 21
    //   6: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   9: aload_0
    //   10: aload_1
    //   11: aload_2
    //   12: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   15: invokeinterface monitorDuration : (Ljava/lang/String;Lorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   20: return
    //   21: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   23: monitorenter
    //   24: invokestatic isMonitorReady : ()Z
    //   27: ifeq -> 47
    //   30: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   33: aload_0
    //   34: aload_1
    //   35: aload_2
    //   36: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   39: invokeinterface monitorDuration : (Ljava/lang/String;Lorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   44: goto -> 60
    //   47: getstatic com/tt/miniapphost/monitor/AppBrandMonitor$MonitorType.Duration : Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;
    //   50: aload_0
    //   51: iconst_0
    //   52: aload_1
    //   53: aload_2
    //   54: aconst_null
    //   55: aconst_null
    //   56: aconst_null
    //   57: invokestatic queueCacheItem : (Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;Ljava/lang/String;ILorg/json/JSONObject;Lorg/json/JSONObject;Ljava/lang/String;Lorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   60: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   62: monitorexit
    //   63: return
    //   64: astore_0
    //   65: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   67: monitorexit
    //   68: aload_0
    //   69: athrow
    // Exception table:
    //   from	to	target	type
    //   24	44	64	finally
    //   47	60	64	finally
    //   60	63	64	finally
    //   65	68	64	finally
  }
  
  public static void event(String paramString, JSONObject paramJSONObject1, JSONObject paramJSONObject2, JSONObject paramJSONObject3) {
    // Byte code:
    //   0: invokestatic isMonitorReady : ()Z
    //   3: ifeq -> 22
    //   6: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   9: aload_0
    //   10: aload_1
    //   11: aload_2
    //   12: aload_3
    //   13: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   16: invokeinterface monitorEvent : (Ljava/lang/String;Lorg/json/JSONObject;Lorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   21: return
    //   22: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   24: monitorenter
    //   25: invokestatic isMonitorReady : ()Z
    //   28: ifeq -> 49
    //   31: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   34: aload_0
    //   35: aload_1
    //   36: aload_2
    //   37: aload_3
    //   38: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   41: invokeinterface monitorEvent : (Ljava/lang/String;Lorg/json/JSONObject;Lorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   46: goto -> 62
    //   49: getstatic com/tt/miniapphost/monitor/AppBrandMonitor$MonitorType.Event : Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;
    //   52: aload_0
    //   53: iconst_0
    //   54: aconst_null
    //   55: aload_3
    //   56: aconst_null
    //   57: aload_1
    //   58: aload_2
    //   59: invokestatic queueCacheItem : (Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;Ljava/lang/String;ILorg/json/JSONObject;Lorg/json/JSONObject;Ljava/lang/String;Lorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   62: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   64: monitorexit
    //   65: return
    //   66: astore_0
    //   67: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   69: monitorexit
    //   70: aload_0
    //   71: athrow
    // Exception table:
    //   from	to	target	type
    //   25	46	66	finally
    //   49	62	66	finally
    //   62	65	66	finally
    //   67	70	66	finally
  }
  
  public static void flush() {
    if (sdkmonitor != null)
      sdkmonitor.flushBuffer(); 
  }
  
  private static e getHostMonitor(Context paramContext) {
    return new e(ProcessUtil.isMainProcess(paramContext)) {
        private void scheduleReport(final String serviceName, final int status, final JSONObject logExtr) {
          if (isMainProcess) {
            ipcSyncReport(serviceName, status, logExtr);
            return;
          } 
          ThreadUtil.runOnWorkThread(new Action() {
                public void act() {
                  AppBrandMonitor.null.this.ipcSyncReport(serviceName, status, logExtr);
                }
              }Schedulers.shortIO());
        }
        
        public final void flushBuffer() {}
        
        public final void ipcSyncReport(String param1String, int param1Int, JSONObject param1JSONObject) {
          ProcessCallControlBridge.callHostProcessSync("appBrandMonitor", CrossProcessDataEntity.Builder.create().put("mpMonitorServiceName", param1String).put("mpMonitorStatusCode", Integer.valueOf(param1Int)).put("mpMonitorData", param1JSONObject).build());
        }
        
        public final void monitorCommonLog(String param1String, JSONObject param1JSONObject) {
          scheduleReport(param1String, 0, param1JSONObject);
        }
        
        public final void monitorDuration(String param1String, JSONObject param1JSONObject1, JSONObject param1JSONObject2) {
          scheduleReport(param1String, 0, param1JSONObject2);
        }
        
        public final void monitorEvent(String param1String, JSONObject param1JSONObject1, JSONObject param1JSONObject2, JSONObject param1JSONObject3) {
          scheduleReport(param1String, 0, param1JSONObject3);
        }
        
        public final void monitorStatusAndDuration(String param1String, int param1Int, JSONObject param1JSONObject1, JSONObject param1JSONObject2) {
          scheduleReport(param1String, param1Int, param1JSONObject2);
        }
        
        public final void monitorStatusRate(String param1String, int param1Int, JSONObject param1JSONObject) {
          scheduleReport(param1String, param1Int, param1JSONObject);
        }
      };
  }
  
  public static void init(final Context context) {
    if (ProcessUtil.isMainProcess(context)) {
      _init(context);
      return;
    } 
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            AppBrandMonitor._init(context);
          }
        },  ThreadPools.defaults());
  }
  
  private static boolean isMonitorReady() {
    return (sdkmonitor != null && mCacheReport == null);
  }
  
  public static boolean isReportByProcessFramework() {
    return sIsReportByProcessFramework;
  }
  
  public static void log(String paramString, JSONObject paramJSONObject) {
    // Byte code:
    //   0: invokestatic isMonitorReady : ()Z
    //   3: ifeq -> 20
    //   6: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   9: aload_0
    //   10: aload_1
    //   11: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   14: invokeinterface monitorCommonLog : (Ljava/lang/String;Lorg/json/JSONObject;)V
    //   19: return
    //   20: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   22: monitorenter
    //   23: invokestatic isMonitorReady : ()Z
    //   26: ifeq -> 45
    //   29: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   32: aload_0
    //   33: aload_1
    //   34: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   37: invokeinterface monitorCommonLog : (Ljava/lang/String;Lorg/json/JSONObject;)V
    //   42: goto -> 58
    //   45: getstatic com/tt/miniapphost/monitor/AppBrandMonitor$MonitorType.CommonLog : Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;
    //   48: aconst_null
    //   49: iconst_0
    //   50: aconst_null
    //   51: aload_1
    //   52: aload_0
    //   53: aconst_null
    //   54: aconst_null
    //   55: invokestatic queueCacheItem : (Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;Ljava/lang/String;ILorg/json/JSONObject;Lorg/json/JSONObject;Ljava/lang/String;Lorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   58: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   60: monitorexit
    //   61: return
    //   62: astore_0
    //   63: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   65: monitorexit
    //   66: aload_0
    //   67: athrow
    // Exception table:
    //   from	to	target	type
    //   23	42	62	finally
    //   45	58	62	finally
    //   58	61	62	finally
    //   63	66	62	finally
  }
  
  private static void queueCacheItem(MonitorType paramMonitorType, String paramString1, int paramInt, JSONObject paramJSONObject1, JSONObject paramJSONObject2, String paramString2, JSONObject paramJSONObject3, JSONObject paramJSONObject4) {
    LinkedList<ReportItem> linkedList = mCacheReport;
    if (linkedList != null) {
      linkedList.add(new ReportItem(paramMonitorType, paramString1, paramInt, paramJSONObject1, paramJSONObject2, paramString2, paramJSONObject3, paramJSONObject4));
      return;
    } 
    AppBrandLogger.e(TAG, new Object[] { "queueCacheItem error" });
  }
  
  private static void reportCacheItems() {
    for (ReportItem reportItem : mCacheReport) {
      JSONObject jSONObject = setTmaLogExtr(reportItem.logExtr, reportItem.timestamp);
      int i = null.$SwitchMap$com$tt$miniapphost$monitor$AppBrandMonitor$MonitorType[reportItem.monitorType.ordinal()];
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 4) {
              if (i != 5) {
                AppBrandLogger.e(TAG, new Object[] { "unknow report type" });
                continue;
              } 
              sdkmonitor.monitorEvent(reportItem.serviceName, reportItem.category, reportItem.metric, jSONObject);
              continue;
            } 
            sdkmonitor.monitorCommonLog(reportItem.logType, jSONObject);
            continue;
          } 
          sdkmonitor.monitorDuration(reportItem.serviceName, reportItem.duration, jSONObject);
          continue;
        } 
        sdkmonitor.monitorStatusRate(reportItem.serviceName, reportItem.status, jSONObject);
        continue;
      } 
      sdkmonitor.monitorStatusAndDuration(reportItem.serviceName, reportItem.status, reportItem.duration, jSONObject);
    } 
    mCacheReport.clear();
    mCacheReport = null;
  }
  
  public static void reportError(String paramString1, String paramString2, String paramString3) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("name", paramString1);
      jSONObject.put("err_msg", paramString2);
      paramString1 = paramString3;
      if (paramString3 == null)
        paramString1 = ""; 
      jSONObject.put("extra_info", paramString1);
      statusRate("mp_special_error", 9400, jSONObject);
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
  
  public static void reportPreloadCase(String paramString, int paramInt) {
    JSONObject jSONObject = new JSONObject();
    if (!TextUtils.isEmpty(paramString))
      try {
        jSONObject.put("tma_event", paramString);
      } catch (JSONException jSONException) {} 
    statusRate("mp_preload_case", paramInt, jSONObject);
  }
  
  private static JSONObject setTmaLogExtr(JSONObject paramJSONObject) {
    return setTmaLogExtr(paramJSONObject, 0L);
  }
  
  private static JSONObject setTmaLogExtr(JSONObject paramJSONObject, long paramLong) {
    JSONObject jSONObject = new JSONObject();
    try {
      String str;
      jSONObject.put("aid", mHostAppId);
      jSONObject.put("huv", mHostUpdateVersion);
      if (AppbrandContext.getInst().getInitParams() != null) {
        str = AppbrandContext.getInst().getInitParams().getPluginVersion();
      } else {
        str = "";
      } 
      if (!TextUtils.isEmpty(str))
        jSONObject.put("plugin_ver", str); 
      paramJSONObject = EventHelper.getCommonParams(paramJSONObject, null);
      if (paramLong > 0L)
        paramJSONObject.put("report_timestamp", paramLong); 
      jSONObject.put("extra", paramJSONObject);
      jSONObject.put("timestamp", paramJSONObject.get("report_timestamp"));
      return jSONObject;
    } catch (Exception exception) {
      AppBrandLogger.d(TAG, new Object[] { exception.getMessage() });
      return jSONObject;
    } 
  }
  
  public static void statusAndDuration(String paramString, int paramInt, JSONObject paramJSONObject1, JSONObject paramJSONObject2) {
    // Byte code:
    //   0: invokestatic isMonitorReady : ()Z
    //   3: ifeq -> 22
    //   6: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   9: aload_0
    //   10: iload_1
    //   11: aload_2
    //   12: aload_3
    //   13: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   16: invokeinterface monitorStatusAndDuration : (Ljava/lang/String;ILorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   21: return
    //   22: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   24: monitorenter
    //   25: invokestatic isMonitorReady : ()Z
    //   28: ifeq -> 49
    //   31: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   34: aload_0
    //   35: iload_1
    //   36: aload_2
    //   37: aload_3
    //   38: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   41: invokeinterface monitorStatusAndDuration : (Ljava/lang/String;ILorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   46: goto -> 62
    //   49: getstatic com/tt/miniapphost/monitor/AppBrandMonitor$MonitorType.StatusDuration : Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;
    //   52: aload_0
    //   53: iload_1
    //   54: aload_2
    //   55: aload_3
    //   56: aconst_null
    //   57: aconst_null
    //   58: aconst_null
    //   59: invokestatic queueCacheItem : (Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;Ljava/lang/String;ILorg/json/JSONObject;Lorg/json/JSONObject;Ljava/lang/String;Lorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   62: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   64: monitorexit
    //   65: return
    //   66: astore_0
    //   67: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   69: monitorexit
    //   70: aload_0
    //   71: athrow
    // Exception table:
    //   from	to	target	type
    //   25	46	66	finally
    //   49	62	66	finally
    //   62	65	66	finally
    //   67	70	66	finally
  }
  
  public static void statusRate(String paramString, int paramInt, JSONObject paramJSONObject) {
    // Byte code:
    //   0: invokestatic isMonitorReady : ()Z
    //   3: ifeq -> 21
    //   6: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   9: aload_0
    //   10: iload_1
    //   11: aload_2
    //   12: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   15: invokeinterface monitorStatusRate : (Ljava/lang/String;ILorg/json/JSONObject;)V
    //   20: return
    //   21: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   23: monitorenter
    //   24: invokestatic isMonitorReady : ()Z
    //   27: ifeq -> 47
    //   30: getstatic com/tt/miniapphost/monitor/AppBrandMonitor.sdkmonitor : Lcom/tt/option/p/e;
    //   33: aload_0
    //   34: iload_1
    //   35: aload_2
    //   36: invokestatic setTmaLogExtr : (Lorg/json/JSONObject;)Lorg/json/JSONObject;
    //   39: invokeinterface monitorStatusRate : (Ljava/lang/String;ILorg/json/JSONObject;)V
    //   44: goto -> 60
    //   47: getstatic com/tt/miniapphost/monitor/AppBrandMonitor$MonitorType.StatusRate : Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;
    //   50: aload_0
    //   51: iload_1
    //   52: aconst_null
    //   53: aload_2
    //   54: aconst_null
    //   55: aconst_null
    //   56: aconst_null
    //   57: invokestatic queueCacheItem : (Lcom/tt/miniapphost/monitor/AppBrandMonitor$MonitorType;Ljava/lang/String;ILorg/json/JSONObject;Lorg/json/JSONObject;Ljava/lang/String;Lorg/json/JSONObject;Lorg/json/JSONObject;)V
    //   60: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   62: monitorexit
    //   63: return
    //   64: astore_0
    //   65: ldc com/tt/miniapphost/monitor/AppBrandMonitor
    //   67: monitorexit
    //   68: aload_0
    //   69: athrow
    // Exception table:
    //   from	to	target	type
    //   24	44	64	finally
    //   47	60	64	finally
    //   60	63	64	finally
    //   65	68	64	finally
  }
  
  enum MonitorType {
    CommonLog, Duration, Event, StatusDuration, StatusRate;
    
    static {
      CommonLog = new MonitorType("CommonLog", 3);
      Event = new MonitorType("Event", 4);
      $VALUES = new MonitorType[] { StatusDuration, StatusRate, Duration, CommonLog, Event };
    }
  }
  
  static class ReportItem {
    JSONObject category;
    
    JSONObject duration;
    
    JSONObject logExtr;
    
    String logType;
    
    JSONObject metric;
    
    AppBrandMonitor.MonitorType monitorType;
    
    String serviceName;
    
    int status;
    
    long timestamp;
    
    public ReportItem(AppBrandMonitor.MonitorType param1MonitorType, String param1String1, int param1Int, JSONObject param1JSONObject1, JSONObject param1JSONObject2, String param1String2, JSONObject param1JSONObject3, JSONObject param1JSONObject4) {
      this.monitorType = param1MonitorType;
      this.serviceName = param1String1;
      this.status = param1Int;
      this.duration = param1JSONObject1;
      this.logExtr = param1JSONObject2;
      this.logType = param1String2;
      this.category = param1JSONObject3;
      this.metric = param1JSONObject4;
      this.timestamp = System.currentTimeMillis();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\monitor\AppBrandMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */