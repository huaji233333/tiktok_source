package com.facebook.react.flat;

import android.graphics.Canvas;

public abstract class DrawCommand {
  static final DrawCommand[] EMPTY_ARRAY = new DrawCommand[0];
  
  abstract void debugDraw(FlatViewGroup paramFlatViewGroup, Canvas paramCanvas);
  
  abstract void draw(FlatViewGroup paramFlatViewGroup, Canvas paramCanvas);
  
  abstract float getBottom();
  
  abstract float getLeft();
  
  abstract float getRight();
  
  abstract float getTop();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\DrawCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */