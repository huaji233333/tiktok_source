package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;

final class DrawBorder extends AbstractDrawBorder {
  private static final Paint PAINT = new Paint(1);
  
  private static final float[] TMP_FLOAT_ARRAY = new float[4];
  
  private int mBackgroundColor;
  
  private int mBorderBottomColor;
  
  private float mBorderBottomWidth;
  
  private int mBorderLeftColor;
  
  private float mBorderLeftWidth;
  
  private int mBorderRightColor;
  
  private float mBorderRightWidth;
  
  private int mBorderStyle;
  
  private int mBorderTopColor;
  
  private float mBorderTopWidth;
  
  private DashPathEffect mPathEffectForBorderStyle;
  
  private Path mPathForBorder;
  
  private static DashPathEffect createDashPathEffect(float paramFloat) {
    for (int i = 0; i < 4; i++)
      TMP_FLOAT_ARRAY[i] = paramFloat; 
    return new DashPathEffect(TMP_FLOAT_ARRAY, 0.0F);
  }
  
  private void drawRectangularBorders(Canvas paramCanvas) {
    int j = getBorderColor();
    float f11 = getBorderWidth();
    float f1 = getTop();
    float f2 = resolveWidth(this.mBorderTopWidth, f11);
    float f3 = f1 + f2;
    int m = resolveBorderColor(4, this.mBorderTopColor, j);
    float f4 = getBottom();
    float f5 = resolveWidth(this.mBorderBottomWidth, f11);
    float f6 = f4 - f5;
    int k = resolveBorderColor(16, this.mBorderBottomColor, j);
    float f7 = getLeft();
    float f8 = resolveWidth(this.mBorderLeftWidth, f11);
    float f9 = f7 + f8;
    int i = resolveBorderColor(2, this.mBorderLeftColor, j);
    float f10 = getRight();
    f11 = resolveWidth(this.mBorderRightWidth, f11);
    float f12 = f10 - f11;
    j = resolveBorderColor(8, this.mBorderRightColor, j);
    int n = fastBorderCompatibleColorOrZero(f8, f2, f11, f5, i, m, j, k);
    if (n != 0) {
      if (Color.alpha(n) != 0) {
        if (Color.alpha(this.mBackgroundColor) != 0) {
          PAINT.setColor(this.mBackgroundColor);
          if (Color.alpha(n) == 255) {
            paramCanvas.drawRect(f9, f3, f12, f6, PAINT);
          } else {
            paramCanvas.drawRect(f7, f1, f10, f4, PAINT);
          } 
        } 
        PAINT.setColor(n);
        if (f8 > 0.0F)
          paramCanvas.drawRect(f7, f1, f9, f6, PAINT); 
        if (f2 > 0.0F)
          paramCanvas.drawRect(f9, f1, f10, f3, PAINT); 
        if (f11 > 0.0F)
          paramCanvas.drawRect(f12, f3, f10, f4, PAINT); 
        if (f5 > 0.0F) {
          paramCanvas.drawRect(f7, f6, f12, f4, PAINT);
          return;
        } 
      } 
    } else {
      if (this.mPathForBorder == null)
        this.mPathForBorder = new Path(); 
      if (Color.alpha(this.mBackgroundColor) != 0) {
        PAINT.setColor(this.mBackgroundColor);
        paramCanvas.drawRect(f7, f1, f10, f4, PAINT);
      } 
      if (f2 != 0.0F && Color.alpha(m) != 0) {
        PAINT.setColor(m);
        updatePathForTopBorder(this.mPathForBorder, f1, f3, f7, f9, f10, f12);
        paramCanvas.drawPath(this.mPathForBorder, PAINT);
      } 
      if (f5 != 0.0F && Color.alpha(k) != 0) {
        PAINT.setColor(k);
        updatePathForBottomBorder(this.mPathForBorder, f4, f6, f7, f9, f10, f12);
        paramCanvas.drawPath(this.mPathForBorder, PAINT);
      } 
      if (f8 != 0.0F && Color.alpha(i) != 0) {
        PAINT.setColor(i);
        updatePathForLeftBorder(this.mPathForBorder, f1, f3, f4, f6, f7, f9);
        paramCanvas.drawPath(this.mPathForBorder, PAINT);
      } 
      if (f11 != 0.0F && Color.alpha(j) != 0) {
        PAINT.setColor(j);
        updatePathForRightBorder(this.mPathForBorder, f1, f3, f4, f6, f10, f12);
        paramCanvas.drawPath(this.mPathForBorder, PAINT);
      } 
    } 
  }
  
  private void drawRoundedBorders(Canvas paramCanvas) {
    int i = this.mBackgroundColor;
    if (i != 0) {
      PAINT.setColor(i);
      paramCanvas.drawPath(getPathForBorderRadius(), PAINT);
    } 
    drawBorders(paramCanvas);
  }
  
  private static int fastBorderCompatibleColorOrZero(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    byte b1;
    byte b2;
    int j = -1;
    if (paramFloat1 > 0.0F) {
      i = paramInt1;
    } else {
      i = -1;
    } 
    if (paramFloat2 > 0.0F) {
      b1 = paramInt2;
    } else {
      b1 = -1;
    } 
    if (paramFloat3 > 0.0F) {
      b2 = paramInt3;
    } else {
      b2 = -1;
    } 
    if (paramFloat4 > 0.0F)
      j = paramInt4; 
    int i = j & i & b1 & b2;
    if (paramFloat1 <= 0.0F)
      paramInt1 = 0; 
    if (paramFloat2 <= 0.0F)
      paramInt2 = 0; 
    if (paramFloat3 <= 0.0F)
      paramInt3 = 0; 
    if (paramFloat4 <= 0.0F)
      paramInt4 = 0; 
    return (i == (paramInt1 | paramInt2 | paramInt3 | paramInt4)) ? i : 0;
  }
  
  private int resolveBorderColor(int paramInt1, int paramInt2, int paramInt3) {
    return isFlagSet(paramInt1) ? paramInt2 : paramInt3;
  }
  
  private static float resolveWidth(float paramFloat1, float paramFloat2) {
    return (paramFloat1 != 0.0F) ? ((paramFloat1 != paramFloat1) ? paramFloat2 : paramFloat1) : paramFloat2;
  }
  
  private static void updatePathForBottomBorder(Path paramPath, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    paramPath.reset();
    paramPath.moveTo(paramFloat3, paramFloat1);
    paramPath.lineTo(paramFloat5, paramFloat1);
    paramPath.lineTo(paramFloat6, paramFloat2);
    paramPath.lineTo(paramFloat4, paramFloat2);
    paramPath.lineTo(paramFloat3, paramFloat1);
  }
  
  private static void updatePathForLeftBorder(Path paramPath, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    paramPath.reset();
    paramPath.moveTo(paramFloat5, paramFloat1);
    paramPath.lineTo(paramFloat6, paramFloat2);
    paramPath.lineTo(paramFloat6, paramFloat4);
    paramPath.lineTo(paramFloat5, paramFloat3);
    paramPath.lineTo(paramFloat5, paramFloat1);
  }
  
  private static void updatePathForRightBorder(Path paramPath, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    paramPath.reset();
    paramPath.moveTo(paramFloat5, paramFloat1);
    paramPath.lineTo(paramFloat5, paramFloat3);
    paramPath.lineTo(paramFloat6, paramFloat4);
    paramPath.lineTo(paramFloat6, paramFloat2);
    paramPath.lineTo(paramFloat5, paramFloat1);
  }
  
  private static void updatePathForTopBorder(Path paramPath, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    paramPath.reset();
    paramPath.moveTo(paramFloat3, paramFloat1);
    paramPath.lineTo(paramFloat4, paramFloat2);
    paramPath.lineTo(paramFloat6, paramFloat2);
    paramPath.lineTo(paramFloat5, paramFloat1);
    paramPath.lineTo(paramFloat3, paramFloat1);
  }
  
  public final int getBackgroundColor() {
    return this.mBackgroundColor;
  }
  
  public final int getBorderColor(int paramInt) {
    int i = getBorderColor();
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt != 3) {
            if (paramInt != 8);
            return i;
          } 
          return resolveBorderColor(16, this.mBorderBottomColor, i);
        } 
        return resolveBorderColor(8, this.mBorderRightColor, i);
      } 
      return resolveBorderColor(4, this.mBorderTopColor, i);
    } 
    return resolveBorderColor(2, this.mBorderLeftColor, i);
  }
  
  public final float getBorderWidth(int paramInt) {
    return (paramInt != 0) ? ((paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 3) ? ((paramInt != 8) ? 0.0F : getBorderWidth()) : this.mBorderBottomWidth) : this.mBorderRightWidth) : this.mBorderTopWidth) : this.mBorderLeftWidth;
  }
  
  protected final DashPathEffect getPathEffectForBorderStyle() {
    if (isFlagSet(32)) {
      int i = this.mBorderStyle;
      if (i != 1) {
        if (i != 2) {
          this.mPathEffectForBorderStyle = null;
        } else {
          this.mPathEffectForBorderStyle = createDashPathEffect(getBorderWidth() * 3.0F);
        } 
      } else {
        this.mPathEffectForBorderStyle = createDashPathEffect(getBorderWidth());
      } 
      resetFlag(32);
    } 
    return this.mPathEffectForBorderStyle;
  }
  
  protected final void onDraw(Canvas paramCanvas) {
    if (getBorderRadius() >= 0.5F || getPathEffectForBorderStyle() != null) {
      drawRoundedBorders(paramCanvas);
      return;
    } 
    drawRectangularBorders(paramCanvas);
  }
  
  public final void resetBorderColor(int paramInt) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt != 3) {
            if (paramInt != 8)
              return; 
            setBorderColor(-16777216);
            return;
          } 
          resetFlag(16);
          return;
        } 
        resetFlag(8);
        return;
      } 
      resetFlag(4);
      return;
    } 
    resetFlag(2);
  }
  
  public final void setBackgroundColor(int paramInt) {
    this.mBackgroundColor = paramInt;
  }
  
  public final void setBorderColor(int paramInt1, int paramInt2) {
    if (paramInt1 != 0) {
      if (paramInt1 != 1) {
        if (paramInt1 != 2) {
          if (paramInt1 != 3) {
            if (paramInt1 != 8)
              return; 
            setBorderColor(paramInt2);
            return;
          } 
          this.mBorderBottomColor = paramInt2;
          setFlag(16);
          return;
        } 
        this.mBorderRightColor = paramInt2;
        setFlag(8);
        return;
      } 
      this.mBorderTopColor = paramInt2;
      setFlag(4);
      return;
    } 
    this.mBorderLeftColor = paramInt2;
    setFlag(2);
  }
  
  public final void setBorderStyle(String paramString) {
    if ("dotted".equals(paramString)) {
      this.mBorderStyle = 1;
    } else if ("dashed".equals(paramString)) {
      this.mBorderStyle = 2;
    } else {
      this.mBorderStyle = 0;
    } 
    setFlag(32);
  }
  
  public final void setBorderWidth(int paramInt, float paramFloat) {
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2) {
          if (paramInt != 3) {
            if (paramInt != 8)
              return; 
            setBorderWidth(paramFloat);
            return;
          } 
          this.mBorderBottomWidth = paramFloat;
          return;
        } 
        this.mBorderRightWidth = paramFloat;
        return;
      } 
      this.mBorderTopWidth = paramFloat;
      return;
    } 
    this.mBorderLeftWidth = paramFloat;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\DrawBorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */