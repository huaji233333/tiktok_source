package com.facebook.react.uimanager;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.facebook.react.common.SingleThreadAsserter;

public class ShadowNodeRegistry {
  private final SparseBooleanArray mRootTags = new SparseBooleanArray();
  
  private final SparseArray<ReactShadowNode> mTagsToCSSNodes = new SparseArray();
  
  private final SingleThreadAsserter mThreadAsserter = new SingleThreadAsserter();
  
  public void addNode(ReactShadowNode paramReactShadowNode) {
    this.mThreadAsserter.assertNow();
    this.mTagsToCSSNodes.put(paramReactShadowNode.getReactTag(), paramReactShadowNode);
  }
  
  public void addRootNode(ReactShadowNode paramReactShadowNode) {
    int i = paramReactShadowNode.getReactTag();
    this.mTagsToCSSNodes.put(i, paramReactShadowNode);
    this.mRootTags.put(i, true);
  }
  
  public ReactShadowNode getNode(int paramInt) {
    this.mThreadAsserter.assertNow();
    return (ReactShadowNode)this.mTagsToCSSNodes.get(paramInt);
  }
  
  public int getRootNodeCount() {
    this.mThreadAsserter.assertNow();
    return this.mRootTags.size();
  }
  
  public int getRootTag(int paramInt) {
    this.mThreadAsserter.assertNow();
    return this.mRootTags.keyAt(paramInt);
  }
  
  public boolean isRootNode(int paramInt) {
    this.mThreadAsserter.assertNow();
    return this.mRootTags.get(paramInt);
  }
  
  public void removeNode(int paramInt) {
    this.mThreadAsserter.assertNow();
    if (!this.mRootTags.get(paramInt)) {
      this.mTagsToCSSNodes.remove(paramInt);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Trying to remove root node ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" without using removeRootNode!");
    throw new IllegalViewOperationException(stringBuilder.toString());
  }
  
  public void removeRootNode(int paramInt) {
    this.mThreadAsserter.assertNow();
    if (this.mRootTags.get(paramInt)) {
      this.mTagsToCSSNodes.remove(paramInt);
      this.mRootTags.delete(paramInt);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("View with tag ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" is not registered as a root view");
    throw new IllegalViewOperationException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ShadowNodeRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */