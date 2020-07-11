package com.tt.miniapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.miniapphost.util.UIUtils;

public class TitleBarProgressView extends FrameLayout {
  private boolean mDarkMode;
  
  private Drawable mDarkModeDrawable;
  
  private Drawable mLightModeDrawable;
  
  private boolean mLoading;
  
  private Animation mLoadingAnimation;
  
  private ImageView mLoadingView;
  
  private PaintFlagsDrawFilter mPaintFilter = new PaintFlagsDrawFilter(0, 3);
  
  private TextWatcher mTextWatcher = new TextWatcher() {
      public void afterTextChanged(Editable param1Editable) {
        TitleBarProgressView.this.computeOffset();
      }
      
      public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
        TitleBarProgressView.this.resetOffset();
      }
      
      public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
    };
  
  public TextView mTitleView;
  
  public TitleBarProgressView(Context paramContext) {
    super(paramContext, null);
    initView();
  }
  
  public TitleBarProgressView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet, 0);
    initView();
  }
  
  public TitleBarProgressView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt, 0);
    initView();
  }
  
  private Drawable getDarkModeRes() {
    if (this.mDarkModeDrawable == null)
      this.mDarkModeDrawable = getContext().getResources().getDrawable(2097479799); 
    return this.mDarkModeDrawable;
  }
  
  private Drawable getLightModeRes() {
    if (this.mLightModeDrawable == null)
      this.mLightModeDrawable = getContext().getResources().getDrawable(2097479800); 
    return this.mLightModeDrawable;
  }
  
  private void initView() {
    this.mLoadingView = new ImageView(getContext());
    this.mLoadingView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    int i = (int)UIUtils.dip2Px(getContext(), 16.0F);
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i);
    layoutParams.gravity = 16;
    layoutParams.topMargin = (int)UIUtils.dip2Px(getContext(), 0.5F);
    addView((View)this.mLoadingView, (ViewGroup.LayoutParams)layoutParams);
    setLayerType(1, null);
  }
  
  public void bindTitle(TextView paramTextView) {
    this.mTitleView = paramTextView;
  }
  
  public void computeOffset() {
    TextView textView = this.mTitleView;
    if (textView != null)
      textView.post(new Runnable() {
            public void run() {
              Layout layout = TitleBarProgressView.this.mTitleView.getLayout();
              if (layout == null)
                return; 
              if (layout.getEllipsisCount(layout.getLineCount() - 1) <= 0)
                return; 
              CharSequence charSequence = layout.getText();
              int i = Math.round(TitleBarProgressView.this.mTitleView.getPaint().measureText(charSequence.toString()));
              int j = TitleBarProgressView.this.mTitleView.getWidth();
              if (j > i) {
                i = (j - i) / 2;
                TitleBarProgressView.this.offset(i);
              } 
            }
          }); 
  }
  
  protected void dispatchDraw(Canvas paramCanvas) {
    super.dispatchDraw(paramCanvas);
    paramCanvas.setDrawFilter((DrawFilter)this.mPaintFilter);
  }
  
  public void hideLoading() {
    setVisibility(8);
    this.mLoading = false;
    this.mLoadingView.clearAnimation();
    this.mTitleView.removeTextChangedListener(this.mTextWatcher);
    resetOffset();
  }
  
  public boolean isDarkMode() {
    return this.mDarkMode;
  }
  
  public boolean isLoading() {
    return (this.mLoading && getVisibility() == 0);
  }
  
  public void offset(int paramInt) {
    if (this.mTitleView == null)
      return; 
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    if (layoutParams instanceof RelativeLayout.LayoutParams) {
      RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams)layoutParams;
      layoutParams1.rightMargin -= paramInt;
      setLayoutParams(layoutParams);
    } 
  }
  
  public void resetOffset() {
    if (this.mTitleView == null)
      return; 
    ViewGroup.LayoutParams layoutParams = getLayoutParams();
    if (layoutParams instanceof RelativeLayout.LayoutParams) {
      ((RelativeLayout.LayoutParams)layoutParams).rightMargin = (int)UIUtils.dip2Px(getContext(), 8.0F);
      setLayoutParams(layoutParams);
    } 
  }
  
  public void setDarkModeDrawable(Drawable paramDrawable) {
    this.mDarkModeDrawable = paramDrawable;
  }
  
  public void setLightModeDrawable(Drawable paramDrawable) {
    this.mLightModeDrawable = paramDrawable;
  }
  
  public void showLoading(boolean paramBoolean) {
    if (this.mLoading)
      return; 
    updateLoading(paramBoolean);
    if (this.mLoadingAnimation == null) {
      this.mLoadingAnimation = AnimationUtils.loadAnimation(getContext(), 2097217549);
      this.mLoadingAnimation.setInterpolator((Interpolator)new LinearInterpolator());
    } 
    this.mLoadingView.startAnimation(this.mLoadingAnimation);
    computeOffset();
    this.mTitleView.addTextChangedListener(this.mTextWatcher);
    this.mLoading = true;
    post(new Runnable() {
          public void run() {
            TitleBarProgressView.this.setVisibility(0);
          }
        });
  }
  
  public void updateLoading(boolean paramBoolean) {
    Drawable drawable;
    this.mDarkMode = paramBoolean;
    if (this.mDarkMode) {
      drawable = getDarkModeRes();
    } else {
      drawable = getLightModeRes();
    } 
    if (this.mLoadingView != null) {
      if (drawable instanceof BitmapDrawable) {
        ((BitmapDrawable)drawable).setAntiAlias(true);
        drawable.setFilterBitmap(true);
      } 
      this.mLoadingView.setImageDrawable(drawable);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\TitleBarProgressView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */