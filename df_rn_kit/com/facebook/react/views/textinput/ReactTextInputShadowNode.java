package com.facebook.react.views.textinput;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.EditText;
import com.facebook.i.a.a;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactBaseTextShadowNode;
import com.facebook.react.views.text.ReactTextUpdate;
import com.facebook.react.views.view.MeasureUtil;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.b;

public class ReactTextInputShadowNode extends ReactBaseTextShadowNode implements YogaMeasureFunction {
  private EditText mDummyEditText;
  
  private ReactTextInputLocalData mLocalData;
  
  private int mMostRecentEventCount = -1;
  
  private String mText;
  
  public ReactTextInputShadowNode() {
    int i = Build.VERSION.SDK_INT;
    this.mTextBreakStrategy = 0;
    setMeasureFunction(this);
  }
  
  private ReactTextInputShadowNode(ReactTextInputShadowNode paramReactTextInputShadowNode) {
    super(paramReactTextInputShadowNode);
    this.mMostRecentEventCount = paramReactTextInputShadowNode.mMostRecentEventCount;
    this.mText = paramReactTextInputShadowNode.mText;
    this.mLocalData = paramReactTextInputShadowNode.mLocalData;
    setMeasureFunction(this);
    ThemedReactContext themedReactContext = getThemedContext();
    if (themedReactContext != null)
      setThemedContext(themedReactContext); 
  }
  
  public String getText() {
    return this.mText;
  }
  
  public boolean isVirtualAnchor() {
    return true;
  }
  
  public boolean isYogaLeafNode() {
    return true;
  }
  
  public long measure(YogaNode paramYogaNode, float paramFloat1, YogaMeasureMode paramYogaMeasureMode1, float paramFloat2, YogaMeasureMode paramYogaMeasureMode2) {
    EditText editText = (EditText)a.b(this.mDummyEditText);
    ReactTextInputLocalData reactTextInputLocalData = this.mLocalData;
    if (reactTextInputLocalData == null)
      return b.a(0, 0); 
    reactTextInputLocalData.apply(editText);
    editText.measure(MeasureUtil.getMeasureSpec(paramFloat1, paramYogaMeasureMode1), MeasureUtil.getMeasureSpec(paramFloat2, paramYogaMeasureMode2));
    return b.a(editText.getMeasuredWidth(), editText.getMeasuredHeight());
  }
  
  public ReactTextInputShadowNode mutableCopy() {
    return new ReactTextInputShadowNode(this);
  }
  
  public void onCollectExtraUpdates(UIViewOperationQueue paramUIViewOperationQueue) {
    super.onCollectExtraUpdates(paramUIViewOperationQueue);
    if (this.mMostRecentEventCount != -1) {
      ReactTextUpdate reactTextUpdate = new ReactTextUpdate(spannedFromShadowNode(this, getText()), this.mMostRecentEventCount, this.mContainsImages, getPadding(0), getPadding(1), getPadding(2), getPadding(3), this.mTextAlign, this.mTextBreakStrategy);
      paramUIViewOperationQueue.enqueueUpdateExtraData(getReactTag(), reactTextUpdate);
    } 
  }
  
  public void setLocalData(Object paramObject) {
    a.a(paramObject instanceof ReactTextInputLocalData);
    this.mLocalData = (ReactTextInputLocalData)paramObject;
    dirty();
  }
  
  @ReactProp(name = "mostRecentEventCount")
  public void setMostRecentEventCount(int paramInt) {
    this.mMostRecentEventCount = paramInt;
  }
  
  public void setPadding(int paramInt, float paramFloat) {
    super.setPadding(paramInt, paramFloat);
    markUpdated();
  }
  
  @ReactProp(name = "text")
  public void setText(String paramString) {
    this.mText = paramString;
    markUpdated();
  }
  
  public void setTextBreakStrategy(String paramString) {
    if (Build.VERSION.SDK_INT < 23)
      return; 
    if (paramString == null || "simple".equals(paramString)) {
      this.mTextBreakStrategy = 0;
      return;
    } 
    if ("highQuality".equals(paramString)) {
      this.mTextBreakStrategy = 1;
      return;
    } 
    if ("balanced".equals(paramString)) {
      this.mTextBreakStrategy = 2;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Invalid textBreakStrategy: ");
    stringBuilder.append(paramString);
    throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
  }
  
  public void setThemedContext(ThemedReactContext paramThemedReactContext) {
    super.setThemedContext(paramThemedReactContext);
    EditText editText = new EditText((Context)getThemedContext());
    setDefaultPadding(4, editText.getPaddingStart());
    setDefaultPadding(1, editText.getPaddingTop());
    setDefaultPadding(5, editText.getPaddingEnd());
    setDefaultPadding(3, editText.getPaddingBottom());
    this.mDummyEditText = editText;
    this.mDummyEditText.setPadding(0, 0, 0, 0);
    this.mDummyEditText.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\textinput\ReactTextInputShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */