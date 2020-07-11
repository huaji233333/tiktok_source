package com.tt.miniapp.video.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RotateImageView extends ImageView {
  private boolean isPlaying;
  
  private long mDuration = 1000L;
  
  private long mStartTime = -1L;
  
  private final Runnable mTimerTask = new Runnable() {
      public void run() {
        RotateImageView.this.invalidate();
      }
    };
  
  public RotateImageView(Context paramContext) {
    super(paramContext);
  }
  
  public RotateImageView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public void RotateImageView__onDetachedFromWindow$___twin___() {
    super.onDetachedFromWindow();
  }
  
  protected void onDetachedFromWindow() {
    _lancet.com_ss_android_ugc_aweme_lancet_ImageStopLossLanect_imageViewOnDetachedFromWindow(this);
  }
  
  protected void onDraw(Canvas paramCanvas) {
    if (this.isPlaying && this.mDuration > 0L) {
      if (this.mStartTime <= 0L)
        this.mStartTime = System.currentTimeMillis(); 
      float f1 = (float)(System.currentTimeMillis() - this.mStartTime) / (float)this.mDuration;
      float f2 = getWidth() / 2.0F;
      float f3 = getHeight() / 2.0F;
      paramCanvas.save();
      paramCanvas.rotate(f1 * 360.0F, f2, f3);
      super.onDraw(paramCanvas);
      paramCanvas.restore();
      postDelayed(this.mTimerTask, 20L);
      return;
    } 
    super.onDraw(paramCanvas);
  }
  
  public void setDuration(long paramLong) {
    this.mDuration = paramLong;
    stopAnimation();
    startAnimation();
  }
  
  public void startAnimation() {
    if (this.isPlaying)
      return; 
    this.isPlaying = true;
    this.mStartTime = -1L;
    invalidate();
  }
  
  public void stopAnimation() {
    this.isPlaying = false;
    this.mStartTime = -1L;
    invalidate();
  }
  
  class RotateImageView {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\view\widget\RotateImageView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */