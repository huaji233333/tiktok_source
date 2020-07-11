package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import com.facebook.common.e.a;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ARTSurfaceViewShadowNode extends LayoutShadowNode implements TextureView.SurfaceTextureListener {
  private Integer mBackgroundColor;
  
  private Surface mSurface;
  
  private void drawOutput() {
    Surface surface = this.mSurface;
    if (surface == null || !surface.isValid()) {
      markChildrenUpdatesSeen((ReactShadowNode)this);
      return;
    } 
    try {
      Canvas canvas = this.mSurface.lockCanvas(null);
      PorterDuff.Mode mode = PorterDuff.Mode.CLEAR;
      int i = 0;
      canvas.drawColor(0, mode);
      if (this.mBackgroundColor != null)
        canvas.drawColor(this.mBackgroundColor.intValue()); 
      Paint paint = new Paint();
      while (i < getChildCount()) {
        ARTVirtualNode aRTVirtualNode = (ARTVirtualNode)getChildAt(i);
        aRTVirtualNode.draw(canvas, paint, 1.0F);
        aRTVirtualNode.markUpdateSeen();
        i++;
      } 
      if (this.mSurface == null)
        return; 
      this.mSurface.unlockCanvasAndPost(canvas);
      return;
    } catch (IllegalArgumentException illegalArgumentException) {
    
    } catch (IllegalStateException illegalStateException) {}
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(illegalStateException.getClass().getSimpleName());
    stringBuilder.append(" in Surface.unlockCanvasAndPost");
    a.c("ReactNative", stringBuilder.toString());
  }
  
  private void markChildrenUpdatesSeen(ReactShadowNode paramReactShadowNode) {
    for (int i = 0; i < paramReactShadowNode.getChildCount(); i++) {
      ReactShadowNode reactShadowNode = paramReactShadowNode.getChildAt(i);
      reactShadowNode.markUpdateSeen();
      markChildrenUpdatesSeen(reactShadowNode);
    } 
  }
  
  public boolean isVirtual() {
    return false;
  }
  
  public boolean isVirtualAnchor() {
    return true;
  }
  
  public void onCollectExtraUpdates(UIViewOperationQueue paramUIViewOperationQueue) {
    super.onCollectExtraUpdates(paramUIViewOperationQueue);
    drawOutput();
    paramUIViewOperationQueue.enqueueUpdateExtraData(getReactTag(), this);
  }
  
  public void onSurfaceTextureAvailable(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2) {
    this.mSurface = new Surface(paramSurfaceTexture);
    drawOutput();
  }
  
  public boolean onSurfaceTextureDestroyed(SurfaceTexture paramSurfaceTexture) {
    paramSurfaceTexture.release();
    this.mSurface = null;
    return true;
  }
  
  public void onSurfaceTextureSizeChanged(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2) {}
  
  public void onSurfaceTextureUpdated(SurfaceTexture paramSurfaceTexture) {}
  
  @ReactProp(customType = "Color", name = "backgroundColor")
  public void setBackgroundColor(Integer paramInteger) {
    this.mBackgroundColor = paramInteger;
    markUpdated();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\art\ARTSurfaceViewShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */