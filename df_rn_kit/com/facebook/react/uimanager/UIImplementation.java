package com.facebook.react.uimanager;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.m.a;
import com.facebook.react.animation.Animation;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.common.MeasureSpecProvider;
import com.facebook.react.uimanager.common.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.yoga.YogaDirection;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UIImplementation {
  protected final EventDispatcher mEventDispatcher;
  
  private long mLastCalculateLayoutTime = 0L;
  
  protected LayoutUpdateListener mLayoutUpdateListener;
  
  private final int[] mMeasureBuffer = new int[4];
  
  private final Set<Integer> mMeasuredRootNodes = new HashSet<Integer>();
  
  private final NativeViewHierarchyOptimizer mNativeViewHierarchyOptimizer;
  
  private final UIViewOperationQueue mOperationsQueue;
  
  protected final ReactApplicationContext mReactContext;
  
  protected final ShadowNodeRegistry mShadowNodeRegistry = new ShadowNodeRegistry();
  
  private final ViewManagerRegistry mViewManagers;
  
  protected Object uiImplementationThreadLock = new Object();
  
  public UIImplementation(ReactApplicationContext paramReactApplicationContext, UIManagerModule.ViewManagerResolver paramViewManagerResolver, EventDispatcher paramEventDispatcher, int paramInt) {
    this(paramReactApplicationContext, new ViewManagerRegistry(paramViewManagerResolver), paramEventDispatcher, paramInt);
  }
  
  public UIImplementation(ReactApplicationContext paramReactApplicationContext, ViewManagerRegistry paramViewManagerRegistry, UIViewOperationQueue paramUIViewOperationQueue, EventDispatcher paramEventDispatcher) {
    this.mReactContext = paramReactApplicationContext;
    this.mViewManagers = paramViewManagerRegistry;
    this.mOperationsQueue = paramUIViewOperationQueue;
    this.mNativeViewHierarchyOptimizer = new NativeViewHierarchyOptimizer(this.mOperationsQueue, this.mShadowNodeRegistry);
    this.mEventDispatcher = paramEventDispatcher;
  }
  
  private UIImplementation(ReactApplicationContext paramReactApplicationContext, ViewManagerRegistry paramViewManagerRegistry, EventDispatcher paramEventDispatcher, int paramInt) {
    this(paramReactApplicationContext, paramViewManagerRegistry, new UIViewOperationQueue(paramReactApplicationContext, new NativeViewHierarchyManager(paramViewManagerRegistry), paramInt), paramEventDispatcher);
  }
  
  public UIImplementation(ReactApplicationContext paramReactApplicationContext, List<ViewManager> paramList, EventDispatcher paramEventDispatcher, int paramInt) {
    this(paramReactApplicationContext, new ViewManagerRegistry(paramList), paramEventDispatcher, paramInt);
  }
  
  private void assertNodeDoesNotNeedCustomLayoutForChildren(ReactShadowNode paramReactShadowNode) {
    ViewManager viewManager = (ViewManager)a.b(this.mViewManagers.get(paramReactShadowNode.getViewClass()));
    if (viewManager instanceof ViewGroupManager) {
      viewManager = viewManager;
      if (viewManager != null) {
        if (!viewManager.needsCustomLayoutForChildren())
          return; 
        StringBuilder stringBuilder1 = new StringBuilder("Trying to measure a view using measureLayout/measureLayoutRelativeToParent relative to an ancestor that requires custom layout for it's children (");
        stringBuilder1.append(paramReactShadowNode.getViewClass());
        stringBuilder1.append("). Use measure instead.");
        throw new IllegalViewOperationException(stringBuilder1.toString());
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Trying to use view ");
    stringBuilder.append(paramReactShadowNode.getViewClass());
    stringBuilder.append(" as a parent, but its Manager doesn't extends ViewGroupManager");
    throw new IllegalViewOperationException(stringBuilder.toString());
  }
  
  private void assertViewExists(int paramInt, String paramString) {
    if (this.mShadowNodeRegistry.getNode(paramInt) != null)
      return; 
    StringBuilder stringBuilder = new StringBuilder("Unable to execute operation ");
    stringBuilder.append(paramString);
    stringBuilder.append(" on view with tag: ");
    stringBuilder.append(paramInt);
    stringBuilder.append(", since the view does not exists");
    throw new IllegalViewOperationException(stringBuilder.toString());
  }
  
  private void dispatchViewUpdatesIfNeeded() {
    if (this.mOperationsQueue.isEmpty())
      dispatchViewUpdates(-1); 
  }
  
  private void measureLayout(int paramInt1, int paramInt2, int[] paramArrayOfint) {
    StringBuilder stringBuilder;
    ReactShadowNode<Object> reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt1);
    ReactShadowNode reactShadowNode1 = this.mShadowNodeRegistry.getNode(paramInt2);
    if (reactShadowNode == null || reactShadowNode1 == null) {
      StringBuilder stringBuilder1 = new StringBuilder("Tag ");
      if (reactShadowNode != null)
        paramInt1 = paramInt2; 
      stringBuilder1.append(paramInt1);
      stringBuilder1.append(" does not exist");
      IllegalViewOperationException illegalViewOperationException = new IllegalViewOperationException(stringBuilder1.toString());
      throw illegalViewOperationException;
    } 
    if (reactShadowNode != reactShadowNode1) {
      ReactShadowNode<ReactShadowNode> reactShadowNode2 = (ReactShadowNode<ReactShadowNode>)reactShadowNode.getParent();
      while (reactShadowNode2 != reactShadowNode1) {
        if (reactShadowNode2 != null) {
          reactShadowNode2 = reactShadowNode2.getParent();
          continue;
        } 
        stringBuilder = new StringBuilder("Tag ");
        stringBuilder.append(paramInt2);
        stringBuilder.append(" is not an ancestor of tag ");
        stringBuilder.append(paramInt1);
        throw new IllegalViewOperationException(stringBuilder.toString());
      } 
    } 
    measureLayoutRelativeToVerifiedAncestor(reactShadowNode, reactShadowNode1, (int[])stringBuilder);
  }
  
  private void measureLayoutRelativeToParent(int paramInt, int[] paramArrayOfint) {
    ReactShadowNode<Object> reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt);
    if (reactShadowNode != null) {
      ReactShadowNode reactShadowNode1 = (ReactShadowNode)reactShadowNode.getParent();
      if (reactShadowNode1 != null) {
        measureLayoutRelativeToVerifiedAncestor(reactShadowNode, reactShadowNode1, paramArrayOfint);
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder("View with tag ");
      stringBuilder1.append(paramInt);
      stringBuilder1.append(" doesn't have a parent!");
      throw new IllegalViewOperationException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder("No native view for tag ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" exists!");
    throw new IllegalViewOperationException(stringBuilder.toString());
  }
  
  private void measureLayoutRelativeToVerifiedAncestor(ReactShadowNode<Object> paramReactShadowNode1, ReactShadowNode paramReactShadowNode2, int[] paramArrayOfint) {
    boolean bool1;
    boolean bool2;
    if (paramReactShadowNode1 != paramReactShadowNode2) {
      bool2 = Math.round(paramReactShadowNode1.getLayoutX());
      bool1 = Math.round(paramReactShadowNode1.getLayoutY());
      for (ReactShadowNode<ReactShadowNode> reactShadowNode = (ReactShadowNode<ReactShadowNode>)paramReactShadowNode1.getParent(); reactShadowNode != paramReactShadowNode2; reactShadowNode = reactShadowNode.getParent()) {
        a.b(reactShadowNode);
        assertNodeDoesNotNeedCustomLayoutForChildren(reactShadowNode);
        bool2 += Math.round(reactShadowNode.getLayoutX());
        bool1 += Math.round(reactShadowNode.getLayoutY());
      } 
      assertNodeDoesNotNeedCustomLayoutForChildren(paramReactShadowNode2);
    } else {
      bool2 = false;
      bool1 = false;
    } 
    paramArrayOfint[0] = bool2;
    paramArrayOfint[1] = bool1;
    paramArrayOfint[2] = paramReactShadowNode1.getScreenWidth();
    paramArrayOfint[3] = paramReactShadowNode1.getScreenHeight();
  }
  
  private void notifyOnBeforeLayoutRecursive(ReactShadowNode<ReactShadowNode> paramReactShadowNode) {
    if (!paramReactShadowNode.hasUpdates())
      return; 
    for (int i = 0; i < paramReactShadowNode.getChildCount(); i++)
      notifyOnBeforeLayoutRecursive(paramReactShadowNode.getChildAt(i)); 
    paramReactShadowNode.onBeforeLayout();
  }
  
  private void removeShadowNodeRecursive(ReactShadowNode<ReactShadowNode> paramReactShadowNode) {
    NativeViewHierarchyOptimizer.handleRemoveNode(paramReactShadowNode);
    this.mShadowNodeRegistry.removeNode(paramReactShadowNode.getReactTag());
    this.mMeasuredRootNodes.remove(Integer.valueOf(paramReactShadowNode.getReactTag()));
    for (int i = paramReactShadowNode.getChildCount() - 1; i >= 0; i--)
      removeShadowNodeRecursive(paramReactShadowNode.getChildAt(i)); 
    paramReactShadowNode.removeAndDisposeAllChildren();
  }
  
  public void addAnimation(int paramInt1, int paramInt2, Callback paramCallback) {
    assertViewExists(paramInt1, "addAnimation");
    this.mOperationsQueue.enqueueAddAnimation(paramInt1, paramInt2, paramCallback);
  }
  
  public void addUIBlock(UIBlock paramUIBlock) {
    this.mOperationsQueue.enqueueUIBlock(paramUIBlock);
  }
  
  protected void applyUpdatesRecursive(ReactShadowNode<ReactShadowNode> paramReactShadowNode, float paramFloat1, float paramFloat2) {
    if (!paramReactShadowNode.hasUpdates())
      return; 
    if (!paramReactShadowNode.isVirtualAnchor()) {
      int j;
      for (j = 0; j < paramReactShadowNode.getChildCount(); j++)
        applyUpdatesRecursive(paramReactShadowNode.getChildAt(j), paramReactShadowNode.getLayoutX() + paramFloat1, paramReactShadowNode.getLayoutY() + paramFloat2); 
    } 
    int i = paramReactShadowNode.getReactTag();
    if (!this.mShadowNodeRegistry.isRootNode(i) && paramReactShadowNode.dispatchUpdates(paramFloat1, paramFloat2, this.mOperationsQueue, this.mNativeViewHierarchyOptimizer) && paramReactShadowNode.shouldNotifyOnLayout())
      this.mEventDispatcher.dispatchEvent(OnLayoutEvent.obtain(i, paramReactShadowNode.getScreenX(), paramReactShadowNode.getScreenY(), paramReactShadowNode.getScreenWidth(), paramReactShadowNode.getScreenHeight())); 
    paramReactShadowNode.markUpdateSeen();
  }
  
  protected void calculateRootLayout(ReactShadowNode paramReactShadowNode) {
    paramReactShadowNode.getReactTag();
    long l = SystemClock.uptimeMillis();
    try {
      paramReactShadowNode.calculateLayout();
      return;
    } finally {
      a.a(0L);
      this.mLastCalculateLayoutTime = SystemClock.uptimeMillis() - l;
    } 
  }
  
  public void clearJSResponder() {
    this.mOperationsQueue.enqueueClearJSResponder();
  }
  
  public void configureNextLayoutAnimation(ReadableMap paramReadableMap, Callback paramCallback1, Callback paramCallback2) {
    this.mOperationsQueue.enqueueConfigureLayoutAnimation(paramReadableMap, paramCallback1, paramCallback2);
  }
  
  protected ReactShadowNode createRootShadowNode() {
    ReactShadowNodeImpl reactShadowNodeImpl = new ReactShadowNodeImpl();
    if (I18nUtil.getInstance().isRTL((Context)this.mReactContext))
      reactShadowNodeImpl.setLayoutDirection(YogaDirection.RTL); 
    reactShadowNodeImpl.setViewClassName("Root");
    try {
      if (this.mReactContext != null && this.mReactContext.getCatalystInstance() != null && this.mOperationsQueue != null && this.mOperationsQueue.getNativeViewHierarchyManager() != null) {
        this.mOperationsQueue.getNativeViewHierarchyManager().setCatalyInstance(new WeakReference<CatalystInstance>(this.mReactContext.getCatalystInstance()));
        return reactShadowNodeImpl;
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return reactShadowNodeImpl;
  }
  
  public ReactShadowNode createShadowNode(String paramString) {
    return (ReactShadowNode)this.mViewManagers.get(paramString).createShadowNodeInstance(this.mReactContext);
  }
  
  public void createView(int paramInt1, String paramString, int paramInt2, ReadableMap paramReadableMap) {
    synchronized (this.uiImplementationThreadLock) {
      ReactStylesDiffMap reactStylesDiffMap;
      ReactShadowNode<ReactShadowNode> reactShadowNode = createShadowNode(paramString);
      ReactShadowNode reactShadowNode1 = this.mShadowNodeRegistry.getNode(paramInt2);
      reactShadowNode.setReactTag(paramInt1);
      reactShadowNode.setViewClassName(paramString);
      reactShadowNode.setRootNode(reactShadowNode1);
      reactShadowNode.setThemedContext(reactShadowNode1.getThemedContext());
      this.mShadowNodeRegistry.addNode(reactShadowNode);
      paramString = null;
      if (paramReadableMap != null) {
        reactStylesDiffMap = new ReactStylesDiffMap(paramReadableMap);
        reactShadowNode.updateProperties(reactStylesDiffMap);
      } 
      handleCreateView(reactShadowNode, paramInt2, reactStylesDiffMap);
      return;
    } 
  }
  
  public void dispatchViewManagerCommand(int paramInt1, int paramInt2, ReadableArray paramReadableArray) {
    assertViewExists(paramInt1, "dispatchViewManagerCommand");
    this.mOperationsQueue.enqueueDispatchCommand(paramInt1, paramInt2, paramReadableArray);
  }
  
  public void dispatchViewUpdates(int paramInt) {
    long l = SystemClock.uptimeMillis();
    try {
      updateViewHierarchy();
      this.mNativeViewHierarchyOptimizer.onBatchComplete();
      this.mOperationsQueue.dispatchViewUpdates(paramInt, l, this.mLastCalculateLayoutTime);
      return;
    } finally {
      a.a(0L);
    } 
  }
  
  public void enableLayoutCalculationForRootNode(int paramInt) {
    this.mMeasuredRootNodes.add(Integer.valueOf(paramInt));
  }
  
  public void findSubviewIn(int paramInt, float paramFloat1, float paramFloat2, Callback paramCallback) {
    this.mOperationsQueue.enqueueFindTargetForTouch(paramInt, paramFloat1, paramFloat2, paramCallback);
  }
  
  public Map<String, Long> getProfiledBatchPerfCounters() {
    return this.mOperationsQueue.getProfiledBatchPerfCounters();
  }
  
  UIViewOperationQueue getUIViewOperationQueue() {
    return this.mOperationsQueue;
  }
  
  public void handleCreateView(ReactShadowNode paramReactShadowNode, int paramInt, ReactStylesDiffMap paramReactStylesDiffMap) {
    synchronized (this.uiImplementationThreadLock) {
      if (!paramReactShadowNode.isVirtual())
        this.mNativeViewHierarchyOptimizer.handleCreateView(paramReactShadowNode, paramReactShadowNode.getThemedContext(), paramReactStylesDiffMap); 
      return;
    } 
  }
  
  public void handleUpdateView(ReactShadowNode paramReactShadowNode, String paramString, ReactStylesDiffMap paramReactStylesDiffMap) {
    if (!paramReactShadowNode.isVirtual())
      this.mNativeViewHierarchyOptimizer.handleUpdateView(paramReactShadowNode, paramString, paramReactStylesDiffMap); 
  }
  
  public void manageChildren(int paramInt, ReadableArray paramReadableArray1, ReadableArray paramReadableArray2, ReadableArray paramReadableArray3, ReadableArray paramReadableArray4, ReadableArray paramReadableArray5) {
    int i;
    int j;
    int k;
    ReactShadowNode<ReactShadowNode> reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt);
    if (paramReadableArray1 == null) {
      i = 0;
    } else {
      i = paramReadableArray1.size();
    } 
    if (paramReadableArray3 == null) {
      k = 0;
    } else {
      k = paramReadableArray3.size();
    } 
    if (paramReadableArray5 == null) {
      j = 0;
    } else {
      j = paramReadableArray5.size();
    } 
    if (i == 0 || (paramReadableArray2 != null && i == paramReadableArray2.size())) {
      if (k == 0 || (paramReadableArray4 != null && k == paramReadableArray4.size())) {
        ViewAtIndex[] arrayOfViewAtIndex = new ViewAtIndex[i + k];
        int[] arrayOfInt3 = new int[i + j];
        int[] arrayOfInt4 = new int[arrayOfInt3.length];
        int[] arrayOfInt1 = new int[j];
        int[] arrayOfInt2 = arrayOfInt1;
        if (i > 0) {
          a.b(paramReadableArray1);
          a.b(paramReadableArray2);
          int m = 0;
          while (true) {
            arrayOfInt2 = arrayOfInt1;
            if (m < i) {
              int n = paramReadableArray1.getInt(m);
              int i1 = reactShadowNode.getChildAt(n).getReactTag();
              arrayOfViewAtIndex[m] = new ViewAtIndex(i1, paramReadableArray2.getInt(m));
              arrayOfInt3[m] = n;
              arrayOfInt4[m] = i1;
              m++;
              continue;
            } 
            break;
          } 
        } 
        if (k > 0) {
          a.b(paramReadableArray3);
          a.b(paramReadableArray4);
          int m;
          for (m = 0; m < k; m++)
            arrayOfViewAtIndex[i + m] = new ViewAtIndex(paramReadableArray3.getInt(m), paramReadableArray4.getInt(m)); 
        } 
        if (j > 0) {
          a.b(paramReadableArray5);
          for (k = 0; k < j; k++) {
            int m = paramReadableArray5.getInt(k);
            int n = reactShadowNode.getChildAt(m).getReactTag();
            int i1 = i + k;
            arrayOfInt3[i1] = m;
            arrayOfInt4[i1] = n;
            arrayOfInt2[k] = n;
          } 
        } 
        Arrays.sort(arrayOfViewAtIndex, ViewAtIndex.COMPARATOR);
        Arrays.sort(arrayOfInt3);
        i = arrayOfInt3.length - 1;
        j = -1;
        while (i >= 0) {
          if (arrayOfInt3[i] != j) {
            reactShadowNode.removeChildAt(arrayOfInt3[i]);
            j = arrayOfInt3[i];
            i--;
            continue;
          } 
          StringBuilder stringBuilder = new StringBuilder("Repeated indices in Removal list for view tag: ");
          stringBuilder.append(paramInt);
          throw new IllegalViewOperationException(stringBuilder.toString());
        } 
        paramInt = 0;
        while (paramInt < arrayOfViewAtIndex.length) {
          ViewAtIndex viewAtIndex = arrayOfViewAtIndex[paramInt];
          ReactShadowNode reactShadowNode1 = this.mShadowNodeRegistry.getNode(viewAtIndex.mTag);
          if (reactShadowNode1 != null) {
            reactShadowNode.addChildAt(reactShadowNode1, viewAtIndex.mIndex);
            paramInt++;
            continue;
          } 
          StringBuilder stringBuilder = new StringBuilder("Trying to add unknown view tag: ");
          stringBuilder.append(viewAtIndex.mTag);
          throw new IllegalViewOperationException(stringBuilder.toString());
        } 
        if (!reactShadowNode.isVirtual() && !reactShadowNode.isVirtualAnchor())
          this.mNativeViewHierarchyOptimizer.handleManageChildren(reactShadowNode, arrayOfInt3, arrayOfInt4, arrayOfViewAtIndex, arrayOfInt2); 
        for (paramInt = 0; paramInt < arrayOfInt2.length; paramInt++)
          removeShadowNode(this.mShadowNodeRegistry.getNode(arrayOfInt2[paramInt])); 
        return;
      } 
      throw new IllegalViewOperationException("Size of addChildTags != size of addAtIndices!");
    } 
    throw new IllegalViewOperationException("Size of moveFrom != size of moveTo!");
  }
  
  public void measure(int paramInt, Callback paramCallback) {
    this.mOperationsQueue.enqueueMeasure(paramInt, paramCallback);
  }
  
  public void measureInWindow(int paramInt, Callback paramCallback) {
    this.mOperationsQueue.enqueueMeasureInWindow(paramInt, paramCallback);
  }
  
  public void measureLayout(int paramInt1, int paramInt2, Callback paramCallback1, Callback paramCallback2) {
    try {
      measureLayout(paramInt1, paramInt2, this.mMeasureBuffer);
      paramCallback2.invoke(new Object[] { Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[0])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[1])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[2])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[3])) });
      return;
    } catch (IllegalViewOperationException illegalViewOperationException) {
      paramCallback1.invoke(new Object[] { illegalViewOperationException.getMessage() });
      return;
    } 
  }
  
  public void measureLayoutRelativeToParent(int paramInt, Callback paramCallback1, Callback paramCallback2) {
    try {
      measureLayoutRelativeToParent(paramInt, this.mMeasureBuffer);
      paramCallback2.invoke(new Object[] { Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[0])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[1])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[2])), Float.valueOf(PixelUtil.toDIPFromPixel(this.mMeasureBuffer[3])) });
      return;
    } catch (IllegalViewOperationException illegalViewOperationException) {
      paramCallback1.invoke(new Object[] { illegalViewOperationException.getMessage() });
      return;
    } 
  }
  
  public void onHostDestroy() {}
  
  public void onHostPause() {
    this.mOperationsQueue.pauseFrameCallback();
  }
  
  public void onHostResume() {
    this.mOperationsQueue.resumeFrameCallback();
  }
  
  public void prependUIBlock(UIBlock paramUIBlock) {
    this.mOperationsQueue.prependUIBlock(paramUIBlock);
  }
  
  public void profileNextBatch() {
    this.mOperationsQueue.profileNextBatch();
  }
  
  public void registerAnimation(Animation paramAnimation) {
    this.mOperationsQueue.enqueueRegisterAnimation(paramAnimation);
  }
  
  public <T extends SizeMonitoringFrameLayout & MeasureSpecProvider> void registerRootView(T paramT, int paramInt, ThemedReactContext paramThemedReactContext) {
    synchronized (this.uiImplementationThreadLock) {
      ReactShadowNode reactShadowNode = createRootShadowNode();
      reactShadowNode.setReactTag(paramInt);
      reactShadowNode.setThemedContext(paramThemedReactContext);
      updateRootView(reactShadowNode, ((MeasureSpecProvider)paramT).getWidthMeasureSpec(), ((MeasureSpecProvider)paramT).getHeightMeasureSpec());
      this.mShadowNodeRegistry.addRootNode(reactShadowNode);
      this.mOperationsQueue.addRootView(paramInt, (SizeMonitoringFrameLayout)paramT, paramThemedReactContext);
      return;
    } 
  }
  
  public void removeAnimation(int paramInt1, int paramInt2) {
    assertViewExists(paramInt1, "removeAnimation");
    this.mOperationsQueue.enqueueRemoveAnimation(paramInt2);
  }
  
  public void removeLayoutUpdateListener() {
    this.mLayoutUpdateListener = null;
  }
  
  public void removeRootShadowNode(int paramInt) {
    synchronized (this.uiImplementationThreadLock) {
      this.mShadowNodeRegistry.removeRootNode(paramInt);
      return;
    } 
  }
  
  public void removeRootView(int paramInt) {
    removeRootShadowNode(paramInt);
    this.mOperationsQueue.enqueueRemoveRootView(paramInt);
  }
  
  protected final void removeShadowNode(ReactShadowNode paramReactShadowNode) {
    removeShadowNodeRecursive(paramReactShadowNode);
    paramReactShadowNode.dispose();
  }
  
  public void removeSubviewsFromContainerWithID(int paramInt) {
    ReactShadowNode reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt);
    if (reactShadowNode != null) {
      WritableArray writableArray = Arguments.createArray();
      for (int i = 0; i < reactShadowNode.getChildCount(); i++)
        writableArray.pushInt(i); 
      manageChildren(paramInt, null, null, null, null, (ReadableArray)writableArray);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Trying to remove subviews of an unknown view tag: ");
    stringBuilder.append(paramInt);
    IllegalViewOperationException illegalViewOperationException = new IllegalViewOperationException(stringBuilder.toString());
    throw illegalViewOperationException;
  }
  
  public void replaceExistingNonRootView(int paramInt1, int paramInt2) {
    if (!this.mShadowNodeRegistry.isRootNode(paramInt1) && !this.mShadowNodeRegistry.isRootNode(paramInt2)) {
      ReactShadowNode<Object> reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt1);
      if (reactShadowNode != null) {
        ReactShadowNode<ReactShadowNode<Object>> reactShadowNode1 = (ReactShadowNode<ReactShadowNode<Object>>)reactShadowNode.getParent();
        if (reactShadowNode1 != null) {
          paramInt1 = reactShadowNode1.indexOf(reactShadowNode);
          if (paramInt1 >= 0) {
            WritableArray writableArray1 = Arguments.createArray();
            writableArray1.pushInt(paramInt2);
            WritableArray writableArray2 = Arguments.createArray();
            writableArray2.pushInt(paramInt1);
            WritableArray writableArray3 = Arguments.createArray();
            writableArray3.pushInt(paramInt1);
            manageChildren(reactShadowNode1.getReactTag(), null, null, (ReadableArray)writableArray1, (ReadableArray)writableArray2, (ReadableArray)writableArray3);
            return;
          } 
          throw new IllegalStateException("Didn't find child tag in parent");
        } 
        StringBuilder stringBuilder1 = new StringBuilder("Node is not attached to a parent: ");
        stringBuilder1.append(paramInt1);
        throw new IllegalViewOperationException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder("Trying to replace unknown view tag: ");
      stringBuilder.append(paramInt1);
      throw new IllegalViewOperationException(stringBuilder.toString());
    } 
    throw new IllegalViewOperationException("Trying to add or replace a root tag!");
  }
  
  public int resolveRootTagFromReactTag(int paramInt) {
    if (this.mShadowNodeRegistry.isRootNode(paramInt))
      return paramInt; 
    ReactShadowNode reactShadowNode = resolveShadowNode(paramInt);
    if (reactShadowNode != null)
      return reactShadowNode.getRootNode().getReactTag(); 
    StringBuilder stringBuilder = new StringBuilder("Warning : attempted to resolve a non-existent react shadow node. reactTag=");
    stringBuilder.append(paramInt);
    a.b("ReactNative", stringBuilder.toString());
    return 0;
  }
  
  public final ReactShadowNode resolveShadowNode(int paramInt) {
    return this.mShadowNodeRegistry.getNode(paramInt);
  }
  
  protected final ViewManager resolveViewManager(String paramString) {
    return this.mViewManagers.get(paramString);
  }
  
  public void sendAccessibilityEvent(int paramInt1, int paramInt2) {
    this.mOperationsQueue.enqueueSendAccessibilityEvent(paramInt1, paramInt2);
  }
  
  public void setChildren(int paramInt, ReadableArray paramReadableArray) {
    synchronized (this.uiImplementationThreadLock) {
      StringBuilder stringBuilder;
      ReactShadowNode<ReactShadowNode> reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt);
      paramInt = 0;
      while (paramInt < paramReadableArray.size()) {
        ReactShadowNode reactShadowNode1 = this.mShadowNodeRegistry.getNode(paramReadableArray.getInt(paramInt));
        if (reactShadowNode1 != null) {
          reactShadowNode.addChildAt(reactShadowNode1, paramInt);
          paramInt++;
          continue;
        } 
        stringBuilder = new StringBuilder("Trying to add unknown view tag: ");
        stringBuilder.append(paramReadableArray.getInt(paramInt));
        throw new IllegalViewOperationException(stringBuilder.toString());
      } 
      if (!stringBuilder.isVirtual() && !stringBuilder.isVirtualAnchor())
        this.mNativeViewHierarchyOptimizer.handleSetChildren((ReactShadowNode)stringBuilder, paramReadableArray); 
      return;
    } 
  }
  
  public void setJSResponder(int paramInt, boolean paramBoolean) {
    assertViewExists(paramInt, "setJSResponder");
    ReactShadowNode<ReactShadowNode> reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt);
    while (true) {
      if (reactShadowNode.isVirtual() || reactShadowNode.isLayoutOnly()) {
        reactShadowNode = reactShadowNode.getParent();
        continue;
      } 
      this.mOperationsQueue.enqueueSetJSResponder(reactShadowNode.getReactTag(), paramInt, paramBoolean);
      return;
    } 
  }
  
  public void setLayoutAnimationEnabledExperimental(boolean paramBoolean) {
    this.mOperationsQueue.enqueueSetLayoutAnimationEnabled(paramBoolean);
  }
  
  public void setLayoutUpdateListener(LayoutUpdateListener paramLayoutUpdateListener) {
    this.mLayoutUpdateListener = paramLayoutUpdateListener;
  }
  
  public void setViewHierarchyUpdateDebugListener(NotThreadSafeViewHierarchyUpdateDebugListener paramNotThreadSafeViewHierarchyUpdateDebugListener) {
    this.mOperationsQueue.setViewHierarchyUpdateDebugListener(paramNotThreadSafeViewHierarchyUpdateDebugListener);
  }
  
  public void setViewLocalData(int paramInt, Object paramObject) {
    ReactShadowNode reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt);
    if (reactShadowNode == null) {
      paramObject = new StringBuilder("Attempt to set local data for view with unknown tag: ");
      paramObject.append(paramInt);
      a.b("ReactNative", paramObject.toString());
      return;
    } 
    reactShadowNode.setLocalData(paramObject);
    dispatchViewUpdatesIfNeeded();
  }
  
  public void showPopupMenu(int paramInt, ReadableArray paramReadableArray, Callback paramCallback1, Callback paramCallback2) {
    assertViewExists(paramInt, "showPopupMenu");
    this.mOperationsQueue.enqueueShowPopupMenu(paramInt, paramReadableArray, paramCallback1, paramCallback2);
  }
  
  public void synchronouslyUpdateViewOnUIThread(int paramInt, ReactStylesDiffMap paramReactStylesDiffMap) {
    UiThreadUtil.assertOnUiThread();
    this.mOperationsQueue.getNativeViewHierarchyManager().updateProperties(paramInt, paramReactStylesDiffMap);
  }
  
  public void updateNodeSize(int paramInt1, int paramInt2, int paramInt3) {
    StringBuilder stringBuilder;
    ReactShadowNode reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt1);
    if (reactShadowNode == null) {
      stringBuilder = new StringBuilder("Tried to update size of non-existent tag: ");
      stringBuilder.append(paramInt1);
      a.b("ReactNative", stringBuilder.toString());
      return;
    } 
    stringBuilder.setStyleWidth(paramInt2);
    stringBuilder.setStyleHeight(paramInt3);
    dispatchViewUpdatesIfNeeded();
  }
  
  public void updateRootView(int paramInt1, int paramInt2, int paramInt3) {
    StringBuilder stringBuilder;
    ReactShadowNode reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt1);
    if (reactShadowNode == null) {
      stringBuilder = new StringBuilder("Tried to update non-existent root tag: ");
      stringBuilder.append(paramInt1);
      a.b("ReactNative", stringBuilder.toString());
      return;
    } 
    updateRootView((ReactShadowNode)stringBuilder, paramInt2, paramInt3);
  }
  
  public void updateRootView(ReactShadowNode paramReactShadowNode, int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.getMode(paramInt1);
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    if (i != Integer.MIN_VALUE) {
      if (i != 0) {
        if (i == 1073741824)
          paramReactShadowNode.setStyleWidth(paramInt1); 
      } else {
        paramReactShadowNode.setStyleWidthAuto();
      } 
    } else {
      paramReactShadowNode.setStyleMaxWidth(paramInt1);
    } 
    paramInt1 = View.MeasureSpec.getMode(paramInt2);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    if (paramInt1 != Integer.MIN_VALUE) {
      if (paramInt1 != 0) {
        if (paramInt1 != 1073741824)
          return; 
        paramReactShadowNode.setStyleHeight(paramInt2);
        return;
      } 
      paramReactShadowNode.setStyleHeightAuto();
      return;
    } 
    paramReactShadowNode.setStyleMaxHeight(paramInt2);
  }
  
  public void updateView(int paramInt, String paramString, ReadableMap paramReadableMap) {
    StringBuilder stringBuilder1;
    if (this.mViewManagers.get(paramString) != null) {
      ReactShadowNode reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt);
      if (reactShadowNode != null) {
        if (paramReadableMap != null) {
          ReactStylesDiffMap reactStylesDiffMap = new ReactStylesDiffMap(paramReadableMap);
          reactShadowNode.updateProperties(reactStylesDiffMap);
          handleUpdateView(reactShadowNode, paramString, reactStylesDiffMap);
        } 
        return;
      } 
      stringBuilder1 = new StringBuilder("Trying to update non-existent view with tag ");
      stringBuilder1.append(paramInt);
      throw new IllegalViewOperationException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder2 = new StringBuilder("Got unknown view type: ");
    stringBuilder2.append((String)stringBuilder1);
    throw new IllegalViewOperationException(stringBuilder2.toString());
  }
  
  public void updateViewHierarchy() {
    a.a(0L, "UIImplementation.updateViewHierarchy");
    int i = 0;
    try {
      while (i < this.mShadowNodeRegistry.getRootNodeCount()) {
        int j = this.mShadowNodeRegistry.getRootTag(i);
        ReactShadowNode reactShadowNode = this.mShadowNodeRegistry.getNode(j);
        if (this.mMeasuredRootNodes.contains(Integer.valueOf(j))) {
          reactShadowNode.getReactTag();
          try {
            notifyOnBeforeLayoutRecursive(reactShadowNode);
            a.a(0L);
            calculateRootLayout(reactShadowNode);
            reactShadowNode.getReactTag();
          } finally {
            a.a(0L);
          } 
        } 
        i++;
      } 
      return;
    } finally {
      a.a(0L);
    } 
  }
  
  public void viewIsDescendantOf(int paramInt1, int paramInt2, Callback paramCallback) {
    ReactShadowNode<ReactShadowNode> reactShadowNode = this.mShadowNodeRegistry.getNode(paramInt1);
    ReactShadowNode reactShadowNode1 = this.mShadowNodeRegistry.getNode(paramInt2);
    if (reactShadowNode == null || reactShadowNode1 == null) {
      paramCallback.invoke(new Object[] { Boolean.valueOf(false) });
      return;
    } 
    paramCallback.invoke(new Object[] { Boolean.valueOf(reactShadowNode.isDescendantOf(reactShadowNode1)) });
  }
  
  public static interface LayoutUpdateListener {
    void onLayoutUpdated(ReactShadowNode param1ReactShadowNode);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\UIImplementation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */