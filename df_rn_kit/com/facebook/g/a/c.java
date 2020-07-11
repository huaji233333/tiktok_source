package com.facebook.g.a;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.e.d;
import android.support.v4.e.e;
import android.support.v4.f.h;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;

public final class c {
  static final h<Integer, Layout> a = new h(100);
  
  public final b b = new b();
  
  public Layout c = null;
  
  public a d;
  
  public boolean e = true;
  
  public boolean f = false;
  
  public final Layout a() {
    // Byte code:
    //   0: aload_0
    //   1: getfield e : Z
    //   4: ifeq -> 21
    //   7: aload_0
    //   8: getfield c : Landroid/text/Layout;
    //   11: astore #9
    //   13: aload #9
    //   15: ifnull -> 21
    //   18: aload #9
    //   20: areturn
    //   21: aload_0
    //   22: getfield b : Lcom/facebook/g/a/c$b;
    //   25: getfield d : Ljava/lang/CharSequence;
    //   28: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   31: istore #8
    //   33: aconst_null
    //   34: astore #9
    //   36: iload #8
    //   38: ifeq -> 43
    //   41: aconst_null
    //   42: areturn
    //   43: aload_0
    //   44: getfield e : Z
    //   47: istore #8
    //   49: iconst_0
    //   50: istore_3
    //   51: iload_3
    //   52: istore #5
    //   54: iload #8
    //   56: ifeq -> 120
    //   59: iload_3
    //   60: istore #5
    //   62: aload_0
    //   63: getfield b : Lcom/facebook/g/a/c$b;
    //   66: getfield d : Ljava/lang/CharSequence;
    //   69: instanceof android/text/Spannable
    //   72: ifeq -> 120
    //   75: iload_3
    //   76: istore #5
    //   78: aload_0
    //   79: getfield b : Lcom/facebook/g/a/c$b;
    //   82: getfield d : Ljava/lang/CharSequence;
    //   85: checkcast android/text/Spannable
    //   88: iconst_0
    //   89: aload_0
    //   90: getfield b : Lcom/facebook/g/a/c$b;
    //   93: getfield d : Ljava/lang/CharSequence;
    //   96: invokeinterface length : ()I
    //   101: iconst_1
    //   102: isub
    //   103: ldc android/text/style/ClickableSpan
    //   105: invokeinterface getSpans : (IILjava/lang/Class;)[Ljava/lang/Object;
    //   110: checkcast [Landroid/text/style/ClickableSpan;
    //   113: arraylength
    //   114: ifle -> 120
    //   117: iconst_1
    //   118: istore #5
    //   120: aload_0
    //   121: getfield e : Z
    //   124: ifeq -> 168
    //   127: iload #5
    //   129: ifne -> 168
    //   132: aload_0
    //   133: getfield b : Lcom/facebook/g/a/c$b;
    //   136: invokevirtual hashCode : ()I
    //   139: istore #6
    //   141: getstatic com/facebook/g/a/c.a : Landroid/support/v4/f/h;
    //   144: iload #6
    //   146: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   149: invokevirtual a : (Ljava/lang/Object;)Ljava/lang/Object;
    //   152: checkcast android/text/Layout
    //   155: astore #10
    //   157: aload #10
    //   159: ifnull -> 165
    //   162: aload #10
    //   164: areturn
    //   165: goto -> 171
    //   168: iconst_m1
    //   169: istore #6
    //   171: aload_0
    //   172: getfield b : Lcom/facebook/g/a/c$b;
    //   175: getfield i : Z
    //   178: ifeq -> 186
    //   181: iconst_1
    //   182: istore_3
    //   183: goto -> 194
    //   186: aload_0
    //   187: getfield b : Lcom/facebook/g/a/c$b;
    //   190: getfield j : I
    //   193: istore_3
    //   194: iload_3
    //   195: iconst_1
    //   196: if_icmpne -> 218
    //   199: aload_0
    //   200: getfield b : Lcom/facebook/g/a/c$b;
    //   203: getfield d : Ljava/lang/CharSequence;
    //   206: aload_0
    //   207: getfield b : Lcom/facebook/g/a/c$b;
    //   210: getfield a : Landroid/text/TextPaint;
    //   213: invokestatic isBoring : (Ljava/lang/CharSequence;Landroid/text/TextPaint;)Landroid/text/BoringLayout$Metrics;
    //   216: astore #9
    //   218: aload_0
    //   219: getfield b : Lcom/facebook/g/a/c$b;
    //   222: getfield c : I
    //   225: istore #4
    //   227: iload #4
    //   229: ifeq -> 330
    //   232: iload #4
    //   234: iconst_1
    //   235: if_icmpeq -> 318
    //   238: iload #4
    //   240: iconst_2
    //   241: if_icmpne -> 281
    //   244: aload_0
    //   245: getfield b : Lcom/facebook/g/a/c$b;
    //   248: getfield d : Ljava/lang/CharSequence;
    //   251: aload_0
    //   252: getfield b : Lcom/facebook/g/a/c$b;
    //   255: getfield a : Landroid/text/TextPaint;
    //   258: invokestatic getDesiredWidth : (Ljava/lang/CharSequence;Landroid/text/TextPaint;)F
    //   261: f2d
    //   262: invokestatic ceil : (D)D
    //   265: d2i
    //   266: aload_0
    //   267: getfield b : Lcom/facebook/g/a/c$b;
    //   270: getfield b : I
    //   273: invokestatic min : (II)I
    //   276: istore #4
    //   278: goto -> 354
    //   281: new java/lang/StringBuilder
    //   284: dup
    //   285: ldc 'Unexpected measure mode '
    //   287: invokespecial <init> : (Ljava/lang/String;)V
    //   290: astore #9
    //   292: aload #9
    //   294: aload_0
    //   295: getfield b : Lcom/facebook/g/a/c$b;
    //   298: getfield c : I
    //   301: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   304: pop
    //   305: new java/lang/IllegalStateException
    //   308: dup
    //   309: aload #9
    //   311: invokevirtual toString : ()Ljava/lang/String;
    //   314: invokespecial <init> : (Ljava/lang/String;)V
    //   317: athrow
    //   318: aload_0
    //   319: getfield b : Lcom/facebook/g/a/c$b;
    //   322: getfield b : I
    //   325: istore #4
    //   327: goto -> 354
    //   330: aload_0
    //   331: getfield b : Lcom/facebook/g/a/c$b;
    //   334: getfield d : Ljava/lang/CharSequence;
    //   337: aload_0
    //   338: getfield b : Lcom/facebook/g/a/c$b;
    //   341: getfield a : Landroid/text/TextPaint;
    //   344: invokestatic getDesiredWidth : (Ljava/lang/CharSequence;Landroid/text/TextPaint;)F
    //   347: f2d
    //   348: invokestatic ceil : (D)D
    //   351: d2i
    //   352: istore #4
    //   354: aload #9
    //   356: ifnull -> 422
    //   359: aload_0
    //   360: getfield b : Lcom/facebook/g/a/c$b;
    //   363: getfield d : Ljava/lang/CharSequence;
    //   366: aload_0
    //   367: getfield b : Lcom/facebook/g/a/c$b;
    //   370: getfield a : Landroid/text/TextPaint;
    //   373: iload #4
    //   375: aload_0
    //   376: getfield b : Lcom/facebook/g/a/c$b;
    //   379: getfield k : Landroid/text/Layout$Alignment;
    //   382: aload_0
    //   383: getfield b : Lcom/facebook/g/a/c$b;
    //   386: getfield e : F
    //   389: aload_0
    //   390: getfield b : Lcom/facebook/g/a/c$b;
    //   393: getfield f : F
    //   396: aload #9
    //   398: aload_0
    //   399: getfield b : Lcom/facebook/g/a/c$b;
    //   402: getfield g : Z
    //   405: aload_0
    //   406: getfield b : Lcom/facebook/g/a/c$b;
    //   409: getfield h : Landroid/text/TextUtils$TruncateAt;
    //   412: iload #4
    //   414: invokestatic make : (Ljava/lang/CharSequence;Landroid/text/TextPaint;ILandroid/text/Layout$Alignment;FFLandroid/text/BoringLayout$Metrics;ZLandroid/text/TextUtils$TruncateAt;I)Landroid/text/BoringLayout;
    //   417: astore #9
    //   419: goto -> 533
    //   422: aload_0
    //   423: getfield b : Lcom/facebook/g/a/c$b;
    //   426: getfield d : Ljava/lang/CharSequence;
    //   429: astore #9
    //   431: aload_0
    //   432: getfield b : Lcom/facebook/g/a/c$b;
    //   435: getfield d : Ljava/lang/CharSequence;
    //   438: invokeinterface length : ()I
    //   443: istore #7
    //   445: aload_0
    //   446: getfield b : Lcom/facebook/g/a/c$b;
    //   449: getfield a : Landroid/text/TextPaint;
    //   452: astore #10
    //   454: aload_0
    //   455: getfield b : Lcom/facebook/g/a/c$b;
    //   458: getfield k : Landroid/text/Layout$Alignment;
    //   461: astore #11
    //   463: aload_0
    //   464: getfield b : Lcom/facebook/g/a/c$b;
    //   467: getfield e : F
    //   470: fstore_1
    //   471: aload_0
    //   472: getfield b : Lcom/facebook/g/a/c$b;
    //   475: getfield f : F
    //   478: fstore_2
    //   479: aload_0
    //   480: getfield b : Lcom/facebook/g/a/c$b;
    //   483: getfield g : Z
    //   486: istore #8
    //   488: aload_0
    //   489: getfield b : Lcom/facebook/g/a/c$b;
    //   492: getfield h : Landroid/text/TextUtils$TruncateAt;
    //   495: astore #12
    //   497: aload_0
    //   498: getfield b : Lcom/facebook/g/a/c$b;
    //   501: getfield l : Landroid/support/v4/e/d;
    //   504: astore #13
    //   506: aload #9
    //   508: iconst_0
    //   509: iload #7
    //   511: aload #10
    //   513: iload #4
    //   515: aload #11
    //   517: fload_1
    //   518: fload_2
    //   519: iload #8
    //   521: aload #12
    //   523: iload #4
    //   525: iload_3
    //   526: aload #13
    //   528: invokestatic a : (Ljava/lang/CharSequence;IILandroid/text/TextPaint;ILandroid/text/Layout$Alignment;FFZLandroid/text/TextUtils$TruncateAt;IILandroid/support/v4/e/d;)Landroid/text/StaticLayout;
    //   531: astore #9
    //   533: aload_0
    //   534: getfield e : Z
    //   537: ifeq -> 565
    //   540: iload #5
    //   542: ifne -> 565
    //   545: aload_0
    //   546: aload #9
    //   548: putfield c : Landroid/text/Layout;
    //   551: getstatic com/facebook/g/a/c.a : Landroid/support/v4/f/h;
    //   554: iload #6
    //   556: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   559: aload #9
    //   561: invokevirtual a : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   564: pop
    //   565: aload_0
    //   566: getfield b : Lcom/facebook/g/a/c$b;
    //   569: iconst_1
    //   570: putfield m : Z
    //   573: aload_0
    //   574: getfield f : Z
    //   577: ifeq -> 600
    //   580: aload_0
    //   581: getfield d : Lcom/facebook/g/a/a;
    //   584: astore #10
    //   586: aload #10
    //   588: ifnull -> 600
    //   591: aload #10
    //   593: aload #9
    //   595: invokeinterface a : (Landroid/text/Layout;)V
    //   600: aload #9
    //   602: areturn
    //   603: astore #9
    //   605: goto -> 610
    //   608: astore #9
    //   610: aload_0
    //   611: getfield b : Lcom/facebook/g/a/c$b;
    //   614: getfield d : Ljava/lang/CharSequence;
    //   617: instanceof java/lang/String
    //   620: ifne -> 647
    //   623: aload_0
    //   624: getfield b : Lcom/facebook/g/a/c$b;
    //   627: astore #9
    //   629: aload #9
    //   631: aload #9
    //   633: getfield d : Ljava/lang/CharSequence;
    //   636: invokeinterface toString : ()Ljava/lang/String;
    //   641: putfield d : Ljava/lang/CharSequence;
    //   644: goto -> 422
    //   647: goto -> 653
    //   650: aload #9
    //   652: athrow
    //   653: goto -> 650
    // Exception table:
    //   from	to	target	type
    //   422	506	608	java/lang/IndexOutOfBoundsException
    //   506	533	603	java/lang/IndexOutOfBoundsException
  }
  
  public final c a(CharSequence paramCharSequence) {
    if (paramCharSequence != this.b.d) {
      if (paramCharSequence != null && this.b.d != null && paramCharSequence.equals(this.b.d))
        return this; 
      this.b.d = paramCharSequence;
      this.c = null;
    } 
    return this;
  }
  
  static final class a extends TextPaint {
    private float a;
    
    private float b;
    
    private float c;
    
    private int d;
    
    public a() {}
    
    public a(int param1Int) {
      super(1);
    }
    
    public a(Paint param1Paint) {
      super(param1Paint);
    }
    
    public final int hashCode() {
      Typeface typeface = getTypeface();
      int k = getColor();
      int m = Float.floatToIntBits(getTextSize());
      int j = 0;
      if (typeface != null) {
        i = typeface.hashCode();
      } else {
        i = 0;
      } 
      k = (((((((k + 31) * 31 + m) * 31 + i) * 31 + Float.floatToIntBits(this.a)) * 31 + Float.floatToIntBits(this.b)) * 31 + Float.floatToIntBits(this.c)) * 31 + this.d) * 31 + this.linkColor;
      int i = k;
      if (this.drawableState == null)
        return k * 31 + 0; 
      while (j < this.drawableState.length) {
        i = i * 31 + this.drawableState[j];
        j++;
      } 
      return i;
    }
    
    public final void setShadowLayer(float param1Float1, float param1Float2, float param1Float3, int param1Int) {
      this.c = param1Float1;
      this.a = param1Float2;
      this.b = param1Float3;
      this.d = param1Int;
      super.setShadowLayer(param1Float1, param1Float2, param1Float3, param1Int);
    }
  }
  
  public static final class b {
    public TextPaint a = new c.a(1);
    
    public int b;
    
    public int c;
    
    CharSequence d;
    
    public float e = 1.0F;
    
    public float f = 0.0F;
    
    public boolean g = true;
    
    public TextUtils.TruncateAt h = null;
    
    public boolean i = false;
    
    public int j = Integer.MAX_VALUE;
    
    public Layout.Alignment k = Layout.Alignment.ALIGN_NORMAL;
    
    public d l = e.c;
    
    boolean m = false;
    
    public final void a() {
      if (this.m) {
        this.a = new c.a((Paint)this.a);
        this.m = false;
      } 
    }
    
    public final int hashCode() {
      throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\g\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */