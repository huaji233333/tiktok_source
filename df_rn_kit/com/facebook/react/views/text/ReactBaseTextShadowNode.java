package com.facebook.react.views.text;

import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaDirection;
import java.util.ArrayList;
import java.util.List;

public abstract class ReactBaseTextShadowNode extends LayoutShadowNode {
  protected boolean mAllowFontScaling;
  
  protected int mBackgroundColor;
  
  protected int mColor;
  
  protected boolean mContainsImages;
  
  protected String mFontFamily;
  
  protected int mFontSize;
  
  protected float mFontSizeInput;
  
  protected int mFontStyle;
  
  protected int mFontWeight;
  
  protected float mHeightOfTallestInlineImage;
  
  protected boolean mIncludeFontPadding;
  
  protected boolean mIsBackgroundColorSet;
  
  protected boolean mIsColorSet;
  
  protected boolean mIsLineThroughTextDecorationSet;
  
  protected boolean mIsUnderlineTextDecorationSet;
  
  protected float mLetterSpacing;
  
  protected float mLetterSpacingInput;
  
  protected float mLineHeight;
  
  protected float mLineHeightInput;
  
  protected int mNumberOfLines;
  
  protected int mTextAlign;
  
  protected int mTextBreakStrategy;
  
  protected int mTextShadowColor;
  
  protected float mTextShadowOffsetDx;
  
  protected float mTextShadowOffsetDy;
  
  protected float mTextShadowRadius;
  
  public ReactBaseTextShadowNode() {
    boolean bool;
    this.mLineHeight = Float.NaN;
    this.mLetterSpacing = Float.NaN;
    this.mAllowFontScaling = true;
    this.mNumberOfLines = -1;
    this.mFontSize = -1;
    this.mFontSizeInput = -1.0F;
    this.mLineHeightInput = -1.0F;
    this.mLetterSpacingInput = Float.NaN;
    if (Build.VERSION.SDK_INT < 23) {
      bool = false;
    } else {
      bool = true;
    } 
    this.mTextBreakStrategy = bool;
    this.mTextShadowOffsetDx = 0.0F;
    this.mTextShadowOffsetDy = 0.0F;
    this.mTextShadowRadius = 1.0F;
    this.mTextShadowColor = 1426063360;
    this.mIsUnderlineTextDecorationSet = false;
    this.mIsLineThroughTextDecorationSet = false;
    this.mIncludeFontPadding = true;
    this.mFontStyle = -1;
    this.mFontWeight = -1;
    this.mFontFamily = null;
    this.mContainsImages = false;
    this.mHeightOfTallestInlineImage = Float.NaN;
  }
  
  public ReactBaseTextShadowNode(ReactBaseTextShadowNode paramReactBaseTextShadowNode) {
    super(paramReactBaseTextShadowNode);
    boolean bool;
    this.mLineHeight = Float.NaN;
    this.mLetterSpacing = Float.NaN;
    this.mAllowFontScaling = true;
    this.mNumberOfLines = -1;
    this.mFontSize = -1;
    this.mFontSizeInput = -1.0F;
    this.mLineHeightInput = -1.0F;
    this.mLetterSpacingInput = Float.NaN;
    if (Build.VERSION.SDK_INT < 23) {
      bool = false;
    } else {
      bool = true;
    } 
    this.mTextBreakStrategy = bool;
    this.mTextShadowOffsetDx = 0.0F;
    this.mTextShadowOffsetDy = 0.0F;
    this.mTextShadowRadius = 1.0F;
    this.mTextShadowColor = 1426063360;
    this.mIsUnderlineTextDecorationSet = false;
    this.mIsLineThroughTextDecorationSet = false;
    this.mIncludeFontPadding = true;
    this.mFontStyle = -1;
    this.mFontWeight = -1;
    this.mFontFamily = null;
    this.mContainsImages = false;
    this.mHeightOfTallestInlineImage = Float.NaN;
    this.mLineHeight = paramReactBaseTextShadowNode.mLineHeight;
    this.mIsColorSet = paramReactBaseTextShadowNode.mIsColorSet;
    this.mAllowFontScaling = paramReactBaseTextShadowNode.mAllowFontScaling;
    this.mColor = paramReactBaseTextShadowNode.mColor;
    this.mIsBackgroundColorSet = paramReactBaseTextShadowNode.mIsBackgroundColorSet;
    this.mBackgroundColor = paramReactBaseTextShadowNode.mBackgroundColor;
    this.mNumberOfLines = paramReactBaseTextShadowNode.mNumberOfLines;
    this.mFontSize = paramReactBaseTextShadowNode.mFontSize;
    this.mFontSizeInput = paramReactBaseTextShadowNode.mFontSizeInput;
    this.mLineHeightInput = paramReactBaseTextShadowNode.mLineHeightInput;
    this.mTextAlign = paramReactBaseTextShadowNode.mTextAlign;
    this.mTextBreakStrategy = paramReactBaseTextShadowNode.mTextBreakStrategy;
    this.mTextShadowOffsetDx = paramReactBaseTextShadowNode.mTextShadowOffsetDx;
    this.mTextShadowOffsetDy = paramReactBaseTextShadowNode.mTextShadowOffsetDy;
    this.mTextShadowRadius = paramReactBaseTextShadowNode.mTextShadowRadius;
    this.mTextShadowColor = paramReactBaseTextShadowNode.mTextShadowColor;
    this.mIsUnderlineTextDecorationSet = paramReactBaseTextShadowNode.mIsUnderlineTextDecorationSet;
    this.mIsLineThroughTextDecorationSet = paramReactBaseTextShadowNode.mIsLineThroughTextDecorationSet;
    this.mIncludeFontPadding = paramReactBaseTextShadowNode.mIncludeFontPadding;
    this.mFontStyle = paramReactBaseTextShadowNode.mFontStyle;
    this.mFontWeight = paramReactBaseTextShadowNode.mFontWeight;
    this.mFontFamily = paramReactBaseTextShadowNode.mFontFamily;
    this.mContainsImages = paramReactBaseTextShadowNode.mContainsImages;
    this.mHeightOfTallestInlineImage = paramReactBaseTextShadowNode.mHeightOfTallestInlineImage;
  }
  
  private static void buildSpannedFromShadowNode(ReactBaseTextShadowNode paramReactBaseTextShadowNode, SpannableStringBuilder paramSpannableStringBuilder, List<SetSpanOperation> paramList) {
    StringBuilder stringBuilder;
    int j = paramSpannableStringBuilder.length();
    int k = paramReactBaseTextShadowNode.getChildCount();
    int i;
    for (i = 0; i < k; i++) {
      ReactShadowNodeImpl reactShadowNodeImpl = paramReactBaseTextShadowNode.getChildAt(i);
      if (reactShadowNodeImpl instanceof ReactRawTextShadowNode) {
        ReactRawTextShadowNode reactRawTextShadowNode = (ReactRawTextShadowNode)reactShadowNodeImpl;
        if (reactRawTextShadowNode.getText() != null)
          paramSpannableStringBuilder.append(reactRawTextShadowNode.getText()); 
      } else if (reactShadowNodeImpl instanceof ReactBaseTextShadowNode) {
        buildSpannedFromShadowNode((ReactBaseTextShadowNode)reactShadowNodeImpl, paramSpannableStringBuilder, paramList);
      } else if (reactShadowNodeImpl instanceof ReactTextInlineImageShadowNode) {
        paramSpannableStringBuilder.append("I");
        paramList.add(new SetSpanOperation(paramSpannableStringBuilder.length() - 1, paramSpannableStringBuilder.length(), ((ReactTextInlineImageShadowNode)reactShadowNodeImpl).buildInlineImageSpan()));
      } else {
        stringBuilder = new StringBuilder("Unexpected view type nested under text node: ");
        stringBuilder.append(reactShadowNodeImpl.getClass());
        throw new IllegalViewOperationException(stringBuilder.toString());
      } 
      reactShadowNodeImpl.markUpdateSeen();
    } 
    i = paramSpannableStringBuilder.length();
    if (i >= j) {
      if (((ReactBaseTextShadowNode)stringBuilder).mIsColorSet)
        paramList.add(new SetSpanOperation(j, i, new ForegroundColorSpan(((ReactBaseTextShadowNode)stringBuilder).mColor))); 
      if (((ReactBaseTextShadowNode)stringBuilder).mIsBackgroundColorSet)
        paramList.add(new SetSpanOperation(j, i, new BackgroundColorSpan(((ReactBaseTextShadowNode)stringBuilder).mBackgroundColor))); 
      if (Build.VERSION.SDK_INT >= 21) {
        float f = ((ReactBaseTextShadowNode)stringBuilder).mLetterSpacing;
        if (f != Float.NaN)
          paramList.add(new SetSpanOperation(j, i, new CustomLetterSpacingSpan(f))); 
      } 
      k = ((ReactBaseTextShadowNode)stringBuilder).mFontSize;
      if (k != -1)
        paramList.add(new SetSpanOperation(j, i, new AbsoluteSizeSpan(k))); 
      if (((ReactBaseTextShadowNode)stringBuilder).mFontStyle != -1 || ((ReactBaseTextShadowNode)stringBuilder).mFontWeight != -1 || ((ReactBaseTextShadowNode)stringBuilder).mFontFamily != null)
        paramList.add(new SetSpanOperation(j, i, new CustomStyleSpan(((ReactBaseTextShadowNode)stringBuilder).mFontStyle, ((ReactBaseTextShadowNode)stringBuilder).mFontWeight, ((ReactBaseTextShadowNode)stringBuilder).mFontFamily, stringBuilder.getThemedContext().getAssets()))); 
      if (((ReactBaseTextShadowNode)stringBuilder).mIsUnderlineTextDecorationSet)
        paramList.add(new SetSpanOperation(j, i, new UnderlineSpan())); 
      if (((ReactBaseTextShadowNode)stringBuilder).mIsLineThroughTextDecorationSet)
        paramList.add(new SetSpanOperation(j, i, new StrikethroughSpan())); 
      if (((ReactBaseTextShadowNode)stringBuilder).mTextShadowOffsetDx != 0.0F || ((ReactBaseTextShadowNode)stringBuilder).mTextShadowOffsetDy != 0.0F)
        paramList.add(new SetSpanOperation(j, i, new ShadowStyleSpan(((ReactBaseTextShadowNode)stringBuilder).mTextShadowOffsetDx, ((ReactBaseTextShadowNode)stringBuilder).mTextShadowOffsetDy, ((ReactBaseTextShadowNode)stringBuilder).mTextShadowRadius, ((ReactBaseTextShadowNode)stringBuilder).mTextShadowColor))); 
      if (!Float.isNaN(stringBuilder.getEffectiveLineHeight()))
        paramList.add(new SetSpanOperation(j, i, new CustomLineHeightSpan(stringBuilder.getEffectiveLineHeight()))); 
      paramList.add(new SetSpanOperation(j, i, new ReactTagSpan(stringBuilder.getReactTag())));
    } 
  }
  
  private int getTextAlign() {
    int j = this.mTextAlign;
    int i = j;
    if (getLayoutDirection() == YogaDirection.RTL) {
      if (j == 5)
        return 3; 
      i = j;
      if (j == 3)
        i = 5; 
    } 
    return i;
  }
  
  private static int parseNumericFontWeight(String paramString) {
    return (paramString.length() == 3 && paramString.endsWith("00") && paramString.charAt(0) <= '9' && paramString.charAt(0) >= '1') ? ((paramString.charAt(0) - 48) * 100) : -1;
  }
  
  protected static Spannable spannedFromShadowNode(ReactBaseTextShadowNode paramReactBaseTextShadowNode, String paramString) {
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    ArrayList<SetSpanOperation> arrayList = new ArrayList();
    buildSpannedFromShadowNode(paramReactBaseTextShadowNode, spannableStringBuilder, arrayList);
    if (paramString != null)
      spannableStringBuilder.append(paramString); 
    int j = paramReactBaseTextShadowNode.mFontSize;
    int i = 0;
    if (j == -1) {
      double d;
      if (paramReactBaseTextShadowNode.mAllowFontScaling) {
        d = Math.ceil(PixelUtil.toPixelFromSP(14.0F));
      } else {
        d = Math.ceil(PixelUtil.toPixelFromDIP(14.0F));
      } 
      j = (int)d;
      arrayList.add(new SetSpanOperation(0, spannableStringBuilder.length(), new AbsoluteSizeSpan(j)));
    } 
    paramReactBaseTextShadowNode.mContainsImages = false;
    paramReactBaseTextShadowNode.mHeightOfTallestInlineImage = Float.NaN;
    for (SetSpanOperation setSpanOperation : arrayList) {
      if (setSpanOperation.what instanceof TextInlineImageSpan) {
        j = ((TextInlineImageSpan)setSpanOperation.what).getHeight();
        paramReactBaseTextShadowNode.mContainsImages = true;
        if (Float.isNaN(paramReactBaseTextShadowNode.mHeightOfTallestInlineImage) || j > paramReactBaseTextShadowNode.mHeightOfTallestInlineImage)
          paramReactBaseTextShadowNode.mHeightOfTallestInlineImage = j; 
      } 
      setSpanOperation.execute(spannableStringBuilder, i);
      i++;
    } 
    return (Spannable)spannableStringBuilder;
  }
  
  public float getEffectiveLineHeight() {
    boolean bool;
    if (!Float.isNaN(this.mLineHeight) && !Float.isNaN(this.mHeightOfTallestInlineImage) && this.mHeightOfTallestInlineImage > this.mLineHeight) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool ? this.mHeightOfTallestInlineImage : this.mLineHeight;
  }
  
  @ReactProp(defaultBoolean = true, name = "allowFontScaling")
  public void setAllowFontScaling(boolean paramBoolean) {
    if (paramBoolean != this.mAllowFontScaling) {
      this.mAllowFontScaling = paramBoolean;
      setFontSize(this.mFontSizeInput);
      setLineHeight(this.mLineHeightInput);
      setLetterSpacing(this.mLetterSpacingInput);
      markUpdated();
    } 
  }
  
  @ReactProp(name = "backgroundColor")
  public void setBackgroundColor(Integer paramInteger) {
    if (!isVirtualAnchor()) {
      boolean bool;
      if (paramInteger != null) {
        bool = true;
      } else {
        bool = false;
      } 
      this.mIsBackgroundColorSet = bool;
      if (this.mIsBackgroundColorSet)
        this.mBackgroundColor = paramInteger.intValue(); 
      markUpdated();
    } 
  }
  
  @ReactProp(name = "color")
  public void setColor(Integer paramInteger) {
    boolean bool;
    if (paramInteger != null) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mIsColorSet = bool;
    if (this.mIsColorSet)
      this.mColor = paramInteger.intValue(); 
    markUpdated();
  }
  
  @ReactProp(name = "fontFamily")
  public void setFontFamily(String paramString) {
    this.mFontFamily = paramString;
    markUpdated();
  }
  
  @ReactProp(defaultFloat = -1.0F, name = "fontSize")
  public void setFontSize(float paramFloat) {
    this.mFontSizeInput = paramFloat;
    float f = paramFloat;
    if (paramFloat != -1.0F) {
      double d;
      if (this.mAllowFontScaling) {
        d = Math.ceil(PixelUtil.toPixelFromSP(paramFloat));
      } else {
        d = Math.ceil(PixelUtil.toPixelFromDIP(paramFloat));
      } 
      f = (float)d;
    } 
    this.mFontSize = (int)f;
    markUpdated();
  }
  
  @ReactProp(name = "fontStyle")
  public void setFontStyle(String paramString) {
    byte b;
    if ("italic".equals(paramString)) {
      b = 2;
    } else if ("normal".equals(paramString)) {
      b = 0;
    } else {
      b = -1;
    } 
    if (b != this.mFontStyle) {
      this.mFontStyle = b;
      markUpdated();
    } 
  }
  
  @ReactProp(name = "fontWeight")
  public void setFontWeight(String paramString) {
    // Byte code:
    //   0: iconst_m1
    //   1: istore #4
    //   3: aload_1
    //   4: ifnull -> 15
    //   7: aload_1
    //   8: invokestatic parseNumericFontWeight : (Ljava/lang/String;)I
    //   11: istore_3
    //   12: goto -> 17
    //   15: iconst_m1
    //   16: istore_3
    //   17: iload_3
    //   18: sipush #500
    //   21: if_icmpge -> 70
    //   24: ldc_w 'bold'
    //   27: aload_1
    //   28: invokevirtual equals : (Ljava/lang/Object;)Z
    //   31: ifeq -> 37
    //   34: goto -> 70
    //   37: ldc_w 'normal'
    //   40: aload_1
    //   41: invokevirtual equals : (Ljava/lang/Object;)Z
    //   44: ifne -> 65
    //   47: iload #4
    //   49: istore_2
    //   50: iload_3
    //   51: iconst_m1
    //   52: if_icmpeq -> 72
    //   55: iload #4
    //   57: istore_2
    //   58: iload_3
    //   59: sipush #500
    //   62: if_icmpge -> 72
    //   65: iconst_0
    //   66: istore_2
    //   67: goto -> 72
    //   70: iconst_1
    //   71: istore_2
    //   72: iload_2
    //   73: aload_0
    //   74: getfield mFontWeight : I
    //   77: if_icmpeq -> 89
    //   80: aload_0
    //   81: iload_2
    //   82: putfield mFontWeight : I
    //   85: aload_0
    //   86: invokevirtual markUpdated : ()V
    //   89: return
  }
  
  @ReactProp(defaultBoolean = true, name = "includeFontPadding")
  public void setIncludeFontPadding(boolean paramBoolean) {
    this.mIncludeFontPadding = paramBoolean;
  }
  
  @ReactProp(defaultFloat = NaNF, name = "letterSpacing")
  public void setLetterSpacing(float paramFloat) {
    this.mLetterSpacingInput = paramFloat;
    if (this.mAllowFontScaling) {
      paramFloat = PixelUtil.toPixelFromSP(this.mLetterSpacingInput);
    } else {
      paramFloat = PixelUtil.toPixelFromDIP(this.mLetterSpacingInput);
    } 
    this.mLetterSpacing = paramFloat;
    markUpdated();
  }
  
  @ReactProp(defaultFloat = -1.0F, name = "lineHeight")
  public void setLineHeight(float paramFloat) {
    this.mLineHeightInput = paramFloat;
    if (paramFloat == -1.0F) {
      paramFloat = Float.NaN;
    } else if (this.mAllowFontScaling) {
      paramFloat = PixelUtil.toPixelFromSP(paramFloat);
    } else {
      paramFloat = PixelUtil.toPixelFromDIP(paramFloat);
    } 
    this.mLineHeight = paramFloat;
    markUpdated();
  }
  
  @ReactProp(defaultInt = -1, name = "numberOfLines")
  public void setNumberOfLines(int paramInt) {
    int i = paramInt;
    if (paramInt == 0)
      i = -1; 
    this.mNumberOfLines = i;
    markUpdated();
  }
  
  @ReactProp(name = "textAlign")
  public void setTextAlign(String paramString) {
    if (paramString == null || "auto".equals(paramString)) {
      this.mTextAlign = 0;
    } else if ("left".equals(paramString)) {
      this.mTextAlign = 3;
    } else if ("right".equals(paramString)) {
      this.mTextAlign = 5;
    } else if ("center".equals(paramString)) {
      this.mTextAlign = 1;
    } else if ("justify".equals(paramString)) {
      this.mTextAlign = 3;
    } else {
      StringBuilder stringBuilder = new StringBuilder("Invalid textAlign: ");
      stringBuilder.append(paramString);
      throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    } 
    markUpdated();
  }
  
  @ReactProp(name = "textBreakStrategy")
  public void setTextBreakStrategy(String paramString) {
    if (Build.VERSION.SDK_INT < 23)
      return; 
    if (paramString == null || "highQuality".equals(paramString)) {
      this.mTextBreakStrategy = 1;
    } else if ("simple".equals(paramString)) {
      this.mTextBreakStrategy = 0;
    } else if ("balanced".equals(paramString)) {
      this.mTextBreakStrategy = 2;
    } else {
      StringBuilder stringBuilder = new StringBuilder("Invalid textBreakStrategy: ");
      stringBuilder.append(paramString);
      throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    } 
    markUpdated();
  }
  
  @ReactProp(name = "textDecorationLine")
  public void setTextDecorationLine(String paramString) {
    int i = 0;
    this.mIsUnderlineTextDecorationSet = false;
    this.mIsLineThroughTextDecorationSet = false;
    if (paramString != null) {
      String[] arrayOfString = paramString.split(" ");
      int j = arrayOfString.length;
      while (i < j) {
        String str = arrayOfString[i];
        if ("underline".equals(str)) {
          this.mIsUnderlineTextDecorationSet = true;
        } else if ("line-through".equals(str)) {
          this.mIsLineThroughTextDecorationSet = true;
        } 
        i++;
      } 
    } 
    markUpdated();
  }
  
  @ReactProp(customType = "Color", defaultInt = 1426063360, name = "textShadowColor")
  public void setTextShadowColor(int paramInt) {
    if (paramInt != this.mTextShadowColor) {
      this.mTextShadowColor = paramInt;
      markUpdated();
    } 
  }
  
  @ReactProp(name = "textShadowOffset")
  public void setTextShadowOffset(ReadableMap paramReadableMap) {
    this.mTextShadowOffsetDx = 0.0F;
    this.mTextShadowOffsetDy = 0.0F;
    if (paramReadableMap != null) {
      if (paramReadableMap.hasKey("width") && !paramReadableMap.isNull("width"))
        this.mTextShadowOffsetDx = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("width")); 
      if (paramReadableMap.hasKey("height") && !paramReadableMap.isNull("height"))
        this.mTextShadowOffsetDy = PixelUtil.toPixelFromDIP(paramReadableMap.getDouble("height")); 
    } 
    markUpdated();
  }
  
  @ReactProp(defaultInt = 1, name = "textShadowRadius")
  public void setTextShadowRadius(float paramFloat) {
    if (paramFloat != this.mTextShadowRadius) {
      this.mTextShadowRadius = paramFloat;
      markUpdated();
    } 
  }
  
  static class SetSpanOperation {
    protected int end;
    
    protected int start;
    
    protected Object what;
    
    SetSpanOperation(int param1Int1, int param1Int2, Object param1Object) {
      this.start = param1Int1;
      this.end = param1Int2;
      this.what = param1Object;
    }
    
    public void execute(SpannableStringBuilder param1SpannableStringBuilder, int param1Int) {
      byte b;
      if (this.start == 0) {
        b = 18;
      } else {
        b = 34;
      } 
      param1SpannableStringBuilder.setSpan(this.what, this.start, this.end, param1Int << 16 & 0xFF0000 | b & 0xFF00FFFF);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactBaseTextShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */