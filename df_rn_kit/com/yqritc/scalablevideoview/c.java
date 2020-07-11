package com.yqritc.scalablevideoview;

import android.graphics.Matrix;

public final class c {
  private d a;
  
  private d b;
  
  public c(d paramd1, d paramd2) {
    this.a = paramd1;
    this.b = paramd2;
  }
  
  private Matrix a() {
    return a(this.b.a / this.a.a, this.b.b / this.a.b, a.LEFT_TOP);
  }
  
  private static Matrix a(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    Matrix matrix = new Matrix();
    matrix.setScale(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    return matrix;
  }
  
  private Matrix a(float paramFloat1, float paramFloat2, a parama) {
    switch (null.b[parama.ordinal()]) {
      default:
        throw new IllegalArgumentException("Illegal PivotPoint");
      case 9:
        return a(paramFloat1, paramFloat2, this.a.a, this.a.b);
      case 8:
        return a(paramFloat1, paramFloat2, this.a.a, this.a.b / 2.0F);
      case 7:
        return a(paramFloat1, paramFloat2, this.a.a, 0.0F);
      case 6:
        return a(paramFloat1, paramFloat2, this.a.a / 2.0F, this.a.b);
      case 5:
        return a(paramFloat1, paramFloat2, this.a.a / 2.0F, this.a.b / 2.0F);
      case 4:
        return a(paramFloat1, paramFloat2, this.a.a / 2.0F, 0.0F);
      case 3:
        return a(paramFloat1, paramFloat2, 0.0F, this.a.b);
      case 2:
        return a(paramFloat1, paramFloat2, 0.0F, this.a.b / 2.0F);
      case 1:
        break;
    } 
    return a(paramFloat1, paramFloat2, 0.0F, 0.0F);
  }
  
  private Matrix a(a parama) {
    float f1 = this.a.a / this.b.a;
    float f2 = this.a.b / this.b.b;
    float f3 = Math.min(f1, f2);
    return a(f3 / f1, f3 / f2, parama);
  }
  
  private Matrix b() {
    return a(1.0F, 1.0F, a.LEFT_TOP);
  }
  
  private Matrix b(a parama) {
    return a(this.b.a / this.a.a, this.b.b / this.a.b, parama);
  }
  
  private Matrix c() {
    return a(a.LEFT_TOP);
  }
  
  private Matrix c(a parama) {
    float f1 = this.a.a / this.b.a;
    float f2 = this.a.b / this.b.b;
    float f3 = Math.max(f1, f2);
    return a(f3 / f1, f3 / f2, parama);
  }
  
  private Matrix d() {
    return a(a.CENTER);
  }
  
  private Matrix e() {
    return a(a.RIGHT_BOTTOM);
  }
  
  private Matrix f() {
    return (this.b.b <= this.a.a && this.b.b <= this.a.b) ? b(a.LEFT_TOP) : c();
  }
  
  private Matrix g() {
    return (this.b.b <= this.a.a && this.b.b <= this.a.b) ? b(a.CENTER) : d();
  }
  
  private Matrix h() {
    return (this.b.b <= this.a.a && this.b.b <= this.a.b) ? b(a.RIGHT_BOTTOM) : e();
  }
  
  public final Matrix a(b paramb) {
    switch (null.a[paramb.ordinal()]) {
      default:
        return null;
      case 26:
        return h();
      case 25:
        return g();
      case 24:
        return f();
      case 23:
        return c(a.RIGHT_BOTTOM);
      case 22:
        return c(a.RIGHT_CENTER);
      case 21:
        return c(a.RIGHT_TOP);
      case 20:
        return c(a.CENTER_BOTTOM);
      case 19:
        return c(a.CENTER);
      case 18:
        return c(a.CENTER_TOP);
      case 17:
        return c(a.LEFT_BOTTOM);
      case 16:
        return c(a.LEFT_CENTER);
      case 15:
        return c(a.LEFT_TOP);
      case 14:
        return b(a.RIGHT_BOTTOM);
      case 13:
        return b(a.RIGHT_CENTER);
      case 12:
        return b(a.RIGHT_TOP);
      case 11:
        return b(a.CENTER_BOTTOM);
      case 10:
        return b(a.CENTER);
      case 9:
        return b(a.CENTER_TOP);
      case 8:
        return b(a.LEFT_BOTTOM);
      case 7:
        return b(a.LEFT_CENTER);
      case 6:
        return b(a.LEFT_TOP);
      case 5:
        return e();
      case 4:
        return c();
      case 3:
        return d();
      case 2:
        return b();
      case 1:
        break;
    } 
    return a();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\yqritc\scalablevideoview\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */