package com.tt.miniapp.ttapkgdecoder.source;

import com.tt.miniapp.ttapkgdecoder.downloader.IStreamFetcher;
import com.tt.miniapp.ttapkgdecoder.utils.DecodeException;
import com.tt.miniapp.ttapkgdecoder.utils.OkioTools;
import g.h;
import g.q;
import g.z;
import java.io.IOException;

public class OkHttpSource extends BaseSource {
  private final IStreamFetcher mDownloader;
  
  private OkioTools.OnProgressChangeListener mListener;
  
  protected h mSource;
  
  private final String mSourceUrl;
  
  public OkHttpSource(String paramString, IStreamFetcher paramIStreamFetcher) {
    this.mSourceUrl = paramString;
    this.mDownloader = paramIStreamFetcher;
  }
  
  public void close() {
    IStreamFetcher iStreamFetcher = this.mDownloader;
    if (iStreamFetcher != null)
      iStreamFetcher.close(); 
    this.mSource = null;
  }
  
  public long getByteSize() {
    return (this.mDownloader != null && isAlive()) ? this.mDownloader.contentLength() : 0L;
  }
  
  public boolean isAlive() {
    return (this.mSource != null && this.mDownloader.isAlive() && this.mSource.isOpen());
  }
  
  public int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
    h h1 = this.mSource;
    if (h1 != null)
      return h1.a(paramArrayOfbyte, paramInt1, paramInt2); 
    throw new IOException("response body is null");
  }
  
  public void readFully(byte[] paramArrayOfbyte) throws IOException {
    h h1 = this.mSource;
    if (h1 != null) {
      h1.a(paramArrayOfbyte);
      return;
    } 
    throw new IOException("response body is null");
  }
  
  public void setOnProgressChangeListener(OkioTools.OnProgressChangeListener paramOnProgressChangeListener) {
    this.mListener = paramOnProgressChangeListener;
  }
  
  public void skip(long paramLong) throws IOException {
    h h1 = this.mSource;
    if (h1 != null) {
      h1.i(paramLong);
      return;
    } 
    throw new IOException("response body is null");
  }
  
  public void start() throws DecodeException {
    try {
      z z = OkioTools.progressListenSource(this.mDownloader.getDownloadInputStream(this.mSourceUrl), this.mListener);
      if (z instanceof h) {
        this.mSource = (h)z;
        return;
      } 
      this.mSource = q.a(z);
      return;
    } catch (DecodeException decodeException) {
      close();
      throw decodeException;
    } catch (Exception exception) {
      close();
      throw new DecodeException(exception, -4);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\source\OkHttpSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */