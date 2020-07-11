package com.facebook.react.views.text;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.style.ReplacementSpan;
import android.widget.TextView;

public abstract class TextInlineImageSpan extends ReplacementSpan {
  public static void possiblyUpdateInlineImageSpans(Spannable paramSpannable, TextView paramTextView) {
    int j = paramSpannable.length();
    int i = 0;
    TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])paramSpannable.getSpans(0, j, TextInlineImageSpan.class);
    j = arrayOfTextInlineImageSpan.length;
    while (i < j) {
      TextInlineImageSpan textInlineImageSpan = arrayOfTextInlineImageSpan[i];
      textInlineImageSpan.onAttachedToWindow();
      textInlineImageSpan.setTextView(paramTextView);
      i++;
    } 
  }
  
  public abstract Drawable getDrawable();
  
  public abstract int getHeight();
  
  public abstract int getWidth();
  
  public abstract void onAttachedToWindow();
  
  public abstract void onDetachedFromWindow();
  
  public abstract void onFinishTemporaryDetach();
  
  public abstract void onStartTemporaryDetach();
  
  public abstract void setTextView(TextView paramTextView);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\TextInlineImageSpan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */