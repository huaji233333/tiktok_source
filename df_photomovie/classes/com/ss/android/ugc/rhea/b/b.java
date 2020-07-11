package com.ss.android.ugc.rhea.b;

import android.content.Context;
import com.ss.android.ugc.rhea.a;
import com.ss.android.ugc.rhea.a.a;
import com.ss.android.ugc.rhea.c.a;
import com.ss.android.ugc.rhea.d.a;
import d.f.b.l;
import java.util.List;
import java.util.Stack;

public final class b extends a {
  public static volatile boolean a;
  
  public static final b b = new b();
  
  public static void a(Context paramContext, boolean paramBoolean) {
    l.b(paramContext, "context");
    if (paramBoolean)
      a.b.cancel(a.a.hashCode()); 
    a = false;
    synchronized (a.a) {
      if (!a.a.isEmpty()) {
        List list = a.a;
        if ("component".equals(com.ss.android.ugc.rhea.b.a())) {
          if (list.size() > 100)
            a.a.clear(); 
          a.a.addAll(list);
        } else {
          a.a.execute((Runnable)new a.a(list, null));
        } 
        a.a.clear();
      } 
      Stack stack = a.b.get();
      null = stack;
      if (stack == null) {
        null = new Stack();
        a.b.set(null);
      } 
      null.clear();
      if (!paramBoolean)
        a.a(); 
      return;
    } 
  }
  
  public static boolean a() {
    return a;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_photomovie\classes.jar!\com\ss\androi\\ugc\rhea\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */