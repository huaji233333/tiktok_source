package com.tt.miniapp.component.nativeview.game;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedImageView extends ImageView {
  public static final Shader.TileMode DEFAULT_TILE_MODE = Shader.TileMode.CLAMP;
  
  private static final ImageView.ScaleType[] SCALE_TYPES = new ImageView.ScaleType[] { ImageView.ScaleType.MATRIX, ImageView.ScaleType.FIT_XY, ImageView.ScaleType.FIT_START, ImageView.ScaleType.FIT_CENTER, ImageView.ScaleType.FIT_END, ImageView.ScaleType.CENTER, ImageView.ScaleType.CENTER_CROP, ImageView.ScaleType.CENTER_INSIDE };
  
  private Drawable mBackgroundDrawable;
  
  private int mBackgroundResource;
  
  private ColorStateList mBorderColor = ColorStateList.valueOf(-16777216);
  
  private float mBorderWidth = 0.0F;
  
  private ColorFilter mColorFilter = null;
  
  private boolean mColorMod = false;
  
  private final float[] mCornerRadii = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
  
  private Drawable mDrawable;
  
  private boolean mHasColorFilter = false;
  
  private boolean mIsOval = false;
  
  private boolean mMutateBackground = false;
  
  private int mResource;
  
  private ImageView.ScaleType mScaleType;
  
  private Shader.TileMode mTileModeX;
  
  private Shader.TileMode mTileModeY;
  
  public RoundedImageView(Context paramContext) {
    super(paramContext);
    Shader.TileMode tileMode = DEFAULT_TILE_MODE;
    this.mTileModeX = tileMode;
    this.mTileModeY = tileMode;
  }
  
  public RoundedImageView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public RoundedImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    Shader.TileMode tileMode = DEFAULT_TILE_MODE;
    this.mTileModeX = tileMode;
    this.mTileModeY = tileMode;
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 
          16843037, 2097283108, 2097283109, 2097283110, 2097283111, 2097283112, 2097283113, 2097283114, 2097283115, 2097283116, 
          2097283117, 2097283118, 2097283119 }, paramInt, 0);
    paramInt = typedArray.getInt(0, -1);
    if (paramInt >= 0) {
      setScaleType(SCALE_TYPES[paramInt]);
    } else {
      setScaleType(ImageView.ScaleType.FIT_CENTER);
    } 
    float f = typedArray.getDimensionPixelSize(3, -1);
    this.mCornerRadii[0] = typedArray.getDimensionPixelSize(6, -1);
    this.mCornerRadii[1] = typedArray.getDimensionPixelSize(7, -1);
    this.mCornerRadii[3] = typedArray.getDimensionPixelSize(5, -1);
    this.mCornerRadii[2] = typedArray.getDimensionPixelSize(4, -1);
    int j = this.mCornerRadii.length;
    paramInt = 0;
    int i = 0;
    while (paramInt < j) {
      float[] arrayOfFloat = this.mCornerRadii;
      if (arrayOfFloat[paramInt] < 0.0F) {
        arrayOfFloat[paramInt] = 0.0F;
      } else {
        i = 1;
      } 
      paramInt++;
    } 
    if (!i) {
      float f1 = f;
      if (f < 0.0F)
        f1 = 0.0F; 
      i = this.mCornerRadii.length;
      for (paramInt = 0; paramInt < i; paramInt++)
        this.mCornerRadii[paramInt] = f1; 
    } 
    this.mBorderWidth = typedArray.getDimensionPixelSize(2, -1);
    if (this.mBorderWidth < 0.0F)
      this.mBorderWidth = 0.0F; 
    this.mBorderColor = typedArray.getColorStateList(1);
    if (this.mBorderColor == null)
      this.mBorderColor = ColorStateList.valueOf(-16777216); 
    this.mMutateBackground = typedArray.getBoolean(8, false);
    this.mIsOval = typedArray.getBoolean(9, false);
    paramInt = typedArray.getInt(10, -2);
    if (paramInt != -2) {
      setTileModeX(parseTileMode(paramInt));
      setTileModeY(parseTileMode(paramInt));
    } 
    paramInt = typedArray.getInt(11, -2);
    if (paramInt != -2)
      setTileModeX(parseTileMode(paramInt)); 
    paramInt = typedArray.getInt(12, -2);
    if (paramInt != -2)
      setTileModeY(parseTileMode(paramInt)); 
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(true);
    if (this.mMutateBackground)
      super.setBackgroundDrawable(this.mBackgroundDrawable); 
    typedArray.recycle();
  }
  
  private void applyColorMod() {
    Drawable drawable = this.mDrawable;
    if (drawable != null && this.mColorMod) {
      this.mDrawable = drawable.mutate();
      if (this.mHasColorFilter)
        this.mDrawable.setColorFilter(this.mColorFilter); 
    } 
  }
  
  private static Shader.TileMode parseTileMode(int paramInt) {
    return (paramInt != 0) ? ((paramInt != 1) ? ((paramInt != 2) ? null : Shader.TileMode.MIRROR) : Shader.TileMode.REPEAT) : Shader.TileMode.CLAMP;
  }
  
  private Drawable resolveBackgroundResource() {
    Resources resources = getResources();
    Drawable drawable2 = null;
    if (resources == null)
      return null; 
    int i = this.mBackgroundResource;
    Drawable drawable1 = drawable2;
    if (i != 0)
      try {
        drawable1 = resources.getDrawable(i);
      } catch (Exception exception) {
        this.mBackgroundResource = 0;
        drawable1 = drawable2;
      }  
    return RoundedDrawable.fromDrawable(drawable1);
  }
  
  private Drawable resolveResource() {
    Resources resources = getResources();
    Drawable drawable2 = null;
    if (resources == null)
      return null; 
    int i = this.mResource;
    Drawable drawable1 = drawable2;
    if (i != 0)
      try {
        drawable1 = resources.getDrawable(i);
      } catch (Exception exception) {
        this.mResource = 0;
        drawable1 = drawable2;
      }  
    return RoundedDrawable.fromDrawable(drawable1);
  }
  
  private void updateAttrs(Drawable paramDrawable, ImageView.ScaleType paramScaleType) {
    float[] arrayOfFloat;
    if (paramDrawable == null)
      return; 
    boolean bool = paramDrawable instanceof RoundedDrawable;
    int i = 0;
    if (bool) {
      paramDrawable = paramDrawable;
      paramDrawable.setScaleType(paramScaleType).setBorderWidth(this.mBorderWidth).setBorderColor(this.mBorderColor).setOval(this.mIsOval).setTileModeX(this.mTileModeX).setTileModeY(this.mTileModeY);
      arrayOfFloat = this.mCornerRadii;
      if (arrayOfFloat != null)
        paramDrawable.setCornerRadius(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[3], arrayOfFloat[2]); 
      applyColorMod();
      return;
    } 
    if (paramDrawable instanceof LayerDrawable) {
      LayerDrawable layerDrawable = (LayerDrawable)paramDrawable;
      int j = layerDrawable.getNumberOfLayers();
      while (i < j) {
        updateAttrs(layerDrawable.getDrawable(i), (ImageView.ScaleType)arrayOfFloat);
        i++;
      } 
    } 
  }
  
  private void updateBackgroundDrawableAttrs(boolean paramBoolean) {
    if (this.mMutateBackground) {
      if (paramBoolean)
        this.mBackgroundDrawable = RoundedDrawable.fromDrawable(this.mBackgroundDrawable); 
      updateAttrs(this.mBackgroundDrawable, ImageView.ScaleType.FIT_XY);
    } 
  }
  
  private void updateDrawableAttrs() {
    updateAttrs(this.mDrawable, this.mScaleType);
  }
  
  public void RoundedImageView__onDetachedFromWindow$___twin___() {
    super.onDetachedFromWindow();
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    invalidate();
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
  
  public float getCornerRadius() {
    return getMaxCornerRadius();
  }
  
  public float getCornerRadius(int paramInt) {
    return this.mCornerRadii[paramInt];
  }
  
  public float getMaxCornerRadius() {
    float[] arrayOfFloat = this.mCornerRadii;
    int j = arrayOfFloat.length;
    float f = 0.0F;
    for (int i = 0; i < j; i++)
      f = Math.max(arrayOfFloat[i], f); 
    return f;
  }
  
  public ImageView.ScaleType getScaleType() {
    return this.mScaleType;
  }
  
  public Shader.TileMode getTileModeX() {
    return this.mTileModeX;
  }
  
  public Shader.TileMode getTileModeY() {
    return this.mTileModeY;
  }
  
  public boolean isOval() {
    return this.mIsOval;
  }
  
  public void mutateBackground(boolean paramBoolean) {
    if (this.mMutateBackground == paramBoolean)
      return; 
    this.mMutateBackground = paramBoolean;
    updateBackgroundDrawableAttrs(true);
    invalidate();
  }
  
  public boolean mutatesBackground() {
    return this.mMutateBackground;
  }
  
  protected void onDetachedFromWindow() {
    _lancet.com_ss_android_ugc_aweme_lancet_ImageStopLossLanect_imageViewOnDetachedFromWindow(this);
  }
  
  public void setBackground(Drawable paramDrawable) {
    setBackgroundDrawable(paramDrawable);
  }
  
  public void setBackgroundColor(int paramInt) {
    this.mBackgroundDrawable = (Drawable)new ColorDrawable(paramInt);
    setBackgroundDrawable(this.mBackgroundDrawable);
  }
  
  @Deprecated
  public void setBackgroundDrawable(Drawable paramDrawable) {
    this.mBackgroundDrawable = paramDrawable;
    updateBackgroundDrawableAttrs(true);
    super.setBackgroundDrawable(this.mBackgroundDrawable);
  }
  
  public void setBackgroundResource(int paramInt) {
    if (this.mBackgroundResource != paramInt) {
      this.mBackgroundResource = paramInt;
      this.mBackgroundDrawable = resolveBackgroundResource();
      setBackgroundDrawable(this.mBackgroundDrawable);
    } 
  }
  
  public void setBorderColor(int paramInt) {
    setBorderColor(ColorStateList.valueOf(paramInt));
  }
  
  public void setBorderColor(ColorStateList paramColorStateList) {
    if (this.mBorderColor.equals(paramColorStateList))
      return; 
    if (paramColorStateList == null)
      paramColorStateList = ColorStateList.valueOf(-16777216); 
    this.mBorderColor = paramColorStateList;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    if (this.mBorderWidth > 0.0F)
      invalidate(); 
  }
  
  public void setBorderWidth(float paramFloat) {
    if (this.mBorderWidth == paramFloat)
      return; 
    this.mBorderWidth = paramFloat;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    invalidate();
  }
  
  public void setBorderWidth(int paramInt) {
    setBorderWidth(getResources().getDimension(paramInt));
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {
    if (this.mColorFilter != paramColorFilter) {
      this.mColorFilter = paramColorFilter;
      this.mHasColorFilter = true;
      this.mColorMod = true;
      applyColorMod();
      invalidate();
    } 
  }
  
  public void setCornerRadius(float paramFloat) {
    setCornerRadius(paramFloat, paramFloat, paramFloat, paramFloat);
  }
  
  public void setCornerRadius(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    float[] arrayOfFloat = this.mCornerRadii;
    if (arrayOfFloat[0] == paramFloat1 && arrayOfFloat[1] == paramFloat2 && arrayOfFloat[3] == paramFloat4 && arrayOfFloat[2] == paramFloat3)
      return; 
    arrayOfFloat = this.mCornerRadii;
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    arrayOfFloat[2] = paramFloat3;
    arrayOfFloat[3] = paramFloat4;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    invalidate();
  }
  
  public void setCornerRadius(int paramInt, float paramFloat) {
    float[] arrayOfFloat = this.mCornerRadii;
    if (arrayOfFloat[paramInt] == paramFloat)
      return; 
    arrayOfFloat[paramInt] = paramFloat;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    invalidate();
  }
  
  public void setCornerRadiusDimen(int paramInt) {
    float f = getResources().getDimension(paramInt);
    setCornerRadius(f, f, f, f);
  }
  
  public void setCornerRadiusDimen(int paramInt1, int paramInt2) {
    setCornerRadius(paramInt1, getResources().getDimensionPixelSize(paramInt2));
  }
  
  public void setImageBitmap(Bitmap paramBitmap) {
    this.mResource = 0;
    this.mDrawable = RoundedDrawable.fromBitmap(paramBitmap);
    updateDrawableAttrs();
    super.setImageDrawable(this.mDrawable);
  }
  
  public void setImageDrawable(Drawable paramDrawable) {
    this.mResource = 0;
    this.mDrawable = RoundedDrawable.fromDrawable(paramDrawable);
    updateDrawableAttrs();
    super.setImageDrawable(this.mDrawable);
  }
  
  public void setImageResource(int paramInt) {
    if (this.mResource != paramInt) {
      this.mResource = paramInt;
      this.mDrawable = resolveResource();
      updateDrawableAttrs();
      super.setImageDrawable(this.mDrawable);
    } 
  }
  
  public void setImageURI(Uri paramUri) {
    super.setImageURI(paramUri);
    setImageDrawable(getDrawable());
  }
  
  public void setOval(boolean paramBoolean) {
    this.mIsOval = paramBoolean;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    invalidate();
  }
  
  public void setScaleType(ImageView.ScaleType paramScaleType) {
    if (this.mScaleType != paramScaleType) {
      this.mScaleType = paramScaleType;
      switch (paramScaleType) {
        default:
          super.setScaleType(paramScaleType);
          break;
        case CENTER:
        case CENTER_CROP:
        case CENTER_INSIDE:
        case FIT_CENTER:
        case FIT_START:
        case FIT_END:
        case null:
          super.setScaleType(ImageView.ScaleType.FIT_XY);
          break;
      } 
      updateDrawableAttrs();
      updateBackgroundDrawableAttrs(false);
      invalidate();
    } 
  }
  
  public void setTileModeX(Shader.TileMode paramTileMode) {
    if (this.mTileModeX == paramTileMode)
      return; 
    this.mTileModeX = paramTileMode;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    invalidate();
  }
  
  public void setTileModeY(Shader.TileMode paramTileMode) {
    if (this.mTileModeY == paramTileMode)
      return; 
    this.mTileModeY = paramTileMode;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    invalidate();
  }
  
  public static class Corner {}
  
  class RoundedImageView {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\RoundedImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */