package com.tt.miniapp.webapp;

import android.content.Context;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;

public class WebAppPreloadManager extends AppbrandServiceManager.ServiceBase {
  private WebAppNestWebview webappWebviewHolder;
  
  public WebAppPreloadManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  public static WebAppPreloadManager getInst() {
    return (WebAppPreloadManager)AppbrandApplicationImpl.getInst().getService(WebAppPreloadManager.class);
  }
  
  public void preloadWebViewResources(Context paramContext) {
    if (!HostDependManager.getInst().isEnableWebAppPreload())
      return; 
    AppBrandLogger.d("WebAppPreloadManager", new Object[] { "start  res preload" });
    final WebAppNestWebview webView = new WebAppNestWebview(paramContext);
    webAppNestWebview.loadUrl("https://sf16-ttcdn-tos.pstatp.com/obj/developer-alisg/app/libra/index.html");
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            AppBrandLogger.d("WebAppPreloadManager", new Object[] { "stop res preload" });
            webView.stopLoading();
          }
        }5000L);
    preloadWebappWebview(paramContext);
  }
  
  public WebAppNestWebview preloadWebappWebview(Context paramContext) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield webappWebviewHolder : Lcom/tt/miniapp/webapp/WebAppNestWebview;
    //   6: ifnull -> 18
    //   9: aload_0
    //   10: getfield webappWebviewHolder : Lcom/tt/miniapp/webapp/WebAppNestWebview;
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: areturn
    //   18: aload_0
    //   19: new com/tt/miniapp/webapp/WebAppNestWebview
    //   22: dup
    //   23: aload_1
    //   24: invokespecial <init> : (Landroid/content/Context;)V
    //   27: putfield webappWebviewHolder : Lcom/tt/miniapp/webapp/WebAppNestWebview;
    //   30: aload_0
    //   31: getfield webappWebviewHolder : Lcom/tt/miniapp/webapp/WebAppNestWebview;
    //   34: astore_1
    //   35: aload_0
    //   36: monitorexit
    //   37: aload_1
    //   38: areturn
    //   39: astore_1
    //   40: aload_0
    //   41: monitorexit
    //   42: aload_1
    //   43: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	39	finally
    //   18	35	39	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webapp\WebAppPreloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */