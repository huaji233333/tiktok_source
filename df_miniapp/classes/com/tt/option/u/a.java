package com.tt.option.u;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.ss.android.ugc.aweme.push.downgrade.d;
import com.tt.miniapp.impl.HostOptionRouterDependImpl;
import com.tt.miniapp.msg.ApiChooseAddressCtrl;
import com.tt.option.a;

public class a extends a<b> implements b {
  public boolean handleActivityChooseAddressResult(int paramInt1, int paramInt2, Intent paramIntent, ApiChooseAddressCtrl.ChooseAddressListener paramChooseAddressListener) {
    return inject() ? ((b)this.defaultOptionDepend).handleActivityChooseAddressResult(paramInt1, paramInt2, paramIntent, paramChooseAddressListener) : false;
  }
  
  public boolean handleAppbrandDisablePage(Context paramContext, String paramString) {
    return inject() ? ((b)this.defaultOptionDepend).handleAppbrandDisablePage(paramContext, paramString) : false;
  }
  
  public boolean interceptOpenWebUrl(Context paramContext, String paramString) {
    return false;
  }
  
  public void jumpToWebView(Context paramContext, String paramString1, String paramString2, boolean paramBoolean) {
    if (inject())
      ((b)this.defaultOptionDepend).jumpToWebView(paramContext, paramString1, paramString2, paramBoolean); 
  }
  
  public boolean navigateToVideoView(Activity paramActivity, String paramString) {
    return inject() ? ((b)this.defaultOptionDepend).navigateToVideoView(paramActivity, paramString) : false;
  }
  
  public boolean openChooseAddressActivity(Activity paramActivity, int paramInt, String paramString) {
    return inject() ? ((b)this.defaultOptionDepend).openChooseAddressActivity(paramActivity, paramInt, paramString) : false;
  }
  
  public boolean openCustomerService(Context paramContext, String paramString) {
    return inject() ? ((b)this.defaultOptionDepend).openCustomerService(paramContext, paramString) : false;
  }
  
  public boolean openDocument(Activity paramActivity, String paramString1, String paramString2) {
    return inject() ? ((b)this.defaultOptionDepend).openDocument(paramActivity, paramString1, paramString2) : false;
  }
  
  public boolean openProfile(Activity paramActivity, String paramString) {
    return inject() ? ((b)this.defaultOptionDepend).openProfile(paramActivity, paramString) : false;
  }
  
  public boolean openSchema(Context paramContext, String paramString) {
    return inject() ? ((b)this.defaultOptionDepend).openSchema(paramContext, paramString) : false;
  }
  
  public boolean openSchema(Context paramContext, String paramString1, String paramString2) {
    return inject() ? ((b)this.defaultOptionDepend).openSchema(paramContext, paramString1, paramString2) : false;
  }
  
  public boolean openWebBrowser(Context paramContext, String paramString, boolean paramBoolean) {
    return inject() ? ((b)this.defaultOptionDepend).openWebBrowser(paramContext, paramString, paramBoolean) : false;
  }
  
  public void overridePendingTransition(Activity paramActivity, int paramInt1, int paramInt2, int paramInt3) {
    if (inject())
      ((b)this.defaultOptionDepend).overridePendingTransition(paramActivity, paramInt1, paramInt2, paramInt3); 
    paramActivity.overridePendingTransition(paramInt1, paramInt2);
  }
  
  public boolean startMiniAppActivity(Context paramContext, Intent paramIntent) {
    if (inject())
      return ((b)this.defaultOptionDepend).startMiniAppActivity(paramContext, paramIntent); 
    paramContext.startActivity(paramIntent);
    return true;
  }
  
  public boolean startMiniAppService(Context paramContext, Intent paramIntent) {
    if (inject())
      return ((b)this.defaultOptionDepend).startMiniAppService(paramContext, paramIntent); 
    if (paramContext == null || !(paramContext instanceof Context) || !d.a(paramContext, paramIntent))
      paramContext.startService(paramIntent); 
    return true;
  }
  
  public boolean supportChooseAddress() {
    return inject() ? ((b)this.defaultOptionDepend).supportChooseAddress() : false;
  }
  
  public boolean supportCustomerService() {
    return inject() ? ((b)this.defaultOptionDepend).supportCustomerService() : false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\optio\\u\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */