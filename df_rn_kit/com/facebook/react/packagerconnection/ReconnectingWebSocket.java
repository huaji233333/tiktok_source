package com.facebook.react.packagerconnection;

import android.os.Handler;
import android.os.Looper;
import com.facebook.common.e.a;
import g.i;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.ai;
import okhttp3.aj;
import okhttp3.y;

public final class ReconnectingWebSocket extends aj {
  private static final String TAG = ReconnectingWebSocket.class.getSimpleName();
  
  private boolean mClosed;
  
  private ConnectionCallback mConnectionCallback;
  
  private final Handler mHandler;
  
  private MessageCallback mMessageCallback;
  
  private boolean mSuppressConnectionErrors;
  
  private final String mUrl;
  
  private ai mWebSocket;
  
  public ReconnectingWebSocket(String paramString, MessageCallback paramMessageCallback, ConnectionCallback paramConnectionCallback) {
    this.mUrl = paramString;
    this.mMessageCallback = paramMessageCallback;
    this.mConnectionCallback = paramConnectionCallback;
    this.mHandler = new Handler(Looper.getMainLooper());
  }
  
  private void abort(String paramString, Throwable paramThrowable) {
    String str = TAG;
    StringBuilder stringBuilder = new StringBuilder("Error occurred, shutting down websocket connection: ");
    stringBuilder.append(paramString);
    a.c(str, stringBuilder.toString(), paramThrowable);
    closeWebSocketQuietly();
  }
  
  private void closeWebSocketQuietly() {
    ai ai1 = this.mWebSocket;
    if (ai1 != null) {
      try {
        ai1.b(1000, "End of session");
      } catch (Exception exception) {}
      this.mWebSocket = null;
    } 
  }
  
  private void reconnect() {
    if (!this.mClosed) {
      if (!this.mSuppressConnectionErrors) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder("Couldn't connect to \"");
        stringBuilder.append(this.mUrl);
        stringBuilder.append("\", will silently retry");
        a.b(str, stringBuilder.toString());
        this.mSuppressConnectionErrors = true;
      } 
      this.mHandler.postDelayed(new Runnable() {
            public void run() {
              ReconnectingWebSocket.this.delayedReconnect();
            }
          },  2000L);
      return;
    } 
    throw new IllegalStateException("Can't reconnect closed client");
  }
  
  public final void closeQuietly() {
    this.mClosed = true;
    closeWebSocketQuietly();
    this.mMessageCallback = null;
    ConnectionCallback connectionCallback = this.mConnectionCallback;
    if (connectionCallback != null)
      connectionCallback.onDisconnected(); 
  }
  
  public final void connect() {
    if (!this.mClosed) {
      (new y.a()).a(10L, TimeUnit.SECONDS).c(10L, TimeUnit.SECONDS).b(0L, TimeUnit.MINUTES).a().a((new ac.a()).a(this.mUrl).c(), this);
      return;
    } 
    throw new IllegalStateException("Can't connect closed client");
  }
  
  public final void delayedReconnect() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mClosed : Z
    //   6: ifne -> 13
    //   9: aload_0
    //   10: invokevirtual connect : ()V
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
  
  public final void onClosed(ai paramai, int paramInt, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aconst_null
    //   4: putfield mWebSocket : Lokhttp3/ai;
    //   7: aload_0
    //   8: getfield mClosed : Z
    //   11: ifne -> 34
    //   14: aload_0
    //   15: getfield mConnectionCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$ConnectionCallback;
    //   18: ifnull -> 30
    //   21: aload_0
    //   22: getfield mConnectionCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$ConnectionCallback;
    //   25: invokeinterface onDisconnected : ()V
    //   30: aload_0
    //   31: invokespecial reconnect : ()V
    //   34: aload_0
    //   35: monitorexit
    //   36: return
    //   37: astore_1
    //   38: aload_0
    //   39: monitorexit
    //   40: aload_1
    //   41: athrow
    // Exception table:
    //   from	to	target	type
    //   2	30	37	finally
    //   30	34	37	finally
  }
  
  public final void onFailure(ai paramai, Throwable paramThrowable, ae paramae) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mWebSocket : Lokhttp3/ai;
    //   6: ifnull -> 16
    //   9: aload_0
    //   10: ldc 'Websocket exception'
    //   12: aload_2
    //   13: invokespecial abort : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   16: aload_0
    //   17: getfield mClosed : Z
    //   20: ifne -> 43
    //   23: aload_0
    //   24: getfield mConnectionCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$ConnectionCallback;
    //   27: ifnull -> 39
    //   30: aload_0
    //   31: getfield mConnectionCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$ConnectionCallback;
    //   34: invokeinterface onDisconnected : ()V
    //   39: aload_0
    //   40: invokespecial reconnect : ()V
    //   43: aload_0
    //   44: monitorexit
    //   45: return
    //   46: astore_1
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_1
    //   50: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	46	finally
    //   16	39	46	finally
    //   39	43	46	finally
  }
  
  public final void onMessage(ai paramai, i parami) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mMessageCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$MessageCallback;
    //   6: ifnull -> 19
    //   9: aload_0
    //   10: getfield mMessageCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$MessageCallback;
    //   13: aload_2
    //   14: invokeinterface onMessage : (Lg/i;)V
    //   19: aload_0
    //   20: monitorexit
    //   21: return
    //   22: astore_1
    //   23: aload_0
    //   24: monitorexit
    //   25: aload_1
    //   26: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	22	finally
  }
  
  public final void onMessage(ai paramai, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mMessageCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$MessageCallback;
    //   6: ifnull -> 19
    //   9: aload_0
    //   10: getfield mMessageCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$MessageCallback;
    //   13: aload_2
    //   14: invokeinterface onMessage : (Ljava/lang/String;)V
    //   19: aload_0
    //   20: monitorexit
    //   21: return
    //   22: astore_1
    //   23: aload_0
    //   24: monitorexit
    //   25: aload_1
    //   26: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	22	finally
  }
  
  public final void onOpen(ai paramai, ae paramae) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: putfield mWebSocket : Lokhttp3/ai;
    //   7: aload_0
    //   8: iconst_0
    //   9: putfield mSuppressConnectionErrors : Z
    //   12: aload_0
    //   13: getfield mConnectionCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$ConnectionCallback;
    //   16: ifnull -> 28
    //   19: aload_0
    //   20: getfield mConnectionCallback : Lcom/facebook/react/packagerconnection/ReconnectingWebSocket$ConnectionCallback;
    //   23: invokeinterface onConnected : ()V
    //   28: aload_0
    //   29: monitorexit
    //   30: return
    //   31: astore_1
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_1
    //   35: athrow
    // Exception table:
    //   from	to	target	type
    //   2	28	31	finally
  }
  
  public final void sendMessage(i parami) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mWebSocket : Lokhttp3/ai;
    //   6: ifnull -> 23
    //   9: aload_0
    //   10: getfield mWebSocket : Lokhttp3/ai;
    //   13: aload_1
    //   14: invokeinterface d : (Lg/i;)Z
    //   19: pop
    //   20: aload_0
    //   21: monitorexit
    //   22: return
    //   23: new java/nio/channels/ClosedChannelException
    //   26: dup
    //   27: invokespecial <init> : ()V
    //   30: athrow
    //   31: astore_1
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_1
    //   35: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	31	finally
    //   23	31	31	finally
  }
  
  public final void sendMessage(String paramString) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mWebSocket : Lokhttp3/ai;
    //   6: ifnull -> 23
    //   9: aload_0
    //   10: getfield mWebSocket : Lokhttp3/ai;
    //   13: aload_1
    //   14: invokeinterface b : (Ljava/lang/String;)Z
    //   19: pop
    //   20: aload_0
    //   21: monitorexit
    //   22: return
    //   23: new java/nio/channels/ClosedChannelException
    //   26: dup
    //   27: invokespecial <init> : ()V
    //   30: athrow
    //   31: astore_1
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_1
    //   35: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	31	finally
    //   23	31	31	finally
  }
  
  public static interface ConnectionCallback {
    void onConnected();
    
    void onDisconnected();
  }
  
  public static interface MessageCallback {
    void onMessage(i param1i);
    
    void onMessage(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\packagerconnection\ReconnectingWebSocket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */