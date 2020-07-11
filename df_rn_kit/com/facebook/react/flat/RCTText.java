package com.facebook.react.flat;

import android.graphics.Typeface;
import android.support.v4.e.d;
import android.support.v4.e.e;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import com.facebook.g.a.a;
import com.facebook.g.a.a.a;
import com.facebook.g.a.c;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.b;

final class RCTText extends RCTVirtualText implements YogaMeasureFunction {
  private static final c sTextLayoutBuilder;
  
  private int mAlignment;
  
  private DrawTextLayout mDrawCommand;
  
  private boolean mIncludeFontPadding = true;
  
  private int mNumberOfLines = Integer.MAX_VALUE;
  
  private float mSpacingAdd;
  
  private float mSpacingMult = 1.0F;
  
  private CharSequence mText;
  
  static {
    c c1 = new c();
    c1.e = false;
    c1.f = true;
    c1.d = (a)new a();
    sTextLayoutBuilder = c1;
  }
  
  public RCTText() {
    setMeasureFunction(this);
    getSpan().setFontSize(getDefaultFontSize());
  }
  
  private static Layout createTextLayout(int paramInt1, YogaMeasureMode paramYogaMeasureMode, TextUtils.TruncateAt paramTruncateAt, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, CharSequence paramCharSequence, int paramInt3, float paramFloat1, float paramFloat2, int paramInt4, Layout.Alignment paramAlignment) {
    StringBuilder stringBuilder;
    int i = null.$SwitchMap$com$facebook$yoga$YogaMeasureMode[paramYogaMeasureMode.ordinal()];
    byte b = 2;
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          stringBuilder = new StringBuilder("Unexpected size mode: ");
          stringBuilder.append(paramYogaMeasureMode);
          throw new IllegalStateException(stringBuilder.toString());
        } 
      } else {
        b = 1;
      } 
    } else {
      b = 0;
    } 
    c c1 = sTextLayoutBuilder;
    if (c1.b.h != stringBuilder) {
      c1.b.h = (TextUtils.TruncateAt)stringBuilder;
      c1.c = null;
    } 
    if (c1.b.j != paramInt2) {
      c1.b.j = paramInt2;
      c1.c = null;
    } 
    if (c1.b.i != paramBoolean2) {
      c1.b.i = paramBoolean2;
      c1.c = null;
    } 
    c1 = c1.a(paramCharSequence);
    float f1 = c1.b.a.getTextSize();
    float f2 = paramInt3;
    if (f1 != f2) {
      c1.b.a();
      c1.b.a.setTextSize(f2);
      c1.c = null;
    } 
    if (c1.b.b != paramInt1 || c1.b.c != b) {
      c1.b.b = paramInt1;
      c1.b.c = b;
      c1.c = null;
    } 
    c1 = sTextLayoutBuilder;
    Typeface typeface = Typeface.defaultFromStyle(paramInt4);
    if (c1.b.a.getTypeface() != typeface) {
      c1.b.a();
      c1.b.a.setTypeface(typeface);
      c1.c = null;
    } 
    c1 = sTextLayoutBuilder;
    d d = e.c;
    if (c1.b.l != d) {
      c1.b.l = d;
      c1.c = null;
    } 
    c1 = sTextLayoutBuilder;
    if (c1.b.g != paramBoolean1) {
      c1.b.g = paramBoolean1;
      c1.c = null;
    } 
    c1 = sTextLayoutBuilder;
    if (c1.b.f != paramFloat1) {
      c1.b.f = paramFloat1;
      c1.c = null;
    } 
    c1 = sTextLayoutBuilder;
    if (c1.b.e != paramFloat2) {
      c1.b.e = paramFloat2;
      c1.c = null;
    } 
    c1 = sTextLayoutBuilder;
    if (c1.b.k != paramAlignment) {
      c1.b.k = paramAlignment;
      c1.c = null;
    } 
    Layout layout = sTextLayoutBuilder.a();
    sTextLayoutBuilder.a(null);
    return layout;
  }
  
  protected final void collectState(StateBuilder paramStateBuilder, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    boolean bool;
    super.collectState(paramStateBuilder, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
    if (this.mText == null) {
      if (paramFloat4 - paramFloat2 > 0.0F && paramFloat3 - paramFloat1 > 0.0F) {
        SpannableStringBuilder spannableStringBuilder = getText();
        if (!TextUtils.isEmpty((CharSequence)spannableStringBuilder))
          this.mText = (CharSequence)spannableStringBuilder; 
      } 
      if (this.mText == null)
        return; 
    } 
    if (this.mDrawCommand == null) {
      boolean bool1;
      bool = (int)Math.ceil((paramFloat3 - paramFloat1));
      YogaMeasureMode yogaMeasureMode = YogaMeasureMode.EXACTLY;
      TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
      boolean bool2 = this.mIncludeFontPadding;
      int i = this.mNumberOfLines;
      if (i == 1) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      this.mDrawCommand = new DrawTextLayout(createTextLayout(bool, yogaMeasureMode, truncateAt, bool2, i, bool1, this.mText, getFontSize(), this.mSpacingAdd, this.mSpacingMult, getFontStyle(), getAlignment()));
      bool = true;
    } else {
      bool = false;
    } 
    paramFloat1 += getPadding(0);
    paramFloat2 += getPadding(1);
    paramFloat3 = this.mDrawCommand.getLayoutWidth();
    paramFloat4 = this.mDrawCommand.getLayoutHeight();
    this.mDrawCommand = (DrawTextLayout)this.mDrawCommand.updateBoundsAndFreeze(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
    paramStateBuilder.addDrawCommand(this.mDrawCommand);
    if (bool) {
      NodeRegion nodeRegion = getNodeRegion();
      if (nodeRegion instanceof TextNodeRegion)
        ((TextNodeRegion)nodeRegion).setLayout(this.mDrawCommand.getLayout()); 
    } 
    performCollectAttachDetachListeners(paramStateBuilder);
  }
  
  final boolean doesDraw() {
    return true;
  }
  
  public final Layout.Alignment getAlignment() {
    boolean bool;
    if (getLayoutDirection() == YogaDirection.RTL) {
      bool = true;
    } else {
      bool = false;
    } 
    int i = this.mAlignment;
    byte b = 4;
    if (i != 3) {
      if (i != 5)
        return (i != 17) ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_CENTER; 
      if (bool)
        b = 3; 
      return Layout.Alignment.values()[b];
    } 
    if (!bool)
      b = 3; 
    return Layout.Alignment.values()[b];
  }
  
  protected final int getDefaultFontSize() {
    return fontSizeFromSp(14.0F);
  }
  
  public final boolean isVirtual() {
    return false;
  }
  
  public final boolean isVirtualAnchor() {
    return true;
  }
  
  public final long measure(YogaNode paramYogaNode, float paramFloat1, YogaMeasureMode paramYogaMeasureMode1, float paramFloat2, YogaMeasureMode paramYogaMeasureMode2) {
    SpannableStringBuilder spannableStringBuilder = getText();
    if (TextUtils.isEmpty((CharSequence)spannableStringBuilder)) {
      this.mText = null;
      return b.a(0, 0);
    } 
    this.mText = (CharSequence)spannableStringBuilder;
    int i = (int)Math.ceil(paramFloat1);
    TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
    boolean bool1 = this.mIncludeFontPadding;
    int j = this.mNumberOfLines;
    boolean bool = true;
    if (j != 1)
      bool = false; 
    Layout layout = createTextLayout(i, paramYogaMeasureMode1, truncateAt, bool1, j, bool, (CharSequence)spannableStringBuilder, getFontSize(), this.mSpacingAdd, this.mSpacingMult, getFontStyle(), getAlignment());
    DrawTextLayout drawTextLayout = this.mDrawCommand;
    if (drawTextLayout != null && !drawTextLayout.isFrozen()) {
      this.mDrawCommand.setLayout(layout);
    } else {
      this.mDrawCommand = new DrawTextLayout(layout);
    } 
    return b.a(this.mDrawCommand.getLayoutWidth(), this.mDrawCommand.getLayoutHeight());
  }
  
  protected final void notifyChanged(boolean paramBoolean) {
    dirty();
  }
  
  @ReactProp(defaultBoolean = true, name = "includeFontPadding")
  public final void setIncludeFontPadding(boolean paramBoolean) {
    this.mIncludeFontPadding = paramBoolean;
  }
  
  @ReactProp(defaultDouble = NaND, name = "lineHeight")
  public final void setLineHeight(double paramDouble) {
    if (Double.isNaN(paramDouble)) {
      this.mSpacingMult = 1.0F;
      this.mSpacingAdd = 0.0F;
    } else {
      this.mSpacingMult = 0.0F;
      this.mSpacingAdd = PixelUtil.toPixelFromSP((float)paramDouble);
    } 
    notifyChanged(true);
  }
  
  @ReactProp(defaultInt = 2147483647, name = "numberOfLines")
  public final void setNumberOfLines(int paramInt) {
    this.mNumberOfLines = paramInt;
    notifyChanged(true);
  }
  
  @ReactProp(name = "textAlign")
  public final void setTextAlign(String paramString) {
    if (paramString == null || "auto".equals(paramString)) {
      this.mAlignment = 0;
    } else if ("left".equals(paramString)) {
      this.mAlignment = 3;
    } else if ("right".equals(paramString)) {
      this.mAlignment = 5;
    } else if ("center".equals(paramString)) {
      this.mAlignment = 17;
    } else {
      StringBuilder stringBuilder = new StringBuilder("Invalid textAlign: ");
      stringBuilder.append(paramString);
      throw new JSApplicationIllegalArgumentException(stringBuilder.toString());
    } 
    notifyChanged(false);
  }
  
  final void updateNodeRegion(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean) {
    NodeRegion nodeRegion = getNodeRegion();
    if (this.mDrawCommand == null) {
      if (!nodeRegion.matches(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean))
        setNodeRegion(new TextNodeRegion(paramFloat1, paramFloat2, paramFloat3, paramFloat4, getReactTag(), paramBoolean, null)); 
      return;
    } 
    Layout layout1 = null;
    if (nodeRegion instanceof TextNodeRegion)
      layout1 = ((TextNodeRegion)nodeRegion).getLayout(); 
    Layout layout2 = this.mDrawCommand.getLayout();
    if (!nodeRegion.matches(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramBoolean) || layout1 != layout2)
      setNodeRegion(new TextNodeRegion(paramFloat1, paramFloat2, paramFloat3, paramFloat4, getReactTag(), paramBoolean, layout2)); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\flat\RCTText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */