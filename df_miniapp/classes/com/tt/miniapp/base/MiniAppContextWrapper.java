package com.tt.miniapp.base;

import android.app.Activity;
import android.content.Context;
import com.bytedance.sandboxapp.b.a.a;
import com.bytedance.sandboxapp.b.a.a.a;
import com.bytedance.sandboxapp.b.a.a.b;
import com.bytedance.sandboxapp.b.a.b.a;
import com.bytedance.sandboxapp.b.a.b.b;
import com.bytedance.sandboxapp.b.b;
import com.bytedance.sandboxapp.c.a.a;
import com.bytedance.sandboxapp.protocol.service.a.a.a;
import com.bytedance.sandboxapp.protocol.service.api.a;
import com.bytedance.sandboxapp.protocol.service.b.a;
import com.bytedance.sandboxapp.protocol.service.c.a;
import com.bytedance.sandboxapp.protocol.service.d.a;
import com.bytedance.sandboxapp.protocol.service.e.b;
import com.bytedance.sandboxapp.protocol.service.f.a;
import com.bytedance.sandboxapp.protocol.service.g.a;
import com.bytedance.sandboxapp.protocol.service.h.c;
import com.bytedance.sandboxapp.protocol.service.i.a;
import com.bytedance.sandboxapp.protocol.service.j.a;
import com.bytedance.sandboxapp.protocol.service.k.a;
import com.bytedance.sandboxapp.protocol.service.l.a;
import com.bytedance.sandboxapp.protocol.service.m.a;
import com.bytedance.sandboxapp.protocol.service.n.a;
import com.bytedance.sandboxapp.protocol.service.n.b;
import com.bytedance.sandboxapp.protocol.service.request.a;
import com.tt.miniapp.base.activity.ActivityService;
import com.tt.miniapp.base.activity.ActivityServiceInterface;
import com.tt.miniapp.base.file.FileService;
import com.tt.miniapp.base.forebackground.ForeBackgroundService;
import com.tt.miniapp.base.identifier.IdentifierService;
import com.tt.miniapp.base.netrequest.NetRequestService;
import com.tt.miniapp.base.report.AppLogReportService;
import com.tt.miniapp.base.report.MonitorReportService;
import com.tt.miniapp.business.ad.site.AdSiteService;
import com.tt.miniapp.business.app.SandboxAppService;
import com.tt.miniapp.business.aweme.AwemeService;
import com.tt.miniapp.business.cloud.LarkCloudService;
import com.tt.miniapp.business.component.video.MediaService;
import com.tt.miniapp.business.device.DeviceService;
import com.tt.miniapp.business.host.HostService;
import com.tt.miniapp.business.pay.PayService;
import com.tt.miniapp.business.permission.PermissionService;
import com.tt.miniapp.business.render.RenderService;
import com.tt.miniapp.service.suffixmeta.SuffixMetaServiceInterface;
import com.tt.miniapp.suffixmeta.SuffixMetaService;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.DebugUtil;
import d.f.b.l;
import java.util.HashMap;
import java.util.Map;

public class MiniAppContextWrapper implements MiniAppContext {
  private IActivityFetcher mActivityFetcher;
  
  private Context mApplicationContext;
  
  private boolean mDestroyed;
  
  private Map<Class, b> mServiceMap;
  
  private MiniAppContextWrapper(IActivityFetcher paramIActivityFetcher, Context paramContext) {
    this.mActivityFetcher = paramIActivityFetcher;
    this.mApplicationContext = paramContext;
    initServiceMap();
    configAbility();
  }
  
  private void configAbility() {
    a a1 = new a() {
        public a getDebugger() {
          return new a() {
              public boolean isDebugMode() {
                return DebugUtil.debug();
              }
              
              public void logOrThrow(String param2String, Object... param2VarArgs) {
                DebugUtil.logOrThrow(param2String, param2VarArgs);
              }
              
              public void logOrToast(String param2String, Object... param2VarArgs) {
                DebugUtil.outputError(param2String, param2VarArgs);
              }
            };
        }
        
        public a getLogger() {
          return new a() {
              public void d(String param2String, Object... param2VarArgs) {
                AppBrandLogger.d(param2String, param2VarArgs);
              }
              
              public void e(String param2String, Object... param2VarArgs) {
                AppBrandLogger.e(param2String, param2VarArgs);
              }
              
              public void i(String param2String, Object... param2VarArgs) {
                AppBrandLogger.i(param2String, param2VarArgs);
              }
              
              public void w(String param2String, Object... param2VarArgs) {
                AppBrandLogger.w(param2String, param2VarArgs);
              }
            };
        }
      };
    l.b(a1, "abilityDeclarer");
    a a2 = a1.getLogger();
    l.b(a2, "ability");
    b.a = a2;
    a a = a1.getDebugger();
    l.b(a, "ability");
    b.a = a;
    b.a(a.isDebugMode());
  }
  
  private void initServiceMap() {
    this.mServiceMap = (Map)new HashMap<Class<?>, b>();
    this.mServiceMap.put(a.class, new a(this));
    this.mServiceMap.put(a.class, new SandboxAppService(this));
    this.mServiceMap.put(a.class, new NetRequestService(this));
    this.mServiceMap.put(ActivityServiceInterface.class, new ActivityService(this));
    this.mServiceMap.put(SuffixMetaServiceInterface.class, new SuffixMetaService(this));
    this.mServiceMap.put(a.class, new PayService(this));
    this.mServiceMap.put(c.class, new HostService(this));
    this.mServiceMap.put(b.class, new DeviceService(this));
    this.mServiceMap.put(a.class, new MediaService(this));
    this.mServiceMap.put(a.class, new RenderService(this));
    this.mServiceMap.put(a.class, new PermissionService(this));
    this.mServiceMap.put(a.class, new IdentifierService(this));
    this.mServiceMap.put(b.class, new MonitorReportService(this));
    this.mServiceMap.put(a.class, new AppLogReportService(this));
    this.mServiceMap.put(a.class, new ForeBackgroundService(this));
    this.mServiceMap.put(a.class, new FileService(this));
    this.mServiceMap.put(a.class, new LarkCloudService(this));
    this.mServiceMap.put(a.class, new AdSiteService(this));
    this.mServiceMap.put(a.class, new AwemeService(this));
  }
  
  public void destroy() {
    this.mDestroyed = true;
    this.mServiceMap.clear();
  }
  
  public AppInfoEntity getAppInfo() {
    return AppbrandApplication.getInst().getAppInfo();
  }
  
  public Context getApplicationContext() {
    return this.mApplicationContext;
  }
  
  public Activity getCurrentActivity() {
    return this.mActivityFetcher.getCurrentActivity();
  }
  
  public <T extends b> T getService(Class<T> paramClass) {
    if (this.mDestroyed)
      DebugUtil.logOrThrow("MiniAppContextWrapper", new Object[] { "当前小程序上下文已被销毁" }); 
    if (DebugUtil.debug() && paramClass.getInterfaces()[0] != b.class)
      DebugUtil.logOrThrow("MiniAppContextWrapper", new Object[] { "获取 Service 的时候必须使用接口获取" }); 
    b b = this.mServiceMap.get(paramClass);
    if (b == null)
      DebugUtil.logOrThrow("MiniAppContextWrapper", new Object[] { "当前 Service 未注册实例，如果是当前运行环境不需要实现此 Service，请为其添加空实现" }); 
    return (T)b;
  }
  
  public static class Builder {
    private final IActivityFetcher mActivityFetcher;
    
    private final Context mApplicationContext;
    
    private Builder(IActivityFetcher param1IActivityFetcher, Context param1Context) {
      this.mActivityFetcher = param1IActivityFetcher;
      this.mApplicationContext = param1Context;
    }
    
    public static Builder create(IActivityFetcher param1IActivityFetcher, Context param1Context) {
      return new Builder(param1IActivityFetcher, param1Context);
    }
    
    public MiniAppContextWrapper build() {
      return new MiniAppContextWrapper(this.mActivityFetcher, this.mApplicationContext);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\MiniAppContextWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */