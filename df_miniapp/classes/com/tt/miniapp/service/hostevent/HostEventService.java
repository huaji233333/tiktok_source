package com.tt.miniapp.service.hostevent;

import android.os.SystemClock;
import android.text.TextUtils;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.bridge.InnerMiniAppProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class HostEventService implements HostEventServiceInterface {
  private static final long startTime = SystemClock.elapsedRealtime();
  
  private ConcurrentHashMap<String, Vector<String>> hostEventListeners = new ConcurrentHashMap<String, Vector<String>>();
  
  private boolean notifyMiniAppHostEvent(String paramString1, String paramString2, JSONObject paramJSONObject) {
    paramString2 = HostEventUtils.checkAndModifyEventNName(paramString2);
    if (!TextUtils.isEmpty(paramString1)) {
      AppProcessManager.ProcessInfo processInfo = AppProcessManager.getProcessInfoByAppId(paramString1);
      if (processInfo != null) {
        InnerMiniAppProcessBridge.dispatchHostEventToMiniApp(processInfo.mProcessIdentity, paramString1, paramString2, paramJSONObject);
        return true;
      } 
      AppBrandLogger.w("host_event", new Object[] { "notifyMiniAppHostEvent info is null" });
    } 
    return false;
  }
  
  public void dispatchHostEvent(String paramString, JSONObject paramJSONObject) {
    paramString = HostEventUtils.checkAndModifyEventNName(paramString);
    if (!TextUtils.isEmpty(paramString) && this.hostEventListeners.containsKey(paramString)) {
      Vector vector = this.hostEventListeners.get(paramString);
      if (vector != null && vector.size() > 0) {
        Vector<String> vector1 = new Vector();
        for (String str : vector) {
          if (!notifyMiniAppHostEvent(str, paramString, paramJSONObject) && SystemClock.elapsedRealtime() - startTime > 5000L)
            vector1.add(str); 
        } 
        Iterator<String> iterator = vector1.iterator();
        while (iterator.hasNext())
          vector.remove(iterator.next()); 
        if (vector.size() == 0)
          this.hostEventListeners.remove(paramString); 
        return;
      } 
      this.hostEventListeners.remove(paramString);
    } 
  }
  
  public void hostProcessAddHostEventListener(String paramString1, String paramString2) {
    paramString2 = HostEventUtils.checkAndModifyEventNName(paramString2);
    Vector<String> vector = this.hostEventListeners.get(paramString2);
    if (vector == null) {
      vector = new Vector();
      this.hostEventListeners.put(paramString2, vector);
      vector.add(paramString1);
      return;
    } 
    boolean bool = false;
    Iterator<String> iterator = vector.iterator();
    while (iterator.hasNext()) {
      if (((String)iterator.next()).equals(paramString1))
        bool = true; 
    } 
    if (!bool)
      vector.add(paramString1); 
  }
  
  public void hostProcessRemoveHostEventListener(String paramString1, String paramString2) {
    paramString2 = HostEventUtils.checkAndModifyEventNName(paramString2);
    Vector vector = this.hostEventListeners.get(paramString2);
    if (vector != null) {
      Iterator<String> iterator = vector.iterator();
      while (iterator.hasNext()) {
        if (((String)iterator.next()).equals(paramString1)) {
          vector.remove(paramString1);
          break;
        } 
      } 
      if (vector.size() == 0)
        this.hostEventListeners.remove(paramString2); 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\hostevent\HostEventService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */