package com.tt.miniapp.msg;

import android.app.Activity;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.option.e.e;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiServiceGetPhoneNumberCtrl extends b {
  public boolean mBindPhoneNumberBeforeAction = true;
  
  private boolean mDisableBindPhoneNumber;
  
  public ApiServiceGetPhoneNumberCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (ApiPermissionManager.intercept(getActionName(), this.mCallBackId))
      return; 
    try {
      this.mDisableBindPhoneNumber = (new JSONObject(this.mArgs)).optBoolean("disableBindPhoneNumber");
    } catch (JSONException jSONException) {}
    requestGetPhoneNumber(true);
  }
  
  public String getActionName() {
    return "_serviceGetPhoneNumber";
  }
  
  public void requestGetPhoneNumber(boolean paramBoolean) {
    UserInfoManager.requestGetBindPhoneNumber(paramBoolean, this.mDisableBindPhoneNumber, new UserInfoManager.GetBindPhoneListener() {
          public void onFail(int param1Int) {
            if (param1Int != 1) {
              if (param1Int != 3) {
                if (param1Int != 4) {
                  if (param1Int == 5)
                    ApiServiceGetPhoneNumberCtrl.this.callbackFail("not bind phone number"); 
                  ApiServiceGetPhoneNumberCtrl.this.callbackFail("unKnow error type!");
                  return;
                } 
                ApiServiceGetPhoneNumberCtrl.this.callbackFail("not login");
                return;
              } 
              ApiServiceGetPhoneNumberCtrl.this.callbackFail("platform auth deny");
              return;
            } 
            ApiServiceGetPhoneNumberCtrl.this.callbackFail("internal error");
          }
          
          public void onSuccess(String param1String1, final String encryptedData, final String iv) {
            final String bindStatusBeforeAction;
            if (ApiServiceGetPhoneNumberCtrl.this.mBindPhoneNumberBeforeAction) {
              str = "True";
            } else {
              str = "False";
            } 
            Event.builder("mp_auth_page_show").kv("bind_status_before_action", str).flush();
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("phoneNumber", param1String1);
            HashSet<BrandPermissionUtils.BrandPermission> hashSet = new HashSet();
            hashSet.add(BrandPermissionUtils.BrandPermission.PHONE_NUMBER);
            final boolean hasRequestPermission = BrandPermissionUtils.hasRequestPermission(16);
            BrandPermissionUtils.requestPermissions((Activity)AppbrandContext.getInst().getCurrentActivity(), ApiServiceGetPhoneNumberCtrl.this.getActionName(), hashSet, new LinkedHashMap<Object, Object>(), new IPermissionsRequestCallback() {
                  public void onDenied(LinkedHashMap<Integer, String> param2LinkedHashMap) {
                    if (hasRequestPermission)
                      PermissionHelper.reportAuthFailResult("phone_num", "mp_reject"); 
                    Event.builder("mp_auth_page_result").kv("bind_status_before_action", bindStatusBeforeAction).kv("result_type", "close").flush();
                    ApiServiceGetPhoneNumberCtrl.this.callbackFail("auth deny");
                  }
                  
                  public void onGranted(LinkedHashMap<Integer, String> param2LinkedHashMap) {
                    if (hasRequestPermission)
                      PermissionHelper.reportAuthSuccessResult("phone_num"); 
                    Event.builder("mp_auth_page_result").kv("bind_status_before_action", bindStatusBeforeAction).kv("result_type", "success").flush();
                    JSONObject jSONObject2 = new JSONObject();
                    JSONObject jSONObject1 = new JSONObject();
                    try {
                      jSONObject2.put("iv", iv);
                      jSONObject2.put("encryptedData", encryptedData);
                      jSONObject1.put("detail", jSONObject2);
                    } catch (JSONException jSONException) {
                      AppBrandLogger.e("tma_ApiServiceGetPhoneNumberCtrl", new Object[] { "onGranted", jSONException });
                    } 
                    ApiServiceGetPhoneNumberCtrl.this.callbackOk(jSONObject1);
                  }
                }hashMap);
          }
          
          public void onUnbindPhoneNumber() {
            ApiServiceGetPhoneNumberCtrl.this.mBindPhoneNumberBeforeAction = false;
            UserInfoManager.requestBindPhoneNumber(new UserInfoManager.BindPhoneListener() {
                  public void onFail(int param2Int) {
                    if (param2Int != 2) {
                      ApiServiceGetPhoneNumberCtrl.this.callbackFail("auth deny");
                      return;
                    } 
                    ApiServiceGetPhoneNumberCtrl.this.callbackAppUnSupportFeature();
                  }
                  
                  public void onSuccess() {
                    ApiServiceGetPhoneNumberCtrl.this.requestGetPhoneNumber(false);
                  }
                });
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiServiceGetPhoneNumberCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */