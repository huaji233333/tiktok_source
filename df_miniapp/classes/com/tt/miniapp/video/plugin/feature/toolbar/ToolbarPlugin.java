package com.tt.miniapp.video.plugin.feature.toolbar;

import android.os.Bundle;
import android.os.Message;
import com.tt.miniapp.util.WeakHandler;
import com.tt.miniapp.video.base.ITTVideoController;
import com.tt.miniapp.video.common.VideoDataContext;
import com.tt.miniapp.video.plugin.base.BaseVideoPlugin;
import com.tt.miniapp.video.plugin.base.IVideoPluginCommand;
import com.tt.miniapp.video.plugin.base.IVideoPluginEvent;
import com.tt.miniapp.video.plugin.event.BasePluginCommand;
import com.tt.miniapphost.AppBrandLogger;

public class ToolbarPlugin extends BaseVideoPlugin implements WeakHandler.IHandler {
  private boolean mBarEnableShow;
  
  private BottomProgressbarLayout mBottomProgressBar;
  
  private BottomToolbarLayout mBottomToolBar;
  
  private CenterToolbarLayout mCenterToolBar;
  
  private WeakHandler mHandler = new WeakHandler(this);
  
  private boolean mIsLoading;
  
  private boolean mIsPlaying;
  
  private boolean mIsToolBarShow;
  
  public boolean mShowReplayButton;
  
  private ITTVideoController.ShowStateEntity mShowStateEntity;
  
  private TopToolbarLayout mTopToolBar;
  
  private boolean isViewReady() {
    return (this.mTopToolBar != null && this.mCenterToolBar != null && this.mBottomToolBar != null && this.mBottomProgressBar != null);
  }
  
  private void showToolBar(boolean paramBoolean) {
    if (!isViewReady())
      return; 
    if (this.mIsLoading && paramBoolean)
      return; 
    this.mIsToolBarShow = paramBoolean;
    cancelDismissToolbar();
    updateBarShowState();
    if (this.mIsToolBarShow)
      autoDismissToolbar(); 
  }
  
  private void updateBarShowState() {
    if (this.mTopToolBar != null && this.mCenterToolBar != null && this.mBottomToolBar != null) {
      if (this.mBottomProgressBar == null)
        return; 
      boolean bool2 = this.mIsToolBarShow;
      ITTVideoController.ShowStateEntity showStateEntity = this.mShowStateEntity;
      boolean bool = true;
      boolean bool1 = bool2;
      if (showStateEntity != null) {
        if (bool2 && showStateEntity.isControls()) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        this.mCenterToolBar.setCenterPlayVisibility(this.mShowStateEntity.shouldShowCenterPlayBtn());
        this.mBottomToolBar.setBottomPlayVisibility(this.mShowStateEntity.shouldShowBottomPlayBtn());
        this.mBottomToolBar.setFullScreenVisibility(this.mShowStateEntity.shouldShowFullScreenBtn());
        this.mBottomProgressBar.showToolBar(this.mShowStateEntity.isControls());
      } 
      this.mTopToolBar.showToolBar(bool1);
      this.mCenterToolBar.showToolBar(bool1);
      this.mBottomToolBar.showToolBar(bool1);
      CenterToolbarLayout centerToolbarLayout = this.mCenterToolBar;
      if (this.mBarEnableShow && bool1) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      centerToolbarLayout.setVisible(bool2);
      BottomToolbarLayout bottomToolbarLayout = this.mBottomToolBar;
      if (this.mBarEnableShow && bool1) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      bottomToolbarLayout.setVisible(bool2);
      BottomProgressbarLayout bottomProgressbarLayout = this.mBottomProgressBar;
      if (this.mBarEnableShow && !bool1) {
        bool1 = bool;
      } else {
        bool1 = false;
      } 
      bottomProgressbarLayout.setVisible(bool1);
    } 
  }
  
  public void autoDismissToolbar() {
    if (this.mIsPlaying) {
      WeakHandler weakHandler = this.mHandler;
      if (weakHandler != null) {
        weakHandler.removeMessages(1001);
        Message message = this.mHandler.obtainMessage(1001);
        this.mHandler.sendMessageDelayed(message, 3000L);
        return;
      } 
    } else {
      cancelDismissToolbar();
    } 
  }
  
  public void cancelDismissToolbar() {
    WeakHandler weakHandler = this.mHandler;
    if (weakHandler != null)
      weakHandler.removeMessages(1001); 
  }
  
  public int getPluginType() {
    return 201;
  }
  
  public int getSeekPos(int paramInt) {
    return (VideoDataContext.getInstance().getDuration() > 0) ? (int)((paramInt * VideoDataContext.getInstance().getDuration()) * 1.0F / 100.0F) : 0;
  }
  
  public void handleMsg(Message paramMessage) {
    if (paramMessage.what != 1001)
      return; 
    showToolBar(false);
  }
  
  public boolean handleVideoEvent(IVideoPluginEvent paramIVideoPluginEvent) {
    if (paramIVideoPluginEvent != null) {
      BottomProgressbarLayout bottomProgressbarLayout1;
      boolean bool = false;
      int i = 0;
      AppBrandLogger.d("tma_ToolbarPlugin", new Object[] { "handleVideoEvent ", Integer.valueOf(paramIVideoPluginEvent.getType()) });
      Bundle bundle = paramIVideoPluginEvent.getArgs();
      int j = paramIVideoPluginEvent.getType();
      if (j != 111) {
        if (j != 113) {
          if (j != 200) {
            if (j != 207) {
              boolean bool1;
              CenterToolbarLayout centerToolbarLayout4;
              BottomToolbarLayout bottomToolbarLayout5;
              BottomProgressbarLayout bottomProgressbarLayout5;
              TopToolbarLayout topToolbarLayout;
              CenterToolbarLayout centerToolbarLayout3;
              BottomToolbarLayout bottomToolbarLayout4;
              CenterToolbarLayout centerToolbarLayout2;
              BottomToolbarLayout bottomToolbarLayout3;
              BottomProgressbarLayout bottomProgressbarLayout4;
              CenterToolbarLayout centerToolbarLayout1;
              BottomToolbarLayout bottomToolbarLayout2;
              BottomProgressbarLayout bottomProgressbarLayout3;
              BottomToolbarLayout bottomToolbarLayout6;
              BottomProgressbarLayout bottomProgressbarLayout6;
              switch (j) {
                default:
                  switch (j) {
                    default:
                      return super.handleVideoEvent(paramIVideoPluginEvent);
                    case 204:
                      this.mBarEnableShow = false;
                      this.mShowReplayButton = false;
                      centerToolbarLayout4 = this.mCenterToolBar;
                      if (centerToolbarLayout4 != null)
                        centerToolbarLayout4.reset(); 
                      bottomToolbarLayout5 = this.mBottomToolBar;
                      if (bottomToolbarLayout5 != null)
                        bottomToolbarLayout5.reset(); 
                      bottomProgressbarLayout5 = this.mBottomProgressBar;
                      if (bottomProgressbarLayout5 != null)
                        bottomProgressbarLayout5.reset(); 
                      break;
                    case 203:
                      this.mShowStateEntity = (ITTVideoController.ShowStateEntity)bottomProgressbarLayout5.getParcelable("showState");
                      updateBarShowState();
                    case 202:
                      break;
                  } 
                  bool1 = bottomProgressbarLayout5.getBoolean("fullscreen");
                  topToolbarLayout = this.mTopToolBar;
                  if (topToolbarLayout != null) {
                    topToolbarLayout.setFullScreen(bool1);
                    this.mTopToolBar.setVisible(bool1);
                  } 
                  centerToolbarLayout3 = this.mCenterToolBar;
                  if (centerToolbarLayout3 != null)
                    centerToolbarLayout3.setFullScreen(bool1); 
                  bottomToolbarLayout4 = this.mBottomToolBar;
                  if (bottomToolbarLayout4 != null)
                    bottomToolbarLayout4.setFullScreen(bool1); 
                case 108:
                  this.mIsLoading = false;
                  if (paramIVideoPluginEvent.getArgs() != null)
                    i = paramIVideoPluginEvent.getArgs().getInt("duration"); 
                  bottomToolbarLayout4 = this.mBottomToolBar;
                  if (bottomToolbarLayout4 != null)
                    bottomToolbarLayout4.setVideoDuration(i); 
                  showToolBar(true);
                case 107:
                  centerToolbarLayout2 = this.mCenterToolBar;
                  if (centerToolbarLayout2 != null)
                    centerToolbarLayout2.setVisible(true); 
                case 106:
                  bottomToolbarLayout6 = this.mBottomToolBar;
                  if (bottomToolbarLayout6 != null)
                    bottomToolbarLayout6.updateBuffer(centerToolbarLayout2.getInt("percent")); 
                  bottomProgressbarLayout6 = this.mBottomProgressBar;
                  if (bottomProgressbarLayout6 != null)
                    bottomProgressbarLayout6.updateBuffer(centerToolbarLayout2.getInt("percent")); 
                case 105:
                  centerToolbarLayout2 = this.mCenterToolBar;
                  if (centerToolbarLayout2 != null)
                    centerToolbarLayout2.setVisible(false); 
                case 104:
                  this.mIsPlaying = false;
                  this.mShowReplayButton = false;
                  updatePlayState();
                  cancelDismissToolbar();
                case 103:
                  this.mBarEnableShow = true;
                  this.mIsPlaying = true;
                  this.mShowReplayButton = false;
                  updatePlayState();
                  updateBarShowState();
                  autoDismissToolbar();
                case 102:
                  this.mIsPlaying = false;
                  this.mShowReplayButton = true;
                  cancelDismissToolbar();
                  showToolBar(true);
                  updatePlayState();
                  i = bool;
                  if (paramIVideoPluginEvent.getArgs() != null)
                    i = paramIVideoPluginEvent.getArgs().getInt("duration"); 
                  bottomToolbarLayout3 = this.mBottomToolBar;
                  if (bottomToolbarLayout3 != null)
                    bottomToolbarLayout3.updateTime(i, i); 
                  bottomProgressbarLayout4 = this.mBottomProgressBar;
                  if (bottomProgressbarLayout4 != null)
                    bottomProgressbarLayout4.updateTime(i, i); 
                case 101:
                  showToolBar(false);
                  centerToolbarLayout1 = this.mCenterToolBar;
                  if (centerToolbarLayout1 != null)
                    centerToolbarLayout1.reset(); 
                  bottomToolbarLayout2 = this.mBottomToolBar;
                  if (bottomToolbarLayout2 != null)
                    bottomToolbarLayout2.reset(); 
                  bottomProgressbarLayout3 = this.mBottomProgressBar;
                  if (bottomProgressbarLayout3 != null)
                    bottomProgressbarLayout3.reset(); 
                  this.mIsPlaying = false;
                case 100:
                  this.mIsLoading = true;
                  break;
              } 
              showToolBar(false);
            } 
            showToolBar(this.mIsToolBarShow ^ true);
            return true;
          } 
          if (!isViewReady()) {
            initView();
            return true;
          } 
        } 
        CenterToolbarLayout centerToolbarLayout = this.mCenterToolBar;
        if (centerToolbarLayout != null)
          centerToolbarLayout.reset(); 
        BottomToolbarLayout bottomToolbarLayout1 = this.mBottomToolBar;
        if (bottomToolbarLayout1 != null)
          bottomToolbarLayout1.reset(); 
        bottomProgressbarLayout1 = this.mBottomProgressBar;
        if (bottomProgressbarLayout1 != null)
          bottomProgressbarLayout1.reset(); 
        this.mIsLoading = false;
        this.mIsPlaying = false;
        showToolBar(true);
      } 
      BottomToolbarLayout bottomToolbarLayout = this.mBottomToolBar;
      if (bottomToolbarLayout != null)
        bottomToolbarLayout.updateTime(bottomProgressbarLayout1.getInt("position"), bottomProgressbarLayout1.getInt("duration")); 
      BottomProgressbarLayout bottomProgressbarLayout2 = this.mBottomProgressBar;
      if (bottomProgressbarLayout2 != null)
        bottomProgressbarLayout2.updateTime(bottomProgressbarLayout1.getInt("position"), bottomProgressbarLayout1.getInt("duration")); 
    } 
  }
  
  protected void initView() {
    if (this.mCenterToolBar == null) {
      this.mCenterToolBar = new CenterToolbarLayout();
      this.mCenterToolBar.initView(getContext(), getPluginMainContainer());
      this.mCenterToolBar.setUIListener(new CenterToolbarLayout.CenterBarUIListener() {
            public void onPlayOrPauseClick(boolean param1Boolean) {
              if (param1Boolean) {
                ToolbarPlugin.this.getHost().execCommand(2007);
                return;
              } 
              ToolbarPlugin.this.getHost().execCommand(2008);
            }
          });
    } 
    if (this.mTopToolBar == null) {
      this.mTopToolBar = new TopToolbarLayout();
      this.mTopToolBar.initView(getContext(), getPluginMainContainer());
      this.mTopToolBar.setUIListener(new TopToolbarLayout.TopBarUIListener() {
            public void onFullScreenBackClick() {
              if (ToolbarPlugin.this.getHost() != null)
                ToolbarPlugin.this.getHost().execCommand(1004); 
            }
          });
    } 
    if (this.mBottomToolBar == null) {
      this.mBottomToolBar = new BottomToolbarLayout();
      this.mBottomToolBar.initView(getContext(), getPluginMainContainer());
      this.mBottomToolBar.setUIListener(new BottomToolbarLayout.BottomBarUIListener() {
            public void onFullScreenClick() {
              if (ToolbarPlugin.this.getHost() != null)
                ToolbarPlugin.this.getHost().execCommand(1002); 
            }
            
            public void onPlayOrPauseClick(boolean param1Boolean) {
              if (param1Boolean) {
                ToolbarPlugin.this.getHost().execCommand(2007);
                return;
              } 
              ToolbarPlugin.this.getHost().execCommand(2008);
            }
            
            public void onSeekTo(int param1Int, boolean param1Boolean) {
              param1Int = ToolbarPlugin.this.getSeekPos(param1Int);
              if (ToolbarPlugin.this.getHost() != null)
                ToolbarPlugin.this.getHost().execCommand((IVideoPluginCommand)new BasePluginCommand(2009, Integer.valueOf(param1Int))); 
            }
            
            public void onStartTrackingTouch() {
              ToolbarPlugin.this.cancelDismissToolbar();
              if (ToolbarPlugin.this.getHost() != null)
                ToolbarPlugin.this.getHost().execCommand(2010); 
              if (ToolbarPlugin.this.mShowReplayButton) {
                ToolbarPlugin toolbarPlugin = ToolbarPlugin.this;
                toolbarPlugin.mShowReplayButton = false;
                toolbarPlugin.updatePlayState();
              } 
            }
            
            public void onStopTrackingTouch(int param1Int1, int param1Int2) {
              ToolbarPlugin.this.autoDismissToolbar();
            }
          });
    } 
    if (this.mBottomProgressBar == null) {
      this.mBottomProgressBar = new BottomProgressbarLayout();
      this.mBottomProgressBar.initView(getContext(), getPluginMainContainer());
    } 
    showToolBar(false);
  }
  
  public void updatePlayState() {
    CenterToolbarLayout centerToolbarLayout = this.mCenterToolBar;
    if (centerToolbarLayout != null)
      centerToolbarLayout.updatePlayBtnShowState(this.mIsPlaying, this.mShowReplayButton); 
    BottomToolbarLayout bottomToolbarLayout = this.mBottomToolBar;
    if (bottomToolbarLayout != null)
      bottomToolbarLayout.updatePlayBtnShowState(this.mIsPlaying, this.mShowReplayButton); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\toolbar\ToolbarPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */