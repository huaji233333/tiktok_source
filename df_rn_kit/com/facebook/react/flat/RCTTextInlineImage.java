package com.facebook.react.flat;

import android.content.Context;
import android.text.SpannableStringBuilder;
import com.facebook.imagepipeline.o.b;
import com.facebook.imagepipeline.o.c;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.imagehelper.ImageSource;

class RCTTextInlineImage extends FlatTextShadowNode {
  private InlineImageSpanWithPipeline mInlineImageSpan = new InlineImageSpanWithPipeline();
  
  private InlineImageSpanWithPipeline getMutableSpan() {
    if (this.mInlineImageSpan.isFrozen())
      this.mInlineImageSpan = this.mInlineImageSpan.mutableCopy(); 
    return this.mInlineImageSpan;
  }
  
  protected void performApplySpans(SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, boolean paramBoolean) {
    this.mInlineImageSpan.freeze();
    paramSpannableStringBuilder.setSpan(this.mInlineImageSpan, paramInt1, paramInt2, 17);
  }
  
  protected void performCollectAttachDetachListeners(StateBuilder paramStateBuilder) {
    paramStateBuilder.addAttachDetachListener(this.mInlineImageSpan);
  }
  
  protected void performCollectText(SpannableStringBuilder paramSpannableStringBuilder) {
    paramSpannableStringBuilder.append("I");
  }
  
  @ReactProp(name = "src")
  public void setSource(ReadableArray paramReadableArray) {
    String str;
    ImageSource imageSource1;
    b b;
    ImageSource imageSource2 = null;
    if (paramReadableArray == null || paramReadableArray.size() == 0) {
      paramReadableArray = null;
    } else {
      str = paramReadableArray.getMap(0).getString("uri");
    } 
    if (str == null) {
      str = null;
    } else {
      imageSource1 = new ImageSource((Context)getThemedContext(), str);
    } 
    InlineImageSpanWithPipeline inlineImageSpanWithPipeline = getMutableSpan();
    if (imageSource1 == null) {
      imageSource1 = imageSource2;
    } else {
      b = c.a(imageSource1.getUri()).a();
    } 
    inlineImageSpanWithPipeline.setImageRequest(b);
  }
  
  public void setStyleHeight(float paramFloat) {
    super.setStyleHeight(paramFloat);
    if (this.mInlineImageSpan.getHeight() != paramFloat) {
      getMutableSpan().setHeight(paramFloat);
      notifyChanged(true);
    } 
  }
  
  public void setStyleWidth(float paramFloat) {
    super.setStyleWidth(paramFloat);
    if (this.mInlineImageSpan.getWidth() != paramFloat) {
      getMutableSpan().setWidth(paramFloat);
      notifyChanged(true);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTTextInlineImage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */