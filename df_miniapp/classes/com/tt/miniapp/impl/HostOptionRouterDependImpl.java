package com.tt.miniapp.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tt.miniapp.msg.ApiChooseAddressCtrl;
import com.tt.option.u.b;

public class HostOptionRouterDependImpl implements b {
  public boolean handleActivityChooseAddressResult(int paramInt1, int paramInt2, Intent paramIntent, ApiChooseAddressCtrl.ChooseAddressListener paramChooseAddressListener) {
    return false;
  }
  
  public boolean handleAppbrandDisablePage(Context paramContext, String paramString) {
    return false;
  }
  
  public boolean interceptOpenWebUrl(Context paramContext, String paramString) {
    return false;
  }
  
  public void jumpToWebView(Context paramContext, String paramString1, String paramString2, boolean paramBoolean) {}
  
  public boolean navigateToVideoView(Activity paramActivity, String paramString) {
    return false;
  }
  
  public boolean openChooseAddressActivity(Activity paramActivity, int paramInt, String paramString) {
    return false;
  }
  
  public boolean openCustomerService(Context paramContext, String paramString) {
    return false;
  }
  
  public boolean openDocument(Activity paramActivity, String paramString1, String paramString2) {
    return false;
  }
  
  public boolean openProfile(Activity paramActivity, String paramString) {
    return false;
  }
  
  public boolean openSchema(Context paramContext, String paramString) {
    return false;
  }
  
  public boolean openSchema(Context paramContext, String paramString1, String paramString2) {
    return false;
  }
  
  public boolean openWebBrowser(Context paramContext, String paramString, boolean paramBoolean) {
    return false;
  }
  
  public void overridePendingTransition(Activity paramActivity, int paramInt1, int paramInt2, int paramInt3) {
    paramActivity.overridePendingTransition(paramInt1, paramInt2);
  }
  
  public boolean startMiniAppActivity(Context paramContext, Intent paramIntent) {
    paramContext.startActivity(paramIntent);
    return true;
  }
  
  public boolean startMiniAppService(Context paramContext, Intent paramIntent) {
    _lancet.com_ss_android_ugc_aweme_push_downgrade_StartServiceLancet_startService(paramContext, paramIntent);
    return true;
  }
  
  public boolean supportChooseAddress() {
    return false;
  }
  
  public boolean supportCustomerService() {
    return false;
  }
  
  class HostOptionRouterDependImpl {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostOptionRouterDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */