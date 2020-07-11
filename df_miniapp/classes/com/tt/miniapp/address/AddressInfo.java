package com.tt.miniapp.address;

import com.tt.miniapp.component.nativeview.picker.wheel.entity.WheelItem;

public class AddressInfo implements WheelItem {
  public String code = "";
  
  public String name = "";
  
  public String type = "";
  
  public AddressInfo(String paramString1, String paramString2, String paramString3) {
    this.name = paramString1;
    this.code = paramString2;
    this.type = paramString3;
  }
  
  public String getAddrHashCode() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.name);
    stringBuilder.append(this.code);
    stringBuilder.append(this.type);
    return stringBuilder.toString();
  }
  
  public String getName() {
    return this.name;
  }
  
  public String toString() {
    return this.name;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\address\AddressInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */