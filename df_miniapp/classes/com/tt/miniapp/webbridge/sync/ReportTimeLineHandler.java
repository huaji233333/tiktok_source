package com.tt.miniapp.webbridge.sync;

import com.tt.frontendapiinterface.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.launchschedule.LaunchScheduler;
import com.tt.miniapp.util.TimeLineReporter;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public class ReportTimeLineHandler extends WebEventHandler {
  public ReportTimeLineHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str = jSONObject.optString("phase");
      AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
      boolean bool = "DOMReady".equalsIgnoreCase(str);
      if (bool) {
        ((TimeLineReporter)AppbrandApplicationImpl.getInst().getService(TimeLineReporter.class)).recordLaunchStopTime();
        ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEvent("stopLaunchTime");
        ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).endAutoTest();
        TimeLogger.getInstance().logTimeDuration(new String[] { "ReportTimeLineHandler_DOMReady" });
        if (this.mRender != null)
          this.mRender.getNativeNestWebView().onDOMReady(); 
        ((LaunchScheduler)appbrandApplicationImpl.getService(LaunchScheduler.class)).onDOMReady();
        return makeOkMsg();
      } 
      if ("snapshot_DOMReady".equalsIgnoreCase(str)) {
        ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEvent("stopLaunchTime");
        ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).endAutoTest();
        TimeLogger.getInstance().logTimeDuration(new String[] { "ReportTimeLineHandler_snapshot_DOMReady" });
        ((LaunchScheduler)appbrandApplicationImpl.getService(LaunchScheduler.class)).onSnapShotDOMReady();
        return makeOkMsg();
      } 
      if ("fcp".equalsIgnoreCase(str)) {
        long l = jSONObject.optLong("timestamp");
        TimeLogger.getInstance().logTimeDuration(new String[] { "fcp" });
        if (this.mRender != null)
          this.mRender.getNativeNestWebView().onFirstContentfulPaint(); 
        ((LaunchScheduler)AppbrandApplicationImpl.getInst().getService(LaunchScheduler.class)).onFirstContentfulPaint(Long.valueOf(l).longValue() - System.currentTimeMillis());
        return makeOkMsg();
      } 
      return makeFailMsg(a.b("phase"));
    } catch (Exception exception) {
      AppBrandLogger.e("WebEventHandler", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "reportTimeline";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ReportTimeLineHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */