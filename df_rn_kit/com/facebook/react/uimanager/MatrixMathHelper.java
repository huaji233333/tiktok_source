package com.facebook.react.uimanager;

import com.facebook.i.a.a;
import java.lang.reflect.Array;

public class MatrixMathHelper {
  public static void applyPerspective(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble[11] = -1.0D / paramDouble;
  }
  
  public static void applyRotateX(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble[5] = Math.cos(paramDouble);
    paramArrayOfdouble[6] = Math.sin(paramDouble);
    paramArrayOfdouble[9] = -Math.sin(paramDouble);
    paramArrayOfdouble[10] = Math.cos(paramDouble);
  }
  
  public static void applyRotateY(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble[0] = Math.cos(paramDouble);
    paramArrayOfdouble[2] = -Math.sin(paramDouble);
    paramArrayOfdouble[8] = Math.sin(paramDouble);
    paramArrayOfdouble[10] = Math.cos(paramDouble);
  }
  
  public static void applyRotateZ(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble[0] = Math.cos(paramDouble);
    paramArrayOfdouble[1] = Math.sin(paramDouble);
    paramArrayOfdouble[4] = -Math.sin(paramDouble);
    paramArrayOfdouble[5] = Math.cos(paramDouble);
  }
  
  public static void applyScaleX(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble[0] = paramDouble;
  }
  
  public static void applyScaleY(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble[5] = paramDouble;
  }
  
  public static void applyScaleZ(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble[10] = paramDouble;
  }
  
  public static void applySkewX(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble[4] = Math.tan(paramDouble);
  }
  
  public static void applySkewY(double[] paramArrayOfdouble, double paramDouble) {
    paramArrayOfdouble[1] = Math.tan(paramDouble);
  }
  
  public static void applyTranslate2D(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2) {
    paramArrayOfdouble[12] = paramDouble1;
    paramArrayOfdouble[13] = paramDouble2;
  }
  
  public static void applyTranslate3D(double[] paramArrayOfdouble, double paramDouble1, double paramDouble2, double paramDouble3) {
    paramArrayOfdouble[12] = paramDouble1;
    paramArrayOfdouble[13] = paramDouble2;
    paramArrayOfdouble[14] = paramDouble3;
  }
  
  public static double[] createIdentityMatrix() {
    double[] arrayOfDouble = new double[16];
    resetIdentityMatrix(arrayOfDouble);
    return arrayOfDouble;
  }
  
  public static void decomposeMatrix(double[] paramArrayOfdouble, MatrixDecompositionContext paramMatrixDecompositionContext) {
    boolean bool;
    if (paramArrayOfdouble.length == 16) {
      bool = true;
    } else {
      bool = false;
    } 
    a.a(bool);
    double[] arrayOfDouble5 = paramMatrixDecompositionContext.perspective;
    double[] arrayOfDouble3 = paramMatrixDecompositionContext.scale;
    double[] arrayOfDouble4 = paramMatrixDecompositionContext.skew;
    double[] arrayOfDouble6 = paramMatrixDecompositionContext.translation;
    double[] arrayOfDouble2 = paramMatrixDecompositionContext.rotationDegrees;
    if (isZero(paramArrayOfdouble[15]))
      return; 
    double[][] arrayOfDouble7 = (double[][])Array.newInstance(double.class, new int[] { 4, 4 });
    double[] arrayOfDouble8 = new double[16];
    int i;
    for (i = 0; i < 4; i++) {
      int j;
      for (j = 0; j < 4; j++) {
        int k = i * 4 + j;
        double d = paramArrayOfdouble[k] / paramArrayOfdouble[15];
        arrayOfDouble7[i][j] = d;
        if (j == 3)
          d = 0.0D; 
        arrayOfDouble8[k] = d;
      } 
    } 
    arrayOfDouble8[15] = 1.0D;
    if (isZero(determinant(arrayOfDouble8)))
      return; 
    if (!isZero(arrayOfDouble7[0][3]) || !isZero(arrayOfDouble7[1][3]) || !isZero(arrayOfDouble7[2][3])) {
      double d1 = arrayOfDouble7[0][3];
      double d2 = arrayOfDouble7[1][3];
      double d3 = arrayOfDouble7[2][3];
      double d4 = arrayOfDouble7[3][3];
      paramArrayOfdouble = transpose(inverse(arrayOfDouble8));
      multiplyVectorByMatrix(new double[] { d1, d2, d3, d4 }, paramArrayOfdouble, arrayOfDouble5);
    } else {
      arrayOfDouble5[2] = 0.0D;
      arrayOfDouble5[1] = 0.0D;
      arrayOfDouble5[0] = 0.0D;
      arrayOfDouble5[3] = 1.0D;
    } 
    for (i = 0; i < 3; i++)
      arrayOfDouble6[i] = arrayOfDouble7[3][i]; 
    double[][] arrayOfDouble1 = (double[][])Array.newInstance(double.class, new int[] { 3, 3 });
    for (i = 0; i < 3; i++) {
      arrayOfDouble1[i][0] = arrayOfDouble7[i][0];
      arrayOfDouble1[i][1] = arrayOfDouble7[i][1];
      arrayOfDouble1[i][2] = arrayOfDouble7[i][2];
    } 
    arrayOfDouble3[0] = v3Length(arrayOfDouble1[0]);
    arrayOfDouble1[0] = v3Normalize(arrayOfDouble1[0], arrayOfDouble3[0]);
    arrayOfDouble4[0] = v3Dot(arrayOfDouble1[0], arrayOfDouble1[1]);
    arrayOfDouble1[1] = v3Combine(arrayOfDouble1[1], arrayOfDouble1[0], 1.0D, -arrayOfDouble4[0]);
    arrayOfDouble4[0] = v3Dot(arrayOfDouble1[0], arrayOfDouble1[1]);
    arrayOfDouble1[1] = v3Combine(arrayOfDouble1[1], arrayOfDouble1[0], 1.0D, -arrayOfDouble4[0]);
    arrayOfDouble3[1] = v3Length(arrayOfDouble1[1]);
    arrayOfDouble1[1] = v3Normalize(arrayOfDouble1[1], arrayOfDouble3[1]);
    arrayOfDouble4[0] = arrayOfDouble4[0] / arrayOfDouble3[1];
    arrayOfDouble4[1] = v3Dot(arrayOfDouble1[0], arrayOfDouble1[2]);
    arrayOfDouble1[2] = v3Combine(arrayOfDouble1[2], arrayOfDouble1[0], 1.0D, -arrayOfDouble4[1]);
    arrayOfDouble4[2] = v3Dot(arrayOfDouble1[1], arrayOfDouble1[2]);
    arrayOfDouble1[2] = v3Combine(arrayOfDouble1[2], arrayOfDouble1[1], 1.0D, -arrayOfDouble4[2]);
    arrayOfDouble3[2] = v3Length(arrayOfDouble1[2]);
    arrayOfDouble1[2] = v3Normalize(arrayOfDouble1[2], arrayOfDouble3[2]);
    arrayOfDouble4[1] = arrayOfDouble4[1] / arrayOfDouble3[2];
    arrayOfDouble4[2] = arrayOfDouble4[2] / arrayOfDouble3[2];
    arrayOfDouble4 = v3Cross(arrayOfDouble1[1], arrayOfDouble1[2]);
    if (v3Dot(arrayOfDouble1[0], arrayOfDouble4) < 0.0D)
      for (i = 0; i < 3; i++) {
        arrayOfDouble3[i] = arrayOfDouble3[i] * -1.0D;
        arrayOfDouble4 = arrayOfDouble1[i];
        arrayOfDouble4[0] = arrayOfDouble4[0] * -1.0D;
        arrayOfDouble4 = arrayOfDouble1[i];
        arrayOfDouble4[1] = arrayOfDouble4[1] * -1.0D;
        arrayOfDouble4 = arrayOfDouble1[i];
        arrayOfDouble4[2] = arrayOfDouble4[2] * -1.0D;
      }  
    arrayOfDouble2[0] = roundTo3Places(-Math.atan2(arrayOfDouble1[2][1], arrayOfDouble1[2][2]) * 57.29577951308232D);
    arrayOfDouble2[1] = roundTo3Places(-Math.atan2(-arrayOfDouble1[2][0], Math.sqrt(arrayOfDouble1[2][1] * arrayOfDouble1[2][1] + arrayOfDouble1[2][2] * arrayOfDouble1[2][2])) * 57.29577951308232D);
    arrayOfDouble2[2] = roundTo3Places(-Math.atan2(arrayOfDouble1[1][0], arrayOfDouble1[0][0]) * 57.29577951308232D);
  }
  
  public static double degreesToRadians(double paramDouble) {
    return paramDouble * Math.PI / 180.0D;
  }
  
  public static double determinant(double[] paramArrayOfdouble) {
    double d15 = paramArrayOfdouble[0];
    double d18 = paramArrayOfdouble[1];
    double d22 = paramArrayOfdouble[2];
    double d17 = paramArrayOfdouble[3];
    double d19 = paramArrayOfdouble[4];
    double d16 = paramArrayOfdouble[5];
    double d21 = paramArrayOfdouble[6];
    double d20 = paramArrayOfdouble[7];
    double d1 = paramArrayOfdouble[8];
    double d2 = paramArrayOfdouble[9];
    double d3 = paramArrayOfdouble[10];
    double d4 = paramArrayOfdouble[11];
    double d5 = paramArrayOfdouble[12];
    double d6 = paramArrayOfdouble[13];
    double d7 = paramArrayOfdouble[14];
    double d8 = paramArrayOfdouble[15];
    double d9 = d17 * d21;
    double d10 = d22 * d20;
    double d11 = d17 * d16;
    double d12 = d18 * d20;
    double d13 = d22 * d16;
    double d14 = d18 * d21;
    d17 *= d19;
    d20 *= d15;
    d22 *= d19;
    d21 *= d15;
    d18 *= d19;
    d15 *= d16;
    return d9 * d2 * d5 - d10 * d2 * d5 - d11 * d3 * d5 + d12 * d3 * d5 + d13 * d4 * d5 - d14 * d4 * d5 - d9 * d1 * d6 + d10 * d1 * d6 + d17 * d3 * d6 - d20 * d3 * d6 - d22 * d4 * d6 + d21 * d4 * d6 + d11 * d1 * d7 - d12 * d1 * d7 - d17 * d2 * d7 + d20 * d2 * d7 + d18 * d4 * d7 - d4 * d15 * d7 - d13 * d1 * d8 + d14 * d1 * d8 + d22 * d2 * d8 - d21 * d2 * d8 - d18 * d3 * d8 + d15 * d3 * d8;
  }
  
  public static double[] inverse(double[] paramArrayOfdouble) {
    double d1 = determinant(paramArrayOfdouble);
    if (isZero(d1))
      return paramArrayOfdouble; 
    double d24 = paramArrayOfdouble[0];
    double d26 = paramArrayOfdouble[1];
    double d42 = paramArrayOfdouble[2];
    double d36 = paramArrayOfdouble[3];
    double d27 = paramArrayOfdouble[4];
    double d25 = paramArrayOfdouble[5];
    double d43 = paramArrayOfdouble[6];
    double d41 = paramArrayOfdouble[7];
    double d2 = paramArrayOfdouble[8];
    double d3 = paramArrayOfdouble[9];
    double d4 = paramArrayOfdouble[10];
    double d5 = paramArrayOfdouble[11];
    double d6 = paramArrayOfdouble[12];
    double d7 = paramArrayOfdouble[13];
    double d8 = paramArrayOfdouble[14];
    double d9 = paramArrayOfdouble[15];
    double d28 = d43 * d5;
    double d29 = d41 * d4;
    double d37 = d41 * d3;
    double d38 = d25 * d5;
    double d10 = d43 * d3;
    double d11 = d25 * d4;
    double d12 = (d28 * d7 - d29 * d7 + d37 * d8 - d38 * d8 - d10 * d9 + d11 * d9) / d1;
    double d33 = d36 * d4;
    double d44 = d42 * d5;
    double d31 = d36 * d3;
    double d32 = d26 * d5;
    double d13 = d42 * d3;
    double d14 = d26 * d4;
    double d15 = (d33 * d7 - d44 * d7 - d31 * d8 + d32 * d8 + d13 * d9 - d14 * d9) / d1;
    double d45 = d42 * d41;
    double d46 = d36 * d43;
    double d16 = d36 * d25;
    double d17 = d26 * d41;
    double d18 = d42 * d25;
    double d19 = d26 * d43;
    double d20 = (d45 * d7 - d46 * d7 + d16 * d8 - d17 * d8 - d18 * d9 + d19 * d9) / d1;
    double d21 = (d46 * d3 - d45 * d3 - d16 * d4 + d17 * d4 + d18 * d5 - d19 * d5) / d1;
    double d39 = d41 * d2;
    double d40 = d27 * d5;
    double d22 = d43 * d2;
    double d23 = d27 * d4;
    d28 = (d29 * d6 - d28 * d6 - d39 * d8 + d40 * d8 + d22 * d9 - d23 * d9) / d1;
    double d34 = d36 * d2;
    double d35 = d24 * d5;
    d29 = d42 * d2;
    double d30 = d24 * d4;
    d33 = (d44 * d6 - d33 * d6 + d34 * d8 - d35 * d8 - d29 * d9 + d30 * d9) / d1;
    d36 *= d27;
    d41 *= d24;
    d42 *= d27;
    d43 *= d24;
    d44 = (d46 * d6 - d45 * d6 - d36 * d8 + d41 * d8 + d42 * d9 - d43 * d9) / d1;
    d45 = (d45 * d2 - d46 * d2 + d36 * d4 - d41 * d4 - d42 * d5 + d43 * d5) / d1;
    d46 = d25 * d2;
    double d47 = d27 * d3;
    d37 = (d38 * d6 - d37 * d6 + d39 * d7 - d40 * d7 - d46 * d9 + d47 * d9) / d1;
    d38 = d26 * d2;
    d39 = d24 * d3;
    d31 = (d31 * d6 - d32 * d6 - d34 * d7 + d35 * d7 + d38 * d9 - d39 * d9) / d1;
    d26 *= d27;
    d24 *= d25;
    return new double[] { 
        d12, d15, d20, d21, d28, d33, d44, d45, d37, d31, 
        (d17 * d6 - d16 * d6 + d36 * d7 - d41 * d7 - d26 * d9 + d9 * d24) / d1, (d16 * d2 - d17 * d2 - d36 * d3 + d41 * d3 + d26 * d5 - d5 * d24) / d1, (d10 * d6 - d11 * d6 - d22 * d7 + d23 * d7 + d46 * d8 - d47 * d8) / d1, (d14 * d6 - d13 * d6 + d29 * d7 - d30 * d7 - d38 * d8 + d39 * d8) / d1, (d18 * d6 - d6 * d19 - d42 * d7 + d7 * d43 + d26 * d8 - d8 * d24) / d1, (d19 * d2 - d18 * d2 + d42 * d3 - d43 * d3 - d26 * d4 + d24 * d4) / d1 };
  }
  
  private static boolean isZero(double paramDouble) {
    return Double.isNaN(paramDouble) ? false : ((Math.abs(paramDouble) < 1.0E-5D));
  }
  
  public static void multiplyInto(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3) {
    double d1 = paramArrayOfdouble2[0];
    double d2 = paramArrayOfdouble2[1];
    double d3 = paramArrayOfdouble2[2];
    double d4 = paramArrayOfdouble2[3];
    double d5 = paramArrayOfdouble2[4];
    double d6 = paramArrayOfdouble2[5];
    double d7 = paramArrayOfdouble2[6];
    double d8 = paramArrayOfdouble2[7];
    double d9 = paramArrayOfdouble2[8];
    double d10 = paramArrayOfdouble2[9];
    double d11 = paramArrayOfdouble2[10];
    double d12 = paramArrayOfdouble2[11];
    double d13 = paramArrayOfdouble2[12];
    double d14 = paramArrayOfdouble2[13];
    double d15 = paramArrayOfdouble2[14];
    double d16 = paramArrayOfdouble2[15];
    double d17 = paramArrayOfdouble3[0];
    double d18 = paramArrayOfdouble3[1];
    double d19 = paramArrayOfdouble3[2];
    double d20 = paramArrayOfdouble3[3];
    paramArrayOfdouble1[0] = d17 * d1 + d18 * d5 + d19 * d9 + d20 * d13;
    paramArrayOfdouble1[1] = d17 * d2 + d18 * d6 + d19 * d10 + d20 * d14;
    paramArrayOfdouble1[2] = d17 * d3 + d18 * d7 + d19 * d11 + d20 * d15;
    paramArrayOfdouble1[3] = d17 * d4 + d18 * d8 + d19 * d12 + d20 * d16;
    d17 = paramArrayOfdouble3[4];
    d18 = paramArrayOfdouble3[5];
    d19 = paramArrayOfdouble3[6];
    d20 = paramArrayOfdouble3[7];
    paramArrayOfdouble1[4] = d17 * d1 + d18 * d5 + d19 * d9 + d20 * d13;
    paramArrayOfdouble1[5] = d17 * d2 + d18 * d6 + d19 * d10 + d20 * d14;
    paramArrayOfdouble1[6] = d17 * d3 + d18 * d7 + d19 * d11 + d20 * d15;
    paramArrayOfdouble1[7] = d17 * d4 + d18 * d8 + d19 * d12 + d20 * d16;
    d17 = paramArrayOfdouble3[8];
    d18 = paramArrayOfdouble3[9];
    d19 = paramArrayOfdouble3[10];
    d20 = paramArrayOfdouble3[11];
    paramArrayOfdouble1[8] = d17 * d1 + d18 * d5 + d19 * d9 + d20 * d13;
    paramArrayOfdouble1[9] = d17 * d2 + d18 * d6 + d19 * d10 + d20 * d14;
    paramArrayOfdouble1[10] = d17 * d3 + d18 * d7 + d19 * d11 + d20 * d15;
    paramArrayOfdouble1[11] = d17 * d4 + d18 * d8 + d19 * d12 + d20 * d16;
    d17 = paramArrayOfdouble3[12];
    d18 = paramArrayOfdouble3[13];
    d19 = paramArrayOfdouble3[14];
    d20 = paramArrayOfdouble3[15];
    paramArrayOfdouble1[12] = d1 * d17 + d5 * d18 + d9 * d19 + d13 * d20;
    paramArrayOfdouble1[13] = d2 * d17 + d6 * d18 + d10 * d19 + d14 * d20;
    paramArrayOfdouble1[14] = d3 * d17 + d7 * d18 + d11 * d19 + d15 * d20;
    paramArrayOfdouble1[15] = d17 * d4 + d18 * d8 + d19 * d12 + d20 * d16;
  }
  
  public static void multiplyVectorByMatrix(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3) {
    double d1 = paramArrayOfdouble1[0];
    double d2 = paramArrayOfdouble1[1];
    double d3 = paramArrayOfdouble1[2];
    double d4 = paramArrayOfdouble1[3];
    paramArrayOfdouble3[0] = paramArrayOfdouble2[0] * d1 + paramArrayOfdouble2[4] * d2 + paramArrayOfdouble2[8] * d3 + paramArrayOfdouble2[12] * d4;
    paramArrayOfdouble3[1] = paramArrayOfdouble2[1] * d1 + paramArrayOfdouble2[5] * d2 + paramArrayOfdouble2[9] * d3 + paramArrayOfdouble2[13] * d4;
    paramArrayOfdouble3[2] = paramArrayOfdouble2[2] * d1 + paramArrayOfdouble2[6] * d2 + paramArrayOfdouble2[10] * d3 + paramArrayOfdouble2[14] * d4;
    paramArrayOfdouble3[3] = d1 * paramArrayOfdouble2[3] + d2 * paramArrayOfdouble2[7] + d3 * paramArrayOfdouble2[11] + d4 * paramArrayOfdouble2[15];
  }
  
  public static void resetIdentityMatrix(double[] paramArrayOfdouble) {
    paramArrayOfdouble[14] = 0.0D;
    paramArrayOfdouble[13] = 0.0D;
    paramArrayOfdouble[12] = 0.0D;
    paramArrayOfdouble[11] = 0.0D;
    paramArrayOfdouble[9] = 0.0D;
    paramArrayOfdouble[8] = 0.0D;
    paramArrayOfdouble[7] = 0.0D;
    paramArrayOfdouble[6] = 0.0D;
    paramArrayOfdouble[4] = 0.0D;
    paramArrayOfdouble[3] = 0.0D;
    paramArrayOfdouble[2] = 0.0D;
    paramArrayOfdouble[1] = 0.0D;
    paramArrayOfdouble[15] = 1.0D;
    paramArrayOfdouble[10] = 1.0D;
    paramArrayOfdouble[5] = 1.0D;
    paramArrayOfdouble[0] = 1.0D;
  }
  
  public static double roundTo3Places(double paramDouble) {
    paramDouble = Math.round(paramDouble * 1000.0D);
    Double.isNaN(paramDouble);
    return paramDouble * 0.001D;
  }
  
  public static double[] transpose(double[] paramArrayOfdouble) {
    return new double[] { 
        paramArrayOfdouble[0], paramArrayOfdouble[4], paramArrayOfdouble[8], paramArrayOfdouble[12], paramArrayOfdouble[1], paramArrayOfdouble[5], paramArrayOfdouble[9], paramArrayOfdouble[13], paramArrayOfdouble[2], paramArrayOfdouble[6], 
        paramArrayOfdouble[10], paramArrayOfdouble[14], paramArrayOfdouble[3], paramArrayOfdouble[7], paramArrayOfdouble[11], paramArrayOfdouble[15] };
  }
  
  public static double[] v3Combine(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double paramDouble1, double paramDouble2) {
    return new double[] { paramArrayOfdouble1[0] * paramDouble1 + paramArrayOfdouble2[0] * paramDouble2, paramArrayOfdouble1[1] * paramDouble1 + paramArrayOfdouble2[1] * paramDouble2, paramDouble1 * paramArrayOfdouble1[2] + paramDouble2 * paramArrayOfdouble2[2] };
  }
  
  public static double[] v3Cross(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
    return new double[] { paramArrayOfdouble1[1] * paramArrayOfdouble2[2] - paramArrayOfdouble1[2] * paramArrayOfdouble2[1], paramArrayOfdouble1[2] * paramArrayOfdouble2[0] - paramArrayOfdouble1[0] * paramArrayOfdouble2[2], paramArrayOfdouble1[0] * paramArrayOfdouble2[1] - paramArrayOfdouble1[1] * paramArrayOfdouble2[0] };
  }
  
  public static double v3Dot(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
    return paramArrayOfdouble1[0] * paramArrayOfdouble2[0] + paramArrayOfdouble1[1] * paramArrayOfdouble2[1] + paramArrayOfdouble1[2] * paramArrayOfdouble2[2];
  }
  
  public static double v3Length(double[] paramArrayOfdouble) {
    return Math.sqrt(paramArrayOfdouble[0] * paramArrayOfdouble[0] + paramArrayOfdouble[1] * paramArrayOfdouble[1] + paramArrayOfdouble[2] * paramArrayOfdouble[2]);
  }
  
  public static double[] v3Normalize(double[] paramArrayOfdouble, double paramDouble) {
    double d = paramDouble;
    if (isZero(paramDouble))
      d = v3Length(paramArrayOfdouble); 
    paramDouble = 1.0D / d;
    return new double[] { paramArrayOfdouble[0] * paramDouble, paramArrayOfdouble[1] * paramDouble, paramArrayOfdouble[2] * paramDouble };
  }
  
  public static class MatrixDecompositionContext {
    double[] perspective = new double[4];
    
    double[] rotationDegrees = new double[3];
    
    double[] scale = new double[3];
    
    double[] skew = new double[3];
    
    double[] translation = new double[3];
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\MatrixMathHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */