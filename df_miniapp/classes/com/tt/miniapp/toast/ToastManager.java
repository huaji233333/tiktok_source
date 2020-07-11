package com.tt.miniapp.toast;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToastManager {
  private static Handler mainHandler = new Handler(Looper.getMainLooper());
  
  static List<Toast> toastContainer = new ArrayList<Toast>();
  
  public static void addToast(Toast paramToast) {
    if (paramToast != null)
      toastContainer.add(paramToast); 
  }
  
  public static void clearToast() {
    mainHandler.post(new Runnable() {
          public final void run() {
            Iterator<ToastManager.Toast> iterator = ToastManager.toastContainer.iterator();
            while (iterator.hasNext())
              ((ToastManager.Toast)iterator.next()).cancel(); 
          }
        });
  }
  
  public static void hideToast() {
    mainHandler.post(new Runnable() {
          public final void run() {
            Iterator<?> iterator = (new ArrayList(ToastManager.toastContainer)).iterator();
            while (iterator.hasNext())
              ((ToastManager.Toast)iterator.next()).cancel(); 
          }
        });
  }
  
  private static boolean isSupportCustomToast(Context paramContext) {
    return (paramContext instanceof Activity && Toast.getContainer((Activity)paramContext) != null);
  }
  
  public static void removeToast(Toast paramToast) {
    if (paramToast != null)
      toastContainer.remove(paramToast); 
  }
  
  public static void showShortToast(Context paramContext, CharSequence paramCharSequence) {
    showToast(paramContext, paramCharSequence, 2000L, null);
  }
  
  public static void showToast(Context paramContext, CharSequence paramCharSequence, long paramLong) {
    showToast(paramContext, paramCharSequence, paramLong, null);
  }
  
  public static void showToast(final Context context, final CharSequence text, final long duration, final String icon) {
    if (!isSupportCustomToast(context)) {
      AppBrandLogger.d("tma_ToastManager", new Object[] { "isSupportCustomToast not suppot" });
      mainHandler.post(new Runnable() {
            public final void run() {
              _lancet.com_ss_android_ugc_aweme_lancet_DesignBugFixLancet_show(android.widget.Toast.makeText(context, text, 1));
            }
            
            class null {}
          });
      return;
    } 
    mainHandler.post(new Runnable() {
          public final void run() {
            Iterator<?> iterator = (new ArrayList(ToastManager.toastContainer)).iterator();
            while (iterator.hasNext())
              ((ToastManager.Toast)iterator.next()).cancel(); 
            ToastManager.Toast toast = ToastManager.Toast.makeText((Activity)context, text, duration, icon);
            toast.setGravity(17);
            toast.show();
          }
        });
  }
  
  public static class Toast {
    public WeakReference<Activity> activityWeakReference;
    
    public boolean isShow;
    
    private long mDuration;
    
    public AlphaAnimation mFadeOutAnimation;
    
    public int mGravity;
    
    private final Runnable mHideAnimationRunnable = new Runnable() {
        public void run() {
          if (ToastManager.Toast.this.isShow)
            ToastManager.Toast.this.mToastView.startAnimation((Animation)ToastManager.Toast.this.mFadeOutAnimation); 
        }
      };
    
    private Runnable mHideToastRunnable = new Runnable() {
        public void run() {
          Activity activity = ToastManager.Toast.this.activityWeakReference.get();
          if (ToastManager.Toast.this.mToastView != null && ToastManager.Toast.this.mToastView.getParent() != null) {
            if (ToastManager.Toast.this.mLoadingProgress != null)
              ToastManager.Toast.this.mLoadingProgress.setVisibility(8); 
            ToastManager.Toast.this.mToastView.clearAnimation();
            if (activity != null) {
              ViewGroup viewGroup = ToastManager.Toast.getContainer(activity);
              if (viewGroup != null)
                viewGroup.removeView(ToastManager.Toast.this.mToastView); 
            } 
            ToastManager.removeToast(ToastManager.Toast.this);
            ToastManager.Toast.this.isShow = false;
            ThreadUtil.cancelUIRunnable(this);
          } 
        }
      };
    
    private WindowManager.LayoutParams mLayoutParams;
    
    public ProgressBar mLoadingProgress;
    
    private Runnable mShowToastRunnable = new Runnable() {
        public void run() {
          Activity activity = ToastManager.Toast.this.activityWeakReference.get();
          View view = ToastManager.Toast.this.mToastView;
          if (view == null)
            return; 
          if (activity == null)
            return; 
          DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
          ViewGroup viewGroup = ToastManager.Toast.getContainer(activity);
          if (viewGroup != null && view.getParent() != null)
            viewGroup.removeView(view); 
          FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
          if (ToastManager.Toast.this.mGravity == 0) {
            layoutParams.gravity = 81;
            layoutParams.bottomMargin = (int)TypedValue.applyDimension(1, 80.0F, displayMetrics);
          } else {
            layoutParams.gravity = ToastManager.Toast.this.mGravity;
          } 
          if (viewGroup != null)
            viewGroup.addView(ToastManager.Toast.this.mToastView, (ViewGroup.LayoutParams)layoutParams); 
          ToastManager.Toast.this.startAnimation();
          ToastManager.addToast(ToastManager.Toast.this);
        }
      };
    
    public View mToastView;
    
    public Toast(Activity param1Activity) {
      this.activityWeakReference = new WeakReference<Activity>(param1Activity);
    }
    
    public static ViewGroup getContainer(Activity param1Activity) {
      return (ViewGroup)param1Activity.findViewById(16908290);
    }
    
    public static Toast makeText(Activity param1Activity, CharSequence param1CharSequence, long param1Long, String param1String) {
      Toast toast = new Toast(param1Activity);
      View view = View.inflate((Context)param1Activity, 2097676340, null);
      TextView textView = (TextView)view.findViewById(2097545396);
      textView.setMaxLines(2);
      textView.setEllipsize(TextUtils.TruncateAt.END);
      textView.setMinWidth((int)UIUtils.dip2Px((Context)param1Activity, 108.0F));
      textView.setMaxWidth((int)UIUtils.dip2Px((Context)param1Activity, 168.0F));
      textView.setText(param1CharSequence);
      if (TextUtils.equals(param1String, "success")) {
        ImageView imageView = (ImageView)view.findViewById(2097545309);
        imageView.setVisibility(0);
        imageView.setImageDrawable(param1Activity.getResources().getDrawable(2097479805));
        textView.setMaxLines(1);
      } else if (TextUtils.equals(param1String, "loading")) {
        view.findViewById(2097545329).setVisibility(0);
        textView.setMaxLines(1);
      } else if (TextUtils.equals(param1String, "fail")) {
        ImageView imageView = (ImageView)view.findViewById(2097545309);
        imageView.setVisibility(0);
        imageView.setImageDrawable(param1Activity.getResources().getDrawable(2097479803));
        textView.setMaxLines(1);
      } 
      toast.setView(view);
      toast.setDuration(param1Long);
      return toast;
    }
    
    private void setLoadingView(ProgressBar param1ProgressBar) {
      this.mLoadingProgress = param1ProgressBar;
    }
    
    public void cancel() {
      ThreadUtil.cancelUIRunnable(this.mShowToastRunnable);
      ThreadUtil.runOnUIThread(this.mHideToastRunnable);
    }
    
    public long getDuration() {
      return this.mDuration;
    }
    
    public int getGravity() {
      return this.mGravity;
    }
    
    public View getView() {
      return this.mToastView;
    }
    
    public void setDuration(long param1Long) {
      if (param1Long == 0L) {
        this.mDuration = 2000L;
        return;
      } 
      if (param1Long == 1L) {
        this.mDuration = 3500L;
        return;
      } 
      this.mDuration = param1Long;
    }
    
    public void setGravity(int param1Int) {
      this.mGravity = param1Int;
    }
    
    public void setIcon(int param1Int) {
      Activity activity = this.activityWeakReference.get();
      if (activity != null)
        setIcon(activity.getResources().getDrawable(param1Int)); 
    }
    
    public void setIcon(Drawable param1Drawable) {
      View view = this.mToastView;
      if (view != null) {
        ImageView imageView = (ImageView)view.findViewById(2097545309);
        TextView textView = (TextView)this.mToastView.findViewById(2097545396);
        if (imageView != null) {
          imageView.setImageDrawable(param1Drawable);
          if (param1Drawable == null) {
            imageView.setVisibility(8);
            textView.setMaxLines(2);
            return;
          } 
          imageView.setVisibility(0);
          textView.setMaxLines(1);
          return;
        } 
        throw new RuntimeException("This Toast was not created with Toast.makeText()");
      } 
      throw new RuntimeException("This Toast was not created with Toast.makeText()");
    }
    
    public void setLayoutParams(WindowManager.LayoutParams param1LayoutParams) {
      this.mLayoutParams = param1LayoutParams;
    }
    
    public void setText(int param1Int) {
      Activity activity = this.activityWeakReference.get();
      if (activity != null)
        setText(activity.getText(param1Int)); 
    }
    
    public void setText(CharSequence param1CharSequence) {
      View view = this.mToastView;
      if (view != null) {
        TextView textView = (TextView)view.findViewById(2097545396);
        if (textView != null) {
          textView.setText(param1CharSequence);
          return;
        } 
        throw new RuntimeException("This Toast was not created with Toast.makeText()");
      } 
      throw new RuntimeException("This Toast was not created with Toast.makeText()");
    }
    
    public void setView(View param1View) {
      this.mToastView = param1View;
    }
    
    public void show() {
      if (this.mToastView != null) {
        ThreadUtil.runOnUIThread(this.mShowToastRunnable);
        return;
      } 
      throw new RuntimeException("setView must have been called");
    }
    
    public void startAnimation() {
      if (this.isShow)
        return; 
      this.isShow = true;
      AlphaAnimation alphaAnimation = new AlphaAnimation(0.0F, 1.0F);
      this.mFadeOutAnimation = new AlphaAnimation(1.0F, 0.0F);
      this.mFadeOutAnimation.setDuration(200L);
      alphaAnimation.setDuration(200L);
      this.mToastView.setVisibility(0);
      AppBrandLogger.d("tma_ToastManager", new Object[] { "startAnimation ", Long.valueOf(getDuration() - 200L), " ", Long.valueOf(getDuration()) });
      ThreadUtil.runOnUIThread(this.mHideAnimationRunnable, getDuration() - 200L);
      ThreadUtil.runOnUIThread(this.mHideToastRunnable, getDuration());
    }
  }
  
  class null implements Runnable {
    public void run() {
      Activity activity = this.this$0.activityWeakReference.get();
      if (this.this$0.mToastView != null && this.this$0.mToastView.getParent() != null) {
        if (this.this$0.mLoadingProgress != null)
          this.this$0.mLoadingProgress.setVisibility(8); 
        this.this$0.mToastView.clearAnimation();
        if (activity != null) {
          ViewGroup viewGroup = ToastManager.Toast.getContainer(activity);
          if (viewGroup != null)
            viewGroup.removeView(this.this$0.mToastView); 
        } 
        ToastManager.removeToast(this.this$0);
        this.this$0.isShow = false;
        ThreadUtil.cancelUIRunnable(this);
      } 
    }
  }
  
  class null implements Runnable {
    public void run() {
      Activity activity = this.this$0.activityWeakReference.get();
      View view = this.this$0.mToastView;
      if (view == null)
        return; 
      if (activity == null)
        return; 
      DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
      ViewGroup viewGroup = ToastManager.Toast.getContainer(activity);
      if (viewGroup != null && view.getParent() != null)
        viewGroup.removeView(view); 
      FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
      if (this.this$0.mGravity == 0) {
        layoutParams.gravity = 81;
        layoutParams.bottomMargin = (int)TypedValue.applyDimension(1, 80.0F, displayMetrics);
      } else {
        layoutParams.gravity = this.this$0.mGravity;
      } 
      if (viewGroup != null)
        viewGroup.addView(this.this$0.mToastView, (ViewGroup.LayoutParams)layoutParams); 
      this.this$0.startAnimation();
      ToastManager.addToast(this.this$0);
    }
  }
  
  class null implements Runnable {
    public void run() {
      if (this.this$0.isShow)
        this.this$0.mToastView.startAnimation((Animation)this.this$0.mFadeOutAnimation); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\toast\ToastManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */