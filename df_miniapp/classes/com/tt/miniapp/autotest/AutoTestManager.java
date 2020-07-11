package com.tt.miniapp.autotest;

import android.os.SystemClock;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.LifeCycleManager;
import com.tt.miniapp.LifeCycleManager.LifecycleInterest;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.DebugUtil;
import d.a.ac;
import d.a.m;
import d.f.a.b;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.m.p;
import d.n;
import d.t;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class AutoTestManager extends AppbrandServiceManager.ServiceBase {
  public static final Companion Companion = new Companion(null);
  
  private static final Map<String, b<Map<String, ? extends List<AutoTestEvent>>, Object>> DEFAULT_CALCULATORS;
  
  private static final b<Map<String, ? extends List<AutoTestEvent>>, Object> path2DomReadyCalculator;
  
  private static final b<Map<String, ? extends List<AutoTestEvent>>, Object> readPageFrameCalculator;
  
  private static final b<Map<String, ? extends List<AutoTestEvent>>, Object> readPathFrameCalculator;
  
  private static final b<Map<String, ? extends List<AutoTestEvent>>, Object> realSendPageFrameCalculator = AutoTestManager$Companion$realSendPageFrameCalculator$1.INSTANCE;
  
  private static final b<Map<String, ? extends List<AutoTestEvent>>, Object> realSendPathFrameCalculator = AutoTestManager$Companion$realSendPathFrameCalculator$1.INSTANCE;
  
  private boolean isEnableTrace = true;
  
  private boolean isFinish;
  
  private final LinkedHashMap<String, b<Map<String, ? extends List<AutoTestEvent>>, Object>> mCalculatorList = new LinkedHashMap<String, b<Map<String, ? extends List<AutoTestEvent>>, Object>>(DEFAULT_CALCULATORS);
  
  private final Vector<AutoTestEvent> mEventList = new Vector<AutoTestEvent>();
  
  private final AutoTestLooperMonitor mMainLooperMonitor = new AutoTestLooperMonitor();
  
  static {
    readPageFrameCalculator = AutoTestManager$Companion$readPageFrameCalculator$1.INSTANCE;
    readPathFrameCalculator = AutoTestManager$Companion$readPathFrameCalculator$1.INSTANCE;
    path2DomReadyCalculator = AutoTestManager$Companion$path2DomReadyCalculator$1.INSTANCE;
    DEFAULT_CALCULATORS = ac.a(new n[] { 
          t.a("appId", Companion.firstValueCalculator("appId")), t.a("isPkgExist", Companion.firstValueCalculator("isPkgExist")), t.a("isMetaExist", Companion.firstValueCalculator("isMetaExist")), t.a("parseOpenSchema", Companion.intervalCalculator("startActivityTime", "startLaunchTime")), t.a("activityCreateTime", Companion.intervalCalculator("afterOnCreate", "beforeOnCreate")), t.a("requestMeta", Companion.intervalCalculator("stopRequestMeta", "startRequestMeta")), t.a("downloadInstallTime", Companion.intervalCalculator("stopDownloadInstallTime", "startDownloadInstallTime")), t.a("appServiceTime", Companion.intervalCalculator("stopAppService", "startAppService")), t.a("sendAppRoute-appService", Companion.intervalCalculator("sendAppRoute", "stopAppService")), t.a("sendPathFrame-sendPageFrame", Companion.intervalCalculator("sendLoadPathFrame", "sendLoadPageFrame")), 
          t.a("realSendPageFrame", realSendPageFrameCalculator), t.a("realSendPathFrame", realSendPathFrameCalculator), t.a("beforeOnCreate", Companion.intervalCalculator("beforeOnCreate", "startLaunchTime")), t.a("beforeRequestMeta", Companion.intervalCalculator("startRequestMeta", "startLaunchTime")), t.a("beforeDownload", Companion.intervalCalculator("startDownloadInstallTime", "startLaunchTime")), t.a("beforeAppService", Companion.intervalCalculator("startAppService", "startLaunchTime")), t.a("readPageFrame", readPageFrameCalculator), t.a("readPathFrame", readPathFrameCalculator), t.a("path2DomReady", path2DomReadyCalculator), t.a("beforeAppRoute", Companion.intervalCalculator("sendAppRoute", "startLaunchTime")), 
          t.a("afterAppRoute", Companion.intervalCalculator("stopLaunchTime", "sendAppRoute")), t.a("firstServiceTime", Companion.firstServiceCalculator()), t.a("publishUseTime", Companion.firstValueCalculator("publishUseTime")), t.a("totalLaunchTime", Companion.intervalCalculator("stopLaunchTime", "startLaunchTime")) });
  }
  
  public AutoTestManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private final void clear() {
    AppBrandLogger.d("AutoTestManager", new Object[] { "clear auto test event" });
    this.mEventList.clear();
    this.mMainLooperMonitor.stop();
    this.mMainLooperMonitor.clear();
  }
  
  public final void addCalculator(String paramString, b<? super Map<String, ? extends List<AutoTestEvent>>, ? extends Object> paramb) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'name'
    //   4: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_2
    //   8: ldc_w 'calculator'
    //   11: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   14: aload_0
    //   15: monitorenter
    //   16: aload_0
    //   17: getfield mCalculatorList : Ljava/util/LinkedHashMap;
    //   20: checkcast java/util/Map
    //   23: aload_1
    //   24: aload_2
    //   25: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   30: pop
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: astore_1
    //   35: aload_0
    //   36: monitorexit
    //   37: aload_1
    //   38: athrow
    // Exception table:
    //   from	to	target	type
    //   16	31	34	finally
  }
  
  public final void addEvent(String paramString) {
    addEvent$default(this, paramString, 0L, 2, null);
  }
  
  public final void addEvent(String paramString, long paramLong) {
    l.b(paramString, "id");
    if (!this.isFinish) {
      if (!this.isEnableTrace)
        return; 
      AutoTestEvent autoTestEvent = new AutoTestEvent(paramString, paramLong, null, 4, null);
      this.mEventList.add(autoTestEvent);
    } 
  }
  
  public final void addEventWithValue(String paramString, Object paramObject) {
    addEventWithValue$default(this, paramString, paramObject, 0L, 4, null);
  }
  
  public final void addEventWithValue(String paramString, Object paramObject, long paramLong) {
    l.b(paramString, "id");
    l.b(paramObject, "value");
    if (!this.isFinish) {
      if (!this.isEnableTrace)
        return; 
      AutoTestEvent autoTestEvent = new AutoTestEvent(paramString, paramLong, paramObject);
      this.mEventList.add(autoTestEvent);
    } 
  }
  
  public final void endAutoTest() {
    // Byte code:
    //   0: aload_0
    //   1: getfield isFinish : Z
    //   4: ifne -> 97
    //   7: aload_0
    //   8: getfield isEnableTrace : Z
    //   11: ifne -> 15
    //   14: return
    //   15: aload_0
    //   16: iconst_1
    //   17: putfield isFinish : Z
    //   20: ldc 'AutoTestManager'
    //   22: iconst_1
    //   23: anewarray java/lang/Object
    //   26: dup
    //   27: iconst_0
    //   28: ldc_w 'endAutoTest'
    //   31: aastore
    //   32: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   35: aload_0
    //   36: getfield mMainLooperMonitor : Lcom/tt/miniapp/autotest/AutoTestLooperMonitor;
    //   39: invokevirtual stop : ()V
    //   42: aload_0
    //   43: monitorenter
    //   44: aload_0
    //   45: getfield mCalculatorList : Ljava/util/LinkedHashMap;
    //   48: checkcast java/util/Map
    //   51: invokestatic e : (Ljava/util/Map;)Ljava/util/List;
    //   54: astore_1
    //   55: aload_0
    //   56: monitorexit
    //   57: new com/tt/miniapp/autotest/AutoTestReport
    //   60: dup
    //   61: aload_0
    //   62: getfield mEventList : Ljava/util/Vector;
    //   65: checkcast java/lang/Iterable
    //   68: invokestatic f : (Ljava/lang/Iterable;)Ljava/util/List;
    //   71: aload_1
    //   72: aload_0
    //   73: getfield mMainLooperMonitor : Lcom/tt/miniapp/autotest/AutoTestLooperMonitor;
    //   76: invokevirtual dump : ()Ljava/util/List;
    //   79: invokespecial <init> : (Ljava/util/List;Ljava/util/List;Ljava/util/List;)V
    //   82: checkcast com/storage/async/Action
    //   85: invokestatic backGround : ()Lcom/storage/async/Scheduler;
    //   88: invokestatic runOnWorkThread : (Lcom/storage/async/Action;Lcom/storage/async/Scheduler;)V
    //   91: return
    //   92: astore_1
    //   93: aload_0
    //   94: monitorexit
    //   95: aload_1
    //   96: athrow
    //   97: return
    // Exception table:
    //   from	to	target	type
    //   44	55	92	finally
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_CREATE})
  public final void onAppCreated() {
    if (DebugUtil.debug())
      this.mMainLooperMonitor.start(); 
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_INFO_INITED})
  public final void onAppInfoInited(LifeCycleManager.LifeCycleEvent paramLifeCycleEvent, AppInfoEntity paramAppInfoEntity) {
    boolean bool;
    l.b(paramLifeCycleEvent, "event");
    l.b(paramAppInfoEntity, "appInfo");
    if (DebugUtil.debug() && paramAppInfoEntity.isAutoTest) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isEnableTrace = bool;
    if (!this.isEnableTrace)
      clear(); 
  }
  
  public static final class Companion {
    private Companion() {}
    
    public final b<Map<String, ? extends List<AutoTestEvent>>, Object> firstServiceCalculator() {
      return AutoTestManager$Companion$firstServiceCalculator$2.INSTANCE;
    }
    
    public final b<Map<String, ? extends List<AutoTestEvent>>, Object> firstValueCalculator(String param1String) {
      l.b(param1String, "id");
      return new AutoTestManager$Companion$firstValueCalculator$1(param1String);
    }
    
    public final b<Map<String, ? extends List<AutoTestEvent>>, Object> intervalCalculator(String param1String1, String param1String2) {
      return new AutoTestManager$Companion$intervalCalculator$1(param1String1, param1String2);
    }
    
    static final class AutoTestManager$Companion$firstServiceCalculator$1 extends m implements b<String, Boolean> {
      public static final AutoTestManager$Companion$firstServiceCalculator$1 INSTANCE = new AutoTestManager$Companion$firstServiceCalculator$1();
      
      AutoTestManager$Companion$firstServiceCalculator$1() {
        super(1);
      }
      
      public final boolean invoke(String param2String) {
        l.b(param2String, "str");
        return (p.c(param2String, "-service.js", false, 2, null) && (l.a(param2String, "app-service.js") ^ true) != 0);
      }
    }
    
    static final class AutoTestManager$Companion$firstServiceCalculator$2 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
      public static final AutoTestManager$Companion$firstServiceCalculator$2 INSTANCE = new AutoTestManager$Companion$firstServiceCalculator$2();
      
      AutoTestManager$Companion$firstServiceCalculator$2() {
        super(1);
      }
      
      public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param2Map) {
        // Byte code:
        //   0: aload_1
        //   1: ldc 'map'
        //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
        //   6: aload_1
        //   7: ldc 'loadScriptEnd'
        //   9: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
        //   14: checkcast java/util/List
        //   17: astore_2
        //   18: aconst_null
        //   19: astore #4
        //   21: aconst_null
        //   22: astore_3
        //   23: aload_2
        //   24: ifnull -> 92
        //   27: aload_2
        //   28: checkcast java/lang/Iterable
        //   31: invokeinterface iterator : ()Ljava/util/Iterator;
        //   36: astore #5
        //   38: aload #5
        //   40: invokeinterface hasNext : ()Z
        //   45: ifeq -> 82
        //   48: aload #5
        //   50: invokeinterface next : ()Ljava/lang/Object;
        //   55: astore_2
        //   56: aload_2
        //   57: checkcast com/tt/miniapp/autotest/AutoTestEvent
        //   60: astore #6
        //   62: getstatic com/tt/miniapp/autotest/AutoTestManager$Companion$firstServiceCalculator$1.INSTANCE : Lcom/tt/miniapp/autotest/AutoTestManager$Companion$firstServiceCalculator$1;
        //   65: aload #6
        //   67: invokevirtual getValue : ()Ljava/lang/Object;
        //   70: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
        //   73: invokevirtual invoke : (Ljava/lang/String;)Z
        //   76: ifeq -> 38
        //   79: goto -> 84
        //   82: aconst_null
        //   83: astore_2
        //   84: aload_2
        //   85: checkcast com/tt/miniapp/autotest/AutoTestEvent
        //   88: astore_2
        //   89: goto -> 94
        //   92: aconst_null
        //   93: astore_2
        //   94: aload_1
        //   95: ldc 'loadScriptBegin'
        //   97: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
        //   102: checkcast java/util/List
        //   105: astore #5
        //   107: aload #4
        //   109: astore_1
        //   110: aload #5
        //   112: ifnull -> 175
        //   115: aload #5
        //   117: checkcast java/lang/Iterable
        //   120: invokeinterface iterator : ()Ljava/util/Iterator;
        //   125: astore #4
        //   127: aload_3
        //   128: astore_1
        //   129: aload #4
        //   131: invokeinterface hasNext : ()Z
        //   136: ifeq -> 170
        //   139: aload #4
        //   141: invokeinterface next : ()Ljava/lang/Object;
        //   146: astore_1
        //   147: aload_1
        //   148: checkcast com/tt/miniapp/autotest/AutoTestEvent
        //   151: astore #5
        //   153: getstatic com/tt/miniapp/autotest/AutoTestManager$Companion$firstServiceCalculator$1.INSTANCE : Lcom/tt/miniapp/autotest/AutoTestManager$Companion$firstServiceCalculator$1;
        //   156: aload #5
        //   158: invokevirtual getValue : ()Ljava/lang/Object;
        //   161: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
        //   164: invokevirtual invoke : (Ljava/lang/String;)Z
        //   167: ifeq -> 127
        //   170: aload_1
        //   171: checkcast com/tt/miniapp/autotest/AutoTestEvent
        //   174: astore_1
        //   175: aload_2
        //   176: ifnull -> 196
        //   179: aload_1
        //   180: ifnull -> 196
        //   183: aload_2
        //   184: invokevirtual getTimestamp : ()J
        //   187: aload_1
        //   188: invokevirtual getTimestamp : ()J
        //   191: lsub
        //   192: invokestatic valueOf : (J)Ljava/lang/Long;
        //   195: areturn
        //   196: iconst_m1
        //   197: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   200: areturn
      }
    }
    
    static final class AutoTestManager$Companion$firstValueCalculator$1 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
      AutoTestManager$Companion$firstValueCalculator$1(String param2String) {
        super(1);
      }
      
      public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param2Map) {
        l.b(param2Map, "map");
        List list = param2Map.get(this.$id);
        if (list != null) {
          AutoTestEvent autoTestEvent = (AutoTestEvent)m.f(list);
          if (autoTestEvent != null)
            return autoTestEvent.getValue(); 
        } 
        return null;
      }
    }
    
    static final class AutoTestManager$Companion$intervalCalculator$1 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
      AutoTestManager$Companion$intervalCalculator$1(String param2String1, String param2String2) {
        super(1);
      }
      
      public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param2Map) {
        AutoTestEvent autoTestEvent;
        l.b(param2Map, "map");
        List list1 = param2Map.get(this.$id1);
        Map map = null;
        if (list1 != null) {
          AutoTestEvent autoTestEvent1 = (AutoTestEvent)m.f(list1);
        } else {
          list1 = null;
        } 
        List list2 = param2Map.get(this.$id2);
        param2Map = map;
        if (list2 != null)
          autoTestEvent = (AutoTestEvent)m.f(list2); 
        return (list1 != null && autoTestEvent != null) ? Long.valueOf(list1.getTimestamp() - autoTestEvent.getTimestamp()) : Integer.valueOf(-1);
      }
    }
  }
  
  static final class AutoTestManager$Companion$firstServiceCalculator$1 extends m implements b<String, Boolean> {
    public static final AutoTestManager$Companion$firstServiceCalculator$1 INSTANCE = new AutoTestManager$Companion$firstServiceCalculator$1();
    
    AutoTestManager$Companion$firstServiceCalculator$1() {
      super(1);
    }
    
    public final boolean invoke(String param1String) {
      l.b(param1String, "str");
      return (p.c(param1String, "-service.js", false, 2, null) && (l.a(param1String, "app-service.js") ^ true) != 0);
    }
  }
  
  static final class AutoTestManager$Companion$firstServiceCalculator$2 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
    public static final AutoTestManager$Companion$firstServiceCalculator$2 INSTANCE = new AutoTestManager$Companion$firstServiceCalculator$2();
    
    AutoTestManager$Companion$firstServiceCalculator$2() {
      super(1);
    }
    
    public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param1Map) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'map'
      //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_1
      //   7: ldc 'loadScriptEnd'
      //   9: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   14: checkcast java/util/List
      //   17: astore_2
      //   18: aconst_null
      //   19: astore #4
      //   21: aconst_null
      //   22: astore_3
      //   23: aload_2
      //   24: ifnull -> 92
      //   27: aload_2
      //   28: checkcast java/lang/Iterable
      //   31: invokeinterface iterator : ()Ljava/util/Iterator;
      //   36: astore #5
      //   38: aload #5
      //   40: invokeinterface hasNext : ()Z
      //   45: ifeq -> 82
      //   48: aload #5
      //   50: invokeinterface next : ()Ljava/lang/Object;
      //   55: astore_2
      //   56: aload_2
      //   57: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   60: astore #6
      //   62: getstatic com/tt/miniapp/autotest/AutoTestManager$Companion$firstServiceCalculator$1.INSTANCE : Lcom/tt/miniapp/autotest/AutoTestManager$Companion$firstServiceCalculator$1;
      //   65: aload #6
      //   67: invokevirtual getValue : ()Ljava/lang/Object;
      //   70: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
      //   73: invokevirtual invoke : (Ljava/lang/String;)Z
      //   76: ifeq -> 38
      //   79: goto -> 84
      //   82: aconst_null
      //   83: astore_2
      //   84: aload_2
      //   85: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   88: astore_2
      //   89: goto -> 94
      //   92: aconst_null
      //   93: astore_2
      //   94: aload_1
      //   95: ldc 'loadScriptBegin'
      //   97: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   102: checkcast java/util/List
      //   105: astore #5
      //   107: aload #4
      //   109: astore_1
      //   110: aload #5
      //   112: ifnull -> 175
      //   115: aload #5
      //   117: checkcast java/lang/Iterable
      //   120: invokeinterface iterator : ()Ljava/util/Iterator;
      //   125: astore #4
      //   127: aload_3
      //   128: astore_1
      //   129: aload #4
      //   131: invokeinterface hasNext : ()Z
      //   136: ifeq -> 170
      //   139: aload #4
      //   141: invokeinterface next : ()Ljava/lang/Object;
      //   146: astore_1
      //   147: aload_1
      //   148: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   151: astore #5
      //   153: getstatic com/tt/miniapp/autotest/AutoTestManager$Companion$firstServiceCalculator$1.INSTANCE : Lcom/tt/miniapp/autotest/AutoTestManager$Companion$firstServiceCalculator$1;
      //   156: aload #5
      //   158: invokevirtual getValue : ()Ljava/lang/Object;
      //   161: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
      //   164: invokevirtual invoke : (Ljava/lang/String;)Z
      //   167: ifeq -> 127
      //   170: aload_1
      //   171: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   174: astore_1
      //   175: aload_2
      //   176: ifnull -> 196
      //   179: aload_1
      //   180: ifnull -> 196
      //   183: aload_2
      //   184: invokevirtual getTimestamp : ()J
      //   187: aload_1
      //   188: invokevirtual getTimestamp : ()J
      //   191: lsub
      //   192: invokestatic valueOf : (J)Ljava/lang/Long;
      //   195: areturn
      //   196: iconst_m1
      //   197: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   200: areturn
    }
  }
  
  static final class AutoTestManager$Companion$firstValueCalculator$1 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
    AutoTestManager$Companion$firstValueCalculator$1(String param1String) {
      super(1);
    }
    
    public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param1Map) {
      l.b(param1Map, "map");
      List list = param1Map.get(this.$id);
      if (list != null) {
        AutoTestEvent autoTestEvent = (AutoTestEvent)m.f(list);
        if (autoTestEvent != null)
          return autoTestEvent.getValue(); 
      } 
      return null;
    }
  }
  
  static final class AutoTestManager$Companion$intervalCalculator$1 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
    AutoTestManager$Companion$intervalCalculator$1(String param1String1, String param1String2) {
      super(1);
    }
    
    public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param1Map) {
      AutoTestEvent autoTestEvent;
      l.b(param1Map, "map");
      List list1 = param1Map.get(this.$id1);
      Map map = null;
      if (list1 != null) {
        AutoTestEvent autoTestEvent1 = (AutoTestEvent)m.f(list1);
      } else {
        list1 = null;
      } 
      List list2 = param1Map.get(this.$id2);
      param1Map = map;
      if (list2 != null)
        autoTestEvent = (AutoTestEvent)m.f(list2); 
      return (list1 != null && autoTestEvent != null) ? Long.valueOf(list1.getTimestamp() - autoTestEvent.getTimestamp()) : Integer.valueOf(-1);
    }
  }
  
  static final class AutoTestManager$Companion$path2DomReadyCalculator$1 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
    public static final AutoTestManager$Companion$path2DomReadyCalculator$1 INSTANCE = new AutoTestManager$Companion$path2DomReadyCalculator$1();
    
    AutoTestManager$Companion$path2DomReadyCalculator$1() {
      super(1);
    }
    
    public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param1Map) {
      AutoTestEvent autoTestEvent;
      l.b(param1Map, "map");
      List list1 = param1Map.get("stopLaunchTime");
      Map map2 = null;
      Map map1 = null;
      if (list1 != null) {
        AutoTestEvent autoTestEvent1 = (AutoTestEvent)m.f(list1);
      } else {
        list1 = null;
      } 
      List list2 = param1Map.get("stopReadFrame");
      param1Map = map2;
      if (list2 != null) {
        Iterator<Map<String, ? extends List<AutoTestEvent>>> iterator = list2.iterator();
        while (true) {
          param1Map = map1;
          if (iterator.hasNext()) {
            param1Map = iterator.next();
            if (l.a(((AutoTestEvent)param1Map).getValue(), "__path_frame__0-frame.js"))
              break; 
            continue;
          } 
          break;
        } 
        autoTestEvent = (AutoTestEvent)param1Map;
      } 
      return (list1 != null && autoTestEvent != null) ? Long.valueOf(list1.getTimestamp() - autoTestEvent.getTimestamp()) : Integer.valueOf(-1);
    }
  }
  
  static final class AutoTestManager$Companion$readPageFrameCalculator$1 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
    public static final AutoTestManager$Companion$readPageFrameCalculator$1 INSTANCE = new AutoTestManager$Companion$readPageFrameCalculator$1();
    
    AutoTestManager$Companion$readPageFrameCalculator$1() {
      super(1);
    }
    
    public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param1Map) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'map'
      //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_1
      //   7: ldc 'stopReadFrame'
      //   9: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   14: checkcast java/util/List
      //   17: astore_2
      //   18: aconst_null
      //   19: astore #4
      //   21: aconst_null
      //   22: astore_3
      //   23: aload_2
      //   24: ifnull -> 84
      //   27: aload_2
      //   28: checkcast java/lang/Iterable
      //   31: invokeinterface iterator : ()Ljava/util/Iterator;
      //   36: astore #5
      //   38: aload #5
      //   40: invokeinterface hasNext : ()Z
      //   45: ifeq -> 74
      //   48: aload #5
      //   50: invokeinterface next : ()Ljava/lang/Object;
      //   55: astore_2
      //   56: aload_2
      //   57: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   60: invokevirtual getValue : ()Ljava/lang/Object;
      //   63: ldc 'page-frame.js'
      //   65: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   68: ifeq -> 38
      //   71: goto -> 76
      //   74: aconst_null
      //   75: astore_2
      //   76: aload_2
      //   77: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   80: astore_2
      //   81: goto -> 86
      //   84: aconst_null
      //   85: astore_2
      //   86: aload_1
      //   87: ldc 'startReadFrame'
      //   89: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   94: checkcast java/util/List
      //   97: astore #5
      //   99: aload #4
      //   101: astore_1
      //   102: aload #5
      //   104: ifnull -> 159
      //   107: aload #5
      //   109: checkcast java/lang/Iterable
      //   112: invokeinterface iterator : ()Ljava/util/Iterator;
      //   117: astore #4
      //   119: aload_3
      //   120: astore_1
      //   121: aload #4
      //   123: invokeinterface hasNext : ()Z
      //   128: ifeq -> 154
      //   131: aload #4
      //   133: invokeinterface next : ()Ljava/lang/Object;
      //   138: astore_1
      //   139: aload_1
      //   140: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   143: invokevirtual getValue : ()Ljava/lang/Object;
      //   146: ldc 'page-frame.js'
      //   148: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   151: ifeq -> 119
      //   154: aload_1
      //   155: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   158: astore_1
      //   159: aload_2
      //   160: ifnull -> 180
      //   163: aload_1
      //   164: ifnull -> 180
      //   167: aload_2
      //   168: invokevirtual getTimestamp : ()J
      //   171: aload_1
      //   172: invokevirtual getTimestamp : ()J
      //   175: lsub
      //   176: invokestatic valueOf : (J)Ljava/lang/Long;
      //   179: areturn
      //   180: iconst_m1
      //   181: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   184: areturn
    }
  }
  
  static final class AutoTestManager$Companion$readPathFrameCalculator$1 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
    public static final AutoTestManager$Companion$readPathFrameCalculator$1 INSTANCE = new AutoTestManager$Companion$readPathFrameCalculator$1();
    
    AutoTestManager$Companion$readPathFrameCalculator$1() {
      super(1);
    }
    
    public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param1Map) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'map'
      //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_1
      //   7: ldc 'stopReadFrame'
      //   9: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   14: checkcast java/util/List
      //   17: astore_2
      //   18: aconst_null
      //   19: astore #4
      //   21: aconst_null
      //   22: astore_3
      //   23: aload_2
      //   24: ifnull -> 84
      //   27: aload_2
      //   28: checkcast java/lang/Iterable
      //   31: invokeinterface iterator : ()Ljava/util/Iterator;
      //   36: astore #5
      //   38: aload #5
      //   40: invokeinterface hasNext : ()Z
      //   45: ifeq -> 74
      //   48: aload #5
      //   50: invokeinterface next : ()Ljava/lang/Object;
      //   55: astore_2
      //   56: aload_2
      //   57: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   60: invokevirtual getValue : ()Ljava/lang/Object;
      //   63: ldc '__path_frame__0-frame.js'
      //   65: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   68: ifeq -> 38
      //   71: goto -> 76
      //   74: aconst_null
      //   75: astore_2
      //   76: aload_2
      //   77: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   80: astore_2
      //   81: goto -> 86
      //   84: aconst_null
      //   85: astore_2
      //   86: aload_1
      //   87: ldc 'startReadFrame'
      //   89: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   94: checkcast java/util/List
      //   97: astore #5
      //   99: aload #4
      //   101: astore_1
      //   102: aload #5
      //   104: ifnull -> 159
      //   107: aload #5
      //   109: checkcast java/lang/Iterable
      //   112: invokeinterface iterator : ()Ljava/util/Iterator;
      //   117: astore #4
      //   119: aload_3
      //   120: astore_1
      //   121: aload #4
      //   123: invokeinterface hasNext : ()Z
      //   128: ifeq -> 154
      //   131: aload #4
      //   133: invokeinterface next : ()Ljava/lang/Object;
      //   138: astore_1
      //   139: aload_1
      //   140: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   143: invokevirtual getValue : ()Ljava/lang/Object;
      //   146: ldc '__path_frame__0-frame.js'
      //   148: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   151: ifeq -> 119
      //   154: aload_1
      //   155: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   158: astore_1
      //   159: aload_2
      //   160: ifnull -> 180
      //   163: aload_1
      //   164: ifnull -> 180
      //   167: aload_2
      //   168: invokevirtual getTimestamp : ()J
      //   171: aload_1
      //   172: invokevirtual getTimestamp : ()J
      //   175: lsub
      //   176: invokestatic valueOf : (J)Ljava/lang/Long;
      //   179: areturn
      //   180: iconst_m1
      //   181: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   184: areturn
    }
  }
  
  static final class AutoTestManager$Companion$realSendPageFrameCalculator$1 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
    public static final AutoTestManager$Companion$realSendPageFrameCalculator$1 INSTANCE = new AutoTestManager$Companion$realSendPageFrameCalculator$1();
    
    AutoTestManager$Companion$realSendPageFrameCalculator$1() {
      super(1);
    }
    
    public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param1Map) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'map'
      //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_1
      //   7: ldc 'evaluateJavascript'
      //   9: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   14: checkcast java/util/List
      //   17: astore_2
      //   18: aconst_null
      //   19: astore_3
      //   20: aload_2
      //   21: ifnull -> 93
      //   24: aload_2
      //   25: checkcast java/lang/Iterable
      //   28: invokeinterface iterator : ()Ljava/util/Iterator;
      //   33: astore #4
      //   35: aload #4
      //   37: invokeinterface hasNext : ()Z
      //   42: ifeq -> 83
      //   45: aload #4
      //   47: invokeinterface next : ()Ljava/lang/Object;
      //   52: astore_2
      //   53: aload_2
      //   54: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   57: invokevirtual getValue : ()Ljava/lang/Object;
      //   60: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
      //   63: checkcast java/lang/CharSequence
      //   66: ldc 'PAGE_FRAME'
      //   68: checkcast java/lang/CharSequence
      //   71: iconst_0
      //   72: iconst_2
      //   73: aconst_null
      //   74: invokestatic b : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      //   77: ifeq -> 35
      //   80: goto -> 85
      //   83: aconst_null
      //   84: astore_2
      //   85: aload_2
      //   86: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   89: astore_2
      //   90: goto -> 95
      //   93: aconst_null
      //   94: astore_2
      //   95: aload_1
      //   96: ldc 'sendLoadPageFrame'
      //   98: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   103: checkcast java/util/List
      //   106: astore #4
      //   108: aload_3
      //   109: astore_1
      //   110: aload #4
      //   112: ifnull -> 124
      //   115: aload #4
      //   117: invokestatic f : (Ljava/util/List;)Ljava/lang/Object;
      //   120: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   123: astore_1
      //   124: aload_2
      //   125: ifnull -> 145
      //   128: aload_1
      //   129: ifnull -> 145
      //   132: aload_2
      //   133: invokevirtual getTimestamp : ()J
      //   136: aload_1
      //   137: invokevirtual getTimestamp : ()J
      //   140: lsub
      //   141: invokestatic valueOf : (J)Ljava/lang/Long;
      //   144: areturn
      //   145: iconst_m1
      //   146: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   149: areturn
    }
  }
  
  static final class AutoTestManager$Companion$realSendPathFrameCalculator$1 extends m implements b<Map<String, ? extends List<? extends AutoTestEvent>>, Object> {
    public static final AutoTestManager$Companion$realSendPathFrameCalculator$1 INSTANCE = new AutoTestManager$Companion$realSendPathFrameCalculator$1();
    
    AutoTestManager$Companion$realSendPathFrameCalculator$1() {
      super(1);
    }
    
    public final Object invoke(Map<String, ? extends List<AutoTestEvent>> param1Map) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'map'
      //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_1
      //   7: ldc 'evaluateJavascript'
      //   9: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   14: checkcast java/util/List
      //   17: astore_2
      //   18: aconst_null
      //   19: astore_3
      //   20: aload_2
      //   21: ifnull -> 93
      //   24: aload_2
      //   25: checkcast java/lang/Iterable
      //   28: invokeinterface iterator : ()Ljava/util/Iterator;
      //   33: astore #4
      //   35: aload #4
      //   37: invokeinterface hasNext : ()Z
      //   42: ifeq -> 83
      //   45: aload #4
      //   47: invokeinterface next : ()Ljava/lang/Object;
      //   52: astore_2
      //   53: aload_2
      //   54: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   57: invokevirtual getValue : ()Ljava/lang/Object;
      //   60: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
      //   63: checkcast java/lang/CharSequence
      //   66: ldc 'PATH_FRAME'
      //   68: checkcast java/lang/CharSequence
      //   71: iconst_0
      //   72: iconst_2
      //   73: aconst_null
      //   74: invokestatic b : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
      //   77: ifeq -> 35
      //   80: goto -> 85
      //   83: aconst_null
      //   84: astore_2
      //   85: aload_2
      //   86: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   89: astore_2
      //   90: goto -> 95
      //   93: aconst_null
      //   94: astore_2
      //   95: aload_1
      //   96: ldc 'sendLoadPathFrame'
      //   98: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   103: checkcast java/util/List
      //   106: astore #4
      //   108: aload_3
      //   109: astore_1
      //   110: aload #4
      //   112: ifnull -> 124
      //   115: aload #4
      //   117: invokestatic f : (Ljava/util/List;)Ljava/lang/Object;
      //   120: checkcast com/tt/miniapp/autotest/AutoTestEvent
      //   123: astore_1
      //   124: aload_2
      //   125: ifnull -> 145
      //   128: aload_1
      //   129: ifnull -> 145
      //   132: aload_2
      //   133: invokevirtual getTimestamp : ()J
      //   136: aload_1
      //   137: invokevirtual getTimestamp : ()J
      //   140: lsub
      //   141: invokestatic valueOf : (J)Ljava/lang/Long;
      //   144: areturn
      //   145: iconst_m1
      //   146: invokestatic valueOf : (I)Ljava/lang/Integer;
      //   149: areturn
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\autotest\AutoTestManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */