package com.facebook.react;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.m.a;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.appregistry.AppRegistry;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.deviceinfo.DeviceInfoModule;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.JSTouchDispatcher;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.common.MeasureSpecProvider;
import com.facebook.react.uimanager.common.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.events.EventDispatcher;

public class ReactRootView extends SizeMonitoringFrameLayout implements RootView, MeasureSpecProvider {
  private final ReactAndroidHWInputDeviceHelper mAndroidHWInputDeviceHelper = new ReactAndroidHWInputDeviceHelper(this);
  
  private Bundle mAppProperties;
  
  private CustomGlobalLayoutListener mCustomGlobalLayoutListener;
  
  private long mFirstDrawTime = 0L;
  
  private int mHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
  
  public boolean mIsAttachedToInstance;
  
  private boolean mIsFabric = false;
  
  private Runnable mJSEntryPoint;
  
  private String mJSModuleName;
  
  private final JSTouchDispatcher mJSTouchDispatcher = new JSTouchDispatcher((ViewGroup)this);
  
  public ReactInstanceManager mReactInstanceManager;
  
  private ReactRootViewEventListener mRootViewEventListener;
  
  private int mRootViewTag;
  
  private boolean mShouldLogContentAppeared;
  
  private long mStartLoadime = 0L;
  
  private boolean mWasMeasured = false;
  
  private int mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
  
  public ReactRootView(Context paramContext) {
    super(paramContext);
    this.mFirstDrawTime = 0L;
    this.mStartLoadime = 0L;
  }
  
  public ReactRootView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    this.mFirstDrawTime = 0L;
    this.mStartLoadime = 0L;
  }
  
  public ReactRootView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    this.mFirstDrawTime = 0L;
    this.mStartLoadime = 0L;
  }
  
  private void attachToReactInstanceManager() {
    a.a(0L, "attachToReactInstanceManager");
    try {
      boolean bool = this.mIsAttachedToInstance;
      if (bool)
        return; 
      this.mIsAttachedToInstance = true;
      ((ReactInstanceManager)a.b(this.mReactInstanceManager)).attachRootView(this);
      getViewTreeObserver().addOnGlobalLayoutListener(getCustomGlobalLayoutListener());
      return;
    } finally {
      a.a(0L);
    } 
  }
  
  private void defaultJSEntryPoint() {
    a.a(0L, "ReactRootView.runApplication");
    try {
      if (this.mReactInstanceManager == null || !this.mIsAttachedToInstance)
        return; 
      ReactContext reactContext = this.mReactInstanceManager.getCurrentReactContext();
      if (reactContext == null)
        return; 
      CatalystInstance catalystInstance = reactContext.getCatalystInstance();
      WritableNativeMap writableNativeMap = new WritableNativeMap();
      writableNativeMap.putDouble("rootTag", getRootViewTag());
      Bundle bundle = getAppProperties();
      if (bundle != null)
        writableNativeMap.putMap("initialProps", Arguments.fromBundle(bundle)); 
      this.mShouldLogContentAppeared = true;
      String str = getJSModuleName();
      ((AppRegistry)catalystInstance.getJSModule(AppRegistry.class)).runApplication(str, (WritableMap)writableNativeMap);
      return;
    } finally {
      a.a(0L);
    } 
  }
  
  private void dispatchJSTouchEvent(MotionEvent paramMotionEvent) {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
      a.b("ReactNative", "Unable to dispatch touch to JS as the catalyst instance has not been attached");
      return;
    } 
    EventDispatcher eventDispatcher = ((UIManagerModule)this.mReactInstanceManager.getCurrentReactContext().getNativeModule(UIManagerModule.class)).getEventDispatcher();
    this.mJSTouchDispatcher.handleTouchEvent(paramMotionEvent, eventDispatcher);
  }
  
  private void enableLayoutCalculation() {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager == null) {
      a.b("ReactNative", "Unable to enable layout calculation for uninitialized ReactInstanceManager");
      return;
    } 
    ReactContext reactContext = reactInstanceManager.getCurrentReactContext();
    if (reactContext != null)
      ((UIManagerModule)reactContext.getCatalystInstance().getNativeModule(UIManagerModule.class)).getUIImplementation().enableLayoutCalculationForRootNode(getRootViewTag()); 
  }
  
  private CustomGlobalLayoutListener getCustomGlobalLayoutListener() {
    if (this.mCustomGlobalLayoutListener == null)
      this.mCustomGlobalLayoutListener = new CustomGlobalLayoutListener(); 
    return this.mCustomGlobalLayoutListener;
  }
  
  private void updateRootLayoutSpecs(final int widthMeasureSpec, final int heightMeasureSpec) {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager == null) {
      a.b("ReactNative", "Unable to update root layout specs for uninitialized ReactInstanceManager");
      return;
    } 
    final ReactContext reactApplicationContext = reactInstanceManager.getCurrentReactContext();
    if (reactContext != null)
      reactContext.runOnLayoutThread((Runnable)new GuardedRunnable(reactContext) {
            public void runGuarded() {
              ((UIManagerModule)reactApplicationContext.getCatalystInstance().getNativeModule(UIManagerModule.class)).updateRootLayoutSpecs(ReactRootView.this.getRootViewTag(), widthMeasureSpec, heightMeasureSpec);
            }
          }); 
  }
  
  protected void dispatchDraw(Canvas paramCanvas) {
    try {
      super.dispatchDraw(paramCanvas);
      return;
    } catch (StackOverflowError stackOverflowError) {
      handleException(stackOverflowError);
      return;
    } 
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
      a.b("ReactNative", "Unable to handle key event as the catalyst instance has not been attached");
      return super.dispatchKeyEvent(paramKeyEvent);
    } 
    this.mAndroidHWInputDeviceHelper.handleKeyEvent(paramKeyEvent);
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    a.a(this.mIsAttachedToInstance ^ true, "The application this ReactRootView was rendering was not unmounted before the ReactRootView was garbage collected. This usually means that your application is leaking large amounts of memory. To solve this, make sure to call ReactRootView#unmountReactApplication in the onDestroy() of your hosting Activity or in the onDestroyView() of your hosting Fragment.");
  }
  
  public Bundle getAppProperties() {
    return this.mAppProperties;
  }
  
  public int getHeightMeasureSpec() {
    return (!this.mWasMeasured && getLayoutParams() != null && (getLayoutParams()).height > 0) ? View.MeasureSpec.makeMeasureSpec((getLayoutParams()).height, 1073741824) : this.mHeightMeasureSpec;
  }
  
  String getJSModuleName() {
    return (String)a.b(this.mJSModuleName);
  }
  
  public ReactInstanceManager getReactInstanceManager() {
    return this.mReactInstanceManager;
  }
  
  public int getRootViewTag() {
    return this.mRootViewTag;
  }
  
  public int getWidthMeasureSpec() {
    return (!this.mWasMeasured && getLayoutParams() != null && (getLayoutParams()).width > 0) ? View.MeasureSpec.makeMeasureSpec((getLayoutParams()).width, 1073741824) : this.mWidthMeasureSpec;
  }
  
  public void handleException(Throwable paramThrowable) {
    Exception exception;
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager != null && reactInstanceManager.getCurrentReactContext() != null) {
      IllegalViewOperationException illegalViewOperationException;
      if (paramThrowable instanceof StackOverflowError) {
        illegalViewOperationException = new IllegalViewOperationException("StackOverflowException", (View)this, paramThrowable);
      } else if (illegalViewOperationException instanceof Exception) {
        exception = (Exception)illegalViewOperationException;
      } else {
        exception = new RuntimeException(exception);
      } 
      this.mReactInstanceManager.getCurrentReactContext().handleException(exception);
      return;
    } 
    throw new RuntimeException(exception);
  }
  
  public void invokeDefaultJSEntryPoint(Bundle paramBundle) {
    UiThreadUtil.assertOnUiThread();
    if (paramBundle != null)
      this.mAppProperties = paramBundle; 
    defaultJSEntryPoint();
  }
  
  void invokeJSEntryPoint() {
    Runnable runnable = this.mJSEntryPoint;
    if (runnable == null) {
      defaultJSEntryPoint();
      return;
    } 
    runnable.run();
  }
  
  public boolean isFabric() {
    return this.mIsFabric;
  }
  
  public void onAttachedToReactInstance() {
    ReactRootViewEventListener reactRootViewEventListener = this.mRootViewEventListener;
    if (reactRootViewEventListener != null)
      reactRootViewEventListener.onAttachedToReactInstance(this); 
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.mIsAttachedToInstance)
      getViewTreeObserver().addOnGlobalLayoutListener(getCustomGlobalLayoutListener()); 
  }
  
  public void onChildStartedNativeGesture(MotionEvent paramMotionEvent) {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
      a.b("ReactNative", "Unable to dispatch touch to JS as the catalyst instance has not been attached");
      return;
    } 
    EventDispatcher eventDispatcher = ((UIManagerModule)this.mReactInstanceManager.getCurrentReactContext().getNativeModule(UIManagerModule.class)).getEventDispatcher();
    this.mJSTouchDispatcher.onChildStartedNativeGesture(paramMotionEvent, eventDispatcher);
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (this.mIsAttachedToInstance) {
      if (Build.VERSION.SDK_INT >= 16) {
        getViewTreeObserver().removeOnGlobalLayoutListener(getCustomGlobalLayoutListener());
        return;
      } 
      getViewTreeObserver().removeGlobalOnLayoutListener(getCustomGlobalLayoutListener());
    } 
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect) {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
      a.b("ReactNative", "Unable to handle focus changed event as the catalyst instance has not been attached");
      super.onFocusChanged(paramBoolean, paramInt, paramRect);
      return;
    } 
    this.mAndroidHWInputDeviceHelper.clearFocus();
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    dispatchJSTouchEvent(paramMotionEvent);
    return super.onInterceptTouchEvent(paramMotionEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    a.a(0L, "ReactRootView.onMeasure");
    try {
      this.mWidthMeasureSpec = paramInt1;
      this.mHeightMeasureSpec = paramInt2;
      int i = View.MeasureSpec.getMode(paramInt1);
      int j = 0;
      if (i == Integer.MIN_VALUE || i == 0) {
        i = 0;
        paramInt1 = 0;
        while (i < getChildCount()) {
          View view = getChildAt(i);
          paramInt1 = Math.max(paramInt1, view.getLeft() + view.getMeasuredWidth() + view.getPaddingLeft() + view.getPaddingRight());
          i++;
        } 
        i = paramInt1;
      } else {
        i = View.MeasureSpec.getSize(paramInt1);
      } 
      paramInt1 = View.MeasureSpec.getMode(paramInt2);
      if (paramInt1 == Integer.MIN_VALUE || paramInt1 == 0) {
        paramInt1 = 0;
        paramInt2 = j;
        while (true) {
          j = paramInt1;
          if (paramInt2 < getChildCount()) {
            View view = getChildAt(paramInt2);
            paramInt1 = Math.max(paramInt1, view.getTop() + view.getMeasuredHeight() + view.getPaddingTop() + view.getPaddingBottom());
            paramInt2++;
            continue;
          } 
          break;
        } 
      } else {
        j = View.MeasureSpec.getSize(paramInt2);
      } 
      setMeasuredDimension(i, j);
      this.mWasMeasured = true;
      if (this.mReactInstanceManager != null && !this.mIsAttachedToInstance) {
        attachToReactInstanceManager();
      } else {
        updateRootLayoutSpecs(this.mWidthMeasureSpec, this.mHeightMeasureSpec);
      } 
      enableLayoutCalculation();
      return;
    } finally {
      a.a(0L);
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    dispatchJSTouchEvent(paramMotionEvent);
    super.onTouchEvent(paramMotionEvent);
    return true;
  }
  
  public void onViewAdded(View paramView) {
    super.onViewAdded(paramView);
    if (this.mShouldLogContentAppeared) {
      this.mShouldLogContentAppeared = false;
      if (this.mJSModuleName != null)
        ReactMarker.logMarker(ReactMarkerConstants.CONTENT_APPEARED, this.mJSModuleName, this.mRootViewTag); 
    } 
    setStartLoadime();
    setFirstDrawTime();
  }
  
  public void requestChildFocus(View paramView1, View paramView2) {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager == null || !this.mIsAttachedToInstance || reactInstanceManager.getCurrentReactContext() == null) {
      a.b("ReactNative", "Unable to handle child focus changed event as the catalyst instance has not been attached");
      super.requestChildFocus(paramView1, paramView2);
      return;
    } 
    this.mAndroidHWInputDeviceHelper.onFocusChanged(paramView2);
    super.requestChildFocus(paramView1, paramView2);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    if (getParent() != null)
      getParent().requestDisallowInterceptTouchEvent(paramBoolean); 
  }
  
  void sendEvent(String paramString, WritableMap paramWritableMap) {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager != null)
      ((DeviceEventManagerModule.RCTDeviceEventEmitter)reactInstanceManager.getCurrentReactContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(paramString, paramWritableMap); 
  }
  
  public void setAppProperties(Bundle paramBundle) {
    UiThreadUtil.assertOnUiThread();
    this.mAppProperties = paramBundle;
    if (getRootViewTag() != 0)
      invokeJSEntryPoint(); 
  }
  
  public void setEventListener(ReactRootViewEventListener paramReactRootViewEventListener) {
    this.mRootViewEventListener = paramReactRootViewEventListener;
  }
  
  public void setFirstDrawTime() {
    try {
      if (this.mReactInstanceManager != null && this.mReactInstanceManager.getCurrentReactContext() != null) {
        CatalystInstance catalystInstance = this.mReactInstanceManager.getCurrentReactContext().getCatalystInstance();
        if (catalystInstance != null && catalystInstance.isFirstDrawn()) {
          this.mFirstDrawTime = System.currentTimeMillis();
          catalystInstance.startFirstDraw();
          catalystInstance.setFirstDraw(this.mFirstDrawTime);
        } 
      } 
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      return;
    } 
  }
  
  public void setIsFabric(boolean paramBoolean) {
    this.mIsFabric = paramBoolean;
  }
  
  public void setJSEntryPoint(Runnable paramRunnable) {
    this.mJSEntryPoint = paramRunnable;
  }
  
  public void setRootViewTag(int paramInt) {
    this.mRootViewTag = paramInt;
  }
  
  public void setStartLoadime() {
    try {
      if (this.mStartLoadime == 0L)
        this.mStartLoadime = System.currentTimeMillis(); 
      if (this.mReactInstanceManager != null && this.mReactInstanceManager.getCurrentReactContext() != null && this.mReactInstanceManager.getCurrentReactContext().getCatalystInstance() != null)
        this.mReactInstanceManager.getCurrentReactContext().getCatalystInstance().setStartLoad(this.mStartLoadime); 
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      return;
    } 
  }
  
  void simulateAttachForTesting() {
    this.mIsAttachedToInstance = true;
  }
  
  public void startReactApplication(ReactInstanceManager paramReactInstanceManager, String paramString) {
    startReactApplication(paramReactInstanceManager, paramString, (Bundle)null);
  }
  
  public void startReactApplication(ReactInstanceManager paramReactInstanceManager, String paramString, Bundle paramBundle) {
    a.a(0L, "startReactApplication");
  }
  
  public void unmountReactApplication() {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager != null && this.mIsAttachedToInstance) {
      reactInstanceManager.detachRootView(this);
      this.mReactInstanceManager = null;
      this.mIsAttachedToInstance = false;
    } 
    this.mShouldLogContentAppeared = false;
  }
  
  class CustomGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private int mDeviceRotation;
    
    private int mKeyboardHeight;
    
    private final int mMinKeyboardHeightDetected;
    
    private DisplayMetrics mScreenMetrics = new DisplayMetrics();
    
    private final Rect mVisibleViewArea;
    
    private DisplayMetrics mWindowMetrics = new DisplayMetrics();
    
    CustomGlobalLayoutListener() {
      DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(ReactRootView.this.getContext().getApplicationContext());
      this.mVisibleViewArea = new Rect();
      this.mMinKeyboardHeightDetected = (int)PixelUtil.toPixelFromDIP(60.0F);
    }
    
    private boolean areMetricsEqual(DisplayMetrics param1DisplayMetrics1, DisplayMetrics param1DisplayMetrics2) {
      return (Build.VERSION.SDK_INT >= 17) ? param1DisplayMetrics1.equals(param1DisplayMetrics2) : ((param1DisplayMetrics1.widthPixels == param1DisplayMetrics2.widthPixels && param1DisplayMetrics1.heightPixels == param1DisplayMetrics2.heightPixels && param1DisplayMetrics1.density == param1DisplayMetrics2.density && param1DisplayMetrics1.densityDpi == param1DisplayMetrics2.densityDpi && param1DisplayMetrics1.scaledDensity == param1DisplayMetrics2.scaledDensity && param1DisplayMetrics1.xdpi == param1DisplayMetrics2.xdpi && param1DisplayMetrics1.ydpi == param1DisplayMetrics2.ydpi));
    }
    
    private void checkForDeviceDimensionsChanges() {
      DisplayMetricsHolder.initDisplayMetrics(ReactRootView.this.getContext());
      if (!areMetricsEqual(this.mWindowMetrics, DisplayMetricsHolder.getWindowDisplayMetrics()) || !areMetricsEqual(this.mScreenMetrics, DisplayMetricsHolder.getScreenDisplayMetrics())) {
        this.mWindowMetrics.setTo(DisplayMetricsHolder.getWindowDisplayMetrics());
        this.mScreenMetrics.setTo(DisplayMetricsHolder.getScreenDisplayMetrics());
        emitUpdateDimensionsEvent();
      } 
    }
    
    private void checkForDeviceOrientationChanges() {
      int i = ((WindowManager)ReactRootView.this.getContext().getSystemService("window")).getDefaultDisplay().getRotation();
      if (this.mDeviceRotation == i)
        return; 
      this.mDeviceRotation = i;
      emitOrientationChanged(i);
    }
    
    private void checkForKeyboardEvents() {
      ReactRootView.this.getRootView().getWindowVisibleDisplayFrame(this.mVisibleViewArea);
      int i = (DisplayMetricsHolder.getWindowDisplayMetrics()).heightPixels - this.mVisibleViewArea.bottom;
      if (this.mKeyboardHeight != i && i > this.mMinKeyboardHeightDetected) {
        this.mKeyboardHeight = i;
        WritableMap writableMap1 = Arguments.createMap();
        WritableMap writableMap2 = Arguments.createMap();
        writableMap2.putDouble("screenY", PixelUtil.toDIPFromPixel(this.mVisibleViewArea.bottom));
        writableMap2.putDouble("screenX", PixelUtil.toDIPFromPixel(this.mVisibleViewArea.left));
        writableMap2.putDouble("width", PixelUtil.toDIPFromPixel(this.mVisibleViewArea.width()));
        writableMap2.putDouble("height", PixelUtil.toDIPFromPixel(this.mKeyboardHeight));
        writableMap1.putMap("endCoordinates", writableMap2);
        ReactRootView.this.sendEvent("keyboardDidShow", writableMap1);
        return;
      } 
      if (this.mKeyboardHeight != 0 && i <= this.mMinKeyboardHeightDetected) {
        this.mKeyboardHeight = 0;
        ReactRootView.this.sendEvent("keyboardDidHide", (WritableMap)null);
      } 
    }
    
    private void emitOrientationChanged(int param1Int) {
      double d;
      String str;
      boolean bool = true;
      if (param1Int != 0) {
        if (param1Int != 1) {
          if (param1Int != 2) {
            if (param1Int != 3)
              return; 
            d = 90.0D;
            str = "landscape-secondary";
          } else {
            d = 180.0D;
            str = "portrait-secondary";
            bool = false;
          } 
        } else {
          d = -90.0D;
          str = "landscape-primary";
        } 
      } else {
        d = 0.0D;
        str = "portrait-primary";
        bool = false;
      } 
      WritableMap writableMap = Arguments.createMap();
      writableMap.putString("name", str);
      writableMap.putDouble("rotationDegrees", d);
      writableMap.putBoolean("isLandscape", bool);
      ReactRootView.this.sendEvent("namedOrientationDidChange", writableMap);
    }
    
    private void emitUpdateDimensionsEvent() {
      ((DeviceInfoModule)ReactRootView.this.mReactInstanceManager.getCurrentReactContext().getNativeModule(DeviceInfoModule.class)).emitUpdateDimensionsEvent();
    }
    
    public void onGlobalLayout() {
      if (ReactRootView.this.mReactInstanceManager != null && ReactRootView.this.mIsAttachedToInstance) {
        if (ReactRootView.this.mReactInstanceManager.getCurrentReactContext() == null)
          return; 
        checkForKeyboardEvents();
        checkForDeviceOrientationChanges();
        checkForDeviceDimensionsChanges();
      } 
    }
  }
  
  public static interface ReactRootViewEventListener {
    void onAttachedToReactInstance(ReactRootView param1ReactRootView);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ReactRootView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */