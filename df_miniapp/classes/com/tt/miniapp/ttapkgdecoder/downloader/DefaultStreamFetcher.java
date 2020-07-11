package com.tt.miniapp.ttapkgdecoder.downloader;

import android.util.Log;
import com.tt.miniapp.ttapkgdecoder.StreamOkHttpClient;
import com.tt.miniapphost.AppBrandLogger;
import g.z;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.af;

public class DefaultStreamFetcher implements IStreamFetcher {
  private af body;
  
  private ae response;
  
  public void close() {
    ae ae1 = this.response;
    if (ae1 != null)
      try {
        ae1.close();
        return;
      } catch (Exception exception) {
        return;
      }  
  }
  
  public long contentLength() {
    af af1 = this.body;
    return (af1 != null) ? af1.contentLength() : 0L;
  }
  
  public z getDownloadInputStream(String paramString) {
    this.response = null;
    try {
      ac ac = (new ac.a()).a(paramString).c();
      this.response = StreamOkHttpClient.okHttpClient.a(ac).b();
    } catch (Exception exception) {
      AppBrandLogger.e("DefaultStreamFetcher", new Object[] { Log.getStackTraceString(exception) });
    } 
    ae ae1 = this.response;
    if (ae1 != null) {
      this.body = ae1.g;
      af af1 = this.body;
      if (af1 != null)
        return (z)af1.source(); 
    } 
    return null;
  }
  
  public boolean isAlive() {
    ae ae1 = this.response;
    return (ae1 != null && ae1.a());
  }
  
  public void onReadFinished() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\downloader\DefaultStreamFetcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */