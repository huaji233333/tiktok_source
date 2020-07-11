package com.tt.miniapp.websocket.task.ttnet;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.RequestInceptUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.websocket.common.WSRequest;
import com.tt.miniapp.websocket.task.base.BaseWebSocketTask;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.q.d;
import com.tt.option.q.k;
import g.i;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import okhttp3.s;
import org.json.JSONObject;

public class TTNetWebSocketTask extends BaseWebSocketTask implements k.a {
  private volatile int mCloseCode;
  
  private volatile String mCloseReason;
  
  private k mTmaWsClient;
  
  private TTNetWebSocketTask(Context paramContext, WSRequest paramWSRequest) {
    super(paramContext, paramWSRequest);
    this.TAG = "_Socket_Task.ttnet";
    this.mCloseCode = 1000;
    this.mCloseReason = "normal close";
  }
  
  private String buildHeaders(JSONObject paramJSONObject) {
    String str = paramJSONObject.optString("__MP_RESP_HEADER");
    if (TextUtils.isEmpty(str))
      return CharacterUtils.empty(); 
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    JSONObject jSONObject = (new JsonBuilder(str)).build();
    Iterator<String> iterator = jSONObject.keys();
    while (iterator.hasNext()) {
      String str1 = iterator.next();
      if (str1 != null) {
        String str2 = jSONObject.optString(str1);
        if (str2 != null)
          hashMap.put(str1.concat("\000"), str2.concat("\000")); 
      } 
    } 
    return s.a(hashMap).toString();
  }
  
  private void interceptHeader(HashMap<String, String> paramHashMap) {
    for (String str1 : paramHashMap.keySet()) {
      if (str1.equalsIgnoreCase("User-Agent"))
        paramHashMap.remove(str1); 
      if (str1.equalsIgnoreCase("referer") && d.b())
        paramHashMap.remove(str1); 
    } 
    String str = ToolUtils.getCustomUA();
    AppBrandLogger.d("_Socket_Task.ttnet", new Object[] { "custom UA = ", str });
    paramHashMap.put("User-Agent", str);
    paramHashMap.put("referer", RequestInceptUtil.getRequestReferer());
  }
  
  private void setupHeader(HashMap<String, String> paramHashMap) {
    if (this.mWSRequest.header != null) {
      Iterator<String> iterator = this.mWSRequest.header.keys();
      if (iterator != null)
        while (iterator.hasNext()) {
          String str = iterator.next();
          if (!TextUtils.isEmpty(str))
            paramHashMap.put(str, String.valueOf(this.mWSRequest.header.opt(str))); 
        }  
    } 
    if (this.mWSRequest.protocols != null) {
      int j = this.mWSRequest.protocols.length();
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < j; i++) {
        stringBuilder.append(this.mWSRequest.protocols.optString(i));
        if (i != j - 1)
          stringBuilder.append(","); 
      } 
      if (!TextUtils.isEmpty(stringBuilder.toString()))
        paramHashMap.put("Sec-WebSocket-Protocol", stringBuilder.toString()); 
    } 
  }
  
  public static TTNetWebSocketTask tryNewInst(Context paramContext, WSRequest paramWSRequest) {
    TTNetWebSocketTask tTNetWebSocketTask = new TTNetWebSocketTask(paramContext, paramWSRequest);
    k k1 = HostDependManager.getInst().createWsClient(tTNetWebSocketTask);
    if (k1 == null)
      return null; 
    tTNetWebSocketTask.mTmaWsClient = k1;
    return tTNetWebSocketTask;
  }
  
  public String getSocketType() {
    return "ttnet";
  }
  
  public boolean isWsConnected() {
    return (this.mTmaWsClient.a() && getCurrentStatus() == 1);
  }
  
  public void onConnStateChange(int paramInt, String paramString1, String paramString2) {
    if (paramInt != 1) {
      if (paramInt != 2) {
        if (paramInt != 3) {
          if (paramInt != 4) {
            if (paramInt != 5)
              return; 
            onDisconnecting(this.mCloseCode, this.mCloseReason);
            return;
          } 
          JSONObject jSONObject = (new JsonBuilder(paramString2)).build();
          onConnected(buildHeaders(jSONObject), jSONObject.optString("__MP_TRANSPORT_PROTOCOL", "unknown"));
          return;
        } 
        onDisconnected(this.mCloseCode, this.mCloseReason);
        return;
      } 
      onFailed(new Throwable(paramString2));
      return;
    } 
    onConnecting();
  }
  
  public void onMessage(byte[] paramArrayOfbyte, int paramInt) {
    String str1;
    byte[] arrayOfByte;
    if (1 == paramInt) {
      if (paramArrayOfbyte == null) {
        str1 = CharacterUtils.empty();
      } else {
        str1 = new String((byte[])str1, StandardCharsets.UTF_8);
      } 
      onReceivedMessage(str1);
      return;
    } 
    String str2 = str1;
    if (str1 == null)
      arrayOfByte = new byte[0]; 
    onReceivedMessage(arrayOfByte);
  }
  
  public boolean sendMessage(i parami) {
    return !super.sendMessage(parami) ? false : this.mTmaWsClient.a(parami.toByteArray(), 2);
  }
  
  public boolean sendMessage(String paramString) {
    return !super.sendMessage(paramString) ? false : this.mTmaWsClient.a(paramString.getBytes(StandardCharsets.UTF_8), 1);
  }
  
  public void startConnectReal() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    setupHeader((HashMap)hashMap);
    interceptHeader((HashMap)hashMap);
    List<String> list = Collections.singletonList(this.mWSRequest.url);
    this.mTmaWsClient.a(new HashMap<Object, Object>(), hashMap, list, false, false);
  }
  
  public void stopConnectReal(int paramInt, String paramString) {
    this.mCloseCode = paramInt;
    this.mCloseReason = paramString;
    try {
      return;
    } finally {
      Exception exception = null;
      onDisconnected(paramInt, paramString);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\task\ttnet\TTNetWebSocketTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */