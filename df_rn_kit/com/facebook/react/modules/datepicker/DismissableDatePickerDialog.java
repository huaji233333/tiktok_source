package com.facebook.react.modules.datepicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;

public class DismissableDatePickerDialog extends DatePickerDialog {
  public DismissableDatePickerDialog(Context paramContext, int paramInt1, DatePickerDialog.OnDateSetListener paramOnDateSetListener, int paramInt2, int paramInt3, int paramInt4) {
    super(paramContext, paramInt1, paramOnDateSetListener, paramInt2, paramInt3, paramInt4);
  }
  
  public DismissableDatePickerDialog(Context paramContext, DatePickerDialog.OnDateSetListener paramOnDateSetListener, int paramInt1, int paramInt2, int paramInt3) {
    super(paramContext, paramOnDateSetListener, paramInt1, paramInt2, paramInt3);
  }
  
  protected void onStop() {
    if (Build.VERSION.SDK_INT > 19)
      super.onStop(); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\datepicker\DismissableDatePickerDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */