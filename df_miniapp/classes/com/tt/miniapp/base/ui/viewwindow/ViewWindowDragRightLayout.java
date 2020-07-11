package com.tt.miniapp.base.ui.viewwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;
import com.tt.miniapp.base.utils.DensityUtil;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.UIUtils;

public class ViewWindowDragRightLayout extends FrameLayout implements LanguageChangeListener {
  public OnDragListener draglistener;
  
  private View.OnClickListener emptyClickListener;
  
  private FlingRunnable flingR;
  
  private boolean inited;
  
  public boolean isNormalDirection;
  
  private View mBackgroundMaskView;
  
  private ViewGroup mContainer;
  
  private int mDownAreaMaxX = 100;
  
  private float mDownX;
  
  private float mDownY;
  
  private boolean mDragEnable;
  
  public volatile boolean mDragEnableGlobal = true;
  
  public int mDragRightCloseMinX = 100;
  
  private boolean mIsCurrentInDownArea;
  
  public int mMaxVelocityX = 3000;
  
  private int mMinimumVelocity;
  
  private boolean mRightMoving;
  
  private View mShadowView;
  
  private int mShadowViewWidth;
  
  public VelocityTracker mVelocityTracker;
  
  private int minXSlop;
  
  private int minYSlop;
  
  private final int units = 1000;
  
  public ViewWindowDragRightLayout(Context paramContext) {
    super(paramContext);
    this.mDragEnableGlobal = true;
    this.isNormalDirection = true ^ UIUtils.isRTL();
    UIUtils.setProperLayoutDirection((View)this);
    LocaleManager.getInst().registerLangChangeListener(this);
  }
  
  private GradientDrawable genBackgroundMaskDrawable() {
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setColors(new int[] { 0, 1291845632 });
    gradientDrawable.setShape(0);
    if (this.isNormalDirection) {
      gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
      return gradientDrawable;
    } 
    gradientDrawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
    return gradientDrawable;
  }
  
  private void init() {
    if (!this.inited) {
      this.inited = true;
      ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
      this.mMinimumVelocity = (int)(viewConfiguration.getScaledMinimumFlingVelocity() * DensityUtil.getScreenDensity(getContext()));
      this.mMinimumVelocity = Math.max(this.mMinimumVelocity, 100);
      this.minXSlop = viewConfiguration.getScaledTouchSlop();
      this.minYSlop = this.minXSlop * 4;
      this.mShadowViewWidth = DensityUtil.dip2px(getContext(), 11.0F);
      this.mDownAreaMaxX = DensityUtil.dip2px(getContext(), 80.0F);
      this.mDragRightCloseMinX = this.mDownAreaMaxX;
    } 
  }
  
  private void processDown(MotionEvent paramMotionEvent) {
    this.mDownX = paramMotionEvent.getRawX();
    this.mDownY = paramMotionEvent.getY();
    if (!shouldDrag())
      return; 
    if (getParent() != null)
      this.mContainer = (ViewGroup)getParent(); 
    FlingRunnable flingRunnable = this.flingR;
    if (flingRunnable != null && !flingRunnable.isFinish())
      this.flingR.cancelFling(); 
    this.mVelocityTracker = VelocityTracker.obtain();
    this.mVelocityTracker.addMovement(paramMotionEvent);
  }
  
  private void processMove(MotionEvent paramMotionEvent) {
    if (shouldDrag()) {
      if (this.mContainer == null)
        return; 
      float f1 = paramMotionEvent.getRawX() - this.mDownX;
      float f2 = paramMotionEvent.getRawY();
      float f3 = this.mDownY;
      if (this.isNormalDirection) {
        if (f1 < 0.0F)
          return; 
      } else if (f1 > 0.0F) {
        return;
      } 
      if (!this.mRightMoving && (Math.abs(f1) < this.minXSlop || Math.abs(f2 - f3) > this.minYSlop))
        return; 
      setUpBackgroundMask();
      if (this.isNormalDirection) {
        if (f1 < 0.0F) {
          setDragFrameByLeft(0.0F);
        } else {
          setDragFrameByLeft(f1);
        } 
      } else if (f1 < -getWidth()) {
        setDragFrameByLeft(-getWidth());
      } else {
        setDragFrameByLeft(f1);
      } 
      if (!this.mRightMoving) {
        OnDragListener onDragListener = this.draglistener;
        if (onDragListener != null)
          onDragListener.onScrollStart(); 
      } 
      this.mRightMoving = true;
    } 
  }
  
  private void processUp(MotionEvent paramMotionEvent) {
    if (shouldDrag()) {
      if (this.mRightMoving) {
        this.flingR = new FlingRunnable(getContext());
        this.flingR.flingLeft(0, (int)getX());
        post(this.flingR);
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
          velocityTracker.recycle();
          this.mVelocityTracker = null;
        } 
      } 
      this.mRightMoving = false;
    } 
  }
  
  private void refreshSwitchByLang() {
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            boolean bool = UIUtils.isRTL();
            ViewWindowDragRightLayout viewWindowDragRightLayout = ViewWindowDragRightLayout.this;
            viewWindowDragRightLayout.isNormalDirection = bool ^ true;
            viewWindowDragRightLayout.mDragEnableGlobal = true;
            StringBuilder stringBuilder = new StringBuilder("onLanguageChange");
            stringBuilder.append(ViewWindowDragRightLayout.this.mDragEnableGlobal);
            AppBrandLogger.d("ViewWindowDragRightLayout", new Object[] { stringBuilder.toString() });
            UIUtils.setProperLayoutDirection((View)ViewWindowDragRightLayout.this);
            ViewWindowDragRightLayout.this.initIfNecessary();
          }
        });
  }
  
  private void setUpBackgroundMask() {
    if (this.mBackgroundMaskView == null) {
      this.mBackgroundMaskView = new View(getContext());
      this.mBackgroundMaskView.setBackgroundColor(Color.parseColor("#4f000000"));
      if (this.emptyClickListener != null)
        this.emptyClickListener = new View.OnClickListener() {
            public void onClick(View param1View) {}
          }; 
      this.mBackgroundMaskView.setOnClickListener(this.emptyClickListener);
      this.mShadowView = new View(getContext());
      this.mShadowView.setBackground((Drawable)genBackgroundMaskDrawable());
    } 
    if (this.mBackgroundMaskView.getParent() == null) {
      this.mContainer.addView(this.mBackgroundMaskView, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
      this.mContainer.addView(this.mShadowView, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(this.mShadowViewWidth, -1));
    } 
  }
  
  private boolean shouldDrag() {
    return (this.mDragEnable && this.mIsCurrentInDownArea && !shouldInterceptDrag() && this.mDragEnableGlobal);
  }
  
  protected void destroyDrag() {
    setDragEnable(false);
    setDragFinishListener((OnDragListener)null);
    releaseBackgroundMask();
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    boolean bool2 = true;
    boolean bool1 = true;
    if (i != 0) {
      if (i != 1)
        if (i != 2) {
          if (i != 3 && i != 4)
            return super.dispatchTouchEvent(paramMotionEvent); 
        } else {
          processMove(paramMotionEvent);
          return super.dispatchTouchEvent(paramMotionEvent);
        }  
      processUp(paramMotionEvent);
    } else {
      if (this.isNormalDirection) {
        if (paramMotionEvent.getRawX() >= this.mDownAreaMaxX)
          bool1 = false; 
        this.mIsCurrentInDownArea = bool1;
      } else {
        if (paramMotionEvent.getRawX() > (getWidth() - this.mDownAreaMaxX)) {
          bool1 = bool2;
        } else {
          bool1 = false;
        } 
        this.mIsCurrentInDownArea = bool1;
      } 
      processDown(paramMotionEvent);
    } 
    return super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public void initIfNecessary() {
    if (this.mDragEnable && this.mDragEnableGlobal)
      init(); 
  }
  
  public boolean isDragEnabled() {
    return (this.mDragEnable && this.mDragEnableGlobal);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    return this.mRightMoving ? true : super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  public void onLanguageChange() {
    refreshSwitchByLang();
  }
  
  public void postOnAnimationWrap(Runnable paramRunnable) {
    if (Build.VERSION.SDK_INT >= 16) {
      postOnAnimation(paramRunnable);
      return;
    } 
    postDelayed(paramRunnable, 16L);
  }
  
  public void releaseBackgroundMask() {
    if (this.mContainer != null) {
      View view = this.mBackgroundMaskView;
      if (view != null) {
        if (view.getParent() != null)
          this.mContainer.removeView(this.mBackgroundMaskView); 
        this.mBackgroundMaskView.setOnClickListener(null);
        if (this.mShadowView.getParent() != null)
          this.mContainer.removeView(this.mShadowView); 
        this.emptyClickListener = null;
        this.mBackgroundMaskView = null;
        this.mShadowView = null;
      } 
    } 
  }
  
  public void setDragEnable(boolean paramBoolean) {
    this.mDragEnable = paramBoolean;
    initIfNecessary();
  }
  
  public void setDragFinishListener(OnDragListener paramOnDragListener) {
    this.draglistener = paramOnDragListener;
  }
  
  public void setDragFrameByLeft(float paramFloat) {
    setX(paramFloat);
    if (this.isNormalDirection) {
      this.mShadowView.setX(paramFloat - this.mShadowViewWidth);
      this.mBackgroundMaskView.setX(paramFloat - getWidth());
      this.mBackgroundMaskView.setAlpha(1.0F - paramFloat / getWidth());
      return;
    } 
    this.mShadowView.setX(getWidth() + paramFloat);
    this.mBackgroundMaskView.setX(getWidth() + paramFloat);
    this.mBackgroundMaskView.setAlpha(1.0F - Math.abs(paramFloat) / getWidth());
  }
  
  protected boolean shouldInterceptDrag() {
    return false;
  }
  
  class FlingRunnable implements Runnable {
    private boolean disapper;
    
    private int disapperTime = 300;
    
    private boolean isHorizontal;
    
    private final Scroller mScroller;
    
    private int screenHeight;
    
    private int screenWidth;
    
    public FlingRunnable(Context param1Context) {
      this.mScroller = new Scroller(param1Context, (Interpolator)new DecelerateInterpolator());
      this.screenHeight = DensityUtil.getScreenHeight(param1Context);
      this.screenWidth = DensityUtil.getScreenWidth(param1Context);
    }
    
    public void cancelFling() {
      this.mScroller.forceFinished(true);
    }
    
    public void flingLeft(int param1Int1, int param1Int2) {
      boolean bool = true;
      this.isHorizontal = true;
      if (param1Int1 != param1Int2) {
        if (ViewWindowDragRightLayout.this.mVelocityTracker != null) {
          ViewWindowDragRightLayout.this.mVelocityTracker.computeCurrentVelocity(1000, ViewWindowDragRightLayout.this.mMaxVelocityX);
          int k = (int)ViewWindowDragRightLayout.this.mVelocityTracker.getXVelocity();
          int j = k;
          if (k > ViewWindowDragRightLayout.this.mMaxVelocityX)
            j = ViewWindowDragRightLayout.this.mMaxVelocityX; 
          if (j < 0) {
            this.disapperTime = 350;
          } else {
            this.disapperTime = 350 - j * 150 / ViewWindowDragRightLayout.this.mMaxVelocityX;
          } 
        } 
        if (Math.abs(param1Int2 - param1Int1) <= ViewWindowDragRightLayout.this.mDragRightCloseMinX)
          bool = false; 
        this.disapper = bool;
        if (this.disapper) {
          if (param1Int2 > param1Int1) {
            this.mScroller.startScroll(param1Int2, 0, this.screenWidth - param1Int2, 0, this.disapperTime);
            return;
          } 
          this.mScroller.startScroll(param1Int2, 0, -ViewWindowDragRightLayout.this.getWidth() - param1Int2, 0, this.disapperTime);
          return;
        } 
        param1Int1 -= param1Int2;
        int i = Math.min(Math.abs(param1Int1) * 1800 / this.screenWidth + 100, 400);
        this.mScroller.startScroll(param1Int2, 0, param1Int1, 0, i);
      } 
    }
    
    public boolean isFinish() {
      return this.mScroller.isFinished();
    }
    
    public void run() {
      try {
        if (ViewWindowDragRightLayout.this != null && this.mScroller.computeScrollOffset()) {
          if (this.isHorizontal) {
            int i = this.mScroller.getCurrX();
            ViewWindowDragRightLayout.this.setDragFrameByLeft(i);
          } 
          ViewWindowDragRightLayout.this.postOnAnimationWrap(this);
          return;
        } 
        ViewWindowDragRightLayout.this.releaseBackgroundMask();
        if (ViewWindowDragRightLayout.this.draglistener != null)
          ViewWindowDragRightLayout.this.draglistener.onScrollFinish(this.disapper); 
        return;
      } catch (Exception exception) {
        return;
      } 
    }
  }
  
  public static interface OnDragListener {
    void onScrollFinish(boolean param1Boolean);
    
    void onScrollStart();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\bas\\ui\viewwindow\ViewWindowDragRightLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */