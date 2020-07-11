package com.tt.miniapp.page;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.base.ui.viewwindow.ViewWindowDragRightLayout;
import com.tt.miniapp.manager.HostSnapShotManager;
import com.tt.miniapp.preload.PreloadManager;
import com.tt.miniapphost.AppBrandLogger;
import d.f.a.a;
import d.f.a.b;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.u;
import d.x;
import java.util.Collection;
import java.util.HashMap;

public final class AppbrandHomePageViewWindow extends AppbrandViewWindowBase {
  public static final Companion Companion = new Companion(null);
  
  private HashMap _$_findViewCache;
  
  public AppbrandSinglePage currentPage;
  
  private boolean isTabMode;
  
  private final RelativeLayout mContentView;
  
  public AppbrandSinglePage mFirstPage;
  
  public final FrameLayout mPageContainer;
  
  public HashMap<String, AppbrandSinglePage> mTab2PageMap;
  
  private AppbrandTabHost mTabHost;
  
  private final TabHostCallbackImpl mTabHostCallback;
  
  private int mTabHostPosition;
  
  private a<x> onDragDispearEndListener;
  
  public AppbrandHomePageViewWindow(Context paramContext, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramContext, paramAppbrandApplicationImpl);
    this.mContentView = new RelativeLayout(paramContext);
    this.mPageContainer = new FrameLayout(paramContext);
    this.mTabHostPosition = 1;
    this.mTabHostCallback = new TabHostCallbackImpl();
    addView((View)this.mContentView, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
    this.mContentView.addView((View)this.mPageContainer, new ViewGroup.LayoutParams(-1, -1));
    this.mPageContainer.setId(View.generateViewId());
  }
  
  private final void adjustTabHostPosition() {
    ViewGroup.LayoutParams layoutParams = this.mPageContainer.getLayoutParams();
    if (layoutParams != null) {
      RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams)layoutParams;
      AppbrandTabHost appbrandTabHost = this.mTabHost;
      if (appbrandTabHost == null)
        l.a(); 
      ViewGroup.LayoutParams layoutParams2 = appbrandTabHost.getLayoutParams();
      if (layoutParams2 != null) {
        AppbrandTabHost appbrandTabHost1;
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams)layoutParams2;
        int i = this.mTabHostPosition;
        if (i == 1) {
          layoutParams3.removeRule(10);
          layoutParams3.removeRule(12);
          layoutParams3.addRule(12);
          layoutParams1.removeRule(2);
          layoutParams1.removeRule(3);
          appbrandTabHost1 = this.mTabHost;
          if (appbrandTabHost1 == null)
            l.a(); 
          layoutParams1.addRule(2, appbrandTabHost1.getId());
          return;
        } 
        if (i == 0) {
          appbrandTabHost1.removeRule(10);
          appbrandTabHost1.removeRule(12);
          appbrandTabHost1.addRule(10);
          layoutParams1.removeRule(2);
          layoutParams1.removeRule(3);
          appbrandTabHost1 = this.mTabHost;
          if (appbrandTabHost1 == null)
            l.a(); 
          layoutParams1.addRule(3, appbrandTabHost1.getId());
        } 
        return;
      } 
      throw new u("null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
    } 
    throw new u("null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
  }
  
  private final void cleanUp() {
    AppBrandLogger.d("AppbrandHomePageViewWindow", new Object[] { "cleanUp" });
    forEachPage(new AppbrandHomePageViewWindow$cleanUp$1());
    HashMap<String, AppbrandSinglePage> hashMap = this.mTab2PageMap;
    if (hashMap != null)
      hashMap.clear(); 
    this.mTab2PageMap = null;
    this.currentPage = null;
    AppbrandTabHost appbrandTabHost = this.mTabHost;
    if (appbrandTabHost != null) {
      this.mContentView.removeView((View)appbrandTabHost);
      this.mTabHost = null;
    } 
  }
  
  private final void forEachPage(b<? super AppbrandSinglePage, x> paramb) {
    if (this.isTabMode) {
      HashMap<String, AppbrandSinglePage> hashMap = this.mTab2PageMap;
      if (hashMap != null) {
        Collection<AppbrandSinglePage> collection = hashMap.values();
        if (collection != null) {
          for (AppbrandSinglePage appbrandSinglePage : collection) {
            l.a(appbrandSinglePage, "it");
            paramb.invoke(appbrandSinglePage);
          } 
        } else {
          return;
        } 
      } else {
        return;
      } 
    } else {
      AppbrandSinglePage appbrandSinglePage = this.currentPage;
      if (appbrandSinglePage != null)
        paramb.invoke(appbrandSinglePage); 
    } 
  }
  
  public final void _$_clearFindViewByIdCache() {
    HashMap hashMap = this._$_findViewCache;
    if (hashMap != null)
      hashMap.clear(); 
  }
  
  public final View _$_findCachedViewById(int paramInt) {
    if (this._$_findViewCache == null)
      this._$_findViewCache = new HashMap<Object, Object>(); 
    View view2 = (View)this._$_findViewCache.get(Integer.valueOf(paramInt));
    View view1 = view2;
    if (view2 == null) {
      view1 = findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), view1);
    } 
    return view1;
  }
  
  public final AppbrandSinglePage getCurrentPage() {
    return this.currentPage;
  }
  
  public final a<x> getOnDragDispearEndListener() {
    return this.onDragDispearEndListener;
  }
  
  public final boolean isTabBarVisible() {
    AppbrandTabHost appbrandTabHost = this.mTabHost;
    return (appbrandTabHost != null && appbrandTabHost.getVisibility() == 0);
  }
  
  public final boolean isTabMode() {
    return this.isTabMode;
  }
  
  public final void onActivityDestroy() {
    forEachPage(AppbrandHomePageViewWindow$onActivityDestroy$1.INSTANCE);
  }
  
  public final void onActivityPause() {
    AppbrandSinglePage appbrandSinglePage = this.currentPage;
    if (appbrandSinglePage != null)
      appbrandSinglePage.onActivityPause(); 
  }
  
  public final void onActivityResume() {
    AppbrandSinglePage appbrandSinglePage = this.currentPage;
    if (appbrandSinglePage != null)
      appbrandSinglePage.onActivityResume(); 
  }
  
  public final boolean onBackPressed() {
    AppbrandSinglePage appbrandSinglePage = this.currentPage;
    if (appbrandSinglePage != null) {
      Boolean bool = appbrandSinglePage.onBackPressed();
      if (bool != null)
        return bool.booleanValue(); 
    } 
    return false;
  }
  
  public final void onCreate() {
    super.onCreate();
    setDragFinishListener(new AppbrandHomePageViewWindow$onCreate$1());
  }
  
  public final void onDestroy() {
    cleanUp();
  }
  
  public final void onThemeChanged(String paramString) {
    super.onThemeChanged(paramString);
    AppbrandSinglePage appbrandSinglePage = this.currentPage;
    if (appbrandSinglePage != null)
      appbrandSinglePage.onThemeChanged(paramString); 
  }
  
  public final void onViewPause(int paramInt) {
    AppbrandSinglePage appbrandSinglePage = this.currentPage;
    if (appbrandSinglePage != null)
      appbrandSinglePage.onViewPause(paramInt); 
  }
  
  public final void onViewResume(int paramInt) {
    setX(0.0F);
    AppbrandSinglePage appbrandSinglePage = this.currentPage;
    if (appbrandSinglePage != null)
      appbrandSinglePage.onViewResume(paramInt); 
  }
  
  public final void prepareLaunch(String paramString1, String paramString2) {
    l.b(paramString1, "pageUrl");
    l.b(paramString2, "openType");
    AppbrandSinglePage appbrandSinglePage = ((PreloadManager)getMApp().getService(PreloadManager.class)).takeFirstPage(this);
    if (appbrandSinglePage != null) {
      appbrandSinglePage.setupRouterParams(paramString1, paramString2);
      AppbrandSinglePage appbrandSinglePage1 = appbrandSinglePage;
    } else {
      paramString1 = null;
    } 
    this.mFirstPage = (AppbrandSinglePage)paramString1;
  }
  
  public final AppbrandSinglePage prepareSnapShotPage() {
    AppbrandSinglePage appbrandSinglePage = ((PreloadManager)getMApp().getService(PreloadManager.class)).takePage(this);
    l.a(appbrandSinglePage, "mApp.getService(PreloadM…lass.java).takePage(this)");
    this.currentPage = appbrandSinglePage;
    this.mPageContainer.addView((View)appbrandSinglePage);
    return appbrandSinglePage;
  }
  
  public final void sendOnAppRoute(String paramString) {
    l.b(paramString, "openType");
    AppbrandSinglePage appbrandSinglePage = this.currentPage;
    if (appbrandSinglePage != null)
      appbrandSinglePage.sendOnAppRoute(paramString); 
  }
  
  public final void setDragEnable(boolean paramBoolean) {
    if (paramBoolean && !isDragEnabled())
      ((HostSnapShotManager)AppbrandApplicationImpl.getInst().getService(HostSnapShotManager.class)).notifyUpdateSnapShot(); 
    super.setDragEnable(paramBoolean);
  }
  
  public final void setOnDragDispearEndListener(a<x> parama) {
    this.onDragDispearEndListener = parama;
  }
  
  public final String setTabBadge(boolean paramBoolean, int paramInt, String paramString) {
    l.b(paramString, "text");
    AppbrandTabHost appbrandTabHost = this.mTabHost;
    if (appbrandTabHost != null) {
      String str = appbrandTabHost.setTabBarBadge(paramBoolean, paramInt, paramString);
      paramString = str;
      return (str == null) ? "tabbar item not found" : paramString;
    } 
    return "tabbar item not found";
  }
  
  public final String setTabBarRedDotVisibility(int paramInt, boolean paramBoolean) {
    AppbrandTabHost appbrandTabHost = this.mTabHost;
    if (appbrandTabHost != null) {
      String str2 = appbrandTabHost.setTabBarRedDotVisibility(paramInt, paramBoolean);
      String str1 = str2;
      return (str2 == null) ? "tabbar item not found" : str1;
    } 
    return "tabbar item not found";
  }
  
  public final String setTabBarStyle(String paramString1, String paramString2, String paramString3, String paramString4) {
    l.b(paramString1, "color");
    l.b(paramString2, "selectedColor");
    l.b(paramString3, "backgroundColor");
    l.b(paramString4, "borderStyle");
    AppbrandTabHost appbrandTabHost = this.mTabHost;
    if (appbrandTabHost != null) {
      paramString2 = appbrandTabHost.setTabBarStyle(paramString1, paramString2, paramString3, paramString4);
      paramString1 = paramString2;
      return (paramString2 == null) ? "tabbar item not found" : paramString1;
    } 
    return "tabbar item not found";
  }
  
  public final String setTabBarVisibility(boolean paramBoolean1, boolean paramBoolean2) {
    AppbrandTabHost appbrandTabHost = this.mTabHost;
    if (appbrandTabHost == null)
      return "tabbar item not found"; 
    if (appbrandTabHost != null) {
      appbrandTabHost.clearAnimation();
      byte b = 0;
      if (!paramBoolean2) {
        if (!paramBoolean1)
          b = 8; 
        appbrandTabHost.setVisibility(b);
      } else if (paramBoolean1) {
        appbrandTabHost.setVisibility(0);
        appbrandTabHost.startAnimation(AnimationUtils.loadAnimation(getContext(), 2131034223));
      } else {
        Animation animation = AnimationUtils.loadAnimation(getContext(), 2131034224);
        animation.setAnimationListener(new AppbrandHomePageViewWindow$setTabBarVisibility$$inlined$let$lambda$1(this, paramBoolean2, paramBoolean1));
        appbrandTabHost.startAnimation(animation);
      } 
    } 
    return "";
  }
  
  public final String setTabItem(int paramInt, String paramString1, String paramString2, String paramString3) {
    AppbrandTabHost appbrandTabHost = this.mTabHost;
    if (appbrandTabHost != null) {
      paramString2 = appbrandTabHost.setTabBarItem(paramInt, paramString1, paramString2, paramString3);
      paramString1 = paramString2;
      return (paramString2 == null) ? "tabbar item not found" : paramString1;
    } 
    return "tabbar item not found";
  }
  
  public final void setupSingle(String paramString1, String paramString2) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'pageUrl'
    //   4: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_2
    //   8: ldc_w 'openType'
    //   11: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   14: ldc 'AppbrandHomePageViewWindow'
    //   16: iconst_1
    //   17: anewarray java/lang/Object
    //   20: dup
    //   21: iconst_0
    //   22: ldc_w 'setupSingle'
    //   25: aastore
    //   26: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   29: aload_0
    //   30: invokespecial cleanUp : ()V
    //   33: aload_0
    //   34: iconst_0
    //   35: putfield isTabMode : Z
    //   38: aload_0
    //   39: getfield mFirstPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   42: astore_3
    //   43: aload_3
    //   44: ifnull -> 70
    //   47: aload_3
    //   48: invokevirtual getPage : ()Ljava/lang/String;
    //   51: aload_1
    //   52: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   55: ifeq -> 61
    //   58: goto -> 63
    //   61: aconst_null
    //   62: astore_3
    //   63: aload_3
    //   64: astore #4
    //   66: aload_3
    //   67: ifnonnull -> 107
    //   70: aload_0
    //   71: invokevirtual getMApp : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   74: ldc_w com/tt/miniapp/preload/PreloadManager
    //   77: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   80: checkcast com/tt/miniapp/preload/PreloadManager
    //   83: aload_0
    //   84: checkcast com/tt/miniapp/page/AppbrandViewWindowBase
    //   87: invokevirtual takePage : (Lcom/tt/miniapp/page/AppbrandViewWindowBase;)Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   90: astore #4
    //   92: aload #4
    //   94: aload_1
    //   95: aload_2
    //   96: invokevirtual setupRouterParams : (Ljava/lang/String;Ljava/lang/String;)V
    //   99: aload #4
    //   101: ldc_w 'mApp.getService(PreloadM…rams(pageUrl, openType) }'
    //   104: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   107: aload_0
    //   108: aconst_null
    //   109: putfield mFirstPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   112: aload_0
    //   113: getfield mPageContainer : Landroid/widget/FrameLayout;
    //   116: aload #4
    //   118: checkcast android/view/View
    //   121: invokevirtual addView : (Landroid/view/View;)V
    //   124: aload_0
    //   125: aload #4
    //   127: putfield currentPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
    //   130: aload_0
    //   131: getfield isResumed : Z
    //   134: ifeq -> 143
    //   137: aload #4
    //   139: iconst_1
    //   140: invokevirtual onViewResume : (I)V
    //   143: return
  }
  
  public final void setupSnapShotSingle(String paramString1, String paramString2) {
    l.b(paramString1, "pageUrl");
    l.b(paramString2, "openType");
    AppBrandLogger.d("AppbrandHomePageViewWindow", new Object[] { "setupSnapShotSingle" });
    this.isTabMode = false;
    AppbrandSinglePage appbrandSinglePage = this.currentPage;
    if (appbrandSinglePage != null)
      appbrandSinglePage.setupRouterParams(paramString1, paramString2); 
    if (this.isResumed) {
      AppbrandSinglePage appbrandSinglePage1 = this.currentPage;
      if (appbrandSinglePage1 != null)
        appbrandSinglePage1.onViewResume(1); 
    } 
  }
  
  public final void setupTabHost(AppConfig.TabBar paramTabBar, String paramString1, String paramString2) {
    l.b(paramTabBar, "tabBarConfig");
    l.b(paramString1, "pageUrl");
    l.b(paramString2, "openType");
    if (paramTabBar.tabs == null)
      return; 
    AppBrandLogger.d("AppbrandHomePageViewWindow", new Object[] { "setupTabHost" });
    cleanUp();
    this.isTabMode = true;
    this.mTab2PageMap = new HashMap<String, AppbrandSinglePage>();
    this.mTabHost = (AppbrandTabHost)((PreloadManager)getMApp().getService(PreloadManager.class)).getPreloadedView(6);
    this.mContentView.addView((View)this.mTabHost);
    AppbrandTabHost appbrandTabHost2 = this.mTabHost;
    if (appbrandTabHost2 == null)
      l.a(); 
    appbrandTabHost2.setupTabs(paramTabBar, this.mTabHostCallback);
    AppbrandTabHost appbrandTabHost1 = this.mTabHost;
    if (appbrandTabHost1 == null)
      l.a(); 
    appbrandTabHost1.setId(View.generateViewId());
    adjustTabHostPosition();
    switchTab(paramString1, paramString2);
  }
  
  public final boolean shouldInterceptDrag() {
    return (getRoot().getContainer().getBackground() == null);
  }
  
  public final void switchTab(String paramString1, String paramString2) {
    l.b(paramString1, "pageUrl");
    l.b(paramString2, "openType");
    AppbrandTabHost appbrandTabHost = this.mTabHost;
    if (appbrandTabHost != null)
      appbrandTabHost.switchTab(paramString1, paramString2); 
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  final class TabHostCallbackImpl implements AppbrandTabHost.TabHostCallback {
    public final void onTabChanged(String param1String1, String param1String2, String param1String3) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'tabPagePath'
      //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_2
      //   7: ldc 'pageUrl'
      //   9: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   12: aload_3
      //   13: ldc 'openType'
      //   15: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   18: aload_0
      //   19: getfield this$0 : Lcom/tt/miniapp/page/AppbrandHomePageViewWindow;
      //   22: getfield currentPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
      //   25: astore #6
      //   27: aload_0
      //   28: getfield this$0 : Lcom/tt/miniapp/page/AppbrandHomePageViewWindow;
      //   31: getfield mTab2PageMap : Ljava/util/HashMap;
      //   34: astore #4
      //   36: aload #4
      //   38: ifnonnull -> 44
      //   41: invokestatic a : ()V
      //   44: aload #4
      //   46: aload_1
      //   47: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
      //   50: checkcast com/tt/miniapp/page/AppbrandSinglePage
      //   53: astore #4
      //   55: aload #4
      //   57: ifnonnull -> 188
      //   60: aload_0
      //   61: getfield this$0 : Lcom/tt/miniapp/page/AppbrandHomePageViewWindow;
      //   64: getfield mFirstPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
      //   67: astore #5
      //   69: aload #5
      //   71: ifnull -> 101
      //   74: aload #5
      //   76: invokevirtual getPage : ()Ljava/lang/String;
      //   79: aload_2
      //   80: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   83: ifeq -> 89
      //   86: goto -> 92
      //   89: aconst_null
      //   90: astore #5
      //   92: aload #5
      //   94: astore #4
      //   96: aload #5
      //   98: ifnonnull -> 135
      //   101: aload_0
      //   102: getfield this$0 : Lcom/tt/miniapp/page/AppbrandHomePageViewWindow;
      //   105: invokevirtual getMApp : ()Lcom/tt/miniapp/AppbrandApplicationImpl;
      //   108: ldc com/tt/miniapp/preload/PreloadManager
      //   110: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
      //   113: checkcast com/tt/miniapp/preload/PreloadManager
      //   116: aload_0
      //   117: getfield this$0 : Lcom/tt/miniapp/page/AppbrandHomePageViewWindow;
      //   120: checkcast com/tt/miniapp/page/AppbrandViewWindowBase
      //   123: invokevirtual takePage : (Lcom/tt/miniapp/page/AppbrandViewWindowBase;)Lcom/tt/miniapp/page/AppbrandSinglePage;
      //   126: astore #4
      //   128: aload #4
      //   130: aload_2
      //   131: aload_3
      //   132: invokevirtual setupRouterParams : (Ljava/lang/String;Ljava/lang/String;)V
      //   135: aload_0
      //   136: getfield this$0 : Lcom/tt/miniapp/page/AppbrandHomePageViewWindow;
      //   139: astore_2
      //   140: aload_2
      //   141: aconst_null
      //   142: putfield mFirstPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
      //   145: aload_2
      //   146: getfield mTab2PageMap : Ljava/util/HashMap;
      //   149: astore_2
      //   150: aload_2
      //   151: ifnonnull -> 157
      //   154: invokestatic a : ()V
      //   157: aload_2
      //   158: checkcast java/util/Map
      //   161: aload_1
      //   162: aload #4
      //   164: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   169: pop
      //   170: aload_0
      //   171: getfield this$0 : Lcom/tt/miniapp/page/AppbrandHomePageViewWindow;
      //   174: getfield mPageContainer : Landroid/widget/FrameLayout;
      //   177: aload #4
      //   179: checkcast android/view/View
      //   182: invokevirtual addView : (Landroid/view/View;)V
      //   185: goto -> 194
      //   188: aload #4
      //   190: aload_3
      //   191: invokevirtual sendOnAppRoute : (Ljava/lang/String;)V
      //   194: aload_0
      //   195: getfield this$0 : Lcom/tt/miniapp/page/AppbrandHomePageViewWindow;
      //   198: aload #4
      //   200: putfield currentPage : Lcom/tt/miniapp/page/AppbrandSinglePage;
      //   203: aload #6
      //   205: ifnull -> 214
      //   208: aload #6
      //   210: iconst_4
      //   211: invokevirtual setVisibility : (I)V
      //   214: aload #4
      //   216: iconst_0
      //   217: invokevirtual setVisibility : (I)V
      //   220: aload_0
      //   221: getfield this$0 : Lcom/tt/miniapp/page/AppbrandHomePageViewWindow;
      //   224: getfield isResumed : Z
      //   227: ifeq -> 247
      //   230: aload #6
      //   232: ifnull -> 241
      //   235: aload #6
      //   237: iconst_1
      //   238: invokevirtual onViewPause : (I)V
      //   241: aload #4
      //   243: iconst_1
      //   244: invokevirtual onViewResume : (I)V
      //   247: return
    }
  }
  
  static final class AppbrandHomePageViewWindow$cleanUp$1 extends m implements b<AppbrandSinglePage, x> {
    AppbrandHomePageViewWindow$cleanUp$1() {
      super(1);
    }
    
    public final void invoke(AppbrandSinglePage param1AppbrandSinglePage) {
      l.b(param1AppbrandSinglePage, "it");
      AppbrandHomePageViewWindow.this.mPageContainer.removeView((View)param1AppbrandSinglePage);
      if (AppbrandHomePageViewWindow.this.isResumed && param1AppbrandSinglePage == AppbrandHomePageViewWindow.this.currentPage)
        param1AppbrandSinglePage.onViewPause(1); 
      param1AppbrandSinglePage.onDestroy();
    }
  }
  
  static final class AppbrandHomePageViewWindow$onActivityDestroy$1 extends m implements b<AppbrandSinglePage, x> {
    public static final AppbrandHomePageViewWindow$onActivityDestroy$1 INSTANCE = new AppbrandHomePageViewWindow$onActivityDestroy$1();
    
    AppbrandHomePageViewWindow$onActivityDestroy$1() {
      super(1);
    }
    
    public final void invoke(AppbrandSinglePage param1AppbrandSinglePage) {
      l.b(param1AppbrandSinglePage, "it");
      param1AppbrandSinglePage.onActivityDestroy();
    }
  }
  
  public static final class AppbrandHomePageViewWindow$onCreate$1 implements ViewWindowDragRightLayout.OnDragListener {
    public final void onScrollFinish(boolean param1Boolean) {
      if (param1Boolean) {
        a<x> a = AppbrandHomePageViewWindow.this.getOnDragDispearEndListener();
        if (a != null)
          a.invoke(); 
      } 
    }
    
    public final void onScrollStart() {}
  }
  
  public static final class AppbrandHomePageViewWindow$setTabBarVisibility$$inlined$let$lambda$1 implements Animation.AnimationListener {
    AppbrandHomePageViewWindow$setTabBarVisibility$$inlined$let$lambda$1(AppbrandHomePageViewWindow param1AppbrandHomePageViewWindow, boolean param1Boolean1, boolean param1Boolean2) {}
    
    public final void onAnimationEnd(Animation param1Animation) {
      AppbrandHomePageViewWindow.this.post(new Runnable() {
            public final void run() {
              AppbrandHomePageViewWindow$setTabBarVisibility$$inlined$let$lambda$1.this.$it$inlined.setVisibility(8);
            }
          });
    }
    
    public final void onAnimationRepeat(Animation param1Animation) {}
    
    public final void onAnimationStart(Animation param1Animation) {}
  }
  
  static final class null implements Runnable {
    public final void run() {
      AppbrandHomePageViewWindow$setTabBarVisibility$$inlined$let$lambda$1.this.$it$inlined.setVisibility(8);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\page\AppbrandHomePageViewWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */