package com.facebook.react.views.text;

import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ReactRawTextShadowNode extends ReactShadowNodeImpl {
  private String mText;
  
  public ReactRawTextShadowNode() {}
  
  private ReactRawTextShadowNode(ReactRawTextShadowNode paramReactRawTextShadowNode) {
    super(paramReactRawTextShadowNode);
    this.mText = paramReactRawTextShadowNode.mText;
  }
  
  public String getText() {
    return this.mText;
  }
  
  public boolean isVirtual() {
    return true;
  }
  
  public ReactShadowNodeImpl mutableCopy() {
    return new ReactRawTextShadowNode(this);
  }
  
  @ReactProp(name = "text")
  public void setText(String paramString) {
    this.mText = paramString;
    markUpdated();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getViewClass());
    stringBuilder.append(" [text: ");
    stringBuilder.append(this.mText);
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactRawTextShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */