package com.facebook.react.devsupport.interfaces;

import android.util.Pair;

public interface ErrorCustomizer {
  Pair<String, StackFrame[]> customizeErrorInfo(Pair<String, StackFrame[]> paramPair);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\interfaces\ErrorCustomizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */