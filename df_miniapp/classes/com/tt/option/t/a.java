package com.tt.option.t;

import android.app.Activity;
import com.tt.miniapp.impl.HostOptionPermissionDependImpl;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.option.a;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public final class a extends a<b> implements b {
  public final Set<BrandPermissionUtils.BrandPermission> filterNeedRequestPermission(String paramString, Set<BrandPermissionUtils.BrandPermission> paramSet) {
    return inject() ? ((b)this.defaultOptionDepend).filterNeedRequestPermission(paramString, paramSet) : null;
  }
  
  public final void getLocalScope(JSONObject paramJSONObject) throws JSONException {
    if (inject())
      ((b)this.defaultOptionDepend).getLocalScope(paramJSONObject); 
  }
  
  public final c getPermissionCustomDialogMsgEntity() {
    return inject() ? ((b)this.defaultOptionDepend).getPermissionCustomDialogMsgEntity() : null;
  }
  
  public final List<BrandPermissionUtils.BrandPermission> getUserDefinableHostPermissionList() {
    return inject() ? ((b)this.defaultOptionDepend).getUserDefinableHostPermissionList() : null;
  }
  
  public final void handleCustomizePermissionResult(JSONObject paramJSONObject, int paramInt, boolean paramBoolean) throws JSONException {
    if (inject())
      ((b)this.defaultOptionDepend).handleCustomizePermissionResult(paramJSONObject, paramInt, paramBoolean); 
  }
  
  public final void metaExtraNotify(String paramString1, String paramString2) {
    if (inject())
      ((b)this.defaultOptionDepend).metaExtraNotify(paramString1, paramString2); 
  }
  
  public final void onDeniedWhenHasRequested(Activity paramActivity, String paramString) {
    if (inject())
      ((b)this.defaultOptionDepend).onDeniedWhenHasRequested(paramActivity, paramString); 
  }
  
  public final BrandPermissionUtils.BrandPermission permissionTypeToPermission(int paramInt) {
    return inject() ? ((b)this.defaultOptionDepend).permissionTypeToPermission(paramInt) : null;
  }
  
  public final void savePermissionGrant(int paramInt, boolean paramBoolean) {
    if (inject())
      ((b)this.defaultOptionDepend).savePermissionGrant(paramInt, paramBoolean); 
  }
  
  public final BrandPermissionUtils.BrandPermission scopeToBrandPermission(String paramString) {
    return inject() ? ((b)this.defaultOptionDepend).scopeToBrandPermission(paramString) : null;
  }
  
  public final void setPermissionTime(int paramInt) {
    if (inject())
      ((b)this.defaultOptionDepend).setPermissionTime(paramInt); 
  }
  
  public final void syncPermissionToService() {
    if (inject())
      ((b)this.defaultOptionDepend).syncPermissionToService(); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\t\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */