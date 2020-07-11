package com.tt.miniapp.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.a;
import com.storage.async.Action;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Scheduler;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.RSAUtil;
import com.tt.miniapp.util.SaftyUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.option.d;
import com.tt.option.q.d;
import com.tt.option.q.i;
import com.tt.option.q.j;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoManager {
  public static String TAG = "UserInfoManager";
  
  public static void fetchHostClientUserInfo(final UserInfoFetcher fetcher) {
    Observable.create(new Function<UserInfo>() {
          public final UserInfoManager.UserInfo fun() {
            return UserInfoManager.getHostClientUserInfo();
          }
        }).schudleOn((Scheduler)LaunchThreadPool.getInst()).observeOn(Schedulers.ui()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<UserInfo>() {
          public final void onError(Throwable param1Throwable) {
            fetcher.onFetched(UserInfoManager.UserInfo.EMPTY);
          }
          
          public final void onSuccess(UserInfoManager.UserInfo param1UserInfo) {
            fetcher.onFetched(param1UserInfo);
          }
        });
  }
  
  public static void getBindPhoneNumber(boolean paramBoolean1, boolean paramBoolean2, GetBindPhoneListener paramGetBindPhoneListener) {
    CharSequence charSequence;
    String str6;
    String str7;
    byte[] arrayOfByte;
    if ((getHostClientUserInfo()).isLogin) {
      charSequence = InnerHostProcessBridge.getPlatformSession((AppbrandApplication.getInst().getAppInfo()).appId);
    } else {
      charSequence = null;
    } 
    if (TextUtils.isEmpty(charSequence)) {
      if (paramBoolean1)
        Event.builder("mp_auth_process_trigger").kv("login_status_before_action", "False").flush(); 
      paramGetBindPhoneListener.onFail(4);
      return;
    } 
    if (paramBoolean1)
      Event.builder("mp_auth_process_trigger").kv("login_status_before_action", "True").flush(); 
    String str10 = AppbrandConstant.OpenApi.getInst().getCurrentDomain();
    if (HostDependManager.getInst().isEnableI18nNetRequest()) {
      str6 = "";
    } else {
      str6 = "https://microapp.bytedance.com";
    } 
    Application application = AppbrandContext.getInst().getApplicationContext();
    String str2 = null;
    String str5 = str2;
    String str4 = str5;
    String str3 = str4;
    int i = 3;
    while (true) {
      str7 = str2;
      String str = str3;
      if (i > 0) {
        String str11 = SaftyUtil.genRandomString();
        str3 = SaftyUtil.genRandomString();
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str11);
        stringBuilder1.append("#");
        stringBuilder1.append(str3);
        byte[] arrayOfByte1 = RSAUtil.encryptContent((Context)application, stringBuilder1.toString());
        if (arrayOfByte1 != null)
          str2 = Base64.encodeToString(arrayOfByte1, 10); 
        str7 = str2;
        str5 = str11;
        str4 = str3;
        arrayOfByte = arrayOfByte1;
        if (TextUtils.isEmpty(str2)) {
          AppBrandLogger.e(TAG, new Object[] { "ttCode isEmpty. key:", str11, " iv:", str3, " secret:", arrayOfByte1 });
          i--;
          str5 = str11;
          str4 = str3;
          byte[] arrayOfByte2 = arrayOfByte1;
          continue;
        } 
      } 
      break;
    } 
    if (TextUtils.isEmpty(str7)) {
      AppBrandLogger.e(TAG, new Object[] { "ttCode isEmpty. key:", str5, " iv:", str4, " secret:", arrayOfByte });
      paramGetBindPhoneListener.onFail(0);
      return;
    } 
    AppBrandLogger.d(TAG, new Object[] { "ttCode ", str7 });
    String str8 = AppbrandContext.getInst().getInitParams().getAppId();
    String str9 = (AppbrandApplication.getInst().getAppInfo()).appId;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str10);
    stringBuilder.append("/api/apps/user/phonenumber");
    stringBuilder.append(a.a("?aid=%s&appid=%s&session=%s&ttcode=%s", new Object[] { str8, str9, charSequence, str7 }));
    i i1 = new i(stringBuilder.toString(), "GET");
    j j2 = NetManager.getInst().request(i1);
    j j1 = j2;
    if (TextUtils.isEmpty(j2.a())) {
      j1 = j2;
      if (!TextUtils.isEmpty(str6)) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str6);
        stringBuilder1.append("/api/apps/user/phonenumber");
        stringBuilder1.append(a.a("?aid=%s&appid=%s&session=%s&ttcode=%s", new Object[] { str8, str9, charSequence, str7 }));
        String str = stringBuilder1.toString();
        j1 = NetManager.getInst().request(str);
      } 
    } 
    String str1 = j1.a();
    if (TextUtils.isEmpty(str1)) {
      paramGetBindPhoneListener.onFail(0);
      return;
    } 
    try {
      JSONObject jSONObject1 = new JSONObject(str1);
      i = jSONObject1.optInt("error");
      if (i != 0) {
        if (i != 2) {
          if (i != 4) {
            paramGetBindPhoneListener.onFail(1);
          } else if (paramBoolean2) {
            paramGetBindPhoneListener.onFail(5);
          } else {
            paramGetBindPhoneListener.onUnbindPhoneNumber();
          } 
        } else {
          paramGetBindPhoneListener.onFail(3);
        } 
        AppBrandLogger.e(TAG, new Object[] { "getBindPhoneNumber fail. message:", jSONObject1.optString("message") });
        return;
      } 
      JSONObject jSONObject2 = jSONObject1.optJSONObject("data");
      str1 = jSONObject2.optString("encryptedData");
      String str12 = jSONObject2.optString("iv");
      String str11 = RSAUtil.AESDecrypt(str5, str4, jSONObject1.optString("phonenumber"));
      if (TextUtils.isEmpty(str11)) {
        paramGetBindPhoneListener.onFail(0);
        return;
      } 
      paramGetBindPhoneListener.onSuccess(str11, str1, str12);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable(TAG, "getBindPhoneNumber", (Throwable)jSONException);
      paramGetBindPhoneListener.onFail(0);
      return;
    } 
  }
  
  public static UserInfo getHostClientUserInfo() {
    CrossProcessDataEntity crossProcessDataEntity = HostProcessBridge.getUserInfo();
    if (crossProcessDataEntity != null)
      return new UserInfo(crossProcessDataEntity); 
    recordUserinfoCrossEmpty();
    return UserInfo.EMPTY;
  }
  
  public static String getLocalTmpId() {
    return getLocalTmpSP((Context)AppbrandContext.getInst().getApplicationContext(), "tmaUser").getString("anonymousId", "");
  }
  
  private static SharedPreferences getLocalTmpSP(Context paramContext, String paramString) {
    return KVUtil.getSharedPreferences(paramContext, paramString);
  }
  
  public static boolean handleHostClientLoginResult(final int requestCode, final int resultCode, final Intent data, final HostClientLoginListener loginListener) {
    if (HostDependManager.getInst().handleActivityLoginResult(requestCode, resultCode, data)) {
      ThreadUtil.runOnWorkThread(new Action() {
            public final void act() {
              if ((UserInfoManager.getHostClientUserInfo()).isLogin) {
                AppBrandLogger.d(UserInfoManager.TAG, new Object[] { "host client login success" });
                Event.builder("mp_login_page_result").kv("result_type", "success").flush();
                loginListener.onLoginSuccess();
                GameModuleController.inst().handleHostClientLoginResult(requestCode, resultCode, data, loginListener);
                return;
              } 
              AppBrandLogger.d(UserInfoManager.TAG, new Object[] { "host client login fail" });
              Event.builder("mp_login_page_result").kv("result_type", "close").flush();
              loginListener.onLoginFail();
            }
          }(Scheduler)LaunchThreadPool.getInst());
      return true;
    } 
    return false;
  }
  
  private static void loginMiniAppPlatform(String paramString, final long loginStartTime, final MiniAppPlatformLoginListener loginListener) {
    AppBrandLogger.d(TAG, new Object[] { "loginMiniAppPlatform" });
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(AppbrandConstant.OpenApi.getInst().getLOGIN_URL());
    stringBuilder.append((AppbrandApplication.getInst().getAppInfo()).appId);
    String str2 = stringBuilder.toString();
    String str1 = str2;
    if (AppbrandContext.getInst().getInitParams() != null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str2);
      stringBuilder1.append("&aid=");
      stringBuilder1.append(AppbrandContext.getInst().getInitParams().getAppId());
      str1 = stringBuilder1.toString();
    } 
    str2 = str1;
    if (!TextUtils.isEmpty(getLocalTmpId())) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str1);
      stringBuilder1.append("&anonymousid=");
      stringBuilder1.append(getLocalTmpId());
      str2 = stringBuilder1.toString();
    } 
    final i request = new i(str2, "GET");
    if (!TextUtils.isEmpty(paramString))
      i.a("X-Tma-Host-Sessionid", paramString); 
    String str3 = d.a();
    if (!TextUtils.isEmpty(str3))
      i.a("X-Tma-Host-Deviceid", str3); 
    final JSONObject requestParamJO = new JSONObject();
    try {
      jSONObject.put("url", str2);
      jSONObject.put("X-Tma-Host-Sessionid", paramString);
      jSONObject.put("X-Tma-Host-Deviceid", str3);
    } catch (JSONException jSONException) {
      AppBrandLogger.e(TAG, new Object[] { jSONException });
    } 
    Observable.create(new Action() {
          public final void act() {
            String str = NetManager.getInst().request(request).a();
            if (!TextUtils.isEmpty(str))
              try {
                String str1;
                AppBrandLogger.d(UserInfoManager.TAG, new Object[] { str });
                JSONObject jSONObject1 = new JSONObject(str);
                int j = jSONObject1.optInt("error", -1);
                if (j == 0) {
                  JSONObject jSONObject3 = jSONObject1.optJSONObject("data");
                  String str2 = jSONObject1.optString("session");
                  str1 = jSONObject1.optString("anonymousid");
                  if (!TextUtils.isEmpty(str2))
                    InnerHostProcessBridge.savePlatformSession(str2, (AppbrandApplication.getInst().getAppInfo()).appId); 
                  if (!TextUtils.isEmpty(str1) && !str1.equals(UserInfoManager.getLocalTmpId()))
                    UserInfoManager.setLocalTmpId(str1); 
                  loginListener.onLoginSuccess(str2, jSONObject3);
                  Event.builder("mp_login_result").kv("duration", Long.valueOf(TimeMeter.currentMillis() - loginStartTime)).flush();
                  return;
                } 
                AppBrandLogger.e(UserInfoManager.TAG, new Object[] { "login fail ", str1 });
                JSONObject jSONObject2 = new JSONObject();
                StringBuilder stringBuilder2 = new StringBuilder("errCode = ");
                stringBuilder2.append(j);
                AppBrandMonitor.statusRate("mp_start_error", 1020, jSONObject2.put("errMsg", stringBuilder2.toString()).put("request", requestParamJO).put("respJO", str1));
                UserInfoManager.MiniAppPlatformLoginListener miniAppPlatformLoginListener = loginListener;
                StringBuilder stringBuilder1 = new StringBuilder("server error ");
                stringBuilder1.append(j);
                miniAppPlatformLoginListener.onLoginFail(stringBuilder1.toString());
                return;
              } catch (Exception exception) {
                AppBrandLogger.e(UserInfoManager.TAG, new Object[] { "", exception });
                JSONObject jSONObject1 = new JSONObject();
                try {
                  StringBuilder stringBuilder = new StringBuilder("server error! resp json parse exception.\nstackTrace: ");
                  stringBuilder.append(Log.getStackTraceString(exception));
                  jSONObject1.put("errMsg", stringBuilder.toString()).put("request", requestParamJO).put("resp", str);
                } catch (JSONException jSONException) {
                  AppBrandLogger.e(UserInfoManager.TAG, new Object[] { jSONException });
                } 
                AppBrandMonitor.statusRate("mp_start_error", 10201, jSONObject1);
                loginListener.onLoginFail("server error! resp json parse exception.");
                return;
              }  
            JSONObject jSONObject = new JSONObject();
            try {
              jSONObject.put("errMsg", "server error! response is empty.").put("request", requestParamJO).put("resp", jSONException);
            } catch (JSONException jSONException1) {
              AppBrandLogger.e(UserInfoManager.TAG, new Object[] { jSONException1 });
            } 
            AppBrandMonitor.statusRate("mp_start_error", 10202, jSONObject);
            loginListener.onLoginFail("server error! response is empty.");
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  private static void recordUserinfoCrossEmpty() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("msg", "ipc userinfo emtpy");
    } catch (JSONException jSONException) {}
    AppBrandMonitor.statusRate("mp_userinfo_lost", 20001, jSONObject);
  }
  
  public static void requestBindPhoneNumber(final BindPhoneListener bindPhoneListener) {
    if (HostDependManager.getInst().bindPhoneNumber(new d.a() {
          public final void onBindPhoneResult(boolean param1Boolean) {
            String str;
            if (param1Boolean) {
              bindPhoneListener.onSuccess();
            } else {
              bindPhoneListener.onFail(0);
            } 
            Event.Builder builder = Event.builder("mp_phone_bind_page_result");
            if (param1Boolean) {
              str = "success";
            } else {
              str = "close";
            } 
            builder.kv("result_type", str).flush();
          }
        })) {
      Event.builder("mp_phone_bind_page_show").flush();
      return;
    } 
    bindPhoneListener.onFail(2);
  }
  
  public static void requestGetBindPhoneNumber(final boolean fromUser, final boolean disableBindPhoneNumber, final GetBindPhoneListener getBindPhoneListener) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            UserInfoManager.getBindPhoneNumber(fromUser, disableBindPhoneNumber, getBindPhoneListener);
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  public static void requestLoginHostClient(Activity paramActivity, HostClientLoginListener paramHostClientLoginListener, HashMap<String, Object> paramHashMap, boolean paramBoolean, String paramString) {
    AppBrandLogger.d(TAG, new Object[] { "requestLoginHostClient" });
    if (paramActivity == null) {
      paramHostClientLoginListener.onLoginFail();
      return;
    } 
    if (paramBoolean && AppbrandApplicationImpl.getInst().getForeBackgroundManager().isBackgroundOrGoingBackground()) {
      AppBrandLogger.i(TAG, new Object[] { "requestLoginHostClient when background or going background" });
      paramHostClientLoginListener.onLoginWhenBackground();
      return;
    } 
    AppBrandLogger.i(TAG, new Object[] { "requestLoginHostClient when foreground" });
    if (!HostDependManager.getInst().openLoginActivity(paramActivity, paramHashMap)) {
      paramHostClientLoginListener.onLoginUnSupport();
      return;
    } 
    AppBrandLogger.d(TAG, new Object[] { "triggerHostClientLogin" });
    paramHostClientLoginListener.onTriggerHostClientLogin(paramString);
    Event.builder("mp_login_page_show").flush();
  }
  
  public static void requestLoginHostClient(HostClientLoginListener paramHostClientLoginListener, HashMap<String, Object> paramHashMap, String paramString) {
    requestLoginHostClient((Activity)AppbrandContext.getInst().getCurrentActivity(), paramHostClientLoginListener, paramHashMap, true, paramString);
  }
  
  public static void requestLoginMiniAppPlatform(boolean paramBoolean, long paramLong, MiniAppPlatformLoginListener paramMiniAppPlatformLoginListener, HostClientLoginListener paramHostClientLoginListener) {
    String str;
    AppBrandLogger.d(TAG, new Object[] { "requestLoginMiniAppPlatform forceLoginHostClient:", Boolean.valueOf(paramBoolean) });
    UserInfo userInfo = getHostClientUserInfo();
    boolean bool = userInfo.isLogin;
    HostClientLoginListener hostClientLoginListener = null;
    if (bool || !paramBoolean) {
      paramHostClientLoginListener = hostClientLoginListener;
      if (userInfo.isLogin)
        str = userInfo.sessionId; 
      loginMiniAppPlatform(str, paramLong, paramMiniAppPlatformLoginListener);
      return;
    } 
    if (str == null) {
      paramMiniAppPlatformLoginListener.onLoginFail("error host login fail");
      return;
    } 
    requestLoginHostClient((HostClientLoginListener)str, null, null);
  }
  
  public static String requestOpenId() {
    String str = (AppbrandApplication.getInst().getAppInfo()).appId;
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null) {
      String str1 = initParamsEntity.getAppId();
    } else {
      initParamsEntity = null;
    } 
    return requestOpenId((String)initParamsEntity, str);
  }
  
  public static String requestOpenId(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder(AppbrandConstant.OpenApi.getInst().getOpenIdUrl());
    stringBuilder.append("?appid=");
    stringBuilder.append(paramString2);
    if (!TextUtils.isEmpty(paramString1)) {
      stringBuilder.append("&aid=");
      stringBuilder.append(paramString1);
    } 
    i i = new i(stringBuilder.toString(), "GET");
    UserInfo userInfo = getHostClientUserInfo();
    if (userInfo == null || TextUtils.isEmpty(userInfo.sessionId))
      return CharacterUtils.empty(); 
    i.a("X-Tma-Host-Sessionid", userInfo.sessionId);
    j j = HostDependManager.getInst().doGet(i);
    if (j != null && !TextUtils.isEmpty(j.a())) {
      JSONObject jSONObject = (new JsonBuilder(j.a())).build();
      int k = jSONObject.optInt("err_no");
      if (k == 0)
        return jSONObject.optString("openid"); 
      String str = TAG;
      stringBuilder = new StringBuilder("getOpenIdFail err_no = ");
      stringBuilder.append(k);
      stringBuilder.append(" err_tip = ");
      stringBuilder.append(jSONObject.optString("err_tips"));
      AppBrandLogger.i(str, new Object[] { stringBuilder.toString() });
    } 
    return CharacterUtils.empty();
  }
  
  public static void setLocalTmpId(String paramString) {
    getLocalTmpSP((Context)AppbrandContext.getInst().getApplicationContext(), "tmaUser").edit().putString("anonymousId", paramString).commit();
  }
  
  public static interface BindPhoneListener {
    void onFail(int param1Int);
    
    void onSuccess();
  }
  
  public static interface GetBindPhoneListener {
    void onFail(int param1Int);
    
    void onSuccess(String param1String1, String param1String2, String param1String3);
    
    void onUnbindPhoneNumber();
  }
  
  public static interface HostClientLoginListener {
    void onLoginFail();
    
    void onLoginSuccess();
    
    void onLoginUnSupport();
    
    void onLoginWhenBackground();
    
    void onTriggerHostClientLogin(String param1String);
  }
  
  public static interface MiniAppPlatformLoginListener {
    void onLoginFail(String param1String);
    
    void onLoginSuccess(String param1String, JSONObject param1JSONObject);
  }
  
  public static class UserInfo {
    public static final UserInfo EMPTY = new UserInfo();
    
    public String authInfo;
    
    public String avatarUrl;
    
    public String country;
    
    public String gender;
    
    public boolean isLogin;
    
    public boolean isVerified;
    
    public String language;
    
    public String nickName;
    
    public String secUID;
    
    public String sessionId;
    
    public String userId;
    
    private UserInfo() {}
    
    public UserInfo(CrossProcessDataEntity param1CrossProcessDataEntity) {
      this.avatarUrl = param1CrossProcessDataEntity.getString("avatarUrl");
      this.nickName = param1CrossProcessDataEntity.getString("nickName");
      this.gender = param1CrossProcessDataEntity.getString("gender");
      this.country = param1CrossProcessDataEntity.getString("country");
      this.isLogin = param1CrossProcessDataEntity.getBoolean("isLogin");
      this.language = param1CrossProcessDataEntity.getString("language");
      this.sessionId = param1CrossProcessDataEntity.getString("sessionId");
      this.userId = param1CrossProcessDataEntity.getString("userId");
      this.secUID = param1CrossProcessDataEntity.getString("sec_uid");
      this.isVerified = param1CrossProcessDataEntity.getBoolean("isVerified");
      this.authInfo = param1CrossProcessDataEntity.getString("authInfo");
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder("UserInfo{avatarUrl='");
      stringBuilder.append(this.avatarUrl);
      stringBuilder.append('\'');
      stringBuilder.append(", nickName='");
      stringBuilder.append(this.nickName);
      stringBuilder.append('\'');
      stringBuilder.append(", gender='");
      stringBuilder.append(this.gender);
      stringBuilder.append('\'');
      stringBuilder.append(", language='");
      stringBuilder.append(this.language);
      stringBuilder.append('\'');
      stringBuilder.append(", country='");
      stringBuilder.append(this.country);
      stringBuilder.append('\'');
      stringBuilder.append(", isLogin=");
      stringBuilder.append(this.isLogin);
      stringBuilder.append(", userId='");
      stringBuilder.append(this.userId);
      stringBuilder.append('\'');
      stringBuilder.append(", sec_uid='");
      stringBuilder.append(this.secUID);
      stringBuilder.append('\'');
      stringBuilder.append(", sessionId='");
      stringBuilder.append(this.sessionId);
      stringBuilder.append('\'');
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
  }
  
  public static interface UserInfoFetcher {
    void onFetched(UserInfoManager.UserInfo param1UserInfo);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\UserInfoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */