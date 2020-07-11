package com.tt.miniapp.service.hostevent;

import com.tt.miniapp.service.PureServiceInterface;
import org.json.JSONObject;

public interface HostEventServiceInterface extends PureServiceInterface {
  void dispatchHostEvent(String paramString, JSONObject paramJSONObject);
  
  void hostProcessAddHostEventListener(String paramString1, String paramString2);
  
  void hostProcessRemoveHostEventListener(String paramString1, String paramString2);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\hostevent\HostEventServiceInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */