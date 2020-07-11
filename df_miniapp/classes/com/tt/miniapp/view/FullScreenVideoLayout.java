package com.tt.miniapp.view;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

public class FullScreenVideoLayout extends FrameLayout {
  public FullScreenVideoLayout(Context paramContext) {
    super(paramContext);
    initTransition();
  }
  
  public FullScreenVideoLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    initTransition();
  }
  
  public FullScreenVideoLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    initTransition();
  }
  
  public FullScreenVideoLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    initTransition();
  }
  
  private void initTransition() {
    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "alpha", new float[] { 0.0F, 1.0F });
    objectAnimator.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
    LayoutTransition layoutTransition = new LayoutTransition();
    layoutTransition.setAnimator(2, (Animator)objectAnimator);
    layoutTransition.setDuration(150L);
    setLayoutTransition(layoutTransition);
    setFocusableInTouchMode(true);
    setFocusable(true);
  }
  
  public void enterFullScreen(View paramView) {
    addView(paramView);
    setVisibility(0);
    requestFocus();
  }
  
  public void exitFullScreen() {
    setVisibility(8);
    removeAllViews();
  }
  
  public static interface ViewCallback {
    void onFinish();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\FullScreenVideoLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */