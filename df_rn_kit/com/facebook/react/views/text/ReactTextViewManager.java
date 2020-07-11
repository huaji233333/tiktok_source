package com.facebook.react.views.text;

import android.content.Context;
import android.view.View;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;

@ReactModule(name = "RCTText")
public class ReactTextViewManager extends ReactTextAnchorViewManager<ReactTextView, ReactTextShadowNode> {
  public ReactTextShadowNode createShadowNodeInstance() {
    return new ReactTextShadowNode();
  }
  
  public ReactTextView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactTextView((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "RCTText";
  }
  
  public Class<ReactTextShadowNode> getShadowNodeClass() {
    return ReactTextShadowNode.class;
  }
  
  protected void onAfterUpdateTransaction(ReactTextView paramReactTextView) {
    super.onAfterUpdateTransaction((View)paramReactTextView);
    paramReactTextView.updateView();
  }
  
  public void updateExtraData(ReactTextView paramReactTextView, Object paramObject) {
    paramObject = paramObject;
    if (paramObject.containsImages())
      TextInlineImageSpan.possiblyUpdateInlineImageSpans(paramObject.getText(), paramReactTextView); 
    paramReactTextView.setText((ReactTextUpdate)paramObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactTextViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */