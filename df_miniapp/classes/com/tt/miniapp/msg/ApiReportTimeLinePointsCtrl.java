package com.tt.miniapp.msg;

import android.os.SystemClock;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiReportTimeLinePointsCtrl extends b {
  public ApiReportTimeLinePointsCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      boolean bool;
      MpTimeLineReporter mpTimeLineReporter = (MpTimeLineReporter)AppbrandApplicationImpl.getInst().getService(MpTimeLineReporter.class);
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str = jSONObject.optString("points");
      if (jSONObject.optInt("is_collect", 0) == 1) {
        bool = true;
      } else {
        bool = false;
      } 
      if (TextUtils.isEmpty(str) && !bool) {
        callbackFail(a.b("points"));
        return;
      } 
      if (!TextUtils.isEmpty(str))
        mpTimeLineReporter.sendPointsDirectly(str); 
      if (bool)
        mpTimeLineReporter.sendJsEndCollectPoints(); 
      mpTimeLineReporter.addPoint("verify_time", System.currentTimeMillis(), SystemClock.elapsedRealtime(), null);
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("ApiReportTimeLinePointsCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "reportTimelinePoints";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiReportTimeLinePointsCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */