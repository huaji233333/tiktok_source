package com.tt.miniapp.settings.data;

import com.tt.miniapp.settings.configs.SettingsConfig;
import com.tt.miniapp.settings.net.SettingsData;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SettingsHandler {
  private static ConcurrentLinkedQueue<SettingsUpdateListener> LISTENERS;
  
  private static volatile int mState;
  
  private static volatile SettingsConfig sConfig;
  
  static void init(SettingsConfig paramSettingsConfig) {
    // Byte code:
    //   0: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/settings/data/SettingsHandler.sConfig : Lcom/tt/miniapp/settings/configs/SettingsConfig;
    //   6: ifnonnull -> 27
    //   9: aload_0
    //   10: putstatic com/tt/miniapp/settings/data/SettingsHandler.sConfig : Lcom/tt/miniapp/settings/configs/SettingsConfig;
    //   13: new java/util/concurrent/ConcurrentLinkedQueue
    //   16: dup
    //   17: invokespecial <init> : ()V
    //   20: putstatic com/tt/miniapp/settings/data/SettingsHandler.LISTENERS : Ljava/util/concurrent/ConcurrentLinkedQueue;
    //   23: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   25: monitorexit
    //   26: return
    //   27: new java/lang/IllegalArgumentException
    //   30: dup
    //   31: ldc 'Settings 只能初始化一次'
    //   33: invokespecial <init> : (Ljava/lang/String;)V
    //   36: athrow
    //   37: astore_0
    //   38: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   40: monitorexit
    //   41: aload_0
    //   42: athrow
    // Exception table:
    //   from	to	target	type
    //   3	23	37	finally
    //   27	37	37	finally
  }
  
  private static void notifyHandlerUpdate(SettingsData paramSettingsData) {
    SettingsDataHandler.update(paramSettingsData);
    Iterator<SettingsUpdateListener> iterator = LISTENERS.iterator();
    while (iterator.hasNext())
      ((SettingsUpdateListener)iterator.next()).onUpdateComplete(); 
  }
  
  static void registerOrNotifyListener(SettingsUpdateListener paramSettingsUpdateListener) {
    // Byte code:
    //   0: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   2: monitorenter
    //   3: aload_0
    //   4: aconst_null
    //   5: invokestatic registerOrNotifyListener : (Lcom/tt/miniapp/settings/data/SettingsUpdateListener;Lcom/tt/miniapp/settings/net/SettingsData;)V
    //   8: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   10: monitorexit
    //   11: return
    //   12: astore_0
    //   13: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   15: monitorexit
    //   16: aload_0
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   3	8	12	finally
  }
  
  private static void registerOrNotifyListener(SettingsUpdateListener paramSettingsUpdateListener, SettingsData paramSettingsData) {
    // Byte code:
    //   0: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/settings/data/SettingsHandler.mState : I
    //   6: ifne -> 19
    //   9: aload_0
    //   10: ifnull -> 19
    //   13: aload_0
    //   14: invokeinterface onUpdateComplete : ()V
    //   19: getstatic com/tt/miniapp/settings/data/SettingsHandler.mState : I
    //   22: iconst_1
    //   23: if_icmpne -> 58
    //   26: aload_1
    //   27: ifnull -> 42
    //   30: aload_0
    //   31: ifnonnull -> 42
    //   34: aload_1
    //   35: invokestatic notifyHandlerUpdate : (Lcom/tt/miniapp/settings/net/SettingsData;)V
    //   38: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   40: monitorexit
    //   41: return
    //   42: aload_1
    //   43: ifnonnull -> 58
    //   46: aload_0
    //   47: ifnull -> 58
    //   50: getstatic com/tt/miniapp/settings/data/SettingsHandler.LISTENERS : Ljava/util/concurrent/ConcurrentLinkedQueue;
    //   53: aload_0
    //   54: invokevirtual offer : (Ljava/lang/Object;)Z
    //   57: pop
    //   58: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   60: monitorexit
    //   61: return
    //   62: astore_0
    //   63: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   65: monitorexit
    //   66: aload_0
    //   67: athrow
    // Exception table:
    //   from	to	target	type
    //   3	9	62	finally
    //   13	19	62	finally
    //   19	26	62	finally
    //   34	38	62	finally
    //   50	58	62	finally
  }
  
  static void unRegisterListener(SettingsUpdateListener paramSettingsUpdateListener) {
    if (paramSettingsUpdateListener == null)
      return; 
    LISTENERS.remove(paramSettingsUpdateListener);
  }
  
  static void updateSettings() {
    // Byte code:
    //   0: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   2: monitorenter
    //   3: iconst_1
    //   4: putstatic com/tt/miniapp/settings/data/SettingsHandler.mState : I
    //   7: getstatic com/tt/miniapp/settings/data/SettingsHandler.sConfig : Lcom/tt/miniapp/settings/configs/SettingsConfig;
    //   10: astore_0
    //   11: aload_0
    //   12: ifnonnull -> 19
    //   15: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   17: monitorexit
    //   18: return
    //   19: aconst_null
    //   20: getstatic com/tt/miniapp/settings/data/SettingsHandler.sConfig : Lcom/tt/miniapp/settings/configs/SettingsConfig;
    //   23: invokevirtual getRequestService : ()Lcom/tt/miniapp/settings/net/RequestService;
    //   26: invokeinterface request : ()Lcom/tt/miniapp/settings/net/SettingsResponse;
    //   31: getfield settingsData : Lcom/tt/miniapp/settings/net/SettingsData;
    //   34: invokestatic registerOrNotifyListener : (Lcom/tt/miniapp/settings/data/SettingsUpdateListener;Lcom/tt/miniapp/settings/net/SettingsData;)V
    //   37: iconst_0
    //   38: putstatic com/tt/miniapp/settings/data/SettingsHandler.mState : I
    //   41: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   43: monitorexit
    //   44: return
    //   45: astore_0
    //   46: ldc com/tt/miniapp/settings/data/SettingsHandler
    //   48: monitorexit
    //   49: aload_0
    //   50: athrow
    // Exception table:
    //   from	to	target	type
    //   3	11	45	finally
    //   19	41	45	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\data\SettingsHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */