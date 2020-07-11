package com.facebook.react.uimanager;

import com.facebook.i.a.a;
import com.facebook.yoga.YogaAlign;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaConfig;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaDisplay;
import com.facebook.yoga.YogaEdge;
import com.facebook.yoga.YogaFlexDirection;
import com.facebook.yoga.YogaJustify;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.YogaNodeClonedFunction;
import com.facebook.yoga.YogaOverflow;
import com.facebook.yoga.YogaPositionType;
import com.facebook.yoga.YogaValue;
import com.facebook.yoga.YogaWrap;
import com.facebook.yoga.a;
import java.util.ArrayList;
import java.util.Arrays;

public class ReactShadowNodeImpl implements ReactShadowNode<ReactShadowNodeImpl> {
  private static final YogaConfig sYogaConfig;
  
  private ArrayList<ReactShadowNodeImpl> mChildren;
  
  private final Spacing mDefaultPadding = new Spacing(0.0F);
  
  private boolean mIsLayoutOnly;
  
  private ArrayList<ReactShadowNodeImpl> mNativeChildren;
  
  private ReactShadowNodeImpl mNativeParent;
  
  private ReactStylesDiffMap mNewProps;
  
  private boolean mNodeUpdated = true;
  
  private final float[] mPadding = new float[9];
  
  private final boolean[] mPaddingIsPercent = new boolean[9];
  
  private ReactShadowNodeImpl mParent;
  
  private int mReactTag;
  
  private ReactShadowNodeImpl mRootNode;
  
  private int mRootTag;
  
  private int mScreenHeight;
  
  private int mScreenWidth;
  
  private int mScreenX;
  
  private int mScreenY;
  
  private boolean mShouldNotifyOnLayout;
  
  private ThemedReactContext mThemedContext;
  
  private int mTotalNativeChildren;
  
  private String mViewClassName;
  
  private final YogaNode mYogaNode;
  
  static {
    YogaConfig yogaConfig = ReactYogaConfigProvider.get();
    sYogaConfig = yogaConfig;
    yogaConfig.setOnNodeCloned(new YogaNodeClonedFunction() {
          public final void onNodeCloned(YogaNode param1YogaNode1, YogaNode param1YogaNode2, YogaNode param1YogaNode3, int param1Int) {
            a.b(param1YogaNode3.getData());
            a.b(param1YogaNode2.getData());
          }
        });
  }
  
  public ReactShadowNodeImpl() {
    if (!isVirtual()) {
      YogaNode yogaNode2 = (YogaNode)YogaNodePool.get().acquire();
      YogaNode yogaNode1 = yogaNode2;
      if (yogaNode2 == null)
        yogaNode1 = new YogaNode(sYogaConfig); 
      this.mYogaNode = yogaNode1;
      this.mYogaNode.setData(this);
      Arrays.fill(this.mPadding, 1.0E21F);
      return;
    } 
    this.mYogaNode = null;
  }
  
  public ReactShadowNodeImpl(ReactShadowNodeImpl paramReactShadowNodeImpl) {
    try {
      ArrayList<ReactShadowNodeImpl> arrayList;
      this.mReactTag = paramReactShadowNodeImpl.mReactTag;
      this.mRootTag = paramReactShadowNodeImpl.mRootTag;
      this.mViewClassName = paramReactShadowNodeImpl.mViewClassName;
      this.mRootNode = paramReactShadowNodeImpl.mRootNode;
      this.mThemedContext = paramReactShadowNodeImpl.mThemedContext;
      this.mShouldNotifyOnLayout = paramReactShadowNodeImpl.mShouldNotifyOnLayout;
      this.mNodeUpdated = paramReactShadowNodeImpl.mNodeUpdated;
      if (paramReactShadowNodeImpl.mChildren == null) {
        arrayList = null;
      } else {
        arrayList = new ArrayList<ReactShadowNodeImpl>(paramReactShadowNodeImpl.mChildren);
      } 
      this.mChildren = arrayList;
      this.mIsLayoutOnly = paramReactShadowNodeImpl.mIsLayoutOnly;
      this.mTotalNativeChildren = paramReactShadowNodeImpl.mTotalNativeChildren;
      this.mNativeParent = paramReactShadowNodeImpl.mNativeParent;
      if (paramReactShadowNodeImpl.mNativeChildren == null) {
        arrayList = null;
      } else {
        arrayList = new ArrayList<ReactShadowNodeImpl>(paramReactShadowNodeImpl.mNativeChildren);
      } 
      this.mNativeChildren = arrayList;
      this.mNativeParent = paramReactShadowNodeImpl.mNativeParent;
      this.mScreenX = paramReactShadowNodeImpl.mScreenX;
      this.mScreenY = paramReactShadowNodeImpl.mScreenY;
      this.mScreenWidth = paramReactShadowNodeImpl.mScreenWidth;
      this.mScreenHeight = paramReactShadowNodeImpl.mScreenHeight;
      System.arraycopy(paramReactShadowNodeImpl.mPadding, 0, this.mPadding, 0, paramReactShadowNodeImpl.mPadding.length);
      System.arraycopy(paramReactShadowNodeImpl.mPaddingIsPercent, 0, this.mPaddingIsPercent, 0, paramReactShadowNodeImpl.mPaddingIsPercent.length);
      this.mYogaNode = paramReactShadowNodeImpl.mYogaNode.clone();
      this.mYogaNode.setData(this);
      this.mParent = null;
      this.mNewProps = null;
      return;
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new IllegalArgumentException();
    } 
  }
  
  private void getHierarchyInfoWithIndentation(StringBuilder paramStringBuilder, int paramInt) {
    boolean bool = false;
    int i;
    for (i = 0; i < paramInt; i++)
      paramStringBuilder.append("__"); 
    paramStringBuilder.append(getClass().getSimpleName());
    paramStringBuilder.append(" ");
    paramStringBuilder.append(getReactTag());
    paramStringBuilder.append(" ");
    if (this.mYogaNode != null) {
      paramStringBuilder.append(getScreenX());
      paramStringBuilder.append(";");
      paramStringBuilder.append(getScreenY());
      paramStringBuilder.append(";");
      paramStringBuilder.append(getLayoutWidth());
      paramStringBuilder.append(";");
      paramStringBuilder.append(getLayoutHeight());
    } else {
      paramStringBuilder.append("(virtual node)");
    } 
    paramStringBuilder.append("\n");
    i = bool;
    if (getChildCount() == 0)
      return; 
    while (i < getChildCount()) {
      getChildAt(i).getHierarchyInfoWithIndentation(paramStringBuilder, paramInt + 1);
      i++;
    } 
  }
  
  private void updateNativeChildrenCountInParent(int paramInt) {
    if (this.mIsLayoutOnly) {
      ReactShadowNodeImpl reactShadowNodeImpl = getParent();
      while (reactShadowNodeImpl != null) {
        reactShadowNodeImpl.mTotalNativeChildren += paramInt;
        if (reactShadowNodeImpl.isLayoutOnly())
          reactShadowNodeImpl = reactShadowNodeImpl.getParent(); 
      } 
    } 
  }
  
  private void updatePadding() {
    for (int i = 0; i <= 8; i++) {
      if (i == 0 || i == 2 || i == 4 || i == 5) {
        if (a.a(this.mPadding[i]) && a.a(this.mPadding[6]) && a.a(this.mPadding[8])) {
          this.mYogaNode.setPadding(YogaEdge.fromInt(i), this.mDefaultPadding.getRaw(i));
          continue;
        } 
      } else if (i == 1 || i == 3) {
        if (a.a(this.mPadding[i]) && a.a(this.mPadding[7]) && a.a(this.mPadding[8])) {
          this.mYogaNode.setPadding(YogaEdge.fromInt(i), this.mDefaultPadding.getRaw(i));
          continue;
        } 
      } else if (a.a(this.mPadding[i])) {
        this.mYogaNode.setPadding(YogaEdge.fromInt(i), this.mDefaultPadding.getRaw(i));
        continue;
      } 
      if (this.mPaddingIsPercent[i]) {
        this.mYogaNode.setPaddingPercent(YogaEdge.fromInt(i), this.mPadding[i]);
      } else {
        this.mYogaNode.setPadding(YogaEdge.fromInt(i), this.mPadding[i]);
      } 
      continue;
    } 
  }
  
  public void addChildAt(ReactShadowNodeImpl paramReactShadowNodeImpl, int paramInt) {
    if (paramReactShadowNodeImpl.getParent() == null) {
      if (this.mChildren == null)
        this.mChildren = new ArrayList<ReactShadowNodeImpl>(4); 
      this.mChildren.add(paramInt, paramReactShadowNodeImpl);
      paramReactShadowNodeImpl.mParent = this;
      if (this.mYogaNode != null && !isYogaLeafNode()) {
        YogaNode yogaNode = paramReactShadowNodeImpl.mYogaNode;
        if (yogaNode != null) {
          this.mYogaNode.addChildAt(yogaNode, paramInt);
        } else {
          StringBuilder stringBuilder = new StringBuilder("Cannot add a child that doesn't have a YogaNode to a parent without a measure function! (Trying to add a '");
          stringBuilder.append(paramReactShadowNodeImpl.toString());
          stringBuilder.append("' to a '");
          stringBuilder.append(toString());
          stringBuilder.append("')");
          throw new RuntimeException(stringBuilder.toString());
        } 
      } 
      markUpdated();
      if (paramReactShadowNodeImpl.isLayoutOnly()) {
        paramInt = paramReactShadowNodeImpl.getTotalNativeChildren();
      } else {
        paramInt = 1;
      } 
      this.mTotalNativeChildren += paramInt;
      updateNativeChildrenCountInParent(paramInt);
      return;
    } 
    throw new IllegalViewOperationException("Tried to add child that already has a parent! Remove it from its parent first.");
  }
  
  public final void addNativeChildAt(ReactShadowNodeImpl paramReactShadowNodeImpl, int paramInt) {
    a.a(this.mIsLayoutOnly ^ true);
    a.a(paramReactShadowNodeImpl.mIsLayoutOnly ^ true);
    if (this.mNativeChildren == null)
      this.mNativeChildren = new ArrayList<ReactShadowNodeImpl>(4); 
    this.mNativeChildren.add(paramInt, paramReactShadowNodeImpl);
    paramReactShadowNodeImpl.mNativeParent = this;
  }
  
  public void calculateLayout() {
    this.mYogaNode.calculateLayout(1.0E21F, 1.0E21F);
  }
  
  public void dirty() {
    if (!isVirtual())
      this.mYogaNode.dirty(); 
  }
  
  public boolean dispatchUpdates(float paramFloat1, float paramFloat2, UIViewOperationQueue paramUIViewOperationQueue, NativeViewHierarchyOptimizer paramNativeViewHierarchyOptimizer) {
    if (this.mNodeUpdated)
      onCollectExtraUpdates(paramUIViewOperationQueue); 
    boolean bool = hasNewLayout();
    boolean bool2 = false;
    boolean bool1 = false;
    if (bool) {
      float f1 = getLayoutX();
      float f2 = getLayoutY();
      paramFloat1 += f1;
      int n = Math.round(paramFloat1);
      paramFloat2 += f2;
      int k = Math.round(paramFloat2);
      int i1 = Math.round(paramFloat1 + getLayoutWidth());
      int m = Math.round(paramFloat2 + getLayoutHeight());
      int i = Math.round(f1);
      int j = Math.round(f2);
      n = i1 - n;
      k = m - k;
      if (i != this.mScreenX || j != this.mScreenY || n != this.mScreenWidth || k != this.mScreenHeight)
        bool1 = true; 
      this.mScreenX = i;
      this.mScreenY = j;
      this.mScreenWidth = n;
      this.mScreenHeight = k;
      bool2 = bool1;
      if (bool1) {
        if (paramNativeViewHierarchyOptimizer != null) {
          paramNativeViewHierarchyOptimizer.handleUpdateLayout(this);
          return bool1;
        } 
        paramUIViewOperationQueue.enqueueUpdateLayout(getParent().getReactTag(), getReactTag(), getScreenX(), getScreenY(), getScreenWidth(), getScreenHeight());
        bool2 = bool1;
      } 
    } 
    return bool2;
  }
  
  public void dispose() {
    YogaNode yogaNode = this.mYogaNode;
    if (yogaNode != null) {
      yogaNode.reset();
      YogaNodePool.get().release(this.mYogaNode);
    } 
  }
  
  public final ReactShadowNodeImpl getChildAt(int paramInt) {
    ArrayList<ReactShadowNodeImpl> arrayList = this.mChildren;
    if (arrayList != null)
      return arrayList.get(paramInt); 
    StringBuilder stringBuilder = new StringBuilder("Index ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" out of bounds: node has no children");
    throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public final int getChildCount() {
    ArrayList<ReactShadowNodeImpl> arrayList = this.mChildren;
    return (arrayList == null) ? 0 : arrayList.size();
  }
  
  public String getHierarchyInfo() {
    StringBuilder stringBuilder = new StringBuilder();
    getHierarchyInfoWithIndentation(stringBuilder, 0);
    return stringBuilder.toString();
  }
  
  public final YogaDirection getLayoutDirection() {
    return this.mYogaNode.getLayoutDirection();
  }
  
  public final float getLayoutHeight() {
    return this.mYogaNode.getLayoutHeight();
  }
  
  public final float getLayoutWidth() {
    return this.mYogaNode.getLayoutWidth();
  }
  
  public final float getLayoutX() {
    return this.mYogaNode.getLayoutX();
  }
  
  public final float getLayoutY() {
    return this.mYogaNode.getLayoutY();
  }
  
  public final int getNativeChildCount() {
    ArrayList<ReactShadowNodeImpl> arrayList = this.mNativeChildren;
    return (arrayList == null) ? 0 : arrayList.size();
  }
  
  public final int getNativeOffsetForChild(ReactShadowNodeImpl paramReactShadowNodeImpl) {
    int k;
    byte b = 0;
    int j = 0;
    int i = 0;
    while (true) {
      int m = getChildCount();
      byte b1 = 1;
      k = b;
      if (j < m) {
        ReactShadowNodeImpl reactShadowNodeImpl = getChildAt(j);
        if (paramReactShadowNodeImpl == reactShadowNodeImpl) {
          k = 1;
          break;
        } 
        k = b1;
        if (reactShadowNodeImpl.isLayoutOnly())
          k = reactShadowNodeImpl.getTotalNativeChildren(); 
        i += k;
        j++;
        continue;
      } 
      break;
    } 
    if (k != 0)
      return i; 
    StringBuilder stringBuilder = new StringBuilder("Child ");
    stringBuilder.append(paramReactShadowNodeImpl.getReactTag());
    stringBuilder.append(" was not a child of ");
    stringBuilder.append(this.mReactTag);
    RuntimeException runtimeException = new RuntimeException(stringBuilder.toString());
    throw runtimeException;
  }
  
  public final ReactShadowNodeImpl getNativeParent() {
    return this.mNativeParent;
  }
  
  public ReactStylesDiffMap getNewProps() {
    return this.mNewProps;
  }
  
  public final float getPadding(int paramInt) {
    return this.mYogaNode.getLayoutPadding(YogaEdge.fromInt(paramInt));
  }
  
  public final ReactShadowNodeImpl getParent() {
    return this.mParent;
  }
  
  public final int getReactTag() {
    return this.mReactTag;
  }
  
  public final ReactShadowNodeImpl getRootNode() {
    return (ReactShadowNodeImpl)a.b(this.mRootNode);
  }
  
  public int getScreenHeight() {
    return this.mScreenHeight;
  }
  
  public int getScreenWidth() {
    return this.mScreenWidth;
  }
  
  public int getScreenX() {
    return this.mScreenX;
  }
  
  public int getScreenY() {
    return this.mScreenY;
  }
  
  public final YogaValue getStyleHeight() {
    return this.mYogaNode.getHeight();
  }
  
  public final YogaValue getStylePadding(int paramInt) {
    return this.mYogaNode.getPadding(YogaEdge.fromInt(paramInt));
  }
  
  public final YogaValue getStyleWidth() {
    return this.mYogaNode.getWidth();
  }
  
  public final ThemedReactContext getThemedContext() {
    return (ThemedReactContext)a.b(this.mThemedContext);
  }
  
  public final int getTotalNativeChildren() {
    return this.mTotalNativeChildren;
  }
  
  public final String getViewClass() {
    return (String)a.b(this.mViewClassName);
  }
  
  public final boolean hasNewLayout() {
    YogaNode yogaNode = this.mYogaNode;
    return (yogaNode != null && yogaNode.hasNewLayout());
  }
  
  public final boolean hasUnseenUpdates() {
    return this.mNodeUpdated;
  }
  
  public final boolean hasUpdates() {
    return (this.mNodeUpdated || hasNewLayout() || isDirty());
  }
  
  public final int indexOf(ReactShadowNodeImpl paramReactShadowNodeImpl) {
    ArrayList<ReactShadowNodeImpl> arrayList = this.mChildren;
    return (arrayList == null) ? -1 : arrayList.indexOf(paramReactShadowNodeImpl);
  }
  
  public final int indexOfNativeChild(ReactShadowNodeImpl paramReactShadowNodeImpl) {
    a.b(this.mNativeChildren);
    return this.mNativeChildren.indexOf(paramReactShadowNodeImpl);
  }
  
  public boolean isDescendantOf(ReactShadowNodeImpl paramReactShadowNodeImpl) {
    for (ReactShadowNodeImpl reactShadowNodeImpl = getParent(); reactShadowNodeImpl != null; reactShadowNodeImpl = reactShadowNodeImpl.getParent()) {
      if (reactShadowNodeImpl == paramReactShadowNodeImpl)
        return true; 
    } 
    return false;
  }
  
  public final boolean isDirty() {
    YogaNode yogaNode = this.mYogaNode;
    return (yogaNode != null && yogaNode.isDirty());
  }
  
  public final boolean isLayoutOnly() {
    return this.mIsLayoutOnly;
  }
  
  public boolean isMeasureDefined() {
    return this.mYogaNode.isMeasureDefined();
  }
  
  public boolean isVirtual() {
    return false;
  }
  
  public boolean isVirtualAnchor() {
    return false;
  }
  
  public boolean isYogaLeafNode() {
    return isMeasureDefined();
  }
  
  public final void markLayoutSeen() {
    YogaNode yogaNode = this.mYogaNode;
    if (yogaNode != null)
      yogaNode.markLayoutSeen(); 
  }
  
  public final void markUpdateSeen() {
    this.mNodeUpdated = false;
    if (hasNewLayout())
      markLayoutSeen(); 
  }
  
  public void markUpdated() {
    if (this.mNodeUpdated)
      return; 
    this.mNodeUpdated = true;
    ReactShadowNodeImpl reactShadowNodeImpl = getParent();
    if (reactShadowNodeImpl != null)
      reactShadowNodeImpl.markUpdated(); 
  }
  
  public ReactShadowNodeImpl mutableCopy() {
    return new ReactShadowNodeImpl(this);
  }
  
  public ReactShadowNodeImpl mutableCopyWithNewChildren() {
    ReactShadowNodeImpl reactShadowNodeImpl = mutableCopy();
    reactShadowNodeImpl.mNativeChildren = null;
    reactShadowNodeImpl.mChildren = null;
    return reactShadowNodeImpl;
  }
  
  public ReactShadowNodeImpl mutableCopyWithNewChildrenAndProps(ReactStylesDiffMap paramReactStylesDiffMap) {
    ReactShadowNodeImpl reactShadowNodeImpl = mutableCopyWithNewChildren();
    if (paramReactStylesDiffMap != null) {
      reactShadowNodeImpl.updateProperties(paramReactStylesDiffMap);
      reactShadowNodeImpl.mNewProps = paramReactStylesDiffMap;
    } 
    return reactShadowNodeImpl;
  }
  
  public ReactShadowNodeImpl mutableCopyWithNewProps(ReactStylesDiffMap paramReactStylesDiffMap) {
    ReactShadowNodeImpl reactShadowNodeImpl = mutableCopy();
    if (paramReactStylesDiffMap != null) {
      reactShadowNodeImpl.updateProperties(paramReactStylesDiffMap);
      reactShadowNodeImpl.mNewProps = paramReactStylesDiffMap;
    } 
    return reactShadowNodeImpl;
  }
  
  public void onAfterUpdateTransaction() {}
  
  public void onBeforeLayout() {}
  
  public void onCollectExtraUpdates(UIViewOperationQueue paramUIViewOperationQueue) {}
  
  public final void removeAllNativeChildren() {
    ArrayList<ReactShadowNodeImpl> arrayList = this.mNativeChildren;
    if (arrayList != null) {
      for (int i = arrayList.size() - 1; i >= 0; i--)
        ((ReactShadowNodeImpl)this.mNativeChildren.get(i)).mNativeParent = null; 
      this.mNativeChildren.clear();
    } 
  }
  
  public void removeAndDisposeAllChildren() {
    if (getChildCount() == 0)
      return; 
    int j = 0;
    for (int i = getChildCount() - 1; i >= 0; i--) {
      byte b;
      if (this.mYogaNode != null && !isYogaLeafNode())
        this.mYogaNode.removeChildAt(i); 
      ReactShadowNodeImpl reactShadowNodeImpl = getChildAt(i);
      reactShadowNodeImpl.mParent = null;
      reactShadowNodeImpl.dispose();
      if (reactShadowNodeImpl.isLayoutOnly()) {
        b = reactShadowNodeImpl.getTotalNativeChildren();
      } else {
        b = 1;
      } 
      j += b;
    } 
    ((ArrayList)a.b(this.mChildren)).clear();
    markUpdated();
    this.mTotalNativeChildren -= j;
    updateNativeChildrenCountInParent(-j);
  }
  
  public ReactShadowNodeImpl removeChildAt(int paramInt) {
    ArrayList<ReactShadowNodeImpl> arrayList = this.mChildren;
    if (arrayList != null) {
      ReactShadowNodeImpl reactShadowNodeImpl = arrayList.remove(paramInt);
      reactShadowNodeImpl.mParent = null;
      if (this.mYogaNode != null && !isYogaLeafNode())
        this.mYogaNode.removeChildAt(paramInt); 
      markUpdated();
      if (reactShadowNodeImpl.isLayoutOnly()) {
        paramInt = reactShadowNodeImpl.getTotalNativeChildren();
      } else {
        paramInt = 1;
      } 
      this.mTotalNativeChildren -= paramInt;
      updateNativeChildrenCountInParent(-paramInt);
      return reactShadowNodeImpl;
    } 
    StringBuilder stringBuilder = new StringBuilder("Index ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" out of bounds: node has no children");
    throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public final ReactShadowNodeImpl removeNativeChildAt(int paramInt) {
    a.b(this.mNativeChildren);
    ReactShadowNodeImpl reactShadowNodeImpl = this.mNativeChildren.remove(paramInt);
    reactShadowNodeImpl.mNativeParent = null;
    return reactShadowNodeImpl;
  }
  
  public void setAlignContent(YogaAlign paramYogaAlign) {
    this.mYogaNode.setAlignContent(paramYogaAlign);
  }
  
  public void setAlignItems(YogaAlign paramYogaAlign) {
    this.mYogaNode.setAlignItems(paramYogaAlign);
  }
  
  public void setAlignSelf(YogaAlign paramYogaAlign) {
    this.mYogaNode.setAlignSelf(paramYogaAlign);
  }
  
  public void setBaselineFunction(YogaBaselineFunction paramYogaBaselineFunction) {
    this.mYogaNode.setBaselineFunction(paramYogaBaselineFunction);
  }
  
  public void setBorder(int paramInt, float paramFloat) {
    this.mYogaNode.setBorder(YogaEdge.fromInt(paramInt), paramFloat);
  }
  
  public void setDefaultPadding(int paramInt, float paramFloat) {
    this.mDefaultPadding.set(paramInt, paramFloat);
    updatePadding();
  }
  
  public void setDisplay(YogaDisplay paramYogaDisplay) {
    this.mYogaNode.setDisplay(paramYogaDisplay);
  }
  
  public void setFlex(float paramFloat) {
    this.mYogaNode.setFlex(paramFloat);
  }
  
  public void setFlexBasis(float paramFloat) {
    this.mYogaNode.setFlexBasis(paramFloat);
  }
  
  public void setFlexBasisAuto() {
    this.mYogaNode.setFlexBasisAuto();
  }
  
  public void setFlexBasisPercent(float paramFloat) {
    this.mYogaNode.setFlexBasisPercent(paramFloat);
  }
  
  public void setFlexDirection(YogaFlexDirection paramYogaFlexDirection) {
    this.mYogaNode.setFlexDirection(paramYogaFlexDirection);
  }
  
  public void setFlexGrow(float paramFloat) {
    this.mYogaNode.setFlexGrow(paramFloat);
  }
  
  public void setFlexShrink(float paramFloat) {
    this.mYogaNode.setFlexShrink(paramFloat);
  }
  
  public void setFlexWrap(YogaWrap paramYogaWrap) {
    this.mYogaNode.setWrap(paramYogaWrap);
  }
  
  public final void setIsLayoutOnly(boolean paramBoolean) {
    boolean bool1;
    ReactShadowNodeImpl reactShadowNodeImpl = getParent();
    boolean bool2 = true;
    if (reactShadowNodeImpl == null) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    a.a(bool1, "Must remove from no opt parent first");
    if (this.mNativeParent == null) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    a.a(bool1, "Must remove from native parent first");
    if (getNativeChildCount() == 0) {
      bool1 = bool2;
    } else {
      bool1 = false;
    } 
    a.a(bool1, "Must remove all native children first");
    this.mIsLayoutOnly = paramBoolean;
  }
  
  public void setJustifyContent(YogaJustify paramYogaJustify) {
    this.mYogaNode.setJustifyContent(paramYogaJustify);
  }
  
  public void setLayoutDirection(YogaDirection paramYogaDirection) {
    this.mYogaNode.setDirection(paramYogaDirection);
  }
  
  public void setLocalData(Object paramObject) {}
  
  public void setMargin(int paramInt, float paramFloat) {
    this.mYogaNode.setMargin(YogaEdge.fromInt(paramInt), paramFloat);
  }
  
  public void setMarginAuto(int paramInt) {
    this.mYogaNode.setMarginAuto(YogaEdge.fromInt(paramInt));
  }
  
  public void setMarginPercent(int paramInt, float paramFloat) {
    this.mYogaNode.setMarginPercent(YogaEdge.fromInt(paramInt), paramFloat);
  }
  
  public void setMeasureFunction(YogaMeasureFunction paramYogaMeasureFunction) {
    boolean bool;
    if (paramYogaMeasureFunction == null) {
      bool = true;
    } else {
      bool = false;
    } 
    if (!(bool ^ this.mYogaNode.isMeasureDefined()) || getChildCount() == 0) {
      this.mYogaNode.setMeasureFunction(paramYogaMeasureFunction);
      return;
    } 
    throw new RuntimeException("Since a node with a measure function does not add any native yoga children, it's not safe to transition to/from having a measure function unless a node has no children");
  }
  
  public void setOverflow(YogaOverflow paramYogaOverflow) {
    this.mYogaNode.setOverflow(paramYogaOverflow);
  }
  
  public void setPadding(int paramInt, float paramFloat) {
    this.mPadding[paramInt] = paramFloat;
    this.mPaddingIsPercent[paramInt] = false;
    updatePadding();
  }
  
  public void setPaddingPercent(int paramInt, float paramFloat) {
    this.mPadding[paramInt] = paramFloat;
    this.mPaddingIsPercent[paramInt] = a.a(paramFloat) ^ true;
    updatePadding();
  }
  
  public void setPosition(int paramInt, float paramFloat) {
    this.mYogaNode.setPosition(YogaEdge.fromInt(paramInt), paramFloat);
  }
  
  public void setPositionPercent(int paramInt, float paramFloat) {
    this.mYogaNode.setPositionPercent(YogaEdge.fromInt(paramInt), paramFloat);
  }
  
  public void setPositionType(YogaPositionType paramYogaPositionType) {
    this.mYogaNode.setPositionType(paramYogaPositionType);
  }
  
  public void setReactTag(int paramInt) {
    this.mReactTag = paramInt;
  }
  
  public final void setRootNode(ReactShadowNodeImpl paramReactShadowNodeImpl) {
    this.mRootNode = paramReactShadowNodeImpl;
  }
  
  public void setShouldNotifyOnLayout(boolean paramBoolean) {
    this.mShouldNotifyOnLayout = paramBoolean;
  }
  
  public void setStyleAspectRatio(float paramFloat) {
    this.mYogaNode.setAspectRatio(paramFloat);
  }
  
  public void setStyleHeight(float paramFloat) {
    this.mYogaNode.setHeight(paramFloat);
  }
  
  public void setStyleHeightAuto() {
    this.mYogaNode.setHeightAuto();
  }
  
  public void setStyleHeightPercent(float paramFloat) {
    this.mYogaNode.setHeightPercent(paramFloat);
  }
  
  public void setStyleMaxHeight(float paramFloat) {
    this.mYogaNode.setMaxHeight(paramFloat);
  }
  
  public void setStyleMaxHeightPercent(float paramFloat) {
    this.mYogaNode.setMaxHeightPercent(paramFloat);
  }
  
  public void setStyleMaxWidth(float paramFloat) {
    this.mYogaNode.setMaxWidth(paramFloat);
  }
  
  public void setStyleMaxWidthPercent(float paramFloat) {
    this.mYogaNode.setMaxWidthPercent(paramFloat);
  }
  
  public void setStyleMinHeight(float paramFloat) {
    this.mYogaNode.setMinHeight(paramFloat);
  }
  
  public void setStyleMinHeightPercent(float paramFloat) {
    this.mYogaNode.setMinHeightPercent(paramFloat);
  }
  
  public void setStyleMinWidth(float paramFloat) {
    this.mYogaNode.setMinWidth(paramFloat);
  }
  
  public void setStyleMinWidthPercent(float paramFloat) {
    this.mYogaNode.setMinWidthPercent(paramFloat);
  }
  
  public void setStyleWidth(float paramFloat) {
    this.mYogaNode.setWidth(paramFloat);
  }
  
  public void setStyleWidthAuto() {
    this.mYogaNode.setWidthAuto();
  }
  
  public void setStyleWidthPercent(float paramFloat) {
    this.mYogaNode.setWidthPercent(paramFloat);
  }
  
  public void setThemedContext(ThemedReactContext paramThemedReactContext) {
    this.mThemedContext = paramThemedReactContext;
  }
  
  public final void setViewClassName(String paramString) {
    this.mViewClassName = paramString;
  }
  
  public final boolean shouldNotifyOnLayout() {
    return this.mShouldNotifyOnLayout;
  }
  
  public String toString() {
    return this.mViewClassName;
  }
  
  public final void updateProperties(ReactStylesDiffMap paramReactStylesDiffMap) {
    ViewManagerPropertyUpdater.updateProps(this, paramReactStylesDiffMap);
    onAfterUpdateTransaction();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ReactShadowNodeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */