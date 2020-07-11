package com.facebook.react.flat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import com.facebook.i.a.a;
import com.facebook.imagepipeline.o.b;

final class InlineImageSpanWithPipeline extends ReplacementSpan implements AttachDetachListener, BitmapUpdateListener {
  private static final RectF TMP_RECT = new RectF();
  
  private FlatViewGroup.InvalidateCallback mCallback;
  
  private boolean mFrozen;
  
  private float mHeight;
  
  private PipelineRequestHelper mRequestHelper;
  
  private float mWidth;
  
  InlineImageSpanWithPipeline() {
    this(null, Float.NaN, Float.NaN);
  }
  
  private InlineImageSpanWithPipeline(PipelineRequestHelper paramPipelineRequestHelper, float paramFloat1, float paramFloat2) {
    this.mRequestHelper = paramPipelineRequestHelper;
    this.mWidth = paramFloat1;
    this.mHeight = paramFloat2;
  }
  
  public final void draw(Canvas paramCanvas, CharSequence paramCharSequence, int paramInt1, int paramInt2, float paramFloat, int paramInt3, int paramInt4, int paramInt5, Paint paramPaint) {
    PipelineRequestHelper pipelineRequestHelper = this.mRequestHelper;
    if (pipelineRequestHelper == null)
      return; 
    Bitmap bitmap = pipelineRequestHelper.getBitmap();
    if (bitmap == null)
      return; 
    float f = paramInt5 - (paramPaint.getFontMetricsInt()).descent;
    TMP_RECT.set(paramFloat, f - this.mHeight, this.mWidth + paramFloat, f);
    paramCanvas.drawBitmap(bitmap, null, TMP_RECT, paramPaint);
  }
  
  final void freeze() {
    this.mFrozen = true;
  }
  
  final float getHeight() {
    return this.mHeight;
  }
  
  public final int getSize(Paint paramPaint, CharSequence paramCharSequence, int paramInt1, int paramInt2, Paint.FontMetricsInt paramFontMetricsInt) {
    if (paramFontMetricsInt != null) {
      paramFontMetricsInt.ascent = -Math.round(this.mHeight);
      paramFontMetricsInt.descent = 0;
      paramFontMetricsInt.top = paramFontMetricsInt.ascent;
      paramFontMetricsInt.bottom = 0;
    } 
    return Math.round(this.mWidth);
  }
  
  final float getWidth() {
    return this.mWidth;
  }
  
  final boolean hasImageRequest() {
    return (this.mRequestHelper != null);
  }
  
  final boolean isFrozen() {
    return this.mFrozen;
  }
  
  final InlineImageSpanWithPipeline mutableCopy() {
    return new InlineImageSpanWithPipeline(this.mRequestHelper, this.mWidth, this.mHeight);
  }
  
  public final void onAttached(FlatViewGroup.InvalidateCallback paramInvalidateCallback) {
    this.mCallback = paramInvalidateCallback;
    PipelineRequestHelper pipelineRequestHelper = this.mRequestHelper;
    if (pipelineRequestHelper != null)
      pipelineRequestHelper.attach(this); 
  }
  
  public final void onBitmapReady(Bitmap paramBitmap) {
    ((FlatViewGroup.InvalidateCallback)a.a(this.mCallback)).invalidate();
  }
  
  public final void onDetached() {
    PipelineRequestHelper pipelineRequestHelper = this.mRequestHelper;
    if (pipelineRequestHelper != null) {
      pipelineRequestHelper.detach();
      if (this.mRequestHelper.isDetached())
        this.mCallback = null; 
    } 
  }
  
  public final void onImageLoadEvent(int paramInt) {}
  
  public final void onSecondaryAttach(Bitmap paramBitmap) {
    ((FlatViewGroup.InvalidateCallback)a.a(this.mCallback)).invalidate();
  }
  
  final void setHeight(float paramFloat) {
    this.mHeight = paramFloat;
  }
  
  final void setImageRequest(b paramb) {
    if (paramb == null) {
      this.mRequestHelper = null;
      return;
    } 
    this.mRequestHelper = new PipelineRequestHelper(paramb);
  }
  
  final void setWidth(float paramFloat) {
    this.mWidth = paramFloat;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\InlineImageSpanWithPipeline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */