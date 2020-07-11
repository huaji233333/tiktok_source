package com.facebook.react.modules.core;

import android.util.SparseArray;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.common.SystemClock;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.jstasks.HeadlessJsTaskEventListener;
import com.facebook.react.module.annotations.ReactModule;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@ReactModule(name = "Timing")
public final class Timing extends ReactContextBaseJavaModule implements LifecycleEventListener, HeadlessJsTaskEventListener {
  public final AtomicBoolean isPaused = new AtomicBoolean(true);
  
  public final AtomicBoolean isRunningTasks = new AtomicBoolean(false);
  
  public IdleCallbackRunnable mCurrentIdleCallbackRunnable;
  
  private final DevSupportManager mDevSupportManager;
  
  private boolean mFrameCallbackPosted = false;
  
  private boolean mFrameIdleCallbackPosted = false;
  
  public final Object mIdleCallbackGuard = new Object();
  
  private final IdleFrameCallback mIdleFrameCallback = new IdleFrameCallback();
  
  public final ReactChoreographer mReactChoreographer;
  
  public boolean mSendIdleEvents = false;
  
  private final TimerFrameCallback mTimerFrameCallback = new TimerFrameCallback();
  
  public final Object mTimerGuard = new Object();
  
  public final SparseArray<Timer> mTimerIdsToTimers;
  
  public final PriorityQueue<Timer> mTimers;
  
  public Timing(ReactApplicationContext paramReactApplicationContext, DevSupportManager paramDevSupportManager) {
    super(paramReactApplicationContext);
    this.mDevSupportManager = paramDevSupportManager;
    this.mTimers = new PriorityQueue<Timer>(11, new Comparator<Timer>() {
          public int compare(Timing.Timer param1Timer1, Timing.Timer param1Timer2) {
            long l = param1Timer1.mTargetTime - param1Timer2.mTargetTime;
            return (l == 0L) ? 0 : ((l < 0L) ? -1 : 1);
          }
        });
    this.mTimerIdsToTimers = new SparseArray();
    this.mReactChoreographer = ReactChoreographer.getInstance();
  }
  
  private void clearFrameCallback() {
    HeadlessJsTaskContext headlessJsTaskContext = HeadlessJsTaskContext.getInstance((ReactContext)getReactApplicationContext());
    if (this.mFrameCallbackPosted && this.isPaused.get() && !headlessJsTaskContext.hasActiveTasks()) {
      this.mReactChoreographer.removeFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, this.mTimerFrameCallback);
      this.mFrameCallbackPosted = false;
    } 
  }
  
  private void maybeIdleCallback() {
    if (this.isPaused.get() && !this.isRunningTasks.get())
      clearFrameCallback(); 
  }
  
  private void maybeSetChoreographerIdleCallback() {
    synchronized (this.mIdleCallbackGuard) {
      if (this.mSendIdleEvents)
        setChoreographerIdleCallback(); 
      return;
    } 
  }
  
  private void setChoreographerCallback() {
    if (!this.mFrameCallbackPosted) {
      this.mReactChoreographer.postFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, this.mTimerFrameCallback);
      this.mFrameCallbackPosted = true;
    } 
  }
  
  public final void clearChoreographerIdleCallback() {
    if (this.mFrameIdleCallbackPosted) {
      this.mReactChoreographer.removeFrameCallback(ReactChoreographer.CallbackType.IDLE_EVENT, this.mIdleFrameCallback);
      this.mFrameIdleCallbackPosted = false;
    } 
  }
  
  @ReactMethod
  public final void createTimer(int paramInt1, int paramInt2, double paramDouble, boolean paramBoolean) {
    long l1 = SystemClock.currentTimeMillis();
    long l2 = (long)paramDouble;
    if (this.mDevSupportManager.getDevSupportEnabled() && Math.abs(l2 - l1) > 60000L)
      ((JSTimers)getReactApplicationContext().getJSModule(JSTimers.class)).emitTimeDriftWarning("Debugger and device times have drifted by more than 60s. Please correct this by running adb shell \"date `date +%m%d%H%M%Y.%S`\" on your debugger machine."); 
    l1 = Math.max(0L, l2 - l1 + paramInt2);
    if (paramInt2 == 0 && !paramBoolean) {
      WritableArray writableArray = Arguments.createArray();
      writableArray.pushInt(paramInt1);
      ((JSTimers)getReactApplicationContext().getJSModule(JSTimers.class)).callTimers(writableArray);
      return;
    } 
    null = new Timer(paramInt1, SystemClock.nanoTime() / 1000000L + l1, paramInt2, paramBoolean);
    synchronized (this.mTimerGuard) {
      this.mTimers.add(null);
      this.mTimerIdsToTimers.put(paramInt1, null);
      return;
    } 
  }
  
  @ReactMethod
  public final void deleteTimer(int paramInt) {
    synchronized (this.mTimerGuard) {
      Timer timer = (Timer)this.mTimerIdsToTimers.get(paramInt);
      if (timer == null)
        return; 
      this.mTimerIdsToTimers.remove(paramInt);
      this.mTimers.remove(timer);
      return;
    } 
  }
  
  public final String getName() {
    return "Timing";
  }
  
  public final void initialize() {
    getReactApplicationContext().addLifecycleEventListener(this);
    HeadlessJsTaskContext.getInstance((ReactContext)getReactApplicationContext()).addTaskEventListener(this);
  }
  
  public final void onCatalystInstanceDestroy() {
    clearFrameCallback();
    clearChoreographerIdleCallback();
    HeadlessJsTaskContext.getInstance((ReactContext)getReactApplicationContext()).removeTaskEventListener(this);
  }
  
  public final void onHeadlessJsTaskFinish(int paramInt) {
    if (!HeadlessJsTaskContext.getInstance((ReactContext)getReactApplicationContext()).hasActiveTasks()) {
      this.isRunningTasks.set(false);
      clearFrameCallback();
      maybeIdleCallback();
    } 
  }
  
  public final void onHeadlessJsTaskStart(int paramInt) {
    if (!this.isRunningTasks.getAndSet(true)) {
      setChoreographerCallback();
      maybeSetChoreographerIdleCallback();
    } 
  }
  
  public final void onHostDestroy() {
    clearFrameCallback();
    maybeIdleCallback();
  }
  
  public final void onHostPause() {
    this.isPaused.set(true);
    clearFrameCallback();
    maybeIdleCallback();
  }
  
  public final void onHostResume() {
    this.isPaused.set(false);
    setChoreographerCallback();
    maybeSetChoreographerIdleCallback();
  }
  
  public final void setChoreographerIdleCallback() {
    if (!this.mFrameIdleCallbackPosted) {
      this.mReactChoreographer.postFrameCallback(ReactChoreographer.CallbackType.IDLE_EVENT, this.mIdleFrameCallback);
      this.mFrameIdleCallbackPosted = true;
    } 
  }
  
  @ReactMethod
  public final void setSendIdleEvents(final boolean sendIdleEvents) {
    synchronized (this.mIdleCallbackGuard) {
      this.mSendIdleEvents = sendIdleEvents;
      UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
              synchronized (Timing.this.mIdleCallbackGuard) {
                if (sendIdleEvents) {
                  Timing.this.setChoreographerIdleCallback();
                } else {
                  Timing.this.clearChoreographerIdleCallback();
                } 
                return;
              } 
            }
          });
      return;
    } 
  }
  
  class IdleCallbackRunnable implements Runnable {
    private volatile boolean mCancelled;
    
    private final long mFrameStartTime;
    
    public IdleCallbackRunnable(long param1Long) {
      this.mFrameStartTime = param1Long;
    }
    
    public void cancel() {
      this.mCancelled = true;
    }
    
    public void run() {
      if (this.mCancelled)
        return; 
      long l1 = this.mFrameStartTime / 1000000L;
      l1 = SystemClock.uptimeMillis() - l1;
      long l2 = SystemClock.currentTimeMillis();
      if (16.666666F - (float)l1 < 1.0F)
        return; 
      synchronized (Timing.this.mIdleCallbackGuard) {
        boolean bool = Timing.this.mSendIdleEvents;
        if (bool)
          ((JSTimers)Timing.this.getReactApplicationContext().getJSModule(JSTimers.class)).callIdleCallbacks((l2 - l1)); 
        Timing.this.mCurrentIdleCallbackRunnable = null;
        return;
      } 
    }
  }
  
  class IdleFrameCallback extends ChoreographerCompat.FrameCallback {
    private IdleFrameCallback() {}
    
    public void doFrame(long param1Long) {
      if (Timing.this.isPaused.get() && !Timing.this.isRunningTasks.get())
        return; 
      if (Timing.this.mCurrentIdleCallbackRunnable != null)
        Timing.this.mCurrentIdleCallbackRunnable.cancel(); 
      Timing timing = Timing.this;
      timing.mCurrentIdleCallbackRunnable = new Timing.IdleCallbackRunnable(param1Long);
      Timing.this.getReactApplicationContext().runOnJSQueueThread(Timing.this.mCurrentIdleCallbackRunnable);
      Timing.this.mReactChoreographer.postFrameCallback(ReactChoreographer.CallbackType.IDLE_EVENT, this);
    }
  }
  
  static class Timer {
    public final int mCallbackID;
    
    public final int mInterval;
    
    public final boolean mRepeat;
    
    public long mTargetTime;
    
    private Timer(int param1Int1, long param1Long, int param1Int2, boolean param1Boolean) {
      this.mCallbackID = param1Int1;
      this.mTargetTime = param1Long;
      this.mInterval = param1Int2;
      this.mRepeat = param1Boolean;
    }
  }
  
  class TimerFrameCallback extends ChoreographerCompat.FrameCallback {
    private WritableArray mTimersToCall;
    
    private TimerFrameCallback() {}
    
    public void doFrame(long param1Long) {
      if (Timing.this.isPaused.get() && !Timing.this.isRunningTasks.get())
        return; 
      param1Long /= 1000000L;
      synchronized (Timing.this.mTimerGuard) {
        while (!Timing.this.mTimers.isEmpty() && ((Timing.Timer)Timing.this.mTimers.peek()).mTargetTime < param1Long) {
          Timing.Timer timer = Timing.this.mTimers.poll();
          if (this.mTimersToCall == null)
            this.mTimersToCall = Arguments.createArray(); 
          this.mTimersToCall.pushInt(timer.mCallbackID);
          if (timer.mRepeat) {
            timer.mTargetTime = timer.mInterval + param1Long;
            Timing.this.mTimers.add(timer);
            continue;
          } 
          Timing.this.mTimerIdsToTimers.remove(timer.mCallbackID);
        } 
        if (this.mTimersToCall != null) {
          ((JSTimers)Timing.this.getReactApplicationContext().getJSModule(JSTimers.class)).callTimers(this.mTimersToCall);
          this.mTimersToCall = null;
        } 
        Timing.this.mReactChoreographer.postFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, this);
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\core\Timing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */