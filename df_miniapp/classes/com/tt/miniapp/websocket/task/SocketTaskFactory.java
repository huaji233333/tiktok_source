package com.tt.miniapp.websocket.task;

import android.app.Application;
import android.content.Context;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.websocket.common.IWebSocketTask;
import com.tt.miniapp.websocket.common.WSRequest;
import com.tt.miniapp.websocket.task.tradition.TraditionWebSocketTask;
import com.tt.miniapp.websocket.task.ttnet.TTNetWebSocketTask;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import org.json.JSONObject;

public class SocketTaskFactory {
  private static Boolean sTTNetEnable;
  
  public static IWebSocketTask createWsTask(Context paramContext, WSRequest paramWSRequest) {
    TTNetWebSocketTask tTNetWebSocketTask1;
    Application application;
    TraditionWebSocketTask traditionWebSocketTask;
    Context context2 = null;
    if (paramWSRequest == null)
      return null; 
    Context context1 = paramContext;
    if (paramContext == null)
      application = AppbrandContext.getInst().getApplicationContext(); 
    if (application == null)
      return null; 
    paramContext = context2;
    if ("ttnet".equalsIgnoreCase(paramWSRequest.socketType)) {
      if (sTTNetEnable == null)
        initSettings((Context)application); 
      paramContext = context2;
      if (Boolean.TRUE.equals(sTTNetEnable))
        tTNetWebSocketTask1 = TTNetWebSocketTask.tryNewInst((Context)application, paramWSRequest); 
    } 
    TTNetWebSocketTask tTNetWebSocketTask2 = tTNetWebSocketTask1;
    if (tTNetWebSocketTask1 == null)
      traditionWebSocketTask = new TraditionWebSocketTask((Context)application, paramWSRequest); 
    return (IWebSocketTask)traditionWebSocketTask;
  }
  
  private static void initSettings(Context paramContext) {
    JSONObject jSONObject = SettingsDAO.getJSONObject(paramContext, new Enum[] { (Enum)Settings.BDP_SOCKET_CTRL });
    if (jSONObject != null && jSONObject.has("ttnet_enable")) {
      boolean bool;
      if (jSONObject.optInt("ttnet_enable", 0) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      sTTNetEnable = Boolean.valueOf(bool);
    } 
    AppBrandLogger.d("_Socket_Factory", new Object[] { "initSettings:", jSONObject });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\task\SocketTaskFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */