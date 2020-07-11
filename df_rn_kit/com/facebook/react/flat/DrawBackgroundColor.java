package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;

final class DrawBackgroundColor extends AbstractDrawCommand {
  private static final Paint PAINT = new Paint();
  
  private final int mBackgroundColor;
  
  DrawBackgroundColor(int paramInt) {
    this.mBackgroundColor = paramInt;
  }
  
  public final void onDraw(Canvas paramCanvas) {
    PAINT.setColor(this.mBackgroundColor);
    paramCanvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), PAINT);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\DrawBackgroundColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */