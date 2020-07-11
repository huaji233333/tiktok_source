package com.tt.miniapp.launchcache.pkg;

import android.content.Context;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.g;
import d.f.b.l;
import java.io.File;

public final class PreloadPkgRequester extends DownloadOnlyBasePkgRequester {
  public static final Companion Companion = new Companion(null);
  
  public PreloadPkgRequester(Context paramContext) {
    super(paramContext, RequestType.preload);
  }
  
  protected final void onRequestPkgSuccess(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    super.onRequestPkgSuccess(paramPkgRequestContext);
    PkgDownloadHelper pkgDownloadHelper = PkgDownloadHelper.INSTANCE;
    AppInfoEntity appInfoEntity = paramPkgRequestContext.getAppInfo();
    File file = paramPkgRequestContext.getPkgFile();
    if (file == null)
      l.a(); 
    pkgDownloadHelper.codeCache(appInfoEntity, file);
  }
  
  protected final void onRequestSync(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    AppBrandLogger.i("PreloadPkgRequester", new Object[] { "onRequestSync" });
    super.onRequestSync(paramPkgRequestContext);
  }
  
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\pkg\PreloadPkgRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */