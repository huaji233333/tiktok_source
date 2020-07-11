package com.tt.miniapphost.entity;

public class ApiErrorInfoEntity {
  private StringBuilder mMsgBuilder = new StringBuilder();
  
  private Throwable mThrowable;
  
  public ApiErrorInfoEntity append(Object paramObject) {
    this.mMsgBuilder.append(paramObject);
    return this;
  }
  
  public String getErrorMsg() {
    return this.mMsgBuilder.toString();
  }
  
  public Throwable getThrowable() {
    return this.mThrowable;
  }
  
  public void setThrowable(Throwable paramThrowable) {
    this.mThrowable = paramThrowable;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\ApiErrorInfoEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */