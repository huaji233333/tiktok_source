package com.tt.miniapp.component.nativeview.video.controller;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.bytedance.sandboxapp.protocol.service.j.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.video.VideoView;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapphost.util.JsonBuilder;
import org.json.JSONObject;

class RenderInBrowserController extends BaseVideoViewController {
  public RenderInBrowserController(VideoView paramVideoView, WebViewManager.IRender paramIRender) {
    super(paramVideoView, paramIRender);
  }
  
  public void enterFullScreen() {
    super.enterFullScreen();
    ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).enterFullScreen((View)getVideoView(), a.a.LANDSCAPE);
    this.mBeforeFullScreenLayoutInfo = BaseVideoViewController.VideoLayoutEntity.saveBeforeFullScreenInfo(this.mVideoView);
    AbsoluteLayout absoluteLayout = this.mVideoView.getViewParent();
    if (absoluteLayout == null)
      return; 
    final ViewTreeObserver viewTreeObserver = absoluteLayout.getViewTreeObserver();
    if (viewTreeObserver == null)
      return; 
    viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          public void onGlobalLayout() {
            if ((RenderInBrowserController.this.mVideoView.getResources().getConfiguration()).orientation == 2) {
              AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(new ViewGroup.LayoutParams(RenderInBrowserController.this.mRender.getRenderWidth(), RenderInBrowserController.this.mRender.getRenderHeight()));
              layoutParams.x = 0;
              layoutParams.y = 0;
              layoutParams.zIndex = RenderInBrowserController.this.mZIndex;
              layoutParams.isFullScreen = true;
              RenderInBrowserController.this.mVideoView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
              viewTreeObserver.removeOnGlobalLayoutListener(this);
            } 
          }
        });
  }
  
  public void exitFullScreen() {
    if (!isFullScreen())
      return; 
    super.exitFullScreen();
    ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).exitFullScreen((View)getVideoView());
    AbsoluteLayout absoluteLayout = this.mVideoView.getViewParent();
    if (absoluteLayout != null) {
      if (this.mBeforeFullScreenLayoutInfo == null)
        this.mBeforeFullScreenLayoutInfo = new BaseVideoViewController.VideoLayoutEntity(this.mVideoView); 
      BaseVideoViewController.VideoLayoutEntity.updateVideoLayoutInfo(this.mBeforeFullScreenLayoutInfo, this.mVideoView);
      this.mVideoView.setLayoutParams((ViewGroup.LayoutParams)this.mBeforeFullScreenLayoutInfo.mBeforeFullScreenLayoutParams);
      absoluteLayout.updateNativeViewOffset((View)this.mVideoView, this.mBeforeFullScreenLayoutInfo.mBeforeFullScreenOffset);
    } 
  }
  
  public void interceptFullScreen(boolean paramBoolean) {
    String str;
    JSONObject jSONObject = (new JsonBuilder()).put("videoPlayerId", Integer.valueOf(this.mVideoModel.videoPlayerId)).build();
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    int i = this.mRender.getWebViewId();
    if (paramBoolean) {
      str = "onVideoRequestFullScreen";
    } else {
      str = "onVideoExitFullScreen";
    } 
    webViewManager.publish(i, str, jSONObject.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\video\controller\RenderInBrowserController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */