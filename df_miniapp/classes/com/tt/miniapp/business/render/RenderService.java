package com.tt.miniapp.business.render;

import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.m.a;
import com.tt.miniapp.service.suffixmeta.SuffixMetaServiceInterface;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.view.webcore.NestWebView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.DebugUtil;
import d.f.b.l;
import d.u;

public final class RenderService implements a {
  private final boolean DEFAULT_USE_WEB_VIDEO;
  
  private final String TAG;
  
  private final a context;
  
  private Boolean mUseWebLivePlayerWhenRenderInBrowser;
  
  private Boolean mUseWebVideoWhenNotRenderInBrowser;
  
  public RenderService(a parama) {
    this.context = parama;
    this.TAG = "RenderService";
  }
  
  public final a getContext() {
    return this.context;
  }
  
  public final boolean isRenderInBrowser() {
    if (!NestWebView.hasSetRenderInBrowser()) {
      DebugUtil.logOrThrow(this.TAG, new Object[] { "not set render in browser", new Throwable() });
      return false;
    } 
    return NestWebView.isRenderInBrowserEnabled();
  }
  
  public final void onDestroy() {}
  
  public final boolean useWebLivePlayer() {
    if (SettingsDAO.getInt(getContext().getApplicationContext(), 1, new Enum[] { (Enum)Settings.TT_TMA_SWITCH, (Enum)Settings.TmaSwitch.USE_NATIVE_LIVE_PLAYER }) != 1)
      return true; 
    if (!NestWebView.hasSetRenderInBrowser()) {
      DebugUtil.logOrThrow(this.TAG, new Object[] { "not set render in browser", new Throwable() });
      return true;
    } 
    if (isRenderInBrowser()) {
      HostDependManager hostDependManager = HostDependManager.getInst();
      l.a(hostDependManager, "HostDependManager.getInst()");
      if (!hostDependManager.isSupportNativeLivePlayer())
        return true; 
      if (this.mUseWebLivePlayerWhenRenderInBrowser == null) {
        boolean bool2 = this.DEFAULT_USE_WEB_VIDEO;
        AppbrandContext appbrandContext = AppbrandContext.getInst();
        l.a(appbrandContext, "AppbrandContext.getInst()");
        InitParamsEntity initParamsEntity = appbrandContext.getInitParams();
        boolean bool1 = bool2;
        if (initParamsEntity != null)
          bool1 = initParamsEntity.useWebLivePlayerWheRenderInBrowser(bool2); 
        AppBrandLogger.d(this.TAG, new Object[] { "localConfigUseLivePlayer:", Boolean.valueOf(bool1) });
        this.mUseWebLivePlayerWhenRenderInBrowser = Boolean.valueOf(((SuffixMetaServiceInterface)getContext().getService(SuffixMetaServiceInterface.class)).get(true).isUseWebLivePlayer(bool1));
      } 
      Boolean bool = this.mUseWebLivePlayerWhenRenderInBrowser;
      if (bool != null)
        return bool.booleanValue(); 
      throw new u("null cannot be cast to non-null type kotlin.Boolean");
    } 
    return true;
  }
  
  public final boolean useWebVideo() {
    if (!NestWebView.hasSetRenderInBrowser()) {
      DebugUtil.logOrThrow(this.TAG, new Object[] { "not set render in browser", new Throwable() });
      return false;
    } 
    if (isRenderInBrowser()) {
      AppBrandLogger.i(this.TAG, new Object[] { "开启同层渲染时使用 native video 组件" });
      return false;
    } 
    if (this.mUseWebVideoWhenNotRenderInBrowser == null) {
      boolean bool1 = this.DEFAULT_USE_WEB_VIDEO;
      AppbrandContext appbrandContext = AppbrandContext.getInst();
      l.a(appbrandContext, "AppbrandContext.getInst()");
      InitParamsEntity initParamsEntity = appbrandContext.getInitParams();
      if (initParamsEntity != null)
        bool1 = initParamsEntity.useWebVideoWhenNotRenderInBrowser(this.DEFAULT_USE_WEB_VIDEO); 
      AppBrandLogger.d(this.TAG, new Object[] { "localConfigUseWebVideo:", Boolean.valueOf(bool1) });
      this.mUseWebVideoWhenNotRenderInBrowser = Boolean.valueOf(((SuffixMetaServiceInterface)getContext().getService(SuffixMetaServiceInterface.class)).get(true).isUseWebVideo(bool1));
    } 
    AppBrandLogger.i(this.TAG, new Object[] { "非同层渲染情况下 useWebVideo:", this.mUseWebVideoWhenNotRenderInBrowser });
    Boolean bool = this.mUseWebVideoWhenNotRenderInBrowser;
    if (bool != null)
      return bool.booleanValue(); 
    throw new u("null cannot be cast to non-null type kotlin.Boolean");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\render\RenderService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */