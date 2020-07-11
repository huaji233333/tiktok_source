package com.tt.miniapp.websocket;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.net.SocketFactory;
import okhttp3.o;
import okhttp3.t;

public class UnixSocketFactory extends SocketFactory implements o {
  private final Map<String, String> hostnameToPath = new LinkedHashMap<String, String>();
  
  private int nextId = 1;
  
  private final Map<String, String> pathToHostname = new LinkedHashMap<String, String>();
  
  private String hostnameForPath(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield pathToHostname : Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   12: checkcast java/lang/String
    //   15: astore #4
    //   17: aload #4
    //   19: astore_3
    //   20: aload #4
    //   22: ifnonnull -> 89
    //   25: new java/lang/StringBuilder
    //   28: dup
    //   29: ldc 'p'
    //   31: invokespecial <init> : (Ljava/lang/String;)V
    //   34: astore_3
    //   35: aload_0
    //   36: getfield nextId : I
    //   39: istore_2
    //   40: aload_0
    //   41: iload_2
    //   42: iconst_1
    //   43: iadd
    //   44: putfield nextId : I
    //   47: aload_3
    //   48: iload_2
    //   49: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: aload_3
    //   54: ldc '.socket'
    //   56: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: aload_3
    //   61: invokevirtual toString : ()Ljava/lang/String;
    //   64: astore_3
    //   65: aload_0
    //   66: getfield pathToHostname : Ljava/util/Map;
    //   69: aload_1
    //   70: aload_3
    //   71: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   76: pop
    //   77: aload_0
    //   78: getfield hostnameToPath : Ljava/util/Map;
    //   81: aload_3
    //   82: aload_1
    //   83: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   88: pop
    //   89: aload_0
    //   90: monitorexit
    //   91: aload_3
    //   92: areturn
    //   93: astore_1
    //   94: aload_0
    //   95: monitorexit
    //   96: aload_1
    //   97: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	93	finally
    //   25	89	93	finally
  }
  
  public Socket createSocket() throws IOException {
    return new UnixSocket();
  }
  
  public Socket createSocket(String paramString, int paramInt) {
    throw new UnsupportedOperationException();
  }
  
  public Socket createSocket(String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2) {
    throw new UnsupportedOperationException();
  }
  
  public Socket createSocket(InetAddress paramInetAddress, int paramInt) {
    throw new UnsupportedOperationException();
  }
  
  public Socket createSocket(InetAddress paramInetAddress1, int paramInt1, InetAddress paramInetAddress2, int paramInt2) {
    throw new UnsupportedOperationException();
  }
  
  public List<InetAddress> lookup(String paramString) throws UnknownHostException {
    return paramString.endsWith(".socket") ? Collections.singletonList(InetAddress.getByAddress(paramString, new byte[] { 0, 0, 0, 0 })) : o.b.lookup(paramString);
  }
  
  public String pathForInetAddress(InetAddress paramInetAddress) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield hostnameToPath : Ljava/util/Map;
    //   6: aload_1
    //   7: invokevirtual getHostName : ()Ljava/lang/String;
    //   10: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   15: checkcast java/lang/String
    //   18: astore_1
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_1
    //   22: areturn
    //   23: astore_1
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_1
    //   27: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	23	finally
  }
  
  public t urlForPath(String paramString) {
    return (new t.a()).a("http").d(hostnameForPath(paramString)).b();
  }
  
  public t urlForPath(String paramString1, String paramString2) {
    return (new t.a()).a("http").d(hostnameForPath(paramString1)).e(paramString2).b();
  }
  
  public t urlForPath(String paramString1, String paramString2, String paramString3, String paramString4) {
    return (new t.a()).a("http").d(hostnameForPath(paramString1)).e(paramString2).e(paramString3).e(paramString4).b();
  }
  
  class UnixSocket extends Socket {
    private LocalSocket client;
    
    public void bind(SocketAddress param1SocketAddress) throws IOException {
      this.client.bind(new LocalSocketAddress(param1SocketAddress.toString()));
    }
    
    public void connect(SocketAddress param1SocketAddress, int param1Int) throws IOException {
      String str = UnixSocketFactory.this.pathForInetAddress(((InetSocketAddress)param1SocketAddress).getAddress());
      System.out.println(str);
      if (str != null) {
        this.client = new LocalSocket();
        this.client.connect(new LocalSocketAddress(str));
        return;
      } 
      throw new IOException("can not find path with this socket");
    }
    
    public InputStream getInputStream() throws IOException {
      return this.client.getInputStream();
    }
    
    public OutputStream getOutputStream() throws IOException {
      return this.client.getOutputStream();
    }
    
    public boolean isConnected() {
      return this.client.isConnected();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\websocket\UnixSocketFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */