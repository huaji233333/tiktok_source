package com.facebook.react.flat;

import android.graphics.Rect;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.OnLayoutEvent;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.annotations.ReactProp;

class FlatShadowNode extends LayoutShadowNode {
  static final FlatShadowNode[] EMPTY_ARRAY = new FlatShadowNode[0];
  
  private static final DrawView EMPTY_DRAW_VIEW;
  
  private static final Rect LOGICAL_OFFSET_EMPTY = new Rect();
  
  private AttachDetachListener[] mAttachDetachListeners = AttachDetachListener.EMPTY_ARRAY;
  
  private boolean mBackingViewIsCreated;
  
  private float mClipBottom;
  
  private float mClipLeft;
  
  float mClipRadius;
  
  private float mClipRight;
  
  boolean mClipToBounds;
  
  private float mClipTop;
  
  private DrawBackgroundColor mDrawBackground;
  
  private DrawCommand[] mDrawCommands = DrawCommand.EMPTY_ARRAY;
  
  private DrawView mDrawView;
  
  private boolean mForceMountChildrenToView;
  
  private boolean mIsUpdated = true;
  
  private int mLayoutHeight;
  
  private int mLayoutWidth;
  
  private int mLayoutX;
  
  private int mLayoutY;
  
  private Rect mLogicalOffset = LOGICAL_OFFSET_EMPTY;
  
  private FlatShadowNode[] mNativeChildren = EMPTY_ARRAY;
  
  private int mNativeParentTag;
  
  private NodeRegion mNodeRegion = NodeRegion.EMPTY;
  
  private NodeRegion[] mNodeRegions = NodeRegion.EMPTY_ARRAY;
  
  private boolean mOverflowsContainer;
  
  private int mViewBottom;
  
  private int mViewLeft;
  
  private int mViewRight;
  
  private int mViewTop;
  
  static {
    EMPTY_DRAW_VIEW = new DrawView(0);
  }
  
  public void addChildAt(ReactShadowNodeImpl paramReactShadowNodeImpl, int paramInt) {
    super.addChildAt(paramReactShadowNodeImpl, paramInt);
    if (this.mForceMountChildrenToView && paramReactShadowNodeImpl instanceof FlatShadowNode)
      ((FlatShadowNode)paramReactShadowNodeImpl).forceMountToView(); 
  }
  
  final boolean clipBoundsChanged(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    return (this.mClipLeft != paramFloat1 || this.mClipTop != paramFloat2 || this.mClipRight != paramFloat3 || this.mClipBottom != paramFloat4);
  }
  
  public final boolean clipToBounds() {
    return this.mClipToBounds;
  }
  
  public boolean clipsSubviews() {
    return false;
  }
  
  final DrawView collectDrawView(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    float f;
    if (this.mDrawView == EMPTY_DRAW_VIEW)
      this.mDrawView = new DrawView(getReactTag()); 
    if (this.mClipToBounds) {
      f = this.mClipRadius;
    } else {
      f = 0.0F;
    } 
    this.mDrawView = this.mDrawView.collectDrawView(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat1 + this.mLogicalOffset.left, paramFloat2 + this.mLogicalOffset.top, paramFloat3 + this.mLogicalOffset.right, paramFloat4 + this.mLogicalOffset.bottom, paramFloat5, paramFloat6, paramFloat7, paramFloat8, f);
    return this.mDrawView;
  }
  
  protected void collectState(StateBuilder paramStateBuilder, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    DrawBackgroundColor drawBackgroundColor = this.mDrawBackground;
    if (drawBackgroundColor != null) {
      this.mDrawBackground = (DrawBackgroundColor)drawBackgroundColor.updateBoundsAndFreeze(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
      paramStateBuilder.addDrawCommand(this.mDrawBackground);
    } 
  }
  
  boolean doesDraw() {
    return (this.mDrawView != null || this.mDrawBackground != null);
  }
  
  final void forceMountChildrenToView() {
    if (this.mForceMountChildrenToView)
      return; 
    this.mForceMountChildrenToView = true;
    int i = 0;
    int j = getChildCount();
    while (i != j) {
      ReactShadowNodeImpl reactShadowNodeImpl = getChildAt(i);
      if (reactShadowNodeImpl instanceof FlatShadowNode)
        ((FlatShadowNode)reactShadowNodeImpl).forceMountToView(); 
      i++;
    } 
  }
  
  final void forceMountToView() {
    if (isVirtual())
      return; 
    if (this.mDrawView == null) {
      this.mDrawView = EMPTY_DRAW_VIEW;
      invalidate();
      this.mNodeRegion = NodeRegion.EMPTY;
    } 
  }
  
  final AttachDetachListener[] getAttachDetachListeners() {
    return this.mAttachDetachListeners;
  }
  
  final DrawCommand[] getDrawCommands() {
    return this.mDrawCommands;
  }
  
  final FlatShadowNode[] getNativeChildren() {
    return this.mNativeChildren;
  }
  
  final int getNativeParentTag() {
    return this.mNativeParentTag;
  }
  
  final NodeRegion getNodeRegion() {
    return this.mNodeRegion;
  }
  
  final NodeRegion[] getNodeRegions() {
    return this.mNodeRegions;
  }
  
  public final int getScreenHeight() {
    return mountsToView() ? (this.mViewBottom - this.mViewTop) : Math.round(this.mNodeRegion.getBottom() - this.mNodeRegion.getTop());
  }
  
  public final int getScreenWidth() {
    return mountsToView() ? (this.mViewRight - this.mViewLeft) : Math.round(this.mNodeRegion.getRight() - this.mNodeRegion.getLeft());
  }
  
  public final int getScreenX() {
    return this.mViewLeft;
  }
  
  public final int getScreenY() {
    return this.mViewTop;
  }
  
  final int getViewBottom() {
    return this.mViewBottom;
  }
  
  final int getViewLeft() {
    return this.mViewLeft;
  }
  
  final int getViewRight() {
    return this.mViewRight;
  }
  
  final int getViewTop() {
    return this.mViewTop;
  }
  
  void handleUpdateProperties(ReactStylesDiffMap paramReactStylesDiffMap) {
    if (!mountsToView() && (paramReactStylesDiffMap.hasKey("opacity") || paramReactStylesDiffMap.hasKey("renderToHardwareTextureAndroid") || paramReactStylesDiffMap.hasKey("testID") || paramReactStylesDiffMap.hasKey("accessibilityLabel") || paramReactStylesDiffMap.hasKey("accessibilityComponentType") || paramReactStylesDiffMap.hasKey("accessibilityLiveRegion") || paramReactStylesDiffMap.hasKey("transform") || paramReactStylesDiffMap.hasKey("importantForAccessibility") || paramReactStylesDiffMap.hasKey("removeClippedSubviews")))
      forceMountToView(); 
  }
  
  protected final void invalidate() {
    FlatShadowNode flatShadowNode = this;
    while (true) {
      if (flatShadowNode.mountsToView()) {
        if (flatShadowNode.mIsUpdated)
          return; 
        flatShadowNode.mIsUpdated = true;
      } 
      ReactShadowNodeImpl reactShadowNodeImpl = flatShadowNode.getParent();
      if (reactShadowNodeImpl == null)
        return; 
      FlatShadowNode flatShadowNode1 = (FlatShadowNode)reactShadowNodeImpl;
    } 
  }
  
  final boolean isBackingViewCreated() {
    return this.mBackingViewIsCreated;
  }
  
  public boolean isHorizontal() {
    return false;
  }
  
  final boolean isUpdated() {
    return this.mIsUpdated;
  }
  
  public void markUpdated() {
    super.markUpdated();
    this.mIsUpdated = true;
    invalidate();
  }
  
  final boolean mountsToView() {
    return (this.mDrawView != null);
  }
  
  final OnLayoutEvent obtainLayoutEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mLayoutX == paramInt1 && this.mLayoutY == paramInt2 && this.mLayoutWidth == paramInt3 && this.mLayoutHeight == paramInt4)
      return null; 
    this.mLayoutX = paramInt1;
    this.mLayoutY = paramInt2;
    this.mLayoutWidth = paramInt3;
    this.mLayoutHeight = paramInt4;
    return OnLayoutEvent.obtain(getReactTag(), paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  final void resetUpdated() {
    this.mIsUpdated = false;
  }
  
  final void setAttachDetachListeners(AttachDetachListener[] paramArrayOfAttachDetachListener) {
    this.mAttachDetachListeners = paramArrayOfAttachDetachListener;
  }
  
  @ReactProp(name = "backgroundColor")
  public void setBackgroundColor(int paramInt) {
    DrawBackgroundColor drawBackgroundColor;
    if (paramInt == 0) {
      drawBackgroundColor = null;
    } else {
      drawBackgroundColor = new DrawBackgroundColor(paramInt);
    } 
    this.mDrawBackground = drawBackgroundColor;
    invalidate();
  }
  
  final void setClipBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.mClipLeft = paramFloat1;
    this.mClipTop = paramFloat2;
    this.mClipRight = paramFloat3;
    this.mClipBottom = paramFloat4;
  }
  
  final void setDrawCommands(DrawCommand[] paramArrayOfDrawCommand) {
    this.mDrawCommands = paramArrayOfDrawCommand;
  }
  
  final void setNativeChildren(FlatShadowNode[] paramArrayOfFlatShadowNode) {
    this.mNativeChildren = paramArrayOfFlatShadowNode;
  }
  
  final void setNativeParentTag(int paramInt) {
    this.mNativeParentTag = paramInt;
  }
  
  protected final void setNodeRegion(NodeRegion paramNodeRegion) {
    this.mNodeRegion = paramNodeRegion;
    updateOverflowsContainer();
  }
  
  final void setNodeRegions(NodeRegion[] paramArrayOfNodeRegion) {
    this.mNodeRegions = paramArrayOfNodeRegion;
    updateOverflowsContainer();
  }
  
  public void setOverflow(String paramString) {
    super.setOverflow(paramString);
    this.mClipToBounds = "hidden".equals(paramString);
    if (this.mClipToBounds) {
      this.mOverflowsContainer = false;
      if (this.mClipRadius > 0.5F)
        forceMountToView(); 
    } else {
      updateOverflowsContainer();
    } 
    invalidate();
  }
  
  final void setViewBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mViewLeft = paramInt1;
    this.mViewTop = paramInt2;
    this.mViewRight = paramInt3;
    this.mViewBottom = paramInt4;
  }
  
  final void signalBackingViewIsCreated() {
    this.mBackingViewIsCreated = true;
  }
  
  void updateNodeRegion(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
    if (!this.mNodeRegion.matches(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean))
      setNodeRegion(new NodeRegion(paramFloat1, paramFloat2, paramFloat3, paramFloat4, getReactTag(), paramBoolean)); 
  }
  
  final void updateOverflowsContainer() {
    int i = (int)(this.mNodeRegion.getRight() - this.mNodeRegion.getLeft());
    int j = (int)(this.mNodeRegion.getBottom() - this.mNodeRegion.getTop());
    float f1 = i;
    float f2 = j;
    boolean bool1 = this.mClipToBounds;
    boolean bool = false;
    Rect rect1 = null;
    if (!bool1 && j > 0 && i > 0) {
      NodeRegion[] arrayOfNodeRegion = this.mNodeRegions;
      j = arrayOfNodeRegion.length;
      float f5 = f1;
      float f3 = f2;
      i = 0;
      boolean bool3 = false;
      float f6 = 0.0F;
      float f4 = 0.0F;
      while (i < j) {
        NodeRegion nodeRegion = arrayOfNodeRegion[i];
        float f7 = f6;
        if (nodeRegion.getLeft() < f6) {
          f7 = nodeRegion.getLeft();
          bool3 = true;
        } 
        float f8 = f5;
        if (nodeRegion.getRight() > f5) {
          f8 = nodeRegion.getRight();
          bool3 = true;
        } 
        f5 = f4;
        if (nodeRegion.getTop() < f4) {
          f5 = nodeRegion.getTop();
          bool3 = true;
        } 
        float f9 = f3;
        if (nodeRegion.getBottom() > f3) {
          f9 = nodeRegion.getBottom();
          bool3 = true;
        } 
        i++;
        f6 = f7;
        f4 = f5;
        f5 = f8;
        f3 = f9;
      } 
      bool1 = bool3;
      if (bool3) {
        rect1 = new Rect((int)f6, (int)f4, (int)(f5 - f1), (int)(f3 - f2));
        bool1 = bool3;
      } 
    } else {
      bool1 = false;
    } 
    Rect rect2 = rect1;
    boolean bool2 = bool1;
    if (!bool1) {
      rect2 = rect1;
      bool2 = bool1;
      if (this.mNodeRegion != NodeRegion.EMPTY) {
        j = getChildCount();
        i = bool;
        while (true) {
          rect2 = rect1;
          bool2 = bool1;
          if (i < j) {
            ReactShadowNodeImpl reactShadowNodeImpl = getChildAt(i);
            rect2 = rect1;
            bool2 = bool1;
            if (reactShadowNodeImpl instanceof FlatShadowNode) {
              FlatShadowNode flatShadowNode = (FlatShadowNode)reactShadowNodeImpl;
              rect2 = rect1;
              bool2 = bool1;
              if (flatShadowNode.mOverflowsContainer) {
                Rect rect = flatShadowNode.mLogicalOffset;
                rect2 = rect1;
                if (rect1 == null)
                  rect2 = new Rect(); 
                rect2.union(rect);
                bool2 = true;
              } 
            } 
            i++;
            rect1 = rect2;
            bool1 = bool2;
            continue;
          } 
          break;
        } 
      } 
    } 
    if (this.mOverflowsContainer != bool2) {
      this.mOverflowsContainer = bool2;
      rect1 = rect2;
      if (rect2 == null)
        rect1 = LOGICAL_OFFSET_EMPTY; 
      this.mLogicalOffset = rect1;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */