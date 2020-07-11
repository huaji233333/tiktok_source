package com.facebook.react.views.progressbar;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;

class ProgressBarContainerView extends FrameLayout {
  private boolean mAnimating = true;
  
  private Integer mColor;
  
  private boolean mIndeterminate = true;
  
  private double mProgress;
  
  private ProgressBar mProgressBar;
  
  public ProgressBarContainerView(Context paramContext) {
    super(paramContext);
  }
  
  private void setColor(ProgressBar paramProgressBar) {
    Drawable drawable;
    if (paramProgressBar.isIndeterminate()) {
      drawable = paramProgressBar.getIndeterminateDrawable();
    } else {
      drawable = drawable.getProgressDrawable();
    } 
    if (drawable == null)
      return; 
    Integer integer = this.mColor;
    if (integer != null) {
      drawable.setColorFilter(integer.intValue(), PorterDuff.Mode.SRC_IN);
      return;
    } 
    drawable.clearColorFilter();
  }
  
  public void apply() {
    ProgressBar progressBar = this.mProgressBar;
    if (progressBar != null) {
      progressBar.setIndeterminate(this.mIndeterminate);
      setColor(this.mProgressBar);
      this.mProgressBar.setProgress((int)(this.mProgress * 1000.0D));
      if (this.mAnimating) {
        this.mProgressBar.setVisibility(0);
        return;
      } 
      this.mProgressBar.setVisibility(8);
      return;
    } 
    throw new JSApplicationIllegalArgumentException("setStyle() not called");
  }
  
  public void setAnimating(boolean paramBoolean) {
    this.mAnimating = paramBoolean;
  }
  
  public void setColor(Integer paramInteger) {
    this.mColor = paramInteger;
  }
  
  public void setIndeterminate(boolean paramBoolean) {
    this.mIndeterminate = paramBoolean;
  }
  
  public void setProgress(double paramDouble) {
    this.mProgress = paramDouble;
  }
  
  public void setStyle(String paramString) {
    int i = ReactProgressBarViewManager.getStyleFromString(paramString);
    this.mProgressBar = ReactProgressBarViewManager.createProgressBar(getContext(), i);
    this.mProgressBar.setMax(1000);
    removeAllViews();
    addView((View)this.mProgressBar, new ViewGroup.LayoutParams(-1, -1));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\progressbar\ProgressBarContainerView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */