package com.facebook.react.modules.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TLSSocketFactory extends SSLSocketFactory {
  private SSLSocketFactory delegate;
  
  public TLSSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
    SSLContext sSLContext = SSLContext.getInstance("TLS");
    sSLContext.init(null, null, null);
    this.delegate = sSLContext.getSocketFactory();
  }
  
  private Socket enableTLSOnSocket(Socket paramSocket) {
    if (paramSocket != null && paramSocket instanceof SSLSocket)
      ((SSLSocket)paramSocket).setEnabledProtocols(new String[] { "TLSv1", "TLSv1.1", "TLSv1.2" }); 
    return paramSocket;
  }
  
  public Socket createSocket(String paramString, int paramInt) throws IOException, UnknownHostException {
    return enableTLSOnSocket(this.delegate.createSocket(paramString, paramInt));
  }
  
  public Socket createSocket(String paramString, int paramInt1, InetAddress paramInetAddress, int paramInt2) throws IOException, UnknownHostException {
    return enableTLSOnSocket(this.delegate.createSocket(paramString, paramInt1, paramInetAddress, paramInt2));
  }
  
  public Socket createSocket(InetAddress paramInetAddress, int paramInt) throws IOException {
    return enableTLSOnSocket(this.delegate.createSocket(paramInetAddress, paramInt));
  }
  
  public Socket createSocket(InetAddress paramInetAddress1, int paramInt1, InetAddress paramInetAddress2, int paramInt2) throws IOException {
    return enableTLSOnSocket(this.delegate.createSocket(paramInetAddress1, paramInt1, paramInetAddress2, paramInt2));
  }
  
  public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean) throws IOException {
    return enableTLSOnSocket(this.delegate.createSocket(paramSocket, paramString, paramInt, paramBoolean));
  }
  
  public String[] getDefaultCipherSuites() {
    return this.delegate.getDefaultCipherSuites();
  }
  
  public String[] getSupportedCipherSuites() {
    return this.delegate.getSupportedCipherSuites();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\TLSSocketFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */