package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

final class DrawView extends AbstractDrawCommand {
  public static final DrawView[] EMPTY_ARRAY = new DrawView[0];
  
  private final RectF TMP_RECT = new RectF();
  
  private float mClipRadius;
  
  float mLogicalBottom;
  
  float mLogicalLeft;
  
  float mLogicalRight;
  
  float mLogicalTop;
  
  private Path mPath;
  
  boolean mWasMounted;
  
  final int reactTag;
  
  public DrawView(int paramInt) {
    this.reactTag = paramInt;
  }
  
  private boolean logicalBoundsMatch(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    return (paramFloat1 == this.mLogicalLeft && paramFloat2 == this.mLogicalTop && paramFloat3 == this.mLogicalRight && paramFloat4 == this.mLogicalBottom);
  }
  
  private void setLogicalBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.mLogicalLeft = paramFloat1;
    this.mLogicalTop = paramFloat2;
    this.mLogicalRight = paramFloat3;
    this.mLogicalBottom = paramFloat4;
  }
  
  private void updateClipPath() {
    this.mPath = new Path();
    this.TMP_RECT.set(getLeft(), getTop(), getRight(), getBottom());
    Path path = this.mPath;
    RectF rectF = this.TMP_RECT;
    float f = this.mClipRadius;
    path.addRoundRect(rectF, f, f, Path.Direction.CW);
  }
  
  protected final void applyClipping(Canvas paramCanvas) {
    if (this.mClipRadius > 0.5F) {
      paramCanvas.clipPath(this.mPath);
      return;
    } 
    super.applyClipping(paramCanvas);
  }
  
  public final DrawView collectDrawView(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13) {
    boolean bool;
    if (!isFrozen()) {
      setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      setClipBounds(paramFloat9, paramFloat10, paramFloat11, paramFloat12);
      setClipRadius(paramFloat13);
      setLogicalBounds(paramFloat5, paramFloat6, paramFloat7, paramFloat8);
      freeze();
      return this;
    } 
    boolean bool1 = boundsMatch(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    boolean bool2 = clipBoundsMatch(paramFloat9, paramFloat10, paramFloat11, paramFloat12);
    if (this.mClipRadius == paramFloat13) {
      bool = true;
    } else {
      bool = false;
    } 
    boolean bool3 = logicalBoundsMatch(paramFloat5, paramFloat6, paramFloat7, paramFloat8);
    if (bool1 && bool2 && bool && bool3)
      return this; 
    DrawView drawView = (DrawView)mutableCopy();
    if (!bool1)
      drawView.setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4); 
    if (!bool2)
      drawView.setClipBounds(paramFloat9, paramFloat10, paramFloat11, paramFloat12); 
    if (!bool3)
      drawView.setLogicalBounds(paramFloat5, paramFloat6, paramFloat7, paramFloat8); 
    if (!bool || !bool1)
      drawView.setClipRadius(paramFloat13); 
    drawView.mWasMounted = false;
    drawView.freeze();
    return drawView;
  }
  
  public final void draw(FlatViewGroup paramFlatViewGroup, Canvas paramCanvas) {
    onPreDraw(paramFlatViewGroup, paramCanvas);
    if (this.mNeedsClipping || this.mClipRadius > 0.5F) {
      paramCanvas.save(2);
      applyClipping(paramCanvas);
      paramFlatViewGroup.drawNextChild(paramCanvas);
      paramCanvas.restore();
      return;
    } 
    paramFlatViewGroup.drawNextChild(paramCanvas);
  }
  
  protected final void onDebugDraw(FlatViewGroup paramFlatViewGroup, Canvas paramCanvas) {
    paramFlatViewGroup.debugDrawNextChild(paramCanvas);
  }
  
  protected final void onDebugDrawHighlight(Canvas paramCanvas) {
    if (this.mPath != null) {
      StringBuilder stringBuilder = new StringBuilder("borderRadius: ");
      stringBuilder.append(this.mClipRadius);
      debugDrawWarningHighlight(paramCanvas, stringBuilder.toString());
      return;
    } 
    if (!boundsMatch(this.mLogicalLeft, this.mLogicalTop, this.mLogicalRight, this.mLogicalBottom)) {
      StringBuilder stringBuilder = new StringBuilder("Overflow: { ");
      int i = 0;
      float[] arrayOfFloat = new float[4];
      arrayOfFloat[0] = getLeft() - this.mLogicalLeft;
      arrayOfFloat[1] = getTop() - this.mLogicalTop;
      arrayOfFloat[2] = this.mLogicalRight - getRight();
      arrayOfFloat[3] = this.mLogicalBottom - getBottom();
      while (i < 4) {
        if (arrayOfFloat[i] != 0.0F) {
          (new String[4])[0] = "left: ";
          (new String[4])[1] = "top: ";
          (new String[4])[2] = "right: ";
          (new String[4])[3] = "bottom: ";
          stringBuilder.append((new String[4])[i]);
          stringBuilder.append(arrayOfFloat[i]);
          stringBuilder.append(", ");
        } 
        i++;
      } 
      stringBuilder.append("}");
      debugDrawCautionHighlight(paramCanvas, stringBuilder.toString());
    } 
  }
  
  protected final void onDraw(Canvas paramCanvas) {}
  
  final void setClipRadius(float paramFloat) {
    this.mClipRadius = paramFloat;
    if (paramFloat > 0.5F) {
      updateClipPath();
      return;
    } 
    this.mPath = null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\DrawView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */