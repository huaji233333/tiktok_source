package com.tt.miniapp.util.timeline;

import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.LifeCycleManager;
import com.tt.miniapp.LifeCycleManager.LifecycleInterest;
import com.tt.miniapp.manager.AppbrandBroadcastService;
import com.tt.miniapp.thread.HandlerThreadUtil;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.a.ac;
import d.f.b.g;
import d.f.b.l;
import d.n;
import d.t;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class MpTimeLineReporter extends AppbrandServiceManager.ServiceBase {
  public static final Companion Companion = new Companion(null);
  
  private final HandlerThread mHt;
  
  private final Map<String, AbsTimeLineSender> mTimeLineSenders;
  
  public MpTimeLineReporter(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
    HandlerThread handlerThread = HandlerThreadUtil.getBackgroundHandlerThread();
    l.a(handlerThread, "HandlerThreadUtil.getBackgroundHandlerThread()");
    this.mHt = handlerThread;
    Looper looper1 = this.mHt.getLooper();
    l.a(looper1, "mHt.looper");
    n n1 = t.a("graph", new GraphTimeLineSender(looper1));
    Looper looper2 = this.mHt.getLooper();
    l.a(looper2, "mHt.looper");
    n n2 = t.a("ide", new IDETimeLineSender(looper2));
    Looper looper3 = this.mHt.getLooper();
    l.a(looper3, "mHt.looper");
    this.mTimeLineSenders = ac.a(new n[] { n1, n2, t.a("mp", new SaladarTimeLineSender(looper3)) });
  }
  
  private final boolean addPoint(MpPoint paramMpPoint) {
    Iterator<AbsTimeLineSender> iterator = this.mTimeLineSenders.values().iterator();
    while (iterator.hasNext())
      ((AbsTimeLineSender)iterator.next()).addPoint(paramMpPoint); 
    return true;
  }
  
  public boolean addPoint(String paramString) {
    l.b(paramString, "name");
    return addPoint(new MpPoint(paramString, System.currentTimeMillis(), SystemClock.elapsedRealtime(), null, false));
  }
  
  public final boolean addPoint(String paramString, long paramLong1, long paramLong2, JSONObject paramJSONObject) {
    l.b(paramString, "name");
    return addPoint(new MpPoint(paramString, paramLong1, paramLong2, paramJSONObject, true));
  }
  
  public final boolean addPoint(String paramString, long paramLong1, long paramLong2, JSONObject paramJSONObject, boolean paramBoolean) {
    l.b(paramString, "name");
    return addPoint(new MpPoint(paramString, paramLong1, paramLong2, paramJSONObject, paramBoolean));
  }
  
  public final boolean addPoint(String paramString, JSONObject paramJSONObject) {
    l.b(paramString, "name");
    return addPoint(new MpPoint(paramString, System.currentTimeMillis(), SystemClock.elapsedRealtime(), paramJSONObject, false));
  }
  
  public final void flush() {
    Iterator<AbsTimeLineSender> iterator = this.mTimeLineSenders.values().iterator();
    while (iterator.hasNext())
      ((AbsTimeLineSender)iterator.next()).flush(); 
  }
  
  public final boolean isJsEnableTrace() {
    boolean bool;
    AbsTimeLineSender absTimeLineSender = this.mTimeLineSenders.get("ide");
    if (absTimeLineSender != null) {
      bool = absTimeLineSender.isEnableTrace();
    } else {
      bool = false;
    } 
    if (!bool) {
      absTimeLineSender = this.mTimeLineSenders.get("graph");
      if (absTimeLineSender != null) {
        bool = absTimeLineSender.isEnableTrace();
      } else {
        bool = false;
      } 
      if (!bool)
        return false; 
    } 
    return true;
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_CREATE})
  public final void onAppCreate() {
    MpTimeLineReporter$onAppCreate$receiver$1 mpTimeLineReporter$onAppCreate$receiver$1 = new MpTimeLineReporter$onAppCreate$receiver$1();
    AppbrandBroadcastService appbrandBroadcastService = (AppbrandBroadcastService)this.mApp.getService(AppbrandBroadcastService.class);
    appbrandBroadcastService.registerReceiver(0, mpTimeLineReporter$onAppCreate$receiver$1);
    appbrandBroadcastService.registerReceiver(1, mpTimeLineReporter$onAppCreate$receiver$1);
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_HIDE})
  public final void onAppHide() {
    addPoint("enter_background");
    flush();
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_INFO_INITED})
  public final void onAppInfoInited(LifeCycleManager.LifeCycleEvent paramLifeCycleEvent, AppInfoEntity paramAppInfoEntity) {
    l.b(paramLifeCycleEvent, "event");
    l.b(paramAppInfoEntity, "appInfo");
    Iterator<AbsTimeLineSender> iterator = this.mTimeLineSenders.values().iterator();
    while (iterator.hasNext())
      ((AbsTimeLineSender)iterator.next()).onAppInfoInited(paramAppInfoEntity); 
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_SHOW})
  public final void onAppShow() {
    addPoint("enter_foreground");
  }
  
  public final void reportTimelineGraph(Callback<String> paramCallback) {
    l.b(paramCallback, "callback");
    flush();
    AbsTimeLineSender absTimeLineSender = this.mTimeLineSenders.get("graph");
    if (absTimeLineSender != null)
      absTimeLineSender.sendMessage(GraphTimeLineSender.Companion.getMSG_REPORT_GRAPH$miniapp_release(), paramCallback); 
  }
  
  public final void sendJsEndCollectPoints() {
    AbsTimeLineSender absTimeLineSender = this.mTimeLineSenders.get("ide");
    if (absTimeLineSender != null)
      AbsTimeLineSender.sendMessage$default(absTimeLineSender, IDETimeLineSender.Companion.getMSG_JS_END_COLLECT_POINT(), null, 2, null); 
  }
  
  public final void sendPointsDirectly(String paramString) {
    l.b(paramString, "points");
    Iterator<AbsTimeLineSender> iterator = this.mTimeLineSenders.values().iterator();
    while (iterator.hasNext())
      ((AbsTimeLineSender)iterator.next()).sendPointsDirectly(paramString); 
  }
  
  public static interface Callback<T> {
    void onError(String param1String);
    
    void onSuccess(T param1T);
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public static final class ExtraBuilder {
    private Map<String, Object> mExtraMap = new HashMap<String, Object>();
    
    public final JSONObject build() {
      return new JSONObject(this.mExtraMap);
    }
    
    public final ExtraBuilder kv(String param1String, Object param1Object) {
      l.b(param1String, "key");
      this.mExtraMap.put(param1String, param1Object);
      return this;
    }
  }
  
  static final class MpTimeLineReporter$onAppCreate$receiver$1 implements AppbrandBroadcastService.LightBroadcastReceiver {
    public final void onReceive(int param1Int, Context param1Context, Intent param1Intent) {
      if (param1Int == 0) {
        MpTimeLineReporter.this.addPoint("throw_exception_log", System.currentTimeMillis(), SystemClock.elapsedRealtime(), (new MpTimeLineReporter.ExtraBuilder()).kv("reason", Integer.valueOf(2)).build());
        return;
      } 
      if (param1Int == 1)
        MpTimeLineReporter.this.addPoint("throw_exception_log", System.currentTimeMillis(), SystemClock.elapsedRealtime(), (new MpTimeLineReporter.ExtraBuilder()).kv("reason", Integer.valueOf(1)).build()); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\timeline\MpTimeLineReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */