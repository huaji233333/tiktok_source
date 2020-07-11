package com.tt.miniapp.view.webcore;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.a.c;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.launchschedule.LaunchScheduler;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.InputStreamWrapper;
import com.tt.miniapp.streamloader.PathUtils;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.MimeTypeUtil;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.ProcessUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class AppbrandWebviewClient extends WebViewClient {
  private InputStream getInputStreamFromLoader(final String urlString) {
    final boolean needReportTimeLine;
    if (urlString.endsWith(".json") || urlString.endsWith(".js")) {
      bool = true;
    } else {
      bool = false;
    } 
    final MpTimeLineReporter mpTimeLineReporter = (MpTimeLineReporter)AppbrandApplicationImpl.getInst().getService(MpTimeLineReporter.class);
    final LoadPathInterceptor loadPathInterceptor = (LoadPathInterceptor)AppbrandApplicationImpl.getInst().getService(LoadPathInterceptor.class);
    final String[] realUrlString = new String[1];
    final InputStreamWrapper streamWrapper = new InputStreamWrapper() {
        public void close() throws IOException {
          super.close();
          ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEventWithValue("stopReadFrame", urlString);
          if (needReportTimeLine) {
            TimeLogger.getInstance().logTimeDuration(new String[] { "AppbrandWebviewClient_stream_close", this.val$realUrlString[0] });
            JSONObject jSONObject = (new MpTimeLineReporter.ExtraBuilder()).kv("file_path", realUrlString[0]).build();
            mpTimeLineReporter.addPoint("get_file_content_from_ttpkg_end", jSONObject);
          } 
        }
      };
    if (bool)
      TimeLogger.getInstance().logTimeDuration(new String[] { "AppbrandWebviewClient_beforeGetStream", urlString }); 
    InputStream inputStream = StreamLoader.getStream(urlString);
    if (inputStream != null) {
      arrayOfString[0] = urlString;
      if (bool) {
        TimeLogger.getInstance().logTimeDuration(new String[] { "AppbrandWebviewClient_stream_open", arrayOfString[0] });
        mpTimeLineReporter.addPoint("get_file_content_from_ttpkg_begin", (new MpTimeLineReporter.ExtraBuilder()).kv("file_path", arrayOfString[0]).build());
      } 
      inputStreamWrapper.setInputStream(inputStream);
    } else if (loadPathInterceptor.shouldIntercept(urlString)) {
      TimeLogger.getInstance().logTimeDuration(new String[] { "AppbrandWebviewClient_doInterceptLoadPath", String.valueOf(urlString) });
      ThreadUtil.runOnWorkThread(new Action() {
            public void act() {
              realUrlString[0] = loadPathInterceptor.interceptPath(urlString);
              if (needReportTimeLine) {
                TimeLogger.getInstance().logTimeDuration(new String[] { "AppbrandWebviewClient_stream_open2", this.val$realUrlString[0] }, );
                JSONObject jSONObject = (new MpTimeLineReporter.ExtraBuilder()).kv("file_path", realUrlString[0]).build();
                mpTimeLineReporter.addPoint("get_file_content_from_ttpkg_begin", jSONObject);
              } 
              InputStream inputStream2 = StreamLoader.getStream(realUrlString[0]);
              InputStream inputStream1 = inputStream2;
              if (inputStream2 == null) {
                TimeLogger.getInstance().logError(new String[] { "AppbrandWebviewClient_interceptLoader_originStream_is_null2", String.valueOf(this.val$urlString), String.valueOf(this.val$realUrlString[0]) });
                inputStream1 = new ByteArrayInputStream(new byte[0]);
              } 
              streamWrapper.setInputStream(inputStream1);
            }
          }(Scheduler)LaunchThreadPool.getInst());
    } else {
      TimeLogger.getInstance().logTimeDuration(new String[] { "AppbrandWebviewClient_interceptLoader_originStream_is_null", String.valueOf(urlString) });
      inputStreamWrapper.setInputStream(new ByteArrayInputStream(new byte[0]));
    } 
    ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEventWithValue("startReadFrame", urlString);
    return (InputStream)inputStreamWrapper;
  }
  
  public void onPageFinished(WebView paramWebView, String paramString) {
    AppBrandLogger.d("AppbrandWebviewClient", new Object[] { "onPageFinished url ", paramString });
    super.onPageFinished(paramWebView, paramString);
  }
  
  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap) {
    AppBrandLogger.d("AppbrandWebviewClient", new Object[] { "onPageStarted url ", paramString });
    super.onPageStarted(paramWebView, paramString, paramBitmap);
  }
  
  public void onReceivedError(WebView paramWebView, WebResourceRequest paramWebResourceRequest, WebResourceError paramWebResourceError) {
    AppBrandLogger.d("AppbrandWebviewClient", new Object[] { "onReceivedError WebResourceRequest  ", paramWebResourceRequest.getUrl().toString(), " ", Boolean.valueOf(paramWebResourceRequest.isForMainFrame()) });
    super.onReceivedError(paramWebView, paramWebResourceRequest, paramWebResourceError);
    if (paramWebResourceRequest.isForMainFrame()) {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("url", paramWebResourceRequest.getUrl().toString());
      } catch (JSONException jSONException) {
        AppBrandLogger.stacktrace(6, "AppbrandWebviewClient", jSONException.getStackTrace());
      } 
      if (Build.VERSION.SDK_INT >= 23) {
        try {
          jSONObject.put("code", paramWebResourceError.getErrorCode());
          jSONObject.put("errMsg", paramWebResourceError.getDescription());
        } catch (JSONException jSONException) {
          AppBrandLogger.stacktrace(6, "AppbrandWebviewClient", jSONException.getStackTrace());
        } 
        AppBrandLogger.d("AppbrandWebviewClient", new Object[] { "onReceivedError WebResourceRequest  ", paramWebResourceRequest.getUrl().toString(), " ", paramWebResourceError.getDescription(), " ", Integer.valueOf(paramWebResourceError.getErrorCode()) });
      } 
      AppBrandMonitor.statusRate("mp_start_error", 3000, jSONObject);
    } 
  }
  
  public void onReceivedHttpError(WebView paramWebView, WebResourceRequest paramWebResourceRequest, WebResourceResponse paramWebResourceResponse) {
    AppBrandLogger.d("AppbrandWebviewClient", new Object[] { "onReceivedHttpError WebResourceRequest  ", paramWebResourceRequest.getUrl().toString(), " ", Integer.valueOf(paramWebResourceResponse.getStatusCode()), " ", Boolean.valueOf(paramWebResourceRequest.isForMainFrame()) });
    super.onReceivedHttpError(paramWebView, paramWebResourceRequest, paramWebResourceResponse);
    if (paramWebResourceRequest.isForMainFrame()) {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("url", paramWebResourceRequest.getUrl().toString());
        jSONObject.put("code", paramWebResourceResponse.getStatusCode());
        jSONObject.put("errMsg", paramWebResourceResponse.getEncoding());
      } catch (JSONException jSONException) {
        AppBrandLogger.stacktrace(6, "AppbrandWebviewClient", jSONException.getStackTrace());
      } 
      AppBrandMonitor.statusRate("mp_start_error", 3000, jSONObject);
    } 
  }
  
  public boolean onRenderProcessGone(WebView paramWebView, RenderProcessGoneDetail paramRenderProcessGoneDetail) {
    if (ProcessUtil.isMiniappProcess()) {
      Boolean bool = null;
      if (Build.VERSION.SDK_INT >= 26)
        bool = Boolean.valueOf(paramRenderProcessGoneDetail.didCrash()); 
      ((LaunchScheduler)AppbrandApplicationImpl.getInst().getService(LaunchScheduler.class)).onRenderProcessGone(bool);
      return c.a(paramWebView, paramRenderProcessGoneDetail);
    } 
    super.onRenderProcessGone(paramWebView, paramRenderProcessGoneDetail);
    return c.a(paramWebView, paramRenderProcessGoneDetail);
  }
  
  public WebResourceResponse shouldInterceptRequest(WebView paramWebView, WebResourceRequest paramWebResourceRequest) {
    byte[] arrayOfByte1;
    byte[] arrayOfByte2;
    String str1;
    AppBrandLogger.i("AppbrandWebviewClient", new Object[] { "shouldInterceptRequest url ", paramWebResourceRequest.getUrl().toString() });
    String str2 = paramWebResourceRequest.getUrl().toString();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(AppbrandConstant.OpenApi.getInst().getPageFrameFakeURLHost());
    stringBuilder.append("/page-frame.html");
    if (str2.equals(stringBuilder.toString())) {
      arrayOfByte2 = IOUtils.readBytes((new File(AppbrandConstant.getJsBundleDir((Context)AppbrandContext.getInst().getApplicationContext()), "page-frame.html")).getAbsolutePath());
      arrayOfByte1 = arrayOfByte2;
      if (arrayOfByte2 == null) {
        arrayOfByte1 = new byte[0];
        TimeLogger.getInstance().logError(new String[] { "AppbrandWebviewClient_templatefile_null" });
        JSONObject jSONObject = new JSONObject();
        try {
          jSONObject.put("errMsg", "templatefile_not_found_at_intercept");
        } catch (JSONException jSONException) {}
        AppBrandMonitor.statusRate("mp_start_error", 6002, jSONObject);
      } 
      return new WebResourceResponse("text/html", "UTF-8", new ByteArrayInputStream(arrayOfByte1));
    } 
    if (jSONException.startsWith("ttjssdk://")) {
      str1 = jSONException.substring(10);
      TimeLogger timeLogger = TimeLogger.getInstance();
      StringBuilder stringBuilder1 = new StringBuilder("AppbrandWebviewClient_jssdkStartLoad1");
      stringBuilder1.append(str1);
      timeLogger.logTimeDuration(new String[] { stringBuilder1.toString() });
      arrayOfByte1 = new byte[0];
      File file = new File(AppbrandConstant.getJsBundleDir((Context)AppbrandContext.getInst().getApplicationContext()), str1);
      if (file.exists()) {
        arrayOfByte1 = IOUtils.readBytes(file.getAbsolutePath());
      } else {
        TimeLogger.getInstance().logError(new String[] { "AppbrandWebviewClient_ttjssdk_filenotfound1", str1, file.getAbsolutePath() });
        stringBuilder1 = new StringBuilder("Intercept file not exist1 : ");
        stringBuilder1.append(file.getAbsolutePath());
        AppBrandLogger.e("AppbrandWebviewClient", new Object[] { stringBuilder1.toString() });
      } 
      arrayOfByte2 = arrayOfByte1;
      if (arrayOfByte1 == null) {
        arrayOfByte2 = new byte[0];
        TimeLogger.getInstance().logError(new String[] { "AppbrandWebviewClient_ttjssdk_file_empty", str1, file.getAbsolutePath() });
      } 
      return new WebResourceResponse(MimeTypeUtil.getMimeType(str1), "UTF-8", new ByteArrayInputStream(arrayOfByte2));
    } 
    if (str1.startsWith(AppbrandConstant.OpenApi.getInst().getPageFrameFakeURLHost()) && str1.endsWith("?from=ttjssdk")) {
      str1 = str1.substring(AppbrandConstant.OpenApi.getInst().getPageFrameFakeURLHost().length(), str1.length() - 13);
      TimeLogger timeLogger2 = TimeLogger.getInstance();
      StringBuilder stringBuilder1 = new StringBuilder("AppbrandWebviewClient_jssdkStartLoad2");
      stringBuilder1.append(str1);
      timeLogger2.logTimeDuration(new String[] { stringBuilder1.toString() });
      byte[] arrayOfByte = new byte[0];
      File file = new File(AppbrandConstant.getJsBundleDir((Context)AppbrandContext.getInst().getApplicationContext()), str1);
      if (file.exists()) {
        arrayOfByte = IOUtils.readBytes(file.getAbsolutePath());
      } else {
        TimeLogger.getInstance().logError(new String[] { "AppbrandWebviewClient_ttjssdk_filenotfound2", str1, file.getAbsolutePath() });
        stringBuilder = new StringBuilder("Intercept file not exist2 : ");
        stringBuilder.append(file.getAbsolutePath());
        AppBrandLogger.e("AppbrandWebviewClient", new Object[] { stringBuilder.toString() });
      } 
      arrayOfByte2 = arrayOfByte;
      if (arrayOfByte == null)
        arrayOfByte2 = new byte[0]; 
      TimeLogger timeLogger1 = TimeLogger.getInstance();
      stringBuilder = new StringBuilder("length:");
      stringBuilder.append(arrayOfByte2.length);
      timeLogger1.logTimeDuration(new String[] { "AppbrandWebviewClient_jssdkStartLoad2", stringBuilder.toString(), str1 });
      return new WebResourceResponse(MimeTypeUtil.getMimeType(str1), "UTF-8", new ByteArrayInputStream(arrayOfByte2));
    } 
    if (str1.startsWith(AppbrandConstant.OpenApi.getInst().getPageFrameFakeURLHost()))
      try {
        String str = PathUtils.relativize(str1, AppbrandConstant.OpenApi.getInst().getPageFrameFakeURLHost());
        return new WebResourceResponse(MimeTypeUtil.getMimeType(str), "UTF-8", getInputStreamFromLoader(str));
      } catch (Exception exception) {
        AppBrandLogger.e("AppbrandWebviewClient", new Object[] { exception });
        TimeLogger.getInstance().logTimeDuration(new String[] { "AppbrandWebviewClient_illegalUrl", str1 });
        return new WebResourceResponse("text/plain", "UTF-8", new ByteArrayInputStream(new byte[0]));
      }  
    if (str1.startsWith("ttfile")) {
      str1 = FileManager.inst().getRealFilePath(str1);
      File file = new File(str1);
      if (file.exists() && file.isFile() && FileManager.inst().canRead(file)) {
        arrayOfByte2 = IOUtils.readBytes(file.getAbsolutePath());
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("Access-Control-Allow-Origin", AppbrandConstant.OpenApi.getInst().getPageFrameFakeURLHost());
        arrayOfByte1 = arrayOfByte2;
        if (arrayOfByte2 == null)
          arrayOfByte1 = new byte[0]; 
        return new WebResourceResponse(MimeTypeUtil.getMimeType(str1), "UTF-8", 200, "ok", hashMap, new ByteArrayInputStream(arrayOfByte1));
      } 
      return new WebResourceResponse("text/plain", "UTF-8", new ByteArrayInputStream(new byte[0]));
    } 
    TimeLogger.getInstance().logTimeDuration(new String[] { "AppbrandWebviewClient_intercept_filenotfound", str1 });
    return _lancet.com_ss_android_ugc_aweme_lancet_WebLancet_shouldInterceptRequest((WebViewClient)this, (WebView)arrayOfByte1, (WebResourceRequest)arrayOfByte2);
  }
  
  public boolean shouldOverrideUrlLoading(WebView paramWebView, WebResourceRequest paramWebResourceRequest) {
    AppBrandLogger.d("AppbrandWebviewClient", new Object[] { "shouldOverrideUrlLoading WebResourceRequest  ", paramWebResourceRequest.getUrl().toString() });
    return shouldOverrideUrlLoading(paramWebView, paramWebResourceRequest.getUrl().toString());
  }
  
  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString) {
    AppBrandLogger.d("AppbrandWebviewClient", new Object[] { "shouldOverrideUrlLoading url ", paramString });
    return super.shouldOverrideUrlLoading(paramWebView, paramString);
  }
  
  class AppbrandWebviewClient {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\AppbrandWebviewClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */