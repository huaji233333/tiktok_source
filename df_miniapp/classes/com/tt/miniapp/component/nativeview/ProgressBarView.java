package com.tt.miniapp.component.nativeview;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.ProgressBar;

public class ProgressBarView extends ProgressBar implements IProgressBar {
  public ProgressBarView(Context paramContext) {
    super(paramContext, null, 16842872);
  }
  
  public void hide() {
    setVisibility(8);
  }
  
  public boolean isShown() {
    return (super.isShown() && getVisibility() == 0);
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
  }
  
  public void setInternalProgress(int paramInt) {
    setProgress(paramInt);
  }
  
  public void show() {
    setVisibility(0);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\ProgressBarView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */