package com.tt.miniapp.video.patchad;

import android.content.Context;
import android.util.AttributeSet;
import com.tt.miniapp.video.TTVideoView;
import com.tt.miniapp.video.core.PluginVideoController;
import com.tt.miniapp.video.plugin.base.IVideoPlugin;
import com.tt.miniapp.video.plugin.feature.patchad.PatchAdPlugin;

public abstract class PatchAdVideoView extends TTVideoView implements PatchAdVideoCallback {
  public PatchAdVideoView(Context paramContext) {
    super(paramContext);
  }
  
  public PatchAdVideoView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public PatchAdVideoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public PatchAdVideoController getVideoController() {
    return (PatchAdVideoController)super.getVideoController();
  }
  
  public void onInitVideoController() {
    super.onInitVideoController();
    getVideoController().addPlugin((IVideoPlugin)new PatchAdPlugin(this));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\patchad\PatchAdVideoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */