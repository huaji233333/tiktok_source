package com.tt.miniapp.video.plugin.feature.toolbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.UIUtils;

public class TopToolbarLayout extends BaseVideoToolbar implements View.OnClickListener {
  private ImageView mFullScreenBackBtn;
  
  private boolean mIsFullScreen;
  
  private TopBarUIListener mUIListener;
  
  private void updateUIStatus() {
    if (this.mIsFullScreen) {
      UIUtils.setViewVisibility((View)this.mFullScreenBackBtn, 0);
      return;
    } 
    UIUtils.setViewVisibility((View)this.mFullScreenBackBtn, 4);
  }
  
  protected int getLayoutId() {
    return 2097676339;
  }
  
  protected int getRootId() {
    return 2097545455;
  }
  
  public void initView(Context paramContext, ViewGroup paramViewGroup) {
    super.initView(paramContext, paramViewGroup);
    if (this.mRootView == null)
      return; 
    this.mFullScreenBackBtn = (ImageView)this.mRootView.findViewById(2097545437);
    this.mFullScreenBackBtn.setOnClickListener(this);
    updateUIStatus();
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2097545437) {
      InputMethodUtil.hideSoftKeyboard((Activity)AppbrandContext.getInst().getCurrentActivity());
      TopBarUIListener topBarUIListener = this.mUIListener;
      if (topBarUIListener != null)
        topBarUIListener.onFullScreenBackClick(); 
    } 
  }
  
  public void setFullScreen(boolean paramBoolean) {
    this.mIsFullScreen = paramBoolean;
    updateUIStatus();
  }
  
  public void setUIListener(TopBarUIListener paramTopBarUIListener) {
    this.mUIListener = paramTopBarUIListener;
  }
  
  public static interface TopBarUIListener {
    void onFullScreenBackClick();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\toolbar\TopToolbarLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */