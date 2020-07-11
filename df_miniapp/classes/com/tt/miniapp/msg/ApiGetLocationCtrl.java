package com.tt.miniapp.msg;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.locate.LocateCrossProcessRequester;
import com.tt.miniapp.maplocate.TMALocation;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.util.CoordinateTransformUtil;
import com.tt.option.e.e;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetLocationCtrl extends b {
  boolean isLocateFinished;
  
  private int mTargetLocateType = 1;
  
  public LocateCrossProcessRequester requester = new LocateCrossProcessRequester(getActionName());
  
  public ApiGetLocationCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void callbackResult(TMALocation paramTMALocation) {
    if (paramTMALocation == null || !CoordinateTransformUtil.isValidLatLng(paramTMALocation.getLatitude(), paramTMALocation.getLongitude())) {
      callbackFail("invalid latitude and longitude");
      return;
    } 
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("longitude", paramTMALocation.getLongitude());
      jSONObject.put("latitude", paramTMALocation.getLatitude());
      jSONObject.put("speed", paramTMALocation.getSpeed());
      jSONObject.put("accuracy", paramTMALocation.getAccuracy());
      jSONObject.put("altitude", paramTMALocation.getAltitude());
      int i = Build.VERSION.SDK_INT;
      if (i >= 26) {
        jSONObject.put("verticalAccuracy", paramTMALocation.getVerticalAccuracyMeters());
      } else {
        jSONObject.put("verticalAccuracy", 0);
      } 
      jSONObject.put("horizontalAccuracy", paramTMALocation.getHorizontalAccuracy());
      jSONObject.put("city", paramTMALocation.getCity());
      callbackOk(jSONObject);
      StringBuilder stringBuilder = new StringBuilder("locate success:from");
      stringBuilder.append(paramTMALocation.getLocType());
      stringBuilder.append(" result:");
      stringBuilder.append(jSONObject.toString());
      AppBrandLogger.d("tma_ApiGetLocationCtrl", new Object[] { stringBuilder.toString() });
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_ApiGetLocationCtrl", new Object[] { jSONException });
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  private TMALocation convert2TargetCoortType(TMALocation paramTMALocation) {
    if (this.mTargetLocateType == 1) {
      double[] arrayOfDouble = CoordinateTransformUtil.gcj02towgs84(paramTMALocation.getLongitude(), paramTMALocation.getLatitude());
      paramTMALocation.setLongitude(arrayOfDouble[0]);
      paramTMALocation.setLatitude(arrayOfDouble[1]);
    } 
    return paramTMALocation;
  }
  
  private void getLocationCtrl(final Activity activity) {
    final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(12);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.LOCATION);
    BrandPermissionUtils.requestPermissions(activity, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestPermission)
              PermissionHelper.reportAuthFailResult("location", "mp_reject"); 
            AppBrandLogger.e("tma_ApiGetLocationCtrl", new Object[] { "onDenied LOCATION" });
            ApiGetLocationCtrl.this.mApiHandlerCallback.callback(ApiGetLocationCtrl.this.mCallBackId, BrandPermissionUtils.makePermissionErrorMsg(ApiGetLocationCtrl.this.getActionName()));
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            AppBrandLogger.d("tma_ApiGetLocationCtrl", new Object[] { "onGranted LOCATION" });
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.ACCESS_COARSE_LOCATION");
            hashSet.add("android.permission.ACCESS_FINE_LOCATION");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthFailResult("location", "system_reject"); 
                    AppBrandLogger.e("tma_ApiGetLocationCtrl", new Object[] { "onGranted onDenied" });
                    ApiGetLocationCtrl.this.mApiHandlerCallback.callback(ApiGetLocationCtrl.this.mCallBackId, BrandPermissionUtils.systemPermissionErrorMsg(ApiGetLocationCtrl.this.getActionName()));
                  }
                  
                  public void onGranted() {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthSuccessResult("location"); 
                    AppBrandLogger.d("tma_ApiGetLocationCtrl", new Object[] { "onGranted ACCESS_FINE_LOCATION" });
                    TMALocation tMALocation = ApiGetLocationCtrl.this.requester.getCachedLocation();
                    if (tMALocation != null && System.currentTimeMillis() - tMALocation.getTime() < 60000L) {
                      ApiGetLocationCtrl.this.callBackSuccess(tMALocation);
                      return;
                    } 
                    ApiGetLocationCtrl.this.requester.startCrossProcessLocate(6000L, new LocateCrossProcessRequester.LocateResultCallbck() {
                          public void onFailed(String param3String) {
                            ApiGetLocationCtrl.this.callBackFailed(param3String);
                          }
                          
                          public void onSuccess(TMALocation param3TMALocation) {
                            ApiGetLocationCtrl.this.callBackSuccess(param3TMALocation);
                          }
                        });
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
    String str = null;
    if (HostDependManager.getInst().createMapInstance() == null) {
      callbackAppUnSupportFeature();
      return;
    } 
    try {
      String str1 = (new JSONObject(this.mArgs)).optString("type");
      str = str1;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("tma_ApiGetLocationCtrl", "locate type", (Throwable)jSONException);
    } 
    if (!TextUtils.isEmpty(str) && TextUtils.equals(str.toLowerCase(), "gcj02"))
      this.mTargetLocateType = 2; 
    getLocationCtrl((Activity)miniappHostBase);
  }
  
  public void callBackFailed(String paramString) {
    if (this.isLocateFinished)
      return; 
    callbackFail(paramString);
    this.isLocateFinished = true;
  }
  
  public void callBackSuccess(TMALocation paramTMALocation) {
    if (this.isLocateFinished)
      return; 
    paramTMALocation = new TMALocation(paramTMALocation);
    convert2TargetCoortType(paramTMALocation);
    callbackResult(paramTMALocation);
    this.isLocateFinished = true;
  }
  
  public String getActionName() {
    return "getLocation";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetLocationCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */