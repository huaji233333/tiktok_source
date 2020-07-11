package com.tt.miniapp.msg;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;
import java.util.HashMap;

public class ApiGetNetworkTypeCtrl extends b {
  public ApiGetNetworkTypeCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private Object getNewNetType(Application paramApplication) {
    NetworkInfo networkInfo = _lancet.com_ss_android_ugc_aweme_lancet_network_ConnecttivityManagerLancet_getActiveNetworkInfo((ConnectivityManager)paramApplication.getSystemService("connectivity"));
    if (networkInfo != null && networkInfo.isAvailable()) {
      int i = networkInfo.getType();
      return (i != 0) ? ((i != 1) ? "unknown" : "wifi") : NetUtil.getNetGeneration((Context)paramApplication);
    } 
    return "none";
  }
  
  public void act() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("networkType", getNewNetType(AppbrandContext.getInst().getApplicationContext()));
    callbackOk(a.a(hashMap));
  }
  
  public String getActionName() {
    return "getNetworkType";
  }
  
  class ApiGetNetworkTypeCtrl {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetNetworkTypeCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */