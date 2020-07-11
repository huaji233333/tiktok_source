package com.facebook.react.views.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.facebook.common.k.g;
import com.facebook.drawee.c.b;
import com.facebook.drawee.c.c;
import com.facebook.drawee.c.d;
import com.facebook.drawee.c.f;
import com.facebook.drawee.e.b;
import com.facebook.drawee.e.q;
import com.facebook.drawee.f.a;
import com.facebook.drawee.f.b;
import com.facebook.drawee.f.e;
import com.facebook.drawee.h.a;
import com.facebook.drawee.view.GenericDraweeView;
import com.facebook.imagepipeline.common.d;
import com.facebook.imagepipeline.j.f;
import com.facebook.imagepipeline.m.b;
import com.facebook.imagepipeline.o.a;
import com.facebook.imagepipeline.o.b;
import com.facebook.imagepipeline.o.c;
import com.facebook.imagepipeline.o.d;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.fresco.ReactNetworkImageRequest;
import com.facebook.react.uimanager.FloatUtil;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.imagehelper.ImageSource;
import com.facebook.react.views.imagehelper.MultiSourceHelper;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import com.facebook.yoga.a;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ReactImageView extends GenericDraweeView {
  public static float[] sComputedCornerRadii = new float[4];
  
  public static final Matrix sInverse;
  
  public static final Matrix sMatrix = new Matrix();
  
  private int mBorderColor;
  
  private float[] mBorderCornerRadii;
  
  private float mBorderRadius = 1.0E21F;
  
  private float mBorderWidth;
  
  private ImageSource mCachedImageSource;
  
  private final Object mCallerContext;
  
  private d mControllerForTesting;
  
  private d mControllerListener;
  
  private final b mDraweeControllerBuilder;
  
  private int mFadeDurationMs = -1;
  
  private GlobalImageLoadListener mGlobalImageLoadListener;
  
  private ReadableMap mHeaders;
  
  public ImageSource mImageSource;
  
  private boolean mIsDirty;
  
  private b mIterativeBoxBlurPostProcessor;
  
  private Drawable mLoadingImageDrawable;
  
  private int mOverlayColor;
  
  private boolean mProgressiveRenderingEnabled;
  
  private ImageResizeMethod mResizeMethod = ImageResizeMethod.AUTO;
  
  private final RoundedCornerPostprocessor mRoundedCornerPostprocessor;
  
  public q.b mScaleType = ImageResizeMode.defaultValue();
  
  private final List<ImageSource> mSources;
  
  static {
    sInverse = new Matrix();
  }
  
  public ReactImageView(Context paramContext, b paramb, GlobalImageLoadListener paramGlobalImageLoadListener, Object paramObject) {
    super(paramContext, buildHierarchy(paramContext));
    this.mDraweeControllerBuilder = paramb;
    this.mRoundedCornerPostprocessor = new RoundedCornerPostprocessor();
    this.mGlobalImageLoadListener = paramGlobalImageLoadListener;
    this.mCallerContext = paramObject;
    this.mSources = new LinkedList<ImageSource>();
  }
  
  private static a buildHierarchy(Context paramContext) {
    return (new b(paramContext.getResources())).a(e.b(0.0F)).a();
  }
  
  private boolean hasMultipleSources() {
    return (this.mSources.size() > 1);
  }
  
  private void setSourceImage() {
    this.mImageSource = null;
    if (this.mSources.isEmpty())
      return; 
    if (hasMultipleSources()) {
      MultiSourceHelper.MultiSourceResult multiSourceResult = MultiSourceHelper.getBestSourceForSize(getWidth(), getHeight(), this.mSources);
      this.mImageSource = multiSourceResult.getBestResult();
      this.mCachedImageSource = multiSourceResult.getBestResultInCache();
      return;
    } 
    this.mImageSource = this.mSources.get(0);
  }
  
  private boolean shouldResize(ImageSource paramImageSource) {
    return (this.mResizeMethod == ImageResizeMethod.AUTO) ? (!g.d(paramImageSource.getUri()) ? (g.c(paramImageSource.getUri())) : true) : ((this.mResizeMethod == ImageResizeMethod.RESIZE));
  }
  
  private void warnImageSource(String paramString) {}
  
  public void ReactImageView__onDetachedFromWindow$___twin___() {
    super.onDetachedFromWindow();
  }
  
  public void cornerRadii(float[] paramArrayOffloat) {
    float f1;
    if (!a.a(this.mBorderRadius)) {
      f1 = this.mBorderRadius;
    } else {
      f1 = 0.0F;
    } 
    float[] arrayOfFloat = this.mBorderCornerRadii;
    if (arrayOfFloat != null && !a.a(arrayOfFloat[0])) {
      f2 = this.mBorderCornerRadii[0];
    } else {
      f2 = f1;
    } 
    paramArrayOffloat[0] = f2;
    arrayOfFloat = this.mBorderCornerRadii;
    if (arrayOfFloat != null && !a.a(arrayOfFloat[1])) {
      f2 = this.mBorderCornerRadii[1];
    } else {
      f2 = f1;
    } 
    paramArrayOffloat[1] = f2;
    arrayOfFloat = this.mBorderCornerRadii;
    if (arrayOfFloat != null && !a.a(arrayOfFloat[2])) {
      f2 = this.mBorderCornerRadii[2];
    } else {
      f2 = f1;
    } 
    paramArrayOffloat[2] = f2;
    arrayOfFloat = this.mBorderCornerRadii;
    float f2 = f1;
    if (arrayOfFloat != null) {
      f2 = f1;
      if (!a.a(arrayOfFloat[3]))
        f2 = this.mBorderCornerRadii[3]; 
    } 
    paramArrayOffloat[3] = f2;
  }
  
  public boolean hasOverlappingRendering() {
    return false;
  }
  
  public void maybeUpdateView() {
    boolean bool;
    b b1;
    d d1;
    if (!this.mIsDirty)
      return; 
    if (hasMultipleSources() && (getWidth() <= 0 || getHeight() <= 0))
      return; 
    setSourceImage();
    ImageSource imageSource1 = this.mImageSource;
    if (imageSource1 == null)
      return; 
    boolean bool1 = shouldResize(imageSource1);
    if (bool1 && (getWidth() <= 0 || getHeight() <= 0))
      return; 
    a a = (a)getHierarchy();
    a.a(this.mScaleType);
    Drawable drawable = this.mLoadingImageDrawable;
    if (drawable != null)
      a.a(drawable, q.b.e); 
    if (this.mScaleType != q.b.g && this.mScaleType != q.b.h) {
      bool = true;
    } else {
      bool = false;
    } 
    e e = a.a;
    if (bool) {
      e.a(0.0F);
    } else {
      cornerRadii(sComputedCornerRadii);
      float[] arrayOfFloat = sComputedCornerRadii;
      e.a(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
    } 
    e.a(this.mBorderColor, this.mBorderWidth);
    int i = this.mOverlayColor;
    if (i != 0) {
      e.a(i);
    } else {
      e.a(e.a.BITMAP_ONLY);
    } 
    a.a(e);
    i = this.mFadeDurationMs;
    if (i < 0)
      if (this.mImageSource.isResource()) {
        i = 0;
      } else {
        i = 300;
      }  
    a.a(i);
    e = null;
    if (bool) {
      RoundedCornerPostprocessor roundedCornerPostprocessor = this.mRoundedCornerPostprocessor;
    } else {
      b1 = this.mIterativeBoxBlurPostProcessor;
      if (b1 == null)
        b1 = null; 
    } 
    if (bool1)
      d1 = new d(getWidth(), getHeight()); 
    ReactNetworkImageRequest reactNetworkImageRequest = ReactNetworkImageRequest.fromBuilderWithHeaders(c.a(this.mImageSource.getUri()).a((d)b1).a(d1).a(true).b(this.mProgressiveRenderingEnabled), this.mHeaders);
    GlobalImageLoadListener globalImageLoadListener = this.mGlobalImageLoadListener;
    if (globalImageLoadListener != null)
      globalImageLoadListener.onLoadAttempt(this.mImageSource.getUri()); 
    this.mDraweeControllerBuilder.b();
    this.mDraweeControllerBuilder.c(true).a(this.mCallerContext).a(getController()).b(reactNetworkImageRequest);
    ImageSource imageSource2 = this.mCachedImageSource;
    if (imageSource2 != null) {
      b b2 = c.a(imageSource2.getUri()).a((d)b1).a(d1).a(true).b(this.mProgressiveRenderingEnabled).a();
      this.mDraweeControllerBuilder.c(b2);
    } 
    if (this.mControllerListener != null && this.mControllerForTesting != null) {
      f f = new f();
      f.a(this.mControllerListener);
      f.a(this.mControllerForTesting);
      this.mDraweeControllerBuilder.a((d)f);
    } else {
      d d2 = this.mControllerForTesting;
      if (d2 != null) {
        this.mDraweeControllerBuilder.a(d2);
      } else {
        d2 = this.mControllerListener;
        if (d2 != null)
          this.mDraweeControllerBuilder.a(d2); 
      } 
    } 
    setController((a)this.mDraweeControllerBuilder.c());
    this.mIsDirty = false;
    this.mDraweeControllerBuilder.b();
  }
  
  public void onDetachedFromWindow() {
    _lancet.com_ss_android_ugc_aweme_lancet_ImageStopLossLanect_imageViewOnDetachedFromWindow(this);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 > 0 && paramInt2 > 0) {
      boolean bool;
      if (this.mIsDirty || hasMultipleSources()) {
        bool = true;
      } else {
        bool = false;
      } 
      this.mIsDirty = bool;
      maybeUpdateView();
    } 
  }
  
  public void setBlurRadius(float paramFloat) {
    int i = (int)PixelUtil.toPixelFromDIP(paramFloat);
    if (i == 0) {
      this.mIterativeBoxBlurPostProcessor = null;
    } else {
      this.mIterativeBoxBlurPostProcessor = new b(i);
    } 
    this.mIsDirty = true;
  }
  
  public void setBorderColor(int paramInt) {
    this.mBorderColor = paramInt;
    this.mIsDirty = true;
  }
  
  public void setBorderRadius(float paramFloat) {
    if (!FloatUtil.floatsEqual(this.mBorderRadius, paramFloat)) {
      this.mBorderRadius = paramFloat;
      this.mIsDirty = true;
    } 
  }
  
  public void setBorderRadius(float paramFloat, int paramInt) {
    if (this.mBorderCornerRadii == null) {
      this.mBorderCornerRadii = new float[4];
      Arrays.fill(this.mBorderCornerRadii, 1.0E21F);
    } 
    if (!FloatUtil.floatsEqual(this.mBorderCornerRadii[paramInt], paramFloat)) {
      this.mBorderCornerRadii[paramInt] = paramFloat;
      this.mIsDirty = true;
    } 
  }
  
  public void setBorderWidth(float paramFloat) {
    this.mBorderWidth = PixelUtil.toPixelFromDIP(paramFloat);
    this.mIsDirty = true;
  }
  
  public void setControllerListener(d paramd) {
    this.mControllerForTesting = paramd;
    this.mIsDirty = true;
    maybeUpdateView();
  }
  
  public void setFadeDuration(int paramInt) {
    this.mFadeDurationMs = paramInt;
  }
  
  public void setHeaders(ReadableMap paramReadableMap) {
    this.mHeaders = paramReadableMap;
  }
  
  public void setLoadingIndicatorSource(String paramString) {
    Drawable drawable = ResourceDrawableIdHelper.getInstance().getResourceDrawable(getContext(), paramString);
    if (drawable != null) {
      b b1 = new b(drawable, 1000);
    } else {
      drawable = null;
    } 
    this.mLoadingImageDrawable = drawable;
    this.mIsDirty = true;
  }
  
  public void setOverlayColor(int paramInt) {
    this.mOverlayColor = paramInt;
    this.mIsDirty = true;
  }
  
  public void setProgressiveRenderingEnabled(boolean paramBoolean) {
    this.mProgressiveRenderingEnabled = paramBoolean;
  }
  
  public void setResizeMethod(ImageResizeMethod paramImageResizeMethod) {
    this.mResizeMethod = paramImageResizeMethod;
    this.mIsDirty = true;
  }
  
  public void setScaleType(q.b paramb) {
    this.mScaleType = paramb;
    this.mIsDirty = true;
  }
  
  public void setShouldNotifyLoadEvents(boolean paramBoolean) {
    if (!paramBoolean) {
      this.mControllerListener = null;
    } else {
      this.mControllerListener = (d)new c<f>() {
          public void onFailure(String param1String, Throwable param1Throwable) {
            mEventDispatcher.dispatchEvent(new ImageLoadEvent(ReactImageView.this.getId(), 1));
            mEventDispatcher.dispatchEvent(new ImageLoadEvent(ReactImageView.this.getId(), 3));
          }
          
          public void onFinalImageSet(String param1String, f param1f, Animatable param1Animatable) {
            if (param1f != null) {
              mEventDispatcher.dispatchEvent(new ImageLoadEvent(ReactImageView.this.getId(), 2, ReactImageView.this.mImageSource.getSource(), param1f.getWidth(), param1f.getHeight()));
              mEventDispatcher.dispatchEvent(new ImageLoadEvent(ReactImageView.this.getId(), 3));
            } 
          }
          
          public void onSubmit(String param1String, Object param1Object) {
            mEventDispatcher.dispatchEvent(new ImageLoadEvent(ReactImageView.this.getId(), 4));
          }
        };
    } 
    this.mIsDirty = true;
  }
  
  public void setSource(ReadableArray paramReadableArray) {
    this.mSources.clear();
    if (paramReadableArray != null && paramReadableArray.size() != 0) {
      String str;
      int j = paramReadableArray.size();
      int i = 0;
      if (j == 1) {
        str = paramReadableArray.getMap(0).getString("uri");
        ImageSource imageSource = new ImageSource(getContext(), str);
        this.mSources.add(imageSource);
        if (Uri.EMPTY.equals(imageSource.getUri()))
          warnImageSource(str); 
      } else {
        while (i < str.size()) {
          ReadableMap readableMap = str.getMap(i);
          String str1 = readableMap.getString("uri");
          ImageSource imageSource = new ImageSource(getContext(), str1, readableMap.getDouble("width"), readableMap.getDouble("height"));
          this.mSources.add(imageSource);
          if (Uri.EMPTY.equals(imageSource.getUri()))
            warnImageSource(str1); 
          i++;
        } 
      } 
    } 
    this.mIsDirty = true;
  }
  
  class RoundedCornerPostprocessor extends a {
    private RoundedCornerPostprocessor() {}
    
    void getRadii(Bitmap param1Bitmap, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2) {
      ReactImageView.this.mScaleType.a(ReactImageView.sMatrix, new Rect(0, 0, param1Bitmap.getWidth(), param1Bitmap.getHeight()), param1Bitmap.getWidth(), param1Bitmap.getHeight(), 0.0F, 0.0F);
      ReactImageView.sMatrix.invert(ReactImageView.sInverse);
      param1ArrayOffloat2[0] = ReactImageView.sInverse.mapRadius(param1ArrayOffloat1[0]);
      param1ArrayOffloat2[1] = param1ArrayOffloat2[0];
      param1ArrayOffloat2[2] = ReactImageView.sInverse.mapRadius(param1ArrayOffloat1[1]);
      param1ArrayOffloat2[3] = param1ArrayOffloat2[2];
      param1ArrayOffloat2[4] = ReactImageView.sInverse.mapRadius(param1ArrayOffloat1[2]);
      param1ArrayOffloat2[5] = param1ArrayOffloat2[4];
      param1ArrayOffloat2[6] = ReactImageView.sInverse.mapRadius(param1ArrayOffloat1[3]);
      param1ArrayOffloat2[7] = param1ArrayOffloat2[6];
    }
    
    public void process(Bitmap param1Bitmap1, Bitmap param1Bitmap2) {
      ReactImageView.this.cornerRadii(ReactImageView.sComputedCornerRadii);
      param1Bitmap1.setHasAlpha(true);
      if (FloatUtil.floatsEqual(ReactImageView.sComputedCornerRadii[0], 0.0F) && FloatUtil.floatsEqual(ReactImageView.sComputedCornerRadii[1], 0.0F) && FloatUtil.floatsEqual(ReactImageView.sComputedCornerRadii[2], 0.0F) && FloatUtil.floatsEqual(ReactImageView.sComputedCornerRadii[3], 0.0F)) {
        super.process(param1Bitmap1, param1Bitmap2);
        return;
      } 
      Paint paint = new Paint();
      paint.setAntiAlias(true);
      Shader.TileMode tileMode = Shader.TileMode.CLAMP;
      paint.setShader((Shader)new BitmapShader(param1Bitmap2, tileMode, tileMode));
      Canvas canvas = new Canvas(param1Bitmap1);
      float[] arrayOfFloat = new float[8];
      getRadii(param1Bitmap2, ReactImageView.sComputedCornerRadii, arrayOfFloat);
      Path path = new Path();
      path.addRoundRect(new RectF(0.0F, 0.0F, param1Bitmap2.getWidth(), param1Bitmap2.getHeight()), arrayOfFloat, Path.Direction.CW);
      canvas.drawPath(path, paint);
    }
  }
  
  class ReactImageView {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\image\ReactImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */