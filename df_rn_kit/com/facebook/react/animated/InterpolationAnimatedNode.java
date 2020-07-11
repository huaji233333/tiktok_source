package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class InterpolationAnimatedNode extends ValueAnimatedNode {
  private final String mExtrapolateLeft;
  
  private final String mExtrapolateRight;
  
  private final double[] mInputRange;
  
  private final double[] mOutputRange;
  
  private ValueAnimatedNode mParent;
  
  public InterpolationAnimatedNode(ReadableMap paramReadableMap) {
    this.mInputRange = fromDoubleArray(paramReadableMap.getArray("inputRange"));
    this.mOutputRange = fromDoubleArray(paramReadableMap.getArray("outputRange"));
    this.mExtrapolateLeft = paramReadableMap.getString("extrapolateLeft");
    this.mExtrapolateRight = paramReadableMap.getString("extrapolateRight");
  }
  
  private static int findRangeIndex(double paramDouble, double[] paramArrayOfdouble) {
    int i;
    for (i = 1; i < paramArrayOfdouble.length - 1 && paramArrayOfdouble[i] < paramDouble; i++);
    return i - 1;
  }
  
  private static double[] fromDoubleArray(ReadableArray paramReadableArray) {
    double[] arrayOfDouble = new double[paramReadableArray.size()];
    for (int i = 0; i < arrayOfDouble.length; i++)
      arrayOfDouble[i] = paramReadableArray.getDouble(i); 
    return arrayOfDouble;
  }
  
  private static double interpolate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, String paramString1, String paramString2) {
    // Byte code:
    //   0: iconst_m1
    //   1: istore #15
    //   3: dload_0
    //   4: dload_2
    //   5: dcmpg
    //   6: ifge -> 158
    //   9: aload #10
    //   11: invokevirtual hashCode : ()I
    //   14: istore #14
    //   16: iload #14
    //   18: ldc -1289044198
    //   20: if_icmpeq -> 72
    //   23: iload #14
    //   25: ldc -135761730
    //   27: if_icmpeq -> 56
    //   30: iload #14
    //   32: ldc 94742715
    //   34: if_icmpeq -> 40
    //   37: goto -> 88
    //   40: aload #10
    //   42: ldc 'clamp'
    //   44: invokevirtual equals : (Ljava/lang/Object;)Z
    //   47: ifeq -> 88
    //   50: iconst_1
    //   51: istore #14
    //   53: goto -> 91
    //   56: aload #10
    //   58: ldc 'identity'
    //   60: invokevirtual equals : (Ljava/lang/Object;)Z
    //   63: ifeq -> 88
    //   66: iconst_0
    //   67: istore #14
    //   69: goto -> 91
    //   72: aload #10
    //   74: ldc 'extend'
    //   76: invokevirtual equals : (Ljava/lang/Object;)Z
    //   79: ifeq -> 88
    //   82: iconst_2
    //   83: istore #14
    //   85: goto -> 91
    //   88: iconst_m1
    //   89: istore #14
    //   91: iload #14
    //   93: ifeq -> 156
    //   96: iload #14
    //   98: iconst_1
    //   99: if_icmpeq -> 151
    //   102: iload #14
    //   104: iconst_2
    //   105: if_icmpne -> 111
    //   108: goto -> 158
    //   111: new java/lang/StringBuilder
    //   114: dup
    //   115: ldc 'Invalid extrapolation type '
    //   117: invokespecial <init> : (Ljava/lang/String;)V
    //   120: astore #11
    //   122: aload #11
    //   124: aload #10
    //   126: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   129: pop
    //   130: aload #11
    //   132: ldc 'for left extrapolation'
    //   134: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   137: pop
    //   138: new com/facebook/react/bridge/JSApplicationIllegalArgumentException
    //   141: dup
    //   142: aload #11
    //   144: invokevirtual toString : ()Ljava/lang/String;
    //   147: invokespecial <init> : (Ljava/lang/String;)V
    //   150: athrow
    //   151: dload_2
    //   152: dstore_0
    //   153: goto -> 158
    //   156: dload_0
    //   157: dreturn
    //   158: dload_0
    //   159: dstore #12
    //   161: dload_0
    //   162: dload #4
    //   164: dcmpl
    //   165: ifle -> 332
    //   168: aload #11
    //   170: invokevirtual hashCode : ()I
    //   173: istore #14
    //   175: iload #14
    //   177: ldc -1289044198
    //   179: if_icmpeq -> 243
    //   182: iload #14
    //   184: ldc -135761730
    //   186: if_icmpeq -> 223
    //   189: iload #14
    //   191: ldc 94742715
    //   193: if_icmpeq -> 203
    //   196: iload #15
    //   198: istore #14
    //   200: goto -> 260
    //   203: iload #15
    //   205: istore #14
    //   207: aload #11
    //   209: ldc 'clamp'
    //   211: invokevirtual equals : (Ljava/lang/Object;)Z
    //   214: ifeq -> 260
    //   217: iconst_1
    //   218: istore #14
    //   220: goto -> 260
    //   223: iload #15
    //   225: istore #14
    //   227: aload #11
    //   229: ldc 'identity'
    //   231: invokevirtual equals : (Ljava/lang/Object;)Z
    //   234: ifeq -> 260
    //   237: iconst_0
    //   238: istore #14
    //   240: goto -> 260
    //   243: iload #15
    //   245: istore #14
    //   247: aload #11
    //   249: ldc 'extend'
    //   251: invokevirtual equals : (Ljava/lang/Object;)Z
    //   254: ifeq -> 260
    //   257: iconst_2
    //   258: istore #14
    //   260: iload #14
    //   262: ifeq -> 330
    //   265: iload #14
    //   267: iconst_1
    //   268: if_icmpeq -> 323
    //   271: iload #14
    //   273: iconst_2
    //   274: if_icmpne -> 283
    //   277: dload_0
    //   278: dstore #12
    //   280: goto -> 332
    //   283: new java/lang/StringBuilder
    //   286: dup
    //   287: ldc 'Invalid extrapolation type '
    //   289: invokespecial <init> : (Ljava/lang/String;)V
    //   292: astore #10
    //   294: aload #10
    //   296: aload #11
    //   298: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   301: pop
    //   302: aload #10
    //   304: ldc 'for right extrapolation'
    //   306: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   309: pop
    //   310: new com/facebook/react/bridge/JSApplicationIllegalArgumentException
    //   313: dup
    //   314: aload #10
    //   316: invokevirtual toString : ()Ljava/lang/String;
    //   319: invokespecial <init> : (Ljava/lang/String;)V
    //   322: athrow
    //   323: dload #4
    //   325: dstore #12
    //   327: goto -> 332
    //   330: dload_0
    //   331: dreturn
    //   332: dload #6
    //   334: dload #8
    //   336: dload #6
    //   338: dsub
    //   339: dload #12
    //   341: dload_2
    //   342: dsub
    //   343: dmul
    //   344: dload #4
    //   346: dload_2
    //   347: dsub
    //   348: ddiv
    //   349: dadd
    //   350: dreturn
  }
  
  static double interpolate(double paramDouble, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, String paramString1, String paramString2) {
    int i = findRangeIndex(paramDouble, paramArrayOfdouble1);
    double d = paramArrayOfdouble1[i];
    int j = i + 1;
    return interpolate(paramDouble, d, paramArrayOfdouble1[j], paramArrayOfdouble2[i], paramArrayOfdouble2[j], paramString1, paramString2);
  }
  
  public void onAttachedToNode(AnimatedNode paramAnimatedNode) {
    if (this.mParent == null) {
      if (paramAnimatedNode instanceof ValueAnimatedNode) {
        this.mParent = (ValueAnimatedNode)paramAnimatedNode;
        return;
      } 
      throw new IllegalArgumentException("Parent is of an invalid type");
    } 
    throw new IllegalStateException("Parent already attached");
  }
  
  public void onDetachedFromNode(AnimatedNode paramAnimatedNode) {
    if (paramAnimatedNode == this.mParent) {
      this.mParent = null;
      return;
    } 
    throw new IllegalArgumentException("Invalid parent node provided");
  }
  
  public void update() {
    ValueAnimatedNode valueAnimatedNode = this.mParent;
    if (valueAnimatedNode == null)
      return; 
    this.mValue = interpolate(valueAnimatedNode.getValue(), this.mInputRange, this.mOutputRange, this.mExtrapolateLeft, this.mExtrapolateRight);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\InterpolationAnimatedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */