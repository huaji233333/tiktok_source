package com.tt.miniapp.msg;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.service.hostevent.HostEventListener;
import com.tt.miniapp.service.hostevent.HostEventMiniAppServiceInterface;
import com.tt.option.e.e;

public class ApiRemoveHostEventListenerCtrl extends ApiHostEventListenerCtrl {
  public ApiRemoveHostEventListenerCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public String getActionName() {
    return "removeHostEventListener";
  }
  
  public void onAct(String paramString, HostEventMiniAppServiceInterface paramHostEventMiniAppServiceInterface) {
    if (eventListeners.containsKey(paramString)) {
      HostEventListener hostEventListener = eventListeners.get(paramString);
      paramHostEventMiniAppServiceInterface.removeHostEventListener((AppbrandApplicationImpl.getInst().getAppInfo()).appId, paramString, hostEventListener);
      eventListeners.remove(paramString);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiRemoveHostEventListenerCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */