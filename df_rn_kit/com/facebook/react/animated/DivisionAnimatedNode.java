package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class DivisionAnimatedNode extends ValueAnimatedNode {
  private final int[] mInputNodes;
  
  private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
  
  public DivisionAnimatedNode(ReadableMap paramReadableMap, NativeAnimatedNodesManager paramNativeAnimatedNodesManager) {
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
    int i = 0;
    while (true) {
      int[] arrayOfInt = this.mInputNodes;
      if (i < arrayOfInt.length) {
        AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(arrayOfInt[i]);
        if (animatedNode != null && animatedNode instanceof ValueAnimatedNode) {
          double d = ((ValueAnimatedNode)animatedNode).getValue();
          if (i == 0) {
            this.mValue = d;
          } else if (d != 0.0D) {
            this.mValue /= d;
          } else {
            throw new JSApplicationCausedNativeException("Detected a division by zero in Animated.divide node");
          } 
          i++;
          continue;
        } 
        throw new JSApplicationCausedNativeException("Illegal node ID set as an input for Animated.divide node");
      } 
      break;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\DivisionAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */