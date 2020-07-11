package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

abstract class AbstractDrawCommand extends DrawCommand implements Cloneable {
  private static Paint sDebugHighlightOverlayText;
  
  private static Paint sDebugHighlightRed;
  
  private static Paint sDebugHighlightYellow;
  
  private float mBottom;
  
  private float mClipBottom;
  
  private float mClipLeft;
  
  private float mClipRight;
  
  private float mClipTop;
  
  private boolean mFrozen;
  
  private float mLeft;
  
  protected boolean mNeedsClipping;
  
  private float mRight;
  
  private float mTop;
  
  private void debugDrawHighlightRect(Canvas paramCanvas, Paint paramPaint, String paramString) {
    paramCanvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), paramPaint);
    paramCanvas.drawText(paramString, getRight() - 5.0F, getBottom() - 5.0F, sDebugHighlightOverlayText);
  }
  
  protected static int getDebugBorderColor() {
    return -16711681;
  }
  
  private void initDebugHighlightResources(FlatViewGroup paramFlatViewGroup) {
    if (sDebugHighlightRed == null) {
      Paint paint = new Paint();
      sDebugHighlightRed = paint;
      paint.setARGB(75, 255, 0, 0);
    } 
    if (sDebugHighlightYellow == null) {
      Paint paint = new Paint();
      sDebugHighlightYellow = paint;
      paint.setARGB(100, 255, 204, 0);
    } 
    if (sDebugHighlightOverlayText == null) {
      Paint paint = new Paint();
      sDebugHighlightOverlayText = paint;
      paint.setAntiAlias(true);
      sDebugHighlightOverlayText.setARGB(200, 50, 50, 50);
      sDebugHighlightOverlayText.setTextAlign(Paint.Align.RIGHT);
      sDebugHighlightOverlayText.setTypeface(Typeface.MONOSPACE);
      sDebugHighlightOverlayText.setTextSize(paramFlatViewGroup.dipsToPixels(9));
    } 
  }
  
  protected void applyClipping(Canvas paramCanvas) {
    paramCanvas.clipRect(this.mClipLeft, this.mClipTop, this.mClipRight, this.mClipBottom);
  }
  
  protected final boolean boundsMatch(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    return (this.mLeft == paramFloat1 && this.mTop == paramFloat2 && this.mRight == paramFloat3 && this.mBottom == paramFloat4);
  }
  
  public final boolean clipBoundsMatch(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    return (this.mClipLeft == paramFloat1 && this.mClipTop == paramFloat2 && this.mClipRight == paramFloat3 && this.mClipBottom == paramFloat4);
  }
  
  public final void debugDraw(FlatViewGroup paramFlatViewGroup, Canvas paramCanvas) {
    onDebugDraw(paramFlatViewGroup, paramCanvas);
  }
  
  protected void debugDrawCautionHighlight(Canvas paramCanvas, String paramString) {
    debugDrawHighlightRect(paramCanvas, sDebugHighlightYellow, paramString);
  }
  
  protected void debugDrawWarningHighlight(Canvas paramCanvas, String paramString) {
    debugDrawHighlightRect(paramCanvas, sDebugHighlightRed, paramString);
  }
  
  public void draw(FlatViewGroup paramFlatViewGroup, Canvas paramCanvas) {
    onPreDraw(paramFlatViewGroup, paramCanvas);
    if (this.mNeedsClipping && shouldClip()) {
      paramCanvas.save(2);
      applyClipping(paramCanvas);
      onDraw(paramCanvas);
      paramCanvas.restore();
      return;
    } 
    onDraw(paramCanvas);
  }
  
  public final void freeze() {
    this.mFrozen = true;
  }
  
  public final float getBottom() {
    return this.mBottom;
  }
  
  public final float getClipBottom() {
    return this.mClipBottom;
  }
  
  public final float getClipLeft() {
    return this.mClipLeft;
  }
  
  public final float getClipRight() {
    return this.mClipRight;
  }
  
  public final float getClipTop() {
    return this.mClipTop;
  }
  
  protected String getDebugName() {
    return getClass().getSimpleName().substring(4);
  }
  
  public final float getLeft() {
    return this.mLeft;
  }
  
  public final float getRight() {
    return this.mRight;
  }
  
  public final float getTop() {
    return this.mTop;
  }
  
  public final boolean isFrozen() {
    return this.mFrozen;
  }
  
  public final AbstractDrawCommand mutableCopy() {
    try {
      AbstractDrawCommand abstractDrawCommand = (AbstractDrawCommand)clone();
      abstractDrawCommand.mFrozen = false;
      return abstractDrawCommand;
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new RuntimeException(cloneNotSupportedException);
    } 
  }
  
  protected void onBoundsChanged() {}
  
  protected void onDebugDraw(FlatViewGroup paramFlatViewGroup, Canvas paramCanvas) {
    paramFlatViewGroup.debugDrawNamedRect(paramCanvas, getDebugBorderColor(), getDebugName(), this.mLeft, this.mTop, this.mRight, this.mBottom);
  }
  
  protected void onDebugDrawHighlight(Canvas paramCanvas) {}
  
  protected abstract void onDraw(Canvas paramCanvas);
  
  protected void onPreDraw(FlatViewGroup paramFlatViewGroup, Canvas paramCanvas) {}
  
  protected final void setBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.mLeft = paramFloat1;
    this.mTop = paramFloat2;
    this.mRight = paramFloat3;
    this.mBottom = paramFloat4;
    onBoundsChanged();
  }
  
  protected final void setClipBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    boolean bool;
    this.mClipLeft = paramFloat1;
    this.mClipTop = paramFloat2;
    this.mClipRight = paramFloat3;
    this.mClipBottom = paramFloat4;
    if (this.mClipLeft != Float.NEGATIVE_INFINITY) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mNeedsClipping = bool;
  }
  
  protected boolean shouldClip() {
    return (this.mLeft < getClipLeft() || this.mTop < getClipTop() || this.mRight > getClipRight() || this.mBottom > getClipBottom());
  }
  
  public AbstractDrawCommand updateBoundsAndFreeze(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    if (this.mFrozen) {
      boolean bool1 = boundsMatch(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      boolean bool2 = clipBoundsMatch(paramFloat5, paramFloat6, paramFloat7, paramFloat8);
      if (bool1 && bool2)
        return this; 
      try {
        AbstractDrawCommand abstractDrawCommand = (AbstractDrawCommand)clone();
        if (!bool1)
          abstractDrawCommand.setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4); 
        if (!bool2)
          abstractDrawCommand.setClipBounds(paramFloat5, paramFloat6, paramFloat7, paramFloat8); 
        return abstractDrawCommand;
      } catch (CloneNotSupportedException cloneNotSupportedException) {
        throw new RuntimeException(cloneNotSupportedException);
      } 
    } 
    setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    setClipBounds(paramFloat5, paramFloat6, paramFloat7, paramFloat8);
    this.mFrozen = true;
    return this;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\AbstractDrawCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */