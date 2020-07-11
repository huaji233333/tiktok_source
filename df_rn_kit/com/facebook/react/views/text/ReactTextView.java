package com.facebook.react.views.text;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.react.uimanager.ReactCompoundView;
import com.facebook.react.views.view.ReactViewBackgroundManager;

public class ReactTextView extends TextView implements ReactCompoundView {
  private static final ViewGroup.LayoutParams EMPTY_LAYOUT_PARAMS = new ViewGroup.LayoutParams(0, 0);
  
  private boolean mContainsImages;
  
  private int mDefaultGravityHorizontal = getGravity() & 0x800007;
  
  private int mDefaultGravityVertical = getGravity() & 0x70;
  
  private TextUtils.TruncateAt mEllipsizeLocation = TextUtils.TruncateAt.END;
  
  private float mLineHeight = Float.NaN;
  
  private int mNumberOfLines = Integer.MAX_VALUE;
  
  private ReactViewBackgroundManager mReactBackgroundManager = new ReactViewBackgroundManager((View)this);
  
  private int mTextAlign;
  
  private boolean mTextIsSelectable;
  
  public ReactTextView(Context paramContext) {
    super(paramContext);
  }
  
  public void invalidateDrawable(Drawable paramDrawable) {
    if (this.mContainsImages && getText() instanceof Spanned) {
      Spanned spanned = (Spanned)getText();
      int j = spanned.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])spanned.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        if (arrayOfTextInlineImageSpan[i].getDrawable() == paramDrawable)
          invalidate(); 
        i++;
      } 
    } 
    super.invalidateDrawable(paramDrawable);
  }
  
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.mContainsImages && getText() instanceof Spanned) {
      Spanned spanned = (Spanned)getText();
      int j = spanned.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])spanned.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        arrayOfTextInlineImageSpan[i].onAttachedToWindow();
        i++;
      } 
    } 
  }
  
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (this.mContainsImages && getText() instanceof Spanned) {
      Spanned spanned = (Spanned)getText();
      int j = spanned.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])spanned.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        arrayOfTextInlineImageSpan[i].onDetachedFromWindow();
        i++;
      } 
    } 
  }
  
  public void onFinishTemporaryDetach() {
    super.onFinishTemporaryDetach();
    if (this.mContainsImages && getText() instanceof Spanned) {
      Spanned spanned = (Spanned)getText();
      int j = spanned.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])spanned.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        arrayOfTextInlineImageSpan[i].onFinishTemporaryDetach();
        i++;
      } 
    } 
  }
  
  public void onStartTemporaryDetach() {
    super.onStartTemporaryDetach();
    if (this.mContainsImages && getText() instanceof Spanned) {
      Spanned spanned = (Spanned)getText();
      int j = spanned.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])spanned.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        arrayOfTextInlineImageSpan[i].onStartTemporaryDetach();
        i++;
      } 
    } 
  }
  
  public int reactTagForTouch(float paramFloat1, float paramFloat2) {
    CharSequence charSequence = getText();
    int i = getId();
    int j = (int)paramFloat1;
    int k = (int)paramFloat2;
    Layout layout = getLayout();
    if (layout == null)
      return i; 
    k = layout.getLineForVertical(k);
    int n = (int)layout.getLineLeft(k);
    int i1 = (int)layout.getLineRight(k);
    int m = i;
    if (charSequence instanceof Spanned) {
      m = i;
      if (j >= n) {
        m = i;
        if (j <= i1) {
          Spanned spanned = (Spanned)charSequence;
          int i2 = layout.getOffsetForHorizontal(k, j);
          ReactTagSpan[] arrayOfReactTagSpan = (ReactTagSpan[])spanned.getSpans(i2, i2, ReactTagSpan.class);
          m = i;
          if (arrayOfReactTagSpan != null) {
            k = charSequence.length();
            j = 0;
            while (true) {
              m = i;
              if (j < arrayOfReactTagSpan.length) {
                i1 = spanned.getSpanStart(arrayOfReactTagSpan[j]);
                int i3 = spanned.getSpanEnd(arrayOfReactTagSpan[j]);
                n = k;
                m = i;
                if (i3 > i2) {
                  i1 = i3 - i1;
                  n = k;
                  m = i;
                  if (i1 <= k) {
                    m = arrayOfReactTagSpan[j].getReactTag();
                    n = i1;
                  } 
                } 
                j++;
                k = n;
                i = m;
                continue;
              } 
              break;
            } 
          } 
        } 
      } 
    } 
    return m;
  }
  
  public void setBackgroundColor(int paramInt) {
    this.mReactBackgroundManager.setBackgroundColor(paramInt);
  }
  
  public void setBorderColor(int paramInt, float paramFloat1, float paramFloat2) {
    this.mReactBackgroundManager.setBorderColor(paramInt, paramFloat1, paramFloat2);
  }
  
  public void setBorderRadius(float paramFloat) {
    this.mReactBackgroundManager.setBorderRadius(paramFloat);
  }
  
  public void setBorderRadius(float paramFloat, int paramInt) {
    this.mReactBackgroundManager.setBorderRadius(paramFloat, paramInt);
  }
  
  public void setBorderStyle(String paramString) {
    this.mReactBackgroundManager.setBorderStyle(paramString);
  }
  
  public void setBorderWidth(int paramInt, float paramFloat) {
    this.mReactBackgroundManager.setBorderWidth(paramInt, paramFloat);
  }
  
  public void setEllipsizeLocation(TextUtils.TruncateAt paramTruncateAt) {
    this.mEllipsizeLocation = paramTruncateAt;
  }
  
  void setGravityHorizontal(int paramInt) {
    int i = paramInt;
    if (paramInt == 0)
      i = this.mDefaultGravityHorizontal; 
    setGravity(i | getGravity() & 0xFFFFFFF8 & 0xFF7FFFF8);
  }
  
  void setGravityVertical(int paramInt) {
    int i = paramInt;
    if (paramInt == 0)
      i = this.mDefaultGravityVertical; 
    setGravity(i | getGravity() & 0xFFFFFF8F);
  }
  
  public void setNumberOfLines(int paramInt) {
    int i = paramInt;
    if (paramInt == 0)
      i = Integer.MAX_VALUE; 
    this.mNumberOfLines = i;
    paramInt = this.mNumberOfLines;
    boolean bool = true;
    if (paramInt != 1)
      bool = false; 
    setSingleLine(bool);
    setMaxLines(this.mNumberOfLines);
  }
  
  public void setText(ReactTextUpdate paramReactTextUpdate) {
    this.mContainsImages = paramReactTextUpdate.containsImages();
    if (getLayoutParams() == null)
      setLayoutParams(EMPTY_LAYOUT_PARAMS); 
    setText((CharSequence)paramReactTextUpdate.getText());
    setPadding((int)Math.floor(paramReactTextUpdate.getPaddingLeft()), (int)Math.floor(paramReactTextUpdate.getPaddingTop()), (int)Math.floor(paramReactTextUpdate.getPaddingRight()), (int)Math.floor(paramReactTextUpdate.getPaddingBottom()));
    int i = paramReactTextUpdate.getTextAlign();
    if (this.mTextAlign != i)
      this.mTextAlign = i; 
    setGravityHorizontal(this.mTextAlign);
    if (Build.VERSION.SDK_INT >= 23 && getBreakStrategy() != paramReactTextUpdate.getTextBreakStrategy())
      setBreakStrategy(paramReactTextUpdate.getTextBreakStrategy()); 
  }
  
  public void setTextIsSelectable(boolean paramBoolean) {
    this.mTextIsSelectable = paramBoolean;
    super.setTextIsSelectable(paramBoolean);
  }
  
  public void updateView() {
    TextUtils.TruncateAt truncateAt;
    if (this.mNumberOfLines == Integer.MAX_VALUE) {
      truncateAt = null;
    } else {
      truncateAt = this.mEllipsizeLocation;
    } 
    setEllipsize(truncateAt);
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    if (this.mContainsImages && getText() instanceof Spanned) {
      Spanned spanned = (Spanned)getText();
      int j = spanned.length();
      int i = 0;
      TextInlineImageSpan[] arrayOfTextInlineImageSpan = (TextInlineImageSpan[])spanned.getSpans(0, j, TextInlineImageSpan.class);
      j = arrayOfTextInlineImageSpan.length;
      while (i < j) {
        if (arrayOfTextInlineImageSpan[i].getDrawable() == paramDrawable)
          return true; 
        i++;
      } 
    } 
    return super.verifyDrawable(paramDrawable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */