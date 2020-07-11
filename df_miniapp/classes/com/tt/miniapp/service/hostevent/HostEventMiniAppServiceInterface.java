package com.tt.miniapp.service.hostevent;

import com.tt.miniapp.service.PureServiceInterface;
import org.json.JSONObject;

public interface HostEventMiniAppServiceInterface extends PureServiceInterface {
  void addHostEventListener(String paramString1, String paramString2, HostEventListener paramHostEventListener);
  
  void onReceiveMiniAppHostEvent(String paramString1, String paramString2, JSONObject paramJSONObject);
  
  void removeHostEventListener(String paramString1, String paramString2, HostEventListener paramHostEventListener);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\hostevent\HostEventMiniAppServiceInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */