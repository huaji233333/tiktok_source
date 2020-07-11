package com.tt.miniapp.webbridge;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.tt.frontendapiinterface.i;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.view.webcore.NestWebView;
import com.tt.miniapp.webbridge.aysnc.ApiOpenCustomerServiceCtrl;
import com.tt.miniapp.webbridge.sync.GetRealFilePathWebViewHandler;
import com.tt.miniapp.webbridge.sync.HideKeyBoardHandler;
import com.tt.miniapp.webbridge.sync.InsertADHTMLWebViewHandler;
import com.tt.miniapp.webbridge.sync.InsertHTMLWebViewHandler;
import com.tt.miniapp.webbridge.sync.LaunchAppHandler;
import com.tt.miniapp.webbridge.sync.PostErrorsHandler;
import com.tt.miniapp.webbridge.sync.RemoveADHTMLWebViewHandler;
import com.tt.miniapp.webbridge.sync.RemoveHTMLWebViewHandler;
import com.tt.miniapp.webbridge.sync.RemoveTextAreaHandler;
import com.tt.miniapp.webbridge.sync.ReportTimeLineHandler;
import com.tt.miniapp.webbridge.sync.SaveLogEventHandler;
import com.tt.miniapp.webbridge.sync.ShowDatePickerViewHandler;
import com.tt.miniapp.webbridge.sync.ShowKeyboardHandler;
import com.tt.miniapp.webbridge.sync.ShowMultiPickerViewHandler;
import com.tt.miniapp.webbridge.sync.ShowPickerViewHandler;
import com.tt.miniapp.webbridge.sync.ShowRegionPickerViewHandler;
import com.tt.miniapp.webbridge.sync.ShowTextAreaKeyboardHandler;
import com.tt.miniapp.webbridge.sync.ShowToastHandler;
import com.tt.miniapp.webbridge.sync.SnapShotRenderReadyHandler;
import com.tt.miniapp.webbridge.sync.SystemLogHandler;
import com.tt.miniapp.webbridge.sync.UpdateADHTMLWebViewHandler;
import com.tt.miniapp.webbridge.sync.UpdateHTMLWebViewHandler;
import com.tt.miniapp.webbridge.sync.UpdateInputHandler;
import com.tt.miniapp.webbridge.sync.UpdateMultiPickerViewHandler;
import com.tt.miniapp.webbridge.sync.UpdateTextAreaHandler;
import com.tt.miniapp.webbridge.sync.WebDisableScrollBounceHandler;
import com.tt.miniapp.webbridge.sync.WebInsertTextAreaHandler;
import com.tt.miniapp.webbridge.sync.ad.InsertAdContainerHandler;
import com.tt.miniapp.webbridge.sync.ad.RemoveAdContainerHandler;
import com.tt.miniapp.webbridge.sync.ad.UpdateAdContainerHandler;
import com.tt.miniapp.webbridge.sync.liveplayer.InsertLivePlayerHandler;
import com.tt.miniapp.webbridge.sync.liveplayer.OperateLivePlayerContextHandler;
import com.tt.miniapp.webbridge.sync.liveplayer.RemoveLivePlayerHandler;
import com.tt.miniapp.webbridge.sync.liveplayer.UpdateLivePlayerHandler;
import com.tt.miniapp.webbridge.sync.map.GetCenterLocationHandler;
import com.tt.miniapp.webbridge.sync.map.GetRegionHandler;
import com.tt.miniapp.webbridge.sync.map.GetRotateHandler;
import com.tt.miniapp.webbridge.sync.map.GetScaleHandler;
import com.tt.miniapp.webbridge.sync.map.GetSkewHandler;
import com.tt.miniapp.webbridge.sync.map.InsertMapContextHandler;
import com.tt.miniapp.webbridge.sync.map.MoveToLocationHandler;
import com.tt.miniapp.webbridge.sync.map.RemoveMapContextHandler;
import com.tt.miniapp.webbridge.sync.map.UpdateMapContextHandler;
import com.tt.miniapp.webbridge.sync.video.H5RequestFullScreenHandler;
import com.tt.miniapp.webbridge.sync.video.InsertVideoPlayerHandler;
import com.tt.miniapp.webbridge.sync.video.OperateVideoContextHandler;
import com.tt.miniapp.webbridge.sync.video.RemoveVideoPlayerHandler;
import com.tt.miniapp.webbridge.sync.video.UpdateVideoPlayerHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.e;
import com.tt.option.e.i;
import com.tt.option.e.j;
import java.util.Arrays;
import java.util.List;

public class WebBridge {
  private AppbrandApplicationImpl mApp;
  
  private List<String> mInterceptInvokeList = Arrays.asList(new String[] { "openCustomerService" });
  
  private WebViewManager.IRender mRender;
  
  public WebBridge(AppbrandApplicationImpl paramAppbrandApplicationImpl, WebViewManager.IRender paramIRender) {
    this.mApp = paramAppbrandApplicationImpl;
    this.mRender = paramIRender;
  }
  
  private void callbackWebView(int paramInt, String paramString) {
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager != null)
      webViewManager.invokeHandler(this.mRender.getWebViewId(), paramInt, paramString); 
  }
  
  public void destroy() {}
  
  protected InterceptedInvokeResult handleInterceptedInvoke(String paramString1, String paramString2, int paramInt) {
    AppBrandLogger.d("WebBridge", new Object[] { "handleInterceptedInvoke event ", paramString1, " param ", paramString2, " callbackId ", Integer.valueOf(paramInt) });
    if (TextUtils.equals(paramString1, "openCustomerService")) {
      (new ApiOpenCustomerServiceCtrl(paramString2, paramInt, new e() {
            public void callback(int param1Int, String param1String) {
              WebBridge.this.callbackWebView(param1Int, param1String);
            }
          })).doAct();
      return new InterceptedInvokeResult(CharacterUtils.empty());
    } 
    return null;
  }
  
  protected boolean interceptInvoke(String paramString) {
    return this.mInterceptInvokeList.contains(paramString);
  }
  
  @JavascriptInterface
  public String invoke(String paramString1, String paramString2, int paramInt) {
    OperateLivePlayerContextHandler operateLivePlayerContextHandler;
    j j;
    if (interceptInvoke(paramString1)) {
      InterceptedInvokeResult interceptedInvokeResult = handleInterceptedInvoke(paramString1, paramString2, paramInt);
      if (interceptedInvokeResult != null)
        return interceptedInvokeResult.mResult; 
    } 
    AppBrandLogger.d("WebBridge", new Object[] { "invoke event ", paramString1, " param ", paramString2, " callbackId ", Integer.valueOf(paramInt) });
    WebInsertTextAreaHandler webInsertTextAreaHandler = null;
    if (TextUtils.equals(paramString1, "insertTextArea")) {
      webInsertTextAreaHandler = new WebInsertTextAreaHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "removeTextArea")) {
      RemoveTextAreaHandler removeTextAreaHandler = new RemoveTextAreaHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "insertVideoPlayer")) {
      InsertVideoPlayerHandler insertVideoPlayerHandler = new InsertVideoPlayerHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "hideKeyboard")) {
      HideKeyBoardHandler hideKeyBoardHandler = new HideKeyBoardHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "updateTextArea")) {
      UpdateTextAreaHandler updateTextAreaHandler = new UpdateTextAreaHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "updateVideoPlayer")) {
      UpdateVideoPlayerHandler updateVideoPlayerHandler = new UpdateVideoPlayerHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "removeVideoPlayer")) {
      RemoveVideoPlayerHandler removeVideoPlayerHandler = new RemoveVideoPlayerHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "showKeyboard")) {
      ShowKeyboardHandler showKeyboardHandler = new ShowKeyboardHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "showTextAreaKeyboard")) {
      ShowTextAreaKeyboardHandler showTextAreaKeyboardHandler = new ShowTextAreaKeyboardHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "updateInput")) {
      UpdateInputHandler updateInputHandler = new UpdateInputHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "showPickerView")) {
      ShowPickerViewHandler showPickerViewHandler = new ShowPickerViewHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "showDatePickerView")) {
      ShowDatePickerViewHandler showDatePickerViewHandler = new ShowDatePickerViewHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "showMultiPickerView")) {
      ShowMultiPickerViewHandler showMultiPickerViewHandler = new ShowMultiPickerViewHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "updateMultiPickerView")) {
      UpdateMultiPickerViewHandler updateMultiPickerViewHandler = new UpdateMultiPickerViewHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "showRegionPickerView")) {
      ShowRegionPickerViewHandler showRegionPickerViewHandler = new ShowRegionPickerViewHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "insertHTMLWebView")) {
      InsertHTMLWebViewHandler insertHTMLWebViewHandler = new InsertHTMLWebViewHandler(this.mRender, paramString2, paramInt);
      this.mRender.setLoadAsWebView();
      AppbrandApplicationImpl.getInst().setCurrentPageType("webview");
    } else if (TextUtils.equals(paramString1, "updateHTMLWebView")) {
      UpdateHTMLWebViewHandler updateHTMLWebViewHandler = new UpdateHTMLWebViewHandler(this.mRender, paramString2, paramInt);
      this.mRender.setLoadAsWebView();
      AppbrandApplicationImpl.getInst().setCurrentPageType("webview");
    } else if (TextUtils.equals(paramString1, "removeHTMLWebView")) {
      RemoveHTMLWebViewHandler removeHTMLWebViewHandler = new RemoveHTMLWebViewHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "protocolPathToAbsPath")) {
      GetRealFilePathWebViewHandler getRealFilePathWebViewHandler = new GetRealFilePathWebViewHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "disableScrollBounce")) {
      WebDisableScrollBounceHandler webDisableScrollBounceHandler = new WebDisableScrollBounceHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "systemLog")) {
      SystemLogHandler systemLogHandler = new SystemLogHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "insertAdContainer")) {
      InsertAdContainerHandler insertAdContainerHandler = new InsertAdContainerHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "updateAdContainer")) {
      UpdateAdContainerHandler updateAdContainerHandler = new UpdateAdContainerHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "removeAdContainer")) {
      RemoveAdContainerHandler removeAdContainerHandler = new RemoveAdContainerHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "operateVideoContext")) {
      OperateVideoContextHandler operateVideoContextHandler = new OperateVideoContextHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "reportTimeline")) {
      ReportTimeLineHandler reportTimeLineHandler = new ReportTimeLineHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "videoRequestFullScreen")) {
      H5RequestFullScreenHandler h5RequestFullScreenHandler = new H5RequestFullScreenHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "postErrors")) {
      PostErrorsHandler postErrorsHandler = new PostErrorsHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "launchApp")) {
      LaunchAppHandler launchAppHandler = new LaunchAppHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "snapshotReady")) {
      SnapShotRenderReadyHandler snapShotRenderReadyHandler = new SnapShotRenderReadyHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "insertAdHTMLWebView")) {
      InsertADHTMLWebViewHandler insertADHTMLWebViewHandler = new InsertADHTMLWebViewHandler(this.mRender, paramString2, paramInt);
      this.mRender.setLoadAsWebView();
      AppbrandApplicationImpl.getInst().setCurrentPageType("webview");
    } else if (TextUtils.equals(paramString1, "updateAdHTMLWebView")) {
      UpdateADHTMLWebViewHandler updateADHTMLWebViewHandler = new UpdateADHTMLWebViewHandler(this.mRender, paramString2, paramInt);
      this.mRender.setLoadAsWebView();
      AppbrandApplicationImpl.getInst().setCurrentPageType("webview");
    } else if (TextUtils.equals(paramString1, "removeAdHTMLWebView")) {
      RemoveADHTMLWebViewHandler removeADHTMLWebViewHandler = new RemoveADHTMLWebViewHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "showToast")) {
      ShowToastHandler showToastHandler = new ShowToastHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "saveLog")) {
      SaveLogEventHandler saveLogEventHandler = new SaveLogEventHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "insertMapContext")) {
      InsertMapContextHandler insertMapContextHandler = new InsertMapContextHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "updateMapContext")) {
      UpdateMapContextHandler updateMapContextHandler = new UpdateMapContextHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "removeMapContext")) {
      RemoveMapContextHandler removeMapContextHandler = new RemoveMapContextHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "getCenterLocation")) {
      GetCenterLocationHandler getCenterLocationHandler = new GetCenterLocationHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "moveToLocation")) {
      MoveToLocationHandler moveToLocationHandler = new MoveToLocationHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "getRegion")) {
      GetRegionHandler getRegionHandler = new GetRegionHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "getRotate")) {
      GetRotateHandler getRotateHandler = new GetRotateHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "getScale")) {
      GetScaleHandler getScaleHandler = new GetScaleHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "getSkew")) {
      GetSkewHandler getSkewHandler = new GetSkewHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "insertLivePlayer")) {
      InsertLivePlayerHandler insertLivePlayerHandler = new InsertLivePlayerHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "updateLivePlayer")) {
      UpdateLivePlayerHandler updateLivePlayerHandler = new UpdateLivePlayerHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "removeLivePlayer")) {
      RemoveLivePlayerHandler removeLivePlayerHandler = new RemoveLivePlayerHandler(this.mRender, paramString2, paramInt);
    } else if (TextUtils.equals(paramString1, "operateLivePlayerContext")) {
      operateLivePlayerContextHandler = new OperateLivePlayerContextHandler(this.mRender, paramString2, paramInt);
    } 
    if (operateLivePlayerContextHandler == null || operateLivePlayerContextHandler.canOverride()) {
      i.a a = AppbrandContext.getInst().getNativeViewCreator();
      if (a != null) {
        j j1 = a.a(paramString1, (i)this.mRender, paramString2, paramInt);
        if (j1 != null)
          j = j1; 
      } 
    } 
    if (j != null) {
      paramString1 = j.act();
      AppBrandLogger.d("WebBridge", new Object[] { "invoke return ", paramString1 });
      return paramString1;
    } 
    return CharacterUtils.empty();
  }
  
  @JavascriptInterface
  public void onDocumentReady(String paramString) {
    AppBrandLogger.d("WebBridge", new Object[] { "WebBridge_onDocumentReady", paramString });
    if (paramString.equals("page-frame.html")) {
      NestWebView nestWebView = (NestWebView)this.mRender.getWebView();
      if (nestWebView != null) {
        TimeLogger timeLogger = TimeLogger.getInstance();
        StringBuilder stringBuilder = new StringBuilder("TTWVStatusCode:");
        stringBuilder.append(nestWebView.getLoadingStatusCode());
        timeLogger.logTimeDuration(new String[] { "WebBridge_onDocumentReady_pageframe.html", stringBuilder.toString() });
      } 
      return;
    } 
    if (this.mRender.getNativeNestWebView() != null) {
      this.mRender.getNativeNestWebView().onWebviewReady();
      return;
    } 
    TimeLogger.getInstance().logTimeDuration(new String[] { "WebBridge_onDocumentReady_WebViewIsNull" });
  }
  
  @JavascriptInterface
  public String publish(String paramString1, String paramString2, int[] paramArrayOfint) {
    AppBrandLogger.d("WebBridge", new Object[] { " publish event ", paramString1, " param ", paramString2, " webviewIds ", paramArrayOfint });
    this.mApp.getJsBridge().sendMsgToJsCore(paramString1, paramString2, this.mRender.getWebViewId());
    return null;
  }
  
  public static class InterceptedInvokeResult {
    public final String mResult;
    
    public InterceptedInvokeResult(String param1String) {
      this.mResult = param1String;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\WebBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */