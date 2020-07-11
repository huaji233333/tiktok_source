package com.facebook.react.views.text;

import android.os.Build;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.facebook.i.a.a;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaNode;
import com.facebook.yoga.a;
import com.facebook.yoga.b;

public class ReactTextShadowNode extends ReactBaseTextShadowNode {
  public static final TextPaint sTextPaintInstance = new TextPaint(1);
  
  public Spannable mPreparedSpannableText;
  
  private final YogaMeasureFunction mTextMeasureFunction = new YogaMeasureFunction() {
      public long measure(YogaNode param1YogaNode, float param1Float1, YogaMeasureMode param1YogaMeasureMode1, float param1Float2, YogaMeasureMode param1YogaMeasureMode2) {
        StaticLayout staticLayout;
        int i;
        TextPaint textPaint = ReactTextShadowNode.sTextPaintInstance;
        Spanned spanned = (Spanned)a.a(ReactTextShadowNode.this.mPreparedSpannableText, "Spannable element has not been prepared in onBeforeLayout");
        BoringLayout.Metrics metrics = BoringLayout.isBoring((CharSequence)spanned, textPaint);
        if (metrics == null) {
          param1Float2 = Layout.getDesiredWidth((CharSequence)spanned, textPaint);
        } else {
          param1Float2 = Float.NaN;
        } 
        if (param1YogaMeasureMode1 == YogaMeasureMode.UNDEFINED || param1Float1 < 0.0F) {
          i = 1;
        } else {
          i = 0;
        } 
        if (metrics == null && (i || (!a.a(param1Float2) && param1Float2 <= param1Float1))) {
          i = (int)Math.ceil(param1Float2);
          if (Build.VERSION.SDK_INT < 23) {
            staticLayout = new StaticLayout((CharSequence)spanned, textPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, ReactTextShadowNode.this.mIncludeFontPadding);
          } else {
            staticLayout = StaticLayout.Builder.obtain((CharSequence)spanned, 0, spanned.length(), (TextPaint)staticLayout, i).setAlignment(Layout.Alignment.ALIGN_NORMAL).setLineSpacing(0.0F, 1.0F).setIncludePad(ReactTextShadowNode.this.mIncludeFontPadding).setBreakStrategy(ReactTextShadowNode.this.mTextBreakStrategy).setHyphenationFrequency(1).build();
          } 
        } else {
          BoringLayout boringLayout;
          if (metrics != null && (i != 0 || metrics.width <= param1Float1)) {
            boringLayout = BoringLayout.make((CharSequence)spanned, (TextPaint)staticLayout, metrics.width, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, metrics, ReactTextShadowNode.this.mIncludeFontPadding);
          } else if (Build.VERSION.SDK_INT < 23) {
            staticLayout = new StaticLayout((CharSequence)spanned, (TextPaint)boringLayout, (int)param1Float1, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, ReactTextShadowNode.this.mIncludeFontPadding);
          } else {
            staticLayout = StaticLayout.Builder.obtain((CharSequence)spanned, 0, spanned.length(), (TextPaint)staticLayout, (int)param1Float1).setAlignment(Layout.Alignment.ALIGN_NORMAL).setLineSpacing(0.0F, 1.0F).setIncludePad(ReactTextShadowNode.this.mIncludeFontPadding).setBreakStrategy(ReactTextShadowNode.this.mTextBreakStrategy).setHyphenationFrequency(1).build();
          } 
        } 
        return (ReactTextShadowNode.this.mNumberOfLines != -1 && ReactTextShadowNode.this.mNumberOfLines < staticLayout.getLineCount()) ? b.a(staticLayout.getWidth(), staticLayout.getLineBottom(ReactTextShadowNode.this.mNumberOfLines - 1)) : b.a(staticLayout.getWidth(), staticLayout.getHeight());
      }
    };
  
  public ReactTextShadowNode() {
    initMeasureFunction();
  }
  
  private ReactTextShadowNode(ReactTextShadowNode paramReactTextShadowNode) {
    super(paramReactTextShadowNode);
    this.mPreparedSpannableText = paramReactTextShadowNode.mPreparedSpannableText;
    initMeasureFunction();
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
  
  private void initMeasureFunction() {
    if (!isVirtual())
      setMeasureFunction(this.mTextMeasureFunction); 
  }
  
  public boolean isVirtualAnchor() {
    return true;
  }
  
  public void markUpdated() {
    super.markUpdated();
    dirty();
  }
  
  public LayoutShadowNode mutableCopy() {
    return new ReactTextShadowNode(this);
  }
  
  public void onBeforeLayout() {
    this.mPreparedSpannableText = spannedFromShadowNode(this, (String)null);
    markUpdated();
  }
  
  public void onCollectExtraUpdates(UIViewOperationQueue paramUIViewOperationQueue) {
    super.onCollectExtraUpdates(paramUIViewOperationQueue);
    Spannable spannable = this.mPreparedSpannableText;
    if (spannable != null) {
      ReactTextUpdate reactTextUpdate = new ReactTextUpdate(spannable, -1, this.mContainsImages, getPadding(4), getPadding(1), getPadding(5), getPadding(3), getTextAlign(), this.mTextBreakStrategy);
      paramUIViewOperationQueue.enqueueUpdateExtraData(getReactTag(), reactTextUpdate);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\text\ReactTextShadowNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */