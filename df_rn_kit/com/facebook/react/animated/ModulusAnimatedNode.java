package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableMap;

class ModulusAnimatedNode extends ValueAnimatedNode {
  private final int mInputNode;
  
  private final double mModulus;
  
  private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
  
  public ModulusAnimatedNode(ReadableMap paramReadableMap, NativeAnimatedNodesManager paramNativeAnimatedNodesManager) {
    this.mNativeAnimatedNodesManager = paramNativeAnimatedNodesManager;
    this.mInputNode = paramReadableMap.getInt("input");
    this.mModulus = paramReadableMap.getDouble("modulus");
  }
  
  public void update() {
    AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(this.mInputNode);
    if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
      this.mValue = ((ValueAnimatedNode)animatedNode).getValue() % this.mModulus;
      return;
    } 
    throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.modulus node");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\ModulusAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */