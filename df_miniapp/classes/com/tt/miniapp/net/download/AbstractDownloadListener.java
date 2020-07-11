package com.tt.miniapp.net.download;

import com.tt.miniapphost.AppBrandLogger;
import okhttp3.ae;

public abstract class AbstractDownloadListener implements DownloadManager.OnDownloadListener {
  public void onDownloadFailed(String paramString, Throwable paramThrowable) {
    AppBrandLogger.d("AbstractDownloadListener", new Object[] { paramString, paramThrowable.toString() });
  }
  
  public void onDownloadSuccess(ae paramae) {
    AppBrandLogger.d("AbstractDownloadListener", new Object[] { paramae.toString() });
  }
  
  public void onDownloading(int paramInt, long paramLong1, long paramLong2) {
    AppBrandLogger.d("AbstractDownloadListener", new Object[] { Integer.valueOf(paramInt), Long.valueOf(paramLong1), Long.valueOf(paramLong2) });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\download\AbstractDownloadListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */