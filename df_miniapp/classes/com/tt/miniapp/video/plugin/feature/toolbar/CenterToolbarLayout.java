package com.tt.miniapp.video.plugin.feature.toolbar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tt.miniapphost.util.UIUtils;

public class CenterToolbarLayout extends BaseVideoToolbar implements View.OnClickListener {
  private boolean mIsFullScreen;
  
  private boolean mIsPlaying;
  
  private ImageView mPlayPauseBtn;
  
  private boolean mShowReplayButton;
  
  private CenterBarUIListener mUIListener;
  
  private int getPlayPauseResId() {
    return this.mIsPlaying ? (this.mIsFullScreen ? 2097479767 : 2097479770) : (this.mShowReplayButton ? (this.mIsFullScreen ? 2097479769 : 2097479772) : (this.mIsFullScreen ? 2097479768 : 2097479771));
  }
  
  protected int getLayoutId() {
    return 2097676335;
  }
  
  protected int getRootId() {
    return 2097545498;
  }
  
  public void initView(Context paramContext, ViewGroup paramViewGroup) {
    super.initView(paramContext, paramViewGroup);
    if (this.mRootView == null)
      return; 
    this.mPlayPauseBtn = (ImageView)this.mRootView.findViewById(2097545444);
    this.mPlayPauseBtn.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2097545444) {
      CenterBarUIListener centerBarUIListener = this.mUIListener;
      if (centerBarUIListener != null)
        centerBarUIListener.onPlayOrPauseClick(this.mIsPlaying ^ true); 
    } 
  }
  
  public void reset() {
    super.reset();
    updatePlayBtnShowState(false, false);
  }
  
  void setCenterPlayVisibility(boolean paramBoolean) {
    byte b;
    ImageView imageView = this.mPlayPauseBtn;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 8;
    } 
    UIUtils.setViewVisibility((View)imageView, b);
  }
  
  public void setFullScreen(boolean paramBoolean) {
    if (this.mIsFullScreen == paramBoolean)
      return; 
    this.mIsFullScreen = paramBoolean;
    updatePlayBtnShowState(this.mIsPlaying, this.mShowReplayButton);
  }
  
  public void setUIListener(CenterBarUIListener paramCenterBarUIListener) {
    this.mUIListener = paramCenterBarUIListener;
  }
  
  public void updatePlayBtnShowState(boolean paramBoolean1, boolean paramBoolean2) {
    this.mShowReplayButton = paramBoolean2;
    this.mIsPlaying = paramBoolean1;
    ImageView imageView = this.mPlayPauseBtn;
    if (imageView != null)
      imageView.setImageResource(getPlayPauseResId()); 
  }
  
  static interface CenterBarUIListener {
    void onPlayOrPauseClick(boolean param1Boolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\toolbar\CenterToolbarLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */