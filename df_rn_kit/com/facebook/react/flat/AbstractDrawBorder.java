package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;

abstract class AbstractDrawBorder extends AbstractDrawCommand {
  private static final Paint PAINT = new Paint(1);
  
  private static final RectF TMP_RECT = new RectF();
  
  private int mBorderColor = -16777216;
  
  private float mBorderRadius;
  
  private float mBorderWidth;
  
  private Path mPathForBorderRadius;
  
  private int mSetPropertiesFlag;
  
  static {
    PAINT.setStyle(Paint.Style.STROKE);
  }
  
  protected final void drawBorders(Canvas paramCanvas) {
    if (this.mBorderWidth < 0.5F)
      return; 
    int i = this.mBorderColor;
    if (i == 0)
      return; 
    PAINT.setColor(i);
    PAINT.setStrokeWidth(this.mBorderWidth);
    PAINT.setPathEffect(getPathEffectForBorderStyle());
    paramCanvas.drawPath(getPathForBorderRadius(), PAINT);
  }
  
  public final int getBorderColor() {
    return this.mBorderColor;
  }
  
  public final float getBorderRadius() {
    return this.mBorderRadius;
  }
  
  public final float getBorderWidth() {
    return this.mBorderWidth;
  }
  
  protected PathEffect getPathEffectForBorderStyle() {
    return null;
  }
  
  protected final Path getPathForBorderRadius() {
    if (isFlagSet(1)) {
      if (this.mPathForBorderRadius == null)
        this.mPathForBorderRadius = new Path(); 
      updatePath(this.mPathForBorderRadius, this.mBorderWidth * 0.5F);
      resetFlag(1);
    } 
    return this.mPathForBorderRadius;
  }
  
  protected final boolean isFlagSet(int paramInt) {
    return ((this.mSetPropertiesFlag & paramInt) == paramInt);
  }
  
  protected void onBoundsChanged() {
    setFlag(1);
  }
  
  protected final void resetFlag(int paramInt) {
    this.mSetPropertiesFlag = (paramInt ^ 0xFFFFFFFF) & this.mSetPropertiesFlag;
  }
  
  public final void setBorderColor(int paramInt) {
    this.mBorderColor = paramInt;
  }
  
  public void setBorderRadius(float paramFloat) {
    this.mBorderRadius = paramFloat;
    setFlag(1);
  }
  
  public final void setBorderWidth(float paramFloat) {
    this.mBorderWidth = paramFloat;
    setFlag(1);
  }
  
  protected final void setFlag(int paramInt) {
    this.mSetPropertiesFlag = paramInt | this.mSetPropertiesFlag;
  }
  
  protected final void updatePath(Path paramPath, float paramFloat) {
    paramPath.reset();
    TMP_RECT.set(getLeft() + paramFloat, getTop() + paramFloat, getRight() - paramFloat, getBottom() - paramFloat);
    RectF rectF = TMP_RECT;
    paramFloat = this.mBorderRadius;
    paramPath.addRoundRect(rectF, paramFloat, paramFloat, Path.Direction.CW);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\AbstractDrawBorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */