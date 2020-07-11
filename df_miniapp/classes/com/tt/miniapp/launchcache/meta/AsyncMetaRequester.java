package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.g;
import d.f.b.l;

public final class AsyncMetaRequester extends DownloadOnlyBaseMetaRequester {
  public static final Companion Companion = new Companion(null);
  
  private final AppbrandApplicationImpl mApp;
  
  public AsyncMetaRequester(AppbrandApplicationImpl paramAppbrandApplicationImpl, Context paramContext) {
    super(paramContext, RequestType.async);
    this.mApp = paramAppbrandApplicationImpl;
  }
  
  protected final AppInfoRequestResult onRequestSync(AppInfoEntity paramAppInfoEntity) {
    l.b(paramAppInfoEntity, "appInfo");
    return ((MetaService)this.mApp.getService(MetaService.class)).competeRequest(getMContext(), paramAppInfoEntity, getMRequestType(), 1);
  }
  
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\AsyncMetaRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */