package com.facebook.react.flat;

import android.content.Context;
import com.facebook.i.a.a;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.modules.fresco.FrescoModule;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.UIImplementation;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.uimanager.ViewManagerRegistry;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.yoga.YogaDirection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FlatUIImplementation extends UIImplementation {
  private static final Map<String, Class<? extends ViewManager>> flatManagerClassMap;
  
  private final boolean mMemoryImprovementEnabled;
  
  private final MoveProxy mMoveProxy = new MoveProxy();
  
  private RCTImageViewManager mRCTImageViewManager;
  
  private final ReactApplicationContext mReactContext;
  
  private final StateBuilder mStateBuilder;
  
  static {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    flatManagerClassMap = (Map)hashMap;
    hashMap.put("RCTView", RCTViewManager.class);
    flatManagerClassMap.put("RCTText", RCTTextManager.class);
    flatManagerClassMap.put("RCTRawText", RCTRawTextManager.class);
    flatManagerClassMap.put("RCTVirtualText", RCTVirtualTextManager.class);
    flatManagerClassMap.put("RCTTextInlineImage", RCTTextInlineImageManager.class);
    flatManagerClassMap.put("RCTImageView", RCTImageViewManager.class);
    flatManagerClassMap.put("AndroidTextInput", RCTTextInputManager.class);
    flatManagerClassMap.put("AndroidViewPager", RCTViewPagerManager.class);
    flatManagerClassMap.put("ARTSurfaceView", FlatARTSurfaceViewManager.class);
    flatManagerClassMap.put("RCTModalHostView", RCTModalHostManager.class);
  }
  
  private FlatUIImplementation(ReactApplicationContext paramReactApplicationContext, RCTImageViewManager paramRCTImageViewManager, ViewManagerRegistry paramViewManagerRegistry, FlatUIViewOperationQueue paramFlatUIViewOperationQueue, EventDispatcher paramEventDispatcher, boolean paramBoolean) {
    super(paramReactApplicationContext, paramViewManagerRegistry, paramFlatUIViewOperationQueue, paramEventDispatcher);
    this.mReactContext = paramReactApplicationContext;
    this.mRCTImageViewManager = paramRCTImageViewManager;
    this.mStateBuilder = new StateBuilder(paramFlatUIViewOperationQueue);
    this.mMemoryImprovementEnabled = paramBoolean;
  }
  
  private static void addChildAt(ReactShadowNode paramReactShadowNode1, ReactShadowNode paramReactShadowNode2, int paramInt1, int paramInt2) {
    if (paramInt1 > paramInt2) {
      paramReactShadowNode1.addChildAt(paramReactShadowNode2, paramInt1);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Invariant failure, needs sorting! ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" <= ");
    stringBuilder.append(paramInt2);
    throw new RuntimeException(stringBuilder.toString());
  }
  
  private void addChildren(ReactShadowNode paramReactShadowNode, ReadableArray paramReadableArray1, ReadableArray paramReadableArray2) {
    int k;
    int i = this.mMoveProxy.size();
    int n = 0;
    if (i == 0) {
      k = Integer.MAX_VALUE;
      i = Integer.MAX_VALUE;
    } else {
      i = this.mMoveProxy.getMoveTo(0);
      k = 0;
    } 
    int j = -1;
    if (paramReadableArray2 == null) {
      n = Integer.MAX_VALUE;
      boolean bool = false;
    } else {
      int i2 = paramReadableArray2.size();
      int i1 = paramReadableArray2.getInt(0);
      while (true) {
        while (true) {
          if (i1 < i) {
            addChildAt(paramReactShadowNode, resolveShadowNode(paramReadableArray1.getInt(n)), i1, j);
            if (++n == i2) {
              j = i1;
            } else {
              int i3 = paramReadableArray2.getInt(n);
              j = i1;
              i1 = i3;
              continue;
            } 
          } else {
            break;
          } 
          i1 = Integer.MAX_VALUE;
        } 
        if (i < i1) {
          addChildAt(paramReactShadowNode, this.mMoveProxy.getChildMoveTo(k), i, j);
          if (++k == this.mMoveProxy.size()) {
            j = i;
            i = Integer.MAX_VALUE;
            continue;
          } 
          int i3 = this.mMoveProxy.getMoveTo(k);
          j = i;
          i = i3;
          continue;
        } 
        break;
      } 
      return;
    } 
    int m = Integer.MAX_VALUE;
    continue;
  }
  
  private static Map<String, ViewManager> buildViewManagerMap(List<ViewManager> paramList) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (ViewManager viewManager : paramList)
      hashMap.put(viewManager.getName(), viewManager); 
    Iterator<Map.Entry> iterator = flatManagerClassMap.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry entry = iterator.next();
      String str = (String)entry.getKey();
      ViewManager viewManager = (ViewManager)hashMap.get(str);
      if (viewManager != null) {
        Class<?> clazz = (Class)entry.getValue();
        if (viewManager.getClass() != clazz)
          try {
            hashMap.put(str, clazz.newInstance());
          } catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder("Unable to access flat class for ");
            stringBuilder.append(str);
            throw new RuntimeException(stringBuilder.toString(), illegalAccessException);
          } catch (InstantiationException instantiationException) {
            StringBuilder stringBuilder = new StringBuilder("Unable to instantiate flat class for ");
            stringBuilder.append(str);
            throw new RuntimeException(stringBuilder.toString(), instantiationException);
          }  
      } 
    } 
    return (Map<String, ViewManager>)instantiationException;
  }
  
  public static FlatUIImplementation createInstance(ReactApplicationContext paramReactApplicationContext, List<ViewManager> paramList, EventDispatcher paramEventDispatcher, boolean paramBoolean, int paramInt) {
    Map<String, ViewManager> map = buildViewManagerMap(paramList);
    RCTImageViewManager rCTImageViewManager = (RCTImageViewManager)map.get("RCTImageView");
    if (rCTImageViewManager != null) {
      Object object = rCTImageViewManager.getCallerContext();
      if (object != null)
        RCTImageView.setCallerContext(object); 
    } 
    DraweeRequestHelper.setResources(paramReactApplicationContext.getResources());
    TypefaceCache.setAssetManager(paramReactApplicationContext.getAssets());
    ViewManagerRegistry viewManagerRegistry = new ViewManagerRegistry(map);
    return new FlatUIImplementation(paramReactApplicationContext, rCTImageViewManager, viewManagerRegistry, new FlatUIViewOperationQueue(paramReactApplicationContext, new FlatNativeViewHierarchyManager(viewManagerRegistry), paramInt), paramEventDispatcher, paramBoolean);
  }
  
  private void dropNativeViews(ReactShadowNode paramReactShadowNode1, ReactShadowNode paramReactShadowNode2) {
    FlatShadowNode flatShadowNode;
    if (paramReactShadowNode1 instanceof FlatShadowNode) {
      FlatShadowNode flatShadowNode1 = (FlatShadowNode)paramReactShadowNode1;
      if (flatShadowNode1.mountsToView() && flatShadowNode1.isBackingViewCreated()) {
        int k;
        byte b = -1;
        while (true) {
          k = b;
          if (paramReactShadowNode2 != null) {
            if (paramReactShadowNode2 instanceof FlatShadowNode) {
              flatShadowNode = (FlatShadowNode)paramReactShadowNode2;
              if (flatShadowNode.mountsToView() && flatShadowNode.isBackingViewCreated() && flatShadowNode.getParent() != null) {
                k = flatShadowNode.getReactTag();
                break;
              } 
            } 
            paramReactShadowNode2 = paramReactShadowNode2.getParent();
            continue;
          } 
          break;
        } 
        this.mStateBuilder.dropView(flatShadowNode1, k);
        return;
      } 
    } 
    int i = 0;
    int j = flatShadowNode.getChildCount();
    while (i != j) {
      dropNativeViews(flatShadowNode.getChildAt(i), (ReactShadowNode)flatShadowNode);
      i++;
    } 
  }
  
  private void ensureMountsToViewAndBackingViewIsCreated(int paramInt) {
    FlatShadowNode flatShadowNode = (FlatShadowNode)resolveShadowNode(paramInt);
    if (flatShadowNode.isBackingViewCreated())
      return; 
    flatShadowNode.forceMountToView();
    this.mStateBuilder.ensureBackingViewIsCreated(flatShadowNode);
  }
  
  private void measureHelper(int paramInt, boolean paramBoolean, Callback paramCallback) {
    FlatShadowNode flatShadowNode2 = (FlatShadowNode)resolveShadowNode(paramInt);
    FlatShadowNode flatShadowNode1 = flatShadowNode2;
    if (flatShadowNode2.mountsToView()) {
      this.mStateBuilder.ensureBackingViewIsCreated(flatShadowNode2);
      if (paramBoolean) {
        super.measureInWindow(paramInt, paramCallback);
        return;
      } 
      super.measure(paramInt, paramCallback);
      return;
    } 
    while (flatShadowNode1 != null && flatShadowNode1.isVirtual())
      flatShadowNode1 = (FlatShadowNode)flatShadowNode1.getParent(); 
    if (flatShadowNode1 == null)
      return; 
    float f5 = flatShadowNode1.getLayoutWidth();
    float f6 = flatShadowNode1.getLayoutHeight();
    boolean bool = flatShadowNode1.mountsToView();
    float f1 = 0.0F;
    if (bool) {
      f2 = flatShadowNode1.getLayoutX();
    } else {
      f2 = 0.0F;
    } 
    flatShadowNode2 = flatShadowNode1;
    float f3 = f2;
    if (bool) {
      f1 = flatShadowNode1.getLayoutY();
      f3 = f2;
      flatShadowNode2 = flatShadowNode1;
    } 
    while (!flatShadowNode2.mountsToView()) {
      float f = f1;
      f2 = f3;
      if (!flatShadowNode2.isVirtual()) {
        f2 = f3 + flatShadowNode2.getLayoutX();
        f = f1 + flatShadowNode2.getLayoutY();
      } 
      flatShadowNode2 = (FlatShadowNode)a.a(flatShadowNode2.getParent());
      f1 = f;
      f3 = f2;
    } 
    float f2 = flatShadowNode2.getLayoutWidth();
    float f4 = flatShadowNode2.getLayoutHeight();
    this.mStateBuilder.getOperationsQueue().enqueueMeasureVirtualView(flatShadowNode2.getReactTag(), f3 / f2, f1 / f4, f5 / f2, f6 / f4, paramBoolean, paramCallback);
  }
  
  private void moveChild(ReactShadowNode paramReactShadowNode, int paramInt) {
    this.mMoveProxy.setChildMoveFrom(paramInt, paramReactShadowNode);
  }
  
  private void removeChild(ReactShadowNode paramReactShadowNode1, ReactShadowNode paramReactShadowNode2) {
    dropNativeViews(paramReactShadowNode1, paramReactShadowNode2);
    removeShadowNode(paramReactShadowNode1);
  }
  
  private static ReactShadowNode removeChildAt(ReactShadowNode paramReactShadowNode, int paramInt1, int paramInt2) {
    if (paramInt1 < paramInt2)
      return paramReactShadowNode.removeChildAt(paramInt1); 
    StringBuilder stringBuilder = new StringBuilder("Invariant failure, needs sorting! ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" >= ");
    stringBuilder.append(paramInt2);
    throw new RuntimeException(stringBuilder.toString());
  }
  
  private void removeChildren(ReactShadowNode paramReactShadowNode, ReadableArray paramReadableArray1, ReadableArray paramReadableArray2, ReadableArray paramReadableArray3) {
    int i;
    int j;
    this.mMoveProxy.setup(paramReadableArray1, paramReadableArray2);
    int n = this.mMoveProxy.size() - 1;
    if (n == -1) {
      j = -1;
    } else {
      j = this.mMoveProxy.getMoveFrom(n);
    } 
    int k = 0;
    if (paramReadableArray3 == null) {
      i = 0;
    } else {
      i = paramReadableArray3.size();
    } 
    int[] arrayOfInt = new int[i];
    if (i > 0) {
      a.b(paramReadableArray3);
      while (k < i) {
        arrayOfInt[k] = paramReadableArray3.getInt(k);
        k++;
      } 
    } 
    Arrays.sort(arrayOfInt);
    int m = Integer.MAX_VALUE;
    if (paramReadableArray3 == null) {
      k = -1;
      i = -1;
    } else {
      k = arrayOfInt.length - 1;
      i = arrayOfInt[k];
    } 
    while (true) {
      int i1 = i;
      if (j > i1) {
        moveChild(removeChildAt(paramReactShadowNode, j, m), n);
        if (--n == -1) {
          i = -1;
        } else {
          i = this.mMoveProxy.getMoveFrom(n);
        } 
        m = j;
        j = i;
        i = i1;
        continue;
      } 
      if (i1 > j) {
        removeChild(removeChildAt(paramReactShadowNode, i1, m), paramReactShadowNode);
        if (--k == -1) {
          i = -1;
        } else {
          i = arrayOfInt[k];
        } 
        m = i1;
        continue;
      } 
      break;
    } 
  }
  
  public void addAnimation(int paramInt1, int paramInt2, Callback paramCallback) {
    ensureMountsToViewAndBackingViewIsCreated(paramInt1);
    super.addAnimation(paramInt1, paramInt2, paramCallback);
  }
  
  public void applyUpdatesRecursive(ReactShadowNode paramReactShadowNode, float paramFloat1, float paramFloat2) {
    this.mStateBuilder.applyUpdates((FlatRootShadowNode)paramReactShadowNode);
  }
  
  public ReactShadowNode createRootShadowNode() {
    if (this.mRCTImageViewManager != null) {
      this.mReactContext.getNativeModule(FrescoModule.class);
      DraweeRequestHelper.setDraweeControllerBuilder(this.mRCTImageViewManager.getDraweeControllerBuilder());
      this.mRCTImageViewManager = null;
    } 
    FlatRootShadowNode flatRootShadowNode = new FlatRootShadowNode();
    if (I18nUtil.getInstance().isRTL((Context)this.mReactContext))
      flatRootShadowNode.setLayoutDirection(YogaDirection.RTL); 
    return (ReactShadowNode)flatRootShadowNode;
  }
  
  public ReactShadowNode createShadowNode(String paramString) {
    NativeViewWrapper nativeViewWrapper;
    ReactShadowNode reactShadowNode2 = super.createShadowNode(paramString);
    ReactShadowNode reactShadowNode1 = reactShadowNode2;
    if (!(reactShadowNode2 instanceof FlatShadowNode)) {
      if (reactShadowNode2.isVirtual())
        return reactShadowNode2; 
      nativeViewWrapper = new NativeViewWrapper(resolveViewManager(paramString));
    } 
    return (ReactShadowNode)nativeViewWrapper;
  }
  
  public void dispatchViewManagerCommand(int paramInt1, int paramInt2, ReadableArray paramReadableArray) {
    ensureMountsToViewAndBackingViewIsCreated(paramInt1);
    this.mStateBuilder.enqueueViewManagerCommand(paramInt1, paramInt2, paramReadableArray);
  }
  
  public void findSubviewIn(int paramInt, float paramFloat1, float paramFloat2, Callback paramCallback) {
    ensureMountsToViewAndBackingViewIsCreated(paramInt);
    super.findSubviewIn(paramInt, paramFloat1, paramFloat2, paramCallback);
  }
  
  public void handleCreateView(ReactShadowNode paramReactShadowNode, int paramInt, ReactStylesDiffMap paramReactStylesDiffMap) {
    FlatShadowNode flatShadowNode;
    if (paramReactShadowNode instanceof FlatShadowNode) {
      flatShadowNode = (FlatShadowNode)paramReactShadowNode;
      if (paramReactStylesDiffMap != null)
        flatShadowNode.handleUpdateProperties(paramReactStylesDiffMap); 
      if (flatShadowNode.mountsToView())
        this.mStateBuilder.enqueueCreateOrUpdateView(flatShadowNode, paramReactStylesDiffMap); 
      return;
    } 
    super.handleCreateView((ReactShadowNode)flatShadowNode, paramInt, paramReactStylesDiffMap);
  }
  
  public void handleUpdateView(ReactShadowNode paramReactShadowNode, String paramString, ReactStylesDiffMap paramReactStylesDiffMap) {
    FlatShadowNode flatShadowNode;
    if (paramReactShadowNode instanceof FlatShadowNode) {
      flatShadowNode = (FlatShadowNode)paramReactShadowNode;
      flatShadowNode.handleUpdateProperties(paramReactStylesDiffMap);
      if (flatShadowNode.mountsToView())
        this.mStateBuilder.enqueueCreateOrUpdateView(flatShadowNode, paramReactStylesDiffMap); 
      return;
    } 
    super.handleUpdateView((ReactShadowNode)flatShadowNode, paramString, paramReactStylesDiffMap);
  }
  
  public void manageChildren(int paramInt, ReadableArray paramReadableArray1, ReadableArray paramReadableArray2, ReadableArray paramReadableArray3, ReadableArray paramReadableArray4, ReadableArray paramReadableArray5) {
    ReactShadowNode reactShadowNode = resolveShadowNode(paramInt);
    removeChildren(reactShadowNode, paramReadableArray1, paramReadableArray2, paramReadableArray5);
    addChildren(reactShadowNode, paramReadableArray3, paramReadableArray4);
  }
  
  public void measure(int paramInt, Callback paramCallback) {
    measureHelper(paramInt, false, paramCallback);
  }
  
  public void measureInWindow(int paramInt, Callback paramCallback) {
    measureHelper(paramInt, true, paramCallback);
  }
  
  public void removeRootView(int paramInt) {
    if (this.mMemoryImprovementEnabled)
      removeRootShadowNode(paramInt); 
    this.mStateBuilder.removeRootView(paramInt);
  }
  
  public void sendAccessibilityEvent(int paramInt1, int paramInt2) {
    ensureMountsToViewAndBackingViewIsCreated(paramInt1);
    super.sendAccessibilityEvent(paramInt1, paramInt2);
  }
  
  public void setChildren(int paramInt, ReadableArray paramReadableArray) {
    ReactShadowNode reactShadowNode = resolveShadowNode(paramInt);
    for (paramInt = 0; paramInt < paramReadableArray.size(); paramInt++)
      addChildAt(reactShadowNode, resolveShadowNode(paramReadableArray.getInt(paramInt)), paramInt, paramInt - 1); 
  }
  
  public void setJSResponder(int paramInt, boolean paramBoolean) {
    ReactShadowNode reactShadowNode;
    for (reactShadowNode = resolveShadowNode(paramInt); reactShadowNode.isVirtual(); reactShadowNode = reactShadowNode.getParent());
    int i = reactShadowNode.getReactTag();
    while (reactShadowNode instanceof FlatShadowNode && !((FlatShadowNode)reactShadowNode).mountsToView())
      reactShadowNode = reactShadowNode.getParent(); 
    FlatUIViewOperationQueue flatUIViewOperationQueue = this.mStateBuilder.getOperationsQueue();
    if (reactShadowNode != null)
      i = reactShadowNode.getReactTag(); 
    flatUIViewOperationQueue.enqueueSetJSResponder(i, paramInt, paramBoolean);
  }
  
  public void showPopupMenu(int paramInt, ReadableArray paramReadableArray, Callback paramCallback1, Callback paramCallback2) {
    ensureMountsToViewAndBackingViewIsCreated(paramInt);
    super.showPopupMenu(paramInt, paramReadableArray, paramCallback1, paramCallback2);
  }
  
  public void updateViewHierarchy() {
    super.updateViewHierarchy();
    this.mStateBuilder.afterUpdateViewHierarchy(this.mEventDispatcher);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatUIImplementation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */