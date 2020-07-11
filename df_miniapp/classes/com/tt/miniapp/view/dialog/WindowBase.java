package com.tt.miniapp.view.dialog;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.tt.miniapphost.AppBrandLogger;

public abstract class WindowBase {
  protected Context mContext;
  
  private long mLastShowTime;
  
  private WindowManager.LayoutParams mLayoutParams;
  
  private boolean mShow;
  
  private View mView;
  
  private WindowManager mWindowManager;
  
  public WindowBase(Context paramContext) {
    this.mContext = paramContext;
    this.mWindowManager = (WindowManager)this.mContext.getSystemService("window");
    this.mLayoutParams = initLayoutParams();
    if (this.mLayoutParams != null)
      return; 
    throw new NullPointerException("initLayoutParams() can't return null");
  }
  
  private boolean checkInterval() {
    long l = System.currentTimeMillis();
    if (l - this.mLastShowTime < 20L)
      return true; 
    this.mLastShowTime = l;
    return false;
  }
  
  public WindowManager.LayoutParams getLayoutParams() {
    return this.mLayoutParams;
  }
  
  public WindowManager getWindowManager() {
    return this.mWindowManager;
  }
  
  protected abstract WindowManager.LayoutParams initLayoutParams();
  
  boolean isShowing() {
    return this.mShow;
  }
  
  public void remove() {
    if (this.mShow) {
      if (checkInterval())
        return; 
      WindowManager windowManager = this.mWindowManager;
      if (windowManager != null) {
        View view = this.mView;
        if (view != null)
          try {
            windowManager.removeViewImmediate(view);
            this.mShow = false;
            return;
          } catch (Exception exception) {
            AppBrandLogger.stacktrace(6, "WindowBase", exception.getStackTrace());
          }  
      } 
    } 
  }
  
  public void show(View paramView, int paramInt1, int paramInt2, IBinder paramIBinder) {
    if (!this.mShow) {
      if (checkInterval())
        return; 
      this.mView = paramView;
      if (this.mWindowManager != null && this.mView != null) {
        if (paramIBinder != null) {
          try {
            this.mLayoutParams.token = paramIBinder;
            this.mLayoutParams.x = paramInt1;
            this.mLayoutParams.y = paramInt2;
            this.mWindowManager.addView(this.mView, (ViewGroup.LayoutParams)this.mLayoutParams);
            this.mShow = true;
            return;
          } catch (Exception exception) {
            AppBrandLogger.stacktrace(6, "WindowBase", exception.getStackTrace());
          } 
          return;
        } 
      } else {
        return;
      } 
    } else {
      return;
    } 
    this.mLayoutParams.x = paramInt1;
    this.mLayoutParams.y = paramInt2;
    this.mWindowManager.addView(this.mView, (ViewGroup.LayoutParams)this.mLayoutParams);
    this.mShow = true;
  }
  
  public void show(View paramView, IBinder paramIBinder) {
    show(paramView, 0, 0, paramIBinder);
  }
  
  public void update(int paramInt1, int paramInt2) {
    if (this.mShow) {
      WindowManager windowManager = this.mWindowManager;
      if (windowManager != null) {
        View view = this.mView;
        if (view != null)
          try {
            this.mLayoutParams.x = paramInt1;
            this.mLayoutParams.y = paramInt2;
            windowManager.updateViewLayout(view, (ViewGroup.LayoutParams)this.mLayoutParams);
            return;
          } catch (Exception exception) {
            AppBrandLogger.stacktrace(6, "WindowBase", exception.getStackTrace());
          }  
      } 
    } 
  }
  
  public void update(WindowManager.LayoutParams paramLayoutParams) {
    if (this.mShow) {
      WindowManager windowManager = this.mWindowManager;
      if (windowManager != null) {
        View view = this.mView;
        if (view != null)
          try {
            this.mLayoutParams = paramLayoutParams;
            windowManager.updateViewLayout(view, (ViewGroup.LayoutParams)this.mLayoutParams);
            return;
          } catch (Exception exception) {
            AppBrandLogger.stacktrace(6, "WindowBase", exception.getStackTrace());
          }  
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\dialog\WindowBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */