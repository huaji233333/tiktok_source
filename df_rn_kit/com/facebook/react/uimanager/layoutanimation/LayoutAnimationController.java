package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;

public class LayoutAnimationController {
  private final AbstractLayoutAnimation mLayoutCreateAnimation = new LayoutCreateAnimation();
  
  private final AbstractLayoutAnimation mLayoutDeleteAnimation = new LayoutDeleteAnimation();
  
  private final AbstractLayoutAnimation mLayoutUpdateAnimation = new LayoutUpdateAnimation();
  
  private boolean mShouldAnimateLayout;
  
  private void disableUserInteractions(View paramView) {
    int i = 0;
    paramView.setClickable(false);
    if (paramView instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      while (i < viewGroup.getChildCount()) {
        disableUserInteractions(viewGroup.getChildAt(i));
        i++;
      } 
    } 
  }
  
  public void applyLayoutUpdate(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    AbstractLayoutAnimation abstractLayoutAnimation;
    UiThreadUtil.assertOnUiThread();
    if (paramView.getWidth() == 0 || paramView.getHeight() == 0) {
      abstractLayoutAnimation = this.mLayoutCreateAnimation;
    } else {
      abstractLayoutAnimation = this.mLayoutUpdateAnimation;
    } 
    Animation animation = abstractLayoutAnimation.createAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
    if (animation == null || !(animation instanceof HandleLayout))
      paramView.layout(paramInt1, paramInt2, paramInt3 + paramInt1, paramInt4 + paramInt2); 
    if (animation != null)
      paramView.startAnimation(animation); 
  }
  
  public void deleteView(View paramView, final LayoutAnimationListener listener) {
    UiThreadUtil.assertOnUiThread();
    Animation animation = this.mLayoutDeleteAnimation.createAnimation(paramView, paramView.getLeft(), paramView.getTop(), paramView.getWidth(), paramView.getHeight());
    if (animation != null) {
      disableUserInteractions(paramView);
      animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation param1Animation) {
              listener.onAnimationEnd();
            }
            
            public void onAnimationRepeat(Animation param1Animation) {}
            
            public void onAnimationStart(Animation param1Animation) {}
          });
      paramView.startAnimation(animation);
      return;
    } 
    listener.onAnimationEnd();
  }
  
  public void initializeFromConfig(ReadableMap paramReadableMap) {
    if (paramReadableMap == null) {
      reset();
      return;
    } 
    int i = 0;
    this.mShouldAnimateLayout = false;
    if (paramReadableMap.hasKey("duration"))
      i = paramReadableMap.getInt("duration"); 
    if (paramReadableMap.hasKey(LayoutAnimationType.CREATE.toString())) {
      this.mLayoutCreateAnimation.initializeFromConfig(paramReadableMap.getMap(LayoutAnimationType.CREATE.toString()), i);
      this.mShouldAnimateLayout = true;
    } 
    if (paramReadableMap.hasKey(LayoutAnimationType.UPDATE.toString())) {
      this.mLayoutUpdateAnimation.initializeFromConfig(paramReadableMap.getMap(LayoutAnimationType.UPDATE.toString()), i);
      this.mShouldAnimateLayout = true;
    } 
    if (paramReadableMap.hasKey(LayoutAnimationType.DELETE.toString())) {
      this.mLayoutDeleteAnimation.initializeFromConfig(paramReadableMap.getMap(LayoutAnimationType.DELETE.toString()), i);
      this.mShouldAnimateLayout = true;
    } 
  }
  
  public void reset() {
    this.mLayoutCreateAnimation.reset();
    this.mLayoutUpdateAnimation.reset();
    this.mLayoutDeleteAnimation.reset();
    this.mShouldAnimateLayout = false;
  }
  
  public boolean shouldAnimateLayout(View paramView) {
    return (this.mShouldAnimateLayout && paramView.getParent() != null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\LayoutAnimationController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */