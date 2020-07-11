package com.tt.miniapp.shortcut.dialog;

import android.content.Context;
import android.support.v4.content.c;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DialogConfig {
  public SingleSpanView cancelBtn;
  
  public MultiSpanView content;
  
  public Context context;
  
  public SingleSpanView okBtn;
  
  public SingleSpanView title;
  
  public DialogConfig() {}
  
  public DialogConfig(SingleSpanView paramSingleSpanView1, MultiSpanView paramMultiSpanView, Context paramContext, SingleSpanView paramSingleSpanView2, SingleSpanView paramSingleSpanView3) {
    this.title = paramSingleSpanView1;
    this.content = paramMultiSpanView;
    this.context = paramContext;
    this.okBtn = paramSingleSpanView2;
    this.cancelBtn = paramSingleSpanView3;
  }
  
  public static TextSpan createDefaultContent(Context paramContext, String paramString) {
    return (new TextSpan.Builder()).setText(paramString).setTextColor(c.c(paramContext, 2097348608)).setTextSize(paramContext.getResources().getDimension(2097414179)).build();
  }
  
  public static List<TextSpan> createDefaultContent(Context paramContext, String[] paramArrayOfString) {
    ArrayList<TextSpan> arrayList = new ArrayList();
    int j = paramArrayOfString.length;
    for (int i = 0; i < j; i++) {
      String str = paramArrayOfString[i];
      arrayList.add((new TextSpan.Builder()).setText(str).setTextColor(c.c(paramContext, 2097348608)).setTextSize(paramContext.getResources().getDimension(2097414179)).build());
    } 
    return arrayList;
  }
  
  public static TextSpan createDefaultTitle(Context paramContext, String paramString) {
    return (new TextSpan.Builder()).setText(paramString).setTextColor(c.c(paramContext, 2097348612)).setTextSize(paramContext.getResources().getDimension(2097414179)).build();
  }
  
  private SpannableString parseSpan(TextView paramTextView, TextSpan paramTextSpan) {
    final String text = paramTextSpan.getText();
    SpannableString spannableString = new SpannableString(str);
    spannableString.setSpan(new AbsoluteSizeSpan((int)paramTextSpan.getTextSize()), 0, str.length(), 33);
    final int textColor = paramTextSpan.getTextColor();
    spannableString.setSpan(new ForegroundColorSpan(i), 0, str.length(), 33);
    spannableString.setSpan(new StyleSpan(paramTextSpan.getTypeFace()), 0, str.length(), 33);
    final TextClickListener textClickListener = paramTextSpan.getTextClickListener();
    if (textClickListener != null) {
      paramTextView.setMovementMethod(LinkMovementMethod.getInstance());
      spannableString.setSpan(new ClickableSpan() {
            public void onClick(View param1View) {
              textClickListener.onTextClick(text);
            }
            
            public void updateDrawState(TextPaint param1TextPaint) {
              super.updateDrawState(param1TextPaint);
              param1TextPaint.setColor(textColor);
              param1TextPaint.setUnderlineText(false);
            }
          },  0, str.length(), 33);
    } 
    return spannableString;
  }
  
  public void applySpan(TextView paramTextView, TextSpan paramTextSpan) {
    if (paramTextSpan != null) {
      if (paramTextView == null)
        return; 
      paramTextView.setText((CharSequence)parseSpan(paramTextView, paramTextSpan));
    } 
  }
  
  public void applySpans(TextView paramTextView, List<TextSpan> paramList) {
    if (paramTextView != null) {
      if (paramList == null)
        return; 
      SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
      Iterator<TextSpan> iterator = paramList.iterator();
      while (iterator.hasNext())
        spannableStringBuilder.append((CharSequence)parseSpan(paramTextView, iterator.next())); 
      paramTextView.setText((CharSequence)spannableStringBuilder);
    } 
  }
  
  public static interface TextClickListener {
    void onTextClick(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\dialog\DialogConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */