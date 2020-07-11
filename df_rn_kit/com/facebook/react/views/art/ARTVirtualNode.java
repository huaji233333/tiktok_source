package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;

public abstract class ARTVirtualNode extends ReactShadowNodeImpl {
  private static final float[] sMatrixData = new float[9];
  
  private static final float[] sRawMatrix = new float[9];
  
  private Matrix mMatrix = new Matrix();
  
  protected float mOpacity = 1.0F;
  
  protected final float mScale = (DisplayMetricsHolder.getWindowDisplayMetrics()).density;
  
  public ARTVirtualNode() {}
  
  protected ARTVirtualNode(ARTVirtualNode paramARTVirtualNode) {
    super(paramARTVirtualNode);
    this.mOpacity = paramARTVirtualNode.mOpacity;
    this.mMatrix = new Matrix(paramARTVirtualNode.mMatrix);
  }
  
  public abstract void draw(Canvas paramCanvas, Paint paramPaint, float paramFloat);
  
  public boolean isVirtual() {
    return true;
  }
  
  protected void restoreCanvas(Canvas paramCanvas) {
    paramCanvas.restore();
  }
  
  protected final void saveAndSetupCanvas(Canvas paramCanvas) {
    paramCanvas.save();
    Matrix matrix = this.mMatrix;
    if (matrix != null)
      paramCanvas.concat(matrix); 
  }
  
  @ReactProp(defaultFloat = 1.0F, name = "opacity")
  public void setOpacity(float paramFloat) {
    this.mOpacity = paramFloat;
    markUpdated();
  }
  
  @ReactProp(name = "transform")
  public void setTransform(ReadableArray paramReadableArray) {
    if (paramReadableArray != null) {
      int i = PropHelper.toFloatArray(paramReadableArray, sMatrixData);
      if (i == 6) {
        setupMatrix();
      } else if (i != -1) {
        throw new JSApplicationIllegalArgumentException("Transform matrices must be of size 6");
      } 
    } else {
      this.mMatrix = null;
    } 
    markUpdated();
  }
  
  protected void setupMatrix() {
    float[] arrayOfFloat1 = sRawMatrix;
    float[] arrayOfFloat2 = sMatrixData;
    arrayOfFloat1[0] = arrayOfFloat2[0];
    arrayOfFloat1[1] = arrayOfFloat2[2];
    float f1 = arrayOfFloat2[4];
    float f2 = this.mScale;
    arrayOfFloat1[2] = f1 * f2;
    arrayOfFloat1[3] = arrayOfFloat2[1];
    arrayOfFloat1[4] = arrayOfFloat2[3];
    arrayOfFloat1[5] = arrayOfFloat2[5] * f2;
    arrayOfFloat1[6] = 0.0F;
    arrayOfFloat1[7] = 0.0F;
    arrayOfFloat1[8] = 1.0F;
    if (this.mMatrix == null)
      this.mMatrix = new Matrix(); 
    this.mMatrix.setValues(sRawMatrix);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\art\ARTVirtualNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */