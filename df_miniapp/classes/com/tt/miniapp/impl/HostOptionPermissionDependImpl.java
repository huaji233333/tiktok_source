package com.tt.miniapp.impl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.msg.ApiGetSettingCtrl;
import com.tt.miniapp.net.NetBus;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.option.t.b;
import com.tt.option.t.c;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import okhttp3.ac;
import okhttp3.ad;
import okhttp3.ae;
import okhttp3.e;
import okhttp3.f;
import okhttp3.w;
import org.json.JSONException;
import org.json.JSONObject;

public class HostOptionPermissionDependImpl implements b {
  public static int savePermissionRetrytimes;
  
  public static void clearNeedSendPermissionGrantState() {
    getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), "HostOptionPermissionDependImpl").edit().clear().commit();
  }
  
  public static boolean getSendPermissionGrantState() {
    SharedPreferences sharedPreferences = getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), "HostOptionPermissionDependImpl");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append((AppbrandApplication.getInst().getAppInfo()).appId);
    stringBuilder.append("UserInfoGrantState");
    return sharedPreferences.getBoolean(stringBuilder.toString(), false);
  }
  
  private static SharedPreferences getSharedPreferences(Context paramContext, String paramString) {
    return KVUtil.getSharedPreferences(paramContext, paramString);
  }
  
  public static boolean isNeedSendPermissionGrantRequest() {
    SharedPreferences sharedPreferences = getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), "HostOptionPermissionDependImpl");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append((AppbrandApplication.getInst().getAppInfo()).appId);
    stringBuilder.append("UserInfoGrantState");
    return sharedPreferences.contains(stringBuilder.toString());
  }
  
  public static void saveNeedSendPermissionGrantRequest(Boolean paramBoolean) {
    SharedPreferences.Editor editor = getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), "HostOptionPermissionDependImpl").edit();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append((AppbrandApplication.getInst().getAppInfo()).appId);
    stringBuilder.append("UserInfoGrantState");
    editor.putBoolean(stringBuilder.toString(), paramBoolean.booleanValue()).commit();
  }
  
  public Set<BrandPermissionUtils.BrandPermission> filterNeedRequestPermission(String paramString, Set<BrandPermissionUtils.BrandPermission> paramSet) {
    return paramSet;
  }
  
  public void getLocalScope(JSONObject paramJSONObject) throws JSONException {
    ApiGetSettingCtrl.getLocalScope(paramJSONObject);
  }
  
  public c getPermissionCustomDialogMsgEntity() {
    return new c(new c.a(), null);
  }
  
  public List<BrandPermissionUtils.BrandPermission> getUserDefinableHostPermissionList() {
    return BrandPermissionUtils.BrandPermission.getUserDefinablePermissionList();
  }
  
  public void handleCustomizePermissionResult(JSONObject paramJSONObject, int paramInt, boolean paramBoolean) throws JSONException {
    switch (paramInt) {
      default:
        return;
      case 18:
        paramJSONObject.put("scope.screenRecord", paramBoolean);
        return;
      case 17:
        paramJSONObject.put("scope.album", paramBoolean);
        return;
      case 15:
        paramJSONObject.put("scope.address", paramBoolean);
        return;
      case 14:
        paramJSONObject.put("scope.camera", paramBoolean);
        return;
      case 13:
        paramJSONObject.put("scope.record", paramBoolean);
        return;
      case 12:
        paramJSONObject.put("scope.userLocation", paramBoolean);
        return;
      case 11:
        break;
    } 
    paramJSONObject.put("scope.userInfo", paramBoolean);
  }
  
  public void metaExtraNotify(String paramString1, String paramString2) {}
  
  public void onDeniedWhenHasRequested(Activity paramActivity, String paramString) {}
  
  public BrandPermissionUtils.BrandPermission permissionTypeToPermission(int paramInt) {
    return BrandPermissionUtils.BrandPermission.makeFromAppbrandPermissionType(paramInt);
  }
  
  void requestSavePermissionGrant(final String sessionId, final int permissionType, final boolean isGranted) {
    int i = savePermissionRetrytimes;
    boolean bool = true;
    savePermissionRetrytimes = i + 1;
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("aid", Integer.parseInt(AppbrandContext.getInst().getInitParams().getAppId()));
      jSONObject.put("appid", (AppbrandApplication.getInst().getAppInfo()).appId);
      jSONObject.put("name", "getUserInfo");
      if (!isGranted)
        bool = false; 
      jSONObject.put("val", bool);
    } catch (JSONException jSONException) {}
    StringBuilder stringBuilder = new StringBuilder(AppbrandConstant.OpenApi.getInst().getSAVE_PERMISSION_GRANT());
    ad ad = ad.create(w.a("application/json"), jSONObject.toString());
    ac ac = (new ac.a()).a(stringBuilder.toString()).b("X-Tma-Host-Sessionid", sessionId).a(ad).c();
    NetBus.okHttpClient.a(ac).a(new f() {
          public void onFailure(e param1e, IOException param1IOException) {
            if (HostOptionPermissionDependImpl.savePermissionRetrytimes < 2) {
              HostOptionPermissionDependImpl.this.requestSavePermissionGrant(sessionId, permissionType, isGranted);
              return;
            } 
            HostOptionPermissionDependImpl.savePermissionRetrytimes = 0;
            StringBuilder stringBuilder = new StringBuilder("save permission grant fail:");
            stringBuilder.append(param1IOException.getStackTrace());
            AppBrandLogger.e("HostOptionPermissionDependImpl", new Object[] { stringBuilder.toString() });
            HostOptionPermissionDependImpl.saveNeedSendPermissionGrantRequest(Boolean.valueOf(isGranted));
          }
          
          public void onResponse(e param1e, ae param1ae) throws IOException {
            HostOptionPermissionDependImpl.savePermissionRetrytimes = 0;
            String str = param1ae.g.string();
            try {
              JSONObject jSONObject = new JSONObject(str);
              if (jSONObject.optInt("error") == 0) {
                AppBrandLogger.e("HostOptionPermissionDependImpl", new Object[] { "save permission grant success" });
                HostOptionPermissionDependImpl.clearNeedSendPermissionGrantState();
                return;
              } 
              StringBuilder stringBuilder = new StringBuilder("save permission grant fail:");
              stringBuilder.append(jSONObject.optString("message", ""));
              AppBrandLogger.e("HostOptionPermissionDependImpl", new Object[] { stringBuilder.toString() });
              HostOptionPermissionDependImpl.saveNeedSendPermissionGrantRequest(Boolean.valueOf(isGranted));
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.e("HostOptionPermissionDependImpl", new Object[] { jSONException });
              return;
            } 
          }
        });
  }
  
  public void savePermissionGrant(int paramInt, boolean paramBoolean) {
    if (paramInt != 11)
      return; 
    CrossProcessDataEntity crossProcessDataEntity = HostProcessBridge.getUserInfo();
    if (crossProcessDataEntity != null) {
      UserInfoManager.UserInfo userInfo = new UserInfoManager.UserInfo(crossProcessDataEntity);
      if (!TextUtils.isEmpty(userInfo.sessionId))
        requestSavePermissionGrant(userInfo.sessionId, paramInt, paramBoolean); 
    } 
  }
  
  public BrandPermissionUtils.BrandPermission scopeToBrandPermission(String paramString) {
    return BrandPermissionUtils.BrandPermission.makeFromScope(paramString);
  }
  
  public void setPermissionTime(int paramInt) {}
  
  public void syncPermissionToService() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\impl\HostOptionPermissionDependImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */