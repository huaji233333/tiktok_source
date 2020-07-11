package com.tt.miniapp.streamloader;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.io.File;

public class StreamDownloadTask {
  private String mCurrentUrl;
  
  private PkgDownloadEntity mPkgDownloadInfo;
  
  public TTAPkgDownloader mPkgDownloader;
  
  private final File mSavePkgFile;
  
  public StreamDownloadListener mStreamDownloadListener;
  
  public StreamDownloadTask(AppInfoEntity paramAppInfoEntity, File paramFile) {
    this.mPkgDownloadInfo = new PkgDownloadEntity(paramAppInfoEntity);
    this.mSavePkgFile = paramFile;
    this.mPkgDownloader = new TTAPkgDownloader(this.mSavePkgFile, new TTAPkgDownloader.PkgDownloadListener() {
          private void notifyRetry(String param1String1, String param1String2, String param1String3, int param1Int, long param1Long) {
            if (StreamDownloadTask.this.mStreamDownloadListener != null)
              StreamDownloadTask.this.mStreamDownloadListener.onRetry(param1String1, param1String2, param1String3, param1Int, param1Long); 
          }
          
          public void onFail(String param1String1, String param1String2, int param1Int, long param1Long) {
            StringBuilder stringBuilder = new StringBuilder("StreamDownloadTask onFail:");
            stringBuilder.append(param1String2);
            AppBrandLogger.e("StreamDownloadTask", new Object[] { stringBuilder.toString() });
            if (StreamDownloadTask.this.mPkgDownloader.isStopped()) {
              if (StreamDownloadTask.this.mStreamDownloadListener != null)
                StreamDownloadTask.this.mStreamDownloadListener.onStreamDownloadStop(); 
              return;
            } 
            String str = StreamDownloadTask.this.nextStreamDownloadUrl();
            if (!TextUtils.isEmpty(str)) {
              notifyRetry(param1String2, param1String1, str, param1Int, param1Long);
              StreamDownloadTask.this.loadWithUrlWhenStreamDownload(str);
              return;
            } 
            if (StreamDownloadTask.this.mStreamDownloadListener != null)
              StreamDownloadTask.this.mStreamDownloadListener.onStreamDownloadError(param1String2, param1Int, param1Long); 
          }
          
          public void onFinish(int param1Int, long param1Long) {
            if (StreamDownloadTask.this.mStreamDownloadListener != null)
              StreamDownloadTask.this.mStreamDownloadListener.onStreamDownloadFinish(param1Int, param1Long); 
          }
          
          public void onProgressChange(int param1Int) {
            if (StreamDownloadTask.this.mStreamDownloadListener != null)
              StreamDownloadTask.this.mStreamDownloadListener.onDownloadProgress(param1Int); 
          }
          
          public void onStop() {
            if (StreamDownloadTask.this.mStreamDownloadListener != null)
              StreamDownloadTask.this.mStreamDownloadListener.onStreamDownloadStop(); 
          }
        });
  }
  
  private String getStreamDownloadUrl() {
    return isSavePkgFileAvailable() ? this.mPkgDownloadInfo.getOriginPkgUrl() : this.mPkgDownloadInfo.getPkgUrl();
  }
  
  private boolean isSavePkgFileAvailable() {
    return (this.mSavePkgFile.exists() && this.mSavePkgFile.length() > 0L);
  }
  
  public void loadWithUrlWhenStreamDownload(String paramString) {
    long l;
    this.mCurrentUrl = paramString;
    paramString = this.mPkgDownloadInfo.getCompressType();
    if (isSavePkgFileAvailable()) {
      l = this.mSavePkgFile.length();
      paramString = "";
    } else {
      l = 0L;
    } 
    AppBrandLogger.i("StreamDownloadTask", new Object[] { "loadWithUrlWhenStreamDownload url:", this.mCurrentUrl, "downloadOffset:", Long.valueOf(l) });
    this.mPkgDownloader.startAsyncDownload(this.mCurrentUrl, new DownloadFetcher(l, paramString));
  }
  
  public String nextStreamDownloadUrl() {
    return isSavePkgFileAvailable() ? this.mPkgDownloadInfo.nextOriginPkgUrl() : this.mPkgDownloadInfo.nextPkgUrl();
  }
  
  public void startDownload(StreamDownloadListener paramStreamDownloadListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: putfield mStreamDownloadListener : Lcom/tt/miniapp/streamloader/StreamDownloadListener;
    //   7: aload_0
    //   8: getfield mPkgDownloader : Lcom/tt/miniapp/streamloader/TTAPkgDownloader;
    //   11: invokevirtual isDownloading : ()Z
    //   14: ifeq -> 34
    //   17: ldc 'StreamDownloadTask'
    //   19: iconst_1
    //   20: anewarray java/lang/Object
    //   23: dup
    //   24: iconst_0
    //   25: ldc 'startDownload isDownloading'
    //   27: aastore
    //   28: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: aload_0
    //   35: getfield mPkgDownloader : Lcom/tt/miniapp/streamloader/TTAPkgDownloader;
    //   38: invokevirtual tryResumeDownload : ()V
    //   41: aload_0
    //   42: getfield mPkgDownloader : Lcom/tt/miniapp/streamloader/TTAPkgDownloader;
    //   45: invokevirtual isDownloading : ()Z
    //   48: ifeq -> 68
    //   51: ldc 'StreamDownloadTask'
    //   53: iconst_1
    //   54: anewarray java/lang/Object
    //   57: dup
    //   58: iconst_0
    //   59: ldc 'startDownload resumeDownload after tryResumeDownload'
    //   61: aastore
    //   62: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   65: aload_0
    //   66: monitorexit
    //   67: return
    //   68: aload_0
    //   69: getfield mPkgDownloader : Lcom/tt/miniapp/streamloader/TTAPkgDownloader;
    //   72: invokevirtual isStopped : ()Z
    //   75: ifeq -> 103
    //   78: ldc 'StreamDownloadTask'
    //   80: iconst_1
    //   81: anewarray java/lang/Object
    //   84: dup
    //   85: iconst_0
    //   86: ldc 'startDownload resumeCurrentUrlDownload'
    //   88: aastore
    //   89: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   92: aload_0
    //   93: aload_0
    //   94: invokespecial getStreamDownloadUrl : ()Ljava/lang/String;
    //   97: invokevirtual loadWithUrlWhenStreamDownload : (Ljava/lang/String;)V
    //   100: aload_0
    //   101: monitorexit
    //   102: return
    //   103: aload_0
    //   104: invokevirtual nextStreamDownloadUrl : ()Ljava/lang/String;
    //   107: astore_2
    //   108: aload_2
    //   109: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   112: ifne -> 123
    //   115: aload_0
    //   116: aload_2
    //   117: invokevirtual loadWithUrlWhenStreamDownload : (Ljava/lang/String;)V
    //   120: aload_0
    //   121: monitorexit
    //   122: return
    //   123: aload_1
    //   124: ldc 'empty url'
    //   126: iconst_0
    //   127: lconst_0
    //   128: invokeinterface onStreamDownloadError : (Ljava/lang/String;IJ)V
    //   133: aload_0
    //   134: monitorexit
    //   135: return
    //   136: astore_1
    //   137: aload_0
    //   138: monitorexit
    //   139: aload_1
    //   140: athrow
    // Exception table:
    //   from	to	target	type
    //   2	31	136	finally
    //   34	65	136	finally
    //   68	100	136	finally
    //   103	120	136	finally
    //   123	133	136	finally
  }
  
  public void stopDownload() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new java/lang/StringBuilder
    //   5: dup
    //   6: ldc 'stopDownload mCurrentUrl:'
    //   8: invokespecial <init> : (Ljava/lang/String;)V
    //   11: astore_1
    //   12: aload_1
    //   13: aload_0
    //   14: getfield mCurrentUrl : Ljava/lang/String;
    //   17: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: pop
    //   21: ldc 'StreamDownloadTask'
    //   23: iconst_1
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_1
    //   30: invokevirtual toString : ()Ljava/lang/String;
    //   33: aastore
    //   34: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   37: aload_0
    //   38: getfield mPkgDownloader : Lcom/tt/miniapp/streamloader/TTAPkgDownloader;
    //   41: ifnull -> 51
    //   44: aload_0
    //   45: getfield mPkgDownloader : Lcom/tt/miniapp/streamloader/TTAPkgDownloader;
    //   48: invokevirtual stopAsyncDownload : ()V
    //   51: aload_0
    //   52: monitorexit
    //   53: return
    //   54: astore_1
    //   55: aload_0
    //   56: monitorexit
    //   57: aload_1
    //   58: athrow
    // Exception table:
    //   from	to	target	type
    //   2	51	54	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\StreamDownloadTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */