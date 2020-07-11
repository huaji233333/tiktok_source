package com.tt.miniapp.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.tt.miniapp.component.nativeview.video.controller.BaseVideoViewController;
import com.tt.miniapp.video.base.ITTVideoController;
import com.tt.miniapp.video.core.PluginVideoController;
import com.tt.miniapp.video.plugin.base.IVideoPlugin;
import com.tt.miniapp.video.plugin.feature.loading.VideoLoadingPlugin;
import com.tt.miniapp.video.plugin.feature.poster.PosterPlugin;
import com.tt.miniapp.video.plugin.feature.toolbar.ToolbarPlugin;

public class TTVideoView extends RelativeLayout {
  protected PluginVideoController mVideoController;
  
  public TTVideoView(Context paramContext) {
    this(paramContext, null, 0);
  }
  
  public TTVideoView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public TTVideoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected PluginVideoController createVideoController() {
    return new PluginVideoController();
  }
  
  public void exitFullScreen() {
    PluginVideoController pluginVideoController = this.mVideoController;
    if (pluginVideoController != null && pluginVideoController.isFullScreen())
      this.mVideoController.exitFullScreen(); 
  }
  
  public TextureView getRenderView() {
    return this.mVideoController.getRenderView();
  }
  
  public PluginVideoController getVideoController() {
    return this.mVideoController;
  }
  
  protected void initVideoController() {
    this.mVideoController = createVideoController();
    onInitVideoController();
    this.mVideoController.initMediaView(getContext(), (ViewGroup)this);
  }
  
  public void initView() {
    initVideoController();
  }
  
  public void onInitVideoController() {
    this.mVideoController.addPlugin((IVideoPlugin)new PosterPlugin());
    this.mVideoController.addPlugin((IVideoPlugin)new ToolbarPlugin());
    this.mVideoController.addPlugin((IVideoPlugin)new VideoLoadingPlugin());
  }
  
  public void pauseVideo() {
    PluginVideoController pluginVideoController = this.mVideoController;
    if (pluginVideoController != null)
      pluginVideoController.pauseVideo(); 
  }
  
  public void play(ITTVideoController.PlayerEntity paramPlayerEntity) {
    PluginVideoController pluginVideoController = this.mVideoController;
    if (pluginVideoController != null)
      pluginVideoController.play(paramPlayerEntity); 
  }
  
  public void releaseMedia() {
    PluginVideoController pluginVideoController = this.mVideoController;
    if (pluginVideoController != null)
      pluginVideoController.releaseMedia(); 
  }
  
  public void requestFullScreen(int paramInt) {
    PluginVideoController pluginVideoController = this.mVideoController;
    if (pluginVideoController != null && !pluginVideoController.isFullScreen()) {
      if (paramInt > 0) {
        pluginVideoController = this.mVideoController;
        if (pluginVideoController instanceof BaseVideoViewController)
          ((BaseVideoViewController)pluginVideoController).setZIndex(paramInt); 
      } 
      this.mVideoController.enterFullScreen();
    } 
  }
  
  public void seek(int paramInt) {
    PluginVideoController pluginVideoController = this.mVideoController;
    if (pluginVideoController != null)
      pluginVideoController.seekTo(paramInt); 
  }
  
  public void startVideo() {
    PluginVideoController pluginVideoController = this.mVideoController;
    if (pluginVideoController != null)
      pluginVideoController.startVideo(); 
  }
  
  public void stopVideo() {
    PluginVideoController pluginVideoController = this.mVideoController;
    if (pluginVideoController != null)
      pluginVideoController.stopVideo(); 
  }
  
  public void updateShowState(ITTVideoController.ShowStateEntity paramShowStateEntity) {
    PluginVideoController pluginVideoController = this.mVideoController;
    if (pluginVideoController != null)
      pluginVideoController.updateShowState(paramShowStateEntity); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\TTVideoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */