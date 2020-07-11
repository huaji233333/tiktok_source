package com.tt.miniapp.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import org.json.JSONObject;

public class UserInfoUtil {
  public static void getUid(Context paramContext, String paramString1, String paramString2, GetUidListener paramGetUidListener) {
    String str;
    if (TextUtils.isEmpty(paramString2)) {
      paramGetUidListener.onFail(-1, "open id is empty");
      return;
    } 
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null) {
      str = initParamsEntity.getAppId();
    } else {
      str = "";
    } 
    if (TextUtils.isEmpty(str)) {
      paramGetUidListener.onFail(-1, "host id is empty");
      return;
    } 
    if (TextUtils.isEmpty(paramString1)) {
      paramGetUidListener.onFail(-1, "app id is empty");
      return;
    } 
    getUid(paramContext, str, paramString1, paramString2, paramGetUidListener);
  }
  
  public static void getUid(Context paramContext, String paramString1, String paramString2, String paramString3, final GetUidListener listener) {
    final String key = SaftyUtil.genRandomString();
    final String iv = SaftyUtil.genRandomString();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str2);
    stringBuilder.append("#");
    stringBuilder.append(str3);
    byte[] arrayOfByte = RSAUtil.encryptContent(paramContext, stringBuilder.toString());
    if (arrayOfByte != null) {
      str1 = Base64.encodeToString(arrayOfByte, 10);
    } else {
      str1 = "";
    } 
    AppBrandLogger.d("tma_UserInfoUtil", new Object[] { "ttCode ", str1 });
    stringBuilder = new StringBuilder();
    stringBuilder.append(AppbrandConstant.OpenApi.getInst().getOPENID_TO_UID_URL());
    stringBuilder.append("appid=");
    stringBuilder.append(paramString2);
    stringBuilder.append("&openid=");
    stringBuilder.append(paramString3);
    stringBuilder.append("&ttcode=");
    stringBuilder.append(str1);
    stringBuilder.append("&aid=");
    stringBuilder.append(paramString1);
    final String url = stringBuilder.toString();
    AppBrandLogger.d("tma_UserInfoUtil", new Object[] { "url ", str1 });
    Observable.create(new Function<String>() {
          public final String fun() {
            return NetManager.getInst().request(url).a();
          }
        }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public final void onError(Throwable param1Throwable) {
            AppBrandLogger.e("tma_UserInfoUtil", new Object[] { "onFail ", param1Throwable });
            listener.onFail(-3, "server response error");
          }
          
          public final void onSuccess(String param1String) {
            if (TextUtils.isEmpty(param1String)) {
              AppBrandLogger.e("tma_UserInfoUtil", new Object[] { "request result is null" });
              listener.onFail(-3, "server response error");
              return;
            } 
            try {
              String str1;
              JSONObject jSONObject = new JSONObject(param1String);
              String str2 = jSONObject.optString("data");
              int i = jSONObject.optInt("error", -1);
              if (i == 0 && !TextUtils.isEmpty(str2)) {
                str2 = (new JSONObject(str2)).optString("uid");
                AppBrandLogger.d("tma_UserInfoUtil", new Object[] { "uid = ", str2 });
                str1 = str2;
                if (!TextUtils.isEmpty(str2))
                  str1 = RSAUtil.AESDecrypt(key, iv, str2); 
                listener.onSuccess(str1);
                return;
              } 
              AppBrandLogger.e("tma_UserInfoUtil", new Object[] { "errorCode == ", Integer.valueOf(i), ", data == ", str2 });
              UserInfoUtil.GetUidListener getUidListener = listener;
              StringBuilder stringBuilder = new StringBuilder("server response error:");
              stringBuilder.append(str1.optString("message"));
              getUidListener.onFail(-3, stringBuilder.toString());
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("tma_UserInfoUtil", new Object[] { "parse json fail", exception });
              UserInfoUtil.GetUidListener getUidListener = listener;
              StringBuilder stringBuilder = new StringBuilder("parse json fail ");
              stringBuilder.append(exception);
              getUidListener.onFail(-2, stringBuilder.toString());
              return;
            } 
          }
        });
  }
  
  public static interface GetUidListener {
    void onFail(int param1Int, String param1String);
    
    void onSuccess(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\UserInfoUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */