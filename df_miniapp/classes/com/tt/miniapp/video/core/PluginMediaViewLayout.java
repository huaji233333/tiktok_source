package com.tt.miniapp.video.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.tt.miniapp.video.view.CoreVideoView;

public class PluginMediaViewLayout extends FrameLayout {
  private CoreVideoView mCoreVideoView;
  
  private RelativeLayout mPluginMainContainer;
  
  public PluginMediaViewLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public PluginMediaViewLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PluginMediaViewLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    LayoutInflater.from(paramContext).inflate(2097676355, (ViewGroup)this);
    initViews();
  }
  
  public ViewGroup getPluginMainContainer() {
    return (ViewGroup)this.mPluginMainContainer;
  }
  
  public CoreVideoView getVideoView() {
    return this.mCoreVideoView;
  }
  
  protected void initViews() {
    this.mCoreVideoView = (CoreVideoView)findViewById(2097545442);
    this.mPluginMainContainer = (RelativeLayout)findViewById(2097545445);
  }
  
  public boolean isVisible() {
    return (getVisibility() == 0);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\core\PluginMediaViewLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */