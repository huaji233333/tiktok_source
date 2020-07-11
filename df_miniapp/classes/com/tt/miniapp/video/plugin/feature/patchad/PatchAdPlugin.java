package com.tt.miniapp.video.plugin.feature.patchad;

import android.os.Bundle;
import com.tt.miniapp.video.patchad.PatchAdVideoCallback;
import com.tt.miniapp.video.plugin.base.BaseVideoPlugin;
import com.tt.miniapp.video.plugin.base.IVideoPluginEvent;

public class PatchAdPlugin extends BaseVideoPlugin {
  private PatchAdVideoCallback mCallback;
  
  private PatchAdLayout mPatchAdLayout;
  
  private int mVideoHeight;
  
  private int mVideoWidth;
  
  public PatchAdPlugin(PatchAdVideoCallback paramPatchAdVideoCallback) {
    this.mCallback = paramPatchAdVideoCallback;
  }
  
  private boolean isViewReady() {
    return (this.mPatchAdLayout != null);
  }
  
  public int getPluginType() {
    return 208;
  }
  
  public boolean handleVideoEvent(IVideoPluginEvent paramIVideoPluginEvent) {
    if (paramIVideoPluginEvent != null) {
      Bundle bundle = paramIVideoPluginEvent.getArgs();
      int i = paramIVideoPluginEvent.getType();
      if (i != 200) {
        PatchAdLayout patchAdLayout1;
        if (i != 202) {
          if (i != 300) {
            if (i != 302) {
              switch (i) {
                default:
                  return super.handleVideoEvent(paramIVideoPluginEvent);
                case 309:
                  if (this.mPatchAdLayout != null && bundle != null) {
                    this.mVideoWidth = bundle.getInt("video_view_width");
                    this.mVideoHeight = bundle.getInt("video_view_height");
                    this.mPatchAdLayout.updateVideoSize(this.mVideoWidth, this.mVideoHeight);
                  } 
                case 308:
                  patchAdLayout3 = this.mPatchAdLayout;
                  if (patchAdLayout3 != null)
                    patchAdLayout3.onLeaveScreen(); 
                case 307:
                  patchAdLayout3 = this.mPatchAdLayout;
                  if (patchAdLayout3 != null)
                    patchAdLayout3.onEnterScreen(); 
                case 306:
                  patchAdLayout3 = this.mPatchAdLayout;
                  if (patchAdLayout3 != null)
                    patchAdLayout3.destroyPatchAd(); 
                case 305:
                  patchAdLayout3 = this.mPatchAdLayout;
                  if (patchAdLayout3 != null)
                    patchAdLayout3.pausePatchAd(); 
                case 304:
                  break;
              } 
              PatchAdLayout patchAdLayout3 = this.mPatchAdLayout;
              if (patchAdLayout3 != null)
                patchAdLayout3.resumePatchAd(); 
            } 
            PatchAdLayout patchAdLayout = this.mPatchAdLayout;
            if (patchAdLayout != null)
              patchAdLayout.showPostRollAd(); 
          } 
          patchAdLayout1 = this.mPatchAdLayout;
          if (patchAdLayout1 != null)
            patchAdLayout1.showPreRollAd(); 
        } 
        PatchAdLayout patchAdLayout2 = this.mPatchAdLayout;
        if (patchAdLayout2 != null)
          patchAdLayout2.setFullscreen(patchAdLayout1.getBoolean("fullscreen")); 
      } 
      if (!isViewReady()) {
        initView();
        return true;
      } 
    } 
  }
  
  protected void initView() {
    if (this.mPatchAdLayout == null) {
      this.mPatchAdLayout = new PatchAdLayout(this.mCallback);
      this.mPatchAdLayout.initView(getContext(), getPluginMainContainer());
      this.mPatchAdLayout.updateVideoSize(this.mVideoWidth, this.mVideoHeight);
      this.mPatchAdLayout.setUIListener(new PatchAdLayout.UIListener() {
            public void onFullscreenChanged(boolean param1Boolean1, boolean param1Boolean2) {
              PatchAdCommand patchAdCommand = new PatchAdCommand(3005, param1Boolean1);
              patchAdCommand.isFullScreen = param1Boolean2;
              PatchAdPlugin.this.getHost().execCommand(patchAdCommand);
            }
            
            public void onVideoAdClose(boolean param1Boolean) {
              PatchAdCommand patchAdCommand = new PatchAdCommand(3003, param1Boolean);
              PatchAdPlugin.this.getHost().execCommand(patchAdCommand);
            }
            
            public void onVideoAdEnded(boolean param1Boolean) {
              PatchAdCommand patchAdCommand = new PatchAdCommand(3002, param1Boolean);
              PatchAdPlugin.this.getHost().execCommand(patchAdCommand);
            }
            
            public void onVideoAdError(boolean param1Boolean, int param1Int, String param1String) {
              PatchAdCommand patchAdCommand = new PatchAdCommand(3004, param1Boolean);
              patchAdCommand.code = param1Int;
              patchAdCommand.message = param1String;
              PatchAdPlugin.this.getHost().execCommand(patchAdCommand);
            }
            
            public void onVideoAdLoaded(boolean param1Boolean) {
              PatchAdCommand patchAdCommand = new PatchAdCommand(3000, param1Boolean);
              PatchAdPlugin.this.getHost().execCommand(patchAdCommand);
            }
            
            public void onVideoAdStart(boolean param1Boolean) {
              PatchAdCommand patchAdCommand = new PatchAdCommand(3001, param1Boolean);
              PatchAdPlugin.this.getHost().execCommand(patchAdCommand);
            }
          });
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\patchad\PatchAdPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */