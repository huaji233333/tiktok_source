package com.tt.miniapp.msg;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
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
import com.tt.option.q.i;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiSetUserCloudStorageInfoCtrl extends b {
  public ApiSetUserCloudStorageInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      String str3 = (new JSONObject(this.mArgs)).optString("KVDataList");
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
      String str4 = InnerHostProcessBridge.getPlatformSession(str2);
      if (!TextUtils.isEmpty(str4) && !TextUtils.isEmpty(str1) && !TextUtils.isEmpty(str2)) {
        final i tmaPostRequest = new i(AppbrandConstant.OpenApi.getInst().getSAVE_CLOUD_STORAGE_URL(), "POST");
        i.a("aid", str1);
        i.a("appid", str2);
        i.a("KVDataList", Uri.encode(str3));
        i.a("session", str4);
        Observable.create(new Function<String>() {
              public String fun() {
                String str = NetManager.getInst().request(tmaPostRequest).a();
                AppBrandLogger.d("tma_ApiSetUserCloudStorageInfoCtrl", new Object[] { "requestResult = ", str });
                return str;
              }
            }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
              public void onError(Throwable param1Throwable) {
                AppBrandLogger.e("tma_ApiSetUserCloudStorageInfoCtrl", new Object[] { Log.getStackTraceString(param1Throwable) });
                ApiSetUserCloudStorageInfoCtrl.this.callbackFail(param1Throwable);
              }
              
              public void onSuccess(String param1String) {
                try {
                  if (!TextUtils.isEmpty(param1String)) {
                    JSONObject jSONObject = new JSONObject(param1String);
                    int i = jSONObject.optInt("error", -1);
                    if (i == 0) {
                      ApiSetUserCloudStorageInfoCtrl.this.callbackOk();
                      return;
                    } 
                    String str = jSONObject.optString("message");
                    ApiSetUserCloudStorageInfoCtrl.this.callbackFail(a.a("%s errorCode = %s", new Object[] { str, Integer.valueOf(i) }));
                    return;
                  } 
                  ApiSetUserCloudStorageInfoCtrl.this.callbackFail("requestResult is null");
                  return;
                } catch (JSONException jSONException) {
                  AppBrandLogger.e("tma_ApiSetUserCloudStorageInfoCtrl", new Object[] { "json exception ", jSONException });
                  ApiSetUserCloudStorageInfoCtrl.this.callbackFail((Throwable)jSONException);
                  return;
                } 
              }
            });
        return;
      } 
      String str1 = a.a("session = %s aId = %s appId = %s", new Object[] { str4, str1, str2 });
      callbackFail(str1);
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("errMsg", str1);
      AppBrandMonitor.statusRate("mp_start_error", 2007, jSONObject);
      AppBrandLogger.e("tma_ApiSetUserCloudStorageInfoCtrl", new Object[] { str1 });
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_ApiSetUserCloudStorageInfoCtrl", exception.getStackTrace());
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "setUserCloudStorage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiSetUserCloudStorageInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */