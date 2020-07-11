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
import android.widget.OverScroller;
import android.widget.ScrollView;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.views.view.ReactViewBackgroundManager;
import java.lang.reflect.Field;

public class ReactScrollView extends ScrollView implements View.OnLayoutChangeListener, ViewGroup.OnHierarchyChangeListener, ReactClippingViewGroup {
  private static Field sScrollerField;
  
  private static boolean sTriedToGetScrollerField;
  
  private Rect mClippingRect;
  
  private View mContentView;
  
  public boolean mDoneFlinging;
  
  private boolean mDragging;
  
  private Drawable mEndBackground;
  
  private int mEndFillColor = 0;
  
  public boolean mFlinging;
  
  private FpsListener mFpsListener = null;
  
  private final OnScrollDispatchHelper mOnScrollDispatchHelper = new OnScrollDispatchHelper();
  
  private ReactViewBackgroundManager mReactBackgroundManager;
  
  private boolean mRemoveClippedSubviews;
  
  private boolean mScrollEnabled = true;
  
  private String mScrollPerfTag;
  
  private final OverScroller mScroller;
  
  private boolean mSendMomentumEvents;
  
  private final VelocityHelper mVelocityHelper = new VelocityHelper();
  
  public ReactScrollView(ReactContext paramReactContext) {
    this(paramReactContext, (FpsListener)null);
  }
  
  public ReactScrollView(ReactContext paramReactContext, FpsListener paramFpsListener) {
    super((Context)paramReactContext);
    this.mFpsListener = paramFpsListener;
    this.mReactBackgroundManager = new ReactViewBackgroundManager((View)this);
    this.mScroller = getOverScrollerFromParent();
    setOnHierarchyChangeListener(this);
    setScrollBarStyle(33554432);
  }
  
  private void enableFpsListener() {
    if (isScrollPerfLoggingEnabled()) {
      a.b(this.mFpsListener);
      a.b(this.mScrollPerfTag);
      this.mFpsListener.enable(this.mScrollPerfTag);
    } 
  }
  
  private int getMaxScrollY() {
    return Math.max(0, this.mContentView.getHeight() - getHeight() - getPaddingBottom() - getPaddingTop());
  }
  
  private OverScroller getOverScrollerFromParent() {
    if (!sTriedToGetScrollerField) {
      sTriedToGetScrollerField = true;
      try {
        Field field1 = ScrollView.class.getDeclaredField("mScroller");
        sScrollerField = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {}
    } 
    Field field = sScrollerField;
    if (field != null)
      try {
        Object object = field.get(this);
        if (object instanceof OverScroller)
          return (OverScroller)object; 
      } catch (IllegalAccessException illegalAccessException) {
        throw new RuntimeException("Failed to get mScroller from ScrollView!", illegalAccessException);
      }  
    return null;
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
      if (this.mEndBackground != null && view != null && view.getBottom() < getHeight()) {
        this.mEndBackground.setBounds(0, view.getBottom(), getWidth(), getHeight());
        this.mEndBackground.draw(paramCanvas);
      } 
    } 
    super.draw(paramCanvas);
  }
  
  public void flashScrollIndicators() {
    awakenScrollBars();
  }
  
  public void fling(int paramInt) {
    if (this.mScroller != null) {
      int i = getHeight();
      int j = getPaddingBottom();
      int k = getPaddingTop();
      paramInt = (int)(Math.abs(paramInt) * Math.signum(this.mOnScrollDispatchHelper.getYFlingVelocity()));
      this.mScroller.fling(getScrollX(), getScrollY(), 0, paramInt, 0, 0, 0, 2147483647, 0, (i - j - k) / 2);
      u.d((View)this);
    } else {
      super.fling(paramInt);
    } 
    if (this.mSendMomentumEvents || isScrollPerfLoggingEnabled()) {
      this.mFlinging = true;
      enableFpsListener();
      ReactScrollViewHelper.emitScrollMomentumBeginEvent((ViewGroup)this, 0, paramInt);
      u.a((View)this, new Runnable() {
            public void run() {
              if (ReactScrollView.this.mDoneFlinging) {
                ReactScrollView reactScrollView1 = ReactScrollView.this;
                reactScrollView1.mFlinging = false;
                reactScrollView1.disableFpsListener();
                ReactScrollViewHelper.emitScrollMomentumEndEvent((ViewGroup)ReactScrollView.this);
                return;
              } 
              ReactScrollView reactScrollView = ReactScrollView.this;
              reactScrollView.mDoneFlinging = true;
              u.a((View)reactScrollView, this, 20L);
            }
          }20L);
    } 
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
  
  public void onChildViewAdded(View paramView1, View paramView2) {
    this.mContentView = paramView2;
    this.mContentView.addOnLayoutChangeListener(this);
  }
  
  public void onChildViewRemoved(View paramView1, View paramView2) {
    this.mContentView.removeOnLayoutChangeListener(this);
    this.mContentView = null;
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
  
  public void onLayoutChange(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
    if (this.mContentView == null)
      return; 
    paramInt1 = getScrollY();
    paramInt2 = getMaxScrollY();
    if (paramInt1 > paramInt2)
      scrollTo(getScrollX(), paramInt2); 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    MeasureSpecAssertions.assertExplicitMeasureSpec(paramInt1, paramInt2);
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
  }
  
  protected void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
    OverScroller overScroller = this.mScroller;
    int i = paramInt2;
    if (overScroller != null) {
      i = paramInt2;
      if (!overScroller.isFinished()) {
        i = paramInt2;
        if (this.mScroller.getCurrY() != this.mScroller.getFinalY()) {
          int j = getMaxScrollY();
          i = paramInt2;
          if (paramInt2 >= j) {
            this.mScroller.abortAnimation();
            i = j;
          } 
        } 
      } 
    } 
    super.onOverScrolled(paramInt1, i, paramBoolean1, paramBoolean2);
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mOnScrollDispatchHelper.onScrollChanged(paramInt1, paramInt2)) {
      if (this.mRemoveClippedSubviews)
        updateClippingRect(); 
      if (this.mFlinging)
        this.mDoneFlinging = false; 
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
      ReactScrollViewHelper.emitScrollEndDragEvent((ViewGroup)this, this.mVelocityHelper.getXVelocity(), this.mVelocityHelper.getYVelocity());
      this.mDragging = false;
      disableFpsListener();
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


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\ReactScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */