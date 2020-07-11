package com.facebook.react.uimanager;

import android.os.SystemClock;
import com.facebook.common.e.a;
import com.facebook.m.a;
import com.facebook.react.animation.Animation;
import com.facebook.react.animation.AnimationRegistry;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.uimanager.common.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UIViewOperationQueue {
  public final AnimationRegistry mAnimationRegistry;
  
  private final Object mDispatchRunnablesLock = new Object();
  
  private final DispatchUIFrameCallback mDispatchUIFrameCallback;
  
  private ArrayList<Runnable> mDispatchUIRunnables = new ArrayList<Runnable>();
  
  private boolean mIsDispatchUIFrameCallbackEnqueued = false;
  
  public boolean mIsInIllegalUIState = false;
  
  public boolean mIsProfilingNextBatch = false;
  
  public final int[] mMeasureBuffer = new int[4];
  
  public final NativeViewHierarchyManager mNativeViewHierarchyManager;
  
  public long mNonBatchedExecutionTotalTime;
  
  public ArrayDeque<UIOperation> mNonBatchedOperations = new ArrayDeque<UIOperation>();
  
  public final Object mNonBatchedOperationsLock = new Object();
  
  private ArrayList<UIOperation> mOperations = new ArrayList<UIOperation>();
  
  private long mProfiledBatchBatchedExecutionTime;
  
  public long mProfiledBatchCommitStartTime;
  
  public long mProfiledBatchDispatchViewUpdatesTime;
  
  public long mProfiledBatchLayoutTime;
  
  private long mProfiledBatchNonBatchedExecutionTime;
  
  public long mProfiledBatchRunStartTime;
  
  private final ReactApplicationContext mReactApplicationContext;
  
  public NotThreadSafeViewHierarchyUpdateDebugListener mViewHierarchyUpdateDebugListener;
  
  public UIViewOperationQueue(ReactApplicationContext paramReactApplicationContext, NativeViewHierarchyManager paramNativeViewHierarchyManager, int paramInt) {
    this.mNativeViewHierarchyManager = paramNativeViewHierarchyManager;
    this.mAnimationRegistry = paramNativeViewHierarchyManager.getAnimationRegistry();
    int i = paramInt;
    if (paramInt == -1)
      i = 8; 
    this.mDispatchUIFrameCallback = new DispatchUIFrameCallback((ReactContext)paramReactApplicationContext, i);
    this.mReactApplicationContext = paramReactApplicationContext;
  }
  
  public void addRootView(int paramInt, SizeMonitoringFrameLayout paramSizeMonitoringFrameLayout, ThemedReactContext paramThemedReactContext) {
    this.mNativeViewHierarchyManager.addRootView(paramInt, paramSizeMonitoringFrameLayout, paramThemedReactContext);
  }
  
  public void dispatchViewUpdates(final int batchId, final long commitStartTime, final long layoutTime) {
    try {
      final long dispatchViewUpdatesTime = SystemClock.uptimeMillis();
      if (!this.mOperations.isEmpty()) {
        null = this.mOperations;
        this.mOperations = new ArrayList<UIOperation>();
      } else {
        null = null;
      } 
      synchronized (this.mNonBatchedOperationsLock) {
        if (!this.mNonBatchedOperations.isEmpty()) {
          null = (Runnable)this.mNonBatchedOperations;
          this.mNonBatchedOperations = new ArrayDeque<UIOperation>();
        } else {
          null = null;
        } 
        if (this.mViewHierarchyUpdateDebugListener != null)
          this.mViewHierarchyUpdateDebugListener.onViewHierarchyUpdateEnqueued(); 
        null = new Runnable() {
            public void run() {
              Exception exception;
              try {
                long l = SystemClock.uptimeMillis();
                if (nonBatchedOperations != null) {
                  Iterator<UIViewOperationQueue.UIOperation> iterator = nonBatchedOperations.iterator();
                  while (iterator.hasNext())
                    ((UIViewOperationQueue.UIOperation)iterator.next()).execute(); 
                } 
                if (batchedOperations != null) {
                  Iterator<UIViewOperationQueue.UIOperation> iterator = batchedOperations.iterator();
                  while (iterator.hasNext())
                    ((UIViewOperationQueue.UIOperation)iterator.next()).execute(); 
                } 
                if (UIViewOperationQueue.this.mIsProfilingNextBatch && UIViewOperationQueue.this.mProfiledBatchCommitStartTime == 0L) {
                  UIViewOperationQueue.this.mProfiledBatchCommitStartTime = commitStartTime;
                  UIViewOperationQueue.this.mProfiledBatchLayoutTime = layoutTime;
                  UIViewOperationQueue.this.mProfiledBatchDispatchViewUpdatesTime = dispatchViewUpdatesTime;
                  UIViewOperationQueue.this.mProfiledBatchRunStartTime = l;
                } 
                UIViewOperationQueue.this.mNativeViewHierarchyManager.clearLayoutAnimation();
                if (UIViewOperationQueue.this.mViewHierarchyUpdateDebugListener != null)
                  UIViewOperationQueue.this.mViewHierarchyUpdateDebugListener.onViewHierarchyUpdateFinished(); 
                a.a(0L);
                return;
              } catch (Exception null) {
                throw exception;
              } finally {}
              a.a(0L);
              throw exception;
            }
          };
        synchronized (this.mDispatchRunnablesLock) {
          a.a(0L);
          this.mDispatchUIRunnables.add(null);
          if (!this.mIsDispatchUIFrameCallbackEnqueued)
            UiThreadUtil.runOnUiThread((Runnable)new GuardedRunnable((ReactContext)this.mReactApplicationContext) {
                  public void runGuarded() {
                    UIViewOperationQueue.this.flushPendingBatches();
                  }
                }); 
          return;
        } 
      } 
    } finally {
      a.a(0L);
    } 
  }
  
  public void enqueueAddAnimation(int paramInt1, int paramInt2, Callback paramCallback) {
    this.mOperations.add(new AddAnimationOperation(paramInt1, paramInt2, paramCallback));
  }
  
  public void enqueueClearJSResponder() {
    this.mOperations.add(new ChangeJSResponderOperation(0, 0, true, false));
  }
  
  public void enqueueConfigureLayoutAnimation(ReadableMap paramReadableMap, Callback paramCallback1, Callback paramCallback2) {
    this.mOperations.add(new ConfigureLayoutAnimationOperation(paramReadableMap));
  }
  
  public void enqueueCreateView(ThemedReactContext paramThemedReactContext, int paramInt, String paramString, ReactStylesDiffMap paramReactStylesDiffMap) {
    synchronized (this.mNonBatchedOperationsLock) {
      this.mNonBatchedOperations.addLast(new CreateViewOperation(paramThemedReactContext, paramInt, paramString, paramReactStylesDiffMap));
      return;
    } 
  }
  
  public void enqueueDispatchCommand(int paramInt1, int paramInt2, ReadableArray paramReadableArray) {
    this.mOperations.add(new DispatchCommandOperation(paramInt1, paramInt2, paramReadableArray));
  }
  
  public void enqueueFindTargetForTouch(int paramInt, float paramFloat1, float paramFloat2, Callback paramCallback) {
    this.mOperations.add(new FindTargetForTouchOperation(paramInt, paramFloat1, paramFloat2, paramCallback));
  }
  
  public void enqueueManageChildren(int paramInt, int[] paramArrayOfint1, ViewAtIndex[] paramArrayOfViewAtIndex, int[] paramArrayOfint2) {
    this.mOperations.add(new ManageChildrenOperation(paramInt, paramArrayOfint1, paramArrayOfViewAtIndex, paramArrayOfint2));
  }
  
  public void enqueueMeasure(int paramInt, Callback paramCallback) {
    this.mOperations.add(new MeasureOperation(paramInt, paramCallback));
  }
  
  public void enqueueMeasureInWindow(int paramInt, Callback paramCallback) {
    this.mOperations.add(new MeasureInWindowOperation(paramInt, paramCallback));
  }
  
  public void enqueueRegisterAnimation(Animation paramAnimation) {
    this.mOperations.add(new RegisterAnimationOperation(paramAnimation));
  }
  
  public void enqueueRemoveAnimation(int paramInt) {
    this.mOperations.add(new RemoveAnimationOperation(paramInt));
  }
  
  public void enqueueRemoveRootView(int paramInt) {
    this.mOperations.add(new RemoveRootViewOperation(paramInt));
  }
  
  public void enqueueSendAccessibilityEvent(int paramInt1, int paramInt2) {
    this.mOperations.add(new SendAccessibilityEvent(paramInt1, paramInt2));
  }
  
  public void enqueueSetChildren(int paramInt, ReadableArray paramReadableArray) {
    this.mOperations.add(new SetChildrenOperation(paramInt, paramReadableArray));
  }
  
  public void enqueueSetJSResponder(int paramInt1, int paramInt2, boolean paramBoolean) {
    this.mOperations.add(new ChangeJSResponderOperation(paramInt1, paramInt2, false, paramBoolean));
  }
  
  public void enqueueSetLayoutAnimationEnabled(boolean paramBoolean) {
    this.mOperations.add(new SetLayoutAnimationEnabledOperation(paramBoolean));
  }
  
  public void enqueueShowPopupMenu(int paramInt, ReadableArray paramReadableArray, Callback paramCallback1, Callback paramCallback2) {
    this.mOperations.add(new ShowPopupMenuOperation(paramInt, paramReadableArray, paramCallback1, paramCallback2));
  }
  
  public void enqueueUIBlock(UIBlock paramUIBlock) {
    this.mOperations.add(new UIBlockOperation(paramUIBlock));
  }
  
  protected void enqueueUIOperation(UIOperation paramUIOperation) {
    SoftAssertions.assertNotNull(paramUIOperation);
    this.mOperations.add(paramUIOperation);
  }
  
  public void enqueueUpdateExtraData(int paramInt, Object paramObject) {
    this.mOperations.add(new UpdateViewExtraData(paramInt, paramObject));
  }
  
  public void enqueueUpdateLayout(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    this.mOperations.add(new UpdateLayoutOperation(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
  }
  
  public void enqueueUpdateProperties(int paramInt, String paramString, ReactStylesDiffMap paramReactStylesDiffMap) {
    this.mOperations.add(new UpdatePropertiesOperation(paramInt, paramReactStylesDiffMap));
  }
  
  public void flushPendingBatches() {
    if (this.mIsInIllegalUIState) {
      a.b("ReactNative", "Not flushing pending UI operations because of previously thrown Exception");
      return;
    } 
    synchronized (this.mDispatchRunnablesLock) {
      if (!this.mDispatchUIRunnables.isEmpty()) {
        ArrayList<Runnable> arrayList = this.mDispatchUIRunnables;
        this.mDispatchUIRunnables = new ArrayList<Runnable>();
        long l = SystemClock.uptimeMillis();
        null = (Object<Runnable>)arrayList.iterator();
        while (null.hasNext())
          ((Runnable)null.next()).run(); 
        if (this.mIsProfilingNextBatch) {
          this.mProfiledBatchBatchedExecutionTime = SystemClock.uptimeMillis() - l;
          this.mProfiledBatchNonBatchedExecutionTime = this.mNonBatchedExecutionTotalTime;
          this.mIsProfilingNextBatch = false;
        } 
        this.mNonBatchedExecutionTotalTime = 0L;
        return;
      } 
      return;
    } 
  }
  
  NativeViewHierarchyManager getNativeViewHierarchyManager() {
    return this.mNativeViewHierarchyManager;
  }
  
  public Map<String, Long> getProfiledBatchPerfCounters() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("CommitStartTime", Long.valueOf(this.mProfiledBatchCommitStartTime));
    hashMap.put("LayoutTime", Long.valueOf(this.mProfiledBatchLayoutTime));
    hashMap.put("DispatchViewUpdatesTime", Long.valueOf(this.mProfiledBatchDispatchViewUpdatesTime));
    hashMap.put("RunStartTime", Long.valueOf(this.mProfiledBatchRunStartTime));
    hashMap.put("BatchedExecutionTime", Long.valueOf(this.mProfiledBatchBatchedExecutionTime));
    hashMap.put("NonBatchedExecutionTime", Long.valueOf(this.mProfiledBatchNonBatchedExecutionTime));
    return (Map)hashMap;
  }
  
  public boolean isEmpty() {
    return this.mOperations.isEmpty();
  }
  
  void pauseFrameCallback() {
    this.mIsDispatchUIFrameCallbackEnqueued = false;
    ReactChoreographer.getInstance().removeFrameCallback(ReactChoreographer.CallbackType.DISPATCH_UI, this.mDispatchUIFrameCallback);
    flushPendingBatches();
  }
  
  public void prependUIBlock(UIBlock paramUIBlock) {
    this.mOperations.add(0, new UIBlockOperation(paramUIBlock));
  }
  
  public void profileNextBatch() {
    this.mIsProfilingNextBatch = true;
    this.mProfiledBatchCommitStartTime = 0L;
  }
  
  void resumeFrameCallback() {
    this.mIsDispatchUIFrameCallbackEnqueued = true;
    ReactChoreographer.getInstance().postFrameCallback(ReactChoreographer.CallbackType.DISPATCH_UI, this.mDispatchUIFrameCallback);
  }
  
  public void setViewHierarchyUpdateDebugListener(NotThreadSafeViewHierarchyUpdateDebugListener paramNotThreadSafeViewHierarchyUpdateDebugListener) {
    this.mViewHierarchyUpdateDebugListener = paramNotThreadSafeViewHierarchyUpdateDebugListener;
  }
  
  class AddAnimationOperation extends AnimationOperation {
    private final int mReactTag;
    
    private final Callback mSuccessCallback;
    
    private AddAnimationOperation(int param1Int1, int param1Int2, Callback param1Callback) {
      super(param1Int2);
      this.mReactTag = param1Int1;
      this.mSuccessCallback = param1Callback;
    }
    
    public void execute() {
      Animation animation = UIViewOperationQueue.this.mAnimationRegistry.getAnimation(this.mAnimationID);
      if (animation != null) {
        UIViewOperationQueue.this.mNativeViewHierarchyManager.startAnimationForNativeView(this.mReactTag, animation, this.mSuccessCallback);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder("Animation with id ");
      stringBuilder.append(this.mAnimationID);
      stringBuilder.append(" was not found");
      throw new IllegalViewOperationException(stringBuilder.toString());
    }
  }
  
  static abstract class AnimationOperation implements UIOperation {
    protected final int mAnimationID;
    
    public AnimationOperation(int param1Int) {
      this.mAnimationID = param1Int;
    }
  }
  
  final class ChangeJSResponderOperation extends ViewOperation {
    private final boolean mBlockNativeResponder;
    
    private final boolean mClearResponder;
    
    private final int mInitialTag;
    
    public ChangeJSResponderOperation(int param1Int1, int param1Int2, boolean param1Boolean1, boolean param1Boolean2) {
      super(param1Int1);
      this.mInitialTag = param1Int2;
      this.mClearResponder = param1Boolean1;
      this.mBlockNativeResponder = param1Boolean2;
    }
    
    public final void execute() {
      if (!this.mClearResponder) {
        UIViewOperationQueue.this.mNativeViewHierarchyManager.setJSResponder(this.mTag, this.mInitialTag, this.mBlockNativeResponder);
        return;
      } 
      UIViewOperationQueue.this.mNativeViewHierarchyManager.clearJSResponder();
    }
  }
  
  class ConfigureLayoutAnimationOperation implements UIOperation {
    private final ReadableMap mConfig;
    
    private ConfigureLayoutAnimationOperation(ReadableMap param1ReadableMap) {
      this.mConfig = param1ReadableMap;
    }
    
    public void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.configureLayoutAnimation(this.mConfig);
    }
  }
  
  final class CreateViewOperation extends ViewOperation {
    private final String mClassName;
    
    private final ReactStylesDiffMap mInitialProps;
    
    private final ThemedReactContext mThemedContext;
    
    public CreateViewOperation(ThemedReactContext param1ThemedReactContext, int param1Int, String param1String, ReactStylesDiffMap param1ReactStylesDiffMap) {
      super(param1Int);
      this.mThemedContext = param1ThemedReactContext;
      this.mClassName = param1String;
      this.mInitialProps = param1ReactStylesDiffMap;
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.createView(this.mThemedContext, this.mTag, this.mClassName, this.mInitialProps);
    }
  }
  
  final class DispatchCommandOperation extends ViewOperation {
    private final ReadableArray mArgs;
    
    private final int mCommand;
    
    public DispatchCommandOperation(int param1Int1, int param1Int2, ReadableArray param1ReadableArray) {
      super(param1Int1);
      this.mCommand = param1Int2;
      this.mArgs = param1ReadableArray;
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.dispatchCommand(this.mTag, this.mCommand, this.mArgs);
    }
  }
  
  class DispatchUIFrameCallback extends GuardedFrameCallback {
    private final int mMinTimeLeftInFrameForNonBatchedOperationMs;
    
    private DispatchUIFrameCallback(ReactContext param1ReactContext, int param1Int) {
      super(param1ReactContext);
      this.mMinTimeLeftInFrameForNonBatchedOperationMs = param1Int;
    }
    
    private void dispatchPendingNonBatchedOperations(long param1Long) {
      while (16L - (System.nanoTime() - param1Long) / 1000000L >= this.mMinTimeLeftInFrameForNonBatchedOperationMs) {
        synchronized (UIViewOperationQueue.this.mNonBatchedOperationsLock) {
          if (UIViewOperationQueue.this.mNonBatchedOperations.isEmpty())
            return; 
          UIViewOperationQueue.UIOperation uIOperation = UIViewOperationQueue.this.mNonBatchedOperations.pollFirst();
          try {
            long l = SystemClock.uptimeMillis();
            uIOperation.execute();
            UIViewOperationQueue.this.mNonBatchedExecutionTotalTime += SystemClock.uptimeMillis() - l;
          } catch (Exception null) {
            throw null;
          } 
        } 
      } 
    }
    
    public void doFrameGuarded(long param1Long) {
      if (UIViewOperationQueue.this.mIsInIllegalUIState) {
        a.b("ReactNative", "Not flushing pending UI operations because of previously thrown Exception");
        return;
      } 
      a.a(0L, "dispatchNonBatchedUIOperations");
      try {
        dispatchPendingNonBatchedOperations(param1Long);
        a.a(0L);
        UIViewOperationQueue.this.flushPendingBatches();
        return;
      } finally {
        a.a(0L);
      } 
    }
  }
  
  final class FindTargetForTouchOperation implements UIOperation {
    private final Callback mCallback;
    
    private final int mReactTag;
    
    private final float mTargetX;
    
    private final float mTargetY;
    
    private FindTargetForTouchOperation(int param1Int, float param1Float1, float param1Float2, Callback param1Callback) {
      this.mReactTag = param1Int;
      this.mTargetX = param1Float1;
      this.mTargetY = param1Float2;
      this.mCallback = param1Callback;
    }
    
    public final void execute() {
      try {
        UIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, UIViewOperationQueue.this.mMeasureBuffer);
        float f1 = UIViewOperationQueue.this.mMeasureBuffer[0];
        float f2 = UIViewOperationQueue.this.mMeasureBuffer[1];
        int i = UIViewOperationQueue.this.mNativeViewHierarchyManager.findTargetTagForTouch(this.mReactTag, this.mTargetX, this.mTargetY);
        try {
          UIViewOperationQueue.this.mNativeViewHierarchyManager.measure(i, UIViewOperationQueue.this.mMeasureBuffer);
          f1 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[0] - f1);
          f2 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[1] - f2);
          float f3 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[2]);
          float f4 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[3]);
          this.mCallback.invoke(new Object[] { Integer.valueOf(i), Float.valueOf(f1), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4) });
          return;
        } catch (IllegalViewOperationException illegalViewOperationException) {
          this.mCallback.invoke(new Object[0]);
        } 
        return;
      } catch (IllegalViewOperationException illegalViewOperationException) {
        this.mCallback.invoke(new Object[0]);
        return;
      } 
    }
  }
  
  final class ManageChildrenOperation extends ViewOperation {
    private final int[] mIndicesToRemove;
    
    private final int[] mTagsToDelete;
    
    private final ViewAtIndex[] mViewsToAdd;
    
    public ManageChildrenOperation(int param1Int, int[] param1ArrayOfint1, ViewAtIndex[] param1ArrayOfViewAtIndex, int[] param1ArrayOfint2) {
      super(param1Int);
      this.mIndicesToRemove = param1ArrayOfint1;
      this.mViewsToAdd = param1ArrayOfViewAtIndex;
      this.mTagsToDelete = param1ArrayOfint2;
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.manageChildren(this.mTag, this.mIndicesToRemove, this.mViewsToAdd, this.mTagsToDelete);
    }
  }
  
  final class MeasureInWindowOperation implements UIOperation {
    private final Callback mCallback;
    
    private final int mReactTag;
    
    private MeasureInWindowOperation(int param1Int, Callback param1Callback) {
      this.mReactTag = param1Int;
      this.mCallback = param1Callback;
    }
    
    public final void execute() {
      try {
        UIViewOperationQueue.this.mNativeViewHierarchyManager.measureInWindow(this.mReactTag, UIViewOperationQueue.this.mMeasureBuffer);
        float f1 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[0]);
        float f2 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[1]);
        float f3 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[2]);
        float f4 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[3]);
        this.mCallback.invoke(new Object[] { Float.valueOf(f1), Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4) });
        return;
      } catch (NoSuchNativeViewException noSuchNativeViewException) {
        this.mCallback.invoke(new Object[0]);
        return;
      } 
    }
  }
  
  final class MeasureOperation implements UIOperation {
    private final Callback mCallback;
    
    private final int mReactTag;
    
    private MeasureOperation(int param1Int, Callback param1Callback) {
      this.mReactTag = param1Int;
      this.mCallback = param1Callback;
    }
    
    public final void execute() {
      try {
        UIViewOperationQueue.this.mNativeViewHierarchyManager.measure(this.mReactTag, UIViewOperationQueue.this.mMeasureBuffer);
        float f1 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[0]);
        float f2 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[1]);
        float f3 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[2]);
        float f4 = PixelUtil.toDIPFromPixel(UIViewOperationQueue.this.mMeasureBuffer[3]);
        this.mCallback.invoke(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f1), Float.valueOf(f2) });
        return;
      } catch (NoSuchNativeViewException noSuchNativeViewException) {
        this.mCallback.invoke(new Object[0]);
        return;
      } 
    }
  }
  
  class RegisterAnimationOperation extends AnimationOperation {
    private final Animation mAnimation;
    
    private RegisterAnimationOperation(Animation param1Animation) {
      super(param1Animation.getAnimationID());
      this.mAnimation = param1Animation;
    }
    
    public void execute() {
      UIViewOperationQueue.this.mAnimationRegistry.registerAnimation(this.mAnimation);
    }
  }
  
  final class RemoveAnimationOperation extends AnimationOperation {
    private RemoveAnimationOperation(int param1Int) {
      super(param1Int);
    }
    
    public final void execute() {
      Animation animation = UIViewOperationQueue.this.mAnimationRegistry.getAnimation(this.mAnimationID);
      if (animation != null)
        animation.cancel(); 
    }
  }
  
  final class RemoveRootViewOperation extends ViewOperation {
    public RemoveRootViewOperation(int param1Int) {
      super(param1Int);
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.removeRootView(this.mTag);
    }
  }
  
  final class SendAccessibilityEvent extends ViewOperation {
    private final int mEventType;
    
    private SendAccessibilityEvent(int param1Int1, int param1Int2) {
      super(param1Int1);
      this.mEventType = param1Int2;
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.sendAccessibilityEvent(this.mTag, this.mEventType);
    }
  }
  
  final class SetChildrenOperation extends ViewOperation {
    private final ReadableArray mChildrenTags;
    
    public SetChildrenOperation(int param1Int, ReadableArray param1ReadableArray) {
      super(param1Int);
      this.mChildrenTags = param1ReadableArray;
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.setChildren(this.mTag, this.mChildrenTags);
    }
  }
  
  class SetLayoutAnimationEnabledOperation implements UIOperation {
    private final boolean mEnabled;
    
    private SetLayoutAnimationEnabledOperation(boolean param1Boolean) {
      this.mEnabled = param1Boolean;
    }
    
    public void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.setLayoutAnimationEnabled(this.mEnabled);
    }
  }
  
  final class ShowPopupMenuOperation extends ViewOperation {
    private final Callback mError;
    
    private final ReadableArray mItems;
    
    private final Callback mSuccess;
    
    public ShowPopupMenuOperation(int param1Int, ReadableArray param1ReadableArray, Callback param1Callback1, Callback param1Callback2) {
      super(param1Int);
      this.mItems = param1ReadableArray;
      this.mError = param1Callback1;
      this.mSuccess = param1Callback2;
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.showPopupMenu(this.mTag, this.mItems, this.mSuccess, this.mError);
    }
  }
  
  class UIBlockOperation implements UIOperation {
    private final UIBlock mBlock;
    
    public UIBlockOperation(UIBlock param1UIBlock) {
      this.mBlock = param1UIBlock;
    }
    
    public void execute() {
      this.mBlock.execute(UIViewOperationQueue.this.mNativeViewHierarchyManager);
    }
  }
  
  public static interface UIOperation {
    void execute();
  }
  
  final class UpdateLayoutOperation extends ViewOperation {
    private final int mHeight;
    
    private final int mParentTag;
    
    private final int mWidth;
    
    private final int mX;
    
    private final int mY;
    
    public UpdateLayoutOperation(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
      super(param1Int2);
      this.mParentTag = param1Int1;
      this.mX = param1Int3;
      this.mY = param1Int4;
      this.mWidth = param1Int5;
      this.mHeight = param1Int6;
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.updateLayout(this.mParentTag, this.mTag, this.mX, this.mY, this.mWidth, this.mHeight);
    }
  }
  
  final class UpdatePropertiesOperation extends ViewOperation {
    private final ReactStylesDiffMap mProps;
    
    private UpdatePropertiesOperation(int param1Int, ReactStylesDiffMap param1ReactStylesDiffMap) {
      super(param1Int);
      this.mProps = param1ReactStylesDiffMap;
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.updateProperties(this.mTag, this.mProps);
    }
  }
  
  final class UpdateViewExtraData extends ViewOperation {
    private final Object mExtraData;
    
    public UpdateViewExtraData(int param1Int, Object param1Object) {
      super(param1Int);
      this.mExtraData = param1Object;
    }
    
    public final void execute() {
      UIViewOperationQueue.this.mNativeViewHierarchyManager.updateViewExtraData(this.mTag, this.mExtraData);
    }
  }
  
  abstract class ViewOperation implements UIOperation {
    public int mTag;
    
    public ViewOperation(int param1Int) {
      this.mTag = param1Int;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\UIViewOperationQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */