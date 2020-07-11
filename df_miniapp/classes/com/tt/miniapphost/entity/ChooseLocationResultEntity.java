package com.tt.miniapphost.entity;

public class ChooseLocationResultEntity {
  private String address;
  
  private boolean isCancel;
  
  private double latitude;
  
  private double longitude;
  
  private String name;
  
  public ChooseLocationResultEntity(boolean paramBoolean, double paramDouble1, double paramDouble2, String paramString1, String paramString2) {
    this.isCancel = paramBoolean;
    this.latitude = paramDouble1;
    this.longitude = paramDouble2;
    this.name = paramString1;
    this.address = paramString2;
  }
  
  public String getAddress() {
    return this.address;
  }
  
  public double getLatitude() {
    return this.latitude;
  }
  
  public double getLongitude() {
    return this.longitude;
  }
  
  public String getName() {
    return this.name;
  }
  
  public boolean isCancel() {
    return this.isCancel;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\ChooseLocationResultEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */