package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

class OpacityAnimation extends Animation {
  private final float mDeltaOpacity;
  
  private final float mStartOpacity;
  
  private final View mView;
  
  public OpacityAnimation(View paramView, float paramFloat1, float paramFloat2) {
    this.mView = paramView;
    this.mStartOpacity = paramFloat1;
    this.mDeltaOpacity = paramFloat2 - paramFloat1;
    setAnimationListener(new OpacityAnimationListener(paramView));
  }
  
  protected void applyTransformation(float paramFloat, Transformation paramTransformation) {
    this.mView.setAlpha(this.mStartOpacity + this.mDeltaOpacity * paramFloat);
  }
  
  public boolean willChangeBounds() {
    return false;
  }
  
  static class OpacityAnimationListener implements Animation.AnimationListener {
    private boolean mLayerTypeChanged;
    
    private final View mView;
    
    public OpacityAnimationListener(View param1View) {
      this.mView = param1View;
    }
    
    public void onAnimationEnd(Animation param1Animation) {
      if (this.mLayerTypeChanged)
        this.mView.setLayerType(0, null); 
    }
    
    public void onAnimationRepeat(Animation param1Animation) {}
    
    public void onAnimationStart(Animation param1Animation) {
      if (this.mView.hasOverlappingRendering() && this.mView.getLayerType() == 0) {
        this.mLayerTypeChanged = true;
        this.mView.setLayerType(2, null);
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\OpacityAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */