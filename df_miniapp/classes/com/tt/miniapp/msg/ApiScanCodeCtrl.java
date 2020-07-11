package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Intent;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.ScanResultEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.option.e.e;
import com.tt.option.n.b;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiScanCodeCtrl extends b {
  public ApiScanCodeCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    final MiniappHostBase currentActivity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail(a.c("currentActivity"));
      return;
    } 
    final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(14);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.CAMERA);
    BrandPermissionUtils.requestPermissions((Activity)miniappHostBase, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestPermission)
              PermissionHelper.reportAuthFailResult("camera", "mp_reject"); 
            ApiScanCodeCtrl.this.mApiHandlerCallback.callback(ApiScanCodeCtrl.this.mCallBackId, BrandPermissionUtils.makePermissionErrorMsg(ApiScanCodeCtrl.this.getActionName()));
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.CAMERA");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(currentActivity, hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthFailResult("camera", "system_reject"); 
                    ApiScanCodeCtrl.this.mApiHandlerCallback.callback(ApiScanCodeCtrl.this.mCallBackId, BrandPermissionUtils.systemPermissionErrorMsg(ApiScanCodeCtrl.this.getActionName()));
                  }
                  
                  public void onGranted() {
                    if (!hasRequestPermission)
                      PermissionHelper.reportAuthSuccessResult("camera"); 
                    ApiScanCodeCtrl.this.doScanCode(currentActivity);
                  }
                });
          }
        }null);
  }
  
  public void doScanCode(Activity paramActivity) {
    if (!HostDependManager.getInst().scanCode(paramActivity, new b.d() {
          public void onScanResult(String param1String1, String param1String2) {
            JSONObject jSONObject = new JSONObject();
            try {
              jSONObject.put("result", param1String1);
              jSONObject.put("scanType", param1String2);
            } catch (JSONException jSONException) {
              AppBrandLogger.e("tma_ApiScanCodeCtrl", new Object[] { "doScanCode", jSONException.getStackTrace() });
            } 
            ApiScanCodeCtrl.this.callbackOk(jSONObject);
          }
        }))
      callbackAppUnSupportFeature(); 
  }
  
  public String getActionName() {
    return "scanCode";
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    AppBrandLogger.d("tma_ApiScanCodeCtrl", new Object[] { "scan code handleActivityResult" });
    if (paramIntent == null) {
      callbackFail("cancel");
    } else {
      ScanResultEntity scanResultEntity = HostDependManager.getInst().handleActivityScanResult(paramInt1, paramInt2, paramIntent);
      if (scanResultEntity.isShouldHandle())
        if (scanResultEntity.getResultType() == 1) {
          callbackFail("cancel");
        } else {
          try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("result", scanResultEntity.getResult());
            jSONObject.put("scanType", scanResultEntity.getScanType());
            AppBrandLogger.d("tma_ApiScanCodeCtrl", new Object[] { "result ", scanResultEntity.getResult(), " scanType ", scanResultEntity.getScanType() });
            callbackOk(jSONObject);
            return true;
          } catch (JSONException jSONException) {
            callbackFail((Throwable)jSONException);
          } 
        }  
    } 
    return super.handleActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public boolean shouldHandleActivityResult() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiScanCodeCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */