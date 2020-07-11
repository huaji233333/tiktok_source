package com.facebook.react.animation;

import android.util.SparseArray;
import com.facebook.react.bridge.UiThreadUtil;

public class AnimationRegistry {
  private final SparseArray<Animation> mRegistry = new SparseArray();
  
  public Animation getAnimation(int paramInt) {
    UiThreadUtil.assertOnUiThread();
    return (Animation)this.mRegistry.get(paramInt);
  }
  
  public void registerAnimation(Animation paramAnimation) {
    UiThreadUtil.assertOnUiThread();
    this.mRegistry.put(paramAnimation.getAnimationID(), paramAnimation);
  }
  
  public Animation removeAnimation(int paramInt) {
    UiThreadUtil.assertOnUiThread();
    Animation animation = (Animation)this.mRegistry.get(paramInt);
    if (animation != null)
      this.mRegistry.delete(paramInt); 
    return animation;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animation\AnimationRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */