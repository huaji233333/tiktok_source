package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableMap;

class ValueAnimatedNode extends AnimatedNode {
  double mOffset;
  
  double mValue = Double.NaN;
  
  private AnimatedNodeValueListener mValueListener;
  
  public ValueAnimatedNode() {}
  
  public ValueAnimatedNode(ReadableMap paramReadableMap) {
    this.mValue = paramReadableMap.getDouble("value");
    this.mOffset = paramReadableMap.getDouble("offset");
  }
  
  public void extractOffset() {
    this.mOffset += this.mValue;
    this.mValue = 0.0D;
  }
  
  public void flattenOffset() {
    this.mValue += this.mOffset;
    this.mOffset = 0.0D;
  }
  
  public double getValue() {
    return this.mOffset + this.mValue;
  }
  
  public void onValueUpdate() {
    AnimatedNodeValueListener animatedNodeValueListener = this.mValueListener;
    if (animatedNodeValueListener == null)
      return; 
    animatedNodeValueListener.onValueUpdate(getValue());
  }
  
  public void setValueListener(AnimatedNodeValueListener paramAnimatedNodeValueListener) {
    this.mValueListener = paramAnimatedNodeValueListener;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\ValueAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */