package com.tt.miniapp.mmkv;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import org.json.JSONObject;

public class MMKVUsageMonitorHandler extends Handler {
  private static MMKVUsageMonitorHandler instance;
  
  private MMKVUsageMonitorHandler(Looper paramLooper) {
    super(paramLooper);
  }
  
  public static MMKVUsageMonitorHandler getInstance() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/mmkv/MMKVUsageMonitorHandler.instance : Lcom/tt/miniapp/mmkv/MMKVUsageMonitorHandler;
    //   3: ifnonnull -> 45
    //   6: ldc com/tt/miniapp/mmkv/MMKVUsageMonitorHandler
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/mmkv/MMKVUsageMonitorHandler.instance : Lcom/tt/miniapp/mmkv/MMKVUsageMonitorHandler;
    //   12: ifnonnull -> 33
    //   15: new com/tt/miniapp/mmkv/MMKVUsageMonitorHandler
    //   18: dup
    //   19: ldc 'MMKVUsageMonitorHandler'
    //   21: invokestatic getNewHandlerThread : (Ljava/lang/String;)Landroid/os/HandlerThread;
    //   24: invokevirtual getLooper : ()Landroid/os/Looper;
    //   27: invokespecial <init> : (Landroid/os/Looper;)V
    //   30: putstatic com/tt/miniapp/mmkv/MMKVUsageMonitorHandler.instance : Lcom/tt/miniapp/mmkv/MMKVUsageMonitorHandler;
    //   33: ldc com/tt/miniapp/mmkv/MMKVUsageMonitorHandler
    //   35: monitorexit
    //   36: goto -> 45
    //   39: astore_0
    //   40: ldc com/tt/miniapp/mmkv/MMKVUsageMonitorHandler
    //   42: monitorexit
    //   43: aload_0
    //   44: athrow
    //   45: getstatic com/tt/miniapp/mmkv/MMKVUsageMonitorHandler.instance : Lcom/tt/miniapp/mmkv/MMKVUsageMonitorHandler;
    //   48: areturn
    // Exception table:
    //   from	to	target	type
    //   9	33	39	finally
    //   33	36	39	finally
    //   40	43	39	finally
  }
  
  public void handleMessage(Message paramMessage) {
    AppBrandMonitor.statusRate("mp_kv_storage_name", paramMessage.what, (JSONObject)paramMessage.obj);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\mmkv\MMKVUsageMonitorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */