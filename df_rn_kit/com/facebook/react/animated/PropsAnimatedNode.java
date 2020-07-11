package com.facebook.react.animated;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIImplementation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class PropsAnimatedNode extends AnimatedNode {
  private int mConnectedViewTag = -1;
  
  private final ReactStylesDiffMap mDiffMap;
  
  private final NativeAnimatedNodesManager mNativeAnimatedNodesManager;
  
  private final JavaOnlyMap mPropMap;
  
  private final Map<String, Integer> mPropNodeMapping;
  
  private final UIImplementation mUIImplementation;
  
  PropsAnimatedNode(ReadableMap paramReadableMap, NativeAnimatedNodesManager paramNativeAnimatedNodesManager, UIImplementation paramUIImplementation) {
    paramReadableMap = paramReadableMap.getMap("props");
    ReadableMapKeySetIterator readableMapKeySetIterator = paramReadableMap.keySetIterator();
    this.mPropNodeMapping = new HashMap<String, Integer>();
    while (readableMapKeySetIterator.hasNextKey()) {
      String str = readableMapKeySetIterator.nextKey();
      int i = paramReadableMap.getInt(str);
      this.mPropNodeMapping.put(str, Integer.valueOf(i));
    } 
    this.mPropMap = new JavaOnlyMap();
    this.mDiffMap = new ReactStylesDiffMap((ReadableMap)this.mPropMap);
    this.mNativeAnimatedNodesManager = paramNativeAnimatedNodesManager;
    this.mUIImplementation = paramUIImplementation;
  }
  
  public void connectToView(int paramInt) {
    if (this.mConnectedViewTag == -1) {
      this.mConnectedViewTag = paramInt;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Animated node ");
    stringBuilder.append(this.mTag);
    stringBuilder.append(" is already attached to a view");
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void disconnectFromView(int paramInt) {
    if (this.mConnectedViewTag == paramInt) {
      this.mConnectedViewTag = -1;
      return;
    } 
    throw new JSApplicationIllegalArgumentException("Attempting to disconnect view that has not been connected with the given animated node");
  }
  
  public void restoreDefaultValues() {
    ReadableMapKeySetIterator readableMapKeySetIterator = this.mPropMap.keySetIterator();
    while (readableMapKeySetIterator.hasNextKey())
      this.mPropMap.putNull(readableMapKeySetIterator.nextKey()); 
    this.mUIImplementation.synchronouslyUpdateViewOnUIThread(this.mConnectedViewTag, this.mDiffMap);
  }
  
  public final void updateView() {
    if (this.mConnectedViewTag == -1)
      return; 
    Iterator<Map.Entry> iterator = this.mPropNodeMapping.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry entry = iterator.next();
      AnimatedNode animatedNode = this.mNativeAnimatedNodesManager.getNodeById(((Integer)entry.getValue()).intValue());
      if (animatedNode != null) {
        if (animatedNode instanceof StyleAnimatedNode) {
          ((StyleAnimatedNode)animatedNode).collectViewUpdates(this.mPropMap);
          continue;
        } 
        if (animatedNode instanceof ValueAnimatedNode) {
          this.mPropMap.putDouble((String)entry.getKey(), ((ValueAnimatedNode)animatedNode).getValue());
          continue;
        } 
        StringBuilder stringBuilder = new StringBuilder("Unsupported type of node used in property node ");
        stringBuilder.append(animatedNode.getClass());
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new IllegalArgumentException("Mapped property node does not exists");
    } 
    this.mUIImplementation.synchronouslyUpdateViewOnUIThread(this.mConnectedViewTag, this.mDiffMap);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\PropsAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */