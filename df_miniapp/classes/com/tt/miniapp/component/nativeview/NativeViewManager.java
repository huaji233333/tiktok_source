package com.tt.miniapp.component.nativeview;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import com.tt.frontendapiinterface.i;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.liveplayer.LivePlayer;
import com.tt.miniapp.component.nativeview.map.Map;
import com.tt.miniapp.component.nativeview.video.VideoView;
import com.tt.miniapp.keyboarddetect.KeyboardHeightProvider;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.k;

public class NativeViewManager {
  private NativeNestWebView mNativeWebView;
  
  private WebViewManager.IRender mRender;
  
  private String mViewType;
  
  private SparseArray<View> mViews = new SparseArray();
  
  private int mWebViewId;
  
  public NativeViewManager(WebViewManager.IRender paramIRender, NativeNestWebView paramNativeNestWebView, int paramInt) {
    this.mNativeWebView = paramNativeNestWebView;
    this.mWebViewId = paramInt;
    this.mRender = paramIRender;
  }
  
  private AbsoluteLayout getAttatchedLayout(View paramView) {
    AbsoluteLayout absoluteLayout = this.mNativeWebView.getAvailableLayout();
    null = absoluteLayout;
    if (paramView != null) {
      if (!(paramView instanceof Input) && !(paramView instanceof NativeWebView)) {
        null = absoluteLayout;
        return !HostDependManager.getInst().enableTTRender(paramView) ? this.mNativeWebView.getAbsoluteLayout() : null;
      } 
    } else {
      return null;
    } 
    return this.mNativeWebView.getAbsoluteLayout();
  }
  
  private AbsoluteLayout getUsableLayout(String paramString) {
    AbsoluteLayout absoluteLayout = this.mNativeWebView.getAvailableLayout();
    if (TextUtils.equals(paramString, "webHtml") || TextUtils.equals(paramString, "adWebHtml") || isInputComp(paramString) || !HostDependManager.getInst().enableTTRender(paramString))
      absoluteLayout = this.mNativeWebView.getAbsoluteLayout(); 
    return absoluteLayout;
  }
  
  private boolean isInputComp(String paramString) {
    return (TextUtils.equals(paramString, "text") || TextUtils.equals(paramString, "digit") || TextUtils.equals(paramString, "number") || TextUtils.equals(paramString, "idcard"));
  }
  
  public void addView(int paramInt, String paramString1, String paramString2, k paramk) {
    LivePlayer livePlayer;
    AppBrandLogger.d("tma_NativeViewManager", new Object[] { "viewType ", paramString1 });
    AbsoluteLayout absoluteLayout = getUsableLayout(paramString1);
    NativeComponent nativeComponent2 = HostDependManager.getInst().getNativeComponentView(paramString1, paramInt, this.mWebViewId, absoluteLayout, this.mNativeWebView, (i)this.mRender);
    NativeComponent nativeComponent1 = nativeComponent2;
    if (nativeComponent2 == null)
      if (TextUtils.equals(paramString1, "textarea")) {
        nativeComponent1 = new TextArea(paramInt, absoluteLayout, this.mRender, this.mWebViewId, this.mNativeWebView);
      } else if (isInputComp(paramString1)) {
        nativeComponent1 = Input.getInputView(paramInt, absoluteLayout, this.mRender, this.mWebViewId, paramString1, this.mNativeWebView);
      } else if (TextUtils.equals(paramString1, "webHtml")) {
        nativeComponent1 = new NativeWebView(absoluteLayout.getContext(), paramInt);
        nativeComponent1.bind(absoluteLayout, this.mRender);
      } else if (TextUtils.equals(paramString1, "adWebHtml")) {
        nativeComponent1 = new NativeAdWebView(absoluteLayout.getContext(), paramInt);
        nativeComponent1.bind(absoluteLayout, this.mRender);
      } else {
        VideoView.VideoModel videoModel;
        if (TextUtils.equals(paramString1, "video")) {
          videoModel = VideoView.VideoModel.parseVideoMode(paramString2);
          videoModel.videoPlayerId = paramInt;
          VideoView videoView = new VideoView(absoluteLayout, this.mRender, videoModel);
        } else if (TextUtils.equals((CharSequence)videoModel, "ad")) {
          nativeComponent1 = new AdContainerView(paramInt, absoluteLayout, this.mRender);
        } else if (TextUtils.equals((CharSequence)videoModel, "map")) {
          Map map = new Map(paramInt, absoluteLayout, this.mRender, this.mNativeWebView);
        } else {
          nativeComponent1 = nativeComponent2;
          if (TextUtils.equals((CharSequence)videoModel, "livePlayer"))
            livePlayer = new LivePlayer(absoluteLayout.getContext(), paramInt, absoluteLayout, this.mRender); 
        } 
      }  
    if (livePlayer instanceof View) {
      View view = (View)livePlayer;
      view.setId(paramInt);
      livePlayer.addView(paramString2, paramk);
      this.mViews.put(paramInt, view);
      absoluteLayout.addAndRegisterPlatformView(view);
    } 
  }
  
  void destroyOtherVideo() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mViews : Landroid/util/SparseArray;
    //   4: invokevirtual size : ()I
    //   7: istore_2
    //   8: iconst_0
    //   9: istore_1
    //   10: iload_1
    //   11: iload_2
    //   12: if_icmpge -> 42
    //   15: aload_0
    //   16: getfield mViews : Landroid/util/SparseArray;
    //   19: iload_1
    //   20: invokevirtual get : (I)Ljava/lang/Object;
    //   23: checkcast android/view/View
    //   26: instanceof com/tt/miniapp/component/nativeview/video/VideoView
    //   29: ifeq -> 35
    //   32: goto -> 44
    //   35: iload_1
    //   36: iconst_1
    //   37: iadd
    //   38: istore_1
    //   39: goto -> 10
    //   42: iconst_m1
    //   43: istore_1
    //   44: iload_1
    //   45: iflt -> 70
    //   48: aload_0
    //   49: getfield mViews : Landroid/util/SparseArray;
    //   52: iload_1
    //   53: invokevirtual get : (I)Ljava/lang/Object;
    //   56: checkcast com/tt/miniapp/component/nativeview/video/VideoView
    //   59: invokevirtual releaseMedia : ()V
    //   62: aload_0
    //   63: getfield mViews : Landroid/util/SparseArray;
    //   66: iload_1
    //   67: invokevirtual remove : (I)V
    //   70: return
  }
  
  public void destroyVideoView(VideoView paramVideoView) {
    AppBrandLogger.d("tma_NativeViewManager", new Object[] { "destroyVideoView" });
    if (paramVideoView != null) {
      paramVideoView.releaseMedia();
      int i = this.mViews.indexOfValue(paramVideoView);
      if (i >= 0)
        this.mViews.delete(i); 
    } 
  }
  
  public SparseArray<View> getAllNativeViews() {
    return this.mViews;
  }
  
  public InputBean getCurrentInputValue() {
    InputBean inputBean = new InputBean();
    inputBean.inputId = -1;
    int j = this.mViews.size();
    for (int i = 0; i < j; i++) {
      int k = this.mViews.keyAt(i);
      View view = (View)this.mViews.get(k);
      if (view instanceof InputComponent) {
        InputComponent inputComponent = (InputComponent)view;
        String str = inputComponent.getType();
        setViewType(str);
        boolean bool = inputComponent.getConfirm();
        this.mNativeWebView.setShowConfirmBar(Boolean.valueOf(bool));
        if (inputComponent.hasFocus()) {
          inputBean.inputId = k;
          inputBean.value = inputComponent.getValue();
          inputBean.cursor = inputComponent.getCursor();
          inputBean.height = KeyboardHeightProvider.getKeyboardHeight();
          inputBean.type = str;
          return inputBean;
        } 
      } 
    } 
    return inputBean;
  }
  
  public NativeWebView getCurrentNativeWebView() {
    int j = this.mViews.size();
    for (int i = 0; i < j; i++) {
      View view = (View)this.mViews.valueAt(i);
      if (view instanceof NativeWebView)
        return (NativeWebView)view; 
    } 
    return null;
  }
  
  public View getFocusedInput() {
    SparseArray<View> sparseArray = this.mViews;
    if (sparseArray == null)
      return null; 
    int j = sparseArray.size();
    for (int i = 0; i < j; i++) {
      int k = this.mViews.keyAt(i);
      View view = (View)this.mViews.get(k);
      if (view instanceof InputComponent && view.isFocused())
        return view; 
    } 
    return null;
  }
  
  public NativeNestWebView getNativeWebView() {
    return this.mNativeWebView;
  }
  
  public View getView(int paramInt) {
    return (View)this.mViews.get(paramInt);
  }
  
  public String getViewType() {
    return this.mViewType;
  }
  
  public boolean hasView(int paramInt) {
    return (getView(paramInt) != null);
  }
  
  public boolean onBackPressed() {
    int i = this.mViews.size() - 1;
    boolean bool;
    for (bool = false; i >= 0; bool = bool1) {
      View view = (View)this.mViews.valueAt(i);
      boolean bool1 = bool;
      if (view instanceof NativeComponent) {
        bool1 = bool;
        if (((NativeComponent)view).onBackPressed())
          bool1 = true; 
      } 
      i--;
    } 
    return bool;
  }
  
  public void onDestroy() {
    for (int i = this.mViews.size() - 1; i >= 0; i--) {
      int j = this.mViews.keyAt(i);
      View view = (View)this.mViews.valueAt(i);
      removeView(j, null);
      if (view instanceof NativeComponent)
        ((NativeComponent)view).onDestroy(); 
    } 
  }
  
  public void onViewPause() {
    int j = this.mViews.size();
    for (int i = 0; i < j; i++) {
      View view = (View)this.mViews.valueAt(i);
      if (view instanceof NativeComponent)
        ((NativeComponent)view).onViewPause(); 
    } 
  }
  
  public void onViewResume() {
    int j = this.mViews.size();
    for (int i = 0; i < j; i++) {
      View view = (View)this.mViews.valueAt(i);
      if (view instanceof NativeComponent)
        ((NativeComponent)view).onViewResume(); 
    } 
  }
  
  public void removeView(int paramInt, k paramk) {
    AppBrandLogger.d("tma_NativeViewManager", new Object[] { "removeView viewId ", Integer.valueOf(paramInt) });
    View view = (View)this.mViews.get(paramInt);
    if (view != null) {
      if (view instanceof NativeComponent)
        ((NativeComponent)view).removeView(paramInt, paramk); 
      this.mViews.delete(paramInt);
      getAttatchedLayout(view).removeView(view);
    } 
  }
  
  public void setViewType(String paramString) {
    this.mViewType = paramString;
  }
  
  public void showKeyboard(int paramInt) {
    View view = (View)this.mViews.get(paramInt);
    if (view != null && view instanceof android.widget.EditText)
      InputMethodUtil.showSoftKeyboard(view, view.getContext().getApplicationContext()); 
  }
  
  public void updateView(int paramInt, String paramString, k paramk) {
    View view = (View)this.mViews.get(paramInt);
    if (view instanceof NativeComponent)
      ((NativeComponent)view).updateView(paramString, paramk); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\NativeViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */