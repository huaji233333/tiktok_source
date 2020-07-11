package com.tt.miniapp.video.plugin.feature.poster;

import android.os.Bundle;
import android.view.View;
import com.tt.miniapp.video.base.ITTVideoController;
import com.tt.miniapp.video.plugin.base.BaseVideoPlugin;
import com.tt.miniapp.video.plugin.base.IVideoPluginEvent;
import com.tt.miniapphost.AppBrandLogger;

public class PosterPlugin extends BaseVideoPlugin {
  private PosterLayout mPosterLayout;
  
  private boolean isViewReady() {
    return (this.mPosterLayout != null);
  }
  
  public int getPluginType() {
    return 208;
  }
  
  public boolean handleVideoEvent(IVideoPluginEvent paramIVideoPluginEvent) {
    if (paramIVideoPluginEvent != null) {
      AppBrandLogger.d("tma_PosterPlugin", new Object[] { "handleVideoEvent ", Integer.valueOf(paramIVideoPluginEvent.getType()) });
      Bundle bundle = paramIVideoPluginEvent.getArgs();
      int i = paramIVideoPluginEvent.getType();
      PosterLayout posterLayout = null;
      if (i != 103)
        if (i != 200) {
          if (i != 208) {
            if (i != 108) {
              if (i != 109) {
                switch (i) {
                  default:
                    return super.handleVideoEvent(paramIVideoPluginEvent);
                  case 204:
                    posterLayout = this.mPosterLayout;
                    if (posterLayout != null)
                      posterLayout.reset(); 
                  case 203:
                    if (this.mPosterLayout != null) {
                      ITTVideoController.ShowStateEntity showStateEntity = (ITTVideoController.ShowStateEntity)bundle.getParcelable("showState");
                      if (showStateEntity != null) {
                        this.mPosterLayout.setObjectFit(showStateEntity.getObjectFit());
                        this.mPosterLayout.setPostPlayEnableShow(showStateEntity.isControls());
                      } 
                    } 
                  case 202:
                    break;
                } 
                posterLayout = this.mPosterLayout;
                if (posterLayout != null && bundle != null)
                  posterLayout.setFullscreen(bundle.getBoolean("fullscreen")); 
              } 
            } else {
              posterLayout = this.mPosterLayout;
              if (posterLayout != null)
                posterLayout.setLoading(false); 
            } 
          } else {
            PosterLayout posterLayout1 = this.mPosterLayout;
            if (posterLayout1 != null) {
              String str;
              posterLayout1.setVisible(true);
              posterLayout1 = this.mPosterLayout;
              if (bundle != null)
                str = bundle.getString("poster"); 
              posterLayout1.setBackgroundUrl(str);
            } 
          } 
        } else if (!isViewReady()) {
          initView();
          return true;
        }  
      posterLayout = this.mPosterLayout;
      if (posterLayout != null) {
        posterLayout.setVisible(false);
        this.mPosterLayout.setBackgroundUrl(null);
      } 
    } 
  }
  
  protected void initView() {
    if (this.mPosterLayout == null) {
      this.mPosterLayout = new PosterLayout();
      this.mPosterLayout.initView(getContext(), getPluginMainContainer());
      this.mPosterLayout.setUIListener(new PosterLayout.PosterUIListener() {
            public void onPlayClick(View param1View) {
              PosterPlugin.this.getHost().execCommand(2007);
            }
          });
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\poster\PosterPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */