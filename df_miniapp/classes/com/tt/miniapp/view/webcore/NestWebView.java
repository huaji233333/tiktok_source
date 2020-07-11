package com.tt.miniapp.view.webcore;

import android.content.Context;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.webkit.ValueCallback;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.VideoFullScreenHelper;
import com.tt.miniapp.web.TTWebSetting;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class NestWebView extends TTWebViewSupportWebView {
  private boolean disableScroll = false;
  
  public boolean hasDispatchDownEvent;
  
  private volatile boolean mDestroyed;
  
  public long mLastOpTime = SystemClock.elapsedRealtime();
  
  public VideoFullScreenHelper mVideoHelper;
  
  public NestWebView(Context paramContext) {
    super(paramContext.getApplicationContext());
    (new TTWebSetting(getSettings())).setDefaultSetting();
  }
  
  public void destroy() {
    this.mDestroyed = true;
    long l = SystemClock.elapsedRealtime();
    if (l - this.mLastOpTime > 5000L) {
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              NestWebView.this.destroy();
            }
          });
      return;
    } 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            NestWebView.this.destroy();
          }
        },  5000L - l - this.mLastOpTime);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    if (i != 0) {
      if (i == 1 || i == 3)
        this.hasDispatchDownEvent = false; 
    } else {
      this.hasDispatchDownEvent = true;
    } 
    return super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public void evaluateJavascript(String paramString, ValueCallback<String> paramValueCallback) {
    evaluateJavascript(paramString, paramValueCallback, (String)null);
  }
  
  public void evaluateJavascript(final String script, final ValueCallback<String> resultCallback, final String tag) {
    if (this.mDestroyed) {
      TimeLogger.getInstance().logTimeDuration(new String[] { "NestWebView_evluatejs_while_destroyed" });
      return;
    } 
    ValueCallback<String> valueCallback = resultCallback;
    if (tag != null) {
      StringBuilder stringBuilder = new StringBuilder("RealEvaluate: ");
      stringBuilder.append(tag);
      AppBrandLogger.i("NestWebView", new Object[] { stringBuilder.toString() });
      valueCallback = new TimeoutValueCallback<String>(3000L) {
          void onReceiveValue2(String param1String) {
            ValueCallback valueCallback = resultCallback;
            if (valueCallback != null)
              valueCallback.onReceiveValue(param1String); 
          }
          
          void onTimeout() {
            TimeLogger timeLogger = TimeLogger.getInstance();
            String str = tag;
            StringBuilder stringBuilder = new StringBuilder("TTWVStatusCode:");
            stringBuilder.append(NestWebView.this.getLoadingStatusCode());
            timeLogger.logError(new String[] { "NestWebView_evaluateJavascript_timeout", str, "timeout: 3000", stringBuilder.toString() });
            JSONObject jSONObject = new JSONObject();
            try {
              jSONObject.put("errMsg", "evaluateJavascript_timeout: 3000");
              jSONObject.put("tag", tag);
              jSONObject.put("lsCode", NestWebView.this.getLoadingStatusCode());
              jSONObject.put("url", NestWebView.this.getUrl());
              str = script;
              String str1 = str;
              if (str.length() > 200) {
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append(str.substring(0, 200));
                stringBuilder1.append("...");
                str1 = stringBuilder1.toString();
              } 
              jSONObject.put("script", str1);
            } catch (JSONException jSONException) {}
            AppBrandMonitor.statusRate("mp_start_error", 6011, jSONObject);
          }
        };
      ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEventWithValue("evaluateJavascript", tag);
    } 
    this.mLastOpTime = SystemClock.elapsedRealtime();
    super.evaluateJavascript(script, valueCallback);
  }
  
  public VideoFullScreenHelper getVideoFullScreenHelper() {
    return this.mVideoHelper;
  }
  
  public void loadData(final String data, final String mimeType, final String encoding) {
    if (this.mDestroyed)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            try {
              NestWebView.this.mLastOpTime = SystemClock.currentThreadTimeMillis();
              NestWebView.this.loadData(data, mimeType, encoding);
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("NestWebView", new Object[] { "loadData", exception });
              return;
            } 
          }
        });
  }
  
  public void loadDataWithBaseURL(final String baseUrl, final String data, final String mimeType, final String encoding, final String historyUrl) {
    if (this.mDestroyed)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            try {
              NestWebView.this.mLastOpTime = SystemClock.currentThreadTimeMillis();
              NestWebView.this.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("NestWebView", new Object[] { "loadDataWithBaseURL", exception });
              return;
            } 
          }
        });
  }
  
  public void loadUrl(final String url) {
    if (this.mDestroyed)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            try {
              NestWebView.this.mLastOpTime = SystemClock.elapsedRealtime();
              NestWebView.this.loadUrl(url);
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("NestWebView", new Object[] { "load url ", this.val$url, " ", exception });
              return;
            } 
          }
        });
  }
  
  public void loadUrl(final String url, final Map<String, String> additionalHttpHeaders) {
    if (this.mDestroyed)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            try {
              NestWebView.this.mLastOpTime = SystemClock.elapsedRealtime();
              NestWebView.this.loadUrl(url, additionalHttpHeaders);
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("NestWebView", new Object[] { "load url 2", this.val$url, " ", exception });
              return;
            } 
          }
        });
  }
  
  protected void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
    if (this.disableScroll) {
      super.scrollTo(0, 0);
      return;
    } 
    super.onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }
  
  public void scrollTo(int paramInt1, int paramInt2) {
    if (this.disableScroll) {
      super.scrollTo(0, 0);
      return;
    } 
    super.scrollTo(paramInt1, paramInt2);
  }
  
  public void setDisableScroll(boolean paramBoolean) {
    this.disableScroll = paramBoolean;
  }
  
  public void setLayerType(int paramInt, Paint paramPaint) {
    if (this.mDestroyed)
      return; 
    super.setLayerType(paramInt, paramPaint);
  }
  
  public void setVideoFullScreenHelper(VideoFullScreenHelper paramVideoFullScreenHelper) {
    this.mVideoHelper = paramVideoFullScreenHelper;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\NestWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */