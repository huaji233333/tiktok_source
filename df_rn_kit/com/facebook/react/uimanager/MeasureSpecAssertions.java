package com.facebook.react.uimanager;

import android.view.View;

public class MeasureSpecAssertions {
  public static final void assertExplicitMeasureSpec(int paramInt1, int paramInt2) {
    paramInt1 = View.MeasureSpec.getMode(paramInt1);
    paramInt2 = View.MeasureSpec.getMode(paramInt2);
    if (paramInt1 != 0 && paramInt2 != 0)
      return; 
    throw new IllegalStateException("A catalyst view must have an explicit width and height given to it. This should normally happen as part of the standard catalyst UI framework.");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\MeasureSpecAssertions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */