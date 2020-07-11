package com.tt.miniapp.launchcache.pkg;

import android.content.Context;
import android.os.SystemClock;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import d.f.b.g;
import d.f.b.l;

public final class AsyncPkgRequester extends DownloadOnlyBasePkgRequester {
  public static final Companion Companion = new Companion(null);
  
  private final AppbrandApplicationImpl mApp;
  
  public AsyncPkgRequester(AppbrandApplicationImpl paramAppbrandApplicationImpl, Context paramContext) {
    super(paramContext, RequestType.async);
    this.mApp = paramAppbrandApplicationImpl;
  }
  
  public final DownloadOnlyBasePkgRequester.StreamDownloadListenerAdapter createStreamDownloadListenerAdapter(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    return new AsyncPkgRequester$createStreamDownloadListenerAdapter$1((MpTimeLineReporter)this.mApp.getService(MpTimeLineReporter.class), paramPkgRequestContext, paramPkgRequestContext);
  }
  
  protected final void onRequestSync(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "AsyncPkgRequester_onRequestSync" });
    super.onRequestSync(paramPkgRequestContext);
  }
  
  public final void reportStartDownload(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    super.reportStartDownload(paramPkgRequestContext);
    ((MpTimeLineReporter)this.mApp.getService(MpTimeLineReporter.class)).addPoint("request_ttpkg_begin", System.currentTimeMillis(), SystemClock.elapsedRealtime(), (new MpTimeLineReporter.ExtraBuilder()).kv("request_type", Integer.valueOf(1)).kv("url", paramPkgRequestContext.getDownloadUrl()).build());
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public static final class AsyncPkgRequester$createStreamDownloadListenerAdapter$1 extends DownloadOnlyBasePkgRequester.StreamDownloadListenerAdapter {
    AsyncPkgRequester$createStreamDownloadListenerAdapter$1(MpTimeLineReporter param1MpTimeLineReporter, PkgRequestContext param1PkgRequestContext1, PkgRequestContext param1PkgRequestContext2) {
      super(param1AsyncPkgRequester, param1PkgRequestContext2);
    }
    
    public final void onRetry(String param1String1, String param1String2, String param1String3, int param1Int, long param1Long) {
      l.b(param1String1, "errorStr");
      l.b(param1String2, "failedUrl");
      l.b(param1String3, "nextUrl");
      this.$mpTimeLineReporter.addPoint("request_ttpkg_end");
      this.$mpTimeLineReporter.addPoint("request_ttpkg_begin", System.currentTimeMillis(), SystemClock.elapsedRealtime(), (new MpTimeLineReporter.ExtraBuilder()).kv("request_type", Integer.valueOf(1)).kv("url", param1String3).build());
      super.onRetry(param1String1, param1String2, param1String3, param1Int, param1Long);
    }
    
    public final void onStreamDownloadFinish(int param1Int, long param1Long) {
      this.$mpTimeLineReporter.addPoint("request_ttpkg_end");
      super.onStreamDownloadFinish(param1Int, param1Long);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\pkg\AsyncPkgRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */