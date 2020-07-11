package com.tt.miniapp.video.plugin.feature.poster;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;

public class PosterLayout {
  public boolean mHidePosterPlay;
  
  private boolean mIsFullscreen;
  
  private boolean mLoading = true;
  
  private String mObjectFit;
  
  private boolean mPostPlayEnableShow;
  
  private ImageView mPosterImageIv;
  
  private ImageView mPosterPlayIv;
  
  private String mPosterUrl;
  
  protected View mRootView;
  
  public PosterUIListener mUIListener;
  
  private void updatePostPlayVisible() {
    if (this.mLoading || !this.mPostPlayEnableShow || this.mHidePosterPlay) {
      UIUtils.setViewVisibility((View)this.mPosterPlayIv, 4);
      return;
    } 
    UIUtils.setViewVisibility((View)this.mPosterPlayIv, 0);
  }
  
  public void initView(Context paramContext, ViewGroup paramViewGroup) {
    if (paramContext != null) {
      if (paramViewGroup == null)
        return; 
      LayoutInflater.from(paramContext).inflate(2097676338, paramViewGroup, true);
      this.mRootView = paramViewGroup.findViewById(2097545499);
      this.mPosterImageIv = (ImageView)this.mRootView.findViewById(2097545446);
      this.mPosterPlayIv = (ImageView)this.mRootView.findViewById(2097545447);
      this.mPosterPlayIv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              if (PosterLayout.this.mUIListener != null)
                PosterLayout.this.mUIListener.onPlayClick(null); 
            }
          });
    } 
  }
  
  void reset() {
    if (this.mHidePosterPlay) {
      this.mHidePosterPlay = false;
      updatePostPlayVisible();
    } 
  }
  
  public void setBackgroundUrl(String paramString) {
    if (TextUtils.isEmpty(paramString)) {
      UIUtils.setViewVisibility((View)this.mPosterImageIv, 4);
      return;
    } 
    UIUtils.setViewVisibility((View)this.mPosterImageIv, 0);
    if (!paramString.equals(this.mPosterUrl)) {
      this.mPosterUrl = paramString;
      HostDependManager.getInst().loadImage(this.mRootView.getContext(), this.mPosterImageIv, Uri.parse(this.mPosterUrl));
    } 
  }
  
  public void setFullscreen(boolean paramBoolean) {
    if (this.mIsFullscreen == paramBoolean)
      return; 
    this.mIsFullscreen = paramBoolean;
    if (this.mPosterPlayIv.getVisibility() == 0) {
      int i;
      ImageView imageView = this.mPosterPlayIv;
      if (this.mIsFullscreen) {
        i = 2097479768;
      } else {
        i = 2097479771;
      } 
      imageView.setImageResource(i);
    } 
  }
  
  public void setLoading(boolean paramBoolean) {
    this.mLoading = paramBoolean;
    updatePostPlayVisible();
  }
  
  public void setObjectFit(String paramString) {
    if (!TextUtils.equals(this.mObjectFit, paramString)) {
      if (paramString == null)
        return; 
      this.mObjectFit = paramString;
      byte b = -1;
      int i = paramString.hashCode();
      if (i != 3143043) {
        if (i == 94852023 && paramString.equals("cover"))
          b = 0; 
      } else if (paramString.equals("fill")) {
        b = 1;
      } 
      if (b != 0) {
        if (b != 1) {
          this.mPosterImageIv.setScaleType(ImageView.ScaleType.FIT_CENTER);
          return;
        } 
        this.mPosterImageIv.setScaleType(ImageView.ScaleType.FIT_XY);
        return;
      } 
      this.mPosterImageIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
    } 
  }
  
  public void setPostPlayEnableShow(boolean paramBoolean) {
    this.mPostPlayEnableShow = paramBoolean;
    updatePostPlayVisible();
  }
  
  public void setUIListener(PosterUIListener paramPosterUIListener) {
    this.mUIListener = paramPosterUIListener;
  }
  
  public void setVisible(boolean paramBoolean) {
    byte b;
    View view = this.mRootView;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 4;
    } 
    UIUtils.setViewVisibility(view, b);
    if (!paramBoolean) {
      this.mHidePosterPlay = true;
      updatePostPlayVisible();
    } 
  }
  
  static interface PosterUIListener {
    void onPlayClick(View param1View);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\poster\PosterLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */