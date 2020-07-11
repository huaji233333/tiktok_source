package com.tt.miniapp.base.ui.viewwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.util.DebugUtil;
import d.a.m;
import d.f.a.b;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.x;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class ViewWindowRoot<T extends ViewWindow> {
  public static final Companion Companion = new Companion(null);
  
  public Activity activity;
  
  private final ViewWindowContainer container;
  
  private final Context mContext;
  
  private final LinkedList<T> mViewWindowList;
  
  public ViewWindowRoot(Context paramContext) {
    this.mContext = paramContext;
    this.container = new ViewWindowContainer(this.mContext);
    this.mViewWindowList = new LinkedList<T>();
    if (!ThreadUtil.isUIThread())
      DebugUtil.logOrThrow("ViewWindowRoot", new Object[] { "Init must be called on UI Thread." }); 
    this.container.setOnAttachedToWindowListener(new b<Context, x>() {
          public final void invoke(Context param1Context) {
            // Byte code:
            //   0: aload_1
            //   1: ldc 'it'
            //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
            //   6: aload_0
            //   7: getfield this$0 : Lcom/tt/miniapp/base/ui/viewwindow/ViewWindowRoot;
            //   10: invokevirtual getActivity : ()Landroid/app/Activity;
            //   13: ifnull -> 132
            //   16: aload_0
            //   17: getfield this$0 : Lcom/tt/miniapp/base/ui/viewwindow/ViewWindowRoot;
            //   20: invokevirtual getContainer : ()Lcom/tt/miniapp/base/ui/viewwindow/ViewWindowContainer;
            //   23: checkcast android/view/ViewGroup
            //   26: astore #4
            //   28: aload_0
            //   29: getfield this$0 : Lcom/tt/miniapp/base/ui/viewwindow/ViewWindowRoot;
            //   32: invokevirtual getActivity : ()Landroid/app/Activity;
            //   35: astore_1
            //   36: aload_1
            //   37: ifnull -> 57
            //   40: aload_1
            //   41: invokevirtual getWindow : ()Landroid/view/Window;
            //   44: astore_1
            //   45: aload_1
            //   46: ifnull -> 57
            //   49: aload_1
            //   50: invokevirtual getDecorView : ()Landroid/view/View;
            //   53: astore_1
            //   54: goto -> 59
            //   57: aconst_null
            //   58: astore_1
            //   59: iconst_0
            //   60: istore_3
            //   61: iload_3
            //   62: istore_2
            //   63: aload #4
            //   65: instanceof android/view/ViewGroup
            //   68: ifeq -> 114
            //   71: aload #4
            //   73: aload_1
            //   74: if_acmpne -> 82
            //   77: iconst_1
            //   78: istore_2
            //   79: goto -> 114
            //   82: aload #4
            //   84: invokevirtual getParent : ()Landroid/view/ViewParent;
            //   87: astore #5
            //   89: aload #5
            //   91: astore #4
            //   93: aload #5
            //   95: instanceof android/view/ViewGroup
            //   98: ifne -> 104
            //   101: aconst_null
            //   102: astore #4
            //   104: aload #4
            //   106: checkcast android/view/ViewGroup
            //   109: astore #4
            //   111: goto -> 61
            //   114: iload_2
            //   115: ifeq -> 119
            //   118: return
            //   119: new java/lang/RuntimeException
            //   122: dup
            //   123: ldc 'Activity and container not match.'
            //   125: invokespecial <init> : (Ljava/lang/String;)V
            //   128: checkcast java/lang/Throwable
            //   131: athrow
            //   132: new java/lang/RuntimeException
            //   135: dup
            //   136: ldc 'Must call bindActivity before adding container to the view hierarchy.'
            //   138: invokespecial <init> : (Ljava/lang/String;)V
            //   141: checkcast java/lang/Throwable
            //   144: astore_1
            //   145: goto -> 150
            //   148: aload_1
            //   149: athrow
            //   150: goto -> 148
          }
        });
    ViewWindowManager.regAsViewWindowContainer(this);
  }
  
  public final void bindActivity(Activity paramActivity) {
    l.b(paramActivity, "a");
    this.activity = paramActivity;
  }
  
  public final void closeAllViewWindow() {
    for (ViewWindow viewWindow : this.mViewWindowList) {
      viewWindow.doPause(1);
      this.container.removeView((View)viewWindow);
      viewWindow.doFinish();
    } 
    this.mViewWindowList.clear();
  }
  
  public final void closeViewWindow(T paramT) {
    l.b(paramT, "v");
    this.mViewWindowList.remove(paramT);
    this.container.removeView((View)paramT);
    paramT.doPause(1);
    T t = getTopView();
    if (t != null) {
      t.doResume(1);
    } else {
      onViewWindowAllClosed();
    } 
    paramT.doFinish();
  }
  
  public final void closeViewWindowWithAnim(T paramT, int paramInt, Animation.AnimationListener paramAnimationListener) {
    l.b(paramT, "v");
    this.mViewWindowList.remove(paramT);
    paramT.doPause(1);
    T t = getTopView();
    if (t != null) {
      t.doResume(1);
    } else {
      onViewWindowAllClosed();
    } 
    Animation animation = AnimationUtils.loadAnimation(this.mContext, paramInt);
    animation.setAnimationListener(new ViewWindowRoot$closeViewWindowWithAnim$$inlined$apply$lambda$1(paramAnimationListener, (ViewWindow)paramT));
    paramT.startAnimation(animation);
  }
  
  public final void dispatchActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    l.b(paramIntent, "data");
    Iterator<T> iterator = this.mViewWindowList.iterator();
    while (iterator.hasNext())
      ((ViewWindow)iterator.next()).doOnActivityResult(paramInt1, paramInt2, paramIntent); 
  }
  
  public final void dispatchOnActivityDestroy() {
    Iterator<T> iterator = this.mViewWindowList.iterator();
    while (iterator.hasNext())
      ((ViewWindow)iterator.next()).doOnActivityDestroy(); 
  }
  
  public final void dispatchOnActivityPause() {
    T t = getTopView();
    if (t != null) {
      t.doOnActivityPause();
      t.doPause(0);
    } 
  }
  
  public final void dispatchOnActivityResume() {
    T t = getTopView();
    if (t != null) {
      t.doOnActivityResume();
      t.doResume(0);
    } 
  }
  
  public final Activity getActivity() {
    return this.activity;
  }
  
  public int getActivityLifecycleState() {
    return ViewWindowManager.getActivityLifecycleState(this.activity);
  }
  
  public final ViewWindowContainer getContainer() {
    return this.container;
  }
  
  public final Context getContext() {
    return this.mContext;
  }
  
  protected final Context getMContext() {
    return this.mContext;
  }
  
  protected final LinkedList<T> getMViewWindowList() {
    return this.mViewWindowList;
  }
  
  public final T getTopView() {
    return (T)m.h(this.mViewWindowList);
  }
  
  public final int getViewWindowCount() {
    return this.mViewWindowList.size();
  }
  
  public boolean onBackPressed() {
    if (getViewWindowCount() > 1) {
      T t = getTopView();
      if (t != null && !t.onBackPressed())
        closeViewWindow(t); 
      return true;
    } 
    return false;
  }
  
  public void onChildViewSwipedBack(T paramT) {
    l.b(paramT, "viewWindow");
    closeViewWindow(paramT);
  }
  
  public void onChildViewSwipedCancel(T paramT) {
    l.b(paramT, "viewWindow");
  }
  
  public void onChildViewSwipedStart(T paramT) {
    l.b(paramT, "viewWindow");
  }
  
  public void onViewWindowAllClosed() {}
  
  public final void showViewWindow(T paramT, Bundle paramBundle) {
    l.b(paramT, "v");
    T t = getTopView();
    if (paramT == t)
      return; 
    paramT.setParams(paramBundle);
    if (paramT.getParent() != null) {
      if (paramT.getRoot() == this) {
        this.mViewWindowList.remove(paramT);
        this.mViewWindowList.addLast(paramT);
        paramT.bringToFront();
        if (t != null)
          t.doPause(1); 
        paramT.doResume(1);
        return;
      } 
      throw (Throwable)new RuntimeException("view is already added on window");
    } 
    paramT.doOnCreate(this);
    this.mViewWindowList.addLast(paramT);
    this.container.addView((View)paramT);
    if (t != null)
      t.doPause(1); 
    paramT.doResume(1);
  }
  
  public final void showViewWindowWithAnim(T paramT, Bundle paramBundle, int paramInt, Animation.AnimationListener paramAnimationListener) {
    l.b(paramT, "v");
    T t = getTopView();
    if (paramT == t)
      return; 
    paramT.setParams(paramBundle);
    if (paramT.getParent() != null) {
      if (paramT.getRoot() == this) {
        this.mViewWindowList.remove(paramT);
        this.mViewWindowList.addLast(paramT);
        paramT.bringToFront();
      } else {
        throw (Throwable)new RuntimeException("view is already added on window");
      } 
    } else {
      this.mViewWindowList.addLast(paramT);
      paramT.doOnCreate(this);
      this.container.addView((View)paramT);
    } 
    Animation animation = AnimationUtils.loadAnimation(this.mContext, paramInt);
    animation.setAnimationListener(new ViewWindowRoot$showViewWindowWithAnim$$inlined$apply$lambda$1(paramAnimationListener, (ViewWindow)t, (ViewWindow)paramT));
    paramT.startAnimation(animation);
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public static final class ViewWindowRoot$closeViewWindowWithAnim$$inlined$apply$lambda$1 implements Animation.AnimationListener {
    ViewWindowRoot$closeViewWindowWithAnim$$inlined$apply$lambda$1(Animation.AnimationListener param1AnimationListener, ViewWindow param1ViewWindow) {}
    
    public final void onAnimationEnd(Animation param1Animation) {
      Animation.AnimationListener animationListener = this.$listener$inlined;
      if (animationListener != null)
        animationListener.onAnimationEnd(param1Animation); 
      ViewWindowRoot.this.getContainer().post(new Runnable() {
            public final void run() {
              ViewWindowRoot.this.getContainer().removeView((View)ViewWindowRoot$closeViewWindowWithAnim$$inlined$apply$lambda$1.this.$v$inlined);
              ViewWindowRoot$closeViewWindowWithAnim$$inlined$apply$lambda$1.this.$v$inlined.doFinish();
            }
          });
    }
    
    public final void onAnimationRepeat(Animation param1Animation) {
      Animation.AnimationListener animationListener = this.$listener$inlined;
      if (animationListener != null)
        animationListener.onAnimationRepeat(param1Animation); 
    }
    
    public final void onAnimationStart(Animation param1Animation) {
      Animation.AnimationListener animationListener = this.$listener$inlined;
      if (animationListener != null)
        animationListener.onAnimationStart(param1Animation); 
    }
  }
  
  static final class null implements Runnable {
    public final void run() {
      ViewWindowRoot.this.getContainer().removeView((View)ViewWindowRoot$closeViewWindowWithAnim$$inlined$apply$lambda$1.this.$v$inlined);
      ViewWindowRoot$closeViewWindowWithAnim$$inlined$apply$lambda$1.this.$v$inlined.doFinish();
    }
  }
  
  public static final class ViewWindowRoot$showViewWindowWithAnim$$inlined$apply$lambda$1 implements Animation.AnimationListener {
    ViewWindowRoot$showViewWindowWithAnim$$inlined$apply$lambda$1(Animation.AnimationListener param1AnimationListener, ViewWindow param1ViewWindow1, ViewWindow param1ViewWindow2) {}
    
    public final void onAnimationEnd(Animation param1Animation) {
      Animation.AnimationListener animationListener = this.$listener$inlined;
      if (animationListener != null)
        animationListener.onAnimationEnd(param1Animation); 
      ViewWindowRoot.this.getContainer().post(new Runnable() {
            public final void run() {
              ViewWindow viewWindow = ViewWindowRoot$showViewWindowWithAnim$$inlined$apply$lambda$1.this.$topView$inlined;
              if (viewWindow != null)
                viewWindow.doPause(1); 
              ViewWindowRoot$showViewWindowWithAnim$$inlined$apply$lambda$1.this.$v$inlined.doResume(1);
            }
          });
    }
    
    public final void onAnimationRepeat(Animation param1Animation) {
      Animation.AnimationListener animationListener = this.$listener$inlined;
      if (animationListener != null)
        animationListener.onAnimationRepeat(param1Animation); 
    }
    
    public final void onAnimationStart(Animation param1Animation) {
      Animation.AnimationListener animationListener = this.$listener$inlined;
      if (animationListener != null)
        animationListener.onAnimationStart(param1Animation); 
    }
  }
  
  static final class null implements Runnable {
    public final void run() {
      ViewWindow viewWindow = ViewWindowRoot$showViewWindowWithAnim$$inlined$apply$lambda$1.this.$topView$inlined;
      if (viewWindow != null)
        viewWindow.doPause(1); 
      ViewWindowRoot$showViewWindowWithAnim$$inlined$apply$lambda$1.this.$v$inlined.doResume(1);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\bas\\ui\viewwindow\ViewWindowRoot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */