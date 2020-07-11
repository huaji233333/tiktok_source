package com.tt.miniapp.jsbridge;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.he.jsbinding.JsContext;
import com.he.jsbinding.JsScopedContext;
import com.tt.miniapp.JsRuntime;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class JsTimerHandler extends Handler {
  private boolean isPauseNotifyJsTimerEvent;
  
  private WeakReference<JsRuntime> mJsRuntimeWeakRef;
  
  private LinkedBlockingQueue<JsTimer> mTimerCacheQueue = new LinkedBlockingQueue<JsTimer>();
  
  private CopyOnWriteArraySet<Integer> mTimerIdSet = new CopyOnWriteArraySet<Integer>();
  
  public JsTimerHandler(Looper paramLooper, JsRuntime paramJsRuntime) {
    super(paramLooper);
    this.mJsRuntimeWeakRef = new WeakReference<JsRuntime>(paramJsRuntime);
  }
  
  private void notifyJsTimerEvent(final String timerType, final int timerId) {
    JsRuntime jsRuntime = this.mJsRuntimeWeakRef.get();
    if (jsRuntime == null)
      return; 
    jsRuntime.executeInJsThread(new JsContext.ScopeCallback() {
          public void run(JsScopedContext param1JsScopedContext) {
            param1JsScopedContext.push(timerType);
            param1JsScopedContext.push(timerId);
            param1JsScopedContext.global().callMethod("nativeInvokeTimer", 2);
          }
        });
  }
  
  private void notifyOrCacheJsTimerEvent(String paramString, int paramInt) {
    if (this.isPauseNotifyJsTimerEvent) {
      this.mTimerCacheQueue.add(new JsTimer(paramString, paramInt));
      return;
    } 
    resumeNotifyJsTimerEventIfNeed();
    notifyJsTimerEvent(paramString, paramInt);
  }
  
  private void resumeNotifyJsTimerEventIfNeed() {
    for (JsTimer jsTimer = this.mTimerCacheQueue.poll(); jsTimer != null; jsTimer = this.mTimerCacheQueue.poll())
      notifyJsTimerEvent(jsTimer.timerType, jsTimer.timerId); 
  }
  
  public void clearTimer(String paramString, int paramInt) {
    this.mTimerIdSet.remove(Integer.valueOf(paramInt));
  }
  
  public void handleMessage(Message paramMessage) {
    super.handleMessage(paramMessage);
    int i = paramMessage.what;
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4)
            return; 
          this.isPauseNotifyJsTimerEvent = false;
          resumeNotifyJsTimerEventIfNeed();
          return;
        } 
        this.isPauseNotifyJsTimerEvent = true;
        return;
      } 
      i = paramMessage.arg1;
      int j = paramMessage.arg2;
      if (this.mTimerIdSet.contains(Integer.valueOf(i))) {
        notifyOrCacheJsTimerEvent("Interval", i);
        sendMessageDelayed(obtainMessage(2, i, j), j);
      } 
      return;
    } 
    i = ((Integer)paramMessage.obj).intValue();
    if (this.mTimerIdSet.contains(Integer.valueOf(i))) {
      notifyOrCacheJsTimerEvent("Timeout", i);
      this.mTimerIdSet.remove(Integer.valueOf(i));
    } 
  }
  
  public void onEnterBackground() {}
  
  public void onEnterForeground() {
    if (hasMessages(3))
      removeMessages(3); 
    sendEmptyMessage(4);
  }
  
  public void setTimer(String paramString, int paramInt, long paramLong) {
    if (TextUtils.equals(paramString, "Timeout")) {
      this.mTimerIdSet.add(Integer.valueOf(paramInt));
      sendMessageDelayed(obtainMessage(1, Integer.valueOf(paramInt)), paramLong);
      return;
    } 
    if (TextUtils.equals(paramString, "Interval")) {
      this.mTimerIdSet.add(Integer.valueOf(paramInt));
      sendMessageDelayed(obtainMessage(2, paramInt, (int)paramLong), paramLong);
    } 
  }
  
  static class JsTimer {
    int timerId;
    
    String timerType;
    
    public JsTimer(String param1String, int param1Int) {
      this.timerType = param1String;
      this.timerId = param1Int;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\jsbridge\JsTimerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */