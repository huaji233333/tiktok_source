package com.tt.miniapp.msg;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import com.a;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.locate.LocateCrossProcessRequester;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.maplocate.TMALocation;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.util.CoordinateTransformUtil;
import com.tt.option.e.e;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetCloudStorageByLocation extends b {
  private static final String BASE_URL = AppbrandConstant.OpenApi.getInst().getUSER_LOCATION_URL();
  
  private LocateCrossProcessRequester requester = new LocateCrossProcessRequester(getActionName());
  
  public ApiGetCloudStorageByLocation(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public static String getApplicationId() {
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null)
      return initParamsEntity.getAppId(); 
    AppBrandLogger.e("tma_getCloudStorageByLocation", new Object[] { "getApplicationId initParams is null" });
    return "";
  }
  
  private void getCloudStorageByLocation(final Activity activity, final String finalKeyLiost, final String finalPositionType) {
    final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(12);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.LOCATION);
    BrandPermissionUtils.requestPermissions(activity, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestPermission)
              PermissionHelper.reportAuthFailResult("location", "mp_reject"); 
            AppBrandLogger.e("tma_getCloudStorageByLocation", new Object[] { "onDenied LOCATION" });
            ApiGetCloudStorageByLocation.this.callbackFail("auth deny");
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            AppBrandLogger.d("tma_getCloudStorageByLocation", new Object[] { "onGranted LOCATION" });
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.ACCESS_COARSE_LOCATION");
            hashSet.add("android.permission.ACCESS_FINE_LOCATION");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthFailResult("location", "system_reject"); 
                    AppBrandLogger.e("tma_getCloudStorageByLocation", new Object[] { "onGranted onDenied" });
                    ApiGetCloudStorageByLocation.this.callbackFail("system auth deny");
                  }
                  
                  public void onGranted() {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthSuccessResult("location"); 
                    AppBrandLogger.d("tma_getCloudStorageByLocation", new Object[] { "onGranted ACCESS_FINE_LOCATION" });
                    ApiGetCloudStorageByLocation.this.getAndReportLocation(finalKeyLiost, finalPositionType);
                  }
                });
          }
        }null);
  }
  
  public void act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail("activity is null");
      return;
    } 
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str1 = jSONObject.getJSONArray("keyList").toString();
      String str2 = jSONObject.getString("type");
      getCloudStorageByLocation((Activity)miniappHostBase, str1, str2);
      return;
    } catch (Exception exception) {
      callbackFail("invoke params error");
      AppBrandLogger.stacktrace(6, "tma_getCloudStorageByLocation", exception.getStackTrace());
      return;
    } 
  }
  
  public String getActionName() {
    return "getCloudStorageByLocation";
  }
  
  public void getAndReportLocation(final String keyList, final String positiionType) {
    AppBrandLogger.d("tma_getCloudStorageByLocation", new Object[] { "getAndReportLocation:", keyList, positiionType });
    TMALocation tMALocation = this.requester.getCachedLocation();
    if (tMALocation != null && System.currentTimeMillis() - tMALocation.getTime() < 86400000L) {
      startGetNearRankRequest(positiionType, keyList, tMALocation);
      return;
    } 
    this.requester.startCrossProcessLocate(5000L, new LocateCrossProcessRequester.LocateResultCallbck() {
          public void onFailed(String param1String) {
            ApiGetCloudStorageByLocation.this.callbackFail(param1String);
          }
          
          public void onSuccess(TMALocation param1TMALocation) {
            ApiGetCloudStorageByLocation.this.startGetNearRankRequest(positiionType, keyList, param1TMALocation);
          }
        });
  }
  
  public void startGetNearRankRequest(final String completeUrl, String paramString2, TMALocation paramTMALocation) {
    AppBrandLogger.d("tma_getCloudStorageByLocation", new Object[] { "startGetNearRankRequest" });
    double[] arrayOfDouble = CoordinateTransformUtil.gcj02towgs84(paramTMALocation.getLongitude(), paramTMALocation.getLatitude());
    double d1 = arrayOfDouble[0];
    double d2 = arrayOfDouble[1];
    Uri.Builder builder = Uri.parse(BASE_URL).buildUpon();
    builder.appendQueryParameter("session", InnerHostProcessBridge.getPlatformSession((AppbrandApplication.getInst().getAppInfo()).appId));
    builder.appendQueryParameter("appid", (AppbrandApplicationImpl.getInst().getAppInfo()).appId);
    builder.appendQueryParameter("aid", getApplicationId());
    builder.appendQueryParameter("keyList", paramString2);
    builder.appendQueryParameter("type", completeUrl);
    builder.appendQueryParameter("longitude", String.valueOf(d1));
    builder.appendQueryParameter("latitude", String.valueOf(d2));
    completeUrl = builder.build().toString();
    StringBuilder stringBuilder = new StringBuilder("completeUrl:");
    stringBuilder.append(completeUrl);
    AppBrandLogger.d("tma_getCloudStorageByLocation", new Object[] { stringBuilder.toString() });
    Observable.create(new Function<String>() {
          public String fun() {
            return NetManager.getInst().request(completeUrl).a();
          }
        }).schudleOn(Schedulers.longIO()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public void onError(Throwable param1Throwable) {
            ApiGetCloudStorageByLocation.this.callbackFail(param1Throwable);
          }
          
          public void onSuccess(String param1String) {
            if (TextUtils.isEmpty(param1String)) {
              ApiGetCloudStorageByLocation.this.callbackFail("requestResult is null");
              return;
            } 
            StringBuilder stringBuilder = new StringBuilder("response s:");
            stringBuilder.append(param1String);
            AppBrandLogger.d("tma_getCloudStorageByLocation", new Object[] { stringBuilder.toString() });
            try {
              String str;
              JSONObject jSONObject2 = new JSONObject(param1String);
              int i = jSONObject2.getInt("error");
              if (i != 0) {
                str = jSONObject2.optString("message");
                ApiGetCloudStorageByLocation.this.callbackFail(a.a("%s errorCode = %s", new Object[] { str, Integer.valueOf(i) }));
                return;
              } 
              JSONObject jSONObject1 = str.getJSONObject("data");
              HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
              Iterator<String> iterator = jSONObject1.keys();
              while (iterator.hasNext()) {
                String str1 = iterator.next();
                hashMap.put(str1, jSONObject1.opt(str1));
              } 
              ApiGetCloudStorageByLocation.this.callbackOk(hashMap);
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.eWithThrowable("tma_getCloudStorageByLocation", "jsonerror", (Throwable)jSONException);
              ApiGetCloudStorageByLocation.this.callbackFail((Throwable)jSONException);
              return;
            } 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetCloudStorageByLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */