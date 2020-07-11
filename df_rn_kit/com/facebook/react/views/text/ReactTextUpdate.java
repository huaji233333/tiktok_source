package com.facebook.react.views.text;

import android.text.Spannable;

public class ReactTextUpdate {
  private final boolean mContainsImages;
  
  private final int mJsEventCounter;
  
  private final float mPaddingBottom;
  
  private final float mPaddingLeft;
  
  private final float mPaddingRight;
  
  private final float mPaddingTop;
  
  private final Spannable mText;
  
  private final int mTextAlign;
  
  private final int mTextBreakStrategy;
  
  @Deprecated
  public ReactTextUpdate(Spannable paramSpannable, int paramInt1, boolean paramBoolean, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt2) {
    this(paramSpannable, paramInt1, paramBoolean, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt2, 1);
  }
  
  public ReactTextUpdate(Spannable paramSpannable, int paramInt1, boolean paramBoolean, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt2, int paramInt3) {
    this.mText = paramSpannable;
    this.mJsEventCounter = paramInt1;
    this.mContainsImages = paramBoolean;
    this.mPaddingLeft = paramFloat1;
    this.mPaddingTop = paramFloat2;
    this.mPaddingRight = paramFloat3;
    this.mPaddingBottom = paramFloat4;
    this.mTextAlign = paramInt2;
    this.mTextBreakStrategy = paramInt3;
  }
  
  public boolean containsImages() {
    return this.mContainsImages;
  }
  
  public int getJsEventCounter() {
    return this.mJsEventCounter;
  }
  
  public float getPaddingBottom() {
    return this.mPaddingBottom;
  }
  
  public float getPaddingLeft() {
    return this.mPaddingLeft;
  }
  
  public float getPaddingRight() {
    return this.mPaddingRight;
  }
  
  public float getPaddingTop() {
    return this.mPaddingTop;
  }
  
  public Spannable getText() {
    return this.mText;
  }
  
  public int getTextAlign() {
    return this.mTextAlign;
  }
  
  public int getTextBreakStrategy() {
    return this.mTextBreakStrategy;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactTextUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */