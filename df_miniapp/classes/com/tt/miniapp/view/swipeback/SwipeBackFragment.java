package com.tt.miniapp.view.swipeback;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.r;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.tt.miniapp.route.BaseRouteFragment;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;

public class SwipeBackFragment extends BaseRouteFragment {
  boolean mLocking;
  
  private Animation mNoAnim;
  
  private SwipeBackLayout mSwipeBackLayout = new SwipeBackLayout((Context)AppbrandContext.getInst().getApplicationContext());
  
  public SwipeBackFragment() {
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
    this.mSwipeBackLayout.setLayoutParams(layoutParams);
    this.mSwipeBackLayout.setBackgroundColor(0);
  }
  
  private void initFragmentBackground(View paramView) {
    if (paramView instanceof SwipeBackLayout) {
      setBackground(((SwipeBackLayout)paramView).getChildAt(0));
      return;
    } 
    setBackground(paramView);
  }
  
  private void setBackground(View paramView) {
    if (paramView != null && paramView.getBackground() == null) {
      int i = 0;
      if (getActivity() instanceof MiniappHostBase)
        i = ((MiniappHostBase)getActivity()).getDefaultFragmentBackground(); 
      if (i == 0) {
        paramView.setBackgroundResource(getWindowBackground());
        return;
      } 
      paramView.setBackgroundResource(i);
    } 
  }
  
  protected View attachToSwipeBack(View paramView) {
    this.mSwipeBackLayout.attachToFragment(this, paramView);
    return (View)this.mSwipeBackLayout;
  }
  
  protected View attachToSwipeBack(View paramView, SwipeBackLayout.EdgeLevel paramEdgeLevel) {
    this.mSwipeBackLayout.attachToFragment(this, paramView);
    this.mSwipeBackLayout.setEdgeLevel(paramEdgeLevel);
    return (View)this.mSwipeBackLayout;
  }
  
  public SwipeBackLayout getSwipeBackLayout() {
    return this.mSwipeBackLayout;
  }
  
  protected int getWindowBackground() {
    TypedArray typedArray = getActivity().getTheme().obtainStyledAttributes(new int[] { 16842836 });
    int i = typedArray.getResourceId(0, 0);
    typedArray.recycle();
    return i;
  }
  
  public void onActivityCreated(Bundle paramBundle) {
    super.onActivityCreated(paramBundle);
    View view = getView();
    initFragmentBackground(view);
    if (view != null)
      view.setClickable(true); 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    if (paramBundle != null) {
      boolean bool = paramBundle.getBoolean("SWIPEBACKFRAGMENT_STATE_SAVE_IS_HIDDEN");
      r r = getFragmentManager().a();
      if (bool) {
        r.b((Fragment)this);
      } else {
        r.c((Fragment)this);
      } 
      r.b();
    } 
    this.mNoAnim = AnimationUtils.loadAnimation((Context)getActivity(), 2131034231);
  }
  
  public Animation onCreateAnimation(int paramInt1, boolean paramBoolean, int paramInt2) {
    return this.mLocking ? this.mNoAnim : super.onCreateAnimation(paramInt1, paramBoolean, paramInt2);
  }
  
  public void onHiddenChanged(boolean paramBoolean) {
    super.onHiddenChanged(paramBoolean);
    if (paramBoolean) {
      SwipeBackLayout swipeBackLayout = this.mSwipeBackLayout;
      if (swipeBackLayout != null)
        swipeBackLayout.hiddenFragment(); 
    } 
  }
  
  public void onResume() {
    super.onResume();
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("SWIPEBACKFRAGMENT_STATE_SAVE_IS_HIDDEN", isHidden());
  }
  
  protected void setEdgeLevel(int paramInt) {
    this.mSwipeBackLayout.setEdgeLevel(paramInt);
  }
  
  protected void setEdgeLevel(SwipeBackLayout.EdgeLevel paramEdgeLevel) {
    this.mSwipeBackLayout.setEdgeLevel(paramEdgeLevel);
  }
  
  public void setSwipeBackEnable(boolean paramBoolean) {
    this.mSwipeBackLayout.setEnableGesture(paramBoolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\swipeback\SwipeBackFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */