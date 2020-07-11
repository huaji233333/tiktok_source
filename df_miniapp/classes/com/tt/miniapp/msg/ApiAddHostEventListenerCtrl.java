package com.tt.miniapp.msg;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.service.hostevent.HostEventListener;
import com.tt.miniapp.service.hostevent.HostEventMiniAppServiceInterface;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiAddHostEventListenerCtrl extends ApiHostEventListenerCtrl {
  public ApiAddHostEventListenerCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public String getActionName() {
    return "addHostEventListener";
  }
  
  public void onAct(String paramString, HostEventMiniAppServiceInterface paramHostEventMiniAppServiceInterface) {
    HostEventListener hostEventListener2 = eventListeners.get(paramString);
    HostEventListener hostEventListener1 = hostEventListener2;
    if (hostEventListener2 == null) {
      hostEventListener1 = new HostEventListener() {
          public void onHostEvent(String param1String, JSONObject param1JSONObject) {
            ApiHostEventListenerCtrl.callback(param1String, param1JSONObject.toString());
          }
        };
      eventListeners.put(paramString, hostEventListener1);
    } 
    paramHostEventMiniAppServiceInterface.addHostEventListener((AppbrandApplicationImpl.getInst().getAppInfo()).appId, paramString, hostEventListener1);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiAddHostEventListenerCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */