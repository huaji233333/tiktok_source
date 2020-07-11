package com.facebook.react.common;

import java.util.Arrays;

public class ArrayUtils {
  public static float[] copyArray(float[] paramArrayOffloat) {
    return (paramArrayOffloat == null) ? null : Arrays.copyOf(paramArrayOffloat, paramArrayOffloat.length);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\ArrayUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */