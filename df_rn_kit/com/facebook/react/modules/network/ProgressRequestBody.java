package com.facebook.react.modules.network;

import g.g;
import g.q;
import g.x;
import java.io.IOException;
import java.io.OutputStream;
import okhttp3.ad;
import okhttp3.w;

public class ProgressRequestBody extends ad {
  private g mBufferedSink;
  
  private long mContentLength;
  
  public final ProgressListener mProgressListener;
  
  private final ad mRequestBody;
  
  public ProgressRequestBody(ad paramad, ProgressListener paramProgressListener) {
    this.mRequestBody = paramad;
    this.mProgressListener = paramProgressListener;
  }
  
  private x outputStreamSink(g paramg) {
    return q.a(new CountingOutputStream(paramg.b()) {
          private void sendProgressUpdate() throws IOException {
            boolean bool;
            long l1 = getCount();
            long l2 = ProgressRequestBody.this.contentLength();
            ProgressListener progressListener = ProgressRequestBody.this.mProgressListener;
            if (l1 == l2) {
              bool = true;
            } else {
              bool = false;
            } 
            progressListener.onProgress(l1, l2, bool);
          }
          
          public void write(int param1Int) throws IOException {
            super.write(param1Int);
            sendProgressUpdate();
          }
          
          public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
            super.write(param1ArrayOfbyte, param1Int1, param1Int2);
            sendProgressUpdate();
          }
        });
  }
  
  public long contentLength() throws IOException {
    if (this.mContentLength == 0L)
      this.mContentLength = this.mRequestBody.contentLength(); 
    return this.mContentLength;
  }
  
  public w contentType() {
    return this.mRequestBody.contentType();
  }
  
  public void writeTo(g paramg) throws IOException {
    if (this.mBufferedSink == null)
      this.mBufferedSink = q.a(outputStreamSink(paramg)); 
    contentLength();
    this.mRequestBody.writeTo(this.mBufferedSink);
    this.mBufferedSink.flush();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\ProgressRequestBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */