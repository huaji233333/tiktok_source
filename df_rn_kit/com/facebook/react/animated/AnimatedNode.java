package com.facebook.react.animated;

import com.facebook.i.a.a;
import java.util.ArrayList;
import java.util.List;

abstract class AnimatedNode {
  int mActiveIncomingNodes;
  
  int mBFSColor;
  
  List<AnimatedNode> mChildren;
  
  int mTag = -1;
  
  public final void addChild(AnimatedNode paramAnimatedNode) {
    if (this.mChildren == null)
      this.mChildren = new ArrayList<AnimatedNode>(1); 
    ((List<AnimatedNode>)a.b(this.mChildren)).add(paramAnimatedNode);
    paramAnimatedNode.onAttachedToNode(this);
  }
  
  public void onAttachedToNode(AnimatedNode paramAnimatedNode) {}
  
  public void onDetachedFromNode(AnimatedNode paramAnimatedNode) {}
  
  public final void removeChild(AnimatedNode paramAnimatedNode) {
    if (this.mChildren == null)
      return; 
    paramAnimatedNode.onDetachedFromNode(this);
    this.mChildren.remove(paramAnimatedNode);
  }
  
  public void update() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\AnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */