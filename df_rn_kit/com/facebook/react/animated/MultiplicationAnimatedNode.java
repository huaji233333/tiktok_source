package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class MultiplicationAnimatedNode extends ValueAnimatedNode {
  private final int[] mInputNodes;
  
  private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
  
  public MultiplicationAnimatedNode(ReadableMap paramReadableMap, NativeAnimatedNodesManager paramNativeAnimatedNodesManager) {
    this.mNativeAnimatedNodesManager = paramNativeAnimatedNodesManager;
    ReadableArray readableArray = paramReadableMap.getArray("input");
    this.mInputNodes = new int[readableArray.size()];
    int i = 0;
    while (true) {
      int[] arrayOfInt = this.mInputNodes;
      if (i < arrayOfInt.length) {
        arrayOfInt[i] = readableArray.getInt(i);
        i++;
        continue;
      } 
      break;
    } 
  }
  
  public void update() {
    this.mValue = 1.0D;
    int i = 0;
    while (true) {
      int[] arrayOfInt = this.mInputNodes;
      if (i < arrayOfInt.length) {
        AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(arrayOfInt[i]);
        if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
          this.mValue *= ((ValueAnimatedNode)animatedNode).getValue();
          i++;
          continue;
        } 
        throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.multiply node");
      } 
      break;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\MultiplicationAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */