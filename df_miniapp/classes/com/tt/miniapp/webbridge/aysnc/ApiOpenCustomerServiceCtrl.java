package com.tt.miniapp.webbridge.aysnc;

import android.content.Intent;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.customer.service.CustomerServiceManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import java.util.HashMap;

public class ApiOpenCustomerServiceCtrl extends b {
  private OpenCustomerServiceListener mListener;
  
  public boolean mTriggeredHostClientLogin;
  
  public ApiOpenCustomerServiceCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (!HostDependManager.getInst().supportCustomerService()) {
      callbackFail("feature is not supported in app");
      return;
    } 
    boolean bool = (UserInfoManager.getHostClientUserInfo()).isLogin;
    this.mListener = new OpenCustomerServiceListener();
    if (bool) {
      CustomerServiceManager.getInstance().openCustomerService(this.mListener);
      return;
    } 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("key_customer_service_login_flag", "");
    UserInfoManager.requestLoginHostClient(this.mListener, hashMap, null);
  }
  
  public String getActionName() {
    return "openCustomerService";
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return this.mTriggeredHostClientLogin ? UserInfoManager.handleHostClientLoginResult(paramInt1, paramInt2, paramIntent, this.mListener) : false;
  }
  
  public boolean shouldHandleActivityResult() {
    return true;
  }
  
  class OpenCustomerServiceListener implements CustomerServiceManager.Callback, UserInfoManager.HostClientLoginListener {
    private OpenCustomerServiceListener() {}
    
    public void onLoginFail() {
      ApiOpenCustomerServiceCtrl.this.unRegesterResultHandler();
      AppBrandLogger.d("ApiOpenCustomerServiceCtrl", new Object[] { "onLoginFail" });
      ApiOpenCustomerServiceCtrl.this.callbackFail("login failed");
    }
    
    public void onLoginSuccess() {
      ApiOpenCustomerServiceCtrl.this.unRegesterResultHandler();
      CustomerServiceManager.getInstance().openCustomerService(this);
    }
    
    public void onLoginUnSupport() {
      ApiOpenCustomerServiceCtrl.this.unRegesterResultHandler();
      AppBrandLogger.d("ApiOpenCustomerServiceCtrl", new Object[] { "onLoginUnSupport" });
      ApiOpenCustomerServiceCtrl.this.callbackFail("login is not supported in app");
    }
    
    public void onLoginWhenBackground() {
      ApiOpenCustomerServiceCtrl.this.unRegesterResultHandler();
      AppBrandLogger.d("ApiOpenCustomerServiceCtrl", new Object[] { "onLoginWhenBackground" });
      ApiOpenCustomerServiceCtrl.this.callbackFail("login fail background");
    }
    
    public void onOpenCustomerServiceFail(String param1String) {
      AppBrandLogger.d("ApiOpenCustomerServiceCtrl", new Object[] { "OpenCustomerServiceFail" });
      ApiOpenCustomerServiceCtrl.this.callbackFail(param1String);
    }
    
    public void onOpenCustomerServiceSuccess() {
      AppBrandLogger.d("ApiOpenCustomerServiceCtrl", new Object[] { "OpenCustomerServiceSuccess" });
      ApiOpenCustomerServiceCtrl.this.callbackOk();
    }
    
    public void onTriggerHostClientLogin(String param1String) {
      ApiOpenCustomerServiceCtrl.this.mTriggeredHostClientLogin = true;
      AppBrandLogger.d("ApiOpenCustomerServiceCtrl", new Object[] { "onTriggerHostClientLogin" });
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\aysnc\ApiOpenCustomerServiceCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */