package com.facebook.react.uimanager;

import com.facebook.yoga.a;
import java.util.Arrays;

public class Spacing {
  private static final int[] sFlagsMap = new int[] { 1, 2, 4, 8, 16, 32, 64, 128, 256 };
  
  private float mDefaultValue;
  
  private boolean mHasAliasesSet;
  
  private final float[] mSpacing = newFullSpacingArray();
  
  private int mValueFlags = 0;
  
  public Spacing() {
    this(0.0F);
  }
  
  public Spacing(float paramFloat) {
    this.mDefaultValue = paramFloat;
  }
  
  private static float[] newFullSpacingArray() {
    return new float[] { 1.0E21F, 1.0E21F, 1.0E21F, 1.0E21F, 1.0E21F, 1.0E21F, 1.0E21F, 1.0E21F, 1.0E21F };
  }
  
  public float get(int paramInt) {
    float f;
    if (paramInt == 4 || paramInt == 5) {
      f = 1.0E21F;
    } else {
      f = this.mDefaultValue;
    } 
    int i = this.mValueFlags;
    if (i == 0)
      return f; 
    if ((i & sFlagsMap[paramInt]) != 0)
      return this.mSpacing[paramInt]; 
    if (this.mHasAliasesSet) {
      if (paramInt == 1 || paramInt == 3) {
        paramInt = 7;
      } else {
        paramInt = 6;
      } 
      i = this.mValueFlags;
      int[] arrayOfInt = sFlagsMap;
      if ((arrayOfInt[paramInt] & i) != 0)
        return this.mSpacing[paramInt]; 
      if ((i & arrayOfInt[8]) != 0)
        return this.mSpacing[8]; 
    } 
    return f;
  }
  
  public float getRaw(int paramInt) {
    return this.mSpacing[paramInt];
  }
  
  float getWithFallback(int paramInt1, int paramInt2) {
    return ((this.mValueFlags & sFlagsMap[paramInt1]) != 0) ? this.mSpacing[paramInt1] : get(paramInt2);
  }
  
  public void reset() {
    Arrays.fill(this.mSpacing, 1.0E21F);
    this.mHasAliasesSet = false;
    this.mValueFlags = 0;
  }
  
  public boolean set(int paramInt, float paramFloat) {
    boolean bool1 = FloatUtil.floatsEqual(this.mSpacing[paramInt], paramFloat);
    boolean bool = false;
    if (!bool1) {
      this.mSpacing[paramInt] = paramFloat;
      if (a.a(paramFloat)) {
        int i = this.mValueFlags;
        this.mValueFlags = (sFlagsMap[paramInt] ^ 0xFFFFFFFF) & i;
      } else {
        int i = this.mValueFlags;
        this.mValueFlags = sFlagsMap[paramInt] | i;
      } 
      paramInt = this.mValueFlags;
      int[] arrayOfInt = sFlagsMap;
      if ((arrayOfInt[8] & paramInt) != 0 || (arrayOfInt[7] & paramInt) != 0 || (paramInt & arrayOfInt[6]) != 0)
        bool = true; 
      this.mHasAliasesSet = bool;
      return true;
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\Spacing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */