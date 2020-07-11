package com.tt.miniapp.component.nativeview.video.controller;

import com.ss.ttvideoengine.TTVideoEngine;
import com.ss.ttvideoengine.utils.Error;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.video.VideoView;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.video.patchad.PatchAdVideoCallback;
import com.tt.miniapp.video.patchad.PatchAdVideoController;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.ad.c;
import org.json.JSONObject;

public class BaseVideoViewController extends PatchAdVideoController {
  VideoLayoutEntity mBeforeFullScreenLayoutInfo;
  
  WebViewManager.IRender mRender;
  
  VideoView.VideoModel mVideoModel;
  
  VideoView mVideoView;
  
  int mZIndex;
  
  BaseVideoViewController(VideoView paramVideoView, WebViewManager.IRender paramIRender) {
    super((PatchAdVideoCallback)paramVideoView, (paramVideoView.getVideoModel()).videoPlayerId);
    this.mVideoView = paramVideoView;
    this.mVideoModel = paramVideoView.getVideoModel();
    this.mRender = paramIRender;
  }
  
  private void callbackVideoAdEvent(String paramString, boolean paramBoolean) {
    String str;
    if (paramBoolean) {
      str = c.APP_VIDEO_PATCH_AD_PRE.getStrType();
    } else {
      str = c.APP_VIDEO_PATCH_AD_POST.getStrType();
    } 
    AppBrandLogger.i("BaseVideoViewController", new Object[] { "send patchAd Event", paramString, Boolean.valueOf(paramBoolean) });
    callbackVideoEvent(paramString, (new JsonBuilder()).put("adType", str).build());
  }
  
  private void callbackVideoEvent(String paramString) {
    callbackVideoEvent(paramString, (JSONObject)null);
  }
  
  private void callbackVideoEvent(String paramString, JSONObject paramJSONObject) {
    j j = AppbrandApplication.getInst().getJsBridge();
    if (j == null)
      return; 
    j.sendMsgToJsCore(paramString, (new JsonBuilder(paramJSONObject)).put("videoPlayerId", Integer.valueOf(this.mVideoModel.videoPlayerId)).put("data", this.mVideoModel.data.toString()).build().toString(), this.mRender.getWebViewId());
  }
  
  public static BaseVideoViewController getImpl(VideoView paramVideoView, WebViewManager.IRender paramIRender) {
    return (BaseVideoViewController)(paramVideoView.getViewParent().isRenderInBrowserEnabled() ? new RenderInBrowserController(paramVideoView, paramIRender) : new NormalController(paramVideoView, paramIRender, null));
  }
  
  private void publishVideoEvent(String paramString, JsonBuilder paramJsonBuilder) {
    String str = paramJsonBuilder.put("videoPlayerId", Integer.valueOf(this.mVideoModel.videoPlayerId)).put("data", this.mVideoModel.data.toString()).build().toString();
    AppBrandLogger.i("BaseVideoViewController", new Object[] { "publish patchAd Event", paramString, str });
    AppbrandApplicationImpl.getInst().getWebViewManager().publish(this.mRender.getWebViewId(), paramString, str);
  }
  
  public void destroyPatchAd() {
    super.destroyPatchAd();
    if (this.calledVideoAdStarted != null)
      onVideoAdClose(Boolean.TRUE.equals(this.calledVideoAdStarted)); 
  }
  
  public void onBufferStart() {
    super.onBufferStart();
    AppBrandLogger.d("BaseVideoViewController", new Object[] { "onBufferStart" });
    callbackVideoEvent("onVideoWaiting");
  }
  
  public void onCompletion(TTVideoEngine paramTTVideoEngine) {
    super.onCompletion(paramTTVideoEngine);
    StringBuilder stringBuilder = new StringBuilder("ended:给js发消息--onVideoEnded--: videoPlayerId = ");
    stringBuilder.append(this.mVideoModel.videoPlayerId);
    AppBrandLogger.d("BaseVideoViewController", new Object[] { stringBuilder.toString() });
    callbackVideoEvent("onVideoEnded");
  }
  
  public void onError(Error paramError) {
    String str;
    super.onError(paramError);
    try {
      str = (new JSONObject(paramError.toMap())).toString();
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("BaseVideoViewController", "video error 2 json failed", exception);
      str = "";
    } 
    Event.builder("mp_video_error").kv("error_msg", str).flush();
    AppBrandLogger.e("BaseVideoViewController", new Object[] { "ended:给js发消息--onVideoError--: videoPlayerId =", Integer.valueOf(this.mVideoModel.videoPlayerId), "errMsg =", str });
    callbackVideoEvent("onVideoError", (new JsonBuilder()).put("errMsg", str).build());
  }
  
  public void onFullScreen(boolean paramBoolean, int paramInt) {
    String str;
    super.onFullScreen(paramBoolean, paramInt);
    JsonBuilder jsonBuilder = (new JsonBuilder()).put("fullScreen", Boolean.valueOf(paramBoolean));
    if (paramInt == 0 || paramInt == 8) {
      str = "horizontal";
    } else {
      str = "vertical";
    } 
    callbackVideoEvent("onVideoFullScreenChange", jsonBuilder.put("direction", str).build());
  }
  
  public void onPlaybackStateChanged(TTVideoEngine paramTTVideoEngine, int paramInt) {
    super.onPlaybackStateChanged(paramTTVideoEngine, paramInt);
    if (paramInt != 1) {
      if (paramInt != 2)
        return; 
      StringBuilder stringBuilder1 = new StringBuilder("pause:给js发消息--onVideoPause--: videoPlayerId = ");
      stringBuilder1.append(this.mVideoModel.videoPlayerId);
      AppBrandLogger.d("BaseVideoViewController", new Object[] { stringBuilder1.toString() });
      callbackVideoEvent("onVideoPause");
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("play:给js发消息--onVideoPlay--: videoPlayerId = ");
    stringBuilder.append(this.mVideoModel.videoPlayerId);
    AppBrandLogger.d("BaseVideoViewController", new Object[] { stringBuilder.toString() });
    callbackVideoEvent("onVideoPlay");
  }
  
  public void onPrepare(TTVideoEngine paramTTVideoEngine) {
    super.onPrepare(paramTTVideoEngine);
  }
  
  public void onProgressAndTimeUpdate(int paramInt1, int paramInt2) {
    super.onProgressAndTimeUpdate(paramInt1, paramInt2);
    callbackVideoEvent("onVideoTimeUpdate", (new JsonBuilder()).put("currentTime", Integer.valueOf(paramInt1)).put("duration", Integer.valueOf(paramInt2)).build());
  }
  
  public void onStuffOverVideoVisibilityChange(boolean paramBoolean1, boolean paramBoolean2) {
    super.onStuffOverVideoVisibilityChange(paramBoolean1, paramBoolean2);
    if (!paramBoolean2 || this.called2Hidden) {
      String str;
      this.called2Hidden = paramBoolean2 ^ true;
      if (paramBoolean1) {
        str = c.APP_VIDEO_PATCH_AD_PRE.getStrType();
      } else {
        str = c.APP_VIDEO_PATCH_AD_POST.getStrType();
      } 
      publishVideoEvent("onStuffOverVideoVisibilityShouldChange", (new JsonBuilder()).put("adType", str).put("hidden", Boolean.valueOf(this.called2Hidden)));
    } 
  }
  
  public void onVideoAdClose(boolean paramBoolean) {
    super.onVideoAdClose(paramBoolean);
    callbackVideoAdEvent("onVideoAdClose", paramBoolean);
    this.calledVideoAdStarted = null;
  }
  
  public void onVideoAdEnded(boolean paramBoolean) {
    super.onVideoAdEnded(paramBoolean);
    callbackVideoAdEvent("onVideoAdEnded", paramBoolean);
    this.calledVideoAdStarted = null;
  }
  
  public void onVideoAdError(boolean paramBoolean, int paramInt, String paramString) {
    String str;
    super.onVideoAdError(paramBoolean, paramInt, paramString);
    JsonBuilder jsonBuilder = new JsonBuilder();
    if (paramBoolean) {
      str = c.APP_VIDEO_PATCH_AD_PRE.getStrType();
    } else {
      str = c.APP_VIDEO_PATCH_AD_POST.getStrType();
    } 
    callbackVideoEvent("onVideoAdError", jsonBuilder.put("adType", str).put("errCode", Integer.valueOf(paramInt)).put("errMsg", paramString).build());
  }
  
  public void onVideoAdFullscreenChange(boolean paramBoolean1, boolean paramBoolean2) {
    String str1;
    String str2;
    super.onVideoAdFullscreenChange(paramBoolean1, paramBoolean2);
    if (paramBoolean2) {
      str1 = "onVideoRequestFullScreen";
    } else {
      str1 = "onVideoExitFullScreen";
    } 
    if (paramBoolean1) {
      str2 = c.APP_VIDEO_PATCH_AD_PRE.getStrType();
    } else {
      str2 = c.APP_VIDEO_PATCH_AD_POST.getStrType();
    } 
    publishVideoEvent(str1, (new JsonBuilder()).put("adType", str2).put("fullscreen", Boolean.valueOf(paramBoolean2)));
  }
  
  public void onVideoAdLoaded(boolean paramBoolean) {
    super.onVideoAdLoaded(paramBoolean);
    callbackVideoAdEvent("onVideoAdLoad", paramBoolean);
  }
  
  public void onVideoAdStart(boolean paramBoolean) {
    super.onVideoAdStart(paramBoolean);
    if (this.calledVideoAdStarted != null)
      return; 
    this.calledVideoAdStarted = Boolean.valueOf(paramBoolean);
    callbackVideoAdEvent("onVideoAdStart", paramBoolean);
  }
  
  public void setZIndex(int paramInt) {
    this.mZIndex = paramInt;
  }
  
  static class VideoLayoutEntity {
    AbsoluteLayout.LayoutParams mBeforeFullScreenLayoutParams;
    
    AbsoluteLayout.ViewOffset mBeforeFullScreenOffset;
    
    int mBeforeFullScreenScrollX;
    
    int mBeforeFullScreenScrollY;
    
    public VideoLayoutEntity(VideoView param1VideoView) {
      if (param1VideoView != null)
        this.mBeforeFullScreenLayoutParams = (AbsoluteLayout.LayoutParams)param1VideoView.getLayoutParams(); 
    }
    
    public static VideoLayoutEntity saveBeforeFullScreenInfo(VideoView param1VideoView) {
      if (param1VideoView == null)
        return null; 
      VideoLayoutEntity videoLayoutEntity = new VideoLayoutEntity(param1VideoView);
      if (param1VideoView.getViewParent() != null) {
        AbsoluteLayout absoluteLayout = param1VideoView.getViewParent();
        videoLayoutEntity.mBeforeFullScreenOffset = absoluteLayout.getViewOffset(param1VideoView.getId());
        videoLayoutEntity.mBeforeFullScreenScrollX = absoluteLayout.getCurScrollX();
        videoLayoutEntity.mBeforeFullScreenScrollY = absoluteLayout.getCurScrollY();
      } 
      return videoLayoutEntity;
    }
    
    public static void updateVideoLayoutInfo(VideoLayoutEntity param1VideoLayoutEntity, VideoView param1VideoView) {
      if (param1VideoLayoutEntity != null) {
        if (param1VideoView == null)
          return; 
        if (param1VideoView.getViewParent() != null) {
          if (param1VideoView.getViewParent().isRenderInBrowserEnabled())
            return; 
          int i = param1VideoView.getViewParent().getCurScrollX();
          int j = param1VideoLayoutEntity.mBeforeFullScreenScrollX;
          int k = param1VideoView.getViewParent().getCurScrollY();
          int m = param1VideoLayoutEntity.mBeforeFullScreenScrollY;
          AbsoluteLayout.LayoutParams layoutParams2 = param1VideoLayoutEntity.mBeforeFullScreenLayoutParams;
          layoutParams2.x -= i - j;
          AbsoluteLayout.LayoutParams layoutParams1 = param1VideoLayoutEntity.mBeforeFullScreenLayoutParams;
          layoutParams1.y -= k - m;
        } 
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\video\controller\BaseVideoViewController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */