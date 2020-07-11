package com.facebook.react.views.view;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import com.facebook.react.views.common.ViewHelper;

public class ReactViewBackgroundManager {
  private ReactViewBackgroundDrawable mReactBackgroundDrawable;
  
  private View mView;
  
  public ReactViewBackgroundManager(View paramView) {
    this.mView = paramView;
  }
  
  private ReactViewBackgroundDrawable getOrCreateReactViewBackground() {
    if (this.mReactBackgroundDrawable == null) {
      this.mReactBackgroundDrawable = new ReactViewBackgroundDrawable(this.mView.getContext());
      Drawable drawable = this.mView.getBackground();
      ViewHelper.setBackground(this.mView, null);
      if (drawable == null) {
        ViewHelper.setBackground(this.mView, this.mReactBackgroundDrawable);
      } else {
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] { this.mReactBackgroundDrawable, drawable });
        ViewHelper.setBackground(this.mView, (Drawable)layerDrawable);
      } 
    } 
    return this.mReactBackgroundDrawable;
  }
  
  public void setBackgroundColor(int paramInt) {
    if (paramInt != 0 || this.mReactBackgroundDrawable != null)
      getOrCreateReactViewBackground().setColor(paramInt); 
  }
  
  public void setBorderColor(int paramInt, float paramFloat1, float paramFloat2) {
    getOrCreateReactViewBackground().setBorderColor(paramInt, paramFloat1, paramFloat2);
  }
  
  public void setBorderRadius(float paramFloat) {
    getOrCreateReactViewBackground().setRadius(paramFloat);
  }
  
  public void setBorderRadius(float paramFloat, int paramInt) {
    getOrCreateReactViewBackground().setRadius(paramFloat, paramInt);
  }
  
  public void setBorderStyle(String paramString) {
    getOrCreateReactViewBackground().setBorderStyle(paramString);
  }
  
  public void setBorderWidth(int paramInt, float paramFloat) {
    getOrCreateReactViewBackground().setBorderWidth(paramInt, paramFloat);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\view\ReactViewBackgroundManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */