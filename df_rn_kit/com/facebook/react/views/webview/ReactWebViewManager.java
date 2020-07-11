package com.facebook.react.views.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.bytedance.v.a.a.a.b;
import com.example.a.c;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.ContentSizeChangeEvent;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.views.webview.events.TopLoadingErrorEvent;
import com.facebook.react.views.webview.events.TopLoadingFinishEvent;
import com.facebook.react.views.webview.events.TopLoadingStartEvent;
import com.facebook.react.views.webview.events.TopMessageEvent;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@ReactModule(name = "RCTWebView")
public class ReactWebViewManager extends SimpleViewManager<WebView> {
  protected WebView.PictureListener mPictureListener;
  
  protected WebViewConfig mWebViewConfig = new WebViewConfig() {
      public void configWebView(WebView param1WebView) {}
    };
  
  public ReactWebViewManager() {}
  
  public ReactWebViewManager(WebViewConfig paramWebViewConfig) {}
  
  protected static void dispatchEvent(WebView paramWebView, Event paramEvent) {
    ((UIManagerModule)((ReactContext)paramWebView.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(paramEvent);
  }
  
  protected void addEventEmitters(ThemedReactContext paramThemedReactContext, WebView paramWebView) {
    paramWebView.setWebViewClient(c.a(new ReactWebViewClient()));
  }
  
  protected ReactWebView createReactWebViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactWebView(paramThemedReactContext);
  }
  
  protected WebView createViewInstance(ThemedReactContext paramThemedReactContext) {
    ReactWebView reactWebView = createReactWebViewInstance(paramThemedReactContext);
    reactWebView.setWebChromeClient(new WebChromeClient() {
          public void ReactWebViewManager$2__onGeolocationPermissionsShowPrompt$___twin___(String param1String, GeolocationPermissions.Callback param1Callback) {
            b.a(this, new Object[] { param1String, param1Callback }, 100003, "com.facebook.react.views.webview.ReactWebViewManager$2.onGeolocationPermissionsShowPrompt(java.lang.String,android.webkit.GeolocationPermissions$Callback)");
            param1Callback.invoke(param1String, true, false);
          }
          
          public boolean onConsoleMessage(ConsoleMessage param1ConsoleMessage) {
            return true;
          }
          
          public void onGeolocationPermissionsShowPrompt(String param1String, GeolocationPermissions.Callback param1Callback) {
            _lancet.com_ss_android_ugc_aweme_lancet_WebLancet_onGeolocationPermissionsShowPrompt(this, param1String, param1Callback);
          }
          
          class null {}
        });
    paramThemedReactContext.addLifecycleEventListener(reactWebView);
    this.mWebViewConfig.configWebView(reactWebView);
    reactWebView.getSettings().setBuiltInZoomControls(true);
    reactWebView.getSettings().setDisplayZoomControls(false);
    reactWebView.getSettings().setDomStorageEnabled(true);
    reactWebView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    return reactWebView;
  }
  
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("goBack", Integer.valueOf(1), "goForward", Integer.valueOf(2), "reload", Integer.valueOf(3), "stopLoading", Integer.valueOf(4), "postMessage", Integer.valueOf(5), "injectJavaScript", Integer.valueOf(6));
  }
  
  public String getName() {
    return "RCTWebView";
  }
  
  protected WebView.PictureListener getPictureListener() {
    if (this.mPictureListener == null)
      this.mPictureListener = new WebView.PictureListener() {
          public void onNewPicture(WebView param1WebView, Picture param1Picture) {
            ReactWebViewManager.dispatchEvent(param1WebView, (Event)new ContentSizeChangeEvent(param1WebView.getId(), param1WebView.getWidth(), param1WebView.getContentHeight()));
          }
        }; 
    return this.mPictureListener;
  }
  
  public void onDropViewInstance(WebView paramWebView) {
    super.onDropViewInstance((View)paramWebView);
    ThemedReactContext themedReactContext = (ThemedReactContext)paramWebView.getContext();
    paramWebView = paramWebView;
    themedReactContext.removeLifecycleEventListener((LifecycleEventListener)paramWebView);
    paramWebView.cleanupCallbacksAndDestroy();
  }
  
  public void receiveCommand(WebView paramWebView, int paramInt, ReadableArray paramReadableArray) {
    StringBuilder stringBuilder;
    switch (paramInt) {
      default:
        return;
      case 6:
        stringBuilder = new StringBuilder("javascript:");
        stringBuilder.append(paramReadableArray.getString(0));
        _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(paramWebView, stringBuilder.toString());
        return;
      case 5:
        try {
          JSONObject jSONObject = new JSONObject();
          jSONObject.put("data", paramReadableArray.getString(0));
          StringBuilder stringBuilder1 = new StringBuilder("javascript:(function () {var event;var data = ");
          stringBuilder1.append(jSONObject.toString());
          stringBuilder1.append(";try {event = new MessageEvent('message', data);} catch (e) {event = document.createEvent('MessageEvent');event.initMessageEvent('message', true, true, data.data, data.origin, data.lastEventId, data.source);}document.dispatchEvent(event);})();");
          _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(paramWebView, stringBuilder1.toString());
          return;
        } catch (JSONException jSONException) {
          throw new RuntimeException(jSONException);
        } 
      case 4:
        jSONException.stopLoading();
        return;
      case 3:
        jSONException.reload();
        return;
      case 2:
        jSONException.goForward();
        return;
      case 1:
        break;
    } 
    jSONException.goBack();
  }
  
  @ReactProp(name = "allowUniversalAccessFromFileURLs")
  public void setAllowUniversalAccessFromFileURLs(WebView paramWebView, boolean paramBoolean) {
    paramWebView.getSettings().setAllowUniversalAccessFromFileURLs(paramBoolean);
  }
  
  @ReactProp(name = "domStorageEnabled")
  public void setDomStorageEnabled(WebView paramWebView, boolean paramBoolean) {
    paramWebView.getSettings().setDomStorageEnabled(paramBoolean);
  }
  
  @ReactProp(name = "injectedJavaScript")
  public void setInjectedJavaScript(WebView paramWebView, String paramString) {
    ((ReactWebView)paramWebView).setInjectedJavaScript(paramString);
  }
  
  @ReactProp(name = "javaScriptEnabled")
  public void setJavaScriptEnabled(WebView paramWebView, boolean paramBoolean) {
    paramWebView.getSettings().setJavaScriptEnabled(paramBoolean);
  }
  
  @ReactProp(name = "mediaPlaybackRequiresUserAction")
  public void setMediaPlaybackRequiresUserAction(WebView paramWebView, boolean paramBoolean) {
    paramWebView.getSettings().setMediaPlaybackRequiresUserGesture(paramBoolean);
  }
  
  @ReactProp(name = "messagingEnabled")
  public void setMessagingEnabled(WebView paramWebView, boolean paramBoolean) {
    ((ReactWebView)paramWebView).setMessagingEnabled(paramBoolean);
  }
  
  @ReactProp(name = "mixedContentMode")
  public void setMixedContentMode(WebView paramWebView, String paramString) {
    if (Build.VERSION.SDK_INT >= 21) {
      if (paramString == null || "never".equals(paramString)) {
        paramWebView.getSettings().setMixedContentMode(1);
        return;
      } 
      if ("always".equals(paramString)) {
        paramWebView.getSettings().setMixedContentMode(0);
        return;
      } 
      if ("compatibility".equals(paramString)) {
        paramWebView.getSettings().setMixedContentMode(2);
        return;
      } 
    } 
  }
  
  @ReactProp(name = "onContentSizeChange")
  public void setOnContentSizeChange(WebView paramWebView, boolean paramBoolean) {
    if (paramBoolean) {
      paramWebView.setPictureListener(getPictureListener());
      return;
    } 
    paramWebView.setPictureListener(null);
  }
  
  @ReactProp(name = "saveFormDataDisabled")
  public void setSaveFormDataDisabled(WebView paramWebView, boolean paramBoolean) {
    paramWebView.getSettings().setSaveFormData(paramBoolean ^ true);
  }
  
  @ReactProp(name = "scalesPageToFit")
  public void setScalesPageToFit(WebView paramWebView, boolean paramBoolean) {
    paramWebView.getSettings().setUseWideViewPort(paramBoolean ^ true);
  }
  
  @ReactProp(name = "source")
  public void setSource(WebView paramWebView, ReadableMap paramReadableMap) {
    if (paramReadableMap != null) {
      if (paramReadableMap.hasKey("html")) {
        String str = paramReadableMap.getString("html");
        if (paramReadableMap.hasKey("baseUrl")) {
          paramWebView.loadDataWithBaseURL(paramReadableMap.getString("baseUrl"), str, "text/html", "UTF-8", null);
          return;
        } 
        paramWebView.loadData(str, "text/html", "UTF-8");
        return;
      } 
      if (paramReadableMap.hasKey("uri")) {
        byte[] arrayOfByte;
        String str2 = paramReadableMap.getString("uri");
        String str1 = paramWebView.getUrl();
        if (str1 != null && str1.equals(str2))
          return; 
        if (paramReadableMap.hasKey("method") && paramReadableMap.getString("method").equals("POST")) {
          byte[] arrayOfByte1;
          str1 = null;
          if (paramReadableMap.hasKey("body")) {
            String str = paramReadableMap.getString("body");
            try {
              arrayOfByte1 = str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
              arrayOfByte1 = str.getBytes();
            } 
          } 
          arrayOfByte = arrayOfByte1;
          if (arrayOfByte1 == null)
            arrayOfByte = new byte[0]; 
          paramWebView.postUrl(str2, arrayOfByte);
          return;
        } 
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        if (arrayOfByte.hasKey("headers")) {
          ReadableMap readableMap = arrayOfByte.getMap("headers");
          ReadableMapKeySetIterator readableMapKeySetIterator = readableMap.keySetIterator();
          while (readableMapKeySetIterator.hasNextKey()) {
            String str = readableMapKeySetIterator.nextKey();
            if ("user-agent".equals(str.toLowerCase(Locale.ENGLISH))) {
              if (paramWebView.getSettings() != null)
                paramWebView.getSettings().setUserAgentString(readableMap.getString(str)); 
              continue;
            } 
            hashMap.put(str, readableMap.getString(str));
          } 
        } 
        _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(paramWebView, str2, hashMap);
        return;
      } 
    } 
    _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(paramWebView, "about:blank");
  }
  
  @ReactProp(name = "thirdPartyCookiesEnabled")
  public void setThirdPartyCookiesEnabled(WebView paramWebView, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 21)
      CookieManager.getInstance().setAcceptThirdPartyCookies(paramWebView, paramBoolean); 
  }
  
  @ReactProp(name = "urlPrefixesForDefaultIntent")
  public void setUrlPrefixesForDefaultIntent(WebView paramWebView, ReadableArray paramReadableArray) {
    ReactWebViewClient reactWebViewClient = ((ReactWebView)paramWebView).getReactWebViewClient();
    if (reactWebViewClient != null && paramReadableArray != null)
      reactWebViewClient.setUrlPrefixesForDefaultIntent(paramReadableArray); 
  }
  
  @ReactProp(name = "userAgent")
  public void setUserAgent(WebView paramWebView, String paramString) {
    if (paramString != null)
      paramWebView.getSettings().setUserAgentString(paramString); 
  }
  
  protected static class ReactWebView extends WebView implements LifecycleEventListener {
    protected String injectedJS;
    
    protected ReactWebViewManager.ReactWebViewClient mReactWebViewClient;
    
    protected boolean messagingEnabled;
    
    public ReactWebView(ThemedReactContext param1ThemedReactContext) {
      super((Context)param1ThemedReactContext);
    }
    
    public void callInjectedJavaScript() {
      if (getSettings().getJavaScriptEnabled()) {
        String str = this.injectedJS;
        if (str != null && !TextUtils.isEmpty(str)) {
          StringBuilder stringBuilder = new StringBuilder("javascript:(function() {\n");
          stringBuilder.append(this.injectedJS);
          stringBuilder.append(";\n})();");
          loadUrl(stringBuilder.toString());
        } 
      } 
    }
    
    protected void cleanupCallbacksAndDestroy() {
      setWebViewClient(c.a(null));
      destroy();
    }
    
    protected ReactWebViewBridge createReactWebViewBridge(ReactWebView param1ReactWebView) {
      return new ReactWebViewBridge(param1ReactWebView);
    }
    
    public ReactWebViewManager.ReactWebViewClient getReactWebViewClient() {
      return this.mReactWebViewClient;
    }
    
    public void linkBridge() {
      if (this.messagingEnabled)
        loadUrl("javascript:(window.originalPostMessage = window.postMessage,window.postMessage = function(data) {__REACT_WEB_VIEW_BRIDGE.postMessage(String(data));})"); 
    }
    
    public void onHostDestroy() {
      cleanupCallbacksAndDestroy();
    }
    
    public void onHostPause() {}
    
    public void onHostResume() {}
    
    public void onMessage(String param1String) {
      ReactWebViewManager.dispatchEvent(this, (Event)new TopMessageEvent(getId(), param1String));
    }
    
    public void setInjectedJavaScript(String param1String) {
      this.injectedJS = param1String;
    }
    
    public void setMessagingEnabled(boolean param1Boolean) {
      if (this.messagingEnabled == param1Boolean)
        return; 
      this.messagingEnabled = param1Boolean;
      if (param1Boolean) {
        addJavascriptInterface(createReactWebViewBridge(this), "__REACT_WEB_VIEW_BRIDGE");
        linkBridge();
        return;
      } 
      removeJavascriptInterface("__REACT_WEB_VIEW_BRIDGE");
    }
    
    public void setWebViewClient(WebViewClient param1WebViewClient) {
      super.setWebViewClient(param1WebViewClient);
      this.mReactWebViewClient = (ReactWebViewManager.ReactWebViewClient)param1WebViewClient;
    }
    
    protected class ReactWebViewBridge {
      ReactWebViewManager.ReactWebView mContext;
      
      ReactWebViewBridge(ReactWebViewManager.ReactWebView param2ReactWebView1) {
        this.mContext = param2ReactWebView1;
      }
      
      @JavascriptInterface
      public void postMessage(String param2String) {
        this.mContext.onMessage(param2String);
      }
    }
  }
  
  protected class ReactWebViewBridge {
    ReactWebViewManager.ReactWebView mContext;
    
    ReactWebViewBridge(ReactWebViewManager.ReactWebView param1ReactWebView1) {
      this.mContext = param1ReactWebView1;
    }
    
    @JavascriptInterface
    public void postMessage(String param1String) {
      this.mContext.onMessage(param1String);
    }
  }
  
  protected static class ReactWebViewClient extends WebViewClient {
    protected boolean mLastLoadFailed;
    
    protected ReadableArray mUrlPrefixesForDefaultIntent;
    
    protected WritableMap createWebViewEvent(WebView param1WebView, String param1String) {
      boolean bool;
      WritableMap writableMap = Arguments.createMap();
      writableMap.putDouble("target", param1WebView.getId());
      writableMap.putString("url", param1String);
      if (!this.mLastLoadFailed && param1WebView.getProgress() != 100) {
        bool = true;
      } else {
        bool = false;
      } 
      writableMap.putBoolean("loading", bool);
      writableMap.putString("title", param1WebView.getTitle());
      writableMap.putBoolean("canGoBack", param1WebView.canGoBack());
      writableMap.putBoolean("canGoForward", param1WebView.canGoForward());
      return writableMap;
    }
    
    protected void emitFinishEvent(WebView param1WebView, String param1String) {
      ReactWebViewManager.dispatchEvent(param1WebView, (Event)new TopLoadingFinishEvent(param1WebView.getId(), createWebViewEvent(param1WebView, param1String)));
    }
    
    public void onPageFinished(WebView param1WebView, String param1String) {
      super.onPageFinished(param1WebView, param1String);
      if (!this.mLastLoadFailed) {
        ReactWebViewManager.ReactWebView reactWebView = (ReactWebViewManager.ReactWebView)param1WebView;
        reactWebView.callInjectedJavaScript();
        reactWebView.linkBridge();
        emitFinishEvent(param1WebView, param1String);
      } 
    }
    
    public void onPageStarted(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
      super.onPageStarted(param1WebView, param1String, param1Bitmap);
      this.mLastLoadFailed = false;
      ReactWebViewManager.dispatchEvent(param1WebView, (Event)new TopLoadingStartEvent(param1WebView.getId(), createWebViewEvent(param1WebView, param1String)));
    }
    
    public void onReceivedError(WebView param1WebView, int param1Int, String param1String1, String param1String2) {
      super.onReceivedError(param1WebView, param1Int, param1String1, param1String2);
      this.mLastLoadFailed = true;
      emitFinishEvent(param1WebView, param1String2);
      WritableMap writableMap = createWebViewEvent(param1WebView, param1String2);
      writableMap.putDouble("code", param1Int);
      writableMap.putString("description", param1String1);
      ReactWebViewManager.dispatchEvent(param1WebView, (Event)new TopLoadingErrorEvent(param1WebView.getId(), writableMap));
    }
    
    public boolean onRenderProcessGone(WebView param1WebView, RenderProcessGoneDetail param1RenderProcessGoneDetail) {
      return c.a(param1WebView, param1RenderProcessGoneDetail);
    }
    
    public void setUrlPrefixesForDefaultIntent(ReadableArray param1ReadableArray) {
      this.mUrlPrefixesForDefaultIntent = param1ReadableArray;
    }
    
    public boolean shouldOverrideUrlLoading(WebView param1WebView, String param1String) {
      // Byte code:
      //   0: aload_0
      //   1: getfield mUrlPrefixesForDefaultIntent : Lcom/facebook/react/bridge/ReadableArray;
      //   4: astore #4
      //   6: aload #4
      //   8: ifnull -> 67
      //   11: aload #4
      //   13: invokeinterface size : ()I
      //   18: ifle -> 67
      //   21: aload_0
      //   22: getfield mUrlPrefixesForDefaultIntent : Lcom/facebook/react/bridge/ReadableArray;
      //   25: invokeinterface toArrayList : ()Ljava/util/ArrayList;
      //   30: invokevirtual iterator : ()Ljava/util/Iterator;
      //   33: astore #4
      //   35: aload #4
      //   37: invokeinterface hasNext : ()Z
      //   42: ifeq -> 67
      //   45: aload_2
      //   46: aload #4
      //   48: invokeinterface next : ()Ljava/lang/Object;
      //   53: checkcast java/lang/String
      //   56: invokevirtual startsWith : (Ljava/lang/String;)Z
      //   59: ifeq -> 35
      //   62: iconst_1
      //   63: istore_3
      //   64: goto -> 69
      //   67: iconst_0
      //   68: istore_3
      //   69: iload_3
      //   70: ifne -> 111
      //   73: aload_2
      //   74: ldc 'http://'
      //   76: invokevirtual startsWith : (Ljava/lang/String;)Z
      //   79: ifne -> 109
      //   82: aload_2
      //   83: ldc 'https://'
      //   85: invokevirtual startsWith : (Ljava/lang/String;)Z
      //   88: ifne -> 109
      //   91: aload_2
      //   92: ldc 'file://'
      //   94: invokevirtual startsWith : (Ljava/lang/String;)Z
      //   97: ifne -> 109
      //   100: aload_2
      //   101: ldc 'about:blank'
      //   103: invokevirtual equals : (Ljava/lang/Object;)Z
      //   106: ifeq -> 111
      //   109: iconst_0
      //   110: ireturn
      //   111: new android/content/Intent
      //   114: dup
      //   115: ldc 'android.intent.action.VIEW'
      //   117: aload_2
      //   118: invokestatic parse : (Ljava/lang/String;)Landroid/net/Uri;
      //   121: invokespecial <init> : (Ljava/lang/String;Landroid/net/Uri;)V
      //   124: astore #4
      //   126: aload #4
      //   128: ldc 268435456
      //   130: invokevirtual setFlags : (I)Landroid/content/Intent;
      //   133: pop
      //   134: aload_1
      //   135: invokevirtual getContext : ()Landroid/content/Context;
      //   138: aload #4
      //   140: invokevirtual startActivity : (Landroid/content/Intent;)V
      //   143: iconst_1
      //   144: ireturn
      //   145: astore_1
      //   146: new java/lang/StringBuilder
      //   149: dup
      //   150: ldc 'activity not found to handle uri scheme for: '
      //   152: invokespecial <init> : (Ljava/lang/String;)V
      //   155: astore #4
      //   157: aload #4
      //   159: aload_2
      //   160: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   163: pop
      //   164: ldc 'ReactNative'
      //   166: aload #4
      //   168: invokevirtual toString : ()Ljava/lang/String;
      //   171: aload_1
      //   172: invokestatic b : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
      //   175: iconst_1
      //   176: ireturn
      // Exception table:
      //   from	to	target	type
      //   111	143	145	android/content/ActivityNotFoundException
    }
  }
  
  class ReactWebViewManager {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\webview\ReactWebViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */