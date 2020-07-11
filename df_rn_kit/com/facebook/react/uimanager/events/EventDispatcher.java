package com.facebook.react.uimanager.events;

import android.util.LongSparseArray;
import com.facebook.i.a.a;
import com.facebook.m.a;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.modules.core.ReactChoreographer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EventDispatcher implements LifecycleEventListener {
  public static final Comparator<Event> EVENT_COMPARATOR = new Comparator<Event>() {
      public final int compare(Event param1Event1, Event param1Event2) {
        if (param1Event1 == null && param1Event2 == null)
          return 0; 
        if (param1Event1 == null)
          return -1; 
        if (param1Event2 == null)
          return 1; 
        long l = param1Event1.getTimestampMs() - param1Event2.getTimestampMs();
        return (l == 0L) ? 0 : ((l < 0L) ? -1 : 1);
      }
    };
  
  public final ScheduleDispatchFrameCallback mCurrentFrameCallback = new ScheduleDispatchFrameCallback();
  
  public final DispatchEventsRunnable mDispatchEventsRunnable = new DispatchEventsRunnable();
  
  public final LongSparseArray<Integer> mEventCookieToLastEventIdx = new LongSparseArray();
  
  private final Map<String, Short> mEventNameToEventId = MapBuilder.newHashMap();
  
  private final ArrayList<Event> mEventStaging = new ArrayList<Event>();
  
  private final Object mEventsStagingLock = new Object();
  
  public Event[] mEventsToDispatch = new Event[16];
  
  public final Object mEventsToDispatchLock = new Object();
  
  public int mEventsToDispatchSize = 0;
  
  public volatile boolean mHasDispatchScheduled = false;
  
  public final AtomicInteger mHasDispatchScheduledCount = new AtomicInteger();
  
  private final ArrayList<EventDispatcherListener> mListeners = new ArrayList<EventDispatcherListener>();
  
  private short mNextEventTypeId = 0;
  
  public volatile RCTEventEmitter mRCTEventEmitter;
  
  public final ReactApplicationContext mReactContext;
  
  public EventDispatcher(ReactApplicationContext paramReactApplicationContext) {
    this.mReactContext = paramReactApplicationContext;
    this.mReactContext.addLifecycleEventListener(this);
  }
  
  private void addEventToEventsToDispatch(Event paramEvent) {
    int i = this.mEventsToDispatchSize;
    Event[] arrayOfEvent = this.mEventsToDispatch;
    if (i == arrayOfEvent.length)
      this.mEventsToDispatch = Arrays.<Event>copyOf(arrayOfEvent, arrayOfEvent.length * 2); 
    arrayOfEvent = this.mEventsToDispatch;
    i = this.mEventsToDispatchSize;
    this.mEventsToDispatchSize = i + 1;
    arrayOfEvent[i] = paramEvent;
  }
  
  private long getEventCookie(int paramInt, String paramString, short paramShort) {
    short s;
    Short short_ = this.mEventNameToEventId.get(paramString);
    if (short_ != null) {
      s = short_.shortValue();
    } else {
      s = this.mNextEventTypeId;
      this.mNextEventTypeId = (short)(s + 1);
      this.mEventNameToEventId.put(paramString, Short.valueOf(s));
    } 
    return getEventCookie(paramInt, s, paramShort);
  }
  
  private static long getEventCookie(int paramInt, short paramShort1, short paramShort2) {
    long l = paramInt;
    return (paramShort1 & 0xFFFFL) << 32L | l | (paramShort2 & 0xFFFFL) << 48L;
  }
  
  public void addListener(EventDispatcherListener paramEventDispatcherListener) {
    this.mListeners.add(paramEventDispatcherListener);
  }
  
  public void clearEventsToDispatch() {
    Arrays.fill((Object[])this.mEventsToDispatch, 0, this.mEventsToDispatchSize, (Object)null);
    this.mEventsToDispatchSize = 0;
  }
  
  public void dispatchEvent(Event paramEvent) {
    a.a(paramEvent.isInitialized(), "Dispatched event hasn't been initialized");
    Iterator<EventDispatcherListener> iterator = this.mListeners.iterator();
    while (iterator.hasNext())
      ((EventDispatcherListener)iterator.next()).onEventDispatch(paramEvent); 
    synchronized (this.mEventsStagingLock) {
      this.mEventStaging.add(paramEvent);
      paramEvent.getEventName();
      paramEvent.getUniqueID();
      if (this.mRCTEventEmitter != null)
        this.mCurrentFrameCallback.maybePostFromNonUI(); 
      return;
    } 
  }
  
  public void moveStagedEventsToDispatchQueue() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mEventsStagingLock : Ljava/lang/Object;
    //   4: astore #7
    //   6: aload #7
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield mEventsToDispatchLock : Ljava/lang/Object;
    //   13: astore #8
    //   15: aload #8
    //   17: monitorenter
    //   18: iconst_0
    //   19: istore_1
    //   20: iload_1
    //   21: aload_0
    //   22: getfield mEventStaging : Ljava/util/ArrayList;
    //   25: invokevirtual size : ()I
    //   28: if_icmpge -> 205
    //   31: aload_0
    //   32: getfield mEventStaging : Ljava/util/ArrayList;
    //   35: iload_1
    //   36: invokevirtual get : (I)Ljava/lang/Object;
    //   39: checkcast com/facebook/react/uimanager/events/Event
    //   42: astore #4
    //   44: aload #4
    //   46: invokevirtual canCoalesce : ()Z
    //   49: ifne -> 61
    //   52: aload_0
    //   53: aload #4
    //   55: invokespecial addEventToEventsToDispatch : (Lcom/facebook/react/uimanager/events/Event;)V
    //   58: goto -> 251
    //   61: aload_0
    //   62: aload #4
    //   64: invokevirtual getViewTag : ()I
    //   67: aload #4
    //   69: invokevirtual getEventName : ()Ljava/lang/String;
    //   72: aload #4
    //   74: invokevirtual getCoalescingKey : ()S
    //   77: invokespecial getEventCookie : (ILjava/lang/String;S)J
    //   80: lstore_2
    //   81: aload_0
    //   82: getfield mEventCookieToLastEventIdx : Landroid/util/LongSparseArray;
    //   85: lload_2
    //   86: invokevirtual get : (J)Ljava/lang/Object;
    //   89: checkcast java/lang/Integer
    //   92: astore #9
    //   94: aconst_null
    //   95: astore #5
    //   97: aload #9
    //   99: ifnonnull -> 120
    //   102: aload_0
    //   103: getfield mEventCookieToLastEventIdx : Landroid/util/LongSparseArray;
    //   106: lload_2
    //   107: aload_0
    //   108: getfield mEventsToDispatchSize : I
    //   111: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   114: invokevirtual put : (JLjava/lang/Object;)V
    //   117: goto -> 181
    //   120: aload_0
    //   121: getfield mEventsToDispatch : [Lcom/facebook/react/uimanager/events/Event;
    //   124: aload #9
    //   126: invokevirtual intValue : ()I
    //   129: aaload
    //   130: astore #5
    //   132: aload #4
    //   134: aload #5
    //   136: invokevirtual coalesce : (Lcom/facebook/react/uimanager/events/Event;)Lcom/facebook/react/uimanager/events/Event;
    //   139: astore #6
    //   141: aload #6
    //   143: aload #5
    //   145: if_acmpeq -> 241
    //   148: aload_0
    //   149: getfield mEventCookieToLastEventIdx : Landroid/util/LongSparseArray;
    //   152: lload_2
    //   153: aload_0
    //   154: getfield mEventsToDispatchSize : I
    //   157: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   160: invokevirtual put : (JLjava/lang/Object;)V
    //   163: aload_0
    //   164: getfield mEventsToDispatch : [Lcom/facebook/react/uimanager/events/Event;
    //   167: aload #9
    //   169: invokevirtual intValue : ()I
    //   172: aconst_null
    //   173: aastore
    //   174: aload #6
    //   176: astore #4
    //   178: goto -> 181
    //   181: aload #4
    //   183: ifnull -> 192
    //   186: aload_0
    //   187: aload #4
    //   189: invokespecial addEventToEventsToDispatch : (Lcom/facebook/react/uimanager/events/Event;)V
    //   192: aload #5
    //   194: ifnull -> 251
    //   197: aload #5
    //   199: invokevirtual dispose : ()V
    //   202: goto -> 251
    //   205: aload #8
    //   207: monitorexit
    //   208: aload_0
    //   209: getfield mEventStaging : Ljava/util/ArrayList;
    //   212: invokevirtual clear : ()V
    //   215: aload #7
    //   217: monitorexit
    //   218: return
    //   219: astore #4
    //   221: aload #8
    //   223: monitorexit
    //   224: aload #4
    //   226: athrow
    //   227: astore #4
    //   229: aload #7
    //   231: monitorexit
    //   232: goto -> 238
    //   235: aload #4
    //   237: athrow
    //   238: goto -> 235
    //   241: aload #4
    //   243: astore #5
    //   245: aconst_null
    //   246: astore #4
    //   248: goto -> 181
    //   251: iload_1
    //   252: iconst_1
    //   253: iadd
    //   254: istore_1
    //   255: goto -> 20
    // Exception table:
    //   from	to	target	type
    //   9	18	227	finally
    //   20	58	219	finally
    //   61	94	219	finally
    //   102	117	219	finally
    //   120	141	219	finally
    //   148	174	219	finally
    //   186	192	219	finally
    //   197	202	219	finally
    //   205	208	219	finally
    //   208	218	227	finally
    //   221	224	219	finally
    //   224	227	227	finally
    //   229	232	227	finally
  }
  
  public void onCatalystInstanceDestroyed() {
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            EventDispatcher.this.stopFrameCallback();
          }
        });
  }
  
  public void onHostDestroy() {
    stopFrameCallback();
  }
  
  public void onHostPause() {
    stopFrameCallback();
  }
  
  public void onHostResume() {
    if (this.mRCTEventEmitter == null)
      this.mRCTEventEmitter = (RCTEventEmitter)this.mReactContext.getJSModule(RCTEventEmitter.class); 
    this.mCurrentFrameCallback.maybePostFromNonUI();
  }
  
  public void removeListener(EventDispatcherListener paramEventDispatcherListener) {
    this.mListeners.remove(paramEventDispatcherListener);
  }
  
  public void stopFrameCallback() {
    UiThreadUtil.assertOnUiThread();
    this.mCurrentFrameCallback.stop();
  }
  
  class DispatchEventsRunnable implements Runnable {
    private DispatchEventsRunnable() {}
    
    public void run() {
      // Byte code:
      //   0: lconst_0
      //   1: ldc 'DispatchEventsRunnable'
      //   3: invokestatic a : (JLjava/lang/String;)V
      //   6: aload_0
      //   7: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   10: getfield mHasDispatchScheduledCount : Ljava/util/concurrent/atomic/AtomicInteger;
      //   13: invokevirtual getAndIncrement : ()I
      //   16: pop
      //   17: aload_0
      //   18: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   21: astore_3
      //   22: iconst_0
      //   23: istore_2
      //   24: aload_3
      //   25: iconst_0
      //   26: putfield mHasDispatchScheduled : Z
      //   29: aload_0
      //   30: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   33: getfield mRCTEventEmitter : Lcom/facebook/react/uimanager/events/RCTEventEmitter;
      //   36: invokestatic b : (Ljava/lang/Object;)Ljava/lang/Object;
      //   39: pop
      //   40: aload_0
      //   41: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   44: getfield mEventsToDispatchLock : Ljava/lang/Object;
      //   47: astore_3
      //   48: aload_3
      //   49: monitorenter
      //   50: iload_2
      //   51: istore_1
      //   52: aload_0
      //   53: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   56: getfield mEventsToDispatchSize : I
      //   59: iconst_1
      //   60: if_icmple -> 86
      //   63: aload_0
      //   64: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   67: getfield mEventsToDispatch : [Lcom/facebook/react/uimanager/events/Event;
      //   70: iconst_0
      //   71: aload_0
      //   72: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   75: getfield mEventsToDispatchSize : I
      //   78: getstatic com/facebook/react/uimanager/events/EventDispatcher.EVENT_COMPARATOR : Ljava/util/Comparator;
      //   81: invokestatic sort : ([Ljava/lang/Object;IILjava/util/Comparator;)V
      //   84: iload_2
      //   85: istore_1
      //   86: iload_1
      //   87: aload_0
      //   88: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   91: getfield mEventsToDispatchSize : I
      //   94: if_icmpge -> 145
      //   97: aload_0
      //   98: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   101: getfield mEventsToDispatch : [Lcom/facebook/react/uimanager/events/Event;
      //   104: iload_1
      //   105: aaload
      //   106: astore #4
      //   108: aload #4
      //   110: ifnull -> 189
      //   113: aload #4
      //   115: invokevirtual getEventName : ()Ljava/lang/String;
      //   118: pop
      //   119: aload #4
      //   121: invokevirtual getUniqueID : ()I
      //   124: pop
      //   125: aload #4
      //   127: aload_0
      //   128: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   131: getfield mRCTEventEmitter : Lcom/facebook/react/uimanager/events/RCTEventEmitter;
      //   134: invokevirtual dispatch : (Lcom/facebook/react/uimanager/events/RCTEventEmitter;)V
      //   137: aload #4
      //   139: invokevirtual dispose : ()V
      //   142: goto -> 189
      //   145: aload_0
      //   146: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   149: invokevirtual clearEventsToDispatch : ()V
      //   152: aload_0
      //   153: getfield this$0 : Lcom/facebook/react/uimanager/events/EventDispatcher;
      //   156: getfield mEventCookieToLastEventIdx : Landroid/util/LongSparseArray;
      //   159: invokevirtual clear : ()V
      //   162: aload_3
      //   163: monitorexit
      //   164: lconst_0
      //   165: invokestatic a : (J)V
      //   168: return
      //   169: astore #4
      //   171: aload_3
      //   172: monitorexit
      //   173: aload #4
      //   175: athrow
      //   176: astore_3
      //   177: lconst_0
      //   178: invokestatic a : (J)V
      //   181: goto -> 186
      //   184: aload_3
      //   185: athrow
      //   186: goto -> 184
      //   189: iload_1
      //   190: iconst_1
      //   191: iadd
      //   192: istore_1
      //   193: goto -> 86
      // Exception table:
      //   from	to	target	type
      //   6	22	176	finally
      //   24	50	176	finally
      //   52	84	169	finally
      //   86	108	169	finally
      //   113	142	169	finally
      //   145	164	169	finally
      //   171	173	169	finally
      //   173	176	176	finally
    }
  }
  
  class ScheduleDispatchFrameCallback extends ChoreographerCompat.FrameCallback {
    private volatile boolean mIsPosted;
    
    private boolean mShouldStop;
    
    private ScheduleDispatchFrameCallback() {}
    
    private void post() {
      ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, EventDispatcher.this.mCurrentFrameCallback);
    }
    
    public void doFrame(long param1Long) {
      UiThreadUtil.assertOnUiThread();
      if (this.mShouldStop) {
        this.mIsPosted = false;
      } else {
        post();
      } 
      a.a(0L, "ScheduleDispatchFrameCallback");
      try {
        EventDispatcher.this.moveStagedEventsToDispatchQueue();
        if (EventDispatcher.this.mEventsToDispatchSize > 0 && !EventDispatcher.this.mHasDispatchScheduled) {
          EventDispatcher.this.mHasDispatchScheduled = true;
          EventDispatcher.this.mHasDispatchScheduledCount.get();
          EventDispatcher.this.mReactContext.runOnJSQueueThread(EventDispatcher.this.mDispatchEventsRunnable);
        } 
        return;
      } finally {
        a.a(0L);
      } 
    }
    
    public void maybePost() {
      if (!this.mIsPosted) {
        this.mIsPosted = true;
        post();
      } 
    }
    
    public void maybePostFromNonUI() {
      if (this.mIsPosted)
        return; 
      if (EventDispatcher.this.mReactContext.isOnUiQueueThread()) {
        maybePost();
        return;
      } 
      EventDispatcher.this.mReactContext.runOnUiQueueThread(new Runnable() {
            public void run() {
              EventDispatcher.ScheduleDispatchFrameCallback.this.maybePost();
            }
          });
    }
    
    public void stop() {
      this.mShouldStop = true;
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$1.maybePost();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\events\EventDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */