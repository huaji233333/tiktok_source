package com.graphic.RNCanvas;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import java.util.ArrayList;
import java.util.HashMap;

public class CanvasConvert {
  public static ArrayList<HashMap> convertActions(ReadableArray paramReadableArray) {
    int j = paramReadableArray.size();
    ArrayList<HashMap> arrayList = new ArrayList();
    for (int i = 0; i < j; i++) {
      try {
        ReadableMap readableMap = paramReadableArray.getMap(i);
        arrayList.add(createAction(readableMap.getString("method"), readableMap.getArray("arguments").toArrayList().toArray()));
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
    return arrayList;
  }
  
  public static int[] convertColor(float[] paramArrayOffloat) {
    return (paramArrayOffloat.length != 4) ? new int[] { 255, 0, 0, 0 } : new int[] { (int)(paramArrayOffloat[3] * 255.0F), (int)(paramArrayOffloat[0] * 255.0F), (int)(paramArrayOffloat[1] * 255.0F), (int)(paramArrayOffloat[2] * 255.0F) };
  }
  
  public static int convertColorListToColor(int[] paramArrayOfint) {
    return Color.argb(paramArrayOfint[0], paramArrayOfint[1], paramArrayOfint[2], paramArrayOfint[3]);
  }
  
  public static Paint.Cap convertLineCap(String paramString) {
    Paint.Cap cap = Paint.Cap.BUTT;
    if (paramString.equals("round"))
      return Paint.Cap.ROUND; 
    if (paramString.equals("square"))
      cap = Paint.Cap.SQUARE; 
    return cap;
  }
  
  public static DashPathEffect convertLineDash(float[] paramArrayOffloat, float paramFloat) {
    int i;
    float[] arrayOfFloat;
    float f = paramArrayOffloat.length;
    byte b = 0;
    if (f % 2.0F != 0.0F) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      arrayOfFloat = new float[paramArrayOffloat.length * 2];
      i = b;
    } else {
      arrayOfFloat = new float[paramArrayOffloat.length];
      i = b;
    } 
    while (i < arrayOfFloat.length) {
      arrayOfFloat[i] = paramArrayOffloat[(int)(i % f)];
      i++;
    } 
    if (paramFloat <= 0.0F)
      paramFloat = 0.0F; 
    return new DashPathEffect(arrayOfFloat, paramFloat);
  }
  
  public static Paint.Join convertLineJoin(String paramString) {
    Paint.Join join = Paint.Join.MITER;
    if (paramString.equals("bevel"))
      return Paint.Join.BEVEL; 
    if (paramString.equals("round"))
      join = Paint.Join.ROUND; 
    return join;
  }
  
  public static Paint.Align convertTextAlign(String paramString) {
    Paint.Align align = Paint.Align.LEFT;
    if (paramString.equals("right"))
      return Paint.Align.RIGHT; 
    if (paramString.equals("center"))
      align = Paint.Align.CENTER; 
    return align;
  }
  
  public static int convertTextBaseline(String paramString) {
    return paramString.equals("top") ? 1 : (paramString.equals("middle") ? 2 : 0);
  }
  
  public static HashMap createAction(String paramString, Object[] paramArrayOfObject) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("method", paramString);
    hashMap.put("arguments", paramArrayOfObject);
    return hashMap;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\CanvasConvert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */