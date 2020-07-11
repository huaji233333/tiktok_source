package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ARTGroupShadowNode extends ARTVirtualNode {
  protected RectF mClipping;
  
  public ARTGroupShadowNode() {}
  
  public ARTGroupShadowNode(ARTGroupShadowNode paramARTGroupShadowNode) {
    super(paramARTGroupShadowNode);
    this.mClipping = new RectF(paramARTGroupShadowNode.mClipping);
  }
  
  private static RectF createClipping(float[] paramArrayOffloat) {
    if (paramArrayOffloat.length == 4)
      return new RectF(paramArrayOffloat[0], paramArrayOffloat[1], paramArrayOffloat[0] + paramArrayOffloat[2], paramArrayOffloat[1] + paramArrayOffloat[3]); 
    throw new JSApplicationIllegalArgumentException("Clipping should be array of length 4 (e.g. [x, y, width, height])");
  }
  
  public void draw(Canvas paramCanvas, Paint paramPaint, float paramFloat) {
    paramFloat *= this.mOpacity;
    if (paramFloat > 0.01F) {
      saveAndSetupCanvas(paramCanvas);
      RectF rectF = this.mClipping;
      if (rectF != null)
        paramCanvas.clipRect(rectF.left * this.mScale, this.mClipping.top * this.mScale, this.mClipping.right * this.mScale, this.mClipping.bottom * this.mScale, Region.Op.REPLACE); 
      int i;
      for (i = 0; i < getChildCount(); i++) {
        ARTVirtualNode aRTVirtualNode = (ARTVirtualNode)getChildAt(i);
        aRTVirtualNode.draw(paramCanvas, paramPaint, paramFloat);
        aRTVirtualNode.markUpdateSeen();
      } 
      restoreCanvas(paramCanvas);
    } 
  }
  
  public boolean isVirtual() {
    return true;
  }
  
  public ReactShadowNodeImpl mutableCopy() {
    return new ARTGroupShadowNode(this);
  }
  
  @ReactProp(name = "clipping")
  public void setClipping(ReadableArray paramReadableArray) {
    float[] arrayOfFloat = PropHelper.toFloatArray(paramReadableArray);
    if (arrayOfFloat != null) {
      this.mClipping = createClipping(arrayOfFloat);
      markUpdated();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\art\ARTGroupShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */