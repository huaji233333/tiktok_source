package com.tt.miniapp.offlinezip;

import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.net.download.AbstractDownloadListener;
import com.tt.miniapp.net.download.DownloadManager;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.StorageUtil;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.option.q.f;
import com.tt.option.q.i;
import d.f.b.g;
import d.f.b.l;
import d.m.p;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import okhttp3.ae;

public final class OfflineZipUpdateManager implements IOfflineZipService {
  public static final OfflineZipUpdateManager INSTANCE = new OfflineZipUpdateManager();
  
  private static final ConcurrentLinkedQueue<OfflineZipUpdateTask> mUpdateTasks = new ConcurrentLinkedQueue<OfflineZipUpdateTask>();
  
  private final OfflineZipUpdateTask getTaskWithModuleName(String paramString) {
    for (OfflineZipUpdateTask offlineZipUpdateTask : mUpdateTasks) {
      if (l.a(offlineZipUpdateTask.getEntity().getModuleName(), paramString))
        return offlineZipUpdateTask; 
    } 
    return null;
  }
  
  public final String getDEBUG_FLAG() {
    return this.$$delegate_0.getDEBUG_FLAG();
  }
  
  public final String getEXTERNAL_OFFLINE_PATH() {
    return this.$$delegate_0.getEXTERNAL_OFFLINE_PATH();
  }
  
  public final int getInternalOfflineZipVersion(Context paramContext) {
    l.b(paramContext, "context");
    return this.$$delegate_0.getInternalOfflineZipVersion(paramContext);
  }
  
  public final String getMD5_FILE_SUFFIX() {
    return this.$$delegate_0.getMD5_FILE_SUFFIX();
  }
  
  public final File getModuleFolder(Context paramContext, String paramString) {
    return new File(AppbrandUtil.getOfflineZipDir(paramContext), paramString);
  }
  
  public final String getSpecifiedOfflineModuleVersion(Context paramContext, File paramFile, String paramString) {
    l.b(paramContext, "context");
    l.b(paramFile, "rootPath");
    l.b(paramString, "moduleName");
    return this.$$delegate_0.getSpecifiedOfflineModuleVersion(paramContext, paramFile, paramString);
  }
  
  public final String getSpecifiedOfflineModuleVersion(Context paramContext, String paramString) {
    l.b(paramContext, "context");
    l.b(paramString, "moduleName");
    return this.$$delegate_0.getSpecifiedOfflineModuleVersion(paramContext, paramString);
  }
  
  public final File getTempFolder(Context paramContext, String paramString) {
    return new File(AppbrandUtil.getAppbrandBaseFile(paramContext), paramString);
  }
  
  public final File getTempZipFile(Context paramContext, String paramString1, String paramString2) {
    File file = getTempFolder(paramContext, paramString1);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString2);
    stringBuilder.append(".zip");
    return new File(file, stringBuilder.toString());
  }
  
  public final String getZIP_FILE_SUFFIX() {
    return this.$$delegate_0.getZIP_FILE_SUFFIX();
  }
  
  public final void init(Context paramContext) {
    l.b(paramContext, "context");
    ThreadUtil.runOnWorkThread(new OfflineZipUpdateManager$init$1(paramContext), Schedulers.longIO(), false);
  }
  
  public final boolean isOfflineModuleNeedUpdate(Context paramContext, String paramString1, String paramString2) {
    l.b(paramContext, "context");
    l.b(paramString1, "moduleName");
    l.b(paramString2, "md5");
    return this.$$delegate_0.isOfflineModuleNeedUpdate(paramContext, paramString1, paramString2);
  }
  
  public final void setInternalOfflineZipVersion(Context paramContext, int paramInt) {
    l.b(paramContext, "context");
    this.$$delegate_0.setInternalOfflineZipVersion(paramContext, paramInt);
  }
  
  public final void setSpecifiedOfflineModuleVersion(File paramFile, String paramString) {
    l.b(paramFile, "moduleDir");
    l.b(paramString, "md5");
    this.$$delegate_0.setSpecifiedOfflineModuleVersion(paramFile, paramString);
  }
  
  public final void startUpdateOfflineZip(Context paramContext) {
    ThreadUtil.runOnWorkThread(new OfflineZipUpdateManager$startUpdateOfflineZip$1(paramContext), Schedulers.longIO(), false);
  }
  
  public final void unzipExternalOfflineModule(Context paramContext) {
    File file = new File(paramContext.getExternalCacheDir(), getEXTERNAL_OFFLINE_PATH());
    String[] arrayOfString = file.list(OfflineZipUpdateManager$unzipExternalOfflineModule$moduleNames$1.INSTANCE);
    if (arrayOfString != null) {
      int j = arrayOfString.length;
      for (int i = 0; i < j; i++) {
        String str = arrayOfString[i];
        try {
          File file1 = new File(file, str);
          File file2 = AppbrandUtil.getOfflineZipDir(paramContext);
          l.a(str, "it");
          File file3 = new File(file2, p.a(str, INSTANCE.getZIP_FILE_SUFFIX(), "", false, 4, null));
          if (file3.exists())
            IOUtils.delete(file3); 
          String str1 = file1.getAbsolutePath();
          l.a(file2, "offlinePath");
          IOUtils.unZipFolder(str1, file2.getAbsolutePath());
          INSTANCE.setSpecifiedOfflineModuleVersion(file3, INSTANCE.getDEBUG_FLAG());
          IOUtils.delete(file1);
        } catch (Exception exception) {
          AppBrandLogger.e("tma_OfflineZipUpdateManager", new Object[] { "unzipExternalOfflineModule", exception });
        } 
      } 
    } 
  }
  
  public final void unzipInternalOfflineZipIfNeeded(Context paramContext) {
    if (getInternalOfflineZipVersion(paramContext) == 1) {
      AppBrandLogger.d("tma_OfflineZipUpdateManager", new Object[] { "don't need unzip internal offline zip" });
      return;
    } 
    StringBuilder stringBuilder1 = new StringBuilder("offline");
    stringBuilder1.append(getZIP_FILE_SUFFIX());
    if (!IOUtils.isAssetsFileExist(paramContext, stringBuilder1.toString())) {
      AppBrandLogger.d("tma_OfflineZipUpdateManager", new Object[] { "internal offline.zip does not exist" });
      return;
    } 
    File file1 = getTempFolder(paramContext, "internal_offline");
    File file2 = getTempZipFile(paramContext, "internal_offline", "offline");
    StringBuilder stringBuilder2 = new StringBuilder("offline");
    stringBuilder2.append(getZIP_FILE_SUFFIX());
    IOUtils.copyAssets(paramContext, stringBuilder2.toString(), file2.getAbsolutePath());
    if (file2.exists()) {
      try {
        IOUtils.unZipFolder(file2.getAbsolutePath(), file1.getAbsolutePath());
        IOUtils.delete(file2);
        File[] arrayOfFile = (new File(file1, "offline")).listFiles();
        File file = AppbrandUtil.getOfflineZipDir(paramContext);
        if (!file.exists())
          file.mkdir(); 
        l.a(arrayOfFile, "internalModules");
        int j = arrayOfFile.length;
        for (int i = 0; i < j; i++) {
          file = arrayOfFile[i];
          OfflineZipUpdateManager offlineZipUpdateManager = INSTANCE;
          l.a(file, "it");
          String str = file.getName();
          l.a(str, "it.name");
          File file3 = offlineZipUpdateManager.getModuleFolder(paramContext, str);
          try {
            OfflineZipUpdateManager offlineZipUpdateManager1 = INSTANCE;
            String str1 = file.getName();
            l.a(str1, "it.name");
            if (TextUtils.isEmpty(offlineZipUpdateManager1.getSpecifiedOfflineModuleVersion(paramContext, str1))) {
              IOUtils.delete(file3);
              file.renameTo(file3);
              StringBuilder stringBuilder = new StringBuilder("use internal ");
              stringBuilder.append(file.getName());
              AppBrandLogger.d("tma_OfflineZipUpdateManager", new Object[] { stringBuilder.toString() });
            } 
            IOUtils.delete(file);
          } catch (Exception exception) {
            AppBrandLogger.e("tma_OfflineZipUpdateManager", new Object[] { "unzipInternalOfflineZipIfNeeded", exception });
            IOUtils.delete(file);
          } finally {}
        } 
      } catch (Exception exception) {
        AppBrandLogger.e("tma_OfflineZipUpdateManager", new Object[] { "unzipInternalOfflineZipIfNeeded", exception });
      } finally {}
      IOUtils.delete(file1);
    } 
    setInternalOfflineZipVersion(paramContext, 1);
  }
  
  public final void updateModule(Context paramContext, ArrayList<OfflineZipEntity> paramArrayList, OnOfflineZipUpdateResultListener paramOnOfflineZipUpdateResultListener) {
    l.b(paramContext, "context");
    l.b(paramArrayList, "entityList");
    if ((paramArrayList.isEmpty() ^ true) != 0) {
      for (OfflineZipEntity offlineZipEntity : paramArrayList) {
        OfflineZipUpdateTask offlineZipUpdateTask2 = INSTANCE.getTaskWithModuleName(offlineZipEntity.getModuleName());
        OfflineZipUpdateTask offlineZipUpdateTask1 = offlineZipUpdateTask2;
        if (offlineZipUpdateTask2 == null)
          offlineZipUpdateTask1 = new OfflineZipUpdateTask(offlineZipEntity, null, 2, null); 
        if (paramOnOfflineZipUpdateResultListener != null)
          offlineZipUpdateTask1.getListeners().add(paramOnOfflineZipUpdateResultListener); 
        if (!mUpdateTasks.contains(offlineZipUpdateTask1))
          mUpdateTasks.offer(offlineZipUpdateTask1); 
      } 
      startUpdateOfflineZip(paramContext);
    } 
  }
  
  public final void useLatestResources(Context paramContext) {
    File file1 = AppbrandUtil.getOfflineUpdateDir(paramContext);
    File file2 = AppbrandUtil.getOfflineZipDir(paramContext);
    String[] arrayOfString = file1.list();
    if (arrayOfString != null) {
      if (!file2.exists())
        file2.mkdir(); 
      int j = arrayOfString.length;
      for (int i = 0; i < j; i++) {
        String str = arrayOfString[i];
        File file3 = new File(file1, str);
        File file4 = new File(file2, str);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("_temp");
        File file5 = new File(file2, stringBuilder.toString());
        try {
          if (file4.exists())
            file4.renameTo(file5); 
          if (!file3.renameTo(file4))
            file5.renameTo(file4); 
          IOUtils.delete(file3);
          IOUtils.delete(file5);
        } catch (Exception exception) {
          AppBrandLogger.e("tma_OfflineZipUpdateManager", new Object[] { "useLatestResources", exception });
          IOUtils.delete(file3);
          IOUtils.delete(file5);
        } finally {}
      } 
    } 
  }
  
  static final class OfflineZipUpdateTask {
    public static final Companion Companion = new Companion(null);
    
    private int downloadDuration;
    
    private OfflineZipEntity entity;
    
    private int errCode;
    
    private String errMsg;
    
    private CopyOnWriteArrayList<OnOfflineZipUpdateResultListener> listeners;
    
    private int mRetryCount;
    
    private OfflineZipStatus updateStatus;
    
    public OfflineZipUpdateTask(OfflineZipEntity param1OfflineZipEntity, OfflineZipStatus param1OfflineZipStatus) {
      this.entity = param1OfflineZipEntity;
      this.updateStatus = param1OfflineZipStatus;
      this.listeners = new CopyOnWriteArrayList<OnOfflineZipUpdateResultListener>();
      this.downloadDuration = -1;
      this.errCode = -1;
      this.errMsg = "";
    }
    
    private final boolean moveTempFolderToOfflineFolder(Context param1Context, OfflineZipEntity param1OfflineZipEntity) {
      String str = param1OfflineZipEntity.getModuleName();
      OfflineZipUpdateManager offlineZipUpdateManager1 = OfflineZipUpdateManager.INSTANCE;
      StringBuilder stringBuilder2 = new StringBuilder("download_offline_");
      stringBuilder2.append(str);
      File file3 = new File(offlineZipUpdateManager1.getTempFolder(param1Context, stringBuilder2.toString()), str);
      File file2 = OfflineZipUpdateManager.INSTANCE.getModuleFolder(param1Context, str);
      File file1 = AppbrandUtil.getOfflineZipDir(param1Context);
      if (!file3.exists())
        return false; 
      if (!file1.exists())
        file1.mkdir(); 
      file1 = file2;
      try {
        boolean bool;
        if (file2.exists()) {
          file1 = AppbrandUtil.getOfflineUpdateDir(param1Context);
          if (!file1.exists())
            file1.mkdir(); 
          file1 = new File(file1, str);
        } 
        IOUtils.delete(file1);
        if (!file3.renameTo(file1)) {
          this.errCode = 9003;
          String str1 = this.errMsg;
          StringBuilder stringBuilder4 = new StringBuilder();
          stringBuilder4.append(str1);
          stringBuilder4.append("move failed\n");
          this.errMsg = stringBuilder4.toString();
          IOUtils.delete(file1);
          bool = false;
        } else {
          bool = true;
        } 
        OfflineZipUpdateManager offlineZipUpdateManager = OfflineZipUpdateManager.INSTANCE;
        StringBuilder stringBuilder = new StringBuilder("download_offline_");
        stringBuilder.append(str);
        IOUtils.delete(offlineZipUpdateManager.getTempFolder(param1Context, stringBuilder.toString()));
        stringBuilder1 = new StringBuilder("clear folder download_offline_");
        stringBuilder1.append(str);
        stringBuilder1.append(" success");
        AppBrandLogger.d("tma_OfflineZipUpdateTask", new Object[] { stringBuilder1.toString() });
        return bool;
      } catch (Exception exception) {
        AppBrandLogger.e("tma_OfflineZipUpdateTask", new Object[] { "moveTempFolderToOfflineFolder", exception });
        this.errCode = 9003;
        String str1 = this.errMsg;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str1);
        stringBuilder.append("move failed\n");
        this.errMsg = stringBuilder.toString();
        OfflineZipUpdateManager offlineZipUpdateManager = OfflineZipUpdateManager.INSTANCE;
        stringBuilder = new StringBuilder("download_offline_");
        stringBuilder.append(str);
        IOUtils.delete(offlineZipUpdateManager.getTempFolder((Context)stringBuilder1, stringBuilder.toString()));
        stringBuilder1 = new StringBuilder("clear folder download_offline_");
        stringBuilder1.append(str);
        stringBuilder1.append(" success");
        AppBrandLogger.d("tma_OfflineZipUpdateTask", new Object[] { stringBuilder1.toString() });
        return false;
      } finally {}
      OfflineZipUpdateManager offlineZipUpdateManager2 = OfflineZipUpdateManager.INSTANCE;
      StringBuilder stringBuilder3 = new StringBuilder("download_offline_");
      stringBuilder3.append(str);
      IOUtils.delete(offlineZipUpdateManager2.getTempFolder((Context)stringBuilder1, stringBuilder3.toString()));
      StringBuilder stringBuilder1 = new StringBuilder("clear folder download_offline_");
      stringBuilder1.append(str);
      stringBuilder1.append(" success");
      AppBrandLogger.d("tma_OfflineZipUpdateTask", new Object[] { stringBuilder1.toString() });
      throw file1;
    }
    
    private final void notifyUpdateResult(boolean param1Boolean) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield listeners : Ljava/util/concurrent/CopyOnWriteArrayList;
      //   6: checkcast java/util/Collection
      //   9: invokeinterface isEmpty : ()Z
      //   14: iconst_1
      //   15: ixor
      //   16: ifeq -> 67
      //   19: aload_0
      //   20: getfield listeners : Ljava/util/concurrent/CopyOnWriteArrayList;
      //   23: iconst_0
      //   24: invokevirtual remove : (I)Ljava/lang/Object;
      //   27: checkcast com/tt/miniapp/offlinezip/OnOfflineZipUpdateResultListener
      //   30: astore_2
      //   31: iload_1
      //   32: ifeq -> 51
      //   35: aload_2
      //   36: aload_0
      //   37: getfield entity : Lcom/tt/miniapp/offlinezip/OfflineZipEntity;
      //   40: invokevirtual getModuleName : ()Ljava/lang/String;
      //   43: invokeinterface onSuccess : (Ljava/lang/String;)V
      //   48: goto -> 2
      //   51: aload_2
      //   52: aload_0
      //   53: getfield entity : Lcom/tt/miniapp/offlinezip/OfflineZipEntity;
      //   56: invokevirtual getModuleName : ()Ljava/lang/String;
      //   59: invokeinterface onFailed : (Ljava/lang/String;)V
      //   64: goto -> 2
      //   67: aload_0
      //   68: monitorexit
      //   69: return
      //   70: astore_2
      //   71: aload_0
      //   72: monitorexit
      //   73: goto -> 78
      //   76: aload_2
      //   77: athrow
      //   78: goto -> 76
      // Exception table:
      //   from	to	target	type
      //   2	31	70	finally
      //   35	48	70	finally
      //   51	64	70	finally
    }
    
    private final boolean unzipDownloadFileToTempFolder(Context param1Context, File param1File, OfflineZipEntity param1OfflineZipEntity) {
      StringBuilder stringBuilder = new StringBuilder("download_offline_");
      stringBuilder.append(param1OfflineZipEntity.getModuleName());
      String str = stringBuilder.toString();
      File file2 = OfflineZipUpdateManager.INSTANCE.getTempFolder(param1Context, str);
      IOUtils.clearDir(file2);
      File file1 = OfflineZipUpdateManager.INSTANCE.getTempZipFile(param1Context, str, param1OfflineZipEntity.getModuleName());
      try {
        IOUtils.copyFile(param1File, file1, true);
        if (file1.exists()) {
          IOUtils.unZipFolder(file1.getAbsolutePath(), file2.getAbsolutePath());
          OfflineZipUpdateManager.INSTANCE.setSpecifiedOfflineModuleVersion(new File(file2, param1OfflineZipEntity.getModuleName()), param1OfflineZipEntity.getMd5());
          file1.delete();
          return true;
        } 
        IOUtils.delete(file2);
        IOUtils.delete(param1File);
        return false;
      } catch (Exception exception) {
        AppBrandLogger.e("tma_OfflineZipUpdateTask", new Object[] { "unzipDownloadFileToTempFolder", exception });
        this.errCode = 9002;
        String str1 = this.errMsg;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str1);
        stringBuilder1.append("unzip failed\n");
        this.errMsg = stringBuilder1.toString();
        IOUtils.delete(file2);
        IOUtils.delete(param1File);
        return false;
      } 
    }
    
    public final void downloadOfflineZip(Context param1Context) {
      l.b(param1Context, "context");
      try {
        setUpdateStatus(OfflineZipStatus.DOWNLOADING);
        f f = new f(this.entity.getUrl(), false);
        File file = StorageUtil.getExternalCacheDir(param1Context);
        l.a(file, "StorageUtil.getExternalCacheDir(context)");
        f.a = file.getPath();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(System.currentTimeMillis()));
        stringBuilder.append(".ooo");
        f.b = stringBuilder.toString();
        TimeMeter timeMeter = TimeMeter.newAndStart();
        DownloadManager.get().syncDownload(f.f(), ((i)f).f, f.a, f.b, (DownloadManager.OnDownloadListener)new OfflineZipUpdateManager$OfflineZipUpdateTask$downloadOfflineZip$1(param1Context, f, timeMeter), null);
        return;
      } catch (Exception exception) {
        AppBrandLogger.e("tma_OfflineZipUpdateTask", new Object[] { "downloadOfflineZip", exception });
        return;
      } 
    }
    
    public final int getDownloadDuration() {
      return this.downloadDuration;
    }
    
    public final OfflineZipEntity getEntity() {
      return this.entity;
    }
    
    public final int getErrCode() {
      return this.errCode;
    }
    
    public final String getErrMsg() {
      return this.errMsg;
    }
    
    public final CopyOnWriteArrayList<OnOfflineZipUpdateResultListener> getListeners() {
      return this.listeners;
    }
    
    public final OfflineZipStatus getUpdateStatus() {
      return this.updateStatus;
    }
    
    public final void onDownloadOfflineZipFailed(String param1String, Throwable param1Throwable, f param1f, TimeMeter param1TimeMeter) {
      setUpdateStatus(OfflineZipStatus.FAILED);
      AppBrandLogger.e("tma_OfflineZipUpdateTask", new Object[] { param1String, param1Throwable });
      this.downloadDuration = (int)param1TimeMeter.getMillisAfterStart();
      this.errCode = 9001;
      String str = this.errMsg;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append("download failed: ");
      stringBuilder.append(param1String);
      stringBuilder.append('\n');
      this.errMsg = stringBuilder.toString();
      IOUtils.delete(new File(param1f.a, param1f.b));
      OfflineZipEventHelper.monitor(this.entity.getModuleName(), this.errCode, this.errMsg, this.downloadDuration);
    }
    
    public final void onDownloadOfflineZipSuccess(Context param1Context, ae param1ae, f param1f, TimeMeter param1TimeMeter) {
      File file = new File(param1f.a, param1f.b);
      try {
        this.downloadDuration = (int)param1TimeMeter.getMillisAfterStart();
        if (!file.exists() || !param1ae.a()) {
          setUpdateStatus(OfflineZipStatus.FAILED);
          this.errCode = 9001;
          String str = this.errMsg;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str);
          stringBuilder.append("download failed: download file does not exist\n");
          this.errMsg = stringBuilder.toString();
          IOUtils.delete(file);
          retryDownloadOfflineZip(param1Context, "file not exist");
        } else if ((l.a(this.entity.getMd5(), IOUtils.calculateMD5(file)) ^ true) != 0) {
          setUpdateStatus(OfflineZipStatus.FAILED);
          this.errCode = 9001;
          String str = this.errMsg;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str);
          stringBuilder.append("download failed: md5 does not match\n");
          this.errMsg = stringBuilder.toString();
          IOUtils.delete(file);
          retryDownloadOfflineZip(param1Context, "md5 not match");
        } else {
          setUpdateStatus(OfflineZipStatus.DOWNLOADED);
          if (unzipDownloadFileToTempFolder(param1Context, file, this.entity)) {
            if (moveTempFolderToOfflineFolder(param1Context, this.entity)) {
              setUpdateStatus(OfflineZipStatus.AVAILABLE);
              this.errCode = 9000;
            } else {
              setUpdateStatus(OfflineZipStatus.FAILED);
            } 
          } else {
            setUpdateStatus(OfflineZipStatus.FAILED);
          } 
        } 
        OfflineZipEventHelper.monitor(this.entity.getModuleName(), this.errCode, this.errMsg, this.downloadDuration);
        return;
      } catch (Exception exception) {
        AppBrandLogger.e("tma_OfflineZipUpdateTask", new Object[] { "onDownloadOfflineZipSuccess", exception });
        return;
      } 
    }
    
    public final void retryDownloadOfflineZip(Context param1Context, String param1String) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1String);
      stringBuilder.append(",prepare to retry,retry count = ");
      stringBuilder.append(this.mRetryCount);
      stringBuilder.append(",max retry count = 2");
      AppBrandLogger.i("tma_OfflineZipUpdateTask", new Object[] { stringBuilder.toString() });
      int i = this.mRetryCount;
      if (i < 2) {
        this.mRetryCount = i + 1;
        String str = this.errMsg;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append("retry download, reason = ");
        stringBuilder1.append(param1String);
        stringBuilder1.append(" path = ");
        stringBuilder1.append(this.entity.getModuleName());
        this.errMsg = stringBuilder1.toString();
        AppbrandContext.mainHandler.postDelayed(new OfflineZipUpdateManager$OfflineZipUpdateTask$retryDownloadOfflineZip$1(param1Context), ((this.mRetryCount - 1) * 60) * 1000L + 3000L);
      } 
    }
    
    public final void setDownloadDuration(int param1Int) {
      this.downloadDuration = param1Int;
    }
    
    public final void setEntity(OfflineZipEntity param1OfflineZipEntity) {
      l.b(param1OfflineZipEntity, "<set-?>");
      this.entity = param1OfflineZipEntity;
    }
    
    public final void setErrCode(int param1Int) {
      this.errCode = param1Int;
    }
    
    public final void setErrMsg(String param1String) {
      l.b(param1String, "<set-?>");
      this.errMsg = param1String;
    }
    
    public final void setListeners(CopyOnWriteArrayList<OnOfflineZipUpdateResultListener> param1CopyOnWriteArrayList) {
      l.b(param1CopyOnWriteArrayList, "<set-?>");
      this.listeners = param1CopyOnWriteArrayList;
    }
    
    public final void setUpdateStatus(OfflineZipStatus param1OfflineZipStatus) {
      l.b(param1OfflineZipStatus, "value");
      this.updateStatus = param1OfflineZipStatus;
      if (this.updateStatus == OfflineZipStatus.AVAILABLE) {
        notifyUpdateResult(true);
        return;
      } 
      if (this.updateStatus == OfflineZipStatus.FAILED)
        notifyUpdateResult(false); 
    }
    
    public static final class Companion {
      private Companion() {}
    }
    
    public enum OfflineZipStatus {
      AVAILABLE, DOWNLOADED, DOWNLOADING, FAILED, INIT;
      
      static {
        OfflineZipStatus offlineZipStatus1 = new OfflineZipStatus("INIT", 0);
        INIT = offlineZipStatus1;
        OfflineZipStatus offlineZipStatus2 = new OfflineZipStatus("DOWNLOADING", 1);
        DOWNLOADING = offlineZipStatus2;
        OfflineZipStatus offlineZipStatus3 = new OfflineZipStatus("DOWNLOADED", 2);
        DOWNLOADED = offlineZipStatus3;
        OfflineZipStatus offlineZipStatus4 = new OfflineZipStatus("FAILED", 3);
        FAILED = offlineZipStatus4;
        OfflineZipStatus offlineZipStatus5 = new OfflineZipStatus("AVAILABLE", 4);
        AVAILABLE = offlineZipStatus5;
        $VALUES = new OfflineZipStatus[] { offlineZipStatus1, offlineZipStatus2, offlineZipStatus3, offlineZipStatus4, offlineZipStatus5 };
      }
    }
    
    public static final class OfflineZipUpdateManager$OfflineZipUpdateTask$downloadOfflineZip$1 extends AbstractDownloadListener {
      OfflineZipUpdateManager$OfflineZipUpdateTask$downloadOfflineZip$1(Context param2Context, f param2f, TimeMeter param2TimeMeter) {}
      
      public final void onDownloadFailed(String param2String, Throwable param2Throwable) {
        l.b(param2String, "message");
        l.b(param2Throwable, "throwable");
        OfflineZipUpdateManager.OfflineZipUpdateTask offlineZipUpdateTask = OfflineZipUpdateManager.OfflineZipUpdateTask.this;
        f f1 = this.$tmaFileRequest;
        TimeMeter timeMeter = this.$downloadTimeMeter;
        l.a(timeMeter, "downloadTimeMeter");
        offlineZipUpdateTask.onDownloadOfflineZipFailed(param2String, param2Throwable, f1, timeMeter);
        OfflineZipUpdateManager.OfflineZipUpdateTask.this.retryDownloadOfflineZip(this.$context, "download fail");
      }
      
      public final void onDownloadSuccess(ae param2ae) {
        l.b(param2ae, "response");
        super.onDownloadSuccess(param2ae);
        OfflineZipUpdateManager.OfflineZipUpdateTask offlineZipUpdateTask = OfflineZipUpdateManager.OfflineZipUpdateTask.this;
        Context context = this.$context;
        f f1 = this.$tmaFileRequest;
        TimeMeter timeMeter = this.$downloadTimeMeter;
        l.a(timeMeter, "downloadTimeMeter");
        offlineZipUpdateTask.onDownloadOfflineZipSuccess(context, param2ae, f1, timeMeter);
      }
      
      public final void onDownloading(int param2Int, long param2Long1, long param2Long2) {}
    }
    
    static final class OfflineZipUpdateManager$OfflineZipUpdateTask$retryDownloadOfflineZip$1 implements Runnable {
      OfflineZipUpdateManager$OfflineZipUpdateTask$retryDownloadOfflineZip$1(Context param2Context) {}
      
      public final void run() {
        OfflineZipUpdateManager.access$getMUpdateTasks$p(OfflineZipUpdateManager.INSTANCE).offer(OfflineZipUpdateManager.OfflineZipUpdateTask.this);
        OfflineZipUpdateManager.INSTANCE.startUpdateOfflineZip(this.$context);
      }
    }
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public enum OfflineZipStatus {
    AVAILABLE, DOWNLOADED, DOWNLOADING, FAILED, INIT;
    
    static {
      OfflineZipStatus offlineZipStatus1 = new OfflineZipStatus("INIT", 0);
      INIT = offlineZipStatus1;
      OfflineZipStatus offlineZipStatus2 = new OfflineZipStatus("DOWNLOADING", 1);
      DOWNLOADING = offlineZipStatus2;
      OfflineZipStatus offlineZipStatus3 = new OfflineZipStatus("DOWNLOADED", 2);
      DOWNLOADED = offlineZipStatus3;
      OfflineZipStatus offlineZipStatus4 = new OfflineZipStatus("FAILED", 3);
      FAILED = offlineZipStatus4;
      OfflineZipStatus offlineZipStatus5 = new OfflineZipStatus("AVAILABLE", 4);
      AVAILABLE = offlineZipStatus5;
      $VALUES = new OfflineZipStatus[] { offlineZipStatus1, offlineZipStatus2, offlineZipStatus3, offlineZipStatus4, offlineZipStatus5 };
    }
  }
  
  public static final class OfflineZipUpdateManager$OfflineZipUpdateTask$downloadOfflineZip$1 extends AbstractDownloadListener {
    OfflineZipUpdateManager$OfflineZipUpdateTask$downloadOfflineZip$1(Context param1Context, f param1f, TimeMeter param1TimeMeter) {}
    
    public final void onDownloadFailed(String param1String, Throwable param1Throwable) {
      l.b(param1String, "message");
      l.b(param1Throwable, "throwable");
      OfflineZipUpdateManager.OfflineZipUpdateTask offlineZipUpdateTask = OfflineZipUpdateManager.OfflineZipUpdateTask.this;
      f f1 = this.$tmaFileRequest;
      TimeMeter timeMeter = this.$downloadTimeMeter;
      l.a(timeMeter, "downloadTimeMeter");
      offlineZipUpdateTask.onDownloadOfflineZipFailed(param1String, param1Throwable, f1, timeMeter);
      OfflineZipUpdateManager.OfflineZipUpdateTask.this.retryDownloadOfflineZip(this.$context, "download fail");
    }
    
    public final void onDownloadSuccess(ae param1ae) {
      l.b(param1ae, "response");
      super.onDownloadSuccess(param1ae);
      OfflineZipUpdateManager.OfflineZipUpdateTask offlineZipUpdateTask = OfflineZipUpdateManager.OfflineZipUpdateTask.this;
      Context context = this.$context;
      f f1 = this.$tmaFileRequest;
      TimeMeter timeMeter = this.$downloadTimeMeter;
      l.a(timeMeter, "downloadTimeMeter");
      offlineZipUpdateTask.onDownloadOfflineZipSuccess(context, param1ae, f1, timeMeter);
    }
    
    public final void onDownloading(int param1Int, long param1Long1, long param1Long2) {}
  }
  
  static final class OfflineZipUpdateManager$OfflineZipUpdateTask$retryDownloadOfflineZip$1 implements Runnable {
    OfflineZipUpdateManager$OfflineZipUpdateTask$retryDownloadOfflineZip$1(Context param1Context) {}
    
    public final void run() {
      OfflineZipUpdateManager.access$getMUpdateTasks$p(OfflineZipUpdateManager.INSTANCE).offer(OfflineZipUpdateManager.OfflineZipUpdateTask.this);
      OfflineZipUpdateManager.INSTANCE.startUpdateOfflineZip(this.$context);
    }
  }
  
  static final class OfflineZipUpdateManager$init$1 implements Action {
    OfflineZipUpdateManager$init$1(Context param1Context) {}
    
    public final void act() {
      OfflineZipUpdateManager.INSTANCE.useLatestResources(this.$context);
      OfflineZipUpdateManager.INSTANCE.unzipInternalOfflineZipIfNeeded(this.$context);
      OfflineZipUpdateManager.INSTANCE.unzipExternalOfflineModule(this.$context);
    }
  }
  
  static final class OfflineZipUpdateManager$startUpdateOfflineZip$1 implements Action {
    OfflineZipUpdateManager$startUpdateOfflineZip$1(Context param1Context) {}
    
    public final void act() {
      synchronized (OfflineZipUpdateManager.INSTANCE) {
        while ((OfflineZipUpdateManager.access$getMUpdateTasks$p(OfflineZipUpdateManager.INSTANCE).isEmpty() ^ true) != 0) {
          OfflineZipUpdateManager.OfflineZipUpdateTask offlineZipUpdateTask = OfflineZipUpdateManager.access$getMUpdateTasks$p(OfflineZipUpdateManager.INSTANCE).poll();
          if (OfflineZipManager.INSTANCE.isOfflineModuleNeedUpdate(this.$context, offlineZipUpdateTask.getEntity().getModuleName(), offlineZipUpdateTask.getEntity().getMd5())) {
            offlineZipUpdateTask.downloadOfflineZip(this.$context);
            continue;
          } 
          offlineZipUpdateTask.setUpdateStatus(OfflineZipUpdateManager.OfflineZipUpdateTask.OfflineZipStatus.AVAILABLE);
        } 
        return;
      } 
    }
  }
  
  static final class OfflineZipUpdateManager$unzipExternalOfflineModule$moduleNames$1 implements FilenameFilter {
    public static final OfflineZipUpdateManager$unzipExternalOfflineModule$moduleNames$1 INSTANCE = new OfflineZipUpdateManager$unzipExternalOfflineModule$moduleNames$1();
    
    public final boolean accept(File param1File, String param1String) {
      l.a(param1String, "s");
      return p.c(param1String, OfflineZipUpdateManager.INSTANCE.getZIP_FILE_SUFFIX(), false, 2, null);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\offlinezip\OfflineZipUpdateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */