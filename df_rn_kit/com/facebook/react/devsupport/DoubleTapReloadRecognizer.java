package com.facebook.react.devsupport;

import android.os.Handler;
import android.view.View;

public class DoubleTapReloadRecognizer {
  public boolean mDoRefresh;
  
  public boolean didDoubleTapR(int paramInt, View paramView) {
    if (paramInt == 46 && !(paramView instanceof android.widget.EditText)) {
      if (this.mDoRefresh) {
        this.mDoRefresh = false;
        return true;
      } 
      this.mDoRefresh = true;
      (new Handler()).postDelayed(new Runnable() {
            public void run() {
              DoubleTapReloadRecognizer.this.mDoRefresh = false;
            }
          },  200L);
    } 
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\DoubleTapReloadRecognizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */