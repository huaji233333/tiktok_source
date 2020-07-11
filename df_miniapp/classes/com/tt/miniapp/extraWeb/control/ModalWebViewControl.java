package com.tt.miniapp.extraWeb.control;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.a.c;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.h;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.BaseActivityProxy;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.extraWeb.ComponentWebViewRender;
import com.tt.miniapp.extraWeb.bridge.ModalWebBridge;
import com.tt.miniapp.offlinezip.OnOfflineZipCheckUpdateResultListener;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.JsCoreUtils;
import com.tt.miniapp.util.MimeTypeUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.view.webcore.AppbrandWebviewClient;
import com.tt.miniapp.web.TTWebSetting;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapp.webbridge.WebBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import java.io.File;
import java.io.FileInputStream;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ModalWebViewControl {
  public h mModalWebViewBackPressedListener = new h() {
      public boolean onBackPressed() {
        int i = ModalWebViewControl.this.getCurrentOpenModalWebViewId();
        if (i != -1) {
          JsCoreUtils.sendAppOnPressBackButton("modalWebview", i);
          return true;
        } 
        return false;
      }
    };
  
  public LinkedHashMap<Integer, Integer> mModalWebViewIndex = new LinkedHashMap<Integer, Integer>();
  
  public SparseArray<ComponentWebViewRender> mModalWebViewRenderArray = new SparseArray();
  
  private boolean checkStyleValid(JSONObject paramJSONObject) {
    if (paramJSONObject.has("left") || paramJSONObject.has("top") || paramJSONObject.has("width") || paramJSONObject.has("height"))
      try {
        paramJSONObject.getInt("left");
        paramJSONObject.getInt("top");
        int i = paramJSONObject.getInt("width");
        int j = paramJSONObject.getInt("height");
        return (i >= 0) ? (!(j < 0)) : false;
      } catch (JSONException jSONException) {
        AppBrandLogger.eWithThrowable("ModalWebViewControl", paramJSONObject.toString(), (Throwable)jSONException);
        return false;
      }  
    return true;
  }
  
  public static ModalWebViewControl getInst() {
    return Holder.mInst;
  }
  
  private ComponentWebViewRender getModalWebViewRender(int paramInt, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: istore_3
    //   4: iload_1
    //   5: iconst_m1
    //   6: if_icmpne -> 14
    //   9: aload_0
    //   10: invokevirtual getCurrentOpenModalWebViewId : ()I
    //   13: istore_3
    //   14: aload_0
    //   15: getfield mModalWebViewRenderArray : Landroid/util/SparseArray;
    //   18: iload_3
    //   19: invokevirtual get : (I)Ljava/lang/Object;
    //   22: checkcast com/tt/miniapp/extraWeb/ComponentWebViewRender
    //   25: astore #4
    //   27: iload_2
    //   28: ifeq -> 51
    //   31: aload_0
    //   32: getfield mModalWebViewRenderArray : Landroid/util/SparseArray;
    //   35: iload_3
    //   36: invokevirtual remove : (I)V
    //   39: aload_0
    //   40: getfield mModalWebViewIndex : Ljava/util/LinkedHashMap;
    //   43: iload_3
    //   44: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   47: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   50: pop
    //   51: aload_0
    //   52: monitorexit
    //   53: aload #4
    //   55: areturn
    //   56: astore #4
    //   58: aload_0
    //   59: monitorexit
    //   60: aload #4
    //   62: athrow
    // Exception table:
    //   from	to	target	type
    //   9	14	56	finally
    //   14	27	56	finally
    //   31	51	56	finally
    //   51	53	56	finally
    //   58	60	56	finally
  }
  
  public boolean closeModalWebView(int paramInt) {
    AppBrandLogger.d("ModalWebViewControl", new Object[] { "closeModalWebView" });
    final ComponentWebViewRender closeModalWebViewRender = getModalWebViewRender(paramInt, true);
    if (componentWebViewRender != null) {
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
              if (webViewManager != null)
                webViewManager.removeRender(closeModalWebViewRender.getWebViewId()); 
              ToolUtils.clearWebView(closeModalWebViewRender.getWebView());
              closeModalWebViewRender.destroyWebBridge();
            }
          });
      return true;
    } 
    return false;
  }
  
  public int getCurrentOpenModalWebViewId() {
    int i = this.mModalWebViewRenderArray.size() - 1;
    if (i >= 0) {
      WebViewManager.IRender iRender = (WebViewManager.IRender)this.mModalWebViewRenderArray.valueAt(i);
      if (iRender != null)
        return iRender.getWebViewId(); 
    } 
    return -1;
  }
  
  public int getModalWebViewInsertIndex(ViewGroup paramViewGroup, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new java/util/ArrayList
    //   5: dup
    //   6: aload_0
    //   7: getfield mModalWebViewIndex : Ljava/util/LinkedHashMap;
    //   10: invokevirtual entrySet : ()Ljava/util/Set;
    //   13: invokespecial <init> : (Ljava/util/Collection;)V
    //   16: astore #5
    //   18: aload #5
    //   20: new com/tt/miniapp/extraWeb/control/ModalWebViewControl$6
    //   23: dup
    //   24: aload_0
    //   25: invokespecial <init> : (Lcom/tt/miniapp/extraWeb/control/ModalWebViewControl;)V
    //   28: invokestatic sort : (Ljava/util/List;Ljava/util/Comparator;)V
    //   31: aload_0
    //   32: getfield mModalWebViewIndex : Ljava/util/LinkedHashMap;
    //   35: invokevirtual clear : ()V
    //   38: iconst_0
    //   39: istore_3
    //   40: iconst_m1
    //   41: istore #4
    //   43: iload_3
    //   44: aload #5
    //   46: invokeinterface size : ()I
    //   51: if_icmpge -> 116
    //   54: aload #5
    //   56: iload_3
    //   57: invokeinterface get : (I)Ljava/lang/Object;
    //   62: checkcast java/util/Map$Entry
    //   65: astore #6
    //   67: aload #6
    //   69: invokeinterface getKey : ()Ljava/lang/Object;
    //   74: checkcast java/lang/Integer
    //   77: invokevirtual intValue : ()I
    //   80: iload_2
    //   81: if_icmpne -> 87
    //   84: iload_3
    //   85: istore #4
    //   87: aload_0
    //   88: getfield mModalWebViewIndex : Ljava/util/LinkedHashMap;
    //   91: aload #6
    //   93: invokeinterface getKey : ()Ljava/lang/Object;
    //   98: aload #6
    //   100: invokeinterface getValue : ()Ljava/lang/Object;
    //   105: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   108: pop
    //   109: iload_3
    //   110: iconst_1
    //   111: iadd
    //   112: istore_3
    //   113: goto -> 43
    //   116: iload #4
    //   118: iconst_1
    //   119: iadd
    //   120: istore_2
    //   121: iload_2
    //   122: aload #5
    //   124: invokeinterface size : ()I
    //   129: if_icmpge -> 180
    //   132: aload #5
    //   134: iload_2
    //   135: invokeinterface get : (I)Ljava/lang/Object;
    //   140: checkcast java/util/Map$Entry
    //   143: invokeinterface getKey : ()Ljava/lang/Object;
    //   148: checkcast java/lang/Integer
    //   151: invokevirtual intValue : ()I
    //   154: istore_2
    //   155: aload_1
    //   156: aload_0
    //   157: getfield mModalWebViewRenderArray : Landroid/util/SparseArray;
    //   160: iload_2
    //   161: invokevirtual get : (I)Ljava/lang/Object;
    //   164: checkcast com/tt/miniapp/extraWeb/ComponentWebViewRender
    //   167: invokevirtual getWebView : ()Landroid/webkit/WebView;
    //   170: invokevirtual indexOfChild : (Landroid/view/View;)I
    //   173: istore_2
    //   174: aload_0
    //   175: monitorexit
    //   176: iload_2
    //   177: iconst_1
    //   178: isub
    //   179: ireturn
    //   180: aload_0
    //   181: monitorexit
    //   182: iconst_m1
    //   183: ireturn
    //   184: astore_1
    //   185: aload_0
    //   186: monitorexit
    //   187: goto -> 192
    //   190: aload_1
    //   191: athrow
    //   192: goto -> 190
    // Exception table:
    //   from	to	target	type
    //   2	38	184	finally
    //   43	67	184	finally
    //   67	84	184	finally
    //   87	109	184	finally
    //   121	174	184	finally
  }
  
  public boolean isSameUri(Uri paramUri1, Uri paramUri2) {
    if (paramUri1 == paramUri2)
      return true; 
    if (paramUri1 != null) {
      if (paramUri2 == null)
        return false; 
      if (TextUtils.equals(paramUri1.getScheme(), paramUri2.getScheme()) && TextUtils.equals(paramUri1.getHost(), paramUri2.getHost()) && TextUtils.equals(paramUri1.getPath(), paramUri2.getPath()))
        return true; 
    } 
    return false;
  }
  
  public void loadWebView(final Activity currentActivity, final OpenModalWebViewListener openModalWebViewListener, final boolean useLocalUrl, final String offlinePath, final String loadUrl, final ModalWebViewStyle style, final boolean openModalWebViewWithHide) {
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            ModalWebViewControl modalWebViewControl;
            WebViewManager webViewManager;
            final int webViewId = ComponentIDCreator.create();
            null = new WebView((Context)currentActivity);
            null.setBackgroundColor(0);
            null.setWebViewClient(c.a((WebViewClient)new AppbrandWebviewClient() {
                    public void onPageFinished(WebView param2WebView, String param2String) {
                      super.onPageFinished(param2WebView, param2String);
                      if (!TextUtils.isEmpty(param2String) && ModalWebViewControl.this.isSameUri(Uri.parse(param2String), loadUri))
                        ThreadUtil.runOnWorkThread(new Action() {
                              public void act() {
                                if (loadUrlSuccess[0]) {
                                  openModalWebViewListener.onOpenModalWebView(true, 0, null, webViewId);
                                  return;
                                } 
                                openModalWebViewListener.onOpenModalWebView(false, 1001, "load failed", webViewId);
                              }
                            }Schedulers.shortIO()); 
                    }
                    
                    public void onReceivedError(WebView param2WebView, int param2Int, String param2String1, String param2String2) {
                      if (!TextUtils.isEmpty(param2String2) && ModalWebViewControl.this.isSameUri(Uri.parse(param2String2), loadUri))
                        loadUrlSuccess[0] = false; 
                      StringBuilder stringBuilder = new StringBuilder("openModalWebView onReceivedError errorCode:");
                      stringBuilder.append(param2Int);
                      stringBuilder.append(" description:");
                      stringBuilder.append(param2String1);
                      stringBuilder.append("failingUrl:");
                      stringBuilder.append(param2String2);
                      InnerEventHelper.mpTechnologyMsg(stringBuilder.toString());
                      super.onReceivedError(param2WebView, param2Int, param2String1, param2String2);
                    }
                    
                    public void onReceivedError(WebView param2WebView, WebResourceRequest param2WebResourceRequest, WebResourceError param2WebResourceError) {
                      if (ModalWebViewControl.this.isSameUri(param2WebResourceRequest.getUrl(), loadUri))
                        loadUrlSuccess[0] = false; 
                      super.onReceivedError(param2WebView, param2WebResourceRequest, param2WebResourceError);
                    }
                    
                    public WebResourceResponse shouldInterceptRequest(WebView param2WebView, WebResourceRequest param2WebResourceRequest) {
                      if (param2WebResourceRequest == null || param2WebResourceRequest.getUrl() == null)
                        return _lancet.com_ss_android_ugc_aweme_lancet_WebLancet_shouldInterceptRequest((AppbrandWebviewClient)this, param2WebView, param2WebResourceRequest); 
                      Uri uri = param2WebResourceRequest.getUrl();
                      String str2 = uri.getScheme();
                      String str1 = uri.getPath();
                      if (useLocalUrl && TextUtils.equals(str2, "file") && !TextUtils.isEmpty(str1) && !str1.startsWith(offlinePath)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(offlinePath);
                        stringBuilder.append(str1);
                        File file = new File(stringBuilder.toString());
                        if (file.exists())
                          try {
                            return new WebResourceResponse(MimeTypeUtil.getMimeType(uri.toString()), "UTF-8", new FileInputStream(file));
                          } catch (Exception exception) {
                            AppBrandLogger.e("ModalWebViewControl", new Object[] { "shouldInterceptRequest", exception });
                          }  
                      } 
                      return _lancet.com_ss_android_ugc_aweme_lancet_WebLancet_shouldInterceptRequest((AppbrandWebviewClient)this, param2WebView, param2WebResourceRequest);
                    }
                    
                    class null {}
                  }));
            ComponentWebViewRender componentWebViewRender = new ComponentWebViewRender(AppbrandApplicationImpl.getInst(), null, i);
            ModalWebBridge modalWebBridge = new ModalWebBridge(AppbrandApplicationImpl.getInst(), componentWebViewRender, i);
            componentWebViewRender.setBridge((WebBridge)modalWebBridge);
            null.addJavascriptInterface(modalWebBridge, "ttJSCore");
            (new TTWebSetting(null.getSettings())).setDefaultSetting();
            _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(null, loadUrl);
            synchronized (ModalWebViewControl.this) {
              ModalWebViewControl.this.mModalWebViewRenderArray.put(i, componentWebViewRender);
              ModalWebViewControl.this.mModalWebViewIndex.put(Integer.valueOf(i), Integer.valueOf(style.zIndex));
              ViewGroup viewGroup = (ViewGroup)((ViewGroup)currentActivity.findViewById(16908290)).getChildAt(0);
              ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(style.width, style.height);
              marginLayoutParams.leftMargin = style.left;
              marginLayoutParams.topMargin = style.top;
              viewGroup.addView((View)null, ModalWebViewControl.this.getModalWebViewInsertIndex(viewGroup, i), (ViewGroup.LayoutParams)marginLayoutParams);
              if (style.left > 0 || style.top > 0)
                null.post(new Runnable() {
                      public void run() {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)webView.getLayoutParams();
                        if (marginLayoutParams.leftMargin != style.left)
                          marginLayoutParams.leftMargin = style.left; 
                        if (marginLayoutParams.topMargin != style.top)
                          marginLayoutParams.topMargin = style.top; 
                        webView.setLayoutParams((ViewGroup.LayoutParams)marginLayoutParams);
                      }
                    }); 
              Activity activity = currentActivity;
              if (activity instanceof MiniappHostBase) {
                IActivityProxy iActivityProxy = ((MiniappHostBase)activity).getActivityProxy();
                if (iActivityProxy instanceof BaseActivityProxy)
                  ((BaseActivityProxy)iActivityProxy).registerBackPressedListener(ModalWebViewControl.this.mModalWebViewBackPressedListener); 
              } 
              webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
              if (webViewManager != null)
                webViewManager.addRender((WebViewManager.IRender)componentWebViewRender); 
              if (openModalWebViewWithHide)
                null.setVisibility(8); 
              return;
            } 
          }
          
          class null {}
        });
  }
  
  public void openModalWebView(String paramString, OpenModalWebViewListener paramOpenModalWebViewListener) {
    // Byte code:
    //   0: ldc 'ModalWebViewControl'
    //   2: iconst_1
    //   3: anewarray java/lang/Object
    //   6: dup
    //   7: iconst_0
    //   8: ldc 'openModalWebView'
    //   10: aastore
    //   11: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   14: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   17: invokevirtual getCurrentActivity : ()Lcom/tt/miniapphost/MiniappHostBase;
    //   20: astore #9
    //   22: aload #9
    //   24: ifnonnull -> 56
    //   27: ldc 'ModalWebViewControl'
    //   29: iconst_1
    //   30: anewarray java/lang/Object
    //   33: dup
    //   34: iconst_0
    //   35: ldc 'openModalWebView currentActivity == null'
    //   37: aastore
    //   38: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   41: aload_2
    //   42: iconst_0
    //   43: sipush #1003
    //   46: ldc_w 'activity is null'
    //   49: iconst_m1
    //   50: invokeinterface onOpenModalWebView : (ZILjava/lang/String;I)V
    //   55: return
    //   56: aload_1
    //   57: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   60: ifeq -> 93
    //   63: ldc 'ModalWebViewControl'
    //   65: iconst_1
    //   66: anewarray java/lang/Object
    //   69: dup
    //   70: iconst_0
    //   71: ldc_w 'openModalWebView TextUtils.isEmpty(params)'
    //   74: aastore
    //   75: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   78: aload_2
    //   79: iconst_0
    //   80: sipush #1003
    //   83: ldc_w 'params is empty'
    //   86: iconst_m1
    //   87: invokeinterface onOpenModalWebView : (ZILjava/lang/String;I)V
    //   92: return
    //   93: new com/tt/miniapp/extraWeb/control/ModalWebViewControl$ModalWebViewStyle
    //   96: dup
    //   97: aload_0
    //   98: iconst_0
    //   99: iconst_0
    //   100: iconst_m1
    //   101: iconst_m1
    //   102: iconst_0
    //   103: invokespecial <init> : (Lcom/tt/miniapp/extraWeb/control/ModalWebViewControl;IIIII)V
    //   106: astore #10
    //   108: aload #9
    //   110: invokestatic getOfflineZipDir : (Landroid/content/Context;)Ljava/io/File;
    //   113: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   116: astore #7
    //   118: new org/json/JSONObject
    //   121: dup
    //   122: aload_1
    //   123: invokespecial <init> : (Ljava/lang/String;)V
    //   126: astore #8
    //   128: aload #8
    //   130: ldc_w 'path'
    //   133: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   136: astore #6
    //   138: aload #8
    //   140: ldc_w 'style'
    //   143: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   146: astore #11
    //   148: aload #11
    //   150: ifnull -> 763
    //   153: ldc_w '{}'
    //   156: aload #11
    //   158: invokevirtual toString : ()Ljava/lang/String;
    //   161: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   164: istore #4
    //   166: iload #4
    //   168: ifne -> 763
    //   171: aload_0
    //   172: aload #11
    //   174: invokespecial checkStyleValid : (Lorg/json/JSONObject;)Z
    //   177: ifne -> 235
    //   180: new java/lang/StringBuilder
    //   183: dup
    //   184: ldc_w 'openModalWebView style params invalid '
    //   187: invokespecial <init> : (Ljava/lang/String;)V
    //   190: astore_1
    //   191: aload_1
    //   192: aload #11
    //   194: invokevirtual toString : ()Ljava/lang/String;
    //   197: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   200: pop
    //   201: ldc 'ModalWebViewControl'
    //   203: iconst_1
    //   204: anewarray java/lang/Object
    //   207: dup
    //   208: iconst_0
    //   209: aload_1
    //   210: invokevirtual toString : ()Ljava/lang/String;
    //   213: aastore
    //   214: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   217: aload_2
    //   218: iconst_0
    //   219: sipush #1003
    //   222: ldc_w 'style'
    //   225: invokestatic b : (Ljava/lang/String;)Ljava/lang/String;
    //   228: iconst_m1
    //   229: invokeinterface onOpenModalWebView : (ZILjava/lang/String;I)V
    //   234: return
    //   235: aload #11
    //   237: ldc 'left'
    //   239: invokevirtual has : (Ljava/lang/String;)Z
    //   242: ifeq -> 351
    //   245: aload #11
    //   247: ldc 'top'
    //   249: invokevirtual has : (Ljava/lang/String;)Z
    //   252: ifeq -> 351
    //   255: aload #11
    //   257: ldc 'width'
    //   259: invokevirtual has : (Ljava/lang/String;)Z
    //   262: ifeq -> 351
    //   265: aload #11
    //   267: ldc 'height'
    //   269: invokevirtual has : (Ljava/lang/String;)Z
    //   272: ifeq -> 351
    //   275: aload #10
    //   277: aload #9
    //   279: aload #11
    //   281: ldc 'left'
    //   283: invokevirtual optInt : (Ljava/lang/String;)I
    //   286: i2f
    //   287: invokestatic dip2Px : (Landroid/content/Context;F)F
    //   290: f2i
    //   291: putfield left : I
    //   294: aload #10
    //   296: aload #9
    //   298: aload #11
    //   300: ldc 'top'
    //   302: invokevirtual optInt : (Ljava/lang/String;)I
    //   305: i2f
    //   306: invokestatic dip2Px : (Landroid/content/Context;F)F
    //   309: f2i
    //   310: putfield top : I
    //   313: aload #10
    //   315: aload #9
    //   317: aload #11
    //   319: ldc 'width'
    //   321: invokevirtual optInt : (Ljava/lang/String;)I
    //   324: i2f
    //   325: invokestatic dip2Px : (Landroid/content/Context;F)F
    //   328: f2i
    //   329: putfield width : I
    //   332: aload #10
    //   334: aload #9
    //   336: aload #11
    //   338: ldc 'height'
    //   340: invokevirtual optInt : (Ljava/lang/String;)I
    //   343: i2f
    //   344: invokestatic dip2Px : (Landroid/content/Context;F)F
    //   347: f2i
    //   348: putfield height : I
    //   351: aload #10
    //   353: aload #11
    //   355: ldc_w 'zIndex'
    //   358: invokevirtual optInt : (Ljava/lang/String;)I
    //   361: putfield zIndex : I
    //   364: goto -> 367
    //   367: aload #6
    //   369: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   372: istore #4
    //   374: iload #4
    //   376: ifne -> 449
    //   379: aload #6
    //   381: ldc_w 'ttoffline'
    //   384: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   387: ifeq -> 449
    //   390: getstatic com/tt/miniapp/offlinezip/OfflineZipManager.INSTANCE : Lcom/tt/miniapp/offlinezip/OfflineZipManager;
    //   393: aload #9
    //   395: aload #6
    //   397: invokevirtual offlineUrlToFileUrl : (Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   400: astore #6
    //   402: aload #6
    //   404: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   407: ifeq -> 766
    //   410: ldc 'ModalWebViewControl'
    //   412: iconst_2
    //   413: anewarray java/lang/Object
    //   416: dup
    //   417: iconst_0
    //   418: ldc_w 'openModalWebView TextUtils.isEmpty(loadUrl) params:'
    //   421: aastore
    //   422: dup
    //   423: iconst_1
    //   424: aload_1
    //   425: aastore
    //   426: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   429: aload_2
    //   430: iconst_0
    //   431: sipush #1003
    //   434: ldc_w 'path'
    //   437: invokestatic b : (Ljava/lang/String;)Ljava/lang/String;
    //   440: iconst_m1
    //   441: invokeinterface onOpenModalWebView : (ZILjava/lang/String;I)V
    //   446: goto -> 766
    //   449: aload #8
    //   451: ldc_w 'url'
    //   454: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   457: astore #6
    //   459: aload #6
    //   461: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   464: ifeq -> 775
    //   467: ldc 'ModalWebViewControl'
    //   469: iconst_2
    //   470: anewarray java/lang/Object
    //   473: dup
    //   474: iconst_0
    //   475: ldc_w 'openModalWebView TextUtils.isEmpty(loadUrl) params:'
    //   478: aastore
    //   479: dup
    //   480: iconst_1
    //   481: aload_1
    //   482: aastore
    //   483: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   486: aload_2
    //   487: iconst_0
    //   488: sipush #1003
    //   491: ldc_w 'url'
    //   494: invokestatic b : (Ljava/lang/String;)Ljava/lang/String;
    //   497: iconst_m1
    //   498: invokeinterface onOpenModalWebView : (ZILjava/lang/String;I)V
    //   503: return
    //   504: aload #8
    //   506: ldc_w 'hide'
    //   509: invokevirtual optInt : (Ljava/lang/String;)I
    //   512: istore_3
    //   513: iload_3
    //   514: iconst_1
    //   515: if_icmpne -> 524
    //   518: iconst_1
    //   519: istore #5
    //   521: goto -> 527
    //   524: iconst_0
    //   525: istore #5
    //   527: aload_1
    //   528: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   531: ifne -> 679
    //   534: aload_1
    //   535: aload #7
    //   537: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   540: ifeq -> 679
    //   543: aload_1
    //   544: aload_1
    //   545: aload #7
    //   547: invokevirtual indexOf : (Ljava/lang/String;)I
    //   550: aload #7
    //   552: invokevirtual length : ()I
    //   555: iadd
    //   556: invokevirtual substring : (I)Ljava/lang/String;
    //   559: astore #8
    //   561: aload #8
    //   563: astore #6
    //   565: aload #8
    //   567: invokevirtual length : ()I
    //   570: ifle -> 596
    //   573: aload #8
    //   575: astore #6
    //   577: aload #8
    //   579: getstatic java/io/File.separator : Ljava/lang/String;
    //   582: invokevirtual indexOf : (Ljava/lang/String;)I
    //   585: ifne -> 596
    //   588: aload #8
    //   590: iconst_1
    //   591: invokevirtual substring : (I)Ljava/lang/String;
    //   594: astore #6
    //   596: aload #6
    //   598: getstatic java/io/File.separator : Ljava/lang/String;
    //   601: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   604: iconst_0
    //   605: aaload
    //   606: astore #6
    //   608: getstatic com/tt/miniapp/offlinezip/OfflineZipManager.INSTANCE : Lcom/tt/miniapp/offlinezip/OfflineZipManager;
    //   611: aload #9
    //   613: aload #6
    //   615: invokevirtual getSpecifiedOfflineModuleVersion : (Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   618: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   621: ifne -> 641
    //   624: aload_0
    //   625: aload #9
    //   627: aload_2
    //   628: iload #4
    //   630: aload #7
    //   632: aload_1
    //   633: aload #10
    //   635: iload #5
    //   637: invokevirtual loadWebView : (Landroid/app/Activity;Lcom/tt/miniapp/extraWeb/control/ModalWebViewControl$OpenModalWebViewListener;ZLjava/lang/String;Ljava/lang/String;Lcom/tt/miniapp/extraWeb/control/ModalWebViewControl$ModalWebViewStyle;Z)V
    //   640: return
    //   641: getstatic com/tt/miniapp/offlinezip/OfflineZipManager.INSTANCE : Lcom/tt/miniapp/offlinezip/OfflineZipManager;
    //   644: aload #9
    //   646: new com/tt/miniapp/extraWeb/control/ModalWebViewControl$2
    //   649: dup
    //   650: aload_0
    //   651: aload #9
    //   653: aload_2
    //   654: iload #4
    //   656: aload #7
    //   658: aload_1
    //   659: aload #10
    //   661: iload #5
    //   663: invokespecial <init> : (Lcom/tt/miniapp/extraWeb/control/ModalWebViewControl;Landroid/app/Activity;Lcom/tt/miniapp/extraWeb/control/ModalWebViewControl$OpenModalWebViewListener;ZLjava/lang/String;Ljava/lang/String;Lcom/tt/miniapp/extraWeb/control/ModalWebViewControl$ModalWebViewStyle;Z)V
    //   666: iconst_1
    //   667: anewarray java/lang/String
    //   670: dup
    //   671: iconst_0
    //   672: aload #6
    //   674: aastore
    //   675: invokevirtual checkUpdateOfflineZip : (Landroid/content/Context;Lcom/tt/miniapp/offlinezip/OnOfflineZipCheckUpdateResultListener;[Ljava/lang/String;)V
    //   678: return
    //   679: aload_0
    //   680: aload #9
    //   682: aload_2
    //   683: iload #4
    //   685: aload #7
    //   687: aload_1
    //   688: aload #10
    //   690: iload #5
    //   692: invokevirtual loadWebView : (Landroid/app/Activity;Lcom/tt/miniapp/extraWeb/control/ModalWebViewControl$OpenModalWebViewListener;ZLjava/lang/String;Ljava/lang/String;Lcom/tt/miniapp/extraWeb/control/ModalWebViewControl$ModalWebViewStyle;Z)V
    //   695: return
    //   696: astore_1
    //   697: goto -> 705
    //   700: astore_1
    //   701: goto -> 705
    //   704: astore_1
    //   705: ldc 'ModalWebViewControl'
    //   707: iconst_2
    //   708: anewarray java/lang/Object
    //   711: dup
    //   712: iconst_0
    //   713: ldc 'openModalWebView'
    //   715: aastore
    //   716: dup
    //   717: iconst_1
    //   718: aload_1
    //   719: aastore
    //   720: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   723: aload_2
    //   724: iconst_0
    //   725: sipush #1003
    //   728: aload_1
    //   729: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/String;
    //   732: iconst_m1
    //   733: invokeinterface onOpenModalWebView : (ZILjava/lang/String;I)V
    //   738: new java/lang/StringBuilder
    //   741: dup
    //   742: ldc_w 'openModalWebView parse loadUrl exception:'
    //   745: invokespecial <init> : (Ljava/lang/String;)V
    //   748: astore_2
    //   749: aload_2
    //   750: aload_1
    //   751: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   754: pop
    //   755: aload_2
    //   756: invokevirtual toString : ()Ljava/lang/String;
    //   759: invokestatic mpTechnologyMsg : (Ljava/lang/String;)V
    //   762: return
    //   763: goto -> 367
    //   766: aload #6
    //   768: astore_1
    //   769: iconst_1
    //   770: istore #4
    //   772: goto -> 504
    //   775: aload #6
    //   777: astore_1
    //   778: iconst_0
    //   779: istore #4
    //   781: goto -> 504
    // Exception table:
    //   from	to	target	type
    //   118	138	704	java/lang/Exception
    //   138	148	700	java/lang/Exception
    //   153	166	700	java/lang/Exception
    //   171	234	696	java/lang/Exception
    //   235	351	696	java/lang/Exception
    //   351	364	696	java/lang/Exception
    //   367	374	696	java/lang/Exception
    //   379	446	696	java/lang/Exception
    //   449	503	696	java/lang/Exception
    //   504	513	696	java/lang/Exception
  }
  
  public void operateModalWebViewShowState(int paramInt, final boolean show, final OperateModalWebViewShowStateListener listener) {
    final ComponentWebViewRender closeModalWebViewRender = getModalWebViewRender(paramInt, false);
    if (componentWebViewRender != null) {
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              WebView webView = closeModalWebViewRender.getWebView();
              byte b = 0;
              if (webView != null) {
                if (!show)
                  b = 8; 
                webView.setVisibility(b);
                listener.onOperateShowStateFinish(true);
                return;
              } 
              listener.onOperateShowStateFinish(false);
            }
          });
      return;
    } 
    listener.onOperateShowStateFinish(false);
  }
  
  static final class Holder {
    static final ModalWebViewControl mInst = new ModalWebViewControl();
  }
  
  class ModalWebViewStyle {
    int height;
    
    int left;
    
    int top;
    
    int width;
    
    int zIndex;
    
    public ModalWebViewStyle(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      this.left = param1Int1;
      this.top = param1Int2;
      this.width = param1Int3;
      this.height = param1Int4;
      this.zIndex = param1Int5;
    }
  }
  
  public static interface OpenModalWebViewListener {
    void onOpenModalWebView(boolean param1Boolean, int param1Int1, String param1String, int param1Int2);
  }
  
  public static interface OperateModalWebViewShowStateListener {
    void onOperateShowStateFinish(boolean param1Boolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\extraWeb\control\ModalWebViewControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */