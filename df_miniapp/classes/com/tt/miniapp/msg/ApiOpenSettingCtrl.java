package com.tt.miniapp.msg;

import android.content.Context;
import android.content.Intent;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionSettingActivity;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiOpenSettingCtrl extends b {
  private JSONObject mJSONObject;
  
  public ApiOpenSettingCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void reportRecordScreen(boolean paramBoolean) {
    if (AppbrandApplication.getInst().getAppInfo().isGame())
      InnerEventHelper.mpAuthoritySetting(paramBoolean); 
  }
  
  public void act() {
    this.mJSONObject = new JSONObject();
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail("activity is null");
      return;
    } 
    miniappHostBase.startActivityForResult(PermissionSettingActivity.genIntent((Context)miniappHostBase), 6);
    try {
      HostDependManager.getInst().getLocalScope(this.mJSONObject);
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      AppBrandLogger.e("tma_ApiOpenSettingCtrl", new Object[] { exception });
      return;
    } 
  }
  
  public String getActionName() {
    return "openSetting";
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    AppBrandLogger.d("tma_ApiOpenSettingCtrl", new Object[] { "handleActivityResult ", Integer.valueOf(paramInt1), " ", Integer.valueOf(paramInt2) });
    if (paramInt1 == 6 && paramInt2 == 51 && paramIntent != null) {
      Map map = (Map)paramIntent.getSerializableExtra("extra_change_permission_map");
      if (map != null && map.size() > 0) {
        for (Map.Entry entry : map.entrySet()) {
          BrandPermissionUtils.setPermission(((Integer)entry.getKey()).intValue(), ((Boolean)entry.getValue()).booleanValue());
          if (this.mJSONObject != null) {
            AppBrandLogger.d("tma_ApiOpenSettingCtrl", new Object[] { "change permission ", entry.getKey(), " ", entry.getValue(), " ", this.mJSONObject.toString() });
            try {
              HostDependManager.getInst().handleCustomizePermissionResult(this.mJSONObject, ((Integer)entry.getKey()).intValue(), ((Boolean)entry.getValue()).booleanValue());
            } catch (Exception exception) {
              AppBrandLogger.e("tma_ApiOpenSettingCtrl", new Object[] { exception });
            } 
          } 
        } 
        HostDependManager.getInst().syncPermissionToService();
        try {
          JSONObject jSONObject = (new JSONObject()).put("authSetting", this.mJSONObject);
          AppBrandLogger.d("tma_ApiOpenSettingCtrl", new Object[] { "extraDataJO ", jSONObject });
          callbackOk(jSONObject);
        } catch (JSONException jSONException) {
          AppBrandLogger.e("tma_ApiOpenSettingCtrl", new Object[] { jSONException });
          callbackFail((Throwable)jSONException);
        } 
      } else {
        callbackFail("permission map is empty");
      } 
      reportRecordScreen(this.mJSONObject.optBoolean("scope.screenRecord", true));
      this.mJSONObject = null;
      return true;
    } 
    if (this.mJSONObject != null) {
      try {
        JSONObject jSONObject = (new JSONObject()).put("authSetting", this.mJSONObject);
        AppBrandLogger.d("tma_ApiOpenSettingCtrl", new Object[] { "extraDataJO ", jSONObject });
        callbackOk(jSONObject);
      } catch (JSONException jSONException) {
        AppBrandLogger.e("tma_ApiOpenSettingCtrl", new Object[] { jSONException });
        callbackFail((Throwable)jSONException);
      } 
      if (paramInt1 == 6)
        reportRecordScreen(this.mJSONObject.optBoolean("scope.screenRecord", true)); 
      this.mJSONObject = null;
    } 
    return false;
  }
  
  public boolean shouldHandleActivityResult() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiOpenSettingCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */