package com.tt.miniapp.base.ui.viewwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import d.f.a.b;
import d.f.b.l;
import d.x;
import java.util.HashMap;

public final class ViewWindowContainer extends FrameLayout {
  private HashMap _$_findViewCache;
  
  private b<? super Context, x> onAttachedToWindowListener;
  
  public ViewWindowContainer(Context paramContext) {
    super(paramContext);
  }
  
  public ViewWindowContainer(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
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
  
  public final b<Context, x> getOnAttachedToWindowListener() {
    return (b)this.onAttachedToWindowListener;
  }
  
  protected final void onAttachedToWindow() {
    super.onAttachedToWindow();
    b<? super Context, x> b1 = this.onAttachedToWindowListener;
    if (b1 != null) {
      Context context = getContext();
      l.a(context, "context");
      b1.invoke(context);
    } 
  }
  
  public final void setOnAttachedToWindowListener(b<? super Context, x> paramb) {
    this.onAttachedToWindowListener = paramb;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\bas\\ui\viewwindow\ViewWindowContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */