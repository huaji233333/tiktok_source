package com.tt.miniapp.component.nativeview.game;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import com.tt.miniapp.thread.ThreadUtil;

public class GameBtnImage extends GameButton {
  private RoundedImageView mImageView;
  
  GameBtnImage(RoundedImageView paramRoundedImageView, GameButtonStyle paramGameButtonStyle) {
    super((View)paramRoundedImageView, paramGameButtonStyle);
    this.mImageView = paramRoundedImageView;
  }
  
  public void applyImageUpdate(byte paramByte, GameButtonStyle paramGameButtonStyle, Drawable paramDrawable) {
    if (paramDrawable != null)
      this.mImageView.setImageDrawable(paramDrawable); 
    if ((paramByte & 0x4) != 0)
      GameButtonHelper.applyImageStyle(this.mImageView, paramGameButtonStyle); 
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
    return 1;
  }
  
  public void loadImageIfNeed(final byte cmp, final GameButtonStyle style, final GameBtnUpdateAnim anim) {
    if ((cmp & 0x1) != 0) {
      GameButtonHelper.applyImage(this.mImageView, style.content, style.width, style.height, new GameButtonHelper.ApplyImageCallback() {
            public void onLoaded(boolean param1Boolean, Drawable param1Drawable) {
              GameBtnImage.this.updateImageBtn(cmp, style, anim, param1Drawable);
            }
          });
      return;
    } 
    updateImageBtn(cmp, style, anim, (Drawable)null);
  }
  
  public void update(final GameButtonStyle style, final GameBtnUpdateAnim anim) {
    if (style == null)
      return; 
    final byte cmp = this.mStyle.compare(style);
    if (b == 0)
      return; 
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            GameBtnImage.this.loadImageIfNeed(cmp, style, anim);
          }
        });
  }
  
  public void updateImageBtn(final byte cmp, final GameButtonStyle style, GameBtnUpdateAnim paramGameBtnUpdateAnim, final Drawable drawable) {
    if (paramGameBtnUpdateAnim == null || this.mRealView.getVisibility() != 0) {
      applyImageUpdate(cmp, style, drawable);
      return;
    } 
    this.mRealView.startAnimation(paramGameBtnUpdateAnim.getAnim());
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            GameBtnImage.this.applyImageUpdate(cmp, style, drawable);
          }
        }paramGameBtnUpdateAnim.getApplyDelayMs());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\game\GameBtnImage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */