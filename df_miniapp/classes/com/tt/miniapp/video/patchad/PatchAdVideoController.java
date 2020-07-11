package com.tt.miniapp.video.patchad;

import android.app.Application;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.ss.ttvideoengine.TTVideoEngine;
import com.tt.miniapp.video.core.PluginVideoController;
import com.tt.miniapp.video.plugin.base.IVideoPluginCommand;
import com.tt.miniapp.video.plugin.base.IVideoPluginEvent;
import com.tt.miniapp.video.plugin.event.VideoCommonEvent;
import com.tt.miniapp.video.plugin.feature.patchad.PatchAdCommand;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.ad.a;
import com.tt.option.ad.c;
import com.tt.option.ad.d;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class PatchAdVideoController extends PluginVideoController {
  private static final Set<WeakReference<PatchAdVideoController>> mControllers = Collections.synchronizedSet(new HashSet<WeakReference<PatchAdVideoController>>());
  
  protected boolean called2Hidden;
  
  protected Boolean calledVideoAdStarted;
  
  private boolean hasCalledPostRollAdError;
  
  private boolean hasCalledPreRollAdError;
  
  public boolean hasPreloadedPostRollAd;
  
  public boolean isLoadingPreRollAdTimeout = false;
  
  public boolean isPatchAdLoading;
  
  private boolean isPatchAdPlaying;
  
  private boolean isPostRollAdLoaded;
  
  private boolean isPostRollAdPlayed;
  
  private boolean isPreRollAdLoaded;
  
  public boolean isPreRollAdPlayed;
  
  public boolean isPreloadingPostRollAd;
  
  private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
      public void onAudioFocusChange(int param1Int) {}
    };
  
  private AudioManager mAudioManager;
  
  private PatchAdVideoCallback mCallback;
  
  private Handler mHandler = new Handler(Looper.getMainLooper());
  
  private Runnable mLoadingPreRollAdTimeoutTask = new Runnable() {
      public void run() {
        if (PatchAdVideoController.this.isLoadingPreRollAdTimeout)
          return; 
        PatchAdVideoController patchAdVideoController = PatchAdVideoController.this;
        patchAdVideoController.isLoadingPreRollAdTimeout = true;
        if (patchAdVideoController.isAutoPlay())
          PatchAdVideoController.this.startVideo(); 
      }
    };
  
  private int mPreloadedPostRollAdCount;
  
  private final int mVideoPlayerId;
  
  public PatchAdVideoController(PatchAdVideoCallback paramPatchAdVideoCallback, int paramInt) {
    this.mCallback = paramPatchAdVideoCallback;
    this.mVideoPlayerId = paramInt;
    addController(this);
  }
  
  private void abandonAudioFocus() {
    AudioManager audioManager = this.mAudioManager;
    if (audioManager != null) {
      AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = this.mAudioFocusListener;
      if (onAudioFocusChangeListener != null) {
        audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        this.mAudioManager = null;
      } 
    } 
  }
  
  private static void addController(PatchAdVideoController paramPatchAdVideoController) {
    Iterator<WeakReference<PatchAdVideoController>> iterator = mControllers.iterator();
    while (iterator.hasNext()) {
      WeakReference weakReference = iterator.next();
      if (weakReference == null || weakReference.get() == null)
        iterator.remove(); 
    } 
    mControllers.add(new WeakReference<PatchAdVideoController>(paramPatchAdVideoController));
  }
  
  private boolean isSupportPatchAd() {
    return (isSupportPreRollAd() || isSupportPostRollAd());
  }
  
  private boolean isSupportPostRollAd() {
    return HostDependManager.getInst().isSupportAd(c.APP_VIDEO_PATCH_AD_POST);
  }
  
  private boolean isSupportPreRollAd() {
    return HostDependManager.getInst().isSupportAd(c.APP_VIDEO_PATCH_AD_PRE);
  }
  
  private void onVideoStarted(int paramInt) {
    if (paramInt == this.mVideoPlayerId)
      return; 
    destroyPatchAd();
  }
  
  private static void onVideoViewStarted(int paramInt) {
    Iterator<WeakReference<PatchAdVideoController>> iterator = mControllers.iterator();
    while (iterator.hasNext()) {
      WeakReference<PatchAdVideoController> weakReference = iterator.next();
      if (weakReference != null) {
        PatchAdVideoController patchAdVideoController = weakReference.get();
        if (patchAdVideoController != null) {
          patchAdVideoController.onVideoStarted(paramInt);
          continue;
        } 
      } 
      iterator.remove();
    } 
  }
  
  private void requestAudioFocus() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null)
      return; 
    if (this.mAudioManager == null)
      this.mAudioManager = (AudioManager)application.getSystemService("audio"); 
    try {
      return;
    } finally {
      application = null;
    } 
  }
  
  private void startLoadingPreRollAdTimeoutTask() {
    this.mHandler.removeCallbacks(this.mLoadingPreRollAdTimeoutTask);
    this.isLoadingPreRollAdTimeout = false;
    this.mHandler.postDelayed(this.mLoadingPreRollAdTimeoutTask, 5000L);
  }
  
  private void startVideoIfNecessary(boolean paramBoolean) {
    if (paramBoolean) {
      startVideo();
      return;
    } 
    if (isVideoLooping())
      startVideo(); 
  }
  
  public void destroyPatchAd() {
    abandonAudioFocus();
    stopLoadingPreRollAdTimeoutTask();
    this.isPatchAdPlaying = false;
    if (isSupportPatchAd()) {
      if (!hasPatchAd())
        return; 
      notifyPatchAdEvent(306);
    } 
  }
  
  public void dispatchVideoAdLoaded(boolean paramBoolean) {
    hideLoading();
    if (paramBoolean) {
      if (!this.isPreRollAdLoaded) {
        this.isPreRollAdLoaded = true;
        onVideoAdLoaded(true);
        return;
      } 
    } else if (!this.isPostRollAdLoaded) {
      this.isPostRollAdLoaded = true;
      onVideoAdLoaded(false);
    } 
  }
  
  public void execCommand(IVideoPluginCommand paramIVideoPluginCommand) {
    if (paramIVideoPluginCommand instanceof PatchAdCommand) {
      PatchAdCommand patchAdCommand = (PatchAdCommand)paramIVideoPluginCommand;
      switch (patchAdCommand.command) {
        case 3005:
          onVideoAdFullscreenChange(patchAdCommand.isPreRollAd, patchAdCommand.isFullScreen);
          return;
        case 3004:
          this.isPatchAdPlaying = false;
          startVideoIfNecessary(patchAdCommand.isPreRollAd);
          onVideoAdError(patchAdCommand.isPreRollAd, patchAdCommand.code, patchAdCommand.message);
          destroyPatchAd();
          break;
        case 3003:
          this.isPatchAdPlaying = false;
          startVideoIfNecessary(patchAdCommand.isPreRollAd);
          onVideoAdClose(patchAdCommand.isPreRollAd);
          destroyPatchAd();
          break;
        case 3002:
          this.isPatchAdPlaying = false;
          startVideoIfNecessary(patchAdCommand.isPreRollAd);
          onVideoAdEnded(patchAdCommand.isPreRollAd);
          destroyPatchAd();
          break;
        case 3001:
          onVideoAdStart(patchAdCommand.isPreRollAd);
          break;
        case 3000:
          dispatchVideoAdLoaded(patchAdCommand.isPreRollAd);
          break;
      } 
    } 
    super.execCommand(paramIVideoPluginCommand);
  }
  
  public boolean hasPatchAd() {
    return (hasPreRollAd() || hasPostRollAd());
  }
  
  public boolean hasPostRollAd() {
    return !TextUtils.isEmpty(this.mCallback.getPostRollAdUnitId());
  }
  
  public boolean hasPreRollAd() {
    return !TextUtils.isEmpty(this.mCallback.getPreRollAdUnitId());
  }
  
  public boolean isPatchAdLoadingOrPlaying() {
    return (this.isPatchAdLoading || this.isPatchAdPlaying);
  }
  
  public void notifyPatchAdEvent(int paramInt) {
    notifyPatchAdEvent(paramInt, (Bundle)null);
  }
  
  public void notifyPatchAdEvent(int paramInt, Bundle paramBundle) {
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(paramInt, paramBundle));
  }
  
  public void onCompletion(TTVideoEngine paramTTVideoEngine) {
    super.onCompletion(paramTTVideoEngine);
    if (this.isPostRollAdPlayed)
      return; 
    if (!isSupportPostRollAd()) {
      if (!this.hasCalledPostRollAdError) {
        this.hasCalledPostRollAdError = true;
        onVideoAdError(false, 1003, "feature is not supported in app");
      } 
      return;
    } 
    a a = d.a(this.mCallback.getPostRollAdUnitId(), c.APP_VIDEO_PATCH_AD_POST);
    if (!a.a) {
      if (!this.hasCalledPostRollAdError) {
        this.hasCalledPostRollAdError = true;
        onVideoAdError(false, a.b, a.c);
      } 
      return;
    } 
    if (isVideoLooping() && !isVideoPlaybackCompleted())
      pauseVideo(); 
    playPostRollAd();
  }
  
  public void onEnterScreen() {
    if (this.mInScreen)
      return; 
    super.onEnterScreen();
    if (isSupportPatchAd()) {
      if (!hasPatchAd())
        return; 
      notifyPatchAdEvent(307);
    } 
  }
  
  public void onLeaveScreen() {
    if (!this.mInScreen)
      return; 
    super.onLeaveScreen();
    if (isSupportPatchAd()) {
      if (!hasPatchAd())
        return; 
      notifyPatchAdEvent(308);
    } 
  }
  
  public void onPrepared(TTVideoEngine paramTTVideoEngine) {
    if (!hasPreRollAd()) {
      super.onPrepared(paramTTVideoEngine);
      return;
    } 
    if (paramTTVideoEngine.getDuration() < 600000) {
      this.isPreRollAdPlayed = true;
      super.onPrepared(paramTTVideoEngine);
      return;
    } 
    if (this.isPreRollAdPlayed) {
      super.onPrepared(paramTTVideoEngine);
      return;
    } 
    if (!isSupportPreRollAd()) {
      if (!this.hasCalledPreRollAdError) {
        this.hasCalledPreRollAdError = true;
        onVideoAdError(true, 1003, "feature is not supported in app");
      } 
      super.onPrepared(paramTTVideoEngine);
      return;
    } 
    a a = d.a(this.mCallback.getPreRollAdUnitId(), c.APP_VIDEO_PATCH_AD_PRE);
    if (!a.a) {
      if (!this.hasCalledPreRollAdError) {
        this.hasCalledPreRollAdError = true;
        onVideoAdError(true, a.b, a.c);
      } 
      super.onPrepared(paramTTVideoEngine);
      return;
    } 
    super.onPrepared(paramTTVideoEngine);
    if (isAutoPlay()) {
      pauseVideo();
      showLoading();
    } 
    this.isPatchAdLoading = true;
    this.mCallback.getPatchAdManager();
    this.mCallback.getPreRollAdUnitId();
    new Object() {
        public void onFailure(int param1Int, String param1String) {
          PatchAdVideoController patchAdVideoController2 = PatchAdVideoController.this;
          patchAdVideoController2.isPatchAdLoading = false;
          patchAdVideoController2.isPreRollAdPlayed = true;
          patchAdVideoController2.onVideoAdError(true, param1Int, param1String);
          PatchAdVideoController.this.stopLoadingPreRollAdTimeoutTask();
          if (PatchAdVideoController.this.isLoadingPreRollAdTimeout)
            return; 
          PatchAdVideoController patchAdVideoController1 = PatchAdVideoController.this;
          patchAdVideoController1.isLoadingPreRollAdTimeout = true;
          if (patchAdVideoController1.isAutoPlay())
            PatchAdVideoController.this.startVideo(); 
        }
        
        public void onSuccess() {
          PatchAdVideoController patchAdVideoController = PatchAdVideoController.this;
          patchAdVideoController.isPatchAdLoading = false;
          patchAdVideoController.dispatchVideoAdLoaded(true);
          PatchAdVideoController.this.stopLoadingPreRollAdTimeoutTask();
          if (PatchAdVideoController.this.isLoadingPreRollAdTimeout)
            return; 
          patchAdVideoController = PatchAdVideoController.this;
          patchAdVideoController.isLoadingPreRollAdTimeout = true;
          if (patchAdVideoController.isAutoPlay())
            PatchAdVideoController.this.playPreRollAd(); 
        }
      };
    startLoadingPreRollAdTimeoutTask();
  }
  
  public void onProgressAndTimeUpdate(int paramInt1, int paramInt2) {
    super.onProgressAndTimeUpdate(paramInt1, paramInt2);
    double d1 = paramInt1;
    double d2 = paramInt2;
    Double.isNaN(d2);
    if (d1 <= d2 * 0.8D)
      return; 
    if (!this.hasPreloadedPostRollAd && !this.isPreloadingPostRollAd) {
      if (this.mPreloadedPostRollAdCount >= 3)
        return; 
      if (isSupportPostRollAd() && hasPostRollAd()) {
        if (this.isPostRollAdPlayed)
          return; 
        if (!(d.a(this.mCallback.getPostRollAdUnitId(), c.APP_VIDEO_PATCH_AD_POST)).a)
          return; 
        this.isPreloadingPostRollAd = true;
        this.mPreloadedPostRollAdCount++;
        this.mCallback.getPatchAdManager();
        this.mCallback.getPostRollAdUnitId();
        new Object() {
            public void onFailure(int param1Int, String param1String) {
              PatchAdVideoController patchAdVideoController = PatchAdVideoController.this;
              patchAdVideoController.hasPreloadedPostRollAd = false;
              patchAdVideoController.isPreloadingPostRollAd = false;
            }
            
            public void onSuccess() {
              PatchAdVideoController patchAdVideoController = PatchAdVideoController.this;
              patchAdVideoController.hasPreloadedPostRollAd = true;
              patchAdVideoController.isPreloadingPostRollAd = false;
              patchAdVideoController.dispatchVideoAdLoaded(false);
            }
          };
      } 
    } 
  }
  
  public void onStuffOverVideoVisibilityChange(boolean paramBoolean1, boolean paramBoolean2) {
    StringBuilder stringBuilder = new StringBuilder("onStuffOverVideoVisibilityChange: ");
    stringBuilder.append(paramBoolean2);
    AppBrandLogger.d("PatchAdVideoController", new Object[] { stringBuilder.toString() });
  }
  
  public void onVideoAdClose(boolean paramBoolean) {
    AppBrandLogger.d("PatchAdVideoController", new Object[] { "onVideoAdClose", "isPreRollAd", Boolean.valueOf(paramBoolean) });
    onStuffOverVideoVisibilityChange(paramBoolean, true);
  }
  
  public void onVideoAdEnded(boolean paramBoolean) {
    AppBrandLogger.d("PatchAdVideoController", new Object[] { "onVideoAdEnded", "isPreRollAd", Boolean.valueOf(paramBoolean) });
    onStuffOverVideoVisibilityChange(paramBoolean, true);
  }
  
  public void onVideoAdError(boolean paramBoolean, int paramInt, String paramString) {
    AppBrandLogger.d("PatchAdVideoController", new Object[] { "onVideoAdError", "isPreRollAd", Boolean.valueOf(paramBoolean), "code", Integer.valueOf(paramInt), "msg", paramString });
    onStuffOverVideoVisibilityChange(paramBoolean, true);
  }
  
  public void onVideoAdFullscreenChange(boolean paramBoolean1, boolean paramBoolean2) {
    StringBuilder stringBuilder = new StringBuilder("onVideoAdFullscreenChange: ");
    stringBuilder.append(paramBoolean2);
    AppBrandLogger.d("PatchAdVideoController", new Object[] { stringBuilder.toString() });
  }
  
  public void onVideoAdLoaded(boolean paramBoolean) {
    AppBrandLogger.d("PatchAdVideoController", new Object[] { "onVideoAdLoad", "isPreRollAd", Boolean.valueOf(paramBoolean) });
  }
  
  public void onVideoAdStart(boolean paramBoolean) {
    AppBrandLogger.d("PatchAdVideoController", new Object[] { "onVideoAdStart", "isPreRollAd", Boolean.valueOf(paramBoolean) });
  }
  
  public void onVideoUrlChanged(String paramString1, String paramString2) {
    super.onVideoUrlChanged(paramString1, paramString2);
    resetPatchAdStatus();
  }
  
  public void pausePatchAd() {
    if (isSupportPatchAd()) {
      if (!hasPatchAd())
        return; 
      notifyPatchAdEvent(305);
    } 
  }
  
  public void playPostRollAd() {
    if (isSupportPostRollAd()) {
      if (!hasPostRollAd())
        return; 
      if (this.isPostRollAdPlayed)
        return; 
      this.isPostRollAdPlayed = true;
      this.isPatchAdPlaying = true;
      requestAudioFocus();
      onStuffOverVideoVisibilityChange(false, false);
      notifyPatchAdEvent(302);
    } 
  }
  
  public void playPreRollAd() {
    if (isSupportPreRollAd()) {
      if (!hasPreRollAd())
        return; 
      if (this.isPreRollAdPlayed)
        return; 
      this.isPreRollAdPlayed = true;
      this.isPatchAdPlaying = true;
      requestAudioFocus();
      onStuffOverVideoVisibilityChange(true, false);
      notifyPatchAdEvent(300);
    } 
  }
  
  public void resetPatchAdStatus() {
    destroyPatchAd();
    this.isPreRollAdPlayed = false;
    this.isPreRollAdLoaded = false;
    this.hasCalledPreRollAdError = false;
    this.isPostRollAdPlayed = false;
    this.isPostRollAdLoaded = false;
    this.hasCalledPostRollAdError = false;
    this.isPreloadingPostRollAd = false;
    this.hasPreloadedPostRollAd = false;
    this.mPreloadedPostRollAdCount = 0;
    this.isPatchAdLoading = false;
    this.isPatchAdPlaying = false;
    this.isLoadingPreRollAdTimeout = false;
  }
  
  public void resumePatchAd() {
    if (isSupportPatchAd()) {
      if (!hasPatchAd())
        return; 
      notifyPatchAdEvent(304);
    } 
  }
  
  public void startVideo() {
    if (!hasPreRollAd() || !isSupportPreRollAd()) {
      super.startVideo();
      onVideoViewStarted(this.mVideoPlayerId);
      return;
    } 
    if (!(d.a(this.mCallback.getPreRollAdUnitId(), c.APP_VIDEO_PATCH_AD_PRE)).a || this.isPreRollAdPlayed) {
      super.startVideo();
      onVideoViewStarted(this.mVideoPlayerId);
      return;
    } 
    playPreRollAd();
    onVideoViewStarted(this.mVideoPlayerId);
  }
  
  public void stopLoadingPreRollAdTimeoutTask() {
    this.mHandler.removeCallbacks(this.mLoadingPreRollAdTimeoutTask);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\patchad\PatchAdVideoController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */