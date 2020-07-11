package com.tt.miniapp.msg.sync;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.route.RouteEventCtrl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import org.json.JSONObject;

public class GetLaunchOptionsSync extends SyncMsgCtrl {
  public GetLaunchOptionsSync(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      RouteEventCtrl routeEventCtrl = AppbrandApplicationImpl.getInst().getRouteEventCtrl();
      if (routeEventCtrl == null)
        return makeFailMsg("route control is null"); 
      JSONObject jSONObject = routeEventCtrl.getLaunchOption();
      jSONObject.put("isSticky", true);
      jSONObject.put("shareTicket", (AppbrandApplication.getInst().getAppInfo()).shareTicket);
      return makeOkMsg(jSONObject);
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "SyncMsgCtrl", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "getLaunchOptionsSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\GetLaunchOptionsSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */