package com.tt.miniapp.storage;

import com.tt.miniapp.storage.kv.AbsKVStorage;

public class InternalStorageHelper {
  private static AbsKVStorage sInternalStorage;
  
  public static AbsKVStorage getStorage() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/storage/InternalStorageHelper.sInternalStorage : Lcom/tt/miniapp/storage/kv/AbsKVStorage;
    //   3: astore_0
    //   4: aload_0
    //   5: ifnull -> 10
    //   8: aload_0
    //   9: areturn
    //   10: ldc com/tt/miniapp/storage/Storage
    //   12: monitorenter
    //   13: getstatic com/tt/miniapp/storage/InternalStorageHelper.sInternalStorage : Lcom/tt/miniapp/storage/kv/AbsKVStorage;
    //   16: ifnull -> 28
    //   19: getstatic com/tt/miniapp/storage/InternalStorageHelper.sInternalStorage : Lcom/tt/miniapp/storage/kv/AbsKVStorage;
    //   22: astore_0
    //   23: ldc com/tt/miniapp/storage/Storage
    //   25: monitorexit
    //   26: aload_0
    //   27: areturn
    //   28: new com/tt/miniapp/storage/kv/InternalStorage
    //   31: dup
    //   32: invokespecial <init> : ()V
    //   35: putstatic com/tt/miniapp/storage/InternalStorageHelper.sInternalStorage : Lcom/tt/miniapp/storage/kv/AbsKVStorage;
    //   38: ldc com/tt/miniapp/storage/Storage
    //   40: monitorexit
    //   41: getstatic com/tt/miniapp/storage/InternalStorageHelper.sInternalStorage : Lcom/tt/miniapp/storage/kv/AbsKVStorage;
    //   44: areturn
    //   45: astore_0
    //   46: ldc com/tt/miniapp/storage/Storage
    //   48: monitorexit
    //   49: aload_0
    //   50: athrow
    // Exception table:
    //   from	to	target	type
    //   13	26	45	finally
    //   28	41	45	finally
    //   46	49	45	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\InternalStorageHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */