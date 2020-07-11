package com.facebook.react.animation;

import android.view.View;
import com.facebook.i.a.a;

public abstract class Animation {
  private View mAnimatedView;
  
  private final int mAnimationID;
  
  private AnimationListener mAnimationListener;
  
  private volatile boolean mCancelled;
  
  private volatile boolean mIsFinished;
  
  private final AnimationPropertyUpdater mPropertyUpdater;
  
  public Animation(int paramInt, AnimationPropertyUpdater paramAnimationPropertyUpdater) {
    this.mAnimationID = paramInt;
    this.mPropertyUpdater = paramAnimationPropertyUpdater;
  }
  
  public final void cancel() {
    if (!this.mIsFinished) {
      if (this.mCancelled)
        return; 
      this.mCancelled = true;
      AnimationListener animationListener = this.mAnimationListener;
      if (animationListener != null)
        animationListener.onCancel(); 
    } 
  }
  
  protected final void finish() {
    a.a(this.mIsFinished ^ true, "Animation must not already be finished!");
    this.mIsFinished = true;
    if (!this.mCancelled) {
      View view = this.mAnimatedView;
      if (view != null)
        this.mPropertyUpdater.onFinish(view); 
      AnimationListener animationListener = this.mAnimationListener;
      if (animationListener != null)
        animationListener.onFinished(); 
    } 
  }
  
  public int getAnimationID() {
    return this.mAnimationID;
  }
  
  protected final boolean onUpdate(float paramFloat) {
    a.a(this.mIsFinished ^ true, "Animation must not already be finished!");
    if (!this.mCancelled)
      this.mPropertyUpdater.onUpdate((View)a.b(this.mAnimatedView), paramFloat); 
    return !this.mCancelled;
  }
  
  public abstract void run();
  
  public void setAnimationListener(AnimationListener paramAnimationListener) {
    this.mAnimationListener = paramAnimationListener;
  }
  
  public final void start(View paramView) {
    this.mAnimatedView = paramView;
    this.mPropertyUpdater.prepare(paramView);
    run();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animation\Animation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */