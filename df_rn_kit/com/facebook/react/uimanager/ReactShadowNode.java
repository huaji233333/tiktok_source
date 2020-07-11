package com.facebook.react.uimanager;

import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaDisplay;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaValue;
import com.facebook.yoga.YogaWrap;

public interface ReactShadowNode<T extends ReactShadowNode> {
  void addChildAt(T paramT, int paramInt);
  
  void addNativeChildAt(T paramT, int paramInt);
  
  void calculateLayout();
  
  void dirty();
  
  boolean dispatchUpdates(float paramFloat1, float paramFloat2, UIViewOperationQueue paramUIViewOperationQueue, NativeViewHierarchyOptimizer paramNativeViewHierarchyOptimizer);
  
  void dispose();
  
  T getChildAt(int paramInt);
  
  int getChildCount();
  
  String getHierarchyInfo();
  
  YogaDirection getLayoutDirection();
  
  float getLayoutHeight();
  
  float getLayoutWidth();
  
  float getLayoutX();
  
  float getLayoutY();
  
  int getNativeChildCount();
  
  int getNativeOffsetForChild(T paramT);
  
  T getNativeParent();
  
  ReactStylesDiffMap getNewProps();
  
  float getPadding(int paramInt);
  
  T getParent();
  
  int getReactTag();
  
  T getRootNode();
  
  int getScreenHeight();
  
  int getScreenWidth();
  
  int getScreenX();
  
  int getScreenY();
  
  YogaValue getStyleHeight();
  
  YogaValue getStylePadding(int paramInt);
  
  YogaValue getStyleWidth();
  
  ThemedReactContext getThemedContext();
  
  int getTotalNativeChildren();
  
  String getViewClass();
  
  boolean hasNewLayout();
  
  boolean hasUnseenUpdates();
  
  boolean hasUpdates();
  
  int indexOf(T paramT);
  
  int indexOfNativeChild(T paramT);
  
  boolean isDescendantOf(T paramT);
  
  boolean isDirty();
  
  boolean isLayoutOnly();
  
  boolean isMeasureDefined();
  
  boolean isVirtual();
  
  boolean isVirtualAnchor();
  
  boolean isYogaLeafNode();
  
  void markLayoutSeen();
  
  void markUpdateSeen();
  
  void markUpdated();
  
  T mutableCopy();
  
  T mutableCopyWithNewChildren();
  
  T mutableCopyWithNewChildrenAndProps(ReactStylesDiffMap paramReactStylesDiffMap);
  
  T mutableCopyWithNewProps(ReactStylesDiffMap paramReactStylesDiffMap);
  
  void onAfterUpdateTransaction();
  
  void onBeforeLayout();
  
  void onCollectExtraUpdates(UIViewOperationQueue paramUIViewOperationQueue);
  
  void removeAllNativeChildren();
  
  void removeAndDisposeAllChildren();
  
  T removeChildAt(int paramInt);
  
  T removeNativeChildAt(int paramInt);
  
  void setAlignContent(YogaAlign paramYogaAlign);
  
  void setAlignItems(YogaAlign paramYogaAlign);
  
  void setAlignSelf(YogaAlign paramYogaAlign);
  
  void setBaselineFunction(YogaBaselineFunction paramYogaBaselineFunction);
  
  void setBorder(int paramInt, float paramFloat);
  
  void setDefaultPadding(int paramInt, float paramFloat);
  
  void setDisplay(YogaDisplay paramYogaDisplay);
  
  void setFlex(float paramFloat);
  
  void setFlexBasis(float paramFloat);
  
  void setFlexBasisAuto();
  
  void setFlexBasisPercent(float paramFloat);
  
  void setFlexDirection(YogaFlexDirection paramYogaFlexDirection);
  
  void setFlexGrow(float paramFloat);
  
  void setFlexShrink(float paramFloat);
  
  void setFlexWrap(YogaWrap paramYogaWrap);
  
  void setIsLayoutOnly(boolean paramBoolean);
  
  void setJustifyContent(YogaJustify paramYogaJustify);
  
  void setLayoutDirection(YogaDirection paramYogaDirection);
  
  void setLocalData(Object paramObject);
  
  void setMargin(int paramInt, float paramFloat);
  
  void setMarginAuto(int paramInt);
  
  void setMarginPercent(int paramInt, float paramFloat);
  
  void setMeasureFunction(YogaMeasureFunction paramYogaMeasureFunction);
  
  void setOverflow(YogaOverflow paramYogaOverflow);
  
  void setPadding(int paramInt, float paramFloat);
  
  void setPaddingPercent(int paramInt, float paramFloat);
  
  void setPosition(int paramInt, float paramFloat);
  
  void setPositionPercent(int paramInt, float paramFloat);
  
  void setPositionType(YogaPositionType paramYogaPositionType);
  
  void setReactTag(int paramInt);
  
  void setRootNode(T paramT);
  
  void setShouldNotifyOnLayout(boolean paramBoolean);
  
  void setStyleAspectRatio(float paramFloat);
  
  void setStyleHeight(float paramFloat);
  
  void setStyleHeightAuto();
  
  void setStyleHeightPercent(float paramFloat);
  
  void setStyleMaxHeight(float paramFloat);
  
  void setStyleMaxHeightPercent(float paramFloat);
  
  void setStyleMaxWidth(float paramFloat);
  
  void setStyleMaxWidthPercent(float paramFloat);
  
  void setStyleMinHeight(float paramFloat);
  
  void setStyleMinHeightPercent(float paramFloat);
  
  void setStyleMinWidth(float paramFloat);
  
  void setStyleMinWidthPercent(float paramFloat);
  
  void setStyleWidth(float paramFloat);
  
  void setStyleWidthAuto();
  
  void setStyleWidthPercent(float paramFloat);
  
  void setThemedContext(ThemedReactContext paramThemedReactContext);
  
  void setViewClassName(String paramString);
  
  boolean shouldNotifyOnLayout();
  
  void updateProperties(ReactStylesDiffMap paramReactStylesDiffMap);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ReactShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */