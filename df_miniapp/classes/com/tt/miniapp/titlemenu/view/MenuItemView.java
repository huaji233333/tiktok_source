package com.tt.miniapp.titlemenu.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.c;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tt.miniapp.component.nativeview.game.RoundedImageView;
import com.tt.miniapp.event.Event;
import com.tt.miniapphost.entity.NativeUIParamsEntity;
import com.tt.miniapphost.util.UIUtils;

public class MenuItemView extends LinearLayout {
  boolean isReportHostCustomClickEvent = true;
  
  private int mIconHeight;
  
  private RoundedImageView mIconImageView;
  
  private int mIconWidth;
  
  private int mItemHeight;
  
  private int mItemWidth;
  
  private int mLabelHeight;
  
  private TextView mLabelTextView;
  
  private int mLabelWidth;
  
  public MenuItemView(Context paramContext) {
    super(paramContext);
    this.mIconWidth = (int)UIUtils.dip2Px(paramContext, 48.0F);
    this.mIconHeight = (int)UIUtils.dip2Px(paramContext, 48.0F);
    this.mIconImageView = new RoundedImageView(paramContext);
    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(this.mIconWidth, this.mIconHeight);
    this.mIconImageView.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
    this.mIconImageView.setImageDrawable((Drawable)new ColorDrawable(-1));
    int i = (int)UIUtils.dip2Px(paramContext, 12.0F);
    this.mIconImageView.setPadding(i, i, i, i);
    this.mIconImageView.setId(View.generateViewId());
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setColor(c.c(paramContext, 2097348681));
    int j = (int)(this.mIconHeight * NativeUIParamsEntity.getInst().getMorePanelItemCornerRadiusRatio());
    if (NativeUIParamsEntity.getInst().getMorePanelItemCornerRadiusRatio() == 0.5D) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i != 0) {
      gradientDrawable.setShape(1);
    } else {
      gradientDrawable.setCornerRadius(j);
    } 
    this.mIconImageView.setBackground((Drawable)gradientDrawable);
    this.mLabelWidth = (int)UIUtils.dip2Px(paramContext, 62.0F);
    this.mLabelHeight = (int)UIUtils.dip2Px(paramContext, 14.0F);
    this.mLabelTextView = new TextView(paramContext);
    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(this.mLabelWidth, -2);
    this.mLabelTextView.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
    this.mLabelTextView.setTextColor(c.c(paramContext, 2097348614));
    this.mLabelTextView.setGravity(17);
    float f = paramContext.getResources().getDimension(2097414176);
    this.mLabelTextView.setTextSize(0, f);
    this.mLabelTextView.setMaxLines(2);
    this.mLabelTextView.setEllipsize(TextUtils.TruncateAt.END);
    this.mLabelTextView.setLineSpacing(UIUtils.dip2Px(paramContext, 2.0F), 1.0F);
    layoutParams1.topMargin = (int)UIUtils.dip2Px(paramContext, 6.0F);
    addView((View)this.mIconImageView);
    addView((View)this.mLabelTextView);
    j = this.mIconWidth;
    int k = this.mLabelWidth;
    i = j;
    if (j < k)
      i = k; 
    this.mItemWidth = i;
    this.mItemHeight = this.mIconHeight + this.mLabelHeight + layoutParams1.topMargin;
    setOrientation(1);
    setGravity(17);
    setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
  }
  
  private void reportHostCustomClickEvent() {
    Event.builder("mp_host_custom_click").kv("params_title", this.mLabelTextView.getText()).flush();
  }
  
  public int getItemHeight() {
    return this.mItemHeight;
  }
  
  public int getItemWidth() {
    return this.mItemWidth;
  }
  
  public boolean performClick() {
    if (this.isReportHostCustomClickEvent)
      reportHostCustomClickEvent(); 
    return super.performClick();
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.mIconImageView.setImageDrawable(paramDrawable);
  }
  
  public void setLabel(String paramString) {
    this.mLabelTextView.setText(paramString);
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener) {
    super.setOnClickListener(paramOnClickListener);
  }
  
  public void setReportHostCustomClickEvent(boolean paramBoolean) {
    this.isReportHostCustomClickEvent = paramBoolean;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlemenu\view\MenuItemView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */