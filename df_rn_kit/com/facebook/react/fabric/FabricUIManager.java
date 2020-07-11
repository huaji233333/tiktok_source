package com.facebook.react.fabric;

import android.content.Context;
import android.view.View;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.ReactRootViewTagGenerator;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.ViewManagerRegistry;
import com.facebook.react.uimanager.common.MeasureSpecProvider;
import com.facebook.react.uimanager.common.SizeMonitoringFrameLayout;
import java.util.ArrayList;
import java.util.List;

public class FabricUIManager implements UIManager {
  private static final String TAG = FabricUIManager.class.toString();
  
  private final ReactApplicationContext mReactApplicationContext;
  
  private final RootShadowNodeRegistry mRootShadowNodeRegistry = new RootShadowNodeRegistry();
  
  private final UIViewOperationQueue mUIViewOperationQueue;
  
  private final ViewManagerRegistry mViewManagerRegistry;
  
  public FabricUIManager(ReactApplicationContext paramReactApplicationContext, ViewManagerRegistry paramViewManagerRegistry) {
    DisplayMetricsHolder.initDisplayMetricsIfNotInitialized((Context)paramReactApplicationContext);
    this.mReactApplicationContext = paramReactApplicationContext;
    this.mViewManagerRegistry = paramViewManagerRegistry;
    this.mUIViewOperationQueue = new UIViewOperationQueue(paramReactApplicationContext, new NativeViewHierarchyManager(paramViewManagerRegistry), 0);
  }
  
  private void applyUpdatesRecursive(ReactShadowNode paramReactShadowNode, float paramFloat1, float paramFloat2) {
    if (!paramReactShadowNode.hasUpdates())
      return; 
    if (!paramReactShadowNode.isVirtualAnchor()) {
      int j;
      for (j = 0; j < paramReactShadowNode.getChildCount(); j++)
        applyUpdatesRecursive(paramReactShadowNode.getChildAt(j), paramReactShadowNode.getLayoutX() + paramFloat1, paramReactShadowNode.getLayoutY() + paramFloat2); 
    } 
    int i = paramReactShadowNode.getReactTag();
    if (this.mRootShadowNodeRegistry.getNode(i) == null)
      paramReactShadowNode.dispatchUpdates(paramFloat1, paramFloat2, this.mUIViewOperationQueue, null); 
    paramReactShadowNode.markUpdateSeen();
  }
  
  private void assertReactShadowNodeCopy(ReactShadowNode paramReactShadowNode1, ReactShadowNode paramReactShadowNode2) {
    boolean bool = paramReactShadowNode1.getClass().equals(paramReactShadowNode2.getClass());
    StringBuilder stringBuilder = new StringBuilder("Found ");
    stringBuilder.append(paramReactShadowNode2.getClass());
    stringBuilder.append(" class when expecting: ");
    stringBuilder.append(paramReactShadowNode1.getClass());
    stringBuilder.append(". Check that ");
    stringBuilder.append(paramReactShadowNode1.getClass());
    stringBuilder.append(" implements the mutableCopy() method correctly.");
    a.a(bool, stringBuilder.toString());
  }
  
  private void calculateRootLayout(ReactShadowNode paramReactShadowNode) {
    paramReactShadowNode.calculateLayout();
  }
  
  private ReactShadowNode createRootShadowNode(int paramInt, ThemedReactContext paramThemedReactContext) {
    ReactShadowNodeImpl reactShadowNodeImpl = new ReactShadowNodeImpl();
    I18nUtil.getInstance();
    reactShadowNodeImpl.setViewClassName("Root");
    reactShadowNodeImpl.setReactTag(paramInt);
    reactShadowNodeImpl.setThemedContext(paramThemedReactContext);
    return (ReactShadowNode)reactShadowNodeImpl;
  }
  
  private ReactShadowNode getRootNode(int paramInt) {
    return this.mRootShadowNodeRegistry.getNode(paramInt);
  }
  
  private void handleException(ReactShadowNode paramReactShadowNode, Throwable paramThrowable) {
    try {
      paramReactShadowNode.getThemedContext().handleException(new RuntimeException(paramThrowable));
      return;
    } catch (Exception exception) {
      throw new RuntimeException(exception.getMessage(), paramThrowable);
    } 
  }
  
  private void notifyOnBeforeLayoutRecursive(ReactShadowNode paramReactShadowNode) {
    if (!paramReactShadowNode.hasUpdates())
      return; 
    for (int i = 0; i < paramReactShadowNode.getChildCount(); i++)
      notifyOnBeforeLayoutRecursive(paramReactShadowNode.getChildAt(i)); 
    paramReactShadowNode.onBeforeLayout();
  }
  
  private ReactStylesDiffMap updateProps(ReactShadowNode paramReactShadowNode, ReadableNativeMap paramReadableNativeMap) {
    if (paramReadableNativeMap != null) {
      ReactStylesDiffMap reactStylesDiffMap = new ReactStylesDiffMap((ReadableMap)paramReadableNativeMap);
      paramReactShadowNode.updateProperties(reactStylesDiffMap);
      return reactStylesDiffMap;
    } 
    return null;
  }
  
  public <T extends SizeMonitoringFrameLayout & MeasureSpecProvider> int addRootView(T paramT) {
    int i = ReactRootViewTagGenerator.getNextRootViewTag();
    ThemedReactContext themedReactContext = new ThemedReactContext(this.mReactApplicationContext, paramT.getContext());
    ReactShadowNode reactShadowNode = createRootShadowNode(i, themedReactContext);
    MeasureSpecProvider measureSpecProvider = (MeasureSpecProvider)paramT;
    updateRootView(reactShadowNode, measureSpecProvider.getWidthMeasureSpec(), measureSpecProvider.getHeightMeasureSpec());
    this.mRootShadowNodeRegistry.addNode(reactShadowNode);
    this.mUIViewOperationQueue.addRootView(i, (SizeMonitoringFrameLayout)paramT, themedReactContext);
    return i;
  }
  
  public void appendChild(ReactShadowNode paramReactShadowNode1, ReactShadowNode paramReactShadowNode2) {
    try {
      int i = paramReactShadowNode1.getChildCount();
      paramReactShadowNode1.addChildAt(paramReactShadowNode2, i);
      return;
    } finally {
      paramReactShadowNode2 = null;
      handleException(paramReactShadowNode1, (Throwable)paramReactShadowNode2);
    } 
  }
  
  public void appendChildToSet(List<ReactShadowNode> paramList, ReactShadowNode paramReactShadowNode) {
    paramList.add(paramReactShadowNode);
  }
  
  public ReactShadowNode cloneNode(ReactShadowNode paramReactShadowNode) {
    try {
      return reactShadowNode;
    } finally {
      Exception exception = null;
      handleException(paramReactShadowNode, exception);
    } 
  }
  
  public ReactShadowNode cloneNodeWithNewChildren(ReactShadowNode paramReactShadowNode) {
    try {
      return reactShadowNode;
    } finally {
      Exception exception = null;
      handleException(paramReactShadowNode, exception);
    } 
  }
  
  public ReactShadowNode cloneNodeWithNewChildrenAndProps(ReactShadowNode paramReactShadowNode, ReadableNativeMap paramReadableNativeMap) {
    if (paramReadableNativeMap == null) {
      paramReadableNativeMap = null;
    } else {
      try {
        ReactStylesDiffMap reactStylesDiffMap = new ReactStylesDiffMap((ReadableMap)paramReadableNativeMap);
        return reactShadowNode1;
      } finally {
        paramReadableNativeMap = null;
        handleException(paramReactShadowNode, (Throwable)paramReadableNativeMap);
      } 
    } 
    ReactShadowNode reactShadowNode = paramReactShadowNode.mutableCopyWithNewChildrenAndProps((ReactStylesDiffMap)paramReadableNativeMap);
    assertReactShadowNodeCopy(paramReactShadowNode, reactShadowNode);
    return reactShadowNode;
  }
  
  public ReactShadowNode cloneNodeWithNewProps(ReactShadowNode paramReactShadowNode, ReadableNativeMap paramReadableNativeMap) {
    if (paramReadableNativeMap == null) {
      paramReadableNativeMap = null;
    } else {
      try {
        ReactStylesDiffMap reactStylesDiffMap = new ReactStylesDiffMap((ReadableMap)paramReadableNativeMap);
        return reactShadowNode1;
      } finally {
        paramReadableNativeMap = null;
        handleException(paramReactShadowNode, (Throwable)paramReadableNativeMap);
      } 
    } 
    ReactShadowNode reactShadowNode = paramReactShadowNode.mutableCopyWithNewProps((ReactStylesDiffMap)paramReadableNativeMap);
    assertReactShadowNodeCopy(paramReactShadowNode, reactShadowNode);
    return reactShadowNode;
  }
  
  public void completeRoot(int paramInt, List<ReactShadowNode> paramList) {
    try {
      ReactShadowNode reactShadowNode = getRootNode(paramInt);
      StringBuilder stringBuilder = new StringBuilder("Root view with tag ");
      stringBuilder.append(paramInt);
      stringBuilder.append(" must be added before completeRoot is called");
      a.a(reactShadowNode, stringBuilder.toString());
      for (int i = 0; i < paramList.size(); i++)
        appendChild(reactShadowNode, paramList.get(i)); 
      notifyOnBeforeLayoutRecursive(reactShadowNode);
      calculateRootLayout(reactShadowNode);
      applyUpdatesRecursive(reactShadowNode, 0.0F, 0.0F);
      this.mUIViewOperationQueue.dispatchViewUpdates(1, System.currentTimeMillis(), System.currentTimeMillis());
      return;
    } catch (Exception exception) {
      handleException(getRootNode(paramInt), exception);
      return;
    } 
  }
  
  public List<ReactShadowNode> createChildSet(int paramInt) {
    return new ArrayList<ReactShadowNode>(1);
  }
  
  public ReactShadowNode createNode(int paramInt1, String paramString, int paramInt2, ReadableNativeMap paramReadableNativeMap) {
    try {
      ReactShadowNode reactShadowNode1 = this.mViewManagerRegistry.get(paramString).createShadowNodeInstance(this.mReactApplicationContext);
      ReactShadowNode reactShadowNode2 = getRootNode(paramInt2);
      reactShadowNode1.setRootNode(reactShadowNode2);
      reactShadowNode1.setViewClassName(paramString);
      reactShadowNode1.setReactTag(paramInt1);
      reactShadowNode1.setThemedContext(reactShadowNode2.getThemedContext());
      return reactShadowNode1;
    } finally {
      paramString = null;
      handleException(getRootNode(paramInt2), (Throwable)paramString);
    } 
  }
  
  public void removeRootView(int paramInt) {
    this.mRootShadowNodeRegistry.removeNode(paramInt);
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
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\fabric\FabricUIManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */