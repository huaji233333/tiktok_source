package com.tt.miniapp.video.view;

import android.graphics.SurfaceTexture;
import android.view.Surface;

public interface IVideoViewCallback {
  void textureViewCreated(Surface paramSurface);
  
  void textureViewDestroyed(Surface paramSurface);
  
  void textureViewSizeChanged(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\view\IVideoViewCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */