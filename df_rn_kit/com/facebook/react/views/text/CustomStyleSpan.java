package com.facebook.react.views.text;

import android.content.res.AssetManager;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class CustomStyleSpan extends MetricAffectingSpan {
  private final AssetManager mAssetManager;
  
  private final String mFontFamily;
  
  private final int mStyle;
  
  private final int mWeight;
  
  public CustomStyleSpan(int paramInt1, int paramInt2, String paramString, AssetManager paramAssetManager) {
    this.mStyle = paramInt1;
    this.mWeight = paramInt2;
    this.mFontFamily = paramString;
    this.mAssetManager = paramAssetManager;
  }
  
  private static void apply(Paint paramPaint, int paramInt1, int paramInt2, String paramString, AssetManager paramAssetManager) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getTypeface : ()Landroid/graphics/Typeface;
    //   4: astore #8
    //   6: iconst_0
    //   7: istore #7
    //   9: aload #8
    //   11: ifnonnull -> 20
    //   14: iconst_0
    //   15: istore #6
    //   17: goto -> 27
    //   20: aload #8
    //   22: invokevirtual getStyle : ()I
    //   25: istore #6
    //   27: iload_2
    //   28: iconst_1
    //   29: if_icmpeq -> 52
    //   32: iload #7
    //   34: istore #5
    //   36: iload #6
    //   38: iconst_1
    //   39: iand
    //   40: ifeq -> 55
    //   43: iload #7
    //   45: istore #5
    //   47: iload_2
    //   48: iconst_m1
    //   49: if_icmpne -> 55
    //   52: iconst_1
    //   53: istore #5
    //   55: iload_1
    //   56: iconst_2
    //   57: if_icmpeq -> 78
    //   60: iload #5
    //   62: istore_2
    //   63: iconst_2
    //   64: iload #6
    //   66: iand
    //   67: ifeq -> 83
    //   70: iload #5
    //   72: istore_2
    //   73: iload_1
    //   74: iconst_m1
    //   75: if_icmpne -> 83
    //   78: iload #5
    //   80: iconst_2
    //   81: ior
    //   82: istore_2
    //   83: aload_3
    //   84: ifnull -> 101
    //   87: invokestatic getInstance : ()Lcom/facebook/react/views/text/ReactFontManager;
    //   90: aload_3
    //   91: iload_2
    //   92: aload #4
    //   94: invokevirtual getTypeface : (Ljava/lang/String;ILandroid/content/res/AssetManager;)Landroid/graphics/Typeface;
    //   97: astore_3
    //   98: goto -> 116
    //   101: aload #8
    //   103: astore_3
    //   104: aload #8
    //   106: ifnull -> 116
    //   109: aload #8
    //   111: iload_2
    //   112: invokestatic create : (Landroid/graphics/Typeface;I)Landroid/graphics/Typeface;
    //   115: astore_3
    //   116: aload_3
    //   117: ifnull -> 127
    //   120: aload_0
    //   121: aload_3
    //   122: invokevirtual setTypeface : (Landroid/graphics/Typeface;)Landroid/graphics/Typeface;
    //   125: pop
    //   126: return
    //   127: aload_0
    //   128: iload_2
    //   129: invokestatic defaultFromStyle : (I)Landroid/graphics/Typeface;
    //   132: invokevirtual setTypeface : (Landroid/graphics/Typeface;)Landroid/graphics/Typeface;
    //   135: pop
    //   136: return
  }
  
  public String getFontFamily() {
    return this.mFontFamily;
  }
  
  public int getStyle() {
    int j = this.mStyle;
    int i = j;
    if (j == -1)
      i = 0; 
    return i;
  }
  
  public int getWeight() {
    int j = this.mWeight;
    int i = j;
    if (j == -1)
      i = 0; 
    return i;
  }
  
  public void updateDrawState(TextPaint paramTextPaint) {
    apply((Paint)paramTextPaint, this.mStyle, this.mWeight, this.mFontFamily, this.mAssetManager);
  }
  
  public void updateMeasureState(TextPaint paramTextPaint) {
    apply((Paint)paramTextPaint, this.mStyle, this.mWeight, this.mFontFamily, this.mAssetManager);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\CustomStyleSpan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */