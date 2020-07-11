package com.facebook.react.animated;

import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import java.util.HashMap;
import java.util.Map;

class StyleAnimatedNode extends AnimatedNode {
  private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
  
  private final Map<String, Integer> mPropMapping;
  
  StyleAnimatedNode(ReadableMap paramReadableMap, NativeAnimatedNodesManager paramNativeAnimatedNodesManager) {
    paramReadableMap = paramReadableMap.getMap("style");
    ReadableMapKeySetIterator readableMapKeySetIterator = paramReadableMap.keySetIterator();
    this.mPropMapping = new HashMap<String, Integer>();
    while (readableMapKeySetIterator.hasNextKey()) {
      String str = readableMapKeySetIterator.nextKey();
      int i = paramReadableMap.getInt(str);
      this.mPropMapping.put(str, Integer.valueOf(i));
    } 
    this.mNativeAnimatedNodesManager = paramNativeAnimatedNodesManager;
  }
  
  public void collectViewUpdates(JavaOnlyMap paramJavaOnlyMap) {
    for (Map.Entry<String, Integer> entry : this.mPropMapping.entrySet()) {
      AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(((Integer)entry.getValue()).intValue());
      if (animatedNode != null) {
        if (animatedNode instanceof TransformAnimatedNode) {
          ((TransformAnimatedNode)animatedNode).collectViewUpdates(paramJavaOnlyMap);
          continue;
        } 
        if (animatedNode instanceof ValueAnimatedNode) {
          paramJavaOnlyMap.putDouble((String)entry.getKey(), ((ValueAnimatedNode)animatedNode).getValue());
          continue;
        } 
        StringBuilder stringBuilder = new StringBuilder("Unsupported type of node used in property node ");
        stringBuilder.append(animatedNode.getClass());
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new IllegalArgumentException("Mapped style node does not exists");
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\StyleAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */