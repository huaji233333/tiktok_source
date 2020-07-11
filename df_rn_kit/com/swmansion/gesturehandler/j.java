package com.swmansion.gesturehandler;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class j extends b<j> {
  private static float O = 3.4028235E38F;
  
  private static float P = 1.4E-45F;
  
  private static int Q = 1;
  
  private static int R = 10;
  
  public float A;
  
  public float B;
  
  public float C;
  
  public int D;
  
  public int E;
  
  public float F;
  
  public float G;
  
  public float H;
  
  public float I;
  
  public float J;
  
  public float K;
  
  public float L;
  
  public float M;
  
  public boolean N;
  
  private float S;
  
  private VelocityTracker T;
  
  public float a;
  
  public float b;
  
  public float u;
  
  public float v;
  
  public float w;
  
  public float x;
  
  public float y;
  
  public float z;
  
  public j(Context paramContext) {
    float f1 = P;
    this.S = f1;
    float f2 = O;
    this.a = f2;
    this.b = f1;
    this.u = f1;
    this.v = f2;
    this.w = f2;
    this.x = f1;
    this.y = f1;
    this.z = f2;
    this.A = f2;
    this.B = f2;
    this.C = f2;
    this.D = Q;
    this.E = R;
    int i = ViewConfiguration.get(paramContext).getScaledTouchSlop();
    this.S = (i * i);
  }
  
  private static void a(VelocityTracker paramVelocityTracker, MotionEvent paramMotionEvent) {
    float f1 = paramMotionEvent.getRawX() - paramMotionEvent.getX();
    float f2 = paramMotionEvent.getRawY() - paramMotionEvent.getY();
    paramMotionEvent.offsetLocation(f1, f2);
    paramVelocityTracker.addMovement(paramMotionEvent);
    paramMotionEvent.offsetLocation(-f1, -f2);
  }
  
  public final j a(float paramFloat) {
    this.S = paramFloat * paramFloat;
    return this;
  }
  
  protected final void a(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield g : I
    //   4: istore #5
    //   6: aload_1
    //   7: invokevirtual getActionMasked : ()I
    //   10: istore #7
    //   12: iload #7
    //   14: bipush #6
    //   16: if_icmpeq -> 55
    //   19: iload #7
    //   21: iconst_5
    //   22: if_icmpne -> 28
    //   25: goto -> 55
    //   28: aload_0
    //   29: aload_1
    //   30: aload_0
    //   31: getfield N : Z
    //   34: invokestatic a : (Landroid/view/MotionEvent;Z)F
    //   37: putfield J : F
    //   40: aload_0
    //   41: aload_1
    //   42: aload_0
    //   43: getfield N : Z
    //   46: invokestatic b : (Landroid/view/MotionEvent;Z)F
    //   49: putfield K : F
    //   52: goto -> 131
    //   55: aload_0
    //   56: aload_0
    //   57: getfield H : F
    //   60: aload_0
    //   61: getfield J : F
    //   64: aload_0
    //   65: getfield F : F
    //   68: fsub
    //   69: fadd
    //   70: putfield H : F
    //   73: aload_0
    //   74: aload_0
    //   75: getfield I : F
    //   78: aload_0
    //   79: getfield K : F
    //   82: aload_0
    //   83: getfield G : F
    //   86: fsub
    //   87: fadd
    //   88: putfield I : F
    //   91: aload_0
    //   92: aload_1
    //   93: aload_0
    //   94: getfield N : Z
    //   97: invokestatic a : (Landroid/view/MotionEvent;Z)F
    //   100: putfield J : F
    //   103: aload_0
    //   104: aload_1
    //   105: aload_0
    //   106: getfield N : Z
    //   109: invokestatic b : (Landroid/view/MotionEvent;Z)F
    //   112: putfield K : F
    //   115: aload_0
    //   116: aload_0
    //   117: getfield J : F
    //   120: putfield F : F
    //   123: aload_0
    //   124: aload_0
    //   125: getfield K : F
    //   128: putfield G : F
    //   131: iload #5
    //   133: ifne -> 195
    //   136: aload_1
    //   137: invokevirtual getPointerCount : ()I
    //   140: aload_0
    //   141: getfield D : I
    //   144: if_icmplt -> 195
    //   147: aload_0
    //   148: aload_0
    //   149: getfield J : F
    //   152: putfield F : F
    //   155: aload_0
    //   156: aload_0
    //   157: getfield K : F
    //   160: putfield G : F
    //   163: aload_0
    //   164: fconst_0
    //   165: putfield H : F
    //   168: aload_0
    //   169: fconst_0
    //   170: putfield I : F
    //   173: aload_0
    //   174: invokestatic obtain : ()Landroid/view/VelocityTracker;
    //   177: putfield T : Landroid/view/VelocityTracker;
    //   180: aload_0
    //   181: getfield T : Landroid/view/VelocityTracker;
    //   184: aload_1
    //   185: invokestatic a : (Landroid/view/VelocityTracker;Landroid/view/MotionEvent;)V
    //   188: aload_0
    //   189: invokevirtual f : ()V
    //   192: goto -> 244
    //   195: aload_0
    //   196: getfield T : Landroid/view/VelocityTracker;
    //   199: astore #8
    //   201: aload #8
    //   203: ifnull -> 244
    //   206: aload #8
    //   208: aload_1
    //   209: invokestatic a : (Landroid/view/VelocityTracker;Landroid/view/MotionEvent;)V
    //   212: aload_0
    //   213: getfield T : Landroid/view/VelocityTracker;
    //   216: sipush #1000
    //   219: invokevirtual computeCurrentVelocity : (I)V
    //   222: aload_0
    //   223: aload_0
    //   224: getfield T : Landroid/view/VelocityTracker;
    //   227: invokevirtual getXVelocity : ()F
    //   230: putfield L : F
    //   233: aload_0
    //   234: aload_0
    //   235: getfield T : Landroid/view/VelocityTracker;
    //   238: invokevirtual getYVelocity : ()F
    //   241: putfield M : F
    //   244: iconst_1
    //   245: istore #6
    //   247: iload #7
    //   249: iconst_1
    //   250: if_icmpne -> 269
    //   253: iload #5
    //   255: iconst_4
    //   256: if_icmpne -> 264
    //   259: aload_0
    //   260: invokevirtual g : ()V
    //   263: return
    //   264: aload_0
    //   265: invokevirtual d : ()V
    //   268: return
    //   269: iload #7
    //   271: iconst_5
    //   272: if_icmpne -> 302
    //   275: aload_1
    //   276: invokevirtual getPointerCount : ()I
    //   279: aload_0
    //   280: getfield E : I
    //   283: if_icmple -> 302
    //   286: iload #5
    //   288: iconst_4
    //   289: if_icmpne -> 297
    //   292: aload_0
    //   293: invokevirtual c : ()V
    //   296: return
    //   297: aload_0
    //   298: invokevirtual d : ()V
    //   301: return
    //   302: iload #7
    //   304: bipush #6
    //   306: if_icmpne -> 331
    //   309: iload #5
    //   311: iconst_4
    //   312: if_icmpne -> 331
    //   315: aload_1
    //   316: invokevirtual getPointerCount : ()I
    //   319: aload_0
    //   320: getfield D : I
    //   323: if_icmpge -> 331
    //   326: aload_0
    //   327: invokevirtual d : ()V
    //   330: return
    //   331: iload #5
    //   333: iconst_2
    //   334: if_icmpne -> 832
    //   337: aload_0
    //   338: getfield J : F
    //   341: aload_0
    //   342: getfield F : F
    //   345: fsub
    //   346: aload_0
    //   347: getfield H : F
    //   350: fadd
    //   351: fstore_2
    //   352: aload_0
    //   353: getfield u : F
    //   356: fstore_3
    //   357: fload_3
    //   358: getstatic com/swmansion/gesturehandler/j.P : F
    //   361: fcmpl
    //   362: ifeq -> 377
    //   365: fload_2
    //   366: fload_3
    //   367: fcmpg
    //   368: ifge -> 377
    //   371: iconst_1
    //   372: istore #5
    //   374: goto -> 461
    //   377: aload_0
    //   378: getfield v : F
    //   381: fstore_3
    //   382: fload_3
    //   383: getstatic com/swmansion/gesturehandler/j.O : F
    //   386: fcmpl
    //   387: ifeq -> 399
    //   390: fload_2
    //   391: fload_3
    //   392: fcmpl
    //   393: ifle -> 399
    //   396: goto -> 371
    //   399: aload_0
    //   400: getfield K : F
    //   403: aload_0
    //   404: getfield G : F
    //   407: fsub
    //   408: aload_0
    //   409: getfield I : F
    //   412: fadd
    //   413: fstore_2
    //   414: aload_0
    //   415: getfield y : F
    //   418: fstore_3
    //   419: fload_3
    //   420: getstatic com/swmansion/gesturehandler/j.P : F
    //   423: fcmpl
    //   424: ifeq -> 436
    //   427: fload_2
    //   428: fload_3
    //   429: fcmpg
    //   430: ifge -> 436
    //   433: goto -> 371
    //   436: aload_0
    //   437: getfield z : F
    //   440: fstore_3
    //   441: fload_3
    //   442: getstatic com/swmansion/gesturehandler/j.O : F
    //   445: fcmpl
    //   446: ifeq -> 458
    //   449: fload_2
    //   450: fload_3
    //   451: fcmpl
    //   452: ifle -> 458
    //   455: goto -> 371
    //   458: iconst_0
    //   459: istore #5
    //   461: iload #5
    //   463: ifeq -> 471
    //   466: aload_0
    //   467: invokevirtual d : ()V
    //   470: return
    //   471: aload_0
    //   472: getfield J : F
    //   475: aload_0
    //   476: getfield F : F
    //   479: fsub
    //   480: aload_0
    //   481: getfield H : F
    //   484: fadd
    //   485: fstore_2
    //   486: aload_0
    //   487: getfield a : F
    //   490: fstore_3
    //   491: fload_3
    //   492: getstatic com/swmansion/gesturehandler/j.O : F
    //   495: fcmpl
    //   496: ifeq -> 512
    //   499: fload_2
    //   500: fload_3
    //   501: fcmpg
    //   502: ifge -> 512
    //   505: iload #6
    //   507: istore #5
    //   509: goto -> 807
    //   512: aload_0
    //   513: getfield b : F
    //   516: fstore_3
    //   517: fload_3
    //   518: getstatic com/swmansion/gesturehandler/j.P : F
    //   521: fcmpl
    //   522: ifeq -> 538
    //   525: fload_2
    //   526: fload_3
    //   527: fcmpl
    //   528: ifle -> 538
    //   531: iload #6
    //   533: istore #5
    //   535: goto -> 807
    //   538: aload_0
    //   539: getfield K : F
    //   542: aload_0
    //   543: getfield G : F
    //   546: fsub
    //   547: aload_0
    //   548: getfield I : F
    //   551: fadd
    //   552: fstore_3
    //   553: aload_0
    //   554: getfield w : F
    //   557: fstore #4
    //   559: fload #4
    //   561: getstatic com/swmansion/gesturehandler/j.O : F
    //   564: fcmpl
    //   565: ifeq -> 582
    //   568: fload_3
    //   569: fload #4
    //   571: fcmpg
    //   572: ifge -> 582
    //   575: iload #6
    //   577: istore #5
    //   579: goto -> 807
    //   582: aload_0
    //   583: getfield x : F
    //   586: fstore #4
    //   588: fload #4
    //   590: getstatic com/swmansion/gesturehandler/j.P : F
    //   593: fcmpl
    //   594: ifeq -> 611
    //   597: fload_3
    //   598: fload #4
    //   600: fcmpl
    //   601: ifle -> 611
    //   604: iload #6
    //   606: istore #5
    //   608: goto -> 807
    //   611: aload_0
    //   612: getfield S : F
    //   615: fstore #4
    //   617: fload #4
    //   619: getstatic com/swmansion/gesturehandler/j.O : F
    //   622: fcmpl
    //   623: ifeq -> 646
    //   626: fload_2
    //   627: fload_2
    //   628: fmul
    //   629: fload_3
    //   630: fload_3
    //   631: fmul
    //   632: fadd
    //   633: fload #4
    //   635: fcmpl
    //   636: iflt -> 646
    //   639: iload #6
    //   641: istore #5
    //   643: goto -> 807
    //   646: aload_0
    //   647: getfield L : F
    //   650: fstore_2
    //   651: aload_0
    //   652: getfield A : F
    //   655: fstore_3
    //   656: fload_3
    //   657: getstatic com/swmansion/gesturehandler/j.O : F
    //   660: fcmpl
    //   661: ifeq -> 704
    //   664: fload_3
    //   665: fconst_0
    //   666: fcmpg
    //   667: ifge -> 680
    //   670: iload #6
    //   672: istore #5
    //   674: fload_2
    //   675: fload_3
    //   676: fcmpg
    //   677: ifle -> 807
    //   680: aload_0
    //   681: getfield A : F
    //   684: fstore_3
    //   685: fload_3
    //   686: fconst_0
    //   687: fcmpl
    //   688: iflt -> 704
    //   691: fload_2
    //   692: fload_3
    //   693: fcmpl
    //   694: iflt -> 704
    //   697: iload #6
    //   699: istore #5
    //   701: goto -> 807
    //   704: aload_0
    //   705: getfield M : F
    //   708: fstore_3
    //   709: aload_0
    //   710: getfield B : F
    //   713: fstore #4
    //   715: fload #4
    //   717: getstatic com/swmansion/gesturehandler/j.O : F
    //   720: fcmpl
    //   721: ifeq -> 769
    //   724: fload #4
    //   726: fconst_0
    //   727: fcmpg
    //   728: ifge -> 742
    //   731: iload #6
    //   733: istore #5
    //   735: fload_2
    //   736: fload #4
    //   738: fcmpg
    //   739: ifle -> 807
    //   742: aload_0
    //   743: getfield B : F
    //   746: fstore #4
    //   748: fload #4
    //   750: fconst_0
    //   751: fcmpl
    //   752: iflt -> 769
    //   755: fload_2
    //   756: fload #4
    //   758: fcmpl
    //   759: iflt -> 769
    //   762: iload #6
    //   764: istore #5
    //   766: goto -> 807
    //   769: aload_0
    //   770: getfield C : F
    //   773: fstore #4
    //   775: fload #4
    //   777: getstatic com/swmansion/gesturehandler/j.O : F
    //   780: fcmpl
    //   781: ifeq -> 804
    //   784: fload_2
    //   785: fload_2
    //   786: fmul
    //   787: fload_3
    //   788: fload_3
    //   789: fmul
    //   790: fadd
    //   791: fload #4
    //   793: fcmpl
    //   794: iflt -> 804
    //   797: iload #6
    //   799: istore #5
    //   801: goto -> 807
    //   804: iconst_0
    //   805: istore #5
    //   807: iload #5
    //   809: ifeq -> 832
    //   812: aload_0
    //   813: aload_0
    //   814: getfield J : F
    //   817: putfield F : F
    //   820: aload_0
    //   821: aload_0
    //   822: getfield K : F
    //   825: putfield G : F
    //   828: aload_0
    //   829: invokevirtual e : ()V
    //   832: return
  }
  
  protected final void b() {
    VelocityTracker velocityTracker = this.T;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.T = null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */