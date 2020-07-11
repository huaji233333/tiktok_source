package com.tt.miniapp.webapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.TTAppbrandTabUI;
import com.tt.miniapp.base.ui.viewwindow.ViewWindowDragRightLayout;
import com.tt.miniapp.guide.ReenterGuideHelper;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.page.AppbrandViewWindowBase;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.ParamManager;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.UIUtils;
import java.lang.ref.WeakReference;

public class TTWebAppViewWindow extends AppbrandViewWindowBase {
  private static WeakReference<TTWebAppViewWindow> sInstance;
  
  private boolean isPureWebappNoBridge;
  
  private AppInfoEntity mAppInfo;
  
  public ImmersedStatusBarHelper mImmersedStatusBarHelper;
  
  public WeakReference<TTAppbrandTabUI> mTTAppbrandTabUIRef;
  
  public View mTitleBar;
  
  WebAppNestWebview mWebView;
  
  public ImageView titlebarCloseBtn;
  
  public TTWebAppViewWindow(Context paramContext, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramContext, paramAppbrandApplicationImpl);
    sInstance = new WeakReference<TTWebAppViewWindow>(this);
  }
  
  public static TTWebAppViewWindow createInstance(Context paramContext, TTAppbrandTabUI paramTTAppbrandTabUI, AppInfoEntity paramAppInfoEntity) {
    boolean bool;
    if (SettingsDAO.getInt(paramContext, 0, new Enum[] { (Enum)Settings.TT_TMA_SWITCH, (Enum)Settings.TmaSwitch.USE_WEBAPP }) == 1) {
      bool = true;
    } else {
      bool = false;
    } 
    StringBuilder stringBuilder = new StringBuilder("isPureWebappNoBridge：");
    stringBuilder.append(bool);
    AppBrandLogger.d("TTWebAppFragment", new Object[] { stringBuilder.toString() });
    TTWebAppViewWindow tTWebAppViewWindow = new TTWebAppViewWindow(paramContext, AppbrandApplicationImpl.getInst());
    tTWebAppViewWindow.setPureWebappNoBridge(bool);
    tTWebAppViewWindow.setTTAppbrandTabUIRef(paramTTAppbrandTabUI);
    tTWebAppViewWindow.setAppInfo(paramAppInfoEntity);
    if (bool)
      paramTTAppbrandTabUI.hideLoadShowUI(); 
    paramTTAppbrandTabUI.getLaunchScheduler().setAllCompleteProgress();
    return tTWebAppViewWindow;
  }
  
  public static WeakReference<TTWebAppViewWindow> getWeakRef() {
    return sInstance;
  }
  
  public void exitInternal() {
    Runnable runnable = new Runnable() {
        public void run() {
          ToolUtils.onActivityExit(TTWebAppViewWindow.this.getActivity(), 2);
        }
      };
    ReenterGuideHelper.checkReenterGuideTip(getActivity(), runnable);
  }
  
  public AppbrandSinglePage getCurrentPage() {
    return null;
  }
  
  protected ImmersedStatusBarHelper.ImmersedStatusBarConfig getImmersedStatusBarConfig() {
    return new ImmersedStatusBarHelper.ImmersedStatusBarConfig();
  }
  
  public Bundle getParams() {
    return super.getParams();
  }
  
  public void hideLoading() {
    TTAppbrandTabUI tTAppbrandTabUI = this.mTTAppbrandTabUIRef.get();
    if (tTAppbrandTabUI != null)
      tTAppbrandTabUI.hideLoadShowUI(); 
  }
  
  public boolean isViewResume() {
    return super.isViewResume();
  }
  
  public void loadUrl(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("load url:");
    stringBuilder.append(paramString);
    AppBrandLogger.d("TTWebAppFragment", new Object[] { stringBuilder.toString() });
    this.mWebView.loadUrl(paramString);
  }
  
  public boolean onBackPressed() {
    if (this.mWebView.canGoBack()) {
      this.mWebView.goBack();
      return true;
    } 
    exitInternal();
    return true;
  }
  
  public void onCreate() {
    super.onCreate();
    this.mImmersedStatusBarHelper = new ImmersedStatusBarHelper(getActivity(), getImmersedStatusBarConfig());
    this.mImmersedStatusBarHelper.setup(true);
    setIsEnableSwipeBack(true);
    ViewGroup viewGroup = (ViewGroup)LayoutInflater.from((Context)AppbrandContext.getInst().getApplicationContext()).inflate(2097676314, (ViewGroup)this);
    WebAppNestWebview webAppNestWebview = WebAppPreloadManager.getInst().preloadWebappWebview((Context)getActivity());
    ((ViewGroup)viewGroup.findViewById(2097545459)).addView((View)webAppNestWebview);
    this.mWebView = webAppNestWebview;
    this.titlebarCloseBtn = (ImageView)viewGroup.findViewById(2097545405);
    this.mTitleBar = viewGroup.findViewById(2097545400);
    UIUtils.configTitleBarWithHeight((Context)getActivity(), this.mTitleBar);
    this.titlebarCloseBtn.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            TTWebAppViewWindow.this.exitInternal();
          }
        });
    setDragFinishListener(new ViewWindowDragRightLayout.OnDragListener() {
          public void onScrollFinish(boolean param1Boolean) {
            ((TTAppbrandTabUI)TTWebAppViewWindow.this.mTTAppbrandTabUIRef.get()).onDragFinish();
          }
          
          public void onScrollStart() {}
        });
    if (!this.isPureWebappNoBridge) {
      this.mWebView.enableJsBridge();
    } else {
      this.mWebView.disableJsBridge();
      setTitleMenuBarColor("black");
      setIsEnableSwipeBack(false);
    } 
    loadUrl(parseUrl(this.mAppInfo));
  }
  
  public String parseUrl(AppInfoEntity paramAppInfoEntity) {
    String str3 = "";
    if (paramAppInfoEntity == null) {
      AppBrandLogger.e("TTWebAppFragment", new Object[] { "parseUrl appinfo null" });
      return "";
    } 
    String str2 = str3;
    if (paramAppInfoEntity.libra_path != null) {
      str2 = str3;
      if (paramAppInfoEntity.libra_path.size() != 0)
        str2 = paramAppInfoEntity.libra_path.get(0); 
    } 
    boolean bool = DebugUtil.debug();
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder("https://");
      stringBuilder.append(paramAppInfoEntity.appId);
      stringBuilder.append(".libra.byteoversea.com/");
      str2 = stringBuilder.toString();
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str2);
    stringBuilder2.append(paramAppInfoEntity.versionType);
    stringBuilder2.append("/libra.html");
    Uri.Builder builder = Uri.parse(stringBuilder2.toString()).buildUpon();
    str2 = paramAppInfoEntity.startPage;
    if (bool)
      builder.appendQueryParameter("appid", paramAppInfoEntity.appId); 
    if (!TextUtils.isEmpty(str2))
      builder.appendQueryParameter("startpage", str2); 
    builder.appendQueryParameter("sdk_verison", ParamManager.getFullAppSdkVersion());
    if (this.isPureWebappNoBridge) {
      str2 = "1";
    } else {
      str2 = "0";
    } 
    builder.appendQueryParameter("use_webapp", str2);
    if (paramAppInfoEntity.isPreviewVersion() && !TextUtils.isEmpty(paramAppInfoEntity.token))
      builder.appendQueryParameter("token", paramAppInfoEntity.token); 
    String str1 = builder.build().toString();
    StringBuilder stringBuilder1 = new StringBuilder("load url:");
    stringBuilder1.append(str1);
    AppBrandLogger.d("TTWebAppFragment", new Object[] { stringBuilder1.toString() });
    return str1;
  }
  
  public void sendOnAppRoute(String paramString) {}
  
  public void setAppInfo(AppInfoEntity paramAppInfoEntity) {
    this.mAppInfo = paramAppInfoEntity;
  }
  
  public void setIsEnableSwipeBack(boolean paramBoolean) {
    setDragEnable(paramBoolean);
    ((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).getViewWindowRoot().getAppbrandHomePage().setDragEnable(true);
  }
  
  public void setParams(Bundle paramBundle) {
    super.setParams(paramBundle);
  }
  
  public void setPureWebappNoBridge(boolean paramBoolean) {
    this.isPureWebappNoBridge = paramBoolean;
  }
  
  public void setTTAppbrandTabUIRef(TTAppbrandTabUI paramTTAppbrandTabUI) {
    this.mTTAppbrandTabUIRef = new WeakReference<TTAppbrandTabUI>(paramTTAppbrandTabUI);
  }
  
  public void setTitleMenuBarColor(final String color) {
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            if (TextUtils.equals(color, "black")) {
              TTWebAppViewWindow.this.mImmersedStatusBarHelper.setUseLightStatusBarInternal(true);
              TTWebAppViewWindow.this.mImmersedStatusBarHelper.setColor(-1);
              TTWebAppViewWindow.this.titlebarCloseBtn.setImageResource(2097479752);
              StringBuilder stringBuilder1 = new StringBuilder("setTitleMenuBarColor text color");
              stringBuilder1.append(color);
              AppBrandLogger.d("TTWebAppFragment", new Object[] { stringBuilder1.toString() });
              return;
            } 
            TTWebAppViewWindow.this.mImmersedStatusBarHelper.setUseLightStatusBarInternal(false);
            TTWebAppViewWindow.this.mImmersedStatusBarHelper.setColor(-16777216);
            TTWebAppViewWindow.this.titlebarCloseBtn.setImageResource(2097479797);
            StringBuilder stringBuilder = new StringBuilder("setTitleMenuBarColor text color");
            stringBuilder.append(color);
            AppBrandLogger.d("TTWebAppFragment", new Object[] { stringBuilder.toString() });
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webapp\TTWebAppViewWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */