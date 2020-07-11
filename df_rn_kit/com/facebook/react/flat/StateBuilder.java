package com.facebook.react.flat;

import android.util.SparseIntArray;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.OnLayoutEvent;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import java.util.ArrayList;

final class StateBuilder {
  static final float[] EMPTY_FLOAT_ARRAY = new float[0];
  
  private static final int[] EMPTY_INT_ARRAY;
  
  static final SparseIntArray EMPTY_SPARSE_INT = new SparseIntArray();
  
  private final ElementsList<AttachDetachListener> mAttachDetachListeners = new ElementsList<AttachDetachListener>(AttachDetachListener.EMPTY_ARRAY);
  
  private FlatUIViewOperationQueue.DetachAllChildrenFromViews mDetachAllChildrenFromViews;
  
  private final ElementsList<DrawCommand> mDrawCommands = new ElementsList<DrawCommand>(DrawCommand.EMPTY_ARRAY);
  
  private final ElementsList<FlatShadowNode> mNativeChildren = new ElementsList<FlatShadowNode>(FlatShadowNode.EMPTY_ARRAY);
  
  private final ElementsList<NodeRegion> mNodeRegions = new ElementsList<NodeRegion>(NodeRegion.EMPTY_ARRAY);
  
  private final ArrayList<OnLayoutEvent> mOnLayoutEvents = new ArrayList<OnLayoutEvent>();
  
  private final FlatUIViewOperationQueue mOperationsQueue;
  
  private final ArrayList<Integer> mParentsForViewsToDrop = new ArrayList<Integer>();
  
  private final ArrayList<UIViewOperationQueue.UIOperation> mUpdateViewBoundsOperations = new ArrayList<UIViewOperationQueue.UIOperation>();
  
  private final ArrayList<UIViewOperationQueue.UIOperation> mViewManagerCommands = new ArrayList<UIViewOperationQueue.UIOperation>();
  
  private final ArrayList<FlatShadowNode> mViewsToDetach = new ArrayList<FlatShadowNode>();
  
  private final ArrayList<FlatShadowNode> mViewsToDetachAllChildrenFrom = new ArrayList<FlatShadowNode>();
  
  private final ArrayList<Integer> mViewsToDrop = new ArrayList<Integer>();
  
  static {
    EMPTY_INT_ARRAY = new int[0];
  }
  
  StateBuilder(FlatUIViewOperationQueue paramFlatUIViewOperationQueue) {
    this.mOperationsQueue = paramFlatUIViewOperationQueue;
  }
  
  private void addNativeChild(FlatShadowNode paramFlatShadowNode) {
    this.mNativeChildren.add(paramFlatShadowNode);
  }
  
  private void addNodeRegion(FlatShadowNode paramFlatShadowNode, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
    if (paramFloat1 != paramFloat3) {
      if (paramFloat2 == paramFloat4)
        return; 
      paramFlatShadowNode.updateNodeRegion(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean);
      if (paramFlatShadowNode.doesDraw())
        this.mNodeRegions.add(paramFlatShadowNode.getNodeRegion()); 
    } 
  }
  
  private boolean collectStateForMountableNode(FlatShadowNode paramFlatShadowNode, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    boolean bool1;
    boolean bool2;
    null = paramFlatShadowNode.hasNewLayout();
    boolean bool3 = false;
    if (null || paramFlatShadowNode.isUpdated() || paramFlatShadowNode.hasUnseenUpdates() || paramFlatShadowNode.clipBoundsChanged(paramFloat5, paramFloat6, paramFloat7, paramFloat8)) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (!bool1)
      return false; 
    paramFlatShadowNode.setClipBounds(paramFloat5, paramFloat6, paramFloat7, paramFloat8);
    this.mDrawCommands.start((Object[])paramFlatShadowNode.getDrawCommands());
    this.mAttachDetachListeners.start((Object[])paramFlatShadowNode.getAttachDetachListeners());
    this.mNodeRegions.start((Object[])paramFlatShadowNode.getNodeRegions());
    this.mNativeChildren.start((Object[])paramFlatShadowNode.getNativeChildren());
    if (paramFlatShadowNode instanceof AndroidView) {
      AndroidView androidView = (AndroidView)paramFlatShadowNode;
      updateViewPadding(androidView, paramFlatShadowNode.getReactTag());
      bool = androidView.needsCustomLayoutForChildren();
      paramFloat5 = Float.NEGATIVE_INFINITY;
      paramFloat6 = Float.NEGATIVE_INFINITY;
      paramFloat7 = Float.POSITIVE_INFINITY;
      null = true;
      paramFloat8 = Float.POSITIVE_INFINITY;
    } else {
      null = false;
      bool = false;
    } 
    if (!null && paramFlatShadowNode.isVirtualAnchor())
      addNodeRegion(paramFlatShadowNode, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true); 
    boolean bool = collectStateRecursively(paramFlatShadowNode, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, null, bool);
    DrawCommand[] arrayOfDrawCommand = this.mDrawCommands.finish();
    if (arrayOfDrawCommand != null) {
      paramFlatShadowNode.setDrawCommands(arrayOfDrawCommand);
      bool1 = true;
    } else {
      bool1 = false;
    } 
    AttachDetachListener[] arrayOfAttachDetachListener = this.mAttachDetachListeners.finish();
    if (arrayOfAttachDetachListener != null) {
      paramFlatShadowNode.setAttachDetachListeners(arrayOfAttachDetachListener);
      bool1 = true;
    } 
    NodeRegion[] arrayOfNodeRegion = this.mNodeRegions.finish();
    if (arrayOfNodeRegion != null) {
      paramFlatShadowNode.setNodeRegions(arrayOfNodeRegion);
      bool2 = true;
    } else {
      bool2 = bool1;
      if (bool) {
        paramFlatShadowNode.updateOverflowsContainer();
        bool2 = bool1;
      } 
    } 
    FlatShadowNode[] arrayOfFlatShadowNode = this.mNativeChildren.finish();
    if (bool2)
      if (paramFlatShadowNode.clipsSubviews()) {
        float[] arrayOfFloat2;
        float[] arrayOfFloat3;
        float[] arrayOfFloat4;
        float[] arrayOfFloat1 = EMPTY_FLOAT_ARRAY;
        SparseIntArray sparseIntArray = EMPTY_SPARSE_INT;
        if (arrayOfDrawCommand != null) {
          sparseIntArray = new SparseIntArray();
          arrayOfFloat2 = new float[arrayOfDrawCommand.length];
          arrayOfFloat3 = new float[arrayOfDrawCommand.length];
          if (paramFlatShadowNode.isHorizontal()) {
            HorizontalDrawCommandManager.fillMaxMinArrays(arrayOfDrawCommand, arrayOfFloat2, arrayOfFloat3, sparseIntArray);
          } else {
            VerticalDrawCommandManager.fillMaxMinArrays(arrayOfDrawCommand, arrayOfFloat2, arrayOfFloat3, sparseIntArray);
          } 
        } else {
          arrayOfFloat3 = arrayOfFloat1;
          arrayOfFloat2 = arrayOfFloat1;
        } 
        arrayOfFloat1 = EMPTY_FLOAT_ARRAY;
        if (arrayOfNodeRegion != null) {
          arrayOfFloat1 = new float[arrayOfNodeRegion.length];
          arrayOfFloat4 = new float[arrayOfNodeRegion.length];
          if (paramFlatShadowNode.isHorizontal()) {
            HorizontalDrawCommandManager.fillMaxMinArrays(arrayOfNodeRegion, arrayOfFloat1, arrayOfFloat4);
          } else {
            VerticalDrawCommandManager.fillMaxMinArrays(arrayOfNodeRegion, arrayOfFloat1, arrayOfFloat4);
          } 
        } else {
          arrayOfFloat4 = arrayOfFloat1;
        } 
        if (arrayOfFlatShadowNode != null) {
          null = true;
        } else {
          null = false;
        } 
        this.mOperationsQueue.enqueueUpdateClippingMountState(paramFlatShadowNode.getReactTag(), arrayOfDrawCommand, sparseIntArray, arrayOfFloat2, arrayOfFloat3, arrayOfAttachDetachListener, arrayOfNodeRegion, arrayOfFloat1, arrayOfFloat4, null);
      } else {
        this.mOperationsQueue.enqueueUpdateMountState(paramFlatShadowNode.getReactTag(), arrayOfDrawCommand, arrayOfAttachDetachListener, arrayOfNodeRegion);
      }  
    if (paramFlatShadowNode.hasUnseenUpdates()) {
      paramFlatShadowNode.onCollectExtraUpdates(this.mOperationsQueue);
      paramFlatShadowNode.markUpdateSeen();
    } 
    if (arrayOfFlatShadowNode != null)
      updateNativeChildren(paramFlatShadowNode, paramFlatShadowNode.getNativeChildren(), arrayOfFlatShadowNode); 
    if (!bool2 && arrayOfFlatShadowNode == null) {
      null = bool3;
      return bool ? true : null;
    } 
    return true;
  }
  
  private boolean collectStateRecursively(FlatShadowNode paramFlatShadowNode, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramFlatShadowNode.hasNewLayout())
      paramFlatShadowNode.markLayoutSeen(); 
    float f1 = roundToPixel(paramFloat1);
    float f2 = roundToPixel(paramFloat2);
    float f3 = roundToPixel(paramFloat3);
    float f4 = roundToPixel(paramFloat4);
    if (paramFlatShadowNode.shouldNotifyOnLayout()) {
      OnLayoutEvent onLayoutEvent = paramFlatShadowNode.obtainLayoutEvent(Math.round(paramFlatShadowNode.getLayoutX()), Math.round(paramFlatShadowNode.getLayoutY()), (int)(f3 - f1), (int)(f4 - f2));
      if (onLayoutEvent != null)
        this.mOnLayoutEvents.add(onLayoutEvent); 
    } 
    if (paramFlatShadowNode.clipToBounds()) {
      paramFloat5 = Math.max(paramFloat1, paramFloat5);
      paramFloat6 = Math.max(paramFloat2, paramFloat6);
      paramFloat7 = Math.min(paramFloat3, paramFloat7);
      paramFloat8 = Math.min(paramFloat4, paramFloat8);
      paramFloat3 = paramFloat5;
      paramFloat4 = paramFloat6;
      paramFloat5 = paramFloat7;
      paramFloat6 = paramFloat8;
      paramFloat7 = paramFloat5;
      paramFloat8 = paramFloat6;
    } else {
      paramFloat4 = paramFloat6;
      paramFloat3 = paramFloat5;
    } 
    paramFlatShadowNode.collectState(this, f1, f2, f3, f4, roundToPixel(paramFloat3), roundToPixel(paramFloat4), roundToPixel(paramFloat7), paramFloat8);
    int j = paramFlatShadowNode.getChildCount();
    int i = 0;
    boolean bool;
    for (bool = false; i != j; bool = bool1) {
      ReactShadowNodeImpl reactShadowNodeImpl = paramFlatShadowNode.getChildAt(i);
      boolean bool1 = bool;
      if (!reactShadowNodeImpl.isVirtual())
        bool1 = bool | processNodeAndCollectState((FlatShadowNode)reactShadowNodeImpl, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat7, paramFloat8, paramBoolean1, paramBoolean2); 
      i++;
    } 
    paramFlatShadowNode.resetUpdated();
    return bool;
  }
  
  private static int[] collectViewTags(ArrayList<FlatShadowNode> paramArrayList) {
    int j = paramArrayList.size();
    if (j == 0)
      return EMPTY_INT_ARRAY; 
    int[] arrayOfInt = new int[j];
    for (int i = 0; i < j; i++)
      arrayOfInt[i] = ((FlatShadowNode)paramArrayList.get(i)).getReactTag(); 
    return arrayOfInt;
  }
  
  private boolean processNodeAndCollectState(FlatShadowNode paramFlatShadowNode, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean1, boolean paramBoolean2) {
    float f2 = paramFlatShadowNode.getLayoutWidth();
    float f1 = paramFlatShadowNode.getLayoutHeight();
    paramFloat1 += paramFlatShadowNode.getLayoutX();
    paramFloat2 += paramFlatShadowNode.getLayoutY();
    f2 += paramFloat1;
    f1 += paramFloat2;
    boolean bool = paramFlatShadowNode.mountsToView();
    if (!paramBoolean1)
      addNodeRegion(paramFlatShadowNode, paramFloat1, paramFloat2, f2, f1, bool ^ true); 
    if (bool) {
      ensureBackingViewIsCreated(paramFlatShadowNode);
      addNativeChild(paramFlatShadowNode);
      bool = collectStateForMountableNode(paramFlatShadowNode, 0.0F, 0.0F, f2 - paramFloat1, f1 - paramFloat2, paramFloat3 - paramFloat1, paramFloat4 - paramFloat2, paramFloat5 - paramFloat1, paramFloat6 - paramFloat2);
      if (!paramBoolean1)
        this.mDrawCommands.add(paramFlatShadowNode.collectDrawView(paramFloat1, paramFloat2, f2, f1, paramFloat3, paramFloat4, paramFloat5, paramFloat6)); 
      paramBoolean1 = bool;
      if (!paramBoolean2) {
        updateViewBounds(paramFlatShadowNode, paramFloat1, paramFloat2, f2, f1);
        return bool;
      } 
    } else {
      paramBoolean1 = collectStateRecursively(paramFlatShadowNode, paramFloat1, paramFloat2, f2, f1, paramFloat3, paramFloat4, paramFloat5, paramFloat6, false, false);
    } 
    return paramBoolean1;
  }
  
  private static float roundToPixel(float paramFloat) {
    return (float)Math.floor((paramFloat + 0.5F));
  }
  
  private void updateNativeChildren(FlatShadowNode paramFlatShadowNode, FlatShadowNode[] paramArrayOfFlatShadowNode1, FlatShadowNode[] paramArrayOfFlatShadowNode2) {
    FlatShadowNode flatShadowNode;
    paramFlatShadowNode.setNativeChildren(paramArrayOfFlatShadowNode2);
    if (this.mDetachAllChildrenFromViews == null)
      this.mDetachAllChildrenFromViews = this.mOperationsQueue.enqueueDetachAllChildrenFromViews(); 
    if (paramArrayOfFlatShadowNode1.length != 0)
      this.mViewsToDetachAllChildrenFrom.add(paramFlatShadowNode); 
    int k = paramFlatShadowNode.getReactTag();
    int i = paramArrayOfFlatShadowNode2.length;
    boolean bool = false;
    if (i == 0) {
      int[] arrayOfInt1 = EMPTY_INT_ARRAY;
    } else {
      int[] arrayOfInt1 = new int[i];
      int n = paramArrayOfFlatShadowNode2.length;
      i = 0;
      int m = 0;
      while (true) {
        int[] arrayOfInt2 = arrayOfInt1;
        if (i < n) {
          flatShadowNode = paramArrayOfFlatShadowNode2[i];
          if (flatShadowNode.getNativeParentTag() == k) {
            arrayOfInt1[m] = -flatShadowNode.getReactTag();
          } else {
            arrayOfInt1[m] = flatShadowNode.getReactTag();
          } 
          flatShadowNode.setNativeParentTag(-1);
          m++;
          i++;
          continue;
        } 
        break;
      } 
    } 
    int j = paramArrayOfFlatShadowNode1.length;
    for (i = 0; i < j; i++) {
      FlatShadowNode flatShadowNode1 = paramArrayOfFlatShadowNode1[i];
      if (flatShadowNode1.getNativeParentTag() == k) {
        this.mViewsToDetach.add(flatShadowNode1);
        flatShadowNode1.setNativeParentTag(-1);
      } 
    } 
    int[] arrayOfInt = collectViewTags(this.mViewsToDetach);
    this.mViewsToDetach.clear();
    j = paramArrayOfFlatShadowNode2.length;
    for (i = bool; i < j; i++)
      paramArrayOfFlatShadowNode2[i].setNativeParentTag(k); 
    this.mOperationsQueue.enqueueUpdateViewGroup(k, (int[])flatShadowNode, arrayOfInt);
  }
  
  private void updateViewBounds(FlatShadowNode paramFlatShadowNode, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    int i = Math.round(paramFloat1);
    int j = Math.round(paramFloat2);
    int k = Math.round(paramFloat3);
    int m = Math.round(paramFloat4);
    if (paramFlatShadowNode.getViewLeft() == i && paramFlatShadowNode.getViewTop() == j && paramFlatShadowNode.getViewRight() == k && paramFlatShadowNode.getViewBottom() == m)
      return; 
    paramFlatShadowNode.setViewBounds(i, j, k, m);
    int n = paramFlatShadowNode.getReactTag();
    this.mUpdateViewBoundsOperations.add(this.mOperationsQueue.createUpdateViewBounds(n, i, j, k, m));
  }
  
  private void updateViewPadding(AndroidView paramAndroidView, int paramInt) {
    if (paramAndroidView.isPaddingChanged()) {
      this.mOperationsQueue.enqueueSetPadding(paramInt, Math.round(paramAndroidView.getPadding(0)), Math.round(paramAndroidView.getPadding(1)), Math.round(paramAndroidView.getPadding(2)), Math.round(paramAndroidView.getPadding(3)));
      paramAndroidView.resetPaddingChanged();
    } 
  }
  
  final void addAttachDetachListener(AttachDetachListener paramAttachDetachListener) {
    this.mAttachDetachListeners.add(paramAttachDetachListener);
  }
  
  final void addDrawCommand(AbstractDrawCommand paramAbstractDrawCommand) {
    this.mDrawCommands.add(paramAbstractDrawCommand);
  }
  
  final void afterUpdateViewHierarchy(EventDispatcher paramEventDispatcher) {
    if (this.mDetachAllChildrenFromViews != null) {
      int[] arrayOfInt = collectViewTags(this.mViewsToDetachAllChildrenFrom);
      this.mViewsToDetachAllChildrenFrom.clear();
      this.mDetachAllChildrenFromViews.setViewsToDetachAllChildrenFrom(arrayOfInt);
      this.mDetachAllChildrenFromViews = null;
    } 
    int j = this.mUpdateViewBoundsOperations.size();
    boolean bool = false;
    int i;
    for (i = 0; i != j; i++)
      this.mOperationsQueue.enqueueFlatUIOperation(this.mUpdateViewBoundsOperations.get(i)); 
    this.mUpdateViewBoundsOperations.clear();
    j = this.mViewManagerCommands.size();
    for (i = 0; i != j; i++)
      this.mOperationsQueue.enqueueFlatUIOperation(this.mViewManagerCommands.get(i)); 
    this.mViewManagerCommands.clear();
    j = this.mOnLayoutEvents.size();
    for (i = bool; i != j; i++)
      paramEventDispatcher.dispatchEvent((Event)this.mOnLayoutEvents.get(i)); 
    this.mOnLayoutEvents.clear();
    if (this.mViewsToDrop.size() > 0) {
      this.mOperationsQueue.enqueueDropViews(this.mViewsToDrop, this.mParentsForViewsToDrop);
      this.mViewsToDrop.clear();
      this.mParentsForViewsToDrop.clear();
    } 
    this.mOperationsQueue.enqueueProcessLayoutRequests();
  }
  
  final void applyUpdates(FlatShadowNode paramFlatShadowNode) {
    float f4 = paramFlatShadowNode.getLayoutWidth();
    float f3 = paramFlatShadowNode.getLayoutHeight();
    float f1 = paramFlatShadowNode.getLayoutX();
    float f2 = paramFlatShadowNode.getLayoutY();
    f4 += f1;
    f3 += f2;
    collectStateForMountableNode(paramFlatShadowNode, f1, f2, f4, f3, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    updateViewBounds(paramFlatShadowNode, f1, f2, f4, f3);
  }
  
  final void dropView(FlatShadowNode paramFlatShadowNode, int paramInt) {
    this.mViewsToDrop.add(Integer.valueOf(paramFlatShadowNode.getReactTag()));
    this.mParentsForViewsToDrop.add(Integer.valueOf(paramInt));
  }
  
  final void enqueueCreateOrUpdateView(FlatShadowNode paramFlatShadowNode, ReactStylesDiffMap paramReactStylesDiffMap) {
    if (paramFlatShadowNode.isBackingViewCreated()) {
      this.mOperationsQueue.enqueueUpdateProperties(paramFlatShadowNode.getReactTag(), paramFlatShadowNode.getViewClass(), paramReactStylesDiffMap);
      return;
    } 
    this.mOperationsQueue.enqueueCreateView(paramFlatShadowNode.getThemedContext(), paramFlatShadowNode.getReactTag(), paramFlatShadowNode.getViewClass(), paramReactStylesDiffMap);
    paramFlatShadowNode.signalBackingViewIsCreated();
  }
  
  final void enqueueViewManagerCommand(int paramInt1, int paramInt2, ReadableArray paramReadableArray) {
    this.mViewManagerCommands.add(this.mOperationsQueue.createViewManagerCommand(paramInt1, paramInt2, paramReadableArray));
  }
  
  final void ensureBackingViewIsCreated(FlatShadowNode paramFlatShadowNode) {
    if (paramFlatShadowNode.isBackingViewCreated())
      return; 
    int i = paramFlatShadowNode.getReactTag();
    this.mOperationsQueue.enqueueCreateView(paramFlatShadowNode.getThemedContext(), i, paramFlatShadowNode.getViewClass(), null);
    paramFlatShadowNode.signalBackingViewIsCreated();
  }
  
  final FlatUIViewOperationQueue getOperationsQueue() {
    return this.mOperationsQueue;
  }
  
  final void removeRootView(int paramInt) {
    this.mViewsToDrop.add(Integer.valueOf(-paramInt));
    this.mParentsForViewsToDrop.add(Integer.valueOf(-1));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\StateBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */