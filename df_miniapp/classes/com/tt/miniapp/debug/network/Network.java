package com.tt.miniapp.debug.network;

import org.json.JSONObject;

public class Network {
  public static class DataReceivedParams {
    public int dataLength;
    
    public int encodedDataLength;
    
    public String requestId;
    
    public double timestamp;
  }
  
  public static class GetResponseBodyResponse {
    public boolean base64Encoded;
    
    public String body;
  }
  
  public static class Initiator {
    public Network.InitiatorType type;
  }
  
  public enum InitiatorType {
    OTHER,
    PARSER("parser"),
    SCRIPT("script");
    
    private final String mProtocolValue;
    
    static {
      $VALUES = new InitiatorType[] { PARSER, SCRIPT, OTHER };
    }
    
    InitiatorType(String param1String1) {
      this.mProtocolValue = param1String1;
    }
    
    public final String getProtocolValue() {
      return this.mProtocolValue;
    }
  }
  
  public static class LoadingFailedParams {
    public String errorText;
    
    public String requestId;
    
    public double timestamp;
    
    public ResourceTypeHelper.ResourceType type;
  }
  
  public static class LoadingFinishedParams {
    public String requestId;
    
    public double timestamp;
  }
  
  public static class Request {
    public JSONObject headers;
    
    public String method;
    
    public String postData;
    
    public String url;
  }
  
  public static class RequestWillBeSentParams {
    public String documentURL;
    
    public String frameId;
    
    public Network.Initiator initiator;
    
    public String loaderId;
    
    public Network.Response redirectResponse;
    
    public Network.Request request;
    
    public String requestId;
    
    public double timestamp;
    
    public ResourceTypeHelper.ResourceType type;
  }
  
  public static class ResourceTiming {
    public double connectionEnd;
    
    public double connectionStart;
    
    public double dnsEnd;
    
    public double dnsStart;
    
    public double proxyEnd;
    
    public double proxyStart;
    
    public double receivedHeadersEnd;
    
    public double requestTime;
    
    public double sendEnd;
    
    public double sendStart;
    
    public double sslEnd;
    
    public double sslStart;
  }
  
  public static class Response {
    public int connectionId;
    
    public boolean connectionReused;
    
    public Boolean fromDiskCache;
    
    public JSONObject headers;
    
    public String headersText;
    
    public String mimeType;
    
    public JSONObject requestHeaders;
    
    public String requestHeadersTest;
    
    public int status;
    
    public String statusText;
    
    public Network.ResourceTiming timing;
    
    public String url;
  }
  
  public static class ResponseReceivedParams {
    public String frameId;
    
    public String loaderId;
    
    public String requestId;
    
    public Network.Response response;
    
    public double timestamp;
    
    public ResourceTypeHelper.ResourceType type;
  }
  
  public static class WebSocketClosedParams {
    public String requestId;
    
    public double timestamp;
  }
  
  public static class WebSocketCreatedParams {
    public String requestId;
    
    public String url;
  }
  
  public static class WebSocketFrame {
    public boolean mask;
    
    public int opcode;
    
    public String payloadData;
  }
  
  public static class WebSocketFrameErrorParams {
    public String errorMessage;
    
    public String requestId;
    
    public double timestamp;
  }
  
  public static class WebSocketFrameReceivedParams {
    public String requestId;
    
    public Network.WebSocketFrame response;
    
    public double timestamp;
  }
  
  public static class WebSocketFrameSentParams {
    public String requestId;
    
    public Network.WebSocketFrame response;
    
    public double timestamp;
  }
  
  public static class WebSocketHandshakeResponseReceivedParams {
    public String requestId;
    
    public Network.WebSocketResponse response;
    
    public double timestamp;
  }
  
  public static class WebSocketRequest {
    public JSONObject headers;
  }
  
  public static class WebSocketResponse {
    public JSONObject headers;
    
    public String headersText;
    
    public JSONObject requestHeaders;
    
    public String requestHeadersText;
    
    public int status;
    
    public String statusText;
  }
  
  public static class WebSocketWillSendHandshakeRequestParams {
    public Network.WebSocketRequest request;
    
    public String requestId;
    
    public double timestamp;
    
    public double wallTime;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\Network.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */