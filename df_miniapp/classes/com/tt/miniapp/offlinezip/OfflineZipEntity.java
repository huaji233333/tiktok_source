package com.tt.miniapp.offlinezip;

import android.os.Parcel;
import android.os.Parcelable;
import d.f.b.g;
import d.f.b.l;

public final class OfflineZipEntity implements Parcelable {
  public static final CREATOR CREATOR = new CREATOR(null);
  
  public String md5;
  
  public String moduleName;
  
  public String url;
  
  public OfflineZipEntity() {}
  
  public OfflineZipEntity(Parcel paramParcel) {
    this();
    String str3 = paramParcel.readString();
    String str2 = str3;
    if (str3 == null)
      str2 = ""; 
    this.moduleName = str2;
    str3 = paramParcel.readString();
    str2 = str3;
    if (str3 == null)
      str2 = ""; 
    this.url = str2;
    str2 = paramParcel.readString();
    String str1 = str2;
    if (str2 == null)
      str1 = ""; 
    this.md5 = str1;
  }
  
  public OfflineZipEntity(String paramString1, String paramString2, String paramString3) {
    this();
    this.moduleName = paramString1;
    this.url = paramString2;
    this.md5 = paramString3;
  }
  
  public final int describeContents() {
    return 0;
  }
  
  public final String getMd5() {
    String str = this.md5;
    if (str == null)
      l.a("md5"); 
    return str;
  }
  
  public final String getModuleName() {
    String str = this.moduleName;
    if (str == null)
      l.a("moduleName"); 
    return str;
  }
  
  public final String getUrl() {
    String str = this.url;
    if (str == null)
      l.a("url"); 
    return str;
  }
  
  public final void setMd5(String paramString) {
    l.b(paramString, "<set-?>");
    this.md5 = paramString;
  }
  
  public final void setModuleName(String paramString) {
    l.b(paramString, "<set-?>");
    this.moduleName = paramString;
  }
  
  public final void setUrl(String paramString) {
    l.b(paramString, "<set-?>");
    this.url = paramString;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt) {
    l.b(paramParcel, "parcel");
    String str = this.moduleName;
    if (str == null)
      l.a("moduleName"); 
    paramParcel.writeString(str);
    str = this.url;
    if (str == null)
      l.a("url"); 
    paramParcel.writeString(str);
    str = this.md5;
    if (str == null)
      l.a("md5"); 
    paramParcel.writeString(str);
  }
  
  public static final class CREATOR implements Parcelable.Creator<OfflineZipEntity> {
    private CREATOR() {}
    
    public final OfflineZipEntity createFromParcel(Parcel param1Parcel) {
      l.b(param1Parcel, "parcel");
      return new OfflineZipEntity(param1Parcel);
    }
    
    public final OfflineZipEntity[] newArray(int param1Int) {
      return new OfflineZipEntity[param1Int];
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\offlinezip\OfflineZipEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */