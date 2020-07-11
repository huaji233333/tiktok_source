package com.tt.miniapp.page;

import android.content.Context;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.preload.PreloadManager;
import d.f.b.l;
import java.util.HashMap;

public final class AppbrandSinglePageViewWindow extends AppbrandViewWindowBase {
  private HashMap _$_findViewCache;
  
  private final AppbrandSinglePage mPage;
  
  public AppbrandSinglePageViewWindow(Context paramContext, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramContext, paramAppbrandApplicationImpl);
    AppbrandSinglePage appbrandSinglePage = ((PreloadManager)paramAppbrandApplicationImpl.getService(PreloadManager.class)).takePage(this);
    l.a(appbrandSinglePage, "app.getService(PreloadMa…lass.java).takePage(this)");
    this.mPage = appbrandSinglePage;
    addView((View)this.mPage);
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
    return this.mPage;
  }
  
  public final void onActivityDestroy() {
    this.mPage.onActivityDestroy();
  }
  
  public final void onActivityPause() {
    this.mPage.onActivityPause();
  }
  
  public final void onActivityResume() {
    this.mPage.onActivityResume();
  }
  
  public final boolean onBackPressed() {
    Boolean bool = this.mPage.onBackPressed();
    return (bool != null) ? bool.booleanValue() : false;
  }
  
  public final void onDestroy() {
    this.mPage.onDestroy();
  }
  
  public final void onThemeChanged(String paramString) {
    super.onThemeChanged(paramString);
    this.mPage.onThemeChanged(paramString);
  }
  
  public final void onViewPause(int paramInt) {
    this.mPage.onViewPause(paramInt);
  }
  
  public final void onViewResume(int paramInt) {
    this.mPage.onViewResume(paramInt);
  }
  
  public final void sendOnAppRoute(String paramString) {
    l.b(paramString, "openType");
    this.mPage.sendOnAppRoute(paramString);
  }
  
  public final void setupRouterParams(String paramString1, String paramString2) {
    l.b(paramString1, "url");
    l.b(paramString2, "openType");
    this.mPage.setupRouterParams(paramString1, paramString2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\page\AppbrandSinglePageViewWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */