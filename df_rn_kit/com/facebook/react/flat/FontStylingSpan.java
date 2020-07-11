package com.facebook.react.flat;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

final class FontStylingSpan extends MetricAffectingSpan {
  static final FontStylingSpan INSTANCE = new FontStylingSpan(-1.6777216E7D, 0, -1, -1, -1, false, false, null, true);
  
  private int mBackgroundColor;
  
  private String mFontFamily;
  
  private int mFontSize;
  
  private int mFontStyle;
  
  private int mFontWeight;
  
  private boolean mFrozen;
  
  private boolean mHasStrikeThrough;
  
  private boolean mHasUnderline;
  
  private double mTextColor;
  
  FontStylingSpan() {}
  
  private FontStylingSpan(double paramDouble, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, String paramString, boolean paramBoolean3) {
    this.mTextColor = paramDouble;
    this.mBackgroundColor = paramInt1;
    this.mFontSize = paramInt2;
    this.mFontStyle = paramInt3;
    this.mFontWeight = paramInt4;
    this.mHasUnderline = paramBoolean1;
    this.mHasStrikeThrough = paramBoolean2;
    this.mFontFamily = paramString;
    this.mFrozen = paramBoolean3;
  }
  
  private int getNewStyle(int paramInt) {
    int j = this.mFontStyle;
    int i = paramInt;
    if (j != -1)
      i = paramInt & 0xFFFFFFFD | j; 
    j = this.mFontWeight;
    paramInt = i;
    if (j != -1)
      paramInt = i & 0xFFFFFFFE | j; 
    return paramInt;
  }
  
  private void updateTypeface(TextPaint paramTextPaint) {
    int i;
    Typeface typeface = paramTextPaint.getTypeface();
    if (typeface == null) {
      i = 0;
    } else {
      i = typeface.getStyle();
    } 
    int j = getNewStyle(i);
    if (i == j && this.mFontFamily == null)
      return; 
    String str = this.mFontFamily;
    if (str != null) {
      typeface = TypefaceCache.getTypeface(str, j);
    } else {
      typeface = TypefaceCache.getTypeface(typeface, j);
    } 
    paramTextPaint.setTypeface(typeface);
  }
  
  final void freeze() {
    this.mFrozen = true;
  }
  
  final int getBackgroundColor() {
    return this.mBackgroundColor;
  }
  
  final String getFontFamily() {
    return this.mFontFamily;
  }
  
  final int getFontSize() {
    return this.mFontSize;
  }
  
  final int getFontStyle() {
    return this.mFontStyle;
  }
  
  final int getFontWeight() {
    return this.mFontWeight;
  }
  
  final double getTextColor() {
    return this.mTextColor;
  }
  
  final boolean hasStrikeThrough() {
    return this.mHasStrikeThrough;
  }
  
  final boolean hasUnderline() {
    return this.mHasUnderline;
  }
  
  final boolean isFrozen() {
    return this.mFrozen;
  }
  
  final FontStylingSpan mutableCopy() {
    return new FontStylingSpan(this.mTextColor, this.mBackgroundColor, this.mFontSize, this.mFontStyle, this.mFontWeight, this.mHasUnderline, this.mHasStrikeThrough, this.mFontFamily, false);
  }
  
  final void setBackgroundColor(int paramInt) {
    this.mBackgroundColor = paramInt;
  }
  
  final void setFontFamily(String paramString) {
    this.mFontFamily = paramString;
  }
  
  final void setFontSize(int paramInt) {
    this.mFontSize = paramInt;
  }
  
  final void setFontStyle(int paramInt) {
    this.mFontStyle = paramInt;
  }
  
  final void setFontWeight(int paramInt) {
    this.mFontWeight = paramInt;
  }
  
  final void setHasStrikeThrough(boolean paramBoolean) {
    this.mHasStrikeThrough = paramBoolean;
  }
  
  final void setHasUnderline(boolean paramBoolean) {
    this.mHasUnderline = paramBoolean;
  }
  
  final void setTextColor(double paramDouble) {
    this.mTextColor = paramDouble;
  }
  
  public final void updateDrawState(TextPaint paramTextPaint) {
    if (!Double.isNaN(this.mTextColor))
      paramTextPaint.setColor((int)this.mTextColor); 
    paramTextPaint.bgColor = this.mBackgroundColor;
    paramTextPaint.setUnderlineText(this.mHasUnderline);
    paramTextPaint.setStrikeThruText(this.mHasStrikeThrough);
    updateMeasureState(paramTextPaint);
  }
  
  public final void updateMeasureState(TextPaint paramTextPaint) {
    int i = this.mFontSize;
    if (i != -1)
      paramTextPaint.setTextSize(i); 
    updateTypeface(paramTextPaint);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FontStylingSpan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */