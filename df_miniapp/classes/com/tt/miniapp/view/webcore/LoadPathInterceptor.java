package com.tt.miniapp.view.webcore;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.LifeCycleManager;
import com.tt.miniapp.LifeCycleManager.LifecycleInterest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadPathInterceptor extends AppbrandServiceManager.ServiceBase {
  private ConcurrentHashMap<String, StringObject> mLockMap = new ConcurrentHashMap<String, StringObject>();
  
  private AtomicInteger placeHolderIdGenerator = new AtomicInteger(0);
  
  public LoadPathInterceptor(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
    this.mLockMap.put("page-frame.js", new StringObject());
  }
  
  private String cutFrameJsSuffix(String paramString) {
    String str = paramString;
    if (paramString.endsWith("-frame.js"))
      str = paramString.substring(0, paramString.length() - 9); 
    return str;
  }
  
  public String genPlaceHolder(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(this.placeHolderIdGenerator.getAndIncrement());
    stringBuilder.append(paramString2);
    paramString1 = stringBuilder.toString();
    this.mLockMap.put(paramString1, new StringObject());
    return paramString1;
  }
  
  String interceptPath(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLockMap : Ljava/util/concurrent/ConcurrentHashMap;
    //   4: aload_1
    //   5: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   8: checkcast com/tt/miniapp/view/webcore/LoadPathInterceptor$StringObject
    //   11: astore_3
    //   12: aload_3
    //   13: astore_2
    //   14: aload_3
    //   15: ifnonnull -> 42
    //   18: aload_0
    //   19: getfield mLockMap : Ljava/util/concurrent/ConcurrentHashMap;
    //   22: aload_0
    //   23: aload_1
    //   24: invokespecial cutFrameJsSuffix : (Ljava/lang/String;)Ljava/lang/String;
    //   27: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   30: checkcast com/tt/miniapp/view/webcore/LoadPathInterceptor$StringObject
    //   33: astore_3
    //   34: aload_3
    //   35: astore_2
    //   36: aload_3
    //   37: ifnonnull -> 42
    //   40: aload_1
    //   41: areturn
    //   42: aload_2
    //   43: getfield realPath : Ljava/lang/String;
    //   46: ifnull -> 54
    //   49: aload_2
    //   50: getfield realPath : Ljava/lang/String;
    //   53: areturn
    //   54: aload_2
    //   55: getfield lock : Ljava/lang/Object;
    //   58: astore_1
    //   59: aload_1
    //   60: monitorenter
    //   61: aload_2
    //   62: getfield realPath : Ljava/lang/String;
    //   65: astore_3
    //   66: aload_3
    //   67: ifnonnull -> 80
    //   70: aload_2
    //   71: getfield lock : Ljava/lang/Object;
    //   74: invokevirtual wait : ()V
    //   77: goto -> 61
    //   80: aload_1
    //   81: monitorexit
    //   82: aload_2
    //   83: getfield realPath : Ljava/lang/String;
    //   86: areturn
    //   87: astore_2
    //   88: aload_1
    //   89: monitorexit
    //   90: goto -> 95
    //   93: aload_2
    //   94: athrow
    //   95: goto -> 93
    //   98: astore_3
    //   99: goto -> 61
    // Exception table:
    //   from	to	target	type
    //   61	66	87	finally
    //   70	77	98	java/lang/InterruptedException
    //   70	77	87	finally
    //   80	82	87	finally
    //   88	90	87	finally
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_INSTALL_SUCCESS})
  public void onAppInstallSuccess() {
    updateRealPath("page-frame.js", "page-frame.js");
  }
  
  boolean shouldIntercept(String paramString) {
    return this.mLockMap.containsKey(paramString) ? true : this.mLockMap.containsKey(cutFrameJsSuffix(paramString));
  }
  
  void updateRealPath(String paramString1, String paramString2) {
    StringObject stringObject = this.mLockMap.get(paramString1);
    if (stringObject == null)
      return; 
    synchronized (stringObject.lock) {
      stringObject.realPath = paramString2;
      stringObject.lock.notifyAll();
      return;
    } 
  }
  
  class StringObject {
    final Object lock = new Object();
    
    String realPath;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\LoadPathInterceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */