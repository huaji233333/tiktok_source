package com.tt.option.u;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tt.miniapp.msg.ApiChooseAddressCtrl;

public interface b {
  boolean handleActivityChooseAddressResult(int paramInt1, int paramInt2, Intent paramIntent, ApiChooseAddressCtrl.ChooseAddressListener paramChooseAddressListener);
  
  boolean handleAppbrandDisablePage(Context paramContext, String paramString);
  
  boolean interceptOpenWebUrl(Context paramContext, String paramString);
  
  void jumpToWebView(Context paramContext, String paramString1, String paramString2, boolean paramBoolean);
  
  boolean navigateToVideoView(Activity paramActivity, String paramString);
  
  boolean openChooseAddressActivity(Activity paramActivity, int paramInt, String paramString);
  
  boolean openCustomerService(Context paramContext, String paramString);
  
  boolean openDocument(Activity paramActivity, String paramString1, String paramString2);
  
  boolean openProfile(Activity paramActivity, String paramString);
  
  boolean openSchema(Context paramContext, String paramString);
  
  boolean openSchema(Context paramContext, String paramString1, String paramString2);
  
  boolean openWebBrowser(Context paramContext, String paramString, boolean paramBoolean);
  
  void overridePendingTransition(Activity paramActivity, int paramInt1, int paramInt2, int paramInt3);
  
  boolean startMiniAppActivity(Context paramContext, Intent paramIntent);
  
  boolean startMiniAppService(Context paramContext, Intent paramIntent);
  
  boolean supportChooseAddress();
  
  boolean supportCustomerService();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\optio\\u\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */