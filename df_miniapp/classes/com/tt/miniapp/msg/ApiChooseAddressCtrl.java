package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Intent;
import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.c;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.option.e.e;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.json.JSONObject;

public class ApiChooseAddressCtrl extends b {
  public Activity activity;
  
  public String addressId = "";
  
  public int mode = -1;
  
  public ApiChooseAddressCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (!HostDependManager.getInst().supportChooseAddress()) {
      callbackAppUnSupportFeature();
      return;
    } 
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      this.addressId = jSONObject.optString("addressId");
      this.mode = jSONObject.optInt("mode", -1);
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiChooseAddressCtrl", new Object[] { exception.getMessage() });
    } 
    this.activity = (Activity)AppbrandContext.getInst().getCurrentActivity();
    if (this.activity == null) {
      callbackFail("activity is null");
      return;
    } 
    final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(15);
    HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
    hashSet.add(BrandPermissionUtils.BrandPermission.ADDRESS);
    BrandPermissionUtils.requestPermissions(this.activity, getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
          public void onDenied(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestPermission)
              PermissionHelper.reportAuthFailResult("address", "mp_reject"); 
            ApiChooseAddressCtrl.this.mApiHandlerCallback.callback(ApiChooseAddressCtrl.this.mCallBackId, BrandPermissionUtils.makePermissionErrorMsg(ApiChooseAddressCtrl.this.getActionName()));
          }
          
          public void onGranted(LinkedHashMap<Integer, String> param1LinkedHashMap) {
            if (!hasRequestPermission)
              PermissionHelper.reportAuthSuccessResult("address"); 
            if (ApiChooseAddressCtrl.this.isLogin()) {
              HostDependManager.getInst().openChooseAddressActivity(ApiChooseAddressCtrl.this.activity, ApiChooseAddressCtrl.this.mode, ApiChooseAddressCtrl.this.addressId);
              return;
            } 
            HostDependManager.getInst().openLoginActivity(ApiChooseAddressCtrl.this.activity, null);
          }
        }null);
  }
  
  public String getActionName() {
    return "chooseAddress";
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (HostDependManager.getInst().handleActivityLoginResult(paramInt1, paramInt2, paramIntent)) {
      if (isLogin()) {
        (c.a()).b = this;
        HostDependManager.getInst().openChooseAddressActivity(this.activity, this.mode, this.addressId);
      } else {
        callbackFail("");
      } 
    } else if (HostDependManager.getInst().handleActivityChooseAddressResult(paramInt1, paramInt2, paramIntent, new ChooseAddressListener() {
          public void onChooseAddressFail(String param1String) {
            ApiChooseAddressCtrl.this.callbackFail(param1String);
          }
          
          public void onChooseAddressSuccess(JSONObject param1JSONObject) {
            ApiChooseAddressCtrl.this.callbackOk(param1JSONObject);
          }
        })) {
      (c.a()).b = null;
    } else {
      callbackFail("");
      (c.a()).b = null;
    } 
    return super.handleActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public boolean isLogin() {
    CrossProcessDataEntity crossProcessDataEntity = HostProcessBridge.getUserInfo();
    if (crossProcessDataEntity != null) {
      UserInfoManager.UserInfo userInfo = new UserInfoManager.UserInfo(crossProcessDataEntity);
    } else {
      crossProcessDataEntity = null;
    } 
    return (crossProcessDataEntity != null && ((UserInfoManager.UserInfo)crossProcessDataEntity).isLogin);
  }
  
  public boolean shouldHandleActivityResult() {
    return true;
  }
  
  public static interface ChooseAddressListener {
    void onChooseAddressFail(String param1String);
    
    void onChooseAddressSuccess(JSONObject param1JSONObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiChooseAddressCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */