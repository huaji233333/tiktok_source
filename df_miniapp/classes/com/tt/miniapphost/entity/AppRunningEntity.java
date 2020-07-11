package com.tt.miniapphost.entity;

public class AppRunningEntity {
  private String appId;
  
  private String appName;
  
  private boolean isGame;
  
  private boolean isSpecial;
  
  private String preAppId;
  
  public String getAppId() {
    return this.appId;
  }
  
  public String getAppName() {
    return this.appName;
  }
  
  public String getPreAppId() {
    return this.preAppId;
  }
  
  public boolean isGame() {
    return this.isGame;
  }
  
  public boolean isSpecial() {
    return this.isSpecial;
  }
  
  public void setAppId(String paramString) {
    this.appId = paramString;
  }
  
  public void setAppName(String paramString) {
    this.appName = paramString;
  }
  
  public void setGame(boolean paramBoolean) {
    this.isGame = paramBoolean;
  }
  
  public void setPreAppId(String paramString) {
    this.preAppId = paramString;
  }
  
  public void setSpecial(boolean paramBoolean) {
    this.isSpecial = paramBoolean;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("{appId: ");
    stringBuilder.append(this.appId);
    stringBuilder.append(", appName: ");
    stringBuilder.append(this.appName);
    stringBuilder.append(", isGame: ");
    stringBuilder.append(this.isGame);
    stringBuilder.append(", isSpecial: ");
    stringBuilder.append(this.isSpecial);
    stringBuilder.append(", preAppId: ");
    stringBuilder.append(this.preAppId);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\AppRunningEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */