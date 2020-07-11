package com.facebook.react.uimanager;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;

public class TransformHelper {
  private static ThreadLocal<double[]> sHelperMatrix = new ThreadLocal<double[]>() {
      protected final double[] initialValue() {
        return new double[16];
      }
    };
  
  private static double convertToRadians(ReadableMap paramReadableMap, String paramString) {
    String str;
    double d;
    ReadableType readableType1 = paramReadableMap.getType(paramString);
    ReadableType readableType2 = ReadableType.String;
    boolean bool2 = true;
    boolean bool1 = true;
    if (readableType1 == readableType2) {
      paramString = paramReadableMap.getString(paramString);
      if (paramString.endsWith("rad")) {
        str = paramString.substring(0, paramString.length() - 3);
      } else {
        str = paramString;
        if (paramString.endsWith("deg")) {
          str = paramString.substring(0, paramString.length() - 3);
          bool1 = false;
        } 
      } 
      d = Float.parseFloat(str);
    } else {
      d = str.getDouble(paramString);
      bool1 = bool2;
    } 
    return bool1 ? d : MatrixMathHelper.degreesToRadians(d);
  }
  
  public static void processTransform(ReadableArray paramReadableArray, double[] paramArrayOfdouble) {
    double[] arrayOfDouble = sHelperMatrix.get();
    MatrixMathHelper.resetIdentityMatrix(paramArrayOfdouble);
    int j = paramReadableArray.size();
    int i;
    for (i = 0; i < j; i++) {
      ReadableArray readableArray;
      ReadableMap readableMap = paramReadableArray.getMap(i);
      String str = readableMap.keySetIterator().nextKey();
      MatrixMathHelper.resetIdentityMatrix(arrayOfDouble);
      if ("matrix".equals(str)) {
        readableArray = readableMap.getArray(str);
        int k;
        for (k = 0; k < 16; k++)
          arrayOfDouble[k] = readableArray.getDouble(k); 
      } else if ("perspective".equals(readableArray)) {
        MatrixMathHelper.applyPerspective(arrayOfDouble, readableMap.getDouble((String)readableArray));
      } else if ("rotateX".equals(readableArray)) {
        MatrixMathHelper.applyRotateX(arrayOfDouble, convertToRadians(readableMap, (String)readableArray));
      } else if ("rotateY".equals(readableArray)) {
        MatrixMathHelper.applyRotateY(arrayOfDouble, convertToRadians(readableMap, (String)readableArray));
      } else if ("rotate".equals(readableArray) || "rotateZ".equals(readableArray)) {
        MatrixMathHelper.applyRotateZ(arrayOfDouble, convertToRadians(readableMap, (String)readableArray));
      } else if ("scale".equals(readableArray)) {
        double d = readableMap.getDouble((String)readableArray);
        MatrixMathHelper.applyScaleX(arrayOfDouble, d);
        MatrixMathHelper.applyScaleY(arrayOfDouble, d);
      } else if ("scaleX".equals(readableArray)) {
        MatrixMathHelper.applyScaleX(arrayOfDouble, readableMap.getDouble((String)readableArray));
      } else if ("scaleY".equals(readableArray)) {
        MatrixMathHelper.applyScaleY(arrayOfDouble, readableMap.getDouble((String)readableArray));
      } else {
        boolean bool = "translate".equals(readableArray);
        double d = 0.0D;
        if (bool) {
          readableArray = readableMap.getArray((String)readableArray);
          double d1 = readableArray.getDouble(0);
          double d2 = readableArray.getDouble(1);
          if (readableArray.size() > 2)
            d = readableArray.getDouble(2); 
          MatrixMathHelper.applyTranslate3D(arrayOfDouble, d1, d2, d);
        } else if ("translateX".equals(readableArray)) {
          MatrixMathHelper.applyTranslate2D(arrayOfDouble, readableMap.getDouble((String)readableArray), 0.0D);
        } else if ("translateY".equals(readableArray)) {
          MatrixMathHelper.applyTranslate2D(arrayOfDouble, 0.0D, readableMap.getDouble((String)readableArray));
        } else if ("skewX".equals(readableArray)) {
          MatrixMathHelper.applySkewX(arrayOfDouble, convertToRadians(readableMap, (String)readableArray));
        } else if ("skewY".equals(readableArray)) {
          MatrixMathHelper.applySkewY(arrayOfDouble, convertToRadians(readableMap, (String)readableArray));
        } else {
          StringBuilder stringBuilder = new StringBuilder("Unsupported transform type: ");
          stringBuilder.append((String)readableArray);
          throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
        } 
      } 
      MatrixMathHelper.multiplyInto(paramArrayOfdouble, paramArrayOfdouble, arrayOfDouble);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\TransformHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */