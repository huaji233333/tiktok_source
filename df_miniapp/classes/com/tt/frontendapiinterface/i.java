package com.tt.frontendapiinterface;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.option.n.c;

public interface i {
  Activity getCurrentActivity();
  
  c getFileChooseHandler();
  
  NativeNestWebView getNativeNestWebView();
  
  NativeViewManager getNativeViewManager();
  
  int getRenderHeight();
  
  int getRenderWidth();
  
  View getRootView();
  
  int getTitleBarHeight();
  
  WebView getWebView();
  
  int getWebViewId();
  
  void hideNavigationBarHomeButton();
  
  void onNativeWebViewPageFinished(boolean paramBoolean);
  
  void onStartPullDownRefresh();
  
  void onStopPullDownRefresh();
  
  void setLoadAsWebView();
  
  void setNavigationBarColor(String paramString1, String paramString2);
  
  void setNavigationBarLoading(boolean paramBoolean);
  
  void setNavigationBarTitle(String paramString);
  
  void showKeyboard(int paramInt);
  
  void updateWebTitle(String paramString, boolean paramBoolean);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\frontendapiinterface\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */