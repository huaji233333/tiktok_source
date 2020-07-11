package com.tt.miniapphost;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.tt.frontendapiinterface.d;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.favorite.FavoriteGuideModel;
import com.tt.miniapp.titlebar.ITitleBar;
import com.tt.option.ad.h;

public interface IActivityProxy {
  void afterOnCreate(Bundle paramBundle);
  
  boolean beforeOnCreate(Bundle paramBundle);
  
  void dismissFavoriteGuide(int paramInt);
  
  View findViewById(int paramInt);
  
  long getLaunchDuration();
  
  ITitleBar getTitleBar();
  
  void hideAnchorButton();
  
  boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent);
  
  void onBackPressed();
  
  void onCreate(Bundle paramBundle);
  
  boolean onCreateBannerView(h paramh);
  
  boolean onCreateVideoAd(h paramh);
  
  void onDestroy();
  
  boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent);
  
  void onMemoryWarning(int paramInt);
  
  void onNewIntent(Intent paramIntent);
  
  boolean onOperateBannerView(h paramh);
  
  String onOperateInterstitialAd(h paramh);
  
  boolean onOperateVideoAd(h paramh);
  
  void onPause();
  
  void onPostCreate(Bundle paramBundle);
  
  void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint);
  
  void onResume();
  
  void onStartActivityForResult(Intent paramIntent, int paramInt);
  
  void onStop();
  
  boolean onUpdateBannerView(h paramh);
  
  void onUserInteraction();
  
  void onWindowFocusChanged(boolean paramBoolean);
  
  void overrideActivityExitAnimation();
  
  void setActivityResultHandler(IActivityResultHandler paramIActivityResultHandler);
  
  d showFavoriteGuide(FavoriteGuideModel paramFavoriteGuideModel);
  
  boolean showLoadFailMessage(String paramString, boolean paramBoolean);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\IActivityProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */