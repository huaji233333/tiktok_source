package com.tt.miniapp.service.hostevent;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.ServiceBindManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.ProcessUtil;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public class HostEventMiniAppService implements ServiceBindManager.HostProcessLifeListener, HostEventMiniAppServiceInterface {
  public ConcurrentHashMap<String, MiniAppListeners> eventListenersInMiniApp = new ConcurrentHashMap<String, MiniAppListeners>();
  
  public HostEventMiniAppService() {
    AppProcessManager.registerHostProcessLifeListener(this);
  }
  
  public void addHostEventListener(String paramString1, String paramString2, HostEventListener paramHostEventListener) {
    String str = HostEventUtils.checkAndModifyEventNName(paramString2);
    MiniAppListeners miniAppListeners2 = this.eventListenersInMiniApp.get(str);
    MiniAppListeners miniAppListeners1 = miniAppListeners2;
    if (miniAppListeners2 == null) {
      miniAppListeners2 = new MiniAppListeners(paramString1);
      this.eventListenersInMiniApp.put(str, miniAppListeners2);
      miniAppListeners1 = miniAppListeners2;
      if (ProcessUtil.isMiniappProcess()) {
        InnerHostProcessBridge.addHostListener(paramString1, str);
        miniAppListeners1 = miniAppListeners2;
      } 
    } 
    miniAppListeners1.add(paramHostEventListener);
  }
  
  public void onAlive(boolean paramBoolean) {
    if (paramBoolean)
      ThreadUtil.runNotOnUIThread(new Runnable() {
            public void run() {
              if (HostEventMiniAppService.this.eventListenersInMiniApp.size() > 0) {
                String str = (AppbrandApplicationImpl.getInst().getAppInfo()).appId;
                ServiceBindManager.getInstance().bindHostService();
                InnerHostProcessBridge.setTmaLaunchFlag();
                Iterator<String> iterator = HostEventMiniAppService.this.eventListenersInMiniApp.keySet().iterator();
                while (iterator.hasNext())
                  InnerHostProcessBridge.addHostListener(str, iterator.next()); 
              } 
            }
          }); 
  }
  
  public void onDied() {}
  
  public void onReceiveMiniAppHostEvent(String paramString1, String paramString2, JSONObject paramJSONObject) {
    paramString2 = HostEventUtils.checkAndModifyEventNName(paramString2);
    MiniAppListeners miniAppListeners = this.eventListenersInMiniApp.get(paramString2);
    if (miniAppListeners != null) {
      if (!miniAppListeners.miniAppId.equals(paramString1)) {
        AppBrandLogger.e("HostEventMiniAppService", new Object[] { "error match miniAppId with event ", paramString2, miniAppListeners.miniAppId, paramString1 });
        return;
      } 
      for (HostEventListener hostEventListener : miniAppListeners) {
        if (hostEventListener != null)
          hostEventListener.onHostEvent(paramString2, paramJSONObject); 
      } 
    } 
  }
  
  public void removeHostEventListener(String paramString1, String paramString2, HostEventListener paramHostEventListener) {
    paramString2 = HostEventUtils.checkAndModifyEventNName(paramString2);
    MiniAppListeners miniAppListeners = this.eventListenersInMiniApp.get(paramString2);
    if (miniAppListeners != null) {
      miniAppListeners.remove(paramHostEventListener);
      if (miniAppListeners.size() == 0) {
        this.eventListenersInMiniApp.remove(paramString2);
        if (ProcessUtil.isMiniappProcess())
          InnerHostProcessBridge.removeHostListener(paramString1, paramString2); 
      } 
    } 
  }
  
  static class MiniAppListeners extends Vector<HostEventListener> {
    String miniAppId;
    
    public MiniAppListeners(String param1String) {
      this.miniAppId = param1String;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\hostevent\HostEventMiniAppService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */