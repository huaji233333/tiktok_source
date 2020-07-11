package com.tt.miniapp.view.loading;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.support.v4.view.b.f;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;

public class NewLoadingView extends RelativeLayout {
  ObjectAnimator animatorAlpha;
  
  ObjectAnimator animatorAlpha1;
  
  ObjectAnimator animatorAlpha2;
  
  ObjectAnimator animatorColor;
  
  ObjectAnimator animatorColor1;
  
  ObjectAnimator animatorColor2;
  
  ObjectAnimator animatorScaleX;
  
  ObjectAnimator animatorScaleX1;
  
  ObjectAnimator animatorScaleX2;
  
  ObjectAnimator animatorScaleY;
  
  ObjectAnimator animatorScaleY1;
  
  ObjectAnimator animatorScaleY2;
  
  FrameLayout frameLayout;
  
  RelativeLayout loading1Ll;
  
  RelativeLayout loading2Ll;
  
  RelativeLayout loading3Ll;
  
  LoadingPoint loadingPoint1;
  
  int[] loadingPoint1AnimColor = new int[] { Color.parseColor("#4dFFFFFF"), Color.parseColor("#33FFFFFF") };
  
  int loadingPoint1OrgColor;
  
  LoadingPoint loadingPoint2;
  
  int[] loadingPoint2AnimColor = new int[] { Color.parseColor("#4dFFFFFF"), Color.parseColor("#33FFFFFF") };
  
  int loadingPoint2OrgColor;
  
  LoadingPoint loadingPoint3;
  
  int[] loadingPoint3AnimColor = new int[] { Color.parseColor("#4dFFFFFF"), Color.parseColor("#33FFFFFF") };
  
  int loadingPoint3OrgColor = Color.parseColor("#33FFFFFF");
  
  AnimatorSet point1Set;
  
  AnimatorSet point2Set;
  
  AnimatorSet point3Set;
  
  AnimatorSet set;
  
  public NewLoadingView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public NewLoadingView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }
  
  public void beforePrepare(final double deltaY) {
    this.loadingPoint1.post(new Runnable() {
          public void run() {
            NewLoadingView.this.loadingPoint1.setColor(NewLoadingView.this.loadingPoint1OrgColor);
            NewLoadingView.this.loadingPoint1.setAlpha(1.0F);
            NewLoadingView.this.loadingPoint1.setScaleX(1.0F);
            NewLoadingView.this.loadingPoint1.setScaleY(1.0F);
            LoadingPoint loadingPoint = NewLoadingView.this.loadingPoint1;
            double d1 = UIUtils.dip2Px(NewLoadingView.this.getContext(), 11.0F);
            double d2 = deltaY;
            Double.isNaN(d1);
            loadingPoint.smoothScrollTo((int)(d1 * d2), 0);
          }
        });
    this.loadingPoint2.post(new Runnable() {
          public void run() {
            NewLoadingView.this.loadingPoint2.setColor(NewLoadingView.this.loadingPoint2OrgColor);
            NewLoadingView.this.loadingPoint2.setAlpha(1.0F);
            NewLoadingView.this.loadingPoint2.setScaleX(1.0F);
            NewLoadingView.this.loadingPoint2.setScaleY(1.0F);
            NewLoadingView.this.loadingPoint2.smoothScrollTo(0, 0);
          }
        });
    this.loadingPoint3.post(new Runnable() {
          public void run() {
            NewLoadingView.this.loadingPoint3.setColor(NewLoadingView.this.loadingPoint3OrgColor);
            NewLoadingView.this.loadingPoint3.setAlpha(1.0F);
            NewLoadingView.this.loadingPoint3.setScaleX(1.0F);
            NewLoadingView.this.loadingPoint3.setScaleY(1.0F);
            LoadingPoint loadingPoint = NewLoadingView.this.loadingPoint3;
            double d1 = -UIUtils.dip2Px(NewLoadingView.this.getContext(), 12.0F);
            double d2 = deltaY;
            Double.isNaN(d1);
            loadingPoint.smoothScrollTo((int)(d1 * d2), 0);
          }
        });
    invalidate();
  }
  
  protected void initView(Context paramContext) {
    this.loadingPoint1 = new LoadingPoint(paramContext);
    this.loadingPoint2 = new LoadingPoint(paramContext);
    this.loadingPoint3 = new LoadingPoint(paramContext);
    this.loading1Ll = new RelativeLayout(paramContext);
    RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int)UIUtils.dip2Px(paramContext, 5.0F), (int)UIUtils.dip2Px(paramContext, 8.0F));
    layoutParams2.addRule(14);
    this.loading1Ll.addView(this.loadingPoint1, (ViewGroup.LayoutParams)layoutParams2);
    this.loading2Ll = new RelativeLayout(paramContext);
    this.loading2Ll.addView(this.loadingPoint2, (ViewGroup.LayoutParams)layoutParams2);
    this.loading3Ll = new RelativeLayout(paramContext);
    this.loading3Ll.addView(this.loadingPoint3, (ViewGroup.LayoutParams)layoutParams2);
    this.frameLayout = new FrameLayout(paramContext);
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
    this.frameLayout.addView((View)this.loading1Ll, (ViewGroup.LayoutParams)layoutParams);
    this.frameLayout.addView((View)this.loading2Ll, (ViewGroup.LayoutParams)layoutParams);
    this.frameLayout.addView((View)this.loading3Ll, (ViewGroup.LayoutParams)layoutParams);
    RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(-1, -2);
    layoutParams1.addRule(13);
    addView((View)this.frameLayout, (ViewGroup.LayoutParams)layoutParams1);
  }
  
  public void setLoadingPoint1AnimColor(int[] paramArrayOfint) {
    this.loadingPoint1AnimColor = paramArrayOfint;
  }
  
  public void setLoadingPoint1OrgColor(int paramInt) {
    this.loadingPoint1OrgColor = paramInt;
  }
  
  public void setLoadingPoint2AnimColor(int[] paramArrayOfint) {
    this.loadingPoint2AnimColor = paramArrayOfint;
  }
  
  public void setLoadingPoint2OrgColor(int paramInt) {
    this.loadingPoint2OrgColor = paramInt;
  }
  
  public void setLoadingPoint3AnimColor(int[] paramArrayOfint) {
    this.loadingPoint3AnimColor = paramArrayOfint;
  }
  
  public void setLoadingPoint3OrgColor(int paramInt) {
    this.loadingPoint3OrgColor = paramInt;
  }
  
  public void start() {
    LoadingPoint loadingPoint = this.loadingPoint1;
    int[] arrayOfInt = this.loadingPoint1AnimColor;
    this.animatorColor = ObjectAnimator.ofInt(loadingPoint, "color", new int[] { arrayOfInt[0], arrayOfInt[1] });
    this.animatorColor.setDuration(300L);
    this.animatorColor.setEvaluator((TypeEvaluator)new ArgbEvaluator());
    this.animatorColor.setRepeatMode(2);
    this.animatorColor.setRepeatCount(-1);
    this.animatorScaleX = ObjectAnimator.ofFloat(this.loadingPoint1, "scaleX", new float[] { 1.0F, 1.2F });
    this.animatorScaleX.setDuration(300L);
    this.animatorScaleX.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.animatorScaleX.setRepeatMode(2);
    this.animatorScaleX.setRepeatCount(-1);
    this.animatorScaleY = ObjectAnimator.ofFloat(this.loadingPoint1, "scaleY", new float[] { 1.0F, 1.2F });
    this.animatorScaleY.setDuration(300L);
    this.animatorScaleY.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.animatorScaleY.setRepeatMode(2);
    this.animatorScaleY.setRepeatCount(-1);
    this.animatorAlpha = ObjectAnimator.ofFloat(this.loadingPoint1, "alpha", new float[] { 0.0F, 1.0F });
    this.animatorAlpha.setDuration(300L);
    this.animatorAlpha.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.animatorAlpha.setRepeatMode(2);
    this.animatorAlpha.setRepeatCount(-1);
    this.point1Set = new AnimatorSet();
    this.point1Set.playTogether(new Animator[] { (Animator)this.animatorColor, (Animator)this.animatorScaleX, (Animator)this.animatorScaleY });
    loadingPoint = this.loadingPoint2;
    arrayOfInt = this.loadingPoint2AnimColor;
    this.animatorColor1 = ObjectAnimator.ofInt(loadingPoint, "color", new int[] { arrayOfInt[0], arrayOfInt[1] });
    this.animatorColor1.setDuration(300L);
    this.animatorColor1.setEvaluator((TypeEvaluator)new ArgbEvaluator());
    this.animatorColor1.setRepeatMode(2);
    this.animatorColor1.setRepeatCount(-1);
    this.animatorColor1.setStartDelay(100L);
    this.animatorScaleX1 = ObjectAnimator.ofFloat(this.loadingPoint2, "scaleX", new float[] { 1.0F, 1.2F });
    this.animatorScaleX1.setDuration(300L);
    this.animatorScaleX1.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.animatorScaleX1.setRepeatMode(2);
    this.animatorScaleX1.setRepeatCount(-1);
    this.animatorScaleY1 = ObjectAnimator.ofFloat(this.loadingPoint2, "scaleY", new float[] { 1.0F, 1.2F });
    this.animatorScaleY1.setDuration(300L);
    this.animatorScaleY1.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.animatorScaleY1.setRepeatMode(2);
    this.animatorScaleY1.setRepeatCount(-1);
    this.animatorAlpha1 = ObjectAnimator.ofFloat(this.loadingPoint2, "alpha", new float[] { 0.0F, 1.0F });
    this.animatorAlpha1.setDuration(300L);
    this.animatorAlpha1.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.animatorAlpha1.setRepeatMode(2);
    this.animatorAlpha1.setRepeatCount(-1);
    this.point2Set = new AnimatorSet();
    this.point2Set.playTogether(new Animator[] { (Animator)this.animatorColor1, (Animator)this.animatorScaleX1, (Animator)this.animatorScaleY1 });
    this.point2Set.setStartDelay(100L);
    loadingPoint = this.loadingPoint3;
    arrayOfInt = this.loadingPoint3AnimColor;
    this.animatorColor2 = ObjectAnimator.ofInt(loadingPoint, "color", new int[] { arrayOfInt[0], arrayOfInt[1] });
    this.animatorColor2.setDuration(300L);
    this.animatorColor2.setEvaluator((TypeEvaluator)new ArgbEvaluator());
    this.animatorColor2.setRepeatMode(2);
    this.animatorColor2.setRepeatCount(-1);
    this.animatorScaleX2 = ObjectAnimator.ofFloat(this.loadingPoint3, "scaleX", new float[] { 1.0F, 1.2F });
    this.animatorScaleX2.setDuration(300L);
    this.animatorScaleX2.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.animatorScaleX2.setRepeatMode(2);
    this.animatorScaleX2.setRepeatCount(-1);
    this.animatorScaleY2 = ObjectAnimator.ofFloat(this.loadingPoint3, "scaleY", new float[] { 1.0F, 1.2F });
    this.animatorScaleY2.setDuration(300L);
    this.animatorScaleY2.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.animatorScaleY2.setRepeatMode(2);
    this.animatorScaleY2.setRepeatCount(-1);
    this.animatorAlpha2 = ObjectAnimator.ofFloat(this.loadingPoint3, "alpha", new float[] { 0.0F, 1.0F });
    this.animatorAlpha2.setDuration(300L);
    this.animatorAlpha2.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.animatorAlpha2.setRepeatMode(2);
    this.animatorAlpha2.setRepeatCount(-1);
    this.point3Set = new AnimatorSet();
    this.point3Set.playTogether(new Animator[] { (Animator)this.animatorColor2, (Animator)this.animatorScaleX2, (Animator)this.animatorScaleY2 });
    this.point3Set.setStartDelay(200L);
    this.set = new AnimatorSet();
    this.set.playTogether(new Animator[] { (Animator)this.point1Set, (Animator)this.point2Set, (Animator)this.point3Set });
    Path path = new Path();
    path.moveTo(0.0F, 0.0F);
    path.cubicTo(0.48F, 0.04F, 0.52F, 0.96F, 1.0F, 1.0F);
    this.set.setInterpolator((TimeInterpolator)f.a(0.48F, 0.04F, 0.52F, 0.96F));
    this.set.start();
  }
  
  public void stop() {
    AnimatorSet animatorSet = this.set;
    if (animatorSet != null) {
      if (animatorSet.isRunning()) {
        AppBrandLogger.d("tma_NewLoadingView", new Object[] { "set.cancelDownload()" });
        this.set.removeAllListeners();
        this.set.end();
        this.set.cancel();
      } 
      this.set = null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\loading\NewLoadingView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */