package com.facebook.react.flat;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.ViewGroup;
import android.widget.EditText;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.view.MeasureUtil;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.b;

public class RCTTextInput extends RCTVirtualText implements AndroidView, YogaMeasureFunction {
  private EditText mEditText;
  
  private int mJsEventCount = -1;
  
  private int mNumberOfLines = -1;
  
  private boolean mPaddingChanged;
  
  private String mText;
  
  public RCTTextInput() {
    forceMountToView();
    setMeasureFunction(this);
  }
  
  boolean isEditable() {
    return true;
  }
  
  public boolean isPaddingChanged() {
    return this.mPaddingChanged;
  }
  
  public boolean isVirtual() {
    return false;
  }
  
  public boolean isVirtualAnchor() {
    return true;
  }
  
  public long measure(YogaNode paramYogaNode, float paramFloat1, YogaMeasureMode paramYogaMeasureMode1, float paramFloat2, YogaMeasureMode paramYogaMeasureMode2) {
    EditText editText = (EditText)a.b(this.mEditText);
    int j = getFontSize();
    int i = j;
    if (j == -1)
      i = (int)Math.ceil(PixelUtil.toPixelFromSP(14.0F)); 
    editText.setTextSize(0, i);
    i = this.mNumberOfLines;
    if (i != -1)
      editText.setLines(i); 
    editText.measure(MeasureUtil.getMeasureSpec(paramFloat1, paramYogaMeasureMode1), MeasureUtil.getMeasureSpec(paramFloat2, paramYogaMeasureMode2));
    return b.a(editText.getMeasuredWidth(), editText.getMeasuredHeight());
  }
  
  public boolean needsCustomLayoutForChildren() {
    return false;
  }
  
  protected void notifyChanged(boolean paramBoolean) {
    super.notifyChanged(paramBoolean);
    markUpdated();
  }
  
  public void onCollectExtraUpdates(UIViewOperationQueue paramUIViewOperationQueue) {
    super.onCollectExtraUpdates(paramUIViewOperationQueue);
    if (this.mJsEventCount != -1) {
      ReactTextUpdate reactTextUpdate = new ReactTextUpdate((Spannable)getText(), this.mJsEventCount, false, getPadding(4), getPadding(1), getPadding(5), getPadding(3), -1);
      paramUIViewOperationQueue.enqueueUpdateExtraData(getReactTag(), reactTextUpdate);
    } 
  }
  
  protected void performCollectText(SpannableStringBuilder paramSpannableStringBuilder) {
    String str = this.mText;
    if (str != null)
      paramSpannableStringBuilder.append(str); 
    super.performCollectText(paramSpannableStringBuilder);
  }
  
  public void resetPaddingChanged() {
    this.mPaddingChanged = false;
  }
  
  public void setBackgroundColor(int paramInt) {}
  
  @ReactProp(name = "mostRecentEventCount")
  public void setMostRecentEventCount(int paramInt) {
    this.mJsEventCount = paramInt;
  }
  
  @ReactProp(defaultInt = 2147483647, name = "numberOfLines")
  public void setNumberOfLines(int paramInt) {
    this.mNumberOfLines = paramInt;
    notifyChanged(true);
  }
  
  public void setPadding(int paramInt, float paramFloat) {
    super.setPadding(paramInt, paramFloat);
    this.mPaddingChanged = true;
    dirty();
  }
  
  @ReactProp(name = "text")
  public void setText(String paramString) {
    this.mText = paramString;
    notifyChanged(true);
  }
  
  public void setThemedContext(ThemedReactContext paramThemedReactContext) {
    super.setThemedContext(paramThemedReactContext);
    this.mEditText = new EditText((Context)paramThemedReactContext);
    this.mEditText.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    setDefaultPadding(4, this.mEditText.getPaddingStart());
    setDefaultPadding(1, this.mEditText.getPaddingTop());
    setDefaultPadding(5, this.mEditText.getPaddingEnd());
    setDefaultPadding(3, this.mEditText.getPaddingBottom());
    this.mEditText.setPadding(0, 0, 0, 0);
  }
  
  boolean shouldAllowEmptySpans() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTTextInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */