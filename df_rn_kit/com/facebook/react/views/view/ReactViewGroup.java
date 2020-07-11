package com.facebook.react.views.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import com.facebook.i.a.a;
import com.facebook.react.touch.OnInterceptTouchEventListener;
import com.facebook.react.touch.ReactHitSlopView;
import com.facebook.react.touch.ReactInterceptingViewGroup;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.ReactClippingViewGroup;
import com.facebook.react.uimanager.ReactClippingViewGroupHelper;
import com.facebook.react.uimanager.ReactPointerEventsView;
import com.facebook.react.uimanager.ReactZIndexedViewGroup;
import com.facebook.react.uimanager.RootView;
import com.facebook.react.uimanager.RootViewUtil;
import com.facebook.react.uimanager.ViewGroupDrawingOrderHelper;
import java.util.ArrayList;

public class ReactViewGroup extends ViewGroup implements ReactHitSlopView, ReactInterceptingViewGroup, ReactClippingViewGroup, ReactPointerEventsView, ReactZIndexedViewGroup {
  private static final ViewGroup.LayoutParams sDefaultLayoutParam = new ViewGroup.LayoutParams(0, 0);
  
  private static final Rect sHelperRect = new Rect();
  
  private View[] mAllChildren;
  
  private int mAllChildrenCount;
  
  private ChildrenLayoutChangeListener mChildrenLayoutChangeListener;
  
  private Rect mClippingRect;
  
  public ArrayList<Integer> mDeleteMark;
  
  private final ViewGroupDrawingOrderHelper mDrawingOrderHelper = new ViewGroupDrawingOrderHelper(this);
  
  private Rect mHitSlopRect;
  
  private int mLayoutDirection;
  
  public int mMarkedChildCount = -1;
  
  private boolean mNeedsOffscreenAlphaCompositing;
  
  private OnInterceptTouchEventListener mOnInterceptTouchEventListener;
  
  private String mOverflow;
  
  private Path mPath;
  
  private PointerEvents mPointerEvents = PointerEvents.AUTO;
  
  private ReactViewBackgroundDrawable mReactBackgroundDrawable;
  
  private boolean mRemoveClippedSubviews;
  
  public ReactViewGroup(Context paramContext) {
    super(paramContext);
  }
  
  private void addInArray(View paramView, int paramInt) {
    View[] arrayOfView = (View[])a.b(this.mAllChildren);
    int i = this.mAllChildrenCount;
    int j = arrayOfView.length;
    if (paramInt == i) {
      View[] arrayOfView1 = arrayOfView;
      if (j == i) {
        this.mAllChildren = new View[j + 12];
        System.arraycopy(arrayOfView, 0, this.mAllChildren, 0, j);
        arrayOfView1 = this.mAllChildren;
      } 
      paramInt = this.mAllChildrenCount;
      this.mAllChildrenCount = paramInt + 1;
      arrayOfView1[paramInt] = paramView;
      return;
    } 
    if (paramInt < i) {
      if (j == i) {
        this.mAllChildren = new View[j + 12];
        System.arraycopy(arrayOfView, 0, this.mAllChildren, 0, paramInt);
        System.arraycopy(arrayOfView, paramInt, this.mAllChildren, paramInt + 1, i - paramInt);
        arrayOfView = this.mAllChildren;
      } else {
        System.arraycopy(arrayOfView, paramInt, arrayOfView, paramInt + 1, i - paramInt);
      } 
      arrayOfView[paramInt] = paramView;
      this.mAllChildrenCount++;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("index=");
    stringBuilder.append(paramInt);
    stringBuilder.append(" count=");
    stringBuilder.append(i);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  private void dispatchOverflowDraw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mOverflow : Ljava/lang/String;
    //   4: astore #18
    //   6: aload #18
    //   8: ifnull -> 896
    //   11: iconst_m1
    //   12: istore #16
    //   14: aload #18
    //   16: invokevirtual hashCode : ()I
    //   19: istore #17
    //   21: iload #17
    //   23: ldc -1217487446
    //   25: if_icmpeq -> 54
    //   28: iload #17
    //   30: ldc 466743410
    //   32: if_icmpeq -> 38
    //   35: goto -> 67
    //   38: aload #18
    //   40: ldc 'visible'
    //   42: invokevirtual equals : (Ljava/lang/Object;)Z
    //   45: ifeq -> 67
    //   48: iconst_0
    //   49: istore #16
    //   51: goto -> 67
    //   54: aload #18
    //   56: ldc 'hidden'
    //   58: invokevirtual equals : (Ljava/lang/Object;)Z
    //   61: ifeq -> 67
    //   64: iconst_1
    //   65: istore #16
    //   67: iload #16
    //   69: ifeq -> 883
    //   72: iload #16
    //   74: iconst_1
    //   75: if_icmpeq -> 79
    //   78: return
    //   79: aload_0
    //   80: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   83: ifnull -> 896
    //   86: aload_0
    //   87: invokevirtual getWidth : ()I
    //   90: i2f
    //   91: fstore #12
    //   93: aload_0
    //   94: invokevirtual getHeight : ()I
    //   97: i2f
    //   98: fstore #13
    //   100: aload_0
    //   101: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   104: invokevirtual getDirectionAwareBorderInsets : ()Landroid/graphics/RectF;
    //   107: astore #18
    //   109: aload #18
    //   111: getfield top : F
    //   114: fconst_0
    //   115: fcmpl
    //   116: ifgt -> 161
    //   119: aload #18
    //   121: getfield left : F
    //   124: fconst_0
    //   125: fcmpl
    //   126: ifgt -> 161
    //   129: aload #18
    //   131: getfield bottom : F
    //   134: fconst_0
    //   135: fcmpl
    //   136: ifgt -> 161
    //   139: aload #18
    //   141: getfield right : F
    //   144: fconst_0
    //   145: fcmpl
    //   146: ifle -> 152
    //   149: goto -> 161
    //   152: fconst_0
    //   153: fstore #14
    //   155: fconst_0
    //   156: fstore #15
    //   158: goto -> 199
    //   161: aload #18
    //   163: getfield left : F
    //   166: fconst_0
    //   167: fadd
    //   168: fstore #14
    //   170: aload #18
    //   172: getfield top : F
    //   175: fconst_0
    //   176: fadd
    //   177: fstore #15
    //   179: fload #12
    //   181: aload #18
    //   183: getfield right : F
    //   186: fsub
    //   187: fstore #12
    //   189: fload #13
    //   191: aload #18
    //   193: getfield bottom : F
    //   196: fsub
    //   197: fstore #13
    //   199: aload_0
    //   200: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   203: invokevirtual getFullBorderRadius : ()F
    //   206: fstore_2
    //   207: aload_0
    //   208: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   211: fload_2
    //   212: getstatic com/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation.TOP_LEFT : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;
    //   215: invokevirtual getBorderRadiusOrDefaultTo : (FLcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;)F
    //   218: fstore_3
    //   219: aload_0
    //   220: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   223: fload_2
    //   224: getstatic com/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation.TOP_RIGHT : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;
    //   227: invokevirtual getBorderRadiusOrDefaultTo : (FLcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;)F
    //   230: fstore #7
    //   232: aload_0
    //   233: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   236: fload_2
    //   237: getstatic com/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation.BOTTOM_LEFT : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;
    //   240: invokevirtual getBorderRadiusOrDefaultTo : (FLcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;)F
    //   243: fstore #6
    //   245: aload_0
    //   246: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   249: fload_2
    //   250: getstatic com/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation.BOTTOM_RIGHT : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;
    //   253: invokevirtual getBorderRadiusOrDefaultTo : (FLcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;)F
    //   256: fstore #5
    //   258: getstatic android/os/Build$VERSION.SDK_INT : I
    //   261: bipush #17
    //   263: if_icmplt -> 602
    //   266: aload_0
    //   267: getfield mLayoutDirection : I
    //   270: iconst_1
    //   271: if_icmpne -> 280
    //   274: iconst_1
    //   275: istore #16
    //   277: goto -> 283
    //   280: iconst_0
    //   281: istore #16
    //   283: aload_0
    //   284: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   287: getstatic com/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation.TOP_START : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;
    //   290: invokevirtual getBorderRadius : (Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;)F
    //   293: fstore #11
    //   295: aload_0
    //   296: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   299: getstatic com/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation.TOP_END : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;
    //   302: invokevirtual getBorderRadius : (Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;)F
    //   305: fstore_2
    //   306: aload_0
    //   307: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   310: getstatic com/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation.BOTTOM_START : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;
    //   313: invokevirtual getBorderRadius : (Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;)F
    //   316: fstore #8
    //   318: aload_0
    //   319: getfield mReactBackgroundDrawable : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable;
    //   322: astore #19
    //   324: aload #19
    //   326: getstatic com/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation.BOTTOM_END : Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;
    //   329: invokevirtual getBorderRadius : (Lcom/facebook/react/views/view/ReactViewBackgroundDrawable$BorderRadiusLocation;)F
    //   332: fstore #9
    //   334: invokestatic getInstance : ()Lcom/facebook/react/modules/i18nmanager/I18nUtil;
    //   337: astore #19
    //   339: aload #19
    //   341: aload_0
    //   342: invokevirtual getContext : ()Landroid/content/Context;
    //   345: invokevirtual doLeftAndRightSwapInRTL : (Landroid/content/Context;)Z
    //   348: ifeq -> 463
    //   351: fload #11
    //   353: invokestatic a : (F)Z
    //   356: ifeq -> 362
    //   359: goto -> 365
    //   362: fload #11
    //   364: fstore_3
    //   365: fload_2
    //   366: fstore #4
    //   368: fload_2
    //   369: invokestatic a : (F)Z
    //   372: ifeq -> 379
    //   375: fload #7
    //   377: fstore #4
    //   379: fload #8
    //   381: fstore_2
    //   382: fload #8
    //   384: invokestatic a : (F)Z
    //   387: ifeq -> 393
    //   390: fload #6
    //   392: fstore_2
    //   393: fload #9
    //   395: invokestatic a : (F)Z
    //   398: ifeq -> 404
    //   401: goto -> 408
    //   404: fload #9
    //   406: fstore #5
    //   408: iload #16
    //   410: ifeq -> 420
    //   413: fload #4
    //   415: fstore #6
    //   417: goto -> 423
    //   420: fload_3
    //   421: fstore #6
    //   423: iload #16
    //   425: ifeq -> 431
    //   428: goto -> 434
    //   431: fload #4
    //   433: fstore_3
    //   434: iload #16
    //   436: ifeq -> 446
    //   439: fload #5
    //   441: fstore #4
    //   443: goto -> 449
    //   446: fload_2
    //   447: fstore #4
    //   449: iload #16
    //   451: ifeq -> 457
    //   454: goto -> 585
    //   457: fload #5
    //   459: fstore_2
    //   460: goto -> 585
    //   463: iload #16
    //   465: ifeq -> 474
    //   468: fload_2
    //   469: fstore #10
    //   471: goto -> 478
    //   474: fload #11
    //   476: fstore #10
    //   478: fload_2
    //   479: fstore #4
    //   481: iload #16
    //   483: ifeq -> 490
    //   486: fload #11
    //   488: fstore #4
    //   490: iload #16
    //   492: ifeq -> 502
    //   495: fload #9
    //   497: fstore #11
    //   499: goto -> 506
    //   502: fload #8
    //   504: fstore #11
    //   506: iload #16
    //   508: ifeq -> 515
    //   511: fload #8
    //   513: fstore #9
    //   515: fload #10
    //   517: invokestatic a : (F)Z
    //   520: ifne -> 529
    //   523: fload #10
    //   525: fstore_2
    //   526: goto -> 531
    //   529: fload_3
    //   530: fstore_2
    //   531: fload #7
    //   533: fstore_3
    //   534: fload #4
    //   536: invokestatic a : (F)Z
    //   539: ifne -> 545
    //   542: fload #4
    //   544: fstore_3
    //   545: fload #6
    //   547: fstore #4
    //   549: fload #11
    //   551: invokestatic a : (F)Z
    //   554: ifne -> 561
    //   557: fload #11
    //   559: fstore #4
    //   561: fload_2
    //   562: fstore #8
    //   564: fload_3
    //   565: fstore #7
    //   567: fload #4
    //   569: fstore #6
    //   571: fload #9
    //   573: invokestatic a : (F)Z
    //   576: ifne -> 605
    //   579: fload_2
    //   580: fstore #6
    //   582: fload #9
    //   584: fstore_2
    //   585: fload #6
    //   587: fstore #8
    //   589: fload_3
    //   590: fstore #7
    //   592: fload #4
    //   594: fstore #6
    //   596: fload_2
    //   597: fstore #5
    //   599: goto -> 605
    //   602: fload_3
    //   603: fstore #8
    //   605: fload #8
    //   607: fconst_0
    //   608: fcmpl
    //   609: ifgt -> 657
    //   612: fload #7
    //   614: fconst_0
    //   615: fcmpl
    //   616: ifgt -> 657
    //   619: fload #5
    //   621: fconst_0
    //   622: fcmpl
    //   623: ifgt -> 657
    //   626: fload #6
    //   628: fconst_0
    //   629: fcmpl
    //   630: ifle -> 636
    //   633: goto -> 657
    //   636: aload_1
    //   637: new android/graphics/RectF
    //   640: dup
    //   641: fload #14
    //   643: fload #15
    //   645: fload #12
    //   647: fload #13
    //   649: invokespecial <init> : (FFFF)V
    //   652: invokevirtual clipRect : (Landroid/graphics/RectF;)Z
    //   655: pop
    //   656: return
    //   657: aload_0
    //   658: getfield mPath : Landroid/graphics/Path;
    //   661: ifnonnull -> 675
    //   664: aload_0
    //   665: new android/graphics/Path
    //   668: dup
    //   669: invokespecial <init> : ()V
    //   672: putfield mPath : Landroid/graphics/Path;
    //   675: aload_0
    //   676: getfield mPath : Landroid/graphics/Path;
    //   679: invokevirtual rewind : ()V
    //   682: aload_0
    //   683: getfield mPath : Landroid/graphics/Path;
    //   686: astore #19
    //   688: new android/graphics/RectF
    //   691: dup
    //   692: fload #14
    //   694: fload #15
    //   696: fload #12
    //   698: fload #13
    //   700: invokespecial <init> : (FFFF)V
    //   703: astore #20
    //   705: fload #8
    //   707: aload #18
    //   709: getfield left : F
    //   712: fsub
    //   713: fconst_0
    //   714: invokestatic max : (FF)F
    //   717: fstore_2
    //   718: fload #8
    //   720: aload #18
    //   722: getfield top : F
    //   725: fsub
    //   726: fconst_0
    //   727: invokestatic max : (FF)F
    //   730: fstore_3
    //   731: fload #7
    //   733: aload #18
    //   735: getfield right : F
    //   738: fsub
    //   739: fconst_0
    //   740: invokestatic max : (FF)F
    //   743: fstore #4
    //   745: fload #7
    //   747: aload #18
    //   749: getfield top : F
    //   752: fsub
    //   753: fconst_0
    //   754: invokestatic max : (FF)F
    //   757: fstore #7
    //   759: fload #5
    //   761: aload #18
    //   763: getfield right : F
    //   766: fsub
    //   767: fconst_0
    //   768: invokestatic max : (FF)F
    //   771: fstore #8
    //   773: fload #5
    //   775: aload #18
    //   777: getfield bottom : F
    //   780: fsub
    //   781: fconst_0
    //   782: invokestatic max : (FF)F
    //   785: fstore #5
    //   787: fload #6
    //   789: aload #18
    //   791: getfield left : F
    //   794: fsub
    //   795: fconst_0
    //   796: invokestatic max : (FF)F
    //   799: fstore #9
    //   801: fload #6
    //   803: aload #18
    //   805: getfield bottom : F
    //   808: fsub
    //   809: fconst_0
    //   810: invokestatic max : (FF)F
    //   813: fstore #6
    //   815: getstatic android/graphics/Path$Direction.CW : Landroid/graphics/Path$Direction;
    //   818: astore #18
    //   820: aload #19
    //   822: aload #20
    //   824: bipush #8
    //   826: newarray float
    //   828: dup
    //   829: iconst_0
    //   830: fload_2
    //   831: fastore
    //   832: dup
    //   833: iconst_1
    //   834: fload_3
    //   835: fastore
    //   836: dup
    //   837: iconst_2
    //   838: fload #4
    //   840: fastore
    //   841: dup
    //   842: iconst_3
    //   843: fload #7
    //   845: fastore
    //   846: dup
    //   847: iconst_4
    //   848: fload #8
    //   850: fastore
    //   851: dup
    //   852: iconst_5
    //   853: fload #5
    //   855: fastore
    //   856: dup
    //   857: bipush #6
    //   859: fload #9
    //   861: fastore
    //   862: dup
    //   863: bipush #7
    //   865: fload #6
    //   867: fastore
    //   868: aload #18
    //   870: invokevirtual addRoundRect : (Landroid/graphics/RectF;[FLandroid/graphics/Path$Direction;)V
    //   873: aload_1
    //   874: aload_0
    //   875: getfield mPath : Landroid/graphics/Path;
    //   878: invokevirtual clipPath : (Landroid/graphics/Path;)Z
    //   881: pop
    //   882: return
    //   883: aload_0
    //   884: getfield mPath : Landroid/graphics/Path;
    //   887: astore_1
    //   888: aload_1
    //   889: ifnull -> 896
    //   892: aload_1
    //   893: invokevirtual rewind : ()V
    //   896: return
  }
  
  private ReactViewBackgroundDrawable getOrCreateReactViewBackground() {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private int indexOfChildInAllChildren(View paramView) {
    int j = this.mAllChildrenCount;
    View[] arrayOfView = (View[])a.b(this.mAllChildren);
    for (int i = 0; i < j; i++) {
      if (arrayOfView[i] == paramView)
        return i; 
    } 
    return -1;
  }
  
  private void removeFromArray(int paramInt) {
    View[] arrayOfView = (View[])a.b(this.mAllChildren);
    int i = this.mAllChildrenCount;
    if (paramInt == i - 1) {
      paramInt = i - 1;
      this.mAllChildrenCount = paramInt;
      arrayOfView[paramInt] = null;
      return;
    } 
    if (paramInt >= 0 && paramInt < i) {
      System.arraycopy(arrayOfView, paramInt + 1, arrayOfView, paramInt, i - paramInt - 1);
      paramInt = this.mAllChildrenCount - 1;
      this.mAllChildrenCount = paramInt;
      arrayOfView[paramInt] = null;
      return;
    } 
    throw new IndexOutOfBoundsException();
  }
  
  private void updateBackgroundDrawable(Drawable paramDrawable) {
    if (Build.VERSION.SDK_INT >= 16) {
      super.setBackground(paramDrawable);
      return;
    } 
    setBackgroundDrawable(paramDrawable);
  }
  
  private void updateClippingToRect(Rect paramRect) {
    a.b(this.mAllChildren);
    int i = 0;
    for (int j = 0; i < this.mAllChildrenCount; j = k) {
      updateSubviewClipStatus(paramRect, i, j);
      int k = j;
      if (this.mAllChildren[i].getParent() == null)
        k = j + 1; 
      i++;
    } 
  }
  
  private void updateSubviewClipStatus(Rect paramRect, int paramInt1, int paramInt2) {
    boolean bool1;
    View view = ((View[])a.b(this.mAllChildren))[paramInt1];
    sHelperRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    boolean bool = paramRect.intersects(sHelperRect.left, sHelperRect.top, sHelperRect.right, sHelperRect.bottom);
    Animation animation = view.getAnimation();
    boolean bool2 = true;
    if (animation != null && !animation.hasEnded()) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (!bool && view.getParent() != null && !bool1) {
      removeViewsInLayout(paramInt1 - paramInt2, 1);
      paramInt1 = bool2;
    } else if (bool && view.getParent() == null) {
      addViewInLayout(view, paramInt1 - paramInt2, sDefaultLayoutParam, true);
      invalidate();
      paramInt1 = bool2;
    } else if (bool) {
      paramInt1 = bool2;
    } else {
      paramInt1 = 0;
    } 
    if (paramInt1 != 0 && view instanceof ReactClippingViewGroup) {
      ReactClippingViewGroup reactClippingViewGroup = (ReactClippingViewGroup)view;
      if (reactClippingViewGroup.getRemoveClippedSubviews())
        reactClippingViewGroup.updateClippingRect(); 
    } 
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    this.mDrawingOrderHelper.handleAddView(paramView);
    setChildrenDrawingOrderEnabled(this.mDrawingOrderHelper.shouldEnableCustomDrawingOrder());
    if (getChildCount() < paramInt) {
      addView(paramView, paramLayoutParams);
      return;
    } 
    super.addView(paramView, paramInt, paramLayoutParams);
  }
  
  void addViewWithSubviewClippingEnabled(View paramView, int paramInt) {
    addViewWithSubviewClippingEnabled(paramView, paramInt, sDefaultLayoutParam);
  }
  
  void addViewWithSubviewClippingEnabled(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    a.a(this.mRemoveClippedSubviews);
    a.b(this.mClippingRect);
    a.b(this.mAllChildren);
    addInArray(paramView, paramInt);
    int i = 0;
    int j;
    for (j = 0; i < paramInt; j = k) {
      int k = j;
      if (this.mAllChildren[i].getParent() == null)
        k = j + 1; 
      i++;
    } 
    updateSubviewClipStatus(this.mClippingRect, paramInt, j);
    paramView.addOnLayoutChangeListener(this.mChildrenLayoutChangeListener);
  }
  
  protected void dispatchDraw(Canvas paramCanvas) {
    try {
      dispatchOverflowDraw(paramCanvas);
      super.dispatchDraw(paramCanvas);
      return;
    } catch (StackOverflowError stackOverflowError) {
      RootView rootView = RootViewUtil.getRootView((View)this);
      if (rootView != null) {
        rootView.handleException(stackOverflowError);
        return;
      } 
      throw stackOverflowError;
    } 
  }
  
  protected void dispatchSetPressed(boolean paramBoolean) {}
  
  int getAllChildrenCount() {
    return this.mAllChildrenCount;
  }
  
  public int getBackgroundColor() {
    return (getBackground() != null) ? ((ReactViewBackgroundDrawable)getBackground()).getColor() : 0;
  }
  
  View getChildAtWithSubviewClippingEnabled(int paramInt) {
    return ((View[])a.b(this.mAllChildren))[paramInt];
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2) {
    return this.mDrawingOrderHelper.getChildDrawingOrder(paramInt1, paramInt2);
  }
  
  public void getClippingRect(Rect paramRect) {
    paramRect.set(this.mClippingRect);
  }
  
  public Rect getHitSlopRect() {
    return this.mHitSlopRect;
  }
  
  public PointerEvents getPointerEvents() {
    return this.mPointerEvents;
  }
  
  public boolean getRemoveClippedSubviews() {
    return this.mRemoveClippedSubviews;
  }
  
  public int getZIndexMappedChildIndex(int paramInt) {
    int i = paramInt;
    if (this.mDrawingOrderHelper.shouldEnableCustomDrawingOrder())
      i = this.mDrawingOrderHelper.getChildDrawingOrder(getChildCount(), paramInt); 
    return i;
  }
  
  public boolean hasOverlappingRendering() {
    return this.mNeedsOffscreenAlphaCompositing;
  }
  
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.mRemoveClippedSubviews)
      updateClippingRect(); 
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    OnInterceptTouchEventListener onInterceptTouchEventListener = this.mOnInterceptTouchEventListener;
    return (onInterceptTouchEventListener != null && onInterceptTouchEventListener.onInterceptTouchEvent(this, paramMotionEvent)) ? true : ((this.mPointerEvents != PointerEvents.NONE) ? ((this.mPointerEvents == PointerEvents.BOX_ONLY) ? true : super.onInterceptTouchEvent(paramMotionEvent)) : true);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    MeasureSpecAssertions.assertExplicitMeasureSpec(paramInt1, paramInt2);
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
  }
  
  public void onRtlPropertiesChanged(int paramInt) {
    if (Build.VERSION.SDK_INT >= 17) {
      ReactViewBackgroundDrawable reactViewBackgroundDrawable = this.mReactBackgroundDrawable;
      if (reactViewBackgroundDrawable != null)
        reactViewBackgroundDrawable.setResolvedLayoutDirection(this.mLayoutDirection); 
    } 
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mRemoveClippedSubviews)
      updateClippingRect(); 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    return !(this.mPointerEvents == PointerEvents.NONE || this.mPointerEvents == PointerEvents.BOX_NONE);
  }
  
  void removeAllViewsWithSubviewClippingEnabled() {
    a.a(this.mRemoveClippedSubviews);
    a.b(this.mAllChildren);
    for (int i = 0; i < this.mAllChildrenCount; i++)
      this.mAllChildren[i].removeOnLayoutChangeListener(this.mChildrenLayoutChangeListener); 
    removeAllViewsInLayout();
    this.mAllChildrenCount = 0;
  }
  
  public void removeView(View paramView) {
    this.mDrawingOrderHelper.handleRemoveView(paramView);
    setChildrenDrawingOrderEnabled(this.mDrawingOrderHelper.shouldEnableCustomDrawingOrder());
    super.removeView(paramView);
  }
  
  public void removeViewAt(int paramInt) {
    this.mDrawingOrderHelper.handleRemoveView(getChildAt(paramInt));
    setChildrenDrawingOrderEnabled(this.mDrawingOrderHelper.shouldEnableCustomDrawingOrder());
    super.removeViewAt(paramInt);
  }
  
  void removeViewWithSubviewClippingEnabled(View paramView) {
    a.a(this.mRemoveClippedSubviews);
    a.b(this.mClippingRect);
    a.b(this.mAllChildren);
    paramView.removeOnLayoutChangeListener(this.mChildrenLayoutChangeListener);
    int i = indexOfChildInAllChildren(paramView);
    if (this.mAllChildren[i].getParent() != null) {
      int j = 0;
      int k;
      for (k = 0; j < i; k = m) {
        int m = k;
        if (this.mAllChildren[j].getParent() == null)
          m = k + 1; 
        j++;
      } 
      removeViewsInLayout(i - k, 1);
    } 
    removeFromArray(i);
  }
  
  public void requestLayout() {}
  
  public void setBackground(Drawable paramDrawable) {
    throw new UnsupportedOperationException("This method is not supported for ReactViewGroup instances");
  }
  
  public void setBackgroundColor(int paramInt) {
    if (paramInt != 0 || this.mReactBackgroundDrawable != null)
      getOrCreateReactViewBackground().setColor(paramInt); 
  }
  
  public void setBorderColor(int paramInt, float paramFloat1, float paramFloat2) {
    getOrCreateReactViewBackground().setBorderColor(paramInt, paramFloat1, paramFloat2);
  }
  
  public void setBorderRadius(float paramFloat) {
    ReactViewBackgroundDrawable reactViewBackgroundDrawable = getOrCreateReactViewBackground();
    reactViewBackgroundDrawable.setRadius(paramFloat);
    if (11 < Build.VERSION.SDK_INT && Build.VERSION.SDK_INT < 18) {
      byte b;
      if (reactViewBackgroundDrawable.hasRoundedBorders()) {
        b = 1;
      } else {
        b = 2;
      } 
      if (b != getLayerType())
        setLayerType(b, null); 
    } 
  }
  
  public void setBorderRadius(float paramFloat, int paramInt) {
    ReactViewBackgroundDrawable reactViewBackgroundDrawable = getOrCreateReactViewBackground();
    reactViewBackgroundDrawable.setRadius(paramFloat, paramInt);
    if (11 < Build.VERSION.SDK_INT && Build.VERSION.SDK_INT < 18) {
      if (reactViewBackgroundDrawable.hasRoundedBorders()) {
        paramInt = 1;
      } else {
        paramInt = 2;
      } 
      if (paramInt != getLayerType())
        setLayerType(paramInt, null); 
    } 
  }
  
  public void setBorderStyle(String paramString) {
    getOrCreateReactViewBackground().setBorderStyle(paramString);
  }
  
  public void setBorderWidth(int paramInt, float paramFloat) {
    getOrCreateReactViewBackground().setBorderWidth(paramInt, paramFloat);
  }
  
  public void setHitSlopRect(Rect paramRect) {
    this.mHitSlopRect = paramRect;
  }
  
  public void setNeedsOffscreenAlphaCompositing(boolean paramBoolean) {
    this.mNeedsOffscreenAlphaCompositing = paramBoolean;
  }
  
  public void setOnInterceptTouchEventListener(OnInterceptTouchEventListener paramOnInterceptTouchEventListener) {
    this.mOnInterceptTouchEventListener = paramOnInterceptTouchEventListener;
  }
  
  public void setOverflow(String paramString) {
    this.mOverflow = paramString;
    invalidate();
  }
  
  void setPointerEvents(PointerEvents paramPointerEvents) {
    this.mPointerEvents = paramPointerEvents;
  }
  
  public void setRemoveClippedSubviews(boolean paramBoolean) {
    if (paramBoolean == this.mRemoveClippedSubviews)
      return; 
    this.mRemoveClippedSubviews = paramBoolean;
    int i = 0;
    if (paramBoolean) {
      this.mClippingRect = new Rect();
      ReactClippingViewGroupHelper.calculateClippingRect((View)this, this.mClippingRect);
      this.mAllChildrenCount = getChildCount();
      this.mAllChildren = new View[Math.max(12, this.mAllChildrenCount)];
      this.mChildrenLayoutChangeListener = new ChildrenLayoutChangeListener(this);
      while (i < this.mAllChildrenCount) {
        View view = getChildAt(i);
        this.mAllChildren[i] = view;
        view.addOnLayoutChangeListener(this.mChildrenLayoutChangeListener);
        i++;
      } 
      updateClippingRect();
      return;
    } 
    a.b(this.mClippingRect);
    a.b(this.mAllChildren);
    a.b(this.mChildrenLayoutChangeListener);
    for (i = 0; i < this.mAllChildrenCount; i++)
      this.mAllChildren[i].removeOnLayoutChangeListener(this.mChildrenLayoutChangeListener); 
    getDrawingRect(this.mClippingRect);
    updateClippingToRect(this.mClippingRect);
    this.mAllChildren = null;
    this.mClippingRect = null;
    this.mAllChildrenCount = 0;
    this.mChildrenLayoutChangeListener = null;
  }
  
  public void setTranslucentBackgroundDrawable(Drawable paramDrawable) {
    updateBackgroundDrawable((Drawable)null);
    ReactViewBackgroundDrawable reactViewBackgroundDrawable = this.mReactBackgroundDrawable;
    if (reactViewBackgroundDrawable != null && paramDrawable != null) {
      updateBackgroundDrawable((Drawable)new LayerDrawable(new Drawable[] { reactViewBackgroundDrawable, paramDrawable }));
      return;
    } 
    if (paramDrawable != null)
      updateBackgroundDrawable(paramDrawable); 
  }
  
  public void updateClippingRect() {
    if (!this.mRemoveClippedSubviews)
      return; 
    a.b(this.mClippingRect);
    a.b(this.mAllChildren);
    ReactClippingViewGroupHelper.calculateClippingRect((View)this, this.mClippingRect);
    updateClippingToRect(this.mClippingRect);
  }
  
  public void updateDrawingOrder() {
    this.mDrawingOrderHelper.update();
    setChildrenDrawingOrderEnabled(this.mDrawingOrderHelper.shouldEnableCustomDrawingOrder());
    invalidate();
  }
  
  public void updateSubviewClipStatus(View paramView) {
    if (this.mRemoveClippedSubviews) {
      boolean bool1;
      if (getParent() == null)
        return; 
      a.b(this.mClippingRect);
      a.b(this.mAllChildren);
      sHelperRect.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
      boolean bool2 = this.mClippingRect.intersects(sHelperRect.left, sHelperRect.top, sHelperRect.right, sHelperRect.bottom);
      ViewParent viewParent = paramView.getParent();
      int i = 0;
      if (viewParent != null) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      if (bool2 != bool1)
        for (int j = 0; i < this.mAllChildrenCount; j = k) {
          View[] arrayOfView = this.mAllChildren;
          if (arrayOfView[i] == paramView) {
            updateSubviewClipStatus(this.mClippingRect, i, j);
            return;
          } 
          int k = j;
          if (arrayOfView[i].getParent() == null)
            k = j + 1; 
          i++;
        }  
    } 
  }
  
  static final class ChildrenLayoutChangeListener implements View.OnLayoutChangeListener {
    private final ReactViewGroup mParent;
    
    private ChildrenLayoutChangeListener(ReactViewGroup param1ReactViewGroup) {
      this.mParent = param1ReactViewGroup;
    }
    
    public final void onLayoutChange(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8) {
      if (this.mParent.getRemoveClippedSubviews())
        this.mParent.updateSubviewClipStatus(param1View); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\view\ReactViewGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */