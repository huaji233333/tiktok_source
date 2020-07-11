package com.tt.miniapp.secrecy;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.audio.AudioRecorderManager;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SecrecyManager {
  public final Object mDefaultLock = new Object();
  
  private SparseArray<List<WeakReference<SecrecyChangedListener>>> mListeners = new SparseArray();
  
  public SparseArray<Object> mSegmentLocks = new SparseArray();
  
  public SparseBooleanArray mUsingTable = new SparseBooleanArray();
  
  private SecrecyManager() {
    Iterator<Integer> iterator = BrandPermissionUtils.getPermissionTypeList().iterator();
    while (iterator.hasNext()) {
      int i = ((Integer)iterator.next()).intValue();
      this.mSegmentLocks.put(i, new Object());
    } 
  }
  
  private void callback(Runnable paramRunnable, boolean paramBoolean) {
    if (!paramBoolean) {
      paramRunnable.run();
      return;
    } 
    AppbrandApplicationImpl.getInst().getMainHandler().post(paramRunnable);
  }
  
  private boolean clearNull(WeakReference<SecrecyChangedListener> paramWeakReference, List<WeakReference<SecrecyChangedListener>> paramList) {
    return ((SecrecyChangedListener)paramWeakReference.get() == null) ? paramList.remove(paramWeakReference) : false;
  }
  
  private List<WeakReference<SecrecyChangedListener>> ensureList(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mListeners : Landroid/util/SparseArray;
    //   4: iload_1
    //   5: invokevirtual get : (I)Ljava/lang/Object;
    //   8: checkcast java/util/List
    //   11: astore_2
    //   12: aload_2
    //   13: ifnull -> 18
    //   16: aload_2
    //   17: areturn
    //   18: aload_0
    //   19: monitorenter
    //   20: aload_0
    //   21: getfield mListeners : Landroid/util/SparseArray;
    //   24: iload_1
    //   25: invokevirtual get : (I)Ljava/lang/Object;
    //   28: checkcast java/util/List
    //   31: astore_2
    //   32: aload_2
    //   33: ifnull -> 40
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_2
    //   39: areturn
    //   40: new java/util/LinkedList
    //   43: dup
    //   44: invokespecial <init> : ()V
    //   47: astore_2
    //   48: aload_0
    //   49: getfield mListeners : Landroid/util/SparseArray;
    //   52: iload_1
    //   53: aload_2
    //   54: invokevirtual put : (ILjava/lang/Object;)V
    //   57: aload_0
    //   58: monitorexit
    //   59: aload_2
    //   60: areturn
    //   61: astore_2
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_2
    //   65: athrow
    // Exception table:
    //   from	to	target	type
    //   20	32	61	finally
    //   36	38	61	finally
    //   40	59	61	finally
    //   62	64	61	finally
  }
  
  public static SecrecyManager inst() {
    return Holder.sInstance;
  }
  
  private void notifyListeners(List<WeakReference<SecrecyChangedListener>> paramList, final int type, final boolean isStart) {
    for (WeakReference<SecrecyChangedListener> weakReference : paramList) {
      final SecrecyChangedListener lis = weakReference.get();
      if (clearNull(weakReference, paramList) || secrecyChangedListener == null)
        continue; 
      callback(new Runnable() {
            public void run() {
              if (isStart) {
                lis.onStart(type);
                return;
              } 
              lis.onStop(type);
            }
          },  secrecyChangedListener.callOnMainThread());
    } 
  }
  
  private void notifyStateChanged(int paramInt, boolean paramBoolean) {
    List<WeakReference<SecrecyChangedListener>> list = (List)this.mListeners.get(paramInt);
    if (list != null)
      notifyListeners(list, paramInt, paramBoolean); 
    list = (List<WeakReference<SecrecyChangedListener>>)this.mListeners.get(-1);
    if (list != null)
      notifyListeners(list, paramInt, paramBoolean); 
  }
  
  public boolean isSecrecyDenied(int paramInt) {
    return (!BrandPermissionUtils.isGranted(paramInt) && BrandPermissionUtils.hasRequestPermission(paramInt));
  }
  
  public boolean notifyStateStart(int paramInt) {
    synchronized (this.mSegmentLocks.get(paramInt, this.mDefaultLock)) {
      if (this.mUsingTable.get(paramInt, false)) {
        AppBrandLogger.d("SecrecyManager", new Object[] { "notifyStateStart: repeated!!!" });
        return false;
      } 
      this.mUsingTable.put(paramInt, true);
      notifyStateChanged(paramInt, true);
      null = new StringBuilder("notifyStateStart: ");
      null.append(paramInt);
      AppBrandLogger.d("SecrecyManager", new Object[] { null.toString() });
      return true;
    } 
  }
  
  public boolean notifyStateStop(int paramInt) {
    synchronized (this.mSegmentLocks.get(paramInt, this.mDefaultLock)) {
      if (!this.mUsingTable.get(paramInt, true)) {
        AppBrandLogger.d("SecrecyManager", new Object[] { "notifyStateStop: no value!!!" });
        return false;
      } 
      this.mUsingTable.put(paramInt, false);
      notifyStateChanged(paramInt, false);
      null = new StringBuilder("notifyStateStop: ");
      null.append(paramInt);
      AppBrandLogger.d("SecrecyManager", new Object[] { null.toString() });
      return true;
    } 
  }
  
  public void registerListener(int paramInt, SecrecyChangedListener paramSecrecyChangedListener) {
    String str;
    if (paramSecrecyChangedListener == null)
      return; 
    List<WeakReference<SecrecyChangedListener>> list = ensureList(paramInt);
    Iterator<WeakReference<SecrecyChangedListener>> iterator = list.iterator();
    boolean bool = false;
    while (iterator.hasNext()) {
      WeakReference<SecrecyChangedListener> weakReference = iterator.next();
      SecrecyChangedListener secrecyChangedListener = weakReference.get();
      if (!clearNull(weakReference, list) && Objects.equals(secrecyChangedListener, paramSecrecyChangedListener))
        bool = true; 
    } 
    if (bool)
      return; 
    list.add(new WeakReference<SecrecyChangedListener>(paramSecrecyChangedListener));
    StringBuilder stringBuilder = new StringBuilder("registerListener: ");
    if (-1 != paramInt) {
      Integer integer = Integer.valueOf(paramInt);
    } else {
      str = "all";
    } 
    stringBuilder.append(str);
    stringBuilder.append(" list=");
    stringBuilder.append(list.size());
    AppBrandLogger.d("SecrecyManager", new Object[] { stringBuilder.toString() });
  }
  
  public void secrecyPermissionChanged(final int typeData, boolean paramBoolean) {
    if (paramBoolean)
      return; 
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            synchronized (SecrecyManager.this.mSegmentLocks.get(typeData, SecrecyManager.this.mDefaultLock)) {
              if (!SecrecyManager.this.mUsingTable.get(typeData, true)) {
                AppBrandLogger.d("SecrecyManager", new Object[] { "secrecyPermissionChanged: not using" });
                return;
              } 
              if (13 == typeData)
                AudioRecorderManager.getInst().stop(); 
              SecrecyManager.this.notifyStateStop(typeData);
              return;
            } 
          }
        }Schedulers.shortIO(), false);
  }
  
  public void unregisterListener(int paramInt, SecrecyChangedListener paramSecrecyChangedListener) {
    Integer integer;
    if (paramSecrecyChangedListener == null)
      return; 
    List<WeakReference<SecrecyChangedListener>> list = (List)this.mListeners.get(paramInt);
    if (list == null)
      return; 
    for (WeakReference<SecrecyChangedListener> weakReference : list) {
      SecrecyChangedListener secrecyChangedListener = weakReference.get();
      if (!clearNull(weakReference, list) && secrecyChangedListener != null && Objects.equals(secrecyChangedListener, paramSecrecyChangedListener))
        list.remove(weakReference); 
    } 
    StringBuilder stringBuilder = new StringBuilder("unregisterListener: ");
    if (-1 == paramInt) {
      String str = "all";
    } else {
      integer = Integer.valueOf(paramInt);
    } 
    stringBuilder.append(integer);
    stringBuilder.append(" list=");
    stringBuilder.append(list.size());
    AppBrandLogger.d("SecrecyManager", new Object[] { stringBuilder.toString() });
  }
  
  static class Holder {
    public static final SecrecyManager sInstance = new SecrecyManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\secrecy\SecrecyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */