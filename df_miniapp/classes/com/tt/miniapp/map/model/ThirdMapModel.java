package com.tt.miniapp.map.model;

import com.tt.option.m.c;

public class ThirdMapModel {
  private String name;
  
  private c point;
  
  public ThirdMapModel(String paramString, c paramc) {
    this.name = paramString;
    this.point = paramc;
  }
  
  public String getName() {
    return this.name;
  }
  
  public c getPoint() {
    return this.point;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public void setPoint(c paramc) {
    this.point = paramc;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\map\model\ThirdMapModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */