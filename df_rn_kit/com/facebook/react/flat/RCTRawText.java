package com.facebook.react.flat;

import android.text.SpannableStringBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;

final class RCTRawText extends FlatTextShadowNode {
  private String mText;
  
  protected final void performApplySpans(SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, boolean paramBoolean) {
    paramSpannableStringBuilder.setSpan(this, paramInt1, paramInt2, 17);
  }
  
  protected final void performCollectAttachDetachListeners(StateBuilder paramStateBuilder) {}
  
  protected final void performCollectText(SpannableStringBuilder paramSpannableStringBuilder) {
    String str = this.mText;
    if (str != null)
      paramSpannableStringBuilder.append(str); 
  }
  
  @ReactProp(name = "text")
  public final void setText(String paramString) {
    this.mText = paramString;
    notifyChanged(true);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTRawText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */