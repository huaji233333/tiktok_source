package com.tt.miniapp.view.webcore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class SwipeRefreshTargetDelegate extends RelativeLayout {
  public SwipeRefreshTargetDelegate(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public SwipeRefreshTargetDelegate(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SwipeRefreshTargetDelegate(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void addView(View paramView) {
    if (getChildCount() <= 0) {
      super.addView(paramView);
      return;
    } 
    throw new IllegalStateException("SwipeRefreshTargetDelegate can host only one direct child");
  }
  
  public void addView(View paramView, int paramInt) {
    if (getChildCount() <= 0) {
      super.addView(paramView, paramInt);
      return;
    } 
    throw new IllegalStateException("SwipeRefreshTargetDelegate can host only one direct child");
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    if (getChildCount() <= 0) {
      super.addView(paramView, paramInt, paramLayoutParams);
      return;
    } 
    throw new IllegalStateException("SwipeRefreshTargetDelegate can host only one direct child");
  }
  
  public void addView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    if (getChildCount() <= 0) {
      super.addView(paramView, paramLayoutParams);
      return;
    } 
    throw new IllegalStateException("SwipeRefreshTargetDelegate can host only one direct child");
  }
  
  public boolean canScrollHorizontally(int paramInt) {
    return (getChildCount() > 0) ? getChildAt(0).canScrollHorizontally(paramInt) : super.canScrollHorizontally(paramInt);
  }
  
  public boolean canScrollVertically(int paramInt) {
    return (getChildCount() > 0) ? getChildAt(0).canScrollVertically(paramInt) : super.canScrollVertically(paramInt);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\SwipeRefreshTargetDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */