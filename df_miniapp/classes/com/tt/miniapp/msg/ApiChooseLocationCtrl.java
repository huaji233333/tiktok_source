package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Intent;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.ChooseLocationResultEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.option.e.e;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class ApiChooseLocationCtrl extends b {
  public ApiChooseLocationCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail("activity is null");
      return;
    } 
    final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(12);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.LOCATION);
    BrandPermissionUtils.requestPermissions((Activity)miniappHostBase, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestPermission)
              PermissionHelper.reportAuthFailResult("location", "mp_reject"); 
            ApiChooseLocationCtrl.this.unRegesterResultHandler();
            ApiChooseLocationCtrl.this.mApiHandlerCallback.callback(ApiChooseLocationCtrl.this.mCallBackId, BrandPermissionUtils.makePermissionErrorMsg(ApiChooseLocationCtrl.this.getActionName()));
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.ACCESS_COARSE_LOCATION");
            hashSet.add("android.permission.ACCESS_FINE_LOCATION");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(activity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthFailResult("location", "system_reject"); 
                    ApiChooseLocationCtrl.this.unRegesterResultHandler();
                    ApiChooseLocationCtrl.this.mApiHandlerCallback.callback(ApiChooseLocationCtrl.this.mCallBackId, BrandPermissionUtils.systemPermissionErrorMsg(ApiChooseLocationCtrl.this.getActionName()));
                  }
                  
                  public void onGranted() {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthSuccessResult("location"); 
                    ApiChooseLocationCtrl.this.chooseLocation(activity);
                  }
                });
          }
        }null);
  }
  
  public void chooseLocation(Activity paramActivity) {
    if (!HostDependManager.getInst().chooseLocationActivity(paramActivity, 13))
      callbackAppUnSupportFeature(); 
  }
  
  public String getActionName() {
    return "chooseLocation";
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    ChooseLocationResultEntity chooseLocationResultEntity = HostDependManager.getInst().handleChooseLocationResult(paramInt1, paramInt2, paramIntent);
    if (chooseLocationResultEntity != null)
      if (chooseLocationResultEntity.isCancel()) {
        callbackFail("cancel");
      } else {
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        String str1 = chooseLocationResultEntity.getName();
        String str2 = chooseLocationResultEntity.getAddress();
        double d1 = chooseLocationResultEntity.getLatitude();
        double d2 = chooseLocationResultEntity.getLongitude();
        if (str1 != null)
          hashMap.put("name", str1); 
        if (str2 != null)
          hashMap.put("address", str2); 
        hashMap.put("latitude", Double.valueOf(d1));
        hashMap.put("longitude", Double.valueOf(d2));
        callbackOk(a.a(hashMap));
      }  
    return super.handleActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public boolean shouldHandleActivityResult() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiChooseLocationCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */