package com.tt.miniapp.view.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

public class LoadingPoint extends View {
  int color = Color.parseColor("#ff000000");
  
  private Scroller mScroller;
  
  Paint paint = new Paint();
  
  public LoadingPoint(Context paramContext) {
    super(paramContext);
    this.mScroller = new Scroller(paramContext);
  }
  
  public LoadingPoint(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public LoadingPoint(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void computeScroll() {
    super.computeScroll();
    if (this.mScroller.computeScrollOffset()) {
      ((View)getParent()).scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
      invalidate();
    } 
  }
  
  public int getColor() {
    return this.color;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    this.paint.setColor(this.color);
    paramCanvas.drawCircle((getWidth() / 2), (getHeight() / 2), (getWidth() / 2), this.paint);
    super.onDraw(paramCanvas);
  }
  
  public void setColor(int paramInt) {
    this.color = paramInt;
    invalidate();
  }
  
  public void smoothScrollTo(int paramInt1, int paramInt2) {
    int i = getScrollX();
    int j = getScrollY();
    this.mScroller.startScroll(i, j, paramInt1, paramInt2, 1);
    invalidate();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\loading\LoadingPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */