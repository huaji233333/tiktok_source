package com.facebook.react.flat;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

final class ShadowStyleSpan extends CharacterStyle {
  static final ShadowStyleSpan INSTANCE = new ShadowStyleSpan(0.0F, 0.0F, 0.0F, 0, true);
  
  private int mColor;
  
  private float mDx;
  
  private float mDy;
  
  private boolean mFrozen;
  
  private float mRadius;
  
  private ShadowStyleSpan(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, boolean paramBoolean) {
    this.mDx = paramFloat1;
    this.mDy = paramFloat2;
    this.mRadius = paramFloat3;
    this.mColor = paramInt;
    this.mFrozen = paramBoolean;
  }
  
  final void freeze() {
    this.mFrozen = true;
  }
  
  public final int getColor() {
    return this.mColor;
  }
  
  public final float getRadius() {
    return this.mRadius;
  }
  
  final boolean isFrozen() {
    return this.mFrozen;
  }
  
  final ShadowStyleSpan mutableCopy() {
    return new ShadowStyleSpan(this.mDx, this.mDy, this.mRadius, this.mColor, false);
  }
  
  public final boolean offsetMatches(float paramFloat1, float paramFloat2) {
    return (this.mDx == paramFloat1 && this.mDy == paramFloat2);
  }
  
  public final void setColor(int paramInt) {
    this.mColor = paramInt;
  }
  
  public final void setOffset(float paramFloat1, float paramFloat2) {
    this.mDx = paramFloat1;
    this.mDy = paramFloat2;
  }
  
  public final void setRadius(float paramFloat) {
    this.mRadius = paramFloat;
  }
  
  public final void updateDrawState(TextPaint paramTextPaint) {
    paramTextPaint.setShadowLayer(this.mRadius, this.mDx, this.mDy, this.mColor);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\ShadowStyleSpan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */