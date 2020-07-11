package com.graphic.RNCanvas;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import java.util.ArrayList;
import java.util.HashMap;

public class CanvasRenderingContext2D {
  private final String COLOR = "_color";
  
  private final String COLOR_PROPS = "_color_steps";
  
  private final String END_POS = "_end_pos";
  
  private final String POS = "_pos";
  
  private final String START_POS = "_start_pos";
  
  private final String X = "_x";
  
  private final String Y = "_y";
  
  private Canvas canvas;
  
  private CanvasDrawingState currentState;
  
  private float[] lastPoint;
  
  private final Matrix matrix = new Matrix();
  
  private final Paint paint = new Paint();
  
  private final Path path = new Path();
  
  private float scale;
  
  private final CanvasDrawingStateManager stateManager = new CanvasDrawingStateManager();
  
  private void drawText(String paramString, float paramFloat1, float paramFloat2) {
    float f1 = this.scale;
    float f2 = getTextVerticalOffset();
    this.canvas.drawText(paramString, paramFloat1 * f1, paramFloat2 * f1 + f2, this.paint);
  }
  
  private float getTextVerticalOffset() {
    if (this.currentState.textBaseline == 0)
      return 0.0F; 
    Paint.FontMetrics fontMetrics = this.paint.getFontMetrics();
    float f = fontMetrics.ascent + fontMetrics.descent;
    return (this.currentState.textBaseline == 1) ? -f : ((this.currentState.textBaseline == 2) ? -(f / 2.0F) : 0.0F);
  }
  
  private float modulus(float paramFloat1, float paramFloat2) {
    float f = paramFloat1 % paramFloat2;
    paramFloat1 = f;
    if (f < 0.0F)
      paramFloat1 = f + paramFloat2; 
    return paramFloat1;
  }
  
  private void resetLastPoint() {
    this.lastPoint = new float[] { 0.0F, 0.0F };
  }
  
  private void resetPaint() {
    this.paint.reset();
    this.paint.setFlags(1);
    this.paint.setTextSize(this.currentState.textSize);
    this.paint.setTextAlign(this.currentState.textAlign);
    this.paint.clearShadowLayer();
  }
  
  private void setPaintStyle(Paint.Style paramStyle, int[] paramArrayOfint) {
    this.paint.setStyle(paramStyle);
    this.paint.setARGB((int)(paramArrayOfint[0] * this.currentState.globalAlpha), paramArrayOfint[1], paramArrayOfint[2], paramArrayOfint[3]);
    this.paint.setShadowLayer(this.currentState.shadowBlur, this.currentState.shadowOffsetX, this.currentState.shadowOffsetY, CanvasConvert.convertColorListToColor(this.currentState.shadowColor));
  }
  
  private void setUpCurrentState() {
    this.currentState = this.stateManager.getCurrentState();
  }
  
  private void setUpFillPaint() {
    resetPaint();
    setPaintStyle(Paint.Style.FILL, this.currentState.fillStyle);
    if (this.currentState.mLinearGradient != null)
      this.paint.setShader((Shader)this.currentState.mLinearGradient); 
  }
  
  private void setUpStrokePaint() {
    resetPaint();
    setPaintStyle(Paint.Style.STROKE, this.currentState.strokeStyle);
    this.paint.setStrokeCap(this.currentState.strokeLineCap);
    this.paint.setStrokeWidth(this.currentState.strokeLineWidth * this.scale);
    this.paint.setStrokeJoin(this.currentState.strokeLineJoin);
    this.paint.setStrokeMiter(this.currentState.miterLimit);
    this.paint.setPathEffect((PathEffect)this.currentState.strokeLineDash);
    if (this.currentState.mLinearGradient != null)
      this.paint.setShader((Shader)this.currentState.mLinearGradient); 
  }
  
  private void trackPoint(float paramFloat1, float paramFloat2) {
    float[] arrayOfFloat = this.lastPoint;
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
  }
  
  public void arc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    arc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, false);
  }
  
  public void arc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, boolean paramBoolean) {
    float f3 = this.scale;
    float f1 = paramFloat1 * f3;
    float f2 = paramFloat2 * f3;
    paramFloat3 *= f3;
    paramFloat4 = (float)Math.toDegrees(paramFloat4);
    paramFloat1 = (float)Math.toDegrees(paramFloat5) - paramFloat4;
    if (Math.abs(paramFloat1) >= 360.0F) {
      Path.Direction direction;
      Path path = this.path;
      if (paramBoolean) {
        direction = Path.Direction.CCW;
      } else {
        direction = Path.Direction.CW;
      } 
      path.addCircle(f1, f2, paramFloat3, direction);
      return;
    } 
    paramFloat2 = modulus(paramFloat1, 360.0F);
    paramFloat1 = paramFloat2;
    if (paramBoolean) {
      paramFloat1 = paramFloat2;
      if (paramFloat2 < 360.0F)
        paramFloat1 = (360.0F - paramFloat2) * -1.0F; 
    } 
    RectF rectF = new RectF(f1 - paramFloat3, f2 - paramFloat3, f1 + paramFloat3, f2 + paramFloat3);
    this.path.arcTo(rectF, paramFloat4, paramFloat1);
  }
  
  public void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {
    boolean bool;
    float[] arrayOfFloat1 = this.lastPoint;
    float f1 = arrayOfFloat1[0];
    float f5 = this.scale;
    float f3 = f1 * f5;
    float f4 = arrayOfFloat1[1] * f5;
    f1 = paramFloat1 * f5;
    float f2 = paramFloat2 * f5;
    paramFloat1 = paramFloat3 * f5;
    paramFloat2 = paramFloat4 * f5;
    paramFloat3 = f5 * paramFloat5;
    if ((f1 == f3 && f2 == f4) || (f1 == paramFloat1 && f2 == paramFloat2) || paramFloat3 == 0.0F) {
      lineTo(f1, f2);
      return;
    } 
    float[] arrayOfFloat3 = new float[2];
    arrayOfFloat3[0] = f3 - f1;
    arrayOfFloat3[1] = f4 - f2;
    float[] arrayOfFloat2 = new float[2];
    arrayOfFloat2[0] = paramFloat1 - f1;
    arrayOfFloat2[1] = paramFloat2 - f2;
    double d1 = arrayOfFloat3[0];
    double d2 = arrayOfFloat3[0];
    Double.isNaN(d1);
    Double.isNaN(d2);
    double d3 = (arrayOfFloat3[1] * arrayOfFloat3[1]);
    Double.isNaN(d3);
    paramFloat1 = (float)Math.sqrt(d1 * d2 + d3);
    d1 = arrayOfFloat2[0];
    d2 = arrayOfFloat2[0];
    Double.isNaN(d1);
    Double.isNaN(d2);
    d3 = (arrayOfFloat2[1] * arrayOfFloat2[1]);
    Double.isNaN(d3);
    paramFloat4 = (float)Math.sqrt(d1 * d2 + d3);
    d1 = ((arrayOfFloat3[0] * arrayOfFloat2[0] + arrayOfFloat3[1] * arrayOfFloat2[1]) / paramFloat1 * paramFloat4);
    if (d1 == -1.0D) {
      lineTo(f1, f2);
      return;
    } 
    if (d1 == 1.0D) {
      paramFloat1 = 65535.0F / paramFloat1;
      lineTo(f3 + arrayOfFloat3[0] * paramFloat1, f4 + paramFloat1 * arrayOfFloat3[1]);
      return;
    } 
    paramFloat5 = paramFloat3 / (float)Math.tan(Math.acos(d1) / 2.0D);
    paramFloat1 = paramFloat5 / paramFloat1;
    arrayOfFloat1 = new float[2];
    arrayOfFloat1[0] = arrayOfFloat3[0] * paramFloat1 + f1;
    arrayOfFloat1[1] = paramFloat1 * arrayOfFloat3[1] + f2;
    float[] arrayOfFloat4 = new float[2];
    arrayOfFloat4[0] = arrayOfFloat3[1];
    arrayOfFloat4[1] = -arrayOfFloat3[0];
    d1 = arrayOfFloat4[0];
    d2 = arrayOfFloat4[0];
    Double.isNaN(d1);
    Double.isNaN(d2);
    d3 = (arrayOfFloat4[1] * arrayOfFloat4[1]);
    Double.isNaN(d3);
    paramFloat1 = (float)Math.sqrt(d1 * d2 + d3);
    paramFloat2 = paramFloat3 / paramFloat1;
    if (((arrayOfFloat4[0] * arrayOfFloat2[0] + arrayOfFloat4[1] * arrayOfFloat2[1]) / paramFloat1 * paramFloat4) < 0.0D) {
      arrayOfFloat4[0] = -arrayOfFloat4[0];
      arrayOfFloat4[1] = -arrayOfFloat4[1];
    } 
    arrayOfFloat3 = new float[2];
    arrayOfFloat3[0] = arrayOfFloat1[0] + arrayOfFloat4[0] * paramFloat2;
    arrayOfFloat3[1] = arrayOfFloat1[1] + paramFloat2 * arrayOfFloat4[1];
    arrayOfFloat4[0] = -arrayOfFloat4[0];
    arrayOfFloat4[1] = -arrayOfFloat4[1];
    paramFloat2 = (float)(Math.acos((arrayOfFloat4[0] / paramFloat1)) * 180.0D / Math.PI);
    paramFloat1 = paramFloat2;
    if (arrayOfFloat4[1] < 0.0F)
      paramFloat1 = 360.0F - paramFloat2; 
    paramFloat2 = paramFloat5 / paramFloat4;
    arrayOfFloat4 = new float[2];
    arrayOfFloat4[0] = f1 + arrayOfFloat2[0] * paramFloat2;
    arrayOfFloat4[1] = f2 + paramFloat2 * arrayOfFloat2[1];
    arrayOfFloat2 = new float[2];
    arrayOfFloat2[0] = arrayOfFloat4[0] - arrayOfFloat3[0];
    arrayOfFloat2[1] = arrayOfFloat4[1] - arrayOfFloat3[1];
    d1 = arrayOfFloat2[0];
    d2 = arrayOfFloat2[0];
    Double.isNaN(d1);
    Double.isNaN(d2);
    d3 = (arrayOfFloat2[1] * arrayOfFloat2[1]);
    Double.isNaN(d3);
    paramFloat2 = (float)Math.sqrt(d1 * d2 + d3);
    paramFloat2 = (float)(Math.acos((arrayOfFloat2[0] / paramFloat2)) * 180.0D / Math.PI);
    if (arrayOfFloat2[1] < 0.0F)
      paramFloat2 = 360.0F - paramFloat2; 
    if (paramFloat1 > paramFloat2 && paramFloat1 - paramFloat2 < 180.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    if (paramFloat1 < paramFloat2 && paramFloat2 - paramFloat1 > 180.0F)
      bool = true; 
    lineTo(arrayOfFloat1[0], arrayOfFloat1[1]);
    arc(arrayOfFloat3[0], arrayOfFloat3[1], paramFloat3, paramFloat1, paramFloat2, bool);
  }
  
  public void beginPath() {
    this.path.reset();
    resetLastPoint();
  }
  
  public void bezierCurveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    float f = this.scale;
    paramFloat5 *= f;
    paramFloat6 *= f;
    trackPoint(paramFloat5, paramFloat6);
    this.path.cubicTo(paramFloat1 * f, paramFloat2 * f, paramFloat3 * f, paramFloat4 * f, paramFloat5, paramFloat6);
  }
  
  public void clearRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    float f = this.scale;
    paramFloat1 *= f;
    paramFloat2 *= f;
    Paint paint = new Paint();
    paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    this.canvas.drawRect(paramFloat1, paramFloat2, paramFloat1 + paramFloat3 * f, paramFloat2 + paramFloat4 * f, paint);
  }
  
  public void clip() {
    this.path.setFillType(Path.FillType.WINDING);
    this.canvas.clipPath(this.path);
  }
  
  public void clip(String paramString) {
    if (paramString.equals("nonzero")) {
      clip();
      return;
    } 
    if (paramString.equals("evenodd")) {
      this.path.setFillType(Path.FillType.EVEN_ODD);
      this.canvas.clipPath(this.path);
    } 
  }
  
  public void closePath() {
    this.path.close();
  }
  
  public void createImageData() {}
  
  public void createLinearGradient(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {}
  
  public void createPattern() {}
  
  public void createRadialGradient(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {}
  
  public void drawFocusIfNeeded() {}
  
  public void drawImage() {}
  
  public void fill() {
    setUpFillPaint();
    this.canvas.drawPath(this.path, this.paint);
  }
  
  public void fillRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    float f = this.scale;
    paramFloat1 *= f;
    paramFloat2 *= f;
    RectF rectF = new RectF(paramFloat1, paramFloat2, paramFloat3 * f + paramFloat1, paramFloat4 * f + paramFloat2);
    setUpFillPaint();
    this.canvas.drawRect(rectF, this.paint);
  }
  
  public void fillText(String paramString, float paramFloat1, float paramFloat2) {
    setUpFillPaint();
    drawText(paramString, paramFloat1, paramFloat2);
  }
  
  public void getImageData() {}
  
  public DashPathEffect getLineDash() {
    return this.currentState.strokeLineDash;
  }
  
  public void isPointInPath() {}
  
  public void isPointInStroke() {}
  
  public void lineTo(float paramFloat1, float paramFloat2) {
    float f = this.scale;
    paramFloat1 *= f;
    paramFloat2 *= f;
    trackPoint(paramFloat1, paramFloat2);
    this.path.lineTo(paramFloat1, paramFloat2);
  }
  
  public HashMap measureText(String paramString) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("width", Float.valueOf(this.paint.measureText(paramString)));
    return hashMap;
  }
  
  public void moveTo(float paramFloat1, float paramFloat2) {
    float f = this.scale;
    paramFloat1 *= f;
    paramFloat2 *= f;
    trackPoint(paramFloat1, paramFloat2);
    this.path.moveTo(paramFloat1, paramFloat2);
  }
  
  public void putImageData() {}
  
  public void quadraticCurveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    float f = this.scale;
    paramFloat3 *= f;
    paramFloat4 *= f;
    trackPoint(paramFloat3, paramFloat4);
    this.path.quadTo(paramFloat1 * f, paramFloat2 * f, paramFloat3, paramFloat4);
  }
  
  public void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    float f = this.scale;
    paramFloat1 *= f;
    paramFloat2 *= f;
    RectF rectF = new RectF(paramFloat1, paramFloat2, paramFloat3 * f + paramFloat1, paramFloat4 * f + paramFloat2);
    this.path.addRect(rectF, Path.Direction.CW);
  }
  
  public void resetTransform() {
    this.matrix.reset();
    this.canvas.setMatrix(this.matrix);
  }
  
  public void restore() {
    this.stateManager.restore();
    setUpCurrentState();
    this.canvas.restore();
  }
  
  public void rotate(float paramFloat) {
    this.canvas.rotate(paramFloat);
  }
  
  public void save() {
    this.stateManager.save();
    setUpCurrentState();
    this.canvas.save();
  }
  
  public void scale(float paramFloat1, float paramFloat2) {
    this.canvas.scale(paramFloat1, paramFloat2);
  }
  
  public void scrollPathIntoView() {}
  
  public void setCanvas(Canvas paramCanvas) {
    this.canvas = paramCanvas;
    this.stateManager.reset();
    setUpCurrentState();
    resetLastPoint();
    this.path.reset();
    resetPaint();
  }
  
  public void setDevicePixelRatio(float paramFloat) {
    this.scale = paramFloat;
  }
  
  public void setFillStyle(float[] paramArrayOffloat) {
    this.currentState.setFillStyle(paramArrayOffloat);
  }
  
  public void setFont(HashMap paramHashMap) {
    double d1 = ((Double)paramHashMap.get("fontSize")).doubleValue();
    double d2 = this.scale;
    Double.isNaN(d2);
    Typeface typeface = Typeface.SANS_SERIF;
    this.paint.setTypeface(typeface);
    this.currentState.setTextSize(Double.valueOf(d1 * d2).floatValue());
  }
  
  public void setGlobalAlpha(float paramFloat) {
    this.currentState.setGlobalAlpha(paramFloat);
  }
  
  public void setGradientStyle(HashMap paramHashMap) {
    String str = "_color";
    if (paramHashMap != null && paramHashMap.size() > 0 && paramHashMap.containsKey("_start_pos") && paramHashMap.containsKey("_end_pos") && paramHashMap.containsKey("_color_steps")) {
      HashMap hashMap1 = (HashMap)paramHashMap.get("_start_pos");
      HashMap hashMap2 = (HashMap)paramHashMap.get("_end_pos");
      double d4 = ((Double)hashMap1.get("_x")).doubleValue();
      double d1 = ((Double)hashMap1.get("_y")).doubleValue();
      double d2 = ((Double)hashMap2.get("_x")).doubleValue();
      double d3 = ((Double)hashMap2.get("_y")).doubleValue();
      ArrayList<Integer> arrayList1 = new ArrayList();
      ArrayList<Float> arrayList2 = new ArrayList();
      ArrayList<HashMap> arrayList = (ArrayList)paramHashMap.get("_color_steps");
      int i = 0;
      while (true) {
        if (i < arrayList.size()) {
          try {
            HashMap hashMap = arrayList.get(i);
            if (hashMap.containsKey("_pos") && hashMap.containsKey(str)) {
              int j = Color.parseColor((String)hashMap.get(str));
              try {
                arrayList1.add(Integer.valueOf(j));
                Double double_ = (Double)hashMap.get("_pos");
                try {
                  arrayList2.add(Float.valueOf((float)double_.doubleValue()));
                } catch (Exception exception) {}
              } catch (Exception exception) {}
            } 
          } catch (Exception exception) {}
          i++;
          continue;
        } 
        if (arrayList1.size() > 0 && arrayList2.size() > 0) {
          int[] arrayOfInt = new int[arrayList1.size()];
          float[] arrayOfFloat = new float[arrayList1.size()];
          for (i = 0; i < arrayList1.size(); i++) {
            arrayOfInt[i] = ((Integer)arrayList1.get(i)).intValue();
            arrayOfFloat[i] = ((Float)arrayList2.get(i)).floatValue();
          } 
          if (this.canvas.getClipBounds() != null && (this.canvas.getClipBounds()).left != 0 && (this.canvas.getClipBounds()).top != 0) {
            float f1;
            float f2;
            if (d4 == d2) {
              CanvasDrawingState canvasDrawingState1 = this.currentState;
              float f5 = (float)d4;
              float f6 = (this.canvas.getClipBounds()).left;
              if (d1 > d3) {
                f1 = (float)d3 + ((this.canvas.getClipBounds()).top * 3);
              } else {
                d4 = (float)d3;
                double d = (this.canvas.getClipBounds()).top;
                Double.isNaN(d);
                Double.isNaN(d4);
                f1 = (float)(d4 - d * 0.6D);
              } 
              float f7 = (float)d2;
              float f8 = (this.canvas.getClipBounds()).left;
              if (d3 > d1) {
                f2 = (float)d3 + ((this.canvas.getClipBounds()).top * 2);
              } else {
                d1 = (float)d3;
                d2 = (this.canvas.getClipBounds()).top;
                Double.isNaN(d2);
                Double.isNaN(d1);
                f2 = (float)(d1 + d2 * 1.2D);
              } 
              canvasDrawingState1.setLinearGradient(new LinearGradient(f5 + f6, f1, f7 + f8, f2, arrayOfInt, arrayOfFloat, Shader.TileMode.CLAMP));
              return;
            } 
            CanvasDrawingState canvasDrawingState = this.currentState;
            if (d4 > d2) {
              f1 = (float)d4 + ((this.canvas.getClipBounds()).left * 3);
            } else {
              f1 = (float)d4;
            } 
            float f3 = (float)d1;
            float f4 = (this.canvas.getClipBounds()).top;
            if (d2 > d4) {
              f2 = (float)d2;
              i = (this.canvas.getClipBounds()).left * 2;
            } else {
              f2 = (float)d2;
              i = (this.canvas.getClipBounds()).left;
            } 
            canvasDrawingState.setLinearGradient(new LinearGradient(f1, f3 + f4, f2 + i, (float)d3 + (this.canvas.getClipBounds()).top, arrayOfInt, arrayOfFloat, Shader.TileMode.CLAMP));
          } 
        } 
        return;
      } 
    } 
  }
  
  public void setLineCap(String paramString) {
    this.currentState.setStrokeLineCap(paramString);
  }
  
  public void setLineDash(float[] paramArrayOffloat) {
    if (paramArrayOffloat.length == 0)
      return; 
    this.currentState.setStrokeLineDash(paramArrayOffloat);
  }
  
  public void setLineDashOffset(float paramFloat) {
    this.currentState.setLineDashOffset(paramFloat);
  }
  
  public void setLineJoin(String paramString) {
    this.currentState.setStrokeLineJoin(paramString);
  }
  
  public void setLineWidth(float paramFloat) {
    this.currentState.setStrokeLineWidth(paramFloat);
  }
  
  public void setMiterLimit(float paramFloat) {
    this.currentState.setMiterLimit(paramFloat);
  }
  
  public void setShadowBlur(float paramFloat) {
    this.currentState.setShadowBlur(paramFloat);
  }
  
  public void setShadowColor(float[] paramArrayOffloat) {
    this.currentState.setShadowColor(paramArrayOffloat);
  }
  
  public void setShadowOffsetX(float paramFloat) {
    this.currentState.setShadowOffsetX(paramFloat);
  }
  
  public void setShadowOffsetY(float paramFloat) {
    this.currentState.setShadowOffsetY(paramFloat);
  }
  
  public void setStrokeStyle(float[] paramArrayOffloat) {
    try {
      this.currentState.setStrokeStyle(paramArrayOffloat);
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      return;
    } 
  }
  
  public void setTextAlign(String paramString) {
    this.currentState.setTextAlign(paramString);
  }
  
  public void setTextBaseline(String paramString) {
    this.currentState.setTextBaseline(paramString);
  }
  
  public void setTransform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    resetTransform();
    transform(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public void stroke() {
    setUpStrokePaint();
    this.canvas.drawPath(this.path, this.paint);
  }
  
  public void strokeRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    float f = this.scale;
    paramFloat1 *= f;
    paramFloat2 *= f;
    RectF rectF = new RectF(paramFloat1, paramFloat2, paramFloat3 * f + paramFloat1, paramFloat4 * f + paramFloat2);
    setUpStrokePaint();
    this.canvas.drawRect(rectF, this.paint);
  }
  
  public void strokeText(String paramString, float paramFloat1, float paramFloat2) {
    setUpStrokePaint();
    drawText(paramString, paramFloat1, paramFloat2);
  }
  
  public void transform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    this.matrix.reset();
    Matrix matrix = this.matrix;
    float f = this.scale;
    matrix.setValues(new float[] { paramFloat1, paramFloat3, paramFloat5 * f, paramFloat2, paramFloat4, paramFloat6 * f, 0.0F, 0.0F, 1.0F });
    this.canvas.concat(this.matrix);
  }
  
  public void translate(float paramFloat1, float paramFloat2) {
    float f = this.scale;
    this.canvas.translate(paramFloat1 * f, paramFloat2 * f);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasRenderingContext2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */