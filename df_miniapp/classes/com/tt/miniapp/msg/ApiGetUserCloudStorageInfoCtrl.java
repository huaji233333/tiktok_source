package com.tt.miniapp.msg;

import android.net.Uri;
import android.text.TextUtils;
import com.a;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetUserCloudStorageInfoCtrl extends b {
  public ApiGetUserCloudStorageInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject2 = new JSONObject(this.mArgs);
      InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
      String str2 = "";
      if (initParamsEntity != null) {
        str1 = initParamsEntity.getAppId();
      } else {
        str1 = "";
      } 
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      if (appInfoEntity != null)
        str2 = appInfoEntity.appId; 
      String str3 = InnerHostProcessBridge.getPlatformSession(str2);
      if (!TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str1)) {
        String str = jSONObject2.optString("keyList");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppbrandConstant.OpenApi.getInst().getUSER_CLOUD_STORAGE_URL());
        stringBuilder.append("appid=");
        stringBuilder.append(str2);
        stringBuilder.append("&aid=");
        stringBuilder.append(str1);
        stringBuilder.append("&keyList=");
        stringBuilder.append(Uri.encode(str));
        stringBuilder.append("&session=");
        stringBuilder.append(str3);
        str1 = stringBuilder.toString();
        AppBrandLogger.d("tma_ApiGetUserCloudStorageInfoCtrl", new Object[] { "url ", str1 });
        Observable.create(new Function<String>() {
              public String fun() {
                return NetManager.getInst().request(url).a();
              }
            }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
              public void onError(Throwable param1Throwable) {
                AppBrandLogger.e("tma_ApiGetUserCloudStorageInfoCtrl", new Object[] { "onFail ", param1Throwable });
                ApiGetUserCloudStorageInfoCtrl.this.callbackFail(param1Throwable);
              }
              
              public void onSuccess(String param1String) {
                if (!TextUtils.isEmpty(param1String))
                  try {
                    JSONObject jSONObject = new JSONObject(param1String);
                    String str2 = jSONObject.optString("data");
                    int i = jSONObject.optInt("error", -1);
                    if (i == 0) {
                      jSONObject = new JSONObject(str2);
                      ApiGetUserCloudStorageInfoCtrl.this.callbackOk(jSONObject);
                      return;
                    } 
                    AppBrandLogger.e("tma_ApiGetUserCloudStorageInfoCtrl", new Object[] { "errorCode == ", Integer.valueOf(i) });
                    String str1 = jSONObject.optString("message");
                    ApiGetUserCloudStorageInfoCtrl.this.callbackFail(a.a("%s errorCode = %s", new Object[] { str1, Integer.valueOf(i) }));
                    return;
                  } catch (JSONException jSONException) {
                    AppBrandLogger.e("tma_ApiGetUserCloudStorageInfoCtrl", new Object[] { "user cloud storage parse json fail", jSONException });
                    ApiGetUserCloudStorageInfoCtrl.this.callbackFail((Throwable)jSONException);
                    return;
                  }  
                AppBrandLogger.e("tma_ApiGetUserCloudStorageInfoCtrl", new Object[] { "request result is null" });
                ApiGetUserCloudStorageInfoCtrl.this.callbackFail("requestResult is null");
              }
            });
        return;
      } 
      final String url = a.a("session = %s aId = %s appId = %s", new Object[] { str3, str1, str2 });
      callbackFail(str1);
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("errMsg", str1);
      AppBrandMonitor.statusRate("mp_start_error", 2006, jSONObject1);
      AppBrandLogger.e("tma_ApiGetUserCloudStorageInfoCtrl", new Object[] { str1 });
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_ApiGetUserCloudStorageInfoCtrl", exception.getStackTrace());
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "getUserCloudStorage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetUserCloudStorageInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */