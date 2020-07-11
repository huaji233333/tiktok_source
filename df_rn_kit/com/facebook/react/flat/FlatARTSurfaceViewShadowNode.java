package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.views.art.ARTVirtualNode;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;

class FlatARTSurfaceViewShadowNode extends FlatShadowNode implements TextureView.SurfaceTextureListener, AndroidView {
  private boolean mPaddingChanged;
  
  private Surface mSurface;
  
  FlatARTSurfaceViewShadowNode() {
    forceMountToView();
    forceMountChildrenToView();
  }
  
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
    } catch (IllegalArgumentException|IllegalStateException illegalArgumentException) {
      return;
    } 
  }
  
  private void markChildrenUpdatesSeen(ReactShadowNode paramReactShadowNode) {
    for (int i = 0; i < paramReactShadowNode.getChildCount(); i++) {
      ReactShadowNode reactShadowNode = paramReactShadowNode.getChildAt(i);
      reactShadowNode.markUpdateSeen();
      markChildrenUpdatesSeen(reactShadowNode);
    } 
  }
  
  public boolean isPaddingChanged() {
    return this.mPaddingChanged;
  }
  
  public boolean isVirtual() {
    return false;
  }
  
  public boolean isVirtualAnchor() {
    return true;
  }
  
  public boolean needsCustomLayoutForChildren() {
    return false;
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
  
  public void resetPaddingChanged() {
    this.mPaddingChanged = false;
  }
  
  public void setPadding(int paramInt, float paramFloat) {
    YogaValue yogaValue = getStylePadding(paramInt);
    if (yogaValue.unit != YogaUnit.POINT || yogaValue.value != paramFloat) {
      super.setPadding(paramInt, paramFloat);
      this.mPaddingChanged = true;
      markUpdated();
    } 
  }
  
  public void setPaddingPercent(int paramInt, float paramFloat) {
    YogaValue yogaValue = getStylePadding(paramInt);
    if (yogaValue.unit != YogaUnit.PERCENT || yogaValue.value != paramFloat) {
      super.setPadding(paramInt, paramFloat);
      this.mPaddingChanged = true;
      markUpdated();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatARTSurfaceViewShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */