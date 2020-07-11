package com.tt.miniapp.video.plugin.feature.toolbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tt.miniapphost.util.UIUtils;

public abstract class BaseVideoToolbar {
  protected View mRootView;
  
  protected boolean mShow;
  
  protected boolean mVisible;
  
  private void updateVisibility() {
    byte b;
    View view = this.mRootView;
    if (this.mVisible && this.mShow) {
      b = 0;
    } else {
      b = 4;
    } 
    UIUtils.setViewVisibility(view, b);
  }
  
  protected abstract int getLayoutId();
  
  protected abstract int getRootId();
  
  public void initView(Context paramContext, ViewGroup paramViewGroup) {
    if (paramContext != null) {
      if (paramViewGroup == null)
        return; 
      LayoutInflater.from(paramContext).inflate(getLayoutId(), paramViewGroup, true);
      this.mRootView = paramViewGroup.findViewById(getRootId());
    } 
  }
  
  public void reset() {}
  
  public void setVisible(boolean paramBoolean) {
    this.mVisible = paramBoolean;
    updateVisibility();
  }
  
  public void showToolBar(boolean paramBoolean) {
    this.mShow = paramBoolean;
    updateVisibility();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\toolbar\BaseVideoToolbar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */