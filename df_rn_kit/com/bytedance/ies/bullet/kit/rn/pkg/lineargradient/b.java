package com.bytedance.ies.bullet.kit.rn.pkg.lineargradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.PixelUtil;

public final class b extends View {
  private final Paint a = new Paint(1);
  
  private Path b;
  
  private RectF c;
  
  private float[] d;
  
  private float[] e = new float[] { 0.0F, 0.0F };
  
  private float[] f = new float[] { 0.0F, 1.0F };
  
  private int[] g;
  
  private int[] h = new int[] { 0, 0 };
  
  private float[] i = new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F };
  
  public b(Context paramContext) {
    super(paramContext);
  }
  
  private void a() {
    int[] arrayOfInt = this.g;
    if (arrayOfInt != null) {
      float[] arrayOfFloat = this.d;
      if (arrayOfFloat != null && arrayOfInt.length != arrayOfFloat.length)
        return; 
      arrayOfFloat = this.e;
      float f1 = arrayOfFloat[0];
      arrayOfInt = this.h;
      float f2 = arrayOfInt[0];
      float f3 = arrayOfFloat[1];
      float f4 = arrayOfInt[1];
      arrayOfFloat = this.f;
      LinearGradient linearGradient = new LinearGradient(f1 * f2, f3 * f4, arrayOfFloat[0] * arrayOfInt[0], arrayOfFloat[1] * arrayOfInt[1], this.g, this.d, Shader.TileMode.CLAMP);
      this.a.setShader((Shader)linearGradient);
      invalidate();
    } 
  }
  
  private void b() {
    if (this.b == null) {
      this.b = new Path();
      this.c = new RectF();
    } 
    this.b.reset();
    RectF rectF = this.c;
    int[] arrayOfInt = this.h;
    rectF.set(0.0F, 0.0F, arrayOfInt[0], arrayOfInt[1]);
    this.b.addRoundRect(this.c, this.i, Path.Direction.CW);
  }
  
  protected final void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    Path path = this.b;
    if (path == null) {
      paramCanvas.drawPaint(this.a);
      return;
    } 
    paramCanvas.drawPath(path, this.a);
  }
  
  protected final void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.h = new int[] { paramInt1, paramInt2 };
    b();
    a();
  }
  
  public final void setBorderRadii(ReadableArray paramReadableArray) {
    float[] arrayOfFloat = new float[paramReadableArray.size()];
    for (int i = 0; i < arrayOfFloat.length; i++)
      arrayOfFloat[i] = PixelUtil.toPixelFromDIP((float)paramReadableArray.getDouble(i)); 
    this.i = arrayOfFloat;
    b();
    a();
  }
  
  public final void setColors(ReadableArray paramReadableArray) {
    int[] arrayOfInt = new int[paramReadableArray.size()];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfInt[i] = paramReadableArray.getInt(i); 
    this.g = arrayOfInt;
    a();
  }
  
  public final void setEndPosition(ReadableArray paramReadableArray) {
    this.f = new float[] { (float)paramReadableArray.getDouble(0), (float)paramReadableArray.getDouble(1) };
    a();
  }
  
  public final void setLocations(ReadableArray paramReadableArray) {
    float[] arrayOfFloat = new float[paramReadableArray.size()];
    for (int i = 0; i < arrayOfFloat.length; i++)
      arrayOfFloat[i] = (float)paramReadableArray.getDouble(i); 
    this.d = arrayOfFloat;
    a();
  }
  
  public final void setStartPosition(ReadableArray paramReadableArray) {
    this.e = new float[] { (float)paramReadableArray.getDouble(0), (float)paramReadableArray.getDouble(1) };
    a();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\lineargradient\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */