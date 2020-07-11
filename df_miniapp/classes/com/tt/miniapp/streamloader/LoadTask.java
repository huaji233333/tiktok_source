package com.tt.miniapp.streamloader;

import android.os.Looper;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.cache.DataCenter;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.ttapkgdecoder.DecoderCallback;
import com.tt.miniapp.ttapkgdecoder.DefaultExtractFilters;
import com.tt.miniapp.ttapkgdecoder.ExtractFilter;
import com.tt.miniapp.ttapkgdecoder.TTAPkgDecoder;
import com.tt.miniapp.ttapkgdecoder.TTAPkgFile;
import com.tt.miniapp.ttapkgdecoder.TTAPkgInfo;
import com.tt.miniapp.ttapkgdecoder.utils.DecodeException;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.IOUtils;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadTask {
  public volatile boolean isReady;
  
  public final DataCenter mDataCache;
  
  private TTAPkgDecoder mDecoder;
  
  public ConcurrentHashMap<String, String> mExtractFile2RealPath;
  
  public final ExtractFilter mExtractFilter;
  
  public HashMap<TTAPkgFile, Future<String>> mExtractFutureMap;
  
  public final String mExtractPath;
  
  private ExecutorService mExtractThreadPool;
  
  private boolean mIsFirstLaunch;
  
  private Boolean mIsUseLocalPkg;
  
  private final String mLoadPkgType;
  
  public final PkgDownloadEntity mPkgDownloadInfo;
  
  private final RequestType mSourceType;
  
  public TTAPkgInfo mTTAPkgInfo;
  
  private final File mTTApkgFile;
  
  public int mTTApkgVersion = -1;
  
  public LoadTask(AppInfoEntity paramAppInfoEntity, File paramFile, String paramString1, String paramString2, RequestType paramRequestType, boolean paramBoolean, int paramInt) {
    this.mPkgDownloadInfo = new PkgDownloadEntity(paramAppInfoEntity);
    this.mTTApkgFile = paramFile;
    this.mExtractPath = paramString1;
    this.mLoadPkgType = paramString2;
    this.mSourceType = paramRequestType;
    this.mIsFirstLaunch = paramBoolean;
    this.mExtractFilter = DefaultExtractFilters.merge(new ExtractFilter[] { DefaultExtractFilters.media(), DefaultExtractFilters.zip() });
    this.mDataCache = new DataCenter(paramFile, paramInt);
  }
  
  private void loadWithLocalFile(File paramFile, StreamLoadListener paramStreamLoadListener) {
    TimeLogger.getInstance().logTimeDuration(new String[] { "LoadTask_loadWithLocalFile" });
    this.mIsUseLocalPkg = Boolean.TRUE;
    ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEventWithValue("isPkgExist", Boolean.valueOf(true));
    this.mDecoder = TTAPkgDecoder.create(paramFile).asyncLoad(new DefaultDecodeCallback(paramStreamLoadListener, paramFile));
  }
  
  public Future<String> extractFile(final String extractPath, final TTAPkgFile ttaPkgFile) {
    if (this.mExtractThreadPool == null)
      this.mExtractThreadPool = _lancet.com_ss_android_ugc_aweme_lancet_ThreadPoolLancet_newCachedThreadPool(new ThreadFactory() {
            public Thread newThread(Runnable param1Runnable) {
              return new Thread(param1Runnable, "pkgFileExtractThread");
            }
          }); 
    return this.mExtractThreadPool.submit(new Callable<String>() {
          public String call() {
            InputStream inputStream = LoadTask.this.requestStream(ttaPkgFile);
            File file2 = new File(extractPath);
            if (!file2.exists())
              file2.mkdirs(); 
            ttaPkgFile.saveTo(file2, inputStream);
            if (LoadTask.this.mExtractFutureMap != null)
              LoadTask.this.mExtractFutureMap.remove(ttaPkgFile); 
            File file1 = new File(extractPath, ttaPkgFile.getFileName());
            if (file1.exists()) {
              LoadTask.this.mExtractFile2RealPath.putIfAbsent(ttaPkgFile.getFileName(), file1.getAbsolutePath());
              return file1.getAbsolutePath();
            } 
            return "";
          }
        });
  }
  
  public TTAPkgFile findFile(String paramString) {
    if (this.mTTAPkgInfo != null) {
      paramString = FileManager.inst().getSchemaFilePath(paramString);
      if (paramString != null) {
        TTAPkgFile tTAPkgFile = this.mTTAPkgInfo.findFile(paramString);
        if (tTAPkgFile != null) {
          ((FileAccessLogger)AppbrandApplicationImpl.getInst().getService(FileAccessLogger.class)).logFileAccess(tTAPkgFile.getFileName());
          return tTAPkgFile;
        } 
      } 
    } 
    return null;
  }
  
  public AppInfoEntity getAppInfo() {
    return this.mPkgDownloadInfo.getAppInfo();
  }
  
  public String getLoadPkgType() {
    return this.mLoadPkgType;
  }
  
  public RequestType getSourceType() {
    return this.mSourceType;
  }
  
  public String getStringCache(String paramString, byte[] paramArrayOfbyte) {
    return this.mDataCache.getStringCache(paramString, paramArrayOfbyte);
  }
  
  public TTAPkgInfo getTTAPkgInfo() {
    return this.mTTAPkgInfo;
  }
  
  public int getTTApkgVersion() {
    return this.mTTApkgVersion;
  }
  
  public boolean isDirectoryOfPkg(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    String str = FileManager.inst().getSchemaFilePath(paramString);
    if (str.startsWith("./")) {
      paramString = str.substring(2);
    } else {
      paramString = str;
      if (str.startsWith("/"))
        paramString = str.substring(1); 
    } 
    TTAPkgInfo tTAPkgInfo = this.mTTAPkgInfo;
    if (tTAPkgInfo != null)
      for (String str1 : tTAPkgInfo.getFileNames()) {
        if (str1 != null) {
          int i = str1.lastIndexOf("/");
          if (i > 0 && TextUtils.equals(paramString, str1.substring(0, i)))
            return true; 
        } 
      }  
    return false;
  }
  
  public boolean isFirstLaunch() {
    return this.mIsFirstLaunch;
  }
  
  public boolean isReady() {
    return this.isReady;
  }
  
  public Boolean isUseLocalPkg() {
    return this.mIsUseLocalPkg;
  }
  
  public void loadWithUrl(String paramString, File paramFile, StreamLoadListener paramStreamLoadListener) {
    TimeLogger.getInstance().logTimeDuration(new String[] { "LoadTask_loadWithUrl" });
    this.mIsUseLocalPkg = Boolean.FALSE;
    ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEventWithValue("isPkgExist", Boolean.valueOf(false));
    this.mDecoder = TTAPkgDecoder.create(paramString).setNetDownloader(new FileSaveStreamFetcher(paramFile, this.mPkgDownloadInfo.getCompressType())).asyncLoad(new DefaultDecodeCallback(paramStreamLoadListener, paramFile));
  }
  
  public void release() {
    ExecutorService executorService = this.mExtractThreadPool;
    if (executorService != null)
      executorService.shutdown(); 
    DataCenter dataCenter = this.mDataCache;
    if (dataCenter != null)
      dataCenter.release(); 
  }
  
  public byte[] requestBytes(TTAPkgFile paramTTAPkgFile) {
    if (paramTTAPkgFile == null)
      return null; 
    if (Looper.getMainLooper() == Looper.myLooper()) {
      StringBuilder stringBuilder = new StringBuilder("不要在主线程阻塞等待文件请求: ");
      stringBuilder.append(paramTTAPkgFile.getFileName());
      AppBrandLogger.eWithThrowable("tma_LoadTask", stringBuilder.toString(), new Throwable());
    } 
    return this.mDataCache.getOrWait(paramTTAPkgFile);
  }
  
  public InputStream requestStream(TTAPkgFile paramTTAPkgFile) {
    return this.mDataCache.getStream(paramTTAPkgFile);
  }
  
  public void startExtractFiles(final String extractPath, final ExtractFilter filter, final TTAPkgInfo info) {
    if (this.mExtractFile2RealPath == null)
      this.mExtractFile2RealPath = new ConcurrentHashMap<String, String>(); 
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            for (TTAPkgFile tTAPkgFile : info.getFiles()) {
              if (!TextUtils.isEmpty(extractPath)) {
                ExtractFilter extractFilter = filter;
                if (extractFilter != null && extractFilter.filter(tTAPkgFile.getFileName())) {
                  Future<String> future;
                  StringBuilder stringBuilder = new StringBuilder("extracting file ");
                  stringBuilder.append(tTAPkgFile.getFileName());
                  stringBuilder.append(" to ");
                  stringBuilder.append(extractPath);
                  AppBrandLogger.d("tma_LoadTask", new Object[] { stringBuilder.toString() });
                  File file = new File(extractPath, tTAPkgFile.getFileName());
                  if (!file.exists() || file.length() != tTAPkgFile.getSize()) {
                    future = LoadTask.this.extractFile(extractPath, tTAPkgFile);
                    if (LoadTask.this.mExtractFutureMap == null)
                      LoadTask.this.mExtractFutureMap = new HashMap<TTAPkgFile, Future<String>>(); 
                    LoadTask.this.mExtractFutureMap.put(tTAPkgFile, future);
                    continue;
                  } 
                  LoadTask.this.mExtractFile2RealPath.put(tTAPkgFile.getFileName(), future.getAbsolutePath());
                } 
              } 
            } 
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  public void startLoad(StreamLoadListener paramStreamLoadListener) {
    File file = this.mTTApkgFile;
    if (file != null && file.exists()) {
      loadWithLocalFile(this.mTTApkgFile, paramStreamLoadListener);
      return;
    } 
    String str = this.mPkgDownloadInfo.nextPkgUrl();
    if (!TextUtils.isEmpty(str)) {
      loadWithUrl(str, this.mTTApkgFile, paramStreamLoadListener);
      return;
    } 
    paramStreamLoadListener.onStreamLoadError(-5, "empty url");
  }
  
  public String waitExtractFinish(TTAPkgFile paramTTAPkgFile) {
    HashMap<TTAPkgFile, Future<String>> hashMap = this.mExtractFutureMap;
    if (hashMap != null) {
      Future<String> future = hashMap.get(paramTTAPkgFile);
      if (future != null)
        try {
          return future.get();
        } catch (ExecutionException|InterruptedException executionException) {} 
    } 
    ConcurrentHashMap<String, String> concurrentHashMap = this.mExtractFile2RealPath;
    return (concurrentHashMap != null && concurrentHashMap.containsKey(paramTTAPkgFile.getFileName())) ? this.mExtractFile2RealPath.get(paramTTAPkgFile.getFileName()) : "";
  }
  
  class DefaultDecodeCallback implements DecoderCallback {
    private final File mCacheFile;
    
    private final StreamLoadListener mListener;
    
    public DefaultDecodeCallback(StreamLoadListener param1StreamLoadListener, File param1File) {
      this.mListener = param1StreamLoadListener;
      this.mCacheFile = param1File;
    }
    
    private void notifyRetry(String param1String1, String param1String2, String param1String3) {
      StreamLoadListener streamLoadListener = this.mListener;
      if (streamLoadListener != null)
        streamLoadListener.onRetry(param1String1, param1String2, param1String3); 
    }
    
    public void onDecodeFail(int param1Int, String param1String) {
      File file = this.mCacheFile;
      if (file != null && file.exists())
        this.mCacheFile.delete(); 
      String str2 = LoadTask.this.mPkgDownloadInfo.getPkgUrl();
      String str1 = str2;
      if (TextUtils.isEmpty(str2))
        str1 = "Default failUrl, maybe decode local file failed."; 
      str2 = LoadTask.this.mPkgDownloadInfo.nextPkgUrl();
      if (!TextUtils.isEmpty(str2)) {
        notifyRetry(param1String, str1, str2);
        LoadTask.this.loadWithUrl(str2, this.mCacheFile, this.mListener);
        return;
      } 
      if (LoadTask.this.mPkgDownloadInfo.enableFallback()) {
        LoadTask.this.mPkgDownloadInfo.reset();
        LoadTask loadTask = LoadTask.this;
        loadTask.loadWithUrl(loadTask.mPkgDownloadInfo.getPkgUrl(), this.mCacheFile, this.mListener);
        try {
          JSONObject jSONObject = new JSONObject();
          jSONObject.put("status", "all br url download fail and retry original url");
          AppBrandMonitor.statusRate("mp_start_error", 1017, jSONObject);
        } catch (JSONException jSONException) {
          AppBrandLogger.e("tma_LoadTask", new Object[] { jSONException.getMessage() });
        } 
        AppBrandLogger.e("tma_LoadTask", new Object[] { "all br url download fail" });
        return;
      } 
      LoadTask.this.isReady = false;
      StreamLoadListener streamLoadListener = this.mListener;
      if (streamLoadListener != null)
        streamLoadListener.onStreamLoadError(param1Int, (String)jSONException); 
    }
    
    public void onDecodeFile(TTAPkgFile param1TTAPkgFile, byte[] param1ArrayOfbyte) {
      LoadTask.this.mDataCache.onFileAvailable(param1TTAPkgFile, param1ArrayOfbyte);
    }
    
    public void onDecodeFilePart(TTAPkgFile param1TTAPkgFile, byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
      LoadTask.this.mDataCache.onDecodePart(param1TTAPkgFile, param1ArrayOfbyte, param1Int1, param1Int2);
    }
    
    public void onDecodeFileStart(TTAPkgFile param1TTAPkgFile, byte[] param1ArrayOfbyte) {
      LoadTask.this.mDataCache.onDecodeStart(param1TTAPkgFile, param1ArrayOfbyte);
    }
    
    public void onDecodeFinish(TTAPkgInfo param1TTAPkgInfo) {
      LoadTask.this.isReady = true;
      StreamLoadListener streamLoadListener = this.mListener;
      if (streamLoadListener != null)
        streamLoadListener.onStreamLoadFinish(param1TTAPkgInfo); 
    }
    
    public void onDecodeProgress(int param1Int) {
      StreamLoadListener streamLoadListener = this.mListener;
      if (streamLoadListener != null)
        streamLoadListener.onDownloadProgress(param1Int); 
    }
    
    public void onLoadHeader(int param1Int, TTAPkgInfo param1TTAPkgInfo) throws DecodeException {
      File file;
      LoadTask loadTask = LoadTask.this;
      loadTask.mTTAPkgInfo = param1TTAPkgInfo;
      loadTask.mTTApkgVersion = param1Int;
      if (loadTask.mTTApkgVersion < 2) {
        file = this.mCacheFile;
        if (file != null && file.exists())
          IOUtils.delete(this.mCacheFile); 
        throw new DecodeException(-7);
      } 
      if (!LoadTask.this.isReady) {
        loadTask = LoadTask.this;
        loadTask.isReady = true;
        loadTask.mDataCache.onHeaderAvailable((TTAPkgInfo)file);
        loadTask = LoadTask.this;
        loadTask.startExtractFiles(loadTask.mExtractPath, LoadTask.this.mExtractFilter, (TTAPkgInfo)file);
        StreamLoadListener streamLoadListener = this.mListener;
        if (streamLoadListener != null)
          streamLoadListener.onHeadInfoLoadSuccess(); 
      } 
    }
  }
  
  class LoadTask {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\LoadTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */