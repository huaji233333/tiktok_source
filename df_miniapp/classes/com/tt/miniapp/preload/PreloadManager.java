package com.tt.miniapp.preload;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.TTAppbrandTabUI;
import com.tt.miniapp.adsite.AdSiteBrowser;
import com.tt.miniapp.audio.AudioManager;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.jsbridge.JsRuntimeManager;
import com.tt.miniapp.launchcache.meta.AppInfoHelper;
import com.tt.miniapp.manager.PreTTRequestManager;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.page.AppbrandTabHost;
import com.tt.miniapp.page.AppbrandViewWindowBase;
import com.tt.miniapp.secrecy.ui.SecrecyUIHelper;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ConcaveScreenUtils;
import com.tt.miniapp.util.RomUtil;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.view.LaunchLoadingView;
import com.tt.miniapp.webapp.WebAppPreloadManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.preload.IPreload;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class PreloadManager extends AppbrandServiceManager.ServiceBase implements IPreload {
  private SoftReference<View> mGameLoadingViewHolder;
  
  private SoftReference<View> mHomeLoadingViewHolder;
  
  private boolean mIsTakeFirstPage;
  
  private Map<Integer, View> mPreloadViewMap = new HashMap<Integer, View>();
  
  private AppbrandSinglePage preloadedPage;
  
  private boolean sPreloadEnabled = true;
  
  private PreloadManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
    initPreloadSwitch();
  }
  
  private View createView(int paramInt) {
    return (View)((paramInt == 1) ? new LaunchLoadingView((Context)AppbrandContext.getInst().getApplicationContext()) : ((paramInt == 4) ? LayoutInflater.from((Context)AppbrandContext.getInst().getApplicationContext()).inflate(2097676350, null) : ((paramInt == 5) ? new FrameLayout((Context)AppbrandContext.getInst().getApplicationContext()) : ((paramInt == 6) ? new AppbrandTabHost((Context)AppbrandContext.getInst().getApplicationContext(), this.mApp) : null))));
  }
  
  private void initPreloadSwitch() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application != null) {
      Settings settings = Settings.TT_TMA_SWITCH;
      boolean bool = false;
      if (SettingsDAO.getInt((Context)application, 0, new Enum[] { (Enum)settings, (Enum)Settings.TmaSwitch.PRELOAD_WEBVIEW }) == 0)
        bool = true; 
      this.sPreloadEnabled = bool;
    } 
  }
  
  private void preloadForStartUp(final Context context) {
    AppInfoHelper.preload(context);
    PreTTRequestManager.preload(context);
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            TTAppbrandTabUI.preload(context);
            AdSiteBrowser.preload(context);
            AudioManager.preload(context);
            SecrecyUIHelper.preload(context);
            ConcaveScreenUtils.preload(context);
            WebAppPreloadManager.getInst().preloadWebViewResources(context);
          }
        },  false);
  }
  
  public void clean() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield preloadedPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   6: astore_1
    //   7: aload_0
    //   8: aconst_null
    //   9: putfield preloadedPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_1
    //   15: ifnull -> 22
    //   18: aload_1
    //   19: invokevirtual onDestroy : ()V
    //   22: return
    //   23: astore_1
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_1
    //   27: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	23	finally
    //   24	26	23	finally
  }
  
  public boolean enabled() {
    return this.sPreloadEnabled;
  }
  
  public View getPreloadedLoadingView(Activity paramActivity, int paramInt) {
    SoftReference<View> softReference;
    if (paramInt == 1) {
      softReference = this.mHomeLoadingViewHolder;
      this.mGameLoadingViewHolder = null;
    } else {
      softReference = this.mGameLoadingViewHolder;
      this.mHomeLoadingViewHolder = null;
    } 
    if (softReference == null) {
      AppBrandLogger.i("PreloadManager", new Object[] { "preloadLoadingView null ", Integer.valueOf(hashCode()) });
      return null;
    } 
    View view = softReference.get();
    if (view == null) {
      AppBrandLogger.i("PreloadManager", new Object[] { "preloadLoadingView null ", Integer.valueOf(hashCode()) });
      return null;
    } 
    AppBrandLogger.i("PreloadManager", new Object[] { "preloadLoadingView got ", Integer.valueOf(hashCode()) });
    return view;
  }
  
  public View getPreloadedView(int paramInt) {
    View view = this.mPreloadViewMap.get(Integer.valueOf(paramInt));
    if (view == null) {
      view = createView(paramInt);
    } else {
      this.mPreloadViewMap.remove(Integer.valueOf(paramInt));
    } 
    if (view != null && paramInt == 1 && view instanceof LaunchLoadingView)
      ((LaunchLoadingView)view).onUsed(); 
    return view;
  }
  
  public void preLoadLoadingView() {
    if (this.sPreloadEnabled)
      try {
        this.mHomeLoadingViewHolder = new SoftReference<View>(LayoutInflater.from((Context)AppbrandContext.getInst().getApplicationContext()).inflate(2097676296, null));
        this.mGameLoadingViewHolder = new SoftReference<View>(LayoutInflater.from((Context)AppbrandContext.getInst().getApplicationContext()).inflate(2097676316, null));
        preloadView(1);
        preloadView(4);
        preloadView(5);
        preloadView(6);
        return;
      } catch (Exception exception) {
        AppBrandLogger.e("PreloadManager", new Object[] { "preload loadingView error", exception });
      }  
  }
  
  public void preload(Context paramContext) {
    // Byte code:
    //   0: aload_1
    //   1: astore_2
    //   2: aload_1
    //   3: ifnonnull -> 13
    //   6: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   9: invokevirtual getCurrentActivity : ()Lcom/tt/miniapphost/MiniappHostBase;
    //   12: astore_2
    //   13: aload_2
    //   14: astore_1
    //   15: aload_2
    //   16: ifnonnull -> 26
    //   19: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   22: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   25: astore_1
    //   26: aload_0
    //   27: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   30: invokevirtual getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   33: ifnull -> 53
    //   36: aload_0
    //   37: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   40: invokevirtual getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   43: invokevirtual isGame : ()Z
    //   46: ifne -> 52
    //   49: goto -> 53
    //   52: return
    //   53: aload_0
    //   54: monitorenter
    //   55: aload_0
    //   56: getfield preloadedPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   59: ifnonnull -> 78
    //   62: aload_0
    //   63: new com/tt/miniapp/page/AppbrandSinglePage
    //   66: dup
    //   67: aload_1
    //   68: aload_0
    //   69: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   72: invokespecial <init> : (Landroid/content/Context;Lcom/tt/miniapp/AppbrandApplicationImpl;)V
    //   75: putfield preloadedPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   78: aload_0
    //   79: monitorexit
    //   80: return
    //   81: astore_1
    //   82: aload_0
    //   83: monitorexit
    //   84: aload_1
    //   85: athrow
    // Exception table:
    //   from	to	target	type
    //   55	78	81	finally
    //   78	80	81	finally
    //   82	84	81	finally
  }
  
  public void preloadOnIdle() {
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            Looper.myQueue().addIdleHandler(new PreloadManager.PreloadHandler());
          }
        });
  }
  
  public void preloadOnProcessInit() {
    if (!this.sPreloadEnabled || RomUtil.isEUI()) {
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "PreloadManager_preloadOnProcessInit_return", String.valueOf(this.sPreloadEnabled) });
      return;
    } 
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "PreloadManager_preload_start" });
    Application application = AppbrandContext.getInst().getApplicationContext();
    try {
      if (!(DebugManager.getInst()).mIsRemoteDebug)
        ((JsRuntimeManager)this.mApp.getService(JsRuntimeManager.class)).preloadTMARuntime((ContextWrapper)application); 
    } finally {
      Exception exception = null;
    } 
    preloadForStartUp((Context)application);
  }
  
  public void preloadView(int paramInt) {
    if (this.mPreloadViewMap.get(Integer.valueOf(paramInt)) == null)
      this.mPreloadViewMap.put(Integer.valueOf(paramInt), createView(paramInt)); 
  }
  
  public AppbrandSinglePage takeFirstPage(AppbrandViewWindowBase paramAppbrandViewWindowBase) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mIsTakeFirstPage : Z
    //   6: ifeq -> 13
    //   9: aload_0
    //   10: monitorexit
    //   11: aconst_null
    //   12: areturn
    //   13: aload_0
    //   14: iconst_1
    //   15: putfield mIsTakeFirstPage : Z
    //   18: aload_0
    //   19: getfield preloadedPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   22: astore_2
    //   23: aload_0
    //   24: aconst_null
    //   25: putfield preloadedPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   28: aload_0
    //   29: monitorexit
    //   30: aload_2
    //   31: ifnull -> 39
    //   34: aload_2
    //   35: aload_1
    //   36: invokevirtual bindHost : (Lcom/tt/miniapp/page/AppbrandViewWindowBase;)V
    //   39: aload_2
    //   40: areturn
    //   41: astore_1
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_1
    //   45: athrow
    // Exception table:
    //   from	to	target	type
    //   2	11	41	finally
    //   13	30	41	finally
    //   42	44	41	finally
  }
  
  public AppbrandSinglePage takePage(AppbrandViewWindowBase paramAppbrandViewWindowBase) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_1
    //   4: putfield mIsTakeFirstPage : Z
    //   7: aload_0
    //   8: getfield preloadedPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   11: astore_3
    //   12: aload_0
    //   13: aconst_null
    //   14: putfield preloadedPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_3
    //   20: astore_2
    //   21: aload_3
    //   22: ifnonnull -> 41
    //   25: new com/tt/miniapp/page/AppbrandSinglePage
    //   28: dup
    //   29: aload_1
    //   30: invokevirtual getContext : ()Landroid/content/Context;
    //   33: aload_0
    //   34: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   37: invokespecial <init> : (Landroid/content/Context;Lcom/tt/miniapp/AppbrandApplicationImpl;)V
    //   40: astore_2
    //   41: aload_2
    //   42: aload_1
    //   43: invokevirtual bindHost : (Lcom/tt/miniapp/page/AppbrandViewWindowBase;)V
    //   46: aload_2
    //   47: areturn
    //   48: astore_1
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_1
    //   52: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	48	finally
    //   49	51	48	finally
  }
  
  public class PreloadHandler implements MessageQueue.IdleHandler {
    public boolean queueIdle() {
      AppBrandLogger.d("PreloadManager", new Object[] { "queueIdle preload webview" });
      PreloadManager.this.preload((Context)AppbrandContext.getInst().getCurrentActivity());
      return false;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\preload\PreloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */