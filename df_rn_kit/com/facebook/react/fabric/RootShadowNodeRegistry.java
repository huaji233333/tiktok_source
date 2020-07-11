package com.facebook.react.fabric;

import android.util.SparseArray;
import com.facebook.react.uimanager.ReactShadowNode;

public class RootShadowNodeRegistry {
  private final SparseArray<ReactShadowNode> mTagsToRootNodes = new SparseArray();
  
  public void addNode(ReactShadowNode paramReactShadowNode) {
    this.mTagsToRootNodes.put(paramReactShadowNode.getReactTag(), paramReactShadowNode);
  }
  
  public ReactShadowNode getNode(int paramInt) {
    return (ReactShadowNode)this.mTagsToRootNodes.get(paramInt);
  }
  
  public void removeNode(int paramInt) {
    this.mTagsToRootNodes.remove(paramInt);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\fabric\RootShadowNodeRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */