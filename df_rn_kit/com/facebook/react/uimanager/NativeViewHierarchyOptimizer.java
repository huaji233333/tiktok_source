package com.facebook.react.uimanager;

import android.util.SparseBooleanArray;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

public class NativeViewHierarchyOptimizer {
  private final ShadowNodeRegistry mShadowNodeRegistry;
  
  private final SparseBooleanArray mTagsWithLayoutVisited = new SparseBooleanArray();
  
  private final UIViewOperationQueue mUIViewOperationQueue;
  
  public NativeViewHierarchyOptimizer(UIViewOperationQueue paramUIViewOperationQueue, ShadowNodeRegistry paramShadowNodeRegistry) {
    this.mUIViewOperationQueue = paramUIViewOperationQueue;
    this.mShadowNodeRegistry = paramShadowNodeRegistry;
  }
  
  private void addGrandchildren(ReactShadowNode paramReactShadowNode1, ReactShadowNode<Object> paramReactShadowNode2, int paramInt) {
    a.a(paramReactShadowNode1.isLayoutOnly() ^ true);
    int i;
    for (i = 0; i < paramReactShadowNode2.getChildCount(); i++) {
      boolean bool;
      ReactShadowNode reactShadowNode = (ReactShadowNode)paramReactShadowNode2.getChildAt(i);
      if (reactShadowNode.getNativeParent() == null) {
        bool = true;
      } else {
        bool = false;
      } 
      a.a(bool);
      if (reactShadowNode.isLayoutOnly()) {
        int j = paramReactShadowNode1.getNativeChildCount();
        addLayoutOnlyNode(paramReactShadowNode1, reactShadowNode, paramInt);
        paramInt += paramReactShadowNode1.getNativeChildCount() - j;
      } else {
        addNonLayoutNode(paramReactShadowNode1, reactShadowNode, paramInt);
        paramInt++;
      } 
    } 
  }
  
  private void addLayoutOnlyNode(ReactShadowNode paramReactShadowNode1, ReactShadowNode paramReactShadowNode2, int paramInt) {
    addGrandchildren(paramReactShadowNode1, paramReactShadowNode2, paramInt);
  }
  
  private void addNodeToNode(ReactShadowNode paramReactShadowNode1, ReactShadowNode paramReactShadowNode2, int paramInt) {
    int i = paramReactShadowNode1.getNativeOffsetForChild(paramReactShadowNode1.getChildAt(paramInt));
    ReactShadowNode reactShadowNode = paramReactShadowNode1;
    paramInt = i;
    if (paramReactShadowNode1.isLayoutOnly()) {
      NodeIndexPair nodeIndexPair = walkUpUntilNonLayoutOnly(paramReactShadowNode1, i);
      if (nodeIndexPair == null)
        return; 
      reactShadowNode = nodeIndexPair.node;
      paramInt = nodeIndexPair.index;
    } 
    if (!paramReactShadowNode2.isLayoutOnly()) {
      addNonLayoutNode(reactShadowNode, paramReactShadowNode2, paramInt);
      return;
    } 
    addLayoutOnlyNode(reactShadowNode, paramReactShadowNode2, paramInt);
  }
  
  private void addNonLayoutNode(ReactShadowNode<ReactShadowNode> paramReactShadowNode1, ReactShadowNode paramReactShadowNode2, int paramInt) {
    paramReactShadowNode1.addNativeChildAt(paramReactShadowNode2, paramInt);
    this.mUIViewOperationQueue.enqueueManageChildren(paramReactShadowNode1.getReactTag(), null, new ViewAtIndex[] { new ViewAtIndex(paramReactShadowNode2.getReactTag(), paramInt) }null);
  }
  
  private void applyLayoutBase(ReactShadowNode<Object> paramReactShadowNode) {
    int i = paramReactShadowNode.getReactTag();
    if (this.mTagsWithLayoutVisited.get(i))
      return; 
    this.mTagsWithLayoutVisited.put(i, true);
    ReactShadowNode<ReactShadowNode> reactShadowNode = (ReactShadowNode<ReactShadowNode>)paramReactShadowNode.getParent();
    int j = paramReactShadowNode.getScreenX();
    i = paramReactShadowNode.getScreenY();
    while (reactShadowNode != null && reactShadowNode.isLayoutOnly()) {
      j += Math.round(reactShadowNode.getLayoutX());
      i += Math.round(reactShadowNode.getLayoutY());
      reactShadowNode = reactShadowNode.getParent();
    } 
    applyLayoutRecursive(paramReactShadowNode, j, i);
  }
  
  private void applyLayoutRecursive(ReactShadowNode<Object> paramReactShadowNode, int paramInt1, int paramInt2) {
    if (!paramReactShadowNode.isLayoutOnly() && paramReactShadowNode.getNativeParent() != null) {
      int j = paramReactShadowNode.getReactTag();
      this.mUIViewOperationQueue.enqueueUpdateLayout(paramReactShadowNode.getNativeParent().getReactTag(), j, paramInt1, paramInt2, paramReactShadowNode.getScreenWidth(), paramReactShadowNode.getScreenHeight());
      return;
    } 
    int i;
    for (i = 0; i < paramReactShadowNode.getChildCount(); i++) {
      ReactShadowNode reactShadowNode = (ReactShadowNode)paramReactShadowNode.getChildAt(i);
      int j = reactShadowNode.getReactTag();
      if (!this.mTagsWithLayoutVisited.get(j)) {
        this.mTagsWithLayoutVisited.put(j, true);
        applyLayoutRecursive(reactShadowNode, reactShadowNode.getScreenX() + paramInt1, reactShadowNode.getScreenY() + paramInt2);
      } 
    } 
  }
  
  public static void handleRemoveNode(ReactShadowNode paramReactShadowNode) {
    paramReactShadowNode.removeAllNativeChildren();
  }
  
  private static boolean isLayoutOnlyAndCollapsable(ReactStylesDiffMap paramReactStylesDiffMap) {
    if (paramReactStylesDiffMap == null)
      return true; 
    if (paramReactStylesDiffMap.hasKey("collapsable") && !paramReactStylesDiffMap.getBoolean("collapsable", true))
      return false; 
    ReadableMapKeySetIterator readableMapKeySetIterator = paramReactStylesDiffMap.mBackingMap.keySetIterator();
    while (readableMapKeySetIterator.hasNextKey()) {
      if (!ViewProps.isLayoutOnly(paramReactStylesDiffMap.mBackingMap, readableMapKeySetIterator.nextKey()))
        return false; 
    } 
    return true;
  }
  
  private void removeNodeFromParent(ReactShadowNode<Object> paramReactShadowNode, boolean paramBoolean) {
    ReactShadowNode<ReactShadowNode<Object>> reactShadowNode = (ReactShadowNode<ReactShadowNode<Object>>)paramReactShadowNode.getNativeParent();
    if (reactShadowNode != null) {
      int j = reactShadowNode.indexOfNativeChild(paramReactShadowNode);
      reactShadowNode.removeNativeChildAt(j);
      UIViewOperationQueue uIViewOperationQueue = this.mUIViewOperationQueue;
      int k = reactShadowNode.getReactTag();
      if (paramBoolean) {
        int[] arrayOfInt2 = new int[1];
        arrayOfInt2[0] = paramReactShadowNode.getReactTag();
        int[] arrayOfInt1 = arrayOfInt2;
      } else {
        paramReactShadowNode = null;
      } 
      uIViewOperationQueue.enqueueManageChildren(k, new int[] { j }, null, (int[])paramReactShadowNode);
      return;
    } 
    for (int i = paramReactShadowNode.getChildCount() - 1; i >= 0; i--)
      removeNodeFromParent((ReactShadowNode)paramReactShadowNode.getChildAt(i), paramBoolean); 
  }
  
  private void transitionLayoutOnlyViewToNativeView(ReactShadowNode<Object> paramReactShadowNode, ReactStylesDiffMap paramReactStylesDiffMap) {
    boolean bool2;
    ReactShadowNode<ReactShadowNode<Object>> reactShadowNode = (ReactShadowNode<ReactShadowNode<Object>>)paramReactShadowNode.getParent();
    boolean bool1 = false;
    if (reactShadowNode == null) {
      paramReactShadowNode.setIsLayoutOnly(false);
      return;
    } 
    int i = reactShadowNode.indexOf(paramReactShadowNode);
    reactShadowNode.removeChildAt(i);
    removeNodeFromParent(paramReactShadowNode, false);
    paramReactShadowNode.setIsLayoutOnly(false);
    this.mUIViewOperationQueue.enqueueCreateView(paramReactShadowNode.getRootNode().getThemedContext(), paramReactShadowNode.getReactTag(), paramReactShadowNode.getViewClass(), paramReactStylesDiffMap);
    reactShadowNode.addChildAt(paramReactShadowNode, i);
    addNodeToNode(reactShadowNode, paramReactShadowNode, i);
    for (i = 0; i < paramReactShadowNode.getChildCount(); i++)
      addNodeToNode(paramReactShadowNode, (ReactShadowNode)paramReactShadowNode.getChildAt(i), i); 
    if (this.mTagsWithLayoutVisited.size() == 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    a.a(bool2);
    applyLayoutBase(paramReactShadowNode);
    for (i = bool1; i < paramReactShadowNode.getChildCount(); i++)
      applyLayoutBase((ReactShadowNode)paramReactShadowNode.getChildAt(i)); 
    this.mTagsWithLayoutVisited.clear();
  }
  
  private NodeIndexPair walkUpUntilNonLayoutOnly(ReactShadowNode<Object> paramReactShadowNode, int paramInt) {
    ReactShadowNode<ReactShadowNode<Object>> reactShadowNode;
    while (paramReactShadowNode.isLayoutOnly()) {
      ReactShadowNode<ReactShadowNode<Object>> reactShadowNode1 = (ReactShadowNode<ReactShadowNode<Object>>)paramReactShadowNode.getParent();
      if (reactShadowNode1 == null)
        return null; 
      paramInt += reactShadowNode1.getNativeOffsetForChild(paramReactShadowNode);
      reactShadowNode = reactShadowNode1;
    } 
    return new NodeIndexPair(reactShadowNode, paramInt);
  }
  
  public void handleCreateView(ReactShadowNode paramReactShadowNode, ThemedReactContext paramThemedReactContext, ReactStylesDiffMap paramReactStylesDiffMap) {
    boolean bool;
    if (paramReactShadowNode.getViewClass().equals("RCTView") && isLayoutOnlyAndCollapsable(paramReactStylesDiffMap)) {
      bool = true;
    } else {
      bool = false;
    } 
    paramReactShadowNode.setIsLayoutOnly(bool);
    if (!bool)
      this.mUIViewOperationQueue.enqueueCreateView(paramThemedReactContext, paramReactShadowNode.getReactTag(), paramReactShadowNode.getViewClass(), paramReactStylesDiffMap); 
  }
  
  public void handleManageChildren(ReactShadowNode paramReactShadowNode, int[] paramArrayOfint1, int[] paramArrayOfint2, ViewAtIndex[] paramArrayOfViewAtIndex, int[] paramArrayOfint3) {
    int j;
    byte b = 0;
    int i = 0;
    while (true) {
      j = b;
      if (i < paramArrayOfint2.length) {
        boolean bool;
        int k = paramArrayOfint2[i];
        j = 0;
        while (true) {
          if (j < paramArrayOfint3.length) {
            if (paramArrayOfint3[j] == k) {
              boolean bool1 = true;
              break;
            } 
            j++;
            continue;
          } 
          bool = false;
          break;
        } 
        removeNodeFromParent(this.mShadowNodeRegistry.getNode(k), bool);
        i++;
        continue;
      } 
      break;
    } 
    while (j < paramArrayOfViewAtIndex.length) {
      ViewAtIndex viewAtIndex = paramArrayOfViewAtIndex[j];
      addNodeToNode(paramReactShadowNode, this.mShadowNodeRegistry.getNode(viewAtIndex.mTag), viewAtIndex.mIndex);
      j++;
    } 
  }
  
  public void handleSetChildren(ReactShadowNode paramReactShadowNode, ReadableArray paramReadableArray) {
    for (int i = 0; i < paramReadableArray.size(); i++)
      addNodeToNode(paramReactShadowNode, this.mShadowNodeRegistry.getNode(paramReadableArray.getInt(i)), i); 
  }
  
  public void handleUpdateLayout(ReactShadowNode paramReactShadowNode) {
    applyLayoutBase(paramReactShadowNode);
  }
  
  public void handleUpdateView(ReactShadowNode paramReactShadowNode, String paramString, ReactStylesDiffMap paramReactStylesDiffMap) {
    boolean bool;
    if (paramReactShadowNode.isLayoutOnly() && !isLayoutOnlyAndCollapsable(paramReactStylesDiffMap)) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      transitionLayoutOnlyViewToNativeView(paramReactShadowNode, paramReactStylesDiffMap);
      return;
    } 
    if (!paramReactShadowNode.isLayoutOnly())
      this.mUIViewOperationQueue.enqueueUpdateProperties(paramReactShadowNode.getReactTag(), paramString, paramReactStylesDiffMap); 
  }
  
  public void onBatchComplete() {
    this.mTagsWithLayoutVisited.clear();
  }
  
  static class NodeIndexPair {
    public final int index;
    
    public final ReactShadowNode node;
    
    NodeIndexPair(ReactShadowNode param1ReactShadowNode, int param1Int) {
      this.node = param1ReactShadowNode;
      this.index = param1Int;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\NativeViewHierarchyOptimizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */