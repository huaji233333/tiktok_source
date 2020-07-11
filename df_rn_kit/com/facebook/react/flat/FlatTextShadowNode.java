package com.facebook.react.flat;

import android.text.SpannableStringBuilder;
import com.facebook.react.uimanager.ReactShadowNodeImpl;

abstract class FlatTextShadowNode extends FlatShadowNode {
  private int mTextBegin;
  
  private int mTextEnd;
  
  final void applySpans(SpannableStringBuilder paramSpannableStringBuilder, boolean paramBoolean) {
    if (this.mTextBegin != this.mTextEnd || shouldAllowEmptySpans())
      performApplySpans(paramSpannableStringBuilder, this.mTextBegin, this.mTextEnd, paramBoolean); 
  }
  
  final void collectText(SpannableStringBuilder paramSpannableStringBuilder) {
    this.mTextBegin = paramSpannableStringBuilder.length();
    performCollectText(paramSpannableStringBuilder);
    this.mTextEnd = paramSpannableStringBuilder.length();
  }
  
  boolean isEditable() {
    return false;
  }
  
  public boolean isVirtual() {
    return true;
  }
  
  protected void notifyChanged(boolean paramBoolean) {
    ReactShadowNodeImpl reactShadowNodeImpl = getParent();
    if (reactShadowNodeImpl instanceof FlatTextShadowNode)
      ((FlatTextShadowNode)reactShadowNodeImpl).notifyChanged(paramBoolean); 
  }
  
  protected abstract void performApplySpans(SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, boolean paramBoolean);
  
  protected abstract void performCollectAttachDetachListeners(StateBuilder paramStateBuilder);
  
  protected abstract void performCollectText(SpannableStringBuilder paramSpannableStringBuilder);
  
  boolean shouldAllowEmptySpans() {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatTextShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */