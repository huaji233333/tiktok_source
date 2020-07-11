package com.tt.miniapp.follow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.view.dialog.LoadingDialog;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.q.d;
import com.tt.option.q.i;
import org.json.JSONException;
import org.json.JSONObject;

public class FollowMethodImpl {
  private FollowResultCallback callback;
  
  public boolean mShowDialog = true;
  
  private Handler mainHandler;
  
  public FollowMethodImpl(FollowResultCallback paramFollowResultCallback) {
    this.callback = paramFollowResultCallback;
    this.mainHandler = new Handler(Looper.getMainLooper());
  }
  
  public void callbackResult(int paramInt, String paramString) {
    FollowResultCallback followResultCallback = this.callback;
    if (followResultCallback == null)
      return; 
    followResultCallback.callBackResult(paramInt, paramString);
  }
  
  public void dismissLoadingDialog(final Dialog dialog) {
    if (dialog != null && dialog.isShowing())
      this.mainHandler.post(new Runnable() {
            public void run() {
              _lancet.com_ss_android_ugc_aweme_lancet_DebugCheckLancet_dialogDismiss(dialog);
            }
            
            class null {}
          }); 
  }
  
  public void doFollow(Activity paramActivity) {
    this.mShowDialog = true;
    final Dialog dialog = showLoadingDialog(paramActivity, paramActivity.getString(2097742033));
    final i tmaPostRequest = new i(AppbrandConstant.OpenApi.getInst().getDO_FOLLOW_URL(), "POST");
    try {
      String str1 = (AppbrandApplication.getInst().getAppInfo()).appId;
      String str2 = InnerHostProcessBridge.getPlatformSession(str1);
      long l1 = Long.valueOf(d.a()).longValue();
      long l2 = Long.valueOf(AppbrandContext.getInst().getInitParams().getAppId()).longValue();
      i.a("session", str2);
      i.a("device_id", Long.valueOf(l1));
      i.a("aid", Long.valueOf(l2));
      i.a("app_id", str1);
    } finally {
      Exception exception = null;
    } 
    Observable.create(new Function<String>() {
          public String fun() {
            String str = NetManager.getInst().request(tmaPostRequest).a();
            AppBrandLogger.d("FollowMethodImpl", new Object[] { "requestResult = ", str });
            return str;
          }
        }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public void onError(Throwable param1Throwable) {
            FollowMethodImpl followMethodImpl = FollowMethodImpl.this;
            followMethodImpl.mShowDialog = false;
            followMethodImpl.dismissLoadingDialog(dialog);
            FollowMethodImpl.this.callbackResult(2, "network error");
          }
          
          public void onSuccess(String param1String) {
            FollowMethodImpl followMethodImpl = FollowMethodImpl.this;
            followMethodImpl.mShowDialog = false;
            followMethodImpl.dismissLoadingDialog(dialog);
            if (TextUtils.isEmpty(param1String)) {
              AppBrandLogger.d("FollowMethodImpl", new Object[] { "response empty" });
              FollowMethodImpl.this.callbackResult(2, "response empty");
              return;
            } 
            try {
              JSONObject jSONObject = new JSONObject(param1String);
              int i = jSONObject.getInt("error");
              if (i != 0) {
                AppBrandLogger.d("FollowMethodImpl", new Object[] { "getUserInfo error not 0" });
                FollowMethodImpl.this.callbackResult(i + 20, FollowErrorMsgBuilder.getResponseCodeDescription(i));
                return;
              } 
              if (jSONObject.getJSONObject("data").getInt("followed") == 1) {
                i = 1;
              } else {
                i = 0;
              } 
              if (i != 0) {
                AppBrandLogger.d("FollowMethodImpl", new Object[] { "has followed success" });
                FollowMethodImpl.this.callbackResult(0, "followed success");
                return;
              } 
              FollowMethodImpl.this.callbackResult(2, "followed not success");
              AppBrandLogger.d("FollowMethodImpl", new Object[] { "followed failed!" });
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.eWithThrowable("FollowMethodImpl", "jsonerror", (Throwable)jSONException);
              FollowMethodImpl.this.callbackResult(2, "json error");
              return;
            } 
          }
        });
  }
  
  public String getAuthType(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return ""; 
    try {
      return (new JSONObject(paramString)).optString("auth_type");
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("FollowMethodImpl", "error", (Throwable)jSONException);
      return "";
    } 
  }
  
  public void getCurrentFollowStateAndShowDialog(final Activity activity, final FollowUserInfo followUserInfo) {
    if (activity == null) {
      AppBrandLogger.d("FollowMethodImpl", new Object[] { "activity == null || callBack == null || userInfo == null" });
      return;
    } 
    AppBrandLogger.d("FollowMethodImpl", new Object[] { "getCurrentFollowStateAndShowDialog" });
    CheckFollowMethodImpl.requestFollowState(new CheckFollowMethodImpl.FollowResultCallback() {
          public void failed(Throwable param1Throwable) {
            FollowMethodImpl.this.showDialog(activity, followUserInfo, false);
          }
          
          public void success(boolean param1Boolean) {
            FollowMethodImpl.this.showDialog(activity, followUserInfo, param1Boolean);
          }
        });
  }
  
  public void getUserInfo(final Activity activity) {
    AppBrandLogger.d("FollowMethodImpl", new Object[] { "getUserInfo start" });
    final i tmaPostRequest = new i(AppbrandConstant.OpenApi.getInst().getQUERY_ACCOUNT_URL(), "POST");
    try {
      String str1 = (AppbrandApplication.getInst().getAppInfo()).appId;
      String str2 = InnerHostProcessBridge.getPlatformSession(str1);
      long l1 = Long.valueOf(d.a()).longValue();
      long l2 = Long.valueOf(AppbrandContext.getInst().getInitParams().getAppId()).longValue();
      i.a("session", str2);
      i.a("device_id", Long.valueOf(l1));
      i.a("aid", Long.valueOf(l2));
      i.a("app_id", str1);
    } finally {
      Exception exception = null;
    } 
    this.mShowDialog = true;
    final Dialog loadingDialog = showLoadingDialog(activity, activity.getString(2097742035));
    Observable.create(new Function<String>() {
          public String fun() {
            String str = NetManager.getInst().request(tmaPostRequest).a();
            AppBrandLogger.d("FollowMethodImpl", new Object[] { "requestResult = ", str });
            return str;
          }
        }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public void onError(Throwable param1Throwable) {
            FollowMethodImpl followMethodImpl = FollowMethodImpl.this;
            followMethodImpl.mShowDialog = false;
            followMethodImpl.dismissLoadingDialog(loadingDialog);
          }
          
          public void onSuccess(String param1String) {
            FollowMethodImpl followMethodImpl = FollowMethodImpl.this;
            followMethodImpl.mShowDialog = false;
            followMethodImpl.dismissLoadingDialog(loadingDialog);
            if (TextUtils.isEmpty(param1String)) {
              AppBrandLogger.d("FollowMethodImpl", new Object[] { "getUserInfo response null" });
              FollowMethodImpl.this.callbackResult(2, "response null");
              return;
            } 
            StringBuilder stringBuilder = new StringBuilder("response s:");
            stringBuilder.append(param1String);
            AppBrandLogger.d("FollowMethodImpl", new Object[] { stringBuilder.toString() });
            try {
              JSONObject jSONObject = new JSONObject(param1String);
              int i = jSONObject.getInt("error");
              if (i != 0) {
                AppBrandLogger.d("FollowMethodImpl", new Object[] { "getUserInfo error not 0" });
                FollowMethodImpl.this.callbackResult(i + 20, FollowErrorMsgBuilder.getResponseCodeDescription(i));
                return;
              } 
              jSONObject = jSONObject.getJSONObject("data");
              FollowUserInfo followUserInfo = new FollowUserInfo();
              followUserInfo.avatarUrl = jSONObject.optString("avatar_url");
              followUserInfo.name = jSONObject.optString("name");
              followUserInfo.description = jSONObject.optString("description");
              followUserInfo.userDecoration = jSONObject.optString("user_decoration");
              followUserInfo.userAuthInfo = jSONObject.optString("user_auth_info");
              followUserInfo.authType = FollowMethodImpl.this.getAuthType(followUserInfo.userAuthInfo);
              FollowMethodImpl.this.getCurrentFollowStateAndShowDialog(activity, followUserInfo);
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.eWithThrowable("FollowMethodImpl", "jsonerror", (Throwable)jSONException);
              FollowMethodImpl.this.callbackResult(2, "response json parse error");
              return;
            } 
          }
        });
  }
  
  public void logEvent(String paramString) {
    Event.builder(paramString).flush();
  }
  
  public void showDialog(final Activity activity, final FollowUserInfo followUserInfo, final boolean hasFollowed) {
    this.mainHandler.post(new Runnable() {
          public void run() {
            (new MicroGameFollowDialog((Context)activity, followUserInfo, hasFollowed, new MicroGameFollowDialog.IOnClickListener() {
                  public void onClose() {
                    FollowMethodImpl.this.callbackResult(1, "user close dialog");
                    FollowMethodImpl.this.logEvent("mp_follow_cancel");
                  }
                  
                  public void onConfirm() {
                    FollowMethodImpl.this.doFollow(activity);
                    FollowMethodImpl.this.logEvent("mp_follow_click");
                  }
                })).show();
          }
        });
    logEvent("mp_follow_show");
  }
  
  public Dialog showLoadingDialog(Activity paramActivity, String paramString) {
    if (paramActivity == null)
      return null; 
    final LoadingDialog dialog = new LoadingDialog((Context)paramActivity, paramString);
    this.mainHandler.postDelayed(new Runnable() {
          public void run() {
            if (FollowMethodImpl.this.mShowDialog)
              dialog.show(); 
          }
        },  1000L);
    return (Dialog)loadingDialog;
  }
  
  public void startFollow(final Activity activity) {
    if (!d.a((Context)activity)) {
      callbackResult(3, "no network");
      return;
    } 
    this.mainHandler.post(new Runnable() {
          public void run() {
            FollowMethodImpl.this.getUserInfo(activity);
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\follow\FollowMethodImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */