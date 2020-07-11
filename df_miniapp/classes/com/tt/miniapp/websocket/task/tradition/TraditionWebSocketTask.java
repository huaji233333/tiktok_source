package com.tt.miniapp.websocket.task.tradition;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.RequestInceptUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.websocket.common.IWebSocketTask;
import com.tt.miniapp.websocket.common.WSRequest;
import com.tt.miniapp.websocket.task.base.BaseWebSocketTask;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.q.d;
import g.i;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.ai;
import okhttp3.aj;
import okhttp3.y;
import org.json.JSONArray;
import org.json.JSONObject;

public class TraditionWebSocketTask extends BaseWebSocketTask implements IWebSocketTask {
  private Lock mLock;
  
  private y mOkHttpClient;
  
  private ac mRequest;
  
  public ai mWebSocket;
  
  private aj mWebSocketListener;
  
  public TraditionWebSocketTask(Context paramContext, WSRequest paramWSRequest) {
    super(paramContext, paramWSRequest);
    this.TAG = "_Socket_Task.tradition";
    this.mLock = new ReentrantLock();
  }
  
  private ac createRequest() {
    ac.a a = new ac.a();
    RequestInceptUtil.inceptRequest(a);
    a.a(this.mWSRequest.url);
    try {
      if (this.mWSRequest != null) {
        setupHeader(a);
        interceptHeader(a);
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("_Socket_Task.tradition", new Object[] { exception });
    } 
    return a.c();
  }
  
  private void initClientAndListener() {
    long l;
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      l = (appConfig.getNetworkTimeout()).connectSocket;
    } else {
      l = 60000L;
    } 
    this.mOkHttpClient = (new y.a()).b(true).b(l, TimeUnit.MILLISECONDS).c(l, TimeUnit.MILLISECONDS).a(l, TimeUnit.MILLISECONDS).a();
    this.mWebSocketListener = new aj() {
        public void onClosed(ai param1ai, int param1Int, String param1String) {
          TraditionWebSocketTask.this.onDisconnected(param1Int, param1String);
        }
        
        public void onClosing(ai param1ai, int param1Int, String param1String) {
          TraditionWebSocketTask.this.onDisconnecting(param1Int, param1String);
        }
        
        public void onFailure(ai param1ai, Throwable param1Throwable, ae param1ae) {
          TraditionWebSocketTask.this.onFailed(param1Throwable);
        }
        
        public void onMessage(ai param1ai, i param1i) {
          byte[] arrayOfByte;
          TraditionWebSocketTask traditionWebSocketTask = TraditionWebSocketTask.this;
          if (param1i != null) {
            arrayOfByte = param1i.toByteArray();
          } else {
            arrayOfByte = new byte[0];
          } 
          traditionWebSocketTask.onReceivedMessage(arrayOfByte);
        }
        
        public void onMessage(ai param1ai, String param1String) {
          TraditionWebSocketTask traditionWebSocketTask = TraditionWebSocketTask.this;
          String str = param1String;
          if (param1String == null)
            str = CharacterUtils.empty(); 
          traditionWebSocketTask.onReceivedMessage(str);
        }
        
        public void onOpen(ai param1ai, ae param1ae) {
          synchronized (TraditionWebSocketTask.this) {
            String str;
            TraditionWebSocketTask.this.mWebSocket = param1ai;
            null = null;
            TraditionWebSocketTask traditionWebSocketTask = null;
            if (param1ae != null) {
              traditionWebSocketTask = null;
              if (param1ae.f != null)
                str = param1ae.f.toString(); 
            } 
            TraditionWebSocketTask.this.onConnected(str, "tcp");
            return;
          } 
        }
      };
  }
  
  private void interceptHeader(ac.a parama) {
    boolean bool = d.b();
    if (bool)
      parama.b("User-Agent"); 
    parama.b("User-Agent", ToolUtils.getCustomUA());
    if (bool)
      parama.b("Referer"); 
    parama.b("Referer", RequestInceptUtil.getRequestReferer());
  }
  
  private void setupHeader(ac.a parama) throws Exception {
    JSONArray jSONArray;
    WSRequest<String> wSRequest = this.mWSRequest;
    WSRequest wSRequest1 = null;
    if (wSRequest != null && this.mWSRequest.header != null) {
      Iterator iterator = this.mWSRequest.header.keys();
    } else {
      wSRequest = null;
    } 
    if (wSRequest != null) {
      JSONObject jSONObject = this.mWSRequest.header;
      while (wSRequest.hasNext()) {
        String str = wSRequest.next();
        parama.b(str, jSONObject.optString(str));
      } 
    } 
    wSRequest = wSRequest1;
    if (this.mWSRequest != null)
      jSONArray = this.mWSRequest.protocols; 
    if (jSONArray != null) {
      int j = this.mWSRequest.protocols.length();
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < j; i++) {
        stringBuilder.append(this.mWSRequest.protocols.getString(i));
        if (i != j - 1)
          stringBuilder.append(","); 
      } 
      if (!TextUtils.isEmpty(stringBuilder.toString()))
        parama.b("Sec-WebSocket-Protocol", stringBuilder.toString()); 
    } 
  }
  
  public String getSocketType() {
    return "tradition";
  }
  
  public boolean isWsConnected() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mWebSocket : Lokhttp3/ai;
    //   6: ifnull -> 23
    //   9: aload_0
    //   10: invokevirtual getCurrentStatus : ()I
    //   13: istore_1
    //   14: iload_1
    //   15: iconst_1
    //   16: if_icmpne -> 23
    //   19: aload_0
    //   20: monitorexit
    //   21: iconst_1
    //   22: ireturn
    //   23: aload_0
    //   24: monitorexit
    //   25: iconst_0
    //   26: ireturn
    //   27: astore_2
    //   28: aload_0
    //   29: monitorexit
    //   30: aload_2
    //   31: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	27	finally
  }
  
  public boolean sendMessage(i parami) {
    return !super.sendMessage(parami) ? false : this.mWebSocket.d(parami);
  }
  
  public boolean sendMessage(String paramString) {
    return !super.sendMessage(paramString) ? false : this.mWebSocket.b(paramString);
  }
  
  public void startConnectReal() {
    if (this.mOkHttpClient == null)
      initClientAndListener(); 
    if (this.mRequest == null)
      this.mRequest = createRequest(); 
    this.mOkHttpClient.c.b();
    try {
      this.mLock.lockInterruptibly();
      try {
        this.mOkHttpClient.a(this.mRequest, this.mWebSocketListener);
        return;
      } finally {
        this.mLock.unlock();
      } 
    } catch (InterruptedException interruptedException) {
      AppBrandLogger.e("_Socket_Task.tradition", new Object[] { interruptedException });
      return;
    } 
  }
  
  public void stopConnectReal(int paramInt, String paramString) {
    y y1 = this.mOkHttpClient;
    if (y1 != null)
      y1.c.b(); 
    ai ai1 = this.mWebSocket;
    if (ai1 != null && !ai1.b(paramInt, paramString))
      onDisconnected(1001, "abnormal close"); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\task\tradition\TraditionWebSocketTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */