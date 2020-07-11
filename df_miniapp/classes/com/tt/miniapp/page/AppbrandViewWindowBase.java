package com.tt.miniapp.page;

import android.content.Context;
import android.view.View;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.base.ui.viewwindow.ViewWindow;
import java.util.HashMap;

public abstract class AppbrandViewWindowBase extends ViewWindow {
  private HashMap _$_findViewCache;
  
  private final AppbrandApplicationImpl mApp;
  
  public AppbrandViewWindowBase(Context paramContext, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramContext);
    this.mApp = paramAppbrandApplicationImpl;
  }
  
  public void _$_clearFindViewByIdCache() {
    HashMap hashMap = this._$_findViewCache;
    if (hashMap != null)
      hashMap.clear(); 
  }
  
  public View _$_findCachedViewById(int paramInt) {
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
  
  public abstract AppbrandSinglePage getCurrentPage();
  
  protected final AppbrandApplicationImpl getMApp() {
    return this.mApp;
  }
  
  public abstract void sendOnAppRoute(String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\page\AppbrandViewWindowBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */