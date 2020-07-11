package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.map.AppbrandMapActivity;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.option.e.e;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiOpenLocationCtrl extends b {
  private String address;
  
  private double lat;
  
  private double lon;
  
  private String name;
  
  private int scale;
  
  public ApiOpenLocationCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void openLocation(final Activity activity) {
    final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(12);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.LOCATION);
    BrandPermissionUtils.requestPermissions(activity, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestPermission)
              PermissionHelper.reportAuthFailResult("location", "mp_reject"); 
            if (!param1LinkedHashMap.isEmpty())
              AppBrandLogger.e("tma_ApiOpenLocationCtrl", new Object[] { "onDenied2 ", HostDependManager.getInst().permissionTypeToPermission(((Integer)param1LinkedHashMap.keySet().iterator().next()).intValue()).getMsg() }); 
            ApiOpenLocationCtrl.this.mApiHandlerCallback.callback(ApiOpenLocationCtrl.this.mCallBackId, BrandPermissionUtils.makePermissionErrorMsg(ApiOpenLocationCtrl.this.getActionName()));
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            AppBrandLogger.d("tma_ApiOpenLocationCtrl", new Object[] { "onGranted" });
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.ACCESS_COARSE_LOCATION");
            hashSet.add("android.permission.ACCESS_FINE_LOCATION");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthFailResult("location", "system_reject"); 
                    AppBrandLogger.e("tma_ApiOpenLocationCtrl", new Object[] { "onDenied ", param2String });
                    ApiOpenLocationCtrl.this.mApiHandlerCallback.callback(ApiOpenLocationCtrl.this.mCallBackId, BrandPermissionUtils.systemPermissionErrorMsg(ApiOpenLocationCtrl.this.getActionName()));
                  }
                  
                  public void onGranted() {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthSuccessResult("location"); 
                    try {
                      AppBrandLogger.d("tma_ApiOpenLocationCtrl", new Object[] { "onGranted2" });
                      ApiOpenLocationCtrl.this.openMapActivity(activity);
                      ApiOpenLocationCtrl.this.callbackOk();
                      return;
                    } catch (Exception exception) {
                      AppBrandLogger.e("tma_ApiOpenLocationCtrl", new Object[] { "", exception });
                      ApiOpenLocationCtrl.this.callbackFail(exception);
                      return;
                    } 
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
      this.name = jSONObject.optString("name", "");
      this.address = jSONObject.optString("address", "");
      this.lat = jSONObject.optDouble("latitude", 0.0D);
      this.lon = jSONObject.optDouble("longitude", 0.0D);
      this.scale = jSONObject.optInt("scale", 18);
      double d = this.lat;
      if (d < -90.0D || d > 90.0D) {
        callbackFail("invalid latitude");
        return;
      } 
      d = this.lon;
      if (d < -180.0D || d > 180.0D) {
        callbackFail("invalid longitude");
        return;
      } 
      if (HostDependManager.getInst().createMapInstance() == null) {
        callbackAppUnSupportFeature();
        return;
      } 
      openLocation((Activity)miniappHostBase);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "tma_ApiOpenLocationCtrl", jSONException.getStackTrace());
      callbackFail(a.a(this.mArgs));
      return;
    } 
  }
  
  public String getActionName() {
    return "openLocation";
  }
  
  public void openMapActivity(Activity paramActivity) {
    AppBrandLogger.d("tma_ApiOpenLocationCtrl", new Object[] { "openMapActivity" });
    if (!HostDependManager.getInst().openLocation(paramActivity, this.name, this.address, this.lat, this.lon, this.scale, this.mArgs)) {
      AppBrandLogger.d("tma_ApiOpenLocationCtrl", new Object[] { "startActivity" });
      Intent intent = new Intent((Context)paramActivity, AppbrandMapActivity.class);
      intent.putExtra("name", this.name);
      intent.putExtra("address", this.address);
      intent.putExtra("latitude", this.lat);
      intent.putExtra("longitude", this.lon);
      intent.putExtra("scale", this.scale);
      paramActivity.startActivity(intent);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiOpenLocationCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */