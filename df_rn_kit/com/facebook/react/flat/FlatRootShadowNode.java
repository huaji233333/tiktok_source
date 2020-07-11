package com.facebook.react.flat;

final class FlatRootShadowNode extends FlatShadowNode {
  FlatRootShadowNode() {
    forceMountToView();
    signalBackingViewIsCreated();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\FlatRootShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */