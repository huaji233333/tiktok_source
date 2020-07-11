package com.tt.miniapp.video.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import com.tt.miniapp.util.VideoUtils;
import com.tt.miniapp.video.callback.IVideoFullScreen;
import com.tt.miniapp.video.view.widget.VideoTextureView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;

public class CoreVideoView extends RelativeLayout implements TextureView.SurfaceTextureListener {
  private IVideoFullScreen mFullScreenListener;
  
  private boolean mIsFullScreen;
  
  private VideoTextureView mVideoTextureView;
  
  private IVideoViewCallback mVideoViewCallback;
  
  public CoreVideoView(Context paramContext) {
    this(paramContext, null, 0);
  }
  
  public CoreVideoView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public CoreVideoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    initViews(paramContext);
  }
  
  private void initViews(Context paramContext) {
    this.mVideoTextureView = (VideoTextureView)LayoutInflater.from(paramContext).inflate(2097676354, (ViewGroup)this).findViewById(2097545399);
    this.mVideoTextureView.setSurfaceTextureListener(this);
  }
  
  private void requestOrientation(int paramInt) {
    Exception exception;
    try {
      Activity activity2 = VideoUtils.getViewAttachedActivity((View)this.mVideoTextureView);
    } finally {
      exception = null;
      AppBrandLogger.eWithThrowable("tma_CoreVideoView", "requestOrientation", exception);
    } 
    if (exception == null)
      return; 
    UIUtils.setActivityOrientation((Activity)exception, paramInt);
    Window window = exception.getWindow();
    if (window == null)
      return; 
    if (paramInt == 0 || paramInt == 8) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if (paramInt != 0) {
      window.setFlags(1024, 1024);
      return;
    } 
    window.clearFlags(1024);
  }
  
  private void setFullScreen(boolean paramBoolean, int paramInt) {
    if (this.mIsFullScreen != paramBoolean) {
      this.mIsFullScreen = paramBoolean;
      IVideoFullScreen iVideoFullScreen = this.mFullScreenListener;
      if (iVideoFullScreen != null)
        iVideoFullScreen.onFullScreen(paramBoolean, paramInt); 
    } 
  }
  
  public void enterFullScreen() {
    setFullScreen(true, 0);
  }
  
  public void exitFullScreen() {
    setFullScreen(false, 1);
  }
  
  public TextureView getRenderView() {
    return (TextureView)this.mVideoTextureView;
  }
  
  public boolean isFullScreen() {
    return this.mIsFullScreen;
  }
  
  public void keepScreenOn(boolean paramBoolean) {
    if (this.mVideoTextureView != null) {
      AppBrandLogger.d("tma_CoreVideoView", new Object[] { "setKeepScreenOn#", Boolean.valueOf(paramBoolean) });
      this.mVideoTextureView.setKeepScreenOn(paramBoolean);
    } 
  }
  
  public void onSurfaceTextureAvailable(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2) {
    keepScreenOn(true);
    IVideoViewCallback iVideoViewCallback = this.mVideoViewCallback;
    if (iVideoViewCallback != null)
      iVideoViewCallback.textureViewCreated(new Surface(paramSurfaceTexture)); 
  }
  
  public boolean onSurfaceTextureDestroyed(SurfaceTexture paramSurfaceTexture) {
    keepScreenOn(false);
    IVideoViewCallback iVideoViewCallback = this.mVideoViewCallback;
    if (iVideoViewCallback != null)
      iVideoViewCallback.textureViewDestroyed(new Surface(paramSurfaceTexture)); 
    return false;
  }
  
  public void onSurfaceTextureSizeChanged(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2) {
    IVideoViewCallback iVideoViewCallback = this.mVideoViewCallback;
    if (iVideoViewCallback != null)
      iVideoViewCallback.textureViewSizeChanged(paramSurfaceTexture, paramInt1, paramInt2); 
  }
  
  public void onSurfaceTextureUpdated(SurfaceTexture paramSurfaceTexture) {}
  
  public void setFullScreenCallback(IVideoFullScreen paramIVideoFullScreen) {
    this.mFullScreenListener = paramIVideoFullScreen;
  }
  
  public void setObjectFit(String paramString) {
    VideoTextureView videoTextureView = this.mVideoTextureView;
    if (videoTextureView != null)
      videoTextureView.setObjectFit(paramString); 
  }
  
  public void setSurfaceViewVisible(int paramInt) {
    AppBrandLogger.d("tma_CoreVideoView", new Object[] { "setSurfaceViewVisible ", Integer.valueOf(paramInt) });
    VideoTextureView videoTextureView = this.mVideoTextureView;
    if (videoTextureView != null)
      videoTextureView.setVisibility(paramInt); 
  }
  
  public void setVideoSize(int paramInt1, int paramInt2) {
    this.mVideoTextureView.setVideoSize(paramInt1, paramInt2);
  }
  
  public void setVideoViewCallback(IVideoViewCallback paramIVideoViewCallback) {
    this.mVideoViewCallback = paramIVideoViewCallback;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\view\CoreVideoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */