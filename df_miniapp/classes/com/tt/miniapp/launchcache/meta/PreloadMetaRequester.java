package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.g;
import d.f.b.l;

public final class PreloadMetaRequester extends DownloadOnlyBaseMetaRequester {
  public static final Companion Companion = new Companion(null);
  
  public PreloadMetaRequester(Context paramContext) {
    super(paramContext, RequestType.preload);
  }
  
  protected final AppInfoRequestResult onRequestSync(AppInfoEntity paramAppInfoEntity) {
    l.b(paramAppInfoEntity, "appInfo");
    AppInfoRequestResult appInfoRequestResult = AppInfoHelper.request(getMContext(), paramAppInfoEntity, getMRequestType());
    l.a(appInfoRequestResult, "AppInfoHelper.request(mC…t, appInfo, mRequestType)");
    return appInfoRequestResult;
  }
  
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\PreloadMetaRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */