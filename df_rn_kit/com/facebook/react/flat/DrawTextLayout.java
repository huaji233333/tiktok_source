package com.facebook.react.flat;

import android.graphics.Canvas;
import android.text.Layout;
import com.facebook.g.a.b.a;

final class DrawTextLayout extends AbstractDrawCommand {
  private Layout mLayout;
  
  private float mLayoutHeight;
  
  private float mLayoutWidth;
  
  DrawTextLayout(Layout paramLayout) {
    setLayout(paramLayout);
  }
  
  public final Layout getLayout() {
    return this.mLayout;
  }
  
  public final float getLayoutHeight() {
    return this.mLayoutHeight;
  }
  
  public final float getLayoutWidth() {
    return this.mLayoutWidth;
  }
  
  protected final void onDraw(Canvas paramCanvas) {
    float f1 = getLeft();
    float f2 = getTop();
    paramCanvas.translate(f1, f2);
    this.mLayout.draw(paramCanvas);
    paramCanvas.translate(-f1, -f2);
  }
  
  public final void setLayout(Layout paramLayout) {
    this.mLayout = paramLayout;
    this.mLayoutWidth = paramLayout.getWidth();
    this.mLayoutHeight = a.a(paramLayout);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\DrawTextLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */