package com.facebook.react.flat;

import android.util.SparseIntArray;
import java.util.Arrays;

final class VerticalDrawCommandManager extends ClippingDrawCommandManager {
  VerticalDrawCommandManager(FlatViewGroup paramFlatViewGroup, DrawCommand[] paramArrayOfDrawCommand) {
    super(paramFlatViewGroup, paramArrayOfDrawCommand);
  }
  
  public static void fillMaxMinArrays(DrawCommand[] paramArrayOfDrawCommand, float[] paramArrayOffloat1, float[] paramArrayOffloat2, SparseIntArray paramSparseIntArray) {
    float f = 0.0F;
    int i;
    for (i = 0; i < paramArrayOfDrawCommand.length; i++) {
      if (paramArrayOfDrawCommand[i] instanceof DrawView) {
        DrawView drawView = (DrawView)paramArrayOfDrawCommand[i];
        paramSparseIntArray.append(drawView.reactTag, i);
        f = Math.max(f, drawView.mLogicalBottom);
      } else {
        f = Math.max(f, paramArrayOfDrawCommand[i].getBottom());
      } 
      paramArrayOffloat1[i] = f;
    } 
    for (i = paramArrayOfDrawCommand.length - 1; i >= 0; i--) {
      if (paramArrayOfDrawCommand[i] instanceof DrawView) {
        f = Math.min(f, ((DrawView)paramArrayOfDrawCommand[i]).mLogicalTop);
      } else {
        f = Math.min(f, paramArrayOfDrawCommand[i].getTop());
      } 
      paramArrayOffloat2[i] = f;
    } 
  }
  
  public static void fillMaxMinArrays(NodeRegion[] paramArrayOfNodeRegion, float[] paramArrayOffloat1, float[] paramArrayOffloat2) {
    float f = 0.0F;
    int i;
    for (i = 0; i < paramArrayOfNodeRegion.length; i++) {
      f = Math.max(f, paramArrayOfNodeRegion[i].getTouchableBottom());
      paramArrayOffloat1[i] = f;
    } 
    for (i = paramArrayOfNodeRegion.length - 1; i >= 0; i--) {
      f = Math.min(f, paramArrayOfNodeRegion[i].getTouchableTop());
      paramArrayOffloat2[i] = f;
    } 
  }
  
  final int commandStartIndex() {
    int j = Arrays.binarySearch(this.mCommandMaxBottom, this.mClippingRect.top);
    int i = j;
    if (j < 0)
      i = j ^ 0xFFFFFFFF; 
    return i;
  }
  
  final int commandStopIndex(int paramInt) {
    float[] arrayOfFloat = this.mCommandMinTop;
    int i = Arrays.binarySearch(arrayOfFloat, paramInt, arrayOfFloat.length, this.mClippingRect.bottom);
    paramInt = i;
    if (i < 0)
      paramInt = i ^ 0xFFFFFFFF; 
    return paramInt;
  }
  
  final boolean regionAboveTouch(int paramInt, float paramFloat1, float paramFloat2) {
    return (this.mRegionMaxBottom[paramInt] < paramFloat2);
  }
  
  final int regionStopIndex(float paramFloat1, float paramFloat2) {
    int j = Arrays.binarySearch(this.mRegionMinTop, paramFloat2 + 1.0E-4F);
    int i = j;
    if (j < 0)
      i = j ^ 0xFFFFFFFF; 
    return i;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\VerticalDrawCommandManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */