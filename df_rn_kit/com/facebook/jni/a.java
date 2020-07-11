package com.facebook.jni;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.atomic.AtomicReference;

public final class a {
  public static b a;
  
  public static c b = new c();
  
  public static ReferenceQueue c = new ReferenceQueue();
  
  private static Thread d;
  
  static {
    a = new b();
    Thread thread = new Thread("HybridData DestructorThread") {
        public final void run() {
          while (true) {
            try {
              a.a a = (a.a)a.c.remove();
              a.a();
              if (a.b == null)
                for (a.a a1 = a.b.a.getAndSet(null); a1 != null; a1 = a2) {
                  a.a a2 = a1.a;
                  a.b b = a.a;
                  a1.a = b.a.a;
                  b.a.a = a1;
                  a1.a.b = a1;
                  a1.b = b.a;
                }  
              a.a.b = a.b;
              a.b.a = a.a;
            } catch (InterruptedException interruptedException) {}
          } 
        }
      };
    d = thread;
    thread.start();
  }
  
  public static abstract class a extends PhantomReference<Object> {
    public a a;
    
    public a b;
    
    private a() {
      super(null, a.c);
    }
    
    a(Object param1Object) {
      super(param1Object, a.c);
      a.b.a(this);
    }
    
    abstract void a();
  }
  
  static final class b {
    a.a a = new a.d();
    
    public b() {
      this.a.a = new a.d();
      this.a.a.b = this.a;
    }
  }
  
  static final class c {
    AtomicReference<a.a> a = new AtomicReference<a.a>();
    
    private c() {}
    
    public final void a(a.a param1a) {
      a.a a1;
      do {
        a1 = this.a.get();
        param1a.a = a1;
      } while (!this.a.compareAndSet(a1, param1a));
    }
  }
  
  static final class d extends a {
    private d() {}
    
    final void a() {
      throw new IllegalStateException("Cannot destroy Terminus Destructor.");
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\jni\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */