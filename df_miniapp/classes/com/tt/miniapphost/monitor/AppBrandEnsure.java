package com.tt.miniapphost.monitor;

import com.tt.miniapphost.host.HostDependManager;
import java.util.Collection;
import java.util.Map;

public class AppBrandEnsure {
  public static boolean ensureFalse(boolean paramBoolean) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    boolean bool = paramBoolean;
    if (iEnsure != null)
      bool = iEnsure.ensureFalse(paramBoolean); 
    return bool;
  }
  
  public static boolean ensureFalse(boolean paramBoolean, String paramString) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    boolean bool = paramBoolean;
    if (iEnsure != null)
      bool = iEnsure.ensureFalse(paramBoolean, paramString); 
    return bool;
  }
  
  public static boolean ensureFalse(boolean paramBoolean, String paramString, Map<String, String> paramMap) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    boolean bool = paramBoolean;
    if (iEnsure != null)
      bool = iEnsure.ensureFalse(paramBoolean, paramString, paramMap); 
    return bool;
  }
  
  public static boolean ensureNotEmpty(Collection paramCollection) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    return (iEnsure != null) ? iEnsure.ensureNotEmpty(paramCollection) : ((paramCollection != null && paramCollection.size() != 0));
  }
  
  public static boolean ensureNotNull(Object paramObject) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    return (iEnsure != null) ? iEnsure.ensureNotNull(paramObject) : ((paramObject != null));
  }
  
  public static boolean ensureNotNull(Object paramObject, String paramString) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    return (iEnsure != null) ? iEnsure.ensureNotNull(paramObject, paramString) : ((paramObject != null));
  }
  
  public static void ensureNotReachHere() {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    if (iEnsure != null)
      iEnsure.ensureNotReachHere(); 
  }
  
  public static void ensureNotReachHere(String paramString) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    if (iEnsure != null)
      iEnsure.ensureNotReachHere(paramString); 
  }
  
  public static void ensureNotReachHere(String paramString, Map<String, String> paramMap) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    if (iEnsure != null)
      iEnsure.ensureNotReachHere(paramString, paramMap); 
  }
  
  public static void ensureNotReachHere(Throwable paramThrowable) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    if (iEnsure != null)
      iEnsure.ensureNotReachHere(paramThrowable); 
  }
  
  public static void ensureNotReachHere(Throwable paramThrowable, String paramString) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    if (iEnsure != null)
      iEnsure.ensureNotReachHere(paramThrowable, paramString); 
  }
  
  public static void ensureNotReachHere(Throwable paramThrowable, String paramString, Map<String, String> paramMap) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    if (iEnsure != null)
      iEnsure.ensureNotReachHere(paramThrowable, paramString, paramMap); 
  }
  
  public static boolean ensureTrue(boolean paramBoolean) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    boolean bool = paramBoolean;
    if (iEnsure != null)
      bool = iEnsure.ensureTrue(paramBoolean); 
    return bool;
  }
  
  public static boolean ensureTrue(boolean paramBoolean, String paramString) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    boolean bool = paramBoolean;
    if (iEnsure != null)
      bool = iEnsure.ensureTrue(paramBoolean, paramString); 
    return bool;
  }
  
  public static boolean ensureTrue(boolean paramBoolean, String paramString, Map<String, String> paramMap) {
    IEnsure iEnsure = HostDependManager.getInst().getEnsure();
    boolean bool = paramBoolean;
    if (iEnsure != null)
      bool = iEnsure.ensureTrue(paramBoolean, paramString, paramMap); 
    return bool;
  }
  
  public static interface IEnsure {
    boolean ensureFalse(boolean param1Boolean);
    
    boolean ensureFalse(boolean param1Boolean, String param1String);
    
    boolean ensureFalse(boolean param1Boolean, String param1String, Map<String, String> param1Map);
    
    boolean ensureNotEmpty(Collection param1Collection);
    
    boolean ensureNotNull(Object param1Object);
    
    boolean ensureNotNull(Object param1Object, String param1String);
    
    void ensureNotReachHere();
    
    void ensureNotReachHere(String param1String);
    
    void ensureNotReachHere(String param1String, Map<String, String> param1Map);
    
    void ensureNotReachHere(Throwable param1Throwable);
    
    void ensureNotReachHere(Throwable param1Throwable, String param1String);
    
    void ensureNotReachHere(Throwable param1Throwable, String param1String, Map<String, String> param1Map);
    
    boolean ensureTrue(boolean param1Boolean);
    
    boolean ensureTrue(boolean param1Boolean, String param1String);
    
    boolean ensureTrue(boolean param1Boolean, String param1String, Map<String, String> param1Map);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\monitor\AppBrandEnsure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */