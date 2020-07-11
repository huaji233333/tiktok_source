package com.tt.miniapp.manager;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.RecentAppsManager;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.AppLaunchInfo;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.recent.DataChangeListener;
import com.tt.miniapphost.recent.IRecentAppsManager;
import com.tt.option.q.d;
import com.tt.option.q.i;
import com.tt.option.q.j;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class SynHistoryManager implements IRecentAppsManager {
  public final List<DataChangeListener> dataChangeListeners = new ArrayList<DataChangeListener>();
  
  public final List<RecentAppsManager.OnDataGetListener> dataGetListeners = new ArrayList<RecentAppsManager.OnDataGetListener>();
  
  public boolean isRequestingServer;
  
  public final List<AppLaunchInfo> recentAppList = new ArrayList<AppLaunchInfo>();
  
  private SynHistoryManager() {}
  
  private void getDataFromServer(RecentAppsManager.OnDataGetListener paramOnDataGetListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new com/tt/miniapp/manager/SynHistoryManager$2
    //   5: dup
    //   6: aload_0
    //   7: invokespecial <init> : (Lcom/tt/miniapp/manager/SynHistoryManager;)V
    //   10: invokestatic create : (Lcom/storage/async/Function;)Lcom/storage/async/Observable;
    //   13: invokestatic longIO : ()Lcom/storage/async/Scheduler;
    //   16: invokevirtual schudleOn : (Lcom/storage/async/Scheduler;)Lcom/storage/async/Observable;
    //   19: invokestatic ui : ()Lcom/storage/async/Scheduler;
    //   22: invokevirtual observeOn : (Lcom/storage/async/Scheduler;)Lcom/storage/async/Observable;
    //   25: new com/tt/miniapp/manager/SynHistoryManager$3
    //   28: dup
    //   29: aload_0
    //   30: aload_1
    //   31: invokespecial <init> : (Lcom/tt/miniapp/manager/SynHistoryManager;Lcom/tt/miniapphost/RecentAppsManager$OnDataGetListener;)V
    //   34: invokevirtual subscribe : (Lcom/storage/async/Subscriber;)V
    //   37: ldc 'SynHistoryManager'
    //   39: iconst_1
    //   40: anewarray java/lang/Object
    //   43: dup
    //   44: iconst_0
    //   45: ldc 'request data from server'
    //   47: aastore
    //   48: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
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
  
  public static SynHistoryManager getInstance() {
    return Holder.Instance;
  }
  
  public void addDataChangeListener(DataChangeListener paramDataChangeListener) {
    // Byte code:
    //   0: aload_0
    //   1: getfield dataChangeListeners : Ljava/util/List;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_1
    //   8: ifnull -> 22
    //   11: aload_0
    //   12: getfield dataChangeListeners : Ljava/util/List;
    //   15: aload_1
    //   16: invokeinterface add : (Ljava/lang/Object;)Z
    //   21: pop
    //   22: aload_2
    //   23: monitorexit
    //   24: return
    //   25: astore_1
    //   26: aload_2
    //   27: monitorexit
    //   28: aload_1
    //   29: athrow
    // Exception table:
    //   from	to	target	type
    //   11	22	25	finally
    //   22	24	25	finally
    //   26	28	25	finally
  }
  
  public void addToDB(final AppLaunchInfo launchInfos) {
    AppBrandLogger.d("SynHistoryManager", new Object[] { "addToDB ", launchInfos.appId });
    Observable.create(new Action() {
          public void act() {
            DbManager.getInstance().getRecentAppsDao().addRecentApp(launchInfos);
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public void addToRecentApps(AppInfoEntity paramAppInfoEntity) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: ifnonnull -> 9
    //   6: aload_0
    //   7: monitorexit
    //   8: return
    //   9: aload_1
    //   10: getfield isNotRecordRecentUseApps : Z
    //   13: istore_2
    //   14: iload_2
    //   15: ifeq -> 21
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: ldc 'SynHistoryManager'
    //   23: iconst_1
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: ldc 'add recent open'
    //   31: aastore
    //   32: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   35: new com/tt/miniapp/manager/SynHistoryManager$4
    //   38: dup
    //   39: aload_0
    //   40: aload_1
    //   41: invokespecial <init> : (Lcom/tt/miniapp/manager/SynHistoryManager;Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   44: invokestatic create : (Lcom/storage/async/Function;)Lcom/storage/async/Observable;
    //   47: invokestatic longIO : ()Lcom/storage/async/Scheduler;
    //   50: invokevirtual schudleOn : (Lcom/storage/async/Scheduler;)Lcom/storage/async/Observable;
    //   53: invokestatic ui : ()Lcom/storage/async/Scheduler;
    //   56: invokevirtual observeOn : (Lcom/storage/async/Scheduler;)Lcom/storage/async/Observable;
    //   59: new com/tt/miniapp/manager/SynHistoryManager$5
    //   62: dup
    //   63: aload_0
    //   64: aload_1
    //   65: invokespecial <init> : (Lcom/tt/miniapp/manager/SynHistoryManager;Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   68: invokevirtual subscribe : (Lcom/storage/async/Subscriber;)V
    //   71: aload_0
    //   72: monitorexit
    //   73: return
    //   74: astore_1
    //   75: aload_0
    //   76: monitorexit
    //   77: aload_1
    //   78: athrow
    // Exception table:
    //   from	to	target	type
    //   9	14	74	finally
    //   21	71	74	finally
  }
  
  public void clearDB() {
    AppBrandLogger.d("SynHistoryManager", new Object[] { "clearDB " });
    Observable.create(new Action() {
          public void act() {
            DbManager.getInstance().getRecentAppsDao().clearRecentApp();
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public void deleteRecentApp(String paramString, RecentAppsManager.OnAppDeleteListener paramOnAppDeleteListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new com/tt/miniapp/manager/SynHistoryManager$6
    //   5: dup
    //   6: aload_0
    //   7: aload_1
    //   8: invokespecial <init> : (Lcom/tt/miniapp/manager/SynHistoryManager;Ljava/lang/String;)V
    //   11: invokestatic create : (Lcom/storage/async/Function;)Lcom/storage/async/Observable;
    //   14: invokestatic longIO : ()Lcom/storage/async/Scheduler;
    //   17: invokevirtual schudleOn : (Lcom/storage/async/Scheduler;)Lcom/storage/async/Observable;
    //   20: invokestatic ui : ()Lcom/storage/async/Scheduler;
    //   23: invokevirtual observeOn : (Lcom/storage/async/Scheduler;)Lcom/storage/async/Observable;
    //   26: new com/tt/miniapp/manager/SynHistoryManager$7
    //   29: dup
    //   30: aload_0
    //   31: aload_1
    //   32: aload_2
    //   33: invokespecial <init> : (Lcom/tt/miniapp/manager/SynHistoryManager;Ljava/lang/String;Lcom/tt/miniapphost/RecentAppsManager$OnAppDeleteListener;)V
    //   36: invokevirtual subscribe : (Lcom/storage/async/Subscriber;)V
    //   39: aload_0
    //   40: monitorexit
    //   41: return
    //   42: astore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: athrow
    // Exception table:
    //   from	to	target	type
    //   2	39	42	finally
  }
  
  public void getDataFromDB(RecentAppsManager.OnDataGetListener paramOnDataGetListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 'SynHistoryManager'
    //   4: iconst_1
    //   5: anewarray java/lang/Object
    //   8: dup
    //   9: iconst_0
    //   10: ldc 'getDataFromDB'
    //   12: aastore
    //   13: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   16: new com/tt/miniapp/manager/SynHistoryManager$8
    //   19: dup
    //   20: aload_0
    //   21: invokespecial <init> : (Lcom/tt/miniapp/manager/SynHistoryManager;)V
    //   24: invokestatic create : (Lcom/storage/async/Function;)Lcom/storage/async/Observable;
    //   27: invokestatic longIO : ()Lcom/storage/async/Scheduler;
    //   30: invokevirtual schudleOn : (Lcom/storage/async/Scheduler;)Lcom/storage/async/Observable;
    //   33: invokestatic ui : ()Lcom/storage/async/Scheduler;
    //   36: invokevirtual observeOn : (Lcom/storage/async/Scheduler;)Lcom/storage/async/Observable;
    //   39: new com/tt/miniapp/manager/SynHistoryManager$9
    //   42: dup
    //   43: aload_0
    //   44: aload_1
    //   45: invokespecial <init> : (Lcom/tt/miniapp/manager/SynHistoryManager;Lcom/tt/miniapphost/RecentAppsManager$OnDataGetListener;)V
    //   48: invokevirtual subscribe : (Lcom/storage/async/Subscriber;)V
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
  
  public List<AppLaunchInfo> getRecentAppList(RecentAppsManager.OnDataGetListener paramOnDataGetListener) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield dataGetListeners : Ljava/util/List;
    //   6: astore_2
    //   7: aload_2
    //   8: monitorenter
    //   9: aload_1
    //   10: ifnull -> 24
    //   13: aload_0
    //   14: getfield dataGetListeners : Ljava/util/List;
    //   17: aload_1
    //   18: invokeinterface add : (Ljava/lang/Object;)Z
    //   23: pop
    //   24: aload_0
    //   25: getfield isRequestingServer : Z
    //   28: ifeq -> 42
    //   31: aload_0
    //   32: getfield recentAppList : Ljava/util/List;
    //   35: astore_1
    //   36: aload_2
    //   37: monitorexit
    //   38: aload_0
    //   39: monitorexit
    //   40: aload_1
    //   41: areturn
    //   42: aload_0
    //   43: iconst_1
    //   44: putfield isRequestingServer : Z
    //   47: aload_2
    //   48: monitorexit
    //   49: aload_0
    //   50: new com/tt/miniapp/manager/SynHistoryManager$1
    //   53: dup
    //   54: aload_0
    //   55: invokespecial <init> : (Lcom/tt/miniapp/manager/SynHistoryManager;)V
    //   58: invokespecial getDataFromServer : (Lcom/tt/miniapphost/RecentAppsManager$OnDataGetListener;)V
    //   61: aload_0
    //   62: getfield recentAppList : Ljava/util/List;
    //   65: astore_1
    //   66: aload_1
    //   67: monitorenter
    //   68: new java/util/ArrayList
    //   71: dup
    //   72: aload_0
    //   73: getfield recentAppList : Ljava/util/List;
    //   76: invokespecial <init> : (Ljava/util/Collection;)V
    //   79: astore_2
    //   80: aload_1
    //   81: monitorexit
    //   82: aload_0
    //   83: monitorexit
    //   84: aload_2
    //   85: areturn
    //   86: astore_2
    //   87: aload_1
    //   88: monitorexit
    //   89: aload_2
    //   90: athrow
    //   91: astore_1
    //   92: aload_2
    //   93: monitorexit
    //   94: aload_1
    //   95: athrow
    //   96: astore_1
    //   97: aload_0
    //   98: monitorexit
    //   99: aload_1
    //   100: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	96	finally
    //   13	24	91	finally
    //   24	38	91	finally
    //   42	49	91	finally
    //   49	68	96	finally
    //   68	82	86	finally
    //   87	89	86	finally
    //   89	91	96	finally
    //   92	94	91	finally
    //   94	96	96	finally
  }
  
  public boolean removeDataChangeListener(DataChangeListener paramDataChangeListener) {
    // Byte code:
    //   0: aload_0
    //   1: getfield dataChangeListeners : Ljava/util/List;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: aload_1
    //   8: ifnull -> 26
    //   11: aload_0
    //   12: getfield dataChangeListeners : Ljava/util/List;
    //   15: aload_1
    //   16: invokeinterface remove : (Ljava/lang/Object;)Z
    //   21: istore_2
    //   22: aload_3
    //   23: monitorexit
    //   24: iload_2
    //   25: ireturn
    //   26: aload_3
    //   27: monitorexit
    //   28: iconst_0
    //   29: ireturn
    //   30: astore_1
    //   31: aload_3
    //   32: monitorexit
    //   33: aload_1
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   11	24	30	finally
    //   26	28	30	finally
    //   31	33	30	finally
  }
  
  public void removeFromDB(final String appId) {
    AppBrandLogger.d("SynHistoryManager", new Object[] { "removeFromDB appId ", appId });
    Observable.create(new Action() {
          public void act() {
            DbManager.getInstance().getRecentAppsDao().removeRecentApp(appId);
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public void saveDataToDB(final List<AppLaunchInfo> launchInfos) {
    AppBrandLogger.d("SynHistoryManager", new Object[] { "saveDataToDB ", Integer.valueOf(launchInfos.size()) });
    Observable.create(new Action() {
          public void act() {
            DbManager.getInstance().getRecentAppsDao().addAllData(launchInfos);
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  static class Holder {
    public static SynHistoryManager Instance = new SynHistoryManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\SynHistoryManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */