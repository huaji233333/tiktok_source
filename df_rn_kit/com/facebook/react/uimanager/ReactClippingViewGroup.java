package com.facebook.react.uimanager;

import android.graphics.Rect;

public interface ReactClippingViewGroup {
  void getClippingRect(Rect paramRect);
  
  boolean getRemoveClippedSubviews();
  
  void setRemoveClippedSubviews(boolean paramBoolean);
  
  void updateClippingRect();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ReactClippingViewGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */