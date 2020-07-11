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

public class ApiGetFriendCloudStorageInfoCtrl extends b {
  public ApiGetFriendCloudStorageInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void getCloudStorage(final String aId, final String appId, final String session, JSONObject paramJSONObject) {
    final String keyList = paramJSONObject.optString("keyList");
    final String extra = paramJSONObject.optString("extra");
    Observable.create(new Function<String>() {
          public String fun() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(AppbrandConstant.OpenApi.getInst().getFRIEND_CLOUD_STORAGE_URL());
            stringBuilder.append("appid=");
            stringBuilder.append(appId);
            stringBuilder.append("&aid=");
            stringBuilder.append(aId);
            stringBuilder.append("&type=");
            stringBuilder.append(type);
            stringBuilder.append("&keyList=");
            stringBuilder.append(Uri.encode(keyList));
            stringBuilder.append("&session=");
            stringBuilder.append(session);
            String str2 = stringBuilder.toString();
            String str1 = str2;
            if (TextUtils.equals(type, "group")) {
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append(str2);
              stringBuilder1.append("&extra=");
              stringBuilder1.append(Uri.encode(extra));
              str1 = stringBuilder1.toString();
            } 
            AppBrandLogger.d("tma_ApiGetFriendCloudStorageInfoCtrl", new Object[] { "url ", str1 });
            return NetManager.getInst().request(str1).a();
          }
        }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public void onError(Throwable param1Throwable) {
            AppBrandLogger.e("tma_ApiGetFriendCloudStorageInfoCtrl", new Object[] { "onFail ", param1Throwable });
            ApiGetFriendCloudStorageInfoCtrl.this.callbackFail(param1Throwable);
          }
          
          public void onSuccess(String param1String) {
            if (!TextUtils.isEmpty(param1String))
              try {
                JSONObject jSONObject = new JSONObject(param1String);
                String str2 = jSONObject.optString("data");
                int i = jSONObject.optInt("error", -1);
                if (i == 0) {
                  jSONObject = new JSONObject(str2);
                  ApiGetFriendCloudStorageInfoCtrl.this.callbackOk(jSONObject);
                  AppBrandLogger.d("tma_ApiGetFriendCloudStorageInfoCtrl", new Object[] { "jsonObject ", jSONObject });
                  return;
                } 
                AppBrandLogger.e("tma_ApiGetFriendCloudStorageInfoCtrl", new Object[] { "errorCode == ", Integer.valueOf(i) });
                String str1 = jSONObject.optString("message");
                ApiGetFriendCloudStorageInfoCtrl.this.callbackFail(a.a("%s errorCode = %s", new Object[] { str1, Integer.valueOf(i) }));
                return;
              } catch (JSONException jSONException) {
                AppBrandLogger.e("tma_ApiGetFriendCloudStorageInfoCtrl", new Object[] { "friend cloud storage fail", jSONException });
                ApiGetFriendCloudStorageInfoCtrl.this.callbackFail((Throwable)jSONException);
                return;
              }  
            AppBrandLogger.e("tma_ApiGetFriendCloudStorageInfoCtrl", new Object[] { "request result is null" });
            ApiGetFriendCloudStorageInfoCtrl.this.callbackFail("requestResult is null");
          }
        });
  }
  
  public void act() {
    try {
      String str1;
      JSONObject jSONObject = new JSONObject(this.mArgs);
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
      if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str2)) {
        str1 = a.a("session = %s aId = %s appId = %s", new Object[] { str3, str1, str2 });
        AppBrandLogger.e("tma_ApiGetFriendCloudStorageInfoCtrl", new Object[] { str1 });
        JSONObject jSONObject1 = new JSONObject();
        try {
          jSONObject1.put("errMsg", str1);
          AppBrandMonitor.statusRate("mp_start_error", 2005, jSONObject1);
        } catch (JSONException jSONException) {
          AppBrandLogger.stacktrace(6, "tma_ApiGetFriendCloudStorageInfoCtrl", jSONException.getStackTrace());
        } 
        callbackFail(str1);
        return;
      } 
      getCloudStorage(str1, (String)jSONException, str3, jSONObject);
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      AppBrandLogger.stacktrace(6, "tma_ApiGetFriendCloudStorageInfoCtrl", exception.getStackTrace());
      return;
    } 
  }
  
  public String getActionName() {
    return "getCloudStorageByRelation";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetFriendCloudStorageInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */