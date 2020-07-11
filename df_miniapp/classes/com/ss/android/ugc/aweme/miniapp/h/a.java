package com.ss.android.ugc.aweme.miniapp.h;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.net.Uri;
import android.widget.ImageView;
import com.squareup.b.ag;
import com.squareup.b.e;
import com.squareup.b.o;
import com.squareup.b.r;
import com.squareup.b.s;
import com.squareup.b.v;
import com.squareup.b.y;
import com.squareup.b.z;
import com.tt.b.c;
import com.tt.miniapphost.AppbrandContext;
import java.io.File;
import java.util.ArrayList;

public class a {
  private static volatile v a;
  
  private static o b = new o((Context)AppbrandContext.getInst().getApplicationContext());
  
  private static v a() {
    // Byte code:
    //   0: getstatic com/ss/android/ugc/aweme/miniapp/h/a.a : Lcom/squareup/b/v;
    //   3: ifnonnull -> 91
    //   6: ldc com/ss/android/ugc/aweme/miniapp/h/a
    //   8: monitorenter
    //   9: getstatic com/ss/android/ugc/aweme/miniapp/h/a.a : Lcom/squareup/b/v;
    //   12: ifnonnull -> 79
    //   15: new com/squareup/b/v$a
    //   18: dup
    //   19: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   22: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   25: invokespecial <init> : (Landroid/content/Context;)V
    //   28: astore_0
    //   29: getstatic com/ss/android/ugc/aweme/miniapp/h/a.b : Lcom/squareup/b/o;
    //   32: astore_1
    //   33: aload_1
    //   34: ifnull -> 69
    //   37: aload_0
    //   38: getfield a : Lcom/squareup/b/d;
    //   41: ifnonnull -> 59
    //   44: aload_0
    //   45: aload_1
    //   46: putfield a : Lcom/squareup/b/d;
    //   49: aload_0
    //   50: invokevirtual a : ()Lcom/squareup/b/v;
    //   53: putstatic com/ss/android/ugc/aweme/miniapp/h/a.a : Lcom/squareup/b/v;
    //   56: goto -> 79
    //   59: new java/lang/IllegalStateException
    //   62: dup
    //   63: ldc 'Memory cache already set.'
    //   65: invokespecial <init> : (Ljava/lang/String;)V
    //   68: athrow
    //   69: new java/lang/IllegalArgumentException
    //   72: dup
    //   73: ldc 'Memory cache must not be null.'
    //   75: invokespecial <init> : (Ljava/lang/String;)V
    //   78: athrow
    //   79: ldc com/ss/android/ugc/aweme/miniapp/h/a
    //   81: monitorexit
    //   82: goto -> 91
    //   85: astore_0
    //   86: ldc com/ss/android/ugc/aweme/miniapp/h/a
    //   88: monitorexit
    //   89: aload_0
    //   90: athrow
    //   91: getstatic com/ss/android/ugc/aweme/miniapp/h/a.a : Lcom/squareup/b/v;
    //   94: areturn
    // Exception table:
    //   from	to	target	type
    //   9	33	85	finally
    //   37	56	85	finally
    //   59	69	85	finally
    //   69	79	85	finally
    //   79	82	85	finally
    //   86	89	85	finally
  }
  
  public static void a(Context paramContext, c paramc) {
    z z;
    if (paramc == null)
      return; 
    String str = paramc.m;
    paramContext = null;
    if (str != null) {
      z = a().a(paramc.m);
    } else if (paramc.n != null) {
      v v1 = a();
      File file = paramc.n;
      if (file == null) {
        z = new z(v1, null, 0);
      } else {
        z = z.a(Uri.fromFile(file));
      } 
    } else if (paramc.o != 0) {
      v v1 = a();
      int i = paramc.o;
      if (i != 0) {
        z = new z(v1, null, i);
      } else {
        throw new IllegalArgumentException("Resource ID must not be zero.");
      } 
    } else if (paramc.p != null) {
      z = a().a(paramc.p);
    } 
    if (z != null) {
      if (paramc.k > 0 && paramc.j > 0)
        z.a(paramc.j, paramc.k); 
      if (paramc.e) {
        y.a a1 = z.b;
        if (!a1.a) {
          a1.b = true;
        } else {
          throw new IllegalStateException("Center inside can not be used after calling centerCrop");
        } 
      } else if (paramc.d) {
        z.b();
      } else if (paramc.f) {
        z.a();
      } 
      if (paramc.i != null) {
        Bitmap.Config config = paramc.i;
        z.b.d = config;
      } 
      if (paramc.c != 0)
        z.a(paramc.c); 
      if (paramc.b != 0) {
        int i = paramc.b;
        if (z.d) {
          if (i != 0) {
            if (z.i == null) {
              z.e = i;
            } else {
              throw new IllegalStateException("Placeholder image already set.");
            } 
          } else {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
          } 
        } else {
          throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } 
      } 
      if (paramc.l != 0.0F) {
        b b = new b(paramc.l);
        y.a a1 = z.b;
        b.a();
        if (a1.c == null)
          a1.c = new ArrayList(2); 
        a1.c.add(b);
      } 
      if (paramc.g)
        z.a(r.NO_CACHE, new r[] { r.NO_STORE }); 
      if (paramc.h)
        z.a(s.NO_CACHE, new s[] { s.NO_STORE }); 
      if (paramc.q instanceof ImageView)
        z.a((ImageView)paramc.q, new a(paramc.r)); 
      return;
    } 
    throw new NullPointerException("requestCreator must not be null");
  }
  
  static final class a implements e {
    com.tt.b.a a;
    
    a(com.tt.b.a param1a) {
      this.a = param1a;
    }
    
    public final void a() {
      com.tt.b.a a1 = this.a;
      if (a1 != null)
        a1.onSuccess(); 
    }
    
    public final void b() {
      com.tt.b.a a1 = this.a;
      if (a1 != null)
        a1.onFail(new Exception("load fail")); 
    }
  }
  
  static final class b implements ag {
    private float a;
    
    protected b(float param1Float) {
      this.a = param1Float;
    }
    
    public final Bitmap a(Bitmap param1Bitmap) {
      float f = this.a;
      Bitmap bitmap = Bitmap.createBitmap(param1Bitmap.getWidth(), param1Bitmap.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      Paint paint = new Paint();
      Rect rect = new Rect(0, 0, param1Bitmap.getWidth(), param1Bitmap.getHeight());
      RectF rectF = new RectF(rect);
      paint.setAntiAlias(true);
      canvas.drawARGB(0, 0, 0, 0);
      paint.setColor(-12434878);
      canvas.drawRoundRect(rectF, f, f, paint);
      paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      canvas.drawBitmap(param1Bitmap, rect, rect, paint);
      param1Bitmap.recycle();
      return bitmap;
    }
    
    public final String a() {
      return "bitmapAngle()";
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\h\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */