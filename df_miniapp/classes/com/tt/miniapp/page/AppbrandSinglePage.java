package com.tt.miniapp.page;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.tt.frontendapiinterface.e;
import com.tt.frontendapiinterface.h;
import com.tt.frontendapiinterface.j;
import com.tt.frontendapiinterface.k;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.KeyboardIdCreator;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.keyboarddetect.KeyboardHeightProvider;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlebar.AppbrandTitleBar;
import com.tt.miniapp.util.ConcaveScreenUtils;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.view.refresh.MiniappRefreshHeaderView;
import com.tt.miniapp.view.refresh.OnRefreshListener;
import com.tt.miniapp.view.refresh.SwipeToLoadLayout;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.miniapp.view.webcore.NestWebView;
import com.tt.miniapp.view.webcore.TTWebViewSupportWebView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.c.b;
import com.tt.option.n.c;
import d.f.b.g;
import d.f.b.l;
import d.u;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

public final class AppbrandSinglePage extends FrameLayout implements k, WebViewManager.IRender, OnRefreshListener {
  public static final Companion Companion = new Companion(null);
  
  public static int fragmentIdentity;
  
  private HashMap _$_findViewCache;
  
  private final ArrayList<h> backPressedListenerList;
  
  private boolean disableSwipeBack;
  
  private boolean isOnDestroyView;
  
  private boolean isReLaunch;
  
  private boolean isRedirectTo;
  
  private boolean isWebViewFragment;
  
  public final AppbrandApplicationImpl mApp;
  
  public b mBottomBar;
  
  public final ViewGroup mBottomBarContainer;
  
  public JSONObject mBottomBarData;
  
  public final View mContentView;
  
  public boolean mEnablePullDownRefresh;
  
  private c mFileChooseHandler;
  
  public AppbrandViewWindowBase mHost;
  
  private final NativeNestWebView mNativeNestWebView;
  
  public String mPagePath;
  
  private String mPageQuery;
  
  private TimeMeter mPageStartTime;
  
  public String mPageUrl;
  
  public final SwipeToLoadLayout mRefreshLayout;
  
  private int mResumeCount;
  
  private JSONObject mSnapShotTitleBarJson;
  
  private final View mView;
  
  private int skeyboardId;
  
  private AppbrandTitleBar titleBar;
  
  public AppbrandSinglePage(Context paramContext, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramContext);
    this.mApp = paramAppbrandApplicationImpl;
    this.backPressedListenerList = new ArrayList<h>();
    if (!ThreadUtil.isUIThread())
      DebugUtil.logOrThrow("AppbrandSinglePage", new Object[] { "Init must be called on UI Thread." }); 
    View view2 = LayoutInflater.from(paramContext).inflate(2097676307, (ViewGroup)this);
    l.a(view2, "LayoutInflater.from(cont…icroapp_m_fragment, this)");
    this.mView = view2;
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    this.titleBar = new AppbrandTitleBar((Context)appbrandContext.getApplicationContext(), this);
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "AppbrandPage_<init>" });
    AppbrandApplicationImpl appbrandApplicationImpl = this.mApp;
    AppbrandSinglePage appbrandSinglePage = this;
    this.mNativeNestWebView = new NativeNestWebView(paramContext, appbrandApplicationImpl, appbrandSinglePage);
    this.mNativeNestWebView.continuePreloadIfNeed();
    View view1 = this.mView.findViewById(2097545261);
    l.a(view1, "mView.findViewById(R.id.microapp_m_content_view)");
    this.mContentView = view1;
    this.mBottomBarContainer = (ViewGroup)this.mView.findViewById(2097545249);
    view1 = this.mView.findViewById(2097545374);
    l.a(view1, "mView.findViewById(R.id.microapp_m_refreshLayout)");
    this.mRefreshLayout = (SwipeToLoadLayout)view1;
    this.mRefreshLayout.setOnRefreshListener(this);
    this.mRefreshLayout.setRefreshEnabled(false);
    view1 = this.mRefreshLayout.getHeaderView();
    if (view1 != null) {
      ((MiniappRefreshHeaderView)view1).setRefreshState(new MiniappRefreshHeaderView.IRefreshState() {
            public final void onComplete() {
              AppbrandSinglePage.this.mRefreshLayout.setEnabled(AppbrandSinglePage.this.mEnablePullDownRefresh);
            }
            
            public final void onRefresh() {}
          });
      this.mRefreshLayout.setEnabled(false);
      view1 = this.mView.findViewById(2097545392);
      if (view1 != null) {
        ViewGroup viewGroup = (ViewGroup)view1;
        ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "AppbrandPage_beforeGetPreloadWebView" });
        UIUtils.removeParentView((View)this.mNativeNestWebView);
        viewGroup.addView((View)this.mNativeNestWebView, new ViewGroup.LayoutParams(-1, -1));
        this.mNativeNestWebView.setScrollListener(new TTWebViewSupportWebView.OnScrollListener() {
              public final void onBackNativeScrollChanged(int param1Int1, int param1Int2, int param1Int3) {}
              
              public final void onNativeScrollChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
                AppbrandSinglePage.this.getTitleBar().autoTransparentTitleBar(param1Int2);
              }
            });
        if ((DebugManager.getInst()).mIsDebugOpen)
          WebView.setWebContentsDebuggingEnabled(true); 
        this.mApp.getWebViewManager().addRender(appbrandSinglePage);
        this.mResumeCount = 0;
        return;
      } 
      throw new u("null cannot be cast to non-null type android.view.ViewGroup");
    } 
    throw new u("null cannot be cast to non-null type com.tt.miniapp.view.refresh.MiniappRefreshHeaderView");
  }
  
  private final void createBottomBarIfNeed(String paramString) {
    if (this.mBottomBarContainer != null && !TextUtils.isEmpty(paramString)) {
      AppbrandViewWindowBase appbrandViewWindowBase = this.mHost;
      if (appbrandViewWindowBase != null) {
        Activity activity = appbrandViewWindowBase.getActivity();
      } else {
        appbrandViewWindowBase = null;
      } 
      if (appbrandViewWindowBase != null && this.mBottomBar == null) {
        if (!isStartPage())
          return; 
        if (this.mBottomBarData == null) {
          this.mBottomBarData = b.a(paramString);
          if (this.mBottomBarData == null)
            return; 
        } 
        JSONObject jSONObject = this.mBottomBarData;
        if (jSONObject == null)
          l.a(); 
        String str = jSONObject.optString("tag");
        if (str == null)
          return; 
        this.mBottomBar = HostDependManager.getInst().createBottomBar(str, paramString, new AppbrandSinglePage$createBottomBarIfNeed$1());
        b b1 = this.mBottomBar;
        if (b1 == null)
          return; 
        if (b1 == null)
          l.a(); 
        if (!b1.a(this.mBottomBarContainer))
          return; 
        if (this.mBottomBar == null)
          l.a(); 
        ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
          ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
          b b2 = this.mBottomBar;
          if (b2 == null)
            l.a(); 
          marginLayoutParams.bottomMargin = b2.a();
        } 
      } 
    } 
  }
  
  private final void handleBottomBar() {
    if (this.mBottomBarContainer == null)
      return; 
    String str = this.mApp.getSchema();
    if (str == null)
      return; 
    b b1 = this.mBottomBar;
    if (b1 != null) {
      if (TextUtils.equals(b1.a, str))
        return; 
      b1.b();
      ViewGroup.LayoutParams layoutParams = this.mContentView.getLayoutParams();
      if (layoutParams instanceof ViewGroup.MarginLayoutParams)
        ((ViewGroup.MarginLayoutParams)layoutParams).bottomMargin = 0; 
      this.mContentView.setLayoutParams(layoutParams);
      this.mBottomBar = null;
      this.mBottomBarData = null;
    } 
    createBottomBarIfNeed(str);
  }
  
  private final void initPullDownRefresh() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   4: invokevirtual getAppConfig : ()Lcom/tt/miniapp/AppConfig;
    //   7: astore_2
    //   8: aload_2
    //   9: ifnull -> 87
    //   12: aload_2
    //   13: getfield global : Lcom/tt/miniapp/AppConfig$Global;
    //   16: astore_3
    //   17: aload_3
    //   18: ifnull -> 45
    //   21: aload_3
    //   22: getfield window : Lcom/tt/miniapp/AppConfig$Window;
    //   25: astore_3
    //   26: aload_3
    //   27: ifnull -> 45
    //   30: aload_3
    //   31: getfield hasEnablePullDownRefresh : Z
    //   34: ifeq -> 45
    //   37: aload_3
    //   38: getfield enablePullDownRefresh : Z
    //   41: istore_1
    //   42: goto -> 47
    //   45: iconst_0
    //   46: istore_1
    //   47: aload_2
    //   48: getfield page : Lcom/tt/miniapp/AppConfig$Page;
    //   51: astore_2
    //   52: aload_2
    //   53: ifnull -> 84
    //   56: aload_2
    //   57: aload_0
    //   58: getfield mPagePath : Ljava/lang/String;
    //   61: invokevirtual getWindow : (Ljava/lang/String;)Lcom/tt/miniapp/AppConfig$Window;
    //   64: astore_2
    //   65: aload_2
    //   66: ifnull -> 84
    //   69: aload_2
    //   70: getfield hasEnablePullDownRefresh : Z
    //   73: ifeq -> 84
    //   76: aload_2
    //   77: getfield enablePullDownRefresh : Z
    //   80: istore_1
    //   81: goto -> 104
    //   84: goto -> 104
    //   87: ldc 'AppbrandSinglePage'
    //   89: iconst_1
    //   90: anewarray java/lang/Object
    //   93: dup
    //   94: iconst_0
    //   95: ldc_w 'initPullDownRefresh appconfig == null'
    //   98: aastore
    //   99: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   102: iconst_0
    //   103: istore_1
    //   104: aload_0
    //   105: iload_1
    //   106: putfield mEnablePullDownRefresh : Z
    //   109: aload_0
    //   110: getfield mPagePath : Ljava/lang/String;
    //   113: invokestatic disableScroll : (Ljava/lang/String;)Z
    //   116: ifeq -> 128
    //   119: aload_0
    //   120: getfield mRefreshLayout : Lcom/tt/miniapp/view/refresh/SwipeToLoadLayout;
    //   123: iconst_0
    //   124: invokevirtual setRefreshEnabled : (Z)V
    //   127: return
    //   128: aload_0
    //   129: getfield mRefreshLayout : Lcom/tt/miniapp/view/refresh/SwipeToLoadLayout;
    //   132: iload_1
    //   133: invokevirtual setRefreshEnabled : (Z)V
    //   136: return
  }
  
  private final void initSwipeBack() {
    AppConfig appConfig = this.mApp.getAppConfig();
    if (appConfig != null) {
      AppConfig.Page page = appConfig.page;
      if (page != null) {
        AppConfig.Window window = page.getWindow(this.mPagePath);
        if (window != null && window.hasDisableSwipeBack)
          this.disableSwipeBack = window.disableSwipeBack; 
      } 
    } else {
      AppBrandLogger.e("AppbrandSinglePage", new Object[] { "initSwipeBack appconfig == null" });
    } 
    AppBrandLogger.e("AppbrandSinglePage", new Object[] { "disableSwipeBack", Boolean.valueOf(this.disableSwipeBack) });
    AppbrandViewWindowBase appbrandViewWindowBase = this.mHost;
    if (appbrandViewWindowBase == null)
      l.a(); 
    appbrandViewWindowBase.setDragEnable(this.disableSwipeBack ^ true);
  }
  
  private final boolean isStartPage() {
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    return (appInfoEntity == null) ? false : TextUtils.equals(this.mPageUrl, appInfoEntity.oriStartPage);
  }
  
  private final void logEnterEvent(int paramInt) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private final void logExitEvent(int paramInt) {
    TimeMeter timeMeter = this.mPageStartTime;
    if (timeMeter != null) {
      if (timeMeter.isRunning() != true)
        return; 
      String str = "";
      if (paramInt != 0)
        str = "new_page"; 
      try {
        InnerEventHelper.mpStayPageEvent(this.mPagePath, this.mPageQuery, TimeMeter.stop(this.mPageStartTime), str, this.isWebViewFragment);
        return;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "AppbrandSinglePage", exception.getStackTrace());
      } 
    } 
  }
  
  private final void onKeyBoardHide() {
    AppBrandLogger.d("AppbrandSinglePage", new Object[] { "onKeyBoardHide mLastFocusInput webviewId ", Integer.valueOf(getWebViewId()) });
    if (System.identityHashCode(this) != fragmentIdentity)
      return; 
    this.mNativeNestWebView.onKeyboardHide();
  }
  
  private final void onKeyBoardShow(int paramInt) {
    AppBrandLogger.d("AppbrandSinglePage", new Object[] { "onKeyBoardShow webviewId ", Integer.valueOf(getWebViewId()) });
    fragmentIdentity = System.identityHashCode(this);
    this.mNativeNestWebView.onKeyboardShow(paramInt);
  }
  
  private final void refreshCachePageState() {
    String str;
    this.mApp.setCurrentWebViewId(getWebViewId());
    this.mApp.setCurrentPageUrl(this.mPageUrl);
    this.mApp.setCurrentPagePath(this.mPagePath);
    AppbrandApplicationImpl appbrandApplicationImpl = this.mApp;
    if (this.isWebViewFragment) {
      str = "webview";
    } else {
      str = "mp_native";
    } 
    appbrandApplicationImpl.setCurrentPageType(str);
  }
  
  private final void sendSwitchRemoteDebugWebViewMsg() {
    if ((DebugManager.getInst()).mRemoteDebugEnable) {
      DebugManager debugManager = DebugManager.getInst();
      l.a(debugManager, "DebugManager.getInst()");
      Message message = debugManager.getDebugHandler().obtainMessage();
      StringBuilder stringBuilder1 = new StringBuilder("mPageContent ");
      stringBuilder1.append(this.mPagePath);
      String str = stringBuilder1.toString();
      StringBuilder stringBuilder2 = new StringBuilder("getWebViewId ");
      stringBuilder2.append(getWebViewId());
      AppBrandLogger.d("AppbrandSinglePage", new Object[] { str, stringBuilder2.toString() });
      try {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("mPageContent", this.mPagePath);
        jSONObject.put("webviewId", getWebViewId());
        message.obj = jSONObject;
        message.what = 2000;
        DebugManager debugManager1 = DebugManager.getInst();
        l.a(debugManager1, "DebugManager.getInst()");
        debugManager1.getDebugHandler().sendMessageDelayed(message, 300L);
        return;
      } catch (Exception exception) {
        return;
      } 
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
  
  public final void bindHost(AppbrandViewWindowBase paramAppbrandViewWindowBase) {
    l.b(paramAppbrandViewWindowBase, "host");
    if (this.mHost == null) {
      this.mHost = paramAppbrandViewWindowBase;
      return;
    } 
    throw new RuntimeException("Cannot rebind host.");
  }
  
  public final Activity getActivity() {
    AppbrandViewWindowBase appbrandViewWindowBase = this.mHost;
    return (appbrandViewWindowBase != null) ? appbrandViewWindowBase.getActivity() : null;
  }
  
  public final b getBottomBar() {
    return this.mBottomBar;
  }
  
  public final Activity getCurrentActivity() {
    return getActivity();
  }
  
  public final c getFileChooseHandler() {
    if (this.mFileChooseHandler == null) {
      HostDependManager hostDependManager = HostDependManager.getInst();
      AppbrandViewWindowBase appbrandViewWindowBase = this.mHost;
      if (appbrandViewWindowBase == null)
        l.a(); 
      this.mFileChooseHandler = hostDependManager.createChooseFileHandler(appbrandViewWindowBase.getActivity());
    } 
    return this.mFileChooseHandler;
  }
  
  public final AppbrandViewWindowBase getHost() {
    return this.mHost;
  }
  
  public final NativeNestWebView getNativeNestWebView() {
    return this.mNativeNestWebView;
  }
  
  public final NativeViewManager getNativeViewManager() {
    NativeViewManager nativeViewManager = this.mNativeNestWebView.getNativeViewManager();
    l.a(nativeViewManager, "mNativeNestWebView.nativeViewManager");
    return nativeViewManager;
  }
  
  public final String getPage() {
    return this.mPageUrl;
  }
  
  public final String getPagePath() {
    return this.mPagePath;
  }
  
  public final int getRenderHeight() {
    return this.mNativeNestWebView.getMeasuredHeight();
  }
  
  public final int getRenderWidth() {
    return this.mNativeNestWebView.getMeasuredWidth();
  }
  
  public final View getRootView() {
    return this.mView;
  }
  
  public final AppbrandTitleBar getTitleBar() {
    return this.titleBar;
  }
  
  public final int getTitleBarHeight() {
    return this.titleBar.getTitleBarHeight();
  }
  
  public final WebView getWebView() {
    NestWebView nestWebView = this.mNativeNestWebView.getWebView();
    l.a(nestWebView, "mNativeNestWebView.webView");
    return (WebView)nestWebView;
  }
  
  public final int getWebViewId() {
    return this.mNativeNestWebView.getWebViewId();
  }
  
  public final void hideBottomBar() {
    if (this.mBottomBar != null) {
      if (this.mBottomBarContainer == null)
        return; 
      ThreadUtil.runOnUIThread(new AppbrandSinglePage$hideBottomBar$1());
    } 
  }
  
  public final void hideNavigationBarHomeButton() {
    this.titleBar.hideBackHomeButton();
  }
  
  public final void initPullDownRefreshHeader$miniapp_release() {
    AppConfig.Window window1;
    String str1;
    AppConfig.Window window2;
    AppConfig appConfig = this.mApp.getAppConfig();
    String str2 = "dark";
    if (appConfig != null) {
      String str3;
      String str4;
      AppConfig.Global global = appConfig.global;
      if (global != null) {
        String str;
        AppConfig.Window window = global.window;
        if (window != null && window.hasBackgroundColor) {
          str = window.backgroundColor;
          l.a(str, "globleWindow.backgroundColor");
        } else {
          str = "#FFFFFF";
        } 
        str4 = str2;
        str3 = str;
        if (window != null) {
          str4 = str2;
          str3 = str;
          if (window.hasBackgroundTextStyle) {
            str4 = window.backgroundTextStyle;
            l.a(str4, "globleWindow.backgroundTextStyle");
            str3 = str;
          } 
        } 
      } else {
        str3 = "#FFFFFF";
        str4 = str2;
      } 
      AppConfig.Page page = appConfig.page;
      if (page != null) {
        String str;
        window2 = page.getWindow(this.mPagePath);
        if (window2 != null && window2.hasBackgroundColor) {
          str = window2.backgroundColor;
          l.a(str, "pageWindow.backgroundColor");
        } else {
          str = str3;
        } 
        str3 = str4;
        str1 = str;
        if (window2 != null) {
          str3 = str4;
          str1 = str;
          if (window2.hasBackgroundTextStyle) {
            str3 = window2.backgroundTextStyle;
            l.a(str3, "pageWindow.backgroundTextStyle");
            str1 = str;
          } 
        } 
      } else {
        str1 = str3;
        str3 = str4;
      } 
    } else {
      AppBrandLogger.e("AppbrandSinglePage", new Object[] { "initPullDownRefreshHeader appcofnig == null" });
      str1 = "#FFFFFF";
      window1 = window2;
    } 
    int i = UIUtils.parseColor(str1, "#FFFFFF");
    this.mRefreshLayout.setBackgroundColor(i);
    this.mNativeNestWebView.getWebView().setBackgroundColor(i);
    View view = this.mRefreshLayout.getHeaderView();
    if (view != null) {
      MiniappRefreshHeaderView miniappRefreshHeaderView = (MiniappRefreshHeaderView)view;
      if (TextUtils.equals((CharSequence)window1, "light")) {
        miniappRefreshHeaderView.setLoadingPoint1OrgColor(Color.parseColor("#33FFFFFF"));
        miniappRefreshHeaderView.setLoadingPoint2OrgColor(Color.parseColor("#33FFFFFF"));
        miniappRefreshHeaderView.setLoadingPoint3OrgColor(Color.parseColor("#33FFFFFF"));
        miniappRefreshHeaderView.setLoadingPoint1AnimColor(new int[] { Color.parseColor("#4dFFFFFF"), Color.parseColor("#33FFFFFF") });
        miniappRefreshHeaderView.setLoadingPoint2AnimColor(new int[] { Color.parseColor("#4dFFFFFF"), Color.parseColor("#33FFFFFF") });
        miniappRefreshHeaderView.setLoadingPoint3AnimColor(new int[] { Color.parseColor("#4dFFFFFF"), Color.parseColor("#33FFFFFF") });
        return;
      } 
      miniappRefreshHeaderView.setLoadingPoint1OrgColor(Color.parseColor("#1a000000"));
      miniappRefreshHeaderView.setLoadingPoint2OrgColor(Color.parseColor("#1a000000"));
      miniappRefreshHeaderView.setLoadingPoint3OrgColor(Color.parseColor("#1a000000"));
      miniappRefreshHeaderView.setLoadingPoint1AnimColor(new int[] { Color.parseColor("#33000000"), Color.parseColor("#1a000000") });
      miniappRefreshHeaderView.setLoadingPoint2AnimColor(new int[] { Color.parseColor("#33000000"), Color.parseColor("#1a000000") });
      miniappRefreshHeaderView.setLoadingPoint3AnimColor(new int[] { Color.parseColor("#33000000"), Color.parseColor("#1a000000") });
      return;
    } 
    throw new u("null cannot be cast to non-null type com.tt.miniapp.view.refresh.MiniappRefreshHeaderView");
  }
  
  public final void initSnapShotTitleBarData(JSONObject paramJSONObject) {
    l.b(paramJSONObject, "data");
    this.mSnapShotTitleBarJson = paramJSONObject;
    this.titleBar.setFirstRenderData(paramJSONObject);
  }
  
  public final boolean isBottomBarHidden() {
    if (this.mBottomBar != null) {
      ViewGroup viewGroup = this.mBottomBarContainer;
      if (viewGroup != null && viewGroup.getVisibility() != 4 && this.mBottomBarContainer.getVisibility() != 8)
        return false; 
    } 
    return true;
  }
  
  public final boolean isBottomBarShown() {
    if (this.mBottomBar != null) {
      ViewGroup viewGroup = this.mBottomBarContainer;
      if (viewGroup != null && viewGroup.getVisibility() == 0)
        return true; 
    } 
    return false;
  }
  
  public final boolean isReLaunch() {
    return this.isReLaunch;
  }
  
  public final boolean isRedirectTo() {
    return this.isRedirectTo;
  }
  
  public final boolean isWebViewFragment() {
    return this.isWebViewFragment;
  }
  
  public final void onActivityDestroy() {}
  
  public final void onActivityPause() {}
  
  public final void onActivityResume() {
    createBottomBarIfNeed(this.mApp.getSchema());
  }
  
  public final void onAppInstallSuccess() {
    this.mNativeNestWebView.continuePreloadIfNeed();
  }
  
  public final void onAppStartLaunching() {
    AppbrandServiceManager.ServiceBase serviceBase = this.mApp.getService(RenderSnapShotManager.class);
    l.a(serviceBase, "mApp.getService(RenderSnapShotManager::class.java)");
    if (!((RenderSnapShotManager)serviceBase).isSnapShotRender())
      this.mNativeNestWebView.markForceLoadPathFrameJs(); 
  }
  
  public final Boolean onBackPressed() {
    return Boolean.valueOf(this.mNativeNestWebView.onBackPressed());
  }
  
  public final void onDestroy() {
    AppBrandLogger.d("AppbrandSinglePage", new Object[] { "onDestroy" });
    this.isOnDestroyView = true;
    e e = this.mApp.getActivityLife();
    if (e != null)
      e.unRegisterKeyboardListener(this); 
    b b1 = this.mBottomBar;
    if (b1 != null)
      b1.b(); 
    this.titleBar.destroy();
    if ((DebugManager.getInst()).mRemoteDebugEnable)
      DebugManager.getInst().removeWebviewId(getWebViewId()); 
    this.mApp.getWebViewManager().removeRender(getWebViewId());
    this.mNativeNestWebView.onDestroy();
    this.backPressedListenerList.clear();
  }
  
  public final void onKeyboardHeightChanged(int paramInt1, int paramInt2) {
    AppBrandLogger.d("AppbrandSinglePage", new Object[] { "onKeyboardHeightChanged" });
    if (isShown()) {
      AppbrandViewWindowBase appbrandViewWindowBase = this.mHost;
      if (appbrandViewWindowBase == null)
        l.a(); 
      if (appbrandViewWindowBase.isViewResume()) {
        appbrandViewWindowBase = this.mHost;
        if (appbrandViewWindowBase == null)
          l.a(); 
        if (!appbrandViewWindowBase.isActivityResume())
          return; 
        if (ConcaveScreenUtils.isVivoConcaveScreen() && paramInt1 == 120) {
          onKeyBoardHide();
          return;
        } 
        if (paramInt1 > 0) {
          onKeyBoardShow(paramInt1);
          return;
        } 
        onKeyBoardHide();
      } 
    } 
  }
  
  public final void onNativeWebViewPageFinished(boolean paramBoolean) {
    this.titleBar.setWebViewPageBackView(paramBoolean);
  }
  
  public final void onRefresh() {
    AppBrandLogger.d("AppbrandSinglePage", new Object[] { "onRefresh" });
    try {
      j j = this.mApp.getJsBridge();
      if (j == null)
        l.a(); 
      j.sendMsgToJsCore("onPullDownRefresh", (new JSONObject()).toString(), getWebViewId());
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("AppbrandSinglePage", new Object[] { "onRefresh", exception });
      return;
    } 
  }
  
  public final void onStartPullDownRefresh() {
    this.mRefreshLayout.setRefreshing(true);
  }
  
  public final void onStopPullDownRefresh() {
    this.mRefreshLayout.setRefreshing(false);
  }
  
  public final void onThemeChanged(String paramString) {}
  
  public final void onViewPause(int paramInt) {
    AppBrandLogger.d("AppbrandSinglePage", new Object[] { "onPause" });
    if (KeyboardHeightProvider.mkeyboardId == this.skeyboardId) {
      try {
        InputMethodUtil.hideSoftKeyboard((EditText)getNativeViewManager().getFocusedInput(), getContext());
      } catch (Exception exception) {
        AppBrandLogger.e("AppbrandSinglePage", new Object[] { "hide input method error", exception });
      } 
      onKeyBoardHide();
    } 
    this.mNativeNestWebView.onViewPause();
    this.mRefreshLayout.setRefreshing(false);
    logExitEvent(paramInt);
  }
  
  public final void onViewResume(int paramInt) {
    AppBrandLogger.d("AppbrandSinglePage", new Object[] { "onResume" });
    this.skeyboardId = KeyboardIdCreator.create();
    KeyboardHeightProvider.mkeyboardId = this.skeyboardId;
    this.mApp.getWebViewManager().setCurrentRender(this);
    handleBottomBar();
    this.mResumeCount++;
    logEnterEvent(paramInt);
    sendSwitchRemoteDebugWebViewMsg();
    refreshCachePageState();
    this.mNativeNestWebView.onViewResume();
    this.titleBar.updateLeftViewVisibility();
    if (isShown()) {
      AppbrandViewWindowBase appbrandViewWindowBase = this.mHost;
      if (appbrandViewWindowBase == null)
        l.a(); 
      if (appbrandViewWindowBase.isViewResume()) {
        appbrandViewWindowBase = this.mHost;
        if (appbrandViewWindowBase == null)
          l.a(); 
        if (appbrandViewWindowBase.isActivityResume()) {
          e e = this.mApp.getActivityLife();
          if (e != null)
            e.registerKeyboardListener(this); 
          this.titleBar.makeStatusBar();
        } 
      } 
    } 
  }
  
  protected final void onVisibilityChanged(View paramView, int paramInt) {
    l.b(paramView, "changedView");
    if (paramInt == 0 && this.mHost != null && isShown()) {
      AppbrandViewWindowBase appbrandViewWindowBase = this.mHost;
      if (appbrandViewWindowBase == null)
        l.a(); 
      if (appbrandViewWindowBase.isViewResume()) {
        appbrandViewWindowBase = this.mHost;
        if (appbrandViewWindowBase == null)
          l.a(); 
        if (appbrandViewWindowBase.isActivityResume()) {
          e e = this.mApp.getActivityLife();
          if (e != null)
            e.registerKeyboardListener(this); 
          this.titleBar.makeStatusBar();
        } 
      } 
    } 
  }
  
  public final boolean pullDownRefreshEnabled() {
    return this.mRefreshLayout.isEnabled();
  }
  
  public final void sendOnAppRoute(String paramString) {
    l.b(paramString, "openType");
    this.mNativeNestWebView.setOpenType(paramString);
    this.mNativeNestWebView.sendOnAppRoute();
  }
  
  public final void setDisableRefresh(boolean paramBoolean) {
    this.mRefreshLayout.setDisableRefresh(paramBoolean);
  }
  
  public final void setLoadAsWebView() {
    this.isWebViewFragment = true;
  }
  
  public final void setNavigationBarColor(String paramString1, String paramString2) {
    l.b(paramString1, "frontColor");
    l.b(paramString2, "backgroundColor");
    this.titleBar.setNavigationBarColor(paramString1, paramString2);
  }
  
  public final void setNavigationBarLoading(boolean paramBoolean) {
    this.titleBar.setTitleBarLoading(paramBoolean);
  }
  
  public final void setNavigationBarTitle(String paramString) {
    l.b(paramString, "title");
    this.titleBar.setNavigationBarTitleText(paramString);
  }
  
  public final void setTitleBar(AppbrandTitleBar paramAppbrandTitleBar) {
    l.b(paramAppbrandTitleBar, "<set-?>");
    this.titleBar = paramAppbrandTitleBar;
  }
  
  public final void setupRouterParams(String paramString1, String paramString2) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'pageUrl'
    //   4: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_2
    //   8: ldc_w 'openType'
    //   11: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   14: aload_0
    //   15: getfield mPageUrl : Ljava/lang/String;
    //   18: checkcast java/lang/CharSequence
    //   21: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   24: ifeq -> 412
    //   27: aload_1
    //   28: checkcast java/lang/CharSequence
    //   31: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   34: ifne -> 412
    //   37: aload_2
    //   38: checkcast java/lang/CharSequence
    //   41: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   44: ifeq -> 50
    //   47: goto -> 412
    //   50: aload_0
    //   51: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   54: ldc com/tt/miniapp/util/TimeLogger
    //   56: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   59: checkcast com/tt/miniapp/util/TimeLogger
    //   62: iconst_1
    //   63: anewarray java/lang/String
    //   66: dup
    //   67: iconst_0
    //   68: ldc_w 'AppbrandPage_setupRouterParams'
    //   71: aastore
    //   72: invokevirtual logTimeDuration : ([Ljava/lang/String;)V
    //   75: aload_0
    //   76: aload_1
    //   77: putfield mPageUrl : Ljava/lang/String;
    //   80: aload_0
    //   81: getfield mPageUrl : Ljava/lang/String;
    //   84: checkcast java/lang/CharSequence
    //   87: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   90: ifne -> 295
    //   93: aload_0
    //   94: getfield mPageUrl : Ljava/lang/String;
    //   97: astore_1
    //   98: aload_1
    //   99: ifnonnull -> 105
    //   102: invokestatic a : ()V
    //   105: aload_1
    //   106: checkcast java/lang/CharSequence
    //   109: astore_1
    //   110: new d/m/l
    //   113: dup
    //   114: ldc_w '\?'
    //   117: invokespecial <init> : (Ljava/lang/String;)V
    //   120: aload_1
    //   121: iconst_0
    //   122: invokevirtual split : (Ljava/lang/CharSequence;I)Ljava/util/List;
    //   125: astore_1
    //   126: aload_1
    //   127: invokeinterface isEmpty : ()Z
    //   132: ifne -> 211
    //   135: aload_1
    //   136: aload_1
    //   137: invokeinterface size : ()I
    //   142: invokeinterface listIterator : (I)Ljava/util/ListIterator;
    //   147: astore #5
    //   149: aload #5
    //   151: invokeinterface hasPrevious : ()Z
    //   156: ifeq -> 211
    //   159: aload #5
    //   161: invokeinterface previous : ()Ljava/lang/Object;
    //   166: checkcast java/lang/String
    //   169: checkcast java/lang/CharSequence
    //   172: invokeinterface length : ()I
    //   177: ifne -> 185
    //   180: iconst_1
    //   181: istore_3
    //   182: goto -> 187
    //   185: iconst_0
    //   186: istore_3
    //   187: iload_3
    //   188: ifne -> 149
    //   191: aload_1
    //   192: checkcast java/lang/Iterable
    //   195: aload #5
    //   197: invokeinterface nextIndex : ()I
    //   202: iconst_1
    //   203: iadd
    //   204: invokestatic b : (Ljava/lang/Iterable;I)Ljava/util/List;
    //   207: astore_1
    //   208: goto -> 215
    //   211: invokestatic a : ()Ljava/util/List;
    //   214: astore_1
    //   215: aload_1
    //   216: checkcast java/util/Collection
    //   219: iconst_0
    //   220: anewarray java/lang/String
    //   223: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   228: astore_1
    //   229: aload_1
    //   230: ifnull -> 284
    //   233: aload_1
    //   234: checkcast [Ljava/lang/String;
    //   237: astore_1
    //   238: aload_1
    //   239: arraylength
    //   240: iconst_1
    //   241: if_icmple -> 264
    //   244: aload_0
    //   245: aload_1
    //   246: iconst_0
    //   247: aaload
    //   248: invokestatic cutHtmlSuffix : (Ljava/lang/String;)Ljava/lang/String;
    //   251: putfield mPagePath : Ljava/lang/String;
    //   254: aload_0
    //   255: aload_1
    //   256: iconst_1
    //   257: aaload
    //   258: putfield mPageQuery : Ljava/lang/String;
    //   261: goto -> 295
    //   264: aload_0
    //   265: aload_1
    //   266: iconst_0
    //   267: aaload
    //   268: invokestatic cutHtmlSuffix : (Ljava/lang/String;)Ljava/lang/String;
    //   271: putfield mPagePath : Ljava/lang/String;
    //   274: aload_0
    //   275: ldc_w ''
    //   278: putfield mPageQuery : Ljava/lang/String;
    //   281: goto -> 295
    //   284: new d/u
    //   287: dup
    //   288: ldc_w 'null cannot be cast to non-null type kotlin.Array<T>'
    //   291: invokespecial <init> : (Ljava/lang/String;)V
    //   294: athrow
    //   295: aload_0
    //   296: invokespecial refreshCachePageState : ()V
    //   299: aload_0
    //   300: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   303: invokevirtual getWebViewManager : ()Lcom/tt/miniapp/WebViewManager;
    //   306: aload_0
    //   307: checkcast com/tt/miniapp/WebViewManager$IRender
    //   310: invokevirtual setCurrentRender : (Lcom/tt/miniapp/WebViewManager$IRender;)V
    //   313: aload_0
    //   314: getfield mNativeNestWebView : Lcom/tt/miniapp/view/webcore/NativeNestWebView;
    //   317: aload_2
    //   318: aload_0
    //   319: getfield mPageUrl : Ljava/lang/String;
    //   322: aload_0
    //   323: getfield mPagePath : Ljava/lang/String;
    //   326: aload_0
    //   327: getfield mPageQuery : Ljava/lang/String;
    //   330: invokevirtual updateArgument : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   333: aload_0
    //   334: aload_2
    //   335: ldc_w 'reLaunch'
    //   338: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   341: putfield isReLaunch : Z
    //   344: aload_0
    //   345: aload_2
    //   346: ldc_w 'redirectTo'
    //   349: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   352: putfield isRedirectTo : Z
    //   355: aload_0
    //   356: invokespecial initSwipeBack : ()V
    //   359: new com/tt/miniapp/page/AppbrandSinglePage$setupRouterParams$1
    //   362: dup
    //   363: aload_0
    //   364: invokespecial <init> : (Lcom/tt/miniapp/page/AppbrandSinglePage;)V
    //   367: checkcast java/lang/Runnable
    //   370: invokestatic runOnUIThread : (Ljava/lang/Runnable;)V
    //   373: invokestatic startPageRender : ()V
    //   376: aload_0
    //   377: getfield mPagePath : Ljava/lang/String;
    //   380: invokestatic disableScroll : (Ljava/lang/String;)Z
    //   383: istore #4
    //   385: aload_0
    //   386: getfield mNativeNestWebView : Lcom/tt/miniapp/view/webcore/NativeNestWebView;
    //   389: iload #4
    //   391: invokevirtual setDisableScroll : (Z)V
    //   394: aload_0
    //   395: getfield mRefreshLayout : Lcom/tt/miniapp/view/refresh/SwipeToLoadLayout;
    //   398: iload #4
    //   400: invokevirtual setDisableScroll : (Z)V
    //   403: aload_0
    //   404: invokespecial initPullDownRefresh : ()V
    //   407: aload_0
    //   408: invokevirtual initPullDownRefreshHeader$miniapp_release : ()V
    //   411: return
    //   412: ldc 'AppbrandSinglePage'
    //   414: bipush #6
    //   416: anewarray java/lang/Object
    //   419: dup
    //   420: iconst_0
    //   421: ldc_w 'Illegal setupRouterParams, mPageUrl:'
    //   424: aastore
    //   425: dup
    //   426: iconst_1
    //   427: aload_0
    //   428: getfield mPageUrl : Ljava/lang/String;
    //   431: aastore
    //   432: dup
    //   433: iconst_2
    //   434: ldc_w 'pageUrl'
    //   437: aastore
    //   438: dup
    //   439: iconst_3
    //   440: aload_1
    //   441: aastore
    //   442: dup
    //   443: iconst_4
    //   444: ldc_w 'openType'
    //   447: aastore
    //   448: dup
    //   449: iconst_5
    //   450: aload_2
    //   451: aastore
    //   452: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   455: return
  }
  
  public final void showBottomBar() {
    if (this.mBottomBar != null) {
      if (this.mBottomBarContainer == null)
        return; 
      ThreadUtil.runOnUIThread(new AppbrandSinglePage$showBottomBar$1());
    } 
  }
  
  public final void showKeyboard(int paramInt) {
    getNativeViewManager().showKeyboard(paramInt);
  }
  
  public final void updateWebTitle(String paramString, boolean paramBoolean) {
    l.b(paramString, "title");
    this.titleBar.updateWebTitle(paramString, paramBoolean);
  }
  
  public static final class Companion {
    private Companion() {}
    
    public final int getFragmentIdentity() {
      return AppbrandSinglePage.fragmentIdentity;
    }
    
    public final void setFragmentIdentity(int param1Int) {
      AppbrandSinglePage.fragmentIdentity = param1Int;
    }
  }
  
  public static final class AppbrandSinglePage$createBottomBarIfNeed$1 implements b.a {
    public final Activity getActivity() {
      AppbrandViewWindowBase appbrandViewWindowBase = AppbrandSinglePage.this.mHost;
      return (appbrandViewWindowBase != null) ? appbrandViewWindowBase.getActivity() : null;
    }
    
    public final JSONObject getData() {
      return AppbrandSinglePage.this.mBottomBarData;
    }
    
    public final String getExtra() {
      AppInfoEntity appInfoEntity = AppbrandSinglePage.this.mApp.getAppInfo();
      if (appInfoEntity != null) {
        String str = appInfoEntity.extra;
        l.a(str, "appInfo.extra");
        return str;
      } 
      return "";
    }
    
    public final String getLaunchFrom() {
      AppInfoEntity appInfoEntity = AppbrandSinglePage.this.mApp.getAppInfo();
      if (appInfoEntity != null) {
        String str = appInfoEntity.launchFrom;
        l.a(str, "appInfo.launchFrom");
        return str;
      } 
      return "";
    }
    
    public final String getMiniAppCover() {
      AppInfoEntity appInfoEntity = AppbrandSinglePage.this.mApp.getAppInfo();
      if (appInfoEntity != null) {
        String str = appInfoEntity.icon;
        l.a(str, "appInfo.icon");
        return str;
      } 
      return "";
    }
    
    public final String getMiniAppId() {
      AppInfoEntity appInfoEntity = AppbrandSinglePage.this.mApp.getAppInfo();
      if (appInfoEntity != null) {
        String str = appInfoEntity.appId;
        l.a(str, "appInfo.appId");
        return str;
      } 
      return "";
    }
    
    public final String getMiniAppName() {
      AppInfoEntity appInfoEntity = AppbrandSinglePage.this.mApp.getAppInfo();
      if (appInfoEntity != null) {
        String str = appInfoEntity.appName;
        l.a(str, "appInfo.appName");
        return str;
      } 
      return "";
    }
    
    public final String getMiniAppTtid() {
      AppInfoEntity appInfoEntity = AppbrandSinglePage.this.mApp.getAppInfo();
      if (appInfoEntity != null) {
        String str = appInfoEntity.ttId;
        l.a(str, "appInfo.ttId");
        return str;
      } 
      return "";
    }
    
    public final String getPageUrl() {
      return AppbrandSinglePage.this.mPageUrl;
    }
  }
  
  static final class AppbrandSinglePage$hideBottomBar$1 implements Runnable {
    public final void run() {
      AppbrandSinglePage.this.mBottomBarContainer.setVisibility(8);
      ViewGroup.LayoutParams layoutParams = AppbrandSinglePage.this.mContentView.getLayoutParams();
      if (layoutParams instanceof ViewGroup.MarginLayoutParams)
        ((ViewGroup.MarginLayoutParams)layoutParams).bottomMargin = 0; 
    }
  }
  
  static final class AppbrandSinglePage$setupRouterParams$1 implements Runnable {
    public final void run() {
      AppbrandSinglePage.this.getTitleBar().initView(AppbrandSinglePage.this.mPagePath);
    }
  }
  
  static final class AppbrandSinglePage$showBottomBar$1 implements Runnable {
    public final void run() {
      if (AppbrandSinglePage.this.mBottomBar != null && AppbrandSinglePage.this.mBottomBarContainer != null && AppbrandSinglePage.this.mContentView != null) {
        AppbrandSinglePage.this.mBottomBarContainer.setVisibility(0);
        ViewGroup.LayoutParams layoutParams = AppbrandSinglePage.this.mContentView.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
          ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
          b b = AppbrandSinglePage.this.mBottomBar;
          if (b == null)
            l.a(); 
          marginLayoutParams.bottomMargin = b.a();
        } 
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\page\AppbrandSinglePage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */