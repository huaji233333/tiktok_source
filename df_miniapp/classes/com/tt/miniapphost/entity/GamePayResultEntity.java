package com.tt.miniapphost.entity;

public class GamePayResultEntity {
  private int mCode = -1;
  
  private String mMessage = "";
  
  private boolean mShouldHandle;
  
  public int getCode() {
    return this.mCode;
  }
  
  public String getMessage() {
    return this.mMessage;
  }
  
  public boolean isShouldHandle() {
    return this.mShouldHandle;
  }
  
  public void setCode(int paramInt) {
    this.mCode = paramInt;
  }
  
  public void setMessage(String paramString) {
    this.mMessage = paramString;
  }
  
  public void setShouldHandle(boolean paramBoolean) {
    this.mShouldHandle = paramBoolean;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\GamePayResultEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */