package com.tt.miniapp.launchcache;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.FileUtil;
import com.tt.miniapphost.util.IOUtils;
import d.f.b.g;
import d.f.b.l;
import d.m.j;
import d.m.l;
import d.m.p;
import d.u;
import java.io.File;
import java.io.FilenameFilter;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public final class LaunchCacheDAO {
  private static final l APPID_DIR_REGEX;
  
  public static final LaunchCacheDAO INSTANCE = new LaunchCacheDAO();
  
  private static final l VERSION_DIR_REGEX;
  
  private static final ConcurrentHashMap<String, LockObject> lockMap;
  
  static {
    APPID_DIR_REGEX = new l("appid_(tt[a-z0-9]+)");
    VERSION_DIR_REGEX = new l("ver_(\\d+)-([a-z]+)");
    lockMap = new ConcurrentHashMap<String, LockObject>();
  }
  
  private final long nextGlobalDownloadCounter(Context paramContext) {
    return (new AtomicFileCounter(getLaunchCacheBaseDir(paramContext), "global_download_counter")).addAndGet(1L);
  }
  
  private final long nextGlobalLaunchCounter(Context paramContext) {
    return (new AtomicFileCounter(getLaunchCacheBaseDir(paramContext), "global_launch_counter")).addAndGet(1L);
  }
  
  private final CacheAppIdDir tryParseCacheAppIdDirFromFile(Context paramContext, File paramFile) {
    try {
      l l1 = APPID_DIR_REGEX;
      String str = paramFile.getName();
      l.a(str, "file.name");
      j j = l1.matchEntire(str);
      if (j != null)
        return new CacheAppIdDir(paramContext, j.d().get(1)); 
    } catch (Exception exception) {
      AppBrandLogger.e("LaunchCacheDAO", new Object[] { exception });
    } 
    return null;
  }
  
  public final void checkLock(String paramString) {
    LockObject lockObject = lockMap.get(paramString);
    if (lockObject != null) {
      l.a(lockObject, "lockMap[appId] ?: throw …lock fail: lock is null\")");
      lockObject.checkThread();
      return;
    } 
    throw (Throwable)new IllegalStateException("Check lock fail: lock is null");
  }
  
  public final CacheAppIdDir getCacheAppIdDir(Context paramContext, String paramString) {
    l.b(paramContext, "context");
    l.b(paramString, "appId");
    return new CacheAppIdDir(paramContext, paramString);
  }
  
  public final File getCacheAppIdOriginDir(Context paramContext, String paramString) {
    File file = getLaunchCacheBaseDir(paramContext);
    StringBuilder stringBuilder = new StringBuilder("appid_");
    stringBuilder.append(paramString);
    file = new File(file, stringBuilder.toString());
    if (!file.exists())
      file.mkdirs(); 
    return file;
  }
  
  public final long getLastSilenceUpdateTime(Context paramContext) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ldc 'context'
    //   5: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   8: new com/tt/miniapp/launchcache/AtomicFileCounter
    //   11: dup
    //   12: aload_0
    //   13: aload_1
    //   14: invokevirtual getLaunchCacheBaseDir : (Landroid/content/Context;)Ljava/io/File;
    //   17: ldc 'silence_update_time'
    //   19: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   22: invokevirtual get : ()J
    //   25: lstore_2
    //   26: aload_0
    //   27: monitorexit
    //   28: lload_2
    //   29: lreturn
    //   30: astore_1
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_1
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   2	26	30	finally
  }
  
  public final File getLaunchCacheBaseDir(Context paramContext) {
    l.b(paramContext, "context");
    return new File(paramContext.getFilesDir(), "appbrand/launchcache");
  }
  
  public final void increaseNormalLaunchCounter(Context paramContext, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ldc 'context'
    //   5: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   8: aload_2
    //   9: ldc 'appId'
    //   11: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   14: ldc 'LaunchCacheDAO'
    //   16: iconst_3
    //   17: anewarray java/lang/Object
    //   20: dup
    //   21: iconst_0
    //   22: ldc 'increateNormalLaunchCounter'
    //   24: aastore
    //   25: dup
    //   26: iconst_1
    //   27: ldc 'AppId: '
    //   29: aastore
    //   30: dup
    //   31: iconst_2
    //   32: aload_2
    //   33: aastore
    //   34: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   37: aload_0
    //   38: aload_1
    //   39: invokespecial nextGlobalLaunchCounter : (Landroid/content/Context;)J
    //   42: lstore_3
    //   43: aload_0
    //   44: aload_1
    //   45: aload_2
    //   46: invokevirtual getCacheAppIdDir : (Landroid/content/Context;Ljava/lang/String;)Lcom/tt/miniapp/launchcache/LaunchCacheDAO$CacheAppIdDir;
    //   49: lload_3
    //   50: invokevirtual setLocalLaunchCounter : (J)V
    //   53: aload_0
    //   54: monitorexit
    //   55: return
    //   56: astore_1
    //   57: aload_0
    //   58: monitorexit
    //   59: aload_1
    //   60: athrow
    // Exception table:
    //   from	to	target	type
    //   2	53	56	finally
  }
  
  public final void increasePreDownloadCounter(Context paramContext, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ldc 'context'
    //   5: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   8: aload_2
    //   9: ldc 'appId'
    //   11: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   14: ldc 'LaunchCacheDAO'
    //   16: iconst_3
    //   17: anewarray java/lang/Object
    //   20: dup
    //   21: iconst_0
    //   22: ldc 'increasePreDownloadCounter'
    //   24: aastore
    //   25: dup
    //   26: iconst_1
    //   27: ldc 'AppId: '
    //   29: aastore
    //   30: dup
    //   31: iconst_2
    //   32: aload_2
    //   33: aastore
    //   34: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   37: aload_0
    //   38: aload_1
    //   39: invokespecial nextGlobalDownloadCounter : (Landroid/content/Context;)J
    //   42: lstore_3
    //   43: aload_0
    //   44: aload_1
    //   45: aload_2
    //   46: invokevirtual getCacheAppIdDir : (Landroid/content/Context;Ljava/lang/String;)Lcom/tt/miniapp/launchcache/LaunchCacheDAO$CacheAppIdDir;
    //   49: lload_3
    //   50: invokevirtual setLocalPreDownloadCounter : (J)V
    //   53: aload_0
    //   54: monitorexit
    //   55: return
    //   56: astore_1
    //   57: aload_0
    //   58: monitorexit
    //   59: aload_1
    //   60: athrow
    // Exception table:
    //   from	to	target	type
    //   2	53	56	finally
  }
  
  public final List<CacheAppIdDir> listCacheAppIdDirs(Context paramContext) {
    l.b(paramContext, "context");
    File[] arrayOfFile = getLaunchCacheBaseDir(paramContext).listFiles();
    ArrayList<CacheAppIdDir> arrayList = new ArrayList();
    if (arrayOfFile != null) {
      int j = arrayOfFile.length;
      for (int i = 0; i < j; i++) {
        File file = arrayOfFile[i];
        CacheAppIdDir cacheAppIdDir = INSTANCE.tryParseCacheAppIdDirFromFile(paramContext, file);
        if (cacheAppIdDir != null)
          arrayList.add(cacheAppIdDir); 
      } 
    } 
    return arrayList;
  }
  
  public final void setSilenceUpdateTime(Context paramContext, long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ldc 'context'
    //   5: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   8: new com/tt/miniapp/launchcache/AtomicFileCounter
    //   11: dup
    //   12: aload_0
    //   13: aload_1
    //   14: invokevirtual getLaunchCacheBaseDir : (Landroid/content/Context;)Ljava/io/File;
    //   17: ldc 'silence_update_time'
    //   19: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   22: lload_2
    //   23: invokevirtual set : (J)V
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: astore_1
    //   30: aload_0
    //   31: monitorexit
    //   32: aload_1
    //   33: athrow
    // Exception table:
    //   from	to	target	type
    //   2	26	29	finally
  }
  
  public final CacheVersionDir tryParseCacheVersionDirFromFileName(Context paramContext, String paramString1, String paramString2) {
    try {
      j j = VERSION_DIR_REGEX.matchEntire(paramString2);
      if (j != null)
        return new CacheVersionDir(paramContext, paramString1, Long.parseLong(j.d().get(1)), RequestType.valueOf(j.d().get(2))); 
    } catch (Exception exception) {
      AppBrandLogger.e("LaunchCacheDAO", new Object[] { exception });
    } 
    return null;
  }
  
  public static final class CacheAppIdDir {
    private final String appId;
    
    private final Context context;
    
    private final File originDir;
    
    public CacheAppIdDir(Context param1Context, String param1String) {
      this.context = param1Context;
      this.appId = param1String;
      this.originDir = LaunchCacheDAO.INSTANCE.getCacheAppIdOriginDir(this.context, this.appId);
    }
    
    public final void checkLocked() {
      LaunchCacheDAO.INSTANCE.checkLock(this.appId);
    }
    
    public final void clearLocked() {
      AppBrandLogger.i("LaunchCacheDAO", new Object[] { "clearLocked", "AppId", this.appId });
      LaunchCacheDAO.INSTANCE.checkLock(this.appId);
      IOUtils.delete(this.originDir);
    }
    
    public final String getAppId() {
      return this.appId;
    }
    
    public final LaunchCacheDAO.CacheVersionDir getCacheVersionDir(long param1Long, RequestType param1RequestType) {
      l.b(param1RequestType, "requestType");
      return new LaunchCacheDAO.CacheVersionDir(this.context, this.appId, param1Long, param1RequestType);
    }
    
    public final Context getContext() {
      return this.context;
    }
    
    public final long getLocalLaunchCounter() {
      return (new AtomicFileCounter(this.originDir, "local_launch_counter")).get();
    }
    
    public final long getLocalPreDownloadCounter() {
      return (new AtomicFileCounter(this.originDir, "local_pre_download_counter")).get();
    }
    
    public final List<LaunchCacheDAO.CacheVersionDir> listCacheVersionDirs() {
      String[] arrayOfString = LaunchCacheDAO.INSTANCE.getCacheAppIdOriginDir(this.context, this.appId).list();
      ArrayList<LaunchCacheDAO.CacheVersionDir> arrayList = new ArrayList();
      if (arrayOfString != null) {
        int j = arrayOfString.length;
        for (int i = 0; i < j; i++) {
          String str = arrayOfString[i];
          LaunchCacheDAO.CacheVersionDir cacheVersionDir = LaunchCacheDAO.INSTANCE.tryParseCacheVersionDirFromFileName(this.context, this.appId, str);
          if (cacheVersionDir != null)
            arrayList.add(cacheVersionDir); 
        } 
      } 
      return arrayList;
    }
    
    public final LaunchCacheDAO.LockObject lock() {
      try {
        LaunchCacheDAO.LockObject lockObject2 = (LaunchCacheDAO.LockObject)LaunchCacheDAO.access$getLockMap$p(LaunchCacheDAO.INSTANCE).get(this.appId);
        LaunchCacheDAO.LockObject lockObject1 = lockObject2;
        if (lockObject2 == null) {
          lockObject1 = new LaunchCacheDAO.LockObject(this.context, this.appId);
          lockObject2 = LaunchCacheDAO.access$getLockMap$p(LaunchCacheDAO.INSTANCE).putIfAbsent(this.appId, lockObject1);
          if (lockObject2 != null)
            lockObject1 = lockObject2; 
        } 
        boolean bool = lockObject1.tryLock(1000L);
        if (bool)
          return lockObject1; 
      } catch (Exception exception) {
        DebugUtil.logOrThrow("LaunchCacheDAO", new Object[] { exception });
      } 
      return null;
    }
    
    public final void setLocalLaunchCounter(long param1Long) {
      (new AtomicFileCounter(this.originDir, "local_launch_counter")).set(param1Long);
    }
    
    public final void setLocalPreDownloadCounter(long param1Long) {
      (new AtomicFileCounter(this.originDir, "local_pre_download_counter")).set(param1Long);
    }
  }
  
  public static final class CacheVersionDir {
    public static final Companion Companion = new Companion(null);
    
    private final String appId;
    
    private final Context context;
    
    private final File installDir;
    
    private final File originDir;
    
    private final File pkgFile;
    
    private final RequestType requestType;
    
    private final long versionCode;
    
    public CacheVersionDir(Context param1Context, String param1String, long param1Long, RequestType param1RequestType) {
      this.context = param1Context;
      this.appId = param1String;
      this.versionCode = param1Long;
      this.requestType = param1RequestType;
      File file = LaunchCacheDAO.INSTANCE.getCacheAppIdOriginDir(this.context, this.appId);
      StringBuilder stringBuilder = new StringBuilder("ver_");
      stringBuilder.append(this.versionCode);
      stringBuilder.append('-');
      stringBuilder.append(this.requestType);
      this.originDir = new File(file, stringBuilder.toString());
      this.pkgFile = new File(this.originDir, "_.pkg");
      this.installDir = new File(this.originDir, "install");
      if (!this.originDir.exists())
        this.originDir.mkdirs(); 
    }
    
    private final File fileForMeta(Object param1Object) {
      File file = this.originDir;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1Object);
      stringBuilder.append(".meta");
      return new File(file, stringBuilder.toString());
    }
    
    private final File fileForSourceFlag(RequestType param1RequestType) {
      File file = this.originDir;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1RequestType);
      stringBuilder.append(".source");
      return new File(file, stringBuilder.toString());
    }
    
    private final File fileForStatusFlag(StatusFlagType param1StatusFlagType) {
      File file = this.originDir;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1StatusFlagType);
      stringBuilder.append(".status");
      return new File(file, stringBuilder.toString());
    }
    
    public final void checkLocked() {
      LaunchCacheDAO.INSTANCE.checkLock(this.appId);
    }
    
    public final boolean checkStatusFlag(StatusFlagType param1StatusFlagType) {
      l.b(param1StatusFlagType, "statusFlag");
      return fileForStatusFlag(param1StatusFlagType).exists();
    }
    
    public final void clearLocked() {
      AppBrandLogger.i("LaunchCacheDAO", new Object[] { "clearLocked", "AppId:", this.appId, "VersionCode:", Long.valueOf(this.versionCode) });
      LaunchCacheDAO.INSTANCE.checkLock(this.appId);
      IOUtils.delete(this.originDir);
    }
    
    public final String getAppId() {
      return this.appId;
    }
    
    public final Context getContext() {
      return this.context;
    }
    
    public final RequestType getCurrentSourceFlag() {
      File[] arrayOfFile = this.originDir.listFiles(LaunchCacheDAO$CacheVersionDir$getCurrentSourceFlag$sourceFlagFiles$1.INSTANCE);
      if (arrayOfFile != null && arrayOfFile.length == 1) {
        File file = arrayOfFile[0];
        try {
          l.a(file, "sourceFlagFiles[0]");
          String str = file.getName();
          l.a(str, "sourceFlagFiles[0].name");
          File file1 = arrayOfFile[0];
          l.a(file1, "sourceFlagFiles[0]");
          int i = file1.getName().length();
          if (str != null) {
            String str1 = str.substring(0, i - 7);
            l.a(str1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return RequestType.valueOf(str1);
          } 
          throw new u("null cannot be cast to non-null type java.lang.String");
        } catch (Exception exception) {
          AppBrandLogger.e("LaunchCacheDAO", new Object[] { exception });
        } 
      } 
      return null;
    }
    
    public final StatusFlagType getCurrentStatusFlag() {
      File[] arrayOfFile = this.originDir.listFiles(LaunchCacheDAO$CacheVersionDir$getCurrentStatusFlag$statusFlagFiles$1.INSTANCE);
      if (arrayOfFile != null && arrayOfFile.length == 1) {
        File file = arrayOfFile[0];
        try {
          l.a(file, "statusFlagFiles[0]");
          String str = file.getName();
          l.a(str, "statusFlagFileName");
          int i = str.length();
          if (str != null) {
            str = str.substring(0, i - 7);
            l.a(str, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return StatusFlagType.valueOf(str);
          } 
          throw new u("null cannot be cast to non-null type java.lang.String");
        } catch (Exception exception) {
          AppBrandLogger.e("LaunchCacheDAO", new Object[] { exception });
        } 
      } 
      return null;
    }
    
    public final File getInstallDir() {
      return this.installDir;
    }
    
    public final File getMetaFile() {
      File file;
      File[] arrayOfFile = this.originDir.listFiles(LaunchCacheDAO$CacheVersionDir$metaFile$metaSuffixFiles$1.INSTANCE);
      int j = arrayOfFile.length;
      int i = 0;
      if (j == 1) {
        file = arrayOfFile[0];
        l.a(file, "metaSuffixFiles[0]");
        return file;
      } 
      l.a(file, "metaSuffixFiles");
      j = file.length;
      while (i < j) {
        IOUtils.delete(file[i]);
        i++;
      } 
      return fileForMeta("_");
    }
    
    public final File getPkgFile() {
      return this.pkgFile;
    }
    
    public final RequestType getRequestType() {
      return this.requestType;
    }
    
    public final long getUpdateTime() {
      File file = getMetaFile();
      if (file.exists())
        try {
          String str = file.getName();
          l.a(str, "metaFile.name");
          int i = file.getName().length();
          if (str != null) {
            String str1 = str.substring(0, i - 5);
            l.a(str1, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            return Long.parseLong(str1);
          } 
          throw new u("null cannot be cast to non-null type java.lang.String");
        } catch (Exception exception) {
          return 0L;
        }  
      return 0L;
    }
    
    public final long getVersionCode() {
      return this.versionCode;
    }
    
    public final boolean mkdirs() {
      return this.originDir.mkdirs();
    }
    
    public final void setSourceFlagLocked(RequestType param1RequestType) {
      l.b(param1RequestType, "sourceFlag");
      LaunchCacheDAO.INSTANCE.checkLock(this.appId);
      File[] arrayOfFile = this.originDir.listFiles(LaunchCacheDAO$CacheVersionDir$setSourceFlagLocked$sourceFlagFiles$1.INSTANCE);
      l.a(arrayOfFile, "sourceFlagFiles");
      int j = arrayOfFile.length;
      for (int i = 0; i < j; i++)
        IOUtils.delete(arrayOfFile[i]); 
      fileForSourceFlag(param1RequestType).createNewFile();
    }
    
    public final void setStatusFlagLocked(StatusFlagType param1StatusFlagType) {
      l.b(param1StatusFlagType, "statusFlag");
      LaunchCacheDAO.INSTANCE.checkLock(this.appId);
      File[] arrayOfFile = this.originDir.listFiles(LaunchCacheDAO$CacheVersionDir$setStatusFlagLocked$statusFlagFiles$1.INSTANCE);
      l.a(arrayOfFile, "statusFlagFiles");
      int j = arrayOfFile.length;
      for (int i = 0; i < j; i++)
        IOUtils.delete(arrayOfFile[i]); 
      fileForStatusFlag(param1StatusFlagType).createNewFile();
    }
    
    public final void setUpdateTimeLocked(long param1Long) {
      LaunchCacheDAO.INSTANCE.checkLock(this.appId);
      File file = getMetaFile();
      if (file.exists())
        file.renameTo(fileForMeta(String.valueOf(param1Long))); 
    }
    
    public final long size() {
      return FileUtil.getFileSize(this.originDir);
    }
    
    public static final class Companion {
      private Companion() {}
    }
    
    static final class LaunchCacheDAO$CacheVersionDir$getCurrentSourceFlag$sourceFlagFiles$1 implements FilenameFilter {
      public static final LaunchCacheDAO$CacheVersionDir$getCurrentSourceFlag$sourceFlagFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$getCurrentSourceFlag$sourceFlagFiles$1();
      
      public final boolean accept(File param2File, String param2String) {
        l.a(param2String, "name");
        return p.c(param2String, ".source", false, 2, null);
      }
    }
    
    static final class LaunchCacheDAO$CacheVersionDir$getCurrentStatusFlag$statusFlagFiles$1 implements FilenameFilter {
      public static final LaunchCacheDAO$CacheVersionDir$getCurrentStatusFlag$statusFlagFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$getCurrentStatusFlag$statusFlagFiles$1();
      
      public final boolean accept(File param2File, String param2String) {
        l.a(param2String, "name");
        return p.c(param2String, ".status", false, 2, null);
      }
    }
    
    static final class LaunchCacheDAO$CacheVersionDir$metaFile$metaSuffixFiles$1 implements FilenameFilter {
      public static final LaunchCacheDAO$CacheVersionDir$metaFile$metaSuffixFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$metaFile$metaSuffixFiles$1();
      
      public final boolean accept(File param2File, String param2String) {
        l.a(param2String, "name");
        return p.c(param2String, ".meta", false, 2, null);
      }
    }
    
    static final class LaunchCacheDAO$CacheVersionDir$setSourceFlagLocked$sourceFlagFiles$1 implements FilenameFilter {
      public static final LaunchCacheDAO$CacheVersionDir$setSourceFlagLocked$sourceFlagFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$setSourceFlagLocked$sourceFlagFiles$1();
      
      public final boolean accept(File param2File, String param2String) {
        l.a(param2String, "name");
        return p.c(param2String, ".source", false, 2, null);
      }
    }
    
    static final class LaunchCacheDAO$CacheVersionDir$setStatusFlagLocked$statusFlagFiles$1 implements FilenameFilter {
      public static final LaunchCacheDAO$CacheVersionDir$setStatusFlagLocked$statusFlagFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$setStatusFlagLocked$statusFlagFiles$1();
      
      public final boolean accept(File param2File, String param2String) {
        l.a(param2String, "name");
        return p.c(param2String, ".status", false, 2, null);
      }
    }
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class LaunchCacheDAO$CacheVersionDir$getCurrentSourceFlag$sourceFlagFiles$1 implements FilenameFilter {
    public static final LaunchCacheDAO$CacheVersionDir$getCurrentSourceFlag$sourceFlagFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$getCurrentSourceFlag$sourceFlagFiles$1();
    
    public final boolean accept(File param1File, String param1String) {
      l.a(param1String, "name");
      return p.c(param1String, ".source", false, 2, null);
    }
  }
  
  static final class LaunchCacheDAO$CacheVersionDir$getCurrentStatusFlag$statusFlagFiles$1 implements FilenameFilter {
    public static final LaunchCacheDAO$CacheVersionDir$getCurrentStatusFlag$statusFlagFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$getCurrentStatusFlag$statusFlagFiles$1();
    
    public final boolean accept(File param1File, String param1String) {
      l.a(param1String, "name");
      return p.c(param1String, ".status", false, 2, null);
    }
  }
  
  static final class LaunchCacheDAO$CacheVersionDir$metaFile$metaSuffixFiles$1 implements FilenameFilter {
    public static final LaunchCacheDAO$CacheVersionDir$metaFile$metaSuffixFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$metaFile$metaSuffixFiles$1();
    
    public final boolean accept(File param1File, String param1String) {
      l.a(param1String, "name");
      return p.c(param1String, ".meta", false, 2, null);
    }
  }
  
  static final class LaunchCacheDAO$CacheVersionDir$setSourceFlagLocked$sourceFlagFiles$1 implements FilenameFilter {
    public static final LaunchCacheDAO$CacheVersionDir$setSourceFlagLocked$sourceFlagFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$setSourceFlagLocked$sourceFlagFiles$1();
    
    public final boolean accept(File param1File, String param1String) {
      l.a(param1String, "name");
      return p.c(param1String, ".source", false, 2, null);
    }
  }
  
  static final class LaunchCacheDAO$CacheVersionDir$setStatusFlagLocked$statusFlagFiles$1 implements FilenameFilter {
    public static final LaunchCacheDAO$CacheVersionDir$setStatusFlagLocked$statusFlagFiles$1 INSTANCE = new LaunchCacheDAO$CacheVersionDir$setStatusFlagLocked$statusFlagFiles$1();
    
    public final boolean accept(File param1File, String param1String) {
      l.a(param1String, "name");
      return p.c(param1String, ".status", false, 2, null);
    }
  }
  
  public static final class LockObject {
    private FileLock fileLock;
    
    private final File lockFile;
    
    private long obtainLockTimeStamp;
    
    private RandomAccessFile raf;
    
    private final ReentrantLock threadLock;
    
    public LockObject(Context param1Context, String param1String) {
      File file = LaunchCacheDAO.INSTANCE.getLaunchCacheBaseDir(param1Context);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1String);
      stringBuilder.append(".lock");
      this.lockFile = new File(file, stringBuilder.toString());
      this.threadLock = new ReentrantLock();
    }
    
    public final void checkThread() {
      if (this.threadLock.isHeldByCurrentThread())
        return; 
      throw (Throwable)new IllegalStateException("Check thread fail: not held by current thread");
    }
    
    public final boolean tryLock(long param1Long) {
      long l = SystemClock.elapsedRealtime();
      if (!this.threadLock.tryLock(param1Long, TimeUnit.MILLISECONDS)) {
        StringBuilder stringBuilder = new StringBuilder("GetLockWaitTimeout: ");
        stringBuilder.append(param1Long);
        AppBrandMonitor.reportError("LaunchCacheDAO", stringBuilder.toString(), Log.getStackTraceString(new Throwable()));
        return false;
      } 
      if (this.threadLock.getHoldCount() == 1) {
        if (!this.lockFile.exists())
          this.lockFile.createNewFile(); 
        if (!this.lockFile.exists()) {
          StringBuilder stringBuilder = new StringBuilder("CreateLockFileFail: ");
          stringBuilder.append(this.lockFile.getAbsolutePath());
          AppBrandMonitor.reportError("LaunchCacheDAO", stringBuilder.toString(), Log.getStackTraceString(new Throwable()));
          return false;
        } 
        this.raf = new RandomAccessFile(this.lockFile, "rw");
        RandomAccessFile randomAccessFile = this.raf;
        if (randomAccessFile == null)
          l.a(); 
        this.fileLock = randomAccessFile.getChannel().lock();
        this.obtainLockTimeStamp = SystemClock.elapsedRealtime();
      } 
      param1Long = SystemClock.elapsedRealtime() - l;
      if (param1Long > 200L) {
        StringBuilder stringBuilder = new StringBuilder("LockUseTooMuchTime: ");
        stringBuilder.append(param1Long);
        AppBrandMonitor.reportError("LaunchCacheDAO", stringBuilder.toString(), Log.getStackTraceString(new Throwable()));
      } 
      return true;
    }
    
    public final void unlock() {
      checkThread();
      if (this.threadLock.getHoldCount() == 1) {
        long l = SystemClock.elapsedRealtime() - this.obtainLockTimeStamp;
        FileLock fileLock = this.fileLock;
        if (fileLock == null)
          l.a(); 
        fileLock.release();
        RandomAccessFile randomAccessFile = this.raf;
        if (randomAccessFile == null)
          l.a(); 
        randomAccessFile.getChannel().close();
        this.fileLock = null;
        this.raf = null;
        this.threadLock.unlock();
        if (l > 500L) {
          StringBuilder stringBuilder = new StringBuilder("UseTooMuchTimeInLock: ");
          stringBuilder.append(l);
          AppBrandMonitor.reportError("LaunchCacheDAO", stringBuilder.toString(), Log.getStackTraceString(new Throwable()));
        } 
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\LaunchCacheDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */