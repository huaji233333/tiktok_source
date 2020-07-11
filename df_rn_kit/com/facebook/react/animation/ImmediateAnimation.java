package com.facebook.react.animation;

public class ImmediateAnimation extends Animation {
  public ImmediateAnimation(int paramInt, AnimationPropertyUpdater paramAnimationPropertyUpdater) {
    super(paramInt, paramAnimationPropertyUpdater);
  }
  
  public void run() {
    onUpdate(1.0F);
    finish();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animation\ImmediateAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */