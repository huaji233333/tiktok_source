package com.tt.miniapp.base.ui.viewwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ViewWindow extends ViewWindowDragRightLayout implements IViewWindow {
  public boolean isResumed;
  
  private Bundle mArgs;
  
  protected ViewWindowRoot mRoot;
  
  public ViewWindow(Context paramContext) {
    super(paramContext);
  }
  
  protected final void doFinish() {
    destroyDrag();
    onDestroy();
  }
  
  protected final void doOnActivityDestroy() {
    onActivityDestroy();
  }
  
  protected final void doOnActivityPause() {
    onActivityPause();
  }
  
  protected final void doOnActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  protected final void doOnActivityResume() {
    onActivityResume();
  }
  
  protected final void doOnCreate(ViewWindowRoot paramViewWindowRoot) {
    if (paramViewWindowRoot != null) {
      StringBuilder stringBuilder;
      Type type = paramViewWindowRoot.getClass().getGenericSuperclass();
      if (type != null) {
        Type[] arrayOfType = ((ParameterizedType)type).getActualTypeArguments();
        if (arrayOfType.length > 0) {
          Class clazz = (Class)arrayOfType[0];
          Class<?> clazz1 = getClass();
          if (!clazz.isAssignableFrom(clazz1)) {
            stringBuilder = new StringBuilder("类型不匹配，root期望的泛型参数类型是：");
            stringBuilder.append(clazz);
            stringBuilder.append("，而实际是：");
            stringBuilder.append(clazz1);
            throw new RuntimeException(stringBuilder.toString());
          } 
        } 
      } 
      this.mRoot = (ViewWindowRoot)stringBuilder;
      setClickable(true);
      setDragFinishListener(new ViewWindowDragRightLayout.OnDragListener() {
            public void onScrollFinish(boolean param1Boolean) {
              if (param1Boolean) {
                ViewWindow.this.onSwipeBack();
                return;
              } 
              ViewWindow.this.onSwipeCancel();
            }
            
            public void onScrollStart() {
              ViewWindow.this.onSwipeStart();
            }
          });
      onCreate();
    } 
  }
  
  protected final void doPause(int paramInt) {
    if (this.isResumed) {
      this.isResumed = false;
      onViewPause(paramInt);
    } 
  }
  
  protected final void doResume(int paramInt) {
    if (!this.isResumed) {
      this.isResumed = true;
      onViewResume(paramInt);
    } 
  }
  
  public Activity getActivity() {
    ViewWindowRoot viewWindowRoot = this.mRoot;
    return (viewWindowRoot != null) ? viewWindowRoot.getActivity() : null;
  }
  
  public Intent getIntent() {
    Activity activity = getActivity();
    return (activity != null) ? activity.getIntent() : null;
  }
  
  public Bundle getParams() {
    return this.mArgs;
  }
  
  public ViewWindowRoot getRoot() {
    return this.mRoot;
  }
  
  public boolean isActivityResume() {
    return (this.mRoot.getActivityLifecycleState() == 3);
  }
  
  public boolean isViewResume() {
    return this.isResumed;
  }
  
  public void onActivityDestroy() {}
  
  public void onActivityPause() {}
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {}
  
  public void onActivityResume() {}
  
  public boolean onBackPressed() {
    return false;
  }
  
  public void onCreate() {}
  
  public void onDestroy() {}
  
  protected void onSwipeBack() {
    this.mRoot.onChildViewSwipedBack(this);
  }
  
  protected void onSwipeCancel() {
    this.mRoot.onChildViewSwipedCancel(this);
  }
  
  protected void onSwipeStart() {
    this.mRoot.onChildViewSwipedStart(this);
  }
  
  public void onThemeChanged(String paramString) {}
  
  public void onViewPause(int paramInt) {}
  
  public void onViewResume(int paramInt) {}
  
  public void setParams(Bundle paramBundle) {
    this.mArgs = paramBundle;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\bas\\ui\viewwindow\ViewWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */