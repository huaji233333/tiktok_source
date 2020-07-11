package com.facebook.react.views.text;

import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class CustomLetterSpacingSpan extends MetricAffectingSpan {
  private final float mLetterSpacing;
  
  public CustomLetterSpacingSpan(float paramFloat) {
    this.mLetterSpacing = paramFloat;
  }
  
  private void apply(TextPaint paramTextPaint) {
    if (!Float.isNaN(this.mLetterSpacing))
      paramTextPaint.setLetterSpacing(this.mLetterSpacing / paramTextPaint.getTextSize()); 
  }
  
  public void updateDrawState(TextPaint paramTextPaint) {
    apply(paramTextPaint);
  }
  
  public void updateMeasureState(TextPaint paramTextPaint) {
    apply(paramTextPaint);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\CustomLetterSpacingSpan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */