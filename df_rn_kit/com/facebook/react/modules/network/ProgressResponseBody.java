package com.facebook.react.modules.network;

import g.f;
import g.h;
import g.l;
import g.q;
import g.z;
import java.io.IOException;
import okhttp3.af;
import okhttp3.w;

public class ProgressResponseBody extends af {
  private h mBufferedSource;
  
  public final ProgressListener mProgressListener;
  
  public final af mResponseBody;
  
  public long mTotalBytesRead;
  
  public ProgressResponseBody(af paramaf, ProgressListener paramProgressListener) {
    this.mResponseBody = paramaf;
    this.mProgressListener = paramProgressListener;
  }
  
  private z source(z paramz) {
    return (z)new l(paramz) {
        public long read(f param1f, long param1Long) throws IOException {
          boolean bool;
          long l1 = super.read(param1f, param1Long);
          ProgressResponseBody progressResponseBody = ProgressResponseBody.this;
          long l2 = progressResponseBody.mTotalBytesRead;
          if (l1 != -1L) {
            param1Long = l1;
          } else {
            param1Long = 0L;
          } 
          progressResponseBody.mTotalBytesRead = l2 + param1Long;
          ProgressListener progressListener = ProgressResponseBody.this.mProgressListener;
          param1Long = ProgressResponseBody.this.mTotalBytesRead;
          l2 = ProgressResponseBody.this.mResponseBody.contentLength();
          if (l1 == -1L) {
            bool = true;
          } else {
            bool = false;
          } 
          progressListener.onProgress(param1Long, l2, bool);
          return l1;
        }
      };
  }
  
  public long contentLength() {
    return this.mResponseBody.contentLength();
  }
  
  public w contentType() {
    return this.mResponseBody.contentType();
  }
  
  public h source() {
    if (this.mBufferedSource == null)
      this.mBufferedSource = q.a(source((z)this.mResponseBody.source())); 
    return this.mBufferedSource;
  }
  
  public long totalBytesRead() {
    return this.mTotalBytesRead;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\ProgressResponseBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */