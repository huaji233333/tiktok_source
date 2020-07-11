package com.facebook.react.flat;

import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;

final class NativeViewWrapper extends FlatShadowNode implements AndroidView {
  private boolean mForceMountGrandChildrenToView;
  
  private final boolean mNeedsCustomLayoutForChildren;
  
  private boolean mPaddingChanged;
  
  private final ReactShadowNode mReactShadowNode;
  
  NativeViewWrapper(ViewManager paramViewManager) {
    ReactShadowNode reactShadowNode = paramViewManager.createShadowNodeInstance();
    if (reactShadowNode instanceof YogaMeasureFunction) {
      this.mReactShadowNode = reactShadowNode;
      setMeasureFunction((YogaMeasureFunction)reactShadowNode);
    } else {
      this.mReactShadowNode = null;
    } 
    if (paramViewManager instanceof ViewGroupManager) {
      ViewGroupManager viewGroupManager = (ViewGroupManager)paramViewManager;
      this.mNeedsCustomLayoutForChildren = viewGroupManager.needsCustomLayoutForChildren();
      this.mForceMountGrandChildrenToView = viewGroupManager.shouldPromoteGrandchildren();
    } else {
      this.mNeedsCustomLayoutForChildren = false;
    } 
    forceMountToView();
    forceMountChildrenToView();
  }
  
  public final void addChildAt(ReactShadowNodeImpl paramReactShadowNodeImpl, int paramInt) {
    super.addChildAt(paramReactShadowNodeImpl, paramInt);
    if (this.mForceMountGrandChildrenToView && paramReactShadowNodeImpl instanceof FlatShadowNode)
      ((FlatShadowNode)paramReactShadowNodeImpl).forceMountChildrenToView(); 
  }
  
  final void handleUpdateProperties(ReactStylesDiffMap paramReactStylesDiffMap) {
    ReactShadowNode reactShadowNode = this.mReactShadowNode;
    if (reactShadowNode != null)
      reactShadowNode.updateProperties(paramReactStylesDiffMap); 
  }
  
  public final boolean isPaddingChanged() {
    return this.mPaddingChanged;
  }
  
  public final boolean needsCustomLayoutForChildren() {
    return this.mNeedsCustomLayoutForChildren;
  }
  
  public final void onCollectExtraUpdates(UIViewOperationQueue paramUIViewOperationQueue) {
    ReactShadowNode reactShadowNode = this.mReactShadowNode;
    if (reactShadowNode != null && reactShadowNode.hasUnseenUpdates()) {
      this.mReactShadowNode.onCollectExtraUpdates(paramUIViewOperationQueue);
      markUpdateSeen();
    } 
  }
  
  public final void resetPaddingChanged() {
    this.mPaddingChanged = false;
  }
  
  public final void setBackgroundColor(int paramInt) {}
  
  public final void setPadding(int paramInt, float paramFloat) {
    YogaValue yogaValue = getStylePadding(paramInt);
    if (yogaValue.unit != YogaUnit.POINT || yogaValue.value != paramFloat) {
      super.setPadding(paramInt, paramFloat);
      this.mPaddingChanged = true;
      markUpdated();
    } 
  }
  
  public final void setPaddingPercent(int paramInt, float paramFloat) {
    YogaValue yogaValue = getStylePadding(paramInt);
    if (yogaValue.unit != YogaUnit.PERCENT || yogaValue.value != paramFloat) {
      super.setPadding(paramInt, paramFloat);
      this.mPaddingChanged = true;
      markUpdated();
    } 
  }
  
  public final void setReactTag(int paramInt) {
    super.setReactTag(paramInt);
    ReactShadowNode reactShadowNode = this.mReactShadowNode;
    if (reactShadowNode != null)
      reactShadowNode.setReactTag(paramInt); 
  }
  
  public final void setThemedContext(ThemedReactContext paramThemedReactContext) {
    super.setThemedContext(paramThemedReactContext);
    ReactShadowNode reactShadowNode = this.mReactShadowNode;
    if (reactShadowNode != null)
      reactShadowNode.setThemedContext(paramThemedReactContext); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\NativeViewWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */