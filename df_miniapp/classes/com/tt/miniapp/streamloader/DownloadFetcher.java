package com.tt.miniapp.streamloader;

import com.tt.miniapp.ttapkgdecoder.downloader.IStreamFetcher;
import com.tt.miniapp.ttapkgdecoder.utils.DecodeException;
import com.tt.miniapphost.AppBrandLogger;
import g.z;
import java.io.IOException;
import okhttp3.ae;
import okhttp3.af;

public class DownloadFetcher implements IStreamFetcher {
  private af mBody;
  
  private long mOffset;
  
  private String mPkgCompressType;
  
  private ae mResponse;
  
  public int mStatusCode;
  
  public DownloadFetcher(long paramLong, String paramString) {
    this.mOffset = paramLong;
    this.mPkgCompressType = paramString;
  }
  
  public void close() {
    ae ae1 = this.mResponse;
    if (ae1 != null)
      try {
        ae1.close();
        return;
      } catch (Exception exception) {
        AppBrandLogger.e("DownloadFetcher", new Object[] { exception });
      }  
  }
  
  public long contentLength() {
    af af1 = this.mBody;
    return (af1 != null) ? af1.contentLength() : 0L;
  }
  
  public z getDownloadInputStream(String paramString) throws IOException {
    byte b;
    this.mResponse = StreamLoaderUtils.getPkgResponseFromOffset(paramString, this.mOffset, this.mPkgCompressType);
    ae ae2 = this.mResponse;
    paramString = null;
    if (ae2 != null) {
      af af2;
      this.mStatusCode = ae2.c;
      if (this.mResponse.a())
        af2 = this.mResponse.g; 
      this.mBody = af2;
    } else {
      this.mBody = null;
    } 
    af af1 = this.mBody;
    if (af1 != null)
      return (z)af1.source(); 
    ae ae1 = this.mResponse;
    if (ae1 != null) {
      b = ae1.c;
    } else {
      b = -2;
    } 
    throw new DecodeException(b);
  }
  
  public boolean isAlive() {
    ae ae1 = this.mResponse;
    return (ae1 != null && ae1.a());
  }
  
  public void onReadFinished() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\DownloadFetcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */