package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.react.bridge.JSApplicationCausedNativeException;

public class IllegalViewOperationException extends JSApplicationCausedNativeException {
  private View mView;
  
  public IllegalViewOperationException(String paramString) {
    super(paramString);
  }
  
  public IllegalViewOperationException(String paramString, View paramView, Throwable paramThrowable) {
    super(paramString, paramThrowable);
    this.mView = paramView;
  }
  
  public View getView() {
    return this.mView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\IllegalViewOperationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */