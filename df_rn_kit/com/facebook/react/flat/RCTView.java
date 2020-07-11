package com.facebook.react.flat;

import android.graphics.Rect;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;

final class RCTView extends FlatShadowNode {
  private static final int[] SPACING_TYPES = new int[] { 8, 0, 2, 1, 3 };
  
  private DrawBorder mDrawBorder;
  
  private Rect mHitSlop;
  
  boolean mHorizontal;
  
  boolean mRemoveClippedSubviews;
  
  private DrawBorder getMutableBorder() {
    DrawBorder drawBorder = this.mDrawBorder;
    if (drawBorder == null) {
      this.mDrawBorder = new DrawBorder();
    } else if (drawBorder.isFrozen()) {
      this.mDrawBorder = (DrawBorder)this.mDrawBorder.mutableCopy();
    } 
    invalidate();
    return this.mDrawBorder;
  }
  
  public final boolean clipsSubviews() {
    return this.mRemoveClippedSubviews;
  }
  
  protected final void collectState(StateBuilder paramStateBuilder, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    super.collectState(paramStateBuilder, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
    DrawBorder drawBorder = this.mDrawBorder;
    if (drawBorder != null) {
      this.mDrawBorder = (DrawBorder)drawBorder.updateBoundsAndFreeze(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
      paramStateBuilder.addDrawCommand(this.mDrawBorder);
    } 
  }
  
  final boolean doesDraw() {
    return (this.mDrawBorder != null || super.doesDraw());
  }
  
  final void handleUpdateProperties(ReactStylesDiffMap paramReactStylesDiffMap) {
    boolean bool = this.mRemoveClippedSubviews;
    boolean bool1 = true;
    if (bool || (paramReactStylesDiffMap.hasKey("removeClippedSubviews") && paramReactStylesDiffMap.getBoolean("removeClippedSubviews", false))) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mRemoveClippedSubviews = bool;
    if (this.mRemoveClippedSubviews) {
      bool = bool1;
      if (!this.mHorizontal)
        if (paramReactStylesDiffMap.hasKey("horizontal") && paramReactStylesDiffMap.getBoolean("horizontal", false)) {
          bool = bool1;
        } else {
          bool = false;
        }  
      this.mHorizontal = bool;
    } 
    super.handleUpdateProperties(paramReactStylesDiffMap);
  }
  
  public final void setBackgroundColor(int paramInt) {
    getMutableBorder().setBackgroundColor(paramInt);
  }
  
  @ReactPropGroup(customType = "Color", defaultDouble = NaND, names = {"borderColor", "borderLeftColor", "borderRightColor", "borderTopColor", "borderBottomColor"})
  public final void setBorderColor(int paramInt, double paramDouble) {
    paramInt = SPACING_TYPES[paramInt];
    if (Double.isNaN(paramDouble)) {
      getMutableBorder().resetBorderColor(paramInt);
      return;
    } 
    getMutableBorder().setBorderColor(paramInt, (int)paramDouble);
  }
  
  @ReactProp(name = "borderRadius")
  public final void setBorderRadius(float paramFloat) {
    this.mClipRadius = paramFloat;
    if (this.mClipToBounds && paramFloat > 0.5F)
      forceMountToView(); 
    getMutableBorder().setBorderRadius(PixelUtil.toPixelFromDIP(paramFloat));
  }
  
  @ReactProp(name = "borderStyle")
  public final void setBorderStyle(String paramString) {
    getMutableBorder().setBorderStyle(paramString);
  }
  
  public final void setBorderWidths(int paramInt, float paramFloat) {
    super.setBorderWidths(paramInt, paramFloat);
    paramInt = SPACING_TYPES[paramInt];
    getMutableBorder().setBorderWidth(paramInt, PixelUtil.toPixelFromDIP(paramFloat));
  }
  
  @ReactProp(name = "hitSlop")
  public final void setHitSlop(ReadableMap paramReadableMap) {
    if (paramReadableMap == null) {
      this.mHitSlop = null;
      return;
    } 
    this.mHitSlop = new Rect((int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("left")), (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("top")), (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("right")), (int)PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("bottom")));
  }
  
  @ReactProp(name = "nativeBackgroundAndroid")
  public final void setHotspot(ReadableMap paramReadableMap) {
    if (paramReadableMap != null)
      forceMountToView(); 
  }
  
  @ReactProp(name = "pointerEvents")
  public final void setPointerEvents(String paramString) {
    forceMountToView();
  }
  
  final void updateNodeRegion(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
    if (!getNodeRegion().matches(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean)) {
      NodeRegion nodeRegion;
      Rect rect = this.mHitSlop;
      if (rect == null) {
        nodeRegion = new NodeRegion(paramFloat1, paramFloat2, paramFloat3, paramFloat4, getReactTag(), paramBoolean);
      } else {
        nodeRegion = new HitSlopNodeRegion((Rect)nodeRegion, paramFloat1, paramFloat2, paramFloat3, paramFloat4, getReactTag(), paramBoolean);
      } 
      setNodeRegion(nodeRegion);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */