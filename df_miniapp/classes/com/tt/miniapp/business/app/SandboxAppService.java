package com.tt.miniapp.business.app;

import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.b.a;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.streamloader.LoadTask;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapphost.AppbrandApplication;
import org.json.JSONArray;
import org.json.JSONObject;

public class SandboxAppService implements a {
  private final MiniAppContext mContext;
  
  public SandboxAppService(MiniAppContext paramMiniAppContext) {
    this.mContext = paramMiniAppContext;
  }
  
  public JSONArray getApiBlackList() {
    return ApiPermissionManager.getBlackListJsonArray();
  }
  
  public JSONArray getApiWhiteList() {
    return ApiPermissionManager.getWhiteListJsonArray();
  }
  
  public String getAppId() {
    return (getContext().getAppInfo()).appId;
  }
  
  public MiniAppContext getContext() {
    return this.mContext;
  }
  
  public JSONObject getExtConfigInfoJson() {
    return getContext().getAppInfo().getExtConfigInfoJson();
  }
  
  public int getPkgType() {
    LoadTask loadTask = StreamLoader.getLoadTask();
    if (loadTask != null) {
      Boolean bool = loadTask.isUseLocalPkg();
    } else {
      loadTask = null;
    } 
    return (loadTask != null) ? (loadTask.booleanValue() ? 2 : 0) : -1;
  }
  
  public String getPlatformSession() {
    return InnerHostProcessBridge.getPlatformSession(getAppId());
  }
  
  public String getSchema() {
    return AppbrandApplication.getInst().getSchema();
  }
  
  public void onDestroy() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\app\SandboxAppService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */