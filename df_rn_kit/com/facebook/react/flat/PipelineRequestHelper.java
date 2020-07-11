package com.facebook.react.flat;

import android.graphics.Bitmap;
import com.facebook.common.b.i;
import com.facebook.common.h.a;
import com.facebook.d.c;
import com.facebook.d.e;
import com.facebook.i.a.a;
import com.facebook.imagepipeline.e.k;
import com.facebook.imagepipeline.j.b;
import com.facebook.imagepipeline.j.c;
import com.facebook.imagepipeline.o.b;
import java.util.concurrent.Executor;

final class PipelineRequestHelper implements e<a<c>> {
  private int mAttachCounter;
  
  private BitmapUpdateListener mBitmapUpdateListener;
  
  private c<a<c>> mDataSource;
  
  private a<c> mImageRef;
  
  private final b mImageRequest;
  
  PipelineRequestHelper(b paramb) {
    this.mImageRequest = paramb;
  }
  
  final void attach(BitmapUpdateListener paramBitmapUpdateListener) {
    this.mBitmapUpdateListener = paramBitmapUpdateListener;
    this.mAttachCounter++;
    if (this.mAttachCounter != 1) {
      Bitmap bitmap = getBitmap();
      if (bitmap != null)
        paramBitmapUpdateListener.onSecondaryAttach(bitmap); 
      return;
    } 
    paramBitmapUpdateListener.onImageLoadEvent(4);
    c<a<c>> c1 = this.mDataSource;
    boolean bool2 = false;
    if (c1 == null) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    a.a(bool1);
    boolean bool1 = bool2;
    if (this.mImageRef == null)
      bool1 = true; 
    a.a(bool1);
    this.mDataSource = k.a().e().b(this.mImageRequest, RCTImageView.getCallerContext());
    this.mDataSource.a(this, (Executor)i.b());
  }
  
  final void detach() {
    this.mAttachCounter--;
    if (this.mAttachCounter != 0)
      return; 
    c<a<c>> c1 = this.mDataSource;
    if (c1 != null) {
      c1.g();
      this.mDataSource = null;
    } 
    a<c> a1 = this.mImageRef;
    if (a1 != null) {
      a1.close();
      this.mImageRef = null;
    } 
    this.mBitmapUpdateListener = null;
  }
  
  final Bitmap getBitmap() {
    a<c> a1 = this.mImageRef;
    if (a1 == null)
      return null; 
    c c1 = (c)a1.a();
    if (!(c1 instanceof b)) {
      this.mImageRef.close();
      this.mImageRef = null;
      return null;
    } 
    return ((b)c1).d();
  }
  
  final boolean isDetached() {
    return (this.mAttachCounter == 0);
  }
  
  public final void onCancellation(c<a<c>> paramc) {
    if (this.mDataSource == paramc)
      this.mDataSource = null; 
    paramc.g();
  }
  
  public final void onFailure(c<a<c>> paramc) {
    if (this.mDataSource == paramc) {
      ((BitmapUpdateListener)a.a(this.mBitmapUpdateListener)).onImageLoadEvent(1);
      ((BitmapUpdateListener)a.a(this.mBitmapUpdateListener)).onImageLoadEvent(3);
      this.mDataSource = null;
    } 
    paramc.g();
  }
  
  public final void onNewResult(c<a<c>> paramc) {
    if (!paramc.b())
      return; 
    try {
      c<a<c>> c1 = this.mDataSource;
      if (c1 != paramc)
        return; 
      this.mDataSource = null;
      a<c> a1 = (a)paramc.d();
      if (a1 == null)
        return; 
      if (!((c)a1.a() instanceof b)) {
        a1.close();
        return;
      } 
      this.mImageRef = a1;
      Bitmap bitmap = getBitmap();
      if (bitmap == null)
        return; 
      BitmapUpdateListener bitmapUpdateListener = (BitmapUpdateListener)a.a(this.mBitmapUpdateListener);
      bitmapUpdateListener.onBitmapReady(bitmap);
      bitmapUpdateListener.onImageLoadEvent(2);
      bitmapUpdateListener.onImageLoadEvent(3);
      return;
    } finally {
      paramc.g();
    } 
  }
  
  public final void onProgressUpdate(c<a<c>> paramc) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\PipelineRequestHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */