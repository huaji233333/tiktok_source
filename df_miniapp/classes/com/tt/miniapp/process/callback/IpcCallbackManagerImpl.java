package com.tt.miniapp.process.callback;

import android.util.SparseArray;
import com.tt.miniapphost.process.callback.IIpcCallbackManager;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public class IpcCallbackManagerImpl implements IIpcCallbackManager {
  private SparseArray<IpcCallback> mIpcCallbacks = new SparseArray();
  
  public void handleIpcCallBack(int paramInt, CrossProcessDataEntity paramCrossProcessDataEntity) {
    // Byte code:
    //   0: iload_1
    //   1: ifne -> 19
    //   4: ldc 'IpcCallbackManagerImpl'
    //   6: iconst_1
    //   7: anewarray java/lang/Object
    //   10: dup
    //   11: iconst_0
    //   12: ldc 'handleIpcCallBack invalid callbackId'
    //   14: aastore
    //   15: invokestatic outputError : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   18: return
    //   19: aload_0
    //   20: monitorenter
    //   21: aload_0
    //   22: getfield mIpcCallbacks : Landroid/util/SparseArray;
    //   25: iload_1
    //   26: invokevirtual get : (I)Ljava/lang/Object;
    //   29: checkcast com/tt/miniapphost/process/callback/IpcCallback
    //   32: astore_3
    //   33: aload_0
    //   34: monitorexit
    //   35: aload_3
    //   36: ifnull -> 44
    //   39: aload_3
    //   40: aload_2
    //   41: invokevirtual onIpcCallback : (Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;)V
    //   44: return
    //   45: astore_2
    //   46: aload_0
    //   47: monitorexit
    //   48: aload_2
    //   49: athrow
    // Exception table:
    //   from	to	target	type
    //   21	35	45	finally
    //   46	48	45	finally
  }
  
  public void onCallProcessDead(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mIpcCallbacks : Landroid/util/SparseArray;
    //   6: invokevirtual size : ()I
    //   9: iconst_1
    //   10: isub
    //   11: istore_2
    //   12: iload_2
    //   13: iflt -> 62
    //   16: aload_0
    //   17: getfield mIpcCallbacks : Landroid/util/SparseArray;
    //   20: iload_2
    //   21: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   24: checkcast com/tt/miniapphost/process/callback/IpcCallback
    //   27: astore_3
    //   28: aload_3
    //   29: ifnull -> 55
    //   32: aload_3
    //   33: invokevirtual getCallProcessIdentify : ()Ljava/lang/String;
    //   36: aload_1
    //   37: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   40: ifeq -> 55
    //   43: aload_0
    //   44: getfield mIpcCallbacks : Landroid/util/SparseArray;
    //   47: iload_2
    //   48: invokevirtual removeAt : (I)V
    //   51: aload_3
    //   52: invokevirtual onIpcConnectError : ()V
    //   55: iload_2
    //   56: iconst_1
    //   57: isub
    //   58: istore_2
    //   59: goto -> 12
    //   62: aload_0
    //   63: monitorexit
    //   64: return
    //   65: astore_1
    //   66: aload_0
    //   67: monitorexit
    //   68: goto -> 73
    //   71: aload_1
    //   72: athrow
    //   73: goto -> 71
    // Exception table:
    //   from	to	target	type
    //   2	12	65	finally
    //   16	28	65	finally
    //   32	55	65	finally
  }
  
  public void registerIpcCallback(IpcCallback paramIpcCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mIpcCallbacks : Landroid/util/SparseArray;
    //   6: aload_1
    //   7: invokevirtual getCallbackId : ()I
    //   10: aload_1
    //   11: invokevirtual put : (ILjava/lang/Object;)V
    //   14: aload_0
    //   15: monitorexit
    //   16: return
    //   17: astore_1
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_1
    //   21: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	17	finally
  }
  
  public void unregisterIpcCallback(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: ifne -> 23
    //   6: ldc 'IpcCallbackManagerImpl'
    //   8: iconst_1
    //   9: anewarray java/lang/Object
    //   12: dup
    //   13: iconst_0
    //   14: ldc 'unregisterIpcCallback invalid callbackId'
    //   16: aastore
    //   17: invokestatic outputError : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   20: aload_0
    //   21: monitorexit
    //   22: return
    //   23: aload_0
    //   24: getfield mIpcCallbacks : Landroid/util/SparseArray;
    //   27: iload_1
    //   28: invokevirtual delete : (I)V
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: astore_2
    //   35: aload_0
    //   36: monitorexit
    //   37: aload_2
    //   38: athrow
    // Exception table:
    //   from	to	target	type
    //   6	20	34	finally
    //   23	31	34	finally
  }
  
  public void unregisterIpcCallback(IpcCallback paramIpcCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokevirtual getCallbackId : ()I
    //   7: invokevirtual unregisterIpcCallback : (I)V
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
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\callback\IpcCallbackManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */