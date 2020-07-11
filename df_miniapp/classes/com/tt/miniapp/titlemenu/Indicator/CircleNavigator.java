package com.tt.miniapp.titlemenu.Indicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v4.content.c;
import android.view.View;
import com.tt.miniapp.view.interpolator.CubicBezierInterpolator;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.UIUtils;
import java.util.ArrayList;
import java.util.List;

public class CircleNavigator extends View implements IPagerNavigator {
  public List<Point> mCirclePoints = new ArrayList<Point>();
  
  private int mCircleRadius;
  
  private int mCircleSpacing;
  
  public int mCurrentIndex;
  
  private ValueAnimator mCurrentNavigatorAnimator;
  
  public int mLastIndex;
  
  private ValueAnimator mLastNavigatorAnimator;
  
  private Paint mPaint = new Paint(1);
  
  public int mSelectedColor;
  
  private int mTotalCount;
  
  public int mUnselectedColor;
  
  public CircleNavigator(Context paramContext) {
    super(paramContext);
    init(paramContext);
  }
  
  private void drawCircles(Canvas paramCanvas) {
    this.mPaint.setStyle(Paint.Style.FILL);
    int j = this.mCirclePoints.size();
    for (int i = 0; i < j; i++) {
      PointF pointF = ((Point)this.mCirclePoints.get(i)).pointF;
      this.mPaint.setColor(((Point)this.mCirclePoints.get(i)).color);
      paramCanvas.drawCircle(pointF.x, pointF.y, this.mCircleRadius, this.mPaint);
    } 
  }
  
  private void init(Context paramContext) {
    this.mCircleRadius = (int)UIUtils.dip2Px(paramContext, 3.0F);
    this.mCircleSpacing = (int)UIUtils.dip2Px(paramContext, 8.0F);
    this.mSelectedColor = c.c(paramContext, 2097348616);
    this.mUnselectedColor = Color.parseColor("#1A000000");
  }
  
  private int measureHeight(int paramInt) {
    int i = View.MeasureSpec.getMode(paramInt);
    paramInt = View.MeasureSpec.getSize(paramInt);
    if (i != Integer.MIN_VALUE && i != 0) {
      if (i != 1073741824)
        return 0; 
    } else {
      paramInt = this.mCircleRadius * 2 + getPaddingTop() + getPaddingBottom();
    } 
    return paramInt;
  }
  
  private int measureWidth(int paramInt) {
    int i = View.MeasureSpec.getMode(paramInt);
    paramInt = View.MeasureSpec.getSize(paramInt);
    if (i != Integer.MIN_VALUE && i != 0) {
      if (i != 1073741824)
        return 0; 
    } else {
      paramInt = this.mTotalCount;
      i = this.mCircleRadius;
      int j = this.mCircleSpacing;
      int k = getPaddingLeft();
      paramInt = getPaddingRight() + i * paramInt * 2 + (paramInt - 1) * j + k;
    } 
    return paramInt;
  }
  
  private void prepareCirclePoints() {
    this.mCirclePoints.clear();
    if (this.mTotalCount > 0) {
      int k = (int)(getHeight() / 2.0F + 0.5F);
      int m = this.mCircleRadius;
      int n = this.mCircleSpacing;
      int j = m + getPaddingLeft();
      for (int i = 0; i < this.mTotalCount; i++) {
        PointF pointF = new PointF(j, k);
        this.mCirclePoints.add(new Point(pointF, this.mUnselectedColor));
        j += m * 2 + n;
      } 
      ((Point)this.mCirclePoints.get(this.mCurrentIndex)).color = this.mSelectedColor;
    } 
  }
  
  private void startAnimation() {
    if (this.mLastNavigatorAnimator == null) {
      this.mLastNavigatorAnimator = ValueAnimator.ofArgb(new int[] { this.mSelectedColor, this.mUnselectedColor });
      this.mLastNavigatorAnimator.setInterpolator((TimeInterpolator)new CubicBezierInterpolator(0.25F, 0.1F, 0.25F, 0.1F));
      this.mLastNavigatorAnimator.setDuration(400L);
      this.mLastNavigatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
              int i = ((Integer)param1ValueAnimator.getAnimatedValue()).intValue();
              ((CircleNavigator.Point)CircleNavigator.this.mCirclePoints.get(CircleNavigator.this.mLastIndex)).color = i;
            }
          });
    } 
    if (this.mCurrentNavigatorAnimator == null) {
      this.mCurrentNavigatorAnimator = ValueAnimator.ofArgb(new int[] { this.mUnselectedColor, this.mSelectedColor });
      this.mCurrentNavigatorAnimator.setInterpolator((TimeInterpolator)new CubicBezierInterpolator(0.25D, 0.1D, 0.25D, 0.1D));
      this.mCurrentNavigatorAnimator.setDuration(400L);
      this.mCurrentNavigatorAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param1Animator) {
              super.onAnimationEnd(param1Animator);
              AppbrandContext.mainHandler.post(new Runnable() {
                    public void run() {
                      for (int i = 0; i < CircleNavigator.this.mCirclePoints.size(); i++)
                        ((CircleNavigator.Point)CircleNavigator.this.mCirclePoints.get(i)).color = CircleNavigator.this.mUnselectedColor; 
                      ((CircleNavigator.Point)CircleNavigator.this.mCirclePoints.get(CircleNavigator.this.mCurrentIndex)).color = CircleNavigator.this.mSelectedColor;
                      CircleNavigator.this.invalidate();
                    }
                  });
            }
          });
      this.mCurrentNavigatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
              int i = ((Integer)param1ValueAnimator.getAnimatedValue()).intValue();
              ((CircleNavigator.Point)CircleNavigator.this.mCirclePoints.get(CircleNavigator.this.mCurrentIndex)).color = i;
              CircleNavigator.this.invalidate();
            }
          });
    } 
    this.mLastNavigatorAnimator.start();
    this.mCurrentNavigatorAnimator.start();
  }
  
  public int getCircleCount() {
    return this.mTotalCount;
  }
  
  public int getCurrentIndex() {
    return this.mCurrentIndex;
  }
  
  public int getRadius() {
    return this.mCircleRadius;
  }
  
  public int getSelectedColor() {
    return this.mSelectedColor;
  }
  
  public int getSpacing() {
    return this.mCircleSpacing;
  }
  
  public int getUnselectedColor() {
    return this.mUnselectedColor;
  }
  
  public void notifyDataSetChanged() {
    prepareCirclePoints();
    invalidate();
  }
  
  public void onAttachToMagicIndicator() {}
  
  public void onDetachFromMagicIndicator() {}
  
  protected void onDraw(Canvas paramCanvas) {
    drawCircles(paramCanvas);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    prepareCirclePoints();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    setMeasuredDimension(measureWidth(paramInt1), measureHeight(paramInt2));
  }
  
  public void onPageScrollStateChanged(int paramInt) {}
  
  public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {}
  
  public void onPageSelected(int paramInt) {
    setCurrentIndex(paramInt);
  }
  
  public void setCircleCount(int paramInt) {
    this.mTotalCount = paramInt;
    prepareCirclePoints();
    invalidate();
  }
  
  public void setCurrentIndex(int paramInt) {
    this.mLastIndex = this.mCurrentIndex;
    this.mCurrentIndex = paramInt;
    startAnimation();
  }
  
  public void setRadius(int paramInt) {
    this.mCircleRadius = paramInt;
    prepareCirclePoints();
    invalidate();
  }
  
  public void setSelectedColor(int paramInt) {
    this.mSelectedColor = paramInt;
    invalidate();
  }
  
  public void setSpacing(int paramInt) {
    this.mCircleSpacing = paramInt;
    prepareCirclePoints();
    invalidate();
  }
  
  public void setUnselectedColor(int paramInt) {
    this.mUnselectedColor = paramInt;
  }
  
  public class Point {
    int color;
    
    PointF pointF;
    
    Point(PointF param1PointF, int param1Int) {
      this.pointF = param1PointF;
      this.color = param1Int;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\Indicator\CircleNavigator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */