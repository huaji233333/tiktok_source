package com.tt.miniapphost.render.export;

import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.bytedance.lynx.webview.extension.TTWebViewExtension;
import com.bytedance.lynx.webview.glue.IWebViewExtension;
import com.tt.miniapphost.render.internal.InterfaceConverter;
import d.f.b.g;
import d.f.b.l;
import d.u;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TTWebViewExtensionWrapper {
  public static final Companion Companion = new Companion(null);
  
  private static final Map<String, Companion.MethodRecord> sNameToMethod = new ConcurrentHashMap<String, Companion.MethodRecord>();
  
  private final TTWebViewExtension realExt;
  
  public TTWebViewExtensionWrapper(WebView paramWebView) {
    this.realExt = new TTWebViewExtension(paramWebView);
  }
  
  public final long getLoadingStatusCode() {
    return this.realExt.getLoadingStatusCode();
  }
  
  public final boolean isTTRenderEnabled() {
    return this.realExt.isTTRenderEnabled("1110018");
  }
  
  public final void registerPlatformView(View paramView, int paramInt) {
    this.realExt.registerPlatformView(paramView, paramInt);
  }
  
  public final boolean renderEvaluateJavaScript(String paramString, ValueCallback<String> paramValueCallback) {
    // Byte code:
    //   0: getstatic com/tt/miniapphost/render/export/TTWebViewExtensionWrapper.sNameToMethod : Ljava/util/Map;
    //   3: ldc 'renderEvaluateJavaScript'
    //   5: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   10: checkcast com/tt/miniapphost/render/export/TTWebViewExtensionWrapper$Companion$MethodRecord
    //   13: astore #4
    //   15: aload #4
    //   17: astore_3
    //   18: aload #4
    //   20: ifnonnull -> 69
    //   23: new com/tt/miniapphost/render/export/TTWebViewExtensionWrapper$Companion$MethodRecord
    //   26: dup
    //   27: aload_0
    //   28: getfield realExt : Lcom/bytedance/lynx/webview/extension/TTWebViewExtension;
    //   31: invokevirtual getClass : ()Ljava/lang/Class;
    //   34: ldc 'renderEvaluateJavaScript'
    //   36: iconst_2
    //   37: anewarray java/lang/Class
    //   40: dup
    //   41: iconst_0
    //   42: ldc java/lang/String
    //   44: aastore
    //   45: dup
    //   46: iconst_1
    //   47: ldc android/webkit/ValueCallback
    //   49: aastore
    //   50: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   53: invokespecial <init> : (Ljava/lang/reflect/Method;)V
    //   56: astore_3
    //   57: getstatic com/tt/miniapphost/render/export/TTWebViewExtensionWrapper.sNameToMethod : Ljava/util/Map;
    //   60: ldc 'renderEvaluateJavaScript'
    //   62: aload_3
    //   63: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   68: pop
    //   69: aload_3
    //   70: invokevirtual getMethod : ()Ljava/lang/reflect/Method;
    //   73: astore_3
    //   74: aload_3
    //   75: ifnull -> 154
    //   78: aload_3
    //   79: aload_0
    //   80: getfield realExt : Lcom/bytedance/lynx/webview/extension/TTWebViewExtension;
    //   83: iconst_2
    //   84: anewarray java/lang/Object
    //   87: dup
    //   88: iconst_0
    //   89: aload_1
    //   90: aastore
    //   91: dup
    //   92: iconst_1
    //   93: aload_2
    //   94: aastore
    //   95: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   98: astore_1
    //   99: aload_1
    //   100: ifnull -> 111
    //   103: aload_1
    //   104: checkcast java/lang/Boolean
    //   107: invokevirtual booleanValue : ()Z
    //   110: ireturn
    //   111: new d/u
    //   114: dup
    //   115: ldc 'null cannot be cast to non-null type kotlin.Boolean'
    //   117: invokespecial <init> : (Ljava/lang/String;)V
    //   120: athrow
    //   121: astore_1
    //   122: ldc 'TTWebViewExtensionWrapper'
    //   124: iconst_1
    //   125: anewarray java/lang/Object
    //   128: dup
    //   129: iconst_0
    //   130: aload_1
    //   131: aastore
    //   132: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   135: getstatic com/tt/miniapphost/render/export/TTWebViewExtensionWrapper.sNameToMethod : Ljava/util/Map;
    //   138: ldc 'renderEvaluateJavaScript'
    //   140: new com/tt/miniapphost/render/export/TTWebViewExtensionWrapper$Companion$MethodRecord
    //   143: dup
    //   144: aconst_null
    //   145: invokespecial <init> : (Ljava/lang/reflect/Method;)V
    //   148: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   153: pop
    //   154: iconst_0
    //   155: ireturn
    // Exception table:
    //   from	to	target	type
    //   23	69	121	java/lang/Exception
    //   69	74	121	java/lang/Exception
    //   78	99	121	java/lang/Exception
    //   103	111	121	java/lang/Exception
    //   111	121	121	java/lang/Exception
  }
  
  public final void setPerformanceTimingListener(PerformanceTimingListener paramPerformanceTimingListener) {
    l.b(paramPerformanceTimingListener, "mTTWebviewPerf");
    Object object = InterfaceConverter.INSTANCE.convertInterface(paramPerformanceTimingListener, PerformanceTimingListener.class);
    TTWebViewExtension tTWebViewExtension = this.realExt;
    if (object != null) {
      tTWebViewExtension.setPerformanceTimingListener((IWebViewExtension.PerformanceTimingListener)object);
      return;
    } 
    throw new u("null cannot be cast to non-null type com.bytedance.lynx.webview.glue.IWebViewExtension.PerformanceTimingListener");
  }
  
  public final void setPlatformViewLayersScrollListener(PlatformViewLayersScrollListener paramPlatformViewLayersScrollListener) {
    l.b(paramPlatformViewLayersScrollListener, "scrollListener");
    Object object = InterfaceConverter.INSTANCE.convertInterface(paramPlatformViewLayersScrollListener, PlatformViewLayersScrollListener.class);
    TTWebViewExtension tTWebViewExtension = this.realExt;
    if (object != null) {
      tTWebViewExtension.setPlatformViewLayersScrollListener((IWebViewExtension.PlatformViewLayersScrollListener)object);
      return;
    } 
    throw new u("null cannot be cast to non-null type com.bytedance.lynx.webview.glue.IWebViewExtension.PlatformViewLayersScrollListener");
  }
  
  public static final class Companion {
    private Companion() {}
    
    public static final class MethodRecord {
      private final Method method;
      
      public MethodRecord(Method param2Method) {
        this.method = param2Method;
      }
      
      public final Method component1() {
        return this.method;
      }
      
      public final MethodRecord copy(Method param2Method) {
        return new MethodRecord(param2Method);
      }
      
      public final boolean equals(Object param2Object) {
        if (this != param2Object) {
          if (param2Object instanceof MethodRecord) {
            param2Object = param2Object;
            if (l.a(this.method, ((MethodRecord)param2Object).method))
              return true; 
          } 
          return false;
        } 
        return true;
      }
      
      public final Method getMethod() {
        return this.method;
      }
      
      public final int hashCode() {
        Method method = this.method;
        return (method != null) ? method.hashCode() : 0;
      }
      
      public final String toString() {
        StringBuilder stringBuilder = new StringBuilder("MethodRecord(method=");
        stringBuilder.append(this.method);
        stringBuilder.append(")");
        return stringBuilder.toString();
      }
    }
  }
  
  public static final class MethodRecord {
    private final Method method;
    
    public MethodRecord(Method param1Method) {
      this.method = param1Method;
    }
    
    public final Method component1() {
      return this.method;
    }
    
    public final MethodRecord copy(Method param1Method) {
      return new MethodRecord(param1Method);
    }
    
    public final boolean equals(Object param1Object) {
      if (this != param1Object) {
        if (param1Object instanceof MethodRecord) {
          param1Object = param1Object;
          if (l.a(this.method, ((MethodRecord)param1Object).method))
            return true; 
        } 
        return false;
      } 
      return true;
    }
    
    public final Method getMethod() {
      return this.method;
    }
    
    public final int hashCode() {
      Method method = this.method;
      return (method != null) ? method.hashCode() : 0;
    }
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder("MethodRecord(method=");
      stringBuilder.append(this.method);
      stringBuilder.append(")");
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\render\export\TTWebViewExtensionWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */