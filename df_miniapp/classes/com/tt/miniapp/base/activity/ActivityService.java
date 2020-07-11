package com.tt.miniapp.base.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import com.bytedance.sandboxapp.b.a;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapphost.AppbrandContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityService implements ActivityServiceInterface {
  private final Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks;
  
  private final List<ActivityServiceInterface.ActivityLifecycleCallbacks> mActivityLifecycleCallbacksList;
  
  private final IActivityResultHandler mActivityResultHandler;
  
  public final List<IActivityResultHandler> mActivityResultHandlerList;
  
  private final MiniAppContext mContext;
  
  private boolean mRegisterHandler;
  
  private boolean mRegisterLifecycleCallbacks;
  
  public ActivityService(MiniAppContext paramMiniAppContext) {
    this.mContext = paramMiniAppContext;
    this.mActivityResultHandler = new IActivityResultHandler() {
        public boolean autoClearAfterActivityResult() {
          return false;
        }
        
        public boolean handleActivityResult(int param1Int1, int param1Int2, Intent param1Intent) {
          boolean bool2;
          null = new ArrayList<IActivityResultHandler>(ActivityService.this.mActivityResultHandlerList);
          Iterator<IActivityResultHandler> iterator = null.iterator();
          boolean bool1 = false;
          while (true) {
            bool2 = bool1;
            if (iterator.hasNext()) {
              bool1 = ((IActivityResultHandler)iterator.next()).handleActivityResult(param1Int1, param1Int2, param1Intent);
              bool2 = bool1;
              if (!bool1)
                continue; 
            } 
            break;
          } 
          synchronized (ActivityService.this) {
            for (IActivityResultHandler iActivityResultHandler : null) {
              if (iActivityResultHandler.autoClearAfterActivityResult())
                ActivityService.this.mActivityResultHandlerList.remove(iActivityResultHandler); 
            } 
            return bool2;
          } 
        }
      };
    this.mActivityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        public void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {
          Iterator<ActivityServiceInterface.ActivityLifecycleCallbacks> iterator = ActivityService.this.getSortLifecycleCallbacks().iterator();
          while (iterator.hasNext())
            ((ActivityServiceInterface.ActivityLifecycleCallbacks)iterator.next()).onActivityCreated(param1Activity, param1Bundle); 
        }
        
        public void onActivityDestroyed(Activity param1Activity) {
          Iterator<ActivityServiceInterface.ActivityLifecycleCallbacks> iterator = ActivityService.this.getSortLifecycleCallbacks().iterator();
          while (iterator.hasNext())
            ((ActivityServiceInterface.ActivityLifecycleCallbacks)iterator.next()).onActivityDestroyed(param1Activity); 
        }
        
        public void onActivityPaused(Activity param1Activity) {
          Iterator<ActivityServiceInterface.ActivityLifecycleCallbacks> iterator = ActivityService.this.getSortLifecycleCallbacks().iterator();
          while (iterator.hasNext())
            ((ActivityServiceInterface.ActivityLifecycleCallbacks)iterator.next()).onActivityPaused(param1Activity); 
        }
        
        public void onActivityResumed(Activity param1Activity) {
          Iterator<ActivityServiceInterface.ActivityLifecycleCallbacks> iterator = ActivityService.this.getSortLifecycleCallbacks().iterator();
          while (iterator.hasNext())
            ((ActivityServiceInterface.ActivityLifecycleCallbacks)iterator.next()).onActivityResumed(param1Activity); 
        }
        
        public void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {
          Iterator<ActivityServiceInterface.ActivityLifecycleCallbacks> iterator = ActivityService.this.getSortLifecycleCallbacks().iterator();
          while (iterator.hasNext())
            ((ActivityServiceInterface.ActivityLifecycleCallbacks)iterator.next()).onActivitySaveInstanceState(param1Activity, param1Bundle); 
        }
        
        public void onActivityStarted(Activity param1Activity) {
          Iterator<ActivityServiceInterface.ActivityLifecycleCallbacks> iterator = ActivityService.this.getSortLifecycleCallbacks().iterator();
          while (iterator.hasNext())
            ((ActivityServiceInterface.ActivityLifecycleCallbacks)iterator.next()).onActivityStarted(param1Activity); 
        }
        
        public void onActivityStopped(Activity param1Activity) {
          Iterator<ActivityServiceInterface.ActivityLifecycleCallbacks> iterator = ActivityService.this.getSortLifecycleCallbacks().iterator();
          while (iterator.hasNext())
            ((ActivityServiceInterface.ActivityLifecycleCallbacks)iterator.next()).onActivityStopped(param1Activity); 
        }
      };
    this.mActivityResultHandlerList = new ArrayList<IActivityResultHandler>();
    this.mActivityLifecycleCallbacksList = new ArrayList<ActivityServiceInterface.ActivityLifecycleCallbacks>();
  }
  
  public MiniAppContext getContext() {
    return this.mContext;
  }
  
  public List<ActivityServiceInterface.ActivityLifecycleCallbacks> getSortLifecycleCallbacks() {
    synchronized (this.mActivityLifecycleCallbacks) {
      int i = this.mActivityLifecycleCallbacksList.size();
      if (i > 0) {
        i--;
        while (true) {
          if (i >= 0) {
            ActivityServiceInterface.ActivityLifecycleCallbacks activityLifecycleCallbacks = this.mActivityLifecycleCallbacksList.get(i);
            if (activityLifecycleCallbacks.shouldUnregister())
              this.mActivityLifecycleCallbacksList.remove(activityLifecycleCallbacks); 
          } else {
            return new ArrayList<ActivityServiceInterface.ActivityLifecycleCallbacks>(this.mActivityLifecycleCallbacksList);
          } 
          i--;
        } 
      } 
      return new ArrayList<ActivityServiceInterface.ActivityLifecycleCallbacks>(this.mActivityLifecycleCallbacksList);
    } 
  }
  
  public void onDestroy() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mActivityResultHandlerList : Ljava/util/List;
    //   6: invokeinterface clear : ()V
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_0
    //   14: getfield mActivityLifecycleCallbacks : Landroid/app/Application$ActivityLifecycleCallbacks;
    //   17: astore_1
    //   18: aload_1
    //   19: monitorenter
    //   20: aload_0
    //   21: getfield mActivityLifecycleCallbacksList : Ljava/util/List;
    //   24: invokeinterface clear : ()V
    //   29: aload_1
    //   30: monitorexit
    //   31: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   34: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   37: aload_0
    //   38: getfield mActivityLifecycleCallbacks : Landroid/app/Application$ActivityLifecycleCallbacks;
    //   41: invokevirtual unregisterActivityLifecycleCallbacks : (Landroid/app/Application$ActivityLifecycleCallbacks;)V
    //   44: return
    //   45: astore_2
    //   46: aload_1
    //   47: monitorexit
    //   48: aload_2
    //   49: athrow
    //   50: astore_1
    //   51: aload_0
    //   52: monitorexit
    //   53: aload_1
    //   54: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	50	finally
    //   20	31	45	finally
    //   46	48	45	finally
    //   51	53	50	finally
  }
  
  public void registerActivityLifecycleCallbacks(ActivityServiceInterface.ActivityLifecycleCallbacks paramActivityLifecycleCallbacks) {
    synchronized (this.mActivityLifecycleCallbacks) {
      if (!this.mRegisterLifecycleCallbacks) {
        AppbrandContext.getInst().getApplicationContext().registerActivityLifecycleCallbacks(this.mActivityLifecycleCallbacks);
        this.mRegisterLifecycleCallbacks = true;
      } 
      Object object = paramActivityLifecycleCallbacks.uniquelyIdentify();
      if (object != null) {
        Iterator<ActivityServiceInterface.ActivityLifecycleCallbacks> iterator = this.mActivityLifecycleCallbacksList.iterator();
        while (iterator.hasNext()) {
          if (object.equals(((ActivityServiceInterface.ActivityLifecycleCallbacks)iterator.next()).uniquelyIdentify()))
            return; 
        } 
      } 
      this.mActivityLifecycleCallbacksList.add(paramActivityLifecycleCallbacks);
      return;
    } 
  }
  
  public void registerActivityResultHandler(IActivityResultHandler paramIActivityResultHandler) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mRegisterHandler : Z
    //   4: ifne -> 51
    //   7: aload_0
    //   8: invokevirtual getContext : ()Lcom/tt/miniapp/base/MiniAppContext;
    //   11: invokeinterface getCurrentActivity : ()Landroid/app/Activity;
    //   16: astore_2
    //   17: aload_2
    //   18: instanceof com/tt/miniapphost/MiniappHostBase
    //   21: ifeq -> 51
    //   24: aload_2
    //   25: checkcast com/tt/miniapphost/MiniappHostBase
    //   28: invokevirtual getActivityProxy : ()Lcom/tt/miniapphost/IActivityProxy;
    //   31: astore_2
    //   32: aload_2
    //   33: ifnull -> 51
    //   36: aload_2
    //   37: aload_0
    //   38: getfield mActivityResultHandler : Lcom/tt/miniapp/base/activity/IActivityResultHandler;
    //   41: invokeinterface setActivityResultHandler : (Lcom/tt/miniapp/base/activity/IActivityResultHandler;)V
    //   46: aload_0
    //   47: iconst_1
    //   48: putfield mRegisterHandler : Z
    //   51: aload_0
    //   52: monitorenter
    //   53: aload_0
    //   54: getfield mActivityResultHandlerList : Ljava/util/List;
    //   57: aload_1
    //   58: invokeinterface add : (Ljava/lang/Object;)Z
    //   63: pop
    //   64: aload_0
    //   65: monitorexit
    //   66: return
    //   67: astore_1
    //   68: aload_0
    //   69: monitorexit
    //   70: aload_1
    //   71: athrow
    // Exception table:
    //   from	to	target	type
    //   53	66	67	finally
    //   68	70	67	finally
  }
  
  public void unregisterActivityLifecycleCallbacks(ActivityServiceInterface.ActivityLifecycleCallbacks paramActivityLifecycleCallbacks) {
    synchronized (this.mActivityLifecycleCallbacks) {
      this.mActivityLifecycleCallbacksList.remove(paramActivityLifecycleCallbacks);
      return;
    } 
  }
  
  public void unregisterActivityResultHandler(IActivityResultHandler paramIActivityResultHandler) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mActivityResultHandlerList : Ljava/util/List;
    //   6: aload_1
    //   7: invokeinterface remove : (Ljava/lang/Object;)Z
    //   12: pop
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\activity\ActivityService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */