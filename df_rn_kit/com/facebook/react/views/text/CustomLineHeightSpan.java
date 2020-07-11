package com.facebook.react.views.text;

import android.graphics.Paint;
import android.text.style.LineHeightSpan;

public class CustomLineHeightSpan implements LineHeightSpan {
  private final int mHeight;
  
  CustomLineHeightSpan(float paramFloat) {
    this.mHeight = (int)Math.ceil(paramFloat);
  }
  
  public void chooseHeight(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Paint.FontMetricsInt paramFontMetricsInt) {
    paramInt1 = paramFontMetricsInt.descent;
    paramInt2 = this.mHeight;
    if (paramInt1 > paramInt2) {
      paramInt1 = Math.min(paramInt2, paramFontMetricsInt.descent);
      paramFontMetricsInt.descent = paramInt1;
      paramFontMetricsInt.bottom = paramInt1;
      paramFontMetricsInt.ascent = 0;
      paramFontMetricsInt.top = 0;
      return;
    } 
    if (-paramFontMetricsInt.ascent + paramFontMetricsInt.descent > this.mHeight) {
      paramFontMetricsInt.bottom = paramFontMetricsInt.descent;
      paramInt1 = -this.mHeight + paramFontMetricsInt.descent;
      paramFontMetricsInt.ascent = paramInt1;
      paramFontMetricsInt.top = paramInt1;
      return;
    } 
    if (-paramFontMetricsInt.ascent + paramFontMetricsInt.bottom > this.mHeight) {
      paramFontMetricsInt.top = paramFontMetricsInt.ascent;
      paramFontMetricsInt.bottom = paramFontMetricsInt.ascent + this.mHeight;
      return;
    } 
    paramInt2 = -paramFontMetricsInt.top;
    paramInt3 = paramFontMetricsInt.bottom;
    paramInt1 = this.mHeight;
    if (paramInt2 + paramInt3 > paramInt1) {
      paramFontMetricsInt.top = paramFontMetricsInt.bottom - this.mHeight;
      return;
    } 
    paramInt2 = -paramFontMetricsInt.top;
    paramInt3 = paramFontMetricsInt.bottom;
    double d2 = paramFontMetricsInt.top;
    double d1 = ((paramInt1 - paramInt2 + paramInt3) / 2.0F);
    double d3 = Math.ceil(d1);
    Double.isNaN(d2);
    paramFontMetricsInt.top = (int)(d2 - d3);
    d2 = paramFontMetricsInt.bottom;
    d1 = Math.floor(d1);
    Double.isNaN(d2);
    paramFontMetricsInt.bottom = (int)(d2 + d1);
    paramFontMetricsInt.ascent = paramFontMetricsInt.top;
    paramFontMetricsInt.descent = paramFontMetricsInt.bottom;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\CustomLineHeightSpan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */