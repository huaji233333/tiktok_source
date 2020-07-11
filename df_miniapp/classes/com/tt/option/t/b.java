package com.tt.option.t;

import android.app.Activity;
import com.tt.miniapp.permission.BrandPermissionUtils;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public interface b {
  Set<BrandPermissionUtils.BrandPermission> filterNeedRequestPermission(String paramString, Set<BrandPermissionUtils.BrandPermission> paramSet);
  
  void getLocalScope(JSONObject paramJSONObject) throws JSONException;
  
  c getPermissionCustomDialogMsgEntity();
  
  List<BrandPermissionUtils.BrandPermission> getUserDefinableHostPermissionList();
  
  void handleCustomizePermissionResult(JSONObject paramJSONObject, int paramInt, boolean paramBoolean) throws JSONException;
  
  void metaExtraNotify(String paramString1, String paramString2);
  
  void onDeniedWhenHasRequested(Activity paramActivity, String paramString);
  
  BrandPermissionUtils.BrandPermission permissionTypeToPermission(int paramInt);
  
  void savePermissionGrant(int paramInt, boolean paramBoolean);
  
  BrandPermissionUtils.BrandPermission scopeToBrandPermission(String paramString);
  
  void setPermissionTime(int paramInt);
  
  void syncPermissionToService();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\t\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */