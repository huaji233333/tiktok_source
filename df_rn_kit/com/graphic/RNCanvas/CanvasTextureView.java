package com.graphic.RNCanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Region;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import com.facebook.common.e.a;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.util.ArrayList;
import java.util.HashMap;

public class CanvasTextureView extends TextureView {
  private static final CanvasMethodDelegate delegate = new CanvasMethodDelegate(CanvasRenderingContext2D.class);
  
  private final String TAG = "CanvasTextureView";
  
  private ArrayList<HashMap> actions = new ArrayList<HashMap>();
  
  private Integer mBackgroundColor;
  
  public Surface mSurface;
  
  private final CanvasRenderingContext2D renderingContext2D = new CanvasRenderingContext2D();
  
  public CanvasTextureView(Context paramContext) {
    super(paramContext);
    setOpaque(false);
    setDevicePixelRatio(paramContext);
    setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
          public void onSurfaceTextureAvailable(SurfaceTexture param1SurfaceTexture, int param1Int1, int param1Int2) {
            CanvasTextureView.this.mSurface = new Surface(param1SurfaceTexture);
            CanvasTextureView.this.drawOutput();
            CanvasTextureView.this.onReady();
          }
          
          public boolean onSurfaceTextureDestroyed(SurfaceTexture param1SurfaceTexture) {
            param1SurfaceTexture.release();
            CanvasTextureView.this.mSurface = null;
            return true;
          }
          
          public void onSurfaceTextureSizeChanged(SurfaceTexture param1SurfaceTexture, int param1Int1, int param1Int2) {}
          
          public void onSurfaceTextureUpdated(SurfaceTexture param1SurfaceTexture) {}
        });
  }
  
  private void drawCanvas(Canvas paramCanvas) {
    paramCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
    Integer integer = this.mBackgroundColor;
    if (integer != null) {
      paramCanvas.drawColor(integer.intValue());
    } else {
      paramCanvas.drawColor(Color.rgb(255, 255, 255));
    } 
    paramCanvas.clipRect(0.0F, 0.0F, paramCanvas.getWidth(), paramCanvas.getHeight(), Region.Op.REPLACE);
    this.renderingContext2D.setCanvas(paramCanvas);
    runActions();
  }
  
  private void runActions() {
    for (HashMap hashMap : this.actions)
      delegate.invoke(this.renderingContext2D, (String)hashMap.get("method"), (Object[])hashMap.get("arguments")); 
  }
  
  private void setDevicePixelRatio(Context paramContext) {
    this.renderingContext2D.setDevicePixelRatio((paramContext.getResources().getDisplayMetrics()).density);
  }
  
  public void drawOutput() {
    Surface surface = this.mSurface;
    if (surface != null) {
      if (!surface.isValid())
        return; 
      try {
        Canvas canvas = this.mSurface.lockCanvas(null);
        drawCanvas(canvas);
        if (this.mSurface == null)
          return; 
        this.mSurface.unlockCanvasAndPost(canvas);
        return;
      } catch (IllegalArgumentException|IllegalStateException illegalArgumentException) {
        a.c("CanvasTextureView", "in Surface.unlockCanvasAndPost");
      } 
    } 
  }
  
  public void onReady() {
    ((RCTEventEmitter)((ReactContext)getContext()).getJSModule(RCTEventEmitter.class)).receiveEvent(getId(), "onReady", null);
  }
  
  public void setActions(ArrayList<HashMap> paramArrayList) {
    this.actions = new ArrayList<HashMap>(paramArrayList);
  }
  
  public void setBackgroundColor(Integer paramInteger) {
    this.mBackgroundColor = paramInteger;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasTextureView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */