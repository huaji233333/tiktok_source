package com.swmansion.gesturehandler;

import android.os.Handler;
import android.view.MotionEvent;

public class o extends b<o> {
  private static float z = 1.4E-45F;
  
  private int A;
  
  private float B;
  
  private float C;
  
  private float D;
  
  private float E;
  
  private float F;
  
  private float G;
  
  private Handler H;
  
  private int I;
  
  private final Runnable J;
  
  public float a;
  
  public float b;
  
  public float u;
  
  public long v;
  
  public long w;
  
  public int x;
  
  public int y;
  
  public o() {
    float f = z;
    this.a = f;
    this.b = f;
    this.u = f;
    this.v = 500L;
    this.w = 500L;
    this.x = 1;
    this.y = 1;
    this.A = 1;
    this.J = new Runnable(this) {
        public final void run() {
          this.a.d();
        }
      };
    a(true);
  }
  
  private void k() {
    Handler handler = this.H;
    if (handler == null) {
      this.H = new Handler();
    } else {
      handler.removeCallbacksAndMessages(null);
    } 
    this.H.postDelayed(this.J, this.v);
  }
  
  protected final void a() {
    Handler handler = this.H;
    if (handler != null)
      handler.removeCallbacksAndMessages(null); 
  }
  
  protected final void a(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield g : I
    //   4: istore #6
    //   6: aload_1
    //   7: invokevirtual getActionMasked : ()I
    //   10: istore #7
    //   12: iload #6
    //   14: ifne -> 43
    //   17: aload_0
    //   18: fconst_0
    //   19: putfield D : F
    //   22: aload_0
    //   23: fconst_0
    //   24: putfield E : F
    //   27: aload_0
    //   28: aload_1
    //   29: invokevirtual getRawX : ()F
    //   32: putfield B : F
    //   35: aload_0
    //   36: aload_1
    //   37: invokevirtual getRawY : ()F
    //   40: putfield C : F
    //   43: iload #7
    //   45: bipush #6
    //   47: if_icmpeq -> 80
    //   50: iload #7
    //   52: iconst_5
    //   53: if_icmpne -> 59
    //   56: goto -> 80
    //   59: aload_0
    //   60: aload_1
    //   61: iconst_1
    //   62: invokestatic a : (Landroid/view/MotionEvent;Z)F
    //   65: putfield F : F
    //   68: aload_0
    //   69: aload_1
    //   70: iconst_1
    //   71: invokestatic b : (Landroid/view/MotionEvent;Z)F
    //   74: putfield G : F
    //   77: goto -> 150
    //   80: aload_0
    //   81: aload_0
    //   82: getfield D : F
    //   85: aload_0
    //   86: getfield F : F
    //   89: aload_0
    //   90: getfield B : F
    //   93: fsub
    //   94: fadd
    //   95: putfield D : F
    //   98: aload_0
    //   99: aload_0
    //   100: getfield E : F
    //   103: aload_0
    //   104: getfield G : F
    //   107: aload_0
    //   108: getfield C : F
    //   111: fsub
    //   112: fadd
    //   113: putfield E : F
    //   116: aload_0
    //   117: aload_1
    //   118: iconst_1
    //   119: invokestatic a : (Landroid/view/MotionEvent;Z)F
    //   122: putfield F : F
    //   125: aload_0
    //   126: aload_1
    //   127: iconst_1
    //   128: invokestatic b : (Landroid/view/MotionEvent;Z)F
    //   131: putfield G : F
    //   134: aload_0
    //   135: aload_0
    //   136: getfield F : F
    //   139: putfield B : F
    //   142: aload_0
    //   143: aload_0
    //   144: getfield G : F
    //   147: putfield C : F
    //   150: aload_0
    //   151: getfield A : I
    //   154: aload_1
    //   155: invokevirtual getPointerCount : ()I
    //   158: if_icmpge -> 169
    //   161: aload_0
    //   162: aload_1
    //   163: invokevirtual getPointerCount : ()I
    //   166: putfield A : I
    //   169: aload_0
    //   170: getfield F : F
    //   173: aload_0
    //   174: getfield B : F
    //   177: fsub
    //   178: aload_0
    //   179: getfield D : F
    //   182: fadd
    //   183: fstore_2
    //   184: aload_0
    //   185: getfield a : F
    //   188: getstatic com/swmansion/gesturehandler/o.z : F
    //   191: fcmpl
    //   192: ifeq -> 213
    //   195: fload_2
    //   196: invokestatic abs : (F)F
    //   199: aload_0
    //   200: getfield a : F
    //   203: fcmpl
    //   204: ifle -> 213
    //   207: iconst_1
    //   208: istore #5
    //   210: goto -> 288
    //   213: aload_0
    //   214: getfield G : F
    //   217: aload_0
    //   218: getfield C : F
    //   221: fsub
    //   222: aload_0
    //   223: getfield E : F
    //   226: fadd
    //   227: fstore_3
    //   228: aload_0
    //   229: getfield b : F
    //   232: getstatic com/swmansion/gesturehandler/o.z : F
    //   235: fcmpl
    //   236: ifeq -> 254
    //   239: fload_3
    //   240: invokestatic abs : (F)F
    //   243: aload_0
    //   244: getfield b : F
    //   247: fcmpl
    //   248: ifle -> 254
    //   251: goto -> 207
    //   254: aload_0
    //   255: getfield u : F
    //   258: fstore #4
    //   260: fload #4
    //   262: getstatic com/swmansion/gesturehandler/o.z : F
    //   265: fcmpl
    //   266: ifeq -> 285
    //   269: fload_3
    //   270: fload_3
    //   271: fmul
    //   272: fload_2
    //   273: fload_2
    //   274: fmul
    //   275: fadd
    //   276: fload #4
    //   278: fcmpl
    //   279: ifle -> 285
    //   282: goto -> 207
    //   285: iconst_0
    //   286: istore #5
    //   288: iload #5
    //   290: ifeq -> 298
    //   293: aload_0
    //   294: invokevirtual d : ()V
    //   297: return
    //   298: iload #6
    //   300: ifne -> 317
    //   303: iload #7
    //   305: ifne -> 312
    //   308: aload_0
    //   309: invokevirtual f : ()V
    //   312: aload_0
    //   313: invokespecial k : ()V
    //   316: return
    //   317: iload #6
    //   319: iconst_2
    //   320: if_icmpne -> 426
    //   323: iload #7
    //   325: iconst_1
    //   326: if_icmpne -> 417
    //   329: aload_0
    //   330: getfield H : Landroid/os/Handler;
    //   333: astore_1
    //   334: aload_1
    //   335: ifnonnull -> 352
    //   338: aload_0
    //   339: new android/os/Handler
    //   342: dup
    //   343: invokespecial <init> : ()V
    //   346: putfield H : Landroid/os/Handler;
    //   349: goto -> 357
    //   352: aload_1
    //   353: aconst_null
    //   354: invokevirtual removeCallbacksAndMessages : (Ljava/lang/Object;)V
    //   357: aload_0
    //   358: getfield I : I
    //   361: iconst_1
    //   362: iadd
    //   363: istore #5
    //   365: aload_0
    //   366: iload #5
    //   368: putfield I : I
    //   371: iload #5
    //   373: aload_0
    //   374: getfield x : I
    //   377: if_icmpne -> 400
    //   380: aload_0
    //   381: getfield A : I
    //   384: aload_0
    //   385: getfield y : I
    //   388: if_icmplt -> 400
    //   391: aload_0
    //   392: invokevirtual e : ()V
    //   395: aload_0
    //   396: invokevirtual g : ()V
    //   399: return
    //   400: aload_0
    //   401: getfield H : Landroid/os/Handler;
    //   404: aload_0
    //   405: getfield J : Ljava/lang/Runnable;
    //   408: aload_0
    //   409: getfield w : J
    //   412: invokevirtual postDelayed : (Ljava/lang/Runnable;J)Z
    //   415: pop
    //   416: return
    //   417: iload #7
    //   419: ifne -> 426
    //   422: aload_0
    //   423: invokespecial k : ()V
    //   426: return
  }
  
  protected final void b() {
    this.I = 0;
    this.A = 0;
    Handler handler = this.H;
    if (handler != null)
      handler.removeCallbacksAndMessages(null); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\o.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */