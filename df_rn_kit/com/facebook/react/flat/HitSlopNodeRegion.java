package com.facebook.react.flat;

import android.graphics.Rect;

final class HitSlopNodeRegion extends NodeRegion {
  private final Rect mHitSlop;
  
  HitSlopNodeRegion(Rect paramRect, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, boolean paramBoolean) {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt, paramBoolean);
    this.mHitSlop = paramRect;
  }
  
  final float getTouchableBottom() {
    return getBottom() + this.mHitSlop.bottom;
  }
  
  final float getTouchableLeft() {
    return getLeft() - this.mHitSlop.left;
  }
  
  final float getTouchableRight() {
    return getRight() + this.mHitSlop.right;
  }
  
  final float getTouchableTop() {
    return getTop() - this.mHitSlop.top;
  }
  
  final boolean withinBounds(float paramFloat1, float paramFloat2) {
    return (getTouchableLeft() <= paramFloat1 && paramFloat1 < getTouchableRight() && getTouchableTop() <= paramFloat2 && paramFloat2 < getTouchableBottom());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\HitSlopNodeRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */