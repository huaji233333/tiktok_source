package com.tt.miniapphost.entity;

public class ShareVideoResultEntity {
  private String mShareVideoFailReason;
  
  private boolean mShareVideoResult;
  
  private boolean shouldHandle;
  
  public String getShareVideoFailReason() {
    return this.mShareVideoFailReason;
  }
  
  public boolean getShareVideoResult() {
    return this.mShareVideoResult;
  }
  
  public boolean isShouldHandle() {
    return this.shouldHandle;
  }
  
  public void setShareVideoFailReason(String paramString) {
    this.mShareVideoFailReason = paramString;
  }
  
  public void setShareVideoResult(boolean paramBoolean) {
    this.mShareVideoResult = paramBoolean;
  }
  
  public void setShouldHandle(boolean paramBoolean) {
    this.shouldHandle = paramBoolean;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\ShareVideoResultEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */