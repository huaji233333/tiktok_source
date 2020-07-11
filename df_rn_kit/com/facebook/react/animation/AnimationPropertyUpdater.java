package com.facebook.react.animation;

import android.view.View;

public interface AnimationPropertyUpdater {
  void onFinish(View paramView);
  
  void onUpdate(View paramView, float paramFloat);
  
  void prepare(View paramView);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animation\AnimationPropertyUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */