package com.tt.miniapp.component.nativeview.game;

import android.view.View;
import android.view.ViewGroup;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class GameButton {
  View mRealView;
  
  GameButtonStyle mStyle;
  
  GameButton(View paramView, GameButtonStyle paramGameButtonStyle) {
    this.mRealView = paramView;
    this.mStyle = paramGameButtonStyle;
  }
  
  public void addToParent(final GameAbsoluteLayout parent) {
    if (parent == null)
      return; 
    AppBrandLogger.d("GameButton", new Object[] { Integer.valueOf(this.mStyle.width), Integer.valueOf(this.mStyle.height), Integer.valueOf(this.mStyle.left), Integer.valueOf(this.mStyle.top) });
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            parent.addView(GameButton.this.mRealView, (ViewGroup.LayoutParams)new GameAbsoluteLayout.LayoutParams(GameButton.this.mStyle.width, GameButton.this.mStyle.height, GameButton.this.mStyle.left, GameButton.this.mStyle.top));
          }
        });
  }
  
  public abstract int getBtnType();
  
  public GameButtonStyle getStyle() {
    return this.mStyle;
  }
  
  public void removeFromParent() {
    final ViewGroup viewParent = (ViewGroup)this.mRealView.getParent();
    if (viewGroup == null)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            viewParent.removeView(GameButton.this.mRealView);
          }
        });
  }
  
  public void setViewOnclickListener(final Runnable r) {
    this.mRealView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            r.run();
          }
        });
  }
  
  public void setVisibility(boolean paramBoolean) {
    final byte visibleInt;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 8;
    } 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            GameButton.this.mRealView.setVisibility(visibleInt);
          }
        });
  }
  
  public abstract void update(GameButtonStyle paramGameButtonStyle, GameBtnUpdateAnim paramGameBtnUpdateAnim);
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Type {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\GameButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */