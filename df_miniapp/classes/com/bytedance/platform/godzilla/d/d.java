package com.bytedance.platform.godzilla.d;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.bytedance.platform.godzilla.e.f;
import java.lang.reflect.Field;
import java.util.HashMap;

public final class d {
  private static volatile Handler a = new Handler(Looper.getMainLooper());
  
  private static HashMap<String, HandlerThread> b = new HashMap<String, HandlerThread>();
  
  public static final class a extends HandlerThread {
    private volatile boolean a;
    
    public a(String param1String, int param1Int1, int param1Int2) {
      super(param1String, param1Int1);
      if (param1Int2 != 0)
        a(param1Int2); 
    }
    
    private boolean a(long param1Long) {
      Field field = com.bytedance.platform.godzilla.e.a.a(HandlerThread.class, "stackSize");
      if (field != null) {
        boolean bool;
        field.setAccessible(true);
        if (field != null) {
          bool = true;
        } else {
          bool = false;
        } 
        if (bool) {
          try {
            if (!field.isAccessible())
              field.setAccessible(true); 
            if (((Long)field.get(this)).longValue() != param1Long) {
              field = (Field)f.a(field, "The field must not be null");
              if (!field.isAccessible())
                field.setAccessible(true); 
              field.set(this, Long.valueOf(param1Long));
              return true;
            } 
          } catch (IllegalAccessException illegalAccessException) {
            return false;
          } 
        } else {
          throw new IllegalArgumentException("The field must not be null");
        } 
      } else {
        return false;
      } 
      return true;
    }
    
    public final void start() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield a : Z
      //   6: istore_1
      //   7: iload_1
      //   8: ifeq -> 14
      //   11: aload_0
      //   12: monitorexit
      //   13: return
      //   14: aload_0
      //   15: iconst_1
      //   16: putfield a : Z
      //   19: aload_0
      //   20: invokespecial start : ()V
      //   23: aload_0
      //   24: monitorexit
      //   25: return
      //   26: astore_2
      //   27: aload_0
      //   28: monitorexit
      //   29: aload_2
      //   30: athrow
      // Exception table:
      //   from	to	target	type
      //   2	7	26	finally
      //   14	23	26	finally
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\platform\godzilla\d\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */