package com.facebook.react.flat;

import android.text.Layout;
import android.text.Spanned;

final class TextNodeRegion extends NodeRegion {
  private Layout mLayout;
  
  TextNodeRegion(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, boolean paramBoolean, Layout paramLayout) {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt, paramBoolean);
    this.mLayout = paramLayout;
  }
  
  final Layout getLayout() {
    return this.mLayout;
  }
  
  final int getReactTag(float paramFloat1, float paramFloat2) {
    Layout layout = this.mLayout;
    if (layout != null) {
      CharSequence charSequence = layout.getText();
      if (charSequence instanceof Spanned) {
        int i = Math.round(paramFloat2 - getTop());
        if (i >= this.mLayout.getLineTop(0)) {
          Layout layout1 = this.mLayout;
          if (i < layout1.getLineBottom(layout1.getLineCount() - 1)) {
            float f = Math.round(paramFloat1 - getLeft());
            i = this.mLayout.getLineForVertical(i);
            if (this.mLayout.getLineLeft(i) <= f && f <= this.mLayout.getLineRight(i)) {
              i = this.mLayout.getOffsetForHorizontal(i, f);
              RCTRawText[] arrayOfRCTRawText = (RCTRawText[])((Spanned)charSequence).getSpans(i, i, RCTRawText.class);
              if (arrayOfRCTRawText.length != 0)
                return arrayOfRCTRawText[0].getReactTag(); 
            } 
          } 
        } 
      } 
    } 
    return super.getReactTag(paramFloat1, paramFloat2);
  }
  
  final boolean matchesTag(int paramInt) {
    if (super.matchesTag(paramInt))
      return true; 
    Layout layout = this.mLayout;
    if (layout != null) {
      CharSequence charSequence = layout.getText();
      if (charSequence instanceof Spanned) {
        RCTRawText[] arrayOfRCTRawText = (RCTRawText[])((Spanned)charSequence).getSpans(0, charSequence.length(), RCTRawText.class);
        int j = arrayOfRCTRawText.length;
        for (int i = 0; i < j; i++) {
          if (arrayOfRCTRawText[i].getReactTag() == paramInt)
            return true; 
        } 
      } 
    } 
    return false;
  }
  
  public final void setLayout(Layout paramLayout) {
    this.mLayout = paramLayout;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\TextNodeRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */