package com.tt.miniapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedImageView extends ImageView {
  private Canvas bitMapCanvas;
  
  private int cornerRadius;
  
  private Paint paint;
  
  private RectF rectF;
  
  public RoundedImageView(Context paramContext) {
    super(paramContext);
    init();
  }
  
  public RoundedImageView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  public RoundedImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private void drawBitmap(Drawable paramDrawable) {
    int i = this.bitMapCanvas.getSaveCount();
    this.bitMapCanvas.save();
    Matrix matrix = getImageMatrix();
    if (matrix != null)
      this.bitMapCanvas.concat(matrix); 
    paramDrawable.draw(this.bitMapCanvas);
    this.bitMapCanvas.restoreToCount(i);
  }
  
  private int getCornerRadius() {
    return this.cornerRadius;
  }
  
  private void init() {
    this.paint = new Paint(5);
  }
  
  private void initSize() {
    if (getWidth() > 0 && getHeight() > 0) {
      Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
      Paint paint = this.paint;
      Shader.TileMode tileMode = Shader.TileMode.CLAMP;
      paint.setShader((Shader)new BitmapShader(bitmap, tileMode, tileMode));
      this.rectF = new RectF(getPaddingLeft(), getPaddingTop(), (getWidth() - getPaddingRight()), (getHeight() - getPaddingBottom()));
      this.bitMapCanvas = new Canvas(bitmap);
    } 
  }
  
  public void RoundedImageView__onDetachedFromWindow$___twin___() {
    super.onDetachedFromWindow();
  }
  
  protected void onDetachedFromWindow() {
    _lancet.com_ss_android_ugc_aweme_lancet_ImageStopLossLanect_imageViewOnDetachedFromWindow(this);
  }
  
  protected void onDraw(Canvas paramCanvas) {
    Drawable drawable = getDrawable();
    Matrix matrix = getImageMatrix();
    if (drawable == null)
      return; 
    if (drawable.getIntrinsicWidth() != 0) {
      if (drawable.getIntrinsicHeight() == 0)
        return; 
      if (matrix == null && getPaddingTop() == 0 && getPaddingLeft() == 0) {
        drawable.draw(paramCanvas);
        return;
      } 
      int i = paramCanvas.getSaveCount();
      paramCanvas.save();
      if (getCropToPadding()) {
        int j = getScrollX();
        int k = getScrollY();
        paramCanvas.clipRect(getPaddingLeft() + j, getPaddingTop() + k, j + getRight() - getLeft() - getPaddingRight(), k + getBottom() - getTop() - getPaddingBottom());
      } 
      paramCanvas.translate(getPaddingLeft(), getPaddingTop());
      drawBitmap(drawable);
      paramCanvas.drawRoundRect(this.rectF, getCornerRadius(), getCornerRadius(), this.paint);
      paramCanvas.restoreToCount(i);
    } 
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramBoolean)
      initSize(); 
  }
  
  public void setCornerRadius(int paramInt) {
    this.cornerRadius = paramInt;
  }
  
  class RoundedImageView {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\RoundedImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */