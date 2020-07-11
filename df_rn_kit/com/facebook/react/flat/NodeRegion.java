package com.facebook.react.flat;

class NodeRegion {
  static final NodeRegion EMPTY;
  
  static final NodeRegion[] EMPTY_ARRAY = new NodeRegion[0];
  
  private final float mBottom;
  
  final boolean mIsVirtual;
  
  private final float mLeft;
  
  private final float mRight;
  
  final int mTag;
  
  private final float mTop;
  
  static {
    EMPTY = new NodeRegion(0.0F, 0.0F, 0.0F, 0.0F, -1, false);
  }
  
  NodeRegion(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, boolean paramBoolean) {
    this.mLeft = paramFloat1;
    this.mTop = paramFloat2;
    this.mRight = paramFloat3;
    this.mBottom = paramFloat4;
    this.mTag = paramInt;
    this.mIsVirtual = paramBoolean;
  }
  
  final float getBottom() {
    return this.mBottom;
  }
  
  final float getLeft() {
    return this.mLeft;
  }
  
  int getReactTag(float paramFloat1, float paramFloat2) {
    return this.mTag;
  }
  
  final float getRight() {
    return this.mRight;
  }
  
  final float getTop() {
    return this.mTop;
  }
  
  float getTouchableBottom() {
    return getBottom();
  }
  
  float getTouchableLeft() {
    return getLeft();
  }
  
  float getTouchableRight() {
    return getRight();
  }
  
  float getTouchableTop() {
    return getTop();
  }
  
  final boolean matches(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
    return (paramFloat1 == this.mLeft && paramFloat2 == this.mTop && paramFloat3 == this.mRight && paramFloat4 == this.mBottom && paramBoolean == this.mIsVirtual);
  }
  
  boolean matchesTag(int paramInt) {
    return (this.mTag == paramInt);
  }
  
  boolean withinBounds(float paramFloat1, float paramFloat2) {
    return (this.mLeft <= paramFloat1 && paramFloat1 < this.mRight && this.mTop <= paramFloat2 && paramFloat2 < this.mBottom);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\NodeRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */