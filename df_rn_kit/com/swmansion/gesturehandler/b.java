package com.swmansion.gesturehandler;

import android.view.MotionEvent;
import android.view.View;
import java.util.Arrays;

public class b<T extends b> {
  private static int a = 11;
  
  private static MotionEvent.PointerProperties[] b;
  
  private static MotionEvent.PointerCoords[] u;
  
  final int[] c = new int[a];
  
  int d;
  
  public int e;
  
  public View f;
  
  public int g;
  
  public float h;
  
  public float i;
  
  public boolean j;
  
  public boolean k = true;
  
  public float l;
  
  public float m;
  
  public int n;
  
  d o;
  
  public i<T> p;
  
  public c q;
  
  int r;
  
  boolean s;
  
  boolean t;
  
  private float[] v;
  
  private float w;
  
  private float x;
  
  private boolean y;
  
  private static boolean a(float paramFloat) {
    return !Float.isNaN(paramFloat);
  }
  
  private void b(int paramInt) {
    int j = this.g;
    if (j == paramInt)
      return; 
    this.g = paramInt;
    d d1 = this.o;
    d1.d++;
    if (d.a(paramInt)) {
      for (int k = 0; k < d1.b; k++) {
        b b1 = d1.a[k];
        if (d.a(b1, this))
          if (paramInt == 5) {
            b1.c();
            b1.t = false;
          } else {
            d1.a(b1);
          }  
      } 
      d1.b();
    } 
    if (paramInt == 4) {
      d1.a(this);
    } else if ((j != 4 && j != 5) || this.s) {
      a(paramInt, j);
    } 
    d1.d--;
    if (d1.c || d1.d != 0) {
      d1.e = true;
    } else {
      d1.a();
    } 
    b(paramInt, j);
  }
  
  public final T a(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
    if (this.v == null)
      this.v = new float[6]; 
    float[] arrayOfFloat = this.v;
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    arrayOfFloat[2] = paramFloat3;
    arrayOfFloat[3] = paramFloat4;
    arrayOfFloat[4] = paramFloat5;
    arrayOfFloat[5] = paramFloat6;
    if (!a(paramFloat5) || !a(paramFloat1) || !a(paramFloat3)) {
      if (!a(paramFloat5) || a(paramFloat1) || a(paramFloat3)) {
        if (!a(paramFloat6) || !a(paramFloat4) || !a(paramFloat2)) {
          if (a(paramFloat6) && !a(paramFloat4)) {
            if (a(paramFloat2))
              return (T)this; 
            throw new IllegalArgumentException("When height is set one of top or bottom pads need to be defined");
          } 
          return (T)this;
        } 
        throw new IllegalArgumentException("Cannot have all of top, bottom and height defined");
      } 
      throw new IllegalArgumentException("When width is set one of left or right pads need to be defined");
    } 
    throw new IllegalArgumentException("Cannot have all of left, right and width defined");
  }
  
  public final T a(boolean paramBoolean) {
    this.y = paramBoolean;
    return (T)this;
  }
  
  protected void a() {}
  
  public final void a(int paramInt) {
    int[] arrayOfInt = this.c;
    if (arrayOfInt[paramInt] == -1) {
      int j = 0;
      while (j < this.d) {
        int k = 0;
        while (true) {
          int[] arrayOfInt1 = this.c;
          if (k < arrayOfInt1.length && arrayOfInt1[k] != j) {
            k++;
            continue;
          } 
          break;
        } 
        if (k != this.c.length)
          j++; 
      } 
      arrayOfInt[paramInt] = j;
      this.d++;
    } 
  }
  
  final void a(int paramInt1, int paramInt2) {
    i<T> i1 = this.p;
    if (i1 != null)
      i1.a((T)this, paramInt1, paramInt2); 
  }
  
  protected void a(MotionEvent paramMotionEvent) {
    b(1);
  }
  
  public final boolean a(View paramView, float paramFloat1, float paramFloat2) {
    float f2;
    float f6;
    float f7;
    float f3 = paramView.getWidth();
    float f4 = paramView.getHeight();
    float[] arrayOfFloat = this.v;
    float f5 = 0.0F;
    float f1 = 0.0F;
    if (arrayOfFloat != null) {
      f6 = arrayOfFloat[0];
      f5 = arrayOfFloat[1];
      f7 = arrayOfFloat[2];
      float f8 = arrayOfFloat[3];
      if (a(f6)) {
        f2 = 0.0F - f6;
      } else {
        f2 = 0.0F;
      } 
      if (a(f5))
        f1 = 0.0F - f8; 
      f5 = f3;
      if (a(f7))
        f5 = f3 + f7; 
      f3 = f4;
      if (a(f8))
        f3 = f4 + f8; 
      arrayOfFloat = this.v;
      float f10 = arrayOfFloat[4];
      float f9 = arrayOfFloat[5];
      f4 = f5;
      f8 = f2;
      if (a(f10))
        if (!a(f6)) {
          f8 = f7 - f10;
          f4 = f5;
        } else {
          f4 = f5;
          f8 = f2;
          if (!a(f7)) {
            f4 = f6 + f10;
            f8 = f2;
          } 
        }  
      f7 = f4;
      f5 = f1;
      f6 = f8;
      f2 = f3;
      if (a(f9))
        if (!a(f1)) {
          f5 = f3 - f9;
          f7 = f4;
          f6 = f8;
          f2 = f3;
        } else {
          f7 = f4;
          f5 = f1;
          f6 = f8;
          f2 = f3;
          if (!a(f3)) {
            f2 = f1 + f9;
            f7 = f4;
            f5 = f1;
            f6 = f8;
          } 
        }  
    } else {
      f6 = 0.0F;
      f2 = f4;
      f7 = f3;
    } 
    return (paramFloat1 >= f6 && paramFloat1 <= f7 && paramFloat2 >= f5 && paramFloat2 <= f2);
  }
  
  public final boolean a(b paramb) {
    int j = 0;
    while (true) {
      int[] arrayOfInt = this.c;
      if (j < arrayOfInt.length) {
        if (arrayOfInt[j] != -1 && paramb.c[j] != -1)
          return true; 
        j++;
        continue;
      } 
      return false;
    } 
  }
  
  public final T b(boolean paramBoolean) {
    if (this.f != null)
      c(); 
    this.k = paramBoolean;
    return (T)this;
  }
  
  protected void b() {}
  
  protected void b(int paramInt1, int paramInt2) {}
  
  public final void b(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield k : Z
    //   4: ifeq -> 763
    //   7: aload_0
    //   8: getfield g : I
    //   11: istore #4
    //   13: iload #4
    //   15: iconst_3
    //   16: if_icmpeq -> 763
    //   19: iload #4
    //   21: iconst_1
    //   22: if_icmpeq -> 763
    //   25: iconst_5
    //   26: istore #7
    //   28: iload #4
    //   30: iconst_5
    //   31: if_icmpeq -> 763
    //   34: aload_0
    //   35: getfield d : I
    //   38: ifgt -> 42
    //   41: return
    //   42: aload_1
    //   43: invokevirtual getPointerCount : ()I
    //   46: istore #4
    //   48: aload_0
    //   49: getfield d : I
    //   52: istore #5
    //   54: iconst_0
    //   55: istore #8
    //   57: iload #4
    //   59: iload #5
    //   61: if_icmpeq -> 70
    //   64: iconst_1
    //   65: istore #4
    //   67: goto -> 121
    //   70: iconst_0
    //   71: istore #4
    //   73: aload_0
    //   74: getfield c : [I
    //   77: astore #12
    //   79: iload #4
    //   81: aload #12
    //   83: arraylength
    //   84: if_icmpge -> 118
    //   87: aload #12
    //   89: iload #4
    //   91: iaload
    //   92: iconst_m1
    //   93: if_icmpeq -> 109
    //   96: aload #12
    //   98: iload #4
    //   100: iaload
    //   101: iload #4
    //   103: if_icmpeq -> 109
    //   106: goto -> 64
    //   109: iload #4
    //   111: iconst_1
    //   112: iadd
    //   113: istore #4
    //   115: goto -> 73
    //   118: iconst_0
    //   119: istore #4
    //   121: iload #4
    //   123: ifne -> 132
    //   126: aload_1
    //   127: astore #12
    //   129: goto -> 607
    //   132: aload_1
    //   133: invokevirtual getActionMasked : ()I
    //   136: istore #5
    //   138: iload #5
    //   140: ifeq -> 233
    //   143: iload #5
    //   145: iconst_5
    //   146: if_icmpne -> 152
    //   149: goto -> 233
    //   152: bipush #6
    //   154: istore #4
    //   156: iload #5
    //   158: iconst_1
    //   159: if_icmpeq -> 182
    //   162: iload #5
    //   164: bipush #6
    //   166: if_icmpne -> 172
    //   169: goto -> 182
    //   172: iload #5
    //   174: istore #4
    //   176: iconst_m1
    //   177: istore #5
    //   179: goto -> 291
    //   182: aload_1
    //   183: invokevirtual getActionIndex : ()I
    //   186: istore #6
    //   188: aload_1
    //   189: iload #6
    //   191: invokevirtual getPointerId : (I)I
    //   194: istore #7
    //   196: iload #6
    //   198: istore #5
    //   200: aload_0
    //   201: getfield c : [I
    //   204: iload #7
    //   206: iaload
    //   207: iconst_m1
    //   208: if_icmpeq -> 288
    //   211: iload #6
    //   213: istore #5
    //   215: aload_0
    //   216: getfield d : I
    //   219: iconst_1
    //   220: if_icmpne -> 291
    //   223: iconst_1
    //   224: istore #4
    //   226: iload #6
    //   228: istore #5
    //   230: goto -> 291
    //   233: aload_1
    //   234: invokevirtual getActionIndex : ()I
    //   237: istore #6
    //   239: aload_1
    //   240: iload #6
    //   242: invokevirtual getPointerId : (I)I
    //   245: istore #4
    //   247: iload #6
    //   249: istore #5
    //   251: aload_0
    //   252: getfield c : [I
    //   255: iload #4
    //   257: iaload
    //   258: iconst_m1
    //   259: if_icmpeq -> 288
    //   262: iload #6
    //   264: istore #5
    //   266: iload #7
    //   268: istore #4
    //   270: aload_0
    //   271: getfield d : I
    //   274: iconst_1
    //   275: if_icmpne -> 291
    //   278: iconst_0
    //   279: istore #4
    //   281: iload #6
    //   283: istore #5
    //   285: goto -> 291
    //   288: iconst_2
    //   289: istore #4
    //   291: aload_0
    //   292: getfield d : I
    //   295: istore #7
    //   297: iload #7
    //   299: istore #6
    //   301: getstatic com/swmansion/gesturehandler/b.b : [Landroid/view/MotionEvent$PointerProperties;
    //   304: ifnonnull -> 332
    //   307: getstatic com/swmansion/gesturehandler/b.a : I
    //   310: istore #6
    //   312: iload #6
    //   314: anewarray android/view/MotionEvent$PointerProperties
    //   317: putstatic com/swmansion/gesturehandler/b.b : [Landroid/view/MotionEvent$PointerProperties;
    //   320: iload #6
    //   322: anewarray android/view/MotionEvent$PointerCoords
    //   325: putstatic com/swmansion/gesturehandler/b.u : [Landroid/view/MotionEvent$PointerCoords;
    //   328: iload #7
    //   330: istore #6
    //   332: iload #6
    //   334: ifle -> 390
    //   337: getstatic com/swmansion/gesturehandler/b.b : [Landroid/view/MotionEvent$PointerProperties;
    //   340: astore #12
    //   342: iload #6
    //   344: iconst_1
    //   345: isub
    //   346: istore #7
    //   348: aload #12
    //   350: iload #7
    //   352: aaload
    //   353: ifnonnull -> 390
    //   356: aload #12
    //   358: iload #7
    //   360: new android/view/MotionEvent$PointerProperties
    //   363: dup
    //   364: invokespecial <init> : ()V
    //   367: aastore
    //   368: getstatic com/swmansion/gesturehandler/b.u : [Landroid/view/MotionEvent$PointerCoords;
    //   371: iload #7
    //   373: new android/view/MotionEvent$PointerCoords
    //   376: dup
    //   377: invokespecial <init> : ()V
    //   380: aastore
    //   381: iload #6
    //   383: iconst_1
    //   384: isub
    //   385: istore #6
    //   387: goto -> 332
    //   390: aload_1
    //   391: invokevirtual getX : ()F
    //   394: fstore_2
    //   395: aload_1
    //   396: invokevirtual getY : ()F
    //   399: fstore_3
    //   400: aload_1
    //   401: aload_1
    //   402: invokevirtual getRawX : ()F
    //   405: aload_1
    //   406: invokevirtual getRawY : ()F
    //   409: invokevirtual setLocation : (FF)V
    //   412: aload_1
    //   413: invokevirtual getPointerCount : ()I
    //   416: istore #10
    //   418: iconst_0
    //   419: istore #7
    //   421: iload #8
    //   423: iload #10
    //   425: if_icmpge -> 539
    //   428: aload_1
    //   429: iload #8
    //   431: invokevirtual getPointerId : (I)I
    //   434: istore #11
    //   436: iload #4
    //   438: istore #6
    //   440: iload #7
    //   442: istore #9
    //   444: aload_0
    //   445: getfield c : [I
    //   448: iload #11
    //   450: iaload
    //   451: iconst_m1
    //   452: if_icmpeq -> 522
    //   455: aload_1
    //   456: iload #8
    //   458: getstatic com/swmansion/gesturehandler/b.b : [Landroid/view/MotionEvent$PointerProperties;
    //   461: iload #7
    //   463: aaload
    //   464: invokevirtual getPointerProperties : (ILandroid/view/MotionEvent$PointerProperties;)V
    //   467: getstatic com/swmansion/gesturehandler/b.b : [Landroid/view/MotionEvent$PointerProperties;
    //   470: iload #7
    //   472: aaload
    //   473: aload_0
    //   474: getfield c : [I
    //   477: iload #11
    //   479: iaload
    //   480: putfield id : I
    //   483: aload_1
    //   484: iload #8
    //   486: getstatic com/swmansion/gesturehandler/b.u : [Landroid/view/MotionEvent$PointerCoords;
    //   489: iload #7
    //   491: aaload
    //   492: invokevirtual getPointerCoords : (ILandroid/view/MotionEvent$PointerCoords;)V
    //   495: iload #4
    //   497: istore #6
    //   499: iload #8
    //   501: iload #5
    //   503: if_icmpne -> 516
    //   506: iload #4
    //   508: iload #7
    //   510: bipush #8
    //   512: ishl
    //   513: ior
    //   514: istore #6
    //   516: iload #7
    //   518: iconst_1
    //   519: iadd
    //   520: istore #9
    //   522: iload #8
    //   524: iconst_1
    //   525: iadd
    //   526: istore #8
    //   528: iload #6
    //   530: istore #4
    //   532: iload #9
    //   534: istore #7
    //   536: goto -> 421
    //   539: aload_1
    //   540: invokevirtual getDownTime : ()J
    //   543: aload_1
    //   544: invokevirtual getEventTime : ()J
    //   547: iload #4
    //   549: iload #7
    //   551: getstatic com/swmansion/gesturehandler/b.b : [Landroid/view/MotionEvent$PointerProperties;
    //   554: getstatic com/swmansion/gesturehandler/b.u : [Landroid/view/MotionEvent$PointerCoords;
    //   557: aload_1
    //   558: invokevirtual getMetaState : ()I
    //   561: aload_1
    //   562: invokevirtual getButtonState : ()I
    //   565: aload_1
    //   566: invokevirtual getXPrecision : ()F
    //   569: aload_1
    //   570: invokevirtual getYPrecision : ()F
    //   573: aload_1
    //   574: invokevirtual getDeviceId : ()I
    //   577: aload_1
    //   578: invokevirtual getEdgeFlags : ()I
    //   581: aload_1
    //   582: invokevirtual getSource : ()I
    //   585: aload_1
    //   586: invokevirtual getFlags : ()I
    //   589: invokestatic obtain : (JJII[Landroid/view/MotionEvent$PointerProperties;[Landroid/view/MotionEvent$PointerCoords;IIFFIIII)Landroid/view/MotionEvent;
    //   592: astore #12
    //   594: aload_1
    //   595: fload_2
    //   596: fload_3
    //   597: invokevirtual setLocation : (FF)V
    //   600: aload #12
    //   602: fload_2
    //   603: fload_3
    //   604: invokevirtual setLocation : (FF)V
    //   607: aload_0
    //   608: aload #12
    //   610: invokevirtual getX : ()F
    //   613: putfield h : F
    //   616: aload_0
    //   617: aload #12
    //   619: invokevirtual getY : ()F
    //   622: putfield i : F
    //   625: aload_0
    //   626: aload #12
    //   628: invokevirtual getPointerCount : ()I
    //   631: putfield n : I
    //   634: aload_0
    //   635: aload_0
    //   636: aload_0
    //   637: getfield f : Landroid/view/View;
    //   640: aload_0
    //   641: getfield h : F
    //   644: aload_0
    //   645: getfield i : F
    //   648: invokevirtual a : (Landroid/view/View;FF)Z
    //   651: putfield j : Z
    //   654: aload_0
    //   655: getfield y : Z
    //   658: ifeq -> 696
    //   661: aload_0
    //   662: getfield j : Z
    //   665: ifne -> 696
    //   668: aload_0
    //   669: getfield g : I
    //   672: istore #4
    //   674: iload #4
    //   676: iconst_4
    //   677: if_icmpne -> 685
    //   680: aload_0
    //   681: invokevirtual c : ()V
    //   684: return
    //   685: iload #4
    //   687: iconst_2
    //   688: if_icmpne -> 695
    //   691: aload_0
    //   692: invokevirtual d : ()V
    //   695: return
    //   696: aload_0
    //   697: aload #12
    //   699: iconst_1
    //   700: invokestatic a : (Landroid/view/MotionEvent;Z)F
    //   703: putfield l : F
    //   706: aload_0
    //   707: aload #12
    //   709: iconst_1
    //   710: invokestatic b : (Landroid/view/MotionEvent;Z)F
    //   713: putfield m : F
    //   716: aload_0
    //   717: aload #12
    //   719: invokevirtual getRawX : ()F
    //   722: aload #12
    //   724: invokevirtual getX : ()F
    //   727: fsub
    //   728: putfield w : F
    //   731: aload_0
    //   732: aload #12
    //   734: invokevirtual getRawY : ()F
    //   737: aload #12
    //   739: invokevirtual getY : ()F
    //   742: fsub
    //   743: putfield x : F
    //   746: aload_0
    //   747: aload #12
    //   749: invokevirtual a : (Landroid/view/MotionEvent;)V
    //   752: aload #12
    //   754: aload_1
    //   755: if_acmpeq -> 763
    //   758: aload #12
    //   760: invokevirtual recycle : ()V
    //   763: return
  }
  
  public boolean b(b paramb) {
    if (paramb == this || this.q != null);
    return false;
  }
  
  public final void c() {
    int j = this.g;
    if (j == 4 || j == 0 || j == 2) {
      a();
      b(3);
    } 
  }
  
  public final boolean c(b paramb) {
    if (paramb != this) {
      c c1 = this.q;
      if (c1 != null)
        return c1.a(this, paramb); 
    } 
    return false;
  }
  
  public final void d() {
    int j = this.g;
    if (j == 4 || j == 0 || j == 2)
      b(1); 
  }
  
  public boolean d(b paramb) {
    if (paramb == this)
      return true; 
    c c1 = this.q;
    return (c1 != null) ? c1.b(this, paramb) : false;
  }
  
  public final void e() {
    int j = this.g;
    if (j == 0 || j == 2)
      b(4); 
  }
  
  public boolean e(b paramb) {
    if (paramb == this)
      return false; 
    if (this.q != null);
    return false;
  }
  
  public final void f() {
    if (this.g == 0)
      b(2); 
  }
  
  public final void g() {
    int j = this.g;
    if (j == 2 || j == 4)
      b(5); 
  }
  
  public final void h() {
    this.f = null;
    this.o = null;
    Arrays.fill(this.c, -1);
    this.d = 0;
    b();
  }
  
  public final float i() {
    return this.l - this.w;
  }
  
  public final float j() {
    return this.m - this.x;
  }
  
  public String toString() {
    String str;
    View view = this.f;
    if (view == null) {
      view = null;
    } else {
      str = view.getClass().getSimpleName();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getClass().getSimpleName());
    stringBuilder.append("@[");
    stringBuilder.append(this.e);
    stringBuilder.append("]:");
    stringBuilder.append(str);
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */