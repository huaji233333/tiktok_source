package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewParent;

abstract class DrawCommandManager {
  protected static void ensureViewHasNoParent(View paramView) {
    ViewParent viewParent = paramView.getParent();
    if (viewParent == null)
      return; 
    StringBuilder stringBuilder = new StringBuilder("Cannot add view ");
    stringBuilder.append(paramView);
    stringBuilder.append(" to DrawCommandManager while it has a parent ");
    stringBuilder.append(viewParent);
    throw new RuntimeException(stringBuilder.toString());
  }
  
  static DrawCommandManager getVerticalClippingInstance(FlatViewGroup paramFlatViewGroup, DrawCommand[] paramArrayOfDrawCommand) {
    return new VerticalDrawCommandManager(paramFlatViewGroup, paramArrayOfDrawCommand);
  }
  
  abstract NodeRegion anyNodeRegionWithinBounds(float paramFloat1, float paramFloat2);
  
  abstract void debugDraw(Canvas paramCanvas);
  
  abstract void draw(Canvas paramCanvas);
  
  abstract void getClippingRect(Rect paramRect);
  
  abstract SparseArray<View> getDetachedViews();
  
  abstract void mountDrawCommands(DrawCommand[] paramArrayOfDrawCommand, SparseIntArray paramSparseIntArray, float[] paramArrayOffloat1, float[] paramArrayOffloat2, boolean paramBoolean);
  
  abstract void mountNodeRegions(NodeRegion[] paramArrayOfNodeRegion, float[] paramArrayOffloat1, float[] paramArrayOffloat2);
  
  abstract void mountViews(ViewResolver paramViewResolver, int[] paramArrayOfint1, int[] paramArrayOfint2);
  
  abstract void onClippedViewDropped(View paramView);
  
  abstract boolean updateClippingRect();
  
  abstract NodeRegion virtualNodeRegionWithinBounds(float paramFloat1, float paramFloat2);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\DrawCommandManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */