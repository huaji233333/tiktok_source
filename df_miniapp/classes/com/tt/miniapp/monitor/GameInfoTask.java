package com.tt.miniapp.monitor;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public class GameInfoTask extends BaseMonitorTask {
  private String mJsonStr;
  
  public GameInfoTask() {
    super(1000L);
  }
  
  protected void executeActual() {
    try {
      JSONObject jSONObject = new JSONObject(this.mJsonStr);
      boolean bool = AppbrandApplicationImpl.getInst().getForeBackgroundManager().isBackground();
      if (jSONObject.getInt("fps") > 0 && !bool)
        MonitorInfoPackTask.addGameFps(jSONObject.getInt("fps")); 
      if (jSONObject.getInt("drawcall") > 0 && !bool)
        MonitorInfoPackTask.addDrawCall(jSONObject.getInt("drawcall")); 
      if (jSONObject.getInt("tri") > 0 && !bool)
        MonitorInfoPackTask.addTri(jSONObject.getInt("tri")); 
      if (jSONObject.getInt("vert") > 0 && !bool)
        MonitorInfoPackTask.addVert(jSONObject.getInt("vert")); 
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "GameInfoTask", exception.getStackTrace());
      return;
    } 
  }
  
  public void setJsonStr(String paramString) {
    this.mJsonStr = paramString;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\GameInfoTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */