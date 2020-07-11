package com.tt.miniapp.monitor;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapp.view.webcore.TTWebViewSupportWebView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.render.export.PerformanceTimingListener;
import org.json.JSONObject;

public class TTWebviewPerformanceTiming implements PerformanceTimingListener {
  private TTWebViewSupportWebView webView;
  
  public TTWebviewPerformanceTiming(TTWebViewSupportWebView paramTTWebViewSupportWebView) {
    this.webView = paramTTWebViewSupportWebView;
  }
  
  private void timelineReport(String paramString) {
    long l = this.webView.getLoadingStatusCode();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(":");
    stringBuilder.append(l);
    AppBrandLogger.d("TTWebviewPerformanceTiming", new Object[] { stringBuilder.toString() });
    JSONObject jSONObject = (new MpTimeLineReporter.ExtraBuilder()).kv("status_code", Long.valueOf(l)).build();
    ((MpTimeLineReporter)AppbrandApplicationImpl.getInst().getService(MpTimeLineReporter.class)).addPoint(paramString, jSONObject);
  }
  
  public void onCustomTagNotify(String paramString) {}
  
  public void onDOMContentLoaded() {
    timelineReport("ttwebview_dom_content_loaded");
  }
  
  public void onFirstContentfulPaint() {
    timelineReport("ttwebview_first_contentful_paint");
  }
  
  public void onFirstMeaningfulPaint() {
    timelineReport("ttwebview_first_meaningful_paint");
  }
  
  public void onFirstScreenPaint() {}
  
  public void onReceivedResponse(String paramString) {}
  
  public void onReceivedSpecialEvent(String paramString) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\TTWebviewPerformanceTiming.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */