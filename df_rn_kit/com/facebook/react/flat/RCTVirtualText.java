package com.facebook.react.flat;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;

class RCTVirtualText extends FlatTextShadowNode {
  private FontStylingSpan mFontStylingSpan = FontStylingSpan.INSTANCE;
  
  private ShadowStyleSpan mShadowStyleSpan = ShadowStyleSpan.INSTANCE;
  
  static int fontSizeFromSp(float paramFloat) {
    return (int)Math.ceil(PixelUtil.toPixelFromSP(paramFloat));
  }
  
  private final ShadowStyleSpan getShadowSpan() {
    if (this.mShadowStyleSpan.isFrozen())
      this.mShadowStyleSpan = this.mShadowStyleSpan.mutableCopy(); 
    return this.mShadowStyleSpan;
  }
  
  private static int parseNumericFontWeight(String paramString) {
    return (paramString.length() == 3 && paramString.endsWith("00") && paramString.charAt(0) <= '9' && paramString.charAt(0) >= '1') ? ((paramString.charAt(0) - 48) * 100) : -1;
  }
  
  public void addChildAt(ReactShadowNodeImpl paramReactShadowNodeImpl, int paramInt) {
    super.addChildAt(paramReactShadowNodeImpl, paramInt);
    notifyChanged(true);
  }
  
  protected int getDefaultFontSize() {
    return -1;
  }
  
  protected final int getFontSize() {
    return this.mFontStylingSpan.getFontSize();
  }
  
  protected final int getFontStyle() {
    int i = this.mFontStylingSpan.getFontStyle();
    return (i >= 0) ? i : 0;
  }
  
  protected final FontStylingSpan getSpan() {
    if (this.mFontStylingSpan.isFrozen())
      this.mFontStylingSpan = this.mFontStylingSpan.mutableCopy(); 
    return this.mFontStylingSpan;
  }
  
  final SpannableStringBuilder getText() {
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    collectText(spannableStringBuilder);
    applySpans(spannableStringBuilder, isEditable());
    return spannableStringBuilder;
  }
  
  protected void performApplySpans(SpannableStringBuilder paramSpannableStringBuilder, int paramInt1, int paramInt2, boolean paramBoolean) {
    byte b;
    this.mFontStylingSpan.freeze();
    if (paramBoolean) {
      b = 33;
    } else if (paramInt1 == 0) {
      b = 18;
    } else {
      b = 34;
    } 
    paramSpannableStringBuilder.setSpan(this.mFontStylingSpan, paramInt1, paramInt2, b);
    if (this.mShadowStyleSpan.getColor() != 0 && this.mShadowStyleSpan.getRadius() != 0.0F) {
      this.mShadowStyleSpan.freeze();
      paramSpannableStringBuilder.setSpan(this.mShadowStyleSpan, paramInt1, paramInt2, b);
    } 
    paramInt1 = 0;
    paramInt2 = getChildCount();
    while (paramInt1 < paramInt2) {
      ((FlatTextShadowNode)getChildAt(paramInt1)).applySpans(paramSpannableStringBuilder, paramBoolean);
      paramInt1++;
    } 
  }
  
  protected void performCollectAttachDetachListeners(StateBuilder paramStateBuilder) {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
      ((FlatTextShadowNode)getChildAt(i)).performCollectAttachDetachListeners(paramStateBuilder); 
  }
  
  protected void performCollectText(SpannableStringBuilder paramSpannableStringBuilder) {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
      ((FlatTextShadowNode)getChildAt(i)).collectText(paramSpannableStringBuilder); 
  }
  
  public void setBackgroundColor(int paramInt) {
    if (isVirtual()) {
      if (this.mFontStylingSpan.getBackgroundColor() != paramInt) {
        getSpan().setBackgroundColor(paramInt);
        notifyChanged(false);
        return;
      } 
    } else {
      super.setBackgroundColor(paramInt);
    } 
  }
  
  @ReactProp(defaultDouble = NaND, name = "color")
  public void setColor(double paramDouble) {
    if (this.mFontStylingSpan.getTextColor() != paramDouble) {
      getSpan().setTextColor(paramDouble);
      notifyChanged(false);
    } 
  }
  
  @ReactProp(name = "fontFamily")
  public void setFontFamily(String paramString) {
    if (!TextUtils.equals(this.mFontStylingSpan.getFontFamily(), paramString)) {
      getSpan().setFontFamily(paramString);
      notifyChanged(true);
    } 
  }
  
  @ReactProp(defaultFloat = NaNF, name = "fontSize")
  public void setFontSize(float paramFloat) {
    int i;
    if (Float.isNaN(paramFloat)) {
      i = getDefaultFontSize();
    } else {
      i = fontSizeFromSp(paramFloat);
    } 
    if (this.mFontStylingSpan.getFontSize() != i) {
      getSpan().setFontSize(i);
      notifyChanged(true);
    } 
  }
  
  @ReactProp(name = "fontStyle")
  public void setFontStyle(String paramString) {
    boolean bool;
    if (paramString == null) {
      bool = true;
    } else if ("italic".equals(paramString)) {
      bool = true;
    } else if ("normal".equals(paramString)) {
      bool = false;
    } else {
      StringBuilder stringBuilder = new StringBuilder("invalid font style ");
      stringBuilder.append(paramString);
      throw new RuntimeException(stringBuilder.toString());
    } 
    if (this.mFontStylingSpan.getFontStyle() != bool) {
      getSpan().setFontStyle(bool);
      notifyChanged(true);
    } 
  }
  
  @ReactProp(name = "fontWeight")
  public void setFontWeight(String paramString) {
    // Byte code:
    //   0: iconst_m1
    //   1: istore_2
    //   2: aload_1
    //   3: ifnonnull -> 9
    //   6: goto -> 82
    //   9: ldc 'bold'
    //   11: aload_1
    //   12: invokevirtual equals : (Ljava/lang/Object;)Z
    //   15: ifeq -> 23
    //   18: iconst_1
    //   19: istore_2
    //   20: goto -> 82
    //   23: ldc 'normal'
    //   25: aload_1
    //   26: invokevirtual equals : (Ljava/lang/Object;)Z
    //   29: ifne -> 80
    //   32: aload_1
    //   33: invokestatic parseNumericFontWeight : (Ljava/lang/String;)I
    //   36: istore_2
    //   37: iload_2
    //   38: iconst_m1
    //   39: if_icmpeq -> 52
    //   42: iload_2
    //   43: sipush #500
    //   46: if_icmplt -> 80
    //   49: goto -> 18
    //   52: new java/lang/StringBuilder
    //   55: dup
    //   56: ldc 'invalid font weight '
    //   58: invokespecial <init> : (Ljava/lang/String;)V
    //   61: astore_3
    //   62: aload_3
    //   63: aload_1
    //   64: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   67: pop
    //   68: new java/lang/RuntimeException
    //   71: dup
    //   72: aload_3
    //   73: invokevirtual toString : ()Ljava/lang/String;
    //   76: invokespecial <init> : (Ljava/lang/String;)V
    //   79: athrow
    //   80: iconst_0
    //   81: istore_2
    //   82: aload_0
    //   83: getfield mFontStylingSpan : Lcom/facebook/react/flat/FontStylingSpan;
    //   86: invokevirtual getFontWeight : ()I
    //   89: iload_2
    //   90: if_icmpeq -> 106
    //   93: aload_0
    //   94: invokevirtual getSpan : ()Lcom/facebook/react/flat/FontStylingSpan;
    //   97: iload_2
    //   98: invokevirtual setFontWeight : (I)V
    //   101: aload_0
    //   102: iconst_1
    //   103: invokevirtual notifyChanged : (Z)V
    //   106: return
  }
  
  @ReactProp(name = "textDecorationLine")
  public void setTextDecorationLine(String paramString) {
    boolean bool1;
    boolean bool2 = false;
    int i = 0;
    if (paramString != null) {
      String[] arrayOfString = paramString.split(" ");
      int j = arrayOfString.length;
      bool2 = false;
      bool1 = false;
      while (i < j) {
        boolean bool;
        String str = arrayOfString[i];
        if ("underline".equals(str)) {
          bool = true;
        } else {
          bool = bool2;
          if ("line-through".equals(str)) {
            bool1 = true;
            bool = bool2;
          } 
        } 
        i++;
        bool2 = bool;
      } 
    } else {
      bool1 = false;
    } 
    if (bool2 != this.mFontStylingSpan.hasUnderline() || bool1 != this.mFontStylingSpan.hasStrikeThrough()) {
      FontStylingSpan fontStylingSpan = getSpan();
      fontStylingSpan.setHasUnderline(bool2);
      fontStylingSpan.setHasStrikeThrough(bool1);
      notifyChanged(true);
    } 
  }
  
  @ReactProp(customType = "Color", defaultInt = 1426063360, name = "textShadowColor")
  public void setTextShadowColor(int paramInt) {
    if (this.mShadowStyleSpan.getColor() != paramInt) {
      getShadowSpan().setColor(paramInt);
      notifyChanged(false);
    } 
  }
  
  @ReactProp(name = "textShadowOffset")
  public void setTextShadowOffset(ReadableMap paramReadableMap) {
    float f1;
    float f2 = 0.0F;
    if (paramReadableMap != null) {
      float f;
      if (paramReadableMap.hasKey("width")) {
        f = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("width"));
      } else {
        f = 0.0F;
      } 
      f1 = f;
      if (paramReadableMap.hasKey("height")) {
        f2 = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("height"));
        f1 = f;
      } 
    } else {
      f1 = 0.0F;
    } 
    if (!this.mShadowStyleSpan.offsetMatches(f1, f2)) {
      getShadowSpan().setOffset(f1, f2);
      notifyChanged(false);
    } 
  }
  
  @ReactProp(name = "textShadowRadius")
  public void setTextShadowRadius(float paramFloat) {
    paramFloat = PixelUtil.toPixelFromDIP(paramFloat);
    if (this.mShadowStyleSpan.getRadius() != paramFloat) {
      getShadowSpan().setRadius(paramFloat);
      notifyChanged(false);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTVirtualText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */