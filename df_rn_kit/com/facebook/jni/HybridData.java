package com.facebook.jni;

public class HybridData {
  private Destructor mDestructor = new Destructor(this);
  
  public boolean isValid() {
    return (this.mDestructor.mNativePointer != 0L);
  }
  
  public void resetNative() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mDestructor : Lcom/facebook/jni/HybridData$Destructor;
    //   6: invokevirtual a : ()V
    //   9: aload_0
    //   10: monitorexit
    //   11: return
    //   12: astore_1
    //   13: aload_0
    //   14: monitorexit
    //   15: aload_1
    //   16: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	12	finally
  }
  
  public static class Destructor extends a.a {
    public long mNativePointer;
    
    Destructor(Object param1Object) {
      super(param1Object);
    }
    
    static native void deleteNative(long param1Long);
    
    final void a() {
      deleteNative(this.mNativePointer);
      this.mNativePointer = 0L;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\jni\HybridData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */