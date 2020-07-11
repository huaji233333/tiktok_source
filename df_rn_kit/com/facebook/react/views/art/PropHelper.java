package com.facebook.react.views.art;

import com.facebook.react.bridge.ReadableArray;

class PropHelper {
  static int toFloatArray(ReadableArray paramReadableArray, float[] paramArrayOffloat) {
    int i;
    if (paramReadableArray.size() > paramArrayOffloat.length) {
      i = paramArrayOffloat.length;
    } else {
      i = paramReadableArray.size();
    } 
    for (int j = 0; j < i; j++)
      paramArrayOffloat[j] = (float)paramReadableArray.getDouble(j); 
    return paramReadableArray.size();
  }
  
  static float[] toFloatArray(ReadableArray paramReadableArray) {
    if (paramReadableArray != null) {
      float[] arrayOfFloat = new float[paramReadableArray.size()];
      toFloatArray(paramReadableArray, arrayOfFloat);
      return arrayOfFloat;
    } 
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\art\PropHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */