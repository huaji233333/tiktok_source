package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.service.ServiceProvider;
import com.tt.miniapp.service.hostevent.HostEventListener;
import com.tt.miniapp.service.hostevent.HostEventMiniAppService;
import com.tt.miniapp.service.hostevent.HostEventMiniAppServiceInterface;
import com.tt.miniapp.service.hostevent.HostEventUtils;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.option.e.e;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class ApiHostEventListenerCtrl extends b {
  protected static ConcurrentHashMap<String, HostEventListener> eventListeners = new ConcurrentHashMap<String, HostEventListener>();
  
  public ApiHostEventListenerCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  protected static void callback(String paramString1, String paramString2) {
    j j = AppbrandApplication.getInst().getJsBridge();
    if (j == null)
      return; 
    j.sendMsgToJsCore(paramString1, paramString2);
  }
  
  private String getErrorMsg(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errMsg", paramString);
    } catch (JSONException jSONException) {}
    return jSONObject.toString();
  }
  
  public void act() {
    String str = "";
    try {
      String str1 = (new JSONObject(this.mArgs)).optString("eventName");
      str = str1;
      if (!ApiPermissionManager.canUseHostMethod(HostEventUtils.trimHostEventPrefix(str1))) {
        str = str1;
        callback(str1, getErrorMsg("unauthorized event"));
        return;
      } 
      str = str1;
      HostEventMiniAppServiceInterface hostEventMiniAppServiceInterface = (HostEventMiniAppServiceInterface)ServiceProvider.getInstance().getService(HostEventMiniAppService.class);
      if (hostEventMiniAppServiceInterface != null) {
        str = str1;
        onAct(str1, hostEventMiniAppServiceInterface);
      } 
      return;
    } catch (Exception exception) {
      callback(str, getErrorMsg(exception.toString()));
      return;
    } 
  }
  
  public abstract void onAct(String paramString, HostEventMiniAppServiceInterface paramHostEventMiniAppServiceInterface);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiHostEventListenerCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */