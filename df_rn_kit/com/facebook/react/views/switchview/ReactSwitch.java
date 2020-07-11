package com.facebook.react.views.switchview;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;

class ReactSwitch extends SwitchCompat {
  private boolean mAllowChange = true;
  
  public ReactSwitch(Context paramContext) {
    super(paramContext);
  }
  
  public void setChecked(boolean paramBoolean) {
    if (this.mAllowChange && isChecked() != paramBoolean) {
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


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\switchview\ReactSwitch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */