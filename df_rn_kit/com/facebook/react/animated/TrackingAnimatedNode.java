package com.facebook.react.animated;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;

class TrackingAnimatedNode extends AnimatedNode {
  private final JavaOnlyMap mAnimationConfig;
  
  private final int mAnimationId;
  
  private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
  
  private final int mToValueNode;
  
  private final int mValueNode;
  
  TrackingAnimatedNode(ReadableMap paramReadableMap, NativeAnimatedNodesManager paramNativeAnimatedNodesManager) {
    this.mNativeAnimatedNodesManager = paramNativeAnimatedNodesManager;
    this.mAnimationId = paramReadableMap.getInt("animationId");
    this.mToValueNode = paramReadableMap.getInt("toValue");
    this.mValueNode = paramReadableMap.getInt("value");
    this.mAnimationConfig = JavaOnlyMap.deepClone(paramReadableMap.getMap("animationConfig"));
  }
  
  public void update() {
    AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(this.mToValueNode);
    this.mAnimationConfig.putDouble("toValue", ((ValueAnimatedNode)animatedNode).getValue());
    this.mNativeAnimatedNodesManager.startAnimatingNode(this.mAnimationId, this.mValueNode, (ReadableMap)this.mAnimationConfig, null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\TrackingAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */