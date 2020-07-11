package com.tt.miniapp.video.view.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import com.tt.miniapphost.AppBrandLogger;

public class VideoTextureView extends TextureView {
  private final String TAG = "TextureVideoView";
  
  public Surface mCachedSurface;
  
  private String mObjectFit = "contain";
  
  public SurfaceTexture mSurfaceTexture;
  
  public TextureView.SurfaceTextureListener mSurfaceTextureListener;
  
  public boolean mTextureValid;
  
  private int mVideoHeight;
  
  private int mVideoWidth;
  
  public VideoTextureView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public VideoTextureView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    initListener();
  }
  
  private void initListener() {
    super.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
          public void onSurfaceTextureAvailable(SurfaceTexture param1SurfaceTexture, int param1Int1, int param1Int2) {
            if (VideoTextureView.this.mCachedSurface != null && (!VideoTextureView.this.mTextureValid || !VideoTextureView.this.mCachedSurface.isValid())) {
              VideoTextureView.this.mCachedSurface.release();
              VideoTextureView videoTextureView1 = VideoTextureView.this;
              videoTextureView1.mCachedSurface = null;
              videoTextureView1.mSurfaceTexture = null;
            } 
            if (VideoTextureView.this.mCachedSurface == null) {
              VideoTextureView.this.mCachedSurface = new Surface(param1SurfaceTexture);
              VideoTextureView.this.mSurfaceTexture = param1SurfaceTexture;
            } else {
              try {
                if (VideoTextureView.this.mSurfaceTexture != null)
                  VideoTextureView.this.setSurfaceTexture(VideoTextureView.this.mSurfaceTexture); 
              } catch (Exception exception) {}
            } 
            VideoTextureView videoTextureView = VideoTextureView.this;
            videoTextureView.mTextureValid = true;
            if (videoTextureView.mSurfaceTextureListener != null)
              VideoTextureView.this.mSurfaceTextureListener.onSurfaceTextureAvailable(VideoTextureView.this.mSurfaceTexture, param1Int1, param1Int2); 
          }
          
          public boolean onSurfaceTextureDestroyed(SurfaceTexture param1SurfaceTexture) {
            boolean bool;
            if (!VideoTextureView.this.mTextureValid && VideoTextureView.this.mCachedSurface != null) {
              VideoTextureView.this.mCachedSurface.release();
              VideoTextureView videoTextureView = VideoTextureView.this;
              videoTextureView.mCachedSurface = null;
              videoTextureView.mSurfaceTexture = null;
            } 
            if (VideoTextureView.this.mSurfaceTextureListener != null && VideoTextureView.this.mSurfaceTextureListener.onSurfaceTextureDestroyed(param1SurfaceTexture)) {
              bool = true;
            } else {
              bool = false;
            } 
            if (bool)
              VideoTextureView.this.releaseSurface(false); 
            return bool;
          }
          
          public void onSurfaceTextureSizeChanged(SurfaceTexture param1SurfaceTexture, int param1Int1, int param1Int2) {
            if (VideoTextureView.this.mSurfaceTextureListener != null)
              VideoTextureView.this.mSurfaceTextureListener.onSurfaceTextureSizeChanged(param1SurfaceTexture, param1Int1, param1Int2); 
          }
          
          public void onSurfaceTextureUpdated(SurfaceTexture param1SurfaceTexture) {
            if (VideoTextureView.this.mSurfaceTextureListener != null)
              VideoTextureView.this.mSurfaceTextureListener.onSurfaceTextureUpdated(param1SurfaceTexture); 
          }
        });
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    int i = 0;
    AppBrandLogger.d("TextureVideoView", new Object[] { "onMeasure" });
    if (TextUtils.equals(this.mObjectFit, "fill")) {
      super.onMeasure(paramInt1, paramInt2);
      return;
    } 
    if (this.mVideoWidth == 0 || this.mVideoHeight == 0) {
      setMeasuredDimension(paramInt1, paramInt2);
      return;
    } 
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    String str = this.mObjectFit;
    if (str.hashCode() != 94852023 || !str.equals("cover"))
      i = -1; 
    if (i != 0) {
      float f5 = paramInt1;
      float f6 = paramInt2;
      float f7 = f5 / f6;
      i = this.mVideoWidth;
      float f8 = i;
      int k = this.mVideoHeight;
      if (f7 > f8 / k) {
        paramInt1 = (int)(i / k * f6);
      } else {
        paramInt2 = (int)(f5 * k / i);
      } 
      setMeasuredDimension(paramInt1, paramInt2);
      return;
    } 
    float f1 = paramInt1;
    float f2 = paramInt2;
    float f3 = f1 / f2;
    i = this.mVideoWidth;
    float f4 = i;
    int j = this.mVideoHeight;
    if (f3 > f4 / j) {
      paramInt2 = (int)(f1 * j / i);
    } else {
      paramInt1 = (int)(i / j * f2);
    } 
    setMeasuredDimension(paramInt1, paramInt2);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void onWindowVisibilityChanged(int paramInt) {
    super.onWindowVisibilityChanged(paramInt);
  }
  
  public void releaseSurface(boolean paramBoolean) {
    if (paramBoolean) {
      SurfaceTexture surfaceTexture = this.mSurfaceTexture;
      if (surfaceTexture != null) {
        surfaceTexture.release();
        this.mSurfaceTexture = null;
      } 
      Surface surface = this.mCachedSurface;
      if (surface != null) {
        surface.release();
        this.mCachedSurface = null;
      } 
    } 
    this.mTextureValid = false;
    this.mCachedSurface = null;
    this.mSurfaceTexture = null;
  }
  
  public void setObjectFit(String paramString) {
    if (!TextUtils.equals(this.mObjectFit, paramString)) {
      if (paramString == null)
        return; 
      this.mObjectFit = paramString;
      requestLayout();
    } 
  }
  
  public void setSurfaceTextureListener(TextureView.SurfaceTextureListener paramSurfaceTextureListener) {
    this.mSurfaceTextureListener = paramSurfaceTextureListener;
  }
  
  public void setVideoSize(int paramInt1, int paramInt2) {
    AppBrandLogger.d("TextureVideoView", new Object[] { "setVideoSize actualViewWidth:", Integer.valueOf(paramInt1), "actualViewHeight:", Integer.valueOf(paramInt2) });
    if (this.mVideoWidth == paramInt1 && this.mVideoHeight == paramInt2)
      return; 
    this.mVideoWidth = paramInt1;
    this.mVideoHeight = paramInt2;
    requestLayout();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\view\widget\VideoTextureView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */