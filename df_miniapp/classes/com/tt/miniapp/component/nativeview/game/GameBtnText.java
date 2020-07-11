package com.tt.miniapp.component.nativeview.game;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tt.miniapp.thread.ThreadUtil;

public class GameBtnText extends GameButton {
  private TextView mTextView;
  
  GameBtnText(TextView paramTextView, GameButtonStyle paramGameButtonStyle) {
    super((View)paramTextView, paramGameButtonStyle);
    this.mTextView = paramTextView;
  }
  
  public void applyTextUpdate(byte paramByte, GameButtonStyle paramGameButtonStyle) {
    if ((paramByte & 0x4) != 0)
      GameButtonHelper.applyTextStyle(this.mTextView, paramGameButtonStyle); 
    if ((paramByte & 0x1) != 0)
      GameButtonHelper.applyText(this.mTextView, paramGameButtonStyle.content); 
    if ((paramByte & 0x2) != 0) {
      GameAbsoluteLayout.LayoutParams layoutParams = (GameAbsoluteLayout.LayoutParams)this.mRealView.getLayoutParams();
      layoutParams.width = paramGameButtonStyle.width;
      layoutParams.height = paramGameButtonStyle.height;
      layoutParams.setXY(paramGameButtonStyle.left, paramGameButtonStyle.top);
      this.mRealView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    } 
    this.mStyle = paramGameButtonStyle;
  }
  
  public int getBtnType() {
    return 0;
  }
  
  public void update(final GameButtonStyle style, final GameBtnUpdateAnim anim) {
    if (style == null)
      return; 
    final byte cmp = this.mStyle.compare(style);
    if (b == 0)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            GameBtnText.this.updateTextBtn(cmp, style, anim);
          }
        });
  }
  
  public void updateTextBtn(final byte cmp, final GameButtonStyle style, GameBtnUpdateAnim paramGameBtnUpdateAnim) {
    if (paramGameBtnUpdateAnim == null || this.mRealView.getVisibility() != 0) {
      applyTextUpdate(cmp, style);
      return;
    } 
    this.mRealView.startAnimation(paramGameBtnUpdateAnim.getAnim());
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            GameBtnText.this.applyTextUpdate(cmp, style);
          }
        }paramGameBtnUpdateAnim.getApplyDelayMs());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\GameBtnText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */