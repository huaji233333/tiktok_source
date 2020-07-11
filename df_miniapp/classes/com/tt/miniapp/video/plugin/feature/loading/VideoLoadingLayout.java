package com.tt.miniapp.video.plugin.feature.loading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tt.miniapp.video.view.widget.RotateImageView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;

public class VideoLoadingLayout {
  private boolean mIsFullscreen;
  
  private RotateImageView mLoadingLayout;
  
  private TextView mRetryBtn;
  
  private TextView mRetryTips;
  
  private View mRetryView;
  
  private View mRootView;
  
  public LoadingUIListener mUIListener;
  
  private void updateLoadingLayout(int paramInt1, int paramInt2) {
    RotateImageView rotateImageView = this.mLoadingLayout;
    if (rotateImageView == null)
      return; 
    Context context = rotateImageView.getContext();
    ViewGroup.LayoutParams layoutParams = this.mLoadingLayout.getLayoutParams();
    if (layoutParams != null) {
      float f = paramInt1;
      layoutParams.width = (int)UIUtils.dip2Px(context, f);
      layoutParams.height = (int)UIUtils.dip2Px(context, f);
      this.mLoadingLayout.setLayoutParams(layoutParams);
    } 
    this.mLoadingLayout.setImageResource(paramInt2);
  }
  
  private void updateRetryButton(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    TextView textView = this.mRetryBtn;
    if (textView == null)
      return; 
    Context context = textView.getContext();
    ViewGroup.LayoutParams layoutParams = this.mRetryBtn.getLayoutParams();
    if (layoutParams != null) {
      layoutParams.width = (int)UIUtils.dip2Px(context, paramInt1);
      layoutParams.height = (int)UIUtils.dip2Px(context, paramInt2);
      this.mRetryBtn.setLayoutParams(layoutParams);
    } 
    this.mRetryBtn.setTextSize(2, paramInt3);
    this.mRetryBtn.setBackgroundResource(paramInt4);
  }
  
  private void updateRetryTips(int paramInt1, int paramInt2) {
    TextView textView = this.mRetryTips;
    if (textView == null)
      return; 
    Context context = textView.getContext();
    this.mRetryTips.setTextSize(2, paramInt1);
    ViewGroup.LayoutParams layoutParams = this.mRetryTips.getLayoutParams();
    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
      ((ViewGroup.MarginLayoutParams)layoutParams).bottomMargin = (int)UIUtils.dip2Px(context, paramInt2);
      this.mRetryTips.setLayoutParams(layoutParams);
    } 
  }
  
  public void initView(Context paramContext, ViewGroup paramViewGroup) {
    if (paramContext != null) {
      if (paramViewGroup == null)
        return; 
      LayoutInflater.from(paramContext).inflate(2097676336, paramViewGroup, true);
      this.mRootView = paramViewGroup.findViewById(2097545439);
      this.mLoadingLayout = (RotateImageView)this.mRootView.findViewById(2097545440);
      this.mRetryView = this.mRootView.findViewById(2097545441);
      this.mRetryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
              if (VideoLoadingLayout.this.mUIListener != null)
                VideoLoadingLayout.this.mUIListener.onRetryClick(); 
            }
          });
      this.mRetryTips = (TextView)this.mRootView.findViewById(2097545449);
      this.mRetryBtn = (TextView)this.mRootView.findViewById(2097545448);
      updateUi();
    } 
  }
  
  public void setFullscreen(boolean paramBoolean) {
    this.mIsFullscreen = paramBoolean;
    updateUi();
  }
  
  public void setUIListener(LoadingUIListener paramLoadingUIListener) {
    this.mUIListener = paramLoadingUIListener;
  }
  
  public void showLoading(boolean paramBoolean) {
    byte b = 0;
    AppBrandLogger.d("tma_VideoLoadingLayout", new Object[] { "showLoading ", Boolean.valueOf(paramBoolean) });
    UIUtils.setViewVisibility(this.mRetryView, 4);
    if (paramBoolean) {
      UIUtils.setViewVisibility((View)this.mLoadingLayout, 0);
      RotateImageView rotateImageView = this.mLoadingLayout;
      if (rotateImageView != null)
        rotateImageView.startAnimation(); 
    } else {
      UIUtils.setViewVisibility((View)this.mLoadingLayout, 4);
      RotateImageView rotateImageView = this.mLoadingLayout;
      if (rotateImageView != null)
        rotateImageView.stopAnimation(); 
    } 
    View view = this.mRootView;
    if (!paramBoolean)
      b = 4; 
    UIUtils.setViewVisibility(view, b);
  }
  
  public void showRetry(boolean paramBoolean) {
    byte b = 0;
    AppBrandLogger.d("tma_VideoLoadingLayout", new Object[] { "showRetry ", Boolean.valueOf(paramBoolean) });
    UIUtils.setViewVisibility((View)this.mLoadingLayout, 4);
    if (paramBoolean) {
      UIUtils.setViewVisibility(this.mRetryView, 0);
    } else {
      UIUtils.setViewVisibility(this.mRetryView, 4);
    } 
    View view = this.mRootView;
    if (!paramBoolean)
      b = 4; 
    UIUtils.setViewVisibility(view, b);
  }
  
  public void updateUi() {
    if (this.mIsFullscreen) {
      updateLoadingLayout(60, 2097479810);
      updateRetryTips(18, 18);
      updateRetryButton(108, 42, 18, 2097479774);
      return;
    } 
    updateLoadingLayout(44, 2097479809);
    updateRetryTips(14, 14);
    updateRetryButton(84, 32, 14, 2097479773);
  }
  
  static interface LoadingUIListener {
    void onRetryClick();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\feature\loading\VideoLoadingLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */