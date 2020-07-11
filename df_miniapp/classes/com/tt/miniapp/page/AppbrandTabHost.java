package com.tt.miniapp.page;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.preload.PreloadManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;
import d.a.m;
import d.f.b.g;
import d.f.b.l;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class AppbrandTabHost extends LinearLayout {
  public static final Companion Companion = new Companion(null);
  
  private final int TAB_HEIGHT;
  
  private HashMap _$_findViewCache;
  
  private final AppbrandApplicationImpl mApp;
  
  private TabHostCallback mCallback;
  
  private Paint mDividerPaint;
  
  private AppbrandTabController mSelectedTab;
  
  private AppConfig.TabBar mTabConfig;
  
  private final ArrayList<AppbrandTabController> mTabControllers;
  
  private AppbrandTabInfo mTabInfo;
  
  public AppbrandTabHost(Context paramContext, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramContext);
    this.mApp = paramAppbrandApplicationImpl;
    this.TAB_HEIGHT = (int)UIUtils.dip2Px(paramContext, 50.0F);
    this.mTabControllers = new ArrayList<AppbrandTabController>();
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, this.TAB_HEIGHT);
    setOrientation(0);
    setLayoutParams(layoutParams);
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
  
  protected final void dispatchDraw(Canvas paramCanvas) {
    super.dispatchDraw(paramCanvas);
    Paint paint = this.mDividerPaint;
    if (paint != null && paramCanvas != null)
      paramCanvas.drawLine(0.0F, 0.0F, getWidth(), 0.0F, paint); 
  }
  
  public final String setTabBarBadge(boolean paramBoolean, int paramInt, String paramString) {
    l.b(paramString, "text");
    if (paramInt < 0 || paramInt >= this.mTabControllers.size())
      return "tabbar item not found"; 
    ((AppbrandTabController)this.mTabControllers.get(paramInt)).setBadge(paramBoolean, paramString);
    return "";
  }
  
  public final String setTabBarItem(int paramInt, String paramString1, String paramString2, String paramString3) {
    if (paramInt < 0 || paramInt >= this.mTabControllers.size())
      return "tabbar item not found"; 
    ((AppbrandTabController)this.mTabControllers.get(paramInt)).setItem(paramString1, paramString2, paramString3);
    return "";
  }
  
  public final String setTabBarRedDotVisibility(int paramInt, boolean paramBoolean) {
    if (paramInt < 0 || paramInt >= this.mTabControllers.size())
      return "tabbar item not found"; 
    ((AppbrandTabController)this.mTabControllers.get(paramInt)).setRedDotVisibility(paramBoolean);
    return "";
  }
  
  public final String setTabBarStyle(String paramString1, String paramString2, String paramString3, String paramString4) {
    Paint paint;
    if (l.a(paramString4, "white")) {
      paint = this.mDividerPaint;
      if (paint != null) {
        Context context = getContext();
        l.a(context, "context");
        paint.setColor(context.getResources().getColor(2097348666));
      } 
      invalidate();
    } else if (l.a(paint, "black")) {
      paint = this.mDividerPaint;
      if (paint != null) {
        Context context = getContext();
        l.a(context, "context");
        paint.setColor(context.getResources().getColor(2097348665));
      } 
      invalidate();
    } 
    if (!TextUtils.isEmpty(paramString3))
      try {
        setBackgroundColor(Color.parseColor(UIUtils.rgbaToFullARGBStr(paramString3, "#ffffff")));
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder("illegal color ");
        stringBuilder.append(paramString3);
        AppBrandLogger.eWithThrowable("AppbrandTabHost", stringBuilder.toString(), exception);
      }  
    if (!TextUtils.isEmpty(paramString1))
      try {
        int i = Color.parseColor(UIUtils.rgbaToFullARGBStr(paramString1, "#222222"));
        Iterator<AppbrandTabController> iterator = this.mTabControllers.iterator();
        while (iterator.hasNext())
          ((AppbrandTabController)iterator.next()).setColor(i); 
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder("illegal color ");
        stringBuilder.append(paramString1);
        AppBrandLogger.eWithThrowable("AppbrandTabHost", stringBuilder.toString(), exception);
      }  
    if (!TextUtils.isEmpty(paramString2))
      try {
        int i = Color.parseColor(UIUtils.rgbaToFullARGBStr(paramString2, "#F85959"));
        Iterator<AppbrandTabController> iterator = this.mTabControllers.iterator();
        while (iterator.hasNext())
          ((AppbrandTabController)iterator.next()).setSelectedColor(i); 
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder("illegal color ");
        stringBuilder.append(paramString2);
        AppBrandLogger.eWithThrowable("AppbrandTabHost", stringBuilder.toString(), exception);
      }  
    return "";
  }
  
  public final void setupTabs(AppConfig.TabBar paramTabBar, TabHostCallback paramTabHostCallback) {
    l.b(paramTabBar, "tabConfig");
    l.b(paramTabHostCallback, "callback");
    this.mTabConfig = paramTabBar;
    this.mCallback = paramTabHostCallback;
    AppbrandTabInfo appbrandTabInfo2 = new AppbrandTabInfo(this.mTabConfig);
    this.mTabInfo = appbrandTabInfo2;
    ArrayList arrayList = paramTabBar.tabs;
    if (arrayList == null)
      l.a(); 
    arrayList = arrayList;
    int i = 0;
    for (AppConfig.TabBar.TabContent tabContent : arrayList) {
      if (i)
        m.b(); 
      tabContent = tabContent;
      View view = ((PreloadManager)this.mApp.getService(PreloadManager.class)).getPreloadedView(4);
      l.a(view, "view");
      l.a(tabContent, "tabContent");
      AppbrandTabController appbrandTabController = new AppbrandTabController(view, tabContent, appbrandTabInfo2, i);
      appbrandTabController.init();
      this.mTabControllers.add(appbrandTabController);
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
      layoutParams.weight = 1.0F;
      addView(view, (ViewGroup.LayoutParams)layoutParams);
      view.setOnClickListener(new AppbrandTabHost$setupTabs$$inlined$forEachIndexed$lambda$1(this, appbrandTabInfo2));
      i++;
    } 
    setBackgroundColor(appbrandTabInfo2.getBackgroundColor());
    Paint paint = new Paint();
    AppbrandTabInfo appbrandTabInfo1 = this.mTabInfo;
    if (appbrandTabInfo1 != null) {
      String str = appbrandTabInfo1.getBorderStyle();
    } else {
      appbrandTabInfo1 = null;
    } 
    if (l.a(appbrandTabInfo1, "white")) {
      Context context = getContext();
      l.a(context, "context");
      i = context.getResources().getColor(2097348666);
    } else {
      Context context = getContext();
      l.a(context, "context");
      i = context.getResources().getColor(2097348665);
    } 
    paint.setColor(i);
    this.mDividerPaint = paint;
  }
  
  public final void switchTab(String paramString1, String paramString2) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'pageUrl'
    //   4: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_2
    //   8: ldc_w 'openType'
    //   11: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   14: aload_0
    //   15: getfield mTabConfig : Lcom/tt/miniapp/AppConfig$TabBar;
    //   18: astore_3
    //   19: aload_3
    //   20: ifnull -> 175
    //   23: aload_3
    //   24: ifnonnull -> 30
    //   27: invokestatic a : ()V
    //   30: aload_3
    //   31: getfield tabs : Ljava/util/ArrayList;
    //   34: ifnull -> 175
    //   37: aload_0
    //   38: getfield mSelectedTab : Lcom/tt/miniapp/page/AppbrandTabController;
    //   41: astore_3
    //   42: aload_3
    //   43: ifnull -> 63
    //   46: aload_3
    //   47: invokevirtual getMTabContent : ()Lcom/tt/miniapp/AppConfig$TabBar$TabContent;
    //   50: astore_3
    //   51: aload_3
    //   52: ifnull -> 63
    //   55: aload_3
    //   56: getfield pagePath : Ljava/lang/String;
    //   59: astore_3
    //   60: goto -> 65
    //   63: aconst_null
    //   64: astore_3
    //   65: aload_3
    //   66: aload_1
    //   67: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   70: ifeq -> 74
    //   73: return
    //   74: aload_1
    //   75: invokestatic getPagePath : (Ljava/lang/String;)Ljava/lang/String;
    //   78: astore_3
    //   79: aload_0
    //   80: getfield mTabControllers : Ljava/util/ArrayList;
    //   83: invokevirtual iterator : ()Ljava/util/Iterator;
    //   86: astore #4
    //   88: aload #4
    //   90: invokeinterface hasNext : ()Z
    //   95: ifeq -> 147
    //   98: aload #4
    //   100: invokeinterface next : ()Ljava/lang/Object;
    //   105: checkcast com/tt/miniapp/page/AppbrandTabController
    //   108: astore #5
    //   110: aload #5
    //   112: invokevirtual getMTabContent : ()Lcom/tt/miniapp/AppConfig$TabBar$TabContent;
    //   115: getfield pagePath : Ljava/lang/String;
    //   118: aload_3
    //   119: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   122: ifeq -> 139
    //   125: aload_0
    //   126: aload #5
    //   128: putfield mSelectedTab : Lcom/tt/miniapp/page/AppbrandTabController;
    //   131: aload #5
    //   133: invokevirtual setSelected : ()V
    //   136: goto -> 88
    //   139: aload #5
    //   141: invokevirtual setUnSelected : ()V
    //   144: goto -> 88
    //   147: aload_0
    //   148: getfield mCallback : Lcom/tt/miniapp/page/AppbrandTabHost$TabHostCallback;
    //   151: astore #4
    //   153: aload #4
    //   155: ifnull -> 175
    //   158: aload_3
    //   159: ldc_w 'tabPagePath'
    //   162: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   165: aload #4
    //   167: aload_3
    //   168: aload_1
    //   169: aload_2
    //   170: invokeinterface onTabChanged : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   175: return
  }
  
  public static final class AppbrandTabInfo {
    private final int backgroundColor;
    
    private final String borderStyle;
    
    private final int color;
    
    private final int selectedColor;
    
    public AppbrandTabInfo(AppConfig.TabBar param1TabBar) {
      // Byte code:
      //   0: aload_0
      //   1: invokespecial <init> : ()V
      //   4: aconst_null
      //   5: astore #4
      //   7: aload_1
      //   8: ifnull -> 19
      //   11: aload_1
      //   12: getfield color : Ljava/lang/String;
      //   15: astore_3
      //   16: goto -> 21
      //   19: aconst_null
      //   20: astore_3
      //   21: aload_0
      //   22: aload_3
      //   23: ldc '#222222'
      //   25: invokestatic parseColor : (Ljava/lang/String;Ljava/lang/String;)I
      //   28: putfield color : I
      //   31: aload_1
      //   32: ifnull -> 43
      //   35: aload_1
      //   36: getfield selectedColor : Ljava/lang/String;
      //   39: astore_3
      //   40: goto -> 45
      //   43: aconst_null
      //   44: astore_3
      //   45: aload_0
      //   46: aload_3
      //   47: ldc '#F85959'
      //   49: invokestatic parseColor : (Ljava/lang/String;Ljava/lang/String;)I
      //   52: putfield selectedColor : I
      //   55: aload_1
      //   56: ifnull -> 67
      //   59: aload_1
      //   60: getfield backgroundColor : Ljava/lang/String;
      //   63: astore_3
      //   64: goto -> 69
      //   67: aconst_null
      //   68: astore_3
      //   69: aload_0
      //   70: aload_3
      //   71: ldc '#ffffff'
      //   73: invokestatic parseColor : (Ljava/lang/String;Ljava/lang/String;)I
      //   76: putfield backgroundColor : I
      //   79: aload_1
      //   80: ifnull -> 91
      //   83: aload_1
      //   84: getfield borderStyle : Ljava/lang/String;
      //   87: astore_3
      //   88: goto -> 93
      //   91: aconst_null
      //   92: astore_3
      //   93: ldc 'white'
      //   95: aload_3
      //   96: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   99: istore_2
      //   100: ldc 'black'
      //   102: astore #5
      //   104: iload_2
      //   105: ifne -> 133
      //   108: aload #4
      //   110: astore_3
      //   111: aload_1
      //   112: ifnull -> 120
      //   115: aload_1
      //   116: getfield borderStyle : Ljava/lang/String;
      //   119: astore_3
      //   120: aload #5
      //   122: astore #4
      //   124: ldc 'black'
      //   126: aload_3
      //   127: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
      //   130: ifeq -> 146
      //   133: aload_1
      //   134: getfield borderStyle : Ljava/lang/String;
      //   137: astore #4
      //   139: aload #4
      //   141: ldc 'tabConfig.borderStyle'
      //   143: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
      //   146: aload_0
      //   147: aload #4
      //   149: putfield borderStyle : Ljava/lang/String;
      //   152: return
    }
    
    public final int getBackgroundColor() {
      return this.backgroundColor;
    }
    
    public final String getBorderStyle() {
      return this.borderStyle;
    }
    
    public final int getColor() {
      return this.color;
    }
    
    public final int getSelectedColor() {
      return this.selectedColor;
    }
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public static interface TabHostCallback {
    void onTabChanged(String param1String1, String param1String2, String param1String3);
  }
  
  static final class AppbrandTabHost$setupTabs$$inlined$forEachIndexed$lambda$1 implements View.OnClickListener {
    AppbrandTabHost$setupTabs$$inlined$forEachIndexed$lambda$1(AppbrandTabHost param1AppbrandTabHost, AppbrandTabHost.AppbrandTabInfo param1AppbrandTabInfo) {}
    
    public final void onClick(View param1View) {
      AppbrandTabHost appbrandTabHost = AppbrandTabHost.this;
      String str = this.$tabContent.pagePath;
      l.a(str, "tabContent.pagePath");
      appbrandTabHost.switchTab(str, "switchTab");
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\page\AppbrandTabHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */