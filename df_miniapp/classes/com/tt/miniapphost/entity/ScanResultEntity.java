package com.tt.miniapphost.entity;

public class ScanResultEntity {
  private String result;
  
  private int resultType;
  
  private String scanType;
  
  private boolean shouldHandle;
  
  public String getResult() {
    return this.result;
  }
  
  public int getResultType() {
    return this.resultType;
  }
  
  public String getScanType() {
    return this.scanType;
  }
  
  public boolean isShouldHandle() {
    return this.shouldHandle;
  }
  
  public void setResult(String paramString) {
    this.result = paramString;
  }
  
  public void setResultType(int paramInt) {
    this.resultType = paramInt;
  }
  
  public void setScanType(String paramString) {
    this.scanType = paramString;
  }
  
  public void setShouldHandle(boolean paramBoolean) {
    this.shouldHandle = paramBoolean;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\ScanResultEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */