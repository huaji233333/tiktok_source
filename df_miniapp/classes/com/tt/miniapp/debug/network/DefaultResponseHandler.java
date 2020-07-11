package com.tt.miniapp.debug.network;

import java.io.IOException;

public class DefaultResponseHandler implements ResponseHandler {
  private int mBytesRead;
  
  private int mDecodedBytesRead = -1;
  
  private final NetworkEventReporter mEventReporter;
  
  private final String mRequestId;
  
  public DefaultResponseHandler(NetworkEventReporter paramNetworkEventReporter, String paramString) {
    this.mEventReporter = paramNetworkEventReporter;
    this.mRequestId = paramString;
  }
  
  private void reportDataReceived() {
    NetworkEventReporter networkEventReporter = this.mEventReporter;
    String str = this.mRequestId;
    int j = this.mBytesRead;
    int i = this.mDecodedBytesRead;
    if (i < 0)
      i = j; 
    networkEventReporter.dataReceived(str, j, i);
  }
  
  public void onEOF() {
    reportDataReceived();
    this.mEventReporter.responseReadFinished(this.mRequestId);
  }
  
  public void onError(IOException paramIOException) {
    reportDataReceived();
    this.mEventReporter.responseReadFailed(this.mRequestId, paramIOException.toString());
  }
  
  public void onRead(int paramInt) {
    this.mBytesRead += paramInt;
  }
  
  public void onReadDecoded(int paramInt) {
    if (this.mDecodedBytesRead == -1)
      this.mDecodedBytesRead = 0; 
    this.mDecodedBytesRead += paramInt;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\network\DefaultResponseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */