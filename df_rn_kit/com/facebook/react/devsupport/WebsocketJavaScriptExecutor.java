package com.facebook.react.devsupport;

import android.os.Handler;
import android.os.Looper;
import com.facebook.i.a.a;
import com.facebook.react.bridge.JavaJSExecutor;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class WebsocketJavaScriptExecutor implements JavaJSExecutor {
  private final HashMap<String, String> mInjectedObjects = new HashMap<String, String>();
  
  public JSDebuggerWebSocketClient mWebSocketClient;
  
  public void close() {
    JSDebuggerWebSocketClient jSDebuggerWebSocketClient = this.mWebSocketClient;
    if (jSDebuggerWebSocketClient != null)
      jSDebuggerWebSocketClient.closeQuietly(); 
  }
  
  public void connect(final String webSocketServerUrl, final JSExecutorConnectCallback callback) {
    connectInternal(webSocketServerUrl, new JSExecutorConnectCallback() {
          public void onFailure(Throwable param1Throwable) {
            if (retryCount.decrementAndGet() <= 0) {
              callback.onFailure(param1Throwable);
              return;
            } 
            WebsocketJavaScriptExecutor.this.connectInternal(webSocketServerUrl, this);
          }
          
          public void onSuccess() {
            callback.onSuccess();
          }
        });
  }
  
  public void connectInternal(String paramString, final JSExecutorConnectCallback callback) {
    final JSDebuggerWebSocketClient client = new JSDebuggerWebSocketClient();
    final Handler timeoutHandler = new Handler(Looper.getMainLooper());
    jSDebuggerWebSocketClient.connect(paramString, new JSDebuggerWebSocketClient.JSDebuggerCallback() {
          public boolean didSendResult;
          
          public void onFailure(Throwable param1Throwable) {
            timeoutHandler.removeCallbacksAndMessages(null);
            if (!this.didSendResult) {
              callback.onFailure(param1Throwable);
              this.didSendResult = true;
            } 
          }
          
          public void onSuccess(String param1String) {
            client.prepareJSRuntime(new JSDebuggerWebSocketClient.JSDebuggerCallback() {
                  public void onFailure(Throwable param2Throwable) {
                    timeoutHandler.removeCallbacksAndMessages(null);
                    if (!WebsocketJavaScriptExecutor.null.this.didSendResult) {
                      callback.onFailure(param2Throwable);
                      WebsocketJavaScriptExecutor.null.this.didSendResult = true;
                    } 
                  }
                  
                  public void onSuccess(String param2String) {
                    timeoutHandler.removeCallbacksAndMessages(null);
                    WebsocketJavaScriptExecutor.this.mWebSocketClient = client;
                    if (!WebsocketJavaScriptExecutor.null.this.didSendResult) {
                      callback.onSuccess();
                      WebsocketJavaScriptExecutor.null.this.didSendResult = true;
                    } 
                  }
                });
          }
        });
    handler.postDelayed(new Runnable() {
          public void run() {
            client.closeQuietly();
            callback.onFailure(new WebsocketJavaScriptExecutor.WebsocketExecutorTimeoutException("Timeout while connecting to remote debugger"));
          }
        },  5000L);
  }
  
  public String executeJSCall(String paramString1, String paramString2) throws JavaJSExecutor.ProxyExecutorException {
    JSExecutorCallbackFuture jSExecutorCallbackFuture = new JSExecutorCallbackFuture();
    ((JSDebuggerWebSocketClient)a.b(this.mWebSocketClient)).executeJSCall(paramString1, paramString2, jSExecutorCallbackFuture);
    try {
      return jSExecutorCallbackFuture.get();
    } finally {
      paramString1 = null;
    } 
  }
  
  public void loadApplicationScript(String paramString) throws JavaJSExecutor.ProxyExecutorException {
    JSExecutorCallbackFuture jSExecutorCallbackFuture = new JSExecutorCallbackFuture();
    ((JSDebuggerWebSocketClient)a.b(this.mWebSocketClient)).loadApplicationScript(paramString, this.mInjectedObjects, jSExecutorCallbackFuture);
    try {
      return;
    } finally {
      paramString = null;
    } 
  }
  
  public void setGlobalVariable(String paramString1, String paramString2) {
    this.mInjectedObjects.put(paramString1, paramString2);
  }
  
  static class JSExecutorCallbackFuture implements JSDebuggerWebSocketClient.JSDebuggerCallback {
    private Throwable mCause;
    
    private String mResponse;
    
    private final Semaphore mSemaphore = new Semaphore(0);
    
    private JSExecutorCallbackFuture() {}
    
    public String get() throws Throwable {
      this.mSemaphore.acquire();
      Throwable throwable = this.mCause;
      if (throwable == null)
        return this.mResponse; 
      throw throwable;
    }
    
    public void onFailure(Throwable param1Throwable) {
      this.mCause = param1Throwable;
      this.mSemaphore.release();
    }
    
    public void onSuccess(String param1String) {
      this.mResponse = param1String;
      this.mSemaphore.release();
    }
  }
  
  public static interface JSExecutorConnectCallback {
    void onFailure(Throwable param1Throwable);
    
    void onSuccess();
  }
  
  public static class WebsocketExecutorTimeoutException extends Exception {
    public WebsocketExecutorTimeoutException(String param1String) {
      super(param1String);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\WebsocketJavaScriptExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */