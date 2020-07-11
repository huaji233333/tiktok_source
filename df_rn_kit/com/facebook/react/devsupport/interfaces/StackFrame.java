package com.facebook.react.devsupport.interfaces;

import org.json.JSONObject;

public interface StackFrame {
  int getColumn();
  
  String getFile();
  
  String getFileName();
  
  int getLine();
  
  String getMethod();
  
  JSONObject toJSON();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\interfaces\StackFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */