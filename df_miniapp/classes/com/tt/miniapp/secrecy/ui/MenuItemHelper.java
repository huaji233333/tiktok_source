package com.tt.miniapp.secrecy.ui;

import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tt.miniapp.secrecy.SecrecyEntity;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.UIUtils;

public class MenuItemHelper {
  private static int TIP_HEIGHT = 28;
  
  private static int TIP_IMAGE_SIZE = 16;
  
  private static int TIP_MARGIN_V = 26;
  
  private static int TIP_PADDING_H = 12;
  
  private SecrecyUIHelper mUIHelper;
  
  MenuItemHelper(SecrecyUIHelper paramSecrecyUIHelper) {
    this.mUIHelper = paramSecrecyUIHelper;
    Application application = AppbrandContext.getInst().getApplicationContext();
    TIP_PADDING_H = (int)UIUtils.dip2Px((Context)application, TIP_PADDING_H);
    TIP_HEIGHT = (int)UIUtils.dip2Px((Context)application, TIP_HEIGHT);
    TIP_MARGIN_V = (int)UIUtils.dip2Px((Context)application, TIP_MARGIN_V);
    TIP_IMAGE_SIZE = (int)UIUtils.dip2Px((Context)application, TIP_IMAGE_SIZE);
  }
  
  private CharSequence getTextSuffix(Context paramContext, int paramInt) {
    String str = getTipStr(paramContext, paramInt);
    Drawable drawable = paramContext.getDrawable(2097479710);
    if (TextUtils.isEmpty(str) || drawable == null)
      return paramContext.getString(2097742025); 
    StringBuilder stringBuilder = new StringBuilder(str);
    paramInt = (int)UIUtils.dip2Px(paramContext, 11.0F);
    drawable.setBounds(0, 0, paramInt, paramInt);
    drawable.setColorFilter(paramContext.getResources().getColor(2097348654), PorterDuff.Mode.SRC_IN);
    stringBuilder.append(' ');
    stringBuilder.append('>');
    CenterImageSpan centerImageSpan = new CenterImageSpan(drawable, 1);
    SpannableString spannableString = new SpannableString(stringBuilder);
    spannableString.setSpan(centerImageSpan, stringBuilder.length() - 1, stringBuilder.length(), 17);
    return (CharSequence)spannableString;
  }
  
  private Drawable getTipIcon(Context paramContext, int paramInt) {
    return (12 == paramInt) ? paramContext.getDrawable(2097479782) : ((13 == paramInt) ? paramContext.getDrawable(2097479783) : null);
  }
  
  private String getTipStr(Context paramContext, int paramInt) {
    int i;
    if (AppbrandApplication.getInst().getAppInfo().isGame()) {
      i = 2097741950;
    } else {
      i = 2097741949;
    } 
    String str2 = paramContext.getString(i);
    String str1 = null;
    if (12 == paramInt) {
      str1 = paramContext.getString(2097741924);
    } else if (13 == paramInt) {
      str1 = paramContext.getString(2097741831);
    } 
    return TextUtils.isEmpty(str1) ? CharacterUtils.empty() : paramContext.getString(2097742017, new Object[] { str2, str1 });
  }
  
  int addSecrecyTip(RelativeLayout paramRelativeLayout, View.OnClickListener paramOnClickListener, int paramInt) {
    Context context = paramRelativeLayout.getContext();
    SecrecyEntity secrecyEntity = this.mUIHelper.getShownEntity();
    if (secrecyEntity != null && !this.mUIHelper.isSecrecyNotStarted(secrecyEntity)) {
      String str = getTipStr(context, secrecyEntity.type);
      if (!TextUtils.isEmpty(str)) {
        Drawable drawable = getTipIcon(context, secrecyEntity.type);
        if (drawable == null)
          return 0; 
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setId(paramInt);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(16);
        paramInt = TIP_PADDING_H;
        linearLayout.setPadding(paramInt, 0, paramInt, 0);
        linearLayout.setOnClickListener(paramOnClickListener);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(context.getResources().getColor(2097348680));
        gradientDrawable.setCornerRadius(TIP_HEIGHT / 2.0F);
        linearLayout.setBackground((Drawable)gradientDrawable);
        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(drawable);
        paramInt = TIP_IMAGE_SIZE;
        linearLayout.addView((View)imageView, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(paramInt, paramInt));
        TextView textView = new TextView(context);
        textView.setText(str);
        textView.setTextSize(12.0F);
        textView.setIncludeFontPadding(false);
        textView.setTextColor(-1728053248);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams1.leftMargin = (int)UIUtils.dip2Px(context, 2.0F);
        linearLayout.addView((View)textView, (ViewGroup.LayoutParams)layoutParams1);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, TIP_HEIGHT);
        layoutParams.addRule(10);
        layoutParams.addRule(14);
        layoutParams.topMargin = TIP_MARGIN_V;
        layoutParams.bottomMargin = 0;
        paramRelativeLayout.addView((View)linearLayout, 0, (ViewGroup.LayoutParams)layoutParams);
        return TIP_MARGIN_V + TIP_HEIGHT;
      } 
    } 
    return 0;
  }
  
  void addSecrecyTip(LinearLayout paramLinearLayout, View.OnClickListener paramOnClickListener) {
    Context context = paramLinearLayout.getContext();
    TextView textView = new TextView(context);
    SecrecyEntity secrecyEntity = this.mUIHelper.getShownEntity();
    if (secrecyEntity != null) {
      if (this.mUIHelper.isSecrecyNotStarted(secrecyEntity))
        return; 
      textView.setGravity(17);
      textView.setText(getTextSuffix(context, secrecyEntity.type));
      textView.setTextSize(15.0F);
      textView.setTextColor(context.getResources().getColor(2097348654));
      textView.setOnClickListener(paramOnClickListener);
      paramLinearLayout.addView((View)textView, 0, (ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-1, (int)UIUtils.dip2Px(context, 44.0F)));
    } 
  }
  
  static class CenterImageSpan extends ImageSpan {
    CenterImageSpan(Drawable param1Drawable, int param1Int) {
      super(param1Drawable, param1Int);
    }
    
    public void draw(Canvas param1Canvas, CharSequence param1CharSequence, int param1Int1, int param1Int2, float param1Float, int param1Int3, int param1Int4, int param1Int5, Paint param1Paint) {
      param1Canvas.save();
      Drawable drawable = getDrawable();
      Paint.FontMetricsInt fontMetricsInt = param1Paint.getFontMetricsInt();
      param1Canvas.translate(param1Float, ((fontMetricsInt.descent + param1Int4 + param1Int4 + fontMetricsInt.ascent) / 2 - (drawable.getBounds()).bottom / 2));
      drawable.draw(param1Canvas);
      param1Canvas.restore();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\secrec\\ui\MenuItemHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */