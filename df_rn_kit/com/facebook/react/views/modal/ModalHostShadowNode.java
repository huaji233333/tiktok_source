package com.facebook.react.views.modal;

import android.content.Context;
import android.graphics.Point;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;

class ModalHostShadowNode extends LayoutShadowNode {
  public ModalHostShadowNode() {}
  
  private ModalHostShadowNode(ModalHostShadowNode paramModalHostShadowNode) {
    super(paramModalHostShadowNode);
  }
  
  public void addChildAt(ReactShadowNodeImpl paramReactShadowNodeImpl, int paramInt) {
    super.addChildAt(paramReactShadowNodeImpl, paramInt);
    Point point = ModalHostHelper.getModalHostSize((Context)getThemedContext());
    paramReactShadowNodeImpl.setStyleWidth(point.x);
    paramReactShadowNodeImpl.setStyleHeight(point.y);
  }
  
  public ModalHostShadowNode mutableCopy() {
    return new ModalHostShadowNode(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\modal\ModalHostShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */