package com.tt.miniapp.business.host;

import android.content.Intent;
import android.net.Uri;
import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.h.a;
import com.bytedance.sandboxapp.protocol.service.h.b;
import com.bytedance.sandboxapp.protocol.service.h.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.base.activity.ActivityServiceInterface;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.ProcessUtil;
import d.f.b.l;

public final class HostService implements c {
  private final MiniAppContext context;
  
  private a mCacheHostAppInfo;
  
  public HostService(MiniAppContext paramMiniAppContext) {
    this.context = paramMiniAppContext;
  }
  
  public final MiniAppContext getContext() {
    return this.context;
  }
  
  public final a getHostAppInfo() {
    a a2 = this.mCacheHostAppInfo;
    if (a2 != null)
      return a2; 
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    InitParamsEntity initParamsEntity = appbrandContext.getInitParams();
    l.a(initParamsEntity, "initParams");
    a a1 = new a(initParamsEntity.getAppId(), initParamsEntity.getChannel(), initParamsEntity.getVersionCode(), initParamsEntity.getUpdateVersionCode());
    this.mCacheHostAppInfo = a1;
    return a1;
  }
  
  public final b getHostAppUserInfo() {
    UserInfoManager.UserInfo userInfo = UserInfoManager.getHostClientUserInfo();
    l.a(userInfo, "UserInfoManager.getHostClientUserInfo()");
    return new b(userInfo.userId, userInfo.secUID, userInfo.isLogin);
  }
  
  public final void loginHostApp(c.a parama) {
    l.b(parama, "hostAppLoginListener");
    UserInfoManager.requestLoginHostClient(new WrapperHostClientLoginListener(parama), null, null);
  }
  
  public final void onDestroy() {}
  
  public final void openMiniApp(c.b paramb) {
    String str2;
    l.b(paramb, "openMiniAppEntity");
    String str3 = paramb.a;
    MicroSchemaEntity microSchemaEntity = MicroSchemaEntity.parseFromSchema(str3);
    boolean bool = paramb.c;
    String str4 = "1";
    if (bool) {
      str2 = "1";
    } else {
      str2 = "0";
    } 
    microSchemaEntity.addCustomField("killCurrentProcess", str2);
    if (paramb.d) {
      str2 = str4;
    } else {
      str2 = "0";
    } 
    microSchemaEntity.addCustomField("forceColdBoot", str2);
    microSchemaEntity.addCustomField("toolbarStyle", String.valueOf(paramb.e));
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    ActivityUtil.previousGetSnapshot(appbrandContext.getCurrentActivity());
    str4 = microSchemaEntity.toSchema();
    String str1 = str3;
    if (str4 != null)
      str1 = str4; 
    bool = paramb.b;
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
    InnerHostProcessBridge.jumpToAppFromSchema(str1, bool, (appbrandApplicationImpl.getAppInfo()).appId);
    appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
    appbrandApplicationImpl.getForeBackgroundManager().pauseBackgroundOverLimitTimeStrategy();
  }
  
  public final void openSchema(c.c paramc, c.d paramd) {
    l.b(paramc, "openSchemaEntity");
    Uri uri = paramc.a;
    Uri.Builder builder = uri.buildUpon();
    String str = uri.getQueryParameter("launchflag");
    ProcessUtil.fillCrossProcessCallbackUri(builder, new HostService$openSchema$1(paramd, "hostProcess"));
    HostDependManager hostDependManager = HostDependManager.getInst();
    l.a(hostDependManager, "HostDependManager.getInst()");
    if (hostDependManager.isEnableOpenSchemaAnimation()) {
      String str1;
      LocaleManager localeManager = LocaleManager.getInst();
      l.a(localeManager, "LocaleManager.getInst()");
      if (localeManager.isRTL()) {
        str1 = "1";
      } else {
        str1 = "0";
      } 
      builder.appendQueryParameter("isNeedRTLAnim", str1);
    } 
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    ActivityUtil.startOpenSchemaActivity(appbrandContext.getCurrentActivity(), builder.toString(), str, paramc.b);
  }
  
  public final class WrapperHostClientLoginListener implements UserInfoManager.HostClientLoginListener {
    private final c.a hostAppLoginListener;
    
    public WrapperHostClientLoginListener(c.a param1a) {
      this.hostAppLoginListener = param1a;
    }
    
    public final boolean handleHostClientLoginResult(int param1Int1, int param1Int2, Intent param1Intent) {
      return UserInfoManager.handleHostClientLoginResult(param1Int1, param1Int2, param1Intent, this);
    }
    
    public final void onLoginFail() {
      this.hostAppLoginListener.a("loginHostFail");
    }
    
    public final void onLoginSuccess() {
      this.hostAppLoginListener.a();
    }
    
    public final void onLoginUnSupport() {
      this.hostAppLoginListener.c();
    }
    
    public final void onLoginWhenBackground() {
      this.hostAppLoginListener.b();
    }
    
    public final void onTriggerHostClientLogin(String param1String) {
      ((ActivityServiceInterface)HostService.this.getContext().getService(ActivityServiceInterface.class)).registerActivityResultHandler(new HostService$WrapperHostClientLoginListener$onTriggerHostClientLogin$1());
    }
    
    public static final class HostService$WrapperHostClientLoginListener$onTriggerHostClientLogin$1 implements IActivityResultHandler {
      public final boolean autoClearAfterActivityResult() {
        return true;
      }
      
      public final boolean handleActivityResult(int param2Int1, int param2Int2, Intent param2Intent) {
        return HostService.WrapperHostClientLoginListener.this.handleHostClientLoginResult(param2Int1, param2Int2, param2Intent);
      }
    }
  }
  
  public static final class HostService$WrapperHostClientLoginListener$onTriggerHostClientLogin$1 implements IActivityResultHandler {
    public final boolean autoClearAfterActivityResult() {
      return true;
    }
    
    public final boolean handleActivityResult(int param1Int1, int param1Int2, Intent param1Intent) {
      return HostService.WrapperHostClientLoginListener.this.handleHostClientLoginResult(param1Int1, param1Int2, param1Intent);
    }
  }
  
  public static final class HostService$openSchema$1 extends IpcCallback {
    HostService$openSchema$1(c.d param1d, String param1String) {
      super(param1String);
    }
    
    public final void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
      Integer integer1;
      Integer integer2;
      Integer integer3 = null;
      if (param1CrossProcessDataEntity != null) {
        Boolean bool2 = Boolean.valueOf(param1CrossProcessDataEntity.getBoolean("openSchemaResult"));
        integer3 = Integer.valueOf(param1CrossProcessDataEntity.getInt("openSchemaFailType"));
        Boolean bool1 = bool2;
        integer2 = integer3;
      } else {
        integer2 = null;
        integer1 = integer3;
      } 
      if (integer1 != null && integer1.booleanValue()) {
        c.d d1 = this.$openSchemaListener;
        if (d1 != null)
          d1.a(); 
      } else if (integer2 != null && integer2.intValue() == 1) {
        c.d d1 = this.$openSchemaListener;
        if (d1 != null)
          d1.b(); 
      } else {
        c.d d1 = this.$openSchemaListener;
        if (d1 != null)
          d1.a("unknown error"); 
      } 
      finishListenIpcCallback();
    }
    
    public final void onIpcConnectError() {
      c.d d1 = this.$openSchemaListener;
      if (d1 != null)
        d1.a("ipc fail"); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\host\HostService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */