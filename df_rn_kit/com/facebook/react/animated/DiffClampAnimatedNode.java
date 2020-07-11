package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableMap;

class DiffClampAnimatedNode extends ValueAnimatedNode {
  private final int mInputNodeTag;
  
  private double mLastValue;
  
  private final double mMax;
  
  private final double mMin;
  
  private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
  
  public DiffClampAnimatedNode(ReadableMap paramReadableMap, NativeAnimatedNodesManager paramNativeAnimatedNodesManager) {
    this.mNativeAnimatedNodesManager = paramNativeAnimatedNodesManager;
    this.mInputNodeTag = paramReadableMap.getInt("input");
    this.mMin = paramReadableMap.getDouble("min");
    this.mMax = paramReadableMap.getDouble("max");
    this.mLastValue = 0.0D;
    this.mValue = 0.0D;
  }
  
  private double getInputNodeValue() {
    AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(this.mInputNodeTag);
    if (animatedNode != null && animatedNode instanceof ValueAnimatedNode)
      return ((ValueAnimatedNode)animatedNode).getValue(); 
    throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.DiffClamp node");
  }
  
  public void update() {
    double d1 = getInputNodeValue();
    double d2 = this.mLastValue;
    this.mLastValue = d1;
    this.mValue = Math.min(Math.max(this.mValue + d1 - d2, this.mMin), this.mMax);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\DiffClampAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */