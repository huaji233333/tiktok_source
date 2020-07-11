package com.tt.miniapp.websocket.task.base;

import android.content.Context;
import android.util.Log;
import com.a;
import com.tt.miniapp.websocket.common.IWebSocketTask;
import com.tt.miniapp.websocket.common.WSRequest;
import com.tt.miniapp.websocket.common.WsStatusListener;
import com.tt.miniapphost.AppBrandLogger;
import g.i;
import java.util.Arrays;
import java.util.Locale;

public abstract class BaseWebSocketTask implements IWebSocketTask {
  public String TAG;
  
  protected Context mContext;
  
  private int mCurrentStatus = -1;
  
  private String mLogUrl;
  
  private volatile WsStatusListener mStatusListener;
  
  private volatile String mTransportProtocol = "unknown";
  
  protected WSRequest mWSRequest;
  
  public BaseWebSocketTask(Context paramContext, WSRequest paramWSRequest) {
    this.mContext = paramContext;
    this.mWSRequest = paramWSRequest;
    StringBuilder stringBuilder = new StringBuilder("【");
    stringBuilder.append(hashCode());
    stringBuilder.append("(");
    stringBuilder.append(this.mWSRequest.url);
    stringBuilder.append(")】");
    this.mLogUrl = stringBuilder.toString();
  }
  
  private void setCurrentStatus(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iload_1
    //   4: putfield mCurrentStatus : I
    //   7: aload_0
    //   8: monitorexit
    //   9: return
    //   10: astore_2
    //   11: aload_0
    //   12: monitorexit
    //   13: aload_2
    //   14: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	10	finally
  }
  
  protected int getCurrentStatus() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCurrentStatus : I
    //   6: istore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: iload_1
    //   10: ireturn
    //   11: astore_2
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_2
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	11	finally
  }
  
  public final String getTransportProtocol() {
    return this.mTransportProtocol;
  }
  
  public final void onConnected(String paramString1, String paramString2) {
    String str1 = this.TAG;
    String str2 = this.mLogUrl;
    StringBuilder stringBuilder1 = new StringBuilder("\n");
    stringBuilder1.append(paramString1);
    String str3 = stringBuilder1.toString();
    StringBuilder stringBuilder2 = new StringBuilder("\nTransportProtocol：");
    stringBuilder2.append(paramString2);
    AppBrandLogger.d(str1, new Object[] { "onOpen:", str2, str3, stringBuilder2.toString() });
    setCurrentStatus(1);
    this.mTransportProtocol = paramString2;
    WsStatusListener wsStatusListener = this.mStatusListener;
    if (wsStatusListener == null)
      return; 
    wsStatusListener.onOpen(paramString1);
  }
  
  protected final void onConnecting() {
    AppBrandLogger.d(this.TAG, new Object[] { "onConnecting... ", this.mLogUrl });
  }
  
  public final void onDisconnected(int paramInt, String paramString) {
    AppBrandLogger.d(this.TAG, new Object[] { a.a(Locale.CHINA, "onClosed:『%d：%s』", new Object[] { Integer.valueOf(paramInt), paramString }), this.mLogUrl });
    setCurrentStatus(-1);
    WsStatusListener wsStatusListener = this.mStatusListener;
    if (wsStatusListener == null)
      return; 
    wsStatusListener.onClosed(paramInt, paramString);
  }
  
  public final void onDisconnecting(int paramInt, String paramString) {
    AppBrandLogger.d(this.TAG, new Object[] { a.a(Locale.CHINA, "onClosing:『%d：%s』", new Object[] { Integer.valueOf(paramInt), paramString }), this.mLogUrl });
    WsStatusListener wsStatusListener = this.mStatusListener;
    if (wsStatusListener == null)
      return; 
    wsStatusListener.onClosing(paramInt, paramString);
  }
  
  public final void onFailed(Throwable paramThrowable) {
    String str1;
    String str2 = this.TAG;
    String str3 = this.mLogUrl;
    if (paramThrowable != null) {
      str1 = Log.getStackTraceString(paramThrowable);
    } else {
      str1 = "";
    } 
    AppBrandLogger.d(str2, new Object[] { "onFailure:", str3, str1 });
    WsStatusListener wsStatusListener = this.mStatusListener;
    if (wsStatusListener == null)
      return; 
    wsStatusListener.onFailure(paramThrowable);
  }
  
  public final void onReceivedMessage(String paramString) {
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder("onMessage text:『");
    stringBuilder.append(paramString);
    stringBuilder.append("』");
    stringBuilder.append(this.mLogUrl);
    AppBrandLogger.d(str, new Object[] { stringBuilder.toString() });
    WsStatusListener wsStatusListener = this.mStatusListener;
    if (wsStatusListener == null)
      return; 
    wsStatusListener.onMessage(paramString);
  }
  
  public final void onReceivedMessage(byte[] paramArrayOfbyte) {
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder("onMessage byte:『");
    stringBuilder.append(Arrays.toString(paramArrayOfbyte));
    stringBuilder.append("』");
    stringBuilder.append(this.mLogUrl);
    AppBrandLogger.d(str, new Object[] { stringBuilder.toString() });
    WsStatusListener wsStatusListener = this.mStatusListener;
    if (wsStatusListener == null)
      return; 
    wsStatusListener.onMessage(paramArrayOfbyte);
  }
  
  public boolean sendMessage(i parami) {
    if (!isWsConnected())
      return false; 
    if (parami == null)
      return false; 
    AppBrandLogger.d(this.TAG, new Object[] { "send byte msg:『", parami.base64(), "』", this.mLogUrl });
    return true;
  }
  
  public boolean sendMessage(String paramString) {
    if (!isWsConnected())
      return false; 
    if (paramString == null)
      return false; 
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder("send text msg: 『");
    stringBuilder.append(paramString);
    stringBuilder.append("』");
    stringBuilder.append(this.mLogUrl);
    AppBrandLogger.d(str, new Object[] { stringBuilder.toString() });
    return true;
  }
  
  public final void setStatusListener(WsStatusListener paramWsStatusListener) {
    this.mStatusListener = paramWsStatusListener;
  }
  
  public final void startConnect() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mContext : Landroid/content/Context;
    //   4: invokestatic isConnect : (Landroid/content/Context;)Z
    //   7: ifne -> 34
    //   10: aload_0
    //   11: getfield TAG : Ljava/lang/String;
    //   14: iconst_2
    //   15: anewarray java/lang/Object
    //   18: dup
    //   19: iconst_0
    //   20: ldc 'network not connected'
    //   22: aastore
    //   23: dup
    //   24: iconst_1
    //   25: aload_0
    //   26: getfield mLogUrl : Ljava/lang/String;
    //   29: aastore
    //   30: invokestatic w : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   33: return
    //   34: aload_0
    //   35: invokevirtual isWsConnected : ()Z
    //   38: ifeq -> 65
    //   41: aload_0
    //   42: getfield TAG : Ljava/lang/String;
    //   45: iconst_2
    //   46: anewarray java/lang/Object
    //   49: dup
    //   50: iconst_0
    //   51: ldc 'already connected'
    //   53: aastore
    //   54: dup
    //   55: iconst_1
    //   56: aload_0
    //   57: getfield mLogUrl : Ljava/lang/String;
    //   60: aastore
    //   61: invokestatic w : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   64: return
    //   65: aload_0
    //   66: invokevirtual getCurrentStatus : ()I
    //   69: ifne -> 96
    //   72: aload_0
    //   73: getfield TAG : Ljava/lang/String;
    //   76: iconst_2
    //   77: anewarray java/lang/Object
    //   80: dup
    //   81: iconst_0
    //   82: ldc 'connecting now'
    //   84: aastore
    //   85: dup
    //   86: iconst_1
    //   87: aload_0
    //   88: getfield mLogUrl : Ljava/lang/String;
    //   91: aastore
    //   92: invokestatic w : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   95: return
    //   96: aload_0
    //   97: monitorenter
    //   98: aload_0
    //   99: invokevirtual getCurrentStatus : ()I
    //   102: ifne -> 131
    //   105: aload_0
    //   106: getfield TAG : Ljava/lang/String;
    //   109: iconst_2
    //   110: anewarray java/lang/Object
    //   113: dup
    //   114: iconst_0
    //   115: ldc 'connecting now'
    //   117: aastore
    //   118: dup
    //   119: iconst_1
    //   120: aload_0
    //   121: getfield mLogUrl : Ljava/lang/String;
    //   124: aastore
    //   125: invokestatic w : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   128: aload_0
    //   129: monitorexit
    //   130: return
    //   131: aload_0
    //   132: iconst_0
    //   133: invokespecial setCurrentStatus : (I)V
    //   136: aload_0
    //   137: monitorexit
    //   138: aload_0
    //   139: getfield TAG : Ljava/lang/String;
    //   142: iconst_2
    //   143: anewarray java/lang/Object
    //   146: dup
    //   147: iconst_0
    //   148: ldc 'startConnect:'
    //   150: aastore
    //   151: dup
    //   152: iconst_1
    //   153: aload_0
    //   154: getfield mLogUrl : Ljava/lang/String;
    //   157: aastore
    //   158: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   161: aload_0
    //   162: invokevirtual startConnectReal : ()V
    //   165: return
    //   166: astore_1
    //   167: aload_0
    //   168: monitorexit
    //   169: aload_1
    //   170: athrow
    // Exception table:
    //   from	to	target	type
    //   98	130	166	finally
    //   131	138	166	finally
    //   167	169	166	finally
  }
  
  protected abstract void startConnectReal();
  
  public final boolean stopConnect(int paramInt, String paramString) {
    String str = this.TAG;
    StringBuilder stringBuilder = new StringBuilder("stopConnect:『");
    stringBuilder.append(paramInt);
    stringBuilder.append("：");
    stringBuilder.append(paramString);
    stringBuilder.append("』");
    stringBuilder.append(this.mLogUrl);
    AppBrandLogger.d(str, new Object[] { stringBuilder.toString() });
    if (getCurrentStatus() != -1) {
      stopConnectReal(paramInt, paramString);
      setCurrentStatus(-1);
      return true;
    } 
    return false;
  }
  
  protected abstract void stopConnectReal(int paramInt, String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\task\base\BaseWebSocketTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */