package com.tt.miniapp;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import com.tt.miniapp.util.DevicesUtil;

public class ImmersedStatusBarHelper {
  private static boolean sIsEnable = true;
  
  private static boolean sIsInit;
  
  private static boolean sOreoStatusBarDrawBackground;
  
  private Activity mActivity;
  
  private View mFakeStatusBar;
  
  private boolean mFitsSystemWindows;
  
  private View mRootView;
  
  private boolean mSelfEnable;
  
  private int mStatusBarColor;
  
  private int mStatusBarPaddingTop;
  
  public ImmersedStatusBarHelper(Activity paramActivity, ImmersedStatusBarConfig paramImmersedStatusBarConfig) {
    this.mActivity = paramActivity;
    this.mStatusBarColor = paramImmersedStatusBarConfig.mStatusBarColor;
    this.mFitsSystemWindows = paramImmersedStatusBarConfig.mFitsSystemWindows;
    this.mSelfEnable = paramImmersedStatusBarConfig.mEnable;
  }
  
  private static View createStatusView(Activity paramActivity, int paramInt) {
    int i = DevicesUtil.getStatusBarHeight((Context)paramActivity);
    View view = new View((Context)paramActivity);
    view.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, i));
    view.setBackgroundColor(paramInt);
    return view;
  }
  
  @Deprecated
  public static boolean isEnabled() {
    return isGlobalEnabled();
  }
  
  private boolean isGlobalAndSelfEnabled() {
    return (this.mSelfEnable && isGlobalEnabled());
  }
  
  public static boolean isGlobalEnabled() {
    return (sIsEnable && Build.VERSION.SDK_INT >= 21);
  }
  
  public static void setUseLightStatusBar(Window paramWindow, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 23 && sIsEnable) {
      int i = paramWindow.getDecorView().getSystemUiVisibility();
      View view = paramWindow.getDecorView();
      if (paramBoolean) {
        i |= 0x2000;
      } else {
        i &= 0xFFFFDFFF;
      } 
      view.setSystemUiVisibility(i);
      if (DevicesUtil.isMiui())
        DevicesUtil.setMiuiStatusBarDarkMode(paramBoolean, paramWindow); 
    } 
  }
  
  public void setColor(int paramInt) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mFakeStatusBar.setBackgroundColor(paramInt); 
  }
  
  public void setUseLightStatusBarInternal(boolean paramBoolean) {
    setUseLightStatusBar(this.mActivity.getWindow(), paramBoolean);
  }
  
  public void setup(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 21) {
      Window window = this.mActivity.getWindow();
      window.clearFlags(67108864);
      window.getDecorView().setSystemUiVisibility(1280);
      window.addFlags(-2147483648);
      window.setStatusBarColor(0);
    } else if (Build.VERSION.SDK_INT >= 19) {
      this.mActivity.getWindow().addFlags(67108864);
    } 
    this.mFakeStatusBar = createStatusView(this.mActivity, this.mStatusBarColor);
    if (!paramBoolean) {
      ((ViewGroup)this.mActivity.getWindow().getDecorView()).addView(this.mFakeStatusBar);
      this.mRootView = ((ViewGroup)this.mActivity.findViewById(16908290)).getChildAt(0);
      this.mStatusBarPaddingTop = this.mRootView.getPaddingTop();
      statusBarToImmersed();
    } 
  }
  
  public void statusBarToImmersed() {
    View view = this.mRootView;
    if (view == null)
      return; 
    view.setPadding(view.getPaddingLeft(), this.mStatusBarPaddingTop + DevicesUtil.getStatusBarHeight((Context)this.mActivity), this.mRootView.getPaddingRight(), this.mRootView.getPaddingBottom());
    this.mFakeStatusBar.setVisibility(0);
  }
  
  public void statusBarToUnImmersed() {
    View view = this.mRootView;
    if (view == null)
      return; 
    view.setPadding(view.getPaddingLeft(), this.mStatusBarPaddingTop, this.mRootView.getPaddingRight(), this.mRootView.getPaddingBottom());
    this.mFakeStatusBar.setVisibility(8);
  }
  
  public static class ImmersedStatusBarConfig {
    public boolean mEnable = true;
    
    public boolean mFitsSystemWindows = true;
    
    public int mStatusBarColor = -1;
    
    public ImmersedStatusBarConfig setEnable(boolean param1Boolean) {
      this.mEnable = param1Boolean;
      return this;
    }
    
    public ImmersedStatusBarConfig setFitsSystemWindows(boolean param1Boolean) {
      this.mFitsSystemWindows = param1Boolean;
      return this;
    }
    
    public ImmersedStatusBarConfig setStatusBarColor(int param1Int) {
      this.mStatusBarColor = param1Int;
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ImmersedStatusBarHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */