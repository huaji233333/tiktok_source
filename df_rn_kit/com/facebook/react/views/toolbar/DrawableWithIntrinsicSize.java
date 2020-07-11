package com.facebook.react.views.toolbar;

import android.graphics.drawable.Drawable;
import com.facebook.drawee.e.g;
import com.facebook.imagepipeline.j.f;

public class DrawableWithIntrinsicSize extends g implements Drawable.Callback {
  private final f mImageInfo;
  
  public DrawableWithIntrinsicSize(Drawable paramDrawable, f paramf) {
    super(paramDrawable);
    this.mImageInfo = paramf;
  }
  
  public int getIntrinsicHeight() {
    return this.mImageInfo.getHeight();
  }
  
  public int getIntrinsicWidth() {
    return this.mImageInfo.getWidth();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\toolbar\DrawableWithIntrinsicSize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */