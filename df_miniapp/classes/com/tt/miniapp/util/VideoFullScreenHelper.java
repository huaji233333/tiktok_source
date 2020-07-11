package com.tt.miniapp.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebChromeClient;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.TTAppbrandTabUI;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.view.FullScreenVideoLayout;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.miniapphost.util.UIUtils;

public class VideoFullScreenHelper {
  private WebChromeClient.CustomViewCallback mCallback;
  
  private String mComponentId;
  
  private View mCustomerView;
  
  private FullScreenVideoLayout mFullScreenLayout;
  
  private ScreenDirection mScreenDirection = ScreenDirection.LANDSCAPE;
  
  private FullScreenVideoLayout getFullScreenLayout(Activity paramActivity) {
    if (paramActivity == null)
      return null; 
    if (this.mFullScreenLayout == null) {
      ViewStub viewStub = (ViewStub)paramActivity.findViewById(2097545456);
      if (viewStub != null)
        viewStub.inflate(); 
      this.mFullScreenLayout = (FullScreenVideoLayout)paramActivity.findViewById(2097545435);
    } 
    return this.mFullScreenLayout;
  }
  
  private void publishFullScreenEvent(int paramInt, String paramString, boolean paramBoolean) {
    if (TextUtils.isEmpty(paramString))
      return; 
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager != null)
      webViewManager.publish(paramInt, "onVideoFullScreenChange", (new JsonBuilder()).put("fullScreen", Boolean.valueOf(paramBoolean)).put("id", paramString).build().toString()); 
  }
  
  private void updateScreenOrientation(Activity paramActivity, int paramInt) {
    UIUtils.setActivityOrientation(paramActivity, paramInt);
    if (paramActivity instanceof MiniappHostBase) {
      IActivityProxy iActivityProxy = ((MiniappHostBase)paramActivity).getActivityProxy();
      if (iActivityProxy instanceof TTAppbrandTabUI)
        ((TTAppbrandTabUI)iActivityProxy).setSavedScreenOrientation(paramInt); 
    } 
  }
  
  public void enterFullScreen(Activity paramActivity, View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
    enterFullScreen(paramActivity, paramView, paramCustomViewCallback, -1);
  }
  
  public void enterFullScreen(Activity paramActivity, View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback, int paramInt) {
    boolean bool;
    if (this.mCustomerView != null) {
      WebChromeClient.CustomViewCallback customViewCallback = this.mCallback;
      if (customViewCallback != null) {
        customViewCallback.onCustomViewHidden();
        return;
      } 
    } 
    this.mCallback = paramCustomViewCallback;
    this.mCustomerView = paramView;
    if (this.mScreenDirection == ScreenDirection.PORTRAIT) {
      bool = true;
    } else if (this.mScreenDirection == ScreenDirection.REVERSE_LANDSCAPE) {
      bool = true;
    } else {
      bool = false;
    } 
    updateScreenOrientation(paramActivity, bool);
    getFullScreenLayout(paramActivity).enterFullScreen(paramView);
    if (paramInt >= 0)
      publishFullScreenEvent(paramInt, this.mComponentId, true); 
    VideoUtils.showOrHideNaviBar(paramView, false);
  }
  
  public void exitFullScreen(Activity paramActivity) {
    exitFullScreen(paramActivity, -1);
  }
  
  public void exitFullScreen(Activity paramActivity, int paramInt) {
    View view = this.mCustomerView;
    if (view == null)
      return; 
    VideoUtils.showOrHideNaviBar(view, true);
    updateScreenOrientation(paramActivity, 1);
    getFullScreenLayout(paramActivity).exitFullScreen();
    if (paramInt >= 0)
      publishFullScreenEvent(paramInt, this.mComponentId, false); 
    this.mScreenDirection = ScreenDirection.LANDSCAPE;
    this.mComponentId = "";
    this.mCustomerView = null;
    this.mCallback = null;
  }
  
  public boolean exitFullScreenManual() {
    WebChromeClient.CustomViewCallback customViewCallback = this.mCallback;
    if (customViewCallback != null) {
      customViewCallback.onCustomViewHidden();
      return true;
    } 
    return false;
  }
  
  public ScreenDirection getDirection() {
    return this.mScreenDirection;
  }
  
  public ScreenDirection getScreenDirection(int paramInt) {
    return (paramInt == -90) ? ScreenDirection.REVERSE_LANDSCAPE : ((paramInt == 90) ? ScreenDirection.LANDSCAPE : ScreenDirection.PORTRAIT);
  }
  
  public void setComponentId(String paramString) {
    this.mComponentId = paramString;
  }
  
  public void setDirection(ScreenDirection paramScreenDirection) {
    this.mScreenDirection = paramScreenDirection;
  }
  
  public enum ScreenDirection {
    LANDSCAPE, PORTRAIT, REVERSE_LANDSCAPE;
    
    static {
      $VALUES = new ScreenDirection[] { PORTRAIT, LANDSCAPE, REVERSE_LANDSCAPE };
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\VideoFullScreenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */