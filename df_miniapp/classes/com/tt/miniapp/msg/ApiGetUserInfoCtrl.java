package com.tt.miniapp.msg;

import android.app.Activity;
import android.text.TextUtils;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.option.e.e;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetUserInfoCtrl extends b {
  private long mStartTime;
  
  public ApiGetUserInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void getUserInfo(final Activity activity) {
    try {
      final boolean withCredentials = (new JSONObject(this.mArgs)).optBoolean("withCredentials");
      final CrossProcessDataEntity finalUserInfo = HostProcessBridge.getUserInfo();
      if (crossProcessDataEntity != null) {
        UserInfoManager.UserInfo userInfo = new UserInfoManager.UserInfo(crossProcessDataEntity);
      } else {
        crossProcessDataEntity = null;
      } 
      final String session = InnerHostProcessBridge.getPlatformSession((AppbrandApplication.getInst().getAppInfo()).appId);
      if (TextUtils.isEmpty(str)) {
        AppBrandLogger.e("tma_ApiGetUserInfoCtrl", new Object[] { "session is empty" });
        callbackFail("session is empty");
        return;
      } 
      if (!((UserInfoManager.UserInfo)crossProcessDataEntity).isLogin) {
        callbackFail("platform auth deny");
        return;
      } 
      Observable.create(new Function<String>() {
            public String fun() {
              String str2;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(AppbrandConstant.OpenApi.getInst().getUSERINFO_URL());
              stringBuilder.append((AppbrandApplication.getInst().getAppInfo()).appId);
              String str3 = stringBuilder.toString();
              if (AppbrandContext.getInst().getInitParams() != null) {
                str2 = AppbrandContext.getInst().getInitParams().getAppId();
              } else {
                str2 = "";
              } 
              String str1 = str3;
              if (!TextUtils.isEmpty(str2)) {
                AppBrandLogger.d("tma_ApiGetUserInfoCtrl", new Object[] { "aid = ", str2 });
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append(str3);
                stringBuilder1.append("&aid=");
                stringBuilder1.append(str2);
                str1 = stringBuilder1.toString();
              } 
              if (withCredentials) {
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append(str1);
                stringBuilder1.append("&withCredentials=true&session=");
                stringBuilder1.append(session);
                str1 = stringBuilder1.toString();
              } else {
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append(str1);
                stringBuilder1.append("&session=");
                stringBuilder1.append(session);
                str1 = stringBuilder1.toString();
              } 
              str1 = NetManager.getInst().request(str1).a();
              AppBrandLogger.d("tma_ApiGetUserInfoCtrl", new Object[] { str1 });
              return str1;
            }
          }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
            public void onError(Throwable param1Throwable) {
              ApiGetUserInfoCtrl.this.callbackFail("server error ");
              AppBrandLogger.e("tma_ApiGetUserInfoCtrl", new Object[] { param1Throwable });
            }
            
            public void onSuccess(String param1String) {
              final JSONObject finalResultJO;
              String str1;
              final boolean hasRequestPermission = TextUtils.isEmpty(param1String);
              String str2 = "";
              JSONObject jSONObject2 = null;
              if (bool) {
                AppBrandMonitor.statusRate("mp_start_error", 1021, null);
                param1String = "";
                jSONObject1 = null;
                str1 = str2;
              } else {
                try {
                  jSONObject1 = new JSONObject(param1String);
                  try {
                    int i = jSONObject1.optInt("error", -1);
                    if (i == 0) {
                      JSONObject jSONObject = jSONObject1.optJSONObject("data").optJSONObject("userInfo");
                      param1String = jSONObject.getString("nickName");
                      try {
                        str1 = jSONObject.getString("avatarUrl");
                        jSONObject2 = jSONObject;
                      } catch (JSONException jSONException) {
                        AppBrandLogger.stacktrace(6, "tma_ApiGetUserInfoCtrl", jSONException.getStackTrace());
                        str1 = str2;
                      } 
                    } else {
                      ApiGetUserInfoCtrl apiGetUserInfoCtrl = ApiGetUserInfoCtrl.this;
                      StringBuilder stringBuilder = new StringBuilder("server error ");
                      stringBuilder.append(i);
                      apiGetUserInfoCtrl.callbackFail(stringBuilder.toString());
                      return;
                    } 
                  } catch (JSONException jSONException) {
                    param1String = "";
                    AppBrandLogger.stacktrace(6, "tma_ApiGetUserInfoCtrl", jSONException.getStackTrace());
                    str1 = str2;
                  } 
                } catch (JSONException jSONException) {
                  param1String = "";
                  jSONObject1 = null;
                  AppBrandLogger.stacktrace(6, "tma_ApiGetUserInfoCtrl", jSONException.getStackTrace());
                  str1 = str2;
                } 
              } 
              if (jSONObject1 == null || jSONObject2 == null) {
                ApiGetUserInfoCtrl.this.callbackFail("server error ");
                return;
              } 
              HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
              hashMap.put("nickName", param1String);
              hashMap.put("avatarUrl", str1);
              bool = BrandPermissionUtils.hasRequestPermission(11);
              HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
              hashSet.add(BrandPermissionUtils.BrandPermission.USER_INFO);
              BrandPermissionUtils.requestPermissions(activity, ApiGetUserInfoCtrl.this.getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
                    public void onDenied(LinkedHashMap<Integer, String> param2LinkedHashMap) {
                      if (!hasRequestPermission)
                        PermissionHelper.reportAuthFailResult("fail", "mp_reject"); 
                      ApiGetUserInfoCtrl.this.callbackFail("auth deny");
                    }
                    
                    public void onGranted(LinkedHashMap<Integer, String> param2LinkedHashMap) {
                      if (!hasRequestPermission)
                        PermissionHelper.reportAuthSuccessResult("user_info"); 
                      try {
                        if (finalUserInfo != null && finalUserInfo.isLogin) {
                          String str = finalResultJO.optString("data");
                          if (!TextUtils.isEmpty(str)) {
                            HashMap<String, Object> hashMap = ApiGetUserInfoCtrl.this.makeResponseData(finalUserInfo, str);
                            if (hashMap != null) {
                              ApiGetUserInfoCtrl.this.callbackOk(hashMap);
                              return;
                            } 
                            ApiGetUserInfoCtrl.this.callbackFail("respData is null");
                            return;
                          } 
                          ApiGetUserInfoCtrl.this.callbackFail("server error, data is empty");
                          return;
                        } 
                        ApiGetUserInfoCtrl.this.callbackFail("platform auth deny");
                        return;
                      } catch (JSONException jSONException) {
                        ApiGetUserInfoCtrl.this.callbackFail((Throwable)jSONException);
                        return;
                      } 
                    }
                  }hashMap);
            }
          });
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      AppBrandLogger.stacktrace(6, "tma_ApiGetUserInfoCtrl", exception.getStackTrace());
      return;
    } 
  }
  
  public void act() {
    this.mStartTime = TimeMeter.currentMillis();
    Event.builder("mp_get_user_info").flush();
    if (!HostProcessBridge.isDataHandlerExist("getUserInfo")) {
      callbackAppUnSupportFeature();
      return;
    } 
    getUserInfo((Activity)AppbrandContext.getInst().getCurrentActivity());
  }
  
  public String getActionName() {
    return "getUserInfo";
  }
  
  public HashMap<String, Object> makeResponseData(UserInfoManager.UserInfo paramUserInfo, String paramString) throws JSONException {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (paramUserInfo != null && paramUserInfo.isLogin && !TextUtils.isEmpty(paramString)) {
      boolean bool = ApiPermissionManager.isCanGetUserInfo();
      JSONObject jSONObject1 = new JSONObject(paramString);
      JSONObject jSONObject2 = jSONObject1.optJSONObject("userInfo");
      if (bool) {
        jSONObject2.put("userId", paramUserInfo.userId);
        jSONObject2.put("sessionId", paramUserInfo.sessionId);
      } 
      hashMap.put("userInfo", jSONObject2);
      hashMap.put("rawData", jSONObject1.optString("rawData"));
      if (jSONObject1.has("encryptedData"))
        hashMap.put("encryptedData", jSONObject1.optString("encryptedData")); 
      if (jSONObject1.has("iv"))
        hashMap.put("iv", jSONObject1.optString("iv")); 
      if (jSONObject1.has("signature"))
        hashMap.put("signature", jSONObject1.optString("signature")); 
      Event.builder("mp_get_user_info_result").kv("duration", Long.valueOf(TimeMeter.currentMillis() - this.mStartTime)).flush();
      return (HashMap)hashMap;
    } 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetUserInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */