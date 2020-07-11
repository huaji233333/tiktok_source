package com.tt.miniapp;

import com.tt.miniapphost.entity.AppInfoEntity;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LifeCycleManager extends AppbrandServiceManager.ServiceBase {
  private List<Listener> mListeners = new ArrayList<Listener>();
  
  LifeCycleManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private void notifyEventLocked(LifeCycleEvent paramLifeCycleEvent, Object paramObject) {
    Iterator<Listener> iterator = this.mListeners.iterator();
    while (true) {
      if (iterator.hasNext()) {
        Listener listener = iterator.next();
        Method method = listener.mInterestMethods[paramLifeCycleEvent.ordinal()];
        if (method != null) {
          int i = (method.getParameterTypes()).length;
          if (i == 0) {
            try {
              method.invoke(listener.mOwner, new Object[0]);
            } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
            continue;
          } 
          if (i == 1) {
            method.invoke(((Listener)illegalAccessException).mOwner, new Object[] { paramLifeCycleEvent });
            continue;
          } 
          method.invoke(((Listener)illegalAccessException).mOwner, new Object[] { paramLifeCycleEvent, paramObject });
        } 
        continue;
      } 
      return;
    } 
  }
  
  public void addLifeCycleListener(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new com/tt/miniapp/LifeCycleManager$Listener
    //   5: dup
    //   6: aload_0
    //   7: aload_1
    //   8: invokespecial <init> : (Lcom/tt/miniapp/LifeCycleManager;Ljava/lang/Object;)V
    //   11: astore_1
    //   12: aload_0
    //   13: getfield mListeners : Ljava/util/List;
    //   16: aload_1
    //   17: invokeinterface add : (Ljava/lang/Object;)Z
    //   22: pop
    //   23: aload_0
    //   24: monitorexit
    //   25: return
    //   26: astore_1
    //   27: aload_0
    //   28: monitorexit
    //   29: aload_1
    //   30: athrow
    // Exception table:
    //   from	to	target	type
    //   2	23	26	finally
  }
  
  public void checkMethod(Method paramMethod) {
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    int i = arrayOfClass.length;
    if (i == 0)
      return; 
    if (arrayOfClass[0].equals(LifeCycleEvent.class)) {
      if (i <= 2)
        return; 
      throw new UnsupportedOperationException("LifecycleInterest注解的方法最多只能有两个参数");
    } 
    throw new IllegalArgumentException("LifecycleInterest注解的方法要么无参数，要么第一个参数必须为LifeCycleEvent类型");
  }
  
  public void notifyAppCreate() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getstatic com/tt/miniapp/LifeCycleManager$LifeCycleEvent.ON_APP_CREATE : Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;
    //   6: aconst_null
    //   7: invokespecial notifyEventLocked : (Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;Ljava/lang/Object;)V
    //   10: aload_0
    //   11: monitorexit
    //   12: return
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	13	finally
  }
  
  public void notifyAppHide() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getstatic com/tt/miniapp/LifeCycleManager$LifeCycleEvent.ON_APP_HIDE : Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;
    //   6: aconst_null
    //   7: invokespecial notifyEventLocked : (Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;Ljava/lang/Object;)V
    //   10: aload_0
    //   11: monitorexit
    //   12: return
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	13	finally
  }
  
  public void notifyAppInfoInited(AppInfoEntity paramAppInfoEntity) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getstatic com/tt/miniapp/LifeCycleManager$LifeCycleEvent.ON_APP_INFO_INITED : Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;
    //   6: aload_1
    //   7: invokespecial notifyEventLocked : (Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;Ljava/lang/Object;)V
    //   10: aload_0
    //   11: monitorexit
    //   12: return
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	13	finally
  }
  
  public void notifyAppRoute() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getstatic com/tt/miniapp/LifeCycleManager$LifeCycleEvent.ON_APP_ROUTE : Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;
    //   6: aconst_null
    //   7: invokespecial notifyEventLocked : (Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;Ljava/lang/Object;)V
    //   10: aload_0
    //   11: monitorexit
    //   12: return
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	13	finally
  }
  
  public void notifyAppShow() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getstatic com/tt/miniapp/LifeCycleManager$LifeCycleEvent.ON_APP_SHOW : Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;
    //   6: aconst_null
    //   7: invokespecial notifyEventLocked : (Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;Ljava/lang/Object;)V
    //   10: aload_0
    //   11: monitorexit
    //   12: return
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	13	finally
  }
  
  public void notifyAppStartLaunching() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getstatic com/tt/miniapp/LifeCycleManager$LifeCycleEvent.ON_APP_START_LAUNCHING : Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;
    //   6: aconst_null
    //   7: invokespecial notifyEventLocked : (Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;Ljava/lang/Object;)V
    //   10: aload_0
    //   11: monitorexit
    //   12: return
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	13	finally
  }
  
  public void notifyMiniAppInstallSuccess() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getstatic com/tt/miniapp/LifeCycleManager$LifeCycleEvent.ON_APP_INSTALL_SUCCESS : Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;
    //   6: aconst_null
    //   7: invokespecial notifyEventLocked : (Lcom/tt/miniapp/LifeCycleManager$LifeCycleEvent;Ljava/lang/Object;)V
    //   10: aload_0
    //   11: monitorexit
    //   12: return
    //   13: astore_1
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_1
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   2	10	13	finally
  }
  
  public void removeLifeCycleListener(AppbrandServiceManager.ServiceBase paramServiceBase) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mListeners : Ljava/util/List;
    //   6: invokeinterface iterator : ()Ljava/util/Iterator;
    //   11: astore_2
    //   12: aload_2
    //   13: invokeinterface hasNext : ()Z
    //   18: ifeq -> 43
    //   21: aload_2
    //   22: invokeinterface next : ()Ljava/lang/Object;
    //   27: checkcast com/tt/miniapp/LifeCycleManager$Listener
    //   30: getfield mOwner : Ljava/lang/Object;
    //   33: aload_1
    //   34: if_acmpne -> 12
    //   37: aload_2
    //   38: invokeinterface remove : ()V
    //   43: aload_0
    //   44: monitorexit
    //   45: return
    //   46: astore_1
    //   47: aload_0
    //   48: monitorexit
    //   49: goto -> 54
    //   52: aload_1
    //   53: athrow
    //   54: goto -> 52
    // Exception table:
    //   from	to	target	type
    //   2	12	46	finally
    //   12	43	46	finally
  }
  
  public enum LifeCycleEvent {
    ON_APP_CREATE, ON_APP_HIDE, ON_APP_INFO_INITED, ON_APP_INSTALL_SUCCESS, ON_APP_ROUTE, ON_APP_SHOW, ON_APP_START_LAUNCHING;
    
    static {
      ON_APP_HIDE = new LifeCycleEvent("ON_APP_HIDE", 4);
      ON_APP_INSTALL_SUCCESS = new LifeCycleEvent("ON_APP_INSTALL_SUCCESS", 5);
      ON_APP_ROUTE = new LifeCycleEvent("ON_APP_ROUTE", 6);
      $VALUES = new LifeCycleEvent[] { ON_APP_CREATE, ON_APP_START_LAUNCHING, ON_APP_INFO_INITED, ON_APP_SHOW, ON_APP_HIDE, ON_APP_INSTALL_SUCCESS, ON_APP_ROUTE };
    }
  }
  
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD})
  public static @interface LifecycleInterest {
    LifeCycleManager.LifeCycleEvent[] value();
  }
  
  class Listener {
    Method[] mInterestMethods;
    
    Object mOwner;
    
    Listener(Object param1Object) {
      this.mOwner = param1Object;
      this.mInterestMethods = new Method[(LifeCycleManager.LifeCycleEvent.values()).length];
      for (Method method : this.mOwner.getClass().getMethods()) {
        if (method.isAnnotationPresent((Class)LifeCycleManager.LifecycleInterest.class)) {
          LifeCycleManager.LifeCycleEvent[] arrayOfLifeCycleEvent = ((LifeCycleManager.LifecycleInterest)method.<LifeCycleManager.LifecycleInterest>getAnnotation(LifeCycleManager.LifecycleInterest.class)).value();
          int j = arrayOfLifeCycleEvent.length;
          int i = 0;
          while (i < j) {
            LifeCycleManager.LifeCycleEvent lifeCycleEvent = arrayOfLifeCycleEvent[i];
            if (this.mInterestMethods[lifeCycleEvent.ordinal()] == null) {
              LifeCycleManager.this.checkMethod(method);
              this.mInterestMethods[lifeCycleEvent.ordinal()] = method;
              i++;
              continue;
            } 
            StringBuilder stringBuilder = new StringBuilder("超过一个方法注解了相同的事件, ");
            stringBuilder.append(lifeCycleEvent.name());
            stringBuilder.append("class: \"");
            stringBuilder.append(param1Object.getClass().getSimpleName());
            stringBuilder.append("\", method: \"");
            stringBuilder.append(this.mInterestMethods[lifeCycleEvent.ordinal()]);
            stringBuilder.append("\" 和 \"");
            stringBuilder.append(method);
            stringBuilder.append("\"");
            throw new IllegalStateException(stringBuilder.toString());
          } 
        } 
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\LifeCycleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */