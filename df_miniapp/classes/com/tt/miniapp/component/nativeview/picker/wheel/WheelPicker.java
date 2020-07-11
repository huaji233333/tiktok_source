package com.tt.miniapp.component.nativeview.picker.wheel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tt.miniapp.component.nativeview.picker.framework.popup.ConfirmPopup;

public abstract class WheelPicker extends ConfirmPopup<View> {
  protected boolean cycleDisable = true;
  
  protected WheelView.DividerConfig dividerConfig = new WheelView.DividerConfig();
  
  protected int labelTextColor = -14540254;
  
  protected float lineSpaceMultiplier = 3.0F;
  
  protected int offset = 5;
  
  protected int outTextSize = 15;
  
  protected int textColorFocus = -14540254;
  
  protected int textColorNormal = -6710887;
  
  protected int textPadding = -1;
  
  protected int textSize = 17;
  
  protected boolean textSizeAutoFit = true;
  
  protected Typeface typeface = Typeface.DEFAULT;
  
  protected boolean useWeight = true;
  
  public WheelPicker(Activity paramActivity) {
    super(paramActivity);
  }
  
  protected TextView createLabelView() {
    TextView textView = new TextView((Context)this.activity);
    textView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    textView.setTextColor(this.labelTextColor);
    textView.setTextSize(this.textSize);
    return textView;
  }
  
  protected WheelView createWheelView() {
    WheelView wheelView = new WheelView((Context)this.activity);
    wheelView.setLineSpaceMultiplier(this.lineSpaceMultiplier);
    wheelView.setTextPadding(this.textPadding);
    wheelView.setTextSize(this.textSize);
    wheelView.setOutTextSize(this.outTextSize);
    wheelView.setTypeface(this.typeface);
    wheelView.setTextColor(this.textColorNormal, this.textColorFocus);
    wheelView.setDividerConfig(this.dividerConfig);
    wheelView.setOffset(this.offset);
    wheelView.setCycleDisable(this.cycleDisable);
    wheelView.setUseWeight(this.useWeight);
    wheelView.setTextSizeAutoFit(this.textSizeAutoFit);
    return wheelView;
  }
  
  public View getContentView() {
    if (this.centerView == null)
      this.centerView = makeCenterView(); 
    return this.centerView;
  }
  
  public void setCycleDisable(boolean paramBoolean) {
    this.cycleDisable = paramBoolean;
  }
  
  public void setDividerColor(int paramInt) {
    if (this.dividerConfig == null)
      this.dividerConfig = new WheelView.DividerConfig(); 
    this.dividerConfig.setVisible(true);
    this.dividerConfig.setColor(paramInt);
  }
  
  public void setDividerConfig(WheelView.DividerConfig paramDividerConfig) {
    if (paramDividerConfig == null) {
      this.dividerConfig = new WheelView.DividerConfig();
      this.dividerConfig.setVisible(false);
      this.dividerConfig.setShadowVisible(false);
      return;
    } 
    this.dividerConfig = paramDividerConfig;
  }
  
  public void setDividerRatio(float paramFloat) {
    if (this.dividerConfig == null)
      this.dividerConfig = new WheelView.DividerConfig(); 
    this.dividerConfig.setRatio(paramFloat);
  }
  
  public void setDividerVisible(boolean paramBoolean) {
    if (this.dividerConfig == null)
      this.dividerConfig = new WheelView.DividerConfig(); 
    this.dividerConfig.setVisible(paramBoolean);
  }
  
  public void setLabelTextColor(int paramInt) {
    this.labelTextColor = paramInt;
  }
  
  @Deprecated
  public void setLineColor(int paramInt) {
    setDividerColor(paramInt);
  }
  
  @Deprecated
  public void setLineConfig(WheelView.DividerConfig paramDividerConfig) {
    setDividerConfig(paramDividerConfig);
  }
  
  public final void setLineSpaceMultiplier(float paramFloat) {
    this.lineSpaceMultiplier = paramFloat;
  }
  
  @Deprecated
  public void setLineVisible(boolean paramBoolean) {
    setDividerVisible(paramBoolean);
  }
  
  public void setOutTextSize(int paramInt) {
    this.outTextSize = paramInt;
  }
  
  @Deprecated
  public void setPadding(int paramInt) {
    this.textPadding = paramInt;
  }
  
  public void setShadowColor(int paramInt) {
    setShadowColor(paramInt, 100);
  }
  
  public void setShadowColor(int paramInt1, int paramInt2) {
    if (this.dividerConfig == null)
      this.dividerConfig = new WheelView.DividerConfig(); 
    this.dividerConfig.setShadowColor(paramInt1);
    this.dividerConfig.setShadowAlpha(paramInt2);
  }
  
  public void setShadowVisible(boolean paramBoolean) {
    if (this.dividerConfig == null)
      this.dividerConfig = new WheelView.DividerConfig(); 
    this.dividerConfig.setShadowVisible(paramBoolean);
  }
  
  public void setTextColor(int paramInt) {
    this.textColorFocus = paramInt;
  }
  
  public void setTextColor(int paramInt1, int paramInt2) {
    this.textColorFocus = paramInt1;
    this.textColorNormal = paramInt2;
  }
  
  public void setTextPadding(int paramInt) {
    this.textPadding = paramInt;
  }
  
  public void setTextSize(int paramInt) {
    this.textSize = paramInt;
  }
  
  public void setTextSizeAutoFit(boolean paramBoolean) {
    this.textSizeAutoFit = paramBoolean;
  }
  
  public void setUseWeight(boolean paramBoolean) {
    this.useWeight = paramBoolean;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\picker\wheel\WheelPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */