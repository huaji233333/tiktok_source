package com.facebook.react.views.scroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.u;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import com.facebook.i.a.a;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.views.view.ReactViewBackgroundManager;

public class ReactHorizontalScrollView extends HorizontalScrollView implements ReactClippingViewGroup {
  public boolean mActivelyScrolling;
  
  private Rect mClippingRect;
  
  private boolean mDragging;
  
  private Drawable mEndBackground;
  
  private int mEndFillColor = 0;
  
  private FpsListener mFpsListener = null;
  
  private final OnScrollDispatchHelper mOnScrollDispatchHelper = new OnScrollDispatchHelper();
  
  public boolean mPagingEnabled = false;
  
  public Runnable mPostTouchRunnable;
  
  private ReactViewBackgroundManager mReactBackgroundManager = new ReactViewBackgroundManager((View)this);
  
  private boolean mRemoveClippedSubviews;
  
  private boolean mScrollEnabled = true;
  
  private String mScrollPerfTag;
  
  public boolean mSendMomentumEvents;
  
  private int mSnapInterval = 0;
  
  private final VelocityHelper mVelocityHelper = new VelocityHelper();
  
  public ReactHorizontalScrollView(Context paramContext) {
    this(paramContext, (FpsListener)null);
  }
  
  public ReactHorizontalScrollView(Context paramContext, FpsListener paramFpsListener) {
    super(paramContext);
    this.mFpsListener = paramFpsListener;
  }
  
  private void enableFpsListener() {
    if (isScrollPerfLoggingEnabled()) {
      a.b(this.mFpsListener);
      a.b(this.mScrollPerfTag);
      this.mFpsListener.enable(this.mScrollPerfTag);
    } 
  }
  
  private int getSnapInterval() {
    int i = this.mSnapInterval;
    return (i != 0) ? i : getWidth();
  }
  
  private void handlePostTouchScrolling(int paramInt1, int paramInt2) {
    if (!this.mSendMomentumEvents && !this.mPagingEnabled && !isScrollPerfLoggingEnabled())
      return; 
    if (this.mPostTouchRunnable != null)
      return; 
    if (this.mSendMomentumEvents)
      ReactScrollViewHelper.emitScrollMomentumBeginEvent((ViewGroup)this, paramInt1, paramInt2); 
    this.mActivelyScrolling = false;
    this.mPostTouchRunnable = new Runnable() {
        private boolean mSnappingToPage;
        
        public void run() {
          if (ReactHorizontalScrollView.this.mActivelyScrolling) {
            ReactHorizontalScrollView reactHorizontalScrollView1 = ReactHorizontalScrollView.this;
            reactHorizontalScrollView1.mActivelyScrolling = false;
            u.a((View)reactHorizontalScrollView1, this, 20L);
            return;
          } 
          if (ReactHorizontalScrollView.this.mPagingEnabled && !this.mSnappingToPage) {
            this.mSnappingToPage = true;
            ReactHorizontalScrollView.this.smoothScrollToPage(0);
            u.a((View)ReactHorizontalScrollView.this, this, 20L);
            return;
          } 
          if (ReactHorizontalScrollView.this.mSendMomentumEvents)
            ReactScrollViewHelper.emitScrollMomentumEndEvent((ViewGroup)ReactHorizontalScrollView.this); 
          ReactHorizontalScrollView reactHorizontalScrollView = ReactHorizontalScrollView.this;
          reactHorizontalScrollView.mPostTouchRunnable = null;
          reactHorizontalScrollView.disableFpsListener();
        }
      };
    u.a((View)this, this.mPostTouchRunnable, 20L);
  }
  
  private boolean isScrollPerfLoggingEnabled() {
    if (this.mFpsListener != null) {
      String str = this.mScrollPerfTag;
      if (str != null && !str.isEmpty())
        return true; 
    } 
    return false;
  }
  
  public void disableFpsListener() {
    if (isScrollPerfLoggingEnabled()) {
      a.b(this.mFpsListener);
      a.b(this.mScrollPerfTag);
      this.mFpsListener.disable(this.mScrollPerfTag);
    } 
  }
  
  public void draw(Canvas paramCanvas) {
    if (this.mEndFillColor != 0) {
      View view = getChildAt(0);
      if (this.mEndBackground != null && view != null && view.getRight() < getWidth()) {
        this.mEndBackground.setBounds(view.getRight(), 0, getWidth(), getHeight());
        this.mEndBackground.draw(paramCanvas);
      } 
    } 
    super.draw(paramCanvas);
  }
  
  public void flashScrollIndicators() {
    awakenScrollBars();
  }
  
  public void fling(int paramInt) {
    if (this.mPagingEnabled) {
      smoothScrollToPage(paramInt);
    } else {
      super.fling(paramInt);
    } 
    handlePostTouchScrolling(paramInt, 0);
  }
  
  public void getClippingRect(Rect paramRect) {
    paramRect.set((Rect)a.b(this.mClippingRect));
  }
  
  public boolean getRemoveClippedSubviews() {
    return this.mRemoveClippedSubviews;
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.mRemoveClippedSubviews)
      updateClippingRect(); 
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    if (!this.mScrollEnabled)
      return false; 
    try {
      if (super.onInterceptTouchEvent(paramMotionEvent)) {
        NativeGestureUtil.notifyNativeGestureStarted((View)this, paramMotionEvent);
        ReactScrollViewHelper.emitScrollBeginDragEvent((ViewGroup)this);
        this.mDragging = true;
        enableFpsListener();
        return true;
      } 
      return false;
    } catch (IllegalArgumentException illegalArgumentException) {
      return false;
    } 
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    scrollTo(getScrollX(), getScrollY());
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    MeasureSpecAssertions.assertExplicitMeasureSpec(paramInt1, paramInt2);
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mActivelyScrolling = true;
    if (this.mOnScrollDispatchHelper.onScrollChanged(paramInt1, paramInt2)) {
      if (this.mRemoveClippedSubviews)
        updateClippingRect(); 
      ReactScrollViewHelper.emitScrollEvent((ViewGroup)this, this.mOnScrollDispatchHelper.getXFlingVelocity(), this.mOnScrollDispatchHelper.getYFlingVelocity());
    } 
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mRemoveClippedSubviews)
      updateClippingRect(); 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (!this.mScrollEnabled)
      return false; 
    this.mVelocityHelper.calculateVelocity(paramMotionEvent);
    if ((paramMotionEvent.getAction() & 0xFF) == 1 && this.mDragging) {
      float f1 = this.mVelocityHelper.getXVelocity();
      float f2 = this.mVelocityHelper.getYVelocity();
      ReactScrollViewHelper.emitScrollEndDragEvent((ViewGroup)this, f1, f2);
      this.mDragging = false;
      handlePostTouchScrolling(Math.round(f1), Math.round(f2));
    } 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setBackgroundColor(int paramInt) {
    this.mReactBackgroundManager.setBackgroundColor(paramInt);
  }
  
  public void setBorderColor(int paramInt, float paramFloat1, float paramFloat2) {
    this.mReactBackgroundManager.setBorderColor(paramInt, paramFloat1, paramFloat2);
  }
  
  public void setBorderRadius(float paramFloat) {
    this.mReactBackgroundManager.setBorderRadius(paramFloat);
  }
  
  public void setBorderRadius(float paramFloat, int paramInt) {
    this.mReactBackgroundManager.setBorderRadius(paramFloat, paramInt);
  }
  
  public void setBorderStyle(String paramString) {
    this.mReactBackgroundManager.setBorderStyle(paramString);
  }
  
  public void setBorderWidth(int paramInt, float paramFloat) {
    this.mReactBackgroundManager.setBorderWidth(paramInt, paramFloat);
  }
  
  public void setEndFillColor(int paramInt) {
    if (paramInt != this.mEndFillColor) {
      this.mEndFillColor = paramInt;
      this.mEndBackground = (Drawable)new ColorDrawable(this.mEndFillColor);
    } 
  }
  
  public void setPagingEnabled(boolean paramBoolean) {
    this.mPagingEnabled = paramBoolean;
  }
  
  public void setRemoveClippedSubviews(boolean paramBoolean) {
    if (paramBoolean && this.mClippingRect == null)
      this.mClippingRect = new Rect(); 
    this.mRemoveClippedSubviews = paramBoolean;
    updateClippingRect();
  }
  
  public void setScrollEnabled(boolean paramBoolean) {
    this.mScrollEnabled = paramBoolean;
  }
  
  public void setScrollPerfTag(String paramString) {
    this.mScrollPerfTag = paramString;
  }
  
  public void setSendMomentumEvents(boolean paramBoolean) {
    this.mSendMomentumEvents = paramBoolean;
  }
  
  public void setSnapInterval(int paramInt) {
    this.mSnapInterval = paramInt;
  }
  
  public void smoothScrollToPage(int paramInt) {
    int k = getSnapInterval();
    int m = getScrollX();
    int j = m / k;
    int i = j;
    if (paramInt + m > j * k + k / 2)
      i = j + 1; 
    smoothScrollTo(i * k, getScrollY());
  }
  
  public void updateClippingRect() {
    if (!this.mRemoveClippedSubviews)
      return; 
    a.b(this.mClippingRect);
    ReactClippingViewGroupHelper.calculateClippingRect((View)this, this.mClippingRect);
    View view = getChildAt(0);
    if (view instanceof ReactClippingViewGroup)
      ((ReactClippingViewGroup)view).updateClippingRect(); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\ReactHorizontalScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */