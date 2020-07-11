package com.tt.miniapp.component.nativeview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.view.ScreenVisibilityDetector;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.miniapphost.util.NativeDimenUtil;
import com.tt.option.ad.b;
import com.tt.option.ad.e;
import com.tt.option.ad.f;
import com.tt.option.e.k;
import org.json.JSONException;
import org.json.JSONObject;

public class AdContainerView extends FrameLayout implements NativeComponent, ScreenVisibilityDetector.OnScreenVisibilityChangedListener, e.a {
  private boolean isRequestAdSuccess = false;
  
  private e mAdViewManager;
  
  private f mAdViewModel;
  
  public Handler mHandler;
  
  private volatile long mLastRequestTime = 0L;
  
  private AbsoluteLayout mParentView;
  
  private WebViewManager.IRender mRender;
  
  private int mViewId;
  
  public AdContainerView(int paramInt, AbsoluteLayout paramAbsoluteLayout, WebViewManager.IRender paramIRender) {
    super((Context)AppbrandContext.getInst().getCurrentActivity());
    this.mViewId = paramInt;
    this.mParentView = paramAbsoluteLayout;
    this.mRender = paramIRender;
    this.mAdViewManager = HostDependManager.getInst().createAdViewManager(this);
  }
  
  private boolean canRefreshAd() {
    e e1 = this.mAdViewManager;
    return (e1 != null && e1.a());
  }
  
  private void handleTimer() {
    if (!canRefreshAd())
      return; 
    if (this.isRequestAdSuccess) {
      f f1 = this.mAdViewModel;
      if (f1 != null && !f1.g) {
        startTimerIfNecessary(this.mAdViewModel.l);
        return;
      } 
    } 
    stopTimerIfNecessary();
  }
  
  private void setAdContainerVisible(boolean paramBoolean) {
    byte b;
    if (paramBoolean) {
      b = 0;
    } else {
      b = 4;
    } 
    setVisibility(b);
  }
  
  private void setAdViewVisible(boolean paramBoolean) {}
  
  private void startTimerIfNecessary(int paramInt) {
    if (!canRefreshAd())
      return; 
    stopTimerIfNecessary();
    if (paramInt <= 0)
      return; 
    int i = paramInt;
    if (paramInt < 30)
      i = 30; 
    final long delayMillis = i * 1000L;
    this.mHandler = new Handler(Looper.getMainLooper());
    this.mHandler.postDelayed(new Runnable() {
          public void run() {
            AdContainerView.this.requestAd((k)null);
            if (AdContainerView.this.mHandler != null)
              AdContainerView.this.mHandler.postDelayed(this, delayMillis); 
          }
        }l);
  }
  
  private void stopTimerIfNecessary() {
    if (!canRefreshAd())
      return; 
    Handler handler = this.mHandler;
    if (handler != null) {
      handler.removeCallbacksAndMessages(null);
      this.mHandler = null;
    } 
  }
  
  private void updateAdViewVisible(boolean paramBoolean) {}
  
  public void addView(String paramString, k paramk) {
    int i = 1;
    AppBrandLogger.d("AdView", new Object[] { "addView ", paramString });
    if (TextUtils.isEmpty(paramString)) {
      b.a("", "", 1001, "参数错误,view为空");
      notifyErrorState(1001, "参数错误,view为空", paramk);
      return;
    } 
    f f1 = new f(paramString);
    if (!f1.a()) {
      b.a(f1.b(), f1.a, 1001, "参数错误,adUnitId为空");
      notifyErrorState(1001, "参数错误,adUnitId为空", paramk);
      return;
    } 
    this.mAdViewModel = f1;
    e e1 = this.mAdViewManager;
    if (e1 != null) {
      View view = e1.a(getContext());
      if (view == null) {
        i = 0;
      } else {
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof ViewGroup)
          ((ViewGroup)viewParent).removeView(view); 
        addView(view, -1, -1);
      } 
      if (i) {
        this.mParentView.addView((View)this);
        AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)getLayoutParams();
        if (this.mAdViewModel.b) {
          layoutParams.width = this.mAdViewModel.e;
          layoutParams.height = this.mAdViewModel.f;
          int n = this.mAdViewModel.c;
          int m = this.mAdViewModel.d;
          int j = n;
          i = m;
          if (!this.mAdViewModel.m) {
            j = n;
            i = m;
            if (!this.mAdViewModel.j) {
              j = n - this.mParentView.getCurScrollX();
              i = m - this.mParentView.getCurScrollY();
            } 
          } 
          layoutParams.x = j;
          layoutParams.y = i;
        } 
        if (f1.i)
          layoutParams.zIndex = f1.h; 
        if (f1.k)
          layoutParams.isFixed = f1.j; 
        setAdContainerVisible(false);
        setAdViewVisible(false);
        requestLayout();
        requestAd(paramk);
        return;
      } 
    } 
    b.a(f1.b(), f1.a, 1003, "feature is not supported in app");
    notifyErrorState(1003, "feature is not supported in app", paramk);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    return (paramMotionEvent.getAction() == 2) ? false : super.dispatchTouchEvent(paramMotionEvent);
  }
  
  public f getAdViewModel() {
    return this.mAdViewModel;
  }
  
  public void notifyErrorState(int paramInt, String paramString, k paramk) {
    AppBrandLogger.e("AdView", new Object[] { "errMsg", paramString, "errCode", Integer.valueOf(paramInt) });
    if (paramk != null)
      paramk.invokeHandler((new JsonBuilder(paramk.buildErrorMsg("fail"))).put("data", (new JsonBuilder()).put("errCode", Integer.valueOf(paramInt)).put("errMsg", paramString).build()).build().toString()); 
  }
  
  protected void notifyStateChanged(JSONObject paramJSONObject, k paramk) {
    if (paramk != null)
      paramk.invokeHandler(paramJSONObject.toString()); 
  }
  
  public boolean onBackPressed() {
    return false;
  }
  
  public void onCloseAdContainer() {
    setAdContainerVisible(false);
    if (this.mAdViewModel != null && this.mRender != null) {
      JSONObject jSONObject = (new JsonBuilder()).put("adUnitId", this.mAdViewModel.a).put("state", "close").put("data", (new JsonBuilder()).put("viewId", Integer.valueOf(this.mViewId)).build()).build();
      AppbrandApplicationImpl.getInst().getWebViewManager().publishDirectly(this.mRender.getWebViewId(), "onBannerAdStateChange", jSONObject.toString());
      this.mRender.getNativeViewManager().removeView(this.mViewId, null);
    } 
  }
  
  public void onDestroy() {}
  
  public void onScreenVisibilityChanged(View paramView, boolean paramBoolean) {}
  
  public void onUpdateAdContainer(int paramInt1, int paramInt2, k paramk) {
    f f1 = this.mAdViewModel;
    f1.e = paramInt1;
    f1.f = paramInt2;
    AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)getLayoutParams();
    layoutParams.width = this.mAdViewModel.e;
    layoutParams.height = this.mAdViewModel.f;
    setAdContainerVisible(this.mAdViewModel.g ^ true);
    requestLayout();
    if (paramk == null)
      return; 
    notifyStateChanged((new JsonBuilder(paramk.buildErrorMsg("ok"))).put("data", (new JsonBuilder()).put("viewId", Integer.valueOf(this.mViewId)).put("width", Integer.valueOf(NativeDimenUtil.convertPxToRx(this.mAdViewModel.e))).put("height", Integer.valueOf(NativeDimenUtil.convertPxToRx(this.mAdViewModel.f))).build()).build(), paramk);
    this.isRequestAdSuccess = true;
    handleTimer();
  }
  
  public void onViewPause() {
    stopTimerIfNecessary();
  }
  
  public void onViewResume() {
    if (this.mAdViewModel == null)
      return; 
    StringBuilder stringBuilder = new StringBuilder("onRecoverForeground=");
    stringBuilder.append(hashCode());
    AppBrandLogger.d("AdView", new Object[] { stringBuilder.toString() });
    if (canRefreshAd() && Math.abs(System.currentTimeMillis() - this.mLastRequestTime) > 30000L)
      requestAd((k)null); 
    handleTimer();
  }
  
  public void removeView(int paramInt, k paramk) {
    stopTimerIfNecessary();
  }
  
  public void requestAd(k paramk) {
    this.mLastRequestTime = System.currentTimeMillis();
  }
  
  public void updateView(String paramString, k paramk) {
    AppBrandLogger.d("AdView", new Object[] { "updateView ", paramString });
    if (TextUtils.isEmpty(paramString)) {
      notifyErrorState(1001, "参数错误,view为空", paramk);
      return;
    } 
    f f1 = this.mAdViewModel;
    if (f1 == null) {
      notifyErrorState(1003, "内部错误,mAdModel为null", paramk);
      return;
    } 
    boolean bool = f1.g;
    f1 = this.mAdViewModel;
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      if (TextUtils.equals(jSONObject.optString("unitId"), f1.a)) {
        JSONObject jSONObject1 = jSONObject.optJSONObject("position");
        if (jSONObject1 != null) {
          if (jSONObject1.has("left"))
            f1.c = NativeDimenUtil.convertRxToPx(jSONObject1.optInt("left")); 
          if (jSONObject1.has("top"))
            f1.d = NativeDimenUtil.convertRxToPx(jSONObject1.optInt("top")); 
        } else {
          f1.b = false;
        } 
        f1.g = jSONObject.optBoolean("hide", f1.g);
        if (jSONObject.has("zIndex")) {
          f1.i = true;
          f1.h = jSONObject.optInt("zIndex");
        } else {
          f1.i = false;
        } 
        if (jSONObject.has("fixed")) {
          f1.k = true;
          f1.j = jSONObject.optBoolean("fixed");
        } else {
          f1.k = false;
        } 
        if (jSONObject.has("isInScrollView"))
          f1.m = jSONObject.optBoolean("isInScrollView"); 
      } 
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "AdViewModel", jSONException.getStackTrace());
    } 
    if (!this.mAdViewModel.a()) {
      notifyErrorState(1001, "参数错误,adUnitId为空", paramk);
      return;
    } 
    AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)getLayoutParams();
    if (this.mAdViewModel.b) {
      int n = this.mAdViewModel.c;
      int m = this.mAdViewModel.d;
      int j = n;
      int i = m;
      if (!this.mAdViewModel.m) {
        j = n - this.mParentView.getCurScrollX();
        i = m - this.mParentView.getCurScrollY();
        this.mParentView.updateCurScrollXY(this.mViewId);
      } 
      layoutParams.x = j;
      layoutParams.y = i;
    } 
    if (this.mAdViewModel.i)
      layoutParams.zIndex = this.mAdViewModel.h; 
    if (this.mAdViewModel.k)
      layoutParams.isFixed = this.mAdViewModel.j; 
    setAdContainerVisible(this.mAdViewModel.g ^ true);
    updateAdViewVisible(this.mAdViewModel.g ^ true);
    requestLayout();
    notifyStateChanged((new JsonBuilder(paramk.buildErrorMsg("ok"))).put("data", (new JsonBuilder()).put("width", Integer.valueOf(NativeDimenUtil.convertPxToRx(this.mAdViewModel.e))).put("height", Integer.valueOf(NativeDimenUtil.convertPxToRx(this.mAdViewModel.f))).build()).build(), paramk);
    if (!this.mAdViewModel.g) {
      if (bool) {
        handleTimer();
        return;
      } 
    } else {
      stopTimerIfNecessary();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\AdContainerView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */