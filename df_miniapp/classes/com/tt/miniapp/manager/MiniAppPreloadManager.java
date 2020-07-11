package com.tt.miniapp.manager;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.launchcache.LaunchCacheCleanDataManager;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.launchcache.meta.AppInfoRequestListener;
import com.tt.miniapp.launchcache.meta.PreloadMetaRequester;
import com.tt.miniapp.launchcache.meta.SilenceMetaRequester;
import com.tt.miniapp.launchcache.pkg.PkgDownloadHelper;
import com.tt.miniapp.launchcache.pkg.PreloadPkgRequester;
import com.tt.miniapp.launchcache.pkg.SilencePkgRequester;
import com.tt.miniapp.launchcache.pkg.StreamDownloadInstallListener;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.streamloader.StreamDownloader;
import com.tt.miniapp.thread.ExecutorToSchedulersAdapter;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.appbase.listener.MiniAppPreloadListCheckListener;
import com.tt.miniapphost.appbase.listener.MiniAppPreloadStateListener;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.MiniAppPreloadConfigEntity;
import com.tt.miniapphost.entity.PreLoadAppEntity;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import org.json.JSONObject;

public class MiniAppPreloadManager {
  public static MiniAppPreloadConfigEntity mMiniAppPreloadConfigEntity;
  
  private static NetStateManager.NetStateChangeListener mNetStateChangeListener;
  
  public static Deque<PreloadTask> mNormalPreloadTaskWaitDeque;
  
  private static List<String> mPreloadedAppIdList = new ArrayList<String>();
  
  public static Deque<PreloadTask> mPreloadingTaskDeque = new ArrayDeque<PreloadTask>(2);
  
  static {
    mNormalPreloadTaskWaitDeque = new ArrayDeque<PreloadTask>(18);
    mMiniAppPreloadConfigEntity = new MiniAppPreloadConfigEntity();
  }
  
  private static void adjustPreloadWaitDeque() {
    int i = mNormalPreloadTaskWaitDeque.size() - 18;
    if (i > 0) {
      for (int j = 0; j < i; j++)
        mNormalPreloadTaskWaitDeque.pollLast(); 
      AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "adjustPreloadWaitDeque needRemoveNormalPreloadTaskSize:", Integer.valueOf(i) });
    } 
  }
  
  public static void cancelPreloadMiniApp(String paramString) {
    // Byte code:
    //   0: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   2: monitorenter
    //   3: ldc 'MiniAppPreloadManager'
    //   5: iconst_2
    //   6: anewarray java/lang/Object
    //   9: dup
    //   10: iconst_0
    //   11: ldc 'cancelPreloadMiniApp. appId: '
    //   13: aastore
    //   14: dup
    //   15: iconst_1
    //   16: aload_0
    //   17: aastore
    //   18: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   21: aload_0
    //   22: iconst_0
    //   23: invokestatic cancelPreloadTask : (Ljava/lang/String;Z)V
    //   26: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   28: monitorexit
    //   29: return
    //   30: astore_0
    //   31: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   33: monitorexit
    //   34: aload_0
    //   35: athrow
    // Exception table:
    //   from	to	target	type
    //   3	26	30	finally
  }
  
  public static void checkPreloadListValid(List<PreLoadAppEntity> paramList, Map<String, String> paramMap, MiniAppPreloadListCheckListener paramMiniAppPreloadListCheckListener) {
    Iterator<PreLoadAppEntity> iterator = paramList.iterator();
    int j = 0;
    int i = 0;
    while (iterator.hasNext()) {
      PreLoadAppEntity preLoadAppEntity = iterator.next();
      if (preLoadAppEntity != null) {
        int k = preLoadAppEntity.getDownloadPriority();
        if (k != 1) {
          if (k != 2)
            continue; 
          j++;
          continue;
        } 
        i++;
      } 
    } 
    if (j > 1 || i > 2) {
      for (PreLoadAppEntity preLoadAppEntity : paramList) {
        if (preLoadAppEntity != null && preLoadAppEntity.getDownloadPriority() != 0)
          preLoadAppEntity.downgradePriority(); 
      } 
      AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "单次预下载队列中极速任务超过1个或高优任务超过2个，降低所有任务优先级为正常下载优先级", " highestPreloadMiniAppNumber:", Integer.valueOf(j), " highPreloadMiniAppNumber:", Integer.valueOf(i), " preloadAppList:", paramList });
      onPreloadListValid(paramMap, "单次预下载队列中极速任务超过1个或高优任务超过2个", paramMiniAppPreloadListCheckListener);
      return;
    } 
    if (j > 0 && i > 0) {
      for (PreLoadAppEntity preLoadAppEntity : paramList) {
        if (preLoadAppEntity != null && preLoadAppEntity.getDownloadPriority() == 1)
          preLoadAppEntity.downgradePriority(); 
      } 
      AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "单次预下载队列中同时存在极速和高优任务，降低所有高优任务优先级为正常下载优先级", " highestPreloadMiniAppNumber:", Integer.valueOf(j), " highPreloadMiniAppNumber:", Integer.valueOf(i), " preloadAppList:", paramList });
      onPreloadListValid(paramMap, "单次预下载队列中同时存在极速和高优任务", paramMiniAppPreloadListCheckListener);
    } 
  }
  
  private static void downgradedTask(PreloadTask paramPreloadTask) {
    if (paramPreloadTask == null)
      return; 
    paramPreloadTask.mDownloadPriority = 0;
    mNormalPreloadTaskWaitDeque.addFirst(paramPreloadTask);
  }
  
  private static boolean enableFillPreloadDeque(PreLoadAppEntity paramPreLoadAppEntity) {
    AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "enableFillPreloadDeque preLoadAppEntity:", paramPreLoadAppEntity });
    String str = paramPreLoadAppEntity.getAppid();
    if (hasPreloaded(str))
      return false; 
    if (isLazyPreloadMode(paramPreLoadAppEntity) && PkgDownloadHelper.INSTANCE.hasValidPkg((Context)AppbrandContext.getInst().getApplicationContext(), str))
      return false; 
    int i = paramPreLoadAppEntity.getDownloadPriority();
    PreloadTask preloadTask = PreloadTaskScheduler.getPreloadTaskByAppId(str, mPreloadingTaskDeque);
    if (preloadTask != null) {
      if (PreLoadAppEntity.compareDownloadPriority(i, preloadTask.mDownloadPriority))
        preloadTask.mDownloadPriority = i; 
      AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "预下载的小程序正在预下载中 appId:", str });
      return false;
    } 
    return true;
  }
  
  private static void fillPreloadDeque(List<PreLoadAppEntity> paramList, Deque<PreloadTask> paramDeque1, Deque<PreloadTask> paramDeque2, Map<String, String> paramMap) {
    Iterator<PreLoadAppEntity> iterator = paramList.iterator();
    paramList = null;
    ArrayList<PreloadTask> arrayList = null;
    int i = 0;
    while (iterator.hasNext()) {
      PreLoadAppEntity preLoadAppEntity = iterator.next();
      if (preLoadAppEntity != null && enableFillPreloadDeque(preLoadAppEntity)) {
        AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "fillPreloadDeque preLoadAppEntity:", preLoadAppEntity });
        String str = preLoadAppEntity.getAppid();
        int j = preLoadAppEntity.getDownloadPriority();
        if (j != 0) {
          if (j != 1) {
            if (j != 2)
              continue; 
            PreloadTask preloadTask1 = PreloadTaskScheduler.getExistPreloadTaskInWaitDeque(str);
            if (preloadTask1 != null) {
              preloadTask1.mDownloadPriority = 2;
              paramDeque1.addLast(preloadTask1);
              continue;
            } 
            PreloadTaskScheduler.removeMiniAppPreloadTaskByAppId(str, paramDeque2);
            if (PreloadTaskScheduler.getPreloadTaskByAppId(str, paramDeque1) != null) {
              AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "小程序已在极速优先级预下载任务等待队列.", "appInfo.appId: ", str });
              continue;
            } 
            paramDeque1.addLast(new PreloadTask(preLoadAppEntity, paramMap, (null)paramList));
            continue;
          } 
          PreloadTask preloadTask = PreloadTaskScheduler.getExistPreloadTaskInWaitDeque(str);
          if (preloadTask != null) {
            preloadTask.mDownloadPriority = 1;
            paramDeque2.addLast(preloadTask);
            continue;
          } 
          if (PreloadTaskScheduler.getPreloadTaskByAppId(str, paramDeque1) != null) {
            AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "当前高优优先级预下载任务已在更高优先级下载任务等待队列.", "appInfo.appId: ", str });
            continue;
          } 
          if (PreloadTaskScheduler.getPreloadTaskByAppId(str, paramDeque2) != null) {
            AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "小程序已在高优先级预下载任务等待队列.", "appInfo.appId: ", str });
            continue;
          } 
          paramDeque2.addLast(new PreloadTask(preLoadAppEntity, paramMap, (null)paramList));
          continue;
        } 
        if (PreloadTaskScheduler.getPreloadTaskByAppId(str, paramDeque1) != null || PreloadTaskScheduler.getPreloadTaskByAppId(str, paramDeque2) != null) {
          AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "当前普通优先级预下载任务已在更高优先级下载任务等待队列", "appInfo.appId: ", str });
          continue;
        } 
        if (preLoadAppEntity.downgraded()) {
          PreloadTask preloadTask = PreloadTaskScheduler.getExistPreloadTaskInWaitDeque(str);
          AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "该预下载小程序被降级，需要进行特殊处理.", "appInfo.appId: ", str });
          ArrayList<PreloadTask> arrayList1 = arrayList;
          if (arrayList == null)
            arrayList1 = new ArrayList(); 
          if (preLoadAppEntity.getOriginDownloadPriority() == 2) {
            if (preloadTask == null)
              preloadTask = new PreloadTask(preLoadAppEntity, paramMap); 
            arrayList1.add(i, preloadTask);
            i++;
          } else {
            if (preloadTask == null)
              preloadTask = new PreloadTask(preLoadAppEntity, paramMap); 
            arrayList1.add(preloadTask);
          } 
          PreloadTaskScheduler.removeMiniAppPreloadTaskByAppId(str, mNormalPreloadTaskWaitDeque);
          AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "小程序已在普通优先级预下载任务等待队列，但因为降级原因进行特殊处理", "appInfo.appId: ", str });
          preloadTask = null;
          arrayList = arrayList1;
          continue;
        } 
        if (PreloadTaskScheduler.getPreloadTaskByAppId(str, mNormalPreloadTaskWaitDeque) != null) {
          AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "小程序已在普通优先级预下载任务等待队列. appInfo.appId: ", str });
          paramList = null;
          continue;
        } 
        Deque<PreloadTask> deque = mNormalPreloadTaskWaitDeque;
        paramList = null;
        deque.addLast(new PreloadTask(preLoadAppEntity, paramMap));
      } 
    } 
    if (arrayList != null) {
      for (i = arrayList.size() - 1; i >= 0; i--)
        mNormalPreloadTaskWaitDeque.addFirst(arrayList.get(i)); 
      AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "处理降级队列 downgradedOriginPreloadTaskList:", arrayList });
      arrayList.clear();
    } 
  }
  
  private static boolean hasPreloaded(String paramString) {
    boolean bool1 = mPreloadedAppIdList.contains(paramString);
    boolean bool = false;
    if (bool1) {
      bool = true;
      AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "小程序已完成预下载 appId:", paramString });
    } 
    return bool;
  }
  
  private static boolean isHighestPreloadTaskDownloading() {
    Iterator<PreloadTask> iterator = mPreloadingTaskDeque.iterator();
    while (iterator.hasNext()) {
      if (((PreloadTask)iterator.next()).mDownloadPriority == 2)
        return true; 
    } 
    return false;
  }
  
  private static boolean isLazyPreloadMode(PreLoadAppEntity paramPreLoadAppEntity) {
    int i = paramPreLoadAppEntity.getPreloadMode();
    if (PreLoadAppEntity.isPreloadModeValid(i))
      return (i == 1); 
    i = SettingsDAO.getInt((Context)AppbrandContext.getInst().getApplicationContext(), 0, new Enum[] { (Enum)Settings.BDP_TTPKG_CONFIG, (Enum)Settings.BdpTtPkgConfig.PRELOAD_MODE });
    return PreLoadAppEntity.isPreloadModeValid(i) ? ((i == 1)) : true;
  }
  
  public static void observeNetStateChange() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/manager/MiniAppPreloadManager.mNetStateChangeListener : Lcom/tt/miniapp/manager/NetStateManager$NetStateChangeListener;
    //   3: ifnonnull -> 44
    //   6: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/manager/MiniAppPreloadManager.mNetStateChangeListener : Lcom/tt/miniapp/manager/NetStateManager$NetStateChangeListener;
    //   12: ifnonnull -> 34
    //   15: new com/tt/miniapp/manager/MiniAppPreloadManager$1
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putstatic com/tt/miniapp/manager/MiniAppPreloadManager.mNetStateChangeListener : Lcom/tt/miniapp/manager/NetStateManager$NetStateChangeListener;
    //   25: invokestatic getInst : ()Lcom/tt/miniapp/manager/NetStateManager;
    //   28: getstatic com/tt/miniapp/manager/MiniAppPreloadManager.mNetStateChangeListener : Lcom/tt/miniapp/manager/NetStateManager$NetStateChangeListener;
    //   31: invokevirtual registerNetStateChangeReceiver : (Lcom/tt/miniapp/manager/NetStateManager$NetStateChangeListener;)V
    //   34: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   36: monitorexit
    //   37: return
    //   38: astore_0
    //   39: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   41: monitorexit
    //   42: aload_0
    //   43: athrow
    //   44: return
    // Exception table:
    //   from	to	target	type
    //   9	34	38	finally
    //   34	37	38	finally
    //   39	42	38	finally
  }
  
  private static void onPreloadListValid(Map<String, String> paramMap, String paramString, MiniAppPreloadListCheckListener paramMiniAppPreloadListCheckListener) {
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("errMsg", paramString);
      if (paramMap != null)
        jSONObject.put("scene", paramMap.get("scene")); 
      jSONObject.put("_param_for_special", "micro_app");
      AppBrandMonitor.statusRate("mp_preload_download_case", 8000, jSONObject);
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("MiniAppPreloadManager", "uploadDownloadSuccessMpMonitor", exception);
    } 
    if (paramMiniAppPreloadListCheckListener != null)
      paramMiniAppPreloadListCheckListener.onPreloadMiniAppListInvalid(paramString); 
  }
  
  public static void onPreloadMiniAppFinish(PreloadTask paramPreloadTask, boolean paramBoolean) {
    AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "onPreloadMiniAppFinish. isPreloadSuccess:", Boolean.valueOf(paramBoolean) });
    String str = paramPreloadTask.mAppId;
    if (paramBoolean) {
      mPreloadedAppIdList.add(str);
      try {
        Application application = AppbrandContext.getInst().getApplicationContext();
        LaunchCacheCleanDataManager.INSTANCE.manageCacheForPreDownloadSuccess((Context)application, str);
      } catch (Exception exception) {
        AppBrandLogger.eWithThrowable("MiniAppPreloadManager", "managePreloadMiniAppStorage", exception);
      } 
    } 
    PreloadTaskScheduler.removeMiniAppPreloadTaskByAppId(str, mPreloadingTaskDeque);
    schedulePreloadTask(null, null);
  }
  
  private static void preloadMiniApp(List<PreLoadAppEntity> paramList, Map<String, String> paramMap) {
    // Byte code:
    //   0: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   2: monitorenter
    //   3: aload_0
    //   4: aload_1
    //   5: aconst_null
    //   6: invokestatic preloadMiniApp : (Ljava/util/List;Ljava/util/Map;Ljava/util/concurrent/Executor;)V
    //   9: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   11: monitorexit
    //   12: return
    //   13: astore_0
    //   14: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   16: monitorexit
    //   17: aload_0
    //   18: athrow
    // Exception table:
    //   from	to	target	type
    //   3	9	13	finally
  }
  
  public static void preloadMiniApp(List<PreLoadAppEntity> paramList, Map<String, String> paramMap, Executor paramExecutor) {
    // Byte code:
    //   0: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   2: monitorenter
    //   3: ldc 'MiniAppPreloadManager'
    //   5: iconst_1
    //   6: anewarray java/lang/Object
    //   9: dup
    //   10: iconst_0
    //   11: ldc_w 'preloadMiniApp'
    //   14: aastore
    //   15: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   18: new java/util/ArrayDeque
    //   21: dup
    //   22: iconst_1
    //   23: invokespecial <init> : (I)V
    //   26: astore_3
    //   27: new java/util/ArrayDeque
    //   30: dup
    //   31: iconst_2
    //   32: invokespecial <init> : (I)V
    //   35: astore #4
    //   37: aload_0
    //   38: aload_3
    //   39: aload #4
    //   41: aload_1
    //   42: invokestatic fillPreloadDeque : (Ljava/util/List;Ljava/util/Deque;Ljava/util/Deque;Ljava/util/Map;)V
    //   45: aload_3
    //   46: aload #4
    //   48: aload_2
    //   49: invokestatic schedulePreloadTask : (Ljava/util/Deque;Ljava/util/Deque;Ljava/util/concurrent/Executor;)V
    //   52: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   54: monitorexit
    //   55: return
    //   56: astore_0
    //   57: ldc com/tt/miniapp/manager/MiniAppPreloadManager
    //   59: monitorexit
    //   60: aload_0
    //   61: athrow
    // Exception table:
    //   from	to	target	type
    //   3	52	56	finally
  }
  
  public static void schedulePreloadTask(Deque<PreloadTask> paramDeque1, Deque<PreloadTask> paramDeque2) {
    schedulePreloadTask(paramDeque1, paramDeque2, null);
  }
  
  private static void schedulePreloadTask(Deque<PreloadTask> paramDeque1, Deque<PreloadTask> paramDeque2, Executor paramExecutor) {
    AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "schedulePreloadTask begin mPreloadingTaskDeque:", mPreloadingTaskDeque });
    ArrayList<PreloadTask> arrayList2 = new ArrayList();
    ArrayList<PreloadTask> arrayList1 = new ArrayList<PreloadTask>(mPreloadingTaskDeque);
    if (paramDeque1 != null && !paramDeque1.isEmpty()) {
      if (paramDeque1.size() > 1)
        AppBrandLogger.e("MiniAppPreloadManager", new Object[] { "极速预下载调度队列长度异常，填充队列前的校验机制失效", " highestPreloadTaskScheduleDeque:", paramDeque1 }); 
      arrayList2.add(paramDeque1.pollFirst());
      paramDeque1.clear();
      i = 1;
    } else {
      i = 0;
    } 
    if (isHighestPreloadTaskDownloading() || i) {
      i = 1;
    } else {
      i = 0;
    } 
    if (paramDeque2 != null && !paramDeque2.isEmpty()) {
      int k = paramDeque2.size();
      if (k > 2)
        AppBrandLogger.e("MiniAppPreloadManager", new Object[] { "高优预下载调度队列长度异常，填充队列前的校验机制失效", " highPreloadTaskScheduleDeque:", paramDeque2 }); 
      if (i) {
        for (i = 0; i < k; i++)
          downgradedTask(paramDeque2.pollLast()); 
      } else {
        arrayList2.addAll(paramDeque2);
      } 
      paramDeque2.clear();
    } 
    int j = arrayList2.size();
    int i;
    for (i = 0; i < j; i++) {
      PreloadTask preloadTask = arrayList2.get(i);
      if (preloadTask.mDownloadPriority == 2) {
        for (PreloadTask preloadTask1 : mPreloadingTaskDeque) {
          if (preloadTask1.mDownloadPriority == 0) {
            PreDownloadScheduler.stopPreDownloadMiniApp(preloadTask1);
            downgradedTask(preloadTask1);
          } 
        } 
        for (PreloadTask preloadTask1 : mPreloadingTaskDeque) {
          if (preloadTask1.mDownloadPriority != 0) {
            PreDownloadScheduler.stopPreDownloadMiniApp(preloadTask1);
            downgradedTask(preloadTask1);
          } 
        } 
        mPreloadingTaskDeque.clear();
      } else {
        paramDeque1 = null;
        if (mPreloadingTaskDeque.size() == 2) {
          PreloadTask preloadTask1;
          for (PreloadTask preloadTask2 : mPreloadingTaskDeque) {
            if (preloadTask2.mDownloadPriority == 0)
              preloadTask1 = preloadTask2; 
          } 
          if (preloadTask1 == null) {
            preloadTask1 = mPreloadingTaskDeque.pollFirst();
          } else {
            mPreloadingTaskDeque.remove(preloadTask1);
          } 
          if (preloadTask1 != null) {
            PreDownloadScheduler.stopPreDownloadMiniApp(preloadTask1);
            downgradedTask(preloadTask1);
          } else {
            AppBrandLogger.e("MiniAppPreloadManager", new Object[] { "高优预下载任务抢占预下载队列异常 mPreloadingTaskDeque:", mPreloadingTaskDeque });
          } 
        } 
      } 
      mPreloadingTaskDeque.addLast(preloadTask);
    } 
    arrayList2.clear();
    if (isHighestPreloadTaskDownloading()) {
      for (PreloadTask preloadTask : mPreloadingTaskDeque) {
        if (preloadTask.mDownloadPriority != 2) {
          PreDownloadScheduler.stopPreDownloadMiniApp(preloadTask);
          downgradedTask(preloadTask);
        } 
      } 
    } else {
      j = mPreloadingTaskDeque.size();
      for (i = 0; i < 2 - j && mNormalPreloadTaskWaitDeque.size() > 0; i++)
        mPreloadingTaskDeque.addLast(mNormalPreloadTaskWaitDeque.pollFirst()); 
    } 
    for (PreloadTask preloadTask : mPreloadingTaskDeque) {
      if (!arrayList1.contains(preloadTask) || preloadTask.isStoppedPreload())
        PreloadTaskScheduler.triggerPreload(preloadTask, paramExecutor); 
    } 
    arrayList1.clear();
    AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "schedulePreloadTask finish mPreloadingTaskDeque:", mPreloadingTaskDeque });
    adjustPreloadWaitDeque();
  }
  
  public static void setMiniAppPreloadConfigEntity(MiniAppPreloadConfigEntity paramMiniAppPreloadConfigEntity) {
    if (paramMiniAppPreloadConfigEntity == null)
      return; 
    mMiniAppPreloadConfigEntity = paramMiniAppPreloadConfigEntity;
  }
  
  public static void startPreloadMiniApp(List<PreLoadAppEntity> paramList, Map<String, String> paramMap, MiniAppPreloadListCheckListener paramMiniAppPreloadListCheckListener) {
    startPreloadMiniApp(paramList, paramMap, paramMiniAppPreloadListCheckListener, null);
  }
  
  public static void startPreloadMiniApp(final List<PreLoadAppEntity> preloadAppList, final Map<String, String> extraParams, final MiniAppPreloadListCheckListener preloadListCheckListener, final Executor executor) {
    AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "startPreloadMiniApp" });
    if (preloadAppList == null)
      return; 
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            MiniAppPreloadManager.observeNetStateChange();
            MiniAppPreloadManager.checkPreloadListValid(preloadAppList, extraParams, preloadListCheckListener);
            MiniAppPreloadManager.preloadMiniApp(preloadAppList, extraParams, executor);
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  static class PreDownloadScheduler {
    private static void preDownloadMiniApp(MiniAppPreloadManager.PreloadTask param1PreloadTask) {
      preDownloadMiniApp(param1PreloadTask, null);
    }
    
    public static void preDownloadMiniApp(final MiniAppPreloadManager.PreloadTask preloadTask, Executor param1Executor) {
      Scheduler scheduler;
      SilencePkgRequester silencePkgRequester;
      PreloadPkgRequester preloadPkgRequester;
      AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "preDownloadMiniApp preloadTask:", preloadTask });
      Application application = AppbrandContext.getInst().getApplicationContext();
      if (preloadTask.mExtraParams != null && TextUtils.equals(RequestType.silence.toString(), preloadTask.mExtraParams.get("__inner_preload_type"))) {
        silencePkgRequester = new SilencePkgRequester((Context)application);
      } else {
        preloadPkgRequester = new PreloadPkgRequester((Context)silencePkgRequester);
      } 
      AppInfoEntity appInfoEntity = preloadTask.mAppInfo;
      if (param1Executor != null) {
        ExecutorToSchedulersAdapter executorToSchedulersAdapter = new ExecutorToSchedulersAdapter(param1Executor);
      } else {
        scheduler = ThreadPools.longIO();
      } 
      preloadPkgRequester.request(appInfoEntity, scheduler, new StreamDownloadInstallListener() {
            public final void onDownloadSuccess(File param2File, boolean param2Boolean) {
              // Byte code:
              //   0: ldc 'MiniAppPreloadManager'
              //   2: iconst_2
              //   3: anewarray java/lang/Object
              //   6: dup
              //   7: iconst_0
              //   8: ldc 'preDownloadMiniApp 回调小程序预下载成功 appId: '
              //   10: aastore
              //   11: dup
              //   12: iconst_1
              //   13: aload_0
              //   14: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   17: getfield mAppId : Ljava/lang/String;
              //   20: aastore
              //   21: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
              //   24: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   26: monitorenter
              //   27: aload_0
              //   28: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   31: iconst_1
              //   32: invokestatic onPreloadMiniAppFinish : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Z)V
              //   35: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   37: monitorexit
              //   38: aload_1
              //   39: invokevirtual exists : ()Z
              //   42: ifeq -> 53
              //   45: aload_1
              //   46: invokevirtual length : ()J
              //   49: lstore_3
              //   50: goto -> 55
              //   53: lconst_0
              //   54: lstore_3
              //   55: aload_0
              //   56: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   59: iconst_0
              //   60: lload_3
              //   61: invokevirtual onPreloadFinish : (ZJ)V
              //   64: return
              //   65: astore_1
              //   66: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   68: monitorexit
              //   69: aload_1
              //   70: athrow
              // Exception table:
              //   from	to	target	type
              //   27	38	65	finally
              //   66	69	65	finally
            }
            
            public final void onDownloadingProgress(int param2Int, long param2Long) {
              preloadTask.onPreloadProgress(param2Int);
            }
            
            public final void onFail(String param2String1, String param2String2) {
              // Byte code:
              //   0: ldc 'MiniAppPreloadManager'
              //   2: iconst_5
              //   3: anewarray java/lang/Object
              //   6: dup
              //   7: iconst_0
              //   8: ldc 'preDownloadMiniApp 回调小程序预下载失败 '
              //   10: aastore
              //   11: dup
              //   12: iconst_1
              //   13: ldc 'appId: '
              //   15: aastore
              //   16: dup
              //   17: iconst_2
              //   18: aload_0
              //   19: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   22: getfield mAppId : Ljava/lang/String;
              //   25: aastore
              //   26: dup
              //   27: iconst_3
              //   28: ldc 'errMsg: '
              //   30: aastore
              //   31: dup
              //   32: iconst_4
              //   33: aload_2
              //   34: aastore
              //   35: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
              //   38: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   40: monitorenter
              //   41: aload_0
              //   42: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   45: iconst_0
              //   46: invokestatic onPreloadMiniAppFinish : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Z)V
              //   49: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   51: monitorexit
              //   52: aload_0
              //   53: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   56: invokevirtual onPreloadFail : ()V
              //   59: return
              //   60: astore_1
              //   61: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   63: monitorexit
              //   64: aload_1
              //   65: athrow
              // Exception table:
              //   from	to	target	type
              //   41	52	60	finally
              //   61	64	60	finally
            }
            
            public final void onInstallSuccess() {}
            
            public final void onStop() {
              AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "preDownloadMiniApp 回调小程序预下载停止 appId: ", this.val$preloadTask.mAppId });
              preloadTask.onPreloadStop();
            }
          });
      preloadTask.startPreDownload();
    }
    
    public static void stopPreDownloadMiniApp(MiniAppPreloadManager.PreloadTask param1PreloadTask) {
      if (param1PreloadTask.isStoppedPreload())
        return; 
      AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "stopPreDownloadMiniApp appId:", param1PreloadTask.mAppId });
      if (param1PreloadTask.mAppInfo != null)
        StreamDownloader.stopStreamDownloadPkg(param1PreloadTask.mAppInfo); 
      param1PreloadTask.stopPreload();
    }
  }
  
  static final class null implements StreamDownloadInstallListener {
    public final void onDownloadSuccess(File param1File, boolean param1Boolean) {
      // Byte code:
      //   0: ldc 'MiniAppPreloadManager'
      //   2: iconst_2
      //   3: anewarray java/lang/Object
      //   6: dup
      //   7: iconst_0
      //   8: ldc 'preDownloadMiniApp 回调小程序预下载成功 appId: '
      //   10: aastore
      //   11: dup
      //   12: iconst_1
      //   13: aload_0
      //   14: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   17: getfield mAppId : Ljava/lang/String;
      //   20: aastore
      //   21: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   24: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   26: monitorenter
      //   27: aload_0
      //   28: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   31: iconst_1
      //   32: invokestatic onPreloadMiniAppFinish : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Z)V
      //   35: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   37: monitorexit
      //   38: aload_1
      //   39: invokevirtual exists : ()Z
      //   42: ifeq -> 53
      //   45: aload_1
      //   46: invokevirtual length : ()J
      //   49: lstore_3
      //   50: goto -> 55
      //   53: lconst_0
      //   54: lstore_3
      //   55: aload_0
      //   56: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   59: iconst_0
      //   60: lload_3
      //   61: invokevirtual onPreloadFinish : (ZJ)V
      //   64: return
      //   65: astore_1
      //   66: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   68: monitorexit
      //   69: aload_1
      //   70: athrow
      // Exception table:
      //   from	to	target	type
      //   27	38	65	finally
      //   66	69	65	finally
    }
    
    public final void onDownloadingProgress(int param1Int, long param1Long) {
      preloadTask.onPreloadProgress(param1Int);
    }
    
    public final void onFail(String param1String1, String param1String2) {
      // Byte code:
      //   0: ldc 'MiniAppPreloadManager'
      //   2: iconst_5
      //   3: anewarray java/lang/Object
      //   6: dup
      //   7: iconst_0
      //   8: ldc 'preDownloadMiniApp 回调小程序预下载失败 '
      //   10: aastore
      //   11: dup
      //   12: iconst_1
      //   13: ldc 'appId: '
      //   15: aastore
      //   16: dup
      //   17: iconst_2
      //   18: aload_0
      //   19: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   22: getfield mAppId : Ljava/lang/String;
      //   25: aastore
      //   26: dup
      //   27: iconst_3
      //   28: ldc 'errMsg: '
      //   30: aastore
      //   31: dup
      //   32: iconst_4
      //   33: aload_2
      //   34: aastore
      //   35: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   38: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   40: monitorenter
      //   41: aload_0
      //   42: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   45: iconst_0
      //   46: invokestatic onPreloadMiniAppFinish : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Z)V
      //   49: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   51: monitorexit
      //   52: aload_0
      //   53: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   56: invokevirtual onPreloadFail : ()V
      //   59: return
      //   60: astore_1
      //   61: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   63: monitorexit
      //   64: aload_1
      //   65: athrow
      // Exception table:
      //   from	to	target	type
      //   41	52	60	finally
      //   61	64	60	finally
    }
    
    public final void onInstallSuccess() {}
    
    public final void onStop() {
      AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "preDownloadMiniApp 回调小程序预下载停止 appId: ", this.val$preloadTask.mAppId });
      preloadTask.onPreloadStop();
    }
  }
  
  static class PreloadTask {
    public final String mAppId;
    
    public AppInfoEntity mAppInfo;
    
    public final int mAppType;
    
    public int mDownloadPriority;
    
    private int mDownloadProgress = -1;
    
    public Map<String, String> mExtraParams;
    
    public boolean mIsRequestingAppInfo;
    
    private boolean mStopped;
    
    private PreloadTask(PreLoadAppEntity param1PreLoadAppEntity, Map<String, String> param1Map) {
      this.mAppId = param1PreLoadAppEntity.getAppid();
      this.mAppType = param1PreLoadAppEntity.getApptype();
      this.mDownloadPriority = param1PreLoadAppEntity.getDownloadPriority();
      this.mExtraParams = param1Map;
    }
    
    private void onPreloadResume() {
      MiniAppPreloadStateListener miniAppPreloadStateListener = MiniAppPreloadManager.mMiniAppPreloadConfigEntity.getPreloadStateListener();
      if (miniAppPreloadStateListener != null) {
        AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "onPreloadResume", this.mAppId });
        miniAppPreloadStateListener.onPreloadResume(this.mAppId);
      } 
    }
    
    private void onPreloadStart() {
      MiniAppPreloadStateListener miniAppPreloadStateListener = MiniAppPreloadManager.mMiniAppPreloadConfigEntity.getPreloadStateListener();
      if (miniAppPreloadStateListener != null) {
        AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "onPreloadStart", this.mAppId });
        miniAppPreloadStateListener.onPreloadStart(this.mAppId);
      } 
    }
    
    public boolean isStoppedPreload() {
      return this.mStopped;
    }
    
    public void onPreloadCancel() {
      MiniAppPreloadStateListener miniAppPreloadStateListener = MiniAppPreloadManager.mMiniAppPreloadConfigEntity.getPreloadStateListener();
      if (miniAppPreloadStateListener != null) {
        AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "onPreloadCancel", this.mAppId });
        miniAppPreloadStateListener.onPreloadCancel(this.mAppId);
      } 
    }
    
    public void onPreloadFail() {
      MiniAppPreloadStateListener miniAppPreloadStateListener = MiniAppPreloadManager.mMiniAppPreloadConfigEntity.getPreloadStateListener();
      if (miniAppPreloadStateListener != null) {
        AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "onPreloadFail", this.mAppId });
        miniAppPreloadStateListener.onPreloadFail(this.mAppId);
      } 
    }
    
    public void onPreloadFinish(boolean param1Boolean, long param1Long) {
      MiniAppPreloadStateListener miniAppPreloadStateListener = MiniAppPreloadManager.mMiniAppPreloadConfigEntity.getPreloadStateListener();
      if (miniAppPreloadStateListener != null) {
        AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "onPreloadFinish", this.mAppId });
        miniAppPreloadStateListener.onPreloadFinish(this.mAppId, param1Boolean, param1Long);
      } 
    }
    
    public void onPreloadProgress(int param1Int) {
      if (this.mStopped)
        return; 
      MiniAppPreloadStateListener miniAppPreloadStateListener = MiniAppPreloadManager.mMiniAppPreloadConfigEntity.getPreloadStateListener();
      if (miniAppPreloadStateListener != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mAppId);
        stringBuilder.append(" , ");
        stringBuilder.append(param1Int);
        AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "onPreloadProgress", stringBuilder.toString() });
        miniAppPreloadStateListener.onPreloadProgress(this.mAppId, param1Int);
      } 
    }
    
    public void onPreloadStop() {
      MiniAppPreloadStateListener miniAppPreloadStateListener = MiniAppPreloadManager.mMiniAppPreloadConfigEntity.getPreloadStateListener();
      if (miniAppPreloadStateListener != null) {
        AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "onPreloadStop", this.mAppId });
        miniAppPreloadStateListener.onPreloadStop(this.mAppId);
      } 
    }
    
    public void startPreDownload() {
      if (this.mStopped)
        onPreloadResume(); 
      this.mStopped = false;
      if (this.mDownloadProgress == -1)
        this.mDownloadProgress = 0; 
    }
    
    public void startPreload() {
      if (this.mStopped) {
        onPreloadResume();
      } else {
        onPreloadStart();
      } 
      this.mStopped = false;
    }
    
    public void stopPreload() {
      if (!this.mStopped)
        onPreloadStop(); 
      this.mStopped = true;
    }
    
    public String toString() {
      boolean bool;
      StringBuilder stringBuilder = new StringBuilder("mAppId: ");
      stringBuilder.append(this.mAppId);
      stringBuilder.append(" mAppInfo != null: ");
      if (this.mAppInfo != null) {
        bool = true;
      } else {
        bool = false;
      } 
      stringBuilder.append(bool);
      stringBuilder.append(" mDownloadPriority: ");
      stringBuilder.append(this.mDownloadPriority);
      stringBuilder.append(" mStopped: ");
      stringBuilder.append(this.mStopped);
      stringBuilder.append(" mDownloadProgress: ");
      stringBuilder.append(this.mDownloadProgress);
      return stringBuilder.toString();
    }
    
    public void updateAppInfo(AppInfoEntity param1AppInfoEntity) {
      this.mAppInfo = param1AppInfoEntity;
      Map<String, String> map = this.mExtraParams;
      if (map != null) {
        this.mAppInfo.scene = map.get("scene");
        this.mAppInfo.launchFrom = this.mExtraParams.get("launch_from");
      } 
      this.mIsRequestingAppInfo = false;
    }
  }
  
  static class PreloadTaskScheduler {
    public static void cancelAllPreloadTask() {
      AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "cancelAllPreloadTask" });
      MiniAppPreloadManager.mNormalPreloadTaskWaitDeque.clear();
      for (int i = MiniAppPreloadManager.mPreloadingTaskDeque.size() - 1; i >= 0; i--)
        cancelPreloadTask(MiniAppPreloadManager.mPreloadingTaskDeque.getFirst(), false); 
    }
    
    public static void cancelPreloadTask(MiniAppPreloadManager.PreloadTask param1PreloadTask, boolean param1Boolean) {
      AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "cancelPreloadTask preloadTask.mAppId:", param1PreloadTask.mAppId });
      removeMiniAppPreloadTaskByAppId(param1PreloadTask.mAppId, MiniAppPreloadManager.mNormalPreloadTaskWaitDeque);
      MiniAppPreloadManager.mPreloadingTaskDeque.remove(param1PreloadTask);
      MiniAppPreloadManager.PreDownloadScheduler.stopPreDownloadMiniApp(param1PreloadTask);
      param1PreloadTask.onPreloadCancel();
      if (param1Boolean)
        MiniAppPreloadManager.schedulePreloadTask(null, null); 
    }
    
    public static void cancelPreloadTask(String param1String, boolean param1Boolean) {
      AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "cancelPreloadTask appId:", param1String });
      MiniAppPreloadManager.PreloadTask preloadTask = getPreloadTaskByAppId(param1String, MiniAppPreloadManager.mPreloadingTaskDeque);
      if (preloadTask != null) {
        cancelPreloadTask(preloadTask, param1Boolean);
        return;
      } 
      removeMiniAppPreloadTaskByAppId(param1String, MiniAppPreloadManager.mNormalPreloadTaskWaitDeque);
    }
    
    public static MiniAppPreloadManager.PreloadTask getExistPreloadTaskInWaitDeque(String param1String) {
      MiniAppPreloadManager.PreloadTask preloadTask = getPreloadTaskByAppId(param1String, MiniAppPreloadManager.mNormalPreloadTaskWaitDeque);
      if (preloadTask != null)
        MiniAppPreloadManager.mNormalPreloadTaskWaitDeque.remove(preloadTask); 
      return preloadTask;
    }
    
    public static MiniAppPreloadManager.PreloadTask getPreloadTaskByAppId(String param1String, Deque<MiniAppPreloadManager.PreloadTask> param1Deque) {
      for (MiniAppPreloadManager.PreloadTask preloadTask : param1Deque) {
        if (TextUtils.equals(preloadTask.mAppId, param1String))
          return preloadTask; 
      } 
      return null;
    }
    
    public static void removeMiniAppPreloadTaskByAppId(String param1String, Deque<MiniAppPreloadManager.PreloadTask> param1Deque) {
      MiniAppPreloadManager.PreloadTask preloadTask = getPreloadTaskByAppId(param1String, param1Deque);
      if (preloadTask != null)
        param1Deque.remove(preloadTask); 
    }
    
    public static void stopAllPreloadingTask() {
      AppBrandLogger.i("MiniAppPreloadManager", new Object[] { "stopAllPreloadingTask" });
      Iterator<MiniAppPreloadManager.PreloadTask> iterator = MiniAppPreloadManager.mPreloadingTaskDeque.iterator();
      while (iterator.hasNext())
        MiniAppPreloadManager.PreDownloadScheduler.stopPreDownloadMiniApp(iterator.next()); 
    }
    
    private static void triggerPreload(MiniAppPreloadManager.PreloadTask param1PreloadTask) {
      triggerPreload(param1PreloadTask, null);
    }
    
    public static void triggerPreload(final MiniAppPreloadManager.PreloadTask preloadTask, final Executor executor) {
      PreloadMetaRequester preloadMetaRequester;
      Scheduler scheduler;
      AppBrandLogger.d("MiniAppPreloadManager", new Object[] { "triggerPreload preloadTask:", preloadTask });
      preloadTask.startPreload();
      if (preloadTask.mAppInfo != null) {
        MiniAppPreloadManager.PreDownloadScheduler.preDownloadMiniApp(preloadTask, executor);
        return;
      } 
      if (preloadTask.mIsRequestingAppInfo)
        return; 
      preloadTask.mIsRequestingAppInfo = true;
      AppInfoEntity appInfoEntity = new AppInfoEntity();
      appInfoEntity.appId = preloadTask.mAppId;
      appInfoEntity.setType(preloadTask.mAppType);
      if (preloadTask.mExtraParams != null && TextUtils.equals(RequestType.silence.toString(), preloadTask.mExtraParams.get("__inner_preload_type"))) {
        try {
          appInfoEntity.versionCode = Long.parseLong(preloadTask.mExtraParams.get("__inner_version_code"));
          SilenceMetaRequester silenceMetaRequester = new SilenceMetaRequester((Context)AppbrandContext.getInst().getApplicationContext());
        } catch (Exception exception) {
          AppBrandLogger.e("MiniAppPreloadManager", new Object[] { exception });
          preloadMetaRequester = new PreloadMetaRequester((Context)AppbrandContext.getInst().getApplicationContext());
        } 
      } else {
        preloadMetaRequester = new PreloadMetaRequester((Context)AppbrandContext.getInst().getApplicationContext());
      } 
      if (executor != null) {
        ExecutorToSchedulersAdapter executorToSchedulersAdapter = new ExecutorToSchedulersAdapter(executor);
      } else {
        scheduler = ThreadPools.longIO();
      } 
      preloadMetaRequester.request(appInfoEntity, scheduler, new AppInfoRequestListener() {
            public final void onAppInfoInvalid(AppInfoEntity param2AppInfoEntity, int param2Int) {
              // Byte code:
              //   0: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   2: monitorenter
              //   3: aload_0
              //   4: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   7: iconst_1
              //   8: invokestatic cancelPreloadTask : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Z)V
              //   11: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   13: monitorexit
              //   14: aload_0
              //   15: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   18: invokevirtual onPreloadFail : ()V
              //   21: return
              //   22: astore_1
              //   23: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   25: monitorexit
              //   26: aload_1
              //   27: athrow
              // Exception table:
              //   from	to	target	type
              //   3	14	22	finally
              //   23	26	22	finally
            }
            
            public final void requestAppInfoFail(String param2String1, String param2String2) {
              // Byte code:
              //   0: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   2: monitorenter
              //   3: aload_0
              //   4: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   7: iconst_1
              //   8: invokestatic cancelPreloadTask : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Z)V
              //   11: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   13: monitorexit
              //   14: aload_0
              //   15: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   18: invokevirtual onPreloadFail : ()V
              //   21: return
              //   22: astore_1
              //   23: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   25: monitorexit
              //   26: aload_1
              //   27: athrow
              // Exception table:
              //   from	to	target	type
              //   3	14	22	finally
              //   23	26	22	finally
            }
            
            public final void requestAppInfoSuccess(AppInfoEntity param2AppInfoEntity) {
              // Byte code:
              //   0: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   2: monitorenter
              //   3: aload_0
              //   4: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   7: aload_1
              //   8: invokevirtual updateAppInfo : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
              //   11: aload_0
              //   12: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   15: invokevirtual isStoppedPreload : ()Z
              //   18: ifeq -> 25
              //   21: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   23: monitorexit
              //   24: return
              //   25: aload_0
              //   26: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
              //   29: aload_0
              //   30: getfield val$executor : Ljava/util/concurrent/Executor;
              //   33: invokestatic preDownloadMiniApp : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Ljava/util/concurrent/Executor;)V
              //   36: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   38: monitorexit
              //   39: return
              //   40: astore_1
              //   41: ldc com/tt/miniapp/manager/MiniAppPreloadManager
              //   43: monitorexit
              //   44: aload_1
              //   45: athrow
              // Exception table:
              //   from	to	target	type
              //   3	24	40	finally
              //   25	39	40	finally
              //   41	44	40	finally
            }
          });
    }
  }
  
  static final class null implements AppInfoRequestListener {
    public final void onAppInfoInvalid(AppInfoEntity param1AppInfoEntity, int param1Int) {
      // Byte code:
      //   0: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   2: monitorenter
      //   3: aload_0
      //   4: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   7: iconst_1
      //   8: invokestatic cancelPreloadTask : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Z)V
      //   11: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   13: monitorexit
      //   14: aload_0
      //   15: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   18: invokevirtual onPreloadFail : ()V
      //   21: return
      //   22: astore_1
      //   23: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   25: monitorexit
      //   26: aload_1
      //   27: athrow
      // Exception table:
      //   from	to	target	type
      //   3	14	22	finally
      //   23	26	22	finally
    }
    
    public final void requestAppInfoFail(String param1String1, String param1String2) {
      // Byte code:
      //   0: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   2: monitorenter
      //   3: aload_0
      //   4: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   7: iconst_1
      //   8: invokestatic cancelPreloadTask : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Z)V
      //   11: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   13: monitorexit
      //   14: aload_0
      //   15: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   18: invokevirtual onPreloadFail : ()V
      //   21: return
      //   22: astore_1
      //   23: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   25: monitorexit
      //   26: aload_1
      //   27: athrow
      // Exception table:
      //   from	to	target	type
      //   3	14	22	finally
      //   23	26	22	finally
    }
    
    public final void requestAppInfoSuccess(AppInfoEntity param1AppInfoEntity) {
      // Byte code:
      //   0: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   2: monitorenter
      //   3: aload_0
      //   4: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   7: aload_1
      //   8: invokevirtual updateAppInfo : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
      //   11: aload_0
      //   12: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   15: invokevirtual isStoppedPreload : ()Z
      //   18: ifeq -> 25
      //   21: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   23: monitorexit
      //   24: return
      //   25: aload_0
      //   26: getfield val$preloadTask : Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;
      //   29: aload_0
      //   30: getfield val$executor : Ljava/util/concurrent/Executor;
      //   33: invokestatic preDownloadMiniApp : (Lcom/tt/miniapp/manager/MiniAppPreloadManager$PreloadTask;Ljava/util/concurrent/Executor;)V
      //   36: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   38: monitorexit
      //   39: return
      //   40: astore_1
      //   41: ldc com/tt/miniapp/manager/MiniAppPreloadManager
      //   43: monitorexit
      //   44: aload_1
      //   45: athrow
      // Exception table:
      //   from	to	target	type
      //   3	24	40	finally
      //   25	39	40	finally
      //   41	44	40	finally
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\MiniAppPreloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */