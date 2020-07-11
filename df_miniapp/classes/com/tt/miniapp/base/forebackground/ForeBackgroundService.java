package com.tt.miniapp.base.forebackground;

import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.g.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.manager.ForeBackgroundManager;
import d.f.b.l;

public final class ForeBackgroundService implements a {
  private final a context;
  
  public ForeBackgroundService(a parama) {
    this.context = parama;
  }
  
  public final a getContext() {
    return this.context;
  }
  
  public final boolean isBackground() {
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
    ForeBackgroundManager foreBackgroundManager = appbrandApplicationImpl.getForeBackgroundManager();
    l.a(foreBackgroundManager, "AppbrandApplicationImpl.…t().foreBackgroundManager");
    return foreBackgroundManager.isBackground();
  }
  
  public final boolean isStayBackgroundOverLimitTime() {
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
    ForeBackgroundManager foreBackgroundManager = appbrandApplicationImpl.getForeBackgroundManager();
    l.a(foreBackgroundManager, "AppbrandApplicationImpl.…t().foreBackgroundManager");
    return foreBackgroundManager.isStayBackgroundOverLimitTime();
  }
  
  public final void onDestroy() {}
  
  public final void registerForeBackgroundListener(a.b paramb) {
    l.b(paramb, "foreBackgroundListener");
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
    appbrandApplicationImpl.getForeBackgroundManager().registerForeBackgroundListener(new ForeBackgroundService$registerForeBackgroundListener$1(paramb));
  }
  
  public static final class ForeBackgroundService$registerForeBackgroundListener$1 implements ForeBackgroundManager.ForeBackgroundListener {
    ForeBackgroundService$registerForeBackgroundListener$1(a.b param1b) {}
    
    public final void onBackground() {
      this.$foreBackgroundListener.b();
    }
    
    public final void onBackgroundOverLimitTime() {}
    
    public final void onForeground() {
      this.$foreBackgroundListener.a();
    }
    
    public final void onTriggerHomeOrRecentApp() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\forebackground\ForeBackgroundService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */