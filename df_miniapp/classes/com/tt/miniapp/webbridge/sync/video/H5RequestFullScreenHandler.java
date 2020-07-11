package com.tt.miniapp.webbridge.sync.video;

import android.text.TextUtils;
import android.webkit.WebView;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.VideoFullScreenHelper;
import com.tt.miniapp.view.webcore.NestWebView;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class H5RequestFullScreenHandler extends WebEventHandler {
  public H5RequestFullScreenHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  private void requestFullScreen(final WebView webView, final String script, final int direction, final String id) {
    if (webView != null) {
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              WebView webView = webView;
              if (webView instanceof NestWebView) {
                NestWebView nestWebView = (NestWebView)webView;
                VideoFullScreenHelper videoFullScreenHelper2 = nestWebView.getVideoFullScreenHelper();
                VideoFullScreenHelper videoFullScreenHelper1 = videoFullScreenHelper2;
                if (videoFullScreenHelper2 == null) {
                  videoFullScreenHelper1 = new VideoFullScreenHelper();
                  nestWebView.setVideoFullScreenHelper(videoFullScreenHelper1);
                } 
                videoFullScreenHelper1.setComponentId(id);
                videoFullScreenHelper1.setDirection(videoFullScreenHelper1.getScreenDirection(direction));
              } 
              webView = webView;
              StringBuilder stringBuilder = new StringBuilder("javascript:");
              stringBuilder.append(script);
              _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(webView, stringBuilder.toString());
              H5RequestFullScreenHandler.this.callbackOk();
            }
            
            class null {}
          });
      return;
    } 
    callbackFail("WebView is null");
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str = jSONObject.optString("code");
      if (!TextUtils.isEmpty(str)) {
        if (this.mRender instanceof com.tt.miniapp.page.AppbrandSinglePage) {
          byte b;
          WebView webView = this.mRender.getWebView();
          String str1 = "";
          if (jSONObject.has("direction")) {
            b = jSONObject.optInt("direction");
            str1 = jSONObject.optString("id");
          } else {
            b = 90;
          } 
          requestFullScreen(webView, str, b, str1);
        } else {
          callbackFail("render type error");
        } 
      } else {
        callbackFail(a.b("code"));
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("WebEventHandler", new Object[] { exception });
      callbackFail(exception);
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "videoRequestFullScreen";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\video\H5RequestFullScreenHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */