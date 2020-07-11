package com.tt.miniapp.service;

import com.tt.miniapp.service.bgaudio.BgAudioService;
import com.tt.miniapp.service.bgaudio.BgAudioServiceInterface;
import com.tt.miniapp.service.codecache.CodeCacheService;
import com.tt.miniapp.service.codecache.CodeCacheServiceImpl;
import com.tt.miniapp.service.hostevent.HostEventMiniAppService;
import com.tt.miniapp.service.hostevent.HostEventService;
import com.tt.miniapp.service.netconfig.AppbrandNetConfigService;
import com.tt.miniapp.service.netconfig.AppbrandNetConfigServiceImpl;
import com.tt.miniapphost.util.DebugUtil;
import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
  private Map<Class, ServiceCreator> mServiceMap = (Map)new HashMap<Class<?>, ServiceCreator>();
  
  private ServiceProvider() {
    this.mServiceMap.put(BgAudioServiceInterface.class, new ServiceCreator() {
          private BgAudioService mService = new BgAudioService();
          
          public PureServiceInterface createService() {
            return (PureServiceInterface)this.mService;
          }
        });
    this.mServiceMap.put(HostEventService.class, new ServiceCreator() {
          private HostEventService mService = new HostEventService();
          
          public PureServiceInterface createService() {
            return (PureServiceInterface)this.mService;
          }
        });
    this.mServiceMap.put(HostEventMiniAppService.class, new ServiceCreator() {
          private HostEventMiniAppService mService = new HostEventMiniAppService();
          
          public PureServiceInterface createService() {
            return (PureServiceInterface)this.mService;
          }
        });
    this.mServiceMap.put(AppbrandNetConfigService.class, new ServiceCreator() {
          private AppbrandNetConfigService mService = (AppbrandNetConfigService)new AppbrandNetConfigServiceImpl();
          
          public PureServiceInterface createService() {
            return (PureServiceInterface)this.mService;
          }
        });
    this.mServiceMap.put(CodeCacheService.class, new ServiceCreator() {
          private CodeCacheService mService = (CodeCacheService)new CodeCacheServiceImpl();
          
          public PureServiceInterface createService() {
            return (PureServiceInterface)this.mService;
          }
        });
  }
  
  public static ServiceProvider getInstance() {
    return Holder.sServiceProvider;
  }
  
  public <T extends PureServiceInterface> T getService(Class<T> paramClass) {
    StringBuilder stringBuilder;
    ServiceCreator serviceCreator = this.mServiceMap.get(paramClass);
    if (serviceCreator == null) {
      stringBuilder = new StringBuilder();
      stringBuilder.append(paramClass);
      stringBuilder.append("没有对应的实现类");
      DebugUtil.outputError("ServiceProvider", new Object[] { stringBuilder.toString() });
      return null;
    } 
    return (T)stringBuilder.createService();
  }
  
  static class Holder {
    static ServiceProvider sServiceProvider = new ServiceProvider();
  }
  
  static interface ServiceCreator {
    PureServiceInterface createService();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\ServiceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */