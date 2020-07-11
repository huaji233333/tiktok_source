package com.facebook.react.flat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

abstract class FlatViewManager extends ViewGroupManager<FlatViewGroup> {
  protected FlatViewGroup createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new FlatViewGroup((Context)paramThemedReactContext);
  }
  
  public void removeAllViews(FlatViewGroup paramFlatViewGroup) {
    paramFlatViewGroup.removeAllViewsInLayout();
  }
  
  public void setBackgroundColor(FlatViewGroup paramFlatViewGroup, int paramInt) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */