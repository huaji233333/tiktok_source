package com.facebook.react.views.slider;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

public class ReactSlider extends SeekBar {
  private static int DEFAULT_TOTAL_STEPS = 128;
  
  private double mMaxValue;
  
  private double mMinValue;
  
  private double mStep;
  
  private double mStepCalculated;
  
  private double mValue;
  
  public ReactSlider(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private double getStepValue() {
    double d = this.mStep;
    return (d > 0.0D) ? d : this.mStepCalculated;
  }
  
  private int getTotalSteps() {
    return (int)Math.ceil((this.mMaxValue - this.mMinValue) / getStepValue());
  }
  
  private void updateAll() {
    if (this.mStep == 0.0D) {
      double d1 = this.mMaxValue;
      double d2 = this.mMinValue;
      double d3 = DEFAULT_TOTAL_STEPS;
      Double.isNaN(d3);
      this.mStepCalculated = (d1 - d2) / d3;
    } 
    setMax(getTotalSteps());
    updateValue();
  }
  
  private void updateValue() {
    double d1 = this.mValue;
    double d2 = this.mMinValue;
    d1 = (d1 - d2) / (this.mMaxValue - d2);
    d2 = getTotalSteps();
    Double.isNaN(d2);
    setProgress((int)Math.round(d1 * d2));
  }
  
  void setMaxValue(double paramDouble) {
    this.mMaxValue = paramDouble;
    updateAll();
  }
  
  void setMinValue(double paramDouble) {
    this.mMinValue = paramDouble;
    updateAll();
  }
  
  void setStep(double paramDouble) {
    this.mStep = paramDouble;
    updateAll();
  }
  
  void setValue(double paramDouble) {
    this.mValue = paramDouble;
    updateValue();
  }
  
  public double toRealProgress(int paramInt) {
    if (paramInt == getMax())
      return this.mMaxValue; 
    double d1 = paramInt;
    double d2 = getStepValue();
    Double.isNaN(d1);
    return d1 * d2 + this.mMinValue;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\slider\ReactSlider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */