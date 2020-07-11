package com.tt.miniapp.event.remedy;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.MiniProcessMonitor;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class InnerMiniProcessLogHelper {
  private static final Map<String, InnerEventHandler> sEventLogHandlerMap = new ConcurrentHashMap<String, InnerEventHandler>();
  
  static {
    AppProcessManager.registerProcessLifeListener(new MiniProcessMonitor.ProcessLifeListener() {
          public final void onAlive(AppProcessManager.ProcessInfo param1ProcessInfo) {
            InnerMiniProcessLogHelper.onMiniAppProcessAlive(param1ProcessInfo);
          }
          
          public final void onDied(AppProcessManager.ProcessInfo param1ProcessInfo) {
            InnerMiniProcessLogHelper.onMiniAppProcessDied(param1ProcessInfo);
          }
        });
  }
  
  public static boolean innerHandleEventLog(String paramString, JSONObject paramJSONObject) {
    StringBuilder stringBuilder = new StringBuilder("innerHandleEventLog: ");
    stringBuilder.append(paramString);
    stringBuilder.append(", ");
    stringBuilder.append(paramJSONObject);
    AppBrandLogger.d("tma_InnerMiniProcessLogHelper", new Object[] { stringBuilder.toString() });
    if (paramJSONObject != null) {
      if (TextUtils.isEmpty(paramString))
        return false; 
      String str = paramJSONObject.optString("mp_id");
      if (TextUtils.isEmpty(str)) {
        StringBuilder stringBuilder1 = new StringBuilder("49411_innerHandleEventLog: empty appId: ");
        stringBuilder1.append(str);
        AppBrandLogger.d("tma_InnerMiniProcessLogHelper", new Object[] { stringBuilder1.toString() });
        return paramJSONObject.optBoolean("__inner_handled", false);
      } 
      if (sEventLogHandlerMap.get(str) == null) {
        Map<String, InnerEventHandler> map;
        StringBuilder stringBuilder1;
        synchronized (sEventLogHandlerMap) {
          if (sEventLogHandlerMap.get(str) == null) {
            InnerEventHandler innerEventHandler1 = new InnerEventHandler(str);
            sEventLogHandlerMap.put(str, innerEventHandler1);
            AppProcessManager.ProcessInfo processInfo = AppProcessManager.getProcessInfoByAppId(str);
            if (processInfo != null) {
              String str1 = processInfo.mProcessName;
              if (processInfo.mMiniProcessMonitor.isAlive())
                innerEventHandler1.onAlive(str1); 
            } 
          } 
          stringBuilder1 = new StringBuilder("49411_mem_event_handler_add: ");
          stringBuilder1.append(str);
          stringBuilder1.append(" : ");
          stringBuilder1.append(sEventLogHandlerMap.size());
          AppBrandLogger.d("tma_InnerMiniProcessLogHelper", new Object[] { stringBuilder1.toString() });
        } 
      } 
      InnerEventHandler innerEventHandler = sEventLogHandlerMap.get(str);
      boolean bool2 = paramJSONObject.optBoolean("__inner_handled", false);
      boolean bool1 = bool2;
      if (innerEventHandler != null)
        bool1 = innerEventHandler.handle(new EventEntity(paramString, paramJSONObject, bool2)); 
      return bool1;
    } 
    return false;
  }
  
  public static void onMiniAppProcessAlive(AppProcessManager.ProcessInfo paramProcessInfo) {
    if (paramProcessInfo != null) {
      String str = paramProcessInfo.mAppId;
      if (!TextUtils.isEmpty(str)) {
        InnerEventHandler innerEventHandler = sEventLogHandlerMap.get(str);
        if (innerEventHandler != null)
          innerEventHandler.onAlive(paramProcessInfo.mProcessName); 
      } 
    } 
  }
  
  public static void onMiniAppProcessDied(AppProcessManager.ProcessInfo paramProcessInfo) {
    if (paramProcessInfo != null) {
      String str = paramProcessInfo.mAppId;
      if (!TextUtils.isEmpty(str)) {
        InnerEventHandler innerEventHandler = sEventLogHandlerMap.get(str);
        if (innerEventHandler != null)
          innerEventHandler.onDied(); 
      } 
    } 
    Application application = AppbrandContext.getInst().getApplicationContext();
    Iterator<Map.Entry> iterator = sEventLogHandlerMap.entrySet().iterator();
    while (iterator.hasNext()) {
      InnerEventHandler innerEventHandler = (InnerEventHandler)((Map.Entry)iterator.next()).getValue();
      if (innerEventHandler != null && !AppProcessManager.isAppProcessExist((Context)application, innerEventHandler.mMiniAppId)) {
        innerEventHandler.onDied();
        iterator.remove();
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder("onMiniAppProcessDied: {event:");
    stringBuilder.append(sEventLogHandlerMap.size());
    stringBuilder.append("}");
    AppBrandLogger.d("tma_InnerMiniProcessLogHelper", new Object[] { stringBuilder.toString() });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\InnerMiniProcessLogHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */