package com.tt.miniapp.business.permission;

import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.l.a;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.IAppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.l;

public final class PermissionService implements a {
  private final a context;
  
  public PermissionService(a parama) {
    this.context = parama;
  }
  
  public final a getContext() {
    return this.context;
  }
  
  public final boolean isSafeDomain(String paramString1, String paramString2) {
    l.b(paramString1, "type");
    l.b(paramString2, "url");
    if (paramString1.hashCode() == -1411064585 && paramString1.equals("appids")) {
      IAppbrandApplication iAppbrandApplication = AppbrandApplication.getInst();
      l.a(iAppbrandApplication, "AppbrandApplication.getInst()");
      AppInfoEntity appInfoEntity = iAppbrandApplication.getAppInfo();
      l.a(appInfoEntity, "AppbrandApplication.getInst().appInfo");
      if (appInfoEntity.isWhite())
        return true; 
    } 
    return NetUtil.isSafeDomain(paramString1, paramString2);
  }
  
  public final void onDestroy() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\permission\PermissionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */