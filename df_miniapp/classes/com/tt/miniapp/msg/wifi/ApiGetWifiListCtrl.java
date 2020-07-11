package com.tt.miniapp.msg.wifi;

import android.app.Activity;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.option.e.e;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class ApiGetWifiListCtrl extends b {
  public ApiGetWifiListCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void getWifiList(final Activity activity) {
    final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(12);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.LOCATION);
    BrandPermissionUtils.requestPermissions(activity, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            PermissionHelper.reportAuthFailResult("location", "mp_reject");
            ApiGetWifiListCtrl.this.callbackFail("auth deny");
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.ACCESS_FINE_LOCATION");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthFailResult("location", "system_reject"); 
                    ApiGetWifiListCtrl.this.callbackFail("system auth deny");
                  }
                  
                  public void onGranted() {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthSuccessResult("location"); 
                    if (AppbrandWifiManager.getInstance().startWifiScan()) {
                      ApiGetWifiListCtrl.this.callbackOk();
                      return;
                    } 
                    ApiGetWifiListCtrl.this.callbackFail("system internal error");
                  }
                });
          }
        }null);
  }
  
  public void act() {
    callbackAppUnSupportFeature();
  }
  
  public String getActionName() {
    return "getWifiList";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\wifi\ApiGetWifiListCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */