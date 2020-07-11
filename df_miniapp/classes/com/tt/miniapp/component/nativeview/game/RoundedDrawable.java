package com.tt.miniapp.component.nativeview.game;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView;
import java.util.HashSet;

public class RoundedDrawable extends Drawable {
  private final Bitmap mBitmap;
  
  private final int mBitmapHeight;
  
  private final Paint mBitmapPaint;
  
  private final RectF mBitmapRect = new RectF();
  
  private final int mBitmapWidth;
  
  private ColorStateList mBorderColor = ColorStateList.valueOf(-16777216);
  
  private final Paint mBorderPaint;
  
  private final RectF mBorderRect = new RectF();
  
  private float mBorderWidth = 0.0F;
  
  private final RectF mBounds = new RectF();
  
  private float mCornerRadius = 0.0F;
  
  private final boolean[] mCornersRounded = new boolean[] { true, true, true, true };
  
  private final RectF mDrawableRect = new RectF();
  
  private boolean mOval = false;
  
  private boolean mRebuildShader = true;
  
  private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;
  
  private final Matrix mShaderMatrix = new Matrix();
  
  private final RectF mSquareCornersRect = new RectF();
  
  private Shader.TileMode mTileModeX = Shader.TileMode.CLAMP;
  
  private Shader.TileMode mTileModeY = Shader.TileMode.CLAMP;
  
  public RoundedDrawable(Bitmap paramBitmap) {
    this.mBitmap = paramBitmap;
    this.mBitmapWidth = paramBitmap.getWidth();
    this.mBitmapHeight = paramBitmap.getHeight();
    this.mBitmapRect.set(0.0F, 0.0F, this.mBitmapWidth, this.mBitmapHeight);
    this.mBitmapPaint = new Paint();
    this.mBitmapPaint.setStyle(Paint.Style.FILL);
    this.mBitmapPaint.setAntiAlias(true);
    this.mBorderPaint = new Paint();
    this.mBorderPaint.setStyle(Paint.Style.STROKE);
    this.mBorderPaint.setAntiAlias(true);
    this.mBorderPaint.setColor(this.mBorderColor.getColorForState(getState(), -16777216));
    this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
  }
  
  private static boolean all(boolean[] paramArrayOfboolean) {
    int j = paramArrayOfboolean.length;
    for (int i = 0; i < j; i++) {
      if (paramArrayOfboolean[i])
        return false; 
    } 
    return true;
  }
  
  private static boolean any(boolean[] paramArrayOfboolean) {
    int j = paramArrayOfboolean.length;
    for (int i = 0; i < j; i++) {
      if (paramArrayOfboolean[i])
        return true; 
    } 
    return false;
  }
  
  public static Bitmap drawableToBitmap(Drawable paramDrawable) {
    if (paramDrawable instanceof BitmapDrawable)
      return ((BitmapDrawable)paramDrawable).getBitmap(); 
    int i = Math.max(paramDrawable.getIntrinsicWidth(), 2);
    int j = Math.max(paramDrawable.getIntrinsicHeight(), 2);
    try {
      Bitmap bitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      paramDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
      return bitmap;
    } finally {
      paramDrawable = null;
    } 
  }
  
  public static RoundedDrawable fromBitmap(Bitmap paramBitmap) {
    return (paramBitmap != null) ? new RoundedDrawable(paramBitmap) : null;
  }
  
  public static Drawable fromDrawable(Drawable paramDrawable) {
    Drawable drawable = paramDrawable;
    if (paramDrawable != null) {
      LayerDrawable layerDrawable1;
      if (paramDrawable instanceof RoundedDrawable)
        return paramDrawable; 
      if (paramDrawable instanceof LayerDrawable) {
        layerDrawable1 = (LayerDrawable)paramDrawable;
        int j = layerDrawable1.getNumberOfLayers();
        for (int i = 0; i < j; i++) {
          drawable = layerDrawable1.getDrawable(i);
          layerDrawable1.setDrawableByLayerId(layerDrawable1.getId(i), fromDrawable(drawable));
        } 
        return (Drawable)layerDrawable1;
      } 
      Bitmap bitmap = drawableToBitmap((Drawable)layerDrawable1);
      LayerDrawable layerDrawable2 = layerDrawable1;
      if (bitmap != null)
        drawable = new RoundedDrawable(bitmap); 
    } 
    return drawable;
  }
  
  private static boolean only(int paramInt, boolean[] paramArrayOfboolean) {
    int j = paramArrayOfboolean.length;
    int i = 0;
    while (true) {
      boolean bool = true;
      if (i < j) {
        boolean bool1 = paramArrayOfboolean[i];
        if (i != paramInt)
          bool = false; 
        if (bool1 != bool)
          return false; 
        i++;
        continue;
      } 
      return true;
    } 
  }
  
  private void redrawBitmapForSquareCorners(Canvas paramCanvas) {
    if (all(this.mCornersRounded))
      return; 
    if (this.mCornerRadius == 0.0F)
      return; 
    float f1 = this.mDrawableRect.left;
    float f2 = this.mDrawableRect.top;
    float f3 = this.mDrawableRect.width() + f1;
    float f4 = this.mDrawableRect.height() + f2;
    float f5 = this.mCornerRadius;
    if (!this.mCornersRounded[0]) {
      this.mSquareCornersRect.set(f1, f2, f1 + f5, f2 + f5);
      paramCanvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
    } 
    if (!this.mCornersRounded[1]) {
      this.mSquareCornersRect.set(f3 - f5, f2, f3, f5);
      paramCanvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
    } 
    if (!this.mCornersRounded[3]) {
      this.mSquareCornersRect.set(f3 - f5, f4 - f5, f3, f4);
      paramCanvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
    } 
    if (!this.mCornersRounded[2]) {
      this.mSquareCornersRect.set(f1, f4 - f5, f5 + f1, f4);
      paramCanvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
    } 
  }
  
  private void redrawBorderForSquareCorners(Canvas paramCanvas) {
    if (all(this.mCornersRounded))
      return; 
    if (this.mCornerRadius == 0.0F)
      return; 
    float f1 = this.mDrawableRect.left;
    float f2 = this.mDrawableRect.top;
    float f3 = f1 + this.mDrawableRect.width();
    float f4 = f2 + this.mDrawableRect.height();
    float f5 = this.mCornerRadius;
    float f6 = this.mBorderWidth / 2.0F;
    if (!this.mCornersRounded[0]) {
      paramCanvas.drawLine(f1 - f6, f2, f1 + f5, f2, this.mBorderPaint);
      paramCanvas.drawLine(f1, f2 - f6, f1, f2 + f5, this.mBorderPaint);
    } 
    if (!this.mCornersRounded[1]) {
      paramCanvas.drawLine(f3 - f5 - f6, f2, f3, f2, this.mBorderPaint);
      paramCanvas.drawLine(f3, f2 - f6, f3, f2 + f5, this.mBorderPaint);
    } 
    if (!this.mCornersRounded[3]) {
      paramCanvas.drawLine(f3 - f5 - f6, f4, f3 + f6, f4, this.mBorderPaint);
      paramCanvas.drawLine(f3, f4 - f5, f3, f4, this.mBorderPaint);
    } 
    if (!this.mCornersRounded[2]) {
      paramCanvas.drawLine(f1 - f6, f4, f1 + f5, f4, this.mBorderPaint);
      paramCanvas.drawLine(f1, f4 - f5, f1, f4, this.mBorderPaint);
    } 
  }
  
  private void updateShaderMatrix() {
    int i = null.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 5) {
            if (i != 6) {
              if (i != 7) {
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, Matrix.ScaleToFit.CENTER);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                RectF rectF = this.mBorderRect;
                float f = this.mBorderWidth;
                rectF.inset(f / 2.0F, f / 2.0F);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
              } else {
                this.mBorderRect.set(this.mBounds);
                RectF rectF = this.mBorderRect;
                float f = this.mBorderWidth;
                rectF.inset(f / 2.0F, f / 2.0F);
                this.mShaderMatrix.reset();
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
              } 
            } else {
              this.mBorderRect.set(this.mBitmapRect);
              this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, Matrix.ScaleToFit.START);
              this.mShaderMatrix.mapRect(this.mBorderRect);
              RectF rectF = this.mBorderRect;
              float f = this.mBorderWidth;
              rectF.inset(f / 2.0F, f / 2.0F);
              this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
            } 
          } else {
            this.mBorderRect.set(this.mBitmapRect);
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, Matrix.ScaleToFit.END);
            this.mShaderMatrix.mapRect(this.mBorderRect);
            RectF rectF = this.mBorderRect;
            float f = this.mBorderWidth;
            rectF.inset(f / 2.0F, f / 2.0F);
            this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
          } 
        } else {
          this.mShaderMatrix.reset();
          if (this.mBitmapWidth <= this.mBounds.width() && this.mBitmapHeight <= this.mBounds.height()) {
            f1 = 1.0F;
          } else {
            f1 = Math.min(this.mBounds.width() / this.mBitmapWidth, this.mBounds.height() / this.mBitmapHeight);
          } 
          float f2 = (int)((this.mBounds.width() - this.mBitmapWidth * f1) * 0.5F + 0.5F);
          float f3 = (int)((this.mBounds.height() - this.mBitmapHeight * f1) * 0.5F + 0.5F);
          this.mShaderMatrix.setScale(f1, f1);
          this.mShaderMatrix.postTranslate(f2, f3);
          this.mBorderRect.set(this.mBitmapRect);
          this.mShaderMatrix.mapRect(this.mBorderRect);
          RectF rectF = this.mBorderRect;
          float f1 = this.mBorderWidth;
          rectF.inset(f1 / 2.0F, f1 / 2.0F);
          this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, Matrix.ScaleToFit.FILL);
        } 
      } else {
        this.mBorderRect.set(this.mBounds);
        RectF rectF = this.mBorderRect;
        float f1 = this.mBorderWidth;
        rectF.inset(f1 / 2.0F, f1 / 2.0F);
        this.mShaderMatrix.reset();
        float f2 = this.mBitmapWidth;
        float f3 = this.mBorderRect.height();
        float f4 = this.mBorderRect.width();
        float f5 = this.mBitmapHeight;
        f1 = 0.0F;
        if (f2 * f3 > f4 * f5) {
          f2 = this.mBorderRect.height() / this.mBitmapHeight;
          f3 = (this.mBorderRect.width() - this.mBitmapWidth * f2) * 0.5F;
        } else {
          f2 = this.mBorderRect.width() / this.mBitmapWidth;
          f1 = (this.mBorderRect.height() - this.mBitmapHeight * f2) * 0.5F;
          f3 = 0.0F;
        } 
        this.mShaderMatrix.setScale(f2, f2);
        Matrix matrix = this.mShaderMatrix;
        f2 = (int)(f3 + 0.5F);
        f3 = this.mBorderWidth;
        matrix.postTranslate(f2 + f3 / 2.0F, (int)(f1 + 0.5F) + f3 / 2.0F);
      } 
    } else {
      this.mBorderRect.set(this.mBounds);
      RectF rectF = this.mBorderRect;
      float f = this.mBorderWidth;
      rectF.inset(f / 2.0F, f / 2.0F);
      this.mShaderMatrix.reset();
      this.mShaderMatrix.setTranslate((int)((this.mBorderRect.width() - this.mBitmapWidth) * 0.5F + 0.5F), (int)((this.mBorderRect.height() - this.mBitmapHeight) * 0.5F + 0.5F));
    } 
    this.mDrawableRect.set(this.mBorderRect);
    this.mRebuildShader = true;
  }
  
  public void draw(Canvas paramCanvas) {
    if (this.mRebuildShader) {
      BitmapShader bitmapShader = new BitmapShader(this.mBitmap, this.mTileModeX, this.mTileModeY);
      if (this.mTileModeX == Shader.TileMode.CLAMP && this.mTileModeY == Shader.TileMode.CLAMP)
        bitmapShader.setLocalMatrix(this.mShaderMatrix); 
      this.mBitmapPaint.setShader((Shader)bitmapShader);
      this.mRebuildShader = false;
    } 
    if (this.mOval) {
      if (this.mBorderWidth > 0.0F) {
        paramCanvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
        paramCanvas.drawOval(this.mBorderRect, this.mBorderPaint);
        return;
      } 
      paramCanvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
      return;
    } 
    if (any(this.mCornersRounded)) {
      float f = this.mCornerRadius;
      if (this.mBorderWidth > 0.0F) {
        paramCanvas.drawRoundRect(this.mDrawableRect, f, f, this.mBitmapPaint);
        paramCanvas.drawRoundRect(this.mBorderRect, f, f, this.mBorderPaint);
        redrawBitmapForSquareCorners(paramCanvas);
        redrawBorderForSquareCorners(paramCanvas);
        return;
      } 
      paramCanvas.drawRoundRect(this.mDrawableRect, f, f, this.mBitmapPaint);
      redrawBitmapForSquareCorners(paramCanvas);
      return;
    } 
    paramCanvas.drawRect(this.mDrawableRect, this.mBitmapPaint);
    if (this.mBorderWidth > 0.0F)
      paramCanvas.drawRect(this.mBorderRect, this.mBorderPaint); 
  }
  
  public int getAlpha() {
    return this.mBitmapPaint.getAlpha();
  }
  
  public int getBorderColor() {
    return this.mBorderColor.getDefaultColor();
  }
  
  public ColorStateList getBorderColors() {
    return this.mBorderColor;
  }
  
  public float getBorderWidth() {
    return this.mBorderWidth;
  }
  
  public ColorFilter getColorFilter() {
    return this.mBitmapPaint.getColorFilter();
  }
  
  public float getCornerRadius() {
    return this.mCornerRadius;
  }
  
  public float getCornerRadius(int paramInt) {
    return this.mCornersRounded[paramInt] ? this.mCornerRadius : 0.0F;
  }
  
  public int getIntrinsicHeight() {
    return this.mBitmapHeight;
  }
  
  public int getIntrinsicWidth() {
    return this.mBitmapWidth;
  }
  
  public int getOpacity() {
    return -3;
  }
  
  public ImageView.ScaleType getScaleType() {
    return this.mScaleType;
  }
  
  public Bitmap getSourceBitmap() {
    return this.mBitmap;
  }
  
  public Shader.TileMode getTileModeX() {
    return this.mTileModeX;
  }
  
  public Shader.TileMode getTileModeY() {
    return this.mTileModeY;
  }
  
  public boolean isOval() {
    return this.mOval;
  }
  
  public boolean isStateful() {
    return this.mBorderColor.isStateful();
  }
  
  protected void onBoundsChange(Rect paramRect) {
    super.onBoundsChange(paramRect);
    this.mBounds.set(paramRect);
    updateShaderMatrix();
  }
  
  protected boolean onStateChange(int[] paramArrayOfint) {
    int i = this.mBorderColor.getColorForState(paramArrayOfint, 0);
    if (this.mBorderPaint.getColor() != i) {
      this.mBorderPaint.setColor(i);
      return true;
    } 
    return super.onStateChange(paramArrayOfint);
  }
  
  public void setAlpha(int paramInt) {
    this.mBitmapPaint.setAlpha(paramInt);
    invalidateSelf();
  }
  
  public RoundedDrawable setBorderColor(int paramInt) {
    return setBorderColor(ColorStateList.valueOf(paramInt));
  }
  
  public RoundedDrawable setBorderColor(ColorStateList paramColorStateList) {
    if (paramColorStateList == null)
      paramColorStateList = ColorStateList.valueOf(0); 
    this.mBorderColor = paramColorStateList;
    this.mBorderPaint.setColor(this.mBorderColor.getColorForState(getState(), -16777216));
    return this;
  }
  
  public RoundedDrawable setBorderWidth(float paramFloat) {
    this.mBorderWidth = paramFloat;
    this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
    return this;
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {
    this.mBitmapPaint.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
  
  public RoundedDrawable setCornerRadius(float paramFloat) {
    setCornerRadius(paramFloat, paramFloat, paramFloat, paramFloat);
    return this;
  }
  
  public RoundedDrawable setCornerRadius(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    HashSet<Float> hashSet = new HashSet(4);
    hashSet.add(Float.valueOf(paramFloat1));
    hashSet.add(Float.valueOf(paramFloat2));
    hashSet.add(Float.valueOf(paramFloat3));
    hashSet.add(Float.valueOf(paramFloat4));
    hashSet.remove(Float.valueOf(0.0F));
    if (hashSet.size() <= 1) {
      if (!hashSet.isEmpty()) {
        float f = ((Float)hashSet.iterator().next()).floatValue();
        if (!Float.isInfinite(f) && !Float.isNaN(f) && f >= 0.0F) {
          this.mCornerRadius = f;
        } else {
          StringBuilder stringBuilder = new StringBuilder("Invalid radius value: ");
          stringBuilder.append(f);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        this.mCornerRadius = 0.0F;
      } 
      boolean[] arrayOfBoolean = this.mCornersRounded;
      boolean bool2 = false;
      if (paramFloat1 > 0.0F) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      arrayOfBoolean[0] = bool1;
      arrayOfBoolean = this.mCornersRounded;
      if (paramFloat2 > 0.0F) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      arrayOfBoolean[1] = bool1;
      arrayOfBoolean = this.mCornersRounded;
      if (paramFloat3 > 0.0F) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      arrayOfBoolean[3] = bool1;
      arrayOfBoolean = this.mCornersRounded;
      boolean bool1 = bool2;
      if (paramFloat4 > 0.0F)
        bool1 = true; 
      arrayOfBoolean[2] = bool1;
      return this;
    } 
    throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
  }
  
  public RoundedDrawable setCornerRadius(int paramInt, float paramFloat) {
    if (paramFloat != 0.0F) {
      float f = this.mCornerRadius;
      if (f != 0.0F && f != paramFloat)
        throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported."); 
    } 
    if (paramFloat == 0.0F) {
      if (only(paramInt, this.mCornersRounded))
        this.mCornerRadius = 0.0F; 
      this.mCornersRounded[paramInt] = false;
      return this;
    } 
    if (this.mCornerRadius == 0.0F)
      this.mCornerRadius = paramFloat; 
    this.mCornersRounded[paramInt] = true;
    return this;
  }
  
  public void setDither(boolean paramBoolean) {
    this.mBitmapPaint.setDither(paramBoolean);
    invalidateSelf();
  }
  
  public void setFilterBitmap(boolean paramBoolean) {
    this.mBitmapPaint.setFilterBitmap(paramBoolean);
    invalidateSelf();
  }
  
  public RoundedDrawable setOval(boolean paramBoolean) {
    this.mOval = paramBoolean;
    return this;
  }
  
  public RoundedDrawable setScaleType(ImageView.ScaleType paramScaleType) {
    ImageView.ScaleType scaleType = paramScaleType;
    if (paramScaleType == null)
      scaleType = ImageView.ScaleType.FIT_CENTER; 
    if (this.mScaleType != scaleType) {
      this.mScaleType = scaleType;
      updateShaderMatrix();
    } 
    return this;
  }
  
  public RoundedDrawable setTileModeX(Shader.TileMode paramTileMode) {
    if (this.mTileModeX != paramTileMode) {
      this.mTileModeX = paramTileMode;
      this.mRebuildShader = true;
      invalidateSelf();
    } 
    return this;
  }
  
  public RoundedDrawable setTileModeY(Shader.TileMode paramTileMode) {
    if (this.mTileModeY != paramTileMode) {
      this.mTileModeY = paramTileMode;
      this.mRebuildShader = true;
      invalidateSelf();
    } 
    return this;
  }
  
  public Bitmap toBitmap() {
    return drawableToBitmap(this);
  }
  
  public static class Corner {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\RoundedDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */