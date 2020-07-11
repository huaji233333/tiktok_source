package com.facebook.react.uimanager;

import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import java.util.WeakHashMap;

public abstract class ViewGroupManager<T extends ViewGroup> extends BaseViewManager<T, LayoutShadowNode> {
  private static WeakHashMap<View, Integer> mZIndexHash = new WeakHashMap<View, Integer>();
  
  public static Integer getViewZIndex(View paramView) {
    return mZIndexHash.get(paramView);
  }
  
  public static void setViewZIndex(View paramView, int paramInt) {
    mZIndexHash.put(paramView, Integer.valueOf(paramInt));
  }
  
  public void addView(T paramT, View paramView, int paramInt) {
    if (paramT.getChildCount() < paramInt) {
      paramT.addView(paramView);
      return;
    } 
    paramT.addView(paramView, paramInt);
  }
  
  public void addViews(T paramT, List<View> paramList) {
    int j = paramList.size();
    for (int i = 0; i < j; i++)
      addView(paramT, paramList.get(i), i); 
  }
  
  public LayoutShadowNode createShadowNodeInstance() {
    return new LayoutShadowNode();
  }
  
  public View getChildAt(T paramT, int paramInt) {
    return paramT.getChildAt(paramInt);
  }
  
  public int getChildCount(T paramT) {
    return paramT.getChildCount();
  }
  
  public Class<? extends LayoutShadowNode> getShadowNodeClass() {
    return LayoutShadowNode.class;
  }
  
  public View getUnmarkedChildAt(T paramT, int paramInt) {
    return null;
  }
  
  public int getUnmarkedChildCount(T paramT) {
    return 0;
  }
  
  public void markView(T paramT, int paramInt) {}
  
  public boolean needsCustomLayoutForChildren() {
    return false;
  }
  
  public void removeAllViews(T paramT) {
    if (paramT instanceof com.facebook.react.views.view.ReactViewGroup) {
      for (int j = getUnmarkedChildCount(paramT) - 1; j >= 0; j--)
        removeViewAt(paramT, j); 
      return;
    } 
    for (int i = getChildCount(paramT) - 1; i >= 0; i--)
      removeViewAt(paramT, i); 
  }
  
  public void removeView(T paramT, View paramView) {
    boolean bool = paramT instanceof com.facebook.react.views.view.ReactViewGroup;
    int i = 0;
    byte b = 0;
    if (bool) {
      for (i = b; i < getUnmarkedChildCount(paramT); i++) {
        if (getUnmarkedChildAt(paramT, i) == paramView) {
          removeViewAt(paramT, i);
          return;
        } 
      } 
      return;
    } 
    while (i < getChildCount(paramT)) {
      if (getChildAt(paramT, i) == paramView) {
        removeViewAt(paramT, i);
        return;
      } 
      i++;
    } 
  }
  
  public void removeViewAt(T paramT, int paramInt) {
    paramT.removeViewAt(paramInt);
  }
  
  public boolean shouldPromoteGrandchildren() {
    return false;
  }
  
  public void updateExtraData(T paramT, Object paramObject) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ViewGroupManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */