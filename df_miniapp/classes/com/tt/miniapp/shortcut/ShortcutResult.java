package com.tt.miniapp.shortcut;

public class ShortcutResult {
  private String errorMsg;
  
  private Result result;
  
  public ShortcutResult(Result paramResult, String paramString) {
    this.result = paramResult;
    this.errorMsg = paramString;
  }
  
  public String getErrorMsg() {
    return this.errorMsg;
  }
  
  public Result getResult() {
    return this.result;
  }
  
  public void setErrorMsg(String paramString) {
    this.errorMsg = paramString;
  }
  
  public void setResult(Result paramResult) {
    this.result = paramResult;
  }
  
  public enum Result {
    FAIL, NEED_CHECK, SUCCESS;
    
    static {
      $VALUES = new Result[] { SUCCESS, FAIL, NEED_CHECK };
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\ShortcutResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */