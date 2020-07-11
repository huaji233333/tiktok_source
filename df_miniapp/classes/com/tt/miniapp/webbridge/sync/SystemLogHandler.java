package com.tt.miniapp.webbridge.sync;

import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.monitor.MiniAppPerformanceDialog;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class SystemLogHandler extends WebEventHandler {
  public SystemLogHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  private void handlePerformanceData(final JSONObject params) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            String str = params.optString("speed_value_type");
            if (TextUtils.equals(str, "firstRenderTime")) {
              MiniAppPerformanceDialog.saveRenderTime(params.optLong("total_duration"));
              return;
            } 
            if (TextUtils.equals(str, "reRenderTime"))
              MiniAppPerformanceDialog.saveReRenderTime(params.optLong("total_duration")); 
          }
        }Schedulers.shortIO());
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str = jSONObject.optString("tag");
      jSONObject = jSONObject.optJSONObject("data");
      handlePerformanceData(jSONObject);
      Event.builder(str).addKVJsonObject(jSONObject).flush();
    } catch (Exception exception) {
      AppBrandLogger.e("SystemLogHandler", new Object[] { "", exception });
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "systemLog";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\SystemLogHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */