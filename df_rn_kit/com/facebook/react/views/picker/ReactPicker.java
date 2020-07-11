package com.facebook.react.views.picker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class ReactPicker extends Spinner {
  private int mMode;
  
  public OnSelectListener mOnSelectListener;
  
  private Integer mPrimaryColor;
  
  private Integer mStagedSelection;
  
  public boolean mSuppressNextEvent;
  
  private final Runnable measureAndLayout = new Runnable() {
      public void run() {
        ReactPicker reactPicker = ReactPicker.this;
        reactPicker.measure(View.MeasureSpec.makeMeasureSpec(reactPicker.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(ReactPicker.this.getHeight(), 1073741824));
        reactPicker = ReactPicker.this;
        reactPicker.layout(reactPicker.getLeft(), ReactPicker.this.getTop(), ReactPicker.this.getRight(), ReactPicker.this.getBottom());
      }
    };
  
  public ReactPicker(Context paramContext) {
    super(paramContext);
  }
  
  public ReactPicker(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.mMode = paramInt;
  }
  
  public ReactPicker(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public ReactPicker(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public ReactPicker(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    this.mMode = paramInt2;
  }
  
  private void setSelectionWithSuppressEvent(int paramInt) {
    if (paramInt != getSelectedItemPosition()) {
      this.mSuppressNextEvent = true;
      setSelection(paramInt);
    } 
  }
  
  public int getMode() {
    return this.mMode;
  }
  
  public OnSelectListener getOnSelectListener() {
    return this.mOnSelectListener;
  }
  
  public Integer getPrimaryColor() {
    return this.mPrimaryColor;
  }
  
  public void requestLayout() {
    super.requestLayout();
    post(this.measureAndLayout);
  }
  
  public void setOnSelectListener(OnSelectListener paramOnSelectListener) {
    if (getOnItemSelectedListener() == null) {
      this.mSuppressNextEvent = true;
      setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
              if (!ReactPicker.this.mSuppressNextEvent && ReactPicker.this.mOnSelectListener != null)
                ReactPicker.this.mOnSelectListener.onItemSelected(param1Int); 
              ReactPicker.this.mSuppressNextEvent = false;
            }
            
            public void onNothingSelected(AdapterView<?> param1AdapterView) {
              if (!ReactPicker.this.mSuppressNextEvent && ReactPicker.this.mOnSelectListener != null)
                ReactPicker.this.mOnSelectListener.onItemSelected(-1); 
              ReactPicker.this.mSuppressNextEvent = false;
            }
          });
    } 
    this.mOnSelectListener = paramOnSelectListener;
  }
  
  public void setPrimaryColor(Integer paramInteger) {
    this.mPrimaryColor = paramInteger;
  }
  
  public void setStagedSelection(int paramInt) {
    this.mStagedSelection = Integer.valueOf(paramInt);
  }
  
  public void updateStagedSelection() {
    Integer integer = this.mStagedSelection;
    if (integer != null) {
      setSelectionWithSuppressEvent(integer.intValue());
      this.mStagedSelection = null;
    } 
  }
  
  public static interface OnSelectListener {
    void onItemSelected(int param1Int);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\picker\ReactPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */