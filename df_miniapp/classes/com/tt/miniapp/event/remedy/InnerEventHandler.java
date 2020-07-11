package com.tt.miniapp.event.remedy;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.appbrand.mmkv.MMKV;
import com.tt.miniapp.event.remedy.delegate.DelegateLoadDetail;
import com.tt.miniapp.event.remedy.delegate.DelegateLoadDomReady;
import com.tt.miniapp.event.remedy.delegate.DelegateLoadResult;
import com.tt.miniapp.event.remedy.delegate.DelegatePageLoadResult;
import com.tt.miniapp.event.remedy.delegate.DelegateStayPage;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import com.tt.miniapphost.util.CharacterUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class InnerEventHandler {
  private List<AbsEventDelegate> mDelegates;
  
  String mMiniAppId;
  
  public InnerEventHandler(String paramString) {
    this.mMiniAppId = paramString;
    this.mDelegates = new ArrayList<AbsEventDelegate>();
    initDelegate();
    StringBuilder stringBuilder = new StringBuilder("49411_AbsInnerLogHandler: ");
    stringBuilder.append(paramString);
    AppBrandLogger.d("InnerEventHandler", new Object[] { stringBuilder.toString() });
  }
  
  public static EventEntity buildEntity(String paramString1, String paramString2) {
    if (!TextUtils.isEmpty(paramString1)) {
      if (TextUtils.isEmpty(paramString2))
        return null; 
      paramString1 = EventEntityHelper.getEventNameFromSaveKey(paramString1);
      try {
        return new EventEntity(paramString1, new JSONObject(paramString2), false);
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("InnerEventHandler", "host_check", (Throwable)jSONException);
      } 
    } 
    return null;
  }
  
  public static void checkAndReportSavedEvents() {
    AppBrandLogger.d("InnerEventHandler", new Object[] { "checkAndReportSavedEvents" });
    ThreadPools.defaults().execute(new Runnable() {
          public final void run() {
            EventEntity eventEntity;
            SharedPreferences sharedPreferences = InnerEventHandler.getEventSP();
            if (sharedPreferences == null) {
              AppBrandLogger.d("InnerEventHandler", new Object[] { "host_check: no prefs" });
              return;
            } 
            if (sharedPreferences instanceof MMKV) {
              String[] arrayOfString = ((MMKV)sharedPreferences).allKeysMMKV();
              if (arrayOfString == null || arrayOfString.length <= 0) {
                AppBrandLogger.d("InnerEventHandler", new Object[] { "host_check: mmkv empty events" });
                return;
              } 
              ArrayList<EventEntity> arrayList = new ArrayList();
              int j = arrayOfString.length;
              int i = 0;
              while (true) {
                ArrayList<EventEntity> arrayList1 = arrayList;
                if (i < j) {
                  String str = arrayOfString[i];
                  eventEntity = InnerEventHandler.buildEntity(str, sharedPreferences.getString(str, CharacterUtils.empty()));
                  if (eventEntity != null)
                    arrayList.add(eventEntity); 
                  i++;
                  continue;
                } 
                break;
              } 
            } else {
              Map map = sharedPreferences.getAll();
              if (map == null || map.isEmpty()) {
                AppBrandLogger.d("InnerEventHandler", new Object[] { "host_check: prefs empty events" });
                return;
              } 
              ArrayList<EventEntity> arrayList = new ArrayList();
              Iterator<String> iterator = map.keySet().iterator();
              while (true) {
                ArrayList<EventEntity> arrayList1 = arrayList;
                if (iterator.hasNext()) {
                  String str = iterator.next();
                  Object object = map.get(str);
                  if (object instanceof String) {
                    eventEntity = InnerEventHandler.buildEntity(str, object.toString());
                    if (eventEntity == null)
                      continue; 
                    arrayList.add(eventEntity);
                  } 
                  continue;
                } 
                break;
              } 
            } 
            AppBrandLogger.d("InnerEventHandler", new Object[] { "checkAndReportSavedEvents: clear" });
            sharedPreferences.edit().clear().apply();
            if (eventEntity.isEmpty()) {
              AppBrandLogger.d("InnerEventHandler", new Object[] { "host_check: empty events" });
              return;
            } 
            StringBuilder stringBuilder = new StringBuilder("checkAndReportSavedEvents: report=");
            stringBuilder.append(eventEntity.size());
            AppBrandLogger.d("InnerEventHandler", new Object[] { stringBuilder.toString() });
            InnerEventHandler.report((List<EventEntity>)eventEntity);
          }
        });
  }
  
  public static SharedPreferences getEventSP() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    return (application == null) ? null : KVUtil.getSharedPreferences((Context)application, "mp_events_prefs");
  }
  
  private void initDelegate() {
    this.mDelegates.add(new DelegateLoadResult(this));
    this.mDelegates.add(new DelegatePageLoadResult(this));
    this.mDelegates.add(new DelegateLoadDetail(this));
    this.mDelegates.add(new DelegateStayPage(this));
    this.mDelegates.add(new DelegateLoadDomReady(this));
  }
  
  public static void report(List<EventEntity> paramList) {
    if (paramList != null) {
      if (paramList.isEmpty())
        return; 
      ISyncHostDataHandler iSyncHostDataHandler = AppbrandContext.getInst().getSyncHandler("actionLog");
      if (iSyncHostDataHandler == null) {
        AppBrandLogger.d("InnerEventHandler", new Object[] { "report: null handler" });
        return;
      } 
      for (EventEntity eventEntity : paramList) {
        StringBuilder stringBuilder = new StringBuilder("report: ");
        stringBuilder.append(eventEntity);
        AppBrandLogger.d("InnerEventHandler", new Object[] { stringBuilder.toString() });
        iSyncHostDataHandler.action(CrossProcessDataEntity.Builder.create().put("logEventName", eventEntity.eventName).put("logEventData", eventEntity.eventData).build());
      } 
    } 
  }
  
  public void clearEntity() {
    SharedPreferences sharedPreferences = getEventSP();
    if (sharedPreferences == null)
      return; 
    sharedPreferences.edit().clear().apply();
  }
  
  public void delEntity(EventEntity paramEventEntity) {
    SharedPreferences sharedPreferences = getEventSP();
    if (sharedPreferences != null) {
      if (paramEventEntity == null)
        return; 
      sharedPreferences.edit().remove(EventEntityHelper.getSaveKey(this.mMiniAppId, paramEventEntity)).apply();
    } 
  }
  
  boolean handle(EventEntity paramEventEntity) {
    boolean bool = paramEventEntity.innerHandled;
    Iterator<AbsEventDelegate> iterator = this.mDelegates.iterator();
    while (iterator.hasNext()) {
      boolean bool1 = ((AbsEventDelegate)iterator.next()).handle(paramEventEntity);
      if (bool || bool1) {
        bool = true;
        continue;
      } 
      bool = false;
    } 
    return bool;
  }
  
  void onAlive(String paramString) {
    Iterator<AbsEventDelegate> iterator = this.mDelegates.iterator();
    while (iterator.hasNext())
      ((AbsEventDelegate)iterator.next()).onAlive(paramString, this.mMiniAppId); 
  }
  
  void onDied() {
    ArrayList<EventEntity> arrayList = new ArrayList();
    for (AbsEventDelegate absEventDelegate : this.mDelegates) {
      List<EventEntity> list = absEventDelegate.getFinalLogs();
      if (list != null && !list.isEmpty())
        arrayList.addAll(list); 
      absEventDelegate.clear();
    } 
    report(arrayList);
    this.mDelegates.clear();
  }
  
  public void saveEntity(EventEntity paramEventEntity) {
    SharedPreferences sharedPreferences = getEventSP();
    if (sharedPreferences != null) {
      if (paramEventEntity == null)
        return; 
      sharedPreferences.edit().putString(EventEntityHelper.getSaveKey(this.mMiniAppId, paramEventEntity), EventEntityHelper.getSaveVal(paramEventEntity)).apply();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\InnerEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */