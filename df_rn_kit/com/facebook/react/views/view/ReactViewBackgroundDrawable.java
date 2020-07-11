package com.facebook.react.views.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import com.facebook.react.modules.i18nmanager.I18nUtil;
import com.facebook.react.uimanager.FloatUtil;
import com.facebook.react.uimanager.Spacing;
import com.facebook.yoga.a;
import java.util.Arrays;
import java.util.Locale;

public class ReactViewBackgroundDrawable extends Drawable {
  private int mAlpha = 255;
  
  private Spacing mBorderAlpha;
  
  private float[] mBorderCornerRadii;
  
  private Spacing mBorderRGB;
  
  private float mBorderRadius = 1.0E21F;
  
  private BorderStyle mBorderStyle;
  
  private Spacing mBorderWidth;
  
  private int mColor = 0;
  
  private final Context mContext;
  
  private PointF mInnerBottomLeftCorner;
  
  private PointF mInnerBottomRightCorner;
  
  private Path mInnerClipPathForBorderRadius;
  
  private RectF mInnerClipTempRectForBorderRadius;
  
  private PointF mInnerTopLeftCorner;
  
  private PointF mInnerTopRightCorner;
  
  private int mLayoutDirection;
  
  private boolean mNeedUpdatePathForBorderRadius;
  
  private Path mOuterClipPathForBorderRadius;
  
  private RectF mOuterClipTempRectForBorderRadius;
  
  private final Paint mPaint = new Paint(1);
  
  private PathEffect mPathEffectForBorderStyle;
  
  private Path mPathForBorder;
  
  private Path mPathForBorderRadiusOutline;
  
  private RectF mTempRectForBorderRadiusOutline;
  
  public ReactViewBackgroundDrawable(Context paramContext) {
    this.mContext = paramContext;
  }
  
  private static int colorFromAlphaAndRGBComponents(float paramFloat1, float paramFloat2) {
    int i = (int)paramFloat2;
    return (int)paramFloat1 << 24 & 0xFF000000 | i & 0xFFFFFF;
  }
  
  private void drawQuadrilateral(Canvas paramCanvas, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    if (paramInt == 0)
      return; 
    if (this.mPathForBorder == null)
      this.mPathForBorder = new Path(); 
    this.mPaint.setColor(paramInt);
    this.mPathForBorder.reset();
    this.mPathForBorder.moveTo(paramFloat1, paramFloat2);
    this.mPathForBorder.lineTo(paramFloat3, paramFloat4);
    this.mPathForBorder.lineTo(paramFloat5, paramFloat6);
    this.mPathForBorder.lineTo(paramFloat7, paramFloat8);
    this.mPathForBorder.lineTo(paramFloat1, paramFloat2);
    paramCanvas.drawPath(this.mPathForBorder, this.mPaint);
  }
  
  private void drawRectangularBackgroundWithBorders(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mColor : I
    //   4: aload_0
    //   5: getfield mAlpha : I
    //   8: invokestatic multiplyColorAlpha : (II)I
    //   11: istore #7
    //   13: iload #7
    //   15: invokestatic alpha : (I)I
    //   18: ifeq -> 55
    //   21: aload_0
    //   22: getfield mPaint : Landroid/graphics/Paint;
    //   25: iload #7
    //   27: invokevirtual setColor : (I)V
    //   30: aload_0
    //   31: getfield mPaint : Landroid/graphics/Paint;
    //   34: getstatic android/graphics/Paint$Style.FILL : Landroid/graphics/Paint$Style;
    //   37: invokevirtual setStyle : (Landroid/graphics/Paint$Style;)V
    //   40: aload_1
    //   41: aload_0
    //   42: invokevirtual getBounds : ()Landroid/graphics/Rect;
    //   45: aload_0
    //   46: getfield mPaint : Landroid/graphics/Paint;
    //   49: invokevirtual drawRect : (Landroid/graphics/Rect;Landroid/graphics/Paint;)V
    //   52: goto -> 55
    //   55: aload_0
    //   56: invokevirtual getDirectionAwareBorderInsets : ()Landroid/graphics/RectF;
    //   59: astore #23
    //   61: aload #23
    //   63: getfield left : F
    //   66: invokestatic round : (F)I
    //   69: istore #13
    //   71: aload #23
    //   73: getfield top : F
    //   76: invokestatic round : (F)I
    //   79: istore #16
    //   81: aload #23
    //   83: getfield right : F
    //   86: invokestatic round : (F)I
    //   89: istore #14
    //   91: aload #23
    //   93: getfield bottom : F
    //   96: invokestatic round : (F)I
    //   99: istore #15
    //   101: iload #13
    //   103: ifgt -> 121
    //   106: iload #14
    //   108: ifgt -> 121
    //   111: iload #16
    //   113: ifgt -> 121
    //   116: iload #15
    //   118: ifle -> 583
    //   121: aload_0
    //   122: invokevirtual getBounds : ()Landroid/graphics/Rect;
    //   125: astore #23
    //   127: aload_0
    //   128: iconst_0
    //   129: invokespecial getBorderColor : (I)I
    //   132: istore #7
    //   134: aload_0
    //   135: iconst_1
    //   136: invokespecial getBorderColor : (I)I
    //   139: istore #18
    //   141: aload_0
    //   142: iconst_2
    //   143: invokespecial getBorderColor : (I)I
    //   146: istore #11
    //   148: aload_0
    //   149: iconst_3
    //   150: invokespecial getBorderColor : (I)I
    //   153: istore #17
    //   155: iload #7
    //   157: istore #9
    //   159: getstatic android/os/Build$VERSION.SDK_INT : I
    //   162: bipush #17
    //   164: if_icmplt -> 379
    //   167: aload_0
    //   168: invokevirtual getResolvedLayoutDirection : ()I
    //   171: iconst_1
    //   172: if_icmpne -> 181
    //   175: iconst_1
    //   176: istore #12
    //   178: goto -> 184
    //   181: iconst_0
    //   182: istore #12
    //   184: aload_0
    //   185: iconst_4
    //   186: invokespecial getBorderColor : (I)I
    //   189: istore #10
    //   191: aload_0
    //   192: iconst_5
    //   193: invokespecial getBorderColor : (I)I
    //   196: istore #8
    //   198: invokestatic getInstance : ()Lcom/facebook/react/modules/i18nmanager/I18nUtil;
    //   201: aload_0
    //   202: getfield mContext : Landroid/content/Context;
    //   205: invokevirtual doLeftAndRightSwapInRTL : (Landroid/content/Context;)Z
    //   208: ifeq -> 276
    //   211: aload_0
    //   212: iconst_4
    //   213: invokespecial isBorderColorDefined : (I)Z
    //   216: ifne -> 222
    //   219: goto -> 226
    //   222: iload #10
    //   224: istore #7
    //   226: aload_0
    //   227: iconst_5
    //   228: invokespecial isBorderColorDefined : (I)Z
    //   231: ifne -> 241
    //   234: iload #11
    //   236: istore #8
    //   238: goto -> 241
    //   241: iload #12
    //   243: ifeq -> 253
    //   246: iload #8
    //   248: istore #9
    //   250: goto -> 257
    //   253: iload #7
    //   255: istore #9
    //   257: iload #12
    //   259: ifeq -> 265
    //   262: goto -> 269
    //   265: iload #8
    //   267: istore #7
    //   269: iload #9
    //   271: istore #8
    //   273: goto -> 387
    //   276: iload #12
    //   278: ifeq -> 288
    //   281: iload #8
    //   283: istore #9
    //   285: goto -> 292
    //   288: iload #10
    //   290: istore #9
    //   292: iload #12
    //   294: ifeq -> 304
    //   297: iload #10
    //   299: istore #8
    //   301: goto -> 304
    //   304: aload_0
    //   305: iconst_4
    //   306: invokespecial isBorderColorDefined : (I)Z
    //   309: istore #20
    //   311: aload_0
    //   312: iconst_5
    //   313: invokespecial isBorderColorDefined : (I)Z
    //   316: istore #21
    //   318: iload #12
    //   320: ifeq -> 330
    //   323: iload #21
    //   325: istore #22
    //   327: goto -> 334
    //   330: iload #20
    //   332: istore #22
    //   334: iload #12
    //   336: ifeq -> 342
    //   339: goto -> 346
    //   342: iload #21
    //   344: istore #20
    //   346: iload #22
    //   348: ifeq -> 355
    //   351: iload #9
    //   353: istore #7
    //   355: iload #7
    //   357: istore #9
    //   359: iload #20
    //   361: ifeq -> 379
    //   364: iload #8
    //   366: istore #9
    //   368: iload #7
    //   370: istore #8
    //   372: iload #9
    //   374: istore #7
    //   376: goto -> 387
    //   379: iload #9
    //   381: istore #8
    //   383: iload #11
    //   385: istore #7
    //   387: aload #23
    //   389: getfield left : I
    //   392: istore #10
    //   394: aload #23
    //   396: getfield top : I
    //   399: istore #9
    //   401: iload #13
    //   403: iload #16
    //   405: iload #14
    //   407: iload #15
    //   409: iload #8
    //   411: iload #18
    //   413: iload #7
    //   415: iload #17
    //   417: invokestatic fastBorderCompatibleColorOrZero : (IIIIIIII)I
    //   420: istore #11
    //   422: iload #11
    //   424: ifeq -> 584
    //   427: iload #11
    //   429: invokestatic alpha : (I)I
    //   432: ifeq -> 583
    //   435: aload #23
    //   437: getfield right : I
    //   440: istore #7
    //   442: aload #23
    //   444: getfield bottom : I
    //   447: istore #8
    //   449: aload_0
    //   450: getfield mPaint : Landroid/graphics/Paint;
    //   453: iload #11
    //   455: invokevirtual setColor : (I)V
    //   458: iload #13
    //   460: ifle -> 489
    //   463: aload_1
    //   464: iload #10
    //   466: i2f
    //   467: iload #9
    //   469: i2f
    //   470: iload #10
    //   472: iload #13
    //   474: iadd
    //   475: i2f
    //   476: iload #8
    //   478: iload #15
    //   480: isub
    //   481: i2f
    //   482: aload_0
    //   483: getfield mPaint : Landroid/graphics/Paint;
    //   486: invokevirtual drawRect : (FFFFLandroid/graphics/Paint;)V
    //   489: iload #16
    //   491: ifle -> 520
    //   494: aload_1
    //   495: iload #10
    //   497: iload #13
    //   499: iadd
    //   500: i2f
    //   501: iload #9
    //   503: i2f
    //   504: iload #7
    //   506: i2f
    //   507: iload #9
    //   509: iload #16
    //   511: iadd
    //   512: i2f
    //   513: aload_0
    //   514: getfield mPaint : Landroid/graphics/Paint;
    //   517: invokevirtual drawRect : (FFFFLandroid/graphics/Paint;)V
    //   520: iload #14
    //   522: ifle -> 551
    //   525: aload_1
    //   526: iload #7
    //   528: iload #14
    //   530: isub
    //   531: i2f
    //   532: iload #9
    //   534: iload #16
    //   536: iadd
    //   537: i2f
    //   538: iload #7
    //   540: i2f
    //   541: iload #8
    //   543: i2f
    //   544: aload_0
    //   545: getfield mPaint : Landroid/graphics/Paint;
    //   548: invokevirtual drawRect : (FFFFLandroid/graphics/Paint;)V
    //   551: iload #15
    //   553: ifle -> 582
    //   556: aload_1
    //   557: iload #10
    //   559: i2f
    //   560: iload #8
    //   562: iload #15
    //   564: isub
    //   565: i2f
    //   566: iload #7
    //   568: iload #14
    //   570: isub
    //   571: i2f
    //   572: iload #8
    //   574: i2f
    //   575: aload_0
    //   576: getfield mPaint : Landroid/graphics/Paint;
    //   579: invokevirtual drawRect : (FFFFLandroid/graphics/Paint;)V
    //   582: return
    //   583: return
    //   584: aload_0
    //   585: getfield mPaint : Landroid/graphics/Paint;
    //   588: iconst_0
    //   589: invokevirtual setAntiAlias : (Z)V
    //   592: aload #23
    //   594: invokevirtual width : ()I
    //   597: istore #11
    //   599: aload #23
    //   601: invokevirtual height : ()I
    //   604: istore #12
    //   606: iload #13
    //   608: ifle -> 670
    //   611: iload #10
    //   613: i2f
    //   614: fstore_2
    //   615: iload #9
    //   617: i2f
    //   618: fstore_3
    //   619: iload #10
    //   621: iload #13
    //   623: iadd
    //   624: i2f
    //   625: fstore #4
    //   627: iload #9
    //   629: iload #16
    //   631: iadd
    //   632: i2f
    //   633: fstore #5
    //   635: iload #9
    //   637: iload #12
    //   639: iadd
    //   640: istore #19
    //   642: aload_0
    //   643: aload_1
    //   644: iload #8
    //   646: fload_2
    //   647: fload_3
    //   648: fload #4
    //   650: fload #5
    //   652: fload #4
    //   654: iload #19
    //   656: iload #15
    //   658: isub
    //   659: i2f
    //   660: fload_2
    //   661: iload #19
    //   663: i2f
    //   664: invokespecial drawQuadrilateral : (Landroid/graphics/Canvas;IFFFFFFFF)V
    //   667: goto -> 670
    //   670: iload #16
    //   672: ifle -> 731
    //   675: iload #10
    //   677: i2f
    //   678: fstore_2
    //   679: iload #9
    //   681: i2f
    //   682: fstore_3
    //   683: iload #10
    //   685: iload #13
    //   687: iadd
    //   688: i2f
    //   689: fstore #4
    //   691: iload #9
    //   693: iload #16
    //   695: iadd
    //   696: i2f
    //   697: fstore #5
    //   699: iload #10
    //   701: iload #11
    //   703: iadd
    //   704: istore #8
    //   706: aload_0
    //   707: aload_1
    //   708: iload #18
    //   710: fload_2
    //   711: fload_3
    //   712: fload #4
    //   714: fload #5
    //   716: iload #8
    //   718: iload #14
    //   720: isub
    //   721: i2f
    //   722: fload #5
    //   724: iload #8
    //   726: i2f
    //   727: fload_3
    //   728: invokespecial drawQuadrilateral : (Landroid/graphics/Canvas;IFFFFFFFF)V
    //   731: iload #14
    //   733: ifle -> 799
    //   736: iload #10
    //   738: iload #11
    //   740: iadd
    //   741: istore #8
    //   743: iload #8
    //   745: i2f
    //   746: fstore_2
    //   747: iload #9
    //   749: i2f
    //   750: fstore_3
    //   751: iload #9
    //   753: iload #12
    //   755: iadd
    //   756: istore #18
    //   758: iload #18
    //   760: i2f
    //   761: fstore #4
    //   763: iload #8
    //   765: iload #14
    //   767: isub
    //   768: i2f
    //   769: fstore #5
    //   771: aload_0
    //   772: aload_1
    //   773: iload #7
    //   775: fload_2
    //   776: fload_3
    //   777: fload_2
    //   778: fload #4
    //   780: fload #5
    //   782: iload #18
    //   784: iload #15
    //   786: isub
    //   787: i2f
    //   788: fload #5
    //   790: iload #9
    //   792: iload #16
    //   794: iadd
    //   795: i2f
    //   796: invokespecial drawQuadrilateral : (Landroid/graphics/Canvas;IFFFFFFFF)V
    //   799: iload #15
    //   801: ifle -> 874
    //   804: iload #10
    //   806: i2f
    //   807: fstore_2
    //   808: iload #9
    //   810: iload #12
    //   812: iadd
    //   813: istore #7
    //   815: iload #7
    //   817: i2f
    //   818: fstore_3
    //   819: iload #10
    //   821: iload #11
    //   823: iadd
    //   824: istore #8
    //   826: iload #8
    //   828: i2f
    //   829: fstore #4
    //   831: iload #8
    //   833: iload #14
    //   835: isub
    //   836: i2f
    //   837: fstore #5
    //   839: iload #7
    //   841: iload #15
    //   843: isub
    //   844: i2f
    //   845: fstore #6
    //   847: aload_0
    //   848: aload_1
    //   849: iload #17
    //   851: fload_2
    //   852: fload_3
    //   853: fload #4
    //   855: fload_3
    //   856: fload #5
    //   858: fload #6
    //   860: iload #10
    //   862: iload #13
    //   864: iadd
    //   865: i2f
    //   866: fload #6
    //   868: invokespecial drawQuadrilateral : (Landroid/graphics/Canvas;IFFFFFFFF)V
    //   871: goto -> 874
    //   874: aload_0
    //   875: getfield mPaint : Landroid/graphics/Paint;
    //   878: iconst_1
    //   879: invokevirtual setAntiAlias : (Z)V
    //   882: return
  }
  
  private void drawRoundedBackgroundWithBorders(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial updatePath : ()V
    //   4: aload_1
    //   5: invokevirtual save : ()I
    //   8: pop
    //   9: aload_0
    //   10: getfield mColor : I
    //   13: aload_0
    //   14: getfield mAlpha : I
    //   17: invokestatic multiplyColorAlpha : (II)I
    //   20: istore #6
    //   22: iload #6
    //   24: invokestatic alpha : (I)I
    //   27: ifeq -> 61
    //   30: aload_0
    //   31: getfield mPaint : Landroid/graphics/Paint;
    //   34: iload #6
    //   36: invokevirtual setColor : (I)V
    //   39: aload_0
    //   40: getfield mPaint : Landroid/graphics/Paint;
    //   43: getstatic android/graphics/Paint$Style.FILL : Landroid/graphics/Paint$Style;
    //   46: invokevirtual setStyle : (Landroid/graphics/Paint$Style;)V
    //   49: aload_1
    //   50: aload_0
    //   51: getfield mInnerClipPathForBorderRadius : Landroid/graphics/Path;
    //   54: aload_0
    //   55: getfield mPaint : Landroid/graphics/Paint;
    //   58: invokevirtual drawPath : (Landroid/graphics/Path;Landroid/graphics/Paint;)V
    //   61: aload_0
    //   62: invokevirtual getDirectionAwareBorderInsets : ()Landroid/graphics/RectF;
    //   65: astore #17
    //   67: aload #17
    //   69: getfield top : F
    //   72: fconst_0
    //   73: fcmpl
    //   74: ifgt -> 107
    //   77: aload #17
    //   79: getfield bottom : F
    //   82: fconst_0
    //   83: fcmpl
    //   84: ifgt -> 107
    //   87: aload #17
    //   89: getfield left : F
    //   92: fconst_0
    //   93: fcmpl
    //   94: ifgt -> 107
    //   97: aload #17
    //   99: getfield right : F
    //   102: fconst_0
    //   103: fcmpl
    //   104: ifle -> 627
    //   107: aload_0
    //   108: getfield mPaint : Landroid/graphics/Paint;
    //   111: getstatic android/graphics/Paint$Style.FILL : Landroid/graphics/Paint$Style;
    //   114: invokevirtual setStyle : (Landroid/graphics/Paint$Style;)V
    //   117: aload_1
    //   118: aload_0
    //   119: getfield mOuterClipPathForBorderRadius : Landroid/graphics/Path;
    //   122: getstatic android/graphics/Region$Op.INTERSECT : Landroid/graphics/Region$Op;
    //   125: invokevirtual clipPath : (Landroid/graphics/Path;Landroid/graphics/Region$Op;)Z
    //   128: pop
    //   129: aload_1
    //   130: aload_0
    //   131: getfield mInnerClipPathForBorderRadius : Landroid/graphics/Path;
    //   134: getstatic android/graphics/Region$Op.DIFFERENCE : Landroid/graphics/Region$Op;
    //   137: invokevirtual clipPath : (Landroid/graphics/Path;Landroid/graphics/Region$Op;)Z
    //   140: pop
    //   141: iconst_0
    //   142: istore #11
    //   144: aload_0
    //   145: iconst_0
    //   146: invokespecial getBorderColor : (I)I
    //   149: istore #6
    //   151: aload_0
    //   152: iconst_1
    //   153: invokespecial getBorderColor : (I)I
    //   156: istore #12
    //   158: aload_0
    //   159: iconst_2
    //   160: invokespecial getBorderColor : (I)I
    //   163: istore #10
    //   165: aload_0
    //   166: iconst_3
    //   167: invokespecial getBorderColor : (I)I
    //   170: istore #13
    //   172: iload #6
    //   174: istore #8
    //   176: getstatic android/os/Build$VERSION.SDK_INT : I
    //   179: bipush #17
    //   181: if_icmplt -> 382
    //   184: aload_0
    //   185: invokevirtual getResolvedLayoutDirection : ()I
    //   188: iconst_1
    //   189: if_icmpne -> 195
    //   192: iconst_1
    //   193: istore #11
    //   195: aload_0
    //   196: iconst_4
    //   197: invokespecial getBorderColor : (I)I
    //   200: istore #9
    //   202: aload_0
    //   203: iconst_5
    //   204: invokespecial getBorderColor : (I)I
    //   207: istore #7
    //   209: invokestatic getInstance : ()Lcom/facebook/react/modules/i18nmanager/I18nUtil;
    //   212: aload_0
    //   213: getfield mContext : Landroid/content/Context;
    //   216: invokevirtual doLeftAndRightSwapInRTL : (Landroid/content/Context;)Z
    //   219: ifeq -> 283
    //   222: aload_0
    //   223: iconst_4
    //   224: invokespecial isBorderColorDefined : (I)Z
    //   227: ifne -> 233
    //   230: goto -> 237
    //   233: iload #9
    //   235: istore #6
    //   237: aload_0
    //   238: iconst_5
    //   239: invokespecial isBorderColorDefined : (I)Z
    //   242: ifne -> 252
    //   245: iload #10
    //   247: istore #7
    //   249: goto -> 252
    //   252: iload #11
    //   254: ifeq -> 264
    //   257: iload #7
    //   259: istore #8
    //   261: goto -> 268
    //   264: iload #6
    //   266: istore #8
    //   268: iload #11
    //   270: ifeq -> 276
    //   273: goto -> 280
    //   276: iload #7
    //   278: istore #6
    //   280: goto -> 386
    //   283: iload #11
    //   285: ifeq -> 295
    //   288: iload #7
    //   290: istore #8
    //   292: goto -> 299
    //   295: iload #9
    //   297: istore #8
    //   299: iload #11
    //   301: ifeq -> 311
    //   304: iload #9
    //   306: istore #7
    //   308: goto -> 311
    //   311: aload_0
    //   312: iconst_4
    //   313: invokespecial isBorderColorDefined : (I)Z
    //   316: istore #14
    //   318: aload_0
    //   319: iconst_5
    //   320: invokespecial isBorderColorDefined : (I)Z
    //   323: istore #15
    //   325: iload #11
    //   327: ifeq -> 337
    //   330: iload #15
    //   332: istore #16
    //   334: goto -> 341
    //   337: iload #14
    //   339: istore #16
    //   341: iload #11
    //   343: ifeq -> 349
    //   346: goto -> 353
    //   349: iload #15
    //   351: istore #14
    //   353: iload #16
    //   355: ifeq -> 362
    //   358: iload #8
    //   360: istore #6
    //   362: iload #6
    //   364: istore #8
    //   366: iload #14
    //   368: ifeq -> 382
    //   371: iload #6
    //   373: istore #8
    //   375: iload #7
    //   377: istore #6
    //   379: goto -> 386
    //   382: iload #10
    //   384: istore #6
    //   386: aload_0
    //   387: getfield mOuterClipTempRectForBorderRadius : Landroid/graphics/RectF;
    //   390: getfield left : F
    //   393: fstore_3
    //   394: aload_0
    //   395: getfield mOuterClipTempRectForBorderRadius : Landroid/graphics/RectF;
    //   398: getfield right : F
    //   401: fstore_2
    //   402: aload_0
    //   403: getfield mOuterClipTempRectForBorderRadius : Landroid/graphics/RectF;
    //   406: getfield top : F
    //   409: fstore #4
    //   411: aload_0
    //   412: getfield mOuterClipTempRectForBorderRadius : Landroid/graphics/RectF;
    //   415: getfield bottom : F
    //   418: fstore #5
    //   420: aload #17
    //   422: getfield left : F
    //   425: fconst_0
    //   426: fcmpl
    //   427: ifle -> 474
    //   430: aload_0
    //   431: aload_1
    //   432: iload #8
    //   434: fload_3
    //   435: fload #4
    //   437: aload_0
    //   438: getfield mInnerTopLeftCorner : Landroid/graphics/PointF;
    //   441: getfield x : F
    //   444: aload_0
    //   445: getfield mInnerTopLeftCorner : Landroid/graphics/PointF;
    //   448: getfield y : F
    //   451: aload_0
    //   452: getfield mInnerBottomLeftCorner : Landroid/graphics/PointF;
    //   455: getfield x : F
    //   458: aload_0
    //   459: getfield mInnerBottomLeftCorner : Landroid/graphics/PointF;
    //   462: getfield y : F
    //   465: fload_3
    //   466: fload #5
    //   468: invokespecial drawQuadrilateral : (Landroid/graphics/Canvas;IFFFFFFFF)V
    //   471: goto -> 474
    //   474: aload #17
    //   476: getfield top : F
    //   479: fconst_0
    //   480: fcmpl
    //   481: ifle -> 525
    //   484: aload_0
    //   485: aload_1
    //   486: iload #12
    //   488: fload_3
    //   489: fload #4
    //   491: aload_0
    //   492: getfield mInnerTopLeftCorner : Landroid/graphics/PointF;
    //   495: getfield x : F
    //   498: aload_0
    //   499: getfield mInnerTopLeftCorner : Landroid/graphics/PointF;
    //   502: getfield y : F
    //   505: aload_0
    //   506: getfield mInnerTopRightCorner : Landroid/graphics/PointF;
    //   509: getfield x : F
    //   512: aload_0
    //   513: getfield mInnerTopRightCorner : Landroid/graphics/PointF;
    //   516: getfield y : F
    //   519: fload_2
    //   520: fload #4
    //   522: invokespecial drawQuadrilateral : (Landroid/graphics/Canvas;IFFFFFFFF)V
    //   525: aload #17
    //   527: getfield right : F
    //   530: fconst_0
    //   531: fcmpl
    //   532: ifle -> 576
    //   535: aload_0
    //   536: aload_1
    //   537: iload #6
    //   539: fload_2
    //   540: fload #4
    //   542: aload_0
    //   543: getfield mInnerTopRightCorner : Landroid/graphics/PointF;
    //   546: getfield x : F
    //   549: aload_0
    //   550: getfield mInnerTopRightCorner : Landroid/graphics/PointF;
    //   553: getfield y : F
    //   556: aload_0
    //   557: getfield mInnerBottomRightCorner : Landroid/graphics/PointF;
    //   560: getfield x : F
    //   563: aload_0
    //   564: getfield mInnerBottomRightCorner : Landroid/graphics/PointF;
    //   567: getfield y : F
    //   570: fload_2
    //   571: fload #5
    //   573: invokespecial drawQuadrilateral : (Landroid/graphics/Canvas;IFFFFFFFF)V
    //   576: aload #17
    //   578: getfield bottom : F
    //   581: fconst_0
    //   582: fcmpl
    //   583: ifle -> 627
    //   586: aload_0
    //   587: aload_1
    //   588: iload #13
    //   590: fload_3
    //   591: fload #5
    //   593: aload_0
    //   594: getfield mInnerBottomLeftCorner : Landroid/graphics/PointF;
    //   597: getfield x : F
    //   600: aload_0
    //   601: getfield mInnerBottomLeftCorner : Landroid/graphics/PointF;
    //   604: getfield y : F
    //   607: aload_0
    //   608: getfield mInnerBottomRightCorner : Landroid/graphics/PointF;
    //   611: getfield x : F
    //   614: aload_0
    //   615: getfield mInnerBottomRightCorner : Landroid/graphics/PointF;
    //   618: getfield y : F
    //   621: fload_2
    //   622: fload #5
    //   624: invokespecial drawQuadrilateral : (Landroid/graphics/Canvas;IFFFFFFFF)V
    //   627: aload_1
    //   628: invokevirtual restore : ()V
    //   631: return
  }
  
  private static int fastBorderCompatibleColorOrZero(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
    byte b1;
    byte b2;
    int j = -1;
    if (paramInt1 > 0) {
      i = paramInt5;
    } else {
      i = -1;
    } 
    if (paramInt2 > 0) {
      b1 = paramInt6;
    } else {
      b1 = -1;
    } 
    if (paramInt3 > 0) {
      b2 = paramInt7;
    } else {
      b2 = -1;
    } 
    if (paramInt4 > 0)
      j = paramInt8; 
    int i = j & i & b1 & b2;
    if (paramInt1 <= 0)
      paramInt5 = 0; 
    if (paramInt2 <= 0)
      paramInt6 = 0; 
    if (paramInt3 <= 0)
      paramInt7 = 0; 
    if (paramInt4 <= 0)
      paramInt8 = 0; 
    return (i == (paramInt5 | paramInt6 | paramInt7 | paramInt8)) ? i : 0;
  }
  
  private int getBorderColor(int paramInt) {
    float f1;
    float f2;
    Spacing spacing = this.mBorderRGB;
    if (spacing != null) {
      f1 = spacing.get(paramInt);
    } else {
      f1 = 0.0F;
    } 
    spacing = this.mBorderAlpha;
    if (spacing != null) {
      f2 = spacing.get(paramInt);
    } else {
      f2 = 255.0F;
    } 
    return colorFromAlphaAndRGBComponents(f2, f1);
  }
  
  private int getBorderWidth(int paramInt) {
    Spacing spacing = this.mBorderWidth;
    if (spacing == null)
      return 0; 
    float f = spacing.get(paramInt);
    return a.a(f) ? -1 : Math.round(f);
  }
  
  private static void getEllipseIntersectionWithLine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, PointF paramPointF) {
    double d2 = (paramDouble1 + paramDouble3) / 2.0D;
    double d1 = (paramDouble2 + paramDouble4) / 2.0D;
    paramDouble5 -= d2;
    paramDouble6 -= d1;
    paramDouble3 = Math.abs(paramDouble3 - paramDouble1) / 2.0D;
    paramDouble4 = Math.abs(paramDouble4 - paramDouble2) / 2.0D;
    paramDouble1 = (paramDouble8 - d1 - paramDouble6) / (paramDouble7 - d2 - paramDouble5);
    paramDouble2 = paramDouble6 - paramDouble5 * paramDouble1;
    paramDouble5 = paramDouble4 * paramDouble4;
    paramDouble6 = paramDouble3 * paramDouble3;
    paramDouble4 = paramDouble5 + paramDouble6 * paramDouble1 * paramDouble1;
    paramDouble3 = paramDouble3 * 2.0D * paramDouble3 * paramDouble2 * paramDouble1;
    paramDouble5 = -(paramDouble6 * (paramDouble2 * paramDouble2 - paramDouble5)) / paramDouble4;
    paramDouble4 *= 2.0D;
    paramDouble5 = Math.sqrt(paramDouble5 + Math.pow(paramDouble3 / paramDouble4, 2.0D));
    paramDouble3 = -paramDouble3 / paramDouble4 - paramDouble5;
    paramDouble4 = paramDouble3 + d2;
    paramDouble1 = paramDouble1 * paramDouble3 + paramDouble2 + d1;
    if (!Double.isNaN(paramDouble4) && !Double.isNaN(paramDouble1)) {
      paramPointF.x = (float)paramDouble4;
      paramPointF.y = (float)paramDouble1;
    } 
  }
  
  private boolean isBorderColorDefined(int paramInt) {
    float f1;
    Spacing spacing = this.mBorderRGB;
    float f2 = 1.0E21F;
    if (spacing != null) {
      f1 = spacing.get(paramInt);
    } else {
      f1 = 1.0E21F;
    } 
    spacing = this.mBorderAlpha;
    if (spacing != null)
      f2 = spacing.get(paramInt); 
    return (!a.a(f1) && !a.a(f2));
  }
  
  private void setBorderAlpha(int paramInt, float paramFloat) {
    if (this.mBorderAlpha == null)
      this.mBorderAlpha = new Spacing(255.0F); 
    if (!FloatUtil.floatsEqual(this.mBorderAlpha.getRaw(paramInt), paramFloat)) {
      this.mBorderAlpha.set(paramInt, paramFloat);
      invalidateSelf();
    } 
  }
  
  private void setBorderRGB(int paramInt, float paramFloat) {
    if (this.mBorderRGB == null)
      this.mBorderRGB = new Spacing(0.0F); 
    if (!FloatUtil.floatsEqual(this.mBorderRGB.getRaw(paramInt), paramFloat)) {
      this.mBorderRGB.set(paramInt, paramFloat);
      invalidateSelf();
    } 
  }
  
  private void updatePath() {
    if (!this.mNeedUpdatePathForBorderRadius)
      return; 
    this.mNeedUpdatePathForBorderRadius = false;
    if (this.mInnerClipPathForBorderRadius == null)
      this.mInnerClipPathForBorderRadius = new Path(); 
    if (this.mOuterClipPathForBorderRadius == null)
      this.mOuterClipPathForBorderRadius = new Path(); 
    if (this.mPathForBorderRadiusOutline == null)
      this.mPathForBorderRadiusOutline = new Path(); 
    if (this.mInnerClipTempRectForBorderRadius == null)
      this.mInnerClipTempRectForBorderRadius = new RectF(); 
    if (this.mOuterClipTempRectForBorderRadius == null)
      this.mOuterClipTempRectForBorderRadius = new RectF(); 
    if (this.mTempRectForBorderRadiusOutline == null)
      this.mTempRectForBorderRadiusOutline = new RectF(); 
    this.mInnerClipPathForBorderRadius.reset();
    this.mOuterClipPathForBorderRadius.reset();
    this.mPathForBorderRadiusOutline.reset();
    this.mInnerClipTempRectForBorderRadius.set(getBounds());
    this.mOuterClipTempRectForBorderRadius.set(getBounds());
    this.mTempRectForBorderRadiusOutline.set(getBounds());
    RectF rectF1 = getDirectionAwareBorderInsets();
    RectF rectF2 = this.mInnerClipTempRectForBorderRadius;
    rectF2.top += rectF1.top;
    rectF2 = this.mInnerClipTempRectForBorderRadius;
    rectF2.bottom -= rectF1.bottom;
    rectF2 = this.mInnerClipTempRectForBorderRadius;
    rectF2.left += rectF1.left;
    rectF2 = this.mInnerClipTempRectForBorderRadius;
    rectF2.right -= rectF1.right;
    float f1 = getFullBorderRadius();
    float f7 = getBorderRadiusOrDefaultTo(f1, BorderRadiusLocation.TOP_LEFT);
    float f3 = getBorderRadiusOrDefaultTo(f1, BorderRadiusLocation.TOP_RIGHT);
    float f2 = getBorderRadiusOrDefaultTo(f1, BorderRadiusLocation.BOTTOM_LEFT);
    float f10 = getBorderRadiusOrDefaultTo(f1, BorderRadiusLocation.BOTTOM_RIGHT);
    f1 = f10;
    float f5 = f7;
    float f6 = f3;
    float f4 = f2;
    if (Build.VERSION.SDK_INT >= 17) {
      boolean bool;
      if (getResolvedLayoutDirection() == 1) {
        bool = true;
      } else {
        bool = false;
      } 
      float f = getBorderRadius(BorderRadiusLocation.TOP_START);
      f6 = getBorderRadius(BorderRadiusLocation.TOP_END);
      f5 = getBorderRadius(BorderRadiusLocation.BOTTOM_START);
      f4 = getBorderRadius(BorderRadiusLocation.BOTTOM_END);
      if (I18nUtil.getInstance().doLeftAndRightSwapInRTL(this.mContext)) {
        if (a.a(f)) {
          f1 = f7;
        } else {
          f1 = f;
        } 
        if (!a.a(f6))
          f3 = f6; 
        if (!a.a(f5))
          f2 = f5; 
        if (a.a(f4))
          f4 = f10; 
        if (bool) {
          f5 = f3;
        } else {
          f5 = f1;
        } 
        if (bool)
          f3 = f1; 
        if (bool) {
          f1 = f4;
        } else {
          f1 = f2;
        } 
        if (bool)
          f4 = f2; 
        f2 = f1;
        f1 = f4;
        f6 = f3;
        f4 = f2;
      } else {
        float f14;
        if (bool) {
          f1 = f6;
        } else {
          f1 = f;
        } 
        if (bool)
          f6 = f; 
        if (bool) {
          f14 = f4;
        } else {
          f14 = f5;
        } 
        if (bool) {
          f = f5;
        } else {
          f = f4;
        } 
        if (!a.a(f1))
          f7 = f1; 
        if (!a.a(f6))
          f3 = f6; 
        if (!a.a(f14))
          f2 = f14; 
        f1 = f10;
        f5 = f7;
        f6 = f3;
        f4 = f2;
        if (!a.a(f)) {
          f1 = f;
          f4 = f2;
          f6 = f3;
          f5 = f7;
        } 
      } 
    } 
    f3 = Math.max(f5 - rectF1.left, 0.0F);
    f7 = Math.max(f5 - rectF1.top, 0.0F);
    float f8 = Math.max(f6 - rectF1.right, 0.0F);
    float f9 = Math.max(f6 - rectF1.top, 0.0F);
    f10 = Math.max(f1 - rectF1.right, 0.0F);
    float f11 = Math.max(f1 - rectF1.bottom, 0.0F);
    float f12 = Math.max(f4 - rectF1.left, 0.0F);
    float f13 = Math.max(f4 - rectF1.bottom, 0.0F);
    Path path2 = this.mInnerClipPathForBorderRadius;
    rectF2 = this.mInnerClipTempRectForBorderRadius;
    Path.Direction direction = Path.Direction.CW;
    path2.addRoundRect(rectF2, new float[] { f3, f7, f8, f9, f10, f11, f12, f13 }, direction);
    path2 = this.mOuterClipPathForBorderRadius;
    rectF2 = this.mOuterClipTempRectForBorderRadius;
    direction = Path.Direction.CW;
    path2.addRoundRect(rectF2, new float[] { f5, f5, f6, f6, f1, f1, f4, f4 }, direction);
    Spacing spacing = this.mBorderWidth;
    if (spacing != null) {
      f2 = spacing.get(8) / 2.0F;
    } else {
      f2 = 0.0F;
    } 
    Path path1 = this.mPathForBorderRadiusOutline;
    rectF2 = this.mTempRectForBorderRadiusOutline;
    f5 += f2;
    f6 += f2;
    f1 += f2;
    f2 = f4 + f2;
    direction = Path.Direction.CW;
    path1.addRoundRect(rectF2, new float[] { f5, f5, f6, f6, f1, f1, f2, f2 }, direction);
    if (this.mInnerTopLeftCorner == null)
      this.mInnerTopLeftCorner = new PointF(); 
    this.mInnerTopLeftCorner.x = this.mInnerClipTempRectForBorderRadius.left;
    this.mInnerTopLeftCorner.y = this.mInnerClipTempRectForBorderRadius.top;
    getEllipseIntersectionWithLine(this.mInnerClipTempRectForBorderRadius.left, this.mInnerClipTempRectForBorderRadius.top, (this.mInnerClipTempRectForBorderRadius.left + f3 * 2.0F), (this.mInnerClipTempRectForBorderRadius.top + f7 * 2.0F), this.mOuterClipTempRectForBorderRadius.left, this.mOuterClipTempRectForBorderRadius.top, this.mInnerClipTempRectForBorderRadius.left, this.mInnerClipTempRectForBorderRadius.top, this.mInnerTopLeftCorner);
    if (this.mInnerBottomLeftCorner == null)
      this.mInnerBottomLeftCorner = new PointF(); 
    this.mInnerBottomLeftCorner.x = this.mInnerClipTempRectForBorderRadius.left;
    this.mInnerBottomLeftCorner.y = this.mInnerClipTempRectForBorderRadius.bottom;
    getEllipseIntersectionWithLine(this.mInnerClipTempRectForBorderRadius.left, (this.mInnerClipTempRectForBorderRadius.bottom - f13 * 2.0F), (this.mInnerClipTempRectForBorderRadius.left + f12 * 2.0F), this.mInnerClipTempRectForBorderRadius.bottom, this.mOuterClipTempRectForBorderRadius.left, this.mOuterClipTempRectForBorderRadius.bottom, this.mInnerClipTempRectForBorderRadius.left, this.mInnerClipTempRectForBorderRadius.bottom, this.mInnerBottomLeftCorner);
    if (this.mInnerTopRightCorner == null)
      this.mInnerTopRightCorner = new PointF(); 
    this.mInnerTopRightCorner.x = this.mInnerClipTempRectForBorderRadius.right;
    this.mInnerTopRightCorner.y = this.mInnerClipTempRectForBorderRadius.top;
    getEllipseIntersectionWithLine((this.mInnerClipTempRectForBorderRadius.right - f8 * 2.0F), this.mInnerClipTempRectForBorderRadius.top, this.mInnerClipTempRectForBorderRadius.right, (this.mInnerClipTempRectForBorderRadius.top + f9 * 2.0F), this.mOuterClipTempRectForBorderRadius.right, this.mOuterClipTempRectForBorderRadius.top, this.mInnerClipTempRectForBorderRadius.right, this.mInnerClipTempRectForBorderRadius.top, this.mInnerTopRightCorner);
    if (this.mInnerBottomRightCorner == null)
      this.mInnerBottomRightCorner = new PointF(); 
    this.mInnerBottomRightCorner.x = this.mInnerClipTempRectForBorderRadius.right;
    this.mInnerBottomRightCorner.y = this.mInnerClipTempRectForBorderRadius.bottom;
    getEllipseIntersectionWithLine((this.mInnerClipTempRectForBorderRadius.right - f10 * 2.0F), (this.mInnerClipTempRectForBorderRadius.bottom - f11 * 2.0F), this.mInnerClipTempRectForBorderRadius.right, this.mInnerClipTempRectForBorderRadius.bottom, this.mOuterClipTempRectForBorderRadius.right, this.mOuterClipTempRectForBorderRadius.bottom, this.mInnerClipTempRectForBorderRadius.right, this.mInnerClipTempRectForBorderRadius.bottom, this.mInnerBottomRightCorner);
  }
  
  private void updatePathEffect() {
    BorderStyle borderStyle = this.mBorderStyle;
    if (borderStyle != null) {
      PathEffect pathEffect = borderStyle.getPathEffect(getFullBorderWidth());
    } else {
      borderStyle = null;
    } 
    this.mPathEffectForBorderStyle = (PathEffect)borderStyle;
    this.mPaint.setPathEffect(this.mPathEffectForBorderStyle);
  }
  
  public void draw(Canvas paramCanvas) {
    updatePathEffect();
    if (!hasRoundedBorders()) {
      drawRectangularBackgroundWithBorders(paramCanvas);
      return;
    } 
    drawRoundedBackgroundWithBorders(paramCanvas);
  }
  
  public int getAlpha() {
    return this.mAlpha;
  }
  
  public float getBorderRadius(BorderRadiusLocation paramBorderRadiusLocation) {
    return getBorderRadiusOrDefaultTo(1.0E21F, paramBorderRadiusLocation);
  }
  
  public float getBorderRadiusOrDefaultTo(float paramFloat, BorderRadiusLocation paramBorderRadiusLocation) {
    float[] arrayOfFloat = this.mBorderCornerRadii;
    if (arrayOfFloat == null)
      return paramFloat; 
    float f = arrayOfFloat[paramBorderRadiusLocation.ordinal()];
    return a.a(f) ? paramFloat : f;
  }
  
  public float getBorderWidthOrDefaultTo(float paramFloat, int paramInt) {
    Spacing spacing = this.mBorderWidth;
    if (spacing == null)
      return paramFloat; 
    float f = spacing.getRaw(paramInt);
    return a.a(f) ? paramFloat : f;
  }
  
  public int getColor() {
    return this.mColor;
  }
  
  public RectF getDirectionAwareBorderInsets() {
    float f4;
    float f1 = getBorderWidthOrDefaultTo(0.0F, 8);
    boolean bool = true;
    float f7 = getBorderWidthOrDefaultTo(f1, 1);
    float f8 = getBorderWidthOrDefaultTo(f1, 3);
    float f3 = getBorderWidthOrDefaultTo(f1, 0);
    float f6 = getBorderWidthOrDefaultTo(f1, 2);
    float f5 = f6;
    float f2 = f3;
    if (Build.VERSION.SDK_INT >= 17) {
      f5 = f6;
      f2 = f3;
      if (this.mBorderWidth != null) {
        if (getResolvedLayoutDirection() != 1)
          bool = false; 
        f4 = this.mBorderWidth.getRaw(4);
        f2 = this.mBorderWidth.getRaw(5);
        if (I18nUtil.getInstance().doLeftAndRightSwapInRTL(this.mContext)) {
          f1 = f4;
          if (a.a(f4))
            f1 = f3; 
          if (a.a(f2))
            f2 = f6; 
          if (bool) {
            f3 = f2;
          } else {
            f3 = f1;
          } 
          f5 = f2;
          f2 = f3;
          if (bool) {
            f4 = f1;
            f1 = f3;
          } else {
            return new RectF(f2, f7, f5, f8);
          } 
        } else {
          if (bool) {
            f5 = f2;
          } else {
            f5 = f4;
          } 
          if (!bool)
            f4 = f2; 
          f1 = f3;
          if (!a.a(f5))
            f1 = f5; 
          f5 = f6;
          f2 = f1;
          if (!a.a(f4)) {
            f2 = f1;
            f5 = f4;
            return new RectF(f2, f7, f5, f8);
          } 
          return new RectF(f2, f7, f5, f8);
        } 
      } else {
        return new RectF(f2, f7, f5, f8);
      } 
    } else {
      return new RectF(f2, f7, f5, f8);
    } 
    f2 = f1;
    f5 = f4;
    return new RectF(f2, f7, f5, f8);
  }
  
  public float getFullBorderRadius() {
    return a.a(this.mBorderRadius) ? 0.0F : this.mBorderRadius;
  }
  
  public float getFullBorderWidth() {
    Spacing spacing = this.mBorderWidth;
    return (spacing != null && !a.a(spacing.getRaw(8))) ? this.mBorderWidth.getRaw(8) : 0.0F;
  }
  
  public int getOpacity() {
    return ColorUtil.getOpacityFromColor(ColorUtil.multiplyColorAlpha(this.mColor, this.mAlpha));
  }
  
  public void getOutline(Outline paramOutline) {
    if (Build.VERSION.SDK_INT < 21) {
      super.getOutline(paramOutline);
      return;
    } 
    if ((!a.a(this.mBorderRadius) && this.mBorderRadius > 0.0F) || this.mBorderCornerRadii != null) {
      updatePath();
      paramOutline.setConvexPath(this.mPathForBorderRadiusOutline);
      return;
    } 
    paramOutline.setRect(getBounds());
  }
  
  public int getResolvedLayoutDirection() {
    return this.mLayoutDirection;
  }
  
  public boolean hasRoundedBorders() {
    if (!a.a(this.mBorderRadius) && this.mBorderRadius > 0.0F)
      return true; 
    float[] arrayOfFloat = this.mBorderCornerRadii;
    if (arrayOfFloat != null) {
      int j = arrayOfFloat.length;
      for (int i = 0; i < j; i++) {
        float f = arrayOfFloat[i];
        if (!a.a(f) && f > 0.0F)
          return true; 
      } 
    } 
    return false;
  }
  
  protected void onBoundsChange(Rect paramRect) {
    super.onBoundsChange(paramRect);
    this.mNeedUpdatePathForBorderRadius = true;
  }
  
  public boolean onResolvedLayoutDirectionChanged(int paramInt) {
    return false;
  }
  
  public void setAlpha(int paramInt) {
    if (paramInt != this.mAlpha) {
      this.mAlpha = paramInt;
      invalidateSelf();
    } 
  }
  
  public void setBorderColor(int paramInt, float paramFloat1, float paramFloat2) {
    setBorderRGB(paramInt, paramFloat1);
    setBorderAlpha(paramInt, paramFloat2);
  }
  
  public void setBorderStyle(String paramString) {
    BorderStyle borderStyle;
    if (paramString == null) {
      paramString = null;
    } else {
      borderStyle = BorderStyle.valueOf(paramString.toUpperCase(Locale.US));
    } 
    if (this.mBorderStyle != borderStyle) {
      this.mBorderStyle = borderStyle;
      this.mNeedUpdatePathForBorderRadius = true;
      invalidateSelf();
    } 
  }
  
  public void setBorderWidth(int paramInt, float paramFloat) {
    if (this.mBorderWidth == null)
      this.mBorderWidth = new Spacing(); 
    if (!FloatUtil.floatsEqual(this.mBorderWidth.getRaw(paramInt), paramFloat)) {
      this.mBorderWidth.set(paramInt, paramFloat);
      if (paramInt == 0 || paramInt == 1 || paramInt == 2 || paramInt == 3 || paramInt == 4 || paramInt == 5 || paramInt == 8)
        this.mNeedUpdatePathForBorderRadius = true; 
      invalidateSelf();
    } 
  }
  
  public void setColor(int paramInt) {
    this.mColor = paramInt;
    invalidateSelf();
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
  
  public void setRadius(float paramFloat) {
    if (!FloatUtil.floatsEqual(this.mBorderRadius, paramFloat)) {
      this.mBorderRadius = paramFloat;
      this.mNeedUpdatePathForBorderRadius = true;
      invalidateSelf();
    } 
  }
  
  public void setRadius(float paramFloat, int paramInt) {
    if (this.mBorderCornerRadii == null) {
      this.mBorderCornerRadii = new float[8];
      Arrays.fill(this.mBorderCornerRadii, 1.0E21F);
    } 
    if (!FloatUtil.floatsEqual(this.mBorderCornerRadii[paramInt], paramFloat)) {
      this.mBorderCornerRadii[paramInt] = paramFloat;
      this.mNeedUpdatePathForBorderRadius = true;
      invalidateSelf();
    } 
  }
  
  public boolean setResolvedLayoutDirection(int paramInt) {
    if (this.mLayoutDirection != paramInt) {
      this.mLayoutDirection = paramInt;
      return onResolvedLayoutDirectionChanged(paramInt);
    } 
    return false;
  }
  
  public enum BorderRadiusLocation {
    BOTTOM_END, BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM_START, TOP_END, TOP_LEFT, TOP_RIGHT, TOP_START;
    
    static {
      BOTTOM_LEFT = new BorderRadiusLocation("BOTTOM_LEFT", 3);
      TOP_START = new BorderRadiusLocation("TOP_START", 4);
      TOP_END = new BorderRadiusLocation("TOP_END", 5);
      BOTTOM_START = new BorderRadiusLocation("BOTTOM_START", 6);
      BOTTOM_END = new BorderRadiusLocation("BOTTOM_END", 7);
      $VALUES = new BorderRadiusLocation[] { TOP_LEFT, TOP_RIGHT, BOTTOM_RIGHT, BOTTOM_LEFT, TOP_START, TOP_END, BOTTOM_START, BOTTOM_END };
    }
  }
  
  enum BorderStyle {
    SOLID, DASHED, DOTTED;
    
    static {
      $VALUES = new BorderStyle[] { SOLID, DASHED, DOTTED };
    }
    
    public final PathEffect getPathEffect(float param1Float) {
      int i = ReactViewBackgroundDrawable.null.$SwitchMap$com$facebook$react$views$view$ReactViewBackgroundDrawable$BorderStyle[ordinal()];
      if (i != 1) {
        if (i != 2)
          return (PathEffect)((i != 3) ? null : new DashPathEffect(new float[] { param1Float, param1Float, param1Float, param1Float }, 0.0F)); 
        param1Float *= 3.0F;
        return (PathEffect)new DashPathEffect(new float[] { param1Float, param1Float, param1Float, param1Float }, 0.0F);
      } 
      return null;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\view\ReactViewBackgroundDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */