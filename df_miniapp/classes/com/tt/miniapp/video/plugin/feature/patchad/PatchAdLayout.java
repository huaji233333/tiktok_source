package com.tt.miniapp.video.plugin.feature.patchad;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.video.patchad.PatchAdVideoCallback;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.ad.k;

public class PatchAdLayout {
  public k mAdView;
  
  public PatchAdVideoCallback mCallback;
  
  private int mFullScreenHeight;
  
  private int mFullScreenWidth;
  
  public boolean mIsFullscreen;
  
  private int mNormalHeight;
  
  private int mNormalWidth;
  
  private ViewGroup mRootView;
  
  public UIListener mUIListener;
  
  private int mUpdateAdDelay = 260;
  
  public PatchAdLayout(PatchAdVideoCallback paramPatchAdVideoCallback) {
    this.mCallback = paramPatchAdVideoCallback;
  }
  
  private void showPatchAd(final boolean isPreRollAd) {
    destroyPatchAd();
    this.mAdView = this.mCallback.getPatchAdManager().a(new VideoPatchAdViewCallback(isPreRollAd));
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
    layoutParams.addRule(15);
    this.mRootView.addView(this.mAdView.a(), (ViewGroup.LayoutParams)layoutParams);
    new Object() {
        public void onFailure(int param1Int, String param1String) {
          if (PatchAdLayout.this.mUIListener != null)
            PatchAdLayout.this.mUIListener.onVideoAdError(isPreRollAd, param1Int, param1String); 
        }
        
        public void onSuccess(int param1Int1, int param1Int2) {
          if (PatchAdLayout.this.mUIListener != null)
            PatchAdLayout.this.mUIListener.onVideoAdLoaded(isPreRollAd); 
          PatchAdLayout.this.updateAdViewLayout();
          PatchAdLayout.this.setVisible(true);
        }
      };
  }
  
  public void destroyPatchAd() {
    if (this.mAdView != null)
      this.mAdView = null; 
    setVisible(false);
    this.mRootView.removeAllViews();
  }
  
  public void initView(Context paramContext, ViewGroup paramViewGroup) {
    if (paramContext != null) {
      if (paramViewGroup == null)
        return; 
      int i = DevicesUtil.getScreenWidth(paramContext);
      int j = DevicesUtil.getScreenHight(paramContext);
      this.mFullScreenWidth = Math.max(i, j);
      this.mFullScreenHeight = Math.min(i, j);
      LayoutInflater.from(paramContext).inflate(2097676337, paramViewGroup, true);
      this.mRootView = (ViewGroup)paramViewGroup.findViewById(2097545443);
      Resources resources = paramContext.getResources();
      if (resources != null)
        this.mUpdateAdDelay = resources.getInteger(17694720); 
    } 
  }
  
  public void onEnterScreen() {}
  
  public void onFullScreenChangeInner() {
    View view;
    k k1 = this.mAdView;
    if (k1 == null) {
      k1 = null;
    } else {
      view = k1.a();
    } 
    if (view != null) {
      if (!this.mRootView.isEnabled())
        return; 
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              PatchAdLayout.this.updateAdViewLayout();
            }
          },  this.mUpdateAdDelay);
    } 
  }
  
  public void onLeaveScreen() {}
  
  public void pausePatchAd() {}
  
  public void resumePatchAd() {}
  
  public void setFullscreen(boolean paramBoolean) {
    if (this.mIsFullscreen == paramBoolean)
      return; 
    this.mIsFullscreen = paramBoolean;
    AppBrandLogger.d("PAdLayout", new Object[] { "onFullScreenChangeInner: from SET" });
    onFullScreenChangeInner();
  }
  
  public void setUIListener(UIListener paramUIListener) {
    this.mUIListener = paramUIListener;
  }
  
  public void setVisible(boolean paramBoolean) {
    byte b;
    ViewGroup viewGroup = this.mRootView;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 4;
    } 
    UIUtils.setViewVisibility((View)viewGroup, b);
    if (paramBoolean) {
      this.mRootView.setBackgroundColor(-16777216);
      this.mRootView.setEnabled(true);
      return;
    } 
    this.mRootView.setBackgroundColor(0);
    this.mRootView.setEnabled(false);
  }
  
  public void showPostRollAd() {
    showPatchAd(false);
  }
  
  public void showPreRollAd() {
    showPatchAd(true);
  }
  
  public void updateAdViewLayout() {
    int i;
    int j;
    if (this.mAdView == null)
      return; 
    if (this.mIsFullscreen) {
      i = this.mFullScreenWidth;
      j = this.mFullScreenHeight;
    } else {
      i = this.mNormalWidth;
      j = this.mNormalHeight;
    } 
    if (i <= 0)
      return; 
    StringBuilder stringBuilder = new StringBuilder("relayout: (");
    stringBuilder.append(i);
    stringBuilder.append("/");
    stringBuilder.append(j);
    stringBuilder.append(")");
    AppBrandLogger.d("PAdLayout", new Object[] { stringBuilder.toString() });
  }
  
  void updateVideoSize(int paramInt1, int paramInt2) {
    this.mNormalWidth = paramInt1;
    this.mNormalHeight = paramInt2;
    StringBuilder stringBuilder = new StringBuilder("updateVideoSize: ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(",");
    stringBuilder.append(paramInt2);
    AppBrandLogger.d("PAdLayout", new Object[] { stringBuilder.toString() });
    updateAdViewLayout();
  }
  
  public static interface UIListener {
    void onFullscreenChanged(boolean param1Boolean1, boolean param1Boolean2);
    
    void onVideoAdClose(boolean param1Boolean);
    
    void onVideoAdEnded(boolean param1Boolean);
    
    void onVideoAdError(boolean param1Boolean, int param1Int, String param1String);
    
    void onVideoAdLoaded(boolean param1Boolean);
    
    void onVideoAdStart(boolean param1Boolean);
  }
  
  class VideoPatchAdViewCallback implements k.a {
    private boolean isPreRollAd;
    
    public VideoPatchAdViewCallback(boolean param1Boolean) {
      this.isPreRollAd = param1Boolean;
    }
    
    public String getAdUnitId() {
      return this.isPreRollAd ? PatchAdLayout.this.mCallback.getPreRollAdUnitId() : PatchAdLayout.this.mCallback.getPostRollAdUnitId();
    }
    
    public boolean isFullscreen() {
      return PatchAdLayout.this.mIsFullscreen;
    }
    
    public boolean isPreRollAd() {
      return this.isPreRollAd;
    }
    
    public void onFullscreenChanged(boolean param1Boolean) {
      PatchAdLayout patchAdLayout = PatchAdLayout.this;
      patchAdLayout.mIsFullscreen = param1Boolean;
      if (patchAdLayout.mUIListener != null)
        PatchAdLayout.this.mUIListener.onFullscreenChanged(this.isPreRollAd, param1Boolean); 
      AppBrandLogger.d("PAdLayout", new Object[] { "onFullScreenChangeInner: from CALLBACK" });
      PatchAdLayout.this.onFullScreenChangeInner();
    }
    
    public void onVideoAdClose() {
      if (PatchAdLayout.this.mUIListener != null)
        PatchAdLayout.this.mUIListener.onVideoAdClose(this.isPreRollAd); 
    }
    
    public void onVideoAdEnded() {
      if (PatchAdLayout.this.mUIListener != null)
        PatchAdLayout.this.mUIListener.onVideoAdEnded(this.isPreRollAd); 
    }
    
    public void onVideoAdError(int param1Int, String param1String) {
      if (PatchAdLayout.this.mUIListener != null)
        PatchAdLayout.this.mUIListener.onVideoAdError(this.isPreRollAd, param1Int, param1String); 
    }
    
    public void onVideoAdStart() {
      if (PatchAdLayout.this.mUIListener != null)
        PatchAdLayout.this.mUIListener.onVideoAdStart(this.isPreRollAd); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\patchad\PatchAdLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */