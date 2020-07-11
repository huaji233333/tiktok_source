package com.tt.miniapp.component.nativeview.liveplayer.context.surface;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import com.tt.miniapphost.AppBrandLogger;

public final class SurfaceHolder {
  private final String TAG;
  
  private boolean available;
  
  private final Surface surface;
  
  private final SurfaceTexture surfaceTexture;
  
  public SurfaceHolder(SurfaceTexture paramSurfaceTexture, Surface paramSurface) {
    this.surfaceTexture = paramSurfaceTexture;
    this.surface = paramSurface;
    this.TAG = "SurfaceHolder";
    this.available = true;
  }
  
  public final boolean available() {
    return (this.surface.isValid() && this.available);
  }
  
  public final Surface getSurface() {
    return this.surface;
  }
  
  public final boolean release() {
    try {
      if (!this.available)
        return true; 
      this.surface.release();
      this.surfaceTexture.release();
      this.available = false;
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e(this.TAG, new Object[] { "release surface exception:", exception });
      return false;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\liveplayer\context\surface\SurfaceHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */