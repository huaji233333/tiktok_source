package com.facebook.react.views.checkbox;

import android.content.Context;
import android.widget.CheckBox;

class ReactCheckBox extends CheckBox {
  private boolean mAllowChange = true;
  
  public ReactCheckBox(Context paramContext) {
    super(paramContext);
  }
  
  public void setChecked(boolean paramBoolean) {
    if (this.mAllowChange) {
      this.mAllowChange = false;
      super.setChecked(paramBoolean);
    } 
  }
  
  void setOn(boolean paramBoolean) {
    if (isChecked() != paramBoolean)
      super.setChecked(paramBoolean); 
    this.mAllowChange = true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\checkbox\ReactCheckBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */