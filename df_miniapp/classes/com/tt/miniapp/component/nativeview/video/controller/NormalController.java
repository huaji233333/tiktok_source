package com.tt.miniapp.component.nativeview.video.controller;

import android.view.View;
import android.view.ViewGroup;
import com.bytedance.sandboxapp.protocol.service.j.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.video.VideoView;
import com.tt.miniapp.video.patchad.PatchAdVideoCallback;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapphost.util.UIUtils;

class NormalController extends BaseVideoViewController {
  public NormalController(VideoView paramVideoView, WebViewManager.IRender paramIRender, PatchAdVideoCallback paramPatchAdVideoCallback) {
    super(paramVideoView, paramIRender);
  }
  
  public void enterFullScreen() {
    super.enterFullScreen();
    ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).enterFullScreen((View)getVideoView(), a.a.LANDSCAPE);
    this.mBeforeFullScreenLayoutInfo = BaseVideoViewController.VideoLayoutEntity.saveBeforeFullScreenInfo(this.mVideoView);
    UIUtils.attachToDecorView(this.mRender.getCurrentActivity(), (View)this.mVideoView);
  }
  
  public void exitFullScreen() {
    if (!isFullScreen())
      return; 
    super.exitFullScreen();
    ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).exitFullScreen((View)getVideoView());
    UIUtils.detachFromParent((View)this.mVideoView);
    AbsoluteLayout absoluteLayout = this.mVideoView.getViewParent();
    if (absoluteLayout != null) {
      if (this.mBeforeFullScreenLayoutInfo == null)
        this.mBeforeFullScreenLayoutInfo = new BaseVideoViewController.VideoLayoutEntity(this.mVideoView); 
      BaseVideoViewController.VideoLayoutEntity.updateVideoLayoutInfo(this.mBeforeFullScreenLayoutInfo, this.mVideoView);
      absoluteLayout.addView((View)this.mVideoView, (ViewGroup.LayoutParams)this.mBeforeFullScreenLayoutInfo.mBeforeFullScreenLayoutParams);
      absoluteLayout.addNativeView((View)this.mVideoView, this.mBeforeFullScreenLayoutInfo.mBeforeFullScreenOffset);
    } 
  }
  
  public void interceptFullScreen(boolean paramBoolean) {
    if (paramBoolean) {
      enterFullScreen();
      return;
    } 
    exitFullScreen();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\video\controller\NormalController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */