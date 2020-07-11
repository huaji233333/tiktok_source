package com.graphic.RNCanvas;

import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;

public class CanvasDrawingState {
  public int[] fillStyle;
  
  public float globalAlpha;
  
  public float lineDashOffset;
  
  public LinearGradient mLinearGradient;
  
  public float miterLimit;
  
  private String primitiveLineCap;
  
  private float[] primitiveLineDash;
  
  private String primitiveLineJoin;
  
  private String primitiveTextAlign;
  
  public float shadowBlur;
  
  public int[] shadowColor;
  
  public float shadowOffsetX;
  
  public float shadowOffsetY;
  
  public Paint.Cap strokeLineCap;
  
  public DashPathEffect strokeLineDash;
  
  public Paint.Join strokeLineJoin;
  
  public float strokeLineWidth;
  
  public int[] strokeStyle;
  
  public Paint.Align textAlign;
  
  public int textBaseline;
  
  public float textSize;
  
  public CanvasDrawingState() {
    setUp();
  }
  
  public CanvasDrawingState(CanvasDrawingState paramCanvasDrawingState) {
    setUp(paramCanvasDrawingState);
  }
  
  private void setUp() {
    this.globalAlpha = 1.0F;
    this.fillStyle = new int[] { 255, 0, 0, 0 };
    this.strokeStyle = new int[] { 255, 0, 0, 0 };
    this.strokeLineWidth = 1.0F;
    this.strokeLineCap = Paint.Cap.BUTT;
    this.strokeLineJoin = Paint.Join.MITER;
    this.miterLimit = 0.0F;
    this.lineDashOffset = 0.0F;
    float f = this.lineDashOffset;
    this.strokeLineDash = new DashPathEffect(new float[] { 0.0F, 0.0F }, f);
    this.textSize = 10.0F;
    this.textBaseline = 0;
    this.textAlign = Paint.Align.LEFT;
    this.shadowColor = new int[] { 0, 0, 0, 0 };
    this.shadowBlur = 0.0F;
    this.shadowOffsetX = 0.0F;
    this.shadowOffsetY = 0.0F;
    this.primitiveTextAlign = "left";
    this.primitiveLineCap = "butt";
    this.primitiveLineJoin = "miter";
    this.primitiveLineDash = new float[] { 0.0F, 0.0F };
    this.mLinearGradient = null;
  }
  
  private void setUp(CanvasDrawingState paramCanvasDrawingState) {
    this.globalAlpha = CanvasDeepCopy.deepCopyFloat(paramCanvasDrawingState.globalAlpha);
    this.fillStyle = CanvasDeepCopy.deepCopyIntList(paramCanvasDrawingState.fillStyle);
    this.strokeStyle = CanvasDeepCopy.deepCopyIntList(paramCanvasDrawingState.strokeStyle);
    this.strokeLineWidth = CanvasDeepCopy.deepCopyFloat(paramCanvasDrawingState.strokeLineWidth);
    this.strokeLineCap = CanvasConvert.convertLineCap(paramCanvasDrawingState.primitiveLineCap);
    this.strokeLineJoin = CanvasConvert.convertLineJoin(paramCanvasDrawingState.primitiveLineJoin);
    this.miterLimit = CanvasDeepCopy.deepCopyFloat(paramCanvasDrawingState.miterLimit);
    this.lineDashOffset = CanvasDeepCopy.deepCopyFloat(paramCanvasDrawingState.lineDashOffset);
    this.strokeLineDash = CanvasConvert.convertLineDash(paramCanvasDrawingState.primitiveLineDash, this.lineDashOffset);
    this.textSize = CanvasDeepCopy.deepCopyFloat(paramCanvasDrawingState.textSize);
    this.textBaseline = CanvasDeepCopy.deepCopyInt(paramCanvasDrawingState.textBaseline);
    this.textAlign = CanvasConvert.convertTextAlign(paramCanvasDrawingState.primitiveTextAlign);
    this.shadowColor = CanvasDeepCopy.deepCopyIntList(paramCanvasDrawingState.shadowColor);
    this.shadowBlur = CanvasDeepCopy.deepCopyFloat(paramCanvasDrawingState.shadowBlur);
    this.shadowOffsetX = CanvasDeepCopy.deepCopyFloat(paramCanvasDrawingState.shadowOffsetX);
    this.shadowOffsetY = CanvasDeepCopy.deepCopyFloat(paramCanvasDrawingState.shadowOffsetY);
    this.primitiveTextAlign = CanvasDeepCopy.deepCopyString(paramCanvasDrawingState.primitiveTextAlign);
    this.primitiveLineCap = CanvasDeepCopy.deepCopyString(paramCanvasDrawingState.primitiveLineCap);
    this.primitiveLineJoin = CanvasDeepCopy.deepCopyString(paramCanvasDrawingState.primitiveLineJoin);
    this.primitiveLineDash = CanvasDeepCopy.deepCopyFloatList(paramCanvasDrawingState.primitiveLineDash);
  }
  
  public void setFillStyle(float[] paramArrayOffloat) {
    this.fillStyle = CanvasConvert.convertColor(paramArrayOffloat);
  }
  
  public void setGlobalAlpha(float paramFloat) {
    this.globalAlpha = paramFloat;
  }
  
  public void setLineDashOffset(float paramFloat) {
    this.lineDashOffset = paramFloat;
    this.strokeLineDash = CanvasConvert.convertLineDash(this.primitiveLineDash, this.lineDashOffset);
  }
  
  public void setLinearGradient(LinearGradient paramLinearGradient) {
    this.mLinearGradient = paramLinearGradient;
  }
  
  public void setMiterLimit(float paramFloat) {
    this.miterLimit = paramFloat;
  }
  
  public void setShadowBlur(float paramFloat) {
    this.shadowBlur = paramFloat;
  }
  
  public void setShadowColor(float[] paramArrayOffloat) {
    this.shadowColor = CanvasConvert.convertColor(paramArrayOffloat);
  }
  
  public void setShadowOffsetX(float paramFloat) {
    this.shadowOffsetX = paramFloat;
  }
  
  public void setShadowOffsetY(float paramFloat) {
    this.shadowOffsetY = paramFloat;
  }
  
  public void setStrokeLineCap(String paramString) {
    this.primitiveLineCap = paramString;
    this.strokeLineCap = CanvasConvert.convertLineCap(paramString);
  }
  
  public void setStrokeLineDash(float[] paramArrayOffloat) {
    this.primitiveLineDash = paramArrayOffloat;
    this.strokeLineDash = CanvasConvert.convertLineDash(paramArrayOffloat, this.lineDashOffset);
  }
  
  public void setStrokeLineJoin(String paramString) {
    this.primitiveLineJoin = paramString;
    this.strokeLineJoin = CanvasConvert.convertLineJoin(paramString);
  }
  
  public void setStrokeLineWidth(float paramFloat) {
    this.strokeLineWidth = paramFloat;
  }
  
  public void setStrokeStyle(float[] paramArrayOffloat) {
    this.strokeStyle = CanvasConvert.convertColor(paramArrayOffloat);
  }
  
  public void setTextAlign(String paramString) {
    this.primitiveTextAlign = paramString;
    this.textAlign = CanvasConvert.convertTextAlign(paramString);
  }
  
  public void setTextBaseline(String paramString) {
    this.textBaseline = CanvasConvert.convertTextBaseline(paramString);
  }
  
  public void setTextSize(float paramFloat) {
    this.textSize = paramFloat;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasDrawingState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */