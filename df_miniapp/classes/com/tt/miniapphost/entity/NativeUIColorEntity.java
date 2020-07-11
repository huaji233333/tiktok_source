package com.tt.miniapphost.entity;

import java.util.ArrayList;

public class NativeUIColorEntity {
  private static final String TAG = NativeUIColorEntity.class.getName();
  
  private static volatile NativeUIColorEntity instance = null;
  
  private ArrayList<String> colorList = new ArrayList<String>();
  
  private NativeUIColorEntity() {
    this.colorList.add("#FF000000");
    this.colorList.add("#CD000000");
    this.colorList.add("#9A000000");
    this.colorList.add("#68000000");
    this.colorList.add("#34000000");
    this.colorList.add("#0c000000");
    this.colorList.add("#14000000");
    this.colorList.add("#0A000000");
    this.colorList.add("#FFFFFFFF");
    this.colorList.add("#CDFFFFFF");
    this.colorList.add("#9AFFFFFF");
    this.colorList.add("#68FFFFFF");
    this.colorList.add("#34FFFFFF");
  }
  
  public static NativeUIColorEntity getInst() {
    // Byte code:
    //   0: getstatic com/tt/miniapphost/entity/NativeUIColorEntity.instance : Lcom/tt/miniapphost/entity/NativeUIColorEntity;
    //   3: ifnonnull -> 37
    //   6: ldc com/tt/miniapphost/entity/NativeUIColorEntity
    //   8: monitorenter
    //   9: getstatic com/tt/miniapphost/entity/NativeUIColorEntity.instance : Lcom/tt/miniapphost/entity/NativeUIColorEntity;
    //   12: ifnonnull -> 25
    //   15: new com/tt/miniapphost/entity/NativeUIColorEntity
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putstatic com/tt/miniapphost/entity/NativeUIColorEntity.instance : Lcom/tt/miniapphost/entity/NativeUIColorEntity;
    //   25: ldc com/tt/miniapphost/entity/NativeUIColorEntity
    //   27: monitorexit
    //   28: goto -> 37
    //   31: astore_0
    //   32: ldc com/tt/miniapphost/entity/NativeUIColorEntity
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    //   37: getstatic com/tt/miniapphost/entity/NativeUIColorEntity.instance : Lcom/tt/miniapphost/entity/NativeUIColorEntity;
    //   40: areturn
    // Exception table:
    //   from	to	target	type
    //   9	25	31	finally
    //   25	28	31	finally
    //   32	35	31	finally
  }
  
  public boolean isLegalColor(String paramString) {
    return this.colorList.contains(paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\NativeUIColorEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */