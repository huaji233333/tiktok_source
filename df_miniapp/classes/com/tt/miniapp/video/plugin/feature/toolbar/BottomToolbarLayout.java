package com.tt.miniapp.video.plugin.feature.toolbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapp.util.VideoUtils;
import com.tt.miniapp.video.common.VideoLog;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.UIUtils;

public class BottomToolbarLayout extends BaseVideoToolbar implements View.OnClickListener {
  public int duration;
  
  private ImageView mBottomPlayBtn;
  
  private View mBottomPlayStub;
  
  public TextView mCurTimeText;
  
  private TextView mDurationText;
  
  private ImageView mFullScreenBtn;
  
  private View mFullScreenStub;
  
  private boolean mIsFullScreen;
  
  private boolean mIsPlaying;
  
  private SeekBar mSeekBar;
  
  private boolean mShowReplayButton;
  
  public BottomBarUIListener mUIListener;
  
  private int getPlayPauseResId() {
    return this.mIsPlaying ? 2097479762 : (this.mShowReplayButton ? 2097479764 : 2097479763);
  }
  
  private void updateUIStatus() {
    ImageView imageView = this.mFullScreenBtn;
    if (imageView != null) {
      int i;
      if (this.mIsFullScreen) {
        i = 2097479766;
      } else {
        i = 2097479765;
      } 
      imageView.setImageResource(i);
    } 
  }
  
  protected int getLayoutId() {
    return 2097676334;
  }
  
  protected int getRootId() {
    return 2097545430;
  }
  
  public void initView(Context paramContext, ViewGroup paramViewGroup) {
    super.initView(paramContext, paramViewGroup);
    if (this.mRootView == null)
      return; 
    this.mBottomPlayBtn = (ImageView)this.mRootView.findViewById(2097545431);
    this.mBottomPlayStub = this.mRootView.findViewById(2097545432);
    this.mCurTimeText = (TextView)this.mRootView.findViewById(2097545454);
    this.mSeekBar = (SeekBar)this.mRootView.findViewById(2097545450);
    this.mDurationText = (TextView)this.mRootView.findViewById(2097545453);
    this.mFullScreenBtn = (ImageView)this.mRootView.findViewById(2097545434);
    this.mFullScreenStub = this.mRootView.findViewById(2097545436);
    this.mFullScreenBtn.setOnClickListener(this);
    this.mBottomPlayBtn.setOnClickListener(this);
    this.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          private int mLastSeekPercent;
          
          public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {
            if (param1Boolean)
              BottomToolbarLayout.this.mCurTimeText.setText(VideoUtils.percentToTime(param1Int, BottomToolbarLayout.this.duration)); 
            VideoLog videoLog = VideoLog.getInstance();
            StringBuilder stringBuilder = new StringBuilder("seekprogress");
            stringBuilder.append(param1Int);
            videoLog.writeVideoLog(stringBuilder.toString(), false);
          }
          
          public void onStartTrackingTouch(SeekBar param1SeekBar) {
            this.mLastSeekPercent = param1SeekBar.getProgress();
            if (BottomToolbarLayout.this.mUIListener != null)
              BottomToolbarLayout.this.mUIListener.onStartTrackingTouch(); 
          }
          
          public void onStopTrackingTouch(SeekBar param1SeekBar) {
            int i = param1SeekBar.getProgress();
            boolean bool = BottomToolbarLayout.this.outOfBuffer(i);
            if (BottomToolbarLayout.this.mUIListener != null)
              BottomToolbarLayout.this.mUIListener.onStopTrackingTouch(this.mLastSeekPercent, i); 
            if (BottomToolbarLayout.this.mUIListener != null)
              BottomToolbarLayout.this.mUIListener.onSeekTo(i, bool); 
          }
        });
    updateUIStatus();
  }
  
  public void onClick(View paramView) {
    BottomBarUIListener bottomBarUIListener;
    if (paramView.getId() == 2097545434) {
      InputMethodUtil.hideSoftKeyboard((Activity)AppbrandContext.getInst().getCurrentActivity());
      bottomBarUIListener = this.mUIListener;
      if (bottomBarUIListener != null) {
        bottomBarUIListener.onFullScreenClick();
        return;
      } 
    } else if (bottomBarUIListener.getId() == 2097545431) {
      bottomBarUIListener = this.mUIListener;
      if (bottomBarUIListener != null)
        bottomBarUIListener.onPlayOrPauseClick(this.mIsPlaying ^ true); 
    } 
  }
  
  public boolean outOfBuffer(int paramInt) {
    SeekBar seekBar = this.mSeekBar;
    return (seekBar != null && paramInt > seekBar.getSecondaryProgress());
  }
  
  public void reset() {
    SeekBar seekBar = this.mSeekBar;
    if (seekBar != null) {
      seekBar.setProgress(0);
      this.mSeekBar.setSecondaryProgress(0);
    } 
    TextView textView = this.mCurTimeText;
    if (textView != null)
      textView.setText(VideoUtils.milliSecondsToTimer(0)); 
    updatePlayBtnShowState(false, false);
  }
  
  public void setBottomPlayVisibility(boolean paramBoolean) {
    ImageView imageView = this.mBottomPlayBtn;
    boolean bool = false;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 8;
    } 
    UIUtils.setViewVisibility((View)imageView, b);
    View view = this.mBottomPlayStub;
    byte b = bool;
    if (paramBoolean)
      b = 8; 
    UIUtils.setViewVisibility(view, b);
  }
  
  public void setFullScreen(boolean paramBoolean) {
    this.mIsFullScreen = paramBoolean;
    updateUIStatus();
  }
  
  public void setFullScreenVisibility(boolean paramBoolean) {
    ImageView imageView = this.mFullScreenBtn;
    boolean bool = false;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 8;
    } 
    UIUtils.setViewVisibility((View)imageView, b);
    View view = this.mFullScreenStub;
    byte b = bool;
    if (paramBoolean)
      b = 8; 
    UIUtils.setViewVisibility(view, b);
  }
  
  public void setUIListener(BottomBarUIListener paramBottomBarUIListener) {
    this.mUIListener = paramBottomBarUIListener;
  }
  
  public void setVideoDuration(int paramInt) {
    this.duration = paramInt;
    TextView textView = this.mDurationText;
    if (textView != null)
      textView.setText(VideoUtils.milliSecondsToTimer(paramInt)); 
  }
  
  public void updateBuffer(int paramInt) {
    SeekBar seekBar = this.mSeekBar;
    if (seekBar != null)
      seekBar.setSecondaryProgress(paramInt); 
  }
  
  public void updatePlayBtnShowState(boolean paramBoolean1, boolean paramBoolean2) {
    this.mShowReplayButton = paramBoolean2;
    this.mIsPlaying = paramBoolean1;
    ImageView imageView = this.mBottomPlayBtn;
    if (imageView != null)
      imageView.setImageResource(getPlayPauseResId()); 
  }
  
  public void updateTime(int paramInt1, int paramInt2) {
    TextView textView = this.mDurationText;
    if (textView != null)
      textView.setText(VideoUtils.milliSecondsToTimer(paramInt2)); 
    textView = this.mCurTimeText;
    if (textView != null)
      textView.setText(VideoUtils.milliSecondsToTimer(paramInt1)); 
    SeekBar seekBar = this.mSeekBar;
    if (seekBar != null)
      seekBar.setProgress(VideoUtils.timeToPercent(paramInt1, paramInt2)); 
  }
  
  static interface BottomBarUIListener {
    void onFullScreenClick();
    
    void onPlayOrPauseClick(boolean param1Boolean);
    
    void onSeekTo(int param1Int, boolean param1Boolean);
    
    void onStartTrackingTouch();
    
    void onStopTrackingTouch(int param1Int1, int param1Int2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\toolbar\BottomToolbarLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */