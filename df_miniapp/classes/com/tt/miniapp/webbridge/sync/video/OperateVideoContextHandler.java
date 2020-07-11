package com.tt.miniapp.webbridge.sync.video;

import android.view.View;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.video.VideoView;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class OperateVideoContextHandler extends WebEventHandler {
  public OperateVideoContextHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      final String finalVideoView;
      final JSONObject jsonObject = new JSONObject(this.mArgs);
      int i = jSONObject.optInt("videoPlayerId");
      final String operationType = jSONObject.optString("type");
      if (this.mRender == null) {
        callbackFail("render is null");
        return CharacterUtils.empty();
      } 
      VideoView videoView = null;
      View view = this.mRender.getNativeViewManager().getView(i);
      if (view instanceof VideoView)
        videoView = (VideoView)view; 
      if (videoView == null) {
        StringBuilder stringBuilder = new StringBuilder("VideoView not found: ");
        stringBuilder.append(i);
        str1 = stringBuilder.toString();
        AppBrandLogger.e("WebEventHandler", new Object[] { str1 });
        callbackFail(str1);
        return CharacterUtils.empty();
      } 
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              if ("play".equals(operationType)) {
                if (!finalVideoView.getVideoController().isPatchAdLoadingOrPlaying()) {
                  finalVideoView.startVideo();
                } else {
                  OperateVideoContextHandler.this.callbackFail("ad is loading or playing");
                  return;
                } 
              } else if ("pause".equals(operationType)) {
                if (!finalVideoView.getVideoController().isPatchAdLoadingOrPlaying()) {
                  finalVideoView.pauseVideo();
                } else {
                  OperateVideoContextHandler.this.callbackFail("ad is loading or playing");
                  return;
                } 
              } else if ("stop".equals(operationType)) {
                if (!finalVideoView.getVideoController().isPatchAdLoadingOrPlaying()) {
                  finalVideoView.stopVideo();
                } else {
                  OperateVideoContextHandler.this.callbackFail("ad is loading or playing");
                  return;
                } 
              } else if ("requestFullScreen".equals(operationType)) {
                JSONObject jSONObject = jsonObject.optJSONObject("data");
                int i = -1;
                if (jSONObject != null)
                  i = jSONObject.optInt("zIndex", -1); 
                finalVideoView.requestFullScreen(i);
              } else if ("exitFullScreen".equals(operationType)) {
                finalVideoView.exitFullScreen();
              } else if ("seek".equals(operationType)) {
                double d = jsonObject.optDouble("data", -1000.0D);
                if (d <= -999.0D) {
                  OperateVideoContextHandler.this.callbackFail(a.b("data"));
                  return;
                } 
                if (!finalVideoView.getVideoController().isPatchAdLoadingOrPlaying()) {
                  finalVideoView.seek((int)(d * 1000.0D));
                } else {
                  OperateVideoContextHandler.this.callbackFail("ad is loading or playing");
                  return;
                } 
              } else {
                OperateVideoContextHandler.this.callbackFail(a.b("type"));
                return;
              } 
              OperateVideoContextHandler.this.callbackOk();
            }
          });
    } catch (Exception exception) {
      AppBrandLogger.e("WebEventHandler", new Object[] { exception });
      callbackFail(exception);
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "operateVideoContext";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\video\OperateVideoContextHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */