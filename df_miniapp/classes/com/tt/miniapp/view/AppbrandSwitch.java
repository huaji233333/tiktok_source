package com.tt.miniapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.CompoundButton;

public class AppbrandSwitch extends CompoundButton {
  private static final int[] CHECKED_STATE_SET = new int[] { 16842912 };
  
  private int mMinFlingVelocity;
  
  private int mSwitchBottom;
  
  private int mSwitchHeight;
  
  private int mSwitchLeft;
  
  private int mSwitchMinWidth;
  
  private int mSwitchPadding;
  
  private int mSwitchRight;
  
  private int mSwitchTop;
  
  private int mSwitchWidth;
  
  private final Rect mTempRect = new Rect();
  
  private Drawable mThumbDrawable;
  
  private float mThumbPosition;
  
  private int mThumbWidth;
  
  private int mTouchMode;
  
  private int mTouchSlop;
  
  private float mTouchX;
  
  private float mTouchY;
  
  private Drawable mTrackDrawable;
  
  private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
  
  public AppbrandSwitch(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public AppbrandSwitch(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public AppbrandSwitch(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 2097283073, 2097283074, 2097283075, 2097283076 }, paramInt, 0);
    this.mThumbDrawable = typedArray.getDrawable(2);
    this.mTrackDrawable = typedArray.getDrawable(3);
    this.mSwitchMinWidth = typedArray.getDimensionPixelSize(0, 0);
    this.mSwitchPadding = typedArray.getDimensionPixelSize(1, 0);
    typedArray.recycle();
    ViewConfiguration viewConfiguration = ViewConfiguration.get(paramContext);
    this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    refreshDrawableState();
    setChecked(isChecked());
  }
  
  private void cancelSuperTouch(MotionEvent paramMotionEvent) {
    paramMotionEvent = MotionEvent.obtain(paramMotionEvent);
    paramMotionEvent.setAction(3);
    super.onTouchEvent(paramMotionEvent);
    paramMotionEvent.recycle();
  }
  
  private boolean getTargetCheckedState() {
    return (this.mThumbPosition >= (getThumbScrollRange() / 2));
  }
  
  private int getThumbScrollRange() {
    Drawable drawable = this.mTrackDrawable;
    if (drawable == null)
      return 0; 
    drawable.getPadding(this.mTempRect);
    return this.mSwitchWidth - this.mThumbWidth - this.mTempRect.left - this.mTempRect.right;
  }
  
  private boolean hitThumb(float paramFloat1, float paramFloat2) {
    this.mThumbDrawable.getPadding(this.mTempRect);
    int i = this.mSwitchTop;
    int j = this.mTouchSlop;
    int k = this.mSwitchLeft + (int)(this.mThumbPosition + 0.5F) - j;
    int m = this.mThumbWidth;
    int n = this.mTempRect.left;
    int i1 = this.mTempRect.right;
    int i2 = this.mTouchSlop;
    int i3 = this.mSwitchBottom;
    return (paramFloat1 > k && paramFloat1 < (m + k + n + i1 + i2) && paramFloat2 > (i - j) && paramFloat2 < (i3 + i2));
  }
  
  private void setThumbPosition(boolean paramBoolean) {
    float f;
    if (paramBoolean) {
      f = getThumbScrollRange();
    } else {
      f = 0.0F;
    } 
    this.mThumbPosition = f;
  }
  
  private void stopDrag(MotionEvent paramMotionEvent) {
    boolean bool;
    boolean bool1 = false;
    this.mTouchMode = 0;
    if (paramMotionEvent.getAction() == 1 && isEnabled()) {
      bool = true;
    } else {
      bool = false;
    } 
    cancelSuperTouch(paramMotionEvent);
    if (bool) {
      this.mVelocityTracker.computeCurrentVelocity(1000);
      float f = this.mVelocityTracker.getXVelocity();
      if (Math.abs(f) > this.mMinFlingVelocity) {
        if (f > 0.0F)
          bool1 = true; 
      } else {
        bool1 = getTargetCheckedState();
      } 
      animateThumbToCheckedState(bool1);
      return;
    } 
    animateThumbToCheckedState(isChecked());
  }
  
  protected void animateThumbToCheckedState(boolean paramBoolean) {
    setChecked(paramBoolean);
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    Drawable drawable = this.mThumbDrawable;
    if (drawable != null)
      drawable.setState(arrayOfInt); 
    drawable = this.mTrackDrawable;
    if (drawable != null)
      drawable.setState(arrayOfInt); 
    invalidate();
  }
  
  public int getCompoundPaddingLeft() {
    int j = super.getCompoundPaddingLeft() + this.mSwitchWidth;
    int i = j;
    if (!TextUtils.isEmpty(getText()))
      i = j + this.mSwitchPadding; 
    return i;
  }
  
  public int getCompoundPaddingRight() {
    int j = super.getCompoundPaddingRight() + this.mSwitchWidth;
    int i = j;
    if (!TextUtils.isEmpty(getText()))
      i = j + this.mSwitchPadding; 
    return i;
  }
  
  public Drawable getThumbDrawable() {
    return this.mThumbDrawable;
  }
  
  public Drawable getTrackDrawable() {
    return this.mTrackDrawable;
  }
  
  protected int[] onCreateDrawableState(int paramInt) {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    if (isChecked())
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET); 
    return arrayOfInt;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    int k = this.mSwitchLeft;
    int i = this.mSwitchTop;
    int m = this.mSwitchRight;
    int j = this.mSwitchBottom;
    this.mTrackDrawable.setBounds(k, i, m, j);
    this.mTrackDrawable.draw(paramCanvas);
    paramCanvas.save();
    this.mTrackDrawable.getPadding(this.mTempRect);
    k += this.mTempRect.left;
    paramCanvas.clipRect(k, i, m - this.mTempRect.right, j);
    this.mThumbDrawable.getPadding(this.mTempRect);
    m = (int)(this.mThumbPosition + 0.5F);
    int n = this.mTempRect.left;
    int i1 = this.mThumbWidth;
    int i2 = this.mTempRect.right;
    this.mThumbDrawable.setBounds(k - n + m, i, k + m + i1 + i2, j);
    this.mThumbDrawable.draw(paramCanvas);
    paramCanvas.restore();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    setThumbPosition(isChecked());
    paramInt3 = getWidth() - getPaddingRight();
    paramInt4 = this.mSwitchWidth;
    paramInt1 = getGravity() & 0x70;
    if (paramInt1 != 16) {
      if (paramInt1 != 80) {
        paramInt1 = getPaddingTop();
        paramInt2 = this.mSwitchHeight;
      } else {
        paramInt2 = getHeight() - getPaddingBottom();
        paramInt1 = paramInt2 - this.mSwitchHeight;
        this.mSwitchLeft = paramInt3 - paramInt4;
        this.mSwitchTop = paramInt1;
        this.mSwitchBottom = paramInt2;
        this.mSwitchRight = paramInt3;
      } 
    } else {
      paramInt1 = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2;
      paramInt2 = this.mSwitchHeight;
      paramInt1 -= paramInt2 / 2;
    } 
    paramInt2 += paramInt1;
    this.mSwitchLeft = paramInt3 - paramInt4;
    this.mSwitchTop = paramInt1;
    this.mSwitchBottom = paramInt2;
    this.mSwitchRight = paramInt3;
  }
  
  public void onMeasure(int paramInt1, int paramInt2) {
    if (this.mThumbDrawable == null)
      this.mThumbDrawable = getResources().getDrawable(2097479697); 
    if (this.mTrackDrawable == null)
      this.mTrackDrawable = getResources().getDrawable(2097479791); 
    Drawable drawable = this.mTrackDrawable;
    if (drawable != null) {
      if (this.mThumbDrawable == null)
        return; 
      paramInt1 = drawable.getIntrinsicWidth();
      paramInt1 = Math.max(this.mSwitchMinWidth, paramInt1);
      paramInt2 = this.mTrackDrawable.getIntrinsicHeight();
      this.mThumbWidth = this.mThumbDrawable.getIntrinsicWidth();
      this.mSwitchWidth = paramInt1;
      this.mSwitchHeight = paramInt2;
      setMeasuredDimension(paramInt1, paramInt2);
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int i = paramMotionEvent.getActionMasked();
    if (i != 0) {
      if (i != 1)
        if (i != 2) {
          if (i != 3)
            if (this.mTouchMode != 0)
              return true;  
        } else {
          i = this.mTouchMode;
          if (i != 0)
            if (i != 1) {
              if (i == 2) {
                float f1 = paramMotionEvent.getX();
                float f2 = this.mTouchX;
                f2 = Math.max(0.0F, Math.min(this.mThumbPosition + f1 - f2, getThumbScrollRange()));
                if (f2 != this.mThumbPosition) {
                  this.mThumbPosition = f2;
                  this.mTouchX = f1;
                  invalidate();
                } 
                return true;
              } 
            } else {
              float f1 = paramMotionEvent.getX();
              float f2 = paramMotionEvent.getY();
              if (Math.abs(f1 - this.mTouchX) > this.mTouchSlop || Math.abs(f2 - this.mTouchY) > this.mTouchSlop) {
                this.mTouchMode = 2;
                this.mTouchX = f1;
                this.mTouchY = f2;
                return true;
              } 
            }  
          if (this.mTouchMode != 0)
            return true; 
        }  
      i = this.mTouchMode;
      if (i == 2) {
        stopDrag(paramMotionEvent);
        return true;
      } 
      if (i == 1 || i == 3) {
        this.mTouchMode = 0;
        this.mVelocityTracker.clear();
        toggle();
        return true;
      } 
      this.mTouchMode = 0;
      this.mVelocityTracker.clear();
    } else {
      getParent().requestDisallowInterceptTouchEvent(true);
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      if (isEnabled()) {
        if (hitThumb(f1, f2)) {
          this.mTouchMode = 1;
        } else {
          this.mTouchMode = 3;
        } 
        this.mTouchX = f1;
        this.mTouchY = f2;
      } 
    } 
    if (this.mTouchMode != 0)
      return true; 
  }
  
  public void setChecked(boolean paramBoolean) {
    super.setChecked(paramBoolean);
    setThumbPosition(isChecked());
    invalidate();
  }
  
  public void setSwitchPadding(int paramInt) {
    this.mSwitchPadding = paramInt;
    requestLayout();
  }
  
  public void setThumbDrawable(Drawable paramDrawable) {
    this.mThumbDrawable = paramDrawable;
    requestLayout();
  }
  
  public void setThumbResource(int paramInt) {
    setThumbDrawable(getContext().getResources().getDrawable(paramInt));
  }
  
  public void setTrackDrawable(Drawable paramDrawable) {
    this.mTrackDrawable = paramDrawable;
    requestLayout();
  }
  
  public void setTrackResource(int paramInt) {
    setTrackDrawable(getContext().getResources().getDrawable(paramInt));
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    return (super.verifyDrawable(paramDrawable) || paramDrawable == this.mThumbDrawable || paramDrawable == this.mTrackDrawable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\AppbrandSwitch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */