package com.tt.miniapp.base.ui.viewwindow;

import android.app.Activity;
import android.content.Intent;

public interface IViewWindow {
  Activity getActivity();
  
  Intent getIntent();
  
  ViewWindowRoot getRoot();
  
  void onActivityDestroy();
  
  void onActivityPause();
  
  void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent);
  
  void onActivityResume();
  
  boolean onBackPressed();
  
  void onCreate();
  
  void onDestroy();
  
  void onThemeChanged(String paramString);
  
  void onViewPause(int paramInt);
  
  void onViewResume(int paramInt);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\bas\\ui\viewwindow\IViewWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */