package com.tt.miniapp.customer.service;

import android.content.Context;
import android.text.TextUtils;
import com.a;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.q.d;
import com.tt.option.q.i;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomerServiceManager {
  private boolean mCacheEnable = true;
  
  public String mCachedUrl;
  
  private Callback mCallback;
  
  private CustomerServiceManager() {}
  
  private boolean cacheEnable() {
    return (this.mCacheEnable && !TextUtils.isEmpty(this.mCachedUrl));
  }
  
  private void callbackSuccess() {
    Callback callback = this.mCallback;
    if (callback != null)
      callback.onOpenCustomerServiceSuccess(); 
  }
  
  public static CustomerServiceManager getInstance() {
    return Holder.INSTANCE;
  }
  
  private void requestCustomerServiceURL() {
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity == null || TextUtils.isEmpty(appInfoEntity.appId)) {
      callbackFail("app info is null");
      return;
    } 
    String str1 = appInfoEntity.appId;
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity == null || TextUtils.isEmpty(initParamsEntity.getAppId())) {
      callbackFail("host init param is null");
      return;
    } 
    String str2 = initParamsEntity.getAppId();
    String str3 = (UserInfoManager.getHostClientUserInfo()).userId;
    if (TextUtils.isEmpty(str3)) {
      callbackFail("get uid params error");
      return;
    } 
    String str4 = DevicesUtil.getPlatform();
    if (!d.a((Context)AppbrandContext.getInst().getApplicationContext())) {
      callbackFail("network unavailable");
      return;
    } 
    Observable.create(new Function<String>() {
          public String fun() {
            return NetManager.getInst().request(tmaRequest).a();
          }
        }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public void onError(Throwable param1Throwable) {
            AppBrandLogger.e("CustomerServiceManager", new Object[] { "requestCustomerServiceURL fail", param1Throwable });
            CustomerServiceManager.this.callbackFail(a.a(param1Throwable));
          }
          
          public void onSuccess(String param1String) {
            if (TextUtils.isEmpty(param1String)) {
              CustomerServiceManager.this.callbackFail("requestResult is null");
              return;
            } 
            AppBrandLogger.e("CustomerServiceManager", new Object[] { param1String });
            try {
              JSONObject jSONObject = new JSONObject(param1String);
              int i = jSONObject.getInt("error");
              if (i != 0) {
                str = jSONObject.optString("message");
                CustomerServiceManager.this.callbackFail(a.a("%s errorCode = %s", new Object[] { str, Integer.valueOf(i) }));
                return;
              } 
              String str = str.optString("data");
              AppBrandLogger.e("CustomerServiceManager", new Object[] { "customerServiceURL", str });
              if (TextUtils.isEmpty(str)) {
                CustomerServiceManager.this.callbackFail("service return empty url");
                return;
              } 
              CustomerServiceManager.this.mCachedUrl = str;
              CustomerServiceManager.this.openCustomerServicePage(str);
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.e("CustomerServiceManager", new Object[] { jSONException });
              CustomerServiceManager.this.callbackFail("response data error");
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("CustomerServiceManager", new Object[] { exception });
              CustomerServiceManager.this.callbackFail(a.a(exception));
              return;
            } 
          }
        });
  }
  
  public void callbackFail(String paramString) {
    Callback callback = this.mCallback;
    if (callback != null)
      callback.onOpenCustomerServiceFail(paramString); 
  }
  
  public void openCustomerService(Callback paramCallback) {
    this.mCallback = paramCallback;
    if (cacheEnable()) {
      openCustomerServicePage(this.mCachedUrl);
      return;
    } 
    requestCustomerServiceURL();
  }
  
  public void openCustomerServicePage(String paramString) {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail("activity is null");
      return;
    } 
    if (HostDependManager.getInst().openCustomerService((Context)miniappHostBase, paramString)) {
      callbackSuccess();
      return;
    } 
    callbackFail("feature is not supported in app");
  }
  
  public static interface Callback {
    void onOpenCustomerServiceFail(String param1String);
    
    void onOpenCustomerServiceSuccess();
  }
  
  static class Holder {
    public static CustomerServiceManager INSTANCE = new CustomerServiceManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\customer\service\CustomerServiceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */