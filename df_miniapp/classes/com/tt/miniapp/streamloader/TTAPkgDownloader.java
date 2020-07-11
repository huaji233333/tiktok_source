package com.tt.miniapp.streamloader;

import android.text.TextUtils;
import android.util.Log;
import com.tt.miniapp.ttapkgdecoder.source.DiskSource;
import com.tt.miniapp.ttapkgdecoder.source.OkHttpSource;
import com.tt.miniapp.ttapkgdecoder.utils.OkioTools;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.LaunchThreadPool;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TTAPkgDownloader implements OkioTools.OnProgressChangeListener {
  private long mContentLength = -1L;
  
  private DownloadFetcher mDownloadFetcher;
  
  public boolean mDownloading;
  
  private volatile boolean mEnableResume;
  
  private long mLoadFileLength;
  
  public final Object mOperateLock = new Object();
  
  private PkgDownloadListener mPkgDownloadListener;
  
  private File mSavePkgFile;
  
  private int mStatusCode = -1;
  
  private volatile boolean mStop;
  
  private long mTotalContentLength = -1L;
  
  public TTAPkgDownloader(File paramFile, PkgDownloadListener paramPkgDownloadListener) {
    this.mSavePkgFile = paramFile;
    this.mPkgDownloadListener = paramPkgDownloadListener;
  }
  
  private void decodeDataForContentLength() {
    // Byte code:
    //   0: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   3: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   6: iconst_0
    //   7: iconst_2
    //   8: anewarray java/lang/Enum
    //   11: dup
    //   12: iconst_0
    //   13: getstatic com/tt/miniapp/settings/keys/Settings.BDP_TTPKG_CONFIG : Lcom/tt/miniapp/settings/keys/Settings;
    //   16: aastore
    //   17: dup
    //   18: iconst_1
    //   19: getstatic com/tt/miniapp/settings/keys/Settings$BdpTtPkgConfig.PRELOAD_REAL_CONTENT_LENGTH : Lcom/tt/miniapp/settings/keys/Settings$BdpTtPkgConfig;
    //   22: aastore
    //   23: invokestatic getBoolean : (Landroid/content/Context;Z[Ljava/lang/Enum;)Z
    //   26: ifne -> 51
    //   29: ldc 'TTAPkgDownloader'
    //   31: iconst_1
    //   32: anewarray java/lang/Object
    //   35: dup
    //   36: iconst_0
    //   37: ldc 'decodeDataForContentLength use fakeContentLength'
    //   39: aastore
    //   40: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   43: aload_0
    //   44: ldc2_w 5242880
    //   47: putfield mTotalContentLength : J
    //   50: return
    //   51: aload_0
    //   52: getfield mSavePkgFile : Ljava/io/File;
    //   55: invokevirtual length : ()J
    //   58: lstore_1
    //   59: ldc 'TTAPkgDownloader'
    //   61: iconst_2
    //   62: anewarray java/lang/Object
    //   65: dup
    //   66: iconst_0
    //   67: ldc 'decodeDataForContentLength savePkgFileLength:'
    //   69: aastore
    //   70: dup
    //   71: iconst_1
    //   72: lload_1
    //   73: invokestatic valueOf : (J)Ljava/lang/Long;
    //   76: aastore
    //   77: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   80: lload_1
    //   81: ldc2_w 20480
    //   84: lcmp
    //   85: ifge -> 89
    //   88: return
    //   89: invokestatic currentTimeMillis : ()J
    //   92: lstore_1
    //   93: new com/tt/miniapp/ttapkgdecoder/reader/TTAPkgReader
    //   96: dup
    //   97: new com/tt/miniapp/streamloader/TTAPkgDownloader$DownloadDiskSource
    //   100: dup
    //   101: aload_0
    //   102: aload_0
    //   103: getfield mSavePkgFile : Ljava/io/File;
    //   106: invokespecial <init> : (Lcom/tt/miniapp/streamloader/TTAPkgDownloader;Ljava/io/File;)V
    //   109: invokespecial <init> : (Lcom/tt/miniapp/ttapkgdecoder/source/ISource;)V
    //   112: astore_3
    //   113: aload_3
    //   114: astore #4
    //   116: aload_3
    //   117: invokevirtual checkMagicString : ()Z
    //   120: ifeq -> 139
    //   123: aload_3
    //   124: astore #4
    //   126: aload_3
    //   127: invokevirtual readVersion : ()I
    //   130: pop
    //   131: aload_3
    //   132: astore #4
    //   134: aload_3
    //   135: invokevirtual readTTPkgInfo : ()Lcom/tt/miniapp/ttapkgdecoder/TTAPkgInfo;
    //   138: pop
    //   139: aload_3
    //   140: astore #4
    //   142: aload_0
    //   143: aload_3
    //   144: invokevirtual getByteSize : ()J
    //   147: putfield mTotalContentLength : J
    //   150: aload_3
    //   151: invokevirtual isReleased : ()Z
    //   154: ifne -> 213
    //   157: goto -> 209
    //   160: astore #5
    //   162: goto -> 176
    //   165: astore_3
    //   166: aconst_null
    //   167: astore #4
    //   169: goto -> 255
    //   172: astore #5
    //   174: aconst_null
    //   175: astore_3
    //   176: aload_3
    //   177: astore #4
    //   179: ldc 'TTAPkgDownloader'
    //   181: iconst_2
    //   182: anewarray java/lang/Object
    //   185: dup
    //   186: iconst_0
    //   187: ldc 'decodeDataForContentLengthFail:'
    //   189: aastore
    //   190: dup
    //   191: iconst_1
    //   192: aload #5
    //   194: aastore
    //   195: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   198: aload_3
    //   199: ifnull -> 213
    //   202: aload_3
    //   203: invokevirtual isReleased : ()Z
    //   206: ifne -> 213
    //   209: aload_3
    //   210: invokevirtual release : ()V
    //   213: ldc 'TTAPkgDownloader'
    //   215: iconst_4
    //   216: anewarray java/lang/Object
    //   219: dup
    //   220: iconst_0
    //   221: ldc 'decodeDataForContentLength duration:'
    //   223: aastore
    //   224: dup
    //   225: iconst_1
    //   226: invokestatic currentTimeMillis : ()J
    //   229: lload_1
    //   230: lsub
    //   231: invokestatic valueOf : (J)Ljava/lang/Long;
    //   234: aastore
    //   235: dup
    //   236: iconst_2
    //   237: ldc 'mTotalContentLength:'
    //   239: aastore
    //   240: dup
    //   241: iconst_3
    //   242: aload_0
    //   243: getfield mTotalContentLength : J
    //   246: invokestatic valueOf : (J)Ljava/lang/Long;
    //   249: aastore
    //   250: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   253: return
    //   254: astore_3
    //   255: aload #4
    //   257: ifnull -> 273
    //   260: aload #4
    //   262: invokevirtual isReleased : ()Z
    //   265: ifne -> 273
    //   268: aload #4
    //   270: invokevirtual release : ()V
    //   273: aload_3
    //   274: athrow
    // Exception table:
    //   from	to	target	type
    //   93	113	172	java/lang/Exception
    //   93	113	165	finally
    //   116	123	160	java/lang/Exception
    //   116	123	254	finally
    //   126	131	160	java/lang/Exception
    //   126	131	254	finally
    //   134	139	160	java/lang/Exception
    //   134	139	254	finally
    //   142	150	160	java/lang/Exception
    //   142	150	254	finally
    //   179	198	254	finally
  }
  
  private RandomAccessFile getRandomAccessFileOfSavePkg() {
    try {
      if (!this.mSavePkgFile.exists()) {
        this.mSavePkgFile.getParentFile().mkdirs();
        this.mSavePkgFile.createNewFile();
      } 
      return new RandomAccessFile(this.mSavePkgFile, "rw");
    } catch (Exception exception) {
      AppBrandLogger.e("TTAPkgDownloader", new Object[] { exception });
      return null;
    } 
  }
  
  public boolean isDownloading() {
    synchronized (this.mOperateLock) {
      if (!this.mStop && this.mDownloading)
        return true; 
    } 
    boolean bool = false;
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=SYNTHETIC_LOCAL_VARIABLE_2} */
    return bool;
  }
  
  public boolean isStopped() {
    synchronized (this.mOperateLock) {
      return this.mStop;
    } 
  }
  
  public void loadWithUrl(OkHttpSource paramOkHttpSource, String paramString, RandomAccessFile paramRandomAccessFile) throws Exception {
    AppBrandLogger.i("TTAPkgDownloader", new Object[] { "loadWithUrl url:", paramString });
    this.mLoadFileLength = paramRandomAccessFile.length();
    paramRandomAccessFile.seek(this.mLoadFileLength);
    if (this.mStop) {
      AppBrandLogger.i("TTAPkgDownloader", new Object[] { "loadWithUrl stop before getSource" });
      this.mPkgDownloadListener.onStop();
      return;
    } 
    paramOkHttpSource.setOnProgressChangeListener(this);
    paramOkHttpSource.start();
    DownloadFetcher downloadFetcher = this.mDownloadFetcher;
    if (downloadFetcher != null)
      this.mStatusCode = downloadFetcher.mStatusCode; 
    this.mContentLength = paramOkHttpSource.getByteSize();
    this.mTotalContentLength = paramOkHttpSource.getByteSize() + this.mLoadFileLength;
    AppBrandLogger.i("TTAPkgDownloader", new Object[] { "loadWithUrl start url:", paramString, " contentLength:", Long.valueOf(this.mTotalContentLength), " loadFileLength:", Long.valueOf(this.mLoadFileLength) });
    while (true) {
      while (this.mStop) {
        synchronized (this.mOperateLock) {
          if (this.mEnableResume) {
            this.mEnableResume = false;
            this.mStop = false;
            this.mOperateLock.notifyAll();
            continue;
          } 
          null = new StringBuilder(" stop loadWithUrl url:");
          null.append(paramString);
          null.append(" mTotalContentLength:");
          null.append(this.mTotalContentLength);
          null.append(" downloadFileSize:");
          null.append(paramRandomAccessFile.length());
          AppBrandLogger.i("TTAPkgDownloader", new Object[] { null.toString() });
          paramOkHttpSource.close();
          this.mPkgDownloadListener.onStop();
          return;
        } 
      } 
      byte[] arrayOfByte = new byte[8192];
      int i = paramOkHttpSource.read(arrayOfByte);
      if (i == -1) {
        AppBrandLogger.d("TTAPkgDownloader", new Object[] { "loadWithUrl finish. url:", paramString, " contentLength:", Long.valueOf(this.mTotalContentLength), " downloadFileSize:", Long.valueOf(paramRandomAccessFile.length()) });
        paramRandomAccessFile.close();
        paramOkHttpSource.close();
        this.mPkgDownloadListener.onFinish(this.mStatusCode, this.mContentLength);
        return;
      } 
      paramRandomAccessFile.write(arrayOfByte, 0, i);
      if (this.mTotalContentLength <= 0L)
        decodeDataForContentLength(); 
    } 
  }
  
  public void onPkgDownloadFail(String paramString1, String paramString2) {
    this.mPkgDownloadListener.onFail(paramString1, paramString2, this.mStatusCode, this.mContentLength);
  }
  
  public void onProgressChange(long paramLong) {
    long l = this.mTotalContentLength;
    if (l <= 0L) {
      this.mPkgDownloadListener.onProgressChange(0);
      return;
    } 
    int j = (int)((paramLong + this.mLoadFileLength) * 100L / l);
    int i = j;
    if (j >= 100)
      i = 99; 
    this.mPkgDownloadListener.onProgressChange(i);
  }
  
  public void startAsyncDownload(final String url, DownloadFetcher paramDownloadFetcher) {
    if (TextUtils.isEmpty(url)) {
      onPkgDownloadFail(url, "empty url");
      return;
    } 
    final RandomAccessFile accessSaveFile = getRandomAccessFileOfSavePkg();
    if (randomAccessFile == null) {
      onPkgDownloadFail(url, "local file is null");
      return;
    } 
    this.mStatusCode = -1;
    this.mContentLength = -1L;
    this.mDownloadFetcher = paramDownloadFetcher;
    final OkHttpSource source = new OkHttpSource(url, paramDownloadFetcher);
    synchronized (this.mOperateLock) {
      this.mDownloading = true;
      this.mStop = false;
      this.mEnableResume = false;
      LaunchThreadPool.getInst().execute(new Runnable() {
            public void run() {
              try {
                TTAPkgDownloader.this.loadWithUrl(source, url, accessSaveFile);
              } catch (Exception exception) {
                source.close();
                TTAPkgDownloader.this.onPkgDownloadFail(url, Log.getStackTraceString(exception));
                AppBrandLogger.e("TTAPkgDownloader", new Object[] { "loadWithUrl fail", exception });
              } 
              synchronized (TTAPkgDownloader.this.mOperateLock) {
                TTAPkgDownloader.this.mDownloading = false;
                TTAPkgDownloader.this.mOperateLock.notifyAll();
                return;
              } 
            }
          });
      return;
    } 
  }
  
  public void stopAsyncDownload() {
    synchronized (this.mOperateLock) {
      this.mStop = true;
      this.mEnableResume = false;
      return;
    } 
  }
  
  public void tryResumeDownload() {
    synchronized (this.mOperateLock) {
      if (this.mStop && this.mDownloading) {
        this.mEnableResume = true;
        try {
          this.mOperateLock.wait();
        } catch (Exception exception) {
          AppBrandLogger.e("TTAPkgDownloader", new Object[] { "tryResumeDownload", exception });
        } 
      } 
      return;
    } 
  }
  
  class DownloadDiskSource extends DiskSource {
    private long mFileLength;
    
    private long mReadData;
    
    public DownloadDiskSource(File param1File) {
      super(param1File);
      this.mFileLength = param1File.length();
    }
    
    private void checkReadData(long param1Long) throws IOException {
      if (this.mReadData + param1Long > this.mFileLength) {
        long l = this.idx;
        AppBrandLogger.w("TTAPkgDownloader", new Object[] { "checkReadData warning mReadData:", Long.valueOf(this.mReadData), "filePointer:", Long.valueOf(l) });
        this.mReadData = l;
        l = this.mReadData;
        if (l + param1Long > this.mFileLength) {
          AppBrandLogger.w("TTAPkgDownloader", new Object[] { "checkReadData warning mReadData:", Long.valueOf(l), "dataLength:", Long.valueOf(param1Long), "mFileLength:", Long.valueOf(this.mFileLength) });
          throw new IOException("读取数据失败，目标数据超出本地下载的 pkg 包数据长度");
        } 
      } 
      this.mReadData += param1Long;
    }
    
    public long getByteSize() {
      return -1L;
    }
    
    public int read(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) throws IOException {
      checkReadData(param1Int2);
      return super.read(param1ArrayOfbyte, param1Int1, param1Int2);
    }
    
    public void readFully(byte[] param1ArrayOfbyte) throws IOException {
      checkReadData(param1ArrayOfbyte.length);
      super.readFully(param1ArrayOfbyte);
    }
  }
  
  public static interface PkgDownloadListener {
    void onFail(String param1String1, String param1String2, int param1Int, long param1Long);
    
    void onFinish(int param1Int, long param1Long);
    
    void onProgressChange(int param1Int);
    
    void onStop();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\TTAPkgDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */