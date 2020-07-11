package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

class PositionAndSizeAnimation extends Animation implements HandleLayout {
  private final int mDeltaHeight;
  
  private final int mDeltaWidth;
  
  private final float mDeltaX;
  
  private final float mDeltaY;
  
  private final int mStartHeight;
  
  private final int mStartWidth;
  
  private final float mStartX;
  
  private final float mStartY;
  
  private final View mView;
  
  public PositionAndSizeAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mView = paramView;
    this.mStartX = paramView.getX() - paramView.getTranslationX();
    this.mStartY = paramView.getY() - paramView.getTranslationY();
    this.mStartWidth = paramView.getWidth();
    this.mStartHeight = paramView.getHeight();
    this.mDeltaX = paramInt1 - this.mStartX;
    this.mDeltaY = paramInt2 - this.mStartY;
    this.mDeltaWidth = paramInt3 - this.mStartWidth;
    this.mDeltaHeight = paramInt4 - this.mStartHeight;
  }
  
  protected void applyTransformation(float paramFloat, Transformation paramTransformation) {
    float f1 = this.mStartX + this.mDeltaX * paramFloat;
    float f2 = this.mStartY + this.mDeltaY * paramFloat;
    float f3 = this.mStartWidth;
    float f4 = this.mDeltaWidth;
    float f5 = this.mStartHeight;
    float f6 = this.mDeltaHeight;
    this.mView.layout(Math.round(f1), Math.round(f2), Math.round(f1 + f3 + f4 * paramFloat), Math.round(f2 + f5 + f6 * paramFloat));
  }
  
  public boolean willChangeBounds() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\PositionAndSizeAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */