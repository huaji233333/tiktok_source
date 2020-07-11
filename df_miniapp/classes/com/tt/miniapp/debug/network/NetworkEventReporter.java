package com.tt.miniapp.debug.network;

import java.io.IOException;
import java.io.InputStream;

public interface NetworkEventReporter {
  void dataReceived(String paramString, int paramInt1, int paramInt2);
  
  void dataSent(String paramString, int paramInt1, int paramInt2);
  
  void httpExchangeFailed(String paramString1, String paramString2);
  
  InputStream interpretResponseStream(String paramString1, String paramString2, String paramString3, InputStream paramInputStream, ResponseHandler paramResponseHandler);
  
  boolean isEnabled();
  
  String nextRequestId();
  
  void requestWillBeSent(InspectorRequest paramInspectorRequest);
  
  void responseHeadersReceived(InspectorResponse paramInspectorResponse);
  
  void responseReadFailed(String paramString1, String paramString2);
  
  void responseReadFinished(String paramString);
  
  void webSocketClosed(String paramString);
  
  void webSocketCreated(String paramString1, String paramString2);
  
  void webSocketFrameError(String paramString1, String paramString2);
  
  void webSocketFrameReceived(InspectorWebSocketFrame paramInspectorWebSocketFrame);
  
  void webSocketFrameSent(InspectorWebSocketFrame paramInspectorWebSocketFrame);
  
  void webSocketHandshakeResponseReceived(InspectorWebSocketResponse paramInspectorWebSocketResponse);
  
  void webSocketWillSendHandshakeRequest(InspectorWebSocketRequest paramInspectorWebSocketRequest);
  
  public static interface InspectorHeaders {
    String firstHeaderValue(String param1String);
    
    int headerCount();
    
    String headerName(int param1Int);
    
    String headerValue(int param1Int);
  }
  
  public static interface InspectorRequest extends InspectorRequestCommon {
    byte[] body() throws IOException;
    
    Integer friendlyNameExtra();
    
    String method();
    
    String url();
  }
  
  public static interface InspectorRequestCommon extends InspectorHeaders {
    String friendlyName();
    
    String id();
  }
  
  public static interface InspectorResponse extends InspectorResponseCommon {
    int connectionId();
    
    boolean connectionReused();
    
    boolean fromDiskCache();
    
    String url();
  }
  
  public static interface InspectorResponseCommon extends InspectorHeaders {
    String reasonPhrase();
    
    String requestId();
    
    int statusCode();
  }
  
  public static interface InspectorWebSocketFrame {
    boolean mask();
    
    int opcode();
    
    String payloadData();
    
    String requestId();
  }
  
  public static interface InspectorWebSocketRequest extends InspectorRequestCommon {}
  
  public static interface InspectorWebSocketResponse extends InspectorResponseCommon {
    NetworkEventReporter.InspectorHeaders requestHeaders();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\NetworkEventReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */