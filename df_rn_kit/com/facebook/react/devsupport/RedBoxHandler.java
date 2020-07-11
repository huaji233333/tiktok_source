package com.facebook.react.devsupport;

import android.content.Context;
import android.text.SpannedString;
import com.facebook.react.devsupport.interfaces.StackFrame;

public interface RedBoxHandler {
  void handleRedbox(String paramString, StackFrame[] paramArrayOfStackFrame, ErrorType paramErrorType);
  
  boolean isReportEnabled();
  
  void reportRedbox(Context paramContext, String paramString1, StackFrame[] paramArrayOfStackFrame, String paramString2, ReportCompletedListener paramReportCompletedListener);
  
  public enum ErrorType {
    JS("JS"),
    NATIVE("Native");
    
    private final String name;
    
    static {
    
    }
    
    ErrorType(String param1String1) {
      this.name = param1String1;
    }
    
    public final String getName() {
      return this.name;
    }
  }
  
  public static interface ReportCompletedListener {
    void onReportError(SpannedString param1SpannedString);
    
    void onReportSuccess(SpannedString param1SpannedString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\RedBoxHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */