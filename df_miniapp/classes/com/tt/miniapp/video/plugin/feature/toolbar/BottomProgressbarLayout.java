package com.tt.miniapp.video.plugin.feature.toolbar;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.tt.miniapp.util.VideoUtils;

public class BottomProgressbarLayout extends BaseVideoToolbar {
  private ProgressBar mProgressBar;
  
  protected int getLayoutId() {
    return 2097676333;
  }
  
  protected int getRootId() {
    return 2097545433;
  }
  
  public void initView(Context paramContext, ViewGroup paramViewGroup) {
    super.initView(paramContext, paramViewGroup);
    this.mShow = true;
    if (this.mRootView == null)
      return; 
    this.mProgressBar = (ProgressBar)this.mRootView.findViewById(2097545433);
  }
  
  public void reset() {
    ProgressBar progressBar = this.mProgressBar;
    if (progressBar != null) {
      progressBar.setProgress(0);
      this.mProgressBar.setSecondaryProgress(0);
    } 
  }
  
  public void updateBuffer(int paramInt) {
    ProgressBar progressBar = this.mProgressBar;
    if (progressBar != null)
      progressBar.setSecondaryProgress(paramInt); 
  }
  
  public void updateTime(int paramInt1, int paramInt2) {
    ProgressBar progressBar = this.mProgressBar;
    if (progressBar != null)
      progressBar.setProgress(VideoUtils.timeToPercent(paramInt1, paramInt2)); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\toolbar\BottomProgressbarLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */