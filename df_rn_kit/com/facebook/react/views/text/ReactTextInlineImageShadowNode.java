package com.facebook.react.views.text;

import com.facebook.react.uimanager.LayoutShadowNode;

public abstract class ReactTextInlineImageShadowNode extends LayoutShadowNode {
  public ReactTextInlineImageShadowNode() {}
  
  public ReactTextInlineImageShadowNode(ReactTextInlineImageShadowNode paramReactTextInlineImageShadowNode) {
    super(paramReactTextInlineImageShadowNode);
  }
  
  public abstract TextInlineImageSpan buildInlineImageSpan();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactTextInlineImageShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */