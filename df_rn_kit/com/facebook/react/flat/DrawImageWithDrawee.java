package com.facebook.react.flat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import com.facebook.drawee.c.d;
import com.facebook.drawee.e.q;
import com.facebook.drawee.f.a;
import com.facebook.drawee.f.e;
import com.facebook.i.a.a;
import com.facebook.imagepipeline.common.d;
import com.facebook.imagepipeline.o.b;
import com.facebook.imagepipeline.o.c;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.views.image.GlobalImageLoadListener;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.imagehelper.ImageSource;
import com.facebook.react.views.imagehelper.MultiSourceHelper;
import java.util.LinkedList;
import java.util.List;

final class DrawImageWithDrawee extends AbstractDrawCommand implements d, DrawImage {
  private int mBorderColor;
  
  private float mBorderRadius;
  
  private float mBorderWidth;
  
  private FlatViewGroup.InvalidateCallback mCallback;
  
  private PorterDuffColorFilter mColorFilter;
  
  private int mFadeDuration = 300;
  
  private final GlobalImageLoadListener mGlobalImageLoadListener;
  
  private boolean mProgressiveRenderingEnabled;
  
  private int mReactTag;
  
  private DraweeRequestHelper mRequestHelper;
  
  private q.b mScaleType = ImageResizeMode.defaultValue();
  
  private final List<ImageSource> mSources = new LinkedList<ImageSource>();
  
  public DrawImageWithDrawee(GlobalImageLoadListener paramGlobalImageLoadListener) {
    this.mGlobalImageLoadListener = paramGlobalImageLoadListener;
  }
  
  private void computeRequestHelper() {
    MultiSourceHelper.MultiSourceResult multiSourceResult = MultiSourceHelper.getBestSourceForSize(Math.round(getRight() - getLeft()), Math.round(getBottom() - getTop()), this.mSources);
    ImageSource imageSource1 = multiSourceResult.getBestResult();
    ImageSource imageSource2 = multiSourceResult.getBestResultInCache();
    b b1 = null;
    if (imageSource1 == null) {
      this.mRequestHelper = null;
      return;
    } 
    if (shouldResize(imageSource1)) {
      d d1 = new d((int)(getRight() - getLeft()), (int)(getBottom() - getTop()));
    } else {
      multiSourceResult = null;
    } 
    b b2 = c.a(imageSource1.getUri()).a((d)multiSourceResult).b(this.mProgressiveRenderingEnabled).a();
    GlobalImageLoadListener globalImageLoadListener = this.mGlobalImageLoadListener;
    if (globalImageLoadListener != null)
      globalImageLoadListener.onLoadAttempt(imageSource1.getUri()); 
    if (imageSource2 != null)
      b1 = c.a(imageSource2.getUri()).a((d)multiSourceResult).b(this.mProgressiveRenderingEnabled).a(); 
    this.mRequestHelper = new DraweeRequestHelper((b)a.b(b2), b1, this);
  }
  
  private boolean shouldDisplayBorder() {
    return (this.mBorderColor != 0 || this.mBorderRadius >= 0.5F);
  }
  
  private static boolean shouldResize(ImageSource paramImageSource) {
    String str;
    Uri uri = paramImageSource.getUri();
    if (uri == null) {
      uri = null;
    } else {
      str = uri.getScheme();
    } 
    return ("file".equals(str) || "content".equals(str));
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
  
  public final q.b getScaleType() {
    return this.mScaleType;
  }
  
  public final boolean hasImageRequest() {
    return !this.mSources.isEmpty();
  }
  
  public final void onAttached(FlatViewGroup.InvalidateCallback paramInvalidateCallback) {
    this.mCallback = paramInvalidateCallback;
    DraweeRequestHelper draweeRequestHelper = this.mRequestHelper;
    if (draweeRequestHelper != null) {
      a a = draweeRequestHelper.getHierarchy();
      e e = a.a;
      if (shouldDisplayBorder()) {
        e e1 = e;
        if (e == null)
          e1 = new e(); 
        e1.a(this.mBorderColor, this.mBorderWidth);
        e1.a(this.mBorderRadius);
        a.a(e1);
      } else if (e != null) {
        a.a(null);
      } 
      a.a(this.mScaleType);
      PorterDuffColorFilter porterDuffColorFilter = this.mColorFilter;
      a.c.setColorFilter((ColorFilter)porterDuffColorFilter);
      a.a(this.mFadeDuration);
      a.a().setBounds(Math.round(getLeft()), Math.round(getTop()), Math.round(getRight()), Math.round(getBottom()));
      this.mRequestHelper.attach(paramInvalidateCallback);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("No DraweeRequestHelper - width: ");
    stringBuilder.append(getRight() - getLeft());
    stringBuilder.append(" - height: ");
    stringBuilder.append(getBottom() - getTop());
    stringBuilder.append(" - number of sources: ");
    stringBuilder.append(this.mSources.size());
    throw new RuntimeException(stringBuilder.toString());
  }
  
  protected final void onBoundsChanged() {
    super.onBoundsChanged();
    computeRequestHelper();
  }
  
  protected final void onDebugDrawHighlight(Canvas paramCanvas) {
    if (this.mCallback != null)
      debugDrawCautionHighlight(paramCanvas, "Invalidate Drawee"); 
  }
  
  public final void onDetached() {
    DraweeRequestHelper draweeRequestHelper = this.mRequestHelper;
    if (draweeRequestHelper != null)
      draweeRequestHelper.detach(); 
  }
  
  public final void onDraw(Canvas paramCanvas) {
    DraweeRequestHelper draweeRequestHelper = this.mRequestHelper;
    if (draweeRequestHelper != null)
      draweeRequestHelper.getDrawable().draw(paramCanvas); 
  }
  
  public final void onFailure(String paramString, Throwable paramThrowable) {
    FlatViewGroup.InvalidateCallback invalidateCallback = this.mCallback;
    if (invalidateCallback != null) {
      int i = this.mReactTag;
      if (i != 0) {
        invalidateCallback.dispatchImageLoadEvent(i, 1);
        this.mCallback.dispatchImageLoadEvent(this.mReactTag, 3);
      } 
    } 
  }
  
  public final void onFinalImageSet(String paramString, Object paramObject, Animatable paramAnimatable) {
    FlatViewGroup.InvalidateCallback invalidateCallback = this.mCallback;
    if (invalidateCallback != null) {
      int i = this.mReactTag;
      if (i != 0) {
        invalidateCallback.dispatchImageLoadEvent(i, 2);
        this.mCallback.dispatchImageLoadEvent(this.mReactTag, 3);
      } 
    } 
  }
  
  public final void onIntermediateImageFailed(String paramString, Throwable paramThrowable) {}
  
  public final void onIntermediateImageSet(String paramString, Object paramObject) {}
  
  public final void onRelease(String paramString) {}
  
  public final void onSubmit(String paramString, Object paramObject) {
    FlatViewGroup.InvalidateCallback invalidateCallback = this.mCallback;
    if (invalidateCallback != null) {
      int i = this.mReactTag;
      if (i != 0)
        invalidateCallback.dispatchImageLoadEvent(i, 4); 
    } 
  }
  
  public final void setBorderColor(int paramInt) {
    this.mBorderColor = paramInt;
  }
  
  public final void setBorderRadius(float paramFloat) {
    this.mBorderRadius = paramFloat;
  }
  
  public final void setBorderWidth(float paramFloat) {
    this.mBorderWidth = paramFloat;
  }
  
  public final void setFadeDuration(int paramInt) {
    this.mFadeDuration = paramInt;
  }
  
  public final void setProgressiveRenderingEnabled(boolean paramBoolean) {
    this.mProgressiveRenderingEnabled = paramBoolean;
  }
  
  public final void setReactTag(int paramInt) {
    this.mReactTag = paramInt;
  }
  
  public final void setScaleType(q.b paramb) {
    this.mScaleType = paramb;
  }
  
  public final void setSource(Context paramContext, ReadableArray paramReadableArray) {
    this.mSources.clear();
    if (paramReadableArray != null && paramReadableArray.size() != 0) {
      ReadableMap readableMap;
      int j = paramReadableArray.size();
      int i = 0;
      if (j == 1) {
        readableMap = paramReadableArray.getMap(0);
        this.mSources.add(new ImageSource(paramContext, readableMap.getString("uri")));
        return;
      } 
      while (i < readableMap.size()) {
        ReadableMap readableMap1 = readableMap.getMap(i);
        this.mSources.add(new ImageSource(paramContext, readableMap1.getString("uri"), readableMap1.getDouble("width"), readableMap1.getDouble("height")));
        i++;
      } 
    } 
  }
  
  public final void setTintColor(int paramInt) {
    if (paramInt == 0) {
      this.mColorFilter = null;
      return;
    } 
    this.mColorFilter = new PorterDuffColorFilter(paramInt, PorterDuff.Mode.SRC_ATOP);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\DrawImageWithDrawee.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */