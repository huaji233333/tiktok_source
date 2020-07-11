package com.facebook.react.views.text.frescosupport;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.TextView;
import com.facebook.drawee.c.a;
import com.facebook.drawee.c.b;
import com.facebook.drawee.f.a;
import com.facebook.drawee.f.b;
import com.facebook.drawee.h.a;
import com.facebook.drawee.h.b;
import com.facebook.drawee.view.b;
import com.facebook.imagepipeline.o.c;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.fresco.ReactNetworkImageRequest;
import com.facebook.react.views.text.TextInlineImageSpan;

public class FrescoBasedReactTextInlineImageSpan extends TextInlineImageSpan {
  private final Object mCallerContext;
  
  private Drawable mDrawable;
  
  private final b mDraweeControllerBuilder;
  
  private final b<a> mDraweeHolder;
  
  private ReadableMap mHeaders;
  
  private int mHeight;
  
  private TextView mTextView;
  
  private Uri mUri;
  
  private int mWidth;
  
  public FrescoBasedReactTextInlineImageSpan(Resources paramResources, int paramInt1, int paramInt2, Uri paramUri, ReadableMap paramReadableMap, b paramb, Object paramObject) {
    this.mDraweeHolder = new b((b)b.a(paramResources).a());
    this.mDraweeControllerBuilder = paramb;
    this.mCallerContext = paramObject;
    this.mHeight = paramInt1;
    this.mWidth = paramInt2;
    if (paramUri == null)
      paramUri = Uri.EMPTY; 
    this.mUri = paramUri;
    this.mHeaders = paramReadableMap;
  }
  
  public void draw(Canvas paramCanvas, CharSequence paramCharSequence, int paramInt1, int paramInt2, float paramFloat, int paramInt3, int paramInt4, int paramInt5, Paint paramPaint) {
    if (this.mDrawable == null) {
      ReactNetworkImageRequest reactNetworkImageRequest = ReactNetworkImageRequest.fromBuilderWithHeaders(c.a(this.mUri), this.mHeaders);
      a a = this.mDraweeControllerBuilder.b().a(this.mDraweeHolder.c).a(this.mCallerContext).b(reactNetworkImageRequest).c();
      this.mDraweeHolder.a((a)a);
      this.mDraweeControllerBuilder.b();
      this.mDrawable = this.mDraweeHolder.e();
      this.mDrawable.setBounds(0, 0, this.mWidth, this.mHeight);
      this.mDrawable.setCallback((Drawable.Callback)this.mTextView);
    } 
    paramCanvas.save();
    paramCanvas.translate(paramFloat, (paramInt4 - (this.mDrawable.getBounds()).bottom));
    this.mDrawable.draw(paramCanvas);
    paramCanvas.restore();
  }
  
  public Drawable getDrawable() {
    return this.mDrawable;
  }
  
  public int getHeight() {
    return this.mHeight;
  }
  
  public int getSize(Paint paramPaint, CharSequence paramCharSequence, int paramInt1, int paramInt2, Paint.FontMetricsInt paramFontMetricsInt) {
    if (paramFontMetricsInt != null) {
      paramFontMetricsInt.ascent = -this.mHeight;
      paramFontMetricsInt.descent = 0;
      paramFontMetricsInt.top = paramFontMetricsInt.ascent;
      paramFontMetricsInt.bottom = 0;
    } 
    return this.mWidth;
  }
  
  public int getWidth() {
    return this.mWidth;
  }
  
  public void onAttachedToWindow() {
    this.mDraweeHolder.b();
  }
  
  public void onDetachedFromWindow() {
    this.mDraweeHolder.c();
  }
  
  public void onFinishTemporaryDetach() {
    this.mDraweeHolder.b();
  }
  
  public void onStartTemporaryDetach() {
    this.mDraweeHolder.c();
  }
  
  public void setTextView(TextView paramTextView) {
    this.mTextView = paramTextView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\frescosupport\FrescoBasedReactTextInlineImageSpan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */