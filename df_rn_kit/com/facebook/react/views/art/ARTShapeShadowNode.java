package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import com.facebook.common.e.a;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.ArrayUtils;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ARTShapeShadowNode extends ARTVirtualNode {
  private float[] mBrushData;
  
  protected Path mPath;
  
  private int mStrokeCap = 1;
  
  private float[] mStrokeColor;
  
  private float[] mStrokeDash;
  
  private int mStrokeJoin = 1;
  
  private float mStrokeWidth = 1.0F;
  
  public ARTShapeShadowNode() {}
  
  public ARTShapeShadowNode(ARTShapeShadowNode paramARTShapeShadowNode) {
    super(paramARTShapeShadowNode);
    this.mPath = new Path(paramARTShapeShadowNode.mPath);
    this.mStrokeColor = ArrayUtils.copyArray(paramARTShapeShadowNode.mStrokeColor);
    this.mBrushData = ArrayUtils.copyArray(paramARTShapeShadowNode.mBrushData);
    this.mStrokeDash = ArrayUtils.copyArray(paramARTShapeShadowNode.mStrokeDash);
    this.mStrokeWidth = paramARTShapeShadowNode.mStrokeWidth;
    this.mStrokeCap = paramARTShapeShadowNode.mStrokeCap;
    this.mStrokeJoin = paramARTShapeShadowNode.mStrokeJoin;
  }
  
  private Path createPath(float[] paramArrayOffloat) {
    Path path = new Path();
    path.moveTo(0.0F, 0.0F);
    int i = 0;
    while (i < paramArrayOffloat.length) {
      StringBuilder stringBuilder1;
      int j = i + 1;
      int k = (int)paramArrayOffloat[i];
      if (k != 0) {
        i = 1;
        if (k != 1) {
          if (k != 2) {
            if (k != 3) {
              if (k == 4) {
                k = j + 1;
                float f9 = paramArrayOffloat[j] * this.mScale;
                j = k + 1;
                float f10 = paramArrayOffloat[k] * this.mScale;
                k = j + 1;
                float f11 = paramArrayOffloat[j] * this.mScale;
                j = k + 1;
                float f12 = (float)Math.toDegrees(paramArrayOffloat[k]);
                k = j + 1;
                float f7 = (float)Math.toDegrees(paramArrayOffloat[j]);
                j = k + 1;
                if (paramArrayOffloat[k] == 1.0F)
                  i = 0; 
                f7 -= f12;
                if (Math.abs(f7) >= 360.0F) {
                  Path.Direction direction;
                  if (i != 0) {
                    direction = Path.Direction.CCW;
                  } else {
                    direction = Path.Direction.CW;
                  } 
                  path.addCircle(f9, f10, f11, direction);
                  i = j;
                  continue;
                } 
                float f8 = modulus(f7, 360.0F);
                f7 = f8;
                if (i != 0) {
                  f7 = f8;
                  if (f8 < 360.0F)
                    f7 = (360.0F - f8) * -1.0F; 
                } 
                path.arcTo(new RectF(f9 - f11, f10 - f11, f9 + f11, f10 + f11), f12, f7);
                i = j;
                continue;
              } 
              stringBuilder1 = new StringBuilder("Unrecognized drawing instruction ");
              stringBuilder1.append(k);
              throw new JSApplicationIllegalArgumentException(stringBuilder1.toString());
            } 
            i = j + 1;
            StringBuilder stringBuilder3 = stringBuilder1[j];
            float f2 = this.mScale;
            j = i + 1;
            StringBuilder stringBuilder4 = stringBuilder1[i];
            float f3 = this.mScale;
            i = j + 1;
            StringBuilder stringBuilder5 = stringBuilder1[j];
            float f4 = this.mScale;
            j = i + 1;
            StringBuilder stringBuilder6 = stringBuilder1[i];
            float f5 = this.mScale;
            k = j + 1;
            StringBuilder stringBuilder7 = stringBuilder1[j];
            float f6 = this.mScale;
            i = k + 1;
            path.cubicTo(stringBuilder3 * f2, f3 * stringBuilder4, f4 * stringBuilder5, f5 * stringBuilder6, f6 * stringBuilder7, stringBuilder1[k] * this.mScale);
            continue;
          } 
          k = j + 1;
          StringBuilder stringBuilder = stringBuilder1[j];
          float f1 = this.mScale;
          i = k + 1;
          path.lineTo(stringBuilder * f1, stringBuilder1[k] * this.mScale);
          continue;
        } 
        path.close();
        i = j;
        continue;
      } 
      k = j + 1;
      StringBuilder stringBuilder2 = stringBuilder1[j];
      float f = this.mScale;
      i = k + 1;
      path.moveTo(stringBuilder2 * f, stringBuilder1[k] * this.mScale);
    } 
    return path;
  }
  
  private float modulus(float paramFloat1, float paramFloat2) {
    float f = paramFloat1 % paramFloat2;
    paramFloat1 = f;
    if (f < 0.0F)
      paramFloat1 = f + paramFloat2; 
    return paramFloat1;
  }
  
  public void draw(Canvas paramCanvas, Paint paramPaint, float paramFloat) {
    paramFloat *= this.mOpacity;
    if (paramFloat > 0.01F) {
      saveAndSetupCanvas(paramCanvas);
      if (this.mPath != null) {
        if (setupFillPaint(paramPaint, paramFloat))
          paramCanvas.drawPath(this.mPath, paramPaint); 
        if (setupStrokePaint(paramPaint, paramFloat))
          paramCanvas.drawPath(this.mPath, paramPaint); 
        restoreCanvas(paramCanvas);
      } else {
        throw new JSApplicationIllegalArgumentException("Shapes should have a valid path (d) prop");
      } 
    } 
    markUpdateSeen();
  }
  
  public ARTShapeShadowNode mutableCopy() {
    return new ARTShapeShadowNode(this);
  }
  
  @ReactProp(name = "fill")
  public void setFill(ReadableArray paramReadableArray) {
    this.mBrushData = PropHelper.toFloatArray(paramReadableArray);
    markUpdated();
  }
  
  @ReactProp(name = "d")
  public void setShapePath(ReadableArray paramReadableArray) {
    this.mPath = createPath(PropHelper.toFloatArray(paramReadableArray));
    markUpdated();
  }
  
  @ReactProp(name = "stroke")
  public void setStroke(ReadableArray paramReadableArray) {
    this.mStrokeColor = PropHelper.toFloatArray(paramReadableArray);
    markUpdated();
  }
  
  @ReactProp(defaultInt = 1, name = "strokeCap")
  public void setStrokeCap(int paramInt) {
    this.mStrokeCap = paramInt;
    markUpdated();
  }
  
  @ReactProp(name = "strokeDash")
  public void setStrokeDash(ReadableArray paramReadableArray) {
    this.mStrokeDash = PropHelper.toFloatArray(paramReadableArray);
    markUpdated();
  }
  
  @ReactProp(defaultInt = 1, name = "strokeJoin")
  public void setStrokeJoin(int paramInt) {
    this.mStrokeJoin = paramInt;
    markUpdated();
  }
  
  @ReactProp(defaultFloat = 1.0F, name = "strokeWidth")
  public void setStrokeWidth(float paramFloat) {
    this.mStrokeWidth = paramFloat;
    markUpdated();
  }
  
  protected boolean setupFillPaint(Paint paramPaint, float paramFloat) {
    float[] arrayOfFloat = this.mBrushData;
    int i = 0;
    if (arrayOfFloat != null && arrayOfFloat.length > 0) {
      StringBuilder stringBuilder;
      paramPaint.reset();
      paramPaint.setFlags(1);
      paramPaint.setStyle(Paint.Style.FILL);
      arrayOfFloat = this.mBrushData;
      int j = (int)arrayOfFloat[0];
      if (j != 0) {
        float[] arrayOfFloat1;
        if (j != 1) {
          stringBuilder = new StringBuilder("ART: Color type ");
          stringBuilder.append(j);
          stringBuilder.append(" not supported!");
          a.b("ReactNative", stringBuilder.toString());
          return true;
        } 
        if (arrayOfFloat.length < 5) {
          stringBuilder = new StringBuilder("[ARTShapeShadowNode setupFillPaint] expects 5 elements, received ");
          stringBuilder.append(this.mBrushData.length);
          a.b("ReactNative", stringBuilder.toString());
          return false;
        } 
        paramFloat = arrayOfFloat[1];
        float f1 = this.mScale;
        float f2 = this.mBrushData[2];
        float f3 = this.mScale;
        float f4 = this.mBrushData[3];
        float f5 = this.mScale;
        float f6 = this.mBrushData[4];
        float f7 = this.mScale;
        j = (this.mBrushData.length - 5) / 5;
        if (j > 0) {
          int[] arrayOfInt = new int[j];
          arrayOfFloat1 = new float[j];
          while (i < j) {
            float[] arrayOfFloat2 = this.mBrushData;
            arrayOfFloat1[i] = arrayOfFloat2[j * 4 + 5 + i];
            int k = i * 4 + 5;
            int m = (int)(arrayOfFloat2[k + 0] * 255.0F);
            int n = (int)(arrayOfFloat2[k + 1] * 255.0F);
            int i1 = (int)(arrayOfFloat2[k + 2] * 255.0F);
            arrayOfInt[i] = Color.argb((int)(arrayOfFloat2[k + 3] * 255.0F), m, n, i1);
            i++;
          } 
        } else {
          arrayOfFloat = null;
          arrayOfFloat1 = arrayOfFloat;
        } 
        stringBuilder.setShader((Shader)new LinearGradient(paramFloat * f1, f2 * f3, f4 * f5, f6 * f7, (int[])arrayOfFloat, arrayOfFloat1, Shader.TileMode.CLAMP));
        return true;
      } 
      if (arrayOfFloat.length > 4) {
        paramFloat = arrayOfFloat[4] * paramFloat * 255.0F;
      } else {
        paramFloat *= 255.0F;
      } 
      i = (int)paramFloat;
      arrayOfFloat = this.mBrushData;
      stringBuilder.setARGB(i, (int)(arrayOfFloat[1] * 255.0F), (int)(arrayOfFloat[2] * 255.0F), (int)(arrayOfFloat[3] * 255.0F));
      return true;
    } 
    return false;
  }
  
  protected boolean setupStrokePaint(Paint paramPaint, float paramFloat) {
    if (this.mStrokeWidth != 0.0F) {
      float[] arrayOfFloat = this.mStrokeColor;
      if (arrayOfFloat != null) {
        StringBuilder stringBuilder;
        if (arrayOfFloat.length == 0)
          return false; 
        paramPaint.reset();
        paramPaint.setFlags(1);
        paramPaint.setStyle(Paint.Style.STROKE);
        int i = this.mStrokeCap;
        if (i != 0) {
          if (i != 1) {
            if (i == 2) {
              paramPaint.setStrokeCap(Paint.Cap.SQUARE);
            } else {
              stringBuilder = new StringBuilder("strokeCap ");
              stringBuilder.append(this.mStrokeCap);
              stringBuilder.append(" unrecognized");
              throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
            } 
          } else {
            stringBuilder.setStrokeCap(Paint.Cap.ROUND);
          } 
        } else {
          stringBuilder.setStrokeCap(Paint.Cap.BUTT);
        } 
        i = this.mStrokeJoin;
        if (i != 0) {
          if (i != 1) {
            if (i == 2) {
              stringBuilder.setStrokeJoin(Paint.Join.BEVEL);
            } else {
              stringBuilder = new StringBuilder("strokeJoin ");
              stringBuilder.append(this.mStrokeJoin);
              stringBuilder.append(" unrecognized");
              throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
            } 
          } else {
            stringBuilder.setStrokeJoin(Paint.Join.ROUND);
          } 
        } else {
          stringBuilder.setStrokeJoin(Paint.Join.MITER);
        } 
        stringBuilder.setStrokeWidth(this.mStrokeWidth * this.mScale);
        arrayOfFloat = this.mStrokeColor;
        if (arrayOfFloat.length > 3) {
          paramFloat = arrayOfFloat[3] * paramFloat * 255.0F;
        } else {
          paramFloat *= 255.0F;
        } 
        i = (int)paramFloat;
        arrayOfFloat = this.mStrokeColor;
        stringBuilder.setARGB(i, (int)(arrayOfFloat[0] * 255.0F), (int)(arrayOfFloat[1] * 255.0F), (int)(arrayOfFloat[2] * 255.0F));
        arrayOfFloat = this.mStrokeDash;
        if (arrayOfFloat != null && arrayOfFloat.length > 0)
          stringBuilder.setPathEffect((PathEffect)new DashPathEffect(arrayOfFloat, 0.0F)); 
        return true;
      } 
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\art\ARTShapeShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */