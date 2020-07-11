package com.graphic.RNCanvas;

public class CanvasDeepCopy {
  public static float deepCopyFloat(float paramFloat) {
    return paramFloat;
  }
  
  public static float[] deepCopyFloatList(float[] paramArrayOffloat) {
    int j = paramArrayOffloat.length;
    int i = 0;
    if (j == 0)
      return new float[0]; 
    float[] arrayOfFloat = new float[paramArrayOffloat.length];
    while (i < paramArrayOffloat.length) {
      arrayOfFloat[i] = deepCopyFloat(paramArrayOffloat[i]);
      i++;
    } 
    return arrayOfFloat;
  }
  
  public static int deepCopyInt(int paramInt) {
    return paramInt;
  }
  
  public static int[] deepCopyIntList(int[] paramArrayOfint) {
    int j = paramArrayOfint.length;
    int i = 0;
    if (j == 0)
      return new int[0]; 
    int[] arrayOfInt = new int[paramArrayOfint.length];
    while (i < paramArrayOfint.length) {
      arrayOfInt[i] = deepCopyInt(paramArrayOfint[i]);
      i++;
    } 
    return arrayOfInt;
  }
  
  public static String deepCopyString(String paramString) {
    return new String(paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasDeepCopy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */