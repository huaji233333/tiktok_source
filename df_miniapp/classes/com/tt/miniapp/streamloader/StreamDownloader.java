package com.tt.miniapp.streamloader;

import com.tt.miniapphost.entity.AppInfoEntity;
import java.io.File;
import java.util.HashMap;

public class StreamDownloader {
  private static final HashMap<String, StreamDownloadTask> sStreamDownloadTasks = new HashMap<String, StreamDownloadTask>();
  
  public static void finishStreamDownloadPkg(AppInfoEntity paramAppInfoEntity) {
    // Byte code:
    //   0: ldc com/tt/miniapp/streamloader/StreamDownloader
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/streamloader/StreamDownloader.sStreamDownloadTasks : Ljava/util/HashMap;
    //   6: aload_0
    //   7: getfield appId : Ljava/lang/String;
    //   10: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   13: pop
    //   14: ldc com/tt/miniapp/streamloader/StreamDownloader
    //   16: monitorexit
    //   17: ldc 'StreamDownloader'
    //   19: iconst_2
    //   20: anewarray java/lang/Object
    //   23: dup
    //   24: iconst_0
    //   25: ldc 'finishStreamDownloadPkg appId:'
    //   27: aastore
    //   28: dup
    //   29: iconst_1
    //   30: aload_0
    //   31: getfield appId : Ljava/lang/String;
    //   34: aastore
    //   35: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   38: return
    //   39: astore_0
    //   40: ldc com/tt/miniapp/streamloader/StreamDownloader
    //   42: monitorexit
    //   43: aload_0
    //   44: athrow
    // Exception table:
    //   from	to	target	type
    //   3	17	39	finally
    //   40	43	39	finally
  }
  
  public static void startStreamDownloadPkg(AppInfoEntity paramAppInfoEntity, File paramFile, StreamDownloadListener paramStreamDownloadListener) {
    // Byte code:
    //   0: aload_0
    //   1: getfield appId : Ljava/lang/String;
    //   4: astore #5
    //   6: ldc com/tt/miniapp/streamloader/StreamDownloader
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/streamloader/StreamDownloader.sStreamDownloadTasks : Ljava/util/HashMap;
    //   12: aload #5
    //   14: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   17: checkcast com/tt/miniapp/streamloader/StreamDownloadTask
    //   20: astore #4
    //   22: aload #4
    //   24: astore_3
    //   25: aload #4
    //   27: ifnonnull -> 50
    //   30: new com/tt/miniapp/streamloader/StreamDownloadTask
    //   33: dup
    //   34: aload_0
    //   35: aload_1
    //   36: invokespecial <init> : (Lcom/tt/miniapphost/entity/AppInfoEntity;Ljava/io/File;)V
    //   39: astore_3
    //   40: getstatic com/tt/miniapp/streamloader/StreamDownloader.sStreamDownloadTasks : Ljava/util/HashMap;
    //   43: aload #5
    //   45: aload_3
    //   46: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   49: pop
    //   50: ldc com/tt/miniapp/streamloader/StreamDownloader
    //   52: monitorexit
    //   53: ldc 'StreamDownloader'
    //   55: iconst_2
    //   56: anewarray java/lang/Object
    //   59: dup
    //   60: iconst_0
    //   61: ldc 'startStreamDownloadPkg appId:'
    //   63: aastore
    //   64: dup
    //   65: iconst_1
    //   66: aload_0
    //   67: getfield appId : Ljava/lang/String;
    //   70: aastore
    //   71: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   74: aload_3
    //   75: new com/tt/miniapp/streamloader/StreamDownloader$StreamDownloadAdapter
    //   78: dup
    //   79: aload_2
    //   80: aload_0
    //   81: invokespecial <init> : (Lcom/tt/miniapp/streamloader/StreamDownloadListener;Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   84: invokevirtual startDownload : (Lcom/tt/miniapp/streamloader/StreamDownloadListener;)V
    //   87: return
    //   88: astore_0
    //   89: ldc com/tt/miniapp/streamloader/StreamDownloader
    //   91: monitorexit
    //   92: aload_0
    //   93: athrow
    // Exception table:
    //   from	to	target	type
    //   9	22	88	finally
    //   30	50	88	finally
    //   50	53	88	finally
    //   89	92	88	finally
  }
  
  public static void stopStreamDownloadPkg(AppInfoEntity paramAppInfoEntity) {
    // Byte code:
    //   0: ldc com/tt/miniapp/streamloader/StreamDownloader
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/streamloader/StreamDownloader.sStreamDownloadTasks : Ljava/util/HashMap;
    //   6: aload_0
    //   7: getfield appId : Ljava/lang/String;
    //   10: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   13: checkcast com/tt/miniapp/streamloader/StreamDownloadTask
    //   16: astore_1
    //   17: ldc com/tt/miniapp/streamloader/StreamDownloader
    //   19: monitorexit
    //   20: ldc 'StreamDownloader'
    //   22: iconst_2
    //   23: anewarray java/lang/Object
    //   26: dup
    //   27: iconst_0
    //   28: ldc 'stopStreamDownloadPkg appId:'
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: aload_0
    //   34: getfield appId : Ljava/lang/String;
    //   37: aastore
    //   38: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   41: aload_1
    //   42: ifnull -> 49
    //   45: aload_1
    //   46: invokevirtual stopDownload : ()V
    //   49: return
    //   50: astore_0
    //   51: ldc com/tt/miniapp/streamloader/StreamDownloader
    //   53: monitorexit
    //   54: aload_0
    //   55: athrow
    // Exception table:
    //   from	to	target	type
    //   3	20	50	finally
    //   51	54	50	finally
  }
  
  static class StreamDownloadAdapter implements StreamDownloadListener {
    private AppInfoEntity mAppInfo;
    
    private StreamDownloadListener mOriginListener;
    
    StreamDownloadAdapter(StreamDownloadListener param1StreamDownloadListener, AppInfoEntity param1AppInfoEntity) {
      this.mOriginListener = param1StreamDownloadListener;
      this.mAppInfo = param1AppInfoEntity;
    }
    
    public void onDownloadProgress(int param1Int) {
      StreamDownloadListener streamDownloadListener = this.mOriginListener;
      if (streamDownloadListener != null)
        streamDownloadListener.onDownloadProgress(param1Int); 
    }
    
    public void onRetry(String param1String1, String param1String2, String param1String3, int param1Int, long param1Long) {
      StreamDownloadListener streamDownloadListener = this.mOriginListener;
      if (streamDownloadListener != null)
        streamDownloadListener.onRetry(param1String1, param1String2, param1String3, param1Int, param1Long); 
    }
    
    public void onStreamDownloadError(String param1String, int param1Int, long param1Long) {
      StreamDownloadListener streamDownloadListener = this.mOriginListener;
      if (streamDownloadListener != null)
        streamDownloadListener.onStreamDownloadError(param1String, param1Int, param1Long); 
      StreamDownloader.finishStreamDownloadPkg(this.mAppInfo);
    }
    
    public void onStreamDownloadFinish(int param1Int, long param1Long) {
      StreamDownloadListener streamDownloadListener = this.mOriginListener;
      if (streamDownloadListener != null)
        streamDownloadListener.onStreamDownloadFinish(param1Int, param1Long); 
      StreamDownloader.finishStreamDownloadPkg(this.mAppInfo);
    }
    
    public void onStreamDownloadStop() {
      StreamDownloadListener streamDownloadListener = this.mOriginListener;
      if (streamDownloadListener != null)
        streamDownloadListener.onStreamDownloadStop(); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\StreamDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */