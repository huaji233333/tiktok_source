package com.tt.miniapp.view.swipeback;

import android.content.Context;
import android.support.v4.view.u;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import java.util.Arrays;

public class ViewDragHelper {
  private static final Interpolator sInterpolator = new Interpolator() {
      public final float getInterpolation(float param1Float) {
        param1Float--;
        return param1Float * param1Float * param1Float * param1Float * param1Float + 1.0F;
      }
    };
  
  private boolean isVerticalMove;
  
  private int mActivePointerId = -1;
  
  private final Callback mCallback;
  
  private View mCapturedView;
  
  private int mDragState;
  
  private int[] mEdgeDragsInProgress;
  
  private int[] mEdgeDragsLocked;
  
  private int mEdgeSize;
  
  private int[] mInitialEdgesTouched;
  
  private float[] mInitialMotionX;
  
  private float[] mInitialMotionY;
  
  private float[] mLastMotionX;
  
  private float[] mLastMotionY;
  
  private float mMaxVelocity;
  
  private float mMinVelocity;
  
  private final ViewGroup mParentView;
  
  private int mPointersDown;
  
  private boolean mReleaseInProgress;
  
  private OverScroller mScroller;
  
  private final Runnable mSetIdleRunnable = new Runnable() {
      public void run() {
        ViewDragHelper.this.setDragState(0);
      }
    };
  
  private int mTouchSlop;
  
  private int mTrackingEdges;
  
  private VelocityTracker mVelocityTracker;
  
  private ViewDragHelper(Context paramContext, ViewGroup paramViewGroup, Callback paramCallback) {
    if (paramViewGroup != null) {
      if (paramCallback != null) {
        this.mParentView = paramViewGroup;
        this.mCallback = paramCallback;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(paramContext);
        this.mEdgeSize = (int)((paramContext.getResources().getDisplayMetrics()).density * 20.0F + 0.5F);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(paramContext, sInterpolator);
        return;
      } 
      throw new IllegalArgumentException("Callback may not be null");
    } 
    throw new IllegalArgumentException("Parent view may not be null");
  }
  
  private boolean checkNewEdgeDrag(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2) {
    paramFloat1 = Math.abs(paramFloat1);
    paramFloat2 = Math.abs(paramFloat2);
    if ((this.mInitialEdgesTouched[paramInt1] & paramInt2) == paramInt2 && (this.mTrackingEdges & paramInt2) != 0 && (this.mEdgeDragsLocked[paramInt1] & paramInt2) != paramInt2 && (this.mEdgeDragsInProgress[paramInt1] & paramInt2) != paramInt2) {
      int i = this.mTouchSlop;
      if (paramFloat1 <= i && paramFloat2 <= i)
        return false; 
      if (paramFloat1 < paramFloat2 * 0.5F && this.mCallback.onEdgeLock(paramInt2)) {
        int[] arrayOfInt = this.mEdgeDragsLocked;
        arrayOfInt[paramInt1] = arrayOfInt[paramInt1] | paramInt2;
        return false;
      } 
      if ((this.mEdgeDragsInProgress[paramInt1] & paramInt2) == 0 && paramFloat1 > this.mTouchSlop)
        return true; 
    } 
    return false;
  }
  
  private boolean checkTouchSlop(View paramView, float paramFloat1, float paramFloat2) {
    int i;
    boolean bool;
    if (paramView == null)
      return false; 
    if (this.mCallback.getViewHorizontalDragRange(paramView) > 0) {
      i = 1;
    } else {
      i = 0;
    } 
    if (this.mCallback.getViewVerticalDragRange(paramView) > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (i && bool) {
      i = this.mTouchSlop;
      return (paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 > (i * i));
    } 
    return (i != 0) ? ((Math.abs(paramFloat1) > this.mTouchSlop)) : ((bool && Math.abs(paramFloat2) > this.mTouchSlop));
  }
  
  private float clampMag(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = Math.abs(paramFloat1);
    if (f < paramFloat2)
      return 0.0F; 
    paramFloat2 = paramFloat1;
    if (f > paramFloat3) {
      if (paramFloat1 > 0.0F)
        return paramFloat3; 
      paramFloat2 = -paramFloat3;
    } 
    return paramFloat2;
  }
  
  private int clampMag(int paramInt1, int paramInt2, int paramInt3) {
    int i = Math.abs(paramInt1);
    if (i < paramInt2)
      return 0; 
    paramInt2 = paramInt1;
    if (i > paramInt3) {
      if (paramInt1 > 0)
        return paramInt3; 
      paramInt2 = -paramInt3;
    } 
    return paramInt2;
  }
  
  private void clearMotionHistory() {
    float[] arrayOfFloat = this.mInitialMotionX;
    if (arrayOfFloat == null)
      return; 
    Arrays.fill(arrayOfFloat, 0.0F);
    Arrays.fill(this.mInitialMotionY, 0.0F);
    Arrays.fill(this.mLastMotionX, 0.0F);
    Arrays.fill(this.mLastMotionY, 0.0F);
    Arrays.fill(this.mInitialEdgesTouched, 0);
    Arrays.fill(this.mEdgeDragsInProgress, 0);
    Arrays.fill(this.mEdgeDragsLocked, 0);
    this.mPointersDown = 0;
    this.isVerticalMove = false;
  }
  
  private void clearMotionHistory(int paramInt) {
    if (this.mInitialMotionX != null) {
      if (!isPointerDown(paramInt))
        return; 
      this.mInitialMotionX[paramInt] = 0.0F;
      this.mInitialMotionY[paramInt] = 0.0F;
      this.mLastMotionX[paramInt] = 0.0F;
      this.mLastMotionY[paramInt] = 0.0F;
      this.mInitialEdgesTouched[paramInt] = 0;
      this.mEdgeDragsInProgress[paramInt] = 0;
      this.mEdgeDragsLocked[paramInt] = 0;
      this.mPointersDown = (1 << paramInt ^ 0xFFFFFFFF) & this.mPointersDown;
    } 
  }
  
  private int computeAxisDuration(int paramInt1, int paramInt2, int paramInt3) {
    if (paramInt1 == 0)
      return 0; 
    int i = this.mParentView.getWidth();
    int j = i / 2;
    float f2 = Math.min(1.0F, Math.abs(paramInt1) / i);
    float f1 = j;
    f2 = distanceInfluenceForSnapDuration(f2);
    paramInt2 = Math.abs(paramInt2);
    if (paramInt2 > 0) {
      paramInt1 = Math.round(Math.abs((f1 + f2 * f1) / paramInt2) * 1000.0F) * 4;
    } else {
      paramInt1 = (int)((Math.abs(paramInt1) / paramInt3 + 1.0F) * 256.0F);
    } 
    return Math.min(paramInt1, 600);
  }
  
  private int computeSettleDuration(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    float f1;
    float f2;
    paramInt3 = clampMag(paramInt3, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    paramInt4 = clampMag(paramInt4, (int)this.mMinVelocity, (int)this.mMaxVelocity);
    int i = Math.abs(paramInt1);
    int j = Math.abs(paramInt2);
    int k = Math.abs(paramInt3);
    int m = Math.abs(paramInt4);
    int n = k + m;
    int i1 = i + j;
    if (paramInt3 != 0) {
      f1 = k;
      f2 = n;
    } else {
      f1 = i;
      f2 = i1;
    } 
    float f3 = f1 / f2;
    if (paramInt4 != 0) {
      f1 = m;
      f2 = n;
    } else {
      f1 = j;
      f2 = i1;
    } 
    f1 /= f2;
    paramInt1 = computeAxisDuration(paramInt1, paramInt3, this.mCallback.getViewHorizontalDragRange(paramView));
    paramInt2 = computeAxisDuration(paramInt2, paramInt4, this.mCallback.getViewVerticalDragRange(paramView));
    return (int)(paramInt1 * f3 + paramInt2 * f1);
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, float paramFloat, Callback paramCallback) {
    ViewDragHelper viewDragHelper = create(paramViewGroup, paramCallback);
    viewDragHelper.mTouchSlop = (int)(viewDragHelper.mTouchSlop * 1.0F / paramFloat);
    return viewDragHelper;
  }
  
  public static ViewDragHelper create(ViewGroup paramViewGroup, Callback paramCallback) {
    return new ViewDragHelper(paramViewGroup.getContext(), paramViewGroup, paramCallback);
  }
  
  private void dispatchViewReleased(float paramFloat1, float paramFloat2) {
    this.mReleaseInProgress = true;
    this.mCallback.onViewReleased(this.mCapturedView, paramFloat1, paramFloat2);
    this.mReleaseInProgress = false;
    if (this.mDragState == 1)
      setDragState(0); 
  }
  
  private float distanceInfluenceForSnapDuration(float paramFloat) {
    return (float)Math.sin(((paramFloat - 0.5F) * 0.47123894F));
  }
  
  private void dragTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int j = this.mCapturedView.getLeft();
    int k = this.mCapturedView.getTop();
    int i = paramInt1;
    if (paramInt3 != 0) {
      i = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, paramInt1, paramInt3);
      u.h(this.mCapturedView, i - j);
    } 
    paramInt1 = paramInt2;
    if (paramInt4 != 0) {
      paramInt1 = this.mCallback.clampViewPositionVertical(this.mCapturedView, paramInt2, paramInt4);
      u.g(this.mCapturedView, paramInt1 - k);
    } 
    if (paramInt3 != 0 || paramInt4 != 0)
      this.mCallback.onViewPositionChanged(this.mCapturedView, i, paramInt1, i - j, paramInt1 - k); 
  }
  
  private void ensureMotionHistorySizeForId(int paramInt) {
    float[] arrayOfFloat = this.mInitialMotionX;
    if (arrayOfFloat == null || arrayOfFloat.length <= paramInt) {
      arrayOfFloat = new float[++paramInt];
      float[] arrayOfFloat1 = new float[paramInt];
      float[] arrayOfFloat2 = new float[paramInt];
      float[] arrayOfFloat3 = new float[paramInt];
      int[] arrayOfInt1 = new int[paramInt];
      int[] arrayOfInt2 = new int[paramInt];
      int[] arrayOfInt3 = new int[paramInt];
      float[] arrayOfFloat4 = this.mInitialMotionX;
      if (arrayOfFloat4 != null) {
        System.arraycopy(arrayOfFloat4, 0, arrayOfFloat, 0, arrayOfFloat4.length);
        arrayOfFloat4 = this.mInitialMotionY;
        System.arraycopy(arrayOfFloat4, 0, arrayOfFloat1, 0, arrayOfFloat4.length);
        arrayOfFloat4 = this.mLastMotionX;
        System.arraycopy(arrayOfFloat4, 0, arrayOfFloat2, 0, arrayOfFloat4.length);
        arrayOfFloat4 = this.mLastMotionY;
        System.arraycopy(arrayOfFloat4, 0, arrayOfFloat3, 0, arrayOfFloat4.length);
        int[] arrayOfInt = this.mInitialEdgesTouched;
        System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, arrayOfInt.length);
        arrayOfInt = this.mEdgeDragsInProgress;
        System.arraycopy(arrayOfInt, 0, arrayOfInt2, 0, arrayOfInt.length);
        arrayOfInt = this.mEdgeDragsLocked;
        System.arraycopy(arrayOfInt, 0, arrayOfInt3, 0, arrayOfInt.length);
      } 
      this.mInitialMotionX = arrayOfFloat;
      this.mInitialMotionY = arrayOfFloat1;
      this.mLastMotionX = arrayOfFloat2;
      this.mLastMotionY = arrayOfFloat3;
      this.mInitialEdgesTouched = arrayOfInt1;
      this.mEdgeDragsInProgress = arrayOfInt2;
      this.mEdgeDragsLocked = arrayOfInt3;
    } 
  }
  
  private boolean forceSettleCapturedViewAt(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = this.mCapturedView.getLeft();
    int j = this.mCapturedView.getTop();
    paramInt1 -= i;
    paramInt2 -= j;
    if (paramInt1 == 0 && paramInt2 == 0) {
      this.mScroller.abortAnimation();
      setDragState(0);
      return false;
    } 
    paramInt3 = computeSettleDuration(this.mCapturedView, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mScroller.startScroll(i, j, paramInt1, paramInt2, paramInt3);
    setDragState(2);
    return true;
  }
  
  private int getEdgesTouched(int paramInt1, int paramInt2) {
    if (paramInt1 < this.mParentView.getLeft() + this.mEdgeSize) {
      j = 1;
    } else {
      j = 0;
    } 
    int i = j;
    if (paramInt2 < this.mParentView.getTop() + this.mEdgeSize)
      i = j | 0x4; 
    int j = i;
    if (paramInt1 > this.mParentView.getRight() - this.mEdgeSize)
      j = i | 0x2; 
    paramInt1 = j;
    if (paramInt2 > this.mParentView.getBottom() - this.mEdgeSize)
      paramInt1 = j | 0x8; 
    return paramInt1;
  }
  
  private boolean isValidPointerForActionMove(int paramInt) {
    return !!isPointerDown(paramInt);
  }
  
  private void releaseViewForPointerUp() {
    this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
    dispatchViewReleased(clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
  }
  
  private void reportNewEdgeDrags(float paramFloat1, float paramFloat2, int paramInt) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private void saveInitialMotion(float paramFloat1, float paramFloat2, int paramInt) {
    ensureMotionHistorySizeForId(paramInt);
    float[] arrayOfFloat = this.mInitialMotionX;
    this.mLastMotionX[paramInt] = paramFloat1;
    arrayOfFloat[paramInt] = paramFloat1;
    arrayOfFloat = this.mInitialMotionY;
    this.mLastMotionY[paramInt] = paramFloat2;
    arrayOfFloat[paramInt] = paramFloat2;
    this.mInitialEdgesTouched[paramInt] = getEdgesTouched((int)paramFloat1, (int)paramFloat2);
    this.mPointersDown |= 1 << paramInt;
  }
  
  private void saveLastMotion(MotionEvent paramMotionEvent) {
    int j = paramMotionEvent.getPointerCount();
    int i;
    for (i = 0; i < j; i++) {
      int k = paramMotionEvent.getPointerId(i);
      if (isValidPointerForActionMove(k)) {
        float f1 = paramMotionEvent.getX(i);
        float f2 = paramMotionEvent.getY(i);
        this.mLastMotionX[k] = f1;
        this.mLastMotionY[k] = f2;
      } 
    } 
  }
  
  public void abort() {
    cancel();
    if (this.mDragState == 2) {
      int i = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      this.mScroller.abortAnimation();
      int k = this.mScroller.getCurrX();
      int m = this.mScroller.getCurrY();
      this.mCallback.onViewPositionChanged(this.mCapturedView, k, m, k - i, m - j);
    } 
    setDragState(0);
  }
  
  protected boolean canScroll(View paramView, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramView instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      int j = paramView.getScrollX();
      int k = paramView.getScrollY();
      int i;
      for (i = viewGroup.getChildCount() - 1; i >= 0; i--) {
        View view = viewGroup.getChildAt(i);
        int m = paramInt3 + j;
        if (m >= view.getLeft() && m < view.getRight()) {
          int n = paramInt4 + k;
          if (n >= view.getTop() && n < view.getBottom() && canScroll(view, true, paramInt1, paramInt2, m - view.getLeft(), n - view.getTop()))
            return true; 
        } 
      } 
    } 
    return (paramBoolean && (paramView.canScrollHorizontally(-paramInt1) || paramView.canScrollVertically(-paramInt2)));
  }
  
  public void cancel() {
    this.mActivePointerId = -1;
    clearMotionHistory();
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  public void captureChildView(View paramView, int paramInt) {
    if (paramView.getParent() == this.mParentView) {
      this.mCapturedView = paramView;
      this.mActivePointerId = paramInt;
      this.mCallback.onViewCaptured(paramView, paramInt);
      setDragState(1);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
    stringBuilder.append(this.mParentView);
    stringBuilder.append(")");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public boolean checkTouchSlop(int paramInt) {
    int j = this.mInitialMotionX.length;
    for (int i = 0; i < j; i++) {
      if (checkTouchSlop(paramInt, i))
        return true; 
    } 
    return false;
  }
  
  public boolean checkTouchSlop(int paramInt1, int paramInt2) {
    boolean bool;
    if (!isPointerDown(paramInt2))
      return false; 
    if ((paramInt1 & 0x1) == 1) {
      bool = true;
    } else {
      bool = false;
    } 
    if ((paramInt1 & 0x2) == 2) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    } 
    float f1 = this.mLastMotionX[paramInt2] - this.mInitialMotionX[paramInt2];
    float f2 = this.mLastMotionY[paramInt2] - this.mInitialMotionY[paramInt2];
    if (bool && paramInt1 != 0) {
      paramInt1 = this.mTouchSlop;
      return (f1 * f1 + f2 * f2 > (paramInt1 * paramInt1));
    } 
    return bool ? ((Math.abs(f1) > this.mTouchSlop)) : ((paramInt1 != 0 && Math.abs(f2) > this.mTouchSlop));
  }
  
  public boolean continueSettling(boolean paramBoolean) {
    if (this.mDragState == 2) {
      boolean bool2 = this.mScroller.computeScrollOffset();
      int i = this.mScroller.getCurrX();
      int j = this.mScroller.getCurrY();
      int k = i - this.mCapturedView.getLeft();
      int m = j - this.mCapturedView.getTop();
      if (k != 0)
        u.h(this.mCapturedView, k); 
      if (m != 0)
        u.g(this.mCapturedView, m); 
      if (k != 0 || m != 0)
        this.mCallback.onViewPositionChanged(this.mCapturedView, i, j, k, m); 
      boolean bool1 = bool2;
      if (bool2) {
        bool1 = bool2;
        if (i == this.mScroller.getFinalX()) {
          bool1 = bool2;
          if (j == this.mScroller.getFinalY()) {
            this.mScroller.abortAnimation();
            bool1 = false;
          } 
        } 
      } 
      if (!bool1)
        if (paramBoolean) {
          this.mParentView.post(this.mSetIdleRunnable);
        } else {
          setDragState(0);
        }  
    } 
    return (this.mDragState == 2);
  }
  
  public View findTopChildUnder(int paramInt1, int paramInt2) {
    for (int i = this.mParentView.getChildCount() - 1; i >= 0; i--) {
      View view = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(i));
      if (paramInt1 >= view.getLeft() && paramInt1 < view.getRight() && paramInt2 >= view.getTop() && paramInt2 < view.getBottom())
        return view; 
    } 
    return null;
  }
  
  public void flingCapturedView(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mReleaseInProgress) {
      this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId), paramInt1, paramInt3, paramInt2, paramInt4);
      setDragState(2);
      return;
    } 
    throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
  }
  
  public int getActivePointerId() {
    return this.mActivePointerId;
  }
  
  public View getCapturedView() {
    return this.mCapturedView;
  }
  
  public int getEdgeSize() {
    return this.mEdgeSize;
  }
  
  public float getMinVelocity() {
    return this.mMinVelocity;
  }
  
  public int getTouchSlop() {
    return this.mTouchSlop;
  }
  
  public int getViewDragState() {
    return this.mDragState;
  }
  
  public int getmEdgeSize() {
    return this.mEdgeSize;
  }
  
  public boolean isCapturedViewUnder(int paramInt1, int paramInt2) {
    return isViewUnder(this.mCapturedView, paramInt1, paramInt2);
  }
  
  public boolean isEdgeTouched(int paramInt) {
    int j = this.mInitialEdgesTouched.length;
    for (int i = 0; i < j; i++) {
      if (isEdgeTouched(paramInt, i))
        return true; 
    } 
    return false;
  }
  
  public boolean isEdgeTouched(int paramInt1, int paramInt2) {
    return (isPointerDown(paramInt2) && (paramInt1 & this.mInitialEdgesTouched[paramInt2]) != 0);
  }
  
  public boolean isPointerDown(int paramInt) {
    return ((1 << paramInt & this.mPointersDown) != 0);
  }
  
  public boolean isViewUnder(View paramView, int paramInt1, int paramInt2) {
    return (paramView == null) ? false : ((paramInt1 >= paramView.getLeft() && paramInt1 < paramView.getRight() && paramInt2 >= paramView.getTop() && paramInt2 < paramView.getBottom()));
  }
  
  public void processTouchEvent(MotionEvent paramMotionEvent) {
    int m = paramMotionEvent.getActionMasked();
    int k = paramMotionEvent.getActionIndex();
    if (m == 0)
      cancel(); 
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int j = 0;
    int i = 0;
    if (m != 0) {
      if (m != 1) {
        if (m != 2) {
          if (m != 3) {
            if (m != 5) {
              if (m != 6)
                return; 
              j = paramMotionEvent.getPointerId(k);
              if (this.mDragState == 1 && j == this.mActivePointerId) {
                k = paramMotionEvent.getPointerCount();
                while (true) {
                  if (i < k) {
                    m = paramMotionEvent.getPointerId(i);
                    if (m != this.mActivePointerId) {
                      float f3 = paramMotionEvent.getX(i);
                      float f4 = paramMotionEvent.getY(i);
                      View view1 = findTopChildUnder((int)f3, (int)f4);
                      View view2 = this.mCapturedView;
                      if (view1 == view2 && tryCaptureViewForDrag(view2, m)) {
                        i = this.mActivePointerId;
                        break;
                      } 
                    } 
                    i++;
                    continue;
                  } 
                  i = -1;
                  break;
                } 
                if (i == -1)
                  releaseViewForPointerUp(); 
              } 
              clearMotionHistory(j);
              return;
            } 
            i = paramMotionEvent.getPointerId(k);
            float f1 = paramMotionEvent.getX(k);
            float f2 = paramMotionEvent.getY(k);
            saveInitialMotion(f1, f2, i);
            if (this.mDragState == 0) {
              tryCaptureViewForDrag(findTopChildUnder((int)f1, (int)f2), i);
              j = this.mInitialEdgesTouched[i];
              k = this.mTrackingEdges;
              if ((j & k) != 0)
                this.mCallback.onEdgeTouched(j & k, i); 
              return;
            } 
            if (isCapturedViewUnder((int)f1, (int)f2)) {
              tryCaptureViewForDrag(this.mCapturedView, i);
              return;
            } 
          } else {
            if (this.mDragState == 1)
              dispatchViewReleased(0.0F, 0.0F); 
            cancel();
            return;
          } 
        } else if (this.mDragState == 1) {
          if (isValidPointerForActionMove(this.mActivePointerId)) {
            i = paramMotionEvent.findPointerIndex(this.mActivePointerId);
            float f1 = paramMotionEvent.getX(i);
            float f2 = paramMotionEvent.getY(i);
            float[] arrayOfFloat = this.mLastMotionX;
            j = this.mActivePointerId;
            i = (int)(f1 - arrayOfFloat[j]);
            j = (int)(f2 - this.mLastMotionY[j]);
            dragTo(this.mCapturedView.getLeft() + i, this.mCapturedView.getTop() + j, i, j);
            saveLastMotion(paramMotionEvent);
            return;
          } 
        } else {
          k = paramMotionEvent.getPointerCount();
          for (i = j; i < k; i++) {
            j = paramMotionEvent.getPointerId(i);
            if (isValidPointerForActionMove(j)) {
              float f1 = paramMotionEvent.getX(i);
              float f2 = paramMotionEvent.getY(i);
              float f3 = f1 - this.mInitialMotionX[j];
              float f4 = f2 - this.mInitialMotionY[j];
              reportNewEdgeDrags(f3, f4, j);
              if (this.mDragState != 1) {
                View view = findTopChildUnder((int)f1, (int)f2);
                if (!checkTouchSlop(view, f3, f4) || !tryCaptureViewForDrag(view, j))
                  continue; 
              } 
              break;
            } 
            continue;
          } 
          saveLastMotion(paramMotionEvent);
          return;
        } 
      } else {
        if (this.mDragState == 1)
          releaseViewForPointerUp(); 
        cancel();
        return;
      } 
    } else {
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      i = paramMotionEvent.getPointerId(0);
      View view = findTopChildUnder((int)f1, (int)f2);
      saveInitialMotion(f1, f2, i);
      tryCaptureViewForDrag(view, i);
      j = this.mInitialEdgesTouched[i];
      k = this.mTrackingEdges;
      if ((j & k) != 0)
        this.mCallback.onEdgeTouched(j & k, i); 
    } 
  }
  
  public void setDragState(int paramInt) {
    this.mParentView.removeCallbacks(this.mSetIdleRunnable);
    if (this.mDragState != paramInt) {
      this.mDragState = paramInt;
      this.mCallback.onViewDragStateChanged(paramInt);
      if (this.mDragState == 0)
        this.mCapturedView = null; 
    } 
  }
  
  public void setEdgeTrackingEnabled(int paramInt) {
    this.mTrackingEdges = paramInt;
  }
  
  public void setMinVelocity(float paramFloat) {
    this.mMinVelocity = paramFloat;
  }
  
  public void setmEdgeSize(int paramInt) {
    this.mEdgeSize = paramInt;
  }
  
  public boolean settleCapturedViewAt(int paramInt1, int paramInt2) {
    if (this.mReleaseInProgress)
      return forceSettleCapturedViewAt(paramInt1, paramInt2, (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId)); 
    throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
  }
  
  public boolean shouldInterceptTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getActionMasked : ()I
    //   4: istore #6
    //   6: aload_1
    //   7: invokevirtual getActionIndex : ()I
    //   10: istore #7
    //   12: iload #6
    //   14: ifne -> 21
    //   17: aload_0
    //   18: invokevirtual cancel : ()V
    //   21: aload_0
    //   22: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   25: ifnonnull -> 35
    //   28: aload_0
    //   29: invokestatic obtain : ()Landroid/view/VelocityTracker;
    //   32: putfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   35: aload_0
    //   36: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   39: aload_1
    //   40: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
    //   43: iload #6
    //   45: ifeq -> 543
    //   48: iload #6
    //   50: iconst_1
    //   51: if_icmpeq -> 536
    //   54: iload #6
    //   56: iconst_2
    //   57: if_icmpeq -> 210
    //   60: iload #6
    //   62: iconst_3
    //   63: if_icmpeq -> 536
    //   66: iload #6
    //   68: iconst_5
    //   69: if_icmpeq -> 95
    //   72: iload #6
    //   74: bipush #6
    //   76: if_icmpeq -> 82
    //   79: goto -> 638
    //   82: aload_0
    //   83: aload_1
    //   84: iload #7
    //   86: invokevirtual getPointerId : (I)I
    //   89: invokespecial clearMotionHistory : (I)V
    //   92: goto -> 638
    //   95: aload_1
    //   96: iload #7
    //   98: invokevirtual getPointerId : (I)I
    //   101: istore #6
    //   103: aload_1
    //   104: iload #7
    //   106: invokevirtual getX : (I)F
    //   109: fstore_2
    //   110: aload_1
    //   111: iload #7
    //   113: invokevirtual getY : (I)F
    //   116: fstore_3
    //   117: aload_0
    //   118: fload_2
    //   119: fload_3
    //   120: iload #6
    //   122: invokespecial saveInitialMotion : (FFI)V
    //   125: aload_0
    //   126: getfield mDragState : I
    //   129: istore #7
    //   131: iload #7
    //   133: ifne -> 176
    //   136: aload_0
    //   137: getfield mInitialEdgesTouched : [I
    //   140: iload #6
    //   142: iaload
    //   143: istore #7
    //   145: aload_0
    //   146: getfield mTrackingEdges : I
    //   149: istore #8
    //   151: iload #7
    //   153: iload #8
    //   155: iand
    //   156: ifeq -> 638
    //   159: aload_0
    //   160: getfield mCallback : Lcom/tt/miniapp/view/swipeback/ViewDragHelper$Callback;
    //   163: iload #7
    //   165: iload #8
    //   167: iand
    //   168: iload #6
    //   170: invokevirtual onEdgeTouched : (II)V
    //   173: goto -> 638
    //   176: iload #7
    //   178: iconst_2
    //   179: if_icmpne -> 638
    //   182: aload_0
    //   183: fload_2
    //   184: f2i
    //   185: fload_3
    //   186: f2i
    //   187: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   190: astore_1
    //   191: aload_1
    //   192: aload_0
    //   193: getfield mCapturedView : Landroid/view/View;
    //   196: if_acmpne -> 638
    //   199: aload_0
    //   200: aload_1
    //   201: iload #6
    //   203: invokevirtual tryCaptureViewForDrag : (Landroid/view/View;I)Z
    //   206: pop
    //   207: goto -> 638
    //   210: aload_0
    //   211: getfield mInitialMotionX : [F
    //   214: ifnull -> 638
    //   217: aload_0
    //   218: getfield mInitialMotionY : [F
    //   221: ifnull -> 638
    //   224: aload_1
    //   225: invokevirtual getPointerCount : ()I
    //   228: istore #7
    //   230: iconst_0
    //   231: istore #6
    //   233: iload #6
    //   235: iload #7
    //   237: if_icmpge -> 528
    //   240: aload_1
    //   241: iload #6
    //   243: invokevirtual getPointerId : (I)I
    //   246: istore #8
    //   248: aload_0
    //   249: iload #8
    //   251: invokespecial isValidPointerForActionMove : (I)Z
    //   254: ifeq -> 519
    //   257: aload_1
    //   258: iload #6
    //   260: invokevirtual getX : (I)F
    //   263: fstore_2
    //   264: aload_1
    //   265: iload #6
    //   267: invokevirtual getY : (I)F
    //   270: fstore_3
    //   271: fload_2
    //   272: aload_0
    //   273: getfield mInitialMotionX : [F
    //   276: iload #8
    //   278: faload
    //   279: fsub
    //   280: fstore #4
    //   282: fload_3
    //   283: aload_0
    //   284: getfield mInitialMotionY : [F
    //   287: iload #8
    //   289: faload
    //   290: fsub
    //   291: fstore #5
    //   293: aload_0
    //   294: getfield isVerticalMove : Z
    //   297: ifne -> 335
    //   300: fload #5
    //   302: invokestatic abs : (F)F
    //   305: aload_0
    //   306: getfield mTouchSlop : I
    //   309: iconst_3
    //   310: imul
    //   311: i2f
    //   312: fcmpl
    //   313: ifle -> 335
    //   316: fload #4
    //   318: invokestatic abs : (F)F
    //   321: fload #5
    //   323: invokestatic abs : (F)F
    //   326: fcmpg
    //   327: ifge -> 335
    //   330: aload_0
    //   331: iconst_1
    //   332: putfield isVerticalMove : Z
    //   335: aload_0
    //   336: getfield isVerticalMove : Z
    //   339: ifne -> 528
    //   342: aload_0
    //   343: fload_2
    //   344: f2i
    //   345: fload_3
    //   346: f2i
    //   347: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   350: astore #16
    //   352: aload_0
    //   353: aload #16
    //   355: fload #4
    //   357: fload #5
    //   359: invokespecial checkTouchSlop : (Landroid/view/View;FF)Z
    //   362: istore #15
    //   364: iload #15
    //   366: ifeq -> 485
    //   369: aload #16
    //   371: invokevirtual getLeft : ()I
    //   374: istore #9
    //   376: fload #4
    //   378: f2i
    //   379: istore #10
    //   381: aload_0
    //   382: getfield mCallback : Lcom/tt/miniapp/view/swipeback/ViewDragHelper$Callback;
    //   385: aload #16
    //   387: iload #9
    //   389: iload #10
    //   391: iadd
    //   392: iload #10
    //   394: invokevirtual clampViewPositionHorizontal : (Landroid/view/View;II)I
    //   397: istore #10
    //   399: aload #16
    //   401: invokevirtual getTop : ()I
    //   404: istore #11
    //   406: fload #5
    //   408: f2i
    //   409: istore #12
    //   411: aload_0
    //   412: getfield mCallback : Lcom/tt/miniapp/view/swipeback/ViewDragHelper$Callback;
    //   415: aload #16
    //   417: iload #11
    //   419: iload #12
    //   421: iadd
    //   422: iload #12
    //   424: invokevirtual clampViewPositionVertical : (Landroid/view/View;II)I
    //   427: istore #12
    //   429: aload_0
    //   430: getfield mCallback : Lcom/tt/miniapp/view/swipeback/ViewDragHelper$Callback;
    //   433: aload #16
    //   435: invokevirtual getViewHorizontalDragRange : (Landroid/view/View;)I
    //   438: istore #13
    //   440: aload_0
    //   441: getfield mCallback : Lcom/tt/miniapp/view/swipeback/ViewDragHelper$Callback;
    //   444: aload #16
    //   446: invokevirtual getViewVerticalDragRange : (Landroid/view/View;)I
    //   449: istore #14
    //   451: iload #13
    //   453: ifeq -> 468
    //   456: iload #13
    //   458: ifle -> 485
    //   461: iload #10
    //   463: iload #9
    //   465: if_icmpne -> 485
    //   468: iload #14
    //   470: ifeq -> 528
    //   473: iload #14
    //   475: ifle -> 485
    //   478: iload #12
    //   480: iload #11
    //   482: if_icmpeq -> 528
    //   485: aload_0
    //   486: fload #4
    //   488: fload #5
    //   490: iload #8
    //   492: invokespecial reportNewEdgeDrags : (FFI)V
    //   495: aload_0
    //   496: getfield mDragState : I
    //   499: iconst_1
    //   500: if_icmpeq -> 528
    //   503: iload #15
    //   505: ifeq -> 519
    //   508: aload_0
    //   509: aload #16
    //   511: iload #8
    //   513: invokevirtual tryCaptureViewForDrag : (Landroid/view/View;I)Z
    //   516: ifne -> 528
    //   519: iload #6
    //   521: iconst_1
    //   522: iadd
    //   523: istore #6
    //   525: goto -> 233
    //   528: aload_0
    //   529: aload_1
    //   530: invokespecial saveLastMotion : (Landroid/view/MotionEvent;)V
    //   533: goto -> 638
    //   536: aload_0
    //   537: invokevirtual cancel : ()V
    //   540: goto -> 638
    //   543: aload_1
    //   544: invokevirtual getX : ()F
    //   547: fstore_2
    //   548: aload_1
    //   549: invokevirtual getY : ()F
    //   552: fstore_3
    //   553: aload_1
    //   554: iconst_0
    //   555: invokevirtual getPointerId : (I)I
    //   558: istore #6
    //   560: aload_0
    //   561: fload_2
    //   562: fload_3
    //   563: iload #6
    //   565: invokespecial saveInitialMotion : (FFI)V
    //   568: aload_0
    //   569: fload_2
    //   570: f2i
    //   571: fload_3
    //   572: f2i
    //   573: invokevirtual findTopChildUnder : (II)Landroid/view/View;
    //   576: astore_1
    //   577: aload_1
    //   578: aload_0
    //   579: getfield mCapturedView : Landroid/view/View;
    //   582: if_acmpne -> 601
    //   585: aload_0
    //   586: getfield mDragState : I
    //   589: iconst_2
    //   590: if_icmpne -> 601
    //   593: aload_0
    //   594: aload_1
    //   595: iload #6
    //   597: invokevirtual tryCaptureViewForDrag : (Landroid/view/View;I)Z
    //   600: pop
    //   601: aload_0
    //   602: getfield mInitialEdgesTouched : [I
    //   605: iload #6
    //   607: iaload
    //   608: istore #7
    //   610: aload_0
    //   611: getfield mTrackingEdges : I
    //   614: istore #8
    //   616: iload #7
    //   618: iload #8
    //   620: iand
    //   621: ifeq -> 638
    //   624: aload_0
    //   625: getfield mCallback : Lcom/tt/miniapp/view/swipeback/ViewDragHelper$Callback;
    //   628: iload #7
    //   630: iload #8
    //   632: iand
    //   633: iload #6
    //   635: invokevirtual onEdgeTouched : (II)V
    //   638: aload_0
    //   639: getfield mDragState : I
    //   642: iconst_1
    //   643: if_icmpne -> 648
    //   646: iconst_1
    //   647: ireturn
    //   648: iconst_0
    //   649: ireturn
  }
  
  public boolean smoothSlideViewTo(View paramView, int paramInt1, int paramInt2) {
    this.mCapturedView = paramView;
    this.mActivePointerId = -1;
    boolean bool = forceSettleCapturedViewAt(paramInt1, paramInt2, 0, 0);
    if (!bool && this.mDragState == 0 && this.mCapturedView != null)
      this.mCapturedView = null; 
    return bool;
  }
  
  boolean tryCaptureViewForDrag(View paramView, int paramInt) {
    if (paramView == this.mCapturedView && this.mActivePointerId == paramInt)
      return true; 
    if (paramView != null && this.mCallback.tryCaptureView(paramView, paramInt)) {
      this.mActivePointerId = paramInt;
      captureChildView(paramView, paramInt);
      return true;
    } 
    return false;
  }
  
  public static abstract class Callback {
    public int clampViewPositionHorizontal(View param1View, int param1Int1, int param1Int2) {
      return 0;
    }
    
    public int clampViewPositionVertical(View param1View, int param1Int1, int param1Int2) {
      return 0;
    }
    
    public int getOrderedChildIndex(int param1Int) {
      return param1Int;
    }
    
    public int getViewHorizontalDragRange(View param1View) {
      return 0;
    }
    
    public int getViewVerticalDragRange(View param1View) {
      return 0;
    }
    
    public void onEdgeDragStarted(int param1Int1, int param1Int2) {}
    
    public boolean onEdgeLock(int param1Int) {
      return false;
    }
    
    public void onEdgeTouched(int param1Int1, int param1Int2) {}
    
    public void onViewCaptured(View param1View, int param1Int) {}
    
    public void onViewDragStateChanged(int param1Int) {}
    
    public void onViewPositionChanged(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
    
    public void onViewReleased(View param1View, float param1Float1, float param1Float2) {}
    
    public abstract boolean tryCaptureView(View param1View, int param1Int);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\swipeback\ViewDragHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */