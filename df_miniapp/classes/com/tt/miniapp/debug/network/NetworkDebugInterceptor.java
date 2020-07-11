package com.tt.miniapp.debug.network;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import g.g;
import g.h;
import g.q;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.ac;
import okhttp3.ad;
import okhttp3.ae;
import okhttp3.af;
import okhttp3.i;
import okhttp3.u;
import okhttp3.w;

public class NetworkDebugInterceptor implements u {
  private final NetworkEventReporter mEventReporter = NetworkEventReporterImpl.get();
  
  public ae intercept(u.a parama) throws IOException {
    boolean bool;
    RequestBodyHelper requestBodyHelper;
    String str = this.mEventReporter.nextRequestId();
    ac ac = parama.a();
    if (!TextUtils.isEmpty(ac.c.a("remoteDebug"))) {
      ac = ac.a().b("remoteDebug").c();
      bool = false;
    } else {
      bool = true;
    } 
    AppBrandLogger.d("NetworkDebugInterceptor", new Object[] { "url: ", ac.a, "isInnerRequest: ", Boolean.valueOf(bool) });
    if (this.mEventReporter.isEnabled() && !bool) {
      requestBodyHelper = new RequestBodyHelper(this.mEventReporter, str);
      OkHttpInspectorRequest okHttpInspectorRequest = new OkHttpInspectorRequest(str, ac, requestBodyHelper);
      this.mEventReporter.requestWillBeSent(okHttpInspectorRequest);
    } else {
      requestBodyHelper = null;
    } 
    try {
      ae ae = parama.a(ac);
      if (this.mEventReporter.isEnabled() && !bool) {
        if (requestBodyHelper != null && requestBodyHelper.hasBody())
          requestBodyHelper.reportDataSent(); 
        i i = parama.b();
        if (i != null) {
          ac ac1;
          this.mEventReporter.responseHeadersReceived(new OkHttpInspectorResponse(str, ac, ae, i));
          af af = ae.g;
          if (af != null) {
            w w = af.contentType();
            InputStream inputStream1 = af.byteStream();
          } else {
            ac = null;
            ac1 = ac;
          } 
          NetworkEventReporter networkEventReporter = this.mEventReporter;
          if (ac != null) {
            String str1 = ac.toString();
          } else {
            ac = null;
          } 
          InputStream inputStream = networkEventReporter.interpretResponseStream(str, (String)ac, ae.b("Content-Encoding"), (InputStream)ac1, new DefaultResponseHandler(this.mEventReporter, str));
          if (inputStream != null)
            return ae.b().a(new ForwardingResponseBody(af, inputStream)).a(); 
        } else {
          throw new IllegalStateException("No connection associated with this request; did you use addInterceptor instead of addNetworkInterceptor?");
        } 
      } 
      return ae;
    } catch (IOException iOException) {
      if (this.mEventReporter.isEnabled())
        this.mEventReporter.httpExchangeFailed(str, iOException.toString()); 
      throw iOException;
    } 
  }
  
  static class ForwardingResponseBody extends af {
    private final af mBody;
    
    private final h mInterceptedSource;
    
    public ForwardingResponseBody(af param1af, InputStream param1InputStream) {
      this.mBody = param1af;
      this.mInterceptedSource = q.a(q.a(param1InputStream));
    }
    
    public long contentLength() {
      return this.mBody.contentLength();
    }
    
    public w contentType() {
      return this.mBody.contentType();
    }
    
    public h source() {
      return this.mInterceptedSource;
    }
  }
  
  static class OkHttpInspectorRequest implements NetworkEventReporter.InspectorRequest {
    private final ac mRequest;
    
    private RequestBodyHelper mRequestBodyHelper;
    
    private final String mRequestId;
    
    public OkHttpInspectorRequest(String param1String, ac param1ac, RequestBodyHelper param1RequestBodyHelper) {
      this.mRequestId = param1String;
      this.mRequest = param1ac;
      this.mRequestBodyHelper = param1RequestBodyHelper;
    }
    
    public byte[] body() throws IOException {
      null = this.mRequest.d;
      if (null == null)
        return null; 
      g g = q.a(q.a(this.mRequestBodyHelper.createBodySink(firstHeaderValue("Content-Encoding"))));
      try {
        null.writeTo(g);
        return this.mRequestBodyHelper.getDisplayBody();
      } finally {
        g.close();
      } 
    }
    
    public String firstHeaderValue(String param1String) {
      return this.mRequest.a(param1String);
    }
    
    public String friendlyName() {
      return null;
    }
    
    public Integer friendlyNameExtra() {
      return null;
    }
    
    public int headerCount() {
      return this.mRequest.c.a();
    }
    
    public String headerName(int param1Int) {
      return this.mRequest.c.a(param1Int);
    }
    
    public String headerValue(int param1Int) {
      return this.mRequest.c.b(param1Int);
    }
    
    public String id() {
      return this.mRequestId;
    }
    
    public String method() {
      return this.mRequest.b;
    }
    
    public String url() {
      return this.mRequest.a.toString();
    }
  }
  
  static class OkHttpInspectorResponse implements NetworkEventReporter.InspectorResponse {
    private final i mConnection;
    
    private final ac mRequest;
    
    private final String mRequestId;
    
    private final ae mResponse;
    
    public OkHttpInspectorResponse(String param1String, ac param1ac, ae param1ae, i param1i) {
      this.mRequestId = param1String;
      this.mRequest = param1ac;
      this.mResponse = param1ae;
      this.mConnection = param1i;
    }
    
    public int connectionId() {
      i i1 = this.mConnection;
      return (i1 == null) ? 0 : i1.hashCode();
    }
    
    public boolean connectionReused() {
      return false;
    }
    
    public String firstHeaderValue(String param1String) {
      return this.mResponse.b(param1String);
    }
    
    public boolean fromDiskCache() {
      return (this.mResponse.i != null);
    }
    
    public int headerCount() {
      return this.mResponse.f.a();
    }
    
    public String headerName(int param1Int) {
      return this.mResponse.f.a(param1Int);
    }
    
    public String headerValue(int param1Int) {
      return this.mResponse.f.b(param1Int);
    }
    
    public String reasonPhrase() {
      return this.mResponse.d;
    }
    
    public String requestId() {
      return this.mRequestId;
    }
    
    public int statusCode() {
      return this.mResponse.c;
    }
    
    public String url() {
      return this.mRequest.a.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\NetworkDebugInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */