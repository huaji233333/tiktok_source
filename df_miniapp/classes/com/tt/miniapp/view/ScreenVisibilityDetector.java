package com.tt.miniapp.view;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;

public class ScreenVisibilityDetector {
  public static final Rect RECT = new Rect();
  
  private OnScreenVisibilityChangedListener mListener;
  
  private HashMap<View, Boolean> mMap = new HashMap<View, Boolean>();
  
  private ViewGroup mViewContainer;
  
  public ScreenVisibilityDetector(ViewGroup paramViewGroup) {
    this.mViewContainer = paramViewGroup;
  }
  
  public static boolean isShowInScreen(View paramView) {
    return (paramView == null) ? false : ((paramView.getVisibility() != 0) ? false : paramView.getLocalVisibleRect(RECT));
  }
  
  public void detect() {
    ViewGroup viewGroup = this.mViewContainer;
    if (viewGroup == null)
      return; 
    int j = viewGroup.getChildCount();
    for (int i = 0; i < j; i++)
      detect(this.mViewContainer.getChildAt(i)); 
  }
  
  public void detect(View paramView) {
    if (paramView != null) {
      Boolean bool = this.mMap.get(paramView);
      if (bool != null) {
        boolean bool1 = isShowInScreen(paramView);
        if (bool1 != bool.booleanValue()) {
          this.mMap.put(paramView, Boolean.valueOf(bool1));
          OnScreenVisibilityChangedListener onScreenVisibilityChangedListener = this.mListener;
          if (onScreenVisibilityChangedListener != null)
            onScreenVisibilityChangedListener.onScreenVisibilityChanged(paramView, bool1); 
        } 
      } 
    } 
  }
  
  public void onViewAdded(final View view) {
    if (view == null)
      return; 
    boolean bool = isShowInScreen(view);
    this.mMap.put(view, Boolean.valueOf(bool));
    view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
          public void onLayoutChange(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8) {
            ScreenVisibilityDetector.this.detect(view);
          }
        });
    OnScreenVisibilityChangedListener onScreenVisibilityChangedListener = this.mListener;
    if (onScreenVisibilityChangedListener != null)
      onScreenVisibilityChangedListener.onScreenVisibilityChanged(view, bool); 
  }
  
  public void onViewRemoved(View paramView) {
    if (paramView == null)
      return; 
    Boolean bool = this.mMap.remove(paramView);
    if (bool != null && bool.booleanValue()) {
      OnScreenVisibilityChangedListener onScreenVisibilityChangedListener = this.mListener;
      if (onScreenVisibilityChangedListener != null)
        onScreenVisibilityChangedListener.onScreenVisibilityChanged(paramView, false); 
    } 
  }
  
  public void setOnVisibilityChangedListener(OnScreenVisibilityChangedListener paramOnScreenVisibilityChangedListener) {
    this.mListener = paramOnScreenVisibilityChangedListener;
  }
  
  public static interface OnScreenVisibilityChangedListener {
    void onScreenVisibilityChanged(View param1View, boolean param1Boolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\ScreenVisibilityDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */