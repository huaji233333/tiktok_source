package com.tt.miniapp.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;

public class InputMethodUtil {
  public static void hideSoftKeyboard(Activity paramActivity) {
    AppBrandLogger.d("tma_InputMethodUtil", new Object[] { "hideSoftKeyboard" });
    if (paramActivity != null) {
      InputMethodManager inputMethodManager = (InputMethodManager)paramActivity.getApplication().getSystemService("input_method");
      View view = paramActivity.getCurrentFocus();
      if (view != null)
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0); 
    } 
  }
  
  public static void hideSoftKeyboard(EditText paramEditText, Context paramContext) {
    AppBrandLogger.d("tma_InputMethodUtil", new Object[] { "hideSoftKeyboard1" });
    if (paramEditText != null) {
      AppBrandLogger.d("tma_InputMethodUtil", new Object[] { "hideSoftKeyboard1 mEditText != null" });
      ((InputMethodManager)paramContext.getSystemService("input_method")).hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
    } 
  }
  
  public static void showSoftKeyboard(final View view, final Context mContext) {
    AppBrandLogger.d("tma_InputMethodUtil", new Object[] { "showSoftKeyboard" });
    if (view.requestFocus())
      AppbrandContext.mainHandler.post(new Runnable() {
            public final void run() {
              ((InputMethodManager)mContext.getSystemService("input_method")).showSoftInput(view, 1);
            }
          }); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\InputMethodUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */