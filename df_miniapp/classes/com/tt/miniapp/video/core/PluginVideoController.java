package com.tt.miniapp.video.core;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import com.ss.ttvideoengine.TTVideoEngine;
import com.ss.ttvideoengine.utils.Error;
import com.tt.miniapp.video.base.TTBaseVideoController;
import com.tt.miniapp.video.callback.IVideoFullScreen;
import com.tt.miniapp.video.plugin.base.IVideoPluginCommand;
import com.tt.miniapp.video.plugin.base.IVideoPluginEvent;
import com.tt.miniapp.video.plugin.event.VideoCommonEvent;
import java.lang.ref.WeakReference;

public class PluginVideoController extends TTBaseVideoController implements IVideoFullScreen {
  private WeakReference<Context> mContextRef;
  
  protected boolean mInScreen;
  
  protected PluginMediaViewLayout mMediaViewLayout;
  
  private void resetDataWhenVideoCompletedAndNotInScreen() {
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(204));
    this.mVideoEngine.setStartTime(0);
    resumeProgressUpdate();
  }
  
  public void execCommand(int paramInt) {
    if (paramInt != 1002) {
      if (paramInt != 1004) {
        if (paramInt != 2010) {
          if (paramInt != 2012) {
            if (paramInt != 2007) {
              if (paramInt == 2008)
                pauseVideo(); 
            } else {
              startVideo();
            } 
          } else {
            retry();
          } 
        } else {
          pauseProgressUpdate();
        } 
      } else if (isFullScreen()) {
        interceptFullScreen(false);
      } 
    } else {
      interceptFullScreen(isFullScreen() ^ true);
    } 
    super.execCommand(paramInt);
  }
  
  public void execCommand(IVideoPluginCommand paramIVideoPluginCommand) {
    if (paramIVideoPluginCommand != null && paramIVideoPluginCommand.getCommand() == 2009) {
      int i = ((Integer)paramIVideoPluginCommand.getParams()).intValue();
      if (i >= 0)
        seekTo(i); 
    } 
    super.execCommand(paramIVideoPluginCommand);
  }
  
  public Context getContext() {
    return !isContextValid() ? null : this.mContextRef.get();
  }
  
  public ViewGroup getPluginMainContainer() {
    PluginMediaViewLayout pluginMediaViewLayout = this.mMediaViewLayout;
    return (pluginMediaViewLayout != null) ? pluginMediaViewLayout.getPluginMainContainer() : null;
  }
  
  public TextureView getRenderView() {
    PluginMediaViewLayout pluginMediaViewLayout = this.mMediaViewLayout;
    return (pluginMediaViewLayout != null) ? pluginMediaViewLayout.getVideoView().getRenderView() : null;
  }
  
  protected void handleTouchDown() {}
  
  protected void handleTouchUp() {
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(207));
  }
  
  public void hideLoading() {
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(115));
  }
  
  public void initMediaView(Context paramContext, ViewGroup paramViewGroup) {
    this.mContextRef = new WeakReference<Context>(paramContext);
    if (this.mMediaViewLayout == null)
      this.mMediaViewLayout = new PluginMediaViewLayout(paramContext); 
    setVideoView(this.mMediaViewLayout.getVideoView());
    getVideoView().setFullScreenCallback(this);
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(200));
    PluginMediaViewLayout pluginMediaViewLayout = this.mMediaViewLayout;
    if (pluginMediaViewLayout != null && pluginMediaViewLayout.getPluginMainContainer() != null)
      this.mMediaViewLayout.getPluginMainContainer().setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
              int i = param1MotionEvent.getAction();
              if (i != 0) {
                if (i != 1)
                  return PluginVideoController.this.needConsumeEvent(); 
              } else {
                PluginVideoController.this.handleTouchDown();
                if (!PluginVideoController.this.needConsumeEvent()) {
                  PluginVideoController.this.handleTouchUp();
                  return PluginVideoController.this.needConsumeEvent();
                } 
                return PluginVideoController.this.needConsumeEvent();
              } 
              PluginVideoController.this.handleTouchUp();
              return PluginVideoController.this.needConsumeEvent();
            }
          }); 
    paramViewGroup.addView((View)this.mMediaViewLayout, new ViewGroup.LayoutParams(-1, -1));
  }
  
  public void interceptFullScreen(boolean paramBoolean) {}
  
  protected boolean isContextValid() {
    WeakReference<Context> weakReference = this.mContextRef;
    return (weakReference != null && weakReference.get() != null);
  }
  
  protected boolean needConsumeEvent() {
    return isFullScreen();
  }
  
  public void onBufferEnd() {
    super.onBufferEnd();
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(107));
  }
  
  public void onBufferStart() {
    super.onBufferStart();
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(105));
  }
  
  public void onBufferingUpdate(TTVideoEngine paramTTVideoEngine, int paramInt) {
    super.onBufferingUpdate(paramTTVideoEngine, paramInt);
    Bundle bundle = new Bundle();
    bundle.putInt("percent", paramInt);
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(106, bundle));
  }
  
  public void onCompletion(TTVideoEngine paramTTVideoEngine) {
    super.onCompletion(paramTTVideoEngine);
    if (isVideoPlaybackCompleted()) {
      VideoCommonEvent videoCommonEvent = new VideoCommonEvent(102);
      if (this.mVideoEngine != null) {
        Bundle bundle = new Bundle();
        bundle.putInt("duration", this.mVideoEngine.getDuration());
        videoCommonEvent.setArgs(bundle);
      } 
      notifyVideoPluginEvent((IVideoPluginEvent)videoCommonEvent);
      videoCommonEvent = new VideoCommonEvent(208);
      if (this.mPlayerEntity != null) {
        Bundle bundle = new Bundle();
        bundle.putString("poster", this.mPlayerEntity.getPoster());
        videoCommonEvent.setArgs(bundle);
      } 
      notifyVideoPluginEvent((IVideoPluginEvent)videoCommonEvent);
      if (!this.mInScreen)
        resetDataWhenVideoCompletedAndNotInScreen(); 
    } 
  }
  
  public void onEnterScreen() {
    if (this.mInScreen)
      return; 
    this.mInScreen = true;
  }
  
  public void onError(Error paramError) {
    super.onError(paramError);
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(110));
  }
  
  public void onFullScreen(boolean paramBoolean, int paramInt) {
    VideoCommonEvent videoCommonEvent = new VideoCommonEvent(202);
    Bundle bundle = new Bundle();
    bundle.putBoolean("fullscreen", paramBoolean);
    bundle.putInt("orientation", paramInt);
    videoCommonEvent.setArgs(bundle);
    notifyVideoPluginEvent((IVideoPluginEvent)videoCommonEvent);
  }
  
  public void onLeaveScreen() {
    if (!this.mInScreen)
      return; 
    this.mInScreen = false;
    if (isVideoPlaybackCompleted())
      resetDataWhenVideoCompletedAndNotInScreen(); 
  }
  
  public void onPlaybackStateChanged(TTVideoEngine paramTTVideoEngine, int paramInt) {
    super.onPlaybackStateChanged(paramTTVideoEngine, paramInt);
    if (paramInt == 1) {
      notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(103));
      return;
    } 
    if (paramInt == 2)
      notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(104)); 
  }
  
  public void onPrepare(TTVideoEngine paramTTVideoEngine) {
    super.onPrepare(paramTTVideoEngine);
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(100));
    if (this.mPlayerEntity != null) {
      Bundle bundle = new Bundle();
      bundle.putString("poster", this.mPlayerEntity.getPoster());
    } else {
      paramTTVideoEngine = null;
    } 
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(208, (Bundle)paramTTVideoEngine));
  }
  
  public void onPrepared(TTVideoEngine paramTTVideoEngine) {
    super.onPrepared(paramTTVideoEngine);
    VideoCommonEvent videoCommonEvent = new VideoCommonEvent(108);
    if (this.mVideoEngine != null) {
      Bundle bundle = new Bundle();
      bundle.putInt("duration", this.mVideoEngine.getDuration());
      videoCommonEvent.setArgs(bundle);
    } 
    notifyVideoPluginEvent((IVideoPluginEvent)videoCommonEvent);
  }
  
  public void onProgressAndTimeUpdate(int paramInt1, int paramInt2) {
    super.onProgressAndTimeUpdate(paramInt1, paramInt2);
    VideoCommonEvent videoCommonEvent = new VideoCommonEvent(111);
    Bundle bundle = new Bundle();
    bundle.putInt("duration", this.mDuration);
    bundle.putInt("position", this.mCurrent);
    videoCommonEvent.setArgs(bundle);
    notifyVideoPluginEvent((IVideoPluginEvent)videoCommonEvent);
  }
  
  public void onRenderStart(TTVideoEngine paramTTVideoEngine) {
    super.onRenderStart(paramTTVideoEngine);
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(109));
  }
  
  public void releaseMedia() {
    super.releaseMedia();
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(101));
  }
  
  public void showLoading() {
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(114));
  }
  
  public void stopVideo() {
    super.stopVideo();
    notifyVideoPluginEvent((IVideoPluginEvent)new VideoCommonEvent(113));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\core\PluginVideoController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */