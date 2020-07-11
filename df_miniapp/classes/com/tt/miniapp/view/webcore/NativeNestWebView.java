package com.tt.miniapp.view.webcore;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.IKeyBoardStateChange;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.InputBean;
import com.tt.miniapp.component.nativeview.InputComponent;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.util.VideoFullScreenHelper;
import com.tt.miniapp.view.refresh.SwipeToLoadLayout;
import com.tt.miniapp.view.webcore.scroller.WebViewScroller;
import com.tt.miniapp.view.webcore.scroller.WebViewScrollerFactory;
import com.tt.miniapp.view.webcore.scroller.WebViewScrollerListener;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.miniapphost.util.UIUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class NativeNestWebView extends NativeNestWebViewLoadBase implements WebViewScrollerListener, LanguageChangeListener {
  public int CONFIRM_BAR_HEIGHT;
  
  private final List<WeakReference<IKeyBoardStateChange>> keyboardStateList = new ArrayList<WeakReference<IKeyBoardStateChange>>();
  
  public AbsoluteLayout mAbsoluteLayout;
  
  public AbsoluteLayout mBackAbsoluteLayout;
  
  private NoScrollView mBackNativeContainer;
  
  public LinearLayout mConfirmHolder;
  
  private Button mConfirmTV;
  
  public int mCurrentScrollerX;
  
  public int mCurrentScrollerY;
  
  private int mKeyboardHeight;
  
  private InputBean mLastFoucInput;
  
  public int mLastKeyboardHeight;
  
  public int mLastScreenHeight;
  
  private NoScrollView mNativeContainer;
  
  public NativeViewManager mNativeViewManager;
  
  private SwipeRefreshTargetDelegate mOffsetTargetView;
  
  private int mOriginWebviewBottom;
  
  public TTWebViewSupportWebView.OnScrollListener mScrollListener;
  
  private WebViewScroller mScroller;
  
  private boolean mShowConfirmBar;
  
  public NativeNestWebView(Context paramContext, AppbrandApplicationImpl paramAppbrandApplicationImpl, WebViewManager.IRender paramIRender) {
    super(paramContext, paramAppbrandApplicationImpl, paramIRender);
    this.CONFIRM_BAR_HEIGHT = (int)UIUtils.dip2Px(paramContext, 50.0F);
    initView();
    this.mNativeViewManager = new NativeViewManager(this.mRender, this, getWebViewId());
  }
  
  private void initTargetView() {
    if (this.mOffsetTargetView == null) {
      ViewParent viewParent = getParent();
      if (viewParent instanceof SwipeRefreshTargetDelegate)
        this.mOffsetTargetView = (SwipeRefreshTargetDelegate)viewParent; 
    } 
  }
  
  private void offsetTargetView(int paramInt) {
    initTargetView();
    SwipeRefreshTargetDelegate swipeRefreshTargetDelegate = this.mOffsetTargetView;
    if (swipeRefreshTargetDelegate == null)
      return; 
    swipeRefreshTargetDelegate.offsetTopAndBottom(paramInt);
  }
  
  private void onKeyboardHide2() {
    initTargetView();
    if (this.mOffsetTargetView == null)
      return; 
    updateOffset(false);
    if (this.mOffsetTargetView.getBottom() == this.mOriginWebviewBottom) {
      this.mScroller.computeAndReset();
      return;
    } 
    offsetTargetView(this.mScroller.computeAndReset());
  }
  
  private void onKeyboardShow2(int paramInt1, int paramInt2) {
    AppBrandLogger.d("tma_NativeNestWebView", new Object[] { "onKeyboardShow keyboardHeight ", Integer.valueOf(paramInt1), " inputId ", Integer.valueOf(paramInt2) });
    initTargetView();
    if (this.mOffsetTargetView == null)
      return; 
    View view = this.mNativeViewManager.getView(paramInt2);
    if (view == null)
      return; 
    if (this.mScroller != null) {
      Rect rect = new Rect();
      this.mNativeContainer.getGlobalVisibleRect(rect);
      smoothOffsetTopAndBottom(this.mScroller.computeOffset(view, paramInt1, rect), view);
    } 
    paramInt2 = this.mRender.getTitleBarHeight();
    int i = DevicesUtil.getStatusBarHeight((Context)AppbrandContext.getInst().getApplicationContext());
    this.mShowConfirmBar = false;
    if (this.mRender != null && TextUtils.equals("textarea", this.mNativeViewManager.getViewType()) && this.mShowConfirmBar) {
      setLastScreenHeight();
      setLastKeyboardHeight();
      setKeyboardHeight(paramInt1);
      this.mConfirmHolder = new LinearLayout(getContext());
      this.mConfirmTV = new Button(getContext());
      int j = this.CONFIRM_BAR_HEIGHT;
      int k = UIUtils.getDeviceHeight(getContext());
      this.mConfirmHolder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
              if (NativeNestWebView.this.mConfirmHolder.getLayoutParams() != null) {
                NativeNestWebView nativeNestWebView = NativeNestWebView.this;
                int i = nativeNestWebView.updateScreenHeight(nativeNestWebView.mLastScreenHeight);
                nativeNestWebView = NativeNestWebView.this;
                int j = nativeNestWebView.updateKeyboardHeight(nativeNestWebView.mLastKeyboardHeight);
                int k = NativeNestWebView.this.getKeyboardHeight();
                int m = NativeNestWebView.this.mRender.getTitleBarHeight();
                int n = DevicesUtil.getStatusBarHeight((Context)AppbrandContext.getInst().getApplicationContext());
                int i1 = NativeNestWebView.this.CONFIRM_BAR_HEIGHT;
                int i2 = UIUtils.getDeviceHeight(NativeNestWebView.this.getContext());
                if (i == 0)
                  return; 
                NativeNestWebView.this.mConfirmHolder.removeAllViews();
                NativeNestWebView.this.mAbsoluteLayout.removeView((View)NativeNestWebView.this.mConfirmHolder);
                NativeNestWebView.this.setConfirmBar(i2 - k + n + m + i1 - j);
              } 
            }
          });
      setListenerOnConfirmBar();
      if (this.mConfirmTV.getLayoutParams() == null)
        setConfirmBar(k - paramInt1 + i + paramInt2 + j); 
    } 
  }
  
  private void setKeyboardHeight(int paramInt) {
    this.mKeyboardHeight = paramInt;
  }
  
  private void setLastKeyboardHeight() {
    Point point = new Point();
    AppbrandContext.getInst().getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(point);
    this.mLastKeyboardHeight = point.y;
  }
  
  private void setLastScreenHeight() {
    Point point = new Point();
    AppbrandContext.getInst().getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(point);
    this.mLastScreenHeight = point.y;
  }
  
  private void setListenerOnConfirmBar() {
    this.mConfirmTV.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            InputBean inputBean = NativeNestWebView.this.mNativeViewManager.getCurrentInputValue();
            int i = inputBean.inputId;
            if (i != -1)
              try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("inputId", i);
                jSONObject.put("value", inputBean.value);
                jSONObject.put("cursor", inputBean.cursor);
                AppBrandLogger.d("testConfirm", new Object[] { Integer.valueOf(i), ",", inputBean.value, ",", Integer.valueOf(inputBean.cursor) });
                NativeNestWebView.this.mApp.getWebViewManager().publish(NativeNestWebView.this.getWebViewId(), "onKeyboardConfirm", jSONObject.toString());
              } catch (JSONException jSONException) {
                AppBrandLogger.stacktrace(6, "tma_NativeNestWebView", jSONException.getStackTrace());
              }  
            InputMethodUtil.hideSoftKeyboard((Activity)AppbrandContext.getInst().getCurrentActivity());
          }
        });
  }
  
  private void updateOffset(boolean paramBoolean) {
    initTargetView();
    SwipeRefreshTargetDelegate swipeRefreshTargetDelegate = this.mOffsetTargetView;
    if (swipeRefreshTargetDelegate == null)
      return; 
    ViewParent viewParent = swipeRefreshTargetDelegate.getParent();
    if (viewParent instanceof SwipeToLoadLayout) {
      boolean bool;
      if (paramBoolean) {
        bool = this.mOffsetTargetView.getTop();
      } else {
        bool = false;
      } 
      ((SwipeToLoadLayout)viewParent).updateOffset(bool);
    } 
  }
  
  public boolean canScrollDown() {
    return (this.mCurrentScrollerY > 0);
  }
  
  public boolean canScrollVertically(int paramInt) {
    return canScrollDown();
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool = super.dispatchTouchEvent(paramMotionEvent);
    int i = paramMotionEvent.getAction();
    if (!bool) {
      if (i == 0 && this.mWebView.hasDispatchDownEvent)
        return true; 
      this.mWebView.dispatchTouchEvent(paramMotionEvent);
      return true;
    } 
    if (i == 0 && !this.mWebView.hasDispatchDownEvent)
      this.mWebView.dispatchTouchEvent(paramMotionEvent); 
    return true;
  }
  
  public AbsoluteLayout getAbsoluteLayout() {
    return this.mAbsoluteLayout;
  }
  
  public AbsoluteLayout getAvailableLayout() {
    boolean bool = TTWebViewSupportWebView.isRenderInBrowserEnabled();
    StringBuilder stringBuilder = new StringBuilder("ttRenderInBrowserEnabled=");
    stringBuilder.append(bool);
    AppBrandLogger.i("tma_NativeNestWebView", new Object[] { stringBuilder.toString() });
    return bool ? this.mBackAbsoluteLayout : this.mAbsoluteLayout;
  }
  
  public AbsoluteLayout getBackAbsoluteLayout() {
    return this.mBackAbsoluteLayout;
  }
  
  public LinearLayout getConfirmHolder() {
    return this.mConfirmHolder;
  }
  
  public int getKeyboardHeight() {
    return this.mKeyboardHeight;
  }
  
  public NativeViewManager getNativeViewManager() {
    return this.mNativeViewManager;
  }
  
  public WebViewScroller getScroller() {
    return this.mScroller;
  }
  
  public boolean getShowConfirmBar() {
    return this.mShowConfirmBar;
  }
  
  public NestWebView getWebView() {
    return this.mWebView;
  }
  
  public Button getmConfirmTV() {
    return this.mConfirmTV;
  }
  
  public void hideConfimBar() {
    this.mConfirmHolder.setVisibility(8);
  }
  
  public void hideConfirmBar() {
    if (getConfirmHolder() != null && TextUtils.equals("textarea", this.mNativeViewManager.getViewType()) && getShowConfirmBar())
      getConfirmHolder().setVisibility(8); 
  }
  
  protected void initView() {
    this.mWebView.setFocusableInTouchMode(false);
    this.mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
    this.mWebView.setWebChromeClient(new WebChromeClient() {
          public void onHideCustomView() {
            super.onHideCustomView();
            if (NativeNestWebView.this.mRender != null) {
              if (NativeNestWebView.this.mRender.getCurrentActivity() == null)
                return; 
              VideoFullScreenHelper videoFullScreenHelper = NativeNestWebView.this.mWebView.getVideoFullScreenHelper();
              if (videoFullScreenHelper == null)
                return; 
              videoFullScreenHelper.exitFullScreen(NativeNestWebView.this.mRender.getCurrentActivity(), NativeNestWebView.this.mRender.getWebViewId());
            } 
          }
          
          public void onShowCustomView(View param1View, WebChromeClient.CustomViewCallback param1CustomViewCallback) {
            super.onShowCustomView(param1View, param1CustomViewCallback);
            if (NativeNestWebView.this.mRender != null) {
              if (NativeNestWebView.this.mRender.getCurrentActivity() == null)
                return; 
              VideoFullScreenHelper videoFullScreenHelper2 = NativeNestWebView.this.mWebView.getVideoFullScreenHelper();
              VideoFullScreenHelper videoFullScreenHelper1 = videoFullScreenHelper2;
              if (videoFullScreenHelper2 == null) {
                videoFullScreenHelper1 = new VideoFullScreenHelper();
                NativeNestWebView.this.mWebView.setVideoFullScreenHelper(videoFullScreenHelper1);
              } 
              videoFullScreenHelper1.enterFullScreen(NativeNestWebView.this.mRender.getCurrentActivity(), param1View, param1CustomViewCallback, NativeNestWebView.this.mRender.getWebViewId());
            } 
          }
        });
    this.mWebView.setScrollListener(new TTWebViewSupportWebView.OnScrollListener() {
          public void onBackNativeScrollChanged(int param1Int1, int param1Int2, int param1Int3) {
            NativeNestWebView nativeNestWebView = NativeNestWebView.this;
            nativeNestWebView.mCurrentScrollerX = param1Int2;
            nativeNestWebView.mCurrentScrollerY = param1Int3;
            nativeNestWebView.mBackAbsoluteLayout.onBackNativeScrollChanged(param1Int1, param1Int2, param1Int3);
            if (NativeNestWebView.this.mScrollListener != null)
              NativeNestWebView.this.mScrollListener.onBackNativeScrollChanged(param1Int1, param1Int2, param1Int3); 
          }
          
          public void onNativeScrollChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
            NativeNestWebView nativeNestWebView = NativeNestWebView.this;
            nativeNestWebView.mCurrentScrollerX = param1Int1;
            nativeNestWebView.mCurrentScrollerY = param1Int2;
            nativeNestWebView.mAbsoluteLayout.onNativeScrollChanged(param1Int1, param1Int2, param1Int3, param1Int4);
            NativeNestWebView.this.mBackAbsoluteLayout.onWebViewScrollChanged(param1Int1, param1Int2, param1Int3, param1Int4);
            if (NativeNestWebView.this.mScrollListener != null)
              NativeNestWebView.this.mScrollListener.onNativeScrollChanged(param1Int1, param1Int2, param1Int3, param1Int4); 
          }
        });
    if (this.mNativeContainer == null) {
      this.mNativeContainer = new NoScrollView(getContext());
      this.mNativeContainer.setVerticalScrollBarEnabled(false);
      FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(-1, -1);
      addView((View)this.mNativeContainer, (ViewGroup.LayoutParams)layoutParams1);
      this.mAbsoluteLayout = new AbsoluteLayout(getContext(), this.mWebView, 0);
      ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
      this.mNativeContainer.addView((View)this.mAbsoluteLayout, layoutParams);
    } 
    if (this.mBackNativeContainer == null) {
      this.mBackNativeContainer = new NoScrollView(getContext());
      this.mBackNativeContainer.setVerticalScrollBarEnabled(false);
      FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(-1, -1);
      addView((View)this.mBackNativeContainer, 0, (ViewGroup.LayoutParams)layoutParams1);
      this.mBackAbsoluteLayout = new AbsoluteLayout(getContext(), this.mWebView, 1);
      ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
      this.mBackNativeContainer.addView((View)this.mBackAbsoluteLayout, layoutParams);
    } 
    if (this.mScroller == null) {
      this.mScroller = WebViewScrollerFactory.createScrollerSimple(getContext());
      this.mScroller.setOnOffsetChangedListener(this);
    } 
  }
  
  public boolean onBackPressed() {
    VideoFullScreenHelper videoFullScreenHelper = this.mWebView.getVideoFullScreenHelper();
    return (videoFullScreenHelper != null && videoFullScreenHelper.exitFullScreenManual()) ? true : this.mNativeViewManager.onBackPressed();
  }
  
  public void onDestroy() {
    this.mWebviewStuckMonitor.stop();
    ToolUtils.clearWebView(this.mWebView);
    this.mNativeViewManager.onDestroy();
  }
  
  public void onKeyboardHide() {
    InputBean inputBean = this.mLastFoucInput;
    if (inputBean != null) {
      Iterator<WeakReference<IKeyBoardStateChange>> iterator1;
      int j = inputBean.inputId;
      InputComponent inputComponent2 = (InputComponent)this.mNativeViewManager.getView(j);
      int i = j;
      InputComponent inputComponent1 = inputComponent2;
      if (inputComponent2 == null) {
        View view = this.mNativeViewManager.getFocusedInput();
        i = j;
        inputComponent1 = inputComponent2;
        if (view instanceof InputComponent) {
          inputComponent1 = (InputComponent)view;
          i = view.getId();
        } 
      } 
      if (inputComponent1 == null) {
        this.mLastFoucInput = null;
        onKeyboardHide2();
        iterator1 = this.keyboardStateList.iterator();
        while (iterator1.hasNext()) {
          IKeyBoardStateChange iKeyBoardStateChange = ((WeakReference<IKeyBoardStateChange>)iterator1.next()).get();
          if (iKeyBoardStateChange != null)
            iKeyBoardStateChange.onKeyboardHide(); 
        } 
        return;
      } 
      if (iterator1 instanceof com.tt.miniapp.component.nativeview.Input) {
        this.mNativeViewManager.removeView(i, null);
      } else if (!TextUtils.equals(iterator1.getType(), "textarea")) {
        this.mLastFoucInput.cursor = iterator1.getCursor();
        this.mLastFoucInput.value = iterator1.getValue();
        JSONObject jSONObject = (new JsonBuilder()).put("value", this.mLastFoucInput.value).put("inputId", Integer.valueOf(this.mLastFoucInput.inputId)).put("cursor", Integer.valueOf(this.mLastFoucInput.cursor)).build();
        this.mApp.getWebViewManager().publish(getWebViewId(), "onKeyboardComplete", jSONObject.toString());
      } 
    } 
    this.mLastFoucInput = null;
    onKeyboardHide2();
    Iterator<WeakReference<IKeyBoardStateChange>> iterator = this.keyboardStateList.iterator();
    while (iterator.hasNext()) {
      IKeyBoardStateChange iKeyBoardStateChange = ((WeakReference<IKeyBoardStateChange>)iterator.next()).get();
      if (iKeyBoardStateChange != null)
        iKeyBoardStateChange.onKeyboardHide(); 
    } 
    this.mApp.getWebViewManager().publish(getWebViewId(), "hideKeyboard", null);
  }
  
  public void onKeyboardShow(int paramInt) {
    InputBean inputBean = this.mNativeViewManager.getCurrentInputValue();
    int i = inputBean.inputId;
    if (i != -1) {
      if (DebugUtil.debug()) {
        AppBrandLogger.d("tma_NativeNestWebView", new Object[] { "currentFocusId ", Integer.valueOf(i) });
        try {
          JSONObject jSONObject = new JSONObject();
          jSONObject.put("inputId", i);
          this.mApp.getWebViewManager().publish(getWebViewId(), "syncNativeScroll", jSONObject.toString());
        } catch (JSONException jSONException) {
          AppBrandLogger.stacktrace(6, "tma_NativeNestWebView", jSONException.getStackTrace());
        } 
      } 
      this.mLastFoucInput = inputBean;
      onKeyboardShow2(paramInt, this.mLastFoucInput.inputId);
      Iterator<WeakReference<IKeyBoardStateChange>> iterator = this.keyboardStateList.iterator();
      while (iterator.hasNext()) {
        IKeyBoardStateChange iKeyBoardStateChange = ((WeakReference<IKeyBoardStateChange>)iterator.next()).get();
        if (iKeyBoardStateChange != null)
          iKeyBoardStateChange.onKeyboardShow(paramInt, this.mLastFoucInput.inputId); 
      } 
    } 
  }
  
  public void onLanguageChange() {
    this.mConfirmTV.setText(getContext().getString(2097741885));
  }
  
  public void onUpdateBottom() {
    this.mOriginWebviewBottom = this.mOffsetTargetView.getBottom();
  }
  
  public void onViewPause() {
    this.mWebView.onPause();
    this.mNativeViewManager.onViewPause();
  }
  
  public void onViewResume() {
    this.mWebView.onResume();
    this.mNativeViewManager.onViewResume();
  }
  
  public void registerKeyBoardStateChange(IKeyBoardStateChange paramIKeyBoardStateChange) {
    if (paramIKeyBoardStateChange != null)
      this.keyboardStateList.add(new WeakReference<IKeyBoardStateChange>(paramIKeyBoardStateChange)); 
  }
  
  public void setConfirmBar(int paramInt) {
    AbsoluteLayout.LayoutParams layoutParams1 = new AbsoluteLayout.LayoutParams(-1, -2, 0, paramInt);
    this.mAbsoluteLayout.addView((View)this.mConfirmHolder, layoutParams1);
    layoutParams1.height = this.CONFIRM_BAR_HEIGHT;
    this.mConfirmTV.setText(getContext().getString(2097741885));
    this.mConfirmTV.setBackground(null);
    this.mConfirmTV.bringToFront();
    this.mConfirmTV.setGravity(16);
    this.mConfirmTV.setTextColor(Color.rgb(76, 70, 68));
    this.mConfirmTV.setTextSize(20.0F);
    this.mConfirmHolder.setVisibility(4);
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -1);
    this.mConfirmTV.setPadding(10, 10, 20, 20);
    layoutParams.gravity = 112;
    this.mConfirmHolder.addView((View)this.mConfirmTV, (ViewGroup.LayoutParams)layoutParams);
    Drawable drawable = getResources().getDrawable(2097479703);
    this.mConfirmHolder.setBackground(drawable);
  }
  
  public void setDisableScroll(boolean paramBoolean) {
    this.mWebView.setDisableScroll(paramBoolean);
  }
  
  public void setScrollListener(TTWebViewSupportWebView.OnScrollListener paramOnScrollListener) {
    this.mScrollListener = paramOnScrollListener;
  }
  
  public void setShowConfirmBar(Boolean paramBoolean) {
    this.mShowConfirmBar = paramBoolean.booleanValue();
  }
  
  public void showConfimBar() {
    this.mConfirmHolder.setVisibility(0);
  }
  
  public void showConfirmBar() {
    if (getConfirmHolder() != null && TextUtils.equals("textarea", this.mNativeViewManager.getViewType()) && getShowConfirmBar())
      getConfirmHolder().setVisibility(0); 
  }
  
  public void smoothOffsetTopAndBottom(int paramInt, View paramView) {
    StringBuilder stringBuilder2 = new StringBuilder("smoothOffsetTopAndBottom:");
    stringBuilder2.append(paramInt);
    AppBrandLogger.e("tma_NativeNestWebView", new Object[] { stringBuilder2.toString() });
    if (paramView instanceof InputComponent && !((InputComponent)paramView).isAdjustPosition()) {
      AppBrandLogger.d("tma_NativeNestWebView", new Object[] { "smoothOffsetTopAndBottom: don't adjust position" });
      return;
    } 
    offsetTargetView(paramInt);
    StringBuilder stringBuilder1 = new StringBuilder("this.getTop():");
    stringBuilder1.append(this.mOffsetTargetView.getTop());
    AppBrandLogger.e("Textarea", new Object[] { stringBuilder1.toString() });
    updateOffset(true);
  }
  
  public void smoothOffsetTopAndBottom(int paramInt, boolean paramBoolean, View paramView) {
    if (paramBoolean) {
      this.mScroller.updateOffset(paramInt);
      smoothOffsetTopAndBottom(paramInt, paramView);
    } 
  }
  
  public void unregisterKeyBoardStateChange(IKeyBoardStateChange paramIKeyBoardStateChange) {
    if (paramIKeyBoardStateChange != null) {
      Iterator<WeakReference<IKeyBoardStateChange>> iterator = this.keyboardStateList.iterator();
      while (iterator.hasNext()) {
        WeakReference weakReference = iterator.next();
        if (weakReference != null && weakReference.get() != null && paramIKeyBoardStateChange.equals(weakReference.get()))
          iterator.remove(); 
      } 
    } 
  }
  
  public int updateKeyboardHeight(int paramInt) {
    Point point = new Point();
    AppbrandContext.getInst().getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(point);
    return point.y - paramInt;
  }
  
  public int updateScreenHeight(int paramInt) {
    Point point = new Point();
    AppbrandContext.getInst().getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(point);
    int i = point.y;
    this.mLastScreenHeight = point.y;
    return i - paramInt;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\NativeNestWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */