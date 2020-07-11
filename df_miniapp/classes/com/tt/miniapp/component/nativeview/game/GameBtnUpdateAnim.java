package com.tt.miniapp.component.nativeview.game;

import android.view.animation.Animation;

public class GameBtnUpdateAnim {
  private Animation mAnim;
  
  private long mApplyDelayMs;
  
  public GameBtnUpdateAnim(Animation paramAnimation, long paramLong) {
    this.mAnim = paramAnimation;
    this.mApplyDelayMs = paramLong;
  }
  
  public Animation getAnim() {
    return this.mAnim;
  }
  
  public long getApplyDelayMs() {
    return this.mApplyDelayMs;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\GameBtnUpdateAnim.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */