package com.tt.miniapp;

import com.tt.miniapphost.AppBrandLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class AppbrandServiceManager {
  private AppbrandApplicationImpl mApp;
  
  private LifeCycleManager mLifeCycleManager;
  
  private Map<Class, ServiceBase> mServiceMap;
  
  public AppbrandServiceManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    this.mApp = paramAppbrandApplicationImpl;
    this.mServiceMap = (Map)new HashMap<Class<?>, ServiceBase>();
    this.mLifeCycleManager = new LifeCycleManager(paramAppbrandApplicationImpl);
    this.mServiceMap.put(LifeCycleManager.class, this.mLifeCycleManager);
  }
  
  final <T extends ServiceBase> T get(Class<T> paramClass) {
    return (T)this.mServiceMap.get(paramClass);
  }
  
  public final <T extends ServiceBase> T register(Class<T> paramClass) {
    try {
      Constructor<T> constructor = paramClass.getDeclaredConstructor(new Class[] { AppbrandApplicationImpl.class });
      constructor.setAccessible(true);
      ServiceBase serviceBase = (ServiceBase)constructor.newInstance(new Object[] { this.mApp });
      this.mServiceMap.put(paramClass, serviceBase);
      this.mLifeCycleManager.addLifeCycleListener(serviceBase);
      return (T)serviceBase;
    } catch (NoSuchMethodException noSuchMethodException) {
    
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InstantiationException instantiationException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    StringBuilder stringBuilder = new StringBuilder("Register service failed: ");
    stringBuilder.append(paramClass.getSimpleName());
    AppBrandLogger.eWithThrowable("AppbrandServiceManager", stringBuilder.toString(), invocationTargetException);
    return null;
  }
  
  public static abstract class ServiceBase {
    public AppbrandApplicationImpl mApp;
    
    public ServiceBase(AppbrandApplicationImpl param1AppbrandApplicationImpl) {
      this.mApp = param1AppbrandApplicationImpl;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\AppbrandServiceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */