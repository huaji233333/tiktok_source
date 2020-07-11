package com.facebook.react.views.text;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;

public class ReactVirtualTextShadowNode extends ReactBaseTextShadowNode {
  public ReactVirtualTextShadowNode() {}
  
  private ReactVirtualTextShadowNode(ReactVirtualTextShadowNode paramReactVirtualTextShadowNode) {
    super(paramReactVirtualTextShadowNode);
  }
  
  public boolean isVirtual() {
    return true;
  }
  
  public ReactVirtualTextShadowNode mutableCopy() {
    return new ReactVirtualTextShadowNode(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactVirtualTextShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */