package com.tt.miniapphost.game;

public interface IPreEditManager {
  String getFilterType();
  
  void getPreEditResult(String paramString, PreEditResultCallback paramPreEditResultCallback);
  
  boolean isFilterApply();
  
  public static interface PreEditResultCallback {
    void onResult(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\game\IPreEditManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */