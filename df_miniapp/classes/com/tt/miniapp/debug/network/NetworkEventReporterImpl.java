package com.tt.miniapp.debug.network;

import android.content.Context;
import android.os.SystemClock;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapphost.AppbrandContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkEventReporterImpl implements NetworkEventReporter {
  private static NetworkEventReporter sInstance;
  
  private final AtomicInteger mNextRequestId = new AtomicInteger(0);
  
  private ResourceTypeHelper mResourceTypeHelper;
  
  private ResponseBodyFileManager responseBodyFileManager;
  
  private static Network.WebSocketFrame convertFrame(NetworkEventReporter.InspectorWebSocketFrame paramInspectorWebSocketFrame) {
    Network.WebSocketFrame webSocketFrame = new Network.WebSocketFrame();
    webSocketFrame.opcode = paramInspectorWebSocketFrame.opcode();
    webSocketFrame.mask = paramInspectorWebSocketFrame.mask();
    webSocketFrame.payloadData = paramInspectorWebSocketFrame.payloadData();
    return webSocketFrame;
  }
  
  private static ResourceTypeHelper.ResourceType determineResourceType(String paramString, ResourceTypeHelper paramResourceTypeHelper) {
    return (paramString != null) ? paramResourceTypeHelper.determineResourceType(paramString) : ResourceTypeHelper.ResourceType.OTHER;
  }
  
  private static JSONObject formatHeadersAsJSON(NetworkEventReporter.InspectorHeaders paramInspectorHeaders) {
    JSONObject jSONObject = new JSONObject();
    int i = 0;
    while (i < paramInspectorHeaders.headerCount()) {
      String str1 = paramInspectorHeaders.headerName(i);
      String str2 = paramInspectorHeaders.headerValue(i);
      try {
        if (jSONObject.has(str1)) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(jSONObject.getString(str1));
          stringBuilder.append("\n");
          stringBuilder.append(str2);
          jSONObject.put(str1, stringBuilder.toString());
        } else {
          jSONObject.put(str1, str2);
        } 
        i++;
      } catch (JSONException jSONException) {
        throw new RuntimeException(jSONException);
      } 
    } 
    return jSONObject;
  }
  
  public static NetworkEventReporter get() {
    // Byte code:
    //   0: ldc com/tt/miniapp/debug/network/NetworkEventReporterImpl
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/debug/network/NetworkEventReporterImpl.sInstance : Lcom/tt/miniapp/debug/network/NetworkEventReporter;
    //   6: ifnonnull -> 19
    //   9: new com/tt/miniapp/debug/network/NetworkEventReporterImpl
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: putstatic com/tt/miniapp/debug/network/NetworkEventReporterImpl.sInstance : Lcom/tt/miniapp/debug/network/NetworkEventReporter;
    //   19: getstatic com/tt/miniapp/debug/network/NetworkEventReporterImpl.sInstance : Lcom/tt/miniapp/debug/network/NetworkEventReporter;
    //   22: astore_0
    //   23: ldc com/tt/miniapp/debug/network/NetworkEventReporterImpl
    //   25: monitorexit
    //   26: aload_0
    //   27: areturn
    //   28: astore_0
    //   29: ldc com/tt/miniapp/debug/network/NetworkEventReporterImpl
    //   31: monitorexit
    //   32: aload_0
    //   33: athrow
    // Exception table:
    //   from	to	target	type
    //   3	19	28	finally
    //   19	23	28	finally
  }
  
  private String getContentType(NetworkEventReporter.InspectorHeaders paramInspectorHeaders) {
    return paramInspectorHeaders.firstHeaderValue("Content-Type");
  }
  
  private ResourceTypeHelper getResourceTypeHelper() {
    if (this.mResourceTypeHelper == null)
      this.mResourceTypeHelper = new ResourceTypeHelper(); 
    return this.mResourceTypeHelper;
  }
  
  private ResponseBodyFileManager getResponseBodyFileManager() {
    if (this.responseBodyFileManager == null)
      this.responseBodyFileManager = new ResponseBodyFileManager((Context)AppbrandContext.getInst().getApplicationContext()); 
    return this.responseBodyFileManager;
  }
  
  private void loadingFailed(String paramString1, String paramString2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.loadingFailed");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramString1);
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("timestamp", d);
      jSONObject1.put("errorText", paramString2);
      jSONObject1.put("type", "Other");
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
  
  private void loadingFinished(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.loadingFinished");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramString);
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("timestamp", d);
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
  
  private static String readBodyAsString(NetworkEventReporter.InspectorRequest paramInspectorRequest) {
    try {
      byte[] arrayOfByte = paramInspectorRequest.body();
      if (arrayOfByte != null)
        return new String(arrayOfByte, Charset.forName("UTF-8")); 
    } catch (IOException|OutOfMemoryError iOException) {}
    return null;
  }
  
  private static long stethoNow() {
    return SystemClock.elapsedRealtime();
  }
  
  public void dataReceived(String paramString, int paramInt1, int paramInt2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.dataReceived");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramString);
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("timestamp", d);
      jSONObject1.put("dataLength", paramInt1);
      jSONObject1.put("encodedDataLength", "encodedDataLength");
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
  
  public void dataSent(String paramString, int paramInt1, int paramInt2) {
    dataReceived(paramString, paramInt1, paramInt2);
  }
  
  public void getResponseBody(JSONObject paramJSONObject) {
    try {
      String str = paramJSONObject.getString("requestId");
      Network.GetResponseBodyResponse getResponseBodyResponse = new Network.GetResponseBodyResponse();
      ResponseBodyFileManager.ResponseBodyData responseBodyData = getResponseBodyFileManager().readFile(str);
      getResponseBodyResponse.body = responseBodyData.data;
      getResponseBodyResponse.base64Encoded = responseBodyData.base64Encoded;
      return;
    } catch (JSONException|IOException jSONException) {
      return;
    } 
  }
  
  public void httpExchangeFailed(String paramString1, String paramString2) {
    loadingFailed(paramString1, paramString2);
  }
  
  public InputStream interpretResponseStream(String paramString1, String paramString2, String paramString3, InputStream paramInputStream, ResponseHandler paramResponseHandler) {
    ResourceTypeHelper.ResourceType resourceType = null;
    if (paramInputStream == null) {
      paramResponseHandler.onEOF();
      return null;
    } 
    if (paramString2 != null)
      resourceType = getResourceTypeHelper().determineResourceType(paramString2); 
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (resourceType != null) {
      bool1 = bool2;
      if (resourceType == ResourceTypeHelper.ResourceType.IMAGE)
        bool1 = true; 
    } 
    try {
      return DecompressionHelper.teeInputWithDecompression(paramString1, paramInputStream, getResponseBodyFileManager().openResponseBodyFile(paramString1, bool1), paramString3, paramResponseHandler);
    } catch (IOException iOException) {
      return paramInputStream;
    } 
  }
  
  public boolean isEnabled() {
    return (DebugManager.getInst()).mRemoteDebugEnable;
  }
  
  public String nextRequestId() {
    return String.valueOf(this.mNextRequestId.getAndIncrement());
  }
  
  public void requestWillBeSent(NetworkEventReporter.InspectorRequest paramInspectorRequest) {
    Network.Request request = new Network.Request();
    request.url = paramInspectorRequest.url();
    request.method = paramInspectorRequest.method();
    request.headers = formatHeadersAsJSON(paramInspectorRequest);
    request.postData = readBodyAsString(paramInspectorRequest);
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.requestWillBeSent");
      JSONObject jSONObject2 = new JSONObject();
      jSONObject2.put("requestId", paramInspectorRequest.id());
      jSONObject2.put("frameId", "1");
      jSONObject2.put("loaderId", "1");
      jSONObject2.put("documentURL", paramInspectorRequest.url());
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("url", request.url);
      jSONObject1.put("method", request.method);
      jSONObject1.put("headers", request.headers);
      if (request.postData != null && request.postData.length() < 2097152)
        jSONObject1.put("postData", request.postData); 
      jSONObject2.put("request", jSONObject1);
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject2.put("timestamp", d);
      jSONObject1 = new JSONObject();
      jSONObject1.put("type", Network.InitiatorType.SCRIPT);
      jSONObject2.put("initiator", jSONObject1);
      jSONObject2.put("type", "Other");
      jSONObject2.put("redirectResponse", null);
      jSONObject.put("params", jSONObject2);
      DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
  
  public void responseHeadersReceived(NetworkEventReporter.InspectorResponse paramInspectorResponse) {
    String str1;
    Network.Response response = new Network.Response();
    response.url = paramInspectorResponse.url();
    response.status = paramInspectorResponse.statusCode();
    response.statusText = paramInspectorResponse.reasonPhrase();
    response.headers = formatHeadersAsJSON(paramInspectorResponse);
    String str2 = getContentType(paramInspectorResponse);
    if (str2 != null) {
      str1 = getResourceTypeHelper().stripContentExtras(str2);
    } else {
      str1 = "application/octet-stream";
    } 
    response.mimeType = str1;
    response.connectionReused = paramInspectorResponse.connectionReused();
    response.connectionId = paramInspectorResponse.connectionId();
    response.fromDiskCache = Boolean.valueOf(paramInspectorResponse.fromDiskCache());
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.responseReceived");
      JSONObject jSONObject2 = new JSONObject();
      jSONObject2.put("requestId", paramInspectorResponse.requestId());
      jSONObject2.put("frameId", "1");
      jSONObject2.put("loaderId", "1");
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject2.put("timestamp", d);
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("url", response.url);
      jSONObject1.put("status", response.status);
      jSONObject1.put("statusText", response.statusText);
      jSONObject1.put("headers", response.headers);
      jSONObject1.put("mimeType", response.mimeType);
      jSONObject1.put("connectionReused", response.connectionReused);
      jSONObject1.put("connectionId", response.connectionId);
      jSONObject1.put("fromDiskCache", response.fromDiskCache);
      jSONObject2.put("response", jSONObject1);
      jSONObject2.put("type", determineResourceType(str2, getResourceTypeHelper()));
      jSONObject.put("params", jSONObject2);
      DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
  
  public void responseReadFailed(String paramString1, String paramString2) {
    loadingFailed(paramString1, paramString2);
  }
  
  public void responseReadFinished(String paramString) {
    loadingFinished(paramString);
  }
  
  public void webSocketClosed(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.webSocketClosed");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramString);
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("timestamp", d);
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
  
  public void webSocketCreated(String paramString1, String paramString2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.webSocketCreated");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramString1);
      jSONObject1.put("url", paramString2);
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
  
  public void webSocketFrameError(String paramString1, String paramString2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.webSocketFrameError");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramString1);
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("timestamp", d);
      jSONObject1.put("errorMessage", paramString2);
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
  
  public void webSocketFrameReceived(NetworkEventReporter.InspectorWebSocketFrame paramInspectorWebSocketFrame) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.webSocketFrameReceived");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramInspectorWebSocketFrame.requestId());
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("timestamp", d);
      jSONObject1.put("response", convertFrame(paramInspectorWebSocketFrame));
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
  
  public void webSocketFrameSent(NetworkEventReporter.InspectorWebSocketFrame paramInspectorWebSocketFrame) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.webSocketFrameSent");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramInspectorWebSocketFrame.requestId());
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("timestamp", d);
      jSONObject1.put("response", convertFrame(paramInspectorWebSocketFrame));
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
  
  public void webSocketHandshakeResponseReceived(NetworkEventReporter.InspectorWebSocketResponse paramInspectorWebSocketResponse) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.webSocketHandshakeResponseReceived");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramInspectorWebSocketResponse.requestId());
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("timestamp", d);
      Network.WebSocketResponse webSocketResponse = new Network.WebSocketResponse();
      webSocketResponse.headers = formatHeadersAsJSON(paramInspectorWebSocketResponse);
      webSocketResponse.headersText = null;
      if (paramInspectorWebSocketResponse.requestHeaders() != null) {
        webSocketResponse.requestHeaders = formatHeadersAsJSON(paramInspectorWebSocketResponse.requestHeaders());
        webSocketResponse.requestHeadersText = null;
      } 
      webSocketResponse.status = paramInspectorWebSocketResponse.statusCode();
      webSocketResponse.statusText = paramInspectorWebSocketResponse.reasonPhrase();
      jSONObject1.put("response", webSocketResponse);
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
  
  public void webSocketWillSendHandshakeRequest(NetworkEventReporter.InspectorWebSocketRequest paramInspectorWebSocketRequest) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "Network.webSocketWillSendHandshakeRequest");
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("requestId", paramInspectorWebSocketRequest.id());
      long l = stethoNow();
      double d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("timestamp", d);
      l = System.currentTimeMillis();
      d = l;
      Double.isNaN(d);
      d /= 1000.0D;
      jSONObject1.put("wallTime", d);
      Network.WebSocketRequest webSocketRequest = new Network.WebSocketRequest();
      webSocketRequest.headers = formatHeadersAsJSON(paramInspectorWebSocketRequest);
      jSONObject1.put("request", webSocketRequest);
      jSONObject.put("params", jSONObject1);
    } catch (JSONException jSONException) {}
    DebugManager.getInst().sendMessageByRemoteWs(jSONObject.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\NetworkEventReporterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */