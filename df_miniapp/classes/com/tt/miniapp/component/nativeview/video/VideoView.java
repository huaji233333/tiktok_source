package com.tt.miniapp.component.nativeview.video;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.NativeComponent;
import com.tt.miniapp.component.nativeview.video.controller.BaseVideoViewController;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.video.base.ITTVideoController;
import com.tt.miniapp.video.core.PluginVideoController;
import com.tt.miniapp.video.patchad.PatchAdVideoController;
import com.tt.miniapp.video.patchad.PatchAdVideoView;
import com.tt.miniapp.view.ScreenVisibilityDetector;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.NativeDimenUtil;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.ad.j;
import com.tt.option.e.k;
import org.json.JSONObject;

public class VideoView extends PatchAdVideoView implements NativeComponent, ScreenVisibilityDetector.OnScreenVisibilityChangedListener {
  private int mDeviceHeight;
  
  private int mDeviceWidth;
  
  private VideoModel mInitVideoMode;
  
  private boolean mIsVideoPlayingBeforePause;
  
  private AbsoluteLayout mParent;
  
  private j mPatchAdManager;
  
  private WebViewManager.IRender mRender;
  
  public Runnable mSchedulePauseRunnable;
  
  public Runnable mScheduleResumeRunnable;
  
  public VideoView(AbsoluteLayout paramAbsoluteLayout, WebViewManager.IRender paramIRender, VideoModel paramVideoModel) {
    super(paramAbsoluteLayout.getContext());
    this.mInitVideoMode = paramVideoModel;
    this.mParent = paramAbsoluteLayout;
    this.mRender = paramIRender;
    this.mDeviceWidth = UIUtils.getDeviceWidth(getContext());
    this.mDeviceHeight = UIUtils.getDeviceHeight(getContext());
    int i = this.mInitVideoMode.width;
    int k = this.mDeviceWidth;
    if (i > k)
      this.mInitVideoMode.width = k; 
    i = this.mInitVideoMode.height;
    k = this.mDeviceHeight;
    if (i > k)
      this.mInitVideoMode.height = k; 
    this.mPatchAdManager = HostDependManager.getInst().createVideoPatchAdManager(new j.a() {
          public Activity getActivity() {
            return AppbrandApplicationImpl.getInst().getMiniAppContext().getCurrentActivity();
          }
        });
  }
  
  private void playVideo(VideoModel paramVideoModel) {
    String str2 = paramVideoModel.filePath;
    String str1 = str2;
    if (!TextUtils.isEmpty(str2)) {
      str1 = str2;
      if (str2.startsWith("ttfile"))
        str1 = FileManager.inst().getRealFilePath(str2); 
    } 
    play((new ITTVideoController.PlayerEntity()).setVideoUrl(str1).setVideoModelJsonStr(paramVideoModel.videoModelJsonStr).setAutoPlay(paramVideoModel.autoplay).setPoster(paramVideoModel.poster).setDecryptToken(paramVideoModel.encryptToken).setEncodedKey(paramVideoModel.encodedKey).setVideoId(paramVideoModel.videoId).setVidDataSourceUrl(paramVideoModel.vidDataSourceUrl).setPlayApiVersion(paramVideoModel.playApiVersion).setAuthToken(paramVideoModel.authToken).setResolution(paramVideoModel.resolution).setLoop(paramVideoModel.mLoop));
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle())
      HostDependManager.getInst().muteLiveWindowView((Activity)AppbrandContext.getInst().getCurrentActivity(), AppbrandApplicationImpl.getInst().getSchema()); 
  }
  
  private void refreshParentView() {
    AbsoluteLayout absoluteLayout = this.mParent;
    if (absoluteLayout == null)
      return; 
    ViewGroup viewGroup = (ViewGroup)absoluteLayout.getParent();
    if (viewGroup == null)
      return; 
    viewGroup.removeView((View)this.mParent);
    viewGroup.addView((View)this.mParent);
    requestLayout();
  }
  
  private void releaseSelf() {
    AppBrandLogger.d("tma_VideoView", new Object[] { "release" });
    exitFullScreen();
    getVideoController().destroyPatchAd();
    getVideoController().releaseMedia();
    Context context = getContext();
    if (context instanceof Activity) {
      Activity activity = (Activity)context;
      if (activity.getRequestedOrientation() == 0)
        UIUtils.setActivityOrientation(activity, 1); 
    } 
  }
  
  public void addView(String paramString, k paramk) {
    AppBrandLogger.d("tma_VideoView", new Object[] { "addView position ", " width ", Integer.valueOf(this.mInitVideoMode.width), " height ", Integer.valueOf(this.mInitVideoMode.height), " x ", Integer.valueOf(this.mInitVideoMode.left), " y ", Integer.valueOf(this.mInitVideoMode.top) });
    initView();
    this.mParent.addView((View)this);
    refreshParentView();
    updateView(paramString, paramk);
  }
  
  public PluginVideoController createVideoController() {
    return (PluginVideoController)BaseVideoViewController.getImpl(this, this.mRender);
  }
  
  public j getPatchAdManager() {
    return this.mPatchAdManager;
  }
  
  public String getPostRollAdUnitId() {
    return this.mInitVideoMode.mPostRollAdUnitId;
  }
  
  public String getPreRollAdUnitId() {
    return this.mInitVideoMode.mPreRollAdUnitId;
  }
  
  public VideoModel getVideoModel() {
    return this.mInitVideoMode;
  }
  
  public AbsoluteLayout getViewParent() {
    return this.mParent;
  }
  
  public boolean onBackPressed() {
    PatchAdVideoController patchAdVideoController = getVideoController();
    if (patchAdVideoController != null && patchAdVideoController.isFullScreen()) {
      patchAdVideoController.interceptFullScreen(false);
      return true;
    } 
    return false;
  }
  
  public void onDestroy() {
    AppBrandLogger.d("tma_VideoView", new Object[] { "release media" });
    getVideoController().releaseMedia();
  }
  
  public void onScreenVisibilityChanged(View paramView, boolean paramBoolean) {
    AppBrandLogger.d("tma_VideoView", new Object[] { "onScreenVisibilityChanged isShow", Boolean.valueOf(paramBoolean) });
    PatchAdVideoController patchAdVideoController = getVideoController();
    if (patchAdVideoController != null) {
      if (patchAdVideoController.isFullScreen())
        return; 
      if (paramBoolean) {
        patchAdVideoController.onEnterScreen();
        return;
      } 
      patchAdVideoController.onLeaveScreen();
    } 
  }
  
  public void onViewPause() {
    AppBrandLogger.d("tma_VideoView", new Object[] { "onEnterBackground" });
    AppBrandLogger.d("tma_VideoView", new Object[] { "onPause" });
    Runnable runnable = this.mScheduleResumeRunnable;
    if (runnable != null) {
      removeCallbacks(runnable);
      this.mScheduleResumeRunnable = null;
      return;
    } 
    final PatchAdVideoController videoController = getVideoController();
    if (patchAdVideoController != null) {
      this.mIsVideoPlayingBeforePause = patchAdVideoController.isVideoPlaying();
      if (this.mIsVideoPlayingBeforePause || patchAdVideoController.isShouldPlay()) {
        this.mSchedulePauseRunnable = new Runnable() {
            public void run() {
              videoController.pauseVideo();
              VideoView.this.mSchedulePauseRunnable = null;
            }
          };
        postDelayed(this.mSchedulePauseRunnable, 300L);
      } 
      patchAdVideoController.pausePatchAd();
    } 
  }
  
  public void onViewResume() {
    AppBrandLogger.d("tma_VideoView", new Object[] { "onRecoverForeground" });
    AppBrandLogger.d("tma_VideoView", new Object[] { "onResume" });
    final PatchAdVideoController videoController = getVideoController();
    if (patchAdVideoController != null) {
      if (this.mIsVideoPlayingBeforePause) {
        this.mScheduleResumeRunnable = new Runnable() {
            public void run() {
              videoController.startVideo();
              VideoView videoView = VideoView.this;
              videoView.mScheduleResumeRunnable = null;
              if (videoView.mSchedulePauseRunnable != null) {
                videoView = VideoView.this;
                videoView.removeCallbacks(videoView.mSchedulePauseRunnable);
                VideoView.this.mSchedulePauseRunnable = null;
              } 
            }
          };
        postDelayed(this.mScheduleResumeRunnable, 100L);
      } 
      patchAdVideoController.resumePatchAd();
    } 
  }
  
  public void removeView(int paramInt, k paramk) {
    releaseSelf();
  }
  
  public void updateView(String paramString, k paramk) {
    AppBrandLogger.d("tma_VideoView", new Object[] { "updateView ", paramString });
    VideoModel videoModel = VideoModel.parseVideoMode(paramString);
    videoModel.videoPlayerId = this.mInitVideoMode.videoPlayerId;
    int i = videoModel.width;
    int m = this.mDeviceWidth;
    if (i > m)
      videoModel.width = m; 
    i = videoModel.height;
    m = this.mDeviceHeight;
    if (i > m)
      videoModel.height = m; 
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    if (layoutParams instanceof AbsoluteLayout.LayoutParams && !((AbsoluteLayout.LayoutParams)layoutParams).isFullScreen) {
      m = 1;
    } else {
      m = 0;
    } 
    if (m != 0) {
      AbsoluteLayout.LayoutParams layoutParams1 = (AbsoluteLayout.LayoutParams)layoutParams;
      if (videoModel.hasPosition) {
        int n = videoModel.left;
        i = videoModel.top;
        AppBrandLogger.i("tma_VideoView", new Object[] { "origin position：Left=", Integer.valueOf(n), ", Top=", Integer.valueOf(i), ", isFixed=", Boolean.valueOf(videoModel.isFixed), ", curScroll： scrollX=", Integer.valueOf(this.mParent.getCurScrollX()), ", scrollY=", Integer.valueOf(this.mParent.getCurScrollY()) });
        n -= this.mParent.getCurScrollX();
        i -= this.mParent.getCurScrollY();
        AppBrandLogger.i("tma_VideoView", new Object[] { "curScroll：scrollX=", Integer.valueOf(this.mParent.getCurScrollX()), ", scrollY=", Integer.valueOf(this.mParent.getCurScrollY()), "; Video-position: Left=", Integer.valueOf(n), ", Top=", Integer.valueOf(i) });
        layoutParams1.height = videoModel.height;
        layoutParams1.width = videoModel.width;
        layoutParams1.x = n;
        layoutParams1.y = i;
        i = 1;
      } else {
        i = 0;
      } 
      if (videoModel.hasFixed)
        layoutParams1.isFixed = videoModel.isFixed; 
      if (videoModel.hasZIndex) {
        layoutParams1.zIndex = videoModel.zIndex;
        i = 1;
      } 
      if (i != 0)
        requestLayout(); 
    } 
    if (videoModel.hide) {
      if (getVisibility() == 0) {
        setVisibility(8);
        pauseVideo();
      } 
    } else {
      updateShowState((new ITTVideoController.ShowStateEntity()).setControls(videoModel.mControls).setShowFullScreenBtn(videoModel.mShowFullScreenBtn).setShowPlayBtn(videoModel.mShowPlayBtn).setPlayBtnPosition(videoModel.mPlayBtnPosition).setObjectFit(videoModel.mObjectFit));
      setVisibility(0);
      playVideo(videoModel);
    } 
    if (m != 0) {
      Bundle bundle = new Bundle();
      bundle.putInt("video_view_width", videoModel.width);
      bundle.putInt("video_view_height", videoModel.height);
      getVideoController().notifyPatchAdEvent(309, bundle);
    } 
  }
  
  public static class VideoModel {
    public String authToken;
    
    public boolean autoplay;
    
    public JSONObject data;
    
    public String encodedKey;
    
    public String encryptToken;
    
    public String filePath;
    
    public boolean hasFixed;
    
    public boolean hasPosition;
    
    public boolean hasZIndex;
    
    public int height;
    
    public boolean hide;
    
    public boolean isFixed;
    
    public int left;
    
    public boolean live;
    
    public boolean mControls = true;
    
    public boolean mLoop;
    
    public String mObjectFit = "contain";
    
    public String mPlayBtnPosition = "center";
    
    public String mPostRollAdUnitId;
    
    public String mPreRollAdUnitId;
    
    public boolean mShowFullScreenBtn = true;
    
    public boolean mShowPlayBtn = true;
    
    public boolean muted;
    
    public boolean needEvent;
    
    public int playApiVersion;
    
    public String poster;
    
    public String resolution;
    
    public int top;
    
    public String vidDataSourceUrl;
    
    public String videoId;
    
    public String videoModelJsonStr;
    
    public int videoPlayerId;
    
    public int width;
    
    public int zIndex;
    
    public static VideoModel parseVideoMode(String param1String) {
      VideoModel videoModel = new VideoModel();
      try {
        JSONObject jSONObject1 = new JSONObject(param1String);
        videoModel.filePath = jSONObject1.optString("filePath");
        if (!TextUtils.isEmpty(videoModel.filePath) && videoModel.filePath.startsWith("ttfile://"))
          videoModel.filePath = FileManager.inst().getRealSchemaPath(videoModel.filePath); 
        videoModel.hide = jSONObject1.optBoolean("hide");
        videoModel.needEvent = jSONObject1.optBoolean("needEvent");
        videoModel.autoplay = jSONObject1.optBoolean("autoplay");
        videoModel.poster = jSONObject1.optString("poster");
        videoModel.mControls = jSONObject1.optBoolean("controls", videoModel.mControls);
        videoModel.live = jSONObject1.optBoolean("live");
        videoModel.muted = jSONObject1.optBoolean("muted");
        videoModel.mLoop = jSONObject1.optBoolean("loop", videoModel.mLoop);
        videoModel.encryptToken = jSONObject1.optString("decrypt_token");
        videoModel.videoModelJsonStr = jSONObject1.optString("video_model");
        videoModel.encodedKey = jSONObject1.optString("encrypt_token");
        videoModel.videoId = jSONObject1.optString("video_id");
        videoModel.vidDataSourceUrl = jSONObject1.optString("fetcher");
        try {
          videoModel.playApiVersion = jSONObject1.optInt("api_version");
        } catch (Exception exception) {
          videoModel.playApiVersion = 2;
        } 
        videoModel.authToken = jSONObject1.optString("auth_token");
        videoModel.resolution = jSONObject1.optString("resolution");
        videoModel.mShowFullScreenBtn = jSONObject1.optBoolean("showFullscreenBtn", videoModel.mShowFullScreenBtn);
        videoModel.mShowPlayBtn = jSONObject1.optBoolean("showPlayBtn", videoModel.mShowPlayBtn);
        videoModel.mObjectFit = jSONObject1.optString("objectFit", videoModel.mObjectFit);
        videoModel.mPlayBtnPosition = jSONObject1.optString("playBtnPosition", videoModel.mPlayBtnPosition);
        videoModel.data = new JSONObject(jSONObject1.optString("data"));
        JSONObject jSONObject2 = jSONObject1.optJSONObject("position");
        if (jSONObject2 != null) {
          videoModel.hasPosition = true;
          videoModel.top = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("top"));
          videoModel.left = NativeDimenUtil.convertRxToPx(jSONObject2.optInt("left"));
          videoModel.width = jSONObject2.optInt("width");
          AppBrandLogger.d("tma_VideoView", new Object[] { "videoMode.width = ", Integer.valueOf(videoModel.width) });
          if (videoModel.width > 0)
            videoModel.width = NativeDimenUtil.convertRxToPx(videoModel.width); 
          videoModel.height = jSONObject2.optInt("height");
          AppBrandLogger.d("tma_VideoView", new Object[] { "videoMode.height = ", Integer.valueOf(videoModel.height) });
          if (videoModel.height > 0)
            videoModel.height = NativeDimenUtil.convertRxToPx(videoModel.height); 
        } else {
          videoModel.hasPosition = false;
        } 
        if (jSONObject1.has("zIndex")) {
          videoModel.hasZIndex = true;
          videoModel.zIndex = jSONObject1.optInt("zIndex");
        } 
        if (jSONObject1.has("fixed")) {
          videoModel.hasFixed = true;
          videoModel.isFixed = jSONObject1.optBoolean("fixed");
        } 
        videoModel.mPreRollAdUnitId = jSONObject1.optString("preRollAdUnitId");
        videoModel.mPostRollAdUnitId = jSONObject1.optString("postRollAdUnitId");
        return videoModel;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "tma_VideoView", exception.getStackTrace());
        return videoModel;
      } 
    }
    
    public String toString() {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("filePath", this.filePath);
        jSONObject.put("videoPlayerId", this.videoPlayerId);
        jSONObject.put("hide", this.hide);
        jSONObject.put("needEvent", this.needEvent);
        jSONObject.put("autoplay", this.autoplay);
        jSONObject.put("poster", this.poster);
        jSONObject.put("controls", this.mControls);
        jSONObject.put("live", this.live);
        jSONObject.put("muted", this.muted);
        jSONObject.put("loop", this.mLoop);
        jSONObject.put("showFullscreenBtn", this.mShowFullScreenBtn);
        jSONObject.put("showPlayBtn", this.mShowPlayBtn);
        jSONObject.put("objectFit", this.mObjectFit);
        jSONObject.put("playBtnPosition", this.mPlayBtnPosition);
        JSONObject jSONObject1 = new JSONObject();
        jSONObject1.put("top", this.top);
        jSONObject1.put("left", this.left);
        jSONObject1.put("width", this.width);
        jSONObject1.put("height", this.height);
        jSONObject.put("position", jSONObject1);
      } catch (Exception exception) {
        AppBrandLogger.e("tma_VideoView", new Object[] { "toString", exception });
      } 
      return jSONObject.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\video\VideoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */