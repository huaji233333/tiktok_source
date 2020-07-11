package com.tt.miniapp.manager.basebundle;

class BaseBundleVersionEntity {
  private volatile String jssdkFourVerUpdateStr;
  
  private String jssdkThreeVerStr;
  
  private BaseBundleVersionEntity() {}
  
  static BaseBundleVersionEntity getInstance() {
    return HOLDER.entity;
  }
  
  public String getJssdkFourVerUpdateStr() {
    return this.jssdkFourVerUpdateStr;
  }
  
  public String getJssdkThreeVerStr() {
    return this.jssdkThreeVerStr;
  }
  
  public void setJssdkFourVerUpdateStr(String paramString) {
    this.jssdkFourVerUpdateStr = paramString;
  }
  
  public void setJssdkThreeVerStr(String paramString) {
    this.jssdkThreeVerStr = paramString;
  }
  
  public String toLogString() {
    StringBuilder stringBuilder = new StringBuilder("BaseBundleEntity{jssdkThreeVerStr='");
    stringBuilder.append(this.jssdkThreeVerStr);
    stringBuilder.append('\'');
    stringBuilder.append(", jssdkFourVerUpdateStr='");
    stringBuilder.append(this.jssdkFourVerUpdateStr);
    stringBuilder.append('\'');
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  static class HOLDER {
    public static final BaseBundleVersionEntity entity = new BaseBundleVersionEntity();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\BaseBundleVersionEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */