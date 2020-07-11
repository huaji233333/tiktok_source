package com.facebook.react.flat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.touch.OnInterceptTouchEventListener;
import com.facebook.react.touch.ReactHitSlopView;
import com.facebook.react.touch.ReactInterceptingViewGroup;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactCompoundViewGroup;
import com.facebook.react.uimanager.ReactPointerEventsView;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.views.image.ImageLoadEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

final class FlatViewGroup extends ViewGroup implements FlatMeasuredViewGroup, ReactHitSlopView, ReactInterceptingViewGroup, ReactClippingViewGroup, ReactCompoundViewGroup, ReactPointerEventsView {
  private static final SparseArray<View> EMPTY_DETACHED_VIEWS;
  
  private static final ArrayList<FlatViewGroup> LAYOUT_REQUESTS = new ArrayList<FlatViewGroup>();
  
  private static final Rect VIEW_BOUNDS = new Rect();
  
  private static Paint sDebugCornerPaint;
  
  private static Rect sDebugRect;
  
  private static Paint sDebugRectPaint;
  
  private static Paint sDebugTextBackgroundPaint;
  
  private static Paint sDebugTextPaint;
  
  private boolean mAndroidDebugDraw;
  
  private AttachDetachListener[] mAttachDetachListeners = AttachDetachListener.EMPTY_ARRAY;
  
  private int mDrawChildIndex;
  
  private DrawCommandManager mDrawCommandManager;
  
  private DrawCommand[] mDrawCommands = DrawCommand.EMPTY_ARRAY;
  
  private Rect mHitSlopRect;
  
  private Drawable mHotspot;
  
  private InvalidateCallback mInvalidateCallback;
  
  private boolean mIsAttached;
  
  private boolean mIsLayoutRequested;
  
  private long mLastTouchDownTime;
  
  private boolean mNeedsOffscreenAlphaCompositing;
  
  private NodeRegion[] mNodeRegions = NodeRegion.EMPTY_ARRAY;
  
  private OnInterceptTouchEventListener mOnInterceptTouchEventListener;
  
  private PointerEvents mPointerEvents = PointerEvents.AUTO;
  
  static {
    EMPTY_DETACHED_VIEWS = new SparseArray(0);
  }
  
  FlatViewGroup(Context paramContext) {
    super(paramContext);
    setClipChildren(false);
  }
  
  private NodeRegion anyNodeRegionWithinBounds(float paramFloat1, float paramFloat2) {
    DrawCommandManager drawCommandManager = this.mDrawCommandManager;
    if (drawCommandManager != null)
      return drawCommandManager.anyNodeRegionWithinBounds(paramFloat1, paramFloat2); 
    for (int i = this.mNodeRegions.length - 1; i >= 0; i--) {
      NodeRegion nodeRegion = this.mNodeRegions[i];
      if (nodeRegion.withinBounds(paramFloat1, paramFloat2))
        return nodeRegion; 
    } 
    return null;
  }
  
  private void debugDraw(Canvas paramCanvas) {
    DrawCommandManager drawCommandManager = this.mDrawCommandManager;
    if (drawCommandManager != null) {
      drawCommandManager.debugDraw(paramCanvas);
    } else {
      DrawCommand[] arrayOfDrawCommand = this.mDrawCommands;
      int j = arrayOfDrawCommand.length;
      for (int i = 0; i < j; i++)
        arrayOfDrawCommand[i].debugDraw(this, paramCanvas); 
    } 
    this.mDrawChildIndex = 0;
  }
  
  private void debugDrawRect(Canvas paramCanvas, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    debugDrawNamedRect(paramCanvas, paramInt, "", paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  private void dispatchOnAttached(AttachDetachListener[] paramArrayOfAttachDetachListener) {
    if (paramArrayOfAttachDetachListener.length == 0)
      return; 
    InvalidateCallback invalidateCallback = getInvalidateCallback();
    int j = paramArrayOfAttachDetachListener.length;
    for (int i = 0; i < j; i++)
      paramArrayOfAttachDetachListener[i].onAttached(invalidateCallback); 
  }
  
  private static void dispatchOnDetached(AttachDetachListener[] paramArrayOfAttachDetachListener) {
    int j = paramArrayOfAttachDetachListener.length;
    for (int i = 0; i < j; i++)
      paramArrayOfAttachDetachListener[i].onDetached(); 
  }
  
  private static void drawCorner(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    fillRect(paramCanvas, paramPaint, paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + sign(paramFloat4) * paramFloat5);
    fillRect(paramCanvas, paramPaint, paramFloat1, paramFloat2, paramFloat1 + paramFloat5 * sign(paramFloat3), paramFloat2 + paramFloat4);
  }
  
  private static void drawRectCorners(Canvas paramCanvas, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Paint paramPaint, int paramInt1, int paramInt2) {
    float f1 = paramInt1;
    float f2 = paramInt2;
    drawCorner(paramCanvas, paramPaint, paramFloat1, paramFloat2, f1, f1, f2);
    float f3 = -paramInt1;
    drawCorner(paramCanvas, paramPaint, paramFloat1, paramFloat4, f1, f3, f2);
    drawCorner(paramCanvas, paramPaint, paramFloat3, paramFloat2, f3, f1, f2);
    drawCorner(paramCanvas, paramPaint, paramFloat3, paramFloat4, f3, f3, f2);
  }
  
  private ViewGroup.LayoutParams ensureLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return checkLayoutParams(paramLayoutParams) ? paramLayoutParams : generateDefaultLayoutParams();
  }
  
  private static void ensureViewHasNoParent(View paramView) {
    ViewParent viewParent = paramView.getParent();
    if (viewParent == null)
      return; 
    StringBuilder stringBuilder = new StringBuilder("Cannot add view ");
    stringBuilder.append(paramView);
    stringBuilder.append(" to FlatViewGroup while it has a parent ");
    stringBuilder.append(viewParent);
    throw new RuntimeException(stringBuilder.toString());
  }
  
  private static void fillRect(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if (paramFloat1 != paramFloat3 && paramFloat2 != paramFloat4) {
      float f;
      if (paramFloat1 > paramFloat3) {
        f = paramFloat1;
        paramFloat1 = paramFloat3;
        paramFloat3 = f;
      } 
      if (paramFloat2 > paramFloat4) {
        f = paramFloat2;
      } else {
        f = paramFloat4;
        paramFloat4 = paramFloat2;
      } 
      paramCanvas.drawRect(paramFloat1, paramFloat4, paramFloat3, f, paramPaint);
    } 
  }
  
  private InvalidateCallback getInvalidateCallback() {
    if (this.mInvalidateCallback == null)
      this.mInvalidateCallback = new InvalidateCallback(this); 
    return this.mInvalidateCallback;
  }
  
  private void initDebugDrawResources() {
    if (sDebugTextPaint == null) {
      Paint paint = new Paint();
      sDebugTextPaint = paint;
      paint.setTextAlign(Paint.Align.RIGHT);
      sDebugTextPaint.setTextSize(dipsToPixels(9));
      sDebugTextPaint.setTypeface(Typeface.MONOSPACE);
      sDebugTextPaint.setAntiAlias(true);
      sDebugTextPaint.setColor(-65536);
    } 
    if (sDebugTextBackgroundPaint == null) {
      Paint paint = new Paint();
      sDebugTextBackgroundPaint = paint;
      paint.setColor(-1);
      sDebugTextBackgroundPaint.setAlpha(200);
      sDebugTextBackgroundPaint.setStyle(Paint.Style.FILL);
    } 
    if (sDebugRectPaint == null) {
      Paint paint = new Paint();
      sDebugRectPaint = paint;
      paint.setAlpha(100);
      sDebugRectPaint.setStyle(Paint.Style.STROKE);
    } 
    if (sDebugCornerPaint == null) {
      Paint paint = new Paint();
      sDebugCornerPaint = paint;
      paint.setAlpha(200);
      sDebugCornerPaint.setColor(Color.rgb(63, 127, 255));
      sDebugCornerPaint.setStyle(Paint.Style.FILL);
    } 
    if (sDebugRect == null)
      sDebugRect = new Rect(); 
  }
  
  private void processLayoutRequest() {
    int i = 0;
    this.mIsLayoutRequested = false;
    int j = getChildCount();
    while (i != j) {
      View view = getChildAt(i);
      if (view.isLayoutRequested()) {
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(view.getHeight(), 1073741824));
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
      } 
      i++;
    } 
  }
  
  static void processLayoutRequests() {
    int j = LAYOUT_REQUESTS.size();
    for (int i = 0; i != j; i++)
      ((FlatViewGroup)LAYOUT_REQUESTS.get(i)).processLayoutRequest(); 
    LAYOUT_REQUESTS.clear();
  }
  
  private static int sign(float paramFloat) {
    return (paramFloat >= 0.0F) ? 1 : -1;
  }
  
  private NodeRegion virtualNodeRegionWithinBounds(float paramFloat1, float paramFloat2) {
    DrawCommandManager drawCommandManager = this.mDrawCommandManager;
    if (drawCommandManager != null)
      return drawCommandManager.virtualNodeRegionWithinBounds(paramFloat1, paramFloat2); 
    for (int i = this.mNodeRegions.length - 1; i >= 0; i--) {
      NodeRegion nodeRegion = this.mNodeRegions[i];
      if (nodeRegion.mIsVirtual && nodeRegion.withinBounds(paramFloat1, paramFloat2))
        return nodeRegion; 
    } 
    return null;
  }
  
  final void addViewInLayout(View paramView) {
    addViewInLayout(paramView, -1, ensureLayoutParams(paramView.getLayoutParams()), true);
  }
  
  final void addViewInLayout(View paramView, int paramInt) {
    addViewInLayout(paramView, paramInt, ensureLayoutParams(paramView.getLayoutParams()), true);
  }
  
  final void attachViewToParent(View paramView) {
    attachViewToParent(paramView, -1, ensureLayoutParams(paramView.getLayoutParams()));
  }
  
  final void attachViewToParent(View paramView, int paramInt) {
    attachViewToParent(paramView, paramInt, ensureLayoutParams(paramView.getLayoutParams()));
  }
  
  final void debugDrawNamedRect(Canvas paramCanvas, int paramInt, String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    Paint paint = sDebugRectPaint;
    paint.setColor(paint.getColor() & 0xFF000000 | 0xFFFFFF & paramInt);
    sDebugRectPaint.setAlpha(100);
    paramCanvas.drawRect(paramFloat1, paramFloat2, paramFloat3 - 1.0F, paramFloat4 - 1.0F, sDebugRectPaint);
    drawRectCorners(paramCanvas, paramFloat1, paramFloat2, paramFloat3, paramFloat4, sDebugCornerPaint, dipsToPixels(8), dipsToPixels(1));
  }
  
  final void debugDrawNextChild(Canvas paramCanvas) {
    int i;
    View view = getChildAt(this.mDrawChildIndex);
    if (view instanceof FlatViewGroup) {
      i = -12303292;
    } else {
      i = -65536;
    } 
    debugDrawRect(paramCanvas, i, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    this.mDrawChildIndex++;
  }
  
  protected final void detachAllViewsFromParent() {
    super.detachAllViewsFromParent();
  }
  
  final int dipsToPixels(int paramInt) {
    float f = (getResources().getDisplayMetrics()).density;
    return (int)(paramInt * f + 0.5F);
  }
  
  public final void dispatchDraw(Canvas paramCanvas) {
    this.mAndroidDebugDraw = false;
    super.dispatchDraw(paramCanvas);
    DrawCommandManager drawCommandManager = this.mDrawCommandManager;
    if (drawCommandManager != null) {
      drawCommandManager.draw(paramCanvas);
    } else {
      DrawCommand[] arrayOfDrawCommand = this.mDrawCommands;
      int j = arrayOfDrawCommand.length;
      for (int i = 0; i < j; i++)
        arrayOfDrawCommand[i].draw(this, paramCanvas); 
    } 
    if (this.mDrawChildIndex == getChildCount()) {
      this.mDrawChildIndex = 0;
      if (this.mAndroidDebugDraw) {
        initDebugDrawResources();
        debugDraw(paramCanvas);
      } 
      Drawable drawable = this.mHotspot;
      if (drawable != null)
        drawable.draw(paramCanvas); 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Did not draw all children: ");
    stringBuilder.append(this.mDrawChildIndex);
    stringBuilder.append(" / ");
    stringBuilder.append(getChildCount());
    RuntimeException runtimeException = new RuntimeException(stringBuilder.toString());
    throw runtimeException;
  }
  
  public final void dispatchDrawableHotspotChanged(float paramFloat1, float paramFloat2) {
    Drawable drawable = this.mHotspot;
    if (drawable != null) {
      drawable.setHotspot(paramFloat1, paramFloat2);
      invalidate();
    } 
  }
  
  protected final boolean drawChild(Canvas paramCanvas, View paramView, long paramLong) {
    return false;
  }
  
  final void drawNextChild(Canvas paramCanvas) {
    View view = getChildAt(this.mDrawChildIndex);
    if (view instanceof FlatViewGroup) {
      super.drawChild(paramCanvas, view, getDrawingTime());
    } else {
      paramCanvas.save(2);
      view.getHitRect(VIEW_BOUNDS);
      paramCanvas.clipRect(VIEW_BOUNDS);
      super.drawChild(paramCanvas, view, getDrawingTime());
      paramCanvas.restore();
    } 
    this.mDrawChildIndex++;
  }
  
  protected final void drawableStateChanged() {
    super.drawableStateChanged();
    Drawable drawable = this.mHotspot;
    if (drawable != null && drawable.isStateful())
      this.mHotspot.setState(getDrawableState()); 
  }
  
  public final void getClippingRect(Rect paramRect) {
    DrawCommandManager drawCommandManager = this.mDrawCommandManager;
    if (drawCommandManager != null) {
      drawCommandManager.getClippingRect(paramRect);
      return;
    } 
    throw new RuntimeException("Trying to get the clipping rect for a non-clipping FlatViewGroup");
  }
  
  final SparseArray<View> getDetachedViews() {
    DrawCommandManager drawCommandManager = this.mDrawCommandManager;
    return (drawCommandManager == null) ? EMPTY_DETACHED_VIEWS : drawCommandManager.getDetachedViews();
  }
  
  public final Rect getHitSlopRect() {
    return this.mHitSlopRect;
  }
  
  final NodeRegion getNodeRegionForTag(int paramInt) {
    for (NodeRegion nodeRegion : this.mNodeRegions) {
      if (nodeRegion.matchesTag(paramInt))
        return nodeRegion; 
    } 
    return NodeRegion.EMPTY;
  }
  
  public final PointerEvents getPointerEvents() {
    return this.mPointerEvents;
  }
  
  public final boolean getRemoveClippedSubviews() {
    return (this.mDrawCommandManager != null);
  }
  
  public final boolean hasOverlappingRendering() {
    return this.mNeedsOffscreenAlphaCompositing;
  }
  
  public final boolean interceptsTouchEvent(float paramFloat1, float paramFloat2) {
    NodeRegion nodeRegion = anyNodeRegionWithinBounds(paramFloat1, paramFloat2);
    return (nodeRegion != null && nodeRegion.mIsVirtual);
  }
  
  public final void invalidate() {
    invalidate(0, 0, getWidth() + 1, getHeight() + 1);
  }
  
  public final void jumpDrawablesToCurrentState() {
    super.jumpDrawablesToCurrentState();
    Drawable drawable = this.mHotspot;
    if (drawable != null)
      drawable.jumpToCurrentState(); 
  }
  
  public final Rect measureWithCommands() {
    int i2 = getChildCount();
    int i1 = 0;
    if (i2 == 0 && this.mDrawCommands.length == 0)
      return new Rect(0, 0, 0, 0); 
    int i = Integer.MAX_VALUE;
    int n = 0;
    int j = Integer.MAX_VALUE;
    int k = Integer.MIN_VALUE;
    int m = Integer.MIN_VALUE;
    while (n < i2) {
      View view = getChildAt(n);
      i = Math.min(i, view.getLeft());
      j = Math.min(j, view.getTop());
      k = Math.max(k, view.getRight());
      m = Math.max(m, view.getBottom());
      n++;
    } 
    DrawCommand[] arrayOfDrawCommand = this.mDrawCommands;
    int i4 = arrayOfDrawCommand.length;
    n = j;
    int i3 = i;
    i = i1;
    while (i < i4) {
      DrawCommand drawCommand = arrayOfDrawCommand[i];
      int i5 = i3;
      i2 = n;
      i1 = k;
      j = m;
      if (drawCommand instanceof AbstractDrawCommand) {
        drawCommand = drawCommand;
        i5 = Math.min(i3, Math.round(drawCommand.getLeft()));
        i2 = Math.min(n, Math.round(drawCommand.getTop()));
        i1 = Math.max(k, Math.round(drawCommand.getRight()));
        j = Math.max(m, Math.round(drawCommand.getBottom()));
      } 
      i++;
      i3 = i5;
      n = i2;
      k = i1;
      m = j;
    } 
    return new Rect(i3, n, k, m);
  }
  
  final void mountAttachDetachListeners(AttachDetachListener[] paramArrayOfAttachDetachListener) {
    if (this.mIsAttached) {
      dispatchOnAttached(paramArrayOfAttachDetachListener);
      dispatchOnDetached(this.mAttachDetachListeners);
    } 
    this.mAttachDetachListeners = paramArrayOfAttachDetachListener;
  }
  
  final void mountClippingDrawCommands(DrawCommand[] paramArrayOfDrawCommand, SparseIntArray paramSparseIntArray, float[] paramArrayOffloat1, float[] paramArrayOffloat2, boolean paramBoolean) {
    ((DrawCommandManager)a.b(this.mDrawCommandManager)).mountDrawCommands(paramArrayOfDrawCommand, paramSparseIntArray, paramArrayOffloat1, paramArrayOffloat2, paramBoolean);
    invalidate();
  }
  
  final void mountClippingNodeRegions(NodeRegion[] paramArrayOfNodeRegion, float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    this.mNodeRegions = paramArrayOfNodeRegion;
    ((DrawCommandManager)a.b(this.mDrawCommandManager)).mountNodeRegions(paramArrayOfNodeRegion, paramArrayOffloat1, paramArrayOffloat2);
  }
  
  final void mountDrawCommands(DrawCommand[] paramArrayOfDrawCommand) {
    this.mDrawCommands = paramArrayOfDrawCommand;
    invalidate();
  }
  
  final void mountNodeRegions(NodeRegion[] paramArrayOfNodeRegion) {
    this.mNodeRegions = paramArrayOfNodeRegion;
  }
  
  final void mountViews(ViewResolver paramViewResolver, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    DrawCommandManager drawCommandManager = this.mDrawCommandManager;
    if (drawCommandManager != null) {
      drawCommandManager.mountViews(paramViewResolver, paramArrayOfint1, paramArrayOfint2);
    } else {
      int j = paramArrayOfint1.length;
      int i;
      for (i = 0; i < j; i++) {
        int k = paramArrayOfint1[i];
        if (k > 0) {
          View view = paramViewResolver.getView(k);
          ensureViewHasNoParent(view);
          addViewInLayout(view);
        } else {
          View view = paramViewResolver.getView(-k);
          ensureViewHasNoParent(view);
          attachViewToParent(view);
        } 
      } 
      j = paramArrayOfint2.length;
      i = 0;
      while (i < j) {
        View view = paramViewResolver.getView(paramArrayOfint2[i]);
        if (view.getParent() == null) {
          removeDetachedView(view, false);
          i++;
          continue;
        } 
        throw new RuntimeException("Trying to remove view not owned by FlatViewGroup");
      } 
    } 
    invalidate();
  }
  
  protected final void onAttachedToWindow() {
    if (this.mIsAttached)
      return; 
    this.mIsAttached = true;
    super.onAttachedToWindow();
    dispatchOnAttached(this.mAttachDetachListeners);
    updateClippingRect();
  }
  
  protected final void onDebugDraw(Canvas paramCanvas) {
    this.mAndroidDebugDraw = true;
  }
  
  protected final void onDetachedFromWindow() {
    if (this.mIsAttached) {
      this.mIsAttached = false;
      super.onDetachedFromWindow();
      dispatchOnDetached(this.mAttachDetachListeners);
      return;
    } 
    throw new RuntimeException("Double detach");
  }
  
  public final boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    long l = paramMotionEvent.getDownTime();
    if (l != this.mLastTouchDownTime) {
      this.mLastTouchDownTime = l;
      if (interceptsTouchEvent(paramMotionEvent.getX(), paramMotionEvent.getY()))
        return true; 
    } 
    OnInterceptTouchEventListener onInterceptTouchEventListener = this.mOnInterceptTouchEventListener;
    return (onInterceptTouchEventListener != null && onInterceptTouchEventListener.onInterceptTouchEvent(this, paramMotionEvent)) ? true : ((this.mPointerEvents != PointerEvents.NONE) ? ((this.mPointerEvents == PointerEvents.BOX_ONLY) ? true : super.onInterceptTouchEvent(paramMotionEvent)) : true);
  }
  
  protected final void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  protected final void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Drawable drawable = this.mHotspot;
    if (drawable != null) {
      drawable.setBounds(0, 0, paramInt1, paramInt2);
      invalidate();
    } 
    updateClippingRect();
  }
  
  public final boolean onTouchEvent(MotionEvent paramMotionEvent) {
    return (this.mPointerEvents == PointerEvents.NONE) ? false : (!(this.mPointerEvents == PointerEvents.BOX_NONE && virtualNodeRegionWithinBounds(paramMotionEvent.getX(), paramMotionEvent.getY()) == null));
  }
  
  final void onViewDropped(View paramView) {
    DrawCommandManager drawCommandManager = this.mDrawCommandManager;
    if (drawCommandManager != null)
      drawCommandManager.onClippedViewDropped(paramView); 
  }
  
  public final int reactTagForTouch(float paramFloat1, float paramFloat2) {
    boolean bool;
    if (this.mPointerEvents != PointerEvents.NONE) {
      bool = true;
    } else {
      bool = false;
    } 
    SoftAssertions.assertCondition(bool, "TouchTargetHelper should not allow calling this method when pointer events are NONE");
    if (this.mPointerEvents != PointerEvents.BOX_ONLY) {
      NodeRegion nodeRegion = virtualNodeRegionWithinBounds(paramFloat1, paramFloat2);
      if (nodeRegion != null)
        return nodeRegion.getReactTag(paramFloat1, paramFloat2); 
    } 
    return getId();
  }
  
  public final void removeAllViewsInLayout() {
    this.mDrawCommands = DrawCommand.EMPTY_ARRAY;
    super.removeAllViewsInLayout();
  }
  
  final void removeDetachedView(View paramView) {
    removeDetachedView(paramView, false);
  }
  
  public final void requestLayout() {
    if (this.mIsLayoutRequested)
      return; 
    this.mIsLayoutRequested = true;
    LAYOUT_REQUESTS.add(this);
  }
  
  final void setHitSlopRect(Rect paramRect) {
    this.mHitSlopRect = paramRect;
  }
  
  final void setHotspot(Drawable paramDrawable) {
    Drawable drawable = this.mHotspot;
    if (drawable != null) {
      drawable.setCallback(null);
      unscheduleDrawable(this.mHotspot);
    } 
    if (paramDrawable != null) {
      paramDrawable.setCallback((Drawable.Callback)this);
      if (paramDrawable.isStateful())
        paramDrawable.setState(getDrawableState()); 
    } 
    this.mHotspot = paramDrawable;
    invalidate();
  }
  
  final void setNeedsOffscreenAlphaCompositing(boolean paramBoolean) {
    this.mNeedsOffscreenAlphaCompositing = paramBoolean;
  }
  
  public final void setOnInterceptTouchEventListener(OnInterceptTouchEventListener paramOnInterceptTouchEventListener) {
    this.mOnInterceptTouchEventListener = paramOnInterceptTouchEventListener;
  }
  
  final void setPointerEvents(PointerEvents paramPointerEvents) {
    this.mPointerEvents = paramPointerEvents;
  }
  
  public final void setRemoveClippedSubviews(boolean paramBoolean) {
    boolean bool = getRemoveClippedSubviews();
    if (paramBoolean == bool)
      return; 
    if (!bool) {
      this.mDrawCommandManager = DrawCommandManager.getVerticalClippingInstance(this, this.mDrawCommands);
      this.mDrawCommands = DrawCommand.EMPTY_ARRAY;
      return;
    } 
    throw new RuntimeException("Trying to transition FlatViewGroup from clipping to non-clipping state");
  }
  
  public final void updateClippingRect() {
    DrawCommandManager drawCommandManager = this.mDrawCommandManager;
    if (drawCommandManager == null)
      return; 
    if (drawCommandManager.updateClippingRect())
      invalidate(); 
  }
  
  protected final boolean verifyDrawable(Drawable paramDrawable) {
    return true;
  }
  
  static final class InvalidateCallback extends WeakReference<FlatViewGroup> {
    private InvalidateCallback(FlatViewGroup param1FlatViewGroup) {
      super(param1FlatViewGroup);
    }
    
    public final void dispatchImageLoadEvent(int param1Int1, int param1Int2) {
      FlatViewGroup flatViewGroup = get();
      if (flatViewGroup == null)
        return; 
      ((UIManagerModule)((ReactContext)flatViewGroup.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent((Event)new ImageLoadEvent(param1Int1, param1Int2));
    }
    
    public final void invalidate() {
      FlatViewGroup flatViewGroup = get();
      if (flatViewGroup != null)
        flatViewGroup.invalidate(); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatViewGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */